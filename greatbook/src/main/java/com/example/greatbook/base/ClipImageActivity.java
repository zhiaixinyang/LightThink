package com.example.greatbook.base;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.TextView;


import com.example.greatbook.R;
import com.example.greatbook.utils.BitmapCompressUtils;
import com.example.greatbook.utils.LogUtils;
import com.example.greatbook.widght.clipimage.ClipImageLayout;
import com.example.greatbook.constants.Constants;
import com.example.greatbook.utils.DpUtils;
import com.example.greatbook.utils.FileAndImageUtils;
import com.example.greatbook.utils.FileUtils;
import com.example.greatbook.utils.SelectorFactory;
import com.example.greatbook.widght.DefaultNavigationBar;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by MDove on 2017/8/14.
 *
 * 用于剪裁的Activity
 */

public class ClipImageActivity extends BaseActivity {

    @BindView(R.id.clipImageLayout)
    ClipImageLayout mClipImageLayout = null;
    @BindView(R.id.btn_ok)
    TextView btnOk;
    @BindView(R.id.btn_cancle)
    TextView btnCancle;

    @Override
    public int getLayoutId() {
        return R.layout.layout_crop_image;
    }

    @Override
    public void init() {
        new DefaultNavigationBar.Builder(this,null)
                .setTitleText("图片剪裁").setOnLeftClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                }).builder();
        btnOk.setBackground(SelectorFactory.newShapeSelector()
                .setCornerRadius(DpUtils.dp2px(20))
                .setDefaultBgColor(ContextCompat.getColor(
                        ClipImageActivity.this,
                        R.color.white))
                .create());
        btnCancle.setBackground(SelectorFactory.newShapeSelector()
                .setCornerRadius(DpUtils.dp2px(20))
                .setDefaultBgColor(ContextCompat.getColor(
                        this,
                        R.color.white))
                .create());

        String path = getIntent().getStringExtra(Constants.TO_CLIPACTIVITY);

        // 有的系统返回的图片是旋转了，有的没有旋转，所以处理
        int degreee = readBitmapDegree(path);
        Bitmap bitmap = BitmapCompressUtils.getImage(path);
        if (bitmap != null) {
            if (degreee == 0) {
                mClipImageLayout.setImageBitmap(bitmap);
            } else {
                mClipImageLayout.setImageBitmap(rotateBitmap(degreee, bitmap));
            }
        } else {
            finish();
        }
    }


    @OnClick({R.id.btn_ok, R.id.btn_cancle})
    public void onClicked(View v) {
        if (v.getId() == R.id.btn_ok) {
            Bitmap bitmap = mClipImageLayout.clip();

            String path = Environment.getExternalStorageDirectory() + "/"
                    + "my_avatar.jpg";
            FileUtils.saveBitmap(bitmap, path);

            Intent intent = new Intent();
            intent.putExtra(Constants.RETURN_CLIP_PHOTO, path);
            setResult(RESULT_OK, intent);
        } finish();
    }


    // 读取图像的旋转度
    private int readBitmapDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    // 旋转图片
    private Bitmap rotateBitmap(int angle, Bitmap bitmap) {
        // 旋转图片 动作
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        // 创建新的图片
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
                bitmap.getWidth(), bitmap.getHeight(), matrix, false);
        return resizedBitmap;
    }

    @Override
    public void showError(String msg) {

    }
}

