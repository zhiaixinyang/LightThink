package com.example.greatbook.diary.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.avos.avoscloud.AVUser;
import com.example.greatbook.MySharedPreferences;
import com.example.greatbook.R;
import com.example.greatbook.base.adapter.CommonAdapter;
import com.example.greatbook.base.adapter.ViewHolder;
import com.example.greatbook.greendao.entity.DiarySelf;
import com.example.greatbook.model.leancloud.User;
import com.example.greatbook.utils.DateUtils;
import com.example.greatbook.utils.DpUtils;
import com.example.greatbook.utils.GlideUtils;
import com.example.greatbook.utils.SelectorFactory;
import com.example.greatbook.widght.CircleImageView;
import com.example.greatbook.widght.itemswip.OnSwipeListener;
import com.example.greatbook.widght.itemswip.SwipeMenuLayout;

import java.util.Date;
import java.util.List;

/**
 * Created by MDove on 2017/10/3.
 */

public class DiarySelfActivityAdapter extends CommonAdapter<DiarySelf> {
    private ViewHolder mViewHolder;

    public DiarySelfActivityAdapter(Context context, int layoutId, List<DiarySelf> datas) {
        super(context, layoutId, datas);
    }

    @Override
    public void convert(final ViewHolder holder, DiarySelf diarySelf) {
        mViewHolder = holder;
        holder.setText(R.id.tv_diary_chat_self, diarySelf.content);
        holder.setText(R.id.tv_name, diarySelf.belongUserAccount);
        holder.getView(R.id.tv_diary_chat_self).setBackground(SelectorFactory.newShapeSelector()
                .setCornerRadius(DpUtils.dp2px(4))
                .setDefaultBgColor(ContextCompat.getColor(context, R.color.blue))
                .setFocusedBgColor(ContextCompat.getColor(context, R.color.blue))
                .create());
        holder.getView(R.id.tv_level).setBackground(SelectorFactory.newShapeSelector()
                .setCornerRadius(DpUtils.dp2px(4))
                .setStrokeWidth(DpUtils.dp2px(1))
                .setDefaultStrokeColor(ContextCompat.getColor(context, R.color.red))
                .create());
        holder.setText(R.id.tv_level,String.format(context.getResources()
                .getString(R.string.string_level),
                MySharedPreferences.getCurLevel()));

        holder.setText(R.id.tv_time_chat_self, DateUtils.getDateChinese(diarySelf.time));
        User currentUser = AVUser.getCurrentUser(User.class);
        holder.setImageResource(R.id.iv_chat_decorate, getResFromTime(diarySelf.time));
        if (currentUser != null) {
            GlideUtils.load(currentUser.getAvatar().getUrl(), (CircleImageView) holder.getView(R.id.iv_avatar_chat_self));
        }

        holder.setOnClickListener(R.id.btn_delete, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mOnSwipeListener) {
                    mOnSwipeListener.onDelete(holder.getAdapterPosition());
                }
            }
        });
    }

    private int getResFromTime(Date time) {
        if (DateUtils.getHour(time.getTime()) <= 9 && DateUtils.getHour(time.getTime()) > 6) {
            return R.drawable.icon_dawn;
        } else if (DateUtils.getHour(time.getTime()) <= 15 && DateUtils.getHour(time.getTime()) > 9) {
            return R.drawable.icon_dawn;
        } else if (DateUtils.getHour(time.getTime()) <= 18 && DateUtils.getHour(time.getTime()) > 15) {
            return R.drawable.icon_dusk;
        } else if (DateUtils.getHour(time.getTime()) <= 22 && DateUtils.getHour(time.getTime()) > 18) {
            return R.drawable.icon_night;
        }
        return R.drawable.icon_late_night;
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
