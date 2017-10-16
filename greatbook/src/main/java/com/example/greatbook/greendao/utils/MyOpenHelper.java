package com.example.greatbook.greendao.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.greatbook.greendao.ContentCommitDao;
import com.example.greatbook.greendao.DaoMaster;
import com.example.greatbook.greendao.DiarySelfDao;
import com.example.greatbook.greendao.EssayDao;
import com.example.greatbook.greendao.LocalGroupDao;
import com.example.greatbook.greendao.LocalRecordDao;
import com.example.greatbook.greendao.MyCollectingDao;
import com.example.greatbook.greendao.MyPlanDao;
import com.example.greatbook.greendao.MyPlanTemplateDao;

import org.greenrobot.greendao.database.Database;

/**
 * Created by MBENBEN on 2017/10/16.
 */

public class MyOpenHelper extends DaoMaster.DevOpenHelper {
    public MyOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }
    /**
     * 数据库升级
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        //操作数据库的更新 有几个表升级都可以传入到下面
        MigrationHelper.getInstance().migrate(db,ContentCommitDao.class);
        MigrationHelper.getInstance().migrate(db,DiarySelfDao.class);
        MigrationHelper.getInstance().migrate(db,EssayDao.class);
        MigrationHelper.getInstance().migrate(db,LocalGroupDao.class);
        MigrationHelper.getInstance().migrate(db,LocalRecordDao.class);
        MigrationHelper.getInstance().migrate(db,MyCollectingDao.class);
        MigrationHelper.getInstance().migrate(db,MyPlanDao.class);
        MigrationHelper.getInstance().migrate(db,MyPlanTemplateDao.class);
    }

}
