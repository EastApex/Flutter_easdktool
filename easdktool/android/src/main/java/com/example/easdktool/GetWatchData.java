package com.example.easdktool;

import android.os.Handler;
import android.os.Looper;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.apex.ax_bluetooth.callback.AttentionCallback;
import com.apex.ax_bluetooth.callback.BTMacCallback;
import com.apex.ax_bluetooth.callback.BatterInfoCallback;
import com.apex.ax_bluetooth.callback.BtStatusCallback;
import com.apex.ax_bluetooth.callback.CalorieSwitchCallback;
import com.apex.ax_bluetooth.callback.CombinationCallback;
import com.apex.ax_bluetooth.callback.DevicePwdCallback;
import com.apex.ax_bluetooth.callback.DistanceUnitCallback;
import com.apex.ax_bluetooth.callback.DonDisturbCallback;
import com.apex.ax_bluetooth.callback.FeaturesCallback;
import com.apex.ax_bluetooth.callback.GoalCallback;
import com.apex.ax_bluetooth.callback.HabitCallback;
import com.apex.ax_bluetooth.callback.HeartCheckCallback;
import com.apex.ax_bluetooth.callback.HeartLimitCallback;
import com.apex.ax_bluetooth.callback.InfoPushCallback;
import com.apex.ax_bluetooth.callback.LanguageCallback;
import com.apex.ax_bluetooth.callback.MenuCallback;
import com.apex.ax_bluetooth.callback.MonitorReminderCallback;
import com.apex.ax_bluetooth.callback.MotionAlarmHrCallback;
import com.apex.ax_bluetooth.callback.MsgContentCallback;
import com.apex.ax_bluetooth.callback.PeriodReminderCallback;
import com.apex.ax_bluetooth.callback.PersonInfoCallback;
import com.apex.ax_bluetooth.callback.RaiseHandBrightScreenCallback;
import com.apex.ax_bluetooth.callback.ReadBookListCallback;
import com.apex.ax_bluetooth.callback.RemindCallback;
import com.apex.ax_bluetooth.callback.RestScreenCallback;
import com.apex.ax_bluetooth.callback.ScreenBrightnessCallback;
import com.apex.ax_bluetooth.callback.SedentaryCheckCallback;
import com.apex.ax_bluetooth.callback.SleepBloodMonitorCallback;
import com.apex.ax_bluetooth.callback.SleepCheckCallback;
import com.apex.ax_bluetooth.callback.SosContactCallback;
import com.apex.ax_bluetooth.callback.StocksCallback;
import com.apex.ax_bluetooth.callback.StressMonitorCallback;
import com.apex.ax_bluetooth.callback.TimeCallback;
import com.apex.ax_bluetooth.callback.TodayTotalDataCallback;
import com.apex.ax_bluetooth.callback.UnitCallback;
import com.apex.ax_bluetooth.callback.VibrateCallback;
import com.apex.ax_bluetooth.callback.WatchFaceCallback;
import com.apex.ax_bluetooth.callback.WatchInfoCallback;
import com.apex.ax_bluetooth.callback.WeightUnitCallback;
import com.apex.ax_bluetooth.core.EABleManager;
import com.apex.ax_bluetooth.enumeration.QueryWatchInfoType;
import com.apex.ax_bluetooth.enumeration.VibrationIntensity;
import com.apex.ax_bluetooth.model.EABleAncsSw;
import com.apex.ax_bluetooth.model.EABleAutoCheckSleep;
import com.apex.ax_bluetooth.model.EABleAutoStressMonitor;
import com.apex.ax_bluetooth.model.EABleBatInfo;
import com.apex.ax_bluetooth.model.EABleCombination;
import com.apex.ax_bluetooth.model.EABleContact;
import com.apex.ax_bluetooth.model.EABleDailyGoal;
import com.apex.ax_bluetooth.model.EABleDevUnit;
import com.apex.ax_bluetooth.model.EABleDeviceLanguage;
import com.apex.ax_bluetooth.model.EABleDevicePwd;
import com.apex.ax_bluetooth.model.EABleDistanceFormat;
import com.apex.ax_bluetooth.model.EABleFeatures;
import com.apex.ax_bluetooth.model.EABleGesturesBrightScreen;
import com.apex.ax_bluetooth.model.EABleHabit;
import com.apex.ax_bluetooth.model.EABleHr;
import com.apex.ax_bluetooth.model.EABleInfoPush;
import com.apex.ax_bluetooth.model.EABleMenuPage;
import com.apex.ax_bluetooth.model.EABleMonitorReminder;
import com.apex.ax_bluetooth.model.EABleMotionAlarmHr;
import com.apex.ax_bluetooth.model.EABleNotDisturb;
import com.apex.ax_bluetooth.model.EABlePeriodReminder;
import com.apex.ax_bluetooth.model.EABlePersonInfo;
import com.apex.ax_bluetooth.model.EABleReminder;
import com.apex.ax_bluetooth.model.EABleSedentariness;
import com.apex.ax_bluetooth.model.EABleSleepBloodSwitch;
import com.apex.ax_bluetooth.model.EABleSoSContact;
import com.apex.ax_bluetooth.model.EABleStocks;
import com.apex.ax_bluetooth.model.EABleSyncTime;
import com.apex.ax_bluetooth.model.EABleWatchFace;
import com.apex.ax_bluetooth.model.EABleWatchInfo;
import com.apex.ax_bluetooth.model.EABleWeightFormat;
import com.apex.ax_bluetooth.model.QueryInfo;
import com.apex.ax_bluetooth.model.TodayTotalData;
import com.apex.ax_bluetooth.utils.LogUtils;
import com.example.easdktool.been.ReminderItem;

