package com.example.easdktool;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.apex.ax_bluetooth.callback.GeneralCallback;
import com.apex.ax_bluetooth.core.EABleManager;

import com.apex.ax_bluetooth.enumeration.MotionReportType;
import com.apex.ax_bluetooth.enumeration.VibrationIntensity;
import com.apex.ax_bluetooth.model.EABleAncsSw;
import com.apex.ax_bluetooth.model.EABleAutoStressMonitor;
import com.apex.ax_bluetooth.model.EABleBindInfo;
import com.apex.ax_bluetooth.model.EABleContact;
import com.apex.ax_bluetooth.model.EABleDailyGoal;
import com.apex.ax_bluetooth.model.EABleDev;
import com.apex.ax_bluetooth.model.EABleDevUnit;
import com.apex.ax_bluetooth.model.EABleDeviceLanguage;
import com.apex.ax_bluetooth.model.EABleGesturesBrightScreen;
import com.apex.ax_bluetooth.model.EABleHabit;
import com.apex.ax_bluetooth.model.EABleHr;
import com.apex.ax_bluetooth.model.EABleInfoPush;
import com.apex.ax_bluetooth.model.EABleMenuPage;
import com.apex.ax_bluetooth.model.EABleMonitorReminder;
import com.apex.ax_bluetooth.model.EABleMusicRespond;
import com.apex.ax_bluetooth.model.EABleNotDisturb;
import com.apex.ax_bluetooth.model.EABlePeriodReminder;
import com.apex.ax_bluetooth.model.EABlePersonInfo;
import com.apex.ax_bluetooth.model.EABlePhysiologyData;
import com.apex.ax_bluetooth.model.EABleReminder;
import com.apex.ax_bluetooth.model.EABleSedentariness;
import com.apex.ax_bluetooth.model.EABleSleepBloodSwitch;
import com.apex.ax_bluetooth.model.EABleSocialContact;
import com.apex.ax_bluetooth.model.EABleSyncTime;
import com.apex.ax_bluetooth.model.EABleWatchFace;
import com.apex.ax_bluetooth.model.EABleWeather;
import com.apex.ax_bluetooth.utils.LogUtils;
import com.example.easdktool.callback.DataResponseCall;
import com.example.easdktool.callback.EditAttentionCall;
import com.example.easdktool.callback.HabitResultCall;
import com.example.easdktool.callback.SetCallback;
import com.example.easdktool.db.DataManager;
import com.google.gson.Gson;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import io.flutter.plugin.common.MethodChannel;

public class SetWatchData {
    private MethodChannel channel;
    final String TAG = this.getClass().getSimpleName();
    final String kArgumentsError = "ArgumentsError";
    EABleBindInfo eaBleBindInfo;
    final String kOperationWacthResponse = "OperationWacthResponse";
    Handler mHandler;


    public SetWatchData(MethodChannel channel) {
        this.channel = channel;
    }

    public void setUserInfo(String jsonString) {
        EABlePersonInfo eaBlePersonInfo = new String2Object().string2User(jsonString);
        EABleManager.getInstance().setUserInfo(eaBlePersonInfo, new SetCallback(4, channel));
    }

    public void syncTime(String jsonString) {
        EABleSyncTime eaBleSyncTime = new String2Object().string2Time(jsonString);
        EABleManager.getInstance().setTimeSync(eaBleSyncTime, new SetCallback(5, channel));
    }

    public void syncLanguage(String jsonString) {
        EABleDeviceLanguage eaBleDeviceLanguage = new String2Object().string2Language(jsonString);
        EABleManager.getInstance().setDevLanguage(eaBleDeviceLanguage, new SetCallback(10, channel));
    }

    public void setUnit(String jsonString) {
        EABleDevUnit eaBleDevUnit = new String2Object().string2Unit(jsonString);
        EABleManager.getInstance().setUnifiedUnit(eaBleDevUnit, new SetCallback(11, channel));
    }

