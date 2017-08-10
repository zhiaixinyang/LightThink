package com.example.greatbook.ui.presenter.view;

import com.example.greatbook.base.BaseView;
import com.example.greatbook.model.ZhihuDetailBean;

/**
 * Created by MBENBEN on 2016/11/26.
 */

public interface ZhiHuDetailView extends BaseView{
    void showContent(ZhihuDetailBean zhihuDetailBean);
    void showLoading();
    void showLoaded();
}
