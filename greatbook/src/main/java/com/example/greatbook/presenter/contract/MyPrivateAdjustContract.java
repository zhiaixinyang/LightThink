package com.example.greatbook.presenter.contract;

import android.graphics.Bitmap;

import com.example.greatbook.base.BasePresenter;
import com.example.greatbook.base.BaseView;

/**
 * Created by MBENBEN on 2016/11/26.
 */

public interface MyPrivateAdjustContract {
    interface Presenter extends BasePresenter<View> {
    }
    interface View extends BaseView{
        void initAvatar(Bitmap avatar);
        void showLoading();
        void showLoaded();
    }
}
