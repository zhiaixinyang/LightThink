package com.example.greatbook.local.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.greatbook.R;
import com.example.greatbook.base.dialog.BaseAlertDialog;
import com.example.greatbook.databinding.FragMiddleLocalRecordSmallBinding;
import com.example.greatbook.local.activity.AllLocalRecordActivity;
import com.example.greatbook.local.adapter.AllCommonAdapter;
import com.example.greatbook.local.model.LocalRecordRLV;
import com.example.greatbook.local.presenter.LocalRecordSmallPresenter;
import com.example.greatbook.local.presenter.contract.LocalRecordSmallContract;
import com.example.greatbook.utils.DpUtils;
import com.example.greatbook.utils.StringUtils;
import com.example.greatbook.utils.ToastUtil;
import com.example.greatbook.widght.itemswip.OnSwipeListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MDove on 2017/10/2.
 */

public class LocalRecordSmallFragment extends Fragment implements LocalRecordSmallContract.View,SwipeRefreshLayout.OnRefreshListener {
    private LocalRecordSmallPresenter mPresenter;
    private FragMiddleLocalRecordSmallBinding mBinding;
    private List<LocalRecordRLV> mData;
    private AllCommonAdapter mAdapter;
    protected BaseAlertDialog mDialog;


    public static LocalRecordSmallFragment newInstance() {

        Bundle args = new Bundle();

        LocalRecordSmallFragment fragment = new LocalRecordSmallFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(LayoutInflater.from(getContext()),
                R.layout.frag_middle_local_record_small, container, false);
        initEvent();
        return mBinding.getRoot();
    }

    private void initEvent() {
        mPresenter=new LocalRecordSmallPresenter();
        mPresenter.attachView(this);
        mBinding.srlLocalRecordSmall.setRefreshing(true);
        mPresenter.initLocalRecords();
        mBinding.srlLocalRecordSmall.setOnRefreshListener(this);

        mData = new ArrayList<>();
        mAdapter = new AllCommonAdapter(getContext(), R.layout.item_set_all_local_record, mData);
        mAdapter.setOnSwipeListener(new OnSwipeListener() {
            @Override
            public void onDelete(int pos) {
                mPresenter.deleteLocalRecord(mData.get(pos));
                mAdapter.getSwipeItem().quickClose();
                mAdapter.notifyItemRemoved(pos);
            }

            @Override
            public void onTop(int pos) {

            }

            @Override
            public void onAlter(final int pos) {
                mDialog =new BaseAlertDialog.Builder(getActivity())
                        .setContentView(R.layout.dialog_set_local_record_or_group)
                        .setWidthAndHeight(DpUtils.dp2px(250),DpUtils.dp2px(300))
                        .create();
                mDialog.show();
                final EditText etName= mDialog.getView(R.id.et_record_or_group_name);
                final EditText etContent= mDialog.getView(R.id.et_record_or_group_content);
                String name= StringUtils.isEmpty(mData.get(pos).title)
                        ?"未设置":mData.get(pos).title;
                String content=StringUtils.isEmpty(mData.get(pos).content)
                        ?"未设置":mData.get(pos).content;
                etContent.setText(content);
                etName.setText(name);
                mDialog.setOnClickListener(R.id.btn_cancel, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDialog.dismiss();
                    }
                });
                mDialog.setOnClickListener(R.id.btn_set_ok, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String title=etName.getText().toString();
                        String content=etContent.getText().toString();
                        mPresenter.updateLocalRecord(mData.get(pos),title,content);
                    }
                });
            }
        });
        mBinding.rlvSetAllLocalRecord.setLayoutManager(new LinearLayoutManager(getContext()));
        mBinding.rlvSetAllLocalRecord.setAdapter(mAdapter);

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    @Override
    public void showError(String msg) {
        ToastUtil.toastShort(msg);
    }

    @Override
    public void initLocalRecords(List<LocalRecordRLV> records) {
        mBinding.srlLocalRecordSmall.setRefreshing(false);
        mData=records;
        if (records.isEmpty()){
            mBinding.includeEmptyLoading.ivEmpty.setVisibility(View.VISIBLE);
        }else {
            mBinding.includeEmptyLoading.ivEmpty.setVisibility(View.GONE);
            mAdapter.addData(records);
        }
    }

    @Override
    public void deleteLocalGroupReturn(String returnStr) {
        ToastUtil.toastShort(returnStr);
        mBinding.srlLocalRecordSmall.setRefreshing(false);
    }

    @Override
    public void deleteLocalGroupToNetReturn(String returnStr) {
        ToastUtil.toastShort(returnStr);
        mBinding.srlLocalRecordSmall.setRefreshing(false);
    }

    @Override
    public void updateLocalRecordReturn(String returnStr) {
        mBinding.srlLocalRecordSmall.setRefreshing(false);
        ToastUtil.toastShort(returnStr);
        mDialog.dismiss();
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void updateLocalRecordToNetReturn(String returnStr) {
        ToastUtil.toastShort(returnStr);
        mBinding.srlLocalRecordSmall.setRefreshing(false);
    }

    @Override
    public void onRefresh() {
        if (mPresenter!=null){
            mPresenter.initLocalRecords();
        }
    }
}
