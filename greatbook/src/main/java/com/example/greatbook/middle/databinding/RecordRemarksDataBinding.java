package com.example.greatbook.middle.databinding;

import android.databinding.BindingAdapter;
import android.support.v7.widget.RecyclerView;

import com.example.greatbook.middle.adapter.RecordRemarksAdapter;
import com.example.greatbook.middle.model.RecordRemark;
import com.example.greatbook.middle.model.leancloud.LRecordRemark;
import com.example.greatbook.middle.viewmodel.RecordRemarkBeanVM;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MDove on 2017/8/21.
 */

public class RecordRemarksDataBinding {
    @BindingAdapter("recordRemarkData")
    public static void recordRemarkData(RecyclerView view, RecordRemark recordRemark) {
        List<RecordRemarkBeanVM> data_=new ArrayList<>();
        RecordRemarksAdapter adapter= (RecordRemarksAdapter) view.getAdapter();
        if (adapter!=null){
            for (LRecordRemark lRecordRemark:recordRemark.date) {
                RecordRemarkBeanVM recordRemarkBeanVM = new RecordRemarkBeanVM();
                recordRemarkBeanVM.content.set(lRecordRemark.getContent());
                data_.add(recordRemarkBeanVM);
            }
            adapter.setData(data_);
        }
    }
}
