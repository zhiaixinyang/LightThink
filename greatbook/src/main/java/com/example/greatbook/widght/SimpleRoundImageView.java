package com.example.greatbook.widght;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.greatbook.R;
import com.example.greatbook.utils.DpUtils;
import com.example.greatbook.utils.LogUtils;
import com.example.greatbook.utils.ScreenUtils;

/**
 * Created by MDove on 2017/8/26.
 */

public class SimpleRoundImageView extends View {
    private int bgColor;
    private int textInColor;
    private float textInSize;
    private int textOutColor;
    private float textOutSize;
    private Paint bgPaint;
    private Paint textInPaint, textOutPaint;
    private String textIn, textOut;
    private int width, height;

    public SimpleRoundImageView(Context context) {
        this(context, null);
        setWillNotDraw(false);
    }

    public SimpleRoundImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
        setWillNotDraw(false);
    }

    public SimpleRoundImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setWillNotDraw(false);

        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.SimpleRoundImageView);

        bgColor = array.getColor(R.styleable.SimpleRoundImageView_simple_bg_color, Color.WHITE);
        textInColor = array.getColor(R.styleable.SimpleRoundImageView_simple_text_in_color, Color.WHITE);
        textOutColor = array.getColor(R.styleable.SimpleRoundImageView_simple_text_out_color, Color.WHITE);

        textInSize = array.getDimension(R.styleable.SimpleRoundImageView_simple_text_in_size, DpUtils.sp2px(10));
        textOutSize = array.getDimension(R.styleable.SimpleRoundImageView_simple_text_out_size, DpUtils.sp2px(10));
        textOut = array.getString(R.styleable.SimpleRoundImageView_simple_text_out);
        textIn = array.getString(R.styleable.SimpleRoundImageView_simple_text_in);

        array.recycle();

        bgPaint = new Paint();
        bgPaint.setColor(bgColor);


        textInPaint = new Paint();
        textInPaint.setColor(textInColor);
        textInPaint.setTextSize(textInSize);

        textOutPaint = new Paint();
        textOutPaint.setTextSize(textOutSize);
        textOutPaint.setColor(textOutColor);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);

        if (widthMode == MeasureSpec.AT_MOST) {
            width = ScreenUtils.getScreenWidth() / 6;
            this.width = width;
        }
        height = (int) (width + textOutSize + DpUtils.dp2px(6));

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        RectF rect = new RectF(0, 0, getMeasuredWidth(), getMeasuredWidth());
        canvas.drawRoundRect(rect, DpUtils.dp2px(5), DpUtils.dp2px(5), bgPaint);

        Rect rectIn = new Rect();
        textInPaint.getTextBounds(textIn, 0, textIn.length(), rectIn);
        //dy 代表的是：高度的一半到 baseLine的距离
        Paint.FontMetricsInt fontMetricsIn = textInPaint.getFontMetricsInt();
        // top 是一个负值  bottom 是一个正值    top，bttom的值代表是  bottom是baseLine到文字底部的距离（正值）
        // 必须要清楚的，可以自己打印就好
        int dyIn = (fontMetricsIn.bottom - fontMetricsIn.top) / 2 - fontMetricsIn.bottom;
        //这里减去rectIn.left是因为汉字的基线位置会有偏差
        canvas.drawText(textIn, width / 2 - (rectIn.width() / 2) - rectIn.left, width / 2 + dyIn, textInPaint);

        canvas.drawCircle(width / 2, width / 2, 1, textOutPaint);

        Rect rectOut = new Rect();
        textOutPaint.getTextBounds(textOut, 0, textOut.length(), rectOut);
        Paint.FontMetricsInt fontMetricsOut = textOutPaint.getFontMetricsInt();
        int dyOut = (fontMetricsOut.bottom - fontMetricsOut.top) / 2 - fontMetricsOut.bottom;
        canvas.drawText(textOut, width / 2 - (rectOut.width()) / 2 - rectOut.left, (width + (height - width) / 2) + dyOut, textOutPaint);

    }

    public void setTextOutColor(int outColor) {
        textOutPaint.setColor(outColor);
        invalidate();
    }

    public void setTextInColor(int inColor) {
        textInPaint.setColor(inColor);
        invalidate();
    }

    public void setBgColor(int bgColor) {
        bgPaint.setColor(bgColor);
        invalidate();
    }

    public void setOutText(String textOut) {
        this.textOut = textOut;
        invalidate();
    }

    public void setInText(String textIn) {
        this.textIn = textIn;
        invalidate();
    }
}
