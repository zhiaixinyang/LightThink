package com.example.greatbook.middle.activity;

import com.example.greatbook.R;
import com.example.greatbook.base.BaseActivity;
import com.example.greatbook.middle.presenter.InitSyncPresenter;
import com.example.greatbook.middle.presenter.contract.InitSyncContract;

/**
 * Created by MDove on 2017/8/18.
 */

public class InitSyncActivity extends BaseActivity<InitSyncPresenter> implements InitSyncContract.View{
    @Override
    public void showError(String msg) {

    }

    @Override
    public void syncDataSuc(String suc) {

    }

    @Override
    public void syncDataErr(String err) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_init_sync;
    }

    @Override
    public void init() {

    }
}
