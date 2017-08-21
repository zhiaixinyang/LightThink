package com.example.greatbook.middle.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.avos.avoscloud.AVUser;
import com.example.greatbook.R;
import com.example.greatbook.base.BaseActivity;
import com.example.greatbook.middle.presenter.InitSyncPresenter;
import com.example.greatbook.middle.presenter.contract.InitSyncContract;
import com.example.greatbook.model.leancloud.User;
import com.example.greatbook.ui.main.activity.LoginActivity;
import com.example.greatbook.ui.main.activity.MainNewActivity;
import com.example.greatbook.utils.ToastUtil;
import com.iflytek.cloud.thirdparty.V;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by MDove on 2017/8/18.
 */

public class InitSyncActivity extends BaseActivity<InitSyncPresenter> implements InitSyncContract.View {
    @BindView(R.id.sync_layout)
    LinearLayout syncLayout;
    @BindView(R.id.net_layout)
    LinearLayout netLayout;
    private User user;

    @Override
    public void showError(String msg) {
        ToastUtil.toastShort(msg);
    }

    @Override
    public void syncDataSuc(String suc) {
        ToastUtil.toastShort(suc);
        Intent toMain=new Intent(this, MainNewActivity.class);
        startActivity(toMain);
        finish();
    }

    @Override
    public void syncDataErr(String err) {
        netLayout.setVisibility(View.VISIBLE);
        syncLayout.setVisibility(View.GONE);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_init_sync;
    }

    @Override
    public void init() {
        presenter=new InitSyncPresenter(this);
        user = AVUser.getCurrentUser(User.class);
        if (user!=null) {
            presenter.syncData(user.getObjectId());
        }else{
            toLogin();
        }
    }

    @OnClick(R.id.btn_net)
    public void onViewClicked() {
        if (user!=null) {
            presenter.syncData(user.getObjectId());
            netLayout.setVisibility(View.GONE);
            syncLayout.setVisibility(View.VISIBLE);
        }else{
            toLogin();
        }
    }

    private void toLogin() {
        ToastUtil.toastShort("请登录");
        Intent toLogin=new Intent(this, LoginActivity.class);
        startActivity(toLogin);
        finish();
    }
}
