package com.example.greatbook.local.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.avos.avoscloud.AVUser;
import com.example.greatbook.R;
import com.example.greatbook.base.adapter.OnItemClickListener;
import com.example.greatbook.databinding.ActivityEssayListBinding;
import com.example.greatbook.local.adapter.EssayListAdapter;
import com.example.greatbook.local.model.EssayListItem;
import com.example.greatbook.local.presenter.EssayListPresenter;
import com.example.greatbook.local.presenter.contract.EssayListContract;
import com.example.greatbook.model.leancloud.User;
import com.example.greatbook.utils.ToastUtil;
import com.example.greatbook.widght.DefaultNavigationBar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MDove on 17/9/18.
 */

public class EssayListActivity extends AppCompatActivity implements EssayListContract.View,
        SwipeRefreshLayout.OnRefreshListener {
    private ActivityEssayListBinding mBinding;
    private EssayListPresenter mPresenter;
    private List<EssayListItem> mData;
    private EssayListAdapter mAdapter;
    private User currentUser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentUser = AVUser.getCurrentUser(User.class);
        if (currentUser == null) {
            finish();
        }

        new DefaultNavigationBar.Builder(this, null)
                .setLeftResId(R.drawable.btn_back_)
                .setOnLeftClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                })
                .setRightResId(R.drawable.btn_plan_add)
                .setOnRightClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mPresenter.addEssay(currentUser);
                    }
                })
                .setTitleText("文章列表")
                .builder();

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_essay_list);
        mPresenter = new EssayListPresenter();
        mPresenter.attachView(this);

        mBinding.srlEssay.setOnRefreshListener(this);

        mData = new ArrayList<>();
        mAdapter = new EssayListAdapter(this, R.layout.item_essay_list, mData);
        mBinding.rlvEssay.setLayoutManager(new LinearLayoutManager(this));
        mBinding.rlvEssay.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new OnItemClickListener<EssayListItem>() {
            @Override
            public void onItemClick(View view, EssayListItem item, int position) {
                PrefectEssayActivity.startPrefectEssay(EssayListActivity.this, item.mEssay.id);
            }
        });

        mBinding.btnAddEssay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (currentUser != null) {
                    mPresenter.addEssay(currentUser);
                }
            }
        });

        mBinding.srlEssay.setRefreshing(true);
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
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }

    @Override
    public void initEssayListSuc(List<EssayListItem> data) {
        mBinding.srlEssay.setRefreshing(false);

        mAdapter.addData(data);
    }

    @Override
    public void initEssayListEmpty() {
        mBinding.srlEssay.setRefreshing(false);

        ToastUtil.toastShort("您还没有写过文章吧？");
    }

    @Override
    public void addEssaySuc(long essayId) {
        PrefectEssayActivity.startPrefectEssay(this, essayId);
    }

    @Override
    public void onRefresh() {
        mBinding.srlEssay.setRefreshing(true);
        if (mPresenter != null) {
            mPresenter.initEssayList();
        }
    }
}
