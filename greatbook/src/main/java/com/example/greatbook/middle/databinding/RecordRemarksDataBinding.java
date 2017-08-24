package com.example.greatbook.middle.databinding;

import android.databinding.BindingAdapter;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.example.greatbook.middle.adapter.RecordRemarksAdapter;
import com.example.greatbook.middle.model.RecordRemark;
import com.example.greatbook.middle.model.leancloud.LRecordRemark;
import com.example.greatbook.middle.viewmodel.RecordRemarkBeanVM;
import com.example.greatbook.model.leancloud.User;
import com.example.greatbook.utils.DateUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MDove on 2017/8/21.
 */

public class RecordRemarksDataBinding {
    @BindingAdapter("recordRemarkData")
    public static void recordRemarkData(RecyclerView view, RecordRemark recordRemark) {
        List<RecordRemarkBeanVM> data_ = new ArrayList<>();
        RecordRemarksAdapter adapter = (RecordRemarksAdapter) view.getAdapter();
        if (adapter != null) {
            for (LRecordRemark lRecordRemark : recordRemark.date) {
                final RecordRemarkBeanVM recordRemarkBeanVM = new RecordRemarkBeanVM();
                recordRemarkBeanVM.content.set(lRecordRemark.getContent());
                recordRemarkBeanVM.time.set(DateUtils.getDateChinese(lRecordRemark.getCreatedAt()));
                AVQuery<User> userAVQuery = AVQuery.getQuery(User.class);
                userAVQuery.findInBackground(new FindCallback<User>() {
                    @Override
                    public void done(List<User> list, AVException e) {
                        if (e == null && !list.isEmpty()) {
                            recordRemarkBeanVM.avatarPath.set(list.get(0).getAvatar().getUrl());
                            recordRemarkBeanVM.nick.set(list.get(0).getName());
                        }
                    }
                });
                data_.add(recordRemarkBeanVM);
            }
            adapter.setData(data_);
        }
    }

    @BindingAdapter("iconLikeRes")
    public static void iconLikeRes(ImageView imageView, int res) {
        imageView.setImageResource(res);
    }
}
