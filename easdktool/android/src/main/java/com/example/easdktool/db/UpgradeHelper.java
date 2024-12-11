package com.example.easdktool.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Keep;

import com.github.yuweiguocn.library.greendao.MigrationHelper;
import com.greendao.gen.BloodDataDao;
import com.greendao.gen.DailyDataDao;
import com.greendao.gen.DaoMaster;
import com.greendao.gen.GpsDataDao;
import com.greendao.gen.HeartDataDao;
import com.greendao.gen.MotionHeartDao;
import com.greendao.gen.MultiDataDao;
import com.greendao.gen.RestingHeartDataDao;
import com.greendao.gen.SleepDataDao;
import com.greendao.gen.SleepScoreDao;
import com.greendao.gen.StepFreqDataDao;
import com.greendao.gen.StepPaceDataDao;
import com.greendao.gen.StressDataDao;

import org.greenrobot.greendao.database.Database;

public class UpgradeHelper extends DaoMaster.OpenHelper {
    @Keep
    public UpgradeHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);

    }

    public UpgradeHelper(Context context, String name) {
        super(context, name);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        MigrationHelper.migrate(db, new MigrationHelper.ReCreateAllTableListener() {
                    @Override
                    public void onCreateAllTables(Database db, boolean ifNotExists) {
                        DaoMaster.createAllTables(db, true);
                    }

                    @Override
                    public void onDropAllTables(Database db, boolean ifExists) {
                        DaoMaster.dropAllTables(db, true);
                    }
                }, DailyDataDao.class, SleepDataDao.class, HeartDataDao.class,
                GpsDataDao.class, MultiDataDao.class, BloodDataDao.class, StressDataDao.class,
                StepFreqDataDao.class, StepPaceDataDao.class, RestingHeartDataDao.class, SleepScoreDao.class, MotionHeartDao.class);

    }
}

