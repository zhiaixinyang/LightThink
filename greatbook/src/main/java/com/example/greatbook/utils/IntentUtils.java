package com.example.greatbook.utils;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.RequiresApi;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;
import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

/**
 * Created by MDove on 2017/9/15.
 */

public class IntentUtils {

    public static void launchCurrentAppMarket(Context context) {
        launchMarket(context, context.getPackageName());
    }

    public static void launchMarket(Context context, String packageName) {
        Uri uri = Uri.parse("market://details?id=" + packageName);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            uri = Uri.parse("https://play.google.com/store/apps/details?id=" + packageName);
            intent = new Intent(Intent.ACTION_VIEW, uri);
            try {
                intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            } catch (ActivityNotFoundException e1) {
                e1.printStackTrace();
            }
        }
    }

    public static void launchSystemShareDialog(Context context, String shareContent, String
            shareDialogTitle) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, shareContent);
        context.startActivity(Intent.createChooser(intent, shareDialogTitle));
    }

    public static void launchByUri(Context context, String url) {
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        boolean enable = resolveActivity(context, intent);
        if (!enable) {
            return;
        }
        if (!(context instanceof Activity)) {
            intent.addFlags(FLAG_ACTIVITY_NEW_TASK | FLAG_ACTIVITY_CLEAR_TOP);
        }
        context.startActivity(intent);
    }

    public static boolean resolveActivity(Context context, Intent intent) {
        PackageManager pm = context.getPackageManager();
        ComponentName cn = intent.resolveActivity(pm);
        return cn != null;
    }

    /**
     * 判断程序是否安装
     *
     * @param context
     * @param pkgName
     * @return
     */
    public static boolean isAppInstalled(Context context, String pkgName) {
        PackageManager pm = context.getPackageManager();
        boolean installed = false;
        try {
            pm.getPackageInfo(pkgName, 0);
            installed = true;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return installed;
    }

    /**
     * 打开安装的程序
     *
     * @param context
     * @param pkgName
     */
    public static void startupApp(Context context, String pkgName) {
        try {
            Intent intent = context.getPackageManager().getLaunchIntentForPackage(pkgName);
            if (intent != null) {
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                        Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                context.startActivity(intent);
            }
        } catch (Exception e) {
        }
    }

    /**
     * 打开已安装程序的制定Activity
     *
     * @param context
     * @param pkgName
     * @param activityName
     */
    public static void startupAppActivity(Context context, String pkgName, String activityName) {
        ComponentName componetName = new ComponentName(pkgName, activityName);
        try {
            Intent intent = new Intent();
            intent.setComponent(componetName);
            context.startActivity(intent);
        } catch (Exception e) {
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public static void openOverlayPermissionActivity(Activity activity, String pkg, int requestCode) {
        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + pkg));
        activity.startActivityForResult(intent, requestCode);
    }
}
