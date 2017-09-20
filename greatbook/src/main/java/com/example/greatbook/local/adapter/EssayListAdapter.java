package com.example.greatbook.local.adapter;

import android.content.Context;

import com.example.greatbook.R;
import com.example.greatbook.base.adapter.CommonAdapter;
import com.example.greatbook.base.adapter.ViewHolder;
import com.example.greatbook.greendao.entity.ContentCommit;
import com.example.greatbook.greendao.entity.Essay;
import com.example.greatbook.local.model.EssayListItem;
import com.example.greatbook.utils.DateUtils;

import java.util.List;

/**
 * Created by MDove on 2017/9/20.
 */

public class EssayListAdapter extends CommonAdapter<EssayListItem> {
    private Context mContext;

    public EssayListAdapter(Context context, int layoutId, List<EssayListItem> datas) {
        super(context, layoutId, datas);
        mContext = context;
    }

    @Override
    public void convert(ViewHolder holder, EssayListItem essayListItem) {
        Essay essay = essayListItem.mEssay;
        ContentCommit lastContentCommit = essayListItem.mLastContentCommit;
        holder.setText(R.id.tv_essay_content, essay.content);
        holder.setText(R.id.tv_author_user_account, essay.belongUserAccount);
        holder.setText(R.id.tv_time, DateUtils.getDateChinese(essay.time));
        if (lastContentCommit != null) {
            holder.setText(R.id.tv_last_commit_tips,
                    String.format(mContext.getResources().getString(R.string.item_essay_list_last_commit),
                            lastContentCommit.belongUserAccount,
                            lastContentCommit.commitTips,
                            DateUtils.getDateChinese(lastContentCommit.time)));
        }
    }

}