import org.greenrobot.greendao.query.Query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.flutter.plugin.common.MethodChannel;

public class GetWatchData {
    final String TAG=this.getClass().getSimpleName();
    private MethodChannel channel;
    final String kGetWatchResponse = "GetWatchResponse";

    public GetWatchData(MethodChannel channel) {
        this.channel = channel;
    }

    public void getWatchInfo() {//获取手表信息
        EABleManager.getInstance().queryWatchInfo(QueryWatchInfoType.watch_info, new WatchInfoCallback() {
            @Override
            public void watchInfo(final EABleWatchInfo eaBleWatchInfo) {
                Map map = new Object2Map().watchInfo2Map(eaBleWatchInfo);
                new Return2Flutter(channel).sendWatchDataWithMap(map, 3);


            }

            @Override
            public void mutualFail(int i) {
            }
        });
    }

    public void getUserInfo() {//获取用户信息
        EABleManager.getInstance().queryWatchInfo(QueryWatchInfoType.user_info, new PersonInfoCallback() {
            @Override
            public void personInfo(final EABlePersonInfo eaBlePersonInfo) {
                Map map = new Object2Map().userInfo2Map(eaBlePersonInfo);
                new Return2Flutter(channel).sendWatchDataWithMap(map, 4);
            }

            @Override
            public void mutualFail(int i) {

            }
        });
    }

    public void getSyncTime() {//获取手表时间
        EABleManager.getInstance().queryWatchInfo(QueryWatchInfoType.sync_time, new TimeCallback() {
            @Override
            public void syncTime(final EABleSyncTime eaBleSyncTime) {
                Map map = new Object2Map().time2Map(eaBleSyncTime);
                new Return2Flutter(channel).sendWatchDataWithMap(map, 5);
            }

            @Override
            public void mutualFail(int i) {

            }
        });
    }

    public void getOffTime() {//获取息屏时间
        EABleManager.getInstance().queryWatchInfo(QueryWatchInfoType.black_screen_time, new RestScreenCallback() {
            @Override
            public void restScreen(int i) {
                new Return2Flutter(channel).sendWatchDataWithOtherKeyValue("timeout", i, 8);

            }

            @Override
            public void mutualFail(int i) {
            }
        });
    }

    public void getScreenLight() {//获取手表屏幕亮度
        EABleManager.getInstance().queryWatchInfo(QueryWatchInfoType.screen_light, new ScreenBrightnessCallback() {
            @Override
            public void screenBrightness(int i) {
                new Return2Flutter(channel).sendWatchDataWithOtherKeyValue("level", i, 7);
            }

            @Override
            public void mutualFail(int i) {
            }
        });
    }

