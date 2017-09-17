package com.example.greatbook.middle.databinding;

import android.databinding.BindingAdapter;

import com.example.greatbook.widght.VerticalTextView;

/**
 * Created by MDove on 2017/9/16.
 */

public class VerticalTextViewDataBinding {
    @BindingAdapter("setVTVText")
    public static void setVTVText(VerticalTextView vtvText,String text){
        vtvText.setText(text);
    }

    @BindingAdapter("setVTVTextColor")
    public static void setVTVTextColor(VerticalTextView vtvText,int textColor){
        vtvText.setTextColor(textColor);
    }
}
