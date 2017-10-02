package com.example.greatbook.main.adapter;

import android.content.Context;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.example.greatbook.R;
import com.example.greatbook.base.adapter.CommonAdapter;
import com.example.greatbook.model.leancloud.User;
import com.example.greatbook.main.model.LFeedBackBean;
import com.example.greatbook.utils.DateUtils;
import com.example.greatbook.utils.GlideUtils;
import com.example.greatbook.utils.StringUtils;
import com.example.greatbook.widght.CircleImageView;

import java.util.List;

/**
 * Created by MDove on 2017/8/10.
 */

public class FeedBackAdapter extends CommonAdapter<LFeedBackBean> {

    public FeedBackAdapter(Context context, int layoutId, List<LFeedBackBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    public void convert(final com.example.greatbook.base.adapter.ViewHolder holder, final LFeedBackBean lFeedBackBean) {
        holder.setText(R.id.tv_time, DateUtils.getDateEnglish(lFeedBackBean.getCreatedAt()));
        holder.setText(R.id.tv_content, lFeedBackBean.getContent());
        holder.setText(R.id.tv_good_num, lFeedBackBean.getLike() + "");
        AVQuery<User> query = AVQuery.getQuery(User.class);
        query.whereEqualTo("objectId", lFeedBackBean.getBelongId());
        query.findInBackground(new FindCallback<User>() {
            @Override
            public void done(List<User> list, AVException e) {
                if (e == null && !list.isEmpty()) {
                    GlideUtils.load(list.get(0).getAvatar().getUrl(), (CircleImageView) holder.getView(R.id.iv_avatar));
                    if (StringUtils.isEmpty(list.get(0).getName())) {
                        holder.setText(R.id.tv_nick, "书心用户");
                    } else {
                        holder.setText(R.id.tv_nick, list.get(0).getName());
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
