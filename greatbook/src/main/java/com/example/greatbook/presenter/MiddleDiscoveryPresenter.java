package com.example.greatbook.presenter;

import com.example.greatbook.base.RxPresenter;
import com.example.greatbook.local.presenter.contract.MiddleDiscoveryContract;

/**
 * Created by MDove on 2017/8/13.
 */

public class MiddleDiscoveryPresenter extends RxPresenter<MiddleDiscoveryContract.View>implements MiddleDiscoveryContract.Presenter{
    public MiddleDiscoveryPresenter(MiddleDiscoveryContract.View view){
        mView=view;
    }

    @Override
    public void initDiscoveryTop() {

    }

    @Override
    public void initDiscoveryRecord() {

    }
}
