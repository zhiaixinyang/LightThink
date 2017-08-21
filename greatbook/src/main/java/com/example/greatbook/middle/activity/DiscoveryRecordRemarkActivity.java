package com.example.greatbook.middle.activity;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.example.greatbook.R;
import com.example.greatbook.constants.Constants;
import com.example.greatbook.databinding.ActivityDiscoveryRecordBinding;
import com.example.greatbook.middle.model.DiscoveryRecord;
import com.example.greatbook.middle.viewmodel.DiscoveryRecordRemarkVM;
import com.example.greatbook.utils.DateUtils;
import com.example.greatbook.utils.GlideUtils;
import com.example.greatbook.widght.DefaultNavigationBar;

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
        remarkVM=new DiscoveryRecordRemarkVM();
        discoveryRecord= (DiscoveryRecord) getIntent().getSerializableExtra(Constants.DISCOVERY_RECORD_ITEM_CLICK);
        if (discoveryRecord!=null){
            remarkVM.belongId.set(discoveryRecord.belongId);
            remarkVM.time.set(DateUtils.getDateChinese(discoveryRecord.time));
            remarkVM.content.set(discoveryRecord.content);
            remarkVM.groupId.set(discoveryRecord.groupId);
            remarkVM.likeNum.set(discoveryRecord.likeNum+"");
            remarkVM.title.set(discoveryRecord.title);
            remarkVM.initAllMes(discoveryRecord.belongId);
            remarkVM.initRemarks(discoveryRecord.belongId);
            dataBinding.setRemarkVM(remarkVM);
        }

    }
}
