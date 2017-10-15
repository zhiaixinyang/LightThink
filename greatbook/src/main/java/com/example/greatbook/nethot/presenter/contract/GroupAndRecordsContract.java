package com.example.greatbook.nethot.presenter.contract;

import com.example.greatbook.base.BasePresenter;
import com.example.greatbook.base.BaseView;

/**
 * Created by MDove on 2017/10/15.
 */

public interface GroupAndRecordsContract {
    interface View extends BaseView {
        void followerSuc(String suc);
        void followerErr(String err);
        void followerLoading();
    }
    interface Presenter extends BasePresenter<GroupAndRecordsContract.View> {
        void followerSuc(String suc);
        void followerErr(String err);
        void followerLoading();
        void showError(String msg);
    }
}
