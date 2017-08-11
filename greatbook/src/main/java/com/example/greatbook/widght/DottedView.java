package com.example.greatbook.widght;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import com.example.greatbook.R;
import com.example.greatbook.utils.DpUtils;

/**
 * Created by MDove on 2017/8/11.
 */

public class DottedView extends View {
    private Paint paint;
    private int dottedViewLineColor;
    private int dottedViewLineInterval;
    private int dottedViewLineHeight;
    private int dottedViewLineWidth;

    public DottedView(Context context) {
        super(context);
        init(context,null);
    }

    private void init(Context context,AttributeSet attrs) {
        if (attrs!=null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.DottedView);
            dottedViewLineColor = typedArray.getColor(R.styleable.DottedView_dv_line_color, ContextCompat.getColor(context, R.color.black));
            dottedViewLineInterval = (int) typedArray.getDimension(R.styleable.DottedView_dv_line_interval, DpUtils.dp2px(6));
            dottedViewLineHeight = (int) typedArray.getDimension(R.styleable.DottedView_dv_line_height, DpUtils.dp2px(2));
            dottedViewLineWidth = (int) typedArray.getDimension(R.styleable.DottedView_dv_line_width, DpUtils.dp2px(12));
            typedArray.recycle();
        }
        paint=new Paint();
        paint.setColor(dottedViewLineColor);
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setStrokeWidth(dottedViewLineHeight);
    }

    public DottedView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    public DottedView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int allWidth=getMeasuredWidth();
        int curWidt=0;
        while (curWidt<allWidth){
            canvas.drawLine(dottedViewLineInterval+curWidt,0,curWidt+dottedViewLineWidth,0,paint);
            curWidt=curWidt+dottedViewLineInterval+dottedViewLineWidth;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int heightMode=MeasureSpec.getMode(heightMeasureSpec);
        int heightSize=MeasureSpec.getSize(heightMeasureSpec);
        int widthSize=MeasureSpec.getSize(widthMeasureSpec);
        if (heightMode==MeasureSpec.AT_MOST){
            heightSize=dottedViewLineHeight;
        }
        setMeasuredDimension(widthSize,heightSize);
    }
}
