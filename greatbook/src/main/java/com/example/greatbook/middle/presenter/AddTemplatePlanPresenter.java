package com.example.greatbook.middle.presenter;

import com.example.greatbook.App;
import com.example.greatbook.greendao.MyPlanTemplateDao;
import com.example.greatbook.greendao.entity.MyPlanTemplate;
import com.example.greatbook.middle.presenter.contract.AddTemplatePlanContract;

/**
 * Created by MDove on 2017/9/17.
 */

public class AddTemplatePlanPresenter implements AddTemplatePlanContract.Presenter {
    private AddTemplatePlanContract.View view;
    private MyPlanTemplateDao myPlanTemplateDao;

    public AddTemplatePlanPresenter() {
        myPlanTemplateDao = App.getDaoSession().getMyPlanTemplateDao();
    }

    @Override
    public void attachView(AddTemplatePlanContract.View view) {
        this.view = view;
    }

    @Override
    public void detachView() {

    }

    @Override
    public void insertAddTemplate(MyPlanTemplate myPlanTemplate) {
        if (myPlanTemplate != null) {
            long insert = myPlanTemplateDao.insert(myPlanTemplate);
            if (insert > 0) {
                view.insertSuc();
            }
        }
    }
}
