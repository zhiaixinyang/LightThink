package com.example.greatbook.middle.activity;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.example.greatbook.R;
import com.example.greatbook.constants.Constants;
import com.example.greatbook.databinding.ActivityDiscoveryRecordBinding;
import com.example.greatbook.middle.adapter.RecordRemarksAdapter;
import com.example.greatbook.middle.model.DiscoveryRecord;
import com.example.greatbook.middle.viewmodel.DiscoveryRecordRemarkVM;
import com.example.greatbook.model.leancloud.LLocalRecord;
import com.example.greatbook.utils.DateUtils;
import com.example.greatbook.utils.DpUtils;
import com.example.greatbook.utils.SelectorFactory;
import com.example.greatbook.widght.DefaultNavigationBar;

/**
 * Created by MDove on 2017/8/21.
 */

public class DiscoveryRecordRemarkActivity extends AppCompatActivity{
    private DiscoveryRecordRemarkVM remarkVM;
    private DiscoveryRecord discoveryRecord;
    private ActivityDiscoveryRecordBinding dataBinding;
    private RecordRemarksAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new DefaultNavigationBar.Builder(this,null)
                .setTitleText("好文")
                .setOnLeftClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                })
                .setLeftResId(R.drawable.btn_back_).builder();

        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_discovery_record);
        initViewBackGround();

        discoveryRecord= (DiscoveryRecord) getIntent().getSerializableExtra(Constants.DISCOVERY_RECORD_ITEM_CLICK);

        adapter=new RecordRemarksAdapter(this);
        dataBinding.rlvActivityDiscovery.setLayoutManager(new LinearLayoutManager(this));
        dataBinding.rlvActivityDiscovery.setAdapter(adapter);

        if (discoveryRecord!=null){
            remarkVM=new DiscoveryRecordRemarkVM(discoveryRecord);

            remarkVM.belongId.set(discoveryRecord.belongId);
            remarkVM.time.set(DateUtils.getDateChinese(discoveryRecord.time));
            remarkVM.content.set(discoveryRecord.content);
            remarkVM.groupId.set(discoveryRecord.groupId);
            remarkVM.likeNum.set(discoveryRecord.likeNum+"");
            remarkVM.title.set(discoveryRecord.title);
            remarkVM.initAllMes(discoveryRecord.belongId);
            remarkVM.initRemarks(discoveryRecord.objectId);
            dataBinding.setRemarkVM(remarkVM);
        }

    }

    private void initViewBackGround() {
        dataBinding.btnSend.setBackground(SelectorFactory.newShapeSelector()
                .setCornerRadius(DpUtils.dp2px(4))
                .setDefaultBgColor(ContextCompat.getColor(this,R.color.blue_light))
                .setFocusedBgColor(ContextCompat.getColor(this,R.color.blue_light))
                .create());
        dataBinding.etContent.setBackground(SelectorFactory.newShapeSelector()
                .setCornerRadius(DpUtils.dp2px(4))
                .setDefaultBgColor(Color.WHITE)
                .create());
    }
}
