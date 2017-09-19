package com.example.greatbook.local.presenter;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SaveCallback;
import com.example.greatbook.App;
import com.example.greatbook.base.RxPresenter;
import com.example.greatbook.greendao.LocalGroupDao;
import com.example.greatbook.greendao.LocalRecordDao;
import com.example.greatbook.greendao.entity.LocalGroup;
import com.example.greatbook.greendao.entity.LocalRecord;
import com.example.greatbook.local.presenter.contract.LocalAddContract;
import com.example.greatbook.model.leancloud.LLocalGroup;
import com.example.greatbook.model.leancloud.LLocalRecord;
import com.example.greatbook.model.leancloud.User;
import com.example.greatbook.utils.NetUtil;
import com.example.greatbook.utils.RxUtil;
import com.example.greatbook.utils.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by MDove on 2017/8/11.
 */

public class LocalAddPresenter extends RxPresenter<LocalAddContract.View> implements LocalAddContract.Presenter {
    private LocalRecordDao localRecordDao;
    private LocalGroupDao localGroupDao;

    public LocalAddPresenter(LocalAddContract.View view) {
        mView = view;
        localRecordDao = App.getDaoSession().getLocalRecordDao();
        localGroupDao = App.getDaoSession().getLocalGroupDao();
    }

    @Override
    public void sendContentToNet(LocalRecord localRecord) {
        if (NetUtil.isNetworkAvailable()) {
            User user = AVUser.getCurrentUser(User.class);
            if (user != null) {
                final LLocalRecord lLocalRecord = new LLocalRecord();
                lLocalRecord.setContent(localRecord.getContent());
                lLocalRecord.setBelongId(localRecord.getBelongId());
                lLocalRecord.setGroupTitle(localRecord.getGroupTitle());
                lLocalRecord.setGroupId(localRecord.getGroupId());
                lLocalRecord.setTime(localRecord.getTimeDate());
                lLocalRecord.setTitle(localRecord.getTitle());
                lLocalRecord.setType(localRecord.getType());
                lLocalRecord.setBelongLocalId(localRecord.getId() + "");
                lLocalRecord.setLikeNum(0);
                Subscription subscription = Observable.create(new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(final Subscriber<? super String> subscriber) {
                        lLocalRecord.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(AVException e) {
                                if (e == null) {
                                    subscriber.onNext("上传至服务器");
                                } else {
                                    subscriber.onNext(e.getMessage());

                                }
                            }
                        });
                    }
                }).compose(RxUtil.<String>rxSchedulerHelper())
                        .subscribe(new Action1<String>() {
                            @Override
                            public void call(String s) {
                                mView.sendContentToNetSuc(s);
                            }
                        }, new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                mView.sendContentToNetError(throwable.getMessage());
                            }
                        });
                addSubscrebe(subscription);
            }
        }
    }

    @Override
    public void addContentToLocal(LocalRecord localRecord) {
        Date time = localRecord.getTimeDate();
        String strContent = localRecord.getContent();
        String groupId = localRecord.getId() + "";
        String avUserId = localRecord.getBelongId();
        if (!StringUtils.isEmpty(time.toString())
                && !StringUtils.isEmpty(strContent)
                && !StringUtils.isEmpty(groupId)
                && !StringUtils.isEmpty(avUserId)) {
            if (localRecordDao.insert(localRecord) != 0) {
                mView.addContentToLocalSuc("记录成功");
                //如果插入成功，同步数据到服务器
                sendContentToNet(localRecord);
            } else {
                mView.addContentToLocalError("记录数据失败");
            }
        } else {
            if (StringUtils.isEmpty(strContent)) {
                mView.addContentToLocalError("内容不能为空");
            } else if (StringUtils.isEmpty(avUserId)) {
                mView.addContentToLocalError("未登陆，请登陆");
            } else if (StringUtils.isEmpty(groupId)) {
                mView.addContentToLocalError("请选择，标签分组");
            }
        }
    }

    @Override
    public void addNewLocalGroup(final LocalGroup localGroup) {
        Subscription subscription = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                localGroupDao.insert(localGroup);
                subscriber.onNext("创建成功");
            }
        }).compose(RxUtil.<String>rxSchedulerHelper())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        addNewLocalGroupToNet(localGroup);
                        mView.addNewLocalGroupSuc(s);
                    }
                });
        addSubscrebe(subscription);
    }

    @Override
    public void addNewLocalGroupToNet(final LocalGroup localGroup) {
        if (NetUtil.isNetworkAvailable()) {
            Subscription subscription = Observable.create(
                    new Observable.OnSubscribe<String>() {
                        @Override
                        public void call(final Subscriber<? super String> subscriber) {
                            LLocalGroup lLocalGroup = new LLocalGroup();
                            lLocalGroup.setContent(localGroup.content);
                            lLocalGroup.setGroupId(localGroup.id);
                            lLocalGroup.setGroupTitle(localGroup.title);
                            lLocalGroup.setBelongId(localGroup.belongId);
                            lLocalGroup.setAttentionNum(0);
                            lLocalGroup.setUserd(false);
                            lLocalGroup.saveInBackground(new SaveCallback() {
                                @Override
                                public void done(AVException e) {
                                    if (e == null) {
                                        subscriber.onNext("上传服务器成功");
                                    } else {
                                        subscriber.onNext("上传失败：" + e.getMessage());
                                    }
                                }
                            });
                        }
                    }).compose(RxUtil.<String>rxSchedulerHelper())
                    .subscribe(new Action1<String>() {
                        @Override
                        public void call(String s) {
                            mView.sendNewLocalGroupToNetReturn(s);
                        }
                    });
            addSubscrebe(subscription);
        }
    }

    @Override
    public void initLocalGroup() {
        Subscription subscription = Observable.create(new Observable.OnSubscribe<List<LocalGroup>>() {
            @Override
            public void call(Subscriber<? super List<LocalGroup>> subscriber) {
                List<LocalGroup> groups = localGroupDao.loadAll();
                if (groups != null && !groups.isEmpty()) {
                    List<LocalGroup> data = new ArrayList<>();
                    //选择所有常用文集
                    for (LocalGroup group : groups) {
                        if (group.isUserd) {
                            data.add(group);
                        }
                    }
                    subscriber.onNext(data);
                } else {
                    mView.showError("本地数据加载失败");
                }
            }
        }).compose(RxUtil.<List<LocalGroup>>rxSchedulerHelper())
                .subscribe(new Action1<List<LocalGroup>>() {
                    @Override
                    public void call(List<LocalGroup> groups) {
                        mView.initLocalGroup(groups);
                    }
                });
        addSubscrebe(subscription);
    }
}
