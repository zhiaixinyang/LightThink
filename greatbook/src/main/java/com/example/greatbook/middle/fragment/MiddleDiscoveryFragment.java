package com.example.greatbook.middle.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.greatbook.App;
import com.example.greatbook.R;
import com.example.greatbook.base.BaseLazyFragment;
import com.example.greatbook.middle.adapter.MiddleDiscoveryAdapter;
import com.example.greatbook.middle.presenter.MiddleDiscoveryPresenter;
import com.example.greatbook.middle.presenter.contract.MiddleDiscoveryContract;
import com.example.greatbook.model.DiscoveryTopGroup;
import com.example.greatbook.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by MDove on 2017/8/11.
 */

public class MiddleDiscoveryFragment extends BaseLazyFragment<MiddleDiscoveryPresenter> implements MiddleDiscoveryContract.View {
    @BindView(R.id.tv_discovery_title)
    TextView tvDiscoveryTitle;
    @BindView(R.id.rlv_discovery_top)
    RecyclerView rlvDiscoveryTop;
    private List<DiscoveryTopGroup> data;
    private MiddleDiscoveryAdapter adapter;
    private Context context;
    private MiddleDiscoveryPresenter presenter;

    public static MiddleDiscoveryFragment newInstance() {
        Bundle args = new Bundle();

        MiddleDiscoveryFragment fragment = new MiddleDiscoveryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.frag_middle_discovery;
    }

    @Override
    protected void initViewsAndEvents(View view) {
        presenter=new MiddleDiscoveryPresenter(this);
        data=new ArrayList<>();
        context = App.getInstance().getContext();
        adapter=new MiddleDiscoveryAdapter(context,R.layout.item_rlv_discovery_top,data);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rlvDiscoveryTop.setLayoutManager(linearLayoutManager);
        rlvDiscoveryTop.setAdapter(adapter);
    }

    @Override
    protected void onFirstUserVisible() {
        presenter.initDiscoveryTop();
    }

    @Override
    protected void onUserVisible() {
        presenter.initDiscoveryTop();
    }

    @Override
    protected void onUserInvisible() {

    }

    @Override
    public void initDiscoveryTopError(String error) {
        ToastUtil.toastShort(error);
    }

    @Override
    public void initDiscoveryTopSuc(List<DiscoveryTopGroup> topGroups) {
        adapter.addData(topGroups);
    }

    @Override
    public void showError(String msg) {
        ToastUtil.toastShort(msg);
    }

}
