package com.example.greatbook.utils;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.provider.MediaStore;

import com.example.greatbook.constants.Constants;
import com.example.greatbook.model.Folder;
import com.example.greatbook.model.Picture;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by MDove on 2017/8/14.
 */

public class AlbumUtils {

    private List<Folder> folderList = new ArrayList<>();
    private Map<Folder,List<Picture>> folderMap=new HashMap<>();


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

    public static List<Picture> getAllPictureFromPhone(Context context) {
        Cursor cursor;
        try {
            ContentResolver cr = context.getContentResolver();
            cursor = cr.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new String[]{MediaStore.Images.ImageColumns.DATA, MediaStore.Images.ImageColumns.DATE_ADDED}, null, null, null);
        } catch (Exception e) {
            return null;
        }
        if (cursor == null) {
            return null;
        }
        ArrayList<Picture> pictureList = new ArrayList<>();
        while (cursor.moveToNext()) {
            Picture picture = new Picture();
            int path_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            int date_added_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_ADDED);
            picture.path = cursor.getString(path_index);
            picture.dateAdded = cursor.getLong(date_added_index);
            pictureList.add(picture);
        }
        cursor.close();
        return pictureList;
    }

    public static List<Folder> getFoldersAndPicture(Context context){
        List<Folder> folderList=new ArrayList<>();
        Map<Folder,List<Picture>> folderMap=setupPictureMap(context);
        for (Folder folder : folderMap.keySet()) {
            folderList.add(folder);
        }
        if (folderList.size() == 0) {
            return null;
        }
        return folderList;
    }


    private static Map<Folder,List<Picture>> setupPictureMap(Context context) {
        Map<Folder,List<Picture>> folderMap=new HashMap<>();
        List<Picture> allPictureList = getAllPictureFromPhone(context);
        if (allPictureList == null || allPictureList.size() == 0) {
            return null;
        }
        //先将全部图片加入Recent
        Collections.sort(allPictureList);
        Folder recentFolder = new Folder();
        recentFolder.name = "最近照片";
        recentFolder.coverPath = allPictureList.get(0).path;
        recentFolder.count = allPictureList.size();
        folderMap.put(recentFolder, allPictureList);
        //依次按文件夹加入图片
        for (Picture picture : allPictureList) {
            Folder pictureFolder = containPictureFolder(picture,folderMap);
            if (pictureFolder != null) {
                List<Picture> pictureList = folderMap.get(pictureFolder);
                pictureFolder.count++;
                pictureList.add(picture);
            } else {
                List<Picture> pictureList = new ArrayList<>();
                pictureList.add(picture);
                Folder folder = new Folder();
                folder.name = getFolderName(picture.path);
                folder.coverPath = picture.path;
                folder.count = 1;
                folderMap.put(folder, pictureList);
            }
        }
        return folderMap;
    }

    private static Folder containPictureFolder(Picture picture,Map<Folder,List<Picture>> folderMap) {
        for (Folder folder : folderMap.keySet()) {
            if (folder.name.equals(getFolderName(picture.path))) {
                return folder;
            }
        }
        return null;
    }

    private static String getFolderName(String path) {
        String pathArr[] = path.split(File.separator);
        return pathArr[pathArr.length - 2];
    }
}
