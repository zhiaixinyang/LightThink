package com.example.greatbook.ui.main.presenter.contract;

import com.example.greatbook.base.BasePresenter;
import com.example.greatbook.base.BaseView;
import com.example.greatbook.ui.model.LFeedBackBean;

import java.util.List;

/**
 * Created by MDove on 2017/8/14.
 */

public interface SplashContract {
    interface View extends BaseView {

    }
    interface Presenter extends BasePresenter<SplashContract.View> {
        void syncDB(String userId);
    }
}