    public void setDisturb(String jsonString) {
        EABleNotDisturb eaBleNotDisturb = new String2Object().string2Disturb(jsonString);
        EABleManager.getInstance().setNotDisturb(eaBleNotDisturb, new SetCallback(13, channel));
    }

    public void setGoal(String jsonString) {
        EABleDailyGoal eaBleDailyData = new String2Object().string2Goal(jsonString);
        EABleManager.getInstance().setDailyGoal(eaBleDailyData, new SetCallback(15, channel));
    }

    public void setHeartAutoCheck(String jsonString) {
        int intervalTime = new String2Object().string2HeartSwitch(jsonString);
        Log.e(TAG, "自动心率检测间隔时间:" + intervalTime);
        EABleManager.getInstance().setHeartRateIntervalTime(intervalTime, new SetCallback(17, channel));
    }

    public void setSedentarinessInfo(String jsonString) {
        EABleSedentariness eaBleSedentariness = new String2Object().string2Sedentariness(jsonString);
        EABleManager.getInstance().setSitCheck(eaBleSedentariness, new SetCallback(18, channel));

    }

    public void setWeatherInfo(String jsonString) {
        EABleWeather eaBleWeather = new String2Object().string2Weather(jsonString);
        EABleManager.getInstance().setWeather(eaBleWeather, new SetCallback(20, channel));
    }

    public void setSocialSwitch(String jsonString) {

        EABleAncsSw eaBleAncsSw = new String2Object().string2Ancs(jsonString);
        EABleManager.getInstance().setAncsSwitch(eaBleAncsSw, new SetCallback(21, channel));
    }

    public void setReminder(String jsonString) {
        EABleReminder eaBleReminder = new String2Object().string2Reminder(jsonString);
        EABleManager.getInstance().setReminderOrder(eaBleReminder, new EditAttentionCall(channel));
    }

    public void setHeartAlertInfo(String jsonString) {
        EABleHr eaBleHr = new String2Object().string2HeartAlert(jsonString);
        EABleManager.getInstance().setHeartRateLimit(eaBleHr, new SetCallback(26, channel));
    }

    public void setHabitInfo(String jsonString) {
        EABleHabit eaBleHabit = new String2Object().string2Habit(jsonString);
        LogUtils.i(TAG, "开始添加习惯");
        EABleManager.getInstance().setHabit(eaBleHabit, new HabitResultCall(channel));
    }

    public void setGesturesInfo(String jsonString) {
        EABleGesturesBrightScreen eaBleGesturesBrightScreen = new String2Object().string2Gesture(jsonString);
        EABleManager.getInstance().setGesturesSwitch(eaBleGesturesBrightScreen, new SetCallback(28, channel));

    }

    public void setHomePage(String jsonString) {
        EABleMenuPage homePage = new String2Object().string2Page(jsonString);
        EABleManager.getInstance().setMenuPage(homePage, new SetCallback(31, channel));
    }

    public void setMenstrual(String jsonString) {
        EABlePhysiologyData eaBlePhysiologyData = new String2Object().string2Physiology(jsonString);
        EABleManager.getInstance().setMenstrualCycle(eaBlePhysiologyData, false, new SetCallback(32, channel));

    }

    public void syncMusicInfo(String jsonString) {
        EABleMusicRespond eaBleMusicRespond = new String2Object().string2Music(jsonString);
        LogUtils.i(TAG, "Send music information to the watch:" + new Gson().toJson(eaBleMusicRespond));
        EABleManager.getInstance().musicQueryResponse(eaBleMusicRespond, new DataResponseCall(channel));

    }

    public void setSystemDial(String jsonString) {
        EABleWatchFace eaBleWatchFace = new String2Object().string2Face(jsonString);
        EABleManager.getInstance().setWatchFace(eaBleWatchFace, new SetCallback(33, channel));
    }

    public void setAppPushSwitch(String jsonString) {

        EABleInfoPush eaBleInfoPush = new String2Object().string2PushSwitch(jsonString);
        EABleManager.getInstance().setAppPushSwitch(eaBleInfoPush, new SetCallback(34, channel));

    }

