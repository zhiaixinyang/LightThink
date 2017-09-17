package com.example.greatbook.presenter;

import android.graphics.Color;

import com.example.greatbook.App;
import com.example.greatbook.greendao.MyPlanDao;
import com.example.greatbook.greendao.MyPlanTemplateDao;
import com.example.greatbook.greendao.entity.MyPlan;
import com.example.greatbook.greendao.entity.MyPlanTemplate;
import com.example.greatbook.presenter.contract.AddPlanContract;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MDove on 2017/9/16.
 */

public class AddPlanPresenter implements AddPlanContract.Presenter {
    private AddPlanContract.View view;
    private MyPlanTemplateDao myPlanTemplateDao;
    private MyPlanDao myPlanDao;

    public AddPlanPresenter() {
        myPlanTemplateDao = App.getDaoSession().getMyPlanTemplateDao();
        myPlanDao = App.getDaoSession().getMyPlanDao();
    }

    @Override
    public void initTemplate() {
        List<MyPlanTemplate> data = myPlanTemplateDao.queryBuilder().list();
        if (data != null && !data.isEmpty()) {
            view.showTemplates(data);
        }
    }

    @Override
    public void insertPlan(MyPlan plan) {
        long insert = myPlanDao.insert(plan);
        if (insert > 0) {
            view.insertPlanSUc();
        }
    }

    @Override
    public void attachView(AddPlanContract.View view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        view = null;
    }
}
