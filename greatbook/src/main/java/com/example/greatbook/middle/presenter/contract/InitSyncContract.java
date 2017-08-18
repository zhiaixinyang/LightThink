package com.example.greatbook.middle.presenter.contract;

import com.example.greatbook.base.BasePresenter;
import com.example.greatbook.base.BaseView;
import com.example.greatbook.greendao.entity.LocalGroup;

/**
 * Created by MDove on 2017/8/15.
 */

public interface InitSyncContract {
    interface Presenter extends BasePresenter<InitSyncContract.View> {
        void syncData(String belongId);
    }

    interface View extends BaseView {
        void syncDataSuc(String suc);
        void syncDataErr(String err);
    }
}
