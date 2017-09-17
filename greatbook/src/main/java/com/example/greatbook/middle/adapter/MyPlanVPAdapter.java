package com.example.greatbook.middle.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.greatbook.R;
import com.example.greatbook.greendao.entity.MyPlan;
import com.example.greatbook.utils.DpUtils;
import com.example.greatbook.utils.LogUtils;
import com.example.greatbook.widght.VerticalTextView;

import java.util.List;

/**
 * Created by MDove on 2017/9/16.
 */

public class MyPlanVPAdapter extends PagerAdapter{
    private List<MyPlan> data;
    private Context context;

    public MyPlanVPAdapter(Context context,List<MyPlan> data) {
        this.context = context;
        this.data=data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View item= LayoutInflater.from(context).inflate(R.layout.item_my_plan,container,false);
        MyPlan myPlan=data.get(position);
        VerticalTextView textView= (VerticalTextView) item.findViewById(R.id.tv_plan_content);
        textView.setLineWidth(DpUtils.dp2px(60));
        textView.setTextSize(DpUtils.sp2px(myPlan.textSize+10));
        textView.setText(myPlan.content);
        textView.setTextColor(myPlan.textColor);
        item.setBackgroundColor(myPlan.bgColor);
        container.addView(item);
        return item;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = (View) object;
        container.removeView(view);
    }
}
