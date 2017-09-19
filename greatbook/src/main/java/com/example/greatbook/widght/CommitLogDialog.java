package com.example.greatbook.widght;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.example.greatbook.R;
import com.example.greatbook.base.adapter.CommonAdapter;
import com.example.greatbook.base.adapter.ViewHolder;
import com.example.greatbook.greendao.entity.ContentCommit;
import com.example.greatbook.utils.DateUtils;

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

    public CommitLogDialog(@NonNull Context context) {
        super(context);
        mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_show_commit_log);
        mRlvShowCommitLog = (RecyclerView) findViewById(R.id.rlv_show_commit_log);
        mTitle = (TextView) findViewById(R.id.tv_title);

        mData = new ArrayList<>();
        mAdapter = new CommonAdapter<ContentCommit>(mContext, R.layout.item_show_commit_log, mData) {
            @Override
            public void convert(ViewHolder holder, ContentCommit contentCommit) {
                holder.setText(R.id.tv_commit_tips, contentCommit.commitTips);
                holder.setText(R.id.tv_time, DateUtils.getDateChinese(contentCommit.time));
                holder.setText(R.id.tv_commit_user_account, contentCommit.belongUserAccount);
            }
        };
        mRlvShowCommitLog.setLayoutManager(new LinearLayoutManager(mContext));
        mRlvShowCommitLog.setAdapter(mAdapter);
    }

    public void setData(List<ContentCommit> data) {
        mData = data;
        mAdapter.notifyDataSetChanged();
    }
}