package com.example.greatbook.local.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.greatbook.R;
import com.example.greatbook.databinding.ActivityMyPlanBinding;
import com.example.greatbook.greendao.entity.MyPlan;
import com.example.greatbook.local.adapter.MyPlanVPAdapter;
import com.example.greatbook.local.presenter.MyPlanPresenter;
import com.example.greatbook.model.event.AddPlanEvent;
import com.example.greatbook.presenter.contract.MyPlanContract;
import com.example.greatbook.utils.ToastUtil;
import com.example.greatbook.widght.DefaultNavigationBar;
import com.example.greatbook.widght.viewpager.view.UltraViewPager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MDove on 2017/9/16.
 */

public class MyPlanActivity extends AppCompatActivity implements MyPlanContract.View {
    private ActivityMyPlanBinding binding;
    private MyPlanVPAdapter adapter;
    private List<MyPlan> data;
    private MyPlanPresenter planPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_my_plan);
        EventBus.getDefault().register(this);
        new DefaultNavigationBar.Builder(this, null)
                .setRightResId(R.drawable.btn_plan_add)
                .setTitleText("我的计划")
                .setOnRightClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent toAddPlan = new Intent(MyPlanActivity.this, AddPlanActivity.class);
                        startActivity(toAddPlan);
                    }
                })
                .setLeftResId(R.drawable.btn_back_)
                .setOnLeftClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                })
                .builder();
        planPresenter = new MyPlanPresenter();
        planPresenter.attachView(this);
        planPresenter.loadMyPlan();
        binding.viewPager.setScrollMode(UltraViewPager.ScrollMode.VERTICAL);
    }

    @Override
    public void showMyPlans(List<MyPlan> data) {
        adapter = new MyPlanVPAdapter(this, data);
        binding.viewPager.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void myPlansEmpty() {
        data = new ArrayList<>();
        MyPlan myPlan = new MyPlan();
        myPlan.textSize = 26;
        myPlan.bgColor = ContextCompat.getColor(this, R.color.black);
        myPlan.textColor = ContextCompat.getColor(this, R.color.white);
        myPlan.content = "人如果没有梦想\n那和咸鱼有什么区别？\n设置我的阶段目标吧！";
        data.add(myPlan);
        adapter = new MyPlanVPAdapter(this, data);
        binding.viewPager.setAdapter(adapter);
    }

    @Override
    public void showError(String msg) {
        ToastUtil.toastShort(msg);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(AddPlanEvent event) {
        switch (event.event) {
            case AddPlanEvent.ADD_PLAN_EVENT:
                planPresenter.loadMyPlan();
                break;
            default:
                break;
        }
    }
}
