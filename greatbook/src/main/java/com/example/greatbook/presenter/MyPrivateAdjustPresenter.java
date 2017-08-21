package com.example.greatbook.presenter;

import com.example.greatbook.base.RxPresenter;
import com.example.greatbook.presenter.contract.MyPrivateAdjustContract;

/**
 * Created by MDove on 2016/11/26.
 */

public class MyPrivateAdjustPresenter extends RxPresenter<MyPrivateAdjustContract.View> implements MyPrivateAdjustContract.Presenter {

    public MyPrivateAdjustPresenter(MyPrivateAdjustContract.View myPrivateAdjustView) {
        mView = myPrivateAdjustView;
    }

}
