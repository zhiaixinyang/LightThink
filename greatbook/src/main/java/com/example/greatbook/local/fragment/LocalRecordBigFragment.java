package com.example.greatbook.local.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.greatbook.R;
import com.example.greatbook.base.adapter.CommonAdapter;
import com.example.greatbook.base.adapter.ViewHolder;
import com.example.greatbook.databinding.FragMiddleLocalRecordBigBinding;
import com.example.greatbook.local.model.LocalRecordRLV;
import com.example.greatbook.local.presenter.LocalRecordBigPresenter;
import com.example.greatbook.local.presenter.contract.LocalRecordBigContract;
import com.example.greatbook.utils.DateUtils;
import com.example.greatbook.utils.DpUtils;
import com.example.greatbook.utils.GlideUtils;
import com.example.greatbook.utils.SelectorFactory;
import com.example.greatbook.utils.StringUtils;
import com.example.greatbook.utils.ToastUtil;
import com.example.greatbook.widght.RoundImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MDove on 2017/10/2.
 */

public class LocalRecordBigFragment extends Fragment implements LocalRecordBigContract.View,
        SwipeRefreshLayout.OnRefreshListener {
    private List<LocalRecordRLV> mData;
    private CommonAdapter<LocalRecordRLV> mAdapter;
    private LocalRecordBigPresenter mPresenter;
    private FragMiddleLocalRecordBigBinding mBinding;

    public static LocalRecordBigFragment newInstance() {

        Bundle args = new Bundle();

        LocalRecordBigFragment fragment = new LocalRecordBigFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(LayoutInflater.from(getContext()),
                R.layout.frag_middle_local_record_big, container, false);
        initEvent();
        return mBinding.getRoot();
    }

    private void initEvent() {

        mPresenter = new LocalRecordBigPresenter();
        mPresenter.attachView(this);

        mData = new ArrayList<>();
        mAdapter = new CommonAdapter<LocalRecordRLV>(getContext(), R.layout.item_middle_local_add, mData) {
            @Override
            public void convert(ViewHolder holder, LocalRecordRLV localRecord) {
                String content = localRecord.content;
                String title = localRecord.title;
                //title可能为空但content不可能为空
                holder.setText(R.id.tv_title, !StringUtils.isEmpty(title) ? title : "无标题");
                holder.setText(R.id.tv_content, content);
                holder.setText(R.id.tv_time, DateUtils.getDateChinese(localRecord.time));
                holder.setText(R.id.tv_group_title, localRecord.groupTitle);
                holder.getView(R.id.tv_group_title).setBackground(SelectorFactory.newShapeSelector()
                        .setDefaultBgColor(ContextCompat.getColor(context, R.color.blue))
                        .setCornerRadius(DpUtils.dp2px(4))
                        .setStrokeWidth(DpUtils.dp2px(1))
                        .create());
                if (StringUtils.isEmpty(localRecord.groupPhotoPath)) {
                    holder.setImageResource(R.id.iv_group, localRecord.groupLocalPhotoPath);
                    holder.getView(R.id.iv_group).setBackgroundColor(Integer.parseInt(localRecord.bgColor));
                } else {
                    GlideUtils.loadSmallIv(localRecord.groupPhotoPath, (RoundImageView) holder.getView(R.id.iv_group));
                }
            }
        };

        mBinding.rlvLocalRecordBig.setLayoutManager(new LinearLayoutManager(getContext()));
        mBinding.rlvLocalRecordBig.setAdapter(mAdapter);

        mBinding.srlLocalRecordBig.setRefreshing(true);
        mPresenter.initLocalRecord();
        mBinding.srlLocalRecordBig.setOnRefreshListener(this);
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
    public void initLocalRecordSuc(List<LocalRecordRLV> records) {
        mBinding.includeEmptyLoading.ivEmpty.setVisibility(View.GONE);
        mBinding.srlLocalRecordBig.setRefreshing(false);
        mAdapter.addData(records);
    }

    @Override
    public void localRecordEmpty() {
        mBinding.includeEmptyLoading.ivEmpty.setVisibility(View.VISIBLE);
        mBinding.srlLocalRecordBig.setRefreshing(false);
    }

    @Override
    public void onRefresh() {
        if (mPresenter != null) {
            mPresenter.initLocalRecord();
        }
    }
}
