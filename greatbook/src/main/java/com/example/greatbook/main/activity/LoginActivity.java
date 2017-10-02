package com.example.greatbook.main.activity;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.greatbook.App;
import com.example.greatbook.R;
import com.example.greatbook.base.BaseActivity;
import com.example.greatbook.main.presenter.LoginPresenter;
import com.example.greatbook.main.presenter.contract.LoginContract;
import com.example.greatbook.utils.NetUtil;
import com.example.greatbook.utils.StringUtils;
import com.example.greatbook.utils.ToastUtil;
import com.example.greatbook.utils.TransWindowUtils;
import com.example.greatbook.utils.WaitNetPopupWindowUtils;

import butterknife.BindView;

/**
 * Created by MDove on 2016/10/20.
 */

public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginContract.View,
        View.OnClickListener, PopupWindow.OnDismissListener {
    @BindView(R.id.btn_login)
    TextView btnLogin;
    @BindView(R.id.btn_register)
    TextView btnRegister;
    @BindView(R.id.et_account)
    EditText etAccount;
    @BindView(R.id.et_password)
    EditText etPassWord;

    private WaitNetPopupWindowUtils waitNetPopupWindowUtils = null;


    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void init() {
        presenter = new LoginPresenter(this);
        waitNetPopupWindowUtils = new WaitNetPopupWindowUtils();
        TransWindowUtils.setTransWindow(this);
        btnLogin.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                login();
                break;
            case R.id.btn_register:
                Intent toRegister = new Intent(this, RegisterActivity.class);
                startActivity(toRegister);
                finish();
                break;
        }
    }

    private void login() {
        //网络通畅时，弹出等待界面，并进行相关操作
        if (NetUtil.isNetworkAvailable()) {
            String username = etAccount.getText().toString();
            String password = etPassWord.getText().toString();
            if (!StringUtils.isEmpty(username) && !StringUtils.isEmpty(password)) {
                waitNetPopupWindowUtils.showWaitNetPopupWindow(this);
                presenter.login(username, password);
            } else {
                ToastUtil.toastShort("请输入您的账号密码");
            }
        } else {
            ToastUtil.toastShort("未连接网络");
        }
    }

    @Override
    public void onDismiss() {
        TransWindowUtils.setBackgroundAlpha(this, 1f);
    }

    @Override
    public void showError(String msg) {
        ToastUtil.toastShort(msg);
    }

    @Override
    public void loginSuc(String suc) {
        presenter.initDB();
        ToastUtil.toastLong(suc);
        waitNetPopupWindowUtils.hideWaitNetPopupWindow(this);
        //先跳转至同步页面
        Intent intent = new Intent(App.getInstance().getContext(), InitSyncActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.login_in, R.anim.login_out);
    }

    @Override
    public void loginErr(String err) {
        ToastUtil.toastLong(err);
        waitNetPopupWindowUtils.hideWaitNetPopupWindow(this);
    }
}
