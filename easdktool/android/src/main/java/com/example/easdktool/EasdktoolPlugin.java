package com.example.easdktool;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothA2dp;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothHeadset;
import android.content.Context;


import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;

import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.alibaba.fastjson.JSONObject;

import com.apex.bluetooth.callback.WatchInfoCallback;
import com.apex.bluetooth.core.EABleManager;

import com.apex.bluetooth.enumeration.EABleConnectState;

import com.apex.bluetooth.enumeration.QueryWatchInfoType;
import com.apex.bluetooth.listener.EABleScanListener;

import com.apex.bluetooth.model.EABleDevice;

import com.apex.bluetooth.model.EABleOta;


import com.apex.bluetooth.model.EABleWatchInfo;
import com.apex.bluetooth.utils.LogUtils;

import com.example.easdktool.been.BtConnect;
import com.example.easdktool.been.CustomWatchFace;
import com.example.easdktool.been.DBParam;
import com.example.easdktool.been.DeleteData;
import com.example.easdktool.been.LogParam;

import com.example.easdktool.been.QueryDb;
import com.example.easdktool.broadcast.BtConnectBroadcast;
import com.example.easdktool.broadcast.CallReceiveBroadcast;
import com.example.easdktool.broadcast.SMSReceiveBroadcast;

import com.example.easdktool.callback.ConnectStateListener;
import com.example.easdktool.callback.DeviceOperationListener;
import com.example.easdktool.callback.MotionDataListener;
import com.example.easdktool.callback.PrewMapCallBack;
import com.example.easdktool.callback.WatchFileCallback;
import com.example.easdktool.db.DataManager;
import com.example.easdktool.enumerate.ConnectState;


import java.io.ByteArrayOutputStream;
import java.io.File;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import java.util.List;
import java.util.Map;
import java.util.Set;

