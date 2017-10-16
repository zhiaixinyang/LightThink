package com.example.greatbook.nethot.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.greatbook.databinding.FragGroupRecordsBinding;
import com.example.greatbook.nethot.adapter.GroupRecordsAdapter;
import com.example.greatbook.nethot.model.DiscoveryTopGroup;
import com.example.greatbook.nethot.viewmodel.GroupAndRecordsVM;

/**
 * Created by MDove on 2017/10/15.
 */

public class GroupAndRecordsFragment extends Fragment {
    private FragGroupRecordsBinding mBinding;
    private GroupRecordsAdapter mAdapter;
    private GroupAndRecordsVM mGroupAndRecordsVM;
    private static final String BUNDLE_KEY = "GroupAndRecordsFragment";
    private boolean mFirstShow = true;

    public static GroupAndRecordsFragment newInstance(DiscoveryTopGroup discoveryTopGroup) {
        Bundle args = new Bundle();

        GroupAndRecordsFragment fragment = new GroupAndRecordsFragment();
        args.putSerializable(BUNDLE_KEY, discoveryTopGroup);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = FragGroupRecordsBinding.inflate(inflater);

        mAdapter = new GroupRecordsAdapter(getContext());
        mBinding.rlvGroupRecords.setLayoutManager(new LinearLayoutManager(getContext()));
        mBinding.rlvGroupRecords.setAdapter(mAdapter);

        DiscoveryTopGroup discoveryTopGroup = (DiscoveryTopGroup) getArguments().getSerializable(BUNDLE_KEY);
        if (discoveryTopGroup != null) {
            mGroupAndRecordsVM = new GroupAndRecordsVM(discoveryTopGroup);
            mBinding.setGroupAndRecordVM(mGroupAndRecordsVM);
            if (mFirstShow) {
                mGroupAndRecordsVM.initRecords();
                mFirstShow = false;
            }
        }
        return mBinding.getRoot();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (mGroupAndRecordsVM != null && mAdapter.getItemCount() == 0) {
            mGroupAndRecordsVM.initRecords();
        }
    }
}
