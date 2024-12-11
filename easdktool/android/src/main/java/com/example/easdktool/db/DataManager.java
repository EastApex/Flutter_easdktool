package com.example.easdktool.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.NonNull;

import com.alibaba.fastjson.JSONObject;

import com.apex.bluetooth.utils.LogUtils;
import com.greendao.gen.BloodDataDao;
import com.greendao.gen.DailyDataDao;
import com.greendao.gen.DaoMaster;
import com.greendao.gen.DaoSession;
import com.greendao.gen.GpsDataDao;
import com.greendao.gen.HabitDataDao;
import com.greendao.gen.HeartDataDao;
import com.greendao.gen.MotionHeartDao;
import com.greendao.gen.MultiDataDao;
import com.greendao.gen.RestingHeartDataDao;
import com.greendao.gen.SleepDataDao;
import com.greendao.gen.SleepScoreDao;
import com.greendao.gen.StepFreqDataDao;
import com.greendao.gen.StepPaceDataDao;
import com.greendao.gen.StressDataDao;

import org.greenrobot.greendao.identityscope.IdentityScopeType;

import java.util.List;

public class DataManager {
    private static final String TAG = DataManager.class.getSimpleName();
    private static DataManager manager;
    private static DaoSession mSession;
    private static volatile boolean isSaveData;
    public static final String DBNAME="sport.db";

    public static DataManager getInstance() {
        if (manager == null) {
            synchronized (DataManager.class) {
                if (manager == null) {
                    manager = new DataManager();
                }
            }
        }
        return manager;
    }

    public void setIsSaveData(boolean saveData) {
        isSaveData = saveData;
    }

    public void initDB(@NonNull Context mContext) {
        if (mSession == null) {
            LogUtils.e(TAG, "开始初始化数据库");
            initDataBase(mContext.getApplicationContext());
        }
    }

    private DataManager() {

    }

    private void initDataBase(@NonNull Context mContext) {

        UpgradeHelper mHelper = new UpgradeHelper(mContext.getApplicationContext(), DBNAME, null);
        SQLiteDatabase db = mHelper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        mSession = daoMaster.newSession(IdentityScopeType.None);
        /**
         DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(mContext.getApplicationContext(), "sport.db");
         SQLiteDatabase db = helper.getWritableDatabase();
         DaoMaster daoMaster = new DaoMaster(db);
         mSession = daoMaster.newSession();
         */

    }

    /**
     * 保存日常数据
     *
     * @param cacheList
     */
    public void insertBatchDailyData(List<DailyData> cacheList) {
        if (cacheList == null || cacheList.isEmpty() || !isSaveData) {
            return;
        }
        LogUtils.e(TAG, "插入数据库的日常数据:" + JSONObject.toJSONString(cacheList));
        if (mSession != null) {
            DailyDataDao dailySportDataCacheDao = mSession.getDailyDataDao();
            dailySportDataCacheDao.insertOrReplaceInTx(cacheList);
            dailySportDataCacheDao.detachAll();
        }
    }

    /**
     * 保存心率
     *
     * @param cacheList
     */
    public void insertBatchHeartData(List<HeartData> cacheList) {
        if (cacheList == null || cacheList.isEmpty() || !isSaveData) {
            return;
        }
        LogUtils.e(TAG, "插入数据库的心率数据:" + JSONObject.toJSONString(cacheList));
        if (mSession != null) {
            HeartDataDao heartDataCacheDao = mSession.getHeartDataDao();
            heartDataCacheDao.insertOrReplaceInTx(cacheList);
            heartDataCacheDao.detachAll();
        }
    }

    /**
     * 保存gps
     *
     * @param cacheList
     */
    public void insertBatchGpsData(List<GpsData> cacheList) {
        if (cacheList == null || cacheList.isEmpty() || !isSaveData) {
            return;
        }
        if (mSession != null) {
            GpsDataDao gpsDataCacheDao = mSession.getGpsDataDao();
            gpsDataCacheDao.insertOrReplaceInTx(cacheList);
            gpsDataCacheDao.detachAll();
        }
    }

