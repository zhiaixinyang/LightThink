package com.example.greatbook.nethot.viewmodel;

import android.content.Context;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.SaveCallback;
import com.example.greatbook.App;
import com.example.greatbook.R;
import com.example.greatbook.greendao.MyCollectingDao;
import com.example.greatbook.greendao.entity.MyCollecting;
import com.example.greatbook.local.model.leancloud.LGroupCollects;
import com.example.greatbook.model.leancloud.User;
import com.example.greatbook.nethot.model.DiscoveryTopGroup;
import com.example.greatbook.nethot.model.GroupRecords;
import com.example.greatbook.nethot.presenter.GroupAndRecordsPresenter;
import com.example.greatbook.utils.DateUtils;
import com.example.greatbook.utils.NetUtil;

import java.util.List;

/**
 * Created by MDove on 2017/8/24.
 */

public class DiscoveryGroupAndRecordsVM {
    public ObservableField<Long> id = new ObservableField<>();
    public ObservableField<String> time = new ObservableField<>();
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
    public ObservableBoolean isNetErr = new ObservableBoolean();
    public ObservableInt iconCollecting = new ObservableInt();
    public ObservableField<GroupRecords> group = new ObservableField<>();

    private DiscoveryTopGroup discoveryTopGroup;
    private GroupAndRecordsPresenter mPresenter;

    private MyCollectingDao mMyCollectingDao;
    private boolean isCollect;
    private Context mContext;

    public DiscoveryGroupAndRecordsVM(DiscoveryTopGroup discoveryTopGroup, GroupAndRecordsPresenter presenter) {
        mContext = App.getInstance().getContext();
        mMyCollectingDao = App.getDaoSession().getMyCollectingDao();
        mPresenter = presenter;
        if (NetUtil.isNetworkAvailable()) {
            isNetErr.set(false);
        } else {
            isNetErr.set(true);
        }
        this.discoveryTopGroup = discoveryTopGroup;
        MyCollecting myCollecting = null;
        if (!mMyCollectingDao.queryBuilder()
                .where(MyCollectingDao.Properties.GroupObjectId.eq(discoveryTopGroup.objectId))
                .list().isEmpty()) {
            myCollecting = mMyCollectingDao.queryBuilder()
                    .where(MyCollectingDao.Properties.GroupObjectId.eq(discoveryTopGroup.objectId))
                    .list().get(0);
        }
        if (myCollecting != null) {
            isCollect = true;
        } else {
            isCollect = false;
        }

        GroupRecords groupRecords = new GroupRecords();
        group.set(groupRecords);
    }

    public void initGroup() {
        belongId.set(discoveryTopGroup.belongId);
        content.set(discoveryTopGroup.content);
        groupId.set(discoveryTopGroup.groupId);
        groupTitle.set(discoveryTopGroup.groupTitle);
        likeNum.set(discoveryTopGroup.attentionNum + "");
        photoPath.set(discoveryTopGroup.groupPhotoPath);
        //关于User的数据是在加载Fragment的时候加载User数据时set的
        avatarPath.set(discoveryTopGroup.avatarPath);
        nick.set(discoveryTopGroup.userNick);
        if (isCollect) {
            iconCollecting.set(R.drawable.btn_collect_on);
        } else {
            iconCollecting.set(R.drawable.btn_collect_off);
        }
        time.set(DateUtils.getDateChinese(discoveryTopGroup.time));
    }

    public void follower() {
        if (NetUtil.isNetworkAvailable()) {
            mPresenter.followerLoading();
            User user = AVUser.getCurrentUser(User.class);
            if (user != null && discoveryTopGroup != null) {
                if (isCollect) {
                    MyCollecting myCollecting = null;
                    if (!mMyCollectingDao.queryBuilder()
                            .where(MyCollectingDao.Properties.BelongUserId.eq(discoveryTopGroup.belongId))
                            .list().isEmpty()) {
                        myCollecting = mMyCollectingDao.queryBuilder()
                                .where(MyCollectingDao.Properties.BelongUserId.eq(discoveryTopGroup.belongId))
                                .list()
                                .get(0);
                    }
                    if (myCollecting != null) {
                        AVQuery<LGroupCollects> query = AVQuery.getQuery(LGroupCollects.class);
                        query.whereEqualTo("belongUserId", discoveryTopGroup.belongId);
                        query.whereEqualTo("collectingUserId", user.getObjectId());
                        query.findInBackground(new FindCallback<LGroupCollects>() {
                            @Override
                            public void done(List<LGroupCollects> list, AVException e) {
                                if (e == null && !list.isEmpty()) {
                                    list.get(0).deleteEventually();
                                }
                            }
                        });
                        mMyCollectingDao.delete(myCollecting);
                        isCollect = false;
                        mPresenter.followerErr("取消收藏");
                        likeNum.set(String.valueOf(Integer.parseInt(likeNum.get()) - 1));
                        iconCollecting.set(R.drawable.btn_collect_off);
                    }
                } else {
                    LGroupCollects followers = new LGroupCollects();
                    followers.setBelongUserId(discoveryTopGroup.belongId);
                    followers.setGroupObjectId(discoveryTopGroup.objectId);
                    followers.setCollectingUserId(user.getObjectId());
                    followers.saveInBackground();

                    MyCollecting myCollecting = new MyCollecting();
                    myCollecting.belongUserId = discoveryTopGroup.belongId;
                    myCollecting.groupObjectId = discoveryTopGroup.objectId;
                    long insert = mMyCollectingDao.insert(myCollecting);
                    if (insert > 0) {
                        likeNum.set(String.valueOf(Integer.parseInt(likeNum.get()) + 1));
                        iconCollecting.set(R.drawable.btn_collect_on);
                        mPresenter.followerSuc("收藏成功");
                        isCollect = true;
                    }
                }

            } else {
                mPresenter.followerErr("请登陆账号");
            }
        } else {
            mPresenter.showError(mContext.getString(R.string.net_err));
        }
    }
}
