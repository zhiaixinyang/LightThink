package com.example.greatbook.local.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.avos.avoscloud.AVUser;
import com.example.greatbook.R;
import com.example.greatbook.base.adapter.OnItemClickListener;
import com.example.greatbook.databinding.ActivityEssayListBinding;
import com.example.greatbook.greendao.entity.Essay;
import com.example.greatbook.local.adapter.EssayListAdapter;
import com.example.greatbook.local.model.EssayListItem;
import com.example.greatbook.local.presenter.EssayListPresenter;
import com.example.greatbook.local.presenter.contract.EssayListContract;
import com.example.greatbook.model.leancloud.User;
import com.example.greatbook.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MDove on 17/9/18.
 */

public class EssayListActivity extends AppCompatActivity implements EssayListContract.View {
    private ActivityEssayListBinding binding;
    private EssayListPresenter mPresenter;
    private List<EssayListItem> mData;
    private EssayListAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_essay_list);
        mPresenter = new EssayListPresenter();
        mPresenter.attachView(this);

        mData = new ArrayList<>();
        mAdapter = new EssayListAdapter(this, R.layout.item_essay_list, mData);
        binding.rlvEssay.setLayoutManager(new LinearLayoutManager(this));
        binding.rlvEssay.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new OnItemClickListener<EssayListItem>() {
            @Override
            public void onItemClick(View view, EssayListItem item, int position) {
                PrefectEssayActivity.startPrefectEssay(EssayListActivity.this, item.mEssay.id);
            }
        });

        binding.btnAddEssay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User currentUser = AVUser.getCurrentUser(User.class);
                if (currentUser != null) {
                    mPresenter.addEssay(currentUser);
                }
            }
        });

        mPresenter.initEssayList();
    }

    @Override
    public void showError(String msg) {
        ToastUtil.toastShort(msg);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        mPresenter.initEssayList();
    }

    @Override
    public void initEssayListSuc(List<EssayListItem> data) {
        mAdapter.addData(data);
    }

    @Override
    public void initEssayListEmpty() {
        ToastUtil.toastShort("您还没有写过文章吧？");
    }

    @Override
    public void addEssaySuc(long essayId) {
        PrefectEssayActivity.startPrefectEssay(this, essayId);
    }
}