    /**
     * 保存血氧
     *
     * @param cacheList
     */
    public void insertBatchBloodData(List<BloodData> cacheList) {
        if (cacheList == null || cacheList.isEmpty() || !isSaveData) {
            return;
        }
        LogUtils.e(TAG, "插入数据库的血氧数据:" + JSONObject.toJSONString(cacheList));
        if (mSession != null) {
            BloodDataDao bloodOxygenCacheDao = mSession.getBloodDataDao();
            bloodOxygenCacheDao.insertOrReplaceInTx(cacheList);
            bloodOxygenCacheDao.detachAll();
        }
    }

    /**
     * 保存压力
     *
     * @param cacheList
     */
    public void insertBatchStressData(List<StressData> cacheList) {
        if (cacheList == null || cacheList.isEmpty() || !isSaveData) {
            return;
        }
        LogUtils.e(TAG, "插入数据库的压力数据:" + JSONObject.toJSONString(cacheList));
        if (mSession != null) {
            StressDataDao stressDataCacheDao = mSession.getStressDataDao();
            stressDataCacheDao.insertOrReplaceInTx(cacheList);
            stressDataCacheDao.detachAll();
        }
    }

    /**
     * 保存步频
     *
     * @param cacheList
     */
    public void insertBatchFreqData(List<StepFreqData> cacheList) {
        if (cacheList == null || cacheList.isEmpty() || !isSaveData) {
            return;
        }
        if (mSession != null) {
            StepFreqDataDao stepFreqCacheDao = mSession.getStepFreqDataDao();
            stepFreqCacheDao.insertOrReplaceInTx(cacheList);
            stepFreqCacheDao.detachAll();
        }
    }

    /**
     * 保存步幅
     *
     * @param cacheList
     */
    public void insertBatchPaceData(List<StepPaceData> cacheList) {
        if (cacheList == null || cacheList.isEmpty() || !isSaveData) {
            return;
        }
        if (mSession != null) {
            StepPaceDataDao paceDataCacheDao = mSession.getStepPaceDataDao();
            paceDataCacheDao.insertOrReplaceInTx(cacheList);
            paceDataCacheDao.detachAll();
        }
    }

    /**
     * 保存静息心率
     *
     * @param cacheList
     */
    public void insertBatchRestingHeartData(List<RestingHeartData> cacheList) {
        if (cacheList == null || cacheList.isEmpty() || !isSaveData) {
            return;
        }
        if (mSession != null) {
            RestingHeartDataDao restingRateCacheDao = mSession.getRestingHeartDataDao();
            restingRateCacheDao.insertOrReplaceInTx(cacheList);
            restingRateCacheDao.detachAll();
        }
    }

    /**
     * 保存运动心率
     *
     * @param cacheList
     */
    public void insertBatchMotionHeartData(List<MotionHeart> cacheList) {
        if (cacheList == null || cacheList.isEmpty() || !isSaveData) {
            return;
        }
        if (mSession != null) {
            MotionHeartDao motionHeartDao = mSession.getMotionHeartDao();
            motionHeartDao.insertOrReplaceInTx(cacheList);
            motionHeartDao.detachAll();
        }
    }

    /**
     * 保存睡眠得分数据
     *
     * @param cacheList
     */
    public void insertBatchSleepScoreData(List<SleepScore> cacheList) {
        if (cacheList == null || cacheList.isEmpty() || !isSaveData) {
            return;
        }
        if (mSession != null) {
            SleepScoreDao sleepScoreDao = mSession.getSleepScoreDao();
            sleepScoreDao.insertOrReplaceInTx(cacheList);
            sleepScoreDao.detachAll();
        }
    }

    /**
     * 保存运动数据
     *
     * @param cacheList
     */
    public void insertBatchMultiData(List<MultiData> cacheList) {
        if (cacheList == null || cacheList.isEmpty() || !isSaveData) {
            return;
        }
        LogUtils.e(TAG, "插入数据库的临时运动数据:" + JSONObject.toJSONString(cacheList));
        if (mSession != null) {
            MultiDataDao multiDataDao = mSession.getMultiDataDao();
            multiDataDao.insertOrReplaceInTx(cacheList);
            multiDataDao.detachAll();
        }
    }

