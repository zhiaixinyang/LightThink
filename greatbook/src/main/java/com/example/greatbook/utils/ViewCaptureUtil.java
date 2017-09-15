package com.example.greatbook.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.view.View;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by MDove on 17/9/15.
 */

public class ViewCaptureUtil {
    private static final String DC_FOLDER_NAME = "DCollage";

    public static void save(View view, String fileName) {
        if (!createDCFolderIfNotExist()) {
            return;
        }
        String imagePath = getDCFolderPath() + File.separator + fileName;

        view.buildDrawingCache();
        view.setDrawingCacheEnabled(true);
//        Bitmap bitmap = createWaterMark(view.getContext(),view.getDrawingCache(),
//                BitmapFactory.decodeResource(view.getResources(), R.drawable.watermark));
        File imageFile = new File(imagePath);
        if (imageFile.exists()) {
            imageFile.delete();
        }
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(imageFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
//        bitmap.compress(Bitmap.CompressFormat.JPEG, 75,
//                fileOutputStream);

        MediaScannerConnection.scanFile(view.getContext(),
                new String[]{imagePath}, null,
                new MediaScannerConnection.OnScanCompletedListener() {
                    public void onScanCompleted(String path, Uri uri) {

                    }
                });

        try {
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        view.destroyDrawingCache();
    }

    public static void saveWithoutWaterMaker(View view, String fileName) {
        if (!createDCFolderIfNotExist()) {
            return;
        }
        String imagePath = getDCFolderPath() + File.separator + fileName;

        view.buildDrawingCache();
        view.setDrawingCacheEnabled(true);
        Bitmap bitmap = view.getDrawingCache();
        File imageFile = new File(imagePath);
        if (imageFile.exists()) {
            imageFile.delete();
        }
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(imageFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        bitmap.compress(Bitmap.CompressFormat.JPEG, 75,
                fileOutputStream);

        MediaScannerConnection.scanFile(view.getContext(),
                new String[]{imagePath}, null,
                new MediaScannerConnection.OnScanCompletedListener() {
                    public void onScanCompleted(String path, Uri uri) {

                    }
                });

        try {
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        view.destroyDrawingCache();
    }

    public static void save(Bitmap bitmap, String fileName, Context context) {
        if (!createDCFolderIfNotExist()) {
            return;
        }
        String imagePath = getDCFolderPath() + File.separator + fileName;
        File imageFile = new File(imagePath);
        if (imageFile.exists()) {
            imageFile.delete();
        }
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(imageFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
//        bitmap = createWaterMark(context, bitmap, BitmapFactory.decodeResource(context.getResources(), R.drawable.watermark));
//        bitmap.compress(Bitmap.CompressFormat.JPEG, 75, fileOutputStream);

        MediaScannerConnection.scanFile(context,
                new String[]{imagePath}, null,
                new MediaScannerConnection.OnScanCompletedListener() {
                    public void onScanCompleted(String path, Uri uri) {

                    }
                });

        try {
            fileOutputStream.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveWithoutWaterMaker(Bitmap bitmap, String fileName, Context context) {
        if (!createDCFolderIfNotExist()) {
            return;
        }
        String imagePath = getDCFolderPath() + File.separator + fileName;
        File imageFile = new File(imagePath);
        if (imageFile.exists()) {
            imageFile.delete();
        }
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(imageFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        //bitmap = createWaterMark(context, bitmap, BitmapFactory.decodeResource(context.getResources(), R.drawable.watermark));
        bitmap.compress(Bitmap.CompressFormat.JPEG, 75, fileOutputStream);

        MediaScannerConnection.scanFile(context,
                new String[]{imagePath}, null,
                new MediaScannerConnection.OnScanCompletedListener() {
                    public void onScanCompleted(String path, Uri uri) {

                    }
                });

        try {
            fileOutputStream.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean createDCFolderIfNotExist() {
        if (!isMounted()) {
            return false;
        }
        String path = getDCFolderPath();
        File file = new File(path);
        if (!file.exists()) {
            file.mkdir();
        }
        return true;
    }

    private static boolean isMounted() {
        return Environment.getExternalStorageState()
                .equals(Environment.MEDIA_MOUNTED);
    }

    public static String getDCFolderPath() {
        return Environment.getExternalStorageDirectory() + File.separator + DC_FOLDER_NAME;
    }

    public static String getSavedPath(String fileName) {
        return getDCFolderPath() + File.separator + fileName;
    }

    private static Bitmap createWaterMark(Context context, Bitmap src, Bitmap watermark) {
        if (src == null) {
            return null;
        }
        int w = src.getWidth();
        int h = src.getHeight();
        int ww = watermark.getWidth();
        int wh = watermark.getHeight();
        Bitmap newb = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);//创建一个新的和SRC长度宽度一样的位图

        Canvas cv = new Canvas(newb);
        cv.drawBitmap(src, 0, 0, null);
        cv.drawBitmap(watermark, w - ww - DpUtils.dp2px(6),
                h - wh - DpUtils.dp2px(8), null);
        cv.save(Canvas.ALL_SAVE_FLAG);
        cv.restore();
        return newb;
    }

}
