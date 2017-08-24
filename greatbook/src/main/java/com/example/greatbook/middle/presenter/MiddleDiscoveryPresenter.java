package com.example.greatbook.middle.presenter;

import android.util.Log;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.example.greatbook.base.RxPresenter;
import com.example.greatbook.middle.model.DiscoveryRecord;
import com.example.greatbook.middle.presenter.contract.MiddleDiscoveryContract;
import com.example.greatbook.middle.model.DiscoveryTopGroup;
import com.example.greatbook.model.leancloud.LLocalGroup;
import com.example.greatbook.model.leancloud.LLocalRecord;
import com.example.greatbook.model.leancloud.User;
import com.example.greatbook.utils.LogUtils;
import com.example.greatbook.utils.NetUtil;
import com.example.greatbook.utils.RxUtil;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by MDove on 2017/8/13.
 */

public class MiddleDiscoveryPresenter extends RxPresenter<MiddleDiscoveryContract.View> implements MiddleDiscoveryContract.Presenter {
    private DiscoveryRecordReturn recordReturn;
    private DiscoveryGroupReturn groupReturn;
    public MiddleDiscoveryPresenter(MiddleDiscoveryContract.View view){
        mView=view;
    }
    @Override
    public void initDiscoveryTop() {
        if (NetUtil.isNetworkAvailable()) {
            Subscription subscription = Observable.create(new Observable.OnSubscribe<DiscoveryGroupReturn>() {
                @Override
                public void call(final Subscriber<? super DiscoveryGroupReturn> subscriber) {
                    LogUtils.d(Thread.currentThread()+"!!!");
                    AVQuery<LLocalGroup> query = AVQuery.getQuery(LLocalGroup.class);
                    query.limit(10);
                    query.addDescendingOrder("attentionNum");
                    query.findInBackground(new FindCallback<LLocalGroup>() {
                        @Override
                        public void done(List<LLocalGroup> list, AVException e) {
                            LogUtils.d(Thread.currentThread()+"!@!@!");

                            groupReturn = new DiscoveryGroupReturn();

                            if (e == null && !list.isEmpty()) {
                                List<DiscoveryTopGroup> data = new ArrayList<>();
                                for (LLocalGroup lLocalGroup : list) {
                                    DiscoveryTopGroup topRecord = new DiscoveryTopGroup();
                                    topRecord.attentionNum = lLocalGroup.getAttentionNum();
                                    topRecord.belongId = lLocalGroup.getBelongId();
                                    topRecord.content = lLocalGroup.getContent();
                                    topRecord.groupId = lLocalGroup.getGroupId();
                                    topRecord.groupTitle = lLocalGroup.getGroupTitle();
                                    topRecord.time = lLocalGroup.getCreatedAt();
                                    topRecord.groupPhotoPath =lLocalGroup.getGroupPhoto().getUrl();
                                    data.add(topRecord);
                                }
                                groupReturn.data = data;
                                subscriber.onNext(groupReturn);
                            } else {
                                groupReturn.strReturn = "加载数据失败";
                                subscriber.onNext(groupReturn);
                            }
                        }
                    });
                }
            }).compose(RxUtil.<DiscoveryGroupReturn>rxSchedulerHelper())
                    .subscribe(new Action1<DiscoveryGroupReturn>() {
                        @Override
                        public void call(DiscoveryGroupReturn discoveryGroupReturn) {
                            if (discoveryGroupReturn.data == null) {
                                mView.initDiscoveryGroupError(discoveryGroupReturn.strReturn);
                            } else {
                                mView.initDiscoveryGroupSuc(discoveryGroupReturn.data);
                            }
                        }
                    });

            addSubscrebe(subscription);
        }else{
            mView.showError("请确保网络连接");
        }
    }

    @Override
    public void initDiscoveryRecord() {
        if (NetUtil.isNetworkAvailable()){
            Subscription subscription=Observable.create(new Observable.OnSubscribe<DiscoveryRecordReturn>() {
                @Override
                public void call(final Subscriber<? super DiscoveryRecordReturn> subscriber) {
                    AVQuery<LLocalRecord> query = AVQuery.getQuery(LLocalRecord.class);
                    query.limit(10);
                    query.addDescendingOrder("likeNum");
                    query.findInBackground(new FindCallback<LLocalRecord>() {
                        @Override
                        public void done(List<LLocalRecord> list, AVException e) {
                            if (e == null && !list.isEmpty()) {
                                List<DiscoveryRecord> data = new ArrayList<>();
                                recordReturn = new DiscoveryRecordReturn();
                                for (LLocalRecord lLocalRecord : list) {
                                    DiscoveryRecord record = new DiscoveryRecord();
                                    record.likeNum = lLocalRecord.getLikeNum();
                                    record.belongId = lLocalRecord.getBelongId();
                                    record.content = lLocalRecord.getContent();
                                    record.groupId = lLocalRecord.getGroupId();
                                    record.time = lLocalRecord.getCreatedAt();
                                    record.title=lLocalRecord.getTitle();
                                    record.objectId=lLocalRecord.getObjectId();
                                    data.add(record);
                                }
                                recordReturn.data = data;
                                subscriber.onNext(recordReturn);
                            }
                        }
                    });
                }
            }).compose(RxUtil.<DiscoveryRecordReturn>rxSchedulerHelper())
                    .subscribe(new Action1<DiscoveryRecordReturn>() {
                        @Override
                        public void call(DiscoveryRecordReturn discoveryRecordReturn) {
                            if (discoveryRecordReturn.data == null) {
                                mView.initDiscoveryRecordError(discoveryRecordReturn.strReturn);
                            } else {
                                mView.initDiscoveryRecordSuc(discoveryRecordReturn.data);
                            }
                        }
                    });
            addSubscrebe(subscription);

        }
    }
    public class DiscoveryRecordReturn{
        public String strReturn;
        public List<DiscoveryRecord> data;
    }
    public class DiscoveryGroupReturn{
        public String strReturn;
        public List<DiscoveryTopGroup> data;
    }
}
