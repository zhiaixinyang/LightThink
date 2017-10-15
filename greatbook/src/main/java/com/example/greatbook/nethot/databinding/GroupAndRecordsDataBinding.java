package com.example.greatbook.nethot.databinding;

import android.databinding.BindingAdapter;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.example.greatbook.model.leancloud.LLocalRecord;
import com.example.greatbook.nethot.adapter.GroupRecordsAdapter;
import com.example.greatbook.nethot.model.GroupRecords;
import com.example.greatbook.nethot.viewmodel.GroupAndRecordsBeanVM;
import com.example.greatbook.utils.DateUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MDove on 2017/8/24.
 */

public class GroupAndRecordsDataBinding {
    @BindingAdapter("groupAndRecordsData")
    public static void groupAndRecordsData(RecyclerView view, GroupRecords groupRecords) {
        List<GroupAndRecordsBeanVM> data_ = new ArrayList<>();
        GroupRecordsAdapter adapter = (GroupRecordsAdapter) view.getAdapter();
        if (adapter != null && groupRecords != null && !groupRecords.data.isEmpty()) {
            for (LLocalRecord lLocalRecord : groupRecords.data) {
                final GroupAndRecordsBeanVM recordRemarkBeanVM = new GroupAndRecordsBeanVM();
                recordRemarkBeanVM.content.set(lLocalRecord.getContent());
                recordRemarkBeanVM.time.set(DateUtils.getDateChinese(lLocalRecord.getCreatedAt()));
                recordRemarkBeanVM.title.set(lLocalRecord.getTitle());
                data_.add(recordRemarkBeanVM);
            }
            adapter.setData(data_);
        }
    }


    @BindingAdapter("iconFollowerRes")
    public static void iconFollowerRes(ImageView imageView, int res) {
        imageView.setImageResource(res);
    }
}
