package com.example.greatbook.middle.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import com.example.greatbook.R;
import com.example.greatbook.base.BaseActivity;
import com.example.greatbook.base.dialog.BaseAlertDialog;
import com.example.greatbook.constants.Constants;
import com.example.greatbook.greendao.entity.LocalGroup;
import com.example.greatbook.middle.adapter.SetGroupsAdapter;
import com.example.greatbook.middle.model.event.SetGroupEvent;
import com.example.greatbook.middle.presenter.SetGroupsPresenter;
import com.example.greatbook.middle.presenter.contract.SetGroupsContract;
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

public class SetGroupsActivity extends BaseActivity<SetGroupsPresenter> implements SetGroupsContract.View {
    private static final String TAG = "SetGroupsActivity";
    @BindView(R.id.rlv_set_groups)
    RecyclerView rlvSetGroups;
    private SetGroupsAdapter adapter;
    private List<LocalGroup> data;
    @Override
    public int getLayoutId() {
        return R.layout.activity_set_groups;
    }

    @Override
    public void init() {
        new DefaultNavigationBar.Builder(this,null)
                .setTitleText("文集设置")
                .setOnLeftClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SetGroupEvent event=new SetGroupEvent();
                        event.event= Constants.SET_GROUP_FINISH;
                        EventBus.getDefault().post(event);
                        finish();
                    }
                }).builder();
        presenter=new SetGroupsPresenter(this);
        data=new ArrayList<>();
        adapter=new SetGroupsAdapter(this,R.layout.item_set_groups,data);
        adapter.setOnSwipeListener(new OnSwipeListener() {
            @Override
            public void onDelete(int pos) {
                presenter.deleteLocalGroup(data.get(pos));
                //通知页面变化
                data.remove(data.get(pos));
                adapter.addData(data);
            }

            @Override
            public void onTop(int pos) {
                //在returnSetUserdGroups回调中处理即可
                presenter.setUserdGroups(data,pos);
            }

            @Override
            public void onAlter(final int pos) {
                dialog=new BaseAlertDialog.Builder(SetGroupsActivity.this)
                        .setContentView(R.layout.dialog_set_local_record_or_group)
                        .setWidthAndHeight(DpUtils.dp2px(250), DpUtils.dp2px(300))
                        .create();
                dialog.show();
                final EditText etName=dialog.getView(R.id.et_record_or_group_name);
                final EditText etContent=dialog.getView(R.id.et_record_or_group_content);
                final String curName= StringUtils.isEmpty(data.get(pos).getTitle())
                        ?"未设置":data.get(pos).getTitle();
                final String curContent=StringUtils.isEmpty(data.get(pos).getContent())
                        ?"未设置":data.get(pos).getContent();
                etName.setText(curName);
                etContent.setText(curContent);
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
                        if (StringUtils.isEquals(curName,title)&&
                                StringUtils.isEquals(curContent,content)){
                            ToastUtil.toastShort("未修改任何任何");
                        }else {
                            presenter.updateGroupMes(data.get(pos), title, content);
                        }
                    }
                });

            }
        });
        rlvSetGroups.setLayoutManager(new LinearLayoutManager(this));
        rlvSetGroups.setAdapter(adapter);
        presenter.initLocalGroup();
    }

    @Override
    public void deleteLocalGroupToNetReturn(String returnStr) {
        LogUtils.d(TAG,returnStr);
    }

    @Override
    public void deleteLocalGroupReturn(String returnStr) {
        ToastUtil.toastShort(returnStr);
    }

    @Override
    public void updateGroupMesReturn(String returnStr) {
        dialog.dismiss();
        adapter.notifyDataSetChanged();
        ToastUtil.toastShort(returnStr);
    }

    @Override
    public void updateGroupMesToNetReturn(String returnStr) {
        LogUtils.d(TAG,returnStr);
    }

    @Override
    public void initLocalGroup(List<LocalGroup> groups) {
        data=groups;
        adapter.addData(data);
    }

    @Override
    public void returnSetUserdGroups(List<LocalGroup> groups) {
        adapter.addData(groups);
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
        SetGroupEvent event=new SetGroupEvent();
        event.event= Constants.SET_GROUP_FINISH;
        EventBus.getDefault().post(event);
    }
}
