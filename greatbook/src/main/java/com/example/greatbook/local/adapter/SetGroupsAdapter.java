package com.example.greatbook.local.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;

import com.example.greatbook.R;
import com.example.greatbook.base.adapter.CommonAdapter;
import com.example.greatbook.base.adapter.ViewHolder;
import com.example.greatbook.greendao.entity.LocalGroup;
import com.example.greatbook.utils.GlideUtils;
import com.example.greatbook.utils.LogUtils;
import com.example.greatbook.utils.StringUtils;
import com.example.greatbook.utils.ToastUtil;
import com.example.greatbook.widght.RoundImageView;
import com.example.greatbook.widght.itemswip.OnSwipeListener;
import com.example.greatbook.widght.itemswip.SwipeMenuLayout;

import java.util.List;

/**
 * Created by MDove on 2017/8/13.
 */

public class SetGroupsAdapter extends CommonAdapter<LocalGroup> {
    private ViewHolder mViewHolder;

    public SetGroupsAdapter(Context context, int layoutId, List<LocalGroup> datas) {
        super(context, layoutId, datas);
    }

    @Override
    public void convert(final ViewHolder holder, final LocalGroup localGroup) {
        mViewHolder = holder;
        holder.setOnClickListener(R.id.btn_alter, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (localGroup.getId() > 3) {
                    mOnSwipeListener.onAlter(holder.getAdapterPosition());
                } else {
                    ToastUtil.toastShort("不修改默认的文库");
                }
            }
        });
        holder.setOnClickListener(R.id.btn_delete, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mOnSwipeListener) {
                    if (localGroup.getId() > 3) {
                        mOnSwipeListener.onDelete(holder.getAdapterPosition());
                    } else {
                        ToastUtil.toastShort("不可以删掉默认的文库");
                    }
                }
            }
        });
        holder.setOnClickListener(R.id.btn_top, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnSwipeListener.onTop(holder.getAdapterPosition());

            }
        });
        holder.setText(R.id.tv_group_title, !StringUtils.isEmpty(
                localGroup.getContent()) ? localGroup.getTitle() : "未设置文集名称");
        holder.setText(R.id.tv_group_content, !StringUtils.isEmpty(
                localGroup.getContent()) ? localGroup.getContent() : "未设置文集介绍");

        View ivUserd = holder.getView(R.id.iv_userd);
        if (localGroup.getIsUserd()) {
            ivUserd.setVisibility(View.VISIBLE);
        } else {
            ivUserd.setVisibility(View.GONE);
        }
        if (!StringUtils.isEmpty(localGroup.getGroupPhotoPath())) {
            holder.getView(R.id.iv_group).setTag(localGroup.getGroupPhotoPath());
            if (holder.getView(R.id.iv_group).getTag().equals(localGroup.getGroupPhotoPath())) {
                GlideUtils.loadSmallIv(localGroup.getGroupPhotoPath(), (RoundImageView) holder.getView(R.id.iv_group));
            }
        } else {
            holder.getView(R.id.iv_group).setTag(localGroup.getGroupPhotoPath());
            if (holder.getView(R.id.iv_group).getTag().equals(localGroup.getGroupPhotoPath())) {
                holder.setImageResource(R.id.iv_group, localGroup.getGroupLocalPhotoPath());
            }
        }
    }

    private OnSwipeListener mOnSwipeListener;

    public void setOnSwipeListener(OnSwipeListener mOnDelListener) {
        this.mOnSwipeListener = mOnDelListener;
    }

    public SwipeMenuLayout getSwipeItem() {
        if (mViewHolder != null) {
            return (SwipeMenuLayout) mViewHolder.itemView;
        } else {
            throw new NullPointerException("ViewHolder 为空");
        }
    }

}

