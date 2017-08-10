package com.example.greatbook.ui.presenter.view;

import com.example.greatbook.base.BaseView;
import com.example.greatbook.model.WeChatItemBean;

import java.util.List;

/**
 * Created by MBENBEN on 2016/11/27.
 */

public interface WeChatListView extends BaseView{
    void showWeChatList(List<WeChatItemBean> weChatItemBean);
    void showLoading();
    void showLoaded();
}
