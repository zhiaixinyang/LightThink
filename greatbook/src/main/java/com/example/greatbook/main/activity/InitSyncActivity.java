package com.example.greatbook.main.activity;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;

import com.avos.avoscloud.AVUser;
import com.example.greatbook.R;
import com.example.greatbook.base.BaseActivity;
import com.example.greatbook.local.presenter.InitSyncPresenter;
import com.example.greatbook.local.presenter.contract.InitSyncContract;
import com.example.greatbook.model.leancloud.User;
import com.example.greatbook.utils.LogUtils;
import com.example.greatbook.utils.ToastUtil;

import butterknife.BindView;
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
        Intent toMain = new Intent(this, MainActivity.class);
        startActivity(toMain);
        finish();
    }

    @Override
    public void syncDataErr(String err) {
        LogUtils.d(err);
        netLayout.setVisibility(View.VISIBLE);
        syncLayout.setVisibility(View.GONE);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_init_sync;
    }

    @Override
    public void init() {
        presenter = new InitSyncPresenter(this);
        user = AVUser.getCurrentUser(User.class);
        if (user != null) {
            presenter.syncData(user.getObjectId());
        } else {
            toLogin();
        }
    }

    @OnClick(R.id.btn_net)
    public void onViewClicked() {
        if (user != null) {
            presenter.syncData(user.getObjectId());
            netLayout.setVisibility(View.GONE);
            syncLayout.setVisibility(View.VISIBLE);
        } else {
            toLogin();
        }
    }

    private void toLogin() {
        ToastUtil.toastShort("请登录");
        Intent toLogin = new Intent(this, LoginActivity.class);
        startActivity(toLogin);
        finish();
    }
}
