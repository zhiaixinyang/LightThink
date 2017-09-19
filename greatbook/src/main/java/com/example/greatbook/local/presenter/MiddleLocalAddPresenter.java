package com.example.greatbook.local.presenter;

import android.content.Context;
import android.support.v4.content.ContextCompat;

import com.example.greatbook.App;
import com.example.greatbook.R;
import com.example.greatbook.base.RxPresenter;
import com.example.greatbook.greendao.LocalGroupDao;
import com.example.greatbook.greendao.LocalRecordDao;
import com.example.greatbook.greendao.entity.LocalGroup;
import com.example.greatbook.greendao.entity.LocalRecord;
import com.example.greatbook.local.fragment.MiddleLocalAddFragment;
import com.example.greatbook.local.model.LocalRecordRLV;
import com.example.greatbook.local.model.MainMenuItemBean;
import com.example.greatbook.local.presenter.contract.MiddleLocalAddContract;
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

    @Override
    public void initMenu(Context context) {
        List<MainMenuItemBean> menuData = new ArrayList<>();

        MainMenuItemBean myAll = new MainMenuItemBean();
        myAll.bgColor = ContextCompat.getColor(context, R.color.blue);
        myAll.inColor = ContextCompat.getColor(context, R.color.white);
        myAll.outColor = ContextCompat.getColor(context, R.color.black);
        myAll.inText = "段";
        myAll.outText = "我的段子库";
        myAll.menuType = MiddleLocalAddFragment.MY_ALL_CONTENT;
        menuData.add(myAll);

        MainMenuItemBean myGroup = new MainMenuItemBean();
        myGroup.bgColor = ContextCompat.getColor(context, R.color.red);
        myGroup.inColor = ContextCompat.getColor(context, R.color.white);
        myGroup.outColor = ContextCompat.getColor(context, R.color.black);
        myGroup.inText = "集";
        myGroup.outText = "我的文集";
        myGroup.menuType = MiddleLocalAddFragment.MY_ALL_GROUP;
        menuData.add(myGroup);


        MainMenuItemBean myCooperateTopic = new MainMenuItemBean();
        myCooperateTopic.bgColor = ContextCompat.getColor(context, R.color.orange);
        myCooperateTopic.inColor = ContextCompat.getColor(context, R.color.white);
        myCooperateTopic.outColor = ContextCompat.getColor(context, R.color.black);
        myCooperateTopic.inText = "协";
        myCooperateTopic.outText = "协同主题";
        myCooperateTopic.menuType = MiddleLocalAddFragment.MY_COOPATER_TOPIC;
        menuData.add(myCooperateTopic);

        MainMenuItemBean myPlan = new MainMenuItemBean();
        myPlan.bgColor = ContextCompat.getColor(context, R.color.pink);
        myPlan.inColor = ContextCompat.getColor(context, R.color.white);
        myPlan.outColor = ContextCompat.getColor(context, R.color.black);
        myPlan.inText = "标";
        myPlan.outText = "我的目标";
        myPlan.menuType = MiddleLocalAddFragment.MY_PLAN;
        menuData.add(myPlan);

        mView.showMenu(menuData);
    }
}
