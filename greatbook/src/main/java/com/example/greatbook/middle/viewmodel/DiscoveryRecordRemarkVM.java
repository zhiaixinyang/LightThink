package com.example.greatbook.middle.viewmodel;

import android.databinding.ObservableField;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.example.greatbook.model.leancloud.LLocalGroup;
import com.example.greatbook.model.leancloud.User;

import java.util.List;

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
    public ObservableField<Integer> likeNum = new ObservableField<>();
    public ObservableField<String> nick = new ObservableField<>();
    public ObservableField<String> photoPath = new ObservableField<>();

    public void initAllMes(final String belongId) {
        AVQuery<User> queryUser = AVQuery.getQuery(User.class);
        queryUser.whereEqualTo("objectId", belongId);
        queryUser.findInBackground(new FindCallback<User>() {
            @Override
            public void done(List<User> list, AVException e) {
                if (e == null && !list.isEmpty()) {
                    nick.set(list.get(0).getName());
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
                    LLocalGroup group=list.get(0);
                    groupTitle.set(group.getGroupTitle());
                    photoPath.set(group.getGroupPhoto().getUrl()==null?"":
                            group.getGroupPhoto().getUrl());
                }
            }
        });
    }
}
