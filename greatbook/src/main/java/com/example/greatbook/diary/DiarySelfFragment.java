package com.example.greatbook.diary;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.greatbook.R;
import com.example.greatbook.databinding.FragDiaryBinding;
import com.example.greatbook.diary.adapter.DiarySelfAdapter;
import com.example.greatbook.diary.presenter.DiarySelfPresenter;
import com.example.greatbook.diary.presenter.contract.DiarySelfContract;
import com.example.greatbook.greendao.entity.DiarySelf;
import com.example.greatbook.utils.StringUtils;
import com.example.greatbook.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MDove on 17/9/27.
 */

public class DiarySelfFragment extends Fragment implements DiarySelfContract.View {
    private DiarySelfAdapter mAdapter;
    private List<DiarySelf> mData;
    private FragDiaryBinding mBinding;
    private DiarySelfPresenter mPresenter;

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
        mData = new ArrayList<>();
        mAdapter = new DiarySelfAdapter(getContext(), R.layout.item_diary, mData);
        mBinding.rlvDiary.setAdapter(mAdapter);
        mBinding.rlvDiary.setLayoutManager(new LinearLayoutManager(getContext()));

        mPresenter=new DiarySelfPresenter();
        mPresenter.attachView(this);
        mPresenter.initDiartSelf();

        mBinding.btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = mBinding.etContent.getText().toString();
                if (!StringUtils.isEmpty(content)){
                    mPresenter.addDiarySelf(content);
                }else{
                    ToastUtil.toastShort(getResources().getString(R.string.chat_self_send_empty));
                }
            }
        });
    }

    @Override
    public void showDiarySelf(List<DiarySelf> data) {
        mAdapter.addData(data);
    }

    @Override
    public void diarySelfEmpty() {
        ToastUtil.toastShort(getResources().getString(R.string.chat_self_show_empty));
    }

    @Override
    public void addDiarySelfSuc() {
        mBinding.etContent.setText("");
        ToastUtil.toastShort(getResources().getString(R.string.chat_self_add_suc));
    }
}
