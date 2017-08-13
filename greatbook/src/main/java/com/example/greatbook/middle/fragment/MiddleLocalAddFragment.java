package com.example.greatbook.middle.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.greatbook.App;
import com.example.greatbook.R;
import com.example.greatbook.base.BaseLazyFragment;
import com.example.greatbook.base.adapter.CommonAdapter;
import com.example.greatbook.base.adapter.ViewHolder;
import com.example.greatbook.constants.Constants;
import com.example.greatbook.middle.activity.AllLocalRecordActivity;
import com.example.greatbook.middle.model.SetGroupEvent;
import com.example.greatbook.middle.presenter.MiddleLocalAddPresenter;
import com.example.greatbook.middle.presenter.contract.MiddleLocalAddContract;
import com.example.greatbook.model.LocalRecordRLV;
import com.example.greatbook.model.event.LocalAddEvent;
import com.example.greatbook.utils.DateUtils;
import com.example.greatbook.utils.DpUtils;
import com.example.greatbook.utils.SelectorFactory;
import com.example.greatbook.utils.StringUtils;
import com.example.greatbook.utils.ToastUtil;
import com.example.greatbook.widght.refresh.DefaultRefreshCreator;
import com.example.greatbook.widght.refresh.LoadRefreshRecyclerView;
import com.example.greatbook.widght.refresh.RefreshRecyclerView;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by MDove on 2017/8/11.
 */

public class MiddleLocalAddFragment extends BaseLazyFragment<MiddleLocalAddPresenter> implements MiddleLocalAddContract.View,
        RefreshRecyclerView.OnRefreshListener{
    private final String TAG="MiddleLocalAddFragment";
    @BindView(R.id.menu_encourage)
    LinearLayout menuEncourage;
    @BindView(R.id.menu_jok)
    LinearLayout menuJok;
    @BindView(R.id.menu_short_essay)
    LinearLayout menuShortEssay;
    @BindView(R.id.menu_all)
    LinearLayout menuAll;
    @BindView(R.id.rlv_local)
    LoadRefreshRecyclerView rlvLocal;
    @BindView(R.id.tv_empty_view)
    RelativeLayout emptyView;
    @BindView(R.id.tv_load_view) TextView loadingView;
    private CommonAdapter<LocalRecordRLV> adapter;
    private Context context;
    private List<LocalRecordRLV> data;
    private MiddleLocalAddPresenter presenter;

    public static MiddleLocalAddFragment newInstance() {
        Bundle args = new Bundle();

        MiddleLocalAddFragment fragment = new MiddleLocalAddFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.frag_middle_add_local;
    }

    @Override
    protected void initViewsAndEvents(View view) {
        EventBus.getDefault().register(this);

        context=App.getInstance().getContext();
        data=new ArrayList<>();
        presenter=new MiddleLocalAddPresenter(this);
        presenter.initLocalRecord();

        adapter=new CommonAdapter<LocalRecordRLV>(context,R.layout.item_middle_local_add,data) {
            @Override
            public void convert(ViewHolder holder, LocalRecordRLV localRecord) {
                String content=localRecord.content;
                String title=localRecord.title;
                //title可能为空但content不可能为空
                holder.setText(R.id.tv_title,!StringUtils.isEmpty(title)?title:"无标题");
                holder.setText(R.id.tv_content,content);
                holder.setText(R.id.tv_time, DateUtils.getDateChinese(localRecord.time));
                holder.setText(R.id.tv_group_title,localRecord.groupTitle);
                holder.getView(R.id.tv_group_title).setBackground(SelectorFactory.newShapeSelector()
                        .setDefaultBgColor(ContextCompat.getColor(context,R.color.blue))
                        .setCornerRadius(DpUtils.dp2px(4))
                        .setStrokeWidth(DpUtils.dp2px(1))
                        .create());
            }
        };
        rlvLocal.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL, false));
        rlvLocal.addRefreshViewCreator(new DefaultRefreshCreator());
        rlvLocal.addLoadingView(loadingView);
        rlvLocal.addEmptyView(emptyView);
        rlvLocal.setOnRefreshListener(this);
        rlvLocal.setAdapter(adapter);
    }

    @Override
    protected void onFirstUserVisible() {
        presenter.initLocalRecord();
    }

    @Override
    protected void onUserVisible() {
        presenter.initLocalRecord();
    }

    @Override
    protected void onUserInvisible() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    @OnClick({R.id.menu_encourage, R.id.menu_jok, R.id.menu_short_essay,R.id.menu_all})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.menu_encourage:
                break;
            case R.id.menu_jok:
                break;
            case R.id.menu_short_essay:
                break;
            case R.id.menu_all:
                Intent toAll =new Intent(context, AllLocalRecordActivity.class);
                startActivity(toAll);
                break;
        }
    }

    @Override
    public void onLoadRlvRefresh() {
        presenter.initLocalRecord();
    }

    @Override
    public void initLocalRecordError(String error) {
        ToastUtil.toastShort(error);
        data=new ArrayList<>();
        adapter.addData(data);
    }

    @Override
    public void initLocalRecordSuc(List<LocalRecordRLV> records) {
        data=records;
        adapter.addData(data);
        rlvLocal.onStopRefresh();
        loadingView.setVisibility(View.GONE);
        emptyView.setVisibility(View.GONE);
    }

    @Override
    public void showError(String msg) {
        ToastUtil.toastShort(msg);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(LocalAddEvent event){
        switch (event.event){
            case Constants.LOCAL_ADD_SUC_FINISH:
                presenter.initLocalRecord();
                break;
            case Constants.LOCAL_SET_RECORD_SUC_FINISH:
                presenter.initLocalRecord();
                break;
        }
    }
}