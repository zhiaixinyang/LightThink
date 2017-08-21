package com.example.greatbook.middle.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ethanhua.skeleton.RecyclerViewSkeletonScreen;
import com.ethanhua.skeleton.Skeleton;
import com.example.greatbook.App;
import com.example.greatbook.R;
import com.example.greatbook.base.BaseLazyFragment;
import com.example.greatbook.base.adapter.OnItemClickListener;
import com.example.greatbook.constants.Constants;
import com.example.greatbook.middle.activity.DiscoveryRecordRemarkActivity;
import com.example.greatbook.middle.adapter.DiscoveryRecordAdapter;
import com.example.greatbook.middle.adapter.MiddleDiscoveryAdapter;
import com.example.greatbook.middle.model.DiscoveryRecord;
import com.example.greatbook.middle.presenter.MiddleDiscoveryPresenter;
import com.example.greatbook.middle.presenter.contract.MiddleDiscoveryContract;
import com.example.greatbook.middle.model.DiscoveryTopGroup;
import com.example.greatbook.model.HeadlineBean;
import com.example.greatbook.middle.activity.TalkAboutActivity;
import com.example.greatbook.utils.LogUtils;
import com.example.greatbook.utils.ToastUtil;
import com.example.greatbook.widght.AdHeadline;
import com.example.greatbook.widght.refresh.DefaultRefreshCreator;
import com.example.greatbook.widght.refresh.LoadRefreshRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by MDove on 2017/8/11.
 */

public class MiddleDiscoveryFragment extends BaseLazyFragment<MiddleDiscoveryPresenter> implements MiddleDiscoveryContract.View,
        SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.tv_discovery_title)
    TextView tvDiscoveryTitle;
    @BindView(R.id.rlv_discovery_top)
    RecyclerView rlvDiscoveryTop;
    @BindView(R.id.rlv_discovery_record)
    RecyclerView rlvDiscoveryRecord;
    @BindView(R.id.talk_about_headline)
    AdHeadline headline;
    @BindView(R.id.srl_top)
    SwipeRefreshLayout srlTop;
    private List<DiscoveryTopGroup> dataGroups;
    private List<DiscoveryRecord> dataRecords;
    private Context context;
    private MiddleDiscoveryPresenter presenter;
    private MiddleDiscoveryAdapter adapterGroup;
    private DiscoveryRecordAdapter adapterRecord;
    private RecyclerViewSkeletonScreen skeletonScreenGroup;
    private RecyclerViewSkeletonScreen skeletonScreenRecord;
    private List<HeadlineBean> talkABoutData;

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
        LogUtils.d("initViewsAndEvents");
        presenter = new MiddleDiscoveryPresenter(this);
        context = App.getInstance().getContext();

        dataGroups=new ArrayList<>();
        adapterGroup = new MiddleDiscoveryAdapter(context, R.layout.item_rlv_discovery_group, dataGroups);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rlvDiscoveryTop.setLayoutManager(linearLayoutManager);
        rlvDiscoveryTop.setAdapter(adapterGroup);
        srlTop.setOnRefreshListener(this);

        dataRecords = new ArrayList<>();
        adapterRecord = new DiscoveryRecordAdapter(context, R.layout.item_rlv_discovery_record, dataRecords);
        adapterRecord.setOnItemClickListener(new OnItemClickListener<DiscoveryRecord>() {
            @Override
            public void onItemClick(View view, DiscoveryRecord o, int position) {
                if (o!=null){
                    Intent toActivity=new Intent(context, DiscoveryRecordRemarkActivity.class);
                    toActivity.putExtra(Constants.DISCOVERY_RECORD_ITEM_CLICK,o);
                    startActivity(toActivity);
                }
            }
        });
        rlvDiscoveryRecord.setLayoutManager(new LinearLayoutManager(context));
        rlvDiscoveryRecord.setAdapter(adapterRecord);

        headline.setHeadlineClickListener(new AdHeadline.HeadlineClickListener() {
            @Override
            public void onHeadlineClick(HeadlineBean bean) {

            }

            @Override
            public void onMoreClick() {
                Intent toTalkAbout = new Intent(context, TalkAboutActivity.class);
                startActivity(toTalkAbout);
            }
        });
    }

    @Override
    protected void onFirstUserVisible() {
        LogUtils.d("onFirstUserVisible");
        talkABoutData = new ArrayList<>();
        talkABoutData.add(new HeadlineBean("作者", "个人制作，简单粗糙，见谅见谅"));
        talkABoutData.add(new HeadlineBean("问题", "如果数据未刷出，可以刷新/重启，一下"));
        talkABoutData.add(new HeadlineBean("拜谢", "您的包容与鼓励是作者莫大的荣幸。"));
        headline.setData(talkABoutData);

        presenter.initDiscoveryTop();
        presenter.initDiscoveryRecord();
        showSkeleton();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtils.d("onDestroy");
    }

    private void showSkeleton() {
        if (skeletonScreenGroup != null) {
            skeletonScreenRecord.show();
            skeletonScreenGroup.show();
        } else {
            skeletonScreenGroup = Skeleton.bind(rlvDiscoveryTop)
                    .adapter(adapterGroup)
                    .load(R.layout.item_skeleton_rlv_discovery_group)
                    .count(5)
                    .show();
            skeletonScreenRecord = Skeleton.bind(rlvDiscoveryRecord)
                    .adapter(adapterRecord)
                    .load(R.layout.item_skeleton_rlv_discovery_record)
                    .count(5)
                    .show();
        }
    }

    @Override
    protected void onUserVisible() {
        if (dataGroups.isEmpty()){
            presenter.initDiscoveryTop();
        }
        if (dataRecords.isEmpty()){
            presenter.initDiscoveryRecord();
        }
    }


    @Override
    protected void onUserInvisible() {
    }

    @Override
    public void initDiscoveryGroupError(String error) {
        ToastUtil.toastShort(error);
        srlTop.setRefreshing(false);
    }

    @Override
    public void initDiscoveryRecordError(String error) {
        ToastUtil.toastShort(error);
        srlTop.setRefreshing(false);
    }

    @Override
    public void initDiscoveryGroupSuc(List<DiscoveryTopGroup> topGroups) {
        skeletonScreenGroup.hide();
        adapterGroup.addData(topGroups);
        srlTop.setRefreshing(false);
    }

    @Override
    public void initDiscoveryRecordSuc(List<DiscoveryRecord> topGroups) {
        skeletonScreenRecord.hide();
        adapterRecord.addData(topGroups);
    }

    @Override
    public void showError(String msg) {
        ToastUtil.toastShort(msg);
    }

    @Override
    public void onRefresh() {
        presenter.initDiscoveryRecord();
        presenter.initDiscoveryTop();
    }
}
