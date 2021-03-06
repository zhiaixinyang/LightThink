package com.example.greatbook.local.presenter.contract;

import com.example.greatbook.base.BasePresenter;
import com.example.greatbook.base.BaseView;
import com.example.greatbook.greendao.entity.MyPlanTemplate;

/**
 * Created by MDove on 2017/9/17.
 */

public interface AddTemplatePlanContract {
    interface Presenter extends BasePresenter<AddTemplatePlanContract.View> {
        void insertAddTemplate(MyPlanTemplate myPlanTemplate);
    }

    interface View extends BaseView {
        void insertSuc();
    }
}
