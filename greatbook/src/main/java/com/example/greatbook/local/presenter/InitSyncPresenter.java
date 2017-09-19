package com.example.greatbook.local.presenter;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.example.greatbook.App;
import com.example.greatbook.base.RxPresenter;
import com.example.greatbook.greendao.LocalGroupDao;
import com.example.greatbook.greendao.LocalRecordDao;
import com.example.greatbook.greendao.entity.LocalGroup;
import com.example.greatbook.greendao.entity.LocalRecord;
import com.example.greatbook.local.presenter.contract.InitSyncContract;
import com.example.greatbook.model.leancloud.LLocalGroup;
import com.example.greatbook.model.leancloud.LLocalRecord;
import com.example.greatbook.utils.NetUtil;
import com.example.greatbook.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by MDove on 2017/8/18.
 */

public class InitSyncPresenter extends RxPresenter<InitSyncContract.View> implements InitSyncContract.Presenter {
    private LocalGroupDao localGroupDao;
    private LocalRecordDao localRecordDao;

    //同步时候：  00表示Group同步失败；01表示Group同步成功；02表示空号，无数据
    //          10表示Record失败；11表示Record成功；12表示空号，无数据
    //          20表示Group成功，Record失败；21表示Group失败，Record成功；22表示都失败；23表示都成功；24Group空；25都空
    public InitSyncPresenter(InitSyncContract.View view) {
        mView = view;
        localGroupDao = App.getDaoSession().getLocalGroupDao();
        localRecordDao = App.getDaoSession().getLocalRecordDao();
    }

