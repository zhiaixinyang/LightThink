package com.example.greatbook.middle.presenter;

import com.example.greatbook.App;
import com.example.greatbook.base.RxPresenter;
import com.example.greatbook.greendao.LocalGroupDao;
import com.example.greatbook.greendao.LocalRecordDao;
import com.example.greatbook.greendao.entity.LocalGroup;
import com.example.greatbook.greendao.entity.LocalRecord;
import com.example.greatbook.middle.model.LocalRecordRLV;
import com.example.greatbook.middle.presenter.contract.MiddleLocalAddContract;
import com.example.greatbook.utils.RxUtil;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by MDove on 2017/8/12.
 */

public class MiddleLocalAddPresenter extends RxPresenter<MiddleLocalAddContract.View> implements MiddleLocalAddContract.Presenter {
    private LocalRecordDao localRecordDao;
    private LocalGroupDao localGroupDao;

    public MiddleLocalAddPresenter(MiddleLocalAddContract.View view) {
        mView = view;
        localRecordDao = App.getDaoSession().getLocalRecordDao();
        localGroupDao = App.getDaoSession().getLocalGroupDao();
    }

    @Override
    public void initLocalRecord() {
        Subscription subscription = Observable.create(new Observable.OnSubscribe<List<LocalRecord>>() {
            @Override
            public void call(Subscriber<? super List<LocalRecord>> subscriber) {
                List<LocalRecord> data = localRecordDao.loadAll();
                subscriber.onNext(data);
            }
        }).flatMap(new Func1<List<LocalRecord>, Observable<List<LocalRecordRLV>>>() {
            @Override
            public Observable<List<LocalRecordRLV>> call(List<LocalRecord> localRecords) {
                List<LocalRecordRLV> data = new ArrayList<>();
                for (LocalRecord local : localRecords) {
                    LocalGroup localGroup = localGroupDao.queryBuilder().where(LocalGroupDao.Properties.Id.eq(local.getGroupId())).list().get(0);
                    LocalRecordRLV localRLV = new LocalRecordRLV();
                    localRLV.belongId = local.getBelongId();
                    localRLV.content = local.getContent();
                    localRLV.groupId = Long.valueOf(local.getGroupId());
                    localRLV.groupTitle = local.getGroupTitle();
                    localRLV.id = local.getId();
                    localRLV.title = local.getTitle();
                    localRLV.time = local.getTimeDate();
                    localRLV.type = local.getType();
                    localRLV.bgColor = localGroup.getBgColor();
                    localRLV.groupLocalPhotoPath = localGroup.getGroupLocalPhotoPath();
                    localRLV.groupPhotoPath = localGroup.getGroupPhotoPath();
                    data.add(localRLV);
                }
                return Observable.just(data);
            }
        }).compose(RxUtil.<List<LocalRecordRLV>>rxSchedulerHelper())
                .subscribe(new Action1<List<LocalRecordRLV>>() {
                    @Override
                    public void call(List<LocalRecordRLV> records) {
                        if (!records.isEmpty()) {
                            mView.initLocalRecordSuc(records);
                        } else {
                            mView.initLocalRecordError("本地数据为空");
                        }
                    }
                });

        addSubscrebe(subscription);
    }
}
