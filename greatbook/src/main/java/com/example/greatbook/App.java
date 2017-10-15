package com.example.greatbook;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.multidex.MultiDexApplication;

import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVObject;
import com.example.greatbook.constants.Constants;
import com.example.greatbook.diary.model.LDiarySelf;
import com.example.greatbook.greendao.DaoSession;
import com.example.greatbook.greendao.utils.DaoManager;
import com.example.greatbook.local.model.leancloud.LGroupCollects;
import com.example.greatbook.local.model.leancloud.LGroupRemark;
import com.example.greatbook.local.model.leancloud.LRecordLike;
import com.example.greatbook.local.model.leancloud.LRecordRemark;
import com.example.greatbook.model.leancloud.LLocalGroup;
import com.example.greatbook.model.leancloud.LLocalRecord;
import com.example.greatbook.model.leancloud.TalkAboutBean;
import com.example.greatbook.main.model.LFeedBackBean;

import java.util.Set;

/**
 * Created by MDove on 2016/10/20.
 */

public class App extends MultiDexApplication {
    private static Context context;
    private static App app;
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
        AVObject.registerSubclass(LRecordLike.class);
        AVObject.registerSubclass(LGroupRemark.class);
        AVObject.registerSubclass(LGroupCollects.class);
        AVObject.registerSubclass(LDiarySelf.class);
    }

    public static void toIntent(Activity activityFrom,Class activityTo){
        Intent to=new Intent(context,activityTo);
        activityFrom.startActivity(to);
        activityFrom.finish();
    }

}
