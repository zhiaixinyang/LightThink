package com.example.greatbook.presenter.book.contract;

import com.example.greatbook.base.BasePresenter;
import com.example.greatbook.base.BaseView;
import com.example.greatbook.model.BookSectionBean;
import com.example.greatbook.model.BookSectionContentBean;

import java.util.List;

/**
 * Created by quanzizhangben on 2017/8/10.
 */

public interface BookSectionContentContract {
    interface View extends BaseView {
        void initBookSectionContent(BookSectionContentBean contentBean);
        void showLoading();
        void showLoaded();
    }
    interface Presenter extends BasePresenter<BookSectionContentContract.View> {
        void querySectionContent(String url);
    }
}
