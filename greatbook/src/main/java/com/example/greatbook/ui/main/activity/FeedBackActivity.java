package com.example.greatbook.ui.main.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.avos.avoscloud.AVUser;
import com.example.greatbook.R;
import com.example.greatbook.base.BaseActivity;
import com.example.greatbook.ui.main.adapter.FeedBackAdapter;
import com.example.greatbook.ui.main.presenter.FeedBackPresenter;
import com.example.greatbook.ui.main.presenter.contract.FeedBackContract;
import com.example.greatbook.ui.model.LFeedBackBean;
import com.example.greatbook.utils.DpUtils;
import com.example.greatbook.utils.SelectorFactory;
import com.example.greatbook.utils.SnackbarUtils;
import com.example.greatbook.utils.StringUtils;
import com.example.greatbook.utils.ToastUtil;
import com.example.greatbook.widght.DefaultNavigationBar;
import com.example.greatbook.widght.refresh.DefaultLoadCreator;
import com.example.greatbook.widght.refresh.DefaultRefreshCreator;
import com.example.greatbook.widght.refresh.LoadRefreshRecyclerView;
import com.example.greatbook.widght.refresh.RefreshRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by quanzizhangben on 2017/8/10.
 */

public class FeedBackActivity extends BaseActivity<FeedBackPresenter> implements FeedBackContract.View, RefreshRecyclerView.OnRefreshListener, LoadRefreshRecyclerView.OnLoadMoreListener {
    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.btn_send)
    TextView btnSend;
    @BindView(R.id.rlv_feedback)
    LoadRefreshRecyclerView rlvFeedback;
    @BindView(R.id.tv_load_view)
    TextView tvLoadView;
    @BindView(R.id.tv_empty_view)
    TextView tvEmptyView;
    private FeedBackAdapter adapter;
    private List<LFeedBackBean> data;
    private LRecyclerViewAdapter rlvAdapter = null;
    private int currentNum = 0;

    @Override
    public void showError(String msg) {
        ToastUtil.toastLong(msg);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_feedback;
    }

    @Override
    public void init() {
        new DefaultNavigationBar.Builder(this,null)
                .setLeftText("返回")
                .setTitleText("意见反馈区")
                .setOnLeftClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                }).builder();

        presenter=new FeedBackPresenter(this);
        data=new ArrayList<>();
        adapter = new FeedBackAdapter(this,R.layout.item_feedback,data);
        rlvAdapter = new LRecyclerViewAdapter(this, adapter);
        rlvFeedback.setLayoutManager(new LinearLayoutManager(this));
        rlvFeedback.addRefreshViewCreator(new DefaultRefreshCreator());
        rlvFeedback.addLoadViewCreator(new DefaultLoadCreator());
        rlvFeedback.setAdapter(rlvAdapter);
        rlvFeedback.addLoadingView(tvLoadView);
        rlvFeedback.addEmptyView(tvEmptyView);
        rlvFeedback.setOnLoadMoreListener(this);
        rlvFeedback.setOnRefreshListener(this);

        initViewBackGround();

        presenter.getFeedBackContent();
    }

    private void initViewBackGround() {
        btnSend.setBackground(SelectorFactory.newShapeSelector()
            .setCornerRadius(DpUtils.dp2px(4))
            .setDefaultBgColor(ContextCompat.getColor(this,R.color.blue_light))
            .setFocusedBgColor(ContextCompat.getColor(this,R.color.blue_light))
            .create());
        etContent.setBackground(SelectorFactory.newShapeSelector()
            .setCornerRadius(DpUtils.dp2px(4))
            .setDefaultBgColor(Color.WHITE)
            .create());
    }


    @OnClick(R.id.btn_send)
    public void onViewClicked() {
        String content=etContent.getText().toString();
        AVUser avUser=AVUser.getCurrentUser();
        if (avUser!=null) {
            if (content != null && !StringUtils.isEmpty(content)) {
                presenter.sendContent(avUser.getObjectId(),content);
            }else{
                ToastUtil.toastShort("不少反馈点内容");
            }
        }else{
            SnackbarUtils.showShortAndAction(getWindow().getDecorView(),
                    "请登录再进行操作", "登录", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent toLogin=new Intent(FeedBackActivity.this,LoginActivity.class);
                            startActivity(toLogin);
                        }
                    });
        }
    }

    @Override
    public void intiFeeBackContent(List<LFeedBackBean> data) {
        adapter.clear();
        adapter.addData(data);
        currentNum = adapter.getItemCount();
    }

    @Override
    public void getMoreFeedBackContent(List<LFeedBackBean> data) {
        currentNum = currentNum + data.size();
        adapter.addData(data);
    }

    @Override
    public void showLoading() {
    }

    @Override
    public void showLoaded() {
        tvLoadView.setVisibility(View.GONE);

        rlvFeedback.onStopRefresh();
        rlvFeedback.onStopLoad();
    }

    @Override
    public void sendContentSuc(String success) {
        ToastUtil.toastShort(success);
        etContent.setText("");
        presenter.getFeedBackContent();
    }

    @Override
    public void sendContentError(String error) {
        ToastUtil.toastShort(error);
    }

    @Override
    public void onRlvLoadMore() {
        presenter.getMoreFeedBackContent(currentNum);
    }

    @Override
    public void onLoadRlvRefresh() {
        presenter.getFeedBackContent();
    }
}
