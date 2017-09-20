package com.example.greatbook.local.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.avos.avoscloud.AVUser;
import com.example.greatbook.R;
import com.example.greatbook.base.dialog.BaseAlertDialog;
import com.example.greatbook.databinding.ActivityPrefectEssayBinding;
import com.example.greatbook.greendao.entity.ContentCommit;
import com.example.greatbook.greendao.entity.Essay;
import com.example.greatbook.local.presenter.PrefectEssayPresenter;
import com.example.greatbook.local.presenter.contract.PrefectEssayContract;
import com.example.greatbook.model.leancloud.User;
import com.example.greatbook.utils.DateUtils;
import com.example.greatbook.utils.DpUtils;
import com.example.greatbook.utils.StringUtils;
import com.example.greatbook.utils.ToastUtil;
import com.example.greatbook.widght.CommitLogDialog;

import java.util.Date;
import java.util.List;

/**
 * Created by MDove on 2017/9/17.
 * <p>
 * 协同Activity
 */

public class PrefectEssayActivity extends AppCompatActivity implements PrefectEssayContract.View {
    private ActivityPrefectEssayBinding binding;
    public static final String ESSAY_ID = "essay_id";
    private PrefectEssayPresenter mPresenter;
    private long mEssayId;
    private BaseAlertDialog mDialog;
    private String mContent;
    private User mUser;
    private Essay mEssay;

    public static void startPrefectEssay(Context context, long essayId) {
        Intent start = new Intent(context, PrefectEssayActivity.class);
        start.putExtra(ESSAY_ID, essayId);
        context.startActivity(start);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_prefect_essay);

        mEssayId = getIntent().getLongExtra(ESSAY_ID, 0);
        mUser = AVUser.getCurrentUser(User.class);

        if (mEssayId > 0 && mUser != null) {
            mPresenter = new PrefectEssayPresenter();
            mPresenter.attachView(this);
            mPresenter.initEssay(mEssayId);
        } else {
            finish();
        }

        binding.btnCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog = new BaseAlertDialog.Builder(PrefectEssayActivity.this)
                        .setContentView(R.layout.dialog_add_commit_tips)
                        .setWidthAndHeight(DpUtils.dp2px(250), DpUtils.dp2px(300))
                        .create();
                mDialog.show();
                final EditText etCommitTips = mDialog.getView(R.id.et_commit_tips);
                mDialog.setOnClickListener(R.id.btn_cancel, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDialog.dismiss();
                    }
                });
                mDialog.setOnClickListener(R.id.btn_commit, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String title = etCommitTips.getText().toString();
                        //无需判空，直接提交内容,同时保存到文章中
                        mContent = binding.etContent.getText().toString();
                        mPresenter.saveEssay(mContent);

                        if (StringUtils.isEmpty(title)) {
                            title = DateUtils.getDateEnglish(new Date());
                        }
                        ContentCommit contentCommit = new ContentCommit();
                        contentCommit.essayId = mEssayId;
                        contentCommit.belongUserId = mUser.getObjectId();
                        contentCommit.belongUserAccount = mUser.getUsername();
                        contentCommit.commitContent = title;
                        contentCommit.commitTips = title;
                        contentCommit.commitContent = mContent;
                        contentCommit.time = new Date();
                        contentCommit.originContent = mEssay.content;

                        mPresenter.insertContentCommit(contentCommit);
                    }
                });

            }
        });

        binding.btnCommitLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.showCommitLog(mUser.getObjectId());
            }
        });
    }


    @Override
    public void showError(String msg) {
        ToastUtil.toastShort(msg);
    }

    @Override
    public void insertContentCommitSuc() {
        ToastUtil.toastShort("当前内容已提交");
        mDialog.dismiss();
    }

    @Override
    public void saveEssaySuc(Essay essay) {
        mEssay = essay;
        binding.etContent.setText(essay.content);
        binding.tvUserAccount.setText(essay.belongUserAccount);
        binding.tvEssayContent.setText(essay.content);
    }

    @Override
    public void showInitEssay(Essay essay) {
        mEssay = essay;
        binding.etContent.setText(essay.content);
        binding.tvUserAccount.setText(essay.belongUserAccount);
        binding.tvEssayContent.setText(essay.content);
    }

    @Override
    public void showCommitLog(List<ContentCommit> data) {
        CommitLogDialog dialog = new CommitLogDialog(this);
        dialog.show();
        dialog.setData(data);
    }

    @Override
    public void showCommitLogEmpty() {
        ToastUtil.toastShort("当前暂无提交数据");
    }
}
