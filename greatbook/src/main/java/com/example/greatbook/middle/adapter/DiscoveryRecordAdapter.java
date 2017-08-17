package com.example.greatbook.middle.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.example.greatbook.R;
import com.example.greatbook.base.adapter.CommonAdapter;
import com.example.greatbook.base.adapter.ViewHolder;
import com.example.greatbook.middle.model.DiscoveryRecord;
import com.example.greatbook.model.leancloud.User;
import com.example.greatbook.utils.DateUtils;
import com.example.greatbook.utils.GlideUtils;
import com.example.greatbook.utils.LogUtils;
import com.example.greatbook.widght.CircleImageView;

import java.util.List;

/**
 * Created by MDove on 2017/8/14.
 */

public class DiscoveryRecordAdapter extends CommonAdapter<DiscoveryRecord> {
    public DiscoveryRecordAdapter(Context context, int layoutId, List<DiscoveryRecord> datas) {
        super(context, layoutId, datas);
    }

    @Override
    public void convert(final ViewHolder holder, DiscoveryRecord discoveryRecord) {
        holder.setText(R.id.tv_title,discoveryRecord.title);
        holder.setText(R.id.tv_content,discoveryRecord.content);
        holder.setText(R.id.tv_group_title,discoveryRecord.groupTitle);
        holder.setText(R.id.tv_time, DateUtils.getDateChinese(discoveryRecord.time));
        GlideUtils.load(discoveryRecord.groupPhotoPath, (ImageView) holder.getView(R.id.iv_group));
        AVQuery<User> query=AVQuery.getQuery(User.class);
        query.whereEqualTo("objectId",discoveryRecord.belongId);
        query.findInBackground(new FindCallback<User>() {
            @Override
            public void done(List<User> list, AVException e) {
                if (e==null&&!list.isEmpty()){
                    User user=list.get(0);
                    GlideUtils.load(user.getAvatar().getUrl(), (CircleImageView) holder.getView(R.id.iv_avatar));
                    holder.setText(R.id.tv_nick,user.getName());
                }
            }
        });
    }
}
