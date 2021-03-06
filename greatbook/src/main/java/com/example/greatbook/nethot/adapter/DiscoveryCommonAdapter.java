package com.example.greatbook.nethot.adapter;

import android.content.Context;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.example.greatbook.R;
import com.example.greatbook.base.adapter.CommonAdapter;
import com.example.greatbook.base.adapter.ViewHolder;
import com.example.greatbook.nethot.model.DiscoveryRecord;
import com.example.greatbook.model.leancloud.LLocalGroup;
import com.example.greatbook.model.leancloud.User;
import com.example.greatbook.utils.DateUtils;
import com.example.greatbook.utils.GlideUtils;
import com.example.greatbook.utils.StringUtils;
import com.example.greatbook.widght.CircleImageView;
import com.example.greatbook.widght.RoundImageView;

import java.util.List;

/**
 * Created by MDove on 2017/8/14.
 */

public class DiscoveryCommonAdapter extends CommonAdapter<DiscoveryRecord> {
    public DiscoveryCommonAdapter(Context context, int layoutId, List<DiscoveryRecord> datas) {
        super(context, layoutId, datas);
    }

    @Override
    public void convert(final ViewHolder holder, final DiscoveryRecord discoveryRecord) {
        holder.setText(R.id.tv_title, discoveryRecord.title);
        holder.setText(R.id.tv_content, discoveryRecord.content);
        holder.setText(R.id.tv_good_num, discoveryRecord.likeNum + "");
        holder.setText(R.id.tv_time, DateUtils.getDateChinese(discoveryRecord.time));
        AVQuery<User> query = AVQuery.getQuery(User.class);
        query.whereEqualTo("objectId", discoveryRecord.belongId);
        query.findInBackground(new FindCallback<User>() {
            @Override
            public void done(List<User> list, AVException e) {
                if (e == null && !list.isEmpty()) {
                    User user = list.get(0);
                    GlideUtils.loadSmallAvatar(user.getAvatar().getUrl(), (CircleImageView) holder.getView(R.id.iv_avatar));
                    holder.setText(R.id.tv_nick, user.getName());
                    //查找用户对应的文集
                    AVQuery<LLocalGroup> query1 = AVQuery.getQuery(LLocalGroup.class);
                    query1.whereEqualTo("belongId", list.get(0).getObjectId());
                    query1.whereEqualTo("groupId", discoveryRecord.groupId);
                    query1.findInBackground(new FindCallback<LLocalGroup>() {
                        @Override
                        public void done(List<LLocalGroup> list, AVException e) {
                            if (e == null && !list.isEmpty()) {
                                LLocalGroup lLocalGroup = list.get(0);
                                String url = lLocalGroup.getGroupPhoto().getUrl();
                                if (!StringUtils.isEmpty(url)) {
                                    GlideUtils.loadSmallIv(lLocalGroup.getGroupPhoto().getUrl(), (RoundImageView) holder.getView(R.id.iv_group));
                                }else{
                                    GlideUtils.load(R.drawable.icon_default_group_jok,(RoundImageView) holder.getView(R.id.iv_group));
                                }
                                holder.setText(R.id.tv_group_title, lLocalGroup.getGroupTitle());
                            }
                        }
                    });
                }
            }
        });
    }
}