    /**
     * 保存睡眠数据
     *
     * @param cacheList
     */
    public void insertBatchSleepData(List<SleepData> cacheList) {
        if (cacheList == null || cacheList.isEmpty() || !isSaveData) {
            return;
        }
        if (mSession != null) {
            SleepDataDao sleepDataDao = mSession.getSleepDataDao();
            sleepDataDao.insertOrReplaceInTx(cacheList);
            sleepDataDao.detachAll();
        }
    }

    /**
     * 保存指定的习惯数据
     *
     * @param cacheList
     */
    public void insertBatchHabitData(List<HabitData> cacheList) {
        if (cacheList == null || cacheList.isEmpty() || !isSaveData) {
            return;
        }
        if (mSession != null) {
            HabitDataDao habitDataDao = mSession.getHabitDataDao();
            habitDataDao.insertOrReplaceInTx(cacheList);
            habitDataDao.detachAll();
        }
    }

    /**
     * 删除指定的日常数据
     *
     * @param cacheList
     */
    public void deleteDailyData(List<DailyData> cacheList) {

        if (mSession != null) {
            DailyDataDao dailyDataDao = mSession.getDailyDataDao();
            if (cacheList == null || cacheList.isEmpty()) {
                dailyDataDao.deleteAll();
            } else {

                dailyDataDao.deleteInTx(cacheList);
            }
        }
    }

    /**
     * 删除指定的睡眠数据
     *
     * @param cacheList
     */
    public void deleteSleepData(List<SleepData> cacheList) {

        if (mSession != null) {
            SleepDataDao sleepDataDao = mSession.getSleepDataDao();
            if (cacheList == null || cacheList.isEmpty()) {
                sleepDataDao.deleteAll();
            } else {
                sleepDataDao.deleteInTx(cacheList);
            }
        }
    }

    /**
     * 删除指定的心率数据
     *
     * @param cacheList
     */
    public void deleteHeartData(List<HeartData> cacheList) {

        if (mSession != null) {
            HeartDataDao heartDataDao = mSession.getHeartDataDao();
            if (cacheList == null || cacheList.isEmpty()) {
                heartDataDao.deleteAll();
            } else {
                heartDataDao.deleteInTx(cacheList);
            }
        }
    }

    /**
     * 删除指定的GPS数据
     *
     * @param cacheList
     */
    public void deleteGpsData(List<GpsData> cacheList) {

        if (mSession != null) {
            GpsDataDao gpsDataDao = mSession.getGpsDataDao();
            if (cacheList == null || cacheList.isEmpty()) {
                gpsDataDao.deleteAll();
            } else {
                gpsDataDao.deleteInTx(cacheList);
            }
        }
    }

    /**
     * 删除指定的多运动数据
     *
     * @param cacheList
     */
    public void deleteMultiData(List<MultiData> cacheList) {

        if (mSession != null) {
            MultiDataDao multiDataDao = mSession.getMultiDataDao();
            if (cacheList == null || cacheList.isEmpty()) {
                multiDataDao.deleteAll();
            } else {
                multiDataDao.deleteInTx(cacheList);
            }
        }
    }

    /**
     * 删除指定的血氧数据
     *
     * @param cacheList
     */
    public void deleteBloodData(List<BloodData> cacheList) {

        if (mSession != null) {
            BloodDataDao bloodDataDao = mSession.getBloodDataDao();
            if (cacheList == null || cacheList.isEmpty()) {
                bloodDataDao.deleteAll();
            } else {
                bloodDataDao.deleteInTx(cacheList);
            }
        }
    }

    /**
     * 删除指定的压力数据
     *
     * @param cacheList
     */
    public void deleteStressData(List<StressData> cacheList) {

        if (mSession != null) {
            StressDataDao stressDataDao = mSession.getStressDataDao();
            if (cacheList == null || cacheList.isEmpty()) {
                stressDataDao.deleteAll();
            } else {
                stressDataDao.deleteInTx(cacheList);
            }
        }
    }