    public void getBatteryInfo() {//获取手表电量信息
        EABleManager.getInstance().queryWatchInfo(QueryWatchInfoType.battery_info, new BatterInfoCallback() {
            @Override
            public void batterInfo(final EABleBatInfo eaBleBatInfo) {
                Map map = new Object2Map().battery2Map(eaBleBatInfo);
                new Return2Flutter(channel).sendWatchDataWithMap(map, 9);
            }

            @Override
            public void mutualFail(int i) {
            }
        });
    }

    public void getLanguageInfo() {//获取设备语言信息
        EABleManager.getInstance().queryWatchInfo(QueryWatchInfoType.language, new LanguageCallback() {
            @Override
            public void languageInfo(final EABleDeviceLanguage eaBleDeviceLanguage) {
                //   Map<String, Integer> map = new HashMap();
                //  map.put("eType", eaBleDeviceLanguage.getE_type().getValue());
                Map map = new Object2Map().language2Map(eaBleDeviceLanguage);
                new Return2Flutter(channel).sendWatchDataWithMap(map, 10);
            }

            @Override
            public void mutualFail(int i) {
            }
        });
    }

    public void getUnitInfo() {
        EABleManager.getInstance().queryWatchInfo(QueryWatchInfoType.unit_format, new UnitCallback() {
            @Override
            public void unitInfo(final EABleDevUnit eaBleDevUnit) {
                Map<String, Integer> map = new HashMap();
                map.put("eFormat", eaBleDevUnit.getE_format().getValue());
                new Return2Flutter(channel).sendWatchDataWithMap(map, 11);

            }

            @Override
            public void mutualFail(int i) {

            }
        });
    }

    public void getDisturbInfo() {
        EABleManager.getInstance().queryWatchInfo(QueryWatchInfoType.not_disturb, new DonDisturbCallback() {
            @Override
            public void donDisturbInfo(final EABleNotDisturb eaBleNotDisturb) {
                Map map = new Object2Map().disturb2Map(eaBleNotDisturb);
                new Return2Flutter(channel).sendWatchDataWithMap(map, 13);
            }

            @Override
            public void mutualFail(int i) {
            }
        });
    }

    public void getGoalInfo() {
        EABleManager.getInstance().queryWatchInfo(QueryWatchInfoType.daily_goal, new GoalCallback() {
            @Override
            public void goalInfo(final EABleDailyGoal eaBleDailyGoal) {
                Map map = new Object2Map().goal2Map(eaBleDailyGoal);
                new Return2Flutter(channel).sendWatchDataWithMap(map, 15);

            }

            @Override
            public void mutualFail(int i) {

            }
        });
    }

    public void getSleepCheckInfo() {
        EABleManager.getInstance().queryWatchInfo(QueryWatchInfoType.sleep_check, new SleepCheckCallback() {
            @Override
            public void sleepInfo(final EABleAutoCheckSleep eaBleAutoCheckSleep) {
                Map map = new Object2Map().sleep2Map(eaBleAutoCheckSleep);
                new Return2Flutter(channel).sendWatchDataWithMap(map, 16);
            }

            @Override
            public void mutualFail(int i) {

            }
        });
    }

    public void getHeartCheck() {
        EABleManager.getInstance().queryWatchInfo(QueryWatchInfoType.heart_rate_check, new HeartCheckCallback() {
            @Override
            public void heartInfo(int i) {
                new Return2Flutter(channel).sendWatchDataWithOtherKeyValue("interval", i, 17);
            }

            @Override
            public void mutualFail(int i) {

            }
        });
    }

    public void getSedentaryInfo() {
        EABleManager.getInstance().queryWatchInfo(QueryWatchInfoType.sit_check, new SedentaryCheckCallback() {
            @Override
            public void sedentaryInfo(final EABleSedentariness eaBleSedentariness) {
                Map map = new Object2Map().sedentariness2Map(eaBleSedentariness);
                new Return2Flutter(channel).sendWatchDataWithMap(map, 18);

            }

            @Override
            public void mutualFail(int i) {

            }
        });
    }

