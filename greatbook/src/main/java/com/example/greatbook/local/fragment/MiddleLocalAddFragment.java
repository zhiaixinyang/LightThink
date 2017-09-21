package com.example.greatbook.local.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.greatbook.App;
import com.example.greatbook.R;
import com.example.greatbook.base.BaseLazyFragment;
import com.example.greatbook.base.adapter.CommonAdapter;
import com.example.greatbook.base.adapter.OnItemClickListener;
import com.example.greatbook.base.adapter.ViewHolder;
import com.example.greatbook.constants.Constants;
import com.example.greatbook.local.activity.AllLocalRecordActivity;
import com.example.greatbook.local.activity.EssayListActivity;
import com.example.greatbook.local.activity.MyPlanActivity;
import com.example.greatbook.local.activity.SetGroupsActivity;
import com.example.greatbook.local.adapter.MainMenuAdapter;
import com.example.greatbook.local.model.LocalRecordRLV;
import com.example.greatbook.local.model.MainMenuItemBean;
import com.example.greatbook.local.presenter.MiddleLocalAddPresenter;
import com.example.greatbook.local.presenter.contract.MiddleLocalAddContract;
import com.example.greatbook.model.event.LocalAddEvent;
import com.example.greatbook.utils.DateUtils;
import com.example.greatbook.utils.DpUtils;
import com.example.greatbook.utils.GlideUtils;
import com.example.greatbook.utils.SelectorFactory;
import com.example.greatbook.utils.StringUtils;
import com.example.greatbook.utils.ToastUtil;
import com.example.greatbook.widght.RoundImageView;
import com.example.greatbook.widght.refresh.DefaultRefreshCreator;
import com.example.greatbook.widght.refresh.LoadRefreshRecyclerView;
import com.example.greatbook.widght.refresh.RefreshRecyclerView;
import com.example.greatbook.widght.rlvanim.SlideInLeftAnimator;
import com.example.greatbook.widght.rlvanim.adapter.SlideInLeftAnimationAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by MDove on 2017/8/11.
 */

public class MiddleLocalAddFragment extends BaseLazyFragment<MiddleLocalAddPresenter> implements MiddleLocalAddContract.View,
        RefreshRecyclerView.OnRefreshListener {
    @BindView(R.id.rlv_local)
    LoadRefreshRecyclerView rlvLocal;
    @BindView(R.id.tv_empty_view)
    RelativeLayout emptyView;
    @BindView(R.id.tv_load_view)
    TextView loadingView;
    @BindView(R.id.rlv_main_menu)
    RecyclerView rlvMainMenu;
    private CommonAdapter<LocalRecordRLV> mAdapter;
    private MainMenuAdapter menuAdapter;
    private List<MainMenuItemBean> menuData;
    private Context context;
    private List<LocalRecordRLV> data;
    private MiddleLocalAddPresenter presenter;
    public static final int MY_ALL_CONTENT = 1;
    public static final int MY_ALL_GROUP = 2;
    public static final int MY_COOPATER_TOPIC = 3;
    public static final int MY_PLAN = 4;

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
        presenter = new MiddleLocalAddPresenter(this);
        context = App.getInstance().getContext();
        presenter.initMenu(context);
        menuAdapter = new MainMenuAdapter(context, menuData);
        rlvMainMenu.setLayoutManager(new GridLayoutManager(context, 5));
        rlvMainMenu.setAdapter(menuAdapter);

        menuAdapter.setListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, Object o, int position) {
                MainMenuItemBean menuItemBean = (MainMenuItemBean) o;
                switch (menuItemBean.menuType) {
                    case MY_ALL_CONTENT:
                        Intent toMyAllContent = new Intent(getContext(), AllLocalRecordActivity.class);
                        startActivity(toMyAllContent);
                        break;
                    case MY_ALL_GROUP:
                        Intent toMyAllGroup = new Intent(getContext(), SetGroupsActivity.class);
                        toMyAllGroup.putExtra(SetGroupsActivity.IS_ALL_GROUPS_SHOW_TAG, SetGroupsActivity.IS_ALL_GROUPS_SHOW);
                        startActivity(toMyAllGroup);
                        break;
                    case MY_PLAN:
                        Intent toMyPlan = new Intent(getContext(), MyPlanActivity.class);
                        startActivity(toMyPlan);
                        break;
                    case MY_COOPATER_TOPIC:
                        Intent to = new Intent(getContext(), EssayListActivity.class);
                        startActivity(to);
                        break;
                    default:
                        break;
                }
            }
        });

        presenter.initLocalRecord();

        mAdapter = new CommonAdapter<LocalRecordRLV>(context, R.layout.item_middle_local_add, data) {
            @Override
            public void convert(ViewHolder holder, LocalRecordRLV localRecord) {
                String content = localRecord.content;
                String title = localRecord.title;
                //title可能为空但content不可能为空
                holder.setText(R.id.tv_title, !StringUtils.isEmpty(title) ? title : "无标题");
                holder.setText(R.id.tv_content, content);
                holder.setText(R.id.tv_time, DateUtils.getDateChinese(localRecord.time));
                holder.setText(R.id.tv_group_title, localRecord.groupTitle);
                holder.getView(R.id.tv_group_title).setBackground(SelectorFactory.newShapeSelector()
                        .setDefaultBgColor(ContextCompat.getColor(context, R.color.blue))
                        .setCornerRadius(DpUtils.dp2px(4))
                        .setStrokeWidth(DpUtils.dp2px(1))
                        .create());
                if (StringUtils.isEmpty(localRecord.groupPhotoPath)) {
                    holder.setImageResource(R.id.iv_group, localRecord.groupLocalPhotoPath);
                    holder.getView(R.id.iv_group).setBackgroundColor(Integer.parseInt(localRecord.bgColor));
                } else {
                    GlideUtils.loadSmallIv(localRecord.groupPhotoPath, (RoundImageView) holder.getView(R.id.iv_group));
                }
            }
        };
        rlvLocal.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        rlvLocal.setItemAnimator(new SlideInLeftAnimator());
        rlvLocal.addRefreshViewCreator(new DefaultRefreshCreator());
        rlvLocal.addLoadingView(loadingView);
        rlvLocal.addEmptyView(emptyView);
        rlvLocal.setOnRefreshListener(this);
        rlvLocal.setAdapter(mAdapter);
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

    @Override
    public void onLoadRlvRefresh() {
        presenter.initLocalRecord();
    }

    @Override
    public void initLocalRecordError(String error) {
        ToastUtil.toastShort(error);
        data = new ArrayList<>();
        mAdapter.addData(data);
    }

    @Override
    public void initLocalRecordSuc(List<LocalRecordRLV> records) {
        data = records;
        mAdapter.addData(data);
        if (rlvLocal != null) {
            rlvLocal.onStopRefresh();
        }
        loadingView.setVisibility(View.GONE);
        emptyView.setVisibility(View.GONE);
    }

    @Override
    public void showMenu(List<MainMenuItemBean> menuData) {
        this.menuData = menuData;
    }

    @Override
    public void showError(String msg) {
        ToastUtil.toastShort(msg);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(LocalAddEvent event) {
        switch (event.event) {
            case Constants.LOCAL_ADD_SUC_FINISH:
                presenter.initLocalRecord();
                break;
            case Constants.LOCAL_SET_RECORD_SUC_FINISH:
                presenter.initLocalRecord();
                break;
        }
    }
}
