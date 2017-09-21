package com.example.greatbook.local.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.greatbook.R;
import com.example.greatbook.base.BaseActivity;
import com.example.greatbook.constants.Constants;
import com.example.greatbook.greendao.entity.LocalGroup;
import com.example.greatbook.local.adapter.SetGroupsAdapter;
import com.example.greatbook.local.model.event.SetGroupEvent;
import com.example.greatbook.local.presenter.SetGroupsPresenter;
import com.example.greatbook.local.presenter.contract.SetGroupsContract;
import com.example.greatbook.utils.LogUtils;
import com.example.greatbook.utils.StringUtils;
import com.example.greatbook.utils.ToastUtil;
import com.example.greatbook.widght.DefaultNavigationBar;
import com.example.greatbook.widght.itemswip.OnSwipeListener;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by MDove on 2017/8/13.
 */

public class SetGroupsActivity extends BaseActivity<SetGroupsPresenter> implements SetGroupsContract.View {
    private static final String TAG = "SetGroupsActivity";
    @BindView(R.id.rlv_set_groups)
    RecyclerView rlvSetGroups;
    private SetGroupsAdapter mAdapter;
    private List<LocalGroup> mData;

    public static final String IS_ALL_GROUPS_SHOW_TAG = "is_all_groups_show_tag";
    public static final String IS_ALL_GROUPS_SHOW = "我的文集";

    @Override
    public int getLayoutId() {
        return R.layout.activity_set_groups;
    }

    @Override
    public void init() {
        String title = getIntent().getStringExtra(IS_ALL_GROUPS_SHOW_TAG);
        if (StringUtils.isEmpty(title)) {
            title = "文集设置";
        }
        new DefaultNavigationBar.Builder(this, null)
                .setTitleText(title)
                .setOnLeftClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SetGroupEvent event = new SetGroupEvent();
                        event.event = Constants.SET_GROUP_FINISH;
                        EventBus.getDefault().post(event);
                        finish();
                    }
                })
                .setLeftResId(R.drawable.btn_back_)
                .builder();
        presenter = new SetGroupsPresenter(this);
        mData = new ArrayList<>();
        mAdapter = new SetGroupsAdapter(this, R.layout.item_set_groups, mData);
        mAdapter.setOnSwipeListener(new OnSwipeListener() {
            @Override
            public void onDelete(int pos) {
                presenter.deleteLocalGroup(mData.get(pos));
                //通知页面变化
                mData.remove(mData.get(pos));
                mAdapter.addData(mData);
            }

            @Override
            public void onTop(int pos) {
                //在returnSetUserdGroups回调中处理即可
                presenter.setUserdGroups(mData, pos);
            }

            @Override
            public void onAlter(final int pos) {
                Intent toDetail = new Intent(SetGroupsActivity.this, AddLocalGroupActivity.class);
                toDetail.putExtra(AddLocalGroupActivity.IS_ALTER, mData.get(pos));
                toDetail.putExtra(AddLocalGroupActivity.IS_SHOW_ONE_GROUP_TAG, mData.get(pos).title);
                startActivity(toDetail);
            }
        });
        rlvSetGroups.setLayoutManager(new LinearLayoutManager(this));
        rlvSetGroups.setAdapter(mAdapter);
        presenter.initLocalGroup();
    }

    @Override
    public void deleteLocalGroupToNetReturn(String returnStr) {
        LogUtils.d(TAG, returnStr);
    }

    @Override
    public void deleteLocalGroupReturn(String returnStr) {
        ToastUtil.toastShort(returnStr);
    }

    @Override
    public void updateGroupMesReturn(String returnStr) {
        dialog.dismiss();
        mAdapter.notifyDataSetChanged();
        ToastUtil.toastShort(returnStr);
    }

    @Override
    public void updateGroupMesToNetReturn(String returnStr) {
        LogUtils.d(TAG, returnStr);
    }

    @Override
    public void initLocalGroup(List<LocalGroup> groups) {
        mData = groups;
        mAdapter.addData(mData);
    }

    @Override
    public void returnSetUserdGroups(List<LocalGroup> groups) {
        mAdapter.addData(groups);
    }

    @Override
    public void userdGroupsIsOver() {
        ToastUtil.toastShort("常用文集不能超过5个");
    }

    @Override
    public void showError(String msg) {
        ToastUtil.toastShort(msg);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        SetGroupEvent event = new SetGroupEvent();
        event.event = Constants.SET_GROUP_FINISH;
        EventBus.getDefault().post(event);
    }
}
