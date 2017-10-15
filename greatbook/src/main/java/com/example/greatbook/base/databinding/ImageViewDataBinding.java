package com.example.greatbook.base.databinding;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.example.greatbook.utils.GlideUtils;
import com.example.greatbook.utils.StringUtils;
import com.example.greatbook.widght.CircleImageView;
import com.example.greatbook.widght.RoundImageView;

/**
 * Created by MDove on 2017/8/21.
 */

public class ImageViewDataBinding {
    @BindingAdapter("loadAvatar")
    public static void loadAvatar(CircleImageView imageView, String path) {
        if (!StringUtils.isEmpty(path)) {
            GlideUtils.loadSmallAvatar(path, imageView);
        }
    }

    @BindingAdapter("loadImageView")
    public static void loadImageView(ImageView imageView, String path) {
        if (!StringUtils.isEmpty(path)) {
            GlideUtils.loadSmallIv(path, imageView);
        }
    }

    @BindingAdapter("loadRoundImage")
    public static void loadRoundImage(RoundImageView imageView, String path) {
        if (!StringUtils.isEmpty(path)) {
            GlideUtils.loadSmallIv(path, imageView);
        }
    }
}
