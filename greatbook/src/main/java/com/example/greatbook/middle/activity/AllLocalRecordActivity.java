package com.example.greatbook.middle.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.example.greatbook.R;
import com.example.greatbook.base.BaseActivity;
import com.example.greatbook.base.dialog.BaseAlertDialog;
import com.example.greatbook.constants.Constants;
import com.example.greatbook.middle.adapter.AllLocalRecordAdapter;
import com.example.greatbook.middle.presenter.AllLocalRecordPresenter;
import com.example.greatbook.middle.presenter.contract.AllLocalRecordContract;
import com.example.greatbook.middle.model.LocalRecordRLV;
import com.example.greatbook.model.event.LocalAddEvent;
import com.example.greatbook.utils.DpUtils;
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
                .setTitleText("我的全部内容")
                .setLeftResId(R.drawable.btn_back_)
                .builder();
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
            public void onAlter(final int pos) {
                dialog=new BaseAlertDialog.Builder(AllLocalRecordActivity.this)
                        .setContentView(R.layout.dialog_set_local_record_or_group)
                        .setWidthAndHeight(DpUtils.dp2px(250),DpUtils.dp2px(300))
                        .create();
                dialog.show();
                final EditText etName=dialog.getView(R.id.et_record_or_group_name);
                final EditText etContent=dialog.getView(R.id.et_record_or_group_content);
                String name= StringUtils.isEmpty(data.get(pos).title)
                        ?"未设置":data.get(pos).title;
                String content=StringUtils.isEmpty(data.get(pos).content)
                        ?"未设置":data.get(pos).content;
                etContent.setText(content);
                etName.setText(name);
                dialog.setOnClickListener(R.id.btn_cancel, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.setOnClickListener(R.id.btn_set_ok, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String title=etName.getText().toString();
                        String content=etContent.getText().toString();
                        presenter.updateLocalRecord(data.get(pos),title,content);
                    }
                });
            }
        });
        rlvSetAllLocalRecord.setLayoutManager(new LinearLayoutManager(this));
        rlvSetAllLocalRecord.setAdapter(adapter);

        presenter.initLocalRecords();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (dialog!=null){
            dialog.dismiss();
        }
    }

    @Override
    public void initLocalRecords(List<LocalRecordRLV> records) {
        data=records;
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
    public void updateLocalRecordReturn(String returnStr) {
        ToastUtil.toastShort(returnStr);
        dialog.dismiss();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void updateLocalRecordToNetReturn(String returnStr) {
        LogUtils.d(TAG,returnStr);
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
