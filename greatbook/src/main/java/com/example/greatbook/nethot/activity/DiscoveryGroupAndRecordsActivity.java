package com.example.greatbook.nethot.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.greatbook.R;
import com.example.greatbook.constants.Constants;
import com.example.greatbook.databinding.ActivityDiscoveryGroupBinding;
import com.example.greatbook.nethot.fragment.GroupAndRecordsFragment;
import com.example.greatbook.nethot.fragment.GroupAndRemarksFragment;
import com.example.greatbook.nethot.model.DiscoveryTopGroup;
import com.example.greatbook.nethot.presenter.GroupAndRecordsPresenter;
import com.example.greatbook.nethot.presenter.contract.GroupAndRecordsContract;
import com.example.greatbook.nethot.viewmodel.DiscoveryGroupAndRecordsVM;
import com.example.greatbook.utils.ToastUtil;
import com.example.greatbook.widght.DefaultNavigationBar;

/**
 * Created by MDove on 2017/8/24.
 */

public class DiscoveryGroupAndRecordsActivity extends AppCompatActivity implements GroupAndRecordsContract.View {
    private ActivityDiscoveryGroupBinding mBinding;
    private DiscoveryGroupAndRecordsVM mDiscoveryGroupAndRecordsVM;
    private DiscoveryTopGroup mDiscoveryTopGroup;
    private String[] mTitles = new String[]{"文集内容", "评论"};

    private GroupAndRecordsFragment mGroupAndRecordsFragment;
    private GroupAndRemarksFragment mGroupAndRemarksFragment;
    private GroupAndRecordsPresenter mPresenter;

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
                .setTitleText("热门文集").builder();

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_discovery_group);

        mDiscoveryTopGroup = (DiscoveryTopGroup) getIntent().getSerializableExtra(Constants.DISCOVERY_GROUP_ITEM_CLICK);

        if (mDiscoveryTopGroup != null) {
            mGroupAndRemarksFragment = GroupAndRemarksFragment.newInstance(mDiscoveryTopGroup);
            mGroupAndRecordsFragment = GroupAndRecordsFragment.newInstance(mDiscoveryTopGroup);
        } else {
            finish();
        }

        mPresenter = new GroupAndRecordsPresenter();
        mDiscoveryGroupAndRecordsVM = new DiscoveryGroupAndRecordsVM(mDiscoveryTopGroup, mPresenter);
        mBinding.setGroupVM(mDiscoveryGroupAndRecordsVM);
        mDiscoveryGroupAndRecordsVM.initGroup();

        mBinding.viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                if (position == 0) {
                    return mGroupAndRecordsFragment;
                }
                return mGroupAndRemarksFragment;
            }

            @Override
            public int getCount() {
                return mTitles.length;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return mTitles[position];
            }
        });
        mBinding.tabLayout.setupWithViewPager(mBinding.viewPager);

        mBinding.btnFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDiscoveryGroupAndRecordsVM != null) {
                    mDiscoveryGroupAndRecordsVM.follower();
                }
            }
        });
    }

    @Override
    public void showError(String msg) {
        ToastUtil.toastShort(msg);
    }

    @Override
    public void followerSuc(String suc) {
        ToastUtil.toastShort(suc);
    }

    @Override
    public void followerErr(String err) {
        ToastUtil.toastShort(err);
    }

    @Override
    public void followerLoading() {

    }
}
