package com.example.easdktool;

import android.content.Context;


import android.os.Handler;
import android.os.Looper;

import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;

import com.alibaba.fastjson.JSONObject;

import com.apex.bluetooth.callback.GeneralCallback;

import com.apex.bluetooth.callback.OtaCallback;

import com.apex.bluetooth.core.EABleManager;

import com.apex.bluetooth.enumeration.EABleConnectState;

import com.apex.bluetooth.enumeration.MotionReportType;

import com.apex.bluetooth.listener.EABleScanListener;

import com.apex.bluetooth.model.EABleBindInfo;

import com.apex.bluetooth.model.EABleDev;

import com.apex.bluetooth.model.EABleDevice;

import com.apex.bluetooth.model.EABleOta;


import com.apex.bluetooth.utils.LogUtils;

import com.example.easdktool.been.LogParam;

import com.example.easdktool.broadcast.CallReceiveBroadcast;
import com.example.easdktool.broadcast.SMSReceiveBroadcast;

import com.example.easdktool.callback.ConnectStateListener;
import com.example.easdktool.callback.DeviceOperationListener;
import com.example.easdktool.callback.MotionDataListener;
import com.example.easdktool.enumerate.ConnectState;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import java.util.ArrayList;

import java.util.List;
import java.util.Map;

import io.flutter.embedding.engine.FlutterEngine;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;

/**
 * EasdktoolPlugin
 */
public class EasdktoolPlugin implements FlutterPlugin, MethodCallHandler {
    private final String TAG = this.getClass().getSimpleName();
    private final String ENGINE_CACHE_KEY = "engine_cache";
    /// The MethodChannel that will the communication between Flutter and native Android
    ///
    /// This local reference serves to register the plugin with the Flutter Engine and unregister it
    /// when the Flutter Engine is detached from the Activity


    private MethodChannel channel;
    private Context mContext;
    // private Handler mHandler;
    private FlutterEngine flutterEngine;

    /// MARK: -call method Name
    final String kEAConnectWatch = "EAConnectWatch";       // 连接
    final String kEADisConnectWatch = "EADisConnectWatch";    // 断开
    final String kEAUnbindWatch = "EAUnbindWatch";        // 解绑
    final String kEAbindingWatch = "EAbindingWatch";       // 绑定
    final String kEAGetWatchInfo = "EAGetWatchInfo";       // 获取手表数据
    final String kEASetWatchInfo = "EASetWatchInfo";       // 设置手表信息
    final String kEAGetBigWatchData = "EAGetBigWatchData";    // 获取手表大数据
    final String kEAOperationWatch = "EAOperationWatch";     // 操作手表
    final String kEAOTA = "EAOTA";                // ota
    final String kEALog = "EAShowLog";                // log
    final String kEAScanWacth = "EAScanWacth"; // 搜索手表
    final String kEAStopScanWacth = "EAStopScanWacth"; //停止搜索手表
    final String kEAGetWacthStateInfo = "EAGetWacthStateInfo"; //获取手表连接状态信息

    /// MARK: - invoke method Name
    final String kConnectState = "ConnectState";
    final String kArgumentsError = "ArgumentsError";
    final String kBluetoothState = "BluetoothState";
    final String kBingdingWatchResponse = "BingdingWatchResponse";
    final String kSetWatchResponse = "SetWatchResponse";
    final String kGetWatchResponse = "GetWatchResponse";

    final String kOperationPhone = "OperationPhone"; //操作手机
    final String kProgress = "Progress";
    final String kScanWacthResponse = "ScanWacthResponse";
    final String kOperationWacthResponse = "OperationWacthResponse";

