package com.example.greatbook.middle.presenter;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.example.greatbook.App;
import com.example.greatbook.base.RxPresenter;
import com.example.greatbook.greendao.LocalGroupDao;
import com.example.greatbook.greendao.LocalRecordDao;
import com.example.greatbook.greendao.entity.LocalGroup;
import com.example.greatbook.middle.presenter.contract.InitSyncContract;
import com.example.greatbook.model.leancloud.LLocalGroup;
import com.example.greatbook.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by quanzizhangben on 2017/8/18.
 */

public class InitSyncPresenter extends RxPresenter<InitSyncContract.View> implements InitSyncContract.Presenter {
    private LocalGroupDao localGroupDao;
    private LocalRecordDao localRecordDao;
    public InitSyncPresenter(InitSyncContract.View view){
        mView=view;
        localGroupDao= App.getDaoSession().getLocalGroupDao();
        localRecordDao=App.getDaoSession().getLocalRecordDao();
    }

    @Override
    public void syncData(final String belongId) {
        Observable.create(new ObservableOnSubscribe<List<LLocalGroup>>() {
            @Override
            public void subscribe(@NonNull final ObservableEmitter<List<LLocalGroup>> observableEmitter) throws Exception {
                AVQuery<LLocalGroup> query=AVQuery.getQuery(LLocalGroup.class);
                query.whereEqualTo("belongId",belongId);
                query.findInBackground(new FindCallback<LLocalGroup>() {
                    @Override
                    public void done(List<LLocalGroup> list, AVException e) {
                        if (e==null){
                            if (!list.isEmpty()){
                                observableEmitter.onNext(list);
                            }
                        }else{
                            observableEmitter.onNext(null);
                        }
                    }
                });
            }
        }).map(new Function<List<LLocalGroup>, List<LocalGroup>>() {
            @Override
            public List<LocalGroup> apply(@NonNull List<LLocalGroup> lLocalGroups) throws Exception {
                List<LocalGroup> data=new ArrayList<LocalGroup>();
                for (LLocalGroup lLocalGroup:lLocalGroups){
                    LocalGroup localGroup=new LocalGroup();
                    localGroup.setGroupPhotoPath(StringUtils.isEmpty(lLocalGroup.getGroupPhoto().getUrl())
                            ?"":lLocalGroup.getGroupPhoto().getUrl());
                    localGroup.setTitle(lLocalGroup.getGroupTitle());
                    localGroup.setTime(lLocalGroup.getTime());
                    localGroup.setContent(lLocalGroup.getContent());
                    localGroup.setBelongId(lLocalGroup.getBelongId());
                    data.add(localGroup);
                }
                return data;
            }
        }).observeOn(Schedulers.io())
                .doOnNext(new Consumer<List<LocalGroup>>() {
            @Override
            public void accept(@NonNull List<LocalGroup> localGroups) throws Exception {
                for (LocalGroup localGroup:localGroups) {
                    localGroupDao.insert(localGroup);
                }
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<List<LocalGroup>>() {
            @Override
            public void accept(@NonNull List<LocalGroup> localGroups) throws Exception {
                if (localGroups!=null){
                    mView.syncDataSuc("同步成功");
                }else{
                    mView.syncDataErr("同步失败");
                }
            }
        });
    }
}
