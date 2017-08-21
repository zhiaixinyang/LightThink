package com.example.greatbook;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.support.multidex.MultiDexApplication;

import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVObject;
import com.example.greatbook.greendao.DaoSession;
import com.example.greatbook.greendao.utils.DaoManager;
import com.example.greatbook.middle.model.leancloud.LRecordRemark;
import com.example.greatbook.model.leancloud.LLocalGroup;
import com.example.greatbook.model.leancloud.LLocalRecord;
import com.example.greatbook.model.leancloud.TalkAboutBean;
import com.example.greatbook.constants.Constants;
import com.example.greatbook.ui.model.LFeedBackBean;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by MBENBEN on 2016/10/20.
 */

public class App extends MultiDexApplication {
    private static Context context;
    private static App app;
    public static int SCREEN_WIDTH = -1;
    public static int SCREEN_HEIGHT = -1;
    public static float DIMEN_RATE = -1.0F;
    public static int DIMEN_DPI = -1;
    private Set<Activity> allActivities;
    private static DaoSession daoSession;
    private DaoManager daoManager;

    @Override
    public void onCreate() {
        super.onCreate();
        app=this;
        context=getApplicationContext();
        initLeanCloud();

        daoManager = DaoManager.getInstance();
        daoManager.init(context);
        if (null == daoSession) {
            synchronized (App.class) {
                if (null == daoSession) {
                    daoSession = daoManager.getDaoMaster().newSession();
                }
            }
        }
    }

    /**
     * 取得DaoSession
     * @return
     */
    public static DaoSession getDaoSession() {

        return daoSession;
    }

    public static App getInstance(){
        return app;
    }

    public Context getContext(){
        return context;
    }

    private void initLeanCloud() {
        AVOSCloud.initialize(this, Constants.LEANCLOUD_APP_ID, Constants.LEANCLOUD_APP_KEY);
        AVObject.registerSubclass(TalkAboutBean.class);
        AVObject.registerSubclass(LFeedBackBean.class);
        AVObject.registerSubclass(LLocalRecord.class);
        AVObject.registerSubclass(LLocalGroup.class);
        AVObject.registerSubclass(LRecordRemark.class);
    }

    public void addActivity(Activity act) {
        if (allActivities == null) {
            allActivities = new HashSet<Activity>();
        }
        allActivities.add(act);
    }

    public void removeActivity(Activity act) {
        if (allActivities != null) {
            allActivities.remove(act);
        }
    }

    public static void toIntent(Activity activityFrom,Class activityTo){
        Intent to=new Intent(context,activityTo);
        activityFrom.startActivity(to);
        activityFrom.finish();
    }

}