    public void getReminderInfo() {
        EABleManager.getInstance().queryWatchInfo(QueryWatchInfoType.reminder, new AttentionCallback() {
            @Override
            public void attentionInfo(final EABleReminder eaBleReminder) {
                Map map = new Object2Map().reminder2Map(eaBleReminder);
                new Return2Flutter(channel).sendWatchDataWithMap(map, 22);

            }

            @Override
            public void mutualFail(int i) {

            }
        });
    }

    public void getAncsInfo() {
        EABleManager.getInstance().queryWatchInfo(QueryWatchInfoType.ancs_sw, new RemindCallback() {
            @Override
            public void remindInfo(final EABleAncsSw eaBleAncsSw) {
                Map map = new Object2Map().ancs2Map(eaBleAncsSw);
                new Return2Flutter(channel).sendWatchDataWithMap(map, 21);

            }

            @Override
            public void mutualFail(int i) {

            }
        });
    }

    public void getDistanceUnit() {
        EABleManager.getInstance().queryWatchInfo(QueryWatchInfoType.distance_unit, new DistanceUnitCallback() {
            @Override
            public void distanceUnitInfo(final EABleDistanceFormat eaBleDistanceFormat) {
                new Return2Flutter(channel).sendWatchDataWithOtherKeyValue("eFormat", eaBleDistanceFormat.getE_format().getValue(), 24);

            }

            @Override
            public void mutualFail(int i) {

            }
        });
    }

    public void getWeightUnit() {
        EABleManager.getInstance().queryWatchInfo(QueryWatchInfoType.weight_unit, new WeightUnitCallback() {
            @Override
            public void weightUnitInfo(final EABleWeightFormat eaBleWeightFormat) {
                new Return2Flutter(channel).sendWatchDataWithOtherKeyValue("eFormat", eaBleWeightFormat.getE_format().getValue(), 25);


            }

            @Override
            public void mutualFail(int i) {

            }
        });
    }

    public void getHeartAlert() {
        EABleManager.getInstance().queryWatchInfo(QueryWatchInfoType.heart_rate_limit, new HeartLimitCallback() {
            @Override
            public void heartLimitInfo(final EABleHr eaBleHr) {
                Map map = new Object2Map().alertHeart2Map(eaBleHr);
                new Return2Flutter(channel).sendWatchDataWithMap(map, 26);

            }

            @Override
            public void mutualFail(int i) {

            }
        });
    }

    public void getBaseCalories() {
        EABleManager.getInstance().queryWatchInfo(QueryWatchInfoType.base_calories, new CalorieSwitchCallback() {
            @Override
            public void switchInfo(int i) {
                new Return2Flutter(channel).sendWatchDataWithOtherKeyValue("sw", i, 27);
            }

            @Override
            public void mutualFail(int i) {

            }
        });
    }

    public void getGesturesInfo() {
        EABleManager.getInstance().queryWatchInfo(QueryWatchInfoType.gestures, new RaiseHandBrightScreenCallback() {
            @Override
            public void switchInfo(final EABleGesturesBrightScreen eaBleGesturesBrightScreen) {
                Map map = new Object2Map().gestures2Map(eaBleGesturesBrightScreen);
                new Return2Flutter(channel).sendWatchDataWithMap(map, 28);

            }

            @Override
            public void mutualFail(int i) {

            }
        });
    }

    public void getPageInfo() {
        EABleManager.getInstance().queryWatchInfo(QueryWatchInfoType.home_page, new MenuCallback() {
            @Override
            public void menuInfo(final EABleMenuPage eaBleMenuPage) {
                Map map = new Object2Map().page2Map(eaBleMenuPage);
                new Return2Flutter(channel).sendWatchDataWithMap(map, 31);

            }

            @Override
            public void mutualFail(int i) {

            }
        });
    }

    public void getCombinationInfo() {
        EABleManager.getInstance().queryWatchInfo(QueryWatchInfoType.combination, new CombinationCallback() {
            @Override
            public void combinationInfo(final EABleCombination eaBleCombination) {
                Map map = new Object2Map().combination2Map(eaBleCombination);
                new Return2Flutter(channel).sendWatchDataWithMap(map, 30);

            }

            @Override
            public void mutualFail(int i) {
            }
        });
    }

