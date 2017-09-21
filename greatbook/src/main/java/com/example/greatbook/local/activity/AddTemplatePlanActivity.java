package com.example.greatbook.local.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CompoundButton;

import com.example.greatbook.R;
import com.example.greatbook.databinding.ActivityAddTemplatePlanBinding;
import com.example.greatbook.greendao.entity.MyPlanTemplate;
import com.example.greatbook.local.presenter.AddTemplatePlanPresenter;
import com.example.greatbook.local.presenter.contract.AddTemplatePlanContract;
import com.example.greatbook.local.viewmodel.AddMyPlanTemplateVM;
import com.example.greatbook.model.event.AddTemplateEvent;
import com.example.greatbook.utils.ToastUtil;
import com.example.greatbook.widght.ColorPickerView;
import com.example.greatbook.widght.DefaultNavigationBar;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by MDove on 2017/9/16.
 */

public class AddTemplatePlanActivity extends AppCompatActivity implements AddTemplatePlanContract.View {
    private ActivityAddTemplatePlanBinding binding;
    private AddMyPlanTemplateVM vm;
    private int curSelect = 0;
    private AddTemplatePlanPresenter planPresenter;


    public static void startAddTemplate(Context context){
        Intent toAddTemplate =new Intent(context,AddTemplatePlanActivity.class);
        context.startActivity(toAddTemplate);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new DefaultNavigationBar.Builder(this, null)
                .setLeftResId(R.drawable.btn_back_)
                .setOnLeftClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                })
                .setTitleText("添加目标模板")
                .setRightResId(R.drawable.btn_send)
                .setOnRightClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (vm != null) {
                            MyPlanTemplate myPlan = new MyPlanTemplate();
                            myPlan.bgColor = vm.bgColor.get();
                            myPlan.content = vm.title1.get() + "\n......" + vm.title2.get() + "\n" + vm.title3.get() + "......";
                            myPlan.detailColor = vm.detailColor.get();
                            myPlan.textColor = vm.textColor.get();
                            myPlan.textSize = 16;
                            planPresenter.insertAddTemplate(myPlan);
                        }
                    }
                })
                .builder();
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_template_plan);

        binding.colorPicker.setColorListener(new ColorPickerView.ColorListener() {
            @Override
            public void onColorSelected(int color) {
                if (curSelect == 0) {
                    vm.textColor.set(color);
                } else {
                    vm.bgColor.set(color);
                }
            }
        });
        binding.cb1.setChecked(true);
        binding.cb1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    binding.cb2.setChecked(false);
                    curSelect = 0;
                } else {
                    curSelect = 1;
                }
            }
        });
        binding.cb2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    binding.cb1.setChecked(false);
                    curSelect = 1;

                } else {
                    curSelect = 0;
                }
            }
        });

        vm = new AddMyPlanTemplateVM();
        vm.tips.set("......");
        vm.bgColor.set(Color.GRAY);
        vm.textColor.set(Color.BLACK);
        vm.detailColor.set(Color.BLACK);
        vm.title1.set("我决定");
        vm.title2.set("之前");
        vm.title3.set("完成");

        binding.setAddTemplateVm(vm);

        planPresenter = new AddTemplatePlanPresenter();
        planPresenter.attachView(this);
    }

    @Override
    public void showError(String msg) {
        ToastUtil.toastShort(msg);
    }

    @Override
    public void insertSuc() {
        ToastUtil.toastShort("创建计划模板成功");
        AddTemplateEvent event = new AddTemplateEvent();
        event.event = AddTemplateEvent.ADD_TEMPLATE_PLAN_EVENT;
        EventBus.getDefault().post(event);
        finish();
    }
}
