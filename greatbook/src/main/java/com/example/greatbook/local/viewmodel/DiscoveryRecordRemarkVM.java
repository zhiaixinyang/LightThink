package com.example.greatbook.local.viewmodel;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.text.Editable;
import android.text.TextWatcher;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.DeleteCallback;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.SaveCallback;
import com.example.greatbook.R;
import com.example.greatbook.constants.Constants;
import com.example.greatbook.nethot.model.DiscoveryRecord;
import com.example.greatbook.nethot.model.RecordRemark;
import com.example.greatbook.local.model.leancloud.LRecordLike;
import com.example.greatbook.local.model.leancloud.LRecordRemark;
import com.example.greatbook.model.event.LikeEvent;
import com.example.greatbook.model.leancloud.LLocalGroup;
import com.example.greatbook.model.leancloud.LLocalRecord;
import com.example.greatbook.model.leancloud.User;
import com.example.greatbook.utils.LogUtils;
import com.example.greatbook.utils.NetUtil;
import com.example.greatbook.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by MDove on 2017/8/21.
 */

public class DiscoveryRecordRemarkVM {
    public ObservableField<Long> id = new ObservableField<>();
    public ObservableField<String> time = new ObservableField<>();
    public ObservableField<String> title = new ObservableField<>();
    public ObservableField<String> content = new ObservableField<>();

    //对应的账号信息，用于网络同步
    public ObservableField<String> belongId = new ObservableField<>();

    //自己的分组id
    public ObservableField<Long> groupId = new ObservableField<>();
    public ObservableField<String> groupTitle = new ObservableField<>();
    public ObservableField<String> likeNum = new ObservableField<>();
    public ObservableField<String> nick = new ObservableField<>();
    public ObservableField<RecordRemark> data = new ObservableField<>();
    public ObservableField<String> photoPath = new ObservableField<>();
    public ObservableField<String> avatarPath = new ObservableField<>();
    public ObservableBoolean loadingRemarks = new ObservableBoolean();
    public ObservableBoolean isRemarksEmpty = new ObservableBoolean();
    public ObservableBoolean isNetErr = new ObservableBoolean();
    public ObservableInt iconLike = new ObservableInt();
    //发送评论
    public String remarksContent;
    public ObservableField<String> etRemarksContent = new ObservableField<>();

    private DiscoveryRecord discoveryRecord;
    private LLocalRecord lLocalRecord;
    private int itemPosition;

