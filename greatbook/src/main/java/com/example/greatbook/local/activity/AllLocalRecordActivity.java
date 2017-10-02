package com.example.greatbook.local.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.greatbook.R;
import com.example.greatbook.databinding.ActivityAllLocalRecordBinding;
import com.example.greatbook.local.fragment.LocalRecordBigFragment;
import com.example.greatbook.local.fragment.LocalRecordSmallFragment;
import com.example.greatbook.widght.DefaultNavigationBar;

/**
 * Created by MDove on 2017/8/13.
 */

public class AllLocalRecordActivity extends AppCompatActivity {
    private LocalRecordBigFragment mBigFragment;
    private LocalRecordSmallFragment mSmallFragment;
    private ActivityAllLocalRecordBinding mBinding;

    private String[] fragTitles = {"段子详情", "段子简介"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_all_local_record);
        init();
    }

    public void init() {
        new DefaultNavigationBar.Builder(this,null)
                .setLeftResId(R.drawable.btn_back_)
                .setOnLeftClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                })
                .setTitleText("我的全部短句")
                .builder();


        mSmallFragment = LocalRecordSmallFragment.newInstance();
        mBigFragment = LocalRecordBigFragment.newInstance();

        mBinding.viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                if (position == 0) {
                    return mBigFragment;
                }
                return mSmallFragment;
            }

            @Override
            public int getCount() {
                return fragTitles.length;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return fragTitles[position];
            }
        });
        mBinding.tabLayout.setupWithViewPager(mBinding.viewPager);
    }
}
