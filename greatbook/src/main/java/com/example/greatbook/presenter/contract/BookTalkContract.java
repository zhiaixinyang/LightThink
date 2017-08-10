package com.example.greatbook.presenter.contract;

import com.example.greatbook.base.BasePresenter;
import com.example.greatbook.base.BaseView;
import com.example.greatbook.model.leancloud.BookTalkBean;

import java.util.List;

/**
 * Created by MBENBEN on 2016/11/25.
 */

public interface BookTalkContract {
    interface Presenter extends BasePresenter<View> {
        void getBookTalkDatas(String bookUrl);
    }
    interface View extends BaseView{
        void initDatas(List<BookTalkBean> datas);
        void showLoading();
        void showLoaded();
    }
}
