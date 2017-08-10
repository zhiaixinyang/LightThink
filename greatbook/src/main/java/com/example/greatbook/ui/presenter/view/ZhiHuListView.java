package com.example.greatbook.ui.presenter.view;

import com.example.greatbook.base.BaseView;
import com.example.greatbook.model.DailyListBean;

/**
 * Created by MBENBEN on 2016/11/26.
 */

public interface ZhiHuListView extends BaseView{
    void showContent(DailyListBean info);
    void showLoading();
    void showLoaded();
}
