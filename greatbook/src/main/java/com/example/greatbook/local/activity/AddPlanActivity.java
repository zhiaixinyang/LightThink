package com.example.greatbook.local.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;

import com.example.greatbook.R;
import com.example.greatbook.base.adapter.OnItemClickListener;
import com.example.greatbook.databinding.ActivityAddPlanBinding;
import com.example.greatbook.greendao.entity.MyPlan;
import com.example.greatbook.greendao.entity.MyPlanTemplate;
import com.example.greatbook.local.adapter.PlanTemplateAdapter;
import com.example.greatbook.local.viewmodel.MyPlanTemplateVM;
import com.example.greatbook.model.event.AddPlanEvent;
import com.example.greatbook.model.event.AddTemplateEvent;
import com.example.greatbook.presenter.AddPlanPresenter;
import com.example.greatbook.presenter.contract.AddPlanContract;
import com.example.greatbook.utils.StringUtils;
import com.example.greatbook.utils.ToastUtil;
import com.example.greatbook.widght.DefaultNavigationBar;
import com.example.greatbook.widght.rlvanim.adapter.SlideInLeftAnimationAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

/**
 * Created by MDove on 2017/9/16.
 */

public class AddPlanActivity extends AppCompatActivity implements AddPlanContract.View {
    private ActivityAddPlanBinding binding;
    private AddPlanPresenter mPlanPresenter;
    private PlanTemplateAdapter mAdapter;
    private MyPlanTemplate mCurPlanTemplate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        new DefaultNavigationBar.Builder(this, null)
                .setRightResId(R.drawable.btn_send)
                .setTitleText("设定目标")
                .setOnRightClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        insertPlan();
                    }
                }).setLeftResId(R.drawable.btn_back_)
                .setOnLeftClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                })
                .builder();
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_plan);
        mPlanPresenter = new AddPlanPresenter();
        mPlanPresenter.attachView(this);
        mPlanPresenter.initTemplate();
    }
    SlideInLeftAnimationAdapter adapter;
    @Override
    public void showTemplates(List<MyPlanTemplate> data) {
        initTitle(data);

        mAdapter = new PlanTemplateAdapter(this, data);
        mAdapter.setOnItemClickListener(new OnItemClickListener<MyPlanTemplate>() {
            @Override
            public void onItemClick(View view, MyPlanTemplate myPlanTemplate, int position) {
                if (myPlanTemplate == null) {
                    //此时说明Add图标被点击
                    AddTemplatePlanActivity.startAddTemplate(AddPlanActivity.this);
                } else {
                    mCurPlanTemplate = myPlanTemplate;
                    String[] titles = myPlanTemplate.content.split("\n");
                    MyPlanTemplateVM vm = new MyPlanTemplateVM();
                    vm.title1.set(titles[0]);
                    vm.title2.set(titles[1].substring(6, titles[1].length()));
                    vm.title3.set(titles[2].substring(0, titles[2].length() - 6));
                    vm.bgColor.set(myPlanTemplate.bgColor);
                    vm.textColor.set(Color.BLACK);
                    vm.detailColor.set(myPlanTemplate.detailColor);
                    vm.textSize.set(myPlanTemplate.textSize);

                    binding.setPlanTemplateVm(vm);
                }
            }
        });
        adapter=new SlideInLeftAnimationAdapter(mAdapter);
        adapter.setDuration(500);


        binding.rlvTemplate.setLayoutManager(new GridLayoutManager(this, 2));
        binding.rlvTemplate.setAdapter(adapter);
    }

    private void initTitle(List<MyPlanTemplate> data) {
        mCurPlanTemplate = data.get(0);
        String[] titles = mCurPlanTemplate.content.split("\n");
        MyPlanTemplateVM vm = new MyPlanTemplateVM();
        vm.title1.set(titles[0]);
        vm.title2.set(titles[1].substring(6, titles[1].length()));
        vm.title3.set(titles[2].substring(0, titles[2].length() - 6));
        vm.bgColor.set(mCurPlanTemplate.bgColor);
        vm.detailColor.set(mCurPlanTemplate.detailColor);
        vm.textColor.set(Color.BLACK);
        vm.textSize.set(mCurPlanTemplate.textSize);

        binding.setPlanTemplateVm(vm);
    }

    @Override
    public void insertPlanSUc() {
        ToastUtil.toastShort("创建目标成功");
        AddPlanEvent event = new AddPlanEvent();
        event.event = AddPlanEvent.ADD_PLAN_EVENT;
        EventBus.getDefault().post(event);
        finish();
    }

    @Override
    public void showError(String msg) {
        ToastUtil.toastShort(msg);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void insertPlan() {
        String tvTime = binding.etTime.getText().toString().trim();
        String tvPlan = binding.etPlan.getText().toString().trim();
        if (!StringUtils.isEmpty(tvTime) && !StringUtils.isEmpty(tvPlan)) {
            MyPlan myPlan = new MyPlan();
            String content = binding.tvTitle1.getText() + "\n"
                    + tvTime + binding.tvTitle2.getText() + "\n"
                    + binding.tvTitle3.getText() + tvPlan;
            myPlan.content = content;
            myPlan.bgColor = mCurPlanTemplate.bgColor;
            myPlan.textColor = mCurPlanTemplate.textColor;
            myPlan.textSize = mCurPlanTemplate.textSize;
            myPlan.detailColor = mCurPlanTemplate.detailColor;

            mPlanPresenter.insertPlan(myPlan);

        } else {
            ToastUtil.toastShort("请完整的填写目标信息");
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(AddTemplateEvent event){
        switch (event.event){
            case AddTemplateEvent.ADD_TEMPLATE_PLAN_EVENT:
                mPlanPresenter.initTemplate();
                break;
            default:
                break;
        }
    }
}