    public DiscoveryRecordRemarkVM(DiscoveryRecord discoveryRecord, int itemPosition) {
        this.discoveryRecord = discoveryRecord;
        this.itemPosition = itemPosition;
        RecordRemark recordRemark = new RecordRemark();
        recordRemark.date = new ArrayList<>();
        data.set(recordRemark);
        loadingRemarks.set(false);
        isRemarksEmpty.set(true);
        isNetErr.set(false);
        if (discoveryRecord.likeNum > 0) {
            iconLike.set(R.drawable.icon_good_on);
            likeNum.set(discoveryRecord.likeNum + "");
        } else {
            likeNum.set("0");
            iconLike.set(R.drawable.icon_good_off);
        }
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
                    LRecordRemark lRecordRemark = new LRecordRemark();
                    lRecordRemark.setContent(content);
                    lRecordRemark.setBelongId(discoveryRecord.objectId);
                    lRecordRemark.setBelongUserId(user.getObjectId());
                    lRecordRemark.saveInBackground(new SaveCallback() {
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
                                    initRemarks(discoveryRecord.objectId);
                                    etRemarksContent.set("");
                                    break;
                                case "1":
                                    break;
                            }
                        }
                    });

        }
    }

    public void btnLike() {
        if (NetUtil.isNetworkAvailable()) {
            final User user = AVUser.getCurrentUser(User.class);
            if (user != null) {
                AVQuery<LRecordLike> query = AVQuery.getQuery(LRecordLike.class);
                query.whereEqualTo("belongId", discoveryRecord.objectId);
                query.whereEqualTo("belongUserId", user.getObjectId());
                query.findInBackground(new FindCallback<LRecordLike>() {
                    @Override
                    public void done(List<LRecordLike> list, AVException e) {
                        if (e == null) {
                            LogUtils.d(list.size());
                            if (list.isEmpty()) {
                                LRecordLike like = new LRecordLike();
                                like.setBelongId(discoveryRecord.objectId);
                                like.setBelongUserId(user.getObjectId());
                                like.saveInBackground(new SaveCallback() {
                                    @Override
                                    public void done(AVException e) {
                                        if (e == null) {
                                            lLocalRecord.setLikeNum(lLocalRecord.getLikeNum() + 1);
                                            lLocalRecord.saveInBackground();
                                            iconLike.set(R.drawable.icon_good_on);
                                            likeNum.set((Integer.valueOf(likeNum.get()) + 1) + "");
                                            ToastUtil.toastShort("点赞成功");
                                            LikeEvent event = new LikeEvent();
                                            event.itemPostion=itemPosition;
                                            event.event = Constants.RECORD_REMARKS_LIKE_TO_REFRESH;
                                            EventBus.getDefault().post(event);
                                        }
                                    }
                                });
                            } else {
                                LRecordLike like = list.get(0);
                                like.deleteInBackground(new DeleteCallback() {
                                    @Override
                                    public void done(AVException e) {
                                        if (e == null) {
                                            lLocalRecord.setLikeNum(lLocalRecord.getLikeNum() - 1);
                                            lLocalRecord.saveInBackground();
                                            iconLike.set(R.drawable.icon_good_off);
                                            likeNum.set((Integer.valueOf(likeNum.get()) - 1) + "");
                                            ToastUtil.toastShort("取消点赞");
                                        }
                                    }
                                });
                            }
                        }
                    }
                });
            }
        } else {
            ToastUtil.toastShort("请保证网络连接畅通");
        }
    }

    public void initAllMes(final String belongId) {
        AVQuery<User> queryUser = AVQuery.getQuery(User.class);
        queryUser.whereEqualTo("objectId", belongId);
        queryUser.findInBackground(new FindCallback<User>() {
            @Override
            public void done(List<User> list, AVException e) {
                if (e == null && !list.isEmpty()) {
                    nick.set(list.get(0).getName());
                    avatarPath.set(list.get(0).getAvatar().getUrl());
                }
            }
        });

        AVQuery<LLocalGroup> queryGroup = AVQuery.getQuery(LLocalGroup.class);
        queryGroup.whereEqualTo("belongId", belongId);
        queryGroup.whereEqualTo("groupId", groupId.get());
        queryGroup.findInBackground(new FindCallback<LLocalGroup>() {
            @Override
            public void done(List<LLocalGroup> list, AVException e) {
                if (e == null && !list.isEmpty()) {
                    LLocalGroup group = list.get(0);
                    groupTitle.set(group.getGroupTitle());
                    photoPath.set(group.getGroupPhoto().getUrl() == null ? "" :
                            group.getGroupPhoto().getUrl());
                }
            }
        });
    }

    public void initRemarks(final String objectId) {
        if (NetUtil.isNetworkAvailable()) {
            loadingRemarks.set(true);
            AVQuery<LLocalRecord> queryRecord = AVQuery.getQuery(LLocalRecord.class);
            queryRecord.whereEqualTo("objectId", objectId);
            queryRecord.findInBackground(new FindCallback<LLocalRecord>() {
                @Override
                public void done(List<LLocalRecord> list, AVException e) {
                    if (e == null && !list.isEmpty()) {
                        isNetErr.set(false);

                        lLocalRecord = list.get(0);
                        AVQuery<LRecordRemark> query = AVQuery.getQuery(LRecordRemark.class);
                        query.whereEqualTo("belongId", objectId);
                        query.findInBackground(new FindCallback<LRecordRemark>() {
                            @Override
                            public void done(List<LRecordRemark> list, AVException e) {
                                if (e == null) {
                                    loadingRemarks.set(false);
                                    if (!list.isEmpty()) {
                                        RecordRemark recordRemark = new RecordRemark();
                                        recordRemark.date = list;
                                        data.set(recordRemark);
                                        isRemarksEmpty.set(false);
                                    } else {
                                        isRemarksEmpty.set(true);
                                    }
                                }
                            }
                        });
                    } else {
                        isNetErr.set(true);
                    }
                }
            });

        } else {
            ToastUtil.toastShort("请保证网络链接");
        }
    }
}
