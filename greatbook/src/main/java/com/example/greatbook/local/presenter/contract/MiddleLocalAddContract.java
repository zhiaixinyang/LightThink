package com.example.greatbook.local.presenter.contract;

import android.content.Context;

import com.example.greatbook.base.BasePresenter;
import com.example.greatbook.base.BaseView;
import com.example.greatbook.local.model.MainMenuItemBean;

import java.util.List;

/**
 * Created by MDove on 2017/8/12.
 */

public interface MiddleLocalAddContract {
    interface Presenter extends BasePresenter<MiddleLocalAddContract.View> {
        void initMenu(Context context);
    }

    interface View extends BaseView {
        void showMenu(List<MainMenuItemBean> menuData);
    }
}
