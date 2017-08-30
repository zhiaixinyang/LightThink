package com.example.greatbook.middle.viewmodel;

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
import com.example.greatbook.middle.model.DiscoveryRecord;
import com.example.greatbook.middle.model.DiscoveryTopGroup;
import com.example.greatbook.middle.model.GroupAndRecords;
import com.example.greatbook.middle.model.RecordRemark;
import com.example.greatbook.middle.model.leancloud.LGroupFollowers;
import com.example.greatbook.middle.model.leancloud.LRecordLike;
import com.example.greatbook.middle.model.leancloud.LRecordRemark;
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
 * Created by MDove on 2017/8/24.
 */

public class DiscoveryGroupAndRecordsVM {
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
    public ObservableField<String> photoPath = new ObservableField<>();
    public ObservableField<String> avatarPath = new ObservableField<>();
    public ObservableBoolean loadingRecords = new ObservableBoolean();
    public ObservableBoolean isRecordsEmpty = new ObservableBoolean();
    public ObservableBoolean isNetErr = new ObservableBoolean();
    public ObservableInt iconLike = new ObservableInt();
    public ObservableField<GroupAndRecords> group = new ObservableField<>();

    private DiscoveryTopGroup discoveryTopGroup;

    public DiscoveryGroupAndRecordsVM(DiscoveryTopGroup discoveryTopGroup) {
        isNetErr.set(false);
        this.discoveryTopGroup = discoveryTopGroup;
    }

    public void initGroup() {
        belongId.set(discoveryTopGroup.belongId);
        content.set(discoveryTopGroup.content);
        groupId.set(discoveryTopGroup.groupId);
        groupTitle.set(discoveryTopGroup.groupTitle);
        likeNum.set(discoveryTopGroup.attentionNum + "");
        photoPath.set(discoveryTopGroup.groupPhotoPath);
        avatarPath.set(discoveryTopGroup.avatarPath);
        iconLike.set(R.drawable.btn_follow_off);
    }

    public void initRecords() {
        if (NetUtil.isNetworkAvailable()) {
            isNetErr.set(false);
            loadingRecords.set(true);
            AVQuery<LLocalRecord> query = AVQuery.getQuery(LLocalRecord.class);
            query.whereEqualTo("belongId", discoveryTopGroup.objectId);
            query.findInBackground(new FindCallback<LLocalRecord>() {
                @Override
                public void done(List<LLocalRecord> list, AVException e) {
                    if (e == null) {
                        if (!list.isEmpty()) {
                            GroupAndRecords groupAndRecords = new GroupAndRecords();
                            groupAndRecords.data = list;
                            group.set(groupAndRecords);
                            isRecordsEmpty.set(false);
                            loadingRecords.set(false);
                        } else {
                            isRecordsEmpty.set(true);
                            loadingRecords.set(false);
                        }
                    } else {
                        loadingRecords.set(false);
                    }
                }
            });
        } else {
            isNetErr.set(true);
        }
    }

    public void follower(){
        if (NetUtil.isWifi()) {
            User user = AVUser.getCurrentUser(User.class);
            if (user!=null) {
                LGroupFollowers followers = new LGroupFollowers();
                followers.setBelongUserId(discoveryTopGroup.belongId);
                followers.setGroupObjectId(discoveryTopGroup.objectId);
                followers.setFollowingUserId(user.getObjectId());

                followers.saveInBackground();
            }
        }

    }
}