    public void pushInfo2Watch(String jsonString) {
        EABleSocialContact eaBleSocialContact = new String2Object().string2Message(jsonString);
        EABleManager.getInstance().pushInfo2Watch(eaBleSocialContact, new SetCallback(19, channel));
    }

    public void setMonitorReminder(String jsonString) {
        EABleMonitorReminder eaBleMonitorReminder = new String2Object().string2MonitorReminder(jsonString);
        LogUtils.i(TAG, "添加日常提醒");
        EABleManager.getInstance().addMonitorReminder(eaBleMonitorReminder, new SetCallback(45, channel));
    }

    public void setContacts(String jsonString) {
        final List<EABleContact> contactList = new String2Object().string2Contacts(jsonString);
        if (contactList == null || contactList.size() <= 10) {
            EABleManager.getInstance().addBookList(contactList, 0, new SetCallback(42, channel));
        } else {
            final List<EABleContact> firstList = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                firstList.add(contactList.get(i));
            }
            EABleManager.getInstance().addBookList(firstList, 0, new GeneralCallback() {
                @Override
                public void result(boolean b, int i) {
                    if (b) {
                        contactList.removeAll(firstList);
                        EABleManager.getInstance().addBookList(contactList, 1, new SetCallback(42, channel));
                    } else {
                        new Return2Flutter(channel).setWatchDataResponse(1, 42);
                    }

                }

                @Override
                public void mutualFail(int i) {
                    new Return2Flutter(channel).setWatchDataResponse(1, 42);
                }
            });
        }

    }

    public void setSleepSpo2Check(String jsonString) {
        EABleSleepBloodSwitch eaBleSleepBloodSwitch = new String2Object().string2SleepSpo2(jsonString);
        LogUtils.i(TAG, "eaBleSleepBloodSwitch:" + JSONObject.toJSONString(eaBleSleepBloodSwitch));
        EABleManager.getInstance().startSleepBloodMonitor(eaBleSleepBloodSwitch, new SetCallback(50, channel));
    }

    public void setStressCheck(String jsonString) {
        EABleAutoStressMonitor autoStressMonitor = new String2Object().string2Stress(jsonString);
        LogUtils.i(TAG, "autoStressMonitor:" + JSONObject.toJSONString(autoStressMonitor));
        EABleManager.getInstance().startStressMonitor(autoStressMonitor, new SetCallback(51, channel));
    }

    public void setVibrate(String jsonString) {
        VibrationIntensity vibrationIntensity = new String2Object().string2Vibrate(jsonString);
        LogUtils.i(TAG, "vibrationIntensity:" + JSONObject.toJSONString(vibrationIntensity));
        EABleManager.getInstance().setVibrateMode(vibrationIntensity, new SetCallback(53, channel));
    }

    public void setPeriodReminder(String jsonString) {
        EABlePeriodReminder eaBlePeriodReminder = new String2Object().string2PeriodReminder(jsonString);
        LogUtils.i(TAG, "eaBlePeriodReminder:" + JSONObject.toJSONString(eaBlePeriodReminder));
        EABleManager.getInstance().setPeriodReminder(eaBlePeriodReminder, new SetCallback(55, channel));
    }

    public void syncLocation(String jsonString) {
        Map<String, BigDecimal> map = JSONObject.parseObject(jsonString, Map.class);
        BigDecimal longitude = map.get("longitude");
        BigDecimal latitude = map.get("latitude");
        LogUtils.i(TAG, "longitude:" + longitude.doubleValue() + ",latitude:" + latitude.doubleValue());
        EABleManager.getInstance().syncLocationInfo(longitude.doubleValue(), latitude.doubleValue(), new SetCallback(72, channel));
    }

    public void unbindDevice() {
        EABleDev eaBleDev = new EABleDev();
        eaBleDev.setE_ops(EABleDev.DevOps.restore_factory);
        EABleManager.getInstance().setDeviceOps(eaBleDev, new GeneralCallback() {
            @Override
            public void result(boolean b, int i) {
                if (b) {
                    DataManager.getInstance().clearDb();
                }
            }


            @Override
            public void mutualFail(int i) {

            }
        });
    }

    public void getBigData() {
        EABleManager.getInstance().requestSyncMotionData(MotionReportType.hr_data_req, new SetCallback(29, channel));
    }

    public void bindDevice(String jsonString) {
        eaBleBindInfo = new String2Object().string2BindInfo(jsonString);
        EABleManager.getInstance().setOpsBinding(eaBleBindInfo, new GeneralCallback() {
            @Override
            public void result(boolean b, int i) {
                if (b) {
                    if (eaBleBindInfo.getE_ops().getValue() == 0) {
                        eaBleBindInfo.setE_ops(EABleBindInfo.BindingOps.end);
                        LogUtils.i(TAG, "daily data sync time:" + eaBleBindInfo.getBind_mod());
                        EABleManager.getInstance().setOpsBinding(eaBleBindInfo, new GeneralCallback() {
                            @Override
                            public void result(boolean b, int i) {
                                new Return2Flutter(channel).setWatchDataResponse((b ? 0 : 1), 6);
                            }


                            @Override
                            public void mutualFail(int i) {
                                if (mHandler == null) {
                                    mHandler = new Handler(Looper.getMainLooper());
                                }
                                mHandler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        channel.invokeMethod(kArgumentsError, "user_id error");
                                        mHandler = null;
                                    }
                                });
                            }
                        });
                    } else {

                        new Return2Flutter(channel).setWatchDataResponse((b ? 0 : 1), 6);
                    }
                } else {
                    new Return2Flutter(channel).setWatchDataResponse((b ? 0 : 1), 6);
                    if (EABleManager.getInstance().getEaBleConnectListener() != null) {
                        EABleManager.getInstance().getEaBleConnectListener().deviceDisconnect();
                    }
                    EABleManager.getInstance().disconnectPeripheral();
                }
            }


            @Override
            public void mutualFail(int i) {
                if (mHandler == null) {
                    mHandler = new Handler(Looper.getMainLooper());
                }
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        channel.invokeMethod(kArgumentsError, "user_id error");
                        mHandler = null;
                    }
                });
            }
        });
    }

    public void operateDevice(final int action) {
        EABleDev eaBleDev = new EABleDev();
        if (action == 0) {
            eaBleDev.setE_ops(EABleDev.DevOps.restore_factory);
        } else if (action == 1) {
            eaBleDev.setE_ops(EABleDev.DevOps.reset);
        } else if (action == 2) {
            eaBleDev.setE_ops(EABleDev.DevOps.power_off);
        } else if (action == 3) {
            eaBleDev.setE_ops(EABleDev.DevOps.disconnect_ble);
        } else if (action == 4) {
            eaBleDev.setE_ops(EABleDev.DevOps.entering_flight_mode);
        } else if (action == 5) {
            eaBleDev.setE_ops(EABleDev.DevOps.light_up_the_screen);
        } else if (action == 6) {
            eaBleDev.setE_ops(EABleDev.DevOps.turn_off_the_screen);
        } else if (action == 7) {
            eaBleDev.setE_ops(EABleDev.DevOps.stop_search_phone);
        } else if (action == 8) {
            eaBleDev.setE_ops(EABleDev.DevOps.start_search_watch);
        } else if (action == 9) {
            eaBleDev.setE_ops(EABleDev.DevOps.stop_search_watch);
        }
        EABleManager.getInstance().setDeviceOps(eaBleDev, new GeneralCallback() {
            @Override
            public void result(final boolean b, int code) {
                if (mHandler == null) {
                    mHandler = new Handler(Looper.getMainLooper());
                }
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("operationType", action);
                        jsonObject.put("respondCodeType", b);
                        channel.invokeMethod(kOperationWacthResponse, jsonObject.toJSONString());
                        mHandler = null;
                    }
                });
            }

            @Override
            public void mutualFail(int i) {

            }
        });

    }

}
