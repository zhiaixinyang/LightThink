package com.example.greatbook.middle.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.greatbook.R;
import com.example.greatbook.base.adapter.CommonAdapter;
import com.example.greatbook.base.adapter.ViewHolder;
import com.example.greatbook.greendao.entity.LocalGroup;
import com.example.greatbook.utils.LogUtils;
import com.example.greatbook.utils.StringUtils;
import com.example.greatbook.utils.ToastUtil;
import com.example.greatbook.utils.anim.SpringAnimationInterpolar;
import com.example.greatbook.widght.itemswip.OnSwipeListener;
import com.example.greatbook.widght.itemswip.SwipeMenuLayout;
import com.iflytek.cloud.thirdparty.V;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MDove on 2017/8/13.
 */

public class SetGroupsAdapter extends CommonAdapter<LocalGroup> {
    public SetGroupsAdapter(Context context, int layoutId, List<LocalGroup> datas) {
        super(context, layoutId, datas);
    }

    @Override
    public void convert(final ViewHolder holder, final LocalGroup localGroup) {
        holder.setOnClickListener(R.id.btn_alter, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (localGroup.getId()>3) {
                    mOnSwipeListener.onAlter(holder.getAdapterPosition());
                }else{
                    ToastUtil.toastShort("不修改删掉默认的文库");
                }
            }
        });
        holder.setOnClickListener(R.id.btn_delete, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mOnSwipeListener) {
                    if (localGroup.getId()>3) {
                        mOnSwipeListener.onDelete(holder.getAdapterPosition());
                    }else{
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
                localGroup.getContent())?localGroup.getTitle():"未设置文集名称");
        holder.setText(R.id.tv_group_content,!StringUtils.isEmpty(
                localGroup.getContent())?localGroup.getContent():"未设置文集介绍");

        View ivUserd=holder.getView(R.id.iv_userd);
        if (localGroup.getIsUserd()){
            ivUserd.setVisibility(View.VISIBLE);
        }else{
            ivUserd.setVisibility(View.GONE);
        }
    }

    private OnSwipeListener mOnSwipeListener;

    public void setOnSwipeListener(OnSwipeListener mOnDelListener) {
        this.mOnSwipeListener = mOnDelListener;
    }

}

