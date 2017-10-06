package com.example.greatbook.widght;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.greatbook.R;
import com.example.greatbook.utils.DateUtils;

import java.util.Date;

/**
 * Created by MDove on 2017/10/6.
 */

public class ShowTimeView extends RelativeLayout {
    private TextView mTvDay,mTvYearMonth,mTvWeek;

    public ShowTimeView(Context context) {
        this(context, null);
    }

    public ShowTimeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.view_show_time_style, this);
        mTvDay= (TextView) findViewById(R.id.tv_day);
        mTvYearMonth= (TextView) findViewById(R.id.tv_year_month);
        mTvWeek= (TextView) findViewById(R.id.tv_week);

        Date time=new Date();
        mTvDay.setText(DateUtils.getDay(time.getTime()));
        mTvYearMonth.setText(DateUtils.getYearMonth(time.getTime()));
        mTvWeek.setText(DateUtils.getDayOfWeek(time.getTime()));
    }

    public void setTime(Date time) {
        mTvDay.setText(DateUtils.getDay(time.getTime()));
        mTvYearMonth.setText(DateUtils.getYearMonth(time.getTime()));
        mTvWeek.setText(DateUtils.getDayOfWeek(time.getTime()));
    }
}
