package com.example.greatbook.ui.main.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogInCallback;
import com.example.greatbook.App;
import com.example.greatbook.MySharedPreferences;
import com.example.greatbook.R;
import com.example.greatbook.base.BaseActivity;
import com.example.greatbook.greendao.LocalGroupDao;
import com.example.greatbook.greendao.LocalRecordDao;
import com.example.greatbook.greendao.entity.LocalGroup;
import com.example.greatbook.model.leancloud.User;
import com.example.greatbook.utils.NetUtil;
import com.example.greatbook.utils.StringUtils;
import com.example.greatbook.utils.ToastUtil;
import com.example.greatbook.utils.TransWindowUtils;
import com.example.greatbook.utils.WaitNetPopupWindowUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;

/**
 * Created by MDove on 2016/10/20.
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener,PopupWindow.OnDismissListener {
    @BindView(R.id.btn_login) TextView btnLogin;
    @BindView(R.id.btn_register) TextView btnRegister;
    @BindView(R.id.et_account) EditText etAccount;
    @BindView(R.id.et_password) EditText etPassWord;

    private WaitNetPopupWindowUtils waitNetPopupWindowUtils=null;


    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void init() {
        waitNetPopupWindowUtils=new WaitNetPopupWindowUtils();
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
            final String username = etAccount.getText().toString();
            String password = etPassWord.getText().toString();
            if (!StringUtils.isEmpty(username)&&!StringUtils.isEmpty(password)){
                waitNetPopupWindowUtils.showWaitNetPopupWindow(this);
                new User().logInInBackground(username, password, new LogInCallback<AVUser>() {
                    @Override
                    public void done(AVUser avUser, AVException e) {
                        if (e == null) {
                            //首次登陆初始化本地数据库
                            initDB();
                            SharedPreferences sharedPreferences = MySharedPreferences.getFristActivityInstance();
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putInt("count", 1);
                            editor.commit();
                            ToastUtil.toastShort("登陆成功。");
                            Intent intent = new Intent(App.getInstance().getContext(), MainActivity.class);
                            startActivity(intent);
                            finish();
                            overridePendingTransition(R.anim.login_in, R.anim.login_out);
                        } else {
                            ToastUtil.toastShort("登陆失败,请检查网络环境。");
                        }
                    }
                });
            }else{
                ToastUtil.toastShort("请输入您的账号密码。");
            }
        } else {
            ToastUtil.toastShort("未连接网络！");
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

    private void initDB() {
        //第一次登陆往本地数据库初始化一些数据
        LocalGroupDao localGroupDao=App.getDaoSession().getLocalGroupDao();
        LocalRecordDao localRecordDao=App.getDaoSession().getLocalRecordDao();
        localGroupDao.deleteAll();
        localRecordDao.deleteAll();

        LocalGroup localGroupJok=new LocalGroup();
        localGroupJok.setTitle("我的本地段子集");
        localGroupJok.setTime(new Date());
        localGroupJok.setUserd(true);
        localGroupJok.setBelongId(AVUser.getCurrentUser().getObjectId());
        localGroupJok.setContent("随手记录让我一笑的段子。");
        localGroupDao.insert(localGroupJok);

        LocalGroup localGroupEncourage=new LocalGroup();
        localGroupEncourage.setTitle("我的本地鸡汤集");
        localGroupEncourage.setTime(new Date());
        localGroupEncourage.setBelongId(AVUser.getCurrentUser().getObjectId());
        localGroupEncourage.setContent("随手记录让我燃起来的鸡汤。");
        localGroupEncourage.setUserd(true);
        localGroupDao.insert(localGroupEncourage);

        LocalGroup localGroupShortEssay =new LocalGroup();
        localGroupShortEssay.setTitle("我的本地清新集");
        localGroupShortEssay.setTime(new Date());
        localGroupShortEssay.setUserd(true);
        localGroupShortEssay.setBelongId(AVUser.getCurrentUser().getObjectId());
        localGroupShortEssay.setContent("随手记录让我静心的短句。");
        localGroupDao.insert(localGroupShortEssay);
    }

}
