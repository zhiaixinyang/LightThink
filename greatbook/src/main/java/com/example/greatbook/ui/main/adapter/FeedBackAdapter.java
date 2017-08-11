package com.example.greatbook.ui.main.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.example.greatbook.R;
import com.example.greatbook.base.ListBaseAdapter;
import com.example.greatbook.base.adapter.CommonAdapter;
import com.example.greatbook.base.adapter.ViewHolder;
import com.example.greatbook.model.leancloud.User;
import com.example.greatbook.ui.model.LFeedBackBean;
import com.example.greatbook.utils.GlideUtils;
import com.example.greatbook.utils.StringUtils;
import com.example.greatbook.widght.CircleImageView;

import java.text.SimpleDateFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MBENBEN on 2017/8/10.
 */

public class FeedBackAdapter extends CommonAdapter<LFeedBackBean> {
    private SimpleDateFormat simpleDateFormat;
    public FeedBackAdapter(Context context, int layoutId, List<LFeedBackBean> datas) {
        super(context, layoutId, datas);
        simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd hh:mm");
    }


    @Override
    public void convert(final com.example.greatbook.base.adapter.ViewHolder holder, final LFeedBackBean lFeedBackBean) {
        holder.setText(R.id.tv_time,simpleDateFormat.format(lFeedBackBean.getCreatedAt()));
        holder.setText(R.id.tv_content,lFeedBackBean.getContent());
        holder.setText(R.id.tv_good_num,lFeedBackBean.getLike()+"");
        AVQuery<User> query = AVQuery.getQuery(User.class);
        query.whereEqualTo("objectId", lFeedBackBean.getBelongId());
        query.findInBackground(new FindCallback<User>() {
            @Override
            public void done(List<User> list, AVException e) {
                if (e == null) {
                    GlideUtils.load(list.get(0).getAvatar().getUrl(), (CircleImageView) holder.getView(R.id.iv_avatar));
                    if (StringUtils.isEmpty(list.get(0).getName())) {
                        holder.setText(R.id.tv_nick,"书心用户");
                    } else {
                        holder.setText(R.id.tv_nick,list.get(0).getName());
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
