package com.example.greatbook.middle.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.greatbook.R;
import com.example.greatbook.base.BaseActivity;
import com.example.greatbook.constants.Constants;
import com.example.greatbook.middle.presenter.AllLocalRecordPresenter;
import com.example.greatbook.middle.presenter.contract.AllLocalRecordContract;
import com.example.greatbook.model.LocalRecordRLV;
import com.example.greatbook.model.event.LocalAddEvent;
import com.example.greatbook.utils.LogUtils;
import com.example.greatbook.utils.ToastUtil;
import com.example.greatbook.widght.DefaultNavigationBar;
import com.example.greatbook.widght.itemswip.OnSwipeListener;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MDove on 2017/8/13.
 */

public class AllLocalRecordActivity extends BaseActivity<AllLocalRecordPresenter> implements AllLocalRecordContract.View {
    private static final String TAG = "AllLocalRecordActivity";
    @BindView(R.id.rlv_set_all_local_record)
    RecyclerView rlvSetAllLocalRecord;
    @BindView(R.id.tv_empty_view)
    RelativeLayout tvEmptyView;
    private List<LocalRecordRLV> data;
    private AllLocalRecordAdapter adapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_all_local_record;
    }

    @Override
    public void init() {
        presenter = new AllLocalRecordPresenter(this);

        new DefaultNavigationBar.Builder(this, null)
                .setOnLeftClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        LocalAddEvent localAddEvent = new LocalAddEvent();
                        localAddEvent.event = Constants.LOCAL_SET_RECORD_SUC_FINISH;
                        EventBus.getDefault().post(localAddEvent);
                        finish();
                    }
                })
                .setTitleText("我的全部内容").builder();
        data = new ArrayList<>();
        adapter = new AllLocalRecordAdapter(this, R.layout.item_set_all_local_record, data);
        adapter.setOnSwipeListener(new OnSwipeListener() {
            @Override
            public void onDelete(int pos) {
                presenter.deleteLocalRecord(data.get(pos));
            }

            @Override
            public void onTop(int pos) {

            }

            @Override
            public void onAlter(int pos) {

            }
        });
        rlvSetAllLocalRecord.setLayoutManager(new LinearLayoutManager(this));
        rlvSetAllLocalRecord.setAdapter(adapter);

        presenter.initLocalRecords();
    }

    @Override
    public void initLocalRecords(List<LocalRecordRLV> records) {
        if (records.isEmpty()){
            tvEmptyView.setVisibility(View.VISIBLE);
        }else {
            tvEmptyView.setVisibility(View.GONE);
            adapter.addData(records);
        }
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
    public void showError(String msg) {
        ToastUtil.toastShort(msg);
    }
}
