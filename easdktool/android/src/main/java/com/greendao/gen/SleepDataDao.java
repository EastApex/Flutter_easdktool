package com.greendao.gen;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.example.easdktool.db.SleepData;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "SLEEP_DATA".
*/
public class SleepDataDao extends AbstractDao<SleepData, Long> {

    public static final String TABLENAME = "SLEEP_DATA";

    /**
     * Properties of entity SleepData.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Time_stamp = new Property(0, long.class, "time_stamp", true, "_id");
        public final static Property SleepMode = new Property(1, int.class, "SleepMode", false, "SLEEP_MODE");
    }


    public SleepDataDao(DaoConfig config) {
        super(config);
    }
    
    public SleepDataDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"SLEEP_DATA\" (" + //
                "\"_id\" INTEGER PRIMARY KEY NOT NULL ," + // 0: time_stamp
                "\"SLEEP_MODE\" INTEGER NOT NULL );"); // 1: SleepMode
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"SLEEP_DATA\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, SleepData entity) {
        stmt.clearBindings();
        stmt.bindLong(1, entity.getTime_stamp());
        stmt.bindLong(2, entity.getSleepMode());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, SleepData entity) {
        stmt.clearBindings();
        stmt.bindLong(1, entity.getTime_stamp());
        stmt.bindLong(2, entity.getSleepMode());
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.getLong(offset + 0);
    }    

    @Override
    public SleepData readEntity(Cursor cursor, int offset) {
        SleepData entity = new SleepData( //
            cursor.getLong(offset + 0), // time_stamp
            cursor.getInt(offset + 1) // SleepMode
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, SleepData entity, int offset) {
        entity.setTime_stamp(cursor.getLong(offset + 0));
        entity.setSleepMode(cursor.getInt(offset + 1));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(SleepData entity, long rowId) {
        entity.setTime_stamp(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(SleepData entity) {
        if(entity != null) {
            return entity.getTime_stamp();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(SleepData entity) {
        throw new UnsupportedOperationException("Unsupported for entities with a non-null key");
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}