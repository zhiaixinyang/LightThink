package com.example.ywb.recyclerviewtest;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Administrator on 2016/2/23.
 */
public class WaterFallAdapter extends RecyclerView.Adapter<WaterFallAdapter.WaterFallItemHolder> {
    private Context mContext;
    private LayoutInflater mInflater;
    public List<String> mDatas;
    private List<Integer> heights;

    public WaterFallAdapter(Context context, List<String> datas) {
        this.mContext = context;
        this.mDatas = datas;
        mInflater = LayoutInflater.from(mContext);
        heights = new ArrayList<>();
        for (int i = 0;i<mDatas.size();i++){
            heights.add((int)(100+Math.random()*300));
        }
    }

    @Override
    public void onBindViewHolder(WaterFallAdapter.WaterFallItemHolder holder, final int position) {
        holder.textView.setText(mDatas.get(position));
        ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
        lp.height = heights.get(position);
        holder.itemView.setLayoutParams(lp);
    }

    @Override
    public WaterFallAdapter.WaterFallItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.waterfall_item, parent, false);
        WaterFallItemHolder mItemViewHolder = new WaterFallItemHolder(view);
        return mItemViewHolder;
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class WaterFallItemHolder extends RecyclerView.ViewHolder {
        protected TextView textView;
        public WaterFallItemHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.text_view);
        }
    }
}


