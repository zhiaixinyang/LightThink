package com.example.greatbook.nethot.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.example.greatbook.R;
import com.example.greatbook.constants.Constants;
import com.example.greatbook.databinding.ActivityDiscoveryGroupBinding;
import com.example.greatbook.nethot.model.DiscoveryTopGroup;
import com.example.greatbook.local.viewmodel.DiscoveryGroupAndRecordsVM;
import com.example.greatbook.widght.DefaultNavigationBar;

/**
 * Created by MDove on 2017/8/24.
 */

public class DiscoveryGroupAndRecordsActivity extends AppCompatActivity {
    private ActivityDiscoveryGroupBinding binding;
    private DiscoveryGroupAndRecordsVM discoveryGroupAndRecordsVM;
    private DiscoveryTopGroup discoveryTopGroup;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new DefaultNavigationBar.Builder(this,null)
                .setLeftResId(R.drawable.btn_back_)
                .setOnLeftClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                })
                .setTitleText("热门文集").builder();
        binding = DataBindingUtil.setContentView(this, R.layout.activity_discovery_group);
        binding.rlvActivityDiscovery.setLayoutManager(new LinearLayoutManager(this));

        discoveryTopGroup = (DiscoveryTopGroup) getIntent().getSerializableExtra(Constants.DISCOVERY_GROUP_ITEM_CLICK);

        discoveryGroupAndRecordsVM = new DiscoveryGroupAndRecordsVM(discoveryTopGroup);
        binding.setGroupVM(discoveryGroupAndRecordsVM);
        discoveryGroupAndRecordsVM.initGroup();
        discoveryGroupAndRecordsVM.initRecords();

    }
}