    /// 数据类型
    /* 手表 */
    final int kEADataInfoTypeWatch = 3;
    /* 用户 */
    final int kEADataInfoTypeUser = 4;
    /* 同步时间 */
    final int kEADataInfoTypeSyncTime = 5;
    /* 绑定手表 */
    final int kEADataInfoTypeBingWatch = 6;
    /* 屏幕亮度 */
    final int kEADataInfoTypeBlacklight = 7;
    /* 屏幕自动灭屏时间 */
    final int kEADataInfoTypeBlacklightTimeout = 8;
    /* 设备电量信息 */
    final int kEADataInfoTypeBattery = 9;
    /* 设备语言信息 */
    final int kEADataInfoTypeLanguage = 10;
    /* 统一设备单位 */
    final int kEADataInfoTypeUnifiedUnit = 11;
    /* 设备操作 */
    final int kEADataInfoTypeDeviceOps = 12;
    /* 免打扰时间段 */
    final int kEADataInfoTypeNotDisturb = 13;
    /* 日常目标值设置 */
    final int kEADataInfoTypeDailyGoal = 15;
    /* 自动睡眠监测 */
    final int kEADataInfoTypeAutoCheckSleep = 16;
    /* 自动心率监测 */
    final int kEADataInfoTypeAutoCheckHeartRate = 17;
    /* 久坐监测 */
    final int kEADataInfoTypeAutoCheckSedentariness = 18;
    final int kEADataInfoTypePushInfo2Watch = 19;

    /* 通用天气 */
    final int kEADataInfoTypeWeather = 20;
    /* 社交提醒开关 */
    final int kEADataInfoTypeSocialSwitch = 21;
    /* 提醒 */
    final int kEADataInfoTypeReminder = 22;
    /* 距离单位 */
    final int kEADataInfoTypeDistanceUnit = 24;
    /* 重量单位 */
    final int kEADataInfoTypeWeightUnit = 25;
    /* 心率报警门限 */
    final int kEADataInfoTypeHeartRateWaringSetting = 26;
    /* 基础卡路里开关 */
    final int kEADataInfoTypeCaloriesSetting = 27;
    /* 抬手亮屏开关 */
    final int kEADataInfoTypeGesturesSetting = 28;
    /* 大数据获取命令 */
    final int kEADataInfoTypeGetBigData = 29;
    /* 设置组合命令 */
    final int kEADataInfoTypeWatchSettingInfo = 30;
    /* 一级菜单设置命令 */
    final int kEADataInfoTypeHomePage = 31;
    /* 经期命令 */
    final int kEADataInfoTypeMenstrual = 32;

    /* 表盘命令 */
    final int kEADataInfoTypeWatchFace = 33;
    /* 消息推送开关 */
    final int kEADataInfoTypeAppMessage = 34;
    /* 血压校准值 （老人表）*/
    final int kEADataInfoTypeBloodPressure = 36;
    /* 自动监测 心率 血氧 血压 （老人表） */
    final int kEADataInfoTypeAutoMonitor = 37;
    /* 习惯追踪 */
    final int kEADataInfoTypeHabitTracker = 38;
    /*习惯追踪回应 */
    final int kEADataInfoTypeHabitTrackerRespond = 39;

    /*提醒 */
    final int kEADataInfoTypeMonitorReminder = 45;

    /* 操作手机命令 */
    final int kEADataInfoTypePhoneOps = 2001;

    final int kEADataInfoTypeMusic = 2004;

    /* MTU */
    final int kEADataInfoTypeMTU = 2006;


    /* OTA命令 */
    final int kEADataInfoTypeOTARequest = 9001;
    /* OTA命令回应 */
    final int kEADataInfoTypeOTARespond = 9000;

    private CallReceiveBroadcast callReceiveBroadcast;
    SMSReceiveBroadcast smsReceiveBroadcast;
    private final String CID_KEY = "CID_KEY_CACHE";
    private final String DISPATCHER_HANDLE_KEY = "dispatch_handler";

