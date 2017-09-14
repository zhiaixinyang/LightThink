package com.example.greatbook.middle.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.greatbook.R;
import com.example.greatbook.base.adapter.OnItemClickListener;
import com.example.greatbook.middle.model.MainMenuItemBean;
import com.example.greatbook.utils.LogUtils;
import com.example.greatbook.widght.SimpleRoundImageView;
import com.example.greatbook.widght.swipecard.SwipeFlingAdapterView;

import java.util.List;

/**
 * Created by MDove on 2017/8/26.
 */

public class MainMenuAdapter extends RecyclerView.Adapter<MainMenuAdapter.ViewHolder> {
    private List<MainMenuItemBean> data;
    private Context context;
    private OnItemClickListener listener;

    public MainMenuAdapter(Context context,List<MainMenuItemBean> data) {
        this.data = data;
        this.context=context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_rlv_main_menu,parent,false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final MainMenuItemBean bean=data.get(position);
        holder.imageView.setInText(bean.inText);
        holder.imageView.setOutText(bean.outText);
        holder.imageView.setTextInColor(bean.inColor);
        holder.imageView.setTextOutColor(bean.outColor);
        holder.imageView.setBgColor(bean.bgColor);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener!=null){
                    listener.onItemClick(v,bean,position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public SimpleRoundImageView imageView;
        public ViewHolder(View itemView) {
            super(itemView);
            imageView= (SimpleRoundImageView) itemView.findViewById(R.id.main_menu);
        }
    }

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
