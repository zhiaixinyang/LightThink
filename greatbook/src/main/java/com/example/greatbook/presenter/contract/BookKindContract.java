package com.example.greatbook.presenter.contract;

import com.example.greatbook.base.BasePresenter;
import com.example.greatbook.base.BaseView;
import com.example.greatbook.model.BookKindBean;
import com.example.greatbook.model.leancloud.LBookKindBean;

import java.util.List;

/**
 * Created by MBENBEN on 2016/11/20.
 */

public interface BookKindContract {
    interface Presenter extends BasePresenter<View> {
        //此变量对应武侠作者中的奇葩链接。0,1
        void setOnLoadBookKind(String path, int position);
    }

    interface View extends BaseView{
        void initDatasSuccess(List<LBookKindBean> datas);
        void initDatasError(String error);
        void showLoading();
        void showLoaded();
    }
}
