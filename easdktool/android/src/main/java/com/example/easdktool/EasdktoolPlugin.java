package com.example.easdktool;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;


import com.apex.bluetooth.callback.AttentionCallback;
import com.apex.bluetooth.callback.BatterInfoCallback;
import com.apex.bluetooth.callback.CalorieSwitchCallback;
import com.apex.bluetooth.callback.CombinationCallback;
import com.apex.bluetooth.callback.DataReportCallback;
import com.apex.bluetooth.callback.DistanceUnitCallback;
import com.apex.bluetooth.callback.DonDisturbCallback;
import com.apex.bluetooth.callback.EditAttentionCallback;
import com.apex.bluetooth.callback.GeneralCallback;
import com.apex.bluetooth.callback.GoalCallback;
import com.apex.bluetooth.callback.HabitCallback;
import com.apex.bluetooth.callback.HabitResultCallback;
import com.apex.bluetooth.callback.HeartCheckCallback;
import com.apex.bluetooth.callback.HeartLimitCallback;
import com.apex.bluetooth.callback.InfoPushCallback;
import com.apex.bluetooth.callback.LanguageCallback;
import com.apex.bluetooth.callback.MenuCallback;
import com.apex.bluetooth.callback.MotionDataReportCallback;
import com.apex.bluetooth.callback.MotionDataResponseCallback;
import com.apex.bluetooth.callback.OtaCallback;
import com.apex.bluetooth.callback.PersonInfoCallback;
import com.apex.bluetooth.callback.RaiseHandBrightScreenCallback;
import com.apex.bluetooth.callback.RemindCallback;
import com.apex.bluetooth.callback.RestScreenCallback;
import com.apex.bluetooth.callback.ScreenBrightnessCallback;
import com.apex.bluetooth.callback.SedentaryCheckCallback;
import com.apex.bluetooth.callback.SleepCheckCallback;
import com.apex.bluetooth.callback.TimeCallback;
import com.apex.bluetooth.callback.TodayTotalDataCallback;
import com.apex.bluetooth.callback.UnitCallback;
import com.apex.bluetooth.callback.WatchFaceCallback;
import com.apex.bluetooth.callback.WatchInfoCallback;
import com.apex.bluetooth.callback.WeightUnitCallback;
import com.apex.bluetooth.core.EABleManager;
import com.apex.bluetooth.enumeration.CommonAction;
import com.apex.bluetooth.enumeration.CommonFlag;
import com.apex.bluetooth.enumeration.EABleConnectState;
import com.apex.bluetooth.enumeration.HabitIcon;
import com.apex.bluetooth.enumeration.HabitState;
import com.apex.bluetooth.enumeration.MotionReportType;
import com.apex.bluetooth.enumeration.PersonHand;
import com.apex.bluetooth.enumeration.QueryWatchInfoType;
import com.apex.bluetooth.enumeration.TimeZone;
import com.apex.bluetooth.enumeration.UnitFormat;
import com.apex.bluetooth.listener.EABleConnectListener;
import com.apex.bluetooth.listener.EABleScanListener;
import com.apex.bluetooth.model.EABleAncsSw;
import com.apex.bluetooth.model.EABleAutoCheckSleep;
import com.apex.bluetooth.model.EABleBatInfo;
import com.apex.bluetooth.model.EABleBindInfo;
import com.apex.bluetooth.model.EABleBloodOxygen;
import com.apex.bluetooth.model.EABleCombination;
import com.apex.bluetooth.model.EABleDailyData;
import com.apex.bluetooth.model.EABleDailyGoal;
import com.apex.bluetooth.model.EABleDev;
import com.apex.bluetooth.model.EABleDevUnit;
import com.apex.bluetooth.model.EABleDevice;
import com.apex.bluetooth.model.EABleDeviceLanguage;
import com.apex.bluetooth.model.EABleDistanceFormat;
import com.apex.bluetooth.model.EABleGeneralSportRespond;
import com.apex.bluetooth.model.EABleGesturesBrightScreen;
import com.apex.bluetooth.model.EABleGpsData;
import com.apex.bluetooth.model.EABleHabit;
import com.apex.bluetooth.model.EABleHabitRecord;
import com.apex.bluetooth.model.EABleHabitRespond;
import com.apex.bluetooth.model.EABleHeartData;
import com.apex.bluetooth.model.EABleHr;
import com.apex.bluetooth.model.EABleInfoPush;
import com.apex.bluetooth.model.EABleMenuPage;
import com.apex.bluetooth.model.EABleMtu;
import com.apex.bluetooth.model.EABleMultiData;
import com.apex.bluetooth.model.EABleMusicControl;
import com.apex.bluetooth.model.EABleNotDisturb;
import com.apex.bluetooth.model.EABleOta;
import com.apex.bluetooth.model.EABlePaceData;
import com.apex.bluetooth.model.EABlePeriod;
import com.apex.bluetooth.model.EABlePersonInfo;
import com.apex.bluetooth.model.EABlePressureData;
import com.apex.bluetooth.model.EABleQueryMusic;
import com.apex.bluetooth.model.EABleRemindRespond;
import com.apex.bluetooth.model.EABleReminder;
import com.apex.bluetooth.model.EABleRestingRateData;
import com.apex.bluetooth.model.EABleSedentariness;
import com.apex.bluetooth.model.EABleSleepData;
import com.apex.bluetooth.model.EABleSocialResponse;
import com.apex.bluetooth.model.EABleStepFrequencyData;
import com.apex.bluetooth.model.EABleSyncTime;
import com.apex.bluetooth.model.EABleWatchFace;
import com.apex.bluetooth.model.EABleWatchInfo;
import com.apex.bluetooth.model.EABleWeather;
import com.apex.bluetooth.model.EABleWeightFormat;
import com.apex.bluetooth.model.TodayTotalData;
import com.apex.bluetooth.utils.LogUtils;
import com.example.easdktool.been.BindInfo;
import com.example.easdktool.been.ConnectParam;
import com.example.easdktool.been.DailyGoal;
import com.example.easdktool.been.GeneralSportRespond;
import com.example.easdktool.been.InfoPush;
import com.example.easdktool.been.InfoPushItem;
import com.example.easdktool.been.LogParam;
import com.example.easdktool.been.MenuPage;
import com.example.easdktool.been.OtaData;
import com.example.easdktool.been.PageItem;
import com.example.easdktool.been.Period;
import com.example.easdktool.been.PersonInfo;
import com.example.easdktool.been.ReminderItem;
import com.example.easdktool.been.Sedentariness;
import com.example.easdktool.been.SetWatchParam;
import com.example.easdktool.been.SyncTime;
import com.example.easdktool.been.TempMotion;
import com.example.easdktool.been.TempOtaData;
import com.example.easdktool.been.TempRemind;
import com.example.easdktool.been.GetWatchParam;
import com.example.easdktool.been.TempWeather;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
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
    /// The MethodChannel that will the communication between Flutter and native Android
    ///
    /// This local reference serves to register the plugin with the Flutter Engine and unregister it
    /// when the Flutter Engine is detached from the Activity

    //绑定状态 0:连接失败 1:连接成功 2:断开连接 3:连接超时 4:无此设备
    enum ConnectState {
        fail(0),
        succ(1),
        disConnect(2),
        timeout(3),
        notFind(4);

        private int value;

        ConnectState(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    //蓝牙状态 0:未开启蓝牙 1:蓝牙开启 2:蓝牙未授权 3:定位未开启 4:不支持BLE
    enum BluetoothState {
        unOpen(0),
        open(1),
        unAuthorized(2),
        unOpenLocation(3),
        unSupportBle(4);

        private int value;

        BluetoothState(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }


    private MethodChannel channel;
    private Context mContext;
    private Handler mHandler;
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
    final String kSetWatchResponse = "SetWatchResponse";
    final String kGetWatchResponse = "GetWatchResponse";
    final String kGetBigWatchData = "GetBigWatchData";
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


    /* 操作手机命令 */
    final int kEADataInfoTypePhoneOps = 2001;
    /* MTU */
    final int kEADataInfoTypeMTU = 2006;
    /* 大数据步数 */
    final int kEADataInfoTypeStepData = 3001;
    /* 大数据睡眠 */
    final int kEADataInfoTypeSleepData = 3002;
    /* 大数据心率  */
    final int kEADataInfoTypeHeartRateData = 3003;
    /* 大数据GPS */
    final int kEADataInfoTypeGPSData = 3004;
    /* 大数据多运动 */
    final int kEADataInfoTypeSportsData = 3005;
    /* 大数据血氧 */
    final int kEADataInfoTypeBloodOxygenData = 3006;
    /* 大数据压力 */
    final int kEADataInfoTypeStressData = 3007;
    /* 大数据步频 */
    final int kEADataInfoTypeStepFreqData = 3008;
    /* 大数据配速 */
    final int kEADataInfoTypeStepPaceData = 3009;
    /* 大数据静息心率 */
    final int kEADataInfoTypeRestingHeartRateData = 3010;
    /* 习惯数据 */
    final int EADataInfoTypeHabitTrackerData = 3011;
    /* OTA命令 */
    final int kEADataInfoTypeOTARequest = 9001;
    /* OTA命令回应 */
    final int kEADataInfoTypeOTARespond = 9000;

    @Override
    public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
        channel.setMethodCallHandler(null);
    }

    class DeviceOperationListener implements DataReportCallback {
        @Override
        public void searchPhone() {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("opePhoneType", 0);
            sendOpePhone(jsonObject);
        }

        @Override
        public void stopSearchPhone() {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("opePhoneType", 1);
            sendOpePhone(jsonObject);
        }

        @Override
        public void connectCamera() {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("opePhoneType", 2);
            sendOpePhone(jsonObject);
        }

        @Override
        public void takePhoto() {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("opePhoneType", 3);
            sendOpePhone(jsonObject);
        }

        @Override
        public void endTakePhoto() {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("opePhoneType", 4);
            sendOpePhone(jsonObject);
        }

        @Override
        public void updateWeather() {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("opePhoneType", 5);
            sendOpePhone(jsonObject);
        }

        @Override
        public void circadian() {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("opePhoneType", 7);
            sendOpePhone(jsonObject);
        }

        @Override
        public void updateAgps() {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("opePhoneType", 6);
            sendOpePhone(jsonObject);
        }

        @Override
        public void transmissionComplete() {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("opePhoneType", 8);
            sendOpePhone(jsonObject);
        }

        @Override
        public void stopSearchWatch() {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("opePhoneType", 8);
            sendOpePhone(jsonObject);
        }

        @Override
        public void queryMusic(final EABleQueryMusic eaBleQueryMusic) {
            if (mHandler != null) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("dataType", 0x0B);
                        if (eaBleQueryMusic.getE_app() == EABleQueryMusic.PlayerType.default_type) {
                            jsonObject.put("appType", 0);
                        } else if (eaBleQueryMusic.getE_app() == EABleQueryMusic.PlayerType.apple_music) {
                            jsonObject.put("appType", 1);
                        } else if (eaBleQueryMusic.getE_app() == EABleQueryMusic.PlayerType.deeze) {
                            jsonObject.put("appType", 2);
                        } else if (eaBleQueryMusic.getE_app() == EABleQueryMusic.PlayerType.deeze) {
                            jsonObject.put("appType", 3);
                        }
                        MethodChannel methodChannel = new MethodChannel(flutterEngine.getDartExecutor(), "sdk");
                        if (methodChannel != null) {
                            methodChannel.invokeMethod("REPORT", jsonObject.toJSONString());
                        }
                    }
                });
            }
        }

        @Override
        public void musicControl(final EABleMusicControl eaBleMusicControl) {
            if (mHandler != null) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("dataType", 0x0C);
                        jsonObject.put("volume", eaBleMusicControl.volume);
                        jsonObject.put("elapsedtime", eaBleMusicControl.elapsedtime);
                        if (eaBleMusicControl.e_ops == EABleMusicControl.MusicControl.play_start) {
                            jsonObject.put("action", 0);
                        }
                        if (eaBleMusicControl.e_ops == EABleMusicControl.MusicControl.play_stop) {
                            jsonObject.put("action", 1);
                        }
                        if (eaBleMusicControl.e_ops == EABleMusicControl.MusicControl.previous_song) {
                            jsonObject.put("action", 2);
                        }
                        if (eaBleMusicControl.e_ops == EABleMusicControl.MusicControl.next_song) {
                            jsonObject.put("action", 3);
                        }
                        if (eaBleMusicControl.e_ops == EABleMusicControl.MusicControl.volume_up) {
                            jsonObject.put("action", 4);
                        }
                        if (eaBleMusicControl.e_ops == EABleMusicControl.MusicControl.volume_reduction) {
                            jsonObject.put("action", 5);
                        }
                        MethodChannel methodChannel = new MethodChannel(flutterEngine.getDartExecutor(), "sdk");
                        if (methodChannel != null) {
                            methodChannel.invokeMethod("REPORT", jsonObject.toJSONString());
                        }
                    }
                });

            }

        }

        @Override
        public void socialResponse(final EABleSocialResponse eaBleSocialResponse) {
            if (mHandler != null) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("dataType", 0x0D);
                        jsonObject.put("socialId", eaBleSocialResponse.id);
                        jsonObject.put("content", eaBleSocialResponse.content);
                        MethodChannel methodChannel = new MethodChannel(flutterEngine.getDartExecutor(), "sdk");
                        if (methodChannel != null) {
                            methodChannel.invokeMethod("REPORT", jsonObject.toJSONString());
                        }
                    }
                });
            }


        }

        @Override
        public void mtu(final EABleMtu eaBleMtu) {
            if (mHandler != null) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("dataType", 0x0E);
                        jsonObject.put("mtu", eaBleMtu.getMtu_value());
                        MethodChannel methodChannel = new MethodChannel(flutterEngine.getDartExecutor(), "sdk");
                        if (methodChannel != null) {
                            methodChannel.invokeMethod("REPORT", jsonObject.toJSONString());
                        }
                    }
                });
            }


        }

        @Override
        public void mutualFail(final int i) {
            if (mHandler != null) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("dataType", 0x0F);
                        jsonObject.put("errorCode", i);
                        MethodChannel methodChannel = new MethodChannel(flutterEngine.getDartExecutor(), "sdk");
                        if (methodChannel != null) {
                            methodChannel.invokeMethod("REPORT", jsonObject.toJSONString());
                        }
                    }
                });
            }

        }
    }


    class MotionDataListener implements MotionDataReportCallback {
        @Override
        public void dailyExerciseData(final List<EABleDailyData> list, final CommonFlag commonFlag) {

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("data", list);
            jsonObject.put("flag", commonFlag.getValue());
            jsonObject.put("dataType", kEADataInfoTypeStepData);
            sendBigWatchData(jsonObject);
        }

        @Override
        public void sleepData(final List<EABleSleepData> list, final CommonFlag commonFlag) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("data", list);
            jsonObject.put("flag", commonFlag.getValue());
            jsonObject.put("dataType", kEADataInfoTypeSleepData);
            sendBigWatchData(jsonObject);
        }

        @Override
        public void heartData(final List<EABleHeartData> list, final CommonFlag commonFlag) {

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("data", list);
            jsonObject.put("flag", commonFlag.getValue());
            jsonObject.put("dataType", kEADataInfoTypeHeartRateData);
            sendBigWatchData(jsonObject);
        }

        @Override
        public void gpsData(final List<EABleGpsData> list, final CommonFlag commonFlag) {

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("data", list);
            jsonObject.put("flag", commonFlag.getValue());
            jsonObject.put("dataType", kEADataInfoTypeGPSData);
            sendBigWatchData(jsonObject);
        }

        @Override
        public void multiMotionData(List<EABleMultiData> list, CommonFlag commonFlag) {

            JSONObject json = new JSONObject();

            List<TempMotion> motionList = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                TempMotion tempMotion = new TempMotion();
                tempMotion.e_type = list.get(i).getE_type().getValue();
                tempMotion.begin_time_stamp = list.get(i).getBegin_time_stamp();
                tempMotion.end_time_stamp = list.get(i).getEnd_time_stamp();
                tempMotion.steps = list.get(i).getSteps();
                tempMotion.calorie = list.get(i).getCalorie();
                tempMotion.distance = list.get(i).getDistance();
                tempMotion.duration = list.get(i).getDuration();
                tempMotion.training_effect_normal = list.get(i).getTraining_effect_normal();
                tempMotion.training_effect_warmUp = list.get(i).getTraining_effect_warmUp();
                tempMotion.training_effect_fatconsumption = list.get(i).getTraining_effect_fatconsumption();
                tempMotion.training_effect_aerobic = list.get(i).getTraining_effect_aerobic();
                tempMotion.training_effect_anaerobic = list.get(i).getTraining_effect_anaerobic();
                tempMotion.training_effect_limit = list.get(i).getTraining_effect_limit();
                tempMotion.average_heart_rate = list.get(i).getAverage_heart_rate();
                tempMotion.average_temperature = list.get(i).getAverage_temperature();
                tempMotion.average_speed = list.get(i).getAverage_speed();
                tempMotion.average_pace = list.get(i).getAverage_pace();
                tempMotion.average_step_freq = list.get(i).getAverage_step_freq();
                tempMotion.average_stride = list.get(i).getAverage_stride();
                tempMotion.average_altitude = list.get(i).getAverage_altitude();
                tempMotion.average_heart_rate_max = list.get(i).getAverage_heart_rate_max();
                tempMotion.average_heart_rate_min = list.get(i).getAverage_heart_rate_min();
                motionList.add(tempMotion);
            }
            json.put("data", motionList);
            json.put("flag", commonFlag.getValue());
            json.put("dataType", kEADataInfoTypeSportsData);
            sendBigWatchData(json);


        }

        @Override
        public void bloodOxygenData(final List<EABleBloodOxygen> list, final CommonFlag commonFlag) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("data", list);
            jsonObject.put("flag", commonFlag.getValue());
            jsonObject.put("dataType", kEADataInfoTypeBloodOxygenData);
            sendBigWatchData(jsonObject);
        }

        @Override
        public void pressureData(List<EABlePressureData> list, CommonFlag commonFlag) {

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("data", list);
            jsonObject.put("flag", commonFlag.getValue());
            jsonObject.put("dataType", kEADataInfoTypeStressData);
            sendBigWatchData(jsonObject);
        }

        @Override
        public void stepFrequencyData(List<EABleStepFrequencyData> list, CommonFlag commonFlag) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("data", list);
            jsonObject.put("flag", commonFlag.getValue());
            jsonObject.put("dataType", kEADataInfoTypeStepFreqData);
            sendBigWatchData(jsonObject);

        }

        @Override
        public void speedData(List<EABlePaceData> list, CommonFlag commonFlag) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("data", list);
            jsonObject.put("flag", commonFlag.getValue());
            jsonObject.put("dataType", kEADataInfoTypeStepPaceData);
            sendBigWatchData(jsonObject);
        }

        @Override
        public void restingHeartRateData(List<EABleRestingRateData> list, CommonFlag commonFlag) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("data", list);
            jsonObject.put("flag", commonFlag.getValue());
            jsonObject.put("dataType", kEADataInfoTypeRestingHeartRateData);
            sendBigWatchData(jsonObject);
        }

        @Override
        public void getHabitData(List<EABleHabitRecord> list, CommonFlag commonFlag) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("data", list);
            jsonObject.put("flag", commonFlag.getValue());
            jsonObject.put("dataType", EADataInfoTypeHabitTrackerData);
            sendBigWatchData(jsonObject);
        }

        @Override
        public void mutualFail(int i) {

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("data", null);
            jsonObject.put("flag", CommonFlag.end.getValue());
            sendBigWatchData(jsonObject);
        }

    }

    class ConnectListener implements EABleConnectListener {

        @Override
        public void deviceConnected() {
            if (mHandler != null) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        channel.invokeMethod(kConnectState, ConnectState.succ.getValue());
                    }
                });
            }
        }

        @Override
        public void deviceDisconnect() {
            if (mHandler != null) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        channel.invokeMethod(kConnectState, ConnectState.disConnect.getValue());
                    }
                });
            }
        }

        @Override
        public void deviceNotFind() {
            if (mHandler != null) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        channel.invokeMethod(kConnectState, ConnectState.notFind.getValue());
                    }
                });
            }
        }

        @Override
        public void connectError(int i) {
            if (mHandler != null) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        channel.invokeMethod(kConnectState, ConnectState.fail.getValue());
                    }
                });
            }
        }

        @Override
        public void connectTimeOut() {
            if (mHandler != null) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        channel.invokeMethod(kConnectState, ConnectState.timeout.getValue());
                    }
                });
            }
        }

        @Override
        public void unsupportedBLE() {
            if (mHandler != null) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        channel.invokeMethod(kBluetoothState, BluetoothState.unSupportBle.getValue());
                    }
                });
            }
        }

        @Override
        public void unopenedBluetooth() {
            if (mHandler != null) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        channel.invokeMethod(kBluetoothState, BluetoothState.unOpen.getValue());
                    }
                });
            }
        }

        @Override
        public void notOpenLocation() {
            if (mHandler != null) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        channel.invokeMethod(kBluetoothState, BluetoothState.unOpenLocation.getValue());
                    }
                });
            }
        }


    }

    @Override
    public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
        channel = new MethodChannel(flutterPluginBinding.getBinaryMessenger(), "easdktool");
        channel.setMethodCallHandler(this);
        flutterEngine = flutterPluginBinding.getFlutterEngine();
        mContext = flutterPluginBinding.getApplicationContext();
        mHandler = new Handler(Looper.myLooper());
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

                    if (mHandler != null) {
                        mHandler.post(new Runnable() {
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
                    }

                }

                @Override
                public void scanError(int i) {

                    if (mHandler != null) {
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {

                                channel.invokeMethod(kArgumentsError, "scan error");
                            }
                        });
                    }

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
                if (mHandler != null) {
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            channel.invokeMethod(kArgumentsError, "connectAddress error");
                        }
                    });
                }
                return;
            }
            EABleManager.getInstance().connectToPeripheral(address, mContext, new ConnectListener(), 128, new DeviceOperationListener(), new MotionDataListener());
        } else if (call.method.equals(kEADisConnectWatch)) { // 手动断开设备

            EABleConnectState connectState = EABleManager.getInstance().getDeviceConnectState();
            if (connectState == EABleConnectState.STATE_CONNECTED) {
                EABleManager.getInstance().disconnectPeripheral();

                if (mHandler != null) {
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            channel.invokeMethod(kConnectState, ConnectState.disConnect.getValue());
                        }
                    });
                }
            }
        } else if (call.method.equals(kEAUnbindWatch)) { // 解绑设备
            EABleDev eaBleDev = new EABleDev();
            eaBleDev.e_ops = EABleDev.DevOps.restore_factory;
            EABleManager.getInstance().setDeviceOps(eaBleDev, new GeneralCallback() {
                @Override
                public void result(boolean b) {

                }

                @Override
                public void mutualFail(int i) {

                }
            });
        } else if (call.method.equals(kEAbindingWatch)) { //绑定设备

            String arguments = (String) call.arguments;
            if (checkArgumentName("user_id", arguments)) {


                final BindInfo bindInfo = JSONObject.parseObject(arguments, BindInfo.class);
                EABleBindInfo eaBleBindInfo = new EABleBindInfo();
                eaBleBindInfo.setUser_id(bindInfo.user_id);
                eaBleBindInfo.setE_ops(bindInfo.ops == 1 ? EABleBindInfo.BindingOps.end : EABleBindInfo.BindingOps.normal_begin);
                eaBleBindInfo.setBind_mod(bindInfo.bindMod);
                EABleManager.getInstance().setOpsBinding(eaBleBindInfo, new GeneralCallback() {
                    @Override
                    public void result(boolean b) {
                        if (b) {
                            if (bindInfo.ops == 0) {
                                EABleBindInfo eaBleBindInfo1 = new EABleBindInfo();
                                eaBleBindInfo1.setUser_id(bindInfo.user_id);
                                eaBleBindInfo1.setE_ops(EABleBindInfo.BindingOps.end);
                                eaBleBindInfo1.setBind_mod(bindInfo.bindMod);
                                EABleManager.getInstance().setOpsBinding(eaBleBindInfo1, new GeneralCallback() {
                                    @Override
                                    public void result(boolean b) {
                                        setWatchDataResponse((b ? 0 : 1), kEADataInfoTypeBingWatch);
                                    }


                                    @Override
                                    public void mutualFail(int i) {
                                        if (mHandler != null) {
                                            mHandler.post(new Runnable() {
                                                @Override
                                                public void run() {
                                                    channel.invokeMethod(kArgumentsError, "user_id error");
                                                }
                                            });
                                        }
                                    }
                                });
                            }
                        } else {
                            setWatchDataResponse((b ? 0 : 1), kEADataInfoTypeBingWatch);
                            if (EABleManager.getInstance().getEaBleConnectListener() != null) {
                                EABleManager.getInstance().getEaBleConnectListener().deviceDisconnect();
                            }
                            EABleManager.getInstance().disconnectPeripheral();
                        }
                    }

                    @Override
                    public void mutualFail(int i) {
                        Log.e(TAG, "收到错误");
                        if (mHandler != null) {
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                }
                            });
                        }
                    }
                });
            }        
        } else if (call.method.equals(kEAGetWatchInfo)) { // 获取手表数据

            String arguments = (String) call.arguments;
            if (checkArgumentName("type", arguments)) {
                Map<String, Integer> map = JSONObject.parseObject(arguments, Map.class);
                int type = map.get("type");
                getWatchData(type);
            }
        } else if (call.method.equals(kEASetWatchInfo)) { // 设置手表

            String arguments = (String) call.arguments;
            if (checkArgumentName("type", arguments) && checkArgumentName("jsonString", arguments)) {

                Map<String, Object> map = JSONObject.parseObject(arguments, Map.class);
                setWatchData(map);
            }
        } else if (call.method.equals(kEAGetBigWatchData)) { /// 获取大数据

            EABleManager.getInstance().requestSyncMotionData(MotionReportType.hr_data_req, new GeneralCallback() {
                @Override
                public void result(boolean b) {
                    setWatchDataResponse(0, kEADataInfoTypeGetBigData);
                }

                @Override
                public void mutualFail(int i) {
                    setWatchDataResponse(1, kEADataInfoTypeGetBigData);
                }
            });
        } else if (call.method.equals(kEAOperationWatch)) {

            String arguments = (String) call.arguments;
            if (checkArgumentName("type", arguments)) {
                Map<String, Object> map = JSONObject.parseObject(arguments, Map.class);
                //  SetWatchParam setWatchParam = JSONObject.parseObject(arguments, SetWatchParam.class);
                int action = (int) map.get("type");
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

                        if (mHandler != null) {
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    JSONObject jsonObject = new JSONObject();
                                    jsonObject.put("operationType", action);
                                    jsonObject.put("respondCodeType", b);
                                    channel.invokeMethod(kOperationWacthResponse, jsonObject.toJSONString());
                                }
                            });
                        }
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
                List<JSONObject> wArray = (List<JSONObject>) map.get("otas");
                if (wArray != null && !wArray.isEmpty()) {
                    List<TempOtaData> otaDataList = new ArrayList<>();
                    for (int i = 0; i < wArray.size(); i++) {
                        JSONObject wMap = wArray.get(i);
                        TempOtaData tempOtaData = new TempOtaData();
                        tempOtaData.version = wMap.getString("version");
                        tempOtaData.firmwareType = wMap.getInteger("firmwareType");
                        tempOtaData.binPath = wMap.getString("binPath");
                        otaDataList.add(tempOtaData);

                    }
                    if (otaDataList != null && !otaDataList.isEmpty()) {
                        new Thread() {
                            @Override
                            public void run() {
                                super.run();
                                otaAction(otaDataList);
                            }
                        }.start();
                    }
                }

            }
        } else {
            result.notImplemented();
        }
    }

    private void getWatchData(int type) {
        switch (type) {
            case 3: {
                EABleManager.getInstance().queryWatchInfo(QueryWatchInfoType.watch_info, new WatchInfoCallback() {
                    @Override
                    public void watchInfo(EABleWatchInfo eaBleWatchInfo) {
                        if (mHandler != null) {
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {

                                    // {agpsUpdateTimestamp: 1648811935, eBindingInfo: 0, type: A02, userId: 10086, firmwareVersion: AP0.1B4.6R0.6T0.1H0.1G0.1, id_p: 001001211112000028}
                                    Map map = new HashMap();
                                    map.put("eBindingInfo", eaBleWatchInfo.bindingInfo.getValue());
                                    map.put("agpsUpdateTimestamp", eaBleWatchInfo.getAgps_update_timestamp());
                                    map.put("firmwareVersion", eaBleWatchInfo.getFirmwareVersion());
                                    map.put("userId", eaBleWatchInfo.getUserId());
                                    map.put("id_p", eaBleWatchInfo.getWatchId());
                                    map.put("bleMacAddr",eaBleWatchInfo.getBle_mac_addr());
                                    map.put("isWaitForBinding",eaBleWatchInfo.getIs_wait_for_binding());
                                    String watchType = eaBleWatchInfo.getWatchType();
                                    if (watchType.equals("G01")){
                                        map.put("type", "iTouch Flex");
                                    }else  {
                                        map.put("type", eaBleWatchInfo.getWatchType());
                                    }
                                    sendWatchDataWithMap(map, type);
                                }
                            });
                        }
                    }

                    @Override
                    public void mutualFail(int i) {
                    }
                });
            }
            break;
            case 4: {
                EABleManager.getInstance().queryWatchInfo(QueryWatchInfoType.user_info, new PersonInfoCallback() {
                    @Override
                    public void personInfo(EABlePersonInfo eaBlePersonInfo) {
                        if (mHandler != null) {
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    //flutter: {weight: 65000, eHandInfo: 0, height: 170, age: 26, eSexInfo: 1, eSkinColor: 0}
                                    Map<String, Integer> map = new HashMap();
                                    map.put("eSexInfo", eaBlePersonInfo.e_sex_info.getValue());
                                    map.put("eHandInfo", eaBlePersonInfo.e_hand_info.getValue());
                                    map.put("eSkinColor", eaBlePersonInfo.e_skin_color.getValue());
                                    sendWatchDataWithObjectMap(eaBlePersonInfo, map, type);
                                }
                            });
                        }
                    }

                    @Override
                    public void mutualFail(int i) {

                    }
                });
            }
            break;
            case 5:{
                EABleManager.getInstance().queryWatchInfo(QueryWatchInfoType.sync_time, new TimeCallback() {
                    @Override
                    public void syncTime(EABleSyncTime eaBleSyncTime) {
                        if (mHandler != null) {
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {

                                    Map<String, Integer> map = new HashMap();
                                    map.put("timeHourType", eaBleSyncTime.e_hour_system.getValue());
                                    map.put("timeZone", eaBleSyncTime.e_time_zone.getValue());
                                    map.put("timeZoneHour", eaBleSyncTime.getTime_zone_hour());
                                    map.put("timeZoneMinute", eaBleSyncTime.getTime_zone_minute());
                                    sendWatchDataWithObjectMap(eaBleSyncTime, map, type);
                                }
                            });
                        }
                    }

                    @Override
                    public void mutualFail(int i) {

                    }
                });
            }break;
            case 8: {
                EABleManager.getInstance().queryWatchInfo(QueryWatchInfoType.black_screen_time, new RestScreenCallback() {
                    @Override
                    public void restScreen(int i) {
                        if (mHandler != null) {
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {

                                    sendWatchDataWithOtherKeyValue("timeout", i, type);
                                }
                            });
                        }
                    }

                    @Override
                    public void mutualFail(int i) {
                    }
                });
            }
            break;
            case 7: {
                EABleManager.getInstance().queryWatchInfo(QueryWatchInfoType.screen_light, new ScreenBrightnessCallback() {
                    @Override
                    public void screenBrightness(int i) {
                        if (mHandler != null) {
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {

                                    sendWatchDataWithOtherKeyValue("level", i, type);
                                }
                            });
                        }
                    }

                    @Override
                    public void mutualFail(int i) {
                    }
                });
            }
            break;
            case 9: {
                EABleManager.getInstance().queryWatchInfo(QueryWatchInfoType.battery_info, new BatterInfoCallback() {
                    @Override
                    public void batterInfo(EABleBatInfo eaBleBatInfo) {
                        if (mHandler != null) {
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    //{eStatus: 0, level: 90}
                                    Map<String, Integer> map = new HashMap();
                                    map.put("eStatus", eaBleBatInfo.e_status.getValue());
                                    sendWatchDataWithObjectMap(eaBleBatInfo, map, type);
                                }
                            });
                        }
                    }

                    @Override
                    public void mutualFail(int i) {
                    }
                });
            }
            break;
            case 10: {
                EABleManager.getInstance().queryWatchInfo(QueryWatchInfoType.language, new LanguageCallback() {
                    @Override
                    public void languageInfo(EABleDeviceLanguage eaBleDeviceLanguage) {
                        if (mHandler != null) {
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {

                                    Map<String, Integer> map = new HashMap();
                                    map.put("eType", eaBleDeviceLanguage.e_type.getValue());
                                    sendWatchDataWithObjectMap(eaBleDeviceLanguage, map, type);
                                }
                            });
                        }
                    }

                    @Override
                    public void mutualFail(int i) {
                    }
                });
            }
            break;
            case 11: {
                EABleManager.getInstance().queryWatchInfo(QueryWatchInfoType.unit_format, new UnitCallback() {
                    @Override
                    public void unitInfo(EABleDevUnit eaBleDevUnit) {
                        if (mHandler != null) {
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    Map<String, Integer> map = new HashMap();
                                    map.put("eFormat", eaBleDevUnit.e_format.getValue());
                                    sendWatchDataWithObjectMap(eaBleDevUnit, map, type);
                                }
                            });
                        }
                    }

                    @Override
                    public void mutualFail(int i) {

                    }
                });
            }
            break;
            case 13: {
                EABleManager.getInstance().queryWatchInfo(QueryWatchInfoType.not_disturb, new DonDisturbCallback() {
                    @Override
                    public void donDisturbInfo(EABleNotDisturb eaBleNotDisturb) {
                        if (mHandler != null) {
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
//                                    {endHour: 23, beginMinute: 0, beginHour: 0, endMinute: 59, sw: 0}
                                    Map<String, Integer> map = new HashMap();
                                    map.put("beginHour", eaBleNotDisturb.getBegin_hour());
                                    map.put("beginMinute", eaBleNotDisturb.getBegin_minute());
                                    map.put("endHour", eaBleNotDisturb.getEnd_hour());
                                    map.put("endMinute", eaBleNotDisturb.getBegin_minute());
                                    map.put("sw", eaBleNotDisturb.getSw());
                                    sendWatchDataWithMap(map, type);
                                }
                            });
                        }
                    }

                    @Override
                    public void mutualFail(int i) {
                    }
                });
            }
            break;
            case 15: {
                EABleManager.getInstance().queryWatchInfo(QueryWatchInfoType.daily_goal, new GoalCallback() {
                    @Override
                    public void goalInfo(EABleDailyGoal eaBleDailyGoal) {
                        if (mHandler != null) {
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {

                                    String sw = "sw";
                                    String goal = "goal";
                                    JSONObject jsonObject = new JSONObject();
                                    if (eaBleDailyGoal.getS_step() != null) {
                                        addKeyValues(sw, eaBleDailyGoal.getS_step().getSw(), goal, eaBleDailyGoal.getS_step().getGoal(), jsonObject, "sStep");
                                    }
                                    if (eaBleDailyGoal.getS_calorie() != null) {
                                        addKeyValues(sw, eaBleDailyGoal.getS_calorie().getSw(), goal, eaBleDailyGoal.getS_calorie().getGoal(), jsonObject, "sCalorie");
                                    }
                                    if (eaBleDailyGoal.getS_distance() != null) {
                                        addKeyValues(sw, eaBleDailyGoal.getS_distance().getSw(), goal, eaBleDailyGoal.getS_distance().getGoal(), jsonObject, "sDistance");
                                    }
                                    if (eaBleDailyGoal.getS_duration() != null) {
                                        addKeyValues(sw, eaBleDailyGoal.getS_duration().getSw(), goal, eaBleDailyGoal.getS_duration().getGoal(), jsonObject, "sDuration");
                                    }
                                    if (eaBleDailyGoal.getS_sleep() != null) {
                                        addKeyValues(sw, eaBleDailyGoal.getS_sleep().getSw(), goal, eaBleDailyGoal.getS_sleep().getGoal(), jsonObject, "sSleep");
                                    }
                                    Map map = jsonObject.getInnerMap();
                                    sendWatchDataWithMap(map, type);
                                }
                            });
                        }
                    }

                    @Override
                    public void mutualFail(int i) {

                    }
                });
            }
            break;
            case 16: {
                EABleManager.getInstance().queryWatchInfo(QueryWatchInfoType.sleep_check, new SleepCheckCallback() {
                    @Override
                    public void sleepInfo(EABleAutoCheckSleep eaBleAutoCheckSleep) {
                        if (mHandler != null) {
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    JSONObject jsonObject = new JSONObject();
                                    jsonObject.put("weekCycleBit", eaBleAutoCheckSleep.getWeek_cycle_bit());
                                    jsonObject.put("beginHour", eaBleAutoCheckSleep.getBegin_hour());
                                    jsonObject.put("beginMinute", eaBleAutoCheckSleep.getBegin_minute());
                                    jsonObject.put("endHour", eaBleAutoCheckSleep.getEnd_hour());
                                    jsonObject.put("endMinute", eaBleAutoCheckSleep.getEnd_minute());
                                    Map map = jsonObject.getInnerMap();
                                    sendWatchDataWithMap(map, type);
                                }
                            });
                        }
                    }

                    @Override
                    public void mutualFail(int i) {

                    }
                });
            }
            break;
            case 17: {
                EABleManager.getInstance().queryWatchInfo(QueryWatchInfoType.heart_rate_check, new HeartCheckCallback() {
                    @Override
                    public void heartInfo(int i) {
                        if (mHandler != null) {
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    // interval
                                    sendWatchDataWithOtherKeyValue("interval", i, type);
                                }
                            });
                        }
                    }

                    @Override
                    public void mutualFail(int i) {

                    }
                });
            }
            break;
            case 18: {
                EABleManager.getInstance().queryWatchInfo(QueryWatchInfoType.sit_check, new SedentaryCheckCallback() {
                    @Override
                    public void sedentaryInfo(EABleSedentariness eaBleSedentariness) {
                        if (mHandler != null) {
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {

                                    JSONObject jsonObject = new JSONObject();
                                    jsonObject.put("interval", eaBleSedentariness.getInterval());
                                    jsonObject.put("weekCycleBit", eaBleSedentariness.getWeek_cycle_bit());
                                    jsonObject.put("beginHour", eaBleSedentariness.getBegin_hour());
                                    jsonObject.put("beginMinute", eaBleSedentariness.getBegin_minute());
                                    jsonObject.put("endHour", eaBleSedentariness.getEnd_hour());
                                    jsonObject.put("endMinute", eaBleSedentariness.getEnd_minute());
                                    jsonObject.put("stepThreshold", eaBleSedentariness.getStep_threshold());
                                    Map map = jsonObject.getInnerMap();
                                    sendWatchDataWithMap(map, type);
                                }
                            });
                        }
                    }

                    @Override
                    public void mutualFail(int i) {

                    }
                });
            }
            break;
            case 21: {
                EABleManager.getInstance().queryWatchInfo(QueryWatchInfoType.ancs_sw, new RemindCallback() {
                    @Override
                    public void remindInfo(EABleAncsSw eaBleAncsSw) {
                        if (mHandler != null) {
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {

                                    JSONObject jsonObject = new JSONObject();
                                    if (eaBleAncsSw.getS_incomingcall() != null) {
                                        JSONObject jsonObject1 = new JSONObject();
                                        jsonObject1.put("sw", eaBleAncsSw.getS_incomingcall().getSw());
                                        jsonObject1.put("remindActionType", eaBleAncsSw.getS_incomingcall().getE_action().getValue());
                                        jsonObject.put("sIncomingcall", jsonObject1.getInnerMap());

                                    }
                                    if (eaBleAncsSw.getS_missedcall() != null) {
                                        JSONObject jsonObject2 = new JSONObject();
                                        jsonObject2.put("sw", eaBleAncsSw.getS_missedcall().getSw());
                                        jsonObject2.put("remindActionType", eaBleAncsSw.getS_missedcall().getE_action().getValue());
                                        jsonObject.put("sMissedcall", jsonObject2.getInnerMap());

                                    }
                                    if (eaBleAncsSw.getS_email() != null) {
                                        JSONObject jsonObject3 = new JSONObject();
                                        jsonObject3.put("sw", eaBleAncsSw.getS_email().getSw());
                                        jsonObject3.put("remindActionType", eaBleAncsSw.getS_email().getE_action().getValue());
                                        jsonObject.put("sEmail", jsonObject3.getInnerMap());
                                    }
                                    if (eaBleAncsSw.getS_sms() != null) {
                                        JSONObject jsonObject4 = new JSONObject();
                                        jsonObject4.put("sw", eaBleAncsSw.getS_sms().getSw());
                                        jsonObject4.put("remindActionType", eaBleAncsSw.getS_sms().getE_action().getValue());
                                        jsonObject.put("sSms", jsonObject4.getInnerMap());
                                    }
                                    if (eaBleAncsSw.getS_social() != null) {
                                        JSONObject jsonObject5 = new JSONObject();
                                        jsonObject5.put("sw", eaBleAncsSw.getS_social().getSw());
                                        jsonObject5.put("remindActionType", eaBleAncsSw.getS_social().getE_action().getValue());
                                        jsonObject.put("sSocial", jsonObject5.getInnerMap());
                                    }
                                    if (eaBleAncsSw.getS_schedule() != null) {
                                        JSONObject jsonObject6 = new JSONObject();
                                        jsonObject6.put("sw", eaBleAncsSw.getS_schedule().getSw());
                                        jsonObject6.put("remindActionType", eaBleAncsSw.getS_schedule().getE_action().getValue());
                                        jsonObject.put("sSchedule", jsonObject6.getInnerMap());
                                    }
                                    Map map = jsonObject.getInnerMap();
                                    sendWatchDataWithMap(map, type);
                                }
                            });
                        }
                    }

                    @Override
                    public void mutualFail(int i) {

                    }
                });

            }
            break;
            case 22: {
                EABleManager.getInstance().queryWatchInfo(QueryWatchInfoType.reminder, new AttentionCallback() {
                    @Override
                    public void attentionInfo(EABleReminder eaBleReminder) {
                        if (mHandler != null) {
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    JSONObject jsonObject = new JSONObject();
                                    jsonObject.put("id", eaBleReminder.id);
                                    jsonObject.put("e_ops", eaBleReminder.getE_ops().getValue());
                                    List<ReminderItem> items = new ArrayList<>();
                                    if (eaBleReminder.getS_index() != null && !eaBleReminder.getS_index().isEmpty()) {
                                        for (int i = 0; i < eaBleReminder.getS_index().size(); i++) {
                                            ReminderItem reminderItem = new ReminderItem();
                                            reminderItem.reminderEventType = eaBleReminder.getS_index().get(i).getE_type().getValue();
                                            reminderItem.id_p = eaBleReminder.getS_index().get(i).getId();
                                            reminderItem.hour = eaBleReminder.getS_index().get(i).getHour();
                                            reminderItem.minute = eaBleReminder.getS_index().get(i).getMinute();
                                            reminderItem.year = eaBleReminder.getS_index().get(i).getYear();
                                            reminderItem.month = eaBleReminder.getS_index().get(i).getMonth();
                                            reminderItem.day = eaBleReminder.getS_index().get(i).getDay();
                                            reminderItem.weekCycleBit = eaBleReminder.getS_index().get(i).getWeek_cycle_bit();
                                            reminderItem.sw = eaBleReminder.getS_index().get(i).getSw();
                                            reminderItem.secSw = eaBleReminder.getS_index().get(i).getSec_sw();
                                            reminderItem.sleepDuration = eaBleReminder.getS_index().get(i).getSleep_duration();
                                            reminderItem.remindActionType = eaBleReminder.getS_index().get(i).getE_action().getValue();
                                            reminderItem.content = eaBleReminder.getS_index().get(i).getContent();
                                            items.add(reminderItem);
                                        }
                                    }
                                    jsonObject.put("sIndexArray", items);
                                    Map map = jsonObject.getInnerMap();
                                    sendWatchDataWithMap(map, type);
                                }
                            });
                        }
                    }

                    @Override
                    public void mutualFail(int i) {

                    }
                });

            }
            break;
            case 24: {
                EABleManager.getInstance().queryWatchInfo(QueryWatchInfoType.distance_unit, new DistanceUnitCallback() {
                    @Override
                    public void distanceUnitInfo(EABleDistanceFormat eaBleDistanceFormat) {
                        if (mHandler != null) {
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    sendWatchDataWithOtherKeyValue("eFormat", eaBleDistanceFormat.e_format.getValue(), type);
                                }
                            });
                        }
                    }

                    @Override
                    public void mutualFail(int i) {

                    }
                });
            }
            break;
            case 25: {
                EABleManager.getInstance().queryWatchInfo(QueryWatchInfoType.weight_unit, new WeightUnitCallback() {
                    @Override
                    public void weightUnitInfo(EABleWeightFormat eaBleWeightFormat) {
                        if (mHandler != null) {
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    sendWatchDataWithOtherKeyValue("eFormat", eaBleWeightFormat.e_format.getValue(), type);
                                }
                            });
                        }
                    }

                    @Override
                    public void mutualFail(int i) {

                    }
                });

            }
            break;
            case 26: {
                EABleManager.getInstance().queryWatchInfo(QueryWatchInfoType.heart_rate_limit, new HeartLimitCallback() {
                    @Override
                    public void heartLimitInfo(EABleHr eaBleHr) {
                        if (mHandler != null) {
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    JSONObject jsonObject = new JSONObject();
                                    jsonObject.put("sw", eaBleHr.getSw());
                                    jsonObject.put("maxHr", eaBleHr.getMax_hr());
                                    jsonObject.put("minHr", eaBleHr.getMin_hr());
                                    sendWatchDataWithMap(jsonObject.getInnerMap(), type);
                                }
                            });
                        }
                    }

                    @Override
                    public void mutualFail(int i) {

                    }
                });
            }
            break;
            case 27: {
                EABleManager.getInstance().queryWatchInfo(QueryWatchInfoType.base_calories, new CalorieSwitchCallback() {
                    @Override
                    public void switchInfo(int i) {
                        if (mHandler != null) {
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    sendWatchDataWithOtherKeyValue("sw", i, type);
                                }
                            });
                        }
                    }

                    @Override
                    public void mutualFail(int i) {

                    }
                });
            }
            break;
            case 28: {
                EABleManager.getInstance().queryWatchInfo(QueryWatchInfoType.gestures, new RaiseHandBrightScreenCallback() {
                    @Override
                    public void switchInfo(EABleGesturesBrightScreen eaBleGesturesBrightScreen) {
                        if (mHandler != null) {
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    JSONObject jsonObject = new JSONObject();
                                    jsonObject.put("eBrightSrc", eaBleGesturesBrightScreen.getBrightScreenSwitch().getValue());
                                    jsonObject.put("beginHour", eaBleGesturesBrightScreen.getBegin_hour());
                                    jsonObject.put("beginMinute", eaBleGesturesBrightScreen.getBegin_minute());
                                    jsonObject.put("endHour", eaBleGesturesBrightScreen.getEnd_hour());
                                    jsonObject.put("endMinute", eaBleGesturesBrightScreen.getEnd_minute());
                                    sendWatchDataWithMap(jsonObject.getInnerMap(), type);
                                }
                            });
                        }
                    }

                    @Override
                    public void mutualFail(int i) {

                    }
                });
            }
            break;
            case 30: {
                EABleManager.getInstance().queryWatchInfo(QueryWatchInfoType.combination, new CombinationCallback() {
                    @Override
                    public void combinationInfo(final EABleCombination eaBleCombination) {
                        if (mHandler != null) {
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {

                                    Map<String, Integer> map = new HashMap();
                                    map.put("e_status", eaBleCombination.getE_status().getValue());
                                    map.put("e_vibrate_intensity", eaBleCombination.getE_vibrate_intensity().getValue());
                                    map.put("e_hand_info", eaBleCombination.getE_hand_info().getValue());
                                    map.put("e_unit_format", eaBleCombination.getE_unit_format().getValue());
                                    sendWatchDataWithObjectMap(eaBleCombination, map, type);
                                }
                            });
                        }
                    }
                    @Override
                    public void mutualFail(int i) {
                    }
                });
            }
            break;
            case 31: {
                EABleManager.getInstance().queryWatchInfo(QueryWatchInfoType.home_page, new MenuCallback() {
                    @Override
                    public void menuInfo(EABleMenuPage eaBleMenuPage) {
                        if (eaBleMenuPage != null) {
                            List<EABleMenuPage.MenuType> types = eaBleMenuPage.getAllSupportList();
                            if (types == null || types.isEmpty()) {
                                types = new ArrayList<>();
                                types.add(EABleMenuPage.MenuType.page_pressure);
                                types.add(EABleMenuPage.MenuType.page_heart_rate);
                                types.add(EABleMenuPage.MenuType.page_menstrual_cycle);
                                types.add(EABleMenuPage.MenuType.page_breath);
                                types.add(EABleMenuPage.MenuType.page_music);
                                types.add(EABleMenuPage.MenuType.page_sleep);
                                types.add(EABleMenuPage.MenuType.page_weather);
                                eaBleMenuPage.setAllSupportList(types);
                            }
                            if (mHandler != null) {
                                mHandler.post(new Runnable() {
                                    @Override
                                    public void run() {

                                        List<Map> aList = new ArrayList<>();
                                        List<Map> sList = new ArrayList<>();

                                        for (int i = 0; i < eaBleMenuPage.getTypeList().size(); i++) {

                                            JSONObject jsonObject = new JSONObject();
                                            jsonObject.put("eType", eaBleMenuPage.getTypeList().get(i).getValue());
                                            aList.add(jsonObject);
                                        }
                                        for (int i = 0; i < eaBleMenuPage.getAllSupportList().size(); i++) {

                                            JSONObject jsonObject = new JSONObject();
                                            jsonObject.put("eType", eaBleMenuPage.getAllSupportList().get(i).getValue());
                                            sList.add(jsonObject);
                                        }

                                        JSONObject jsonObject = new JSONObject();
                                        jsonObject.put("aList", aList);
                                        jsonObject.put("sList", sList);
                                        sendWatchDataWithMap(jsonObject.getInnerMap(), type);
                                    }
                                });
                            }
                        }
                    }

                    @Override
                    public void mutualFail(int i) {

                    }
                });
            }
            case 33: {
                EABleManager.getInstance().queryWatchInfo(QueryWatchInfoType.dial, new WatchFaceCallback() {
                    @Override
                    public void watchFaceInfo(EABleWatchFace eaBleWatchFace) {
                        if (mHandler != null) {
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {

                                    Map<String, Integer> map = new HashMap();
                                    sendWatchDataWithObjectMap(eaBleWatchFace, map, type);

                                }
                            });
                        }
                    }

                    @Override
                    public void mutualFail(int i) {
                    }
                });
            }
            break;
            case 34: {
                EABleManager.getInstance().queryWatchInfo(QueryWatchInfoType.push_info, new InfoPushCallback() {
                    @Override
                    public void pushInfo(EABleInfoPush eaBleInfoPush) {
                        if (mHandler != null) {
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    JSONObject jsonObject = new JSONObject();
                                    List<Integer> integerList = new ArrayList<>();
                                    if (eaBleInfoPush.getS_app_sw() != null && !eaBleInfoPush.getS_app_sw().isEmpty()) {
                                        for (int i = 0; i < eaBleInfoPush.getS_app_sw().size(); i++) {
                                            integerList.add(eaBleInfoPush.getS_app_sw().get(i).getSw());
                                        }
                                        jsonObject.put("data", integerList);
                                    }
                                    Map map = jsonObject.getInnerMap();
                                    sendWatchDataWithMap(map, type);
                                }
                            });
                        }
                    }

                    @Override
                    public void mutualFail(int i) {
                    }
                });
            }
            break;
            case 36: {

            }
            break;

            case 38:{
                EABleManager.getInstance().queryWatchInfo(QueryWatchInfoType.habit, new HabitCallback() {
                    @Override
                    public void habitInfo(EABleHabit eaBleHabit) {
                        if (eaBleHabit!=null){
                            if (mHandler != null) {
                                mHandler.post(new Runnable() {
                                    @Override
                                    public void run() {

                                        JSONObject jsonObject = new JSONObject();
                                        jsonObject.put("id", eaBleHabit.id);
                                        jsonObject.put("eOps", eaBleHabit.getE_ops().getValue());
                                        List<JSONObject> items = new ArrayList<>();
                                        if (eaBleHabit.getItemList() != null && !eaBleHabit.getItemList().isEmpty()) {
                                            for (int i = 0; i < eaBleHabit.getItemList().size(); i++) {

                                                EABleHabit.HabitItem habitItem = eaBleHabit.getItemList().get(i);
                                                JSONObject item = new JSONObject();
                                                item.put("eIconId", habitItem.getE_icon_id().getValue());
                                                item.put("id_p", habitItem.getId());
                                                item.put("beginHour", habitItem.getBegin_hour());
                                                item.put("beginMinute", habitItem.getBegin_minute());
                                                item.put("endHour", habitItem.getEnd_hour());
                                                item.put("endMinute", habitItem.getEnd_minute());
                                                item.put("r", habitItem.getRedColor());
                                                item.put("g", habitItem.getGreenColor());
                                                item.put("b", habitItem.getBlueColor());
                                                item.put("duration", habitItem.getDuration());
                                                item.put("eAction", habitItem.getE_action().getValue());
                                                item.put("content", habitItem.getContent());
                                                item.put("eFlag", habitItem.getHabitState().getValue());
                                                items.add(item);
                                            }
                                        }
                                        jsonObject.put("sIndexArray", items);
                                        Map map = jsonObject.getInnerMap();
                                        sendWatchDataWithMap(map, type);
                                    }
                                });
                            }
                        }
                    }
                    @Override
                    public void mutualFail(int i) {

                    }
                });
            }
            break;
            case 40:{

                EABleManager.getInstance().queryWatchInfo(QueryWatchInfoType.todayData, new TodayTotalDataCallback() {
                    @Override
                    public void todayData(TodayTotalData todayTotalData) {
                        if (mHandler != null) {
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {

                                    Map<String, Integer> map = new HashMap();
                                    sendWatchDataWithObjectMap(todayTotalData, map, type);
                                }
                            });
                        }
                    }
                    @Override
                    public void mutualFail(int i) {

                    }
                });
            }
            break;
            default:
                channel.invokeMethod(kArgumentsError, "type error");
                break;
        }
    }

    private void setWatchData(Map<String, Object> setWatchParam) {
        int type = (int) setWatchParam.get("type");
        String jsonString = (String) setWatchParam.get("jsonString");
        switch (type) {
            case (kEADataInfoTypeUser): {
                Map<String, Object> personInfo = JSONObject.parseObject(jsonString, Map.class);
                EABlePersonInfo eaBlePersonInfo = new EABlePersonInfo();
                eaBlePersonInfo.setAge((Integer) personInfo.get("age"));
                eaBlePersonInfo.setHeight((Integer) personInfo.get("height"));
                eaBlePersonInfo.setWeight((Integer) personInfo.get("weight"));
                int pHand = (int) personInfo.get("eHandInfo");
                if (pHand == 0) {
                    eaBlePersonInfo.setE_hand_info(PersonHand.left);
                } else {
                    eaBlePersonInfo.setE_hand_info(PersonHand.right);
                }
                int eSex = (int) personInfo.get("eSexInfo");
                if (eSex == 0) {
                    eaBlePersonInfo.setE_sex_info(EABlePersonInfo.PersonSex.male);
                } else {
                    eaBlePersonInfo.setE_sex_info(EABlePersonInfo.PersonSex.female);
                }
                int eSkin = (int) personInfo.get("eSkinColor");
                if (eSkin == 0) {
                    eaBlePersonInfo.setE_skin_color(EABlePersonInfo.SkinColor.skin_white);
                } else if (eSkin == 1) {
                    eaBlePersonInfo.setE_skin_color(EABlePersonInfo.SkinColor.skin_white_yellow);
                } else if (eSkin == 2) {
                    eaBlePersonInfo.setE_skin_color(EABlePersonInfo.SkinColor.skin_yellow);
                } else if (eSkin == 3) {
                    eaBlePersonInfo.setE_skin_color(EABlePersonInfo.SkinColor.skin_yellow_black);
                } else if (eSkin == 4) {
                    eaBlePersonInfo.setE_skin_color(EABlePersonInfo.SkinColor.skin_balck);
                }
                EABleManager.getInstance().setUserInfo(eaBlePersonInfo, new GeneralCallback() {
                    @Override
                    public void result(boolean b) {
                        setWatchDataResponse(0, (Integer) setWatchParam.get("type"));
                    }

                    @Override
                    public void mutualFail(int i) {
                        setWatchDataResponse(1, (Integer) setWatchParam.get("type"));
                    }
                });
            }
            break;
            case (kEADataInfoTypeSyncTime): {
                Map<String, Integer> syncTime = JSONObject.parseObject(jsonString, Map.class);
                EABleSyncTime eaBleSyncTime = new EABleSyncTime();
                eaBleSyncTime.setYear(syncTime.get("year"));
                eaBleSyncTime.setMonth(syncTime.get("month"));
                eaBleSyncTime.setDay(syncTime.get("day"));
                eaBleSyncTime.setHour(syncTime.get("hour"));
                eaBleSyncTime.setMinute(syncTime.get("minute"));
                eaBleSyncTime.setSecond(syncTime.get("second"));
                eaBleSyncTime.setTime_zone_hour(syncTime.get("timeZoneHour"));
                eaBleSyncTime.setTime_zone_minute(syncTime.get("timeZoneMinute"));
                int tType = syncTime.get("timeHourType");
                if (tType == 0) {
                    eaBleSyncTime.setE_hour_system(EABleSyncTime.HourSystem.hour_12);
                } else {
                    eaBleSyncTime.setE_hour_system(EABleSyncTime.HourSystem.hour_24);
                }
                int tZone = syncTime.get("timeZone");
                if (tZone == 0) {
                    eaBleSyncTime.setE_time_zone(TimeZone.zero);
                } else if (tZone == 1) {
                    eaBleSyncTime.setE_time_zone(TimeZone.east);
                } else if (tZone == 2) {
                    eaBleSyncTime.setE_time_zone(TimeZone.west);
                } else {
                    eaBleSyncTime.setE_time_zone(TimeZone.unknown);
                }
                EABleManager.getInstance().setTimeSync(eaBleSyncTime, new GeneralCallback() {
                    @Override
                    public void result(boolean b) {

                        setWatchDataResponse(0, (Integer) setWatchParam.get("type"));

                    }

                    @Override
                    public void mutualFail(int i) {

                        setWatchDataResponse(1, (Integer) setWatchParam.get("type"));

                    }
                });
            }
            break;
            case (kEADataInfoTypeLanguage): {

                Map<String, Integer> map = JSONObject.parseObject(jsonString, Map.class);
                int language = (int) map.get("language");
                EABleDeviceLanguage eaBleDeviceLanguage = new EABleDeviceLanguage();
                if (language == 0) {
                    eaBleDeviceLanguage.setE_type(EABleDeviceLanguage.LanguageType.default_type);
                } else if (language == 1) {
                    eaBleDeviceLanguage.setE_type(EABleDeviceLanguage.LanguageType.english);
                } else if (language == 2) {
                    eaBleDeviceLanguage.setE_type(EABleDeviceLanguage.LanguageType.chinese_simplifid);
                } else if (language == 3) {
                    eaBleDeviceLanguage.setE_type(EABleDeviceLanguage.LanguageType.chinese_traditional);
                } else if (language == 4) {
                    eaBleDeviceLanguage.setE_type(EABleDeviceLanguage.LanguageType.korean);
                } else if (language == 5) {
                    eaBleDeviceLanguage.setE_type(EABleDeviceLanguage.LanguageType.thai);
                } else if (language == 6) {
                    eaBleDeviceLanguage.setE_type(EABleDeviceLanguage.LanguageType.japanese);
                } else if (language == 7) {
                    eaBleDeviceLanguage.setE_type(EABleDeviceLanguage.LanguageType.spanish);
                } else if (language == 8) {
                    eaBleDeviceLanguage.setE_type(EABleDeviceLanguage.LanguageType.francais);
                } else if (language == 9) {
                    eaBleDeviceLanguage.setE_type(EABleDeviceLanguage.LanguageType.deutsch);
                } else if (language == 10) {
                    eaBleDeviceLanguage.setE_type(EABleDeviceLanguage.LanguageType.italiano);
                } else if (language == 11) {
                    eaBleDeviceLanguage.setE_type(EABleDeviceLanguage.LanguageType.polski);
                } else if (language == 12) {
                    eaBleDeviceLanguage.setE_type(EABleDeviceLanguage.LanguageType.portuguese);
                } else if (language == 13) {
                    eaBleDeviceLanguage.setE_type(EABleDeviceLanguage.LanguageType.russian);
                } else if (language == 14) {
                    eaBleDeviceLanguage.setE_type(EABleDeviceLanguage.LanguageType.dutch);
                } else if (language == 15) {
                    eaBleDeviceLanguage.setE_type(EABleDeviceLanguage.LanguageType.arabic);
                } else if (language == 16) {
                    eaBleDeviceLanguage.setE_type(EABleDeviceLanguage.LanguageType.greek);
                } else if (language == 17) {
                    eaBleDeviceLanguage.setE_type(EABleDeviceLanguage.LanguageType.hebrew);
                } else if (language == 18) {
                    eaBleDeviceLanguage.setE_type(EABleDeviceLanguage.LanguageType.swedish);
                } else if (language == 19) {
                    eaBleDeviceLanguage.setE_type(EABleDeviceLanguage.LanguageType.unknown);
                }
                EABleManager.getInstance().setDevLanguage(eaBleDeviceLanguage, new GeneralCallback() {
                    @Override
                    public void result(boolean b) {

                        setWatchDataResponse(0, (Integer) setWatchParam.get("type"));
                    }

                    @Override
                    public void mutualFail(int i) {
                        setWatchDataResponse(1, (Integer) setWatchParam.get("type"));
                    }
                });
            }
            break;
            case (kEADataInfoTypeUnifiedUnit): {
                Map<String, Integer> map = JSONObject.parseObject(jsonString, Map.class);
                int unit = (int) map.get("unit");
                EABleDevUnit eaBleDevUnit = new EABleDevUnit();
                if (unit == 0) {
                    eaBleDevUnit.setE_format(UnitFormat.metric);
                } else {
                    eaBleDevUnit.setE_format(UnitFormat.british);
                }
                EABleManager.getInstance().setUnifiedUnit(eaBleDevUnit, new GeneralCallback() {
                    @Override
                    public void result(boolean b) {

                        setWatchDataResponse(0, (Integer) setWatchParam.get("type"));
                    }

                    @Override
                    public void mutualFail(int i) {

                        setWatchDataResponse(1, (Integer) setWatchParam.get("type"));
                    }
                });
            }
            break;
            case (kEADataInfoTypeNotDisturb): {

                Map<String, Integer> map = JSONObject.parseObject(jsonString, Map.class);
                EABleNotDisturb eaBleNotDisturb = new EABleNotDisturb();
                eaBleNotDisturb.setSw(map.get("sw"));
                eaBleNotDisturb.setBegin_hour(map.get("beginHour"));
                eaBleNotDisturb.setBegin_minute(map.get("beginMinute"));
                eaBleNotDisturb.setEnd_hour(map.get("endHour"));
                eaBleNotDisturb.setEnd_minute(map.get("endMinute"));
                EABleManager.getInstance().setNotDisturb(eaBleNotDisturb, new GeneralCallback() {
                    @Override
                    public void result(boolean b) {
                        setWatchDataResponse(0, (Integer) setWatchParam.get("type"));
                    }

                    @Override
                    public void mutualFail(int i) {
                        setWatchDataResponse(1, (Integer) setWatchParam.get("type"));
                    }
                });

            }
            break;
            case (kEADataInfoTypeDailyGoal): {
                Map<String, JSONObject> dailyGoal = JSONObject.parseObject(jsonString, Map.class);
                JSONObject calorie = dailyGoal.get("sCalorie");
                JSONObject step = dailyGoal.get("sStep");
                JSONObject distance = dailyGoal.get("sDistance");
                JSONObject duration = dailyGoal.get("sDuration");
                JSONObject sleep = dailyGoal.get("sSleep");
                EABleDailyGoal eaBleDailyData = new EABleDailyGoal();
                if (sleep != null) {
                    EABleDailyGoal.EABleDaily eaBleDaily = new EABleDailyGoal.EABleDaily();
                    eaBleDaily.setGoal(sleep.getInteger("goal"));
                    eaBleDaily.setSw(sleep.getInteger("sw"));
                    eaBleDailyData.setS_sleep(eaBleDaily);
                }
                if (duration != null) {
                    EABleDailyGoal.EABleDaily eaBleDaily = new EABleDailyGoal.EABleDaily();
                    eaBleDaily.setGoal(duration.getInteger("goal"));
                    eaBleDaily.setSw(duration.getInteger("sw"));
                    eaBleDailyData.setS_duration(eaBleDaily);
                }
                if (distance != null) {
                    EABleDailyGoal.EABleDaily eaBleDaily = new EABleDailyGoal.EABleDaily();
                    eaBleDaily.setGoal(distance.getInteger("goal"));
                    eaBleDaily.setSw(distance.getInteger("sw"));
                    eaBleDailyData.setS_distance(eaBleDaily);
                }
                if (step != null) {
                    EABleDailyGoal.EABleDaily eaBleDaily = new EABleDailyGoal.EABleDaily();
                    eaBleDaily.setGoal(step.getInteger("goal"));
                    eaBleDaily.setSw(step.getInteger("sw"));
                    eaBleDailyData.setS_step(eaBleDaily);
                }
                if (calorie != null) {
                    EABleDailyGoal.EABleDaily eaBleDaily = new EABleDailyGoal.EABleDaily();
                    eaBleDaily.setGoal(calorie.getInteger("goal"));
                    eaBleDaily.setSw(calorie.getInteger("sw"));
                    eaBleDailyData.setS_calorie(eaBleDaily);
                }
                EABleManager.getInstance().setDailyGoal(eaBleDailyData, new GeneralCallback() {
                    @Override
                    public void result(boolean b) {
                        setWatchDataResponse(0, (Integer) setWatchParam.get("type"));
                    }

                    @Override
                    public void mutualFail(int i) {
                        setWatchDataResponse(1, (Integer) setWatchParam.get("type"));
                    }
                });
            }
            break;
            case (kEADataInfoTypeAutoCheckHeartRate): {
                Map<String, Integer> map = JSONObject.parseObject(jsonString, Map.class);
                int intervalTime = (int) map.get("interval");
                EABleManager.getInstance().setHeartRateIntervalTime(intervalTime, new GeneralCallback() {
                    @Override
                    public void result(boolean b) {
                        setWatchDataResponse(0, (Integer) setWatchParam.get("type"));
                    }

                    @Override
                    public void mutualFail(int i) {
                        setWatchDataResponse(1, (Integer) setWatchParam.get("type"));
                    }
                });
            }
            break;
            case (kEADataInfoTypeAutoCheckSedentariness): {
                Map<String, Integer> map = JSONObject.parseObject(jsonString, Map.class);
                EABleSedentariness eaBleSedentariness = new EABleSedentariness();
                eaBleSedentariness.setBegin_hour(map.get("beginHour"));
                eaBleSedentariness.setBegin_minute(map.get("beginMinute"));
                eaBleSedentariness.setEnd_hour(map.get("endHour"));
                eaBleSedentariness.setEnd_minute(map.get("endMinute"));
                eaBleSedentariness.setInterval(map.get("interval"));
                eaBleSedentariness.setStep_threshold(map.get("stepThreshold"));
                eaBleSedentariness.setWeek_cycle_bit(map.get("weekCycleBit"));
                EABleManager.getInstance().setSitCheck(eaBleSedentariness, new GeneralCallback() {
                    @Override
                    public void result(boolean b) {
                        setWatchDataResponse(0, (Integer) setWatchParam.get("type"));
                    }

                    @Override
                    public void mutualFail(int i) {
                        setWatchDataResponse(1, (Integer) setWatchParam.get("type"));
                    }
                });

            }
            break;
            case (kEADataInfoTypeWeather): {

                Map<String, Object> map = JSONObject.parseObject(jsonString, Map.class);
                EABleWeather eaBleWeather = new EABleWeather();
                int cTemperature = (int) map.get("currentTemperature");
                eaBleWeather.setCurrent_temperature(cTemperature);
                eaBleWeather.setPlace((String) map.get("place"));
                int temperatureUnit = (int) map.get("weatherUnit");
                eaBleWeather.setTemperatureUnit(temperatureUnit == 0 ? EABleWeather.TemperatureUnit.centigrade : EABleWeather.TemperatureUnit.fahrenheit);
                List<JSONObject> wArray = (List<JSONObject>) map.get("sDayArray");
                if (wArray != null && !wArray.isEmpty()) {
                    List<EABleWeather.EABleWeatherItem> itemList = new ArrayList<>();
                    eaBleWeather.setS_day(itemList);
                    for (int i = 0; i < wArray.size(); i++) {
                        JSONObject wMap = wArray.get(i);
                        EABleWeather.EABleWeatherItem eaBleWeatherItem = new EABleWeather.EABleWeatherItem();
                        eaBleWeatherItem.setAir_humidity((Integer) wMap.getInteger("airGrade"));
                        eaBleWeatherItem.setCloudiness((Integer) wMap.getInteger("cloudiness"));
                        eaBleWeatherItem.setMax_temperature((Integer) wMap.getInteger("maxTemperature"));
                        eaBleWeatherItem.setMax_wind_power((Integer) wMap.getInteger("maxWindPower"));
                        eaBleWeatherItem.setMin_temperature((Integer) wMap.getInteger("minTemperature"));
                        eaBleWeatherItem.setMin_wind_power((Integer) wMap.getInteger("minWindPower"));
                        int riseTime = wMap.getInteger("sunriseTimestamp");
                        int setTime = wMap.getInteger("sunsetTimestamp");
                        eaBleWeatherItem.setSunrise_timestamp(riseTime);
                        eaBleWeatherItem.setSunset_timestamp(setTime);
                        int dayType = (int) wMap.get("eDayType");
                        if (dayType == 0) {
                            eaBleWeatherItem.setE_day_type(EABleWeather.WeatherType.clear);
                        } else if (dayType == 1) {
                            eaBleWeatherItem.setE_day_type(EABleWeather.WeatherType.cloudy);
                        } else if (dayType == 2) {
                            eaBleWeatherItem.setE_day_type(EABleWeather.WeatherType.gloomy);
                        } else if (dayType == 3) {
                            eaBleWeatherItem.setE_day_type(EABleWeather.WeatherType.drizzle);
                        } else if (dayType == 4) {
                            eaBleWeatherItem.setE_day_type(EABleWeather.WeatherType.moderate_rain);
                        } else if (dayType == 5) {
                            eaBleWeatherItem.setE_day_type(EABleWeather.WeatherType.thunderstorm);
                        } else if (dayType == 6) {
                            eaBleWeatherItem.setE_day_type(EABleWeather.WeatherType.heavy_rain);
                        } else if (dayType == 7) {
                            eaBleWeatherItem.setE_day_type(EABleWeather.WeatherType.sleet);
                        } else if (dayType == 8) {
                            eaBleWeatherItem.setE_day_type(EABleWeather.WeatherType.light_snow);
                        } else if (dayType == 9) {
                            eaBleWeatherItem.setE_day_type(EABleWeather.WeatherType.moderate_snow);
                        } else if (dayType == 10) {
                            eaBleWeatherItem.setE_day_type(EABleWeather.WeatherType.heavy_snow);
                        }
                        int nightType = (int) wMap.get("eNightType");
                        if (nightType == 0) {
                            eaBleWeatherItem.setE_night_type(EABleWeather.WeatherType.clear);
                        } else if (nightType == 1) {
                            eaBleWeatherItem.setE_night_type(EABleWeather.WeatherType.cloudy);
                        } else if (nightType == 2) {
                            eaBleWeatherItem.setE_night_type(EABleWeather.WeatherType.gloomy);
                        } else if (nightType == 3) {
                            eaBleWeatherItem.setE_night_type(EABleWeather.WeatherType.drizzle);
                        } else if (nightType == 4) {
                            eaBleWeatherItem.setE_night_type(EABleWeather.WeatherType.moderate_rain);
                        } else if (nightType == 5) {
                            eaBleWeatherItem.setE_night_type(EABleWeather.WeatherType.thunderstorm);
                        } else if (nightType == 6) {
                            eaBleWeatherItem.setE_night_type(EABleWeather.WeatherType.heavy_rain);
                        } else if (nightType == 7) {
                            eaBleWeatherItem.setE_night_type(EABleWeather.WeatherType.sleet);
                        } else if (nightType == 8) {
                            eaBleWeatherItem.setE_night_type(EABleWeather.WeatherType.light_snow);
                        } else if (nightType == 9) {
                            eaBleWeatherItem.setE_night_type(EABleWeather.WeatherType.moderate_snow);
                        } else if (nightType == 10) {
                            eaBleWeatherItem.setE_night_type(EABleWeather.WeatherType.heavy_snow);
                        }
                        int moon = (int) wMap.get("eMoon");
                        if (moon == 0) {
                            eaBleWeatherItem.setE_moon(EABleWeather.Moon.new_moon);
                        } else if (moon == 1) {
                            eaBleWeatherItem.setE_moon(EABleWeather.Moon.waxing_crescent_moon);
                        } else if (moon == 2) {
                            eaBleWeatherItem.setE_moon(EABleWeather.Moon.quarter_moon);
                        } else if (moon == 3) {
                            eaBleWeatherItem.setE_moon(EABleWeather.Moon.half_moon_1);
                        } else if (moon == 4) {
                            eaBleWeatherItem.setE_moon(EABleWeather.Moon.waxing_gibbous_moon);
                        } else if (moon == 5) {
                            eaBleWeatherItem.setE_moon(EABleWeather.Moon.full_moon);
                        } else if (moon == 6) {
                            eaBleWeatherItem.setE_moon(EABleWeather.Moon.waning_gibbous_moon);
                        } else if (moon == 7) {
                            eaBleWeatherItem.setE_moon(EABleWeather.Moon.half_moon_2);
                        } else if (moon == 8) {
                            eaBleWeatherItem.setE_moon(EABleWeather.Moon.last_quarter_moon);
                        } else if (moon == 9) {
                            eaBleWeatherItem.setE_moon(EABleWeather.Moon.waning_crescent_moon);
                        }
                        int ray = (int) wMap.get("eRays");
                        if (ray == 0) {
                            eaBleWeatherItem.setE_rays(EABleWeather.RaysLevel.weak);
                        } else if (ray == 1) {
                            eaBleWeatherItem.setE_rays(EABleWeather.RaysLevel.medium);
                        } else if (ray == 2) {
                            eaBleWeatherItem.setE_rays(EABleWeather.RaysLevel.strong);
                        } else if (ray == 3) {
                            eaBleWeatherItem.setE_rays(EABleWeather.RaysLevel.very_strong);
                        } else if (ray == 4) {
                            eaBleWeatherItem.setE_rays(EABleWeather.RaysLevel.super_strong);
                        }
                        int air = (int) wMap.get("eAir");
                        if (air == 0) {
                            eaBleWeatherItem.setE_air(EABleWeather.AirQuality.excellent);
                        } else if (air == 1) {
                            eaBleWeatherItem.setE_air(EABleWeather.AirQuality.good);
                        } else {
                            eaBleWeatherItem.setE_air(EABleWeather.AirQuality.bad);
                        }
                        itemList.add(eaBleWeatherItem);
                    }
                }
                EABleManager.getInstance().setWeather(eaBleWeather, new GeneralCallback() {
                    @Override
                    public void result(boolean b) {
                        setWatchDataResponse(0, (Integer) setWatchParam.get("type"));
                    }

                    @Override
                    public void mutualFail(int i) {
                        setWatchDataResponse(1, (Integer) setWatchParam.get("type"));
                    }
                });
            }
            break;
            case (kEADataInfoTypeReminder): {
                Map<String, Object> map = JSONObject.parseObject(jsonString, Map.class);
                EABleReminder eaBleReminder = new EABleReminder();
                eaBleReminder.setId((Integer) map.get("id_p"));
                int eOps = (int) map.get("eOps");
                if (eOps == 0) {
                    eaBleReminder.setE_ops(EABleReminder.ReminderOps.add);
                } else if (eOps == 1) {
                    eaBleReminder.setE_ops(EABleReminder.ReminderOps.edit);
                } else if (eOps == 2) {
                    eaBleReminder.setE_ops(EABleReminder.ReminderOps.del);
                } else if (eOps == 3) {
                    eaBleReminder.setE_ops(EABleReminder.ReminderOps.del_remind);
                } else if (eOps == 4) {
                    eaBleReminder.setE_ops(EABleReminder.ReminderOps.del_alarm);
                } else if (eOps == 5) {
                    eaBleReminder.setE_ops(EABleReminder.ReminderOps.del_remind_alarm);
                } else {
                    eaBleReminder.setE_ops(EABleReminder.ReminderOps.unknown);
                }
                List<JSONObject> wArray = (List<JSONObject>) map.get("sIndexArray");
                if (wArray != null && !wArray.isEmpty()) {
                    List<EABleReminder.EABleReminderItem> itemList = new ArrayList<>();
                    eaBleReminder.setS_index(itemList);
                    for (int i = 0; i < wArray.size(); i++) {
                        JSONObject wMap = wArray.get(i);
                        EABleReminder.EABleReminderItem reminderItem = new EABleReminder.EABleReminderItem();
                        reminderItem.setContent((String) wMap.get("content"));
                        reminderItem.setDay((Integer) wMap.get("day"));
                        reminderItem.setHour((Integer) wMap.get("hour"));
                        reminderItem.setId((Integer) wMap.get("id_p"));
                        reminderItem.setMinute((Integer) wMap.get("minute"));
                        reminderItem.setMonth((Integer) wMap.get("month"));
                        reminderItem.setSec_sw((Integer) wMap.get("secSw"));
                        reminderItem.setSleep_duration((Integer) wMap.get("sleepDuration"));
                        reminderItem.setSw((Integer) wMap.get("sw"));
                        reminderItem.setWeek_cycle_bit((Integer) wMap.get("weekCycleBit"));
                        reminderItem.setYear((Integer) wMap.get("year"));
                        int remindActionType = (int) wMap.get("remindActionType");
                        if (remindActionType == 0) {
                            reminderItem.setE_action(CommonAction.no_action);
                        } else if (remindActionType == 1) {
                            reminderItem.setE_action(CommonAction.one_long_vibration);
                        } else if (remindActionType == 2) {
                            reminderItem.setE_action(CommonAction.one_short_vibration);
                        } else if (remindActionType == 3) {
                            reminderItem.setE_action(CommonAction.two_long_vibration);
                        } else if (remindActionType == 4) {
                            reminderItem.setE_action(CommonAction.two_short_vibration);
                        } else if (remindActionType == 5) {
                            reminderItem.setE_action(CommonAction.long_vibration);
                        } else if (remindActionType == 6) {
                            reminderItem.setE_action(CommonAction.long_short_vibration);
                        } else if (remindActionType == 7) {
                            reminderItem.setE_action(CommonAction.one_ring);
                        } else if (remindActionType == 8) {
                            reminderItem.setE_action(CommonAction.two_ring);
                        } else if (remindActionType == 9) {
                            reminderItem.setE_action(CommonAction.ring);
                        } else if (remindActionType == 10) {
                            reminderItem.setE_action(CommonAction.one_vibration_ring);
                        } else if (remindActionType == 11) {
                            reminderItem.setE_action(CommonAction.vibration_ring);
                        }
                        int reminderEventType = (int) wMap.get("reminderEventType");
                        if (reminderEventType == 0) {
                            reminderItem.setE_type(EABleReminder.ReminderType.alarm);
                        } else if (reminderEventType == 1) {
                            reminderItem.setE_type(EABleReminder.ReminderType.sleep);
                        } else if (reminderEventType == 2) {
                            reminderItem.setE_type(EABleReminder.ReminderType.sport);
                        } else if (reminderEventType == 3) {
                            reminderItem.setE_type(EABleReminder.ReminderType.drink);
                        } else if (reminderEventType == 4) {
                            reminderItem.setE_type(EABleReminder.ReminderType.medicine);
                        } else if (reminderEventType == 5) {
                            reminderItem.setE_type(EABleReminder.ReminderType.meeting);
                        } else if (reminderEventType == 6) {
                            reminderItem.setE_type(EABleReminder.ReminderType.user);
                        }
                        itemList.add(reminderItem);
                    }
                }
                EABleManager.getInstance().setReminderOrder(eaBleReminder, new EditAttentionCallback() {
                    @Override
                    public void editResult(final EABleRemindRespond eaBleRemindRespond) {
                        setWatchDataResponse(eaBleRemindRespond.remindRespondResult.getValue(), (Integer) setWatchParam.get("type"));
                    }

                    @Override
                    public void mutualFail(int i) {
                        setWatchDataResponse(1, (Integer) setWatchParam.get("type"));
                    }
                });
            }
            break;
            case (kEADataInfoTypeHeartRateWaringSetting): {

                Map<String, Integer> map = JSONObject.parseObject(jsonString, Map.class);
                EABleHr eaBleHr = new EABleHr();
                eaBleHr.setSw(map.get("sw"));
                eaBleHr.setMax_hr(map.get("maxHr"));
                eaBleHr.setMin_hr(map.get("minHr"));
                EABleManager.getInstance().setHeartRateLimit(eaBleHr, new GeneralCallback() {
                    @Override
                    public void result(boolean b) {
                        setWatchDataResponse(0, (Integer) setWatchParam.get("type"));
                    }

                    @Override
                    public void mutualFail(int i) {
                        setWatchDataResponse(1, (Integer) setWatchParam.get("type"));
                    }
                });
            }
            break;
            case (kEADataInfoTypeHabitTracker): {

                Map<String, Object> map = JSONObject.parseObject(jsonString, Map.class);
                EABleHabit eaBleHabit = new EABleHabit();
                eaBleHabit.setId((Integer) map.get("id_p"));

                int eOps = (int) map.get("eOps");
                if (eOps == 0) {
                    eaBleHabit.setE_ops(EABleHabit.HabitualOperation.add);
                } else if (eOps == 1) {
                    eaBleHabit.setE_ops(EABleHabit.HabitualOperation.edit);
                } else if (eOps == 2) {
                    eaBleHabit.setE_ops(EABleHabit.HabitualOperation.del);
                } else {
                    eaBleHabit.setE_ops(EABleHabit.HabitualOperation.del_all);
                }
                List<JSONObject> wArray = (List<JSONObject>) map.get("sIndexArray");
                if (wArray != null && !wArray.isEmpty()) {
                    List<EABleHabit.HabitItem> itemList = new ArrayList<>();
                    eaBleHabit.setItemList(itemList);
                    for (int i = 0; i < wArray.size(); i++) {
                        JSONObject wMap = wArray.get(i);
                        EABleHabit.HabitItem habitItem = new EABleHabit.HabitItem();
                        habitItem.setContent((String) wMap.get("content"));
                        habitItem.setBegin_hour((Integer) wMap.get("beginHour"));
                        habitItem.setBegin_minute((Integer) wMap.get("beginMinute"));
                        habitItem.setEnd_hour((Integer) wMap.get("endHour"));
                        habitItem.setEnd_minute((Integer) wMap.get("endMinute"));
                        habitItem.setBlueColor((Integer) wMap.get("b"));
                        habitItem.setGreenColor((Integer) wMap.get("g"));
                        habitItem.setRedColor((Integer) wMap.get("r"));
                        habitItem.setDuration((Integer) wMap.get("duration"));
                        habitItem.setId((Integer) wMap.get("id_p"));
                        int remindActionType = (int) wMap.get("eAction");
                        if (remindActionType == 0) {
                            habitItem.setE_action(CommonAction.no_action);
                        } else if (remindActionType == 1) {
                            habitItem.setE_action(CommonAction.one_long_vibration);
                        } else if (remindActionType == 2) {
                            habitItem.setE_action(CommonAction.one_short_vibration);
                        } else if (remindActionType == 3) {
                            habitItem.setE_action(CommonAction.two_long_vibration);
                        } else if (remindActionType == 4) {
                            habitItem.setE_action(CommonAction.two_short_vibration);
                        } else if (remindActionType == 5) {
                            habitItem.setE_action(CommonAction.long_vibration);
                        } else if (remindActionType == 6) {
                            habitItem.setE_action(CommonAction.long_short_vibration);
                        } else if (remindActionType == 7) {
                            habitItem.setE_action(CommonAction.one_ring);
                        } else if (remindActionType == 8) {
                            habitItem.setE_action(CommonAction.two_ring);
                        } else if (remindActionType == 9) {
                            habitItem.setE_action(CommonAction.ring);
                        } else if (remindActionType == 10) {
                            habitItem.setE_action(CommonAction.one_vibration_ring);
                        } else if (remindActionType == 11) {
                            habitItem.setE_action(CommonAction.vibration_ring);
                        }
                        int reminderEventType = (int) wMap.get("eIconId");
                        if (reminderEventType == 0) {
                            habitItem.setE_icon_id(HabitIcon.study_01);
                        } else if (reminderEventType == 1) {
                            habitItem.setE_icon_id(HabitIcon.sleep_02);
                        } else if (reminderEventType == 2) {
                            habitItem.setE_icon_id(HabitIcon.study_03);
                        } else if (reminderEventType == 3) {
                            habitItem.setE_icon_id(HabitIcon.chores_04);
                        } else if (reminderEventType == 4) {
                            habitItem.setE_icon_id(HabitIcon.havefun_05);
                        } else if (reminderEventType == 5) {
                            habitItem.setE_icon_id(HabitIcon.drink_06);
                        } else if (reminderEventType == 6) {
                            habitItem.setE_icon_id(HabitIcon.sun_07);
                        } else if (reminderEventType == 7) {
                            habitItem.setE_icon_id(HabitIcon.teeth_08);
                        } else if (reminderEventType == 8) {
                            habitItem.setE_icon_id(HabitIcon.calendar_09);
                        } else if (reminderEventType == 9) {
                            habitItem.setE_icon_id(HabitIcon.piano_10);
                        } else if (reminderEventType == 10) {
                            habitItem.setE_icon_id(HabitIcon.fruit_11);
                        } else if (reminderEventType == 11) {
                            habitItem.setE_icon_id(HabitIcon.medicine_12);
                        } else if (reminderEventType == 12) {
                            habitItem.setE_icon_id(HabitIcon.draw_13);
                        } else if (reminderEventType == 13) {
                            habitItem.setE_icon_id(HabitIcon.target_14);
                        } else if (reminderEventType == 14) {
                            habitItem.setE_icon_id(HabitIcon.dog_15);
                        } else if (reminderEventType == 15) {
                            habitItem.setE_icon_id(HabitIcon.exercise_16);
                        } else if (reminderEventType == 16) {
                            habitItem.setE_icon_id(HabitIcon.bed_17);
                        } else {
                            habitItem.setE_icon_id(HabitIcon.tidyup_18);
                        }
                        int flag = (int) wMap.get("eFlag");
                        if (flag == 0) {
                            habitItem.setHabitState(HabitState.initial);
                        } else if (flag == 1) {
                            habitItem.setHabitState(HabitState.in_progress);
                        } else if (flag == 2) {
                            habitItem.setHabitState(HabitState.completed);
                        } else {
                            habitItem.setHabitState(HabitState.cancel);
                        }
                        itemList.add(habitItem);
                    }
                }
                EABleManager.getInstance().setHabit(eaBleHabit, new HabitResultCallback() {
                    @Override
                    public void editResult(EABleHabitRespond eaBleHabitRespond) {
                        setWatchDataResponse(eaBleHabitRespond.result.getValue(), (Integer) setWatchParam.get("type"));
                    }

                    @Override
                    public void mutualFail(int i) {
                        setWatchDataResponse(1, (Integer) setWatchParam.get("type"));
                    }
                });
            }
            break;
            case (kEADataInfoTypeGesturesSetting): {
                Map<String, Integer> map = JSONObject.parseObject(jsonString, Map.class);
                EABleGesturesBrightScreen eaBleGesturesBrightScreen = new EABleGesturesBrightScreen();
                eaBleGesturesBrightScreen.setBegin_hour(map.get("beginHour"));
                eaBleGesturesBrightScreen.setBegin_minute(map.get("beginMinute"));
                eaBleGesturesBrightScreen.setEnd_hour(map.get("endHour"));
                eaBleGesturesBrightScreen.setEnd_minute(map.get("endMinute"));
                int bType = map.get("eBrightSrc");
                if (bType == 0) {
                    eaBleGesturesBrightScreen.setBrightScreenSwitch(EABleGesturesBrightScreen.BrightScreenSwitch.close);
                } else if (bType == 1) {
                    eaBleGesturesBrightScreen.setBrightScreenSwitch(EABleGesturesBrightScreen.BrightScreenSwitch.all_day);
                } else {
                    eaBleGesturesBrightScreen.setBrightScreenSwitch(EABleGesturesBrightScreen.BrightScreenSwitch.select_time);
                }
                EABleManager.getInstance().setGesturesSwitch(eaBleGesturesBrightScreen, new GeneralCallback() {
                    @Override
                    public void result(boolean b) {
                        setWatchDataResponse(0, (Integer) setWatchParam.get("type"));
                    }

                    @Override
                    public void mutualFail(int i) {
                        setWatchDataResponse(1, (Integer) setWatchParam.get("type"));
                    }
                });

            }
            break;
            case (kEADataInfoTypeHomePage): {

                Map<String, Object> map = JSONObject.parseObject(jsonString, Map.class);
                List<JSONObject> wArray = (List<JSONObject>) map.get("sPageArray");
                EABleMenuPage homePage = new EABleMenuPage();
                List<EABleMenuPage.MenuType> typeList = new ArrayList<>();
                if (wArray != null && !wArray.isEmpty()) {
                    for (int i = 0; i < wArray.size(); i++) {
                        JSONObject wMap = wArray.get(i);
                        int menu = wMap.getInteger("eType");
                        if (menu == 0) {
                            typeList.add(EABleMenuPage.MenuType.page_null);
                        } else if (menu == 1) {
                            typeList.add(EABleMenuPage.MenuType.page_heart_rate);
                        } else if (menu == 2) {
                            typeList.add(EABleMenuPage.MenuType.page_pressure);
                        } else if (menu == 3) {
                            typeList.add(EABleMenuPage.MenuType.page_weather);
                        } else if (menu == 4) {
                            typeList.add(EABleMenuPage.MenuType.page_music);
                        } else if (menu == 5) {
                            typeList.add(EABleMenuPage.MenuType.page_breath);
                        } else if (menu == 6) {
                            typeList.add(EABleMenuPage.MenuType.page_sleep);
                        } else if (menu == 7) {
                            typeList.add(EABleMenuPage.MenuType.page_menstrual_cycle);
                        }
                    }
                }


                if (typeList.isEmpty()) {
                    typeList.add(EABleMenuPage.MenuType.page_null);
                }
                homePage.setTypeList(typeList);
                EABleManager.getInstance().setMenuPage(homePage, new GeneralCallback() {
                    @Override
                    public void result(boolean b) {
                        setWatchDataResponse(0, (Integer) setWatchParam.get("type"));
                    }

                    @Override
                    public void mutualFail(int i) {
                        setWatchDataResponse(1, (Integer) setWatchParam.get("type"));
                    }
                });
            }
            break;
            case (kEADataInfoTypeMenstrual): {

                Map<String, Object> map = JSONObject.parseObject(jsonString, Map.class);
                Period period = new Period();
                period.startDate = (String) map.get("startDate");
                period.cycleDay = (int) map.get("cycleDay");
                period.keepDay = (int) map.get("keepDay");
                EABlePeriod eaBlePeriod = period.getPeriodDate();
                EABleManager.getInstance().setMenstrualCycle(eaBlePeriod, new GeneralCallback() {
                    @Override
                    public void result(boolean b) {
                        // setWatchDataResponse(0,setWatchParam.type);
                    }

                    @Override
                    public void mutualFail(int i) {
                        Log.e(TAG, "cuowuma:" + i);
                        // setWatchDataResponse(1,setWatchParam.type);
                    }
                });

            }
            break;
            case (kEADataInfoTypeWatchFace): {
                Map<String, Integer> map = JSONObject.parseObject(jsonString, Map.class);
                int builtInId = (int) map.get("id_p");
                EABleWatchFace eaBleWatchFace = new EABleWatchFace();
                eaBleWatchFace.id = builtInId;
                EABleManager.getInstance().setWatchFace(eaBleWatchFace, new GeneralCallback() {
                    @Override
                    public void result(boolean b) {
                        setWatchDataResponse(0, (Integer) setWatchParam.get("type"));
                    }

                    @Override
                    public void mutualFail(int i) {
                        setWatchDataResponse(1, (Integer) setWatchParam.get("type"));
                    }
                });
            }
            break;
            case (kEADataInfoTypeAppMessage): {

                Map<String, JSONObject> map = JSONObject.parseObject(jsonString, Map.class);
                List<JSONObject> wArray = (List<JSONObject>) map.get("sIndexArray");
                EABleInfoPush eaBleInfoPush = new EABleInfoPush();
                if (wArray != null && !wArray.isEmpty()) {
                    List<EABleInfoPush.EABlePushSwitch> switchList = new ArrayList<>();
                    eaBleInfoPush.setS_app_sw(switchList);
                    for (int i = 0; i < wArray.size(); i++) {
                        JSONObject wMap = wArray.get(i);
                        EABleInfoPush.EABlePushSwitch pushSwitch = new EABleInfoPush.EABlePushSwitch();
                        pushSwitch.setSw(wMap.getBoolean("sw") ? 1 : 0);
                        switchList.add(pushSwitch);
                    }
                }
                EABleManager.getInstance().setAppPushSwitch(eaBleInfoPush, new GeneralCallback() {
                    @Override
                    public void result(boolean b) {
                        setWatchDataResponse(0, (Integer) setWatchParam.get("type"));
                    }

                    @Override
                    public void mutualFail(int i) {
                        setWatchDataResponse(1, (Integer) setWatchParam.get("type"));
                    }
                });

            }
            break;
//            case (kEADataInfoTypeLanguage):{}break;

            default: {
                if (mHandler != null) {
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            channel.invokeMethod(kArgumentsError, "argument error");
                        }
                    });
                }
            }
            break;
        }
    }

    private void setWatchDataResponse(int respondCodeType, int type) {
        if (mHandler != null) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("dataType", type);
                    jsonObject.put("respondCodeType", respondCodeType);
                    channel.invokeMethod(kSetWatchResponse, jsonObject.toJSONString());
                }
            });
        }
    }

    private void sendWatchData(Object model, int type) {
        String jsonString = JSON.toJSONString(model);
        Map map = JSON.parseObject(jsonString);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("dataType", type);
        jsonObject.put("value", map);
        channel.invokeMethod(kGetWatchResponse, jsonObject.toJSONString());
    }

    private void sendWatchDataWithOtherKeyValue(String key, int value, int type) {
        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put(key, value);
        String jsonString = jsonObject1.toJSONString();
        Map map = JSON.parseObject(jsonString);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("dataType", type);
        jsonObject.put("value", map);
        channel.invokeMethod(kGetWatchResponse, jsonObject.toJSONString());
    }

    private void sendWatchDataWithObjectMap(Object model, Map mapValue, int type) {
        String jsonString = JSON.toJSONString(model);
        Map map = JSON.parseObject(jsonString);
        map.putAll(mapValue);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("dataType", type);
        jsonObject.put("value", map);
        System.out.println("打印" + jsonObject.toJSONString());
        channel.invokeMethod(kGetWatchResponse, jsonObject.toJSONString());
    }

    private void sendWatchDataWithMap(Map mapValue, int type) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("dataType", type);
        jsonObject.put("value", mapValue);
        String jsonString = jsonObject.toJSONString();
        channel.invokeMethod(kGetWatchResponse, jsonString);
    }
    ///addKeyValuesToJsonObjectWithJsonObjectKey
    private void addKeyValues(String key1, Object value1, String key2, Object value2, JSONObject jsonObject, String jsonObjectKey) {
        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put(key1, value1);
        jsonObject1.put(key2, value2);
        jsonObject.put(jsonObjectKey, jsonObject1.getInnerMap());
    }

    private boolean checkArgumentName(String argumentName, String arguments) {

        if (!arguments.contains(argumentName)) {
            if (mHandler != null) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        channel.invokeMethod(kArgumentsError, argumentName + " error");
                    }
                });
            }
            return false;
        }
        return true;
    }

    private void sendBigWatchData(JSONObject jsonObject) {
        if (mHandler != null) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    int flag = jsonObject.getInteger("flag");
                    int dataType = jsonObject.getInteger("dataType");
                    bigDataRespond(dataType, flag);
                    channel.invokeMethod(kGetBigWatchData, jsonObject.toJSONString());
                }
            });
        }
    }

    private void sendOpePhone(JSONObject jsonObject) {
        if (mHandler != null) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    channel.invokeMethod(kOperationPhone, jsonObject.toJSONString());
                }
            });
        }
    }

    private void otaAction(List otas) {

        // if (mHandler != null) {
        //     mHandler.post(new Runnable() {
        //         @Override
        //         public void run() {

        List<EABleOta> otaList = new ArrayList<>();
        for (int i = 0; i < otas.size(); i++) {

            TempOtaData tempOtaData = (TempOtaData) otas.get(i);
            EABleOta eaBleOta = new EABleOta();
            File file = new File(tempOtaData.binPath);
            if (!file.exists() || file.isDirectory()) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {

                        channel.invokeMethod(kArgumentsError, "not find file in binPath");
                    }
                });
                return;
            }
            try {
                eaBleOta.fileByte = file2Byte(file);
                eaBleOta.version = tempOtaData.version;
                if (tempOtaData.firmwareType == 0) {
                    eaBleOta.otaType = EABleOta.OtaType.apollo;
                } else if (tempOtaData.firmwareType == 1) {
                    eaBleOta.otaType = EABleOta.OtaType.res;
                } else if (tempOtaData.firmwareType == 2) {
                    eaBleOta.otaType = EABleOta.OtaType.hr;
                } else if (tempOtaData.firmwareType == 3) {
                    eaBleOta.otaType = EABleOta.OtaType.tp;
                }
                otaList.add(eaBleOta);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        channel.invokeMethod(kArgumentsError, "binpath file error");
                    }
                });
                return;
            }
        }
        if (otaList != null && !otaList.isEmpty()) {
            EABleManager.getInstance().otaUpdate(otaList, new OtaCallback() {
                @Override
                public void success() {
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            channel.invokeMethod(kProgress, 100);
                        }
                    });

                }

                @Override
                public void progress(int i) {
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            channel.invokeMethod(kProgress, i);
                        }
                    });

                }

                @Override
                public void mutualFail(int i) {
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            channel.invokeMethod(kProgress, -1);
                        }
                    });

                }
            });
        }
        //  }

        // });
        //}

    }

    private byte[] file2Byte(@NonNull File f) throws FileNotFoundException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        InputStream inputStream = new FileInputStream(f);
        byte[] buffer = new byte[1024 * 4];
        int n = 0;
        try {
            while (-1 != (n = inputStream.read(buffer))) {
                output.write(buffer, 0, n);
            }
            return output.toByteArray();
        } catch (IOException e) {

        }
        return null;
    }

    private void bigDataRespond(int request_id, int e_common_flag) {

        EABleGeneralSportRespond eaBleGeneralSportRespond = new EABleGeneralSportRespond();
        eaBleGeneralSportRespond.setRequest_id(request_id);
        if (e_common_flag == 0) {
            eaBleGeneralSportRespond.setE_common_flag(CommonFlag.begin);
        } else if (e_common_flag == 1) {
            eaBleGeneralSportRespond.setE_common_flag(CommonFlag.proceed);
        } else if (e_common_flag == 2) {
            eaBleGeneralSportRespond.setE_common_flag(CommonFlag.end);
        } else if (e_common_flag == 3) {
            eaBleGeneralSportRespond.setE_common_flag(CommonFlag.begin_end);
        }
        EABleManager.getInstance().motionDataResponse(eaBleGeneralSportRespond, new MotionDataResponseCallback() {
            @Override
            public void mutualSuccess() {
                if (mHandler != null) {
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {

                        }
                    });
                }
            }

            @Override
            public void mutualFail(int i) {
                if (mHandler != null) {
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {

                        }
                    });
                }
            }
        });
    }
}


