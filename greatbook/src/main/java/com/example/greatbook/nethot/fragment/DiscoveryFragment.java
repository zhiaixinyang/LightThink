package com.example.greatbook.nethot.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.ethanhua.skeleton.RecyclerViewSkeletonScreen;
import com.ethanhua.skeleton.Skeleton;
import com.example.greatbook.App;
import com.example.greatbook.R;
import com.example.greatbook.base.BaseLazyFragment;
import com.example.greatbook.base.adapter.OnItemClickListener;
import com.example.greatbook.constants.Constants;
import com.example.greatbook.nethot.activity.DiscoveryGroupAndRecordsActivity;
import com.example.greatbook.nethot.activity.DiscoveryRecordRemarkActivity;
import com.example.greatbook.local.activity.TalkAboutActivity;
import com.example.greatbook.nethot.adapter.DiscoveryCommonAdapter;
import com.example.greatbook.nethot.adapter.MiddleDiscoveryAdapter;
import com.example.greatbook.nethot.model.DiscoveryRecord;
import com.example.greatbook.nethot.model.DiscoveryTopGroup;
import com.example.greatbook.local.presenter.MiddleDiscoveryPresenter;
import com.example.greatbook.local.presenter.contract.MiddleDiscoveryContract;
import com.example.greatbook.model.HeadlineBean;
import com.example.greatbook.model.event.LikeEvent;
import com.example.greatbook.utils.ToastUtil;
import com.example.greatbook.widght.AdHeadline;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by MDove on 2017/8/11.
 */

public class DiscoveryFragment extends BaseLazyFragment<MiddleDiscoveryPresenter> implements MiddleDiscoveryContract.View,
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
    private DiscoveryCommonAdapter adapterRecord;
    private RecyclerViewSkeletonScreen skeletonScreenGroup;
    private RecyclerViewSkeletonScreen skeletonScreenRecord;
    private List<HeadlineBean> talkABoutData;

    public static DiscoveryFragment newInstance() {
        Bundle args = new Bundle();

        DiscoveryFragment fragment = new DiscoveryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.frag_middle_discovery;
    }

    @Override
    protected void initViewsAndEvents(View view) {
        EventBus.getDefault().register(this);
        presenter = new MiddleDiscoveryPresenter(this);
        context = App.getInstance().getContext();

        dataGroups = new ArrayList<>();
        adapterGroup = new MiddleDiscoveryAdapter(context, R.layout.item_rlv_discovery_group, dataGroups);
        adapterGroup.setOnItemClickListener(new OnItemClickListener<DiscoveryTopGroup>() {
            @Override
            public void onItemClick(View view, DiscoveryTopGroup o, int position) {
                Intent toGroup=new Intent(context, DiscoveryGroupAndRecordsActivity.class);
                toGroup.putExtra(Constants.DISCOVERY_GROUP_ITEM_CLICK,o);
                startActivity(toGroup);
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rlvDiscoveryTop.setLayoutManager(linearLayoutManager);
        rlvDiscoveryTop.setAdapter(adapterGroup);
        srlTop.setOnRefreshListener(this);

        dataRecords = new ArrayList<>();
        adapterRecord = new DiscoveryCommonAdapter(context, R.layout.item_rlv_discovery_record, dataRecords);
        adapterRecord.setOnItemClickListener(new OnItemClickListener<DiscoveryRecord>() {
            @Override
            public void onItemClick(View view, DiscoveryRecord o, int position) {
                if (o != null) {
                    Intent toActivity = new Intent(context, DiscoveryRecordRemarkActivity.class);
                    toActivity.putExtra(Constants.DISCOVERY_RECORD_ITEM_CLICK, o);
                    toActivity.putExtra(Constants.OPEN_RECORD_ITEM_POSITION,position);
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
        talkABoutData = new ArrayList<>();
        talkABoutData.add(new HeadlineBean("作者", "个人制作，简单粗糙，见谅见谅"));
        talkABoutData.add(new HeadlineBean("问题", "如果数据未刷出，可以刷新/重启，一下"));
        talkABoutData.add(new HeadlineBean("拜谢", "您的包容与鼓励是作者莫大的荣幸。"));
        headline.setData(talkABoutData);

        presenter.initDiscoveryTop();
        presenter.initDiscoveryRecord();
        showSkeleton();
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
        if (dataGroups.isEmpty()) {
            presenter.initDiscoveryTop();
        }
        if (dataRecords.isEmpty()) {
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(LikeEvent event){
        switch (event.event){
            case Constants.RECORD_REMARKS_LIKE_TO_REFRESH:
                presenter.initDiscoveryRecord();

                break;
        }
    }
}
