package com.example.greatbook.diary.fragment;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
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
import com.example.greatbook.databinding.FragDiaryBinding;
import com.example.greatbook.diary.adapter.DiarySelfFragAdapter;
import com.example.greatbook.diary.presenter.DiarySelfFragPresenter;
import com.example.greatbook.diary.presenter.contract.DiarySelfFragContract;
import com.example.greatbook.greendao.entity.DiarySelf;
import com.example.greatbook.utils.DpUtils;
import com.example.greatbook.utils.SelectorFactory;
import com.example.greatbook.utils.StringUtils;
import com.example.greatbook.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MDove on 17/9/27.
 */

public class DiarySelfFragment extends Fragment implements DiarySelfFragContract.View,
        SwipeRefreshLayout.OnRefreshListener {
    private DiarySelfFragAdapter mAdapter;
    private List<DiarySelf> mData;
    private FragDiaryBinding mBinding;
    private DiarySelfFragPresenter mPresenter;

    public static DiarySelfFragment newInstance() {

        Bundle args = new Bundle();

        DiarySelfFragment fragment = new DiarySelfFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.frag_diary, container, false);
        initEvent();
        return mBinding.getRoot();
    }

    @Override
    public void showError(String msg) {
        ToastUtil.toastShort(msg);
    }

    public void initEvent() {
        initViewBackGround();

        mData = new ArrayList<>();
        mAdapter = new DiarySelfFragAdapter(getContext(), R.layout.item_frag_diary_self, mData);
        mBinding.srlDiary.setOnRefreshListener(this);

        mBinding.rlvDiary.setAdapter(mAdapter);
        mBinding.rlvDiary.setLayoutManager(new LinearLayoutManager(getContext()));

//        StickyDecoration decoration = StickyDecoration.Builder
//                .init(new GroupListener() {
//                    @Override
//                    public String getGroupName(int position) {
//                        if (mData.size() > position) {
//                            LogUtils.d("" + DateUtils.getDateChineseYMD(mData.get(position).time));
//                            return DateUtils.getDateChineseYMD(mData.get(position).time);
//                        }
//                        return null;
//                    }
//                })
//                .setGroupBackground(Color.parseColor("#48BDFF"))
//                .setGroupHeight(DpUtils.dp2px(35))
//                .setGroupTextColor(Color.WHITE)
//                .setGroupTextSize(DpUtils.dp2px(15))
//                .setTextLeftMargin(DpUtils.dp2px(10))
//                .build();
//
//        mBinding.rlvDiary.addItemDecoration(decoration);

        mPresenter = new DiarySelfFragPresenter();
        mPresenter.attachView(this);
        mBinding.srlDiary.setRefreshing(true);
        mPresenter.initDiartSelf();

        mBinding.btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = mBinding.etContent.getText().toString();
                if (!StringUtils.isEmpty(content)) {
                    mPresenter.addDiarySelf(content);
                } else {
                    ToastUtil.toastShort(getResources().getString(R.string.chat_self_send_empty));
                }
            }
        });
    }

    @Override
    public void showDiarySelf(List<DiarySelf> data) {
        mBinding.srlDiary.setRefreshing(false);

        mBinding.includeEmptyLoading.layoutEmptyView.setVisibility(View.GONE);

        mData = data;
        mAdapter.addData(data);
    }

    @Override
    public void diarySelfEmpty() {
        mBinding.srlDiary.setRefreshing(false);

        mBinding.includeEmptyLoading.layoutEmptyView.setVisibility(View.VISIBLE);
    }

    private void initViewBackGround() {
        mBinding.btnSend.setBackground(SelectorFactory.newShapeSelector()
                .setCornerRadius(DpUtils.dp2px(4))
                .setDefaultBgColor(ContextCompat.getColor(getContext(), R.color.blue))
                .setFocusedBgColor(ContextCompat.getColor(getContext(), R.color.blue))
                .create());
        mBinding.etContent.setBackground(SelectorFactory.newShapeSelector()
                .setCornerRadius(DpUtils.dp2px(4))
                .setDefaultBgColor(Color.WHITE)
                .create());
    }

    @Override
    public void addDiarySelfSuc() {
        mBinding.srlDiary.setRefreshing(false);

        mBinding.etContent.setText("");
        mPresenter.initDiartSelf();
        ToastUtil.toastShort(getResources().getString(R.string.chat_self_add_suc));
    }

    @Override
    public void addDiarySelfToNetErr(String netErr) {
        ToastUtil.toastShort(netErr);
    }

    @Override
    public void onRefresh() {
        if (mPresenter != null) {
            mPresenter.initDiartSelf();
            //重新将未传至服务器的数据
            mPresenter.retrySavaNet();
        }
    }
}