    public void getDialInfo() {
        EABleManager.getInstance().queryWatchInfo(QueryWatchInfoType.dial, new WatchFaceCallback() {
            @Override
            public void watchFaceInfo(final EABleWatchFace eaBleWatchFace) {
                Map map = new Object2Map().face2Map(eaBleWatchFace);
                new Return2Flutter(channel).sendWatchDataWithMap(map, 33);

            }

            @Override
            public void mutualFail(int i) {
            }
        });
    }

    public void getPushInfo() {
        EABleManager.getInstance().queryWatchInfo(QueryWatchInfoType.push_info, new InfoPushCallback() {
            @Override
            public void pushInfo(final EABleInfoPush eaBleInfoPush) {
                Map map = new Object2Map().appPushSwitch2Map(eaBleInfoPush);
                new Return2Flutter(channel).sendWatchDataWithMap(map, 34);
            }

            @Override
            public void mutualFail(int i) {
            }
        });
    }

    public void getHabitInfo() {
        EABleManager.getInstance().queryWatchInfo(QueryWatchInfoType.habit, new HabitCallback() {
            @Override
            public void habitInfo(final EABleHabit eaBleHabit) {
                if (eaBleHabit != null) {
                    Map map = new Object2Map().habit2Map(eaBleHabit);
                    new Return2Flutter(channel).sendWatchDataWithMap(map, 38);

                }
            }

            @Override
            public void mutualFail(int i) {

            }
        });
    }

    public void getTodayData() {
        EABleManager.getInstance().queryWatchInfo(QueryWatchInfoType.todayData, new TodayTotalDataCallback() {
            @Override
            public void todayData(final TodayTotalData todayTotalData) {
                Map map = new Object2Map().today2Map(todayTotalData);
                new Return2Flutter(channel).sendWatchDataWithMap(map, 40);

            }

            @Override
            public void mutualFail(int i) {

            }
        });
    }

    public void getContact() {
        EABleManager.getInstance().queryWatchInfo(QueryWatchInfoType.bookList, new ReadBookListCallback() {
            @Override
            public void bookList(List<EABleContact> list) {
                Map map = new Object2Map().contact(list);
                new Return2Flutter(channel).sendWatchDataWithMap(map, 43);

            }

            @Override
            public void mutualFail(int i) {

            }
        });
    }

    public void getFunctionList() {
        EABleManager.getInstance().queryWatchInfo(QueryWatchInfoType.features, new FeaturesCallback() {
            @Override
            public void featuresList(EABleFeatures eaBleFeatures) {

            }

            @Override
            public void mutualFail(int i) {

            }
        });
    }

    public void getSleepSpo2Check() {
        EABleManager.getInstance().queryWatchInfo(QueryWatchInfoType.sleep_blood_monitor, new SleepBloodMonitorCallback() {
            @Override
            public void sleepBloodMonitor(EABleSleepBloodSwitch eaBleSleepBloodSwitch) {
                LogUtils.i(TAG,"查询出来的eaBleSleepBloodSwitch:"+JSONObject.toJSONString(eaBleSleepBloodSwitch));
                Map map = new Object2Map().sleepSpo2Check(eaBleSleepBloodSwitch);
                new Return2Flutter(channel).sendWatchDataWithMap(map, 50);

            }

            @Override
            public void mutualFail(int i) {

            }
        });
    }

    public void getStressCheck() {
        EABleManager.getInstance().queryWatchInfo(QueryWatchInfoType.stress_monitor, new StressMonitorCallback() {
            @Override
            public void stressSwitch(EABleAutoStressMonitor eaBleAutoStressMonitor) {
                LogUtils.i(TAG,"查询出来的eaBleAutoStressMonitor:"+JSONObject.toJSONString(eaBleAutoStressMonitor));
                Map map = new Object2Map().stressCheck(eaBleAutoStressMonitor);
                new Return2Flutter(channel).sendWatchDataWithMap(map, 51);

            }

            @Override
            public void mutualFail(int i) {

            }
        });
    }

