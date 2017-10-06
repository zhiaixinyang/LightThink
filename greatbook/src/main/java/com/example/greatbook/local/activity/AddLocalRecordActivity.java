package com.example.greatbook.local.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.ArrayMap;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.avos.avoscloud.AVUser;
import com.example.greatbook.R;
import com.example.greatbook.base.BaseActivity;
import com.example.greatbook.base.dialog.BaseAlertDialog;
import com.example.greatbook.constants.Constants;
import com.example.greatbook.greendao.entity.LocalGroup;
import com.example.greatbook.greendao.entity.LocalRecord;
import com.example.greatbook.local.model.event.SetGroupEvent;
import com.example.greatbook.local.presenter.LocalAddPresenter;
import com.example.greatbook.local.presenter.contract.LocalAddContract;
import com.example.greatbook.model.event.LocalAddEvent;
import com.example.greatbook.utils.DpUtils;
import com.example.greatbook.utils.LogUtils;
import com.example.greatbook.utils.ScreenUtils;
import com.example.greatbook.utils.StringUtils;
import com.example.greatbook.utils.ToastUtil;
import com.example.greatbook.widght.DefaultNavigationBar;
import com.example.greatbook.widght.FlowLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by MDove on 2017/8/11.
 */

public class AddLocalRecordActivity extends BaseActivity<LocalAddPresenter> implements LocalAddContract.View {

    private static final String TAG = "AddLocalRecordActivity";
    @BindView(R.id.flowLayout)
    FlowLayout mFlowLayout;
    @BindView(R.id.et_title)
    EditText etTitle;
    @BindView(R.id.et_content)
    EditText etContent;
    private ArrayMap<String, LocalGroup> localMap;
    private LocalGroup seletLocalGroup;
    private int[] photoArr = new int[]{R.drawable.icon_default_group_encourage, R.drawable.icon_default_group_jok, R.drawable.icon_default_group_short_eassy};

    public static void startAddLocalRecord(Context context) {
        Intent toStart = new Intent(context, AddLocalRecordActivity.class);
        context.startActivity(toStart);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_add_local_record;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dialog != null) {
            dialog.dismiss();
        }
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void init() {
        EventBus.getDefault().register(this);
        localMap = new ArrayMap<>();
        presenter = new LocalAddPresenter(this);
        presenter.initLocalGroup();
        new DefaultNavigationBar.Builder(this, null)
                .setOnLeftClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                })
                .setTitleText("随手记")
                .setLeftResId(R.drawable.btn_back_)
                .setRightResId(R.drawable.btn_send)
                .setOnRightClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addAndSendContent();
                    }
                })
                .builder();
    }

    private void addAndSendContent() {
        Date time = new Date();
        String strContent = etContent.getText().toString() != null ? etContent.getText().toString() : "";
        String title = etTitle.getText().toString() != null ? etTitle.getText().toString() : "无标题";
        Long groupId = seletLocalGroup.getId() != null ? seletLocalGroup.getId() : 0;
        String groupTitle = seletLocalGroup.getTitle() != null ? seletLocalGroup.getTitle() : "未选择文集";
        String avUserId = AVUser.getCurrentUser().getObjectId() != null ? AVUser.getCurrentUser().getObjectId() : "";

        LocalRecord localRecord = new LocalRecord();
        localRecord.setContent(strContent);
        localRecord.setBelongId(avUserId);
        localRecord.setTimeDate(time);
        localRecord.setGroupId(groupId);
        localRecord.setGroupTitle(groupTitle);
        localRecord.setTitle(title);

        presenter.addContentToLocal(localRecord);
    }

    @Override
    public void sendContentToNetSuc(String success) {
        LogUtils.d(TAG, success);
    }

    @Override
    public void sendContentToNetError(String error) {
        LogUtils.d(TAG, error);
    }

    @Override
    public void sendNewLocalGroupToNetReturn(String returnStr) {
        LogUtils.d(TAG, returnStr);
    }


    @Override
    public void addContentToLocalSuc(String success) {
        ToastUtil.toastShort(success);
        etContent.setText("");
        etTitle.setText("");
        //注意此时并不会回调onUserVisible方法(用EventBus处理一下)
        LocalAddEvent event = new LocalAddEvent();
        event.event = Constants.LOCAL_ADD_SUC_FINISH;
        EventBus.getDefault().post(event);
        finish();
    }

    @Override
    public void addContentToLocalError(String error) {
        ToastUtil.toastShort(error);
    }

    @Override
    public void addNewLocalGroupSuc(String success) {
        dialog.dismiss();
        ToastUtil.toastShort(success);
        presenter.initLocalGroup();
    }

    @Override
    public void initLocalGroup(List<LocalGroup> groups) {
        List<String> names = new ArrayList<>();
        for (LocalGroup localGroup : groups) {
            names.add(localGroup.getTitle());
            localMap.put(localGroup.getTitle(), localGroup);
        }
        mFlowLayout.setList(names);
        mFlowLayout.setOnItemClickListener(new FlowLayout.onItemClickListener() {
            @Override
            public void onItemClick(int position, String text) {
                seletLocalGroup = localMap.get(text);
            }
        });
        //默认选择情况
        mFlowLayout.setIndexItemSelected(0);
        seletLocalGroup = localMap.get(groups.get(0).getTitle());
    }

    @Override
    public void showError(String msg) {
        ToastUtil.toastShort(msg);
    }

    @OnClick(R.id.btn_add_group)
    public void onViewClickedAdd() {
        dialog = new BaseAlertDialog.Builder(this)
                .setContentView(R.layout.dialog_add_group)
                .setWidthAndHeight(ScreenUtils.getScreenWidth() - DpUtils.dp2px(60), ScreenUtils.getScreenHeight() - DpUtils.dp2px(200))
                .setOnClickListener(R.id.btn_cancel, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                })
                .setOnClickListener(R.id.btn_detail, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent toDetaolAdd = new Intent(AddLocalRecordActivity.this, AddLocalGroupActivity.class);
                        startActivity(toDetaolAdd);
                    }
                }).create();
        final EditText etName = dialog.getView(R.id.et_group_name);
        final EditText etContent = dialog.getView(R.id.et_group_content);
        TextView btnAdd = dialog.getView(R.id.btn_ok);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString();
                String content = etContent.getText().toString();
                AVUser user = AVUser.getCurrentUser();
                if (name != null && !StringUtils.isEmpty(name) && user != null) {
                    LocalGroup localGroup = new LocalGroup();
                    localGroup.setBelongId(user.getObjectId());
                    localGroup.setTime(new Date());
                    localGroup.setTitle(name);
                    localGroup.setGroupPhotoPath("");
                    localGroup.setBgColor(ContextCompat.getColor(AddLocalRecordActivity.this, R.color.blue) + "");
                    localGroup.setGroupLocalPhotoPath(photoArr[new Random().nextInt(3)]);
                    if (!StringUtils.isEmpty(content)) {
                        localGroup.setContent(content);
                    }
                    presenter.addNewLocalGroup(localGroup);
                }
            }
        });
        dialog.show();
    }

    @OnClick(R.id.btn_set_group)
    public void onViewClickedSet() {
        Intent toSet = new Intent(this, SetGroupsActivity.class);
        startActivity(toSet);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(SetGroupEvent event) {
        switch (event.event) {
            case Constants.SET_GROUP_FINISH:
                presenter.initLocalGroup();
                break;
            case Constants.ADD_GROUP_TO_RECORD_ABANDON:
                if (dialog != null) {
                    dialog.dismiss();
                }
                break;
        }
    }
}
