package com.example.greatbook.middle.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.greatbook.R;
import com.example.greatbook.widght.DefaultNavigationBar;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by MDove on 2017/8/11.
 */

public class LocalAddActivity extends AppCompatActivity {

    private Unbinder unbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_add);
        unbinder = ButterKnife.bind(this);
        new DefaultNavigationBar.Builder(this,null)
                .setOnLeftClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                })
                .setTitleText("随手记")
                .builder();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (unbinder!=null){
            unbinder.unbind();
        }
    }
}
