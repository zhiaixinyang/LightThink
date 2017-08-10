package com.example.greatbook.presenter.book;

import android.util.Log;

import com.example.greatbook.apis.RetrofitHelper;
import com.example.greatbook.base.RxPresenter;
import com.example.greatbook.model.BookSectionContentBean;
import com.example.greatbook.presenter.book.contract.BookSectionContentContract;
import com.example.greatbook.utils.LogUtils;
import com.example.greatbook.utils.RxUtil;

import java.util.List;

import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by quanzizhangben on 2017/8/10.
 */

public class BookSectionContentPresenter extends RxPresenter<BookSectionContentContract.View> implements BookSectionContentContract.Presenter {
    private final String TAG="BookSectionContentPresenter";
    private RetrofitHelper retrofitHelper;
    public BookSectionContentPresenter(BookSectionContentContract.View view) {
        mView=view;
        retrofitHelper=new RetrofitHelper();
    }

    @Override
    public void querySectionContent(String url) {
        mView.showLoading();
        Subscription subscription=retrofitHelper.queryBookContentByHref(url)
                .compose(RxUtil.<BookSectionContentBean>rxSchedulerHelper())
                .subscribe(new Action1<BookSectionContentBean>() {
                    @Override
                    public void call(BookSectionContentBean bookSectionContentBeen) {
                        mView.showLoaded();
                        mView.initBookSectionContent(bookSectionContentBeen);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mView.showLoaded();
                        mView.showError("数据加载失败");
                        LogUtils.d(TAG,throwable.getMessage());
                    }
                });
        addSubscrebe(subscription);
    }
}