    @Override
    public void syncData(final String belongId) {
        if (NetUtil.isNetworkAvailable()) {
            Observable.zip(syncGroups(belongId), syncRecords(belongId), new BiFunction<String, String, String>() {
                @Override
                public String apply(@NonNull String o, @NonNull String o2) throws Exception {
                    if (StringUtils.isEquals(o, "01") && StringUtils.isEquals(o2, "11")) {
                        //此时表示俩者都同步成功
                        return "23";
                    } else if (StringUtils.isEquals(o, "01") && !StringUtils.isEquals(o2, "11")) {
                        //表示Group成功，Record失败
                        return "20";
                    } else if (!StringUtils.isEquals(o, "01") && StringUtils.isEquals(o2, "11")) {
                        //表示Group失败，Record成功
                        return "21";
                    } else if (StringUtils.isEquals(o, "02") && StringUtils.isEquals(o2, "12")) {
                        //空号表示成功
                        return "25";
                    } else {
                        //俩者都失败
                        return "22";
                    }
                }
            }).observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new DisposableObserver<String>() {
                        @Override
                        public void onNext(@NonNull String o) {
                            switch (o) {
                                case "20":
                                    //表示Group成功，Record失败
                                    mView.syncDataErr("同步失败20");
                                    break;
                                case "21":
                                    //表示Group失败，Record成功
                                    mView.syncDataErr("同步失败21");
                                    break;
                                case "22":
                                    //俩者都失败
                                    mView.syncDataErr("同步失败22");
                                    break;
                                case "23":
                                    //此时表示俩者都同步成功
                                    mView.syncDataSuc("同步成功");
                                    break;
                                case "25":
                                    //空号
                                    mView.syncDataSuc("开始您的趣记之旅吧");
                                    break;

                            }
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {

                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        }
    }

    private Observable syncGroups(final String belongId) {
        return Observable.create(new ObservableOnSubscribe<List<LLocalGroup>>() {
            @Override
            public void subscribe(@NonNull final ObservableEmitter<List<LLocalGroup>> observableEmitter) throws Exception {
                AVQuery<LLocalGroup> query = AVQuery.getQuery(LLocalGroup.class);
                query.whereEqualTo("belongId", belongId);
                query.findInBackground(new FindCallback<LLocalGroup>() {
                    @Override
                    public void done(List<LLocalGroup> list, AVException e) {
                        if (e == null) {
                            if (!list.isEmpty()) {
                                observableEmitter.onNext(list);
                            } else {
                                observableEmitter.onNext(new ArrayList<LLocalGroup>());
                            }
                        } else {
                            observableEmitter.onNext(null);
                        }
                    }
                });
            }
        }).subscribeOn(Schedulers.io()).observeOn(Schedulers.io())
                .map(new Function<List<LLocalGroup>, List<LocalGroup>>() {
                    @Override
                    public List<LocalGroup> apply(@NonNull List<LLocalGroup> lLocalGroups) throws Exception {
                        if (lLocalGroups != null) {
                            if (!lLocalGroups.isEmpty()) {
                                List<LocalGroup> data = new ArrayList<LocalGroup>();
                                for (LLocalGroup lLocalGroup : lLocalGroups) {
                                    LocalGroup localGroup = new LocalGroup();
                                    if (!StringUtils.isEmpty(lLocalGroup.getGroupPhoto().getUrl())) {
                                        localGroup.setGroupPhotoPath(lLocalGroup.getGroupPhoto().getUrl());
                                        localGroup.setBgColor("");
                                        localGroup.setGroupLocalPhotoPath(0);
                                    } else {
                                        localGroup.setGroupPhotoPath("");
                                    }
                                    localGroup.setTitle(lLocalGroup.getGroupTitle());
                                    localGroup.setTime(lLocalGroup.getTime());
                                    localGroup.setContent(lLocalGroup.getContent());
                                    localGroup.setBelongId(lLocalGroup.getBelongId());
                                    data.add(localGroup);
                                }

                                return data;
                            } else {
                                return new ArrayList<LocalGroup>();
                            }
                        } else {
                            return null;
                        }
                    }
                }).observeOn(Schedulers.io())
                .doOnNext(new Consumer<List<LocalGroup>>() {
                    @Override
                    public void accept(@NonNull List<LocalGroup> localGroups) throws Exception {
                        if (!localGroups.isEmpty() && localGroups != null) {
                            for (LocalGroup localGroup : localGroups) {
                                localGroupDao.insert(localGroup);
                            }
                        }
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Function<List<LocalGroup>, ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(@NonNull List<LocalGroup> groups) throws Exception {
                        if (groups!=null) {
                            if (!groups.isEmpty()) {
                                return Observable.just("01");
                            } else {
                                return Observable.just("02");
                            }
                        }else{
                            return Observable.just("00");
                        }
                    }
                });
    }

    private Observable syncRecords(final String belongId) {
        return Observable.create(new ObservableOnSubscribe<List<LLocalRecord>>() {
            @Override
            public void subscribe(@NonNull final ObservableEmitter<List<LLocalRecord>> emitter) throws Exception {
                AVQuery<LLocalRecord> query = AVQuery.getQuery(LLocalRecord.class);
                query.whereEqualTo("belongId", belongId);
                query.findInBackground(new FindCallback<LLocalRecord>() {
                    @Override
                    public void done(List<LLocalRecord> list, AVException e) {
                        if (e == null) {
                            if (!list.isEmpty()) {
                                emitter.onNext(list);
                            } else {
                                emitter.onNext(new ArrayList<LLocalRecord>());
                            }
                        } else {
                            emitter.onNext(null);
                        }
                    }
                });
            }
        }).subscribeOn(Schedulers.io())
                .map(new Function<List<LLocalRecord>, List<LocalRecord>>() {
                    @Override
                    public List<LocalRecord> apply(@NonNull List<LLocalRecord> lLocalRecords) throws Exception {
                    if (lLocalRecords!=null) {
                        if (!lLocalRecords.isEmpty()) {
                            List<LocalRecord> data = new ArrayList<LocalRecord>();
                            for (LLocalRecord lLocalRecord : lLocalRecords) {
                                LocalRecord localRecord = new LocalRecord();
                                localRecord.setTimeDate(lLocalRecord.getTime());
                                localRecord.setTitle(lLocalRecord.getTitle());
                                localRecord.setContent(lLocalRecord.getContent());
                                localRecord.setBelongId(lLocalRecord.getBelongId());
                                localRecord.setGroupId(lLocalRecord.getGroupId());
                                localRecord.setGroupTitle(lLocalRecord.getGroupTitle());
                                data.add(localRecord);
                            }
                            return data;
                        }
                        return new ArrayList<LocalRecord>();
                    }else{
                        return null;
                    }
                    }
                }).observeOn(Schedulers.io())
                .doOnNext(new Consumer<List<LocalRecord>>() {
                    @Override
                    public void accept(@NonNull List<LocalRecord> records) throws Exception {

                        if (!records.isEmpty()&&records!=null) {
                            for (LocalRecord localRecord : records) {
                                localRecordDao.insert(localRecord);
                            }
                        }
                    }
                }).flatMap(new Function<List<LocalRecord>, ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(@NonNull List<LocalRecord> records) throws Exception {
                    if (records!=null) {
                        if (records.isEmpty()) {
                            //10表示同步失败
                            return Observable.just("12");
                        } else {
                            return Observable.just("11");
                        }
                    }else{
                        return Observable.just("10");
                    }
                    }
                });
    }
}
