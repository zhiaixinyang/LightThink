package com.example.greatbook.middle.databinding;

import android.databinding.BindingAdapter;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.example.greatbook.middle.adapter.GroupAndRecordsAdapter;
import com.example.greatbook.middle.adapter.RecordRemarksAdapter;
import com.example.greatbook.middle.model.GroupAndRecords;
import com.example.greatbook.middle.model.RecordRemark;
import com.example.greatbook.middle.model.leancloud.LRecordRemark;
import com.example.greatbook.middle.viewmodel.GroupAndRecordsBeanVM;
import com.example.greatbook.middle.viewmodel.RecordRemarkBeanVM;
import com.example.greatbook.model.leancloud.LLocalRecord;
import com.example.greatbook.model.leancloud.User;
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
