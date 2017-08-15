package com.example.greatbook.middle.presenter;

import com.example.greatbook.App;
import com.example.greatbook.base.RxPresenter;
import com.example.greatbook.greendao.LocalRecordDao;
import com.example.greatbook.greendao.entity.LocalRecord;
import com.example.greatbook.middle.presenter.contract.MiddleLocalAddContract;
import com.example.greatbook.middle.model.LocalRecordRLV;
import com.example.greatbook.utils.RxUtil;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by MDove on 2017/8/12.
 */

public class MiddleLocalAddPresenter extends RxPresenter<MiddleLocalAddContract.View> implements MiddleLocalAddContract.Presenter {
    private LocalRecordDao localRecordDao;
    public MiddleLocalAddPresenter(MiddleLocalAddContract.View view){
        mView=view;
        localRecordDao= App.getDaoSession().getLocalRecordDao();
    }
    @Override
    public void initLocalRecord() {
        Subscription subscription= Observable.create(new Observable.OnSubscribe<List<LocalRecord>>() {
            @Override
            public void call(Subscriber<? super List<LocalRecord>> subscriber) {
                List<LocalRecord> data=localRecordDao.loadAll();
                subscriber.onNext(data);
            }
        }).compose(RxUtil.<List<LocalRecord>>rxSchedulerHelper())
                .subscribe(new Action1<List<LocalRecord>>() {
                    @Override
                    public void call(List<LocalRecord> records) {
                        if (!records.isEmpty()){
                            List<LocalRecordRLV> data=new ArrayList<>();
                            for (LocalRecord local: records) {
                                LocalRecordRLV localRLV=new LocalRecordRLV();
                                localRLV.belongId=local.getBelongId();
                                localRLV.content=local.getContent();
                                localRLV.groupId =Long.valueOf(local.getGroupId());
                                localRLV.groupTitle=local.getGroupTitle();
                                localRLV.id=local.getId();
                                localRLV.title=local.getTitle();
                                localRLV.time= local.getTimeDate();
                                localRLV.type=local.getType();
                                data.add(localRLV);
                            }
                            mView.initLocalRecordSuc(data);
                        }else{
                            mView.initLocalRecordError("本地数据为空");
                        }
                    }
                });

        addSubscrebe(subscription);
    }
}
