package com.example.greatbook.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.greatbook.App;
import com.example.greatbook.R;
import com.example.greatbook.utils.SystemBarTintManager;

import butterknife.ButterKnife;

/**
 * Created by MDove on 2016/11/21.
 */

public abstract class NewBaseActivity<T extends BasePresenter> extends AppCompatActivity implements BaseView {

    protected T presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        setTrans();
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
        if (presenter != null) {
            presenter.detachView();
        }
    }

    public abstract int getLayoutId();
    public abstract void init();
}
