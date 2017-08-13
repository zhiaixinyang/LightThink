package com.example.greatbook.presenter;

import com.avos.avoscloud.AVQuery;
import com.example.greatbook.base.RxPresenter;
import com.example.greatbook.middle.presenter.MiddleLocalAddPresenter;
import com.example.greatbook.middle.presenter.contract.MiddleLocalAddContract;
import com.example.greatbook.model.leancloud.LLocalGroup;

/**
 * Created by MDove on 2017/8/13.
 */

public class MiddleDiscoveryPresenter extends RxPresenter<MiddleLocalAddContract.View>implements MiddleLocalAddContract.Presenter{
    public MiddleDiscoveryPresenter(MiddleLocalAddContract.View view){
        mView=view;
    }
    @Override
    public void initLocalRecord() {
        AVQuery<LLocalGroup> query=AVQuery.getQuery(LLocalGroup.class);
    }
}
