package com.example.greatbook.ui.main.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.example.greatbook.R;
import com.example.greatbook.base.adapter.CommonAdapter;
import com.example.greatbook.model.leancloud.TalkAboutBean;
import com.example.greatbook.model.leancloud.User;
import com.example.greatbook.utils.DateUtils;
import com.example.greatbook.utils.GlideUtils;
import com.example.greatbook.utils.StringUtils;
import com.example.greatbook.widght.CircleImageView;

import java.util.List;

/**
 * Created by MBENBEN on 2016/11/24.
 */

public class TalkAboutAdapter extends CommonAdapter<TalkAboutBean> {

    public TalkAboutAdapter(Context context, int layoutId, List<TalkAboutBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    public void convert(final com.example.greatbook.base.adapter.ViewHolder holder, TalkAboutBean talkAboutBean) {
        AVQuery<User> query = AVQuery.getQuery(User.class);
        query.whereEqualTo("objectId", talkAboutBean.getBelongId());
        query.findInBackground(new FindCallback<User>() {
            @Override
            public void done(List<User> list, AVException e) {
                if (e == null&&!list.isEmpty()) {
                    GlideUtils.load(list.get(0).getAvatar().getUrl(), (CircleImageView) holder.getView(R.id.iv_avatar_item_talkabout));
                    if (StringUtils.isEmpty(list.get(0).getName())) {
                        holder.setText(R.id.tv_name_item_talkabout,"书心用户");
                    } else {
                        holder.setText(R.id.tv_name_item_talkabout,list.get(0).getName());
                    }
                }
            }
        });
        GlideUtils.load(talkAboutBean.getContentPhoto().getUrl(), (ImageView) holder.getView(R.id.iv_photo_item_talkabout));
        holder.setText(R.id.tv_content_item_talkabout,talkAboutBean.getContent());
        holder.setText(R.id.tv_time_item_talkabout, DateUtils.getDateChinese(talkAboutBean.getCreatedAt()));
    }

}
