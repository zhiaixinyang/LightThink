package com.example.greatbook.nethot.databinding;

import android.databinding.BindingAdapter;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.example.greatbook.local.model.leancloud.LGroupRemark;
import com.example.greatbook.model.leancloud.User;
import com.example.greatbook.nethot.adapter.GroupRemarksAdapter;
import com.example.greatbook.nethot.adapter.RecordRemarksAdapter;
import com.example.greatbook.nethot.model.GroupRemarks;
import com.example.greatbook.nethot.viewmodel.GroupRemarkBeanVM;
import com.example.greatbook.utils.DateUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MDove on 2017/10/15.
 */

public class GroupAndRemarksDataBinding {
    @BindingAdapter("groupAndRemarksData")
    public static void groupAndRemarksData(RecyclerView view, GroupRemarks groupAndRecords) {
        List<GroupRemarkBeanVM> data_ = new ArrayList<>();
        GroupRemarksAdapter adapter = (GroupRemarksAdapter) view.getAdapter();
        if (adapter != null&&groupAndRecords!=null&&!groupAndRecords.data.isEmpty()) {
            for (LGroupRemark lGroupRemark : groupAndRecords.data) {
                final GroupRemarkBeanVM groupRemarkBeanVM = new GroupRemarkBeanVM();
                groupRemarkBeanVM.content.set(lGroupRemark.getContent());
                groupRemarkBeanVM.time.set(DateUtils.getDateChinese(lGroupRemark.getCreatedAt()));
                AVQuery<User> userAVQuery = AVQuery.getQuery(User.class);
                userAVQuery.findInBackground(new FindCallback<User>() {
                    @Override
                    public void done(List<User> list, AVException e) {
                        if (e == null && !list.isEmpty()) {
                            groupRemarkBeanVM.avatarPath.set(list.get(0).getAvatar().getUrl());
                            groupRemarkBeanVM.nick.set(list.get(0).getName());
                        }
                    }
                });
                data_.add(groupRemarkBeanVM);
            }
            adapter.setData(data_);
        }
    }
}
