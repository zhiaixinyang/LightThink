package com.example.greatbook.presenter.book;

import android.util.Log;

import com.example.greatbook.apis.RetrofitHelper;
import com.example.greatbook.base.RxPresenter;
import com.example.greatbook.model.BookSectionBean;
import com.example.greatbook.presenter.book.contract.BookDesContract;
import com.example.greatbook.utils.RxUtil;

import java.util.List;

import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by quanzizhangben on 2017/8/10.
 */

public class BookDesPresenter extends RxPresenter<BookDesContract.View> implements BookDesContract.Presenter {
    private RetrofitHelper retrofitHelper;
    public BookDesPresenter(BookDesContract.View view) {
        mView=view;
        retrofitHelper=new RetrofitHelper();
    }

    @Override
    public void queryBookDes(String url) {
        Log.d("aaa",url);
        Subscription subscription=retrofitHelper.querySectionNameByBookName(url)
                .compose(RxUtil.<List<BookSectionBean>>rxSchedulerHelper())
                .subscribe(new Action1<List<BookSectionBean>>() {
                    @Override
                    public void call(List<BookSectionBean> bookSectionBeen) {
                        mView.showLoaded();
                        mView.initBookDesList(bookSectionBeen);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mView.showLoaded();
                        mView.showError("数据加载失败，请重试"+throwable.getMessage());
                        Log.d("aaaa",throwable.getMessage());
                    }
                });
        addSubscrebe(subscription);
    }
}
