package com.example.greatbook.diary.adapter;

import android.content.Context;

import com.avos.avoscloud.AVUser;
import com.example.greatbook.R;
import com.example.greatbook.base.adapter.CommonAdapter;
import com.example.greatbook.base.adapter.ViewHolder;
import com.example.greatbook.greendao.entity.DiarySelf;
import com.example.greatbook.model.leancloud.User;
import com.example.greatbook.utils.DateUtils;
import com.example.greatbook.utils.GlideUtils;
import com.example.greatbook.widght.CircleImageView;

import java.util.Date;
import java.util.List;

/**
 * Created by MDove on 2017/10/2.
 */

public class DiarySelfFragAdapter extends CommonAdapter<DiarySelf> {
    private Context mContext;

    public DiarySelfFragAdapter(Context context, int layoutId, List<DiarySelf> datas) {
        super(context, layoutId, datas);
        mContext = context;
    }

    @Override
    public void convert(ViewHolder holder, DiarySelf diarySelf) {
        holder.setText(R.id.tv_diary_chat_self, diarySelf.content);
        holder.setText(R.id.tv_time_chat_self, DateUtils.getDateChinese(diarySelf.time));
        User currentUser = AVUser.getCurrentUser(User.class);
        holder.setImageResource(R.id.iv_chat_decorate, getResFromTime(diarySelf.time));
        if (currentUser != null) {
            GlideUtils.load(currentUser.getAvatar().getUrl(), (CircleImageView) holder.getView(R.id.iv_avatar_chat_self));
        }
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
}
