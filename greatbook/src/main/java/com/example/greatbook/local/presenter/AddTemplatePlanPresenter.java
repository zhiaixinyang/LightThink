package com.example.greatbook.local.presenter;

import com.example.greatbook.App;
import com.example.greatbook.MySharedPreferences;
import com.example.greatbook.greendao.MyPlanTemplateDao;
import com.example.greatbook.greendao.entity.MyPlanTemplate;
import com.example.greatbook.local.presenter.contract.AddTemplatePlanContract;
import com.example.greatbook.utils.StringUtils;

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
            //统计累计书写字数
            MySharedPreferences.putCurWords(StringUtils.isEmpty(myPlanTemplate.content) ? 0 : myPlanTemplate.content.length());

            long insert = myPlanTemplateDao.insert(myPlanTemplate);
            if (insert > 0) {
                view.insertSuc();
            }
        }
    }
}
