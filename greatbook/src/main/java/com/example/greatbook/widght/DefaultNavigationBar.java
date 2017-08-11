package com.example.greatbook.widght;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.example.greatbook.R;
import com.example.greatbook.base.navigationbar.AbsNavigationBar;


/**
 * Created by MDove on 2017/8/4.
 */

public class DefaultNavigationBar extends AbsNavigationBar {
    //此类需要自己去实现
    public DefaultNavigationBar(Builder.AbsNavigationParams params) {
        super(params);
    }

    @Override
    public int bingLayoutId() {
        return R.layout.layout_default_navigationbar;
    }

    @Override
    public void applyView() {
        Builder.DefaultNavigationBarParams P= (Builder.DefaultNavigationBarParams) getParams();
        setText(R.id.tv_left, P.leftString);
        setText(R.id.tv_middle, P.titleString);
        setOnClickListener(R.id.btn_back,P.onClickListener);
    }

    public static class Builder extends AbsNavigationBar.Builder{
        DefaultNavigationBarParams P;
        /**
         * 设置所有效果的类（set）
         * @param context
         * @param parent
         */
        public Builder(Context context, ViewGroup parent) {
            super(context, parent);
            P=new DefaultNavigationBarParams(context,parent);
        }

        public DefaultNavigationBar.Builder setLeftText(String leftString){
            P.leftString=leftString;
            return this;
        }

        public DefaultNavigationBar.Builder setTitleText(String titleString){
            P.titleString=titleString;
            return this;
        }

        public DefaultNavigationBar.Builder setOnLeftClickListener(View.OnClickListener onClickListener){
            P.onClickListener=onClickListener;
            return this;
        }

        @Override
        public DefaultNavigationBar builder() {
            DefaultNavigationBar navigationBar=new DefaultNavigationBar(P);
            return navigationBar;
        }

        public DefaultNavigationBarParams getP() {
            return P;
        }

        /**
         * 所有效果的类
         */
        public static class DefaultNavigationBarParams extends AbsNavigationBar.Builder.AbsNavigationParams{
            public String leftString;
            public String titleString;
            public View.OnClickListener onClickListener;

            public DefaultNavigationBarParams(Context context, ViewGroup parent) {
                super(context, parent);
            }
        }
    }
}
