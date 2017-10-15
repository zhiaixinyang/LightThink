package com.example.greatbook.nethot.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.greatbook.R;
import com.example.greatbook.databinding.FragGroupRemarksBinding;
import com.example.greatbook.nethot.adapter.GroupRemarksAdapter;
import com.example.greatbook.nethot.model.DiscoveryTopGroup;
import com.example.greatbook.nethot.viewmodel.GroupAndRemarksVM;
import com.example.greatbook.utils.DpUtils;
import com.example.greatbook.utils.SelectorFactory;

/**
 * Created by MDove on 2017/10/15.
 */

public class GroupAndRemarksFragment extends Fragment {
    private FragGroupRemarksBinding mBinding;
    private GroupAndRemarksVM mGroupAndRemarksVM;
    private DiscoveryTopGroup mDiscoveryTopGroup;
    private GroupRemarksAdapter mAdapter;
    private boolean mFirstShow = true;

    private static final String BUNDLE_KEY = "GroupAndRemarksFragment";

    public static GroupAndRemarksFragment newInstance(DiscoveryTopGroup discoveryTopGroup) {

        Bundle args = new Bundle();
        args.putSerializable(BUNDLE_KEY, discoveryTopGroup);
        GroupAndRemarksFragment fragment = new GroupAndRemarksFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = FragGroupRemarksBinding.inflate(inflater);

        initViewBackGround();

        mDiscoveryTopGroup = (DiscoveryTopGroup) getArguments().getSerializable(BUNDLE_KEY);
        if (mDiscoveryTopGroup != null) {
            mGroupAndRemarksVM = new GroupAndRemarksVM(mDiscoveryTopGroup);
            mBinding.setRemarkVM(mGroupAndRemarksVM);
            if (mFirstShow) {
                mGroupAndRemarksVM.initRemarks();
                mFirstShow = false;
            }
        }
        return mBinding.getRoot();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (mGroupAndRemarksVM != null) {
            mGroupAndRemarksVM.initRemarks();
        }
    }

    private void initViewBackGround() {
        mAdapter = new GroupRemarksAdapter(getContext());
        mBinding.rlvGroupRemarks.setLayoutManager(new LinearLayoutManager(getContext()));
        mBinding.rlvGroupRemarks.setAdapter(mAdapter);

        mBinding.btnSend.setBackground(SelectorFactory.newShapeSelector()
                .setCornerRadius(DpUtils.dp2px(4))
                .setDefaultBgColor(ContextCompat.getColor(getContext(), R.color.blue_light))
                .setFocusedBgColor(ContextCompat.getColor(getContext(), R.color.blue_light))
                .create());
        mBinding.etContent.setBackground(SelectorFactory.newShapeSelector()
                .setCornerRadius(DpUtils.dp2px(4))
                .setDefaultBgColor(Color.WHITE)
                .create());
    }
}