    /**
     * 删除指定的步频数据
     *
     * @param cacheList
     */
    public void deleteStepFreqData(List<StepFreqData> cacheList) {

        if (mSession != null) {
            StepFreqDataDao stepFreqDataDao = mSession.getStepFreqDataDao();
            if (cacheList == null || cacheList.isEmpty()) {
                stepFreqDataDao.deleteAll();
            } else {
                stepFreqDataDao.deleteInTx(cacheList);
            }
        }
    }

    /**
     * 删除指定的步幅数据
     *
     * @param cacheList
     */
    public void deleteStepPaceData(List<StepPaceData> cacheList) {

        if (mSession != null) {
            StepPaceDataDao stepPaceDataDao = mSession.getStepPaceDataDao();
            if (cacheList == null || cacheList.isEmpty()) {
                stepPaceDataDao.deleteAll();
            } else {
                stepPaceDataDao.deleteInTx(cacheList);
            }
        }
    }

    /**
     * 删除指定的静息心率数据
     *
     * @param cacheList
     */
    public void deleteRestingHeartData(List<RestingHeartData> cacheList) {

        if (mSession != null) {
            RestingHeartDataDao restingHeartDataDao = mSession.getRestingHeartDataDao();
            if (cacheList == null || cacheList.isEmpty()) {
                restingHeartDataDao.deleteAll();
            } else {
                restingHeartDataDao.deleteInTx(cacheList);
            }
        }
    }

    /**
     * 删除指定的习惯数据
     *
     * @param cacheList
     */
    public void deleteHabitData(List<HabitData> cacheList) {

        if (mSession != null) {
            HabitDataDao habitDataDao = mSession.getHabitDataDao();
            if (cacheList == null || cacheList.isEmpty()) {
                habitDataDao.deleteAll();
            } else {
                habitDataDao.deleteInTx(cacheList);
            }
        }
    }

    /**
     * 删除指定的睡眠得分数据
     *
     * @param cacheList
     */
    public void deleteSleepScoreData(List<SleepScore> cacheList) {
        if (mSession != null) {
            SleepScoreDao sleepScoreDao = mSession.getSleepScoreDao();
            if (cacheList == null || cacheList.isEmpty()) {
                sleepScoreDao.deleteAll();
            } else {
                sleepScoreDao.deleteInTx(cacheList);
            }
        }
    }

    public void deleteMotionHeartData(List<MotionHeart> cacheList) {
        if (mSession != null) {
            MotionHeartDao motionHeartDao = mSession.getMotionHeartDao();
            if (cacheList == null || cacheList.isEmpty()) {
                motionHeartDao.deleteAll();
            } else {
                motionHeartDao.deleteInTx(cacheList);
            }
        }
    }

    /**
     * 查询日常数据
     *
     * @return
     */
    public List<DailyData> queryDailyData() {
        if (mSession != null) {
            DailyDataDao dailyDataDao = mSession.getDailyDataDao();
            return dailyDataDao.queryBuilder().orderAsc(DailyDataDao.Properties.Time_stamp).list();
        }
        return null;
    }

    /**
     * 查询睡眠数据
     *
     * @return
     */
    public List<SleepData> querySleepData() {
        if (mSession != null) {
            SleepDataDao sleepDataDao = mSession.getSleepDataDao();
            return sleepDataDao.queryBuilder().orderAsc(SleepDataDao.Properties.Time_stamp).list();
        }
        return null;
    }

    /**
     * 查询心率数据
     *
     * @return
     */
    public List<HeartData> queryHeartData() {
        if (mSession != null) {
            HeartDataDao heartDataDao = mSession.getHeartDataDao();
            return heartDataDao.queryBuilder().orderAsc(HeartDataDao.Properties.Time_stamp).list();
        }
        return null;
    }

    /**
     * 查询GPS数据
     *
     * @return
     */
    public List<GpsData> queryGpsData() {
        if (mSession != null) {
            GpsDataDao gpsDataDao = mSession.getGpsDataDao();
            return gpsDataDao.queryBuilder().orderAsc(GpsDataDao.Properties.Time_stamp).list();
        }
        return null;
    }

