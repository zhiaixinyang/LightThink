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
        Observable.create(new Observable.OnSubscribe<List<Essay>>() {
            @Override
            public void call(Subscriber<? super List<Essay>> subscriber) {
                List<Essay> data = essayDao.queryBuilder().build().list();
                if (data != null && !data.isEmpty()) {
                    subscriber.onNext(data);
                } else {
                    subscriber.onNext(new ArrayList<Essay>());
                }
            }
        }).map(new Func1<List<Essay>, List<EssayListItem>>() {

            @Override
            public List<EssayListItem> call(List<Essay> essays) {
                List<EssayListItem> data = new ArrayList<>();
                if (essays != null && !essays.isEmpty()) {
                    for (Essay essay : essays) {
                        EssayListItem item = new EssayListItem();
                        item.mEssay = essay;
                        //此处不能直接使用List的public变量
                        if (essay.getContentCommits() != null && !essay.getContentCommits().isEmpty()) {
                            item.mLastContentCommit = essay.contentCommits
                                    .get(essay.contentCommits.size() - 1);
                        } else {
                            item.mLastContentCommit = null;
                        }
                        data.add(item);
                    }
                    return data;
                } else {
                    return data;
                }
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<EssayListItem>>() {
                    @Override
                    public void call(List<EssayListItem> essayList) {
                        if (essayList != null && !essayList.isEmpty()) {
                            mView.initEssayListSuc(essayList);
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
