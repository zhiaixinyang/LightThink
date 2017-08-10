package com.example.greatbook.presenter.contract;

import com.example.greatbook.base.BasePresenter;
import com.example.greatbook.base.BaseView;
import com.example.greatbook.model.BookDetailBean;

/**
 * Created by MBENBEN on 2016/11/21.
 */

public interface BookDetailContract {

    interface Presenter extends BasePresenter<View>{
        //position作用，判断奇葩链接
        void getBookDetail(String path,int position);
    }

    interface View extends BaseView{
        void showDatas(BookDetailBean data);
        void showLoading();
        void showLoaded();
    }
}
