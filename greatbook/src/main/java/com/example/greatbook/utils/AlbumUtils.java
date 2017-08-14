package com.example.greatbook.utils;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.provider.MediaStore;

import com.example.greatbook.constants.Constants;

/**
 * Created by MDove on 2017/8/14.
 */

public class AlbumUtils {
    public static void startAlbumToClip(Activity activity) {
        try {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
            intent.setType("image/*");
            activity.startActivityForResult(intent, Constants.START_ALBUM_REQUESTCODE);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
            try {
                Intent intent = new Intent(Intent.ACTION_PICK, null);
                intent.setDataAndType(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                activity.startActivityForResult(intent, Constants.START_ALBUM_REQUESTCODE);
            } catch (Exception e2) {
                e.printStackTrace();
            }
        }
    }
}
