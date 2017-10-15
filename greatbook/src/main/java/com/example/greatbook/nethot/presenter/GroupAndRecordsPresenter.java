package com.example.greatbook.nethot.presenter;

import com.example.greatbook.nethot.presenter.contract.GroupAndRecordsContract;

/**
 * Created by MDove on 2017/10/15.
 */

public class GroupAndRecordsPresenter implements GroupAndRecordsContract.Presenter {
    private GroupAndRecordsContract.View mView;

    @Override
    public void attachView(GroupAndRecordsContract.View view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }

    @Override
    public void followerSuc(String suc) {
        if (mView!=null){
            mView.followerSuc(suc);
        }
    }

    @Override
    public void followerErr(String err) {
        if (mView!=null){
            mView.followerErr(err);
        }
    }

    @Override
    public void followerLoading() {
        if (mView!=null){
            mView.followerLoading();
        }
    }

    @Override
    public void showError(String msg) {
        if (mView!=null){
            mView.showError(msg);
        }
    }
}
