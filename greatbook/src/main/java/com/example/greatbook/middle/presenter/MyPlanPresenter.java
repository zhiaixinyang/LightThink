package com.example.greatbook.middle.presenter;

import com.example.greatbook.App;
import com.example.greatbook.greendao.MyPlanDao;
import com.example.greatbook.greendao.entity.MyPlan;
import com.example.greatbook.presenter.contract.MyPlanContract;

import java.util.List;

/**
 * Created by MDO\ove on 2017/9/16.
 */

public class MyPlanPresenter implements MyPlanContract.Presenter {
    private MyPlanContract.View view;
    private MyPlanDao myPlanDao;

    public MyPlanPresenter() {
        myPlanDao = App.getDaoSession().getMyPlanDao();
    }

    @Override
    public void loadMyPlan() {
        List<MyPlan> data = myPlanDao.queryBuilder().list();
        if (data == null || data.size() == 0) {
            view.myPlansEmpty();
        } else {
            view.showMyPlans(data);
        }
    }

    @Override
    public void attachView(MyPlanContract.View view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        this.view = null;
    }
}
