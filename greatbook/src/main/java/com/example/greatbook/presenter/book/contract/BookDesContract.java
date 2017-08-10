package com.example.greatbook.presenter.book.contract;

import com.example.greatbook.base.BasePresenter;
import com.example.greatbook.base.BaseView;
import com.example.greatbook.model.BookKindListBean;
import com.example.greatbook.model.BookSectionBean;

import java.util.List;

/**
 * Created by MBENBEN on 2017/2/6.
 */

public interface BookDesContract{
    interface View extends BaseView {
        void initBookDesList(List<BookSectionBean> data);
        void showLoading();
        void showLoaded();
    }
    interface Presenter extends BasePresenter<View> {
        void queryBookDes(String url);
    }
}
