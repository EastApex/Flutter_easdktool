package com.example.easdktool.gen;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.example.easdktool.db.BloodData;
import com.example.easdktool.db.DailyData;
import com.example.easdktool.db.GpsData;
import com.example.easdktool.db.HabitData;
import com.example.easdktool.db.HeartData;
import com.example.easdktool.db.MotionHeart;
import com.example.easdktool.db.MultiData;
import com.example.easdktool.db.RestingHeartData;
import com.example.easdktool.db.SleepData;
import com.example.easdktool.db.SleepScore;
import com.example.easdktool.db.StepFreqData;
import com.example.easdktool.db.StepPaceData;
import com.example.easdktool.db.StressData;

import com.example.easdktool.gen.BloodDataDao;
import com.example.easdktool.gen.DailyDataDao;
import com.example.easdktool.gen.GpsDataDao;
import com.example.easdktool.gen.HabitDataDao;
import com.example.easdktool.gen.HeartDataDao;
import com.example.easdktool.gen.MotionHeartDao;
import com.example.easdktool.gen.MultiDataDao;
import com.example.easdktool.gen.RestingHeartDataDao;
import com.example.easdktool.gen.SleepDataDao;
import com.example.easdktool.gen.SleepScoreDao;
import com.example.easdktool.gen.StepFreqDataDao;
import com.example.easdktool.gen.StepPaceDataDao;
import com.example.easdktool.gen.StressDataDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig bloodDataDaoConfig;
    private final DaoConfig dailyDataDaoConfig;
    private final DaoConfig gpsDataDaoConfig;
    private final DaoConfig habitDataDaoConfig;
    private final DaoConfig heartDataDaoConfig;
    private final DaoConfig motionHeartDaoConfig;
    private final DaoConfig multiDataDaoConfig;
    private final DaoConfig restingHeartDataDaoConfig;
    private final DaoConfig sleepDataDaoConfig;
    private final DaoConfig sleepScoreDaoConfig;
    private final DaoConfig stepFreqDataDaoConfig;
    private final DaoConfig stepPaceDataDaoConfig;
    private final DaoConfig stressDataDaoConfig;

    private final BloodDataDao bloodDataDao;
    private final DailyDataDao dailyDataDao;
    private final GpsDataDao gpsDataDao;
    private final HabitDataDao habitDataDao;
    private final HeartDataDao heartDataDao;
    private final MotionHeartDao motionHeartDao;
    private final MultiDataDao multiDataDao;
    private final RestingHeartDataDao restingHeartDataDao;
    private final SleepDataDao sleepDataDao;
    private final SleepScoreDao sleepScoreDao;
    private final StepFreqDataDao stepFreqDataDao;
    private final StepPaceDataDao stepPaceDataDao;
    private final StressDataDao stressDataDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        bloodDataDaoConfig = daoConfigMap.get(BloodDataDao.class).clone();
        bloodDataDaoConfig.initIdentityScope(type);

        dailyDataDaoConfig = daoConfigMap.get(DailyDataDao.class).clone();
        dailyDataDaoConfig.initIdentityScope(type);

        gpsDataDaoConfig = daoConfigMap.get(GpsDataDao.class).clone();
        gpsDataDaoConfig.initIdentityScope(type);

        habitDataDaoConfig = daoConfigMap.get(HabitDataDao.class).clone();
        habitDataDaoConfig.initIdentityScope(type);

        heartDataDaoConfig = daoConfigMap.get(HeartDataDao.class).clone();
        heartDataDaoConfig.initIdentityScope(type);

        motionHeartDaoConfig = daoConfigMap.get(MotionHeartDao.class).clone();
        motionHeartDaoConfig.initIdentityScope(type);

        multiDataDaoConfig = daoConfigMap.get(MultiDataDao.class).clone();
        multiDataDaoConfig.initIdentityScope(type);

        restingHeartDataDaoConfig = daoConfigMap.get(RestingHeartDataDao.class).clone();
        restingHeartDataDaoConfig.initIdentityScope(type);

        sleepDataDaoConfig = daoConfigMap.get(SleepDataDao.class).clone();
        sleepDataDaoConfig.initIdentityScope(type);

        sleepScoreDaoConfig = daoConfigMap.get(SleepScoreDao.class).clone();
        sleepScoreDaoConfig.initIdentityScope(type);

        stepFreqDataDaoConfig = daoConfigMap.get(StepFreqDataDao.class).clone();
        stepFreqDataDaoConfig.initIdentityScope(type);

        stepPaceDataDaoConfig = daoConfigMap.get(StepPaceDataDao.class).clone();
        stepPaceDataDaoConfig.initIdentityScope(type);

        stressDataDaoConfig = daoConfigMap.get(StressDataDao.class).clone();
        stressDataDaoConfig.initIdentityScope(type);

        bloodDataDao = new BloodDataDao(bloodDataDaoConfig, this);
        dailyDataDao = new DailyDataDao(dailyDataDaoConfig, this);
        gpsDataDao = new GpsDataDao(gpsDataDaoConfig, this);
        habitDataDao = new HabitDataDao(habitDataDaoConfig, this);
        heartDataDao = new HeartDataDao(heartDataDaoConfig, this);
        motionHeartDao = new MotionHeartDao(motionHeartDaoConfig, this);
        multiDataDao = new MultiDataDao(multiDataDaoConfig, this);
        restingHeartDataDao = new RestingHeartDataDao(restingHeartDataDaoConfig, this);
        sleepDataDao = new SleepDataDao(sleepDataDaoConfig, this);
        sleepScoreDao = new SleepScoreDao(sleepScoreDaoConfig, this);
        stepFreqDataDao = new StepFreqDataDao(stepFreqDataDaoConfig, this);
        stepPaceDataDao = new StepPaceDataDao(stepPaceDataDaoConfig, this);
        stressDataDao = new StressDataDao(stressDataDaoConfig, this);

        registerDao(BloodData.class, bloodDataDao);
        registerDao(DailyData.class, dailyDataDao);
        registerDao(GpsData.class, gpsDataDao);
        registerDao(HabitData.class, habitDataDao);
        registerDao(HeartData.class, heartDataDao);
        registerDao(MotionHeart.class, motionHeartDao);
        registerDao(MultiData.class, multiDataDao);
        registerDao(RestingHeartData.class, restingHeartDataDao);
        registerDao(SleepData.class, sleepDataDao);
        registerDao(SleepScore.class, sleepScoreDao);
        registerDao(StepFreqData.class, stepFreqDataDao);
        registerDao(StepPaceData.class, stepPaceDataDao);
        registerDao(StressData.class, stressDataDao);
    }
    
    public void clear() {
        bloodDataDaoConfig.clearIdentityScope();
        dailyDataDaoConfig.clearIdentityScope();
        gpsDataDaoConfig.clearIdentityScope();
        habitDataDaoConfig.clearIdentityScope();
        heartDataDaoConfig.clearIdentityScope();
        motionHeartDaoConfig.clearIdentityScope();
        multiDataDaoConfig.clearIdentityScope();
        restingHeartDataDaoConfig.clearIdentityScope();
        sleepDataDaoConfig.clearIdentityScope();
        sleepScoreDaoConfig.clearIdentityScope();
        stepFreqDataDaoConfig.clearIdentityScope();
        stepPaceDataDaoConfig.clearIdentityScope();
        stressDataDaoConfig.clearIdentityScope();
    }

    public BloodDataDao getBloodDataDao() {
        return bloodDataDao;
    }

    public DailyDataDao getDailyDataDao() {
        return dailyDataDao;
    }

    public GpsDataDao getGpsDataDao() {
        return gpsDataDao;
    }

    public HabitDataDao getHabitDataDao() {
        return habitDataDao;
    }

    public HeartDataDao getHeartDataDao() {
        return heartDataDao;
    }

    public MotionHeartDao getMotionHeartDao() {
        return motionHeartDao;
    }

    public MultiDataDao getMultiDataDao() {
        return multiDataDao;
    }

    public RestingHeartDataDao getRestingHeartDataDao() {
        return restingHeartDataDao;
    }

    public SleepDataDao getSleepDataDao() {
        return sleepDataDao;
    }

    public SleepScoreDao getSleepScoreDao() {
        return sleepScoreDao;
    }

    public StepFreqDataDao getStepFreqDataDao() {
        return stepFreqDataDao;
    }

    public StepPaceDataDao getStepPaceDataDao() {
        return stepPaceDataDao;
    }

    public StressDataDao getStressDataDao() {
        return stressDataDao;
    }

}
