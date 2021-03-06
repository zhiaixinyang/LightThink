package com.example.greatbook.greendao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.example.greatbook.greendao.entity.LocalGroup;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "LOCAL_GROUP".
*/
public class LocalGroupDao extends AbstractDao<LocalGroup, Long> {

    public static final String TABLENAME = "LOCAL_GROUP";

    /**
     * Properties of entity LocalGroup.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "proid");
        public final static Property Time = new Property(1, java.util.Date.class, "time", false, "TIME");
        public final static Property Title = new Property(2, String.class, "title", false, "TITLE");
        public final static Property Content = new Property(3, String.class, "content", false, "CONTENT");
        public final static Property BelongId = new Property(4, String.class, "belongId", false, "BELONG_ID");
        public final static Property IsUserd = new Property(5, boolean.class, "isUserd", false, "IS_USERD");
        public final static Property GroupPhotoPath = new Property(6, String.class, "groupPhotoPath", false, "GROUP_PHOTO_PATH");
        public final static Property GroupLocalPhotoPath = new Property(7, int.class, "groupLocalPhotoPath", false, "GROUP_LOCAL_PHOTO_PATH");
        public final static Property BgColor = new Property(8, String.class, "bgColor", false, "BG_COLOR");
    };


    public LocalGroupDao(DaoConfig config) {
        super(config);
    }
    
    public LocalGroupDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"LOCAL_GROUP\" (" + //
                "\"proid\" INTEGER PRIMARY KEY ," + // 0: id
                "\"TIME\" INTEGER," + // 1: time
                "\"TITLE\" TEXT," + // 2: title
                "\"CONTENT\" TEXT," + // 3: content
                "\"BELONG_ID\" TEXT," + // 4: belongId
                "\"IS_USERD\" INTEGER NOT NULL ," + // 5: isUserd
                "\"GROUP_PHOTO_PATH\" TEXT," + // 6: groupPhotoPath
                "\"GROUP_LOCAL_PHOTO_PATH\" INTEGER NOT NULL ," + // 7: groupLocalPhotoPath
                "\"BG_COLOR\" TEXT);"); // 8: bgColor
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"LOCAL_GROUP\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, LocalGroup entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        java.util.Date time = entity.getTime();
        if (time != null) {
            stmt.bindLong(2, time.getTime());
        }
 
        String title = entity.getTitle();
        if (title != null) {
            stmt.bindString(3, title);
        }
 
        String content = entity.getContent();
        if (content != null) {
            stmt.bindString(4, content);
        }
 
        String belongId = entity.getBelongId();
        if (belongId != null) {
            stmt.bindString(5, belongId);
        }
        stmt.bindLong(6, entity.getIsUserd() ? 1L: 0L);
 
        String groupPhotoPath = entity.getGroupPhotoPath();
        if (groupPhotoPath != null) {
            stmt.bindString(7, groupPhotoPath);
        }
        stmt.bindLong(8, entity.getGroupLocalPhotoPath());
 
        String bgColor = entity.getBgColor();
        if (bgColor != null) {
            stmt.bindString(9, bgColor);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, LocalGroup entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        java.util.Date time = entity.getTime();
        if (time != null) {
            stmt.bindLong(2, time.getTime());
        }
 
        String title = entity.getTitle();
        if (title != null) {
            stmt.bindString(3, title);
        }
 
        String content = entity.getContent();
        if (content != null) {
            stmt.bindString(4, content);
        }
 
        String belongId = entity.getBelongId();
        if (belongId != null) {
            stmt.bindString(5, belongId);
        }
        stmt.bindLong(6, entity.getIsUserd() ? 1L: 0L);
 
        String groupPhotoPath = entity.getGroupPhotoPath();
        if (groupPhotoPath != null) {
            stmt.bindString(7, groupPhotoPath);
        }
        stmt.bindLong(8, entity.getGroupLocalPhotoPath());
 
        String bgColor = entity.getBgColor();
        if (bgColor != null) {
            stmt.bindString(9, bgColor);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public LocalGroup readEntity(Cursor cursor, int offset) {
        LocalGroup entity = new LocalGroup( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : new java.util.Date(cursor.getLong(offset + 1)), // time
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // title
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // content
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // belongId
            cursor.getShort(offset + 5) != 0, // isUserd
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // groupPhotoPath
            cursor.getInt(offset + 7), // groupLocalPhotoPath
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8) // bgColor
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, LocalGroup entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setTime(cursor.isNull(offset + 1) ? null : new java.util.Date(cursor.getLong(offset + 1)));
        entity.setTitle(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setContent(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setBelongId(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setIsUserd(cursor.getShort(offset + 5) != 0);
        entity.setGroupPhotoPath(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setGroupLocalPhotoPath(cursor.getInt(offset + 7));
        entity.setBgColor(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(LocalGroup entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(LocalGroup entity) {
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
