package com.example.greatbook.presenter.contract;

import android.graphics.Bitmap;

import com.example.greatbook.base.BasePresenter;
import com.example.greatbook.base.BaseView;
import com.example.greatbook.greendao.entity.MyPlan;

import java.util.List;

/**
 * Created by MDOve on 2017/9/16.
 */

public interface MyPlanContract{
    interface Presenter extends BasePresenter<MyPlanContract.View> {
        void loadMyPlan();
    }
    interface View extends BaseView {
        void showMyPlans(List<MyPlan> data);
        void myPlansEmpty();
    }
}
