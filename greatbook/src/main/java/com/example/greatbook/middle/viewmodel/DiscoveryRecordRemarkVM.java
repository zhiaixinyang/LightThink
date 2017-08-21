package com.example.greatbook.middle.viewmodel;

import android.databinding.ObservableField;
import android.databinding.tool.util.L;
import android.text.Editable;
import android.text.TextWatcher;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVOperationQueue;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.example.greatbook.middle.model.RecordRemark;
import com.example.greatbook.middle.model.leancloud.LRecordRemark;
import com.example.greatbook.model.leancloud.LLocalGroup;
import com.example.greatbook.model.leancloud.User;

import java.util.List;
import java.util.Objects;

import rx.Observable;

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
    //发送评论
    public String remarksContent;

    public TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            remarksContent=s.toString();
        }
    };

    public void sendRemarkContent(String content) {
        User user = AVUser.getCurrentUser(User.class);
        LRecordRemark lRecordRemark = new LRecordRemark();
        lRecordRemark.setContent(content);
        lRecordRemark.saveInBackground();
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

    public void initRemarks(String belongId) {
        AVQuery<LRecordRemark> query = AVQuery.getQuery(LRecordRemark.class);
        query.findInBackground(new FindCallback<LRecordRemark>() {
            @Override
            public void done(List<LRecordRemark> list, AVException e) {
                if (e == null) {
                    RecordRemark recordRemark=new RecordRemark();
                    recordRemark.date=list;
                    data.set(recordRemark);
                }
            }
        });
    }
}