    /**
     * 查询运动数据
     *
     * @return
     */
    public List<MultiData> queryMultiData() {
        if (mSession != null) {
            MultiDataDao multiDataDao = mSession.getMultiDataDao();
            return multiDataDao.queryBuilder().orderAsc(MultiDataDao.Properties.Begin_time_stamp).list();
        }
        return null;
    }

    /**
     * 查询血氧数据
     *
     * @return
     */
    public List<BloodData> queryBloodData() {
        if (mSession != null) {
            BloodDataDao bloodDataDao = mSession.getBloodDataDao();
            return bloodDataDao.queryBuilder().orderAsc(BloodDataDao.Properties.Time_stamp).list();
        }
        return null;
    }

    /**
     * 查询压力数据
     *
     * @return
     */
    public List<StressData> queryStressData() {
        if (mSession != null) {
            StressDataDao stressDataDao = mSession.getStressDataDao();
            return stressDataDao.queryBuilder().orderAsc(StressDataDao.Properties.Time_stamp).list();
        }
        return null;
    }

    /**
     * 查询步频数据
     *
     * @return
     */
    public List<StepFreqData> queryStepFreqData() {
        if (mSession != null) {
            StepFreqDataDao stepFreqDataDao = mSession.getStepFreqDataDao();
            return stepFreqDataDao.queryBuilder().orderAsc(StepFreqDataDao.Properties.Time_stamp).list();
        }
        return null;
    }

    /**
     * 查询步幅数据
     *
     * @return
     */
    public List<StepPaceData> queryStepPaceData() {
        if (mSession != null) {
            StepPaceDataDao stepPaceDataDao = mSession.getStepPaceDataDao();
            return stepPaceDataDao.queryBuilder().orderAsc(StepPaceDataDao.Properties.Time_stamp).list();
        }
        return null;
    }

    /**
     * 查询静息心率数据
     *
     * @return
     */
    public List<RestingHeartData> queryRestingHeartData() {
        if (mSession != null) {
            RestingHeartDataDao restingHeartDataDao = mSession.getRestingHeartDataDao();
            return restingHeartDataDao.queryBuilder().orderAsc(RestingHeartDataDao.Properties.Time_stamp).list();
        }
        return null;
    }

    /**
     * 查询习惯数据
     *
     * @return
     */
    public List<HabitData> queryHabitData() {
        if (mSession != null) {
            HabitDataDao habitDataDao = mSession.getHabitDataDao();
            return habitDataDao.queryBuilder().orderAsc(HabitDataDao.Properties.Time_stamp).list();
        }
        return null;
    }

    /**
     * 查询睡眠得分数据
     *
     * @return
     */
    public List<SleepScore> querySleepScoreData() {
        if (mSession != null) {
            SleepScoreDao sleepScoreDao = mSession.getSleepScoreDao();
            return sleepScoreDao.queryBuilder().orderAsc(SleepScoreDao.Properties.StartTime).list();
        }
        return null;
    }

    /**
     * 查询运动心率数据
     *
     * @return
     */
    public List<MotionHeart> queryMotionHeartData() {
        if (mSession != null) {
            MotionHeartDao motionHeartDao = mSession.getMotionHeartDao();
            return motionHeartDao.queryBuilder().orderAsc(MotionHeartDao.Properties.StampTime).list();
        }
        return null;
    }

    public void clearDb() {
        if (mSession != null) {
            mSession.getDailyDataDao().deleteAll();
            mSession.getSleepDataDao().deleteAll();
            mSession.getHeartDataDao().deleteAll();
            mSession.getGpsDataDao().deleteAll();
            mSession.getMultiDataDao().deleteAll();
            mSession.getBloodDataDao().deleteAll();
            mSession.getStressDataDao().deleteAll();
            mSession.getStepFreqDataDao().deleteAll();
            mSession.getStepPaceDataDao().deleteAll();
            mSession.getRestingHeartDataDao().deleteAll();
            mSession.getHabitDataDao().deleteAll();

        }
    }

}

