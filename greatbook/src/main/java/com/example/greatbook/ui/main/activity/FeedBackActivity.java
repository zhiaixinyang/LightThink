package com.example.greatbook.ui.main.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.TextView;

import com.example.greatbook.R;
import com.example.greatbook.base.BaseActivity;
import com.example.greatbook.base.adapter.CommonAdapter;
import com.example.greatbook.base.adapter.ViewHolder;
import com.example.greatbook.ui.main.presenter.FeedBackPresenter;
import com.example.greatbook.ui.main.presenter.contract.FeedBackContract;
import com.example.greatbook.ui.model.LFeedBackBean;
import com.example.greatbook.utils.ToastUtil;
import com.example.greatbook.utils.WaitNetPopupWindowUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by quanzizhangben on 2017/8/10.
 */

public class FeedBackActivity extends BaseActivity<FeedBackPresenter> implements FeedBackContract.View {
    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.btn_send)
    TextView btnSend;
    @BindView(R.id.rlv_feedback)
    RecyclerView rlvFeedback;
    private WaitNetPopupWindowUtils waitNetPopupWindowUtils;
    private CommonAdapter<LFeedBackBean> adapter;
    private List<LFeedBackBean> data;

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
        waitNetPopupWindowUtils=new WaitNetPopupWindowUtils();
        adapter=new CommonAdapter<LFeedBackBean>(this,R.layout.item_feedback,data) {
            @Override
            public void convert(ViewHolder holder, LFeedBackBean lFeedBackBean) {

            }
        };
    }


    @OnClick(R.id.btn_send)
    public void onViewClicked() {

    }

    @Override
    public void intiFeeBackContent(List<LFeedBackBean> data) {

    }

    @Override
    public void showLoading() {
        waitNetPopupWindowUtils.showWaitNetPopupWindow(this);
    }

    @Override
    public void showLoaded() {
        waitNetPopupWindowUtils.hideWaitNetPopupWindow(this);
    }
}
