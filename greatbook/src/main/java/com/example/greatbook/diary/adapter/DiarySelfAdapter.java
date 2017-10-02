package com.example.greatbook.diary.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.example.greatbook.R;
import com.example.greatbook.base.adapter.CommonAdapter;
import com.example.greatbook.base.adapter.ViewHolder;
import com.example.greatbook.greendao.entity.DiarySelf;
import com.example.greatbook.model.leancloud.User;
import com.example.greatbook.utils.GlideUtils;
import com.example.greatbook.widght.CircleImageView;

import java.util.List;

/**
 * Created by MDove on 2017/10/2.
 */

public class DiarySelfAdapter extends CommonAdapter<DiarySelf> {
    private Context mContext;

    public DiarySelfAdapter(Context context, int layoutId, List<DiarySelf> datas) {
        super(context, layoutId, datas);
        mContext = context;
    }

    @Override
    public void convert(ViewHolder holder, DiarySelf diarySelf) {
        holder.setText(R.id.tv_diary_chat_self, diarySelf.content);
        User currentUser = AVUser.getCurrentUser(User.class);
        if (currentUser!=null) {
            GlideUtils.load(currentUser.getAvatar().getUrl(), (CircleImageView) holder.getView(R.id.iv_avatar_chat_self));
        }
    }
}