    @Override
    public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
        channel.setMethodCallHandler(null);
        LogUtils.e(TAG, "End of page");
        //  destroyIncomingCall();
        //  destroySmsListener();
    }


    @Override
    public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
        Log.e(TAG, "The page starts");
        flutterEngine = flutterPluginBinding.getFlutterEngine();
        channel = new MethodChannel(flutterPluginBinding.getBinaryMessenger(), "easdktool");
        channel.setMethodCallHandler(this);
        if (mContext == null) {
            mContext = flutterPluginBinding.getApplicationContext();
        }


    }


    @Override
    public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {

        if (call.method.equals(kEALog)) {

            String arguments = (String) call.arguments;
            if (checkArgumentName("showLog", arguments)) {

                LogParam logParam = JSONObject.parseObject(arguments, LogParam.class);

                LogUtils.setShowLog(logParam.showLog);
            }
        } else if (call.method.equals(kEAGetWacthStateInfo)) {

            EABleConnectState connectState = EABleManager.getInstance().getDeviceConnectState();
            int i = 0;
            if (connectState == EABleConnectState.STATE_CONNECTED) {
                i = 1;
            } else if (connectState == EABleConnectState.STATE_CONNECTING) {
                i = 2;
            }
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("connectState", i);
            result.success(jsonObject.toJSONString());
        } else if (call.method.equals(kEAScanWacth)) {

            EABleScanListener bleScanListener = new EABleScanListener() {
                @Override
                public void scanDevice(EABleDevice eaBleDevice) {

                    //  if (mHandler != null) {
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            JSONObject jsonObject = new JSONObject();
                            jsonObject.put("name", eaBleDevice.deviceName);
                            jsonObject.put("connectAddress", eaBleDevice.deviceAddress);
                            jsonObject.put("rssi", eaBleDevice.rssi);
                            jsonObject.put("snNumber", eaBleDevice.deviceSign);
                            channel.invokeMethod(kScanWacthResponse, jsonObject.toJSONString());
                        }
                    });
                    //  }

                }

                @Override
                public void scanError(int i) {

                    // if (mHandler != null) {
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {

                            channel.invokeMethod(kArgumentsError, "scan error");
                        }
                    });
                    // }

                }
            };
            EABleManager.getInstance().didDiscoverPeripheral(bleScanListener, mContext, false);

        } else if (call.method.equals(kEAStopScanWacth)) {


            EABleManager.getInstance().stopScanPeripherals(mContext);
        } else if (call.method.equals(kEAConnectWatch)) {

            String arguments = (String) call.arguments;
            Map<String, String> map = JSONObject.parseObject(arguments, Map.class);
            String address = map.get("connectAddress");
            if (TextUtils.isEmpty(address)) {// 判断空地址
                channel.invokeMethod(kArgumentsError, "connectAddress error");
                return;
            }

            EABleManager.getInstance().connectToPeripheral(address, mContext, new ConnectStateListener(channel), 128, new DeviceOperationListener(channel), new MotionDataListener(channel));
        } else if (call.method.equals(kEADisConnectWatch)) { // 手动断开设备

            EABleConnectState connectState = EABleManager.getInstance().getDeviceConnectState();
            if (connectState == EABleConnectState.STATE_CONNECTED) {
                EABleManager.getInstance().disconnectPeripheral();
                channel.invokeMethod(kConnectState, ConnectState.disConnect.getValue());
            }
        } else if (call.method.equals(kEAUnbindWatch)) { // 解绑设备
            new SetWatchData(channel).unbindDevice();

        } else if (call.method.equals(kEAbindingWatch)) { //绑定设备

            String arguments = (String) call.arguments;
            if (checkArgumentName("user_id", arguments)) {
                new SetWatchData(channel).bindDevice(arguments);
            }

        } else if (call.method.equals(kEAGetWatchInfo)) { // 获取手表数据
            String arguments = (String) call.arguments;
            if (checkArgumentName("dataType", arguments)) {
                System.out.print("获取指令");
                Map<String, Integer> map = JSONObject.parseObject(arguments, Map.class);
                int dataType = map.get("dataType");
                if (dataType != 45) {
                    getWatchData(dataType);
                } else {
                    int type = map.get("type");
                    new GetWatchData(channel).getNewWatchData(dataType, type);

                }
            }
        } else if (call.method.equals(kEASetWatchInfo)) { // 设置手表

            String arguments = (String) call.arguments;
            if (checkArgumentName("dataType", arguments) && checkArgumentName("jsonString", arguments)) {
                System.out.print("设置指令");

                Map<String, Object> map = JSONObject.parseObject(arguments, Map.class);
                setWatchData(map);
            }
        } else if (call.method.equals(kEAGetBigWatchData)) { /// 获取大数据
            LogUtils.i(TAG, "get big data command");
            System.out.print("获取大数据");
            new SetWatchData(channel).getBigData();

        } else if (call.method.equals(kEAOperationWatch)) {

            String arguments = (String) call.arguments;
            if (checkArgumentName("dataType", arguments)) {
                Map<String, Object> map = JSONObject.parseObject(arguments, Map.class);
                //  SetWatchParam setWatchParam = JSONObject.parseObject(arguments, SetWatchParam.class);
                final int action = (int) map.get("dataType");
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
                    public void result(boolean b) {

                        //  if (mHandler != null) {
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                JSONObject jsonObject = new JSONObject();
                                jsonObject.put("operationType", action);
                                jsonObject.put("respondCodeType", b);
                                channel.invokeMethod(kOperationWacthResponse, jsonObject.toJSONString());
                            }
                        });
                        //  }
                    }

                    @Override
                    public void mutualFail(int i) {

                    }
                });
            }
        } else if (call.method.equals(kEAOTA)) {
            String arguments = (String) call.arguments;
            if (checkArgumentName("type", arguments) && checkArgumentName("otas", arguments)) {
                Map<String, Object> map = JSONObject.parseObject(arguments, Map.class);
                new OTAFunction(channel).startOta(map);
            }

        } else {
            result.notImplemented();
        }

    }

    private void getWatchData(int type) {
        switch (type) {
            case 3:
                new GetWatchData(channel).getWatchInfo();
                break;
            case 4:
                new GetWatchData(channel).getUserInfo();
                break;
            case 5:
                new GetWatchData(channel).getSyncTime();
                break;
            case 8:
                new GetWatchData(channel).getOffTime();
                break;
            case 7:
                new GetWatchData(channel).getScreenLight();
                break;
            case 9:
                new GetWatchData(channel).getBatteryInfo();
                break;
            case 10:
                new GetWatchData(channel).getLanguageInfo();
                break;
            case 11:
                new GetWatchData(channel).getUnitInfo();
                break;
            case 13:
                new GetWatchData(channel).getDisturbInfo();
                break;
            case 15:
                new GetWatchData(channel).getGoalInfo();
                break;
            case 16:
                new GetWatchData(channel).getSleepCheckInfo();
                break;
            case 17:
                new GetWatchData(channel).getHeartCheck();
                break;
            case 18:
                new GetWatchData(channel).getSedentaryInfo();
                break;
            case 21:
                new GetWatchData(channel).getAncsInfo();
                break;
            case 22:
                new GetWatchData(channel).getReminderInfo();
                break;
            case 24:
                new GetWatchData(channel).getDistanceUnit();
                break;
            case 25:
                new GetWatchData(channel).getWeightUnit();
                break;
            case 26:
                new GetWatchData(channel).getHeartAlert();
                break;
            case 27:
                new GetWatchData(channel).getBaseCalories();
                break;
            case 28:
                new GetWatchData(channel).getGesturesInfo();
                break;
            case 30:
                new GetWatchData(channel).getCombinationInfo();
                break;
            case 31:
                new GetWatchData(channel).getPageInfo();
                break;
            case 33:
                new GetWatchData(channel).getDialInfo();
                break;
            case 34:
                new GetWatchData(channel).getPushInfo();
                break;
            case 36:
                break;

            case 38:
                new GetWatchData(channel).getHabitInfo();
                break;
            case 40:
                new GetWatchData(channel).getTodayData();
                break;

            default:
                channel.invokeMethod(kArgumentsError, "type error");
                break;
        }
    }

    private void setWatchData(Map<String, Object> setWatchParam) {
        int type = (int) setWatchParam.get("dataType");
        String jsonString = (String) setWatchParam.get("jsonString");
        switch (type) {
            case (kEADataInfoTypeUser):
                LogUtils.i(TAG, "Set the user information");
                new SetWatchData(channel).setUserInfo(jsonString);
                break;
            case (kEADataInfoTypeSyncTime):
                LogUtils.i(TAG, "Synchronize time");
                new SetWatchData(channel).syncTime(jsonString);
                break;
            case (kEADataInfoTypeLanguage):
                LogUtils.i(TAG, "Set the language");
                new SetWatchData(channel).syncLanguage(jsonString);
                break;
            case (kEADataInfoTypeUnifiedUnit):
                LogUtils.i(TAG, "Set up units");
                new SetWatchData(channel).setUnit(jsonString);
                break;
            case (kEADataInfoTypeNotDisturb):
                LogUtils.i(TAG, "Set up Do Not Disturb");
                new SetWatchData(channel).setDisturb(jsonString);
                break;
            case (kEADataInfoTypeDailyGoal):
                LogUtils.i(TAG, "Set goals");
                new SetWatchData(channel).setGoal(jsonString);
                break;
            case (kEADataInfoTypeAutoCheckHeartRate):
                LogUtils.i(TAG, "Automatic heart rate monitoring");
                new SetWatchData(channel).setHeartAutoCheck(jsonString);
                break;
            case (kEADataInfoTypeAutoCheckSedentariness):
                LogUtils.i(TAG, "Set sedentary");
                new SetWatchData(channel).setSedentarinessInfo(jsonString);
                break;
            case (kEADataInfoTypeWeather):
                LogUtils.i(TAG, "Synchronize the weather");
                new SetWatchData(channel).setWeatherInfo(jsonString);
                break;
            case (kEADataInfoTypeSocialSwitch):
                LogUtils.i(TAG, "Social switch");
                new SetWatchData(channel).setSocialSwitch(jsonString);
                break;
            case (kEADataInfoTypeReminder):
                LogUtils.i(TAG, "Add a reminder");
                new SetWatchData(channel).setReminder(jsonString);
                break;
            case (kEADataInfoTypeHeartRateWaringSetting):
                LogUtils.i(TAG, "Add a heart rate alert");
                new SetWatchData(channel).setHeartAlertInfo(jsonString);
                break;
            case (kEADataInfoTypeHabitTracker):
                LogUtils.i(TAG, "Add habits");
                new SetWatchData(channel).setHabitInfo(jsonString);
                break;
            case (kEADataInfoTypeGesturesSetting):
                LogUtils.i(TAG, "Raise your hand to light up the screen");
                new SetWatchData(channel).setGesturesInfo(jsonString);
                break;
            case (kEADataInfoTypeHomePage):
                LogUtils.i(TAG, "Level 1 menu");
                new SetWatchData(channel).setHomePage(jsonString);
                break;
            case (kEADataInfoTypeMenstrual):
                LogUtils.i(TAG, "Physiological period");
                new SetWatchData(channel).setMenstrual(jsonString);
                break;
            case (kEADataInfoTypeMusic):
                LogUtils.i(TAG, "Physiological period");
                new SetWatchData(channel).syncMusicInfo(jsonString);
                break;
            case (kEADataInfoTypeWatchFace):
                LogUtils.e(TAG, "Set up the watch face");
                new SetWatchData(channel).setSystemDial(jsonString);
                break;
            case (kEADataInfoTypeAppMessage):
                LogUtils.i(TAG, "App push switch");
                new SetWatchData(channel).setAppPushSwitch(jsonString);
                break;

            case (kEADataInfoTypePushInfo2Watch):
                Log.e(TAG, "Push information");
                new SetWatchData(channel).pushInfo2Watch(jsonString);
                break;
            case (kEADataInfoTypeMonitorReminder):
                LogUtils.i(TAG, "Daily reminders");
                new SetWatchData(channel).setMonitorReminder(jsonString);
                break;
            default:
                channel.invokeMethod(kArgumentsError, "argument error");
                break;
        }
    }


    private boolean checkArgumentName(String argumentName, String arguments) {

        if (!arguments.contains(argumentName)) {
            channel.invokeMethod(kArgumentsError, argumentName + " error");
            return false;
        }
        return true;
    }


}


