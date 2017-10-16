package com.example.greatbook.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.greatbook.App;
import com.example.greatbook.R;
import com.example.greatbook.base.dialog.BaseAlertDialog;
import com.example.greatbook.utils.SystemBarTintManager;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by MDove on 2016/11/21.
 */

public abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity implements BaseView{
    protected T presenter;
    protected BaseAlertDialog dialog;
    private Unbinder unbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        unbinder = ButterKnife.bind(this);
        //StatusBarUtil.setImgTransparent(this);
        setTrans();
        App.getInstance().addActivity(this);
        if (presenter!=null) {
            presenter.attachView(this);
        }
        init();
    }

    private void setTrans() {
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintResource(R.color.black);//通知栏所需颜色
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        App.getInstance().removeActivity(this);
        if (dialog!=null){
            dialog.dismiss();
        }
    }


    public abstract int getLayoutId();
    public abstract void init();
}
