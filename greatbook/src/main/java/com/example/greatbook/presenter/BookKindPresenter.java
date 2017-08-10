package com.example.greatbook.presenter;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.example.greatbook.apis.OkHttpHelper;
import com.example.greatbook.apis.RetrofitHelper;
import com.example.greatbook.base.RxPresenter;
import com.example.greatbook.model.leancloud.LBookKindBean;
import com.example.greatbook.presenter.contract.BookKindContract;

import java.util.List;

/**
 * Created by MBENBEN on 2016/11/20.
 */

public class BookKindPresenter extends RxPresenter<BookKindContract.View>implements BookKindContract.Presenter {
    private RetrofitHelper retrofitHelper=null;
    private OkHttpHelper okHttpHelper=null;

    public BookKindPresenter(BookKindContract.View bookKindView) {
        mView = bookKindView;
        okHttpHelper=new OkHttpHelper();
        retrofitHelper=new RetrofitHelper();
    }

    @Override
    public void setOnLoadBookKind(final String path, final int position) {
        AVQuery<LBookKindBean> query=AVQuery.getQuery(LBookKindBean.class);
        query.whereEqualTo("belongId",path);
        query.findInBackground(new FindCallback<LBookKindBean>() {
            @Override
            public void done(List<LBookKindBean> list, AVException e) {
                if (e==null){
                    mView.showLoaded();
                    mView.initDatasSuccess(list);
                }else{
                    mView.showLoaded();
                    mView.initDatasError("加载失败，请重试。");
                }
            }
        });
//        mView.showLoading();
//        if (path.contains("mingzhu")||path.contains("renwuchuanji")) {
//            Subscription subscription = retrofitHelper.getBookKind(path)
//                    .compose(RxUtil.<String>rxSchedulerHelper())
//                    .subscribe(new Action1<String>() {
//                        @Override
//                        public void call(final String s) {
//                            mView.showLoaded();
//                            mView.initDatasSuccess(JsoupUtils.getBookKind(s));
//                        }
//                    }, new Action1<Throwable>() {
//                        @Override
//                        public void call(Throwable throwable) {
//                            mView.showLoaded();
//                            mView.showError("有首歌这样唱：“不如我们重新进一次...”。(数据获取失败)");
//                        }
//                    });
//            addSubscrebe(subscription);
//        }else if (path.contains("gushihui")||path.contains("wuxia")||path.contains("lizhishu")){
//            Observable.create(new Observable.OnSubscribe<String>() {
//                @Override
//                public void call(final Subscriber<? super String> subscriber) {
//                    okHttpHelper.sendRequestByUrl(Url.HOST+path)
//                            .enqueue(new Callback() {
//                                @Override
//                                public void onFailure(Call call, IOException e) {
//                                    subscriber.onError(e);
//                                }
//                                @Override
//                                public void onResponse(Call call, Response response) throws IOException {
//                                    byte[] datas=response.body().bytes();
//                                    final String content=new String(datas, "GB2312");
//                                    subscriber.onNext(content);
//                                }
//                            });
//                }
//            }).subscribeOn(Schedulers.io())
//                    .subscribeOn(AndroidSchedulers.mainThread())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe(new Subscriber<String>() {
//                        @Override
//                        public void onCompleted() {
//                            mView.showLoaded();
//                        }
//
//                        @Override
//                        public void onError(Throwable throwable) {
//                            mView.showLoaded();
//                            mView.showError("服务器：来啊，互相伤害啊！（数据获取失败）");
//                        }
//
//                        @Override
//                        public void onNext(final String s) {
//                            mView.showLoaded();
//
//                            mView.initDatasSuccess(JsoupUtils.getBookKind(s));
//                        }
//                    });
//        }else {
//            //点击武侠作家后的跳转
//            Observable.create(new Observable.OnSubscribe<String>() {
//                @Override
//                public void call(final Subscriber<? super String> subscriber) {
//                    okHttpHelper.sendRequestByUrl(path)
//                            .enqueue(new Callback() {
//                                @Override
//                                public void onFailure(Call call, IOException e) {
//                                    subscriber.onError(e);
//                                }
//                                @Override
//                                public void onResponse(Call call, Response response) throws IOException {
//                                    byte[] datas=response.body().bytes();
//                                    final String content=new String(datas, "GB2312");
//                                    subscriber.onNext(content);
//                                }
//                            });
//                }
//            }).subscribeOn(Schedulers.io())
//                    .subscribeOn(AndroidSchedulers.mainThread())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe(new Subscriber<String>() {
//                        @Override
//                        public void onCompleted() {
//                            mView.showLoaded();
//                        }
//
//                        @Override
//                        public void onError(Throwable throwable) {
//                            mView.showLoaded();
//                            mView.showError("服务器：宝宝罢工了！（数据获取失败）");
//                        }
//
//                        @Override
//                        public void onNext(final String s) {
//                            mView.showLoaded();
//                            if (position==0||position==1) {
//
//                                mView.initDatasSuccess(JsoupUtils.getWuXiaBookKindOne(s));
//                            }else{
//                                mView.initDatasSuccess(JsoupUtils.getWuXiaBookKindTwo(s));
//                            }
//                        }
//                    });
//        }
    }

}