import io.flutter.Log;
import io.flutter.embedding.engine.FlutterEngine;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.EventChannel;
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
    final String saveData = "saveData";

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
    final String queryData = "queryData";
    final String deleteData = "deleteData";
    final String pairBT = "connectClassicBluetooth";
    final String kEACustomWatchface = "EACustomWatchface"; // 自定义表盘及OTA

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
    final int kEADataInfoTypeContact = 42;
    final int kEADataInfoTypeSleepSpo2 = 50;
    final int kEADataInfoTypeStress = 51;
    final int kEADataInfoTypeVibrate = 53;
    final int kEADataInfoTypePeriodReminder = 55;

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
    BtConnectBroadcast btConnectBroadcast;
    EventChannel eventChannel;

    public EasdktoolPlugin() {
        LogUtils.e(TAG, "创建plugin");
    }

    @Override
    public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
        channel.setMethodCallHandler(null);
        LogUtils.e(TAG, "End of page");
        if (eventChannel != null) {
            eventChannel.setStreamHandler(null);
        }
        if (btConnectBroadcast != null && mContext != null) {
            mContext.unregisterReceiver(btConnectBroadcast);
            btConnectBroadcast = null;
        }
        //  destroyIncomingCall();
        //  destroySmsListener();
    }


    @Override
    public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
        LogUtils.e(TAG, "The page starts");
        flutterEngine = flutterPluginBinding.getFlutterEngine();
        channel = new MethodChannel(flutterPluginBinding.getBinaryMessenger(), "easdktool");
        channel.setMethodCallHandler(this);
        if (mContext == null) {
            mContext = flutterPluginBinding.getApplicationContext();
        }
        eventChannel = new EventChannel(flutterPluginBinding.getBinaryMessenger(), "broadReceiver");
        DataManager.getInstance().initDB(mContext);

        eventChannel.setStreamHandler(new EventChannel.StreamHandler() {
            @Override
            public void onListen(Object arguments, EventChannel.EventSink events) {
                if (btConnectBroadcast == null) {
                    btConnectBroadcast = new BtConnectBroadcast();
                    IntentFilter intentFilter = new IntentFilter();
                    intentFilter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
                    intentFilter.addAction(BluetoothA2dp.ACTION_CONNECTION_STATE_CHANGED);
                    intentFilter.addAction(BluetoothHeadset.ACTION_CONNECTION_STATE_CHANGED);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        mContext.registerReceiver(btConnectBroadcast, intentFilter, Context.RECEIVER_EXPORTED);
                    } else {
                        mContext.registerReceiver(btConnectBroadcast, intentFilter);
                    }


                }
            }

            @Override
            public void onCancel(Object arguments) {
                if (mContext != null && btConnectBroadcast != null) {
                    mContext.unregisterReceiver(btConnectBroadcast);
                    btConnectBroadcast = null;
                }

            }
        });


    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {

        if (call.method.equals(kEALog)) {//是否显示log

            String arguments = (String) call.arguments;
            if (checkArgumentName("showLog", arguments)) {

                LogParam logParam = JSONObject.parseObject(arguments, LogParam.class);

                LogUtils.setShowLog(logParam.showLog);
            }
        } else if (call.method.equals(kEAGetWacthStateInfo)) {//设备连接状态

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
        } else if (call.method.equals(kEAScanWacth)) {//搜索设备

            EABleScanListener bleScanListener = new EABleScanListener() {
                @Override
                public void scanDevice(final EABleDevice eaBleDevice) {

                    //  if (mHandler != null) {
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            JSONObject jsonObject = new JSONObject();
                            jsonObject.put("name", eaBleDevice.getDeviceName());
                            jsonObject.put("connectAddress", eaBleDevice.getDeviceAddress());
                            jsonObject.put("rssi", eaBleDevice.getRssi());
                            jsonObject.put("snNumber", eaBleDevice.getDeviceSign());
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
            BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            Set<BluetoothDevice> devices = bluetoothAdapter.getBondedDevices();
            if (devices != null && devices.size() > 0) {
                for (BluetoothDevice device : devices) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("name", device.getName());
                    jsonObject.put("connectAddress", device.getAddress());
                    jsonObject.put("snNumber", null);
                    channel.invokeMethod(kScanWacthResponse, jsonObject.toJSONString());
                }
            }

        } else if (call.method.equals(kEAStopScanWacth)) {//停止搜索设备


            EABleManager.getInstance().stopScanPeripherals(mContext);
        } else if (call.method.equals(saveData)) {//是否将数据保存到数据库
            String arguments = (String) call.arguments;
            if (checkArgumentName("saveData", arguments)) {
                DBParam dbParam = JSONObject.parseObject(arguments, DBParam.class);
                LogUtils.e(TAG, "保存数据传过来的参数:" + dbParam.saveData);
                DataManager.getInstance().setIsSaveData(dbParam.saveData);

            }
        } else if (call.method.equals(queryData)) {//查询数据库保存的数据
            String arguments = (String) call.arguments;
            QueryDb queryDb = JSONObject.parseObject(arguments, QueryDb.class);
            LogUtils.e(TAG, "查询传过来的参数:" + queryDb.dataType);
            new QueryMotionData(channel, queryDb.dataType).queryData();
        } else if (call.method.equals(pairBT)) {
            String arguments = (String) call.arguments;
        //    if (checkArgumentName("btAddress", arguments)) {
        //        BtConnect btConnect = JSONObject.parseObject(arguments, BtConnect.class);
                if (!TextUtils.isEmpty(arguments) && BluetoothAdapter.checkBluetoothAddress(arguments.toUpperCase())) {
                    BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                    BluetoothDevice bluetoothDevice = bluetoothAdapter.getRemoteDevice(arguments.toUpperCase());
                    if (bluetoothDevice != null) {
                        int states = bluetoothDevice.getBondState();
                        if (btConnectBroadcast != null) {
                            btConnectBroadcast.setCurrentBlueAddress(arguments.toUpperCase());
                        }
                        LogUtils.i(TAG, "开始连接BT:"+states);
                        if (states == BluetoothDevice.BOND_NONE) {

                            //配对

                            if (!TextUtils.isEmpty(Build.PRODUCT) && (Build.PRODUCT.equalsIgnoreCase("sagit") || Build.PRODUCT.equalsIgnoreCase("starqltezc") || Build.PRODUCT.equalsIgnoreCase("MHA-AL00") || Build.PRODUCT.equalsIgnoreCase("cuscoi_g"))) {
                                LogUtils.e(TAG, "走直连配对");
                                bluetoothDevice.createBond();

                            } else {
                                try {
                                    Log.e(TAG, "非直连配对");
                                    Method bondMethod = BluetoothDevice.class.getDeclaredMethod("createBond", int.class);
                                    bondMethod.setAccessible(true);
                                    bondMethod.invoke(bluetoothDevice, 1);
                                } catch (NoSuchMethodException e) {
                                    LogUtils.e(TAG, "找不到反射的方法");
                                } catch (InvocationTargetException e) {
                                    LogUtils.e(TAG, "反射时的错误:" + e.getMessage());
                                } catch (IllegalAccessException e) {
                                    LogUtils.e(TAG, "反射时非法错误:" + e.getMessage());
                                }
                            }
                        } else if (states == BluetoothDevice.BOND_BONDING) {
                            LogUtils.i(TAG, "正在配对");
                        } else if (states == BluetoothDevice.BOND_BONDED) {
                            LogUtils.i(TAG, "已经配对");
                            new ConnectAudioUtils().connectAudio(mContext, bluetoothDevice);
                        }
                    }

/**
 if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.O) {
 Intent intent = new Intent(mContext, PairService.class);
 intent.putExtra("btAddress", btConnect.btAddress);
 intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
 intent.setPackage(mContext.getPackageName());
 mContext.startService(intent);
 } else {
 if (Build.VERSION.SDK_INT > Build.VERSION_CODES.TIRAMISU) {
 int permission = ActivityCompat.checkSelfPermission(mContext, Manifest.permission.BLUETOOTH_CONNECT);
 if (permission != PackageManager.PERMISSION_GRANTED) {
 channel.invokeMethod(kArgumentsError, "bluetooth connect permission off");
 return;
 }
 }
 Intent intent = new Intent(mContext, PairService.class);
 intent.putExtra("btAddress", btConnect.btAddress);
 intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
 intent.setPackage(mContext.getPackageName());
 mContext.startForegroundService(intent);
 return;
 }
 */

                } else {
                    channel.invokeMethod(kArgumentsError, "param error");
                    return;
                }
      //      }
        } else if (call.method.equals(kEACustomWatchface)) {//自定义表盘
            String arguments = (String) call.arguments;
            final CustomWatchFace customWatchFace = JSONObject.parseObject(arguments, CustomWatchFace.class);
            if (customWatchFace != null) {
                if (TextUtils.isEmpty(customWatchFace.bgImagePath) || !new File(customWatchFace.bgImagePath).exists()) {
                    channel.invokeMethod(kArgumentsError, "param error");
                    return;
                }
                //获取手表尺寸
                EABleManager.getInstance().queryWatchInfo(QueryWatchInfoType.watch_info, new WatchInfoCallback() {
                    @Override
                    public void watchInfo(EABleWatchInfo eaBleWatchInfo) {
                        if (eaBleWatchInfo != null) {
                            PreviewTask previewTask = new PreviewTask(eaBleWatchInfo, customWatchFace, mContext, null);
                            previewTask.setPrewMapCallBack(new PrewMapCallBack() {
                                @Override
                                public void thumbnail(final Bitmap bitmap) {
                                    if (bitmap != null) {
                                        if (customWatchFace.getPreviewImage) {
                                            new Thread() {
                                                @Override
                                                public void run() {
                                                    super.run();
                                                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                                                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                                                    byte[] bitArray = outputStream.toByteArray();
                                                    if (channel != null) {
                                                        if (bitArray == null) {
                                                            //将其转Uint8List
                                                            channel.invokeMethod(kArgumentsError, "Failed to create preview image");
                                                        } else {
                                                            channel.invokeMethod(kEACustomWatchface, bitArray);
                                                        }
                                                    }
                                                }
                                            }.start();

                                        } else {
                                            new CustomWatchFaceUtils(eaBleWatchInfo, customWatchFace, mContext, null, new WatchFileCallback() {
                                                @Override
                                                public void watchFaceFile(String filePath) {
                                                    if (TextUtils.isEmpty(filePath) || !new File(filePath).exists()) {
                                                        if (channel != null) {
                                                            channel.invokeMethod("Progress", -1);
                                                        }

                                                    } else {
                                                        EABleOta eaBleOta = new EABleOta();
                                                        eaBleOta.setPop(true);
                                                        eaBleOta.setOtaType(EABleOta.OtaType.user_wf);
                                                        eaBleOta.setFilePath(filePath);
                                                        List<EABleOta> otaList = new ArrayList<>();
                                                        otaList.add(eaBleOta);
                                                        new OTAFunction(channel).startCustomWatchFace(otaList);


                                                    }
                                                }
                                            }, bitmap).createWatchFaceFile();
                                        }
                                    } else {
                                        channel.invokeMethod(kArgumentsError, "param error");
                                        return;
                                    }


                                }
                            });
                            previewTask.execute();


                        }
                    }

                    @Override
                    public void mutualFail(int i) {
                        channel.invokeMethod(kArgumentsError, "param error");
                        return;
                    }
                });
            }
        } else if (call.method.equals(deleteData)) {//删除数据库里面的运动数据
            String arguments = (String) call.arguments;
            DeleteData deleteData = JSONObject.parseObject(arguments, DeleteData.class);
            if (deleteData.dataType == 0) {
                DataManager.getInstance().deleteDailyData(null);
            } else if (deleteData.dataType == 1) {
                DataManager.getInstance().deleteSleepData(null);
            } else if (deleteData.dataType == 2) {
                DataManager.getInstance().deleteHeartData(null);
            } else if (deleteData.dataType == 3) {
                DataManager.getInstance().deleteGpsData(null);
            } else if (deleteData.dataType == 4) {
                DataManager.getInstance().deleteMultiData(null);
            } else if (deleteData.dataType == 5) {
                DataManager.getInstance().deleteBloodData(null);
            } else if (deleteData.dataType == 6) {
                DataManager.getInstance().deleteStressData(null);
            } else if (deleteData.dataType == 7) {
                DataManager.getInstance().deleteStepFreqData(null);
            } else if (deleteData.dataType == 8) {
                DataManager.getInstance().deleteStepPaceData(null);
            } else if (deleteData.dataType == 9) {
                DataManager.getInstance().deleteRestingHeartData(null);
            } else if (deleteData.dataType == 10) {
                DataManager.getInstance().deleteHabitData(null);
            } else if (deleteData.dataType == 11) {
                DataManager.getInstance().deleteSleepScoreData(null);
            } else if (deleteData.dataType == 12) {
                DataManager.getInstance().deleteMotionHeartData(null);
            }
        } else if (call.method.equals(kEAConnectWatch)) {//连接设备
            String arguments = (String) call.arguments;
            Map<String, String> map = JSONObject.parseObject(arguments, Map.class);
            String address = map.get("connectAddress");
            if (TextUtils.isEmpty(address)) {// 判断空地址
                channel.invokeMethod(kArgumentsError, "connectAddress error");
                return;
            }

            try {
                EABleManager.getInstance().connectToPeripheral(address, mContext, new ConnectStateListener(channel), 128, new DeviceOperationListener(channel), new MotionDataListener(channel), false);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

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
                LogUtils.i(TAG, "获取指令");
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

        } else if (call.method.equals(kEAOperationWatch)) {//设备手表数据

            String arguments = (String) call.arguments;
            if (checkArgumentName("dataType", arguments)) {
                Map<String, Object> map = JSONObject.parseObject(arguments, Map.class);
                //  SetWatchParam setWatchParam = JSONObject.parseObject(arguments, SetWatchParam.class);
                final int action = (int) map.get("dataType");
                new SetWatchData(channel).operateDevice(action);

            }
        } else if (call.method.equals(kEAOTA)) {//ota
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
            case 41:
                break;
            case 43:
                new GetWatchData(channel).getContact();
                break;
            case 44:
                new GetWatchData(channel).getFunctionList();
                break;
            case 50:
                new GetWatchData(channel).getSleepSpo2Check();
                break;
            case 51:
                new GetWatchData(channel).getStressCheck();
                break;
            case 53:
                new GetWatchData(channel).getVibrateMode();
                break;
            case 55:
                new GetWatchData(channel).getPeriodReminder();
                break;
            case 57:
                new GetWatchData(channel).getMotionHeartAlert();
                break;
            case 58:
                new GetWatchData(channel).getSosContact();
                break;
            case 59:
                new GetWatchData(channel).getBtStatus();
                break;
            case 60:
                new GetWatchData(channel).getCustomReplayInfo();
                break;
            case 62:
                new GetWatchData(channel).getBtAddress();
                break;
            case 64:
                new GetWatchData(channel).getStockInfo();
                break;
            case 65:
                new GetWatchData(channel).getLockScreenPassword();
                break;
            case 66:
                break;
            case 67:
                break;
            case 68:
                break;
            case 69:
                break;
            case 70:
                break;
            case 71:
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
                LogUtils.e(TAG, "Push information");
                new SetWatchData(channel).pushInfo2Watch(jsonString);
                break;
            case (kEADataInfoTypeMonitorReminder):
                LogUtils.i(TAG, "Daily reminders");
                new SetWatchData(channel).setMonitorReminder(jsonString);
                break;
            case (kEADataInfoTypeContact):
                LogUtils.i(TAG, "telephone book");
                new SetWatchData(channel).setContacts(jsonString);
                break;
            case (kEADataInfoTypeSleepSpo2):
                LogUtils.i(TAG, "spo2 check");
                new SetWatchData(channel).setSleepSpo2Check(jsonString);
                break;
            case (kEADataInfoTypeStress):
                LogUtils.i(TAG, "stress check");
                new SetWatchData(channel).setStressCheck(jsonString);
                break;
            case (kEADataInfoTypeVibrate):
                LogUtils.i(TAG, "set vibrate");
                new SetWatchData(channel).setVibrate(jsonString);
                break;
            case (kEADataInfoTypePeriodReminder):
                LogUtils.i(TAG, "set period reminder");
                new SetWatchData(channel).setPeriodReminder(jsonString);
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


