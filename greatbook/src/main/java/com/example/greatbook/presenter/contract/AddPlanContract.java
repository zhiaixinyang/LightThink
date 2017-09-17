package com.example.greatbook.presenter.contract;

import com.example.greatbook.base.BasePresenter;
import com.example.greatbook.base.BaseView;
import com.example.greatbook.greendao.entity.MyPlan;
import com.example.greatbook.greendao.entity.MyPlanTemplate;

import java.util.List;

/**
 * Created by MDove on 2017/9/16.
 */

public interface AddPlanContract {
    interface Presenter extends BasePresenter<AddPlanContract.View>{
        void initTemplate();
        void insertPlan(MyPlan plan);
    }
    interface View extends BaseView{
        void showTemplates(List<MyPlanTemplate> data);
        void insertPlanSUc();
    }
}
