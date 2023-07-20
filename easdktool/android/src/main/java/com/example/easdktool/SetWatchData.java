package com.example.easdktool;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.apex.bluetooth.callback.GeneralCallback;
import com.apex.bluetooth.core.EABleManager;

import com.apex.bluetooth.enumeration.MotionReportType;
import com.apex.bluetooth.model.EABleAncsSw;
import com.apex.bluetooth.model.EABleBindInfo;
import com.apex.bluetooth.model.EABleDailyGoal;
import com.apex.bluetooth.model.EABleDev;
import com.apex.bluetooth.model.EABleDevUnit;
import com.apex.bluetooth.model.EABleDeviceLanguage;
import com.apex.bluetooth.model.EABleGesturesBrightScreen;
import com.apex.bluetooth.model.EABleHabit;
import com.apex.bluetooth.model.EABleHr;
import com.apex.bluetooth.model.EABleInfoPush;
import com.apex.bluetooth.model.EABleMenuPage;
import com.apex.bluetooth.model.EABleMonitorReminder;
import com.apex.bluetooth.model.EABleMusicRespond;
import com.apex.bluetooth.model.EABleNotDisturb;
import com.apex.bluetooth.model.EABlePersonInfo;
import com.apex.bluetooth.model.EABlePhysiologyData;
import com.apex.bluetooth.model.EABleReminder;
import com.apex.bluetooth.model.EABleSedentariness;
import com.apex.bluetooth.model.EABleSocialContact;
import com.apex.bluetooth.model.EABleSyncTime;
import com.apex.bluetooth.model.EABleWatchFace;
import com.apex.bluetooth.model.EABleWeather;
import com.apex.bluetooth.utils.LogUtils;
import com.example.easdktool.callback.DataResponseCall;
import com.example.easdktool.callback.EditAttentionCall;
import com.example.easdktool.callback.HabitResultCall;
import com.example.easdktool.callback.SetCallback;
import com.example.easdktool.db.DataManager;


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
        EABleManager.getInstance().setMenstrualCycle(eaBlePhysiologyData, new SetCallback(32, channel));

    }

    public void syncMusicInfo(String jsonString) {
        EABleMusicRespond eaBleMusicRespond = new String2Object().string2Music(jsonString);
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

    public void unbindDevice() {
        EABleDev eaBleDev = new EABleDev();
        eaBleDev.e_ops = EABleDev.DevOps.restore_factory;
        EABleManager.getInstance().setDeviceOps(eaBleDev, new GeneralCallback() {
            @Override
            public void result(boolean b) {
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
            public void result(boolean b) {
                if (b) {
                    if (eaBleBindInfo.getE_ops().getValue() == 0) {
                        eaBleBindInfo.setE_ops(EABleBindInfo.BindingOps.end);
                        LogUtils.i(TAG, "daily data sync time:" + eaBleBindInfo.bind_mod);
                        EABleManager.getInstance().setOpsBinding(eaBleBindInfo, new GeneralCallback() {
                            @Override
                            public void result(boolean b) {
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
            eaBleDev.e_ops = EABleDev.DevOps.restore_factory;
        } else if (action == 1) {
            eaBleDev.e_ops = EABleDev.DevOps.reset;
        } else if (action == 2) {
            eaBleDev.e_ops = EABleDev.DevOps.power_off;
        } else if (action == 3) {
            eaBleDev.e_ops = EABleDev.DevOps.disconnect_ble;
        } else if (action == 4) {
            eaBleDev.e_ops = EABleDev.DevOps.entering_flight_mode;
        } else if (action == 5) {
            eaBleDev.e_ops = EABleDev.DevOps.light_up_the_screen;
        } else if (action == 6) {
            eaBleDev.e_ops = EABleDev.DevOps.turn_off_the_screen;
        } else if (action == 7) {
            eaBleDev.e_ops = EABleDev.DevOps.stop_search_phone;
        } else if (action == 8) {
            eaBleDev.e_ops = EABleDev.DevOps.start_search_watch;
        } else if (action == 9) {
            eaBleDev.e_ops = EABleDev.DevOps.stop_search_watch;
        }
        EABleManager.getInstance().setDeviceOps(eaBleDev, new GeneralCallback() {
            @Override
            public void result(final boolean b) {
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
