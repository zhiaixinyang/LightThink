package com.example.greatbook.nethot.presenter;

import com.example.greatbook.base.BasePresenter;
import com.example.greatbook.base.BaseView;

/**
 * Created by MDove on 2017/8/10.
 */

public interface HotContract {
    interface View extends BaseView {
    }
    interface Presenter extends BasePresenter<HotContract.View> {
    }
}
