package com.example.greatbook.middle.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.example.greatbook.R;
import com.example.greatbook.base.adapter.CommonAdapter;
import com.example.greatbook.base.adapter.ViewHolder;
import com.example.greatbook.middle.model.DiscoveryTopGroup;
import com.example.greatbook.model.leancloud.User;
import com.example.greatbook.utils.GlideUtils;
import com.example.greatbook.utils.StringUtils;
import com.example.greatbook.widght.CircleImageView;
import com.example.greatbook.widght.RoundImageView;

import java.util.List;

/**
 * Created by MDove on 2017/8/13.
 */

public class MiddleDiscoveryAdapter extends CommonAdapter<DiscoveryTopGroup> {
    public MiddleDiscoveryAdapter(Context context, int layoutId, List<DiscoveryTopGroup> datas) {
        super(context, layoutId, datas);
    }

    @Override
    public void convert(final ViewHolder holder, DiscoveryTopGroup discoveryTopGroup) {
        holder.setText(R.id.tv_discovery_top_num, discoveryTopGroup.attentionNum > 99 ?
                "99+" : discoveryTopGroup.attentionNum + "");
        holder.setText(R.id.tv_discovery_top_group_name,
                !StringUtils.isEmpty(discoveryTopGroup.groupTitle) ? discoveryTopGroup.groupTitle : "未设置标题");
        if (!StringUtils.isEmpty(discoveryTopGroup.groupPhotoPath)) {
            GlideUtils.loadSmallIv(discoveryTopGroup.groupPhotoPath, (RoundImageView) holder.getView(R.id.iv_group));
        }else{
            GlideUtils.load(R.drawable.icon_default_group_jok,(RoundImageView) holder.getView(R.id.iv_group));
        }
        AVQuery<User> query = AVQuery.getQuery(User.class);
        query.whereEqualTo("objectId", discoveryTopGroup.belongId);
        query.findInBackground(new FindCallback<User>() {
            @Override
            public void done(List<User> list, AVException e) {
                if (e == null && !list.isEmpty()) {
                    User user = list.get(0);
                    GlideUtils.loadSmallAvatar(user.getAvatar().getUrl(), (CircleImageView) holder.getView(R.id.iv_discovery_top_user_avatar));
                    holder.setText(R.id.tv_discovery_top_user_name, !StringUtils.isEmpty(user.getName()) ? user.getName() : "匿名用户");
                }
            }
        });
    }
}
