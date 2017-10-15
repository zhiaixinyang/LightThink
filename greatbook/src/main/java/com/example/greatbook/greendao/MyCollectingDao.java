package com.example.greatbook.greendao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.example.greatbook.greendao.entity.MyCollecting;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "MY_COLLECTING".
*/
public class MyCollectingDao extends AbstractDao<MyCollecting, Long> {

    public static final String TABLENAME = "MY_COLLECTING";

    /**
     * Properties of entity MyCollecting.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "proid");
        public final static Property BelongUserId = new Property(1, String.class, "belongUserId", false, "BELONG_USER_ID");
        public final static Property GroupObjectId = new Property(2, String.class, "groupObjectId", false, "GROUP_OBJECT_ID");
    };


    public MyCollectingDao(DaoConfig config) {
        super(config);
    }
    
    public MyCollectingDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"MY_COLLECTING\" (" + //
                "\"proid\" INTEGER PRIMARY KEY ," + // 0: id
                "\"BELONG_USER_ID\" TEXT," + // 1: belongUserId
                "\"GROUP_OBJECT_ID\" TEXT);"); // 2: groupObjectId
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"MY_COLLECTING\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, MyCollecting entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String belongUserId = entity.getBelongUserId();
        if (belongUserId != null) {
            stmt.bindString(2, belongUserId);
        }
 
        String groupObjectId = entity.getGroupObjectId();
        if (groupObjectId != null) {
            stmt.bindString(3, groupObjectId);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, MyCollecting entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String belongUserId = entity.getBelongUserId();
        if (belongUserId != null) {
            stmt.bindString(2, belongUserId);
        }
 
        String groupObjectId = entity.getGroupObjectId();
        if (groupObjectId != null) {
            stmt.bindString(3, groupObjectId);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public MyCollecting readEntity(Cursor cursor, int offset) {
        MyCollecting entity = new MyCollecting( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // belongUserId
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2) // groupObjectId
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, MyCollecting entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setBelongUserId(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setGroupObjectId(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(MyCollecting entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(MyCollecting entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
