package com.example.greatbook.nethot.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.greatbook.R;
import com.example.greatbook.databinding.ItemFragGroupRemarkBinding;
import com.example.greatbook.nethot.viewmodel.GroupRemarkBeanVM;

import java.util.List;

/**
 * Created by MDove on 2017/10/15.
 */

public class GroupRemarksAdapter extends RecyclerView.Adapter<GroupRemarksAdapter.ViewHolder> {
    private List<GroupRemarkBeanVM> data;
    private Context context;

    public GroupRemarksAdapter(Context context) {
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemFragGroupRemarkBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context),
                R.layout.item_frag_group_remark, parent, false);
        ViewHolder viewHolder = new ViewHolder(binding.getRoot());
        viewHolder.setBinding(binding);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ((ItemFragGroupRemarkBinding) holder.getBinding()).setRemarkVm(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data != null ? data.size() : 0;
    }

    public void setData(List<GroupRemarkBeanVM> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ViewDataBinding binding;

        public ViewHolder(View itemView) {
            super(itemView);
        }

        public ViewDataBinding getBinding() {
            return binding;
        }

        public void setBinding(ViewDataBinding binding) {
            this.binding = binding;
        }
    }
}
