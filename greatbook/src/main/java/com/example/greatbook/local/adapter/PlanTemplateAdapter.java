package com.example.greatbook.local.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.greatbook.R;
import com.example.greatbook.base.adapter.OnItemClickListener;
import com.example.greatbook.greendao.entity.MyPlanTemplate;
import com.example.greatbook.utils.DpUtils;
import com.example.greatbook.widght.VerticalTextView;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by MDove on 2017/9/16.
 */

public class PlanTemplateAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<MyPlanTemplate> data;
    private LayoutInflater inflater;
    private final int ADD_ITEM = 1;
    private final int NORMAL_ITEM = 2;
    private OnItemClickListener onItemClickListener;

    public PlanTemplateAdapter(Context context, List<MyPlanTemplate> datas) {
        this.context = context;
        this.data = datas;
        inflater = LayoutInflater.from(this.context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ADD_ITEM) {
            return new AddViewHolder(inflater.inflate(R.layout.item_add_my_template_plan, parent, false));
        } else {
            return new NormalViewHolder(inflater.inflate(R.layout.item_my_template_plan, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof AddViewHolder) {
            if (onItemClickListener != null) {
                ((AddViewHolder) holder).btnAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onItemClickListener.onItemClick(((AddViewHolder) holder).btnAdd, null, position);
                    }
                });
            }
        } else {
            //position共计data.size()+1，因为还有一个add的Item
            final MyPlanTemplate myPlanTemplate = data.get(position-1);

            if (onItemClickListener != null) {
                ((NormalViewHolder) holder).bg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onItemClickListener.onItemClick(((NormalViewHolder) holder).bg, myPlanTemplate, position);
                    }
                });
            }
//            ((NormalViewHolder) holder).textView.setText(myPlanTemplate.content);
            ((NormalViewHolder) holder).textView.setTextColor(myPlanTemplate.textColor);
            ((NormalViewHolder) holder).textView.setTextSize(DpUtils.sp2px(myPlanTemplate.textSize));
            ((NormalViewHolder) holder).textView.setLineWidth(DpUtils.dp2px(25));
            ((NormalViewHolder) holder).bg.setBackgroundColor(myPlanTemplate.bgColor);
            SpannableString spannableString = new SpannableString(myPlanTemplate.content);
            String regex = "((?i)我)";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(myPlanTemplate.content);
            while (matcher.find()) {
                ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(myPlanTemplate.detailColor);
                spannableString.setSpan(foregroundColorSpan, matcher.start(), matcher.end(), SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);

            }
            ((NormalViewHolder) holder).textView.setText(spannableString.toString());

        }
    }

    @Override
    public int getItemCount() {
        if (data.size() == 0) {
            return 1;
        } else {
            return data.size() + 1;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return ADD_ITEM;
        } else {
            return NORMAL_ITEM;
        }
    }

    public class NormalViewHolder extends RecyclerView.ViewHolder {
        private VerticalTextView textView;
        private RelativeLayout bg;

        public NormalViewHolder(View itemView) {
            super(itemView);
            textView = (VerticalTextView) itemView.findViewById(R.id.tv_template);
            bg = (RelativeLayout) itemView.findViewById(R.id.bg);
        }
    }

    public class AddViewHolder extends RecyclerView.ViewHolder {
        private RelativeLayout btnAdd;

        public AddViewHolder(View itemView) {
            super(itemView);
            btnAdd = (RelativeLayout) itemView.findViewById(R.id.bg);
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setData(List<MyPlanTemplate> data) {
        this.data = data;
        notifyDataSetChanged();
    }
}
