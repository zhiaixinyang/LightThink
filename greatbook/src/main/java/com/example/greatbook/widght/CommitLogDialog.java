package com.example.greatbook.widght;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.example.greatbook.R;
import com.example.greatbook.base.adapter.CommonAdapter;
import com.example.greatbook.base.adapter.OnItemClickListener;
import com.example.greatbook.base.adapter.ViewHolder;
import com.example.greatbook.base.dialog.BaseAlertDialog;
import com.example.greatbook.greendao.entity.ContentCommit;
import com.example.greatbook.local.activity.PrefectEssayActivity;
import com.example.greatbook.local.model.event.BackCommitEvent;
import com.example.greatbook.utils.DateUtils;
import com.example.greatbook.utils.DpUtils;
import com.example.greatbook.utils.LogUtils;
import com.example.greatbook.utils.ScreenUtils;
import com.example.greatbook.widght.rlvanim.SlideInLeftAnimator;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MDove on 17/9/19.
 */

public class CommitLogDialog extends BottomSheetDialog {
    private List<ContentCommit> mData;
    private RecyclerView mRlvShowCommitLog;
    private TextView mTitle;
    private CommonAdapter<ContentCommit> mAdapter;
    private Context mContext;
    private BaseAlertDialog mDialog;

    public CommitLogDialog(@NonNull Context context) {
        super(context);
        mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_show_commit_log);
        Window window = getWindow();
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(lp);

        mRlvShowCommitLog = (RecyclerView) findViewById(R.id.rlv_show_commit_log);
        mTitle = (TextView) findViewById(R.id.tv_title);

        mData = new ArrayList<>();
        mAdapter = new CommonAdapter<ContentCommit>(mContext, R.layout.item_show_commit_log, mData) {
            @Override
            public void convert(ViewHolder holder, ContentCommit contentCommit) {
                holder.setText(R.id.tv_essay_content, contentCommit.commitTips);
                holder.setText(R.id.tv_time, DateUtils.getDateChinese(contentCommit.time));
                holder.setText(R.id.tv_author_user_account, contentCommit.belongUserAccount);
            }
        };
        mAdapter.setOnItemClickListener(new OnItemClickListener<ContentCommit>() {
            @Override
            public void onItemClick(View view, final ContentCommit contentCommit, int position) {
                mDialog = new BaseAlertDialog.Builder(getContext())
                        .setContentView(R.layout.dialog_show_single_commit_log)
                        .setWidthAndHeight(DpUtils.dp2px(350), ScreenUtils.getScreenWidth() - DpUtils.dp2px(50))
                        .create();
                mDialog.show();
                TextView tvCommitTips = mDialog.getView(R.id.tv_commit_tips);
                TextView tvCommitAuthor = mDialog.getView(R.id.tv_commit_author);
                TextView tvCommitTime = mDialog.getView(R.id.tv_commit_time);
                ExpandableTextView tvCommitCurContent = mDialog.getView(R.id.tv_commit_cur_content);
                ExpandableTextView tvCommitOriginContent = mDialog.getView(R.id.tv_commit_origin_content);

                Resources resources = getContext().getResources();
                tvCommitAuthor.setText(String.format(resources.getString(R.string.dialog_show_single_commit_log_author),
                        contentCommit.belongUserAccount));
                tvCommitTips.setText(String.format(resources.getString(R.string.dialog_show_single_commit_log_tips),
                        contentCommit.commitTips));
                tvCommitTime.setText(String.format(resources.getString(R.string.dialog_show_single_commit_log_time),
                        DateUtils.getDateChinese(contentCommit.time)));
                tvCommitOriginContent.setText(String.format(resources.getString(R.string.dialog_show_single_commit_log_origin_content),
                        contentCommit.originContent));
                tvCommitCurContent.setText(String.format(resources.getString(R.string.dialog_show_single_commit_log_cur_content),
                        contentCommit.commitContent));

                mDialog.setOnClickListener(R.id.btn_finish, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDialog.dismiss();
                    }
                });
                //版本回退
                mDialog.setOnClickListener(R.id.btn_back_version, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        BackCommitEvent event = new BackCommitEvent();
                        event.event = BackCommitEvent.BACK_COMMIT_EVENT;
                        event.eventContent = contentCommit.originContent;
                        EventBus.getDefault().post(event);
                        mDialog.dismiss();
                        dismiss();
                    }
                });

            }
        });

        mRlvShowCommitLog.setLayoutManager(new LinearLayoutManager(mContext));
        mRlvShowCommitLog.setAdapter(mAdapter);
    }

    public void setData(List<ContentCommit> data) {
        mAdapter.addData(data);
    }
}