    public void getVibrateMode() {
        EABleManager.getInstance().queryWatchInfo(QueryWatchInfoType.vibrate_mode, new VibrateCallback() {
            @Override
            public void vibrateMode(VibrationIntensity vibrationIntensity) {
                LogUtils.i(TAG,"查询出来的vibrationIntensity:"+JSONObject.toJSONString(vibrationIntensity.getValue()));
                Map<String, Integer> map = new HashMap();
                map.put("eVibrateIntensity", vibrationIntensity.getValue());
                new Return2Flutter(channel).sendWatchDataWithMap(map, 53);
            }

            @Override
            public void mutualFail(int i) {

            }
        });
    }

    public void getPeriodReminder() {
        EABleManager.getInstance().queryWatchInfo(QueryWatchInfoType.period_reminder, new PeriodReminderCallback() {
            @Override
            public void periodReminderInfo(EABlePeriodReminder eaBlePeriodReminder) {
                LogUtils.i(TAG,"查询出来的eaBlePeriodReminder:"+JSONObject.toJSONString(eaBlePeriodReminder));
                Map map = new Object2Map().periodReminder(eaBlePeriodReminder);
                new Return2Flutter(channel).sendWatchDataWithMap(map,55);

            }

            @Override
            public void mutualFail(int i) {

            }
        });
    }

    public void getMotionHeartAlert() {
        EABleManager.getInstance().queryWatchInfo(QueryWatchInfoType.motion_heart_alarm, new MotionAlarmHrCallback() {
            @Override
            public void alarmHr(EABleMotionAlarmHr eaBleMotionAlarmHr) {

            }

            @Override
            public void mutualFail(int i) {

            }
        });
    }

    public void getSosContact() {
        EABleManager.getInstance().queryWatchInfo(QueryWatchInfoType.sos_contact, new SosContactCallback() {
            @Override
            public void sosContact(EABleSoSContact eaBleSoSContact) {

            }

            @Override
            public void mutualFail(int i) {

            }
        });
    }

    public void getBtStatus() {
        EABleManager.getInstance().queryWatchInfo(QueryWatchInfoType.bt_status, new BtStatusCallback() {
            @Override
            public void btStatus(int i) {

            }

            @Override
            public void mutualFail(int i) {

            }
        });
    }

    public void getCustomReplayInfo() {
        EABleManager.getInstance().queryWatchInfo(QueryWatchInfoType.msgContent, new MsgContentCallback() {
            @Override
            public void msgInfo(List<String> list) {

            }

            @Override
            public void mutualFail(int i) {

            }
        });

    }

    public void getBtAddress() {
        EABleManager.getInstance().queryWatchInfo(QueryWatchInfoType.bk_mac, new BTMacCallback() {
            @Override
            public void macData(String s) {

            }

            @Override
            public void mutualFail(int i) {

            }
        });
    }

    public void getStockInfo() {
        EABleManager.getInstance().queryWatchInfo(QueryWatchInfoType.stocks, new StocksCallback() {
            @Override
            public void stocksInfo(EABleStocks eaBleStocks) {

            }

            @Override
            public void mutualFail(int i) {

            }
        });
    }

    public void getLockScreenPassword() {
        EABleManager.getInstance().queryWatchInfo(QueryWatchInfoType.device_pwd, new DevicePwdCallback() {
            @Override
            public void devicePwd(EABleDevicePwd eaBleDevicePwd) {

            }

            @Override
            public void mutualFail(int i) {

            }
        });
    }

    public void getNewWatchData(final int dataType, int type) {
        QueryWatchInfoType infoType = QueryWatchInfoType.watch_info;
        switch (dataType) {
            case 45:
                infoType = QueryWatchInfoType.monitor_reminder;
                break;
            default:
                break;
        }
        QueryInfo queryInfo = new QueryInfo();
        queryInfo.setQueryWatchInfoType(infoType);
        queryInfo.setDataType(type);
        EABleManager.getInstance().queryInfo(queryInfo, new MonitorReminderCallback() {
            @Override
            public void mutualFail(int i) {

            }

            @Override
            public void monitorReminder(final EABleMonitorReminder eaBleMonitorReminder) {
                Map map = new Object2Map().monitorReminder2Map(eaBleMonitorReminder);
                new Return2Flutter(channel).sendWatchDataWithMap(map, dataType);
            }
        });
    }


}
