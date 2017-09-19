package com.example.greatbook.local.presenter;

import com.example.greatbook.App;
import com.example.greatbook.greendao.ContentCommitDao;
import com.example.greatbook.greendao.EssayDao;
import com.example.greatbook.greendao.entity.ContentCommit;
import com.example.greatbook.greendao.entity.Essay;
import com.example.greatbook.local.model.EssayListItem;
import com.example.greatbook.local.presenter.contract.EssayListContract;
import com.example.greatbook.model.leancloud.User;
import com.example.greatbook.utils.LogUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by MDove on 17/9/18.
 */

public class EssayListPresenter implements EssayListContract.Presenter {
    private EssayListContract.View mView;
    private EssayDao essayDao;
    private ContentCommitDao mContentCommitDao;
    private List<Essay> mData;

    public EssayListPresenter() {
        essayDao = App.getDaoSession().getEssayDao();
        mContentCommitDao = App.getDaoSession().getContentCommitDao();
        mData = new ArrayList<>();
    }

    @Override
    public void attachView(EssayListContract.View view) {
        this.mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
        mData = null;
    }

    @Override
    public void initEssayList() {

        Observable.create(new Observable.OnSubscribe<Map<Essay, List<ContentCommit>>>() {
            @Override
            public void call(Subscriber<? super Map<Essay, List<ContentCommit>>> subscriber) {
                Map<Essay, List<ContentCommit>> map = new HashMap<>();
                List<Essay> data = essayDao.loadAll();
                LogUtils.d("a:"+data.size());
                if (data != null && !data.isEmpty()) {
                    for (Essay essay : data) {
                        List<ContentCommit> commits = mContentCommitDao.queryBuilder()
                                .where(ContentCommitDao.Properties.EssayId.eq(essay.id))
                                .list();
                        if (commits != null && !commits.isEmpty()) {
                            map.put(essay, commits);
                        } else {
                            map.put(essay, new ArrayList<ContentCommit>());
                        }
                    }
                    subscriber.onNext(map);
                }else {
                    subscriber.onNext(map);
                }
            }
        }).map(new Func1<Map<Essay, List<ContentCommit>>, List<EssayListItem>>() {
            @Override
            public List<EssayListItem> call(Map<Essay, List<ContentCommit>> essayListMap) {
                List<EssayListItem> data = new ArrayList<>();
                if (essayListMap.isEmpty()) {
                    return data;
                } else {
                    for (Map.Entry<Essay, List<ContentCommit>> essaySet : essayListMap.entrySet()) {
                        EssayListItem item = new EssayListItem();
                        item.mEssay = essaySet.getKey();
                        item.mLastContentCommit = essaySet
                                .getValue()
                                .get(essaySet.getValue().size() - 1);
                        data.add(item);
                    }
                    return data;
                }
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<EssayListItem>>() {
                    @Override
                    public void call(List<EssayListItem> essayListMap) {
                        if (essayListMap == null && !essayListMap.isEmpty()) {
                            mView.initEssayListSuc(essayListMap);
                        } else {
                            mView.initEssayListEmpty();
                        }
                    }
                });

    }

    @Override
    public void addEssay(User user) {
        Essay essay = new Essay();
        essay.belongId = user.getObjectId();
        essay.belongUserAccount = user.getUsername();
        essay.content = "";
        essay.time = new Date();

        long insertId = essayDao.insert(essay);
        LogUtils.d(insertId);
        if (insertId > 0) {
            mView.addEssaySuc(insertId);
        }
    }
}
