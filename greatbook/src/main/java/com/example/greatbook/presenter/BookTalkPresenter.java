package com.example.greatbook.presenter;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.example.greatbook.base.RxPresenter;
import com.example.greatbook.model.leancloud.BookTalkBean;
import com.example.greatbook.presenter.contract.BookTalkContract;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by MBENBEN on 2016/11/25.
 */

public class BookTalkPresenter extends RxPresenter<BookTalkContract.View> implements BookTalkContract.Presenter {

    public BookTalkPresenter(BookTalkContract.View bookTalkView) {
        mView = bookTalkView;
    }

    @Override
    public void getBookTalkDatas(final String bookUrl) {
        mView.showLoading();
        Observable.create(new Observable.OnSubscribe<List<BookTalkBean>>() {
            @Override
            public void call(final Subscriber<? super List<BookTalkBean>> subscriber) {
                AVQuery<BookTalkBean> query=AVQuery.getQuery(BookTalkBean.class);
                query.whereEqualTo("belongBookId",bookUrl);
                query.findInBackground(new FindCallback<BookTalkBean>() {
                    @Override
                    public void done(List<BookTalkBean> list, AVException e) {
                        if (e==null){
                            if (!list.isEmpty()){
                                subscriber.onNext(list);
                            }else{
                                mView.showLoaded();
                                mView.showError("暂无评论信息。");
                            }
                        }else{
                            mView.showLoaded();
                            subscriber.onError(e);
                        }
                    }
                });
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<BookTalkBean>>() {
                    @Override
                    public void call(List<BookTalkBean> bookTalkBeen) {
                        mView.initDatas(bookTalkBeen);
                        mView.showLoaded();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mView.showLoaded();
                        mView.showError("程序员正在码不停蹄地制造新的BUG（数据获取失败）");
                    }
                });

    }
}
