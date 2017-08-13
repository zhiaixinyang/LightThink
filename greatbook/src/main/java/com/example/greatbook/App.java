package com.example.greatbook;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVObject;
import com.example.greatbook.greendao.DaoSession;
import com.example.greatbook.greendao.utils.DaoManager;
import com.example.greatbook.model.leancloud.BookTalkBean;
import com.example.greatbook.model.leancloud.LBookDesBean;
import com.example.greatbook.model.leancloud.LBookDesCatalogue;
import com.example.greatbook.model.leancloud.LBookKindBean;
import com.example.greatbook.model.leancloud.LBookKindListBean;
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

public class App extends Application{
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
        AVObject.registerSubclass(BookTalkBean.class);
        AVObject.registerSubclass(LBookDesBean.class);
        AVObject.registerSubclass(LBookKindBean.class);
        AVObject.registerSubclass(LBookKindListBean.class);
        AVObject.registerSubclass(LBookDesCatalogue.class);
        AVObject.registerSubclass(LFeedBackBean.class);
        AVObject.registerSubclass(LLocalRecord.class);
        AVObject.registerSubclass(LLocalGroup.class);
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

}
