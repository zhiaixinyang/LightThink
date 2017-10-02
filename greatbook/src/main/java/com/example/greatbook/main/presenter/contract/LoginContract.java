package com.example.greatbook.main.presenter.contract;

import com.example.greatbook.base.BasePresenter;
import com.example.greatbook.base.BaseView;

/**
 * Created by MDove on 2017/8/14.
 */

public interface LoginContract {
    interface View extends BaseView {
        void loginSuc(String suc);
        void loginErr(String err);
    }
    interface Presenter extends BasePresenter<LoginContract.View> {
        void login(String username,String password);
        void initDB();
    }
}
