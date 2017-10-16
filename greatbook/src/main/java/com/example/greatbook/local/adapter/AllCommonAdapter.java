package com.example.greatbook.local.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.example.greatbook.R;
import com.example.greatbook.base.adapter.CommonAdapter;
import com.example.greatbook.base.adapter.ViewHolder;
import com.example.greatbook.local.model.LocalRecordRLV;
import com.example.greatbook.utils.DateUtils;
import com.example.greatbook.utils.StringUtils;
import com.example.greatbook.widght.ExpandableTextView;
import com.example.greatbook.widght.itemswip.OnSwipeListener;
import com.example.greatbook.widght.itemswip.SwipeMenuLayout;

import java.util.List;

/**
 * Created by MDove on 2017/8/13.
 */

public class AllCommonAdapter extends CommonAdapter<LocalRecordRLV> {
    private Context context;
    private ViewHolder mViewHolder;

    public AllCommonAdapter(Context context, int layoutId, List<LocalRecordRLV> datas) {
        super(context, layoutId, datas);
        this.context = context;
    }

    @Override
    public void convert(final ViewHolder holder, LocalRecordRLV localGroup) {
        mViewHolder = holder;
        ((ExpandableTextView) holder.getView(R.id.tv_content)).setText(StringUtils.isEmpty(localGroup.content) ? "未设置" : localGroup.content);
        holder.setText(R.id.tv_title, StringUtils.isEmpty(localGroup.title) ? "未设置" : localGroup.title);
        holder.setText(R.id.tv_time, localGroup.time == null ? "未设置" : DateUtils.getDateChinese(localGroup.time));
        holder.setText(R.id.tv_group_title, StringUtils.isEmpty(localGroup.groupTitle) ? "未设置" : localGroup.groupTitle);
        holder.getView(R.id.tv_group_title).setBackgroundColor(ContextCompat.getColor(context, R.color.blue));
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

    public SwipeMenuLayout getSwipeItem() {
        if (mViewHolder != null) {
            return (SwipeMenuLayout) mViewHolder.itemView;
        } else {
            throw new NullPointerException("ViewHolder 为空");
        }
    }

    private OnSwipeListener mOnSwipeListener;

    public void setOnSwipeListener(OnSwipeListener mOnDelListener) {
        this.mOnSwipeListener = mOnDelListener;
    }

}
