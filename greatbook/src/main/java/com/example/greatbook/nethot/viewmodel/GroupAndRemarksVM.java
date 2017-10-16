package com.example.greatbook.nethot.viewmodel;

import android.content.Context;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.text.Editable;
import android.text.TextWatcher;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.SaveCallback;
import com.example.greatbook.App;
import com.example.greatbook.R;
import com.example.greatbook.local.model.leancloud.LGroupRemark;
import com.example.greatbook.model.leancloud.User;
import com.example.greatbook.nethot.model.DiscoveryTopGroup;
import com.example.greatbook.nethot.model.GroupRemarks;
import com.example.greatbook.utils.LogUtils;
import com.example.greatbook.utils.NetUtil;
import com.example.greatbook.utils.ToastUtil;

import java.util.List;

import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by MDove on 2017/10/15.
 */

public class GroupAndRemarksVM {
    //发送评论
    public String remarksContent;
    public ObservableField<String> etRemarksContent = new ObservableField<>();
    public ObservableBoolean loadingRemarks = new ObservableBoolean();
    public ObservableBoolean isRemarksEmpty = new ObservableBoolean();
    public ObservableBoolean isNetErr = new ObservableBoolean();
    private DiscoveryTopGroup discoveryTopGroup;
    public ObservableField<GroupRemarks> groupRemarksObservableField = new ObservableField<>();
    private Context mContext;

    public GroupAndRemarksVM(DiscoveryTopGroup discoveryTopGroup) {
        this.discoveryTopGroup = discoveryTopGroup;
        mContext = App.getInstance().getContext();
        GroupRemarks recordRemarks = new GroupRemarks();
        groupRemarksObservableField.set(recordRemarks);
        if (NetUtil.isNetworkAvailable()) {
            isNetErr.set(false);
        } else {
            isNetErr.set(true);
        }
        loadingRemarks.set(true);
        isRemarksEmpty.set(false);
    }

    public TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            remarksContent = s.toString();
        }
    };

    public void sendRemarkContent(final String content) {
        final User user = AVUser.getCurrentUser(User.class);
        if (user != null) {
            io.reactivex.Observable.create(new ObservableOnSubscribe<String>() {
                @Override
                public void subscribe(@NonNull final ObservableEmitter<String> emitter) throws Exception {
                    LGroupRemark lGroupRemark = new LGroupRemark();
                    lGroupRemark.setContent(content);
                    lGroupRemark.setBelongId(discoveryTopGroup.objectId);
                    lGroupRemark.setBelongUserId(user.getObjectId());
                    lGroupRemark.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(AVException e) {
                            if (e == null) {
                                emitter.onNext("0");
                            } else {
                                emitter.onNext("1");
                            }
                        }
                    });
                }
            }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<String>() {
                        @Override
                        public void accept(@NonNull String s) throws Exception {
                            switch (s) {
                                case "0":
                                    ToastUtil.toastShort("评论成功");
                                    initRemarks();
                                    etRemarksContent.set("");
                                    break;
                                case "1":
                                    break;
                            }
                        }
                    });

        }
    }

    public void initRemarks() {
        if (NetUtil.isNetworkAvailable()) {
            loadingRemarks.set(true);
            isNetErr.set(false);

            if (discoveryTopGroup != null) {
                AVQuery<LGroupRemark> query = AVQuery.getQuery(LGroupRemark.class);
                query.whereEqualTo("belongId", discoveryTopGroup.objectId);
                query.findInBackground(new FindCallback<LGroupRemark>() {
                    @Override
                    public void done(List<LGroupRemark> list, AVException e) {
                        if (e == null) {
                            loadingRemarks.set(false);
                            if (!list.isEmpty()) {
                                LogUtils.d(list.size());
                                GroupRemarks recordRemarks = new GroupRemarks();
                                recordRemarks.data = list;
                                groupRemarksObservableField.set(recordRemarks);
                                isRemarksEmpty.set(false);
                            } else {
                                isRemarksEmpty.set(true);
                            }
                        }
                    }
                });
            }
        } else {
            ToastUtil.toastShort(mContext.getString(R.string.net_err));
        }
    }
}
