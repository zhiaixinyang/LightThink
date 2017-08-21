package com.example.greatbook.middle.activity;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.greatbook.R;
import com.example.greatbook.constants.Constants;
import com.example.greatbook.databinding.ActivityDiscoveryRecordBinding;
import com.example.greatbook.middle.model.DiscoveryRecord;
import com.example.greatbook.middle.viewmodel.DiscoveryRecordRemarkVM;
import com.example.greatbook.utils.DateUtils;

import org.jsoup.helper.DataUtil;

/**
 * Created by MDove on 2017/8/21.
 */

public class DiscoveryRecordRemarkActivity extends AppCompatActivity{
    private DiscoveryRecordRemarkVM remarkVM;
    private DiscoveryRecord discoveryRecord;
    private ActivityDiscoveryRecordBinding dataBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_discovery_record);
        remarkVM=new DiscoveryRecordRemarkVM();
        discoveryRecord= (DiscoveryRecord) getIntent().getSerializableExtra(Constants.DISCOVERY_RECORD_ITEM_CLICK);
        if (discoveryRecord!=null){
            remarkVM.belongId.set(discoveryRecord.belongId);
            remarkVM.time.set(DateUtils.getDateChinese(discoveryRecord.time));
            remarkVM.content.set(discoveryRecord.content);
            remarkVM.groupId.set(discoveryRecord.groupId);
            remarkVM.likeNum.set(discoveryRecord.likeNum);
            remarkVM.initAllMes(discoveryRecord.belongId);
            dataBinding.setRemarkVM(remarkVM);
        }
    }
}
