package com.example.greatbook.middle.adapter;

import android.content.Context;
import android.view.View;

import com.example.greatbook.R;
import com.example.greatbook.base.adapter.CommonAdapter;
import com.example.greatbook.base.adapter.ViewHolder;
import com.example.greatbook.middle.model.LocalRecordRLV;
import com.example.greatbook.utils.DateUtils;
import com.example.greatbook.utils.StringUtils;
import com.example.greatbook.widght.ExpandableTextView;
import com.example.greatbook.widght.itemswip.OnSwipeListener;

import java.util.List;

/**
 * Created by MDove on 2017/8/13.
 */

public class AllCommonAdapter extends CommonAdapter<LocalRecordRLV> {
    public AllCommonAdapter(Context context, int layoutId, List<LocalRecordRLV> datas) {
        super(context, layoutId, datas);
    }

    @Override
    public void convert(final ViewHolder holder, LocalRecordRLV localGroup) {
        ((ExpandableTextView)holder.getView(R.id.tv_content)).setText( StringUtils.isEmpty(localGroup.content) ? "未设置" : localGroup.content);
        holder.setText(R.id.tv_title, StringUtils.isEmpty(localGroup.title) ? "未设置" : localGroup.title);
        holder.setText(R.id.tv_time, StringUtils.isEmpty(DateUtils.getDateChinese(localGroup.time)) ? "未设置" : DateUtils.getDateChinese(localGroup.time));
        holder.setText(R.id.tv_group_title, StringUtils.isEmpty(localGroup.groupTitle) ? "未设置" : localGroup.groupTitle);
        holder.setOnClickListener(R.id.btn_alter, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnSwipeListener.onAlter(holder.getAdapterPosition());
            }
        });
        holder.setOnClickListener(R.id.btn_delete, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mOnSwipeListener) {
                    mOnSwipeListener.onDelete(holder.getAdapterPosition());
                }
            }
        });
    }

    private OnSwipeListener mOnSwipeListener;

    public void setOnSwipeListener(OnSwipeListener mOnDelListener) {
        this.mOnSwipeListener = mOnDelListener;
    }
}
