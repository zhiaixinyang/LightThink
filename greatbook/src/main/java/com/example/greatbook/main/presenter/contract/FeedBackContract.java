package com.example.greatbook.main.presenter.contract;

import com.example.greatbook.base.BasePresenter;
import com.example.greatbook.base.BaseView;
import com.example.greatbook.main.model.LFeedBackBean;

import java.util.List;

/**
 * Created by quanzizhangben on 2017/8/10.
 */

public interface FeedBackContract {
    interface View extends BaseView {
        void intiFeeBackContent(List<LFeedBackBean> data);
        void getMoreFeedBackContent(List<LFeedBackBean> data);
        void showLoading();
        void showLoaded();
        void sendContentSuc(String success);
        void sendContentError(String error);
    }
    interface Presenter extends BasePresenter<FeedBackContract.View> {
        void getFeedBackContent();
        void getMoreFeedBackContent(int currentNum);
        void sendContent(String belongId,String content);
    }
}
