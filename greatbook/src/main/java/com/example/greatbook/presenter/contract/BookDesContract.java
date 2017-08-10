package com.example.greatbook.presenter.contract;


import com.example.greatbook.base.BasePresenter;
import com.example.greatbook.base.BaseView;
import com.example.greatbook.model.BookDesBean;

/**
 * Created by MBENBEN on 2016/11/21.
 */

public interface BookDesContract {

    interface Presenter extends BasePresenter<View>{
        /**
         *
         * @param url 文章链接
         * @param position 此变量的作用比较奇特：
         *                 因为此批链接中的源码不同。
         *                 所以通过不同的postion来选择不同的Jsoup解析模式。
         */
        void setOnLoadBookDes(String url,int position);
    }


    interface View extends BaseView{
        //position变量，为了区别奇葩链接。
        void initDatas(BookDesBean datas, int position);
        void showLoading();
        void showLoaded();
    }
}
