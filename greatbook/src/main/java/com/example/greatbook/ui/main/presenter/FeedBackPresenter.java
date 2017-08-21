package com.example.greatbook.ui.main.presenter;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.SaveCallback;
import com.example.greatbook.base.RxPresenter;
import com.example.greatbook.ui.main.presenter.contract.FeedBackContract;
import com.example.greatbook.ui.model.LFeedBackBean;
import com.example.greatbook.utils.LogUtils;
import com.example.greatbook.utils.RxUtil;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by quanzizhangben on 2017/8/10.
 */

public class FeedBackPresenter extends RxPresenter<FeedBackContract.View> implements FeedBackContract.Presenter {
    private int pageNum = 5;
    private final String TAG="FeedBackPresenter";

    public FeedBackPresenter(FeedBackContract.View view){
        mView=view;
    }
    @Override
    public void getFeedBackContent() {
        mView.showLoading();
        Subscription subscription = Observable.create(new Observable.OnSubscribe<List<LFeedBackBean>>() {
            @Override
            public void call(final Subscriber<? super List<LFeedBackBean>> subscriber) {
                AVQuery < LFeedBackBean > query = AVQuery.getQuery(LFeedBackBean.class);
                query.limit(pageNum);
                query.addDescendingOrder("createdAt");
                query.findInBackground(new FindCallback<LFeedBackBean>() {
                    @Override
                    public void done(List<LFeedBackBean> list, AVException e) {
                        if (e == null) {
                            if (!list.isEmpty()) {
                                subscriber.onNext(list);
                            }else{
                                subscriber.onNext(new ArrayList<LFeedBackBean>());
                                mView.showLoaded();
                                mView.showError("暂无用户反馈");
                            }
                        } else {
                            subscriber.onError(e);
                        }

                    }
                });
            }
        }).compose(RxUtil.<List<LFeedBackBean>>rxSchedulerHelper())
                .subscribe(
                        new Action1<List<LFeedBackBean>>() {
                            @Override
                            public void call(List<LFeedBackBean> list) {
                                mView.showLoaded();
                                mView.intiFeeBackContent(list);
                            }
                        }, new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                mView.showLoaded();
                                mView.showError("加载失败，请注意网络状况");
                            }
                        });
        addSubscrebe(subscription);
    }

    @Override
    public void getMoreFeedBackContent(final int currentNum) {
        Subscription subscription = Observable.create(new Observable.OnSubscribe<List<LFeedBackBean>>() {
            @Override
            public void call(final Subscriber<? super List<LFeedBackBean>> subscriber) {
                AVQuery<LFeedBackBean> query = AVQuery.getQuery(LFeedBackBean.class);
                query.limit(pageNum);
                query.skip(currentNum);
                query.findInBackground(new FindCallback<LFeedBackBean>() {
                    @Override
                    public void done(List<LFeedBackBean> list, AVException e) {
                        if (e == null) {
                            if (!list.isEmpty()) {
                                subscriber.onNext(list);
                            } else {
                                mView.showLoaded();
                                mView.showError("暂无用户反馈");
                                subscriber.onNext(new ArrayList<LFeedBackBean>());
                            }
                        } else {
                            subscriber.onError(e);
                        }
                    }
                });
            }
        }).compose(RxUtil.<List<LFeedBackBean>>rxSchedulerHelper())
                .subscribe(new Action1<List<LFeedBackBean>>() {
                    @Override
                    public void call(List<LFeedBackBean> talkAboutBeen) {
                        mView.getMoreFeedBackContent(talkAboutBeen);
                        mView.showLoaded();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mView.showError("有的人活着，但它已经死了。我觉得我还能救一下。(数据获取失败)");
                        mView.showLoaded();
                    }
                });
        addSubscrebe(subscription);
    }

    @Override
    public void sendContent(String belongId, String content) {
        final LFeedBackBean lFeedBackBean=new LFeedBackBean();
        lFeedBackBean.setBelongId(belongId);
        lFeedBackBean.setLike(0);
        lFeedBackBean.setContent(content);
        Subscription subscription=Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(final Subscriber<? super String> subscriber) {
                lFeedBackBean.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(AVException e) {
                        if (e==null){
                            subscriber.onNext("发送成功");
                        }
                    }
                });
            }
        }).compose(RxUtil.<String>rxSchedulerHelper())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        mView.sendContentSuc(s);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mView.sendContentError("发送失败，请注意网络问题");
                        LogUtils.e(TAG,throwable.getMessage());
                    }
                });
        addSubscrebe(subscription);

    }
}
