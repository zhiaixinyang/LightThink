package com.example.greatbook.local.databinding;

import android.databinding.BindingAdapter;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.example.greatbook.nethot.adapter.GroupAndRecordsAdapter;
import com.example.greatbook.local.model.GroupAndRecords;
import com.example.greatbook.local.viewmodel.GroupAndRecordsBeanVM;
import com.example.greatbook.model.leancloud.LLocalRecord;
import com.example.greatbook.utils.DateUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MDove on 2017/8/24.
 */

public class GroupAndRecordsDataBinding {
    @BindingAdapter("groupAndRecordsData")
    public static void groupAndRecordsData(RecyclerView view, GroupAndRecords groupAndRecords) {
        List<GroupAndRecordsBeanVM> data_ = new ArrayList<>();
        GroupAndRecordsAdapter adapter = (GroupAndRecordsAdapter) view.getAdapter();
        if (adapter != null) {
            for (LLocalRecord lLocalRecord : groupAndRecords.data) {
                final GroupAndRecordsBeanVM recordRemarkBeanVM = new GroupAndRecordsBeanVM();
                recordRemarkBeanVM.content.set(lLocalRecord.getContent());
                recordRemarkBeanVM.time.set(DateUtils.getDateChinese(lLocalRecord.getCreatedAt()));
                recordRemarkBeanVM.title.set(lLocalRecord.getTitle());
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
