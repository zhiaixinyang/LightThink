package com.example.greatbook.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.greatbook.App;
import com.example.greatbook.R;
import com.example.greatbook.widght.CircleImageView;
import com.example.greatbook.widght.RoundImageView;

/**
 * Created by MDove on 2016/11/11.
 */

public class GlideUtils {
    private static Context context= App.getInstance().getContext();
    public static void load(String url, ImageView iv) {
        Glide.with(context).load(url).crossFade().diskCacheStrategy(DiskCacheStrategy.NONE).into(iv);
    }

    public static void load(byte[] url, ImageView iv) {
        Glide.with(context).load(url).crossFade().diskCacheStrategy(DiskCacheStrategy.NONE).into(iv);
    }

    public static void load(byte[] url, RoundImageView iv) {
        Glide.with(context).load(url).crossFade().diskCacheStrategy(DiskCacheStrategy.NONE).into(iv);
    }

    public static void load(int url, ImageView iv) {
        Glide.with(context).load(url)
                .crossFade()
                .fitCenter()
                .placeholder(R.drawable.pictures_no)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(iv);
    }
    public static void loadSmallIv(String url, ImageView iv) {
        Glide.with(context).load(url)
                .crossFade()
                .fitCenter()
                .override(60,60)
                .placeholder(R.drawable.pictures_no)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(iv);
    }
    public static void loadSmallIv(String url, RoundImageView iv) {
        Glide.with(context).load(url)
                .crossFade()
                .fitCenter()
                .override(100,100)
                .placeholder(R.drawable.pictures_no)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(iv);
    }
    public static void loadCircle( String url, ImageView iv) {
        Glide.with(context).load(url).transform(new GlideCircleTransform(context)).into(iv);
    }
    public static void load(String url, CircleImageView circleImageView){
        Glide.with(context).load(url)
                .crossFade()
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(circleImageView);

    }
    public static void loadSmallAvatar(String url, CircleImageView circleImageView){
        Glide.with(context).load(url)
                .crossFade()
                .fitCenter()
                .override(100,100)
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(circleImageView);

    }
}