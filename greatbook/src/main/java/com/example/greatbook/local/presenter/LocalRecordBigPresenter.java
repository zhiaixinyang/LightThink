package com.example.greatbook.local.presenter;

import com.example.greatbook.App;
import com.example.greatbook.base.RxPresenter;
import com.example.greatbook.greendao.LocalGroupDao;
import com.example.greatbook.greendao.LocalRecordDao;
import com.example.greatbook.greendao.entity.LocalGroup;
import com.example.greatbook.greendao.entity.LocalRecord;
import com.example.greatbook.local.model.LocalRecordRLV;
import com.example.greatbook.local.presenter.contract.LocalRecordBigContract;
import com.example.greatbook.local.presenter.contract.MiddleLocalAddContract;
import com.example.greatbook.utils.LogUtils;
import com.example.greatbook.utils.RxUtil;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by MDove on 2017/10/2.
 */

public class LocalRecordBigPresenter extends RxPresenter<LocalRecordBigContract.View> implements LocalRecordBigContract.Presenter {
    private LocalRecordDao mLocalRecordDao;
    private LocalGroupDao mLocalGroupDao;

    public LocalRecordBigPresenter() {
        mLocalRecordDao = App.getDaoSession().getLocalRecordDao();
        mLocalGroupDao = App.getDaoSession().getLocalGroupDao();
    }

    @Override
    public void initLocalRecord() {
        Subscription subscription = Observable.create(new Observable.OnSubscribe<List<LocalRecord>>() {
            @Override
            public void call(Subscriber<? super List<LocalRecord>> subscriber) {
                List<LocalRecord> data = mLocalRecordDao
                        .queryBuilder()
                        .orderDesc(LocalRecordDao.Properties.TimeDate)
                        .list();
                subscriber.onNext(data);
            }
        }).flatMap(new Func1<List<LocalRecord>, Observable<List<LocalRecordRLV>>>() {
            @Override
            public Observable<List<LocalRecordRLV>> call(List<LocalRecord> localRecords) {
                List<LocalRecordRLV> data = new ArrayList<>();
                if (localRecords != null) {
                    for (LocalRecord local : localRecords) {
                        if (local.getGroupId()!=null) {
                            List<LocalGroup> list = mLocalGroupDao.queryBuilder()
                                    .where(LocalGroupDao.Properties.Id.eq(local.getGroupId()))
                                    .list();
                            if (list != null && !list.isEmpty()) {
                                LocalGroup localGroup = list.get(0);

                                LocalRecordRLV localRLV = new LocalRecordRLV();
                                localRLV.belongId = local.getBelongId();
                                localRLV.content = local.getContent();
                                localRLV.groupId = local.getGroupId();
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
                        }
                    }
                }
                return Observable.just(data);
            }
        }).compose(RxUtil.<List<LocalRecordRLV>>rxSchedulerHelper())
                .subscribe(new Subscriber<List<LocalRecordRLV>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showError("加载失败");
                    }

                    @Override
                    public void onNext(List<LocalRecordRLV> localRecordRLVs) {
                        if (!localRecordRLVs.isEmpty()) {
                            mView.initLocalRecordSuc(localRecordRLVs);
                        } else {
                            mView.localRecordEmpty();
                        }
                    }
                });

        addSubscrebe(subscription);
    }
}
