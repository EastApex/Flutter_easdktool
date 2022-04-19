import 'dart:async';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
// import 'been.dart';
import 'EACallback.dart';
import 'dart:convert' as convert;

import 'package:easdktool/Been/EABeen.dart';

const String kEAsdktool = "easdktool"; // 连接

/// MARK: -call method Name
const String kEAConnectWatch = "EAConnectWatch"; // 连接
const String kEADisConnectWatch = "EADisConnectWatch"; // 断开
const String kEAUnbindWatch = "EAUnbindWatch"; // 解绑
const String kEAbindingWatch = "EAbindingWatch"; // 绑定
const String kEAGetWatchInfo = "EAGetWatchInfo"; // 获取手表数据
const String kEASetWatchInfo = "EASetWatchInfo"; // 设置手表信息
const String kEASyncPhoneInfoToWacth = "EASyncPhoneInfoToWacth"; // 同步手机信息到手表
const String kEAGetBigWatchData = "EAGetBigWatchData"; // 获取手表大数据
const String kEAOperationWatch = "EAOperationWatch"; // 操作手表
const String kEAOTA = "EAOTA"; // ota
const String kEALog = "EALog"; // log

/// MARK: - invoke method Name
const String kConnectState = "ConnectState";
const String kArgumentsError = "ArgumentsError";
const String kBluetoothState = "BluetoothState";
const String kSetWatchResponse = "SetWatchResponse";
const String kGetWatchResponse = "GetWatchResponse";
const String kGetBigWatchData = "GetBigWatchData";
const String kOperationPhone = "OperationPhone";
const String kProgress = "Progress";

class Easdktool {
  static const MethodChannel _channel = MethodChannel(kEAsdktool);

  Easdktool._privateConstructor();

  static final Easdktool _instance = Easdktool._privateConstructor();

  factory Easdktool() {
    return _instance;
  }

  static EAGetDataCallback? mGetDataCallback;
  static EASetDataCallback? mSetDataCallback;
  static EAGetBitDataCallback? mGetBitDataCallback;
  static OperationPhoneCallback? mOperationPhoneCallback;
  static EABleConnectListener? mEaBleConnectListener;
  static OperationWatchCallback? mOperationCallback;
  static EAOTAProgressCallback? mOTAProgressCallback;
  static void addOperationPhoneCallback(
      OperationPhoneCallback operationPhoneCallback) {
    mOperationPhoneCallback = operationPhoneCallback;
  }

  static void addBleConnectListener(EABleConnectListener eaBleConnectListener) {
    mEaBleConnectListener = eaBleConnectListener;
    _channel.setMethodCallHandler(platformCallHandler);
  }

  /// 打开SDK Log
  /// Open SDK Log
  void showLog(bool isShow) {
    _channel.invokeMethod(kEALog, {"showLog": isShow}.toString());
  }

  /// 连接手表
  /// Connect the watch
  void connectToPeripheral(EAConnectParam connectParam) {
    String param = convert.jsonEncode(connectParam);
    _channel.invokeMethod(kEAConnectWatch, param);
  }

  /// 绑定手表
  /// Binding a watch
  void bindingWatch(EABindInfo bindInfo) {
    Map map = bindInfo.toMap();
    _channel.invokeMethod(kEAbindingWatch, map.toString());
  }

  /// 解绑手表（重置手表）
  /// Untying watch (reset watch)
  void unbindWatch() {
    _channel.invokeMethod(kEAUnbindWatch);
  }

  /// 主动断连手表
  /// Disconnect the watch actively
  void disConnectWatch() {
    _channel.invokeMethod(kEADisConnectWatch);
  }

  /// 获取手表信息
  /// Obtaining watch Information
  void getWatchData(int dataType, EAGetDataCallback getDataCallback) {
    mGetDataCallback = getDataCallback;
    EAGetData dataInfoType = EAGetData();
    dataInfoType.type = dataType;
    String param = convert.jsonEncode(dataInfoType);
    _channel.invokeMethod(kEAGetWatchInfo, param);
  }

  /// 【设置手表信息（修改的类型，数据，回调）】
  /// Set watch information (modified type, data, callback)
  void setWatchData(
      int dataType, Map value, EASetDataCallback setDataCallback) {
    mSetDataCallback = setDataCallback;
    EASetData dataInfoType = EASetData();
    dataInfoType.type = dataType;
    dataInfoType.jsonString = convert.jsonEncode(value);
    String param = convert.jsonEncode(dataInfoType);
    _channel.invokeMethod(kEASetWatchInfo, param);
  }

  /// 【获取所有手表大数据（日常步数、心率、运动记录等）】
  /// Get all the big data of the watch (daily steps, heart rate and exercise records, etc.)
  void getBigWatchData(EAGetBitDataCallback getBitDataCallback) {
    mGetBitDataCallback = getBitDataCallback;
    _channel.invokeMethod(kEAGetBigWatchData);
  }

  /// 【操作手表】
  ///  operation watch  operationType see EAEnum.dart => EAOperationWatchType
  void operationWatch(EAOperationWatchType operationType,
      OperationWatchCallback operationCallback) {
    mOperationCallback = operationCallback;
    EASetData dataInfoType = EASetData();
    dataInfoType.type = operationType.index;
    String param = convert.jsonEncode(dataInfoType);
    _channel.invokeMethod(kEAOperationWatch, param);
  }

  ///【OTA 升级】
  /// OTA upgrade
  void otaUpgrade(
      EAOTAList otaList, EAOTAProgressCallback otaProgressCallback) {
    mOTAProgressCallback = otaProgressCallback;
    String param = convert.jsonEncode(otaList);
    _channel.invokeMethod(kEAOTA, param);
  }

  //
  static Future<dynamic> platformCallHandler(MethodCall methodCall) async {
    /* methodName 
     *  1.ArgumentsError    => Parameter error, please check parameter【参数错误，请检查参数】
     *  2.ConnectState      => Binding status 0: connection failed 1: connection succeeded 2: disconnected 3: connection timed out 4: no device 5:iOS needs to remove pairing【绑定状态 0:连接失败 1:连接成功 2:断开连接 3:连接超时 4:无此设备 5:iOS需要移除配对】 
     *  3.BluetoothState    => Bluetooth status 0: disabled Bluetooth 1: Enabled Bluetooth 2: unauthorized Bluetooth 3: enabled location 4: not supported BLE【蓝牙状态 0:未开启蓝牙 1:蓝牙开启 2:蓝牙未授权 3:定位未开启 4:不支持BLE 】
     *  4.SetWatchResponse  => Set the watch data response 0: failed 1: succeeded 2: unknown【设置手表数据回应 0:失败 1:成功 2:未知】
     *  5.GetWatchResponse  => Get the watch data response【获取手表数据回应】
     *  6.GetBigWatchData   => Get watch big data response【获取手表大数据回应】
     *  7.OperationPhone    => Operating mobile phone【操作手机】
     *  8.Progress         => The progress situation【进度情况】
     */

    String methodName = methodCall.method;
    switch (methodName) {
      case kArgumentsError:
        String error = methodCall.arguments;
        break;
      case kConnectState:
        int state = methodCall.arguments;
        deviceConnectState(state);
        break;
      case kBluetoothState:
        int state = methodCall.arguments;
        bluetoothState(state);
        break;
      case kSetWatchResponse:
        Map<String, dynamic> info = convert.jsonDecode(methodCall.arguments);
        EARespond respond = EARespond.fromMap(info);
        if (mSetDataCallback != null) {
          mSetDataCallback!.onRespond(respond);
        }
        break;
      case kGetWatchResponse:
        Map<String, dynamic> info = convert.jsonDecode(methodCall.arguments);
        if (mGetDataCallback != null) {
          mGetDataCallback!.onSuccess(info);
        }
        break;
      case kGetBigWatchData:
        Map<String, dynamic> info = convert.jsonDecode(methodCall.arguments);
        if (mGetBitDataCallback != null) {
          mGetBitDataCallback!.callback(info);
        }
        break;
      case kOperationPhone:
        Map<String, dynamic> info = convert.jsonDecode(methodCall.arguments);
        if (mOperationPhoneCallback != null) {
          mOperationPhoneCallback!.callback(info);
        }
        break;
      case kProgress:
        int progress = convert.jsonDecode(methodCall.arguments);
        if (mOTAProgressCallback != null) {
          mOTAProgressCallback!.callback(progress);
        }
        break;
    }
  }

  // Device Connection status【设备连接状态】
  static Future<void> deviceConnectState(int code) async {
    ///Binding status 0: connection failed 1: connection succeeded 2: disconnected 3: connection timed out 4: no device 5:iOS needs to remove pairing
    ///【绑定状态 0:连接失败 1:连接成功 2:断开连接 3:连接超时 4:无此设备 5:iOS需要移除配对】
    switch (code) {
      case 0: // connection failed【连接失败】
        if (mEaBleConnectListener != null) {
          mEaBleConnectListener?.connectError();
        }
        break;
      case 1: //connection succeeded 【连接成功】
        if (mEaBleConnectListener != null) {
          mEaBleConnectListener?.deviceConnected();
        }
        break;
      case 2: //disconnected 【断开连接】
        if (mEaBleConnectListener != null) {
          mEaBleConnectListener?.deviceDisconnect();
        }
        break;
      case 3: //connection timed out 【连接超时】
        if (mEaBleConnectListener != null) {
          mEaBleConnectListener?.connectTimeOut();
        }
        break;
      case 4: //no device 【无此设备】
        if (mEaBleConnectListener != null) {
          mEaBleConnectListener?.deviceNotFind();
        }
        break;
      case 5: //iOS needs to remove pairing 【iOS需要移除配对】
        if (mEaBleConnectListener != null) {
          mEaBleConnectListener?.iOSRelievePair();
        }
        break;
      default:
    }
  }

  // Bluetooth state【蓝牙状态】
  static Future<void> bluetoothState(int code) async {
    /// Bluetooth status 0: disabled Bluetooth 1: Enabled Bluetooth 2: unauthorized Bluetooth 3: enabled location 4: not supported BLE
    // 【蓝牙状态 0:未开启蓝牙 1:蓝牙开启 2:蓝牙未授权 3:定位未开启 4:不支持BLE】
    switch (code) {
      case 0: //disabled Bluetooth 【未开启蓝牙】
        if (mEaBleConnectListener != null) {
          mEaBleConnectListener?.unopenedBluetooth();
        }
        break;
      case 2: //unauthorized Bluetooth 【蓝牙未授权】
        if (mEaBleConnectListener != null) {
          mEaBleConnectListener?.iOSUnAuthorized();
        }
        break;
      case 3: //enabled location【定位未开启】
        if (mEaBleConnectListener != null) {
          mEaBleConnectListener?.notOpenLocation();
        }
        break;
      case 4: //not supported BLE【支持BLE】
        if (mEaBleConnectListener != null) {
          mEaBleConnectListener?.unsupportedBLE();
        }
        break;
      default:
    }
  }

  // static Future<void> reportData(String param) async {
  //   Map dataMap = convert.jsonDecode(param);
  //   int type = dataMap["dataType"];
  //   if (type == 0x01) {
  //     if (mDeviceOperationListener != null) {
  //       mDeviceOperationListener?.searchPhone();
  //     }
  //   } else if (type == 0x02) {
  //     if (mDeviceOperationListener != null) {
  //       mDeviceOperationListener?.stopSearchPhone();
  //     }
  //   } else if (type == 0x03) {
  //     if (mDeviceOperationListener != null) {
  //       mDeviceOperationListener?.connectCamera();
  //     }
  //   } else if (type == 0x04) {
  //     if (mDeviceOperationListener != null) {
  //       mDeviceOperationListener?.takePhoto();
  //     }
  //   } else if (type == 0x05) {
  //     if (mDeviceOperationListener != null) {
  //       mDeviceOperationListener?.endTakePhoto();
  //     }
  //   } else if (type == 0x06) {
  //     if (mDeviceOperationListener != null) {
  //       mDeviceOperationListener?.updateWeather();
  //     }
  //   } else if (type == 0x07) {
  //     if (mDeviceOperationListener != null) {
  //       mDeviceOperationListener?.circadian();
  //     }
  //   } else if (type == 0x08) {
  //     if (mDeviceOperationListener != null) {
  //       mDeviceOperationListener?.updateAgps();
  //     }
  //   } else if (type == 0x0A) {
  //     if (mDeviceOperationListener != null) {
  //       mDeviceOperationListener?.transmissionComplete();
  //     }
  //   } else if (type == 0x0B) {
  //     if (mDeviceOperationListener != null) {
  //       int appType = dataMap["appType"];
  //       EABleQueryMusic eaBleQueryMusic = EABleQueryMusic();
  //       if (appType == 0) {
  //         eaBleQueryMusic.e_app = PlayerType.default_type;
  //       } else if (appType == 1) {
  //         eaBleQueryMusic.e_app = PlayerType.apple_music;
  //       } else if (appType == 1) {
  //         eaBleQueryMusic.e_app = PlayerType.deeze;
  //       } else if (appType == 1) {
  //         eaBleQueryMusic.e_app = PlayerType.spotify;
  //       }
  //       mDeviceOperationListener?.queryMusic(eaBleQueryMusic);
  //     }
  //   } else if (type == 0x0C) {
  //     if (mDeviceOperationListener != null) {
  //       int volume = dataMap["volume"];
  //       int elapsedtime = dataMap["elapsedtime"];
  //       int action = dataMap["action"];
  //       EABleMusicControl eaBleMusicControl = EABleMusicControl();
  //       eaBleMusicControl.elapsedtime = elapsedtime;
  //       eaBleMusicControl.volume = volume;
  //       if (action == 0) {
  //         eaBleMusicControl.e_ops = MusicControl.play_start;
  //       } else if (action == 1) {
  //         eaBleMusicControl.e_ops = MusicControl.play_stop;
  //       } else if (action == 2) {
  //         eaBleMusicControl.e_ops = MusicControl.previous_song;
  //       } else if (action == 3) {
  //         eaBleMusicControl.e_ops = MusicControl.next_song;
  //       } else if (action == 4) {
  //         eaBleMusicControl.e_ops = MusicControl.volume_up;
  //       } else if (action == 5) {
  //         eaBleMusicControl.e_ops = MusicControl.volume_reduction;
  //       }
  //       mDeviceOperationListener?.musicControl(eaBleMusicControl);
  //     }
  //   } else if (type == 0x0D) {
  //     if (mDeviceOperationListener != null) {
  //       int? socialId = dataMap["socialId"];
  //       String? content = dataMap["content"];
  //       EABleSocialResponse eaBleSocialResponse = EABleSocialResponse();
  //       eaBleSocialResponse.id = socialId;
  //       eaBleSocialResponse.content = content;

  //       mDeviceOperationListener?.socialResponse(eaBleSocialResponse);
  //     }
  //   } else if (type == 0x0E) {
  //     if (mDeviceOperationListener != null) {
  //       mDeviceOperationListener?.mtu(dataMap["mtu"]);
  //     }
  //   } else if (type == 0x0F) {
  //     if (mDeviceOperationListener != null) {
  //       mDeviceOperationListener?.mutualFail(dataMap["errorCode"]);
  //     }
  //   }
  // }

  // static Future<void> motionData(String param) async {
  //   Map<String, dynamic> dataMap = convert.jsonDecode(param);
  //   MotionData motionData = MotionData.fromJson(dataMap);
  //   int dataType = motionData.dataType;
  //   int flag = motionData.position;
  //   CommonFlag commonFlag;
  //   if (flag == 0) {
  //     commonFlag = CommonFlag.begin;
  //   } else if (flag == 1) {
  //     commonFlag = CommonFlag.proceed;
  //   } else if (flag == 2) {
  //     commonFlag = CommonFlag.end;
  //   } else {
  //     commonFlag = CommonFlag.begin_end;
  //   }
  //   if (dataType == 0x01) {
  //     mMotionDataListener?.dailyMotionData(motionData.dailyData, commonFlag);
  //   } else if (dataType == 0x02) {
  //     List<TempSleepData> tempList = motionData.sleepData;
  //     List<EABleSleepData> dataList = <EABleSleepData>[];
  //     for (int i = 0; i < tempList.length; i++) {
  //       EABleSleepData eaBleSleepData = EABleSleepData();
  //       eaBleSleepData.time_stamp = tempList.elementAt(i).time_stamp;
  //       int sleepNode = tempList.elementAt(i).sleepNode;
  //       if (sleepNode == 0) {
  //         eaBleSleepData.e_sleep_node = SleepMode.activity;
  //       } else if (sleepNode == 1) {
  //         eaBleSleepData.e_sleep_node = SleepMode.enter;
  //       } else if (sleepNode == 2) {
  //         eaBleSleepData.e_sleep_node = SleepMode.wake;
  //       } else if (sleepNode == 3) {
  //         eaBleSleepData.e_sleep_node = SleepMode.rem;
  //       } else if (sleepNode == 4) {
  //         eaBleSleepData.e_sleep_node = SleepMode.light;
  //       } else if (sleepNode == 5) {
  //         eaBleSleepData.e_sleep_node = SleepMode.deep;
  //       } else if (sleepNode == 6) {
  //         eaBleSleepData.e_sleep_node = SleepMode.quit;
  //       } else if (sleepNode == 7) {
  //         eaBleSleepData.e_sleep_node = SleepMode.unknown;
  //       } else if (sleepNode == 8) {
  //         eaBleSleepData.e_sleep_node = SleepMode.summary;
  //       }
  //       dataList.add(eaBleSleepData);
  //     }
  //     mMotionDataListener?.sleepData(dataList, commonFlag);
  //   } else if (dataType == 0x03) {
  //     mMotionDataListener?.heartRateData(motionData.heartData, commonFlag);
  //   } else if (dataType == 0x04) {
  //     mMotionDataListener?.gpsData(motionData.gpsData, commonFlag);
  //   } else if (dataType == 0x05) {
  //     List<TempMultiData> tempList = motionData.multiData;
  //     List<EABleMultiData> dataList = <EABleMultiData>[];
  //     for (int i = 0; i < tempList.length; i++) {
  //       EABleMultiData eaBleMultiData = EABleMultiData();
  //       eaBleMultiData.begin_time_stamp =
  //           tempList.elementAt(i).begin_time_stamp;
  //       eaBleMultiData.end_time_stamp = tempList.elementAt(i).end_time_stamp;
  //       eaBleMultiData.steps = tempList.elementAt(i).steps;
  //       eaBleMultiData.calorie = tempList.elementAt(i).calorie;
  //       eaBleMultiData.distance = tempList.elementAt(i).distance;
  //       eaBleMultiData.duration = tempList.elementAt(i).duration;
  //       eaBleMultiData.training_effect_normal =
  //           tempList.elementAt(i).training_effect_normal;
  //       eaBleMultiData.training_effect_warmUp =
  //           tempList.elementAt(i).training_effect_warmUp;
  //       eaBleMultiData.training_effect_fatconsumption =
  //           tempList.elementAt(i).training_effect_fatconsumption;
  //       eaBleMultiData.training_effect_aerobic =
  //           tempList.elementAt(i).training_effect_aerobic;
  //       eaBleMultiData.training_effect_anaerobic =
  //           tempList.elementAt(i).training_effect_anaerobic;
  //       eaBleMultiData.training_effect_limit =
  //           tempList.elementAt(i).training_effect_limit;
  //       eaBleMultiData.average_heart_rate =
  //           tempList.elementAt(i).average_heart_rate;
  //       eaBleMultiData.average_temperature =
  //           tempList.elementAt(i).average_temperature;
  //       eaBleMultiData.average_speed = tempList.elementAt(i).average_speed;
  //       eaBleMultiData.average_pace = tempList.elementAt(i).average_pace;
  //       eaBleMultiData.average_step_freq =
  //           tempList.elementAt(i).average_step_freq;
  //       eaBleMultiData.average_stride = tempList.elementAt(i).average_stride;
  //       eaBleMultiData.average_altitude =
  //           tempList.elementAt(i).average_altitude;
  //       eaBleMultiData.average_heart_rate_max =
  //           tempList.elementAt(i).average_heart_rate_max;
  //       eaBleMultiData.average_heart_rate_min =
  //           tempList.elementAt(i).average_heart_rate_min;
  //       int type = tempList.elementAt(i).e_type;
  //       if (type == 0) {
  //         eaBleMultiData.e_type = MotionType.daily;
  //       } else if (type == 1) {
  //         eaBleMultiData.e_type = MotionType.ourdoor_walking;
  //       } else if (type == 2) {
  //         eaBleMultiData.e_type = MotionType.ourdoor_running;
  //       } else if (type == 3) {
  //         eaBleMultiData.e_type = MotionType.ourdoor_on_foot;
  //       } else if (type == 4) {
  //         eaBleMultiData.e_type = MotionType.ourdoor_on_mountaineering;
  //       } else if (type == 5) {
  //         eaBleMultiData.e_type = MotionType.ourdoor_trail_running;
  //       } else if (type == 6) {
  //         eaBleMultiData.e_type = MotionType.ourdoor_cycling;
  //       } else if (type == 7) {
  //         eaBleMultiData.e_type = MotionType.outdoor_swimming;
  //       } else if (type == 8) {
  //         eaBleMultiData.e_type = MotionType.indoor_walking;
  //       } else if (type == 9) {
  //         eaBleMultiData.e_type = MotionType.indoor_running;
  //       } else if (type == 10) {
  //         eaBleMultiData.e_type = MotionType.indoor_exercise;
  //       } else if (type == 11) {
  //         eaBleMultiData.e_type = MotionType.indoor_cycling;
  //       } else if (type == 12) {
  //         eaBleMultiData.e_type = MotionType.elliptical;
  //       } else if (type == 13) {
  //         eaBleMultiData.e_type = MotionType.yoga;
  //       } else if (type == 14) {
  //         eaBleMultiData.e_type = MotionType.rowing;
  //       }
  //       dataList.add(eaBleMultiData);
  //     }
  //     mMotionDataListener?.multiMotionData(dataList, commonFlag);
  //   } else if (dataType == 0x06) {
  //     mMotionDataListener?.bloodOxygenData(motionData.bloodData, commonFlag);
  //   } else if (dataType == 0x07) {
  //     List<TempPressureData> tempList = motionData.pressureData;
  //     List<EABlePressureData> dataList = <EABlePressureData>[];
  //     for (int i = 0; i < tempList.length; i++) {
  //       EABlePressureData eaBlePressureData = EABlePressureData();
  //       eaBlePressureData.time_stamp = tempList.elementAt(i).time_stamp;
  //       eaBlePressureData.stess_value = tempList.elementAt(i).stess_value;
  //       int level = tempList.elementAt(i).e_type;
  //       if (level == 0) {
  //         eaBlePressureData.e_type = PressureLevel.pressure_unkown;
  //       } else if (level == 1) {
  //         eaBlePressureData.e_type = PressureLevel.pressure_relax;
  //       } else if (level == 2) {
  //         eaBlePressureData.e_type = PressureLevel.pressure_normal;
  //       } else if (level == 3) {
  //         eaBlePressureData.e_type = PressureLevel.pressure_middle;
  //       } else if (level == 4) {
  //         eaBlePressureData.e_type = PressureLevel.pressure_high;
  //       }
  //       dataList.add(eaBlePressureData);
  //     }
  //     mMotionDataListener?.pressureData(dataList, commonFlag);
  //   } else if (dataType == 0x08) {
  //     mMotionDataListener?.stepFrequencyData(motionData.freqData, commonFlag);
  //   } else if (dataType == 0x09) {
  //     mMotionDataListener?.speedData(motionData.paceData, commonFlag);
  //   } else if (dataType == 0x0A) {
  //     mMotionDataListener?.restingHeartRateData(
  //         motionData.restingData, commonFlag);
  //   } else if (dataType == 0x0B) {
  //     mMotionDataListener?.mutualFail(dataMap["errorCode"]);
  //   }
  // }

  // Future<void> getDonDisturbInfo(DonDisturbCallback disturbCallback) async {
  //   final String txt = await _channel.invokeMethod("getDonDisturbInfo");
  //   Map<String, dynamic> info = convert.jsonDecode(txt);
  //   NotDisturb notDisturb = NotDisturb.fromJson(info);
  //   if (notDisturb.dataType == 0x0F) {
  //     disturbCallback.mutualFail(notDisturb.errorCode);
  //   } else {
  //     EABleNotDisturb eaBleNotDisturb = EABleNotDisturb();
  //     eaBleNotDisturb.sw = notDisturb.sw;
  //     eaBleNotDisturb.begin_hour = notDisturb.begin_hour;
  //     eaBleNotDisturb.begin_minute = notDisturb.begin_minute;
  //     eaBleNotDisturb.end_hour = notDisturb.end_hour;
  //     eaBleNotDisturb.end_minute = notDisturb.end_minute;
  //     disturbCallback.donDisturbInfo(eaBleNotDisturb);
  //   }
  // }

  // Future<void> getDailyGoalsInfo(GoalCallback goalCallback) async {
  //   final String txt = await _channel.invokeMethod("getDailyGoalsInfo");
  //   Map<String, dynamic> info = convert.jsonDecode(txt);
  //   DailyGoal dailyGoal = DailyGoal.fromJson(info);
  //   if (dailyGoal.dataType == 0x0F) {
  //     goalCallback.mutualFail(dailyGoal.errorCode);
  //   } else {
  //     EABleDailyGoal eaBleDailyGoal = EABleDailyGoal();
  //     EABleDaily stepDaily = EABleDaily();
  //     stepDaily.sw = dailyGoal.s_step_sw;
  //     stepDaily.goal = dailyGoal.s_step_goal;
  //     eaBleDailyGoal.s_step = stepDaily;
  //     EABleDaily calorieDaily = EABleDaily();
  //     calorieDaily.sw = dailyGoal.s_calorie_sw;
  //     calorieDaily.goal = dailyGoal.s_calorie_goal;
  //     eaBleDailyGoal.s_calorie = calorieDaily;
  //     EABleDaily distanceDaily = EABleDaily();
  //     distanceDaily.sw = dailyGoal.s_distance_sw;
  //     distanceDaily.goal = dailyGoal.s_distance_goal;
  //     eaBleDailyGoal.s_distance = distanceDaily;
  //     EABleDaily durationDaily = EABleDaily();
  //     durationDaily.sw = dailyGoal.s_duration_sw;
  //     durationDaily.goal = dailyGoal.s_duration_goal;
  //     eaBleDailyGoal.s_duration = durationDaily;
  //     EABleDaily sleepDaily = EABleDaily();
  //     sleepDaily.sw = dailyGoal.s_sleep_sw;
  //     sleepDaily.goal = dailyGoal.s_sleep_goal;
  //     eaBleDailyGoal.s_sleep = sleepDaily;
  //     goalCallback.goalInfo(eaBleDailyGoal);
  //   }
  // }

  // Future<void> getAutomaticSleepMonitoringInfo(
  //     SleepCheckCallback sleepCheckCallback) async {
  //   final String txt =
  //       await _channel.invokeMethod("getAutomaticSleepMonitoringInfo");
  //   Map<String, dynamic> info = convert.jsonDecode(txt);
  //   AutoCheckSleep autoCheckSleep = AutoCheckSleep.fromJson(info);
  //   if (autoCheckSleep.dataType == 0x0F) {
  //     sleepCheckCallback.mutualFail(autoCheckSleep.errorCode);
  //   } else {
  //     EABleAutoCheckSleep eaBleAutoCheckSleep = EABleAutoCheckSleep();
  //     eaBleAutoCheckSleep.end_minute = autoCheckSleep.end_minute;
  //     eaBleAutoCheckSleep.end_hour = autoCheckSleep.end_hour;
  //     eaBleAutoCheckSleep.begin_minute = autoCheckSleep.begin_minute;
  //     eaBleAutoCheckSleep.begin_hour = autoCheckSleep.begin_hour;
  //     eaBleAutoCheckSleep.week_cycle_bit = autoCheckSleep.week_cycle_bit;
  //     sleepCheckCallback.sleepInfo(eaBleAutoCheckSleep);
  //   }
  // }

  // Future<void> getSedentaryMonitoringInfo(
  //     SedentaryCheckCallback sedentaryCheckCallback) async {
  //   final String txt =
  //       await _channel.invokeMethod("getSedentaryMonitoringInfo");
  //   Map<String, dynamic> info = convert.jsonDecode(txt);
  //   Sedentariness sedentariness = Sedentariness.fromJson(info);
  //   if (sedentariness.dataType == 0x0F) {
  //     sedentaryCheckCallback.mutualFail(sedentariness.errorCode);
  //   } else {
  //     EABleSedentariness eaBleSedentariness = EABleSedentariness();
  //     eaBleSedentariness.week_cycle_bit = sedentariness.week_cycle_bit;
  //     eaBleSedentariness.begin_hour = sedentariness.begin_hour;
  //     eaBleSedentariness.begin_minute = sedentariness.begin_minute;
  //     eaBleSedentariness.end_hour = sedentariness.end_hour;
  //     eaBleSedentariness.end_minute = sedentariness.end_minute;
  //     eaBleSedentariness.interval = sedentariness.interval;
  //     eaBleSedentariness.step_threshold = sedentariness.step_threshold;
  //     sedentaryCheckCallback.sedentaryInfo(eaBleSedentariness);
  //   }
  // }

  // Future<void> getWatchWeatherInfo(WeatherCallback weatherCallback) async {
  //   final String txt = await _channel.invokeMethod("getWatchWeatherInfo");
  //   Map<String, dynamic> info = convert.jsonDecode(txt);
  //   TempWeather tempWeather = TempWeather.fromJson(info);
  //   if (tempWeather.dataType == 0x0F) {
  //     weatherCallback.mutualFail(tempWeather.errorCode);
  //   } else {
  //     EABleWeather weather = EABleWeather();
  //     if (tempWeather.temperatureUnit == 0) {
  //       weather.temperatureUnit = TemperatureUnit.centigrade;
  //     } else {
  //       weather.temperatureUnit = TemperatureUnit.fahrenheit;
  //     }
  //     weather.current_temperature = tempWeather.current_temperature;
  //     weather.place = tempWeather.place;
  //     if (tempWeather.s_day.isNotEmpty) {
  //       List<EABleWeatherItem> itemList = <EABleWeatherItem>[];
  //       for (int i = 0; i < tempWeather.s_day.length; i++) {
  //         EABleWeatherItem eaBleWeatherItem = EABleWeatherItem();
  //         eaBleWeatherItem.air_humidity =
  //             tempWeather.s_day.elementAt(i).air_humidity;
  //         eaBleWeatherItem.cloudiness =
  //             tempWeather.s_day.elementAt(i).cloudiness;
  //         eaBleWeatherItem.max_temperature =
  //             tempWeather.s_day.elementAt(i).max_temperature;
  //         eaBleWeatherItem.max_wind_power =
  //             tempWeather.s_day.elementAt(i).max_wind_power;
  //         eaBleWeatherItem.min_temperature =
  //             tempWeather.s_day.elementAt(i).min_temperature;
  //         eaBleWeatherItem.min_wind_power =
  //             tempWeather.s_day.elementAt(i).min_wind_power;
  //         eaBleWeatherItem.sunrise_timestamp =
  //             tempWeather.s_day.elementAt(i).sunrise_timestamp;
  //         eaBleWeatherItem.sunset_timestamp =
  //             tempWeather.s_day.elementAt(i).sunset_timestamp;
  //         if (tempWeather.s_day.elementAt(i).e_air == 0) {
  //           eaBleWeatherItem.e_air = AirQuality.excellent;
  //         } else if (tempWeather.s_day.elementAt(i).e_air == 1) {
  //           eaBleWeatherItem.e_air = AirQuality.good;
  //         } else {
  //           eaBleWeatherItem.e_air = AirQuality.bad;
  //         }
  //         if (tempWeather.s_day.elementAt(i).e_moon == 0) {
  //           eaBleWeatherItem.e_moon = Moon.new_moon;
  //         } else if (tempWeather.s_day.elementAt(i).e_moon == 1) {
  //           eaBleWeatherItem.e_moon = Moon.waxing_crescent_moon;
  //         } else if (tempWeather.s_day.elementAt(i).e_moon == 2) {
  //           eaBleWeatherItem.e_moon = Moon.quarter_moon;
  //         } else if (tempWeather.s_day.elementAt(i).e_moon == 3) {
  //           eaBleWeatherItem.e_moon = Moon.half_moon_1;
  //         } else if (tempWeather.s_day.elementAt(i).e_moon == 4) {
  //           eaBleWeatherItem.e_moon = Moon.waxing_gibbous_moon;
  //         } else if (tempWeather.s_day.elementAt(i).e_moon == 5) {
  //           eaBleWeatherItem.e_moon = Moon.full_moon;
  //         } else if (tempWeather.s_day.elementAt(i).e_moon == 6) {
  //           eaBleWeatherItem.e_moon = Moon.waning_gibbous_moon;
  //         } else if (tempWeather.s_day.elementAt(i).e_moon == 7) {
  //           eaBleWeatherItem.e_moon = Moon.half_moon_2;
  //         } else if (tempWeather.s_day.elementAt(i).e_moon == 8) {
  //           eaBleWeatherItem.e_moon = Moon.last_quarter_moon;
  //         } else if (tempWeather.s_day.elementAt(i).e_moon == 9) {
  //           eaBleWeatherItem.e_moon = Moon.waning_crescent_moon;
  //         }
  //         if (tempWeather.s_day.elementAt(i).e_day_type == 0) {
  //           eaBleWeatherItem.e_day_type = WeatherType.clear;
  //         } else if (tempWeather.s_day.elementAt(i).e_day_type == 1) {
  //           eaBleWeatherItem.e_day_type = WeatherType.cloudy;
  //         } else if (tempWeather.s_day.elementAt(i).e_day_type == 2) {
  //           eaBleWeatherItem.e_day_type = WeatherType.gloomy;
  //         } else if (tempWeather.s_day.elementAt(i).e_day_type == 3) {
  //           eaBleWeatherItem.e_day_type = WeatherType.drizzle;
  //         } else if (tempWeather.s_day.elementAt(i).e_day_type == 4) {
  //           eaBleWeatherItem.e_day_type = WeatherType.moderate_rain;
  //         } else if (tempWeather.s_day.elementAt(i).e_day_type == 5) {
  //           eaBleWeatherItem.e_day_type = WeatherType.thunderstorm;
  //         } else if (tempWeather.s_day.elementAt(i).e_day_type == 6) {
  //           eaBleWeatherItem.e_day_type = WeatherType.heavy_rain;
  //         } else if (tempWeather.s_day.elementAt(i).e_day_type == 7) {
  //           eaBleWeatherItem.e_day_type = WeatherType.sleet;
  //         } else if (tempWeather.s_day.elementAt(i).e_day_type == 8) {
  //           eaBleWeatherItem.e_day_type = WeatherType.light_snow;
  //         } else if (tempWeather.s_day.elementAt(i).e_day_type == 9) {
  //           eaBleWeatherItem.e_day_type = WeatherType.moderate_snow;
  //         } else if (tempWeather.s_day.elementAt(i).e_day_type == 10) {
  //           eaBleWeatherItem.e_day_type = WeatherType.heavy_snow;
  //         }
  //         if (tempWeather.s_day.elementAt(i).e_night_type == 0) {
  //           eaBleWeatherItem.e_night_type = WeatherType.clear;
  //         } else if (tempWeather.s_day.elementAt(i).e_night_type == 1) {
  //           eaBleWeatherItem.e_night_type = WeatherType.cloudy;
  //         } else if (tempWeather.s_day.elementAt(i).e_night_type == 2) {
  //           eaBleWeatherItem.e_night_type = WeatherType.gloomy;
  //         } else if (tempWeather.s_day.elementAt(i).e_night_type == 3) {
  //           eaBleWeatherItem.e_night_type = WeatherType.drizzle;
  //         } else if (tempWeather.s_day.elementAt(i).e_night_type == 4) {
  //           eaBleWeatherItem.e_night_type = WeatherType.moderate_rain;
  //         } else if (tempWeather.s_day.elementAt(i).e_night_type == 5) {
  //           eaBleWeatherItem.e_night_type = WeatherType.thunderstorm;
  //         } else if (tempWeather.s_day.elementAt(i).e_night_type == 6) {
  //           eaBleWeatherItem.e_night_type = WeatherType.heavy_rain;
  //         } else if (tempWeather.s_day.elementAt(i).e_night_type == 7) {
  //           eaBleWeatherItem.e_night_type = WeatherType.sleet;
  //         } else if (tempWeather.s_day.elementAt(i).e_night_type == 8) {
  //           eaBleWeatherItem.e_night_type = WeatherType.light_snow;
  //         } else if (tempWeather.s_day.elementAt(i).e_night_type == 9) {
  //           eaBleWeatherItem.e_night_type = WeatherType.moderate_snow;
  //         } else if (tempWeather.s_day.elementAt(i).e_night_type == 10) {
  //           eaBleWeatherItem.e_night_type = WeatherType.heavy_snow;
  //         }
  //         if (tempWeather.s_day.elementAt(i).e_rays == 0) {
  //           eaBleWeatherItem.e_rays = RaysLevel.weak;
  //         } else if (tempWeather.s_day.elementAt(i).e_rays == 1) {
  //           eaBleWeatherItem.e_rays = RaysLevel.medium;
  //         } else if (tempWeather.s_day.elementAt(i).e_rays == 2) {
  //           eaBleWeatherItem.e_rays = RaysLevel.strong;
  //         } else if (tempWeather.s_day.elementAt(i).e_rays == 3) {
  //           eaBleWeatherItem.e_rays = RaysLevel.very_strong;
  //         } else if (tempWeather.s_day.elementAt(i).e_rays == 4) {
  //           eaBleWeatherItem.e_rays = RaysLevel.super_strong;
  //         }
  //         itemList.add(eaBleWeatherItem);
  //       }
  //       weather.s_day = itemList;
  //     }
  //     weatherCallback.weatherInfo(weather);
  //   }
  // }

  // Future<void> getReminderControlInfo(RemindCallback remindCallback) async {
  //   final String txt = await _channel.invokeMethod("getReminderControlInfo");
  //   Map<String, dynamic> info = convert.jsonDecode(txt);
  //   TempAncsSw tempAncsSw = TempAncsSw.fromJson(info);
  //   if (tempAncsSw.dataType == 0x0F) {
  //     remindCallback.mutualFail(tempAncsSw.errorCode);
  //   } else {
  //     EABleAncsSw eaBleAncsSw = EABleAncsSw();
  //     EABleAncsSwItem incomingcall = EABleAncsSwItem();
  //     EABleAncsSwItem missedcall = EABleAncsSwItem();
  //     EABleAncsSwItem sms = EABleAncsSwItem();
  //     EABleAncsSwItem social = EABleAncsSwItem();
  //     EABleAncsSwItem email = EABleAncsSwItem();
  //     EABleAncsSwItem schedule = EABleAncsSwItem();
  //     eaBleAncsSw.s_incomingcall = incomingcall;
  //     eaBleAncsSw.s_missedcall = missedcall;
  //     eaBleAncsSw.s_sms = sms;
  //     eaBleAncsSw.s_social = social;
  //     eaBleAncsSw.s_email = email;
  //     eaBleAncsSw.s_schedule = schedule;

  //     missedcall.sw = tempAncsSw.s_missedcall_sw;
  //     incomingcall.sw = tempAncsSw.s_incomingcall_sw;
  //     sms.sw = tempAncsSw.s_sms_sw;
  //     social.sw = tempAncsSw.s_social_sw;
  //     email.sw = tempAncsSw.s_email_sw;
  //     schedule.sw = tempAncsSw.s_schedule_sw;

  //     if (tempAncsSw.s_incomingcall_action == 0) {
  //       incomingcall.e_action = CommonAction.no_action;
  //       missedcall.e_action = CommonAction.no_action;
  //       sms.e_action = CommonAction.no_action;
  //       social.e_action = CommonAction.no_action;
  //       email.e_action = CommonAction.no_action;
  //       schedule.e_action = CommonAction.no_action;
  //     } else if (tempAncsSw.s_incomingcall_action == 1) {
  //       incomingcall.e_action = CommonAction.one_long_vibration;
  //       missedcall.e_action = CommonAction.one_long_vibration;
  //       sms.e_action = CommonAction.one_long_vibration;
  //       social.e_action = CommonAction.one_long_vibration;
  //       email.e_action = CommonAction.one_long_vibration;
  //       schedule.e_action = CommonAction.one_long_vibration;
  //     } else if (tempAncsSw.s_incomingcall_action == 2) {
  //       incomingcall.e_action = CommonAction.one_short_vibration;
  //       missedcall.e_action = CommonAction.one_short_vibration;
  //       sms.e_action = CommonAction.one_short_vibration;
  //       social.e_action = CommonAction.one_short_vibration;
  //       email.e_action = CommonAction.one_short_vibration;
  //       schedule.e_action = CommonAction.one_short_vibration;
  //     } else if (tempAncsSw.s_incomingcall_action == 3) {
  //       incomingcall.e_action = CommonAction.two_long_vibration;
  //       missedcall.e_action = CommonAction.two_long_vibration;
  //       sms.e_action = CommonAction.two_long_vibration;
  //       social.e_action = CommonAction.two_long_vibration;
  //       email.e_action = CommonAction.two_long_vibration;
  //       schedule.e_action = CommonAction.two_long_vibration;
  //     } else if (tempAncsSw.s_incomingcall_action == 4) {
  //       incomingcall.e_action = CommonAction.two_short_vibration;
  //       missedcall.e_action = CommonAction.two_short_vibration;
  //       sms.e_action = CommonAction.two_short_vibration;
  //       social.e_action = CommonAction.two_short_vibration;
  //       email.e_action = CommonAction.two_short_vibration;
  //       schedule.e_action = CommonAction.two_short_vibration;
  //     } else if (tempAncsSw.s_incomingcall_action == 5) {
  //       incomingcall.e_action = CommonAction.long_vibration;
  //       missedcall.e_action = CommonAction.long_vibration;
  //       sms.e_action = CommonAction.long_vibration;
  //       social.e_action = CommonAction.long_vibration;
  //       email.e_action = CommonAction.long_vibration;
  //       schedule.e_action = CommonAction.long_vibration;
  //     } else if (tempAncsSw.s_incomingcall_action == 6) {
  //       incomingcall.e_action = CommonAction.long_short_vibration;
  //       missedcall.e_action = CommonAction.long_short_vibration;
  //       sms.e_action = CommonAction.long_short_vibration;
  //       social.e_action = CommonAction.long_short_vibration;
  //       email.e_action = CommonAction.long_short_vibration;
  //       schedule.e_action = CommonAction.long_short_vibration;
  //     } else if (tempAncsSw.s_incomingcall_action == 7) {
  //       incomingcall.e_action = CommonAction.one_ring;
  //       missedcall.e_action = CommonAction.one_ring;
  //       sms.e_action = CommonAction.one_ring;
  //       social.e_action = CommonAction.one_ring;
  //       email.e_action = CommonAction.one_ring;
  //       schedule.e_action = CommonAction.one_ring;
  //     } else if (tempAncsSw.s_incomingcall_action == 8) {
  //       incomingcall.e_action = CommonAction.two_ring;
  //       missedcall.e_action = CommonAction.two_ring;
  //       sms.e_action = CommonAction.two_ring;
  //       social.e_action = CommonAction.two_ring;
  //       email.e_action = CommonAction.two_ring;
  //       schedule.e_action = CommonAction.two_ring;
  //     } else if (tempAncsSw.s_incomingcall_action == 9) {
  //       incomingcall.e_action = CommonAction.ring;
  //       missedcall.e_action = CommonAction.ring;
  //       sms.e_action = CommonAction.ring;
  //       social.e_action = CommonAction.ring;
  //       email.e_action = CommonAction.ring;
  //       schedule.e_action = CommonAction.ring;
  //     } else if (tempAncsSw.s_incomingcall_action == 10) {
  //       incomingcall.e_action = CommonAction.one_vibration_ring;
  //       missedcall.e_action = CommonAction.one_vibration_ring;
  //       sms.e_action = CommonAction.one_vibration_ring;
  //       social.e_action = CommonAction.one_vibration_ring;
  //       email.e_action = CommonAction.one_vibration_ring;
  //       schedule.e_action = CommonAction.one_vibration_ring;
  //     } else if (tempAncsSw.s_incomingcall_action == 10) {
  //       incomingcall.e_action = CommonAction.vibration_ring;
  //       missedcall.e_action = CommonAction.vibration_ring;
  //       sms.e_action = CommonAction.vibration_ring;
  //       social.e_action = CommonAction.vibration_ring;
  //       email.e_action = CommonAction.vibration_ring;
  //       schedule.e_action = CommonAction.vibration_ring;
  //     }
  //     remindCallback.remindInfo(eaBleAncsSw);
  //   }
  // }

  // Future<void> getAlarmClockInfo(AttentionCallback attentionCallback) async {
  //   final String txt = await _channel.invokeMethod("getAlarmClockInfo");
  //   Map<String, dynamic> info = convert.jsonDecode(txt);
  //   Reminder reminder = Reminder.fromJson(info);
  //   if (reminder.dataType == 0x0F) {
  //     attentionCallback.mutualFail(reminder.errorCode);
  //   } else {
  //     EABleReminder eaBleReminder = EABleReminder();
  //     eaBleReminder.id = reminder.id;
  //     if (reminder.e_ops == 0) {
  //       eaBleReminder.e_ops = ReminderOps.add;
  //     } else if (reminder.e_ops == 1) {
  //       eaBleReminder.e_ops = ReminderOps.edit;
  //     } else if (reminder.e_ops == 2) {
  //       eaBleReminder.e_ops = ReminderOps.del;
  //     } else if (reminder.e_ops == 3) {
  //       eaBleReminder.e_ops = ReminderOps.del_remind;
  //     } else if (reminder.e_ops == 4) {
  //       eaBleReminder.e_ops = ReminderOps.del_alarm;
  //     } else if (reminder.e_ops == 5) {
  //       eaBleReminder.e_ops = ReminderOps.del_remind_alarm;
  //     } else if (reminder.e_ops == 6) {
  //       eaBleReminder.e_ops = ReminderOps.unknown;
  //     }
  //     if (reminder.tempList.isNotEmpty) {
  //       List<EABleReminderItem> itemList = <EABleReminderItem>[];
  //       eaBleReminder.s_index = itemList;
  //       for (int i = 0; i < reminder.tempList.length; i++) {
  //         EABleReminderItem eaBleReminderItem = EABleReminderItem();
  //         eaBleReminderItem.id = reminder.tempList.elementAt(i).id;
  //         eaBleReminderItem.sw = reminder.tempList.elementAt(i).sw;
  //         eaBleReminderItem.week_cycle_bit =
  //             reminder.tempList.elementAt(i).week_cycle_bit;
  //         eaBleReminderItem.minute = reminder.tempList.elementAt(i).minute;
  //         eaBleReminderItem.hour = reminder.tempList.elementAt(i).hour;
  //         eaBleReminderItem.day = reminder.tempList.elementAt(i).day;
  //         eaBleReminderItem.month = reminder.tempList.elementAt(i).month;
  //         eaBleReminderItem.year = reminder.tempList.elementAt(i).year;
  //         eaBleReminderItem.content = reminder.tempList.elementAt(i).content;
  //         eaBleReminderItem.sec_sw = reminder.tempList.elementAt(i).sec_sw;
  //         eaBleReminderItem.sleep_duration =
  //             reminder.tempList.elementAt(i).sleep_duration;
  //         if (reminder.tempList.elementAt(i).e_action == 0) {
  //           eaBleReminderItem.e_action = CommonAction.no_action;
  //         } else if (reminder.tempList.elementAt(i).e_action == 1) {
  //           eaBleReminderItem.e_action = CommonAction.one_long_vibration;
  //         } else if (reminder.tempList.elementAt(i).e_action == 2) {
  //           eaBleReminderItem.e_action = CommonAction.one_short_vibration;
  //         } else if (reminder.tempList.elementAt(i).e_action == 3) {
  //           eaBleReminderItem.e_action = CommonAction.two_long_vibration;
  //         } else if (reminder.tempList.elementAt(i).e_action == 4) {
  //           eaBleReminderItem.e_action = CommonAction.two_short_vibration;
  //         } else if (reminder.tempList.elementAt(i).e_action == 5) {
  //           eaBleReminderItem.e_action = CommonAction.long_vibration;
  //         } else if (reminder.tempList.elementAt(i).e_action == 6) {
  //           eaBleReminderItem.e_action = CommonAction.long_short_vibration;
  //         } else if (reminder.tempList.elementAt(i).e_action == 7) {
  //           eaBleReminderItem.e_action = CommonAction.one_ring;
  //         } else if (reminder.tempList.elementAt(i).e_action == 8) {
  //           eaBleReminderItem.e_action = CommonAction.two_ring;
  //         } else if (reminder.tempList.elementAt(i).e_action == 9) {
  //           eaBleReminderItem.e_action = CommonAction.ring;
  //         } else if (reminder.tempList.elementAt(i).e_action == 10) {
  //           eaBleReminderItem.e_action = CommonAction.one_vibration_ring;
  //         } else if (reminder.tempList.elementAt(i).e_action == 11) {
  //           eaBleReminderItem.e_action = CommonAction.vibration_ring;
  //         }
  //         if (reminder.tempList.elementAt(i).e_type == 0) {
  //           eaBleReminderItem.e_type = ReminderType.alarm;
  //         } else if (reminder.tempList.elementAt(i).e_type == 1) {
  //           eaBleReminderItem.e_type = ReminderType.sleep;
  //         } else if (reminder.tempList.elementAt(i).e_type == 2) {
  //           eaBleReminderItem.e_type = ReminderType.sport;
  //         } else if (reminder.tempList.elementAt(i).e_type == 3) {
  //           eaBleReminderItem.e_type = ReminderType.drink;
  //         } else if (reminder.tempList.elementAt(i).e_type == 4) {
  //           eaBleReminderItem.e_type = ReminderType.medicine;
  //         } else if (reminder.tempList.elementAt(i).e_type == 5) {
  //           eaBleReminderItem.e_type = ReminderType.meeting;
  //         } else if (reminder.tempList.elementAt(i).e_type == 6) {
  //           eaBleReminderItem.e_type = ReminderType.user;
  //         }
  //         itemList.add(eaBleReminderItem);
  //       }
  //     }
  //     attentionCallback.attentionInfo(eaBleReminder);
  //   }
  // }

  // Future<void> getHeartRateAlarmInfo(
  //     HeartLimitCallback heartLimitCallback) async {
  //   final String txt = await _channel.invokeMethod("getHeartRateAlarmInfo");
  //   Map<String, dynamic> info = convert.jsonDecode(txt);
  //   Hr hr = Hr.fromJson(info);
  //   if (hr.dataType == 0x0F) {
  //     heartLimitCallback.mutualFail(hr.errorCode);
  //   } else {
  //     EABleHr eaBleHr = EABleHr();
  //     eaBleHr.sw = hr.sw;
  //     eaBleHr.max_hr = hr.max_hr;
  //     eaBleHr.min_hr = hr.min_hr;
  //     heartLimitCallback.heartLimitInfo(eaBleHr);
  //   }
  // }

  // Future<void> getCombinationInfo(
  //     CombinationCallback combinationCallback) async {
  //   final String txt = await _channel.invokeMethod("getCombinationInfo");
  //   Map<String, dynamic> info = convert.jsonDecode(txt);
  //   Combination combination = Combination.fromJson(info);
  //   if (combination.dataType == 0x0F) {
  //     combinationCallback.mutualFail(combination.errorCode);
  //   } else {
  //     EABleCombination eaBleCombination = EABleCombination();
  //     eaBleCombination.auto_check_hr_sw = combination.auto_check_hr_sw;
  //     eaBleCombination.auto_pressure_sw = combination.auto_pressure_sw;
  //     eaBleCombination.auto_sedentariness_sw =
  //         combination.auto_sedentariness_sw;
  //     eaBleCombination.bat_level = combination.bat_level;
  //     eaBleCombination.gestures_sw = combination.gestures_sw;
  //     eaBleCombination.not_disturb_sw = combination.not_disturb_sw;
  //     eaBleCombination.set_vibrate_intensity =
  //         combination.set_vibrate_intensity;
  //     eaBleCombination.user_wf_id = combination.user_wf_id;
  //     eaBleCombination.wf_id = combination.wf_id;
  //     if (combination.e_status == 0) {
  //       eaBleCombination.e_status = EABatInfoStatus.normal;
  //     } else {
  //       eaBleCombination.e_status = EABatInfoStatus.charging;
  //     }
  //     if (combination.e_hand_info == 0) {
  //       eaBleCombination.e_hand_info = EAPersonHand.left;
  //     } else {
  //       eaBleCombination.e_hand_info = EAPersonHand.right;
  //     }
  //     if (combination.e_vibrate_intensity == 0) {
  //       eaBleCombination.e_vibrate_intensity = VibrationIntensity.light;
  //     } else if (combination.e_vibrate_intensity == 1) {
  //       eaBleCombination.e_vibrate_intensity = VibrationIntensity.medium;
  //     } else {
  //       eaBleCombination.e_vibrate_intensity = VibrationIntensity.strong;
  //     }
  //     if (combination.e_unit_format == 0) {
  //       eaBleCombination.e_unit_format = UnitFormat.metric;
  //     } else {
  //       eaBleCombination.e_unit_format = UnitFormat.british;
  //     }
  //     combinationCallback.combinationInfo(eaBleCombination);
  //   }
  // }

  // Future<void> getWatchMainMenuInfo(MenuCallback menuCallback) async {
  //   final String txt = await _channel.invokeMethod("getWatchMainMenuInfo");
  //   Map<String, dynamic> info = convert.jsonDecode(txt);
  //   MenuPage menuPage = MenuPage.fromJson(info);
  //   if (menuPage.dataType == 0x0F) {
  //     menuCallback.mutualFail(menuPage.errorCode);
  //   } else {
  //     EABleMenuPage eaBleMenuPage = EABleMenuPage();
  //     if (menuPage.pageList.isNotEmpty) {
  //       List<MenuType> typeList = <MenuType>[];
  //       eaBleMenuPage.typeList = typeList;
  //       for (int i = 0; i < menuPage.pageList.length; i++) {
  //         if (menuPage.pageList.elementAt(i) == 0) {
  //           typeList.add(MenuType.page_null);
  //         } else if (menuPage.pageList.elementAt(i) == 1) {
  //           typeList.add(MenuType.page_heart_rate);
  //         } else if (menuPage.pageList.elementAt(i) == 2) {
  //           typeList.add(MenuType.page_pressure);
  //         } else if (menuPage.pageList.elementAt(i) == 3) {
  //           typeList.add(MenuType.page_weather);
  //         } else if (menuPage.pageList.elementAt(i) == 4) {
  //           typeList.add(MenuType.page_music);
  //         } else if (menuPage.pageList.elementAt(i) == 5) {
  //           typeList.add(MenuType.page_breath);
  //         } else if (menuPage.pageList.elementAt(i) == 6) {
  //           typeList.add(MenuType.page_sleep);
  //         } else if (menuPage.pageList.elementAt(i) == 7) {
  //           typeList.add(MenuType.page_menstrual_cycle);
  //         }
  //       }
  //     }
  //     menuCallback.menuInfo(eaBleMenuPage);
  //   }
  // }

  // Future<void> getPhysiologicalPeriodInformation(
  //     PeriodCallback periodCallback) async {
  //   final String txt =
  //       await _channel.invokeMethod("getPhysiologicalPeriodInformation");
  //   Map<String, dynamic> info = convert.jsonDecode(txt);
  //   Period period = Period.fromJson(info);
  //   if (period.dataType == 0x0F) {
  //     periodCallback.mutualFail(period.errorCode);
  //   } else {
  //     EABlePeriod eaBlePeriod = EABlePeriod();
  //     if (period.periodItem.isNotEmpty) {
  //       List<EABlePeriodData> dataList = <EABlePeriodData>[];
  //       eaBlePeriod.dataList = dataList;
  //       for (int i = 0; i < period.periodItem.length; i++) {
  //         EABlePeriodData eaBlePeriodData = EABlePeriodData();
  //         eaBlePeriodData.time_stamp =
  //             period.periodItem.elementAt(i).time_stamp;
  //         eaBlePeriodData.days = period.periodItem.elementAt(i).days;
  //         if (period.periodItem.elementAt(i).periodType == 0) {
  //           eaBlePeriodData.periodType = PeriodType.safety_period_1;
  //         } else if (period.periodItem.elementAt(i).periodType == 1) {
  //           eaBlePeriodData.periodType = PeriodType.safety_period_2;
  //         } else if (period.periodItem.elementAt(i).periodType == 2) {
  //           eaBlePeriodData.periodType = PeriodType.menstrual;
  //         } else if (period.periodItem.elementAt(i).periodType == 3) {
  //           eaBlePeriodData.periodType = PeriodType.ovulation;
  //         }
  //         dataList.add(eaBlePeriodData);
  //       }
  //     }
  //     periodCallback.periodInfo(eaBlePeriod);
  //   }
  // }

  // Future<void> getCurrentDialId(WatchFaceCallback watchFaceCallback) async {
  //   final String txt = await _channel.invokeMethod("getCurrentDialId");
  //   Map<String, dynamic> info = convert.jsonDecode(txt);
  //   WatchFace watchFace = WatchFace.fromJson(info);
  //   if (watchFace.dataType == 0x0F) {
  //     watchFaceCallback.mutualFail(watchFace.errorCode);
  //   } else {
  //     EABleWatchFace eaBleWatchFace = EABleWatchFace();
  //     eaBleWatchFace.user_wf_id = watchFace.user_wf_id;
  //     eaBleWatchFace.id = watchFace.id;
  //     eaBleWatchFace.user_wf_id_0 = watchFace.user_wf_id_0;
  //     eaBleWatchFace.user_wf_id_1 = watchFace.user_wf_id_1;
  //     eaBleWatchFace.user_wf_id_2 = watchFace.user_wf_id_2;
  //     eaBleWatchFace.user_wf_id_3 = watchFace.user_wf_id_3;
  //     watchFaceCallback.watchFaceInfo(eaBleWatchFace);
  //   }
  // }

  // Future<void> getInfoPushControlInfo(InfoPushCallback infoPushCallback) async {
  //   final String txt = await _channel.invokeMethod("getInfoPushControlInfo");
  //   Map<String, dynamic> info = convert.jsonDecode(txt);
  //   InfoPush infoPush = InfoPush.fromJson(info);
  //   if (infoPush.dataType == 0x0F) {
  //     infoPushCallback.mutualFail(infoPush.errorCode);
  //   } else {
  //     EABleInfoPush eaBleInfoPush = EABleInfoPush();
  //     if (infoPush.list.isNotEmpty) {
  //       List<EABlePushSwitch> itemList = <EABlePushSwitch>[];
  //       eaBleInfoPush.s_app_sw = itemList;
  //       for (int i = 0; i < infoPush.list.length; i++) {
  //         EABlePushSwitch eaBlePushSwitch = EABlePushSwitch();
  //         eaBlePushSwitch.sw = infoPush.list.elementAt(i);
  //         itemList.add(eaBlePushSwitch);
  //       }
  //     }
  //     infoPushCallback.pushInfo(eaBleInfoPush);
  //   }
  // }

  // Future<void> setTimeSync(
  //     EABleSyncTime eaBleSyncTime, GeneralCallback generalCallback) async {
  //   SyncTime syncTime = SyncTime();
  //   syncTime.year = eaBleSyncTime.year;
  //   syncTime.month = eaBleSyncTime.month;
  //   syncTime.day = eaBleSyncTime.day;
  //   syncTime.hour = eaBleSyncTime.hour;
  //   syncTime.minute = eaBleSyncTime.minute;
  //   syncTime.second = eaBleSyncTime.second;
  //   if (eaBleSyncTime.e_hour_system == HourSystem.hour_12) {
  //     syncTime.e_hour_system = 0;
  //   } else {
  //     syncTime.e_hour_system = 1;
  //   }
  //   if (eaBleSyncTime.e_time_zone == TimeZone.zero) {
  //     syncTime.e_time_zone = 0;
  //   } else if (eaBleSyncTime.e_time_zone == TimeZone.east) {
  //     syncTime.e_time_zone = 1;
  //   } else if (eaBleSyncTime.e_time_zone == TimeZone.west) {
  //     syncTime.e_time_zone = 2;
  //   } else {
  //     syncTime.e_time_zone = 3;
  //   }
  //   if (eaBleSyncTime.e_sync_mode == SyncMode.normal) {
  //     syncTime.e_sync_mode = 0;
  //   } else {
  //     syncTime.e_sync_mode = 1;
  //   }
  //   syncTime.time_zone_hour = eaBleSyncTime.time_zone_hour;
  //   syncTime.time_zone_minute = eaBleSyncTime.time_zone_minute;
  //   final String txt = await _channel.invokeMethod(
  //       "setTimeSync", convert.jsonEncode(syncTime));
  //   Map<String, dynamic> info = convert.jsonDecode(txt);
  //   GeneralData generalData = GeneralData.fromJson(info);
  //   if (generalData.dataType == 0x0F) {
  //     generalCallback.mutualFail(generalData.errorCode);
  //   } else {
  //     generalCallback.result(generalData.result == 0 ? true : false);
  //   }
  // }

  // Future<void> setDeviceOps(
  //     EABleDev eaBleDev, GeneralCallback generalCallback) async {
  //   int action = 0;
  //   if (eaBleDev.e_ops == DevOps.restore_factory) {
  //     action = 0;
  //   } else if (eaBleDev.e_ops == DevOps.reset) {
  //     action = 1;
  //   } else if (eaBleDev.e_ops == DevOps.power_off) {
  //     action = 2;
  //   } else if (eaBleDev.e_ops == DevOps.disconnect_ble) {
  //     action = 3;
  //   } else if (eaBleDev.e_ops == DevOps.entering_flight_mode) {
  //     action = 4;
  //   } else if (eaBleDev.e_ops == DevOps.light_up_the_screen) {
  //     action = 5;
  //   } else if (eaBleDev.e_ops == DevOps.turn_off_the_screen) {
  //     action = 6;
  //   } else if (eaBleDev.e_ops == DevOps.stop_search_phone) {
  //     action = 7;
  //   } else if (eaBleDev.e_ops == DevOps.enter_factory_test_mode) {
  //     action = 8;
  //   } else if (eaBleDev.e_ops == DevOps.exit_factory_test_mode) {
  //     action = 9;
  //   }
  //   final String txt = await _channel.invokeMethod("setDeviceOps", action);
  //   Map<String, dynamic> info = convert.jsonDecode(txt);
  //   GeneralData generalData = GeneralData.fromJson(info);
  //   if (generalData.dataType == 0x0F) {
  //     generalCallback.mutualFail(generalData.errorCode);
  //   } else {
  //     generalCallback.result(generalData.result == 0 ? true : false);
  //   }
  // }

  // // Future<void> setOpsBinding(
  // //     EABleBindInfo eaBleBindInfo, GeneralCallback generalCallback) async {
  // //   BindInfo bindInfo = BindInfo();
  // //   if (eaBleBindInfo.e_ops == BindingOps.qr_code_begin) {
  // //     bindInfo.e_ops = 1;
  // //   } else if (eaBleBindInfo.e_ops == BindingOps.normal_begin) {
  // //     bindInfo.e_ops = 0;
  // //   } else if (eaBleBindInfo.e_ops == BindingOps.end) {
  // //     bindInfo.e_ops = 2;
  // //   }
  // //   bindInfo.user_id = eaBleBindInfo.user_id;
  // //   final String txt = await _channel.invokeMethod(
  // //       "setOpsBinding", convert.jsonEncode(bindInfo));
  // //   Map<String, dynamic> info = convert.jsonDecode(txt);
  // //   GeneralData generalData = GeneralData.fromJson(info);
  // //   if (generalData.dataType == 0x0F) {
  // //     generalCallback.mutualFail(generalData.errorCode);
  // //   } else {
  // //     generalCallback.result(generalData.result == 0 ? true : false);
  // //   }
  // // }

  // Future<void> setScreenBrightness(
  //     int screenLight, GeneralCallback generalCallback) async {
  //   final String txt =
  //       await _channel.invokeMethod("setScreenBrightness", screenLight);
  //   Map<String, dynamic> info = convert.jsonDecode(txt);
  //   GeneralData generalData = GeneralData.fromJson(info);
  //   if (generalData.dataType == 0x0F) {
  //     generalCallback.mutualFail(generalData.errorCode);
  //   } else {
  //     generalCallback.result(generalData.result == 0 ? true : false);
  //   }
  // }

  // Future<void> setBlackScreenTimeout(
  //     int restTime, GeneralCallback generalCallback) async {
  //   final String txt =
  //       await _channel.invokeMethod("setBlackScreenTimeout", restTime);
  //   Map<String, dynamic> info = convert.jsonDecode(txt);
  //   GeneralData generalData = GeneralData.fromJson(info);
  //   if (generalData.dataType == 0x0F) {
  //     generalCallback.mutualFail(generalData.errorCode);
  //   } else {
  //     generalCallback.result(generalData.result == 0 ? true : false);
  //   }
  // }

  // // Future<void> setDevLanguage(EABleDeviceLanguage eaBleDeviceLanguage,
  // //     GeneralCallback generalCallback) async {
  // //   int language = 0;
  // //   if (eaBleDeviceLanguage.e_type == LanguageType.default_type) {
  // //     language = 0;
  // //   } else if (eaBleDeviceLanguage.e_type == LanguageType.english) {
  // //     language = 1;
  // //   } else if (eaBleDeviceLanguage.e_type == LanguageType.chinese_simplifid) {
  // //     language = 2;
  // //   } else if (eaBleDeviceLanguage.e_type == LanguageType.chinese_traditional) {
  // //     language = 3;
  // //   } else if (eaBleDeviceLanguage.e_type == LanguageType.korean) {
  // //     language = 4;
  // //   } else if (eaBleDeviceLanguage.e_type == LanguageType.thai) {
  // //     language = 5;
  // //   } else if (eaBleDeviceLanguage.e_type == LanguageType.japanese) {
  // //     language = 6;
  // //   } else if (eaBleDeviceLanguage.e_type == LanguageType.spanish) {
  // //     language = 7;
  // //   } else if (eaBleDeviceLanguage.e_type == LanguageType.francais) {
  // //     language = 8;
  // //   } else if (eaBleDeviceLanguage.e_type == LanguageType.deutsch) {
  // //     language = 9;
  // //   } else if (eaBleDeviceLanguage.e_type == LanguageType.italiano) {
  // //     language = 10;
  // //   } else if (eaBleDeviceLanguage.e_type == LanguageType.polski) {
  // //     language = 11;
  // //   } else if (eaBleDeviceLanguage.e_type == LanguageType.portuguese) {
  // //     language = 12;
  // //   } else if (eaBleDeviceLanguage.e_type == LanguageType.russian) {
  // //     language = 13;
  // //   } else if (eaBleDeviceLanguage.e_type == LanguageType.dutch) {
  // //     language = 14;
  // //   } else if (eaBleDeviceLanguage.e_type == LanguageType.arabic) {
  // //     language = 15;
  // //   } else if (eaBleDeviceLanguage.e_type == LanguageType.greek) {
  // //     language = 16;
  // //   } else if (eaBleDeviceLanguage.e_type == LanguageType.hebrew) {
  // //     language = 17;
  // //   } else if (eaBleDeviceLanguage.e_type == LanguageType.swedish) {
  // //     language = 18;
  // //   } else if (eaBleDeviceLanguage.e_type == LanguageType.unknown) {
  // //     language = 19;
  // //   }
  // //   final String txt = await _channel.invokeMethod("setDevLanguage", language);
  // //   Map<String, dynamic> info = convert.jsonDecode(txt);
  // //   GeneralData generalData = GeneralData.fromJson(info);
  // //   if (generalData.dataType == 0x0F) {
  // //     generalCallback.mutualFail(generalData.errorCode);
  // //   } else {
  // //     generalCallback.result(generalData.result == 0 ? true : false);
  // //   }
  // // }

  // Future<void> setUnifiedUnit(
  //     EABleDevUnit eaBleDevUnit, GeneralCallback generalCallback) async {
  //   int unit = 0;
  //   if (eaBleDevUnit.e_format == UnitFormat.metric) {
  //     unit = 0;
  //   } else {
  //     unit = 1;
  //   }
  //   final String txt = await _channel.invokeMethod("setUnifiedUnit", unit);
  //   Map<String, dynamic> info = convert.jsonDecode(txt);
  //   GeneralData generalData = GeneralData.fromJson(info);
  //   if (generalData.dataType == 0x0F) {
  //     generalCallback.mutualFail(generalData.errorCode);
  //   } else {
  //     generalCallback.result(generalData.result == 0 ? true : false);
  //   }
  // }

  // Future<void> setNotDisturb(
  //     EABleNotDisturb eaBleNotDisturb, GeneralCallback generalCallback) async {
  //   NotDisturb notDisturb = NotDisturb();
  //   notDisturb.sw = eaBleNotDisturb.sw;
  //   notDisturb.end_hour = eaBleNotDisturb.end_hour;
  //   notDisturb.end_minute = eaBleNotDisturb.end_minute;
  //   notDisturb.begin_hour = eaBleNotDisturb.begin_hour;
  //   notDisturb.begin_minute = eaBleNotDisturb.begin_minute;
  //   final String txt = await _channel.invokeMethod(
  //       "setNotDisturb", convert.jsonEncode(notDisturb));
  //   Map<String, dynamic> info = convert.jsonDecode(txt);
  //   GeneralData generalData = GeneralData.fromJson(info);
  //   if (generalData.dataType == 0x0F) {
  //     generalCallback.mutualFail(generalData.errorCode);
  //   } else {
  //     generalCallback.result(generalData.result == 0 ? true : false);
  //   }
  // }

  // Future<void> setDailyGoal(
  //     EABleDailyGoal eaBleDailyGoal, GeneralCallback generalCallback) async {
  //   DailyGoal dailyGoal = DailyGoal();
  //   dailyGoal.s_sleep_sw = eaBleDailyGoal.s_sleep.sw;
  //   dailyGoal.s_sleep_goal = eaBleDailyGoal.s_sleep.goal;
  //   dailyGoal.s_duration_goal = eaBleDailyGoal.s_duration.goal;
  //   dailyGoal.s_duration_sw = eaBleDailyGoal.s_duration.sw;
  //   dailyGoal.s_distance_goal = eaBleDailyGoal.s_distance.goal;
  //   dailyGoal.s_distance_sw = eaBleDailyGoal.s_distance.sw;
  //   dailyGoal.s_calorie_goal = eaBleDailyGoal.s_calorie.goal;
  //   dailyGoal.s_calorie_sw = eaBleDailyGoal.s_calorie.sw;
  //   dailyGoal.s_step_sw = eaBleDailyGoal.s_step.sw;
  //   dailyGoal.s_step_goal = eaBleDailyGoal.s_step.goal;
  //   final String txt = await _channel.invokeMethod(
  //       "setDailyGoal", convert.jsonEncode(dailyGoal));
  //   Map<String, dynamic> info = convert.jsonDecode(txt);
  //   GeneralData generalData = GeneralData.fromJson(info);
  //   if (generalData.dataType == 0x0F) {
  //     generalCallback.mutualFail(generalData.errorCode);
  //   } else {
  //     generalCallback.result(generalData.result == 0 ? true : false);
  //   }
  // }

  // Future<void> setAutoSleepCheck(EABleAutoCheckSleep eaBleAutoCheckSleep,
  //     GeneralCallback generalCallback) async {
  //   AutoCheckSleep autoCheckSleep = AutoCheckSleep();
  //   autoCheckSleep.begin_hour = eaBleAutoCheckSleep.begin_hour;
  //   autoCheckSleep.begin_minute = eaBleAutoCheckSleep.begin_minute;
  //   autoCheckSleep.end_hour = eaBleAutoCheckSleep.end_hour;
  //   autoCheckSleep.end_minute = eaBleAutoCheckSleep.end_minute;
  //   autoCheckSleep.week_cycle_bit = eaBleAutoCheckSleep.week_cycle_bit;
  //   final String txt = await _channel.invokeMethod(
  //       "setAutoSleepCheck", convert.jsonEncode(autoCheckSleep));
  //   Map<String, dynamic> info = convert.jsonDecode(txt);
  //   GeneralData generalData = GeneralData.fromJson(info);
  //   if (generalData.dataType == 0x0F) {
  //     generalCallback.mutualFail(generalData.errorCode);
  //   } else {
  //     generalCallback.result(generalData.result == 0 ? true : false);
  //   }
  // }

  // Future<void> setHeartRateIntervalTime(
  //     int intervalTime, GeneralCallback generalCallback) async {
  //   final String txt =
  //       await _channel.invokeMethod("setHeartRateIntervalTime", intervalTime);
  //   Map<String, dynamic> info = convert.jsonDecode(txt);
  //   GeneralData generalData = GeneralData.fromJson(info);
  //   if (generalData.dataType == 0x0F) {
  //     generalCallback.mutualFail(generalData.errorCode);
  //   } else {
  //     generalCallback.result(generalData.result == 0 ? true : false);
  //   }
  // }

  // Future<void> setSitCheck(EABleSedentariness eaBleSedentariness,
  //     GeneralCallback generalCallback) async {
  //   Sedentariness sedentariness = Sedentariness();
  //   sedentariness.interval = eaBleSedentariness.interval;
  //   sedentariness.step_threshold = eaBleSedentariness.step_threshold;
  //   sedentariness.begin_minute = eaBleSedentariness.begin_minute;
  //   sedentariness.week_cycle_bit = eaBleSedentariness.week_cycle_bit;
  //   sedentariness.end_hour = eaBleSedentariness.end_hour;
  //   sedentariness.end_minute = eaBleSedentariness.end_minute;
  //   sedentariness.begin_hour = eaBleSedentariness.begin_hour;
  //   final String txt = await _channel.invokeMethod(
  //       "setSitCheck", convert.jsonEncode(sedentariness));
  //   Map<String, dynamic> info = convert.jsonDecode(txt);
  //   GeneralData generalData = GeneralData.fromJson(info);
  //   if (generalData.dataType == 0x0F) {
  //     generalCallback.mutualFail(generalData.errorCode);
  //   } else {
  //     generalCallback.result(generalData.result == 0 ? true : false);
  //   }
  // }

  // Future<void> pushInfo2Watch(EABleSocialContact eaBleSocialContact,
  //     GeneralCallback generalCallback) async {
  //   SocialContact socialContact = SocialContact();
  //   socialContact.date = eaBleSocialContact.date;
  //   socialContact.content = eaBleSocialContact.content;
  //   socialContact.title = eaBleSocialContact.title;
  //   socialContact.id = eaBleSocialContact.id;
  //   if (eaBleSocialContact.eType == SocialContactType.retain) {
  //     socialContact.eType = 0;
  //   } else if (eaBleSocialContact.eType == SocialContactType.incomingcall) {
  //     socialContact.eType = 1;
  //   } else if (eaBleSocialContact.eType == SocialContactType.missedcall) {
  //     socialContact.eType = 2;
  //   } else if (eaBleSocialContact.eType == SocialContactType.social) {
  //     socialContact.eType = 4;
  //   } else if (eaBleSocialContact.eType == SocialContactType.schedule) {
  //     socialContact.eType = 5;
  //   } else if (eaBleSocialContact.eType == SocialContactType.email) {
  //     socialContact.eType = 6;
  //   } else if (eaBleSocialContact.eType == SocialContactType.sms) {
  //     socialContact.eType = 13;
  //   } else if (eaBleSocialContact.eType == SocialContactType.unknow) {
  //     socialContact.eType = 100;
  //   } else if (eaBleSocialContact.eType == SocialContactType.wechat) {
  //     socialContact.eType = 101;
  //   } else if (eaBleSocialContact.eType == SocialContactType.qq) {
  //     socialContact.eType = 102;
  //   } else if (eaBleSocialContact.eType == SocialContactType.facebook) {
  //     socialContact.eType = 103;
  //   } else if (eaBleSocialContact.eType == SocialContactType.twitter) {
  //     socialContact.eType = 104;
  //   } else if (eaBleSocialContact.eType == SocialContactType.messenger) {
  //     socialContact.eType = 105;
  //   } else if (eaBleSocialContact.eType == SocialContactType.hangouts) {
  //     socialContact.eType = 106;
  //   } else if (eaBleSocialContact.eType == SocialContactType.gmail) {
  //     socialContact.eType = 107;
  //   } else if (eaBleSocialContact.eType == SocialContactType.viber) {
  //     socialContact.eType = 108;
  //   } else if (eaBleSocialContact.eType == SocialContactType.snapchat) {
  //     socialContact.eType = 109;
  //   } else if (eaBleSocialContact.eType == SocialContactType.whatsApp) {
  //     socialContact.eType = 110;
  //   } else if (eaBleSocialContact.eType == SocialContactType.instagram) {
  //     socialContact.eType = 111;
  //   } else if (eaBleSocialContact.eType == SocialContactType.linkedin) {
  //     socialContact.eType = 112;
  //   } else if (eaBleSocialContact.eType == SocialContactType.line) {
  //     socialContact.eType = 113;
  //   } else if (eaBleSocialContact.eType == SocialContactType.skype) {
  //     socialContact.eType = 114;
  //   } else if (eaBleSocialContact.eType == SocialContactType.booking) {
  //     socialContact.eType = 115;
  //   } else if (eaBleSocialContact.eType == SocialContactType.airbnb) {
  //     socialContact.eType = 116;
  //   } else if (eaBleSocialContact.eType == SocialContactType.flipboard) {
  //     socialContact.eType = 117;
  //   } else if (eaBleSocialContact.eType == SocialContactType.spotify) {
  //     socialContact.eType = 118;
  //   } else if (eaBleSocialContact.eType == SocialContactType.pandora) {
  //     socialContact.eType = 119;
  //   } else if (eaBleSocialContact.eType == SocialContactType.telegram) {
  //     socialContact.eType = 120;
  //   } else if (eaBleSocialContact.eType == SocialContactType.dropbox) {
  //     socialContact.eType = 121;
  //   } else if (eaBleSocialContact.eType == SocialContactType.waze) {
  //     socialContact.eType = 122;
  //   } else if (eaBleSocialContact.eType == SocialContactType.lift) {
  //     socialContact.eType = 123;
  //   } else if (eaBleSocialContact.eType == SocialContactType.slack) {
  //     socialContact.eType = 124;
  //   } else if (eaBleSocialContact.eType == SocialContactType.shazam) {
  //     socialContact.eType = 125;
  //   } else if (eaBleSocialContact.eType == SocialContactType.deliveroo) {
  //     socialContact.eType = 126;
  //   } else if (eaBleSocialContact.eType == SocialContactType.kakaotalk) {
  //     socialContact.eType = 127;
  //   } else if (eaBleSocialContact.eType == SocialContactType.pinterest) {
  //     socialContact.eType = 128;
  //   } else if (eaBleSocialContact.eType == SocialContactType.tumblr) {
  //     socialContact.eType = 129;
  //   } else if (eaBleSocialContact.eType == SocialContactType.vk) {
  //     socialContact.eType = 130;
  //   } else if (eaBleSocialContact.eType == SocialContactType.youtube) {
  //     socialContact.eType = 131;
  //   } else if (eaBleSocialContact.eType == SocialContactType.outlook) {
  //     socialContact.eType = 132;
  //   }
  //   if (eaBleSocialContact.e_ops == SocialContactOps.add) {
  //     socialContact.e_ops = 0;
  //   } else if (eaBleSocialContact.e_ops == SocialContactOps.edit) {
  //     socialContact.e_ops = 1;
  //   } else if (eaBleSocialContact.e_ops == SocialContactOps.del) {
  //     socialContact.e_ops = 2;
  //   } else if (eaBleSocialContact.e_ops == SocialContactOps.del_type) {
  //     socialContact.e_ops = 3;
  //   } else if (eaBleSocialContact.e_ops == SocialContactOps.del_all) {
  //     socialContact.e_ops = 4;
  //   }
  //   final String txt = await _channel.invokeMethod(
  //       "pushInfo2Watch", convert.jsonEncode(socialContact));
  //   Map<String, dynamic> info = convert.jsonDecode(txt);
  //   GeneralData generalData = GeneralData.fromJson(info);
  //   if (generalData.dataType == 0x0F) {
  //     generalCallback.mutualFail(generalData.errorCode);
  //   } else {
  //     generalCallback.result(generalData.result == 0 ? true : false);
  //   }
  // }

  // Future<void> setWeather(
  //     EABleWeather eaBleWeather, GeneralCallback generalCallback) async {
  //   TempWeather tempWeather = TempWeather();
  //   tempWeather.current_temperature = eaBleWeather.current_temperature;
  //   tempWeather.place = eaBleWeather.place;
  //   if (eaBleWeather.temperatureUnit == TemperatureUnit.centigrade) {
  //     tempWeather.temperatureUnit = 0;
  //   } else {
  //     tempWeather.temperatureUnit = 1;
  //   }
  //   if (eaBleWeather.s_day.isNotEmpty) {
  //     List<WeatherItem> itemList = <WeatherItem>[];
  //     tempWeather.s_day = itemList;
  //     for (int i = 0; i < eaBleWeather.s_day.length; i++) {
  //       WeatherItem weatherItem = WeatherItem();
  //       weatherItem.min_wind_power =
  //           eaBleWeather.s_day.elementAt(i).min_wind_power;
  //       weatherItem.max_wind_power =
  //           eaBleWeather.s_day.elementAt(i).max_wind_power;
  //       weatherItem.sunset_timestamp =
  //           eaBleWeather.s_day.elementAt(i).sunset_timestamp;
  //       weatherItem.sunrise_timestamp =
  //           eaBleWeather.s_day.elementAt(i).sunrise_timestamp;
  //       weatherItem.min_temperature =
  //           eaBleWeather.s_day.elementAt(i).min_temperature;
  //       weatherItem.max_temperature =
  //           eaBleWeather.s_day.elementAt(i).max_temperature;
  //       weatherItem.cloudiness = eaBleWeather.s_day.elementAt(i).cloudiness;
  //       weatherItem.air_humidity = eaBleWeather.s_day.elementAt(i).air_humidity;
  //       if (eaBleWeather.s_day.elementAt(i).e_day_type == WeatherType.clear) {
  //         weatherItem.e_day_type = 0;
  //       } else if (eaBleWeather.s_day.elementAt(i).e_day_type ==
  //           WeatherType.cloudy) {
  //         weatherItem.e_day_type = 1;
  //       } else if (eaBleWeather.s_day.elementAt(i).e_day_type ==
  //           WeatherType.gloomy) {
  //         weatherItem.e_day_type = 2;
  //       } else if (eaBleWeather.s_day.elementAt(i).e_day_type ==
  //           WeatherType.drizzle) {
  //         weatherItem.e_day_type = 3;
  //       } else if (eaBleWeather.s_day.elementAt(i).e_day_type ==
  //           WeatherType.moderate_rain) {
  //         weatherItem.e_day_type = 4;
  //       } else if (eaBleWeather.s_day.elementAt(i).e_day_type ==
  //           WeatherType.thunderstorm) {
  //         weatherItem.e_day_type = 5;
  //       } else if (eaBleWeather.s_day.elementAt(i).e_day_type ==
  //           WeatherType.heavy_rain) {
  //         weatherItem.e_day_type = 6;
  //       } else if (eaBleWeather.s_day.elementAt(i).e_day_type ==
  //           WeatherType.sleet) {
  //         weatherItem.e_day_type = 7;
  //       } else if (eaBleWeather.s_day.elementAt(i).e_day_type ==
  //           WeatherType.light_snow) {
  //         weatherItem.e_day_type = 8;
  //       } else if (eaBleWeather.s_day.elementAt(i).e_day_type ==
  //           WeatherType.moderate_snow) {
  //         weatherItem.e_day_type = 9;
  //       } else if (eaBleWeather.s_day.elementAt(i).e_day_type ==
  //           WeatherType.heavy_snow) {
  //         weatherItem.e_day_type = 10;
  //       }
  //       if (eaBleWeather.s_day.elementAt(i).e_night_type == WeatherType.clear) {
  //         weatherItem.e_night_type = 0;
  //       } else if (eaBleWeather.s_day.elementAt(i).e_night_type ==
  //           WeatherType.cloudy) {
  //         weatherItem.e_night_type = 1;
  //       } else if (eaBleWeather.s_day.elementAt(i).e_night_type ==
  //           WeatherType.gloomy) {
  //         weatherItem.e_night_type = 2;
  //       } else if (eaBleWeather.s_day.elementAt(i).e_night_type ==
  //           WeatherType.drizzle) {
  //         weatherItem.e_night_type = 3;
  //       } else if (eaBleWeather.s_day.elementAt(i).e_night_type ==
  //           WeatherType.moderate_rain) {
  //         weatherItem.e_night_type = 4;
  //       } else if (eaBleWeather.s_day.elementAt(i).e_night_type ==
  //           WeatherType.thunderstorm) {
  //         weatherItem.e_night_type = 5;
  //       } else if (eaBleWeather.s_day.elementAt(i).e_night_type ==
  //           WeatherType.heavy_rain) {
  //         weatherItem.e_night_type = 6;
  //       } else if (eaBleWeather.s_day.elementAt(i).e_night_type ==
  //           WeatherType.sleet) {
  //         weatherItem.e_night_type = 7;
  //       } else if (eaBleWeather.s_day.elementAt(i).e_night_type ==
  //           WeatherType.light_snow) {
  //         weatherItem.e_night_type = 8;
  //       } else if (eaBleWeather.s_day.elementAt(i).e_night_type ==
  //           WeatherType.moderate_snow) {
  //         weatherItem.e_night_type = 9;
  //       } else if (eaBleWeather.s_day.elementAt(i).e_night_type ==
  //           WeatherType.heavy_snow) {
  //         weatherItem.e_night_type = 10;
  //       }
  //       if (eaBleWeather.s_day.elementAt(i).e_air == AirQuality.excellent) {
  //         weatherItem.e_air = 0;
  //       } else if (eaBleWeather.s_day.elementAt(i).e_air == AirQuality.good) {
  //         weatherItem.e_air = 1;
  //       } else if (eaBleWeather.s_day.elementAt(i).e_air == AirQuality.bad) {
  //         weatherItem.e_air = 2;
  //       }
  //       if (eaBleWeather.s_day.elementAt(i).e_rays == RaysLevel.weak) {
  //         weatherItem.e_rays = 0;
  //       } else if (eaBleWeather.s_day.elementAt(i).e_rays == RaysLevel.medium) {
  //         weatherItem.e_rays = 1;
  //       } else if (eaBleWeather.s_day.elementAt(i).e_rays == RaysLevel.strong) {
  //         weatherItem.e_rays = 2;
  //       } else if (eaBleWeather.s_day.elementAt(i).e_rays ==
  //           RaysLevel.very_strong) {
  //         weatherItem.e_rays = 3;
  //       } else if (eaBleWeather.s_day.elementAt(i).e_rays ==
  //           RaysLevel.super_strong) {
  //         weatherItem.e_rays = 4;
  //       }
  //       if (eaBleWeather.s_day.elementAt(i).e_moon == Moon.new_moon) {
  //         weatherItem.e_moon = 0;
  //       } else if (eaBleWeather.s_day.elementAt(i).e_moon ==
  //           Moon.waxing_crescent_moon) {
  //         weatherItem.e_moon = 1;
  //       } else if (eaBleWeather.s_day.elementAt(i).e_moon ==
  //           Moon.quarter_moon) {
  //         weatherItem.e_moon = 2;
  //       } else if (eaBleWeather.s_day.elementAt(i).e_moon == Moon.half_moon_1) {
  //         weatherItem.e_moon = 3;
  //       } else if (eaBleWeather.s_day.elementAt(i).e_moon ==
  //           Moon.waxing_gibbous_moon) {
  //         weatherItem.e_moon = 4;
  //       } else if (eaBleWeather.s_day.elementAt(i).e_moon == Moon.full_moon) {
  //         weatherItem.e_moon = 5;
  //       } else if (eaBleWeather.s_day.elementAt(i).e_moon ==
  //           Moon.waning_gibbous_moon) {
  //         weatherItem.e_moon = 6;
  //       } else if (eaBleWeather.s_day.elementAt(i).e_moon == Moon.half_moon_2) {
  //         weatherItem.e_moon = 7;
  //       } else if (eaBleWeather.s_day.elementAt(i).e_moon ==
  //           Moon.last_quarter_moon) {
  //         weatherItem.e_moon = 8;
  //       } else if (eaBleWeather.s_day.elementAt(i).e_moon ==
  //           Moon.waning_crescent_moon) {
  //         weatherItem.e_moon = 9;
  //       }
  //       itemList.add(weatherItem);
  //     }
  //   }
  //   final String txt = await _channel.invokeMethod(
  //       "setWeather", convert.jsonEncode(tempWeather));
  //   Map<String, dynamic> info = convert.jsonDecode(txt);
  //   GeneralData generalData = GeneralData.fromJson(info);
  //   if (generalData.dataType == 0x0F) {
  //     generalCallback.mutualFail(generalData.errorCode);
  //   } else {
  //     generalCallback.result(generalData.result == 0 ? true : false);
  //   }
  // }

  // Future<void> setAncsSwitch(
  //     EABleAncsSw eaBleAncsSw, GeneralCallback generalCallback) async {
  //   TempAncsSw tempAncsSw = TempAncsSw();
  //   tempAncsSw.s_schedule_sw = eaBleAncsSw.s_schedule.sw;
  //   tempAncsSw.s_email_sw = eaBleAncsSw.s_email.sw;
  //   tempAncsSw.s_social_sw = eaBleAncsSw.s_social.sw;
  //   tempAncsSw.s_sms_sw = eaBleAncsSw.s_sms.sw;
  //   tempAncsSw.s_missedcall_sw = eaBleAncsSw.s_missedcall.sw;
  //   tempAncsSw.s_incomingcall_sw = eaBleAncsSw.s_incomingcall.sw;
  //   if (eaBleAncsSw.s_incomingcall.e_action == CommonAction.no_action) {
  //     tempAncsSw.s_incomingcall_action = 0;
  //   } else if (eaBleAncsSw.s_incomingcall.e_action ==
  //       CommonAction.one_long_vibration) {
  //     tempAncsSw.s_incomingcall_action = 1;
  //   } else if (eaBleAncsSw.s_incomingcall.e_action ==
  //       CommonAction.one_short_vibration) {
  //     tempAncsSw.s_incomingcall_action = 2;
  //   } else if (eaBleAncsSw.s_incomingcall.e_action ==
  //       CommonAction.two_long_vibration) {
  //     tempAncsSw.s_incomingcall_action = 3;
  //   } else if (eaBleAncsSw.s_incomingcall.e_action ==
  //       CommonAction.two_short_vibration) {
  //     tempAncsSw.s_incomingcall_action = 4;
  //   } else if (eaBleAncsSw.s_incomingcall.e_action ==
  //       CommonAction.long_vibration) {
  //     tempAncsSw.s_incomingcall_action = 5;
  //   } else if (eaBleAncsSw.s_incomingcall.e_action ==
  //       CommonAction.long_short_vibration) {
  //     tempAncsSw.s_incomingcall_action = 6;
  //   } else if (eaBleAncsSw.s_incomingcall.e_action == CommonAction.one_ring) {
  //     tempAncsSw.s_incomingcall_action = 7;
  //   } else if (eaBleAncsSw.s_incomingcall.e_action == CommonAction.two_ring) {
  //     tempAncsSw.s_incomingcall_action = 8;
  //   } else if (eaBleAncsSw.s_incomingcall.e_action == CommonAction.ring) {
  //     tempAncsSw.s_incomingcall_action = 9;
  //   } else if (eaBleAncsSw.s_incomingcall.e_action ==
  //       CommonAction.one_vibration_ring) {
  //     tempAncsSw.s_incomingcall_action = 10;
  //   } else if (eaBleAncsSw.s_incomingcall.e_action ==
  //       CommonAction.vibration_ring) {
  //     tempAncsSw.s_incomingcall_action = 11;
  //   }
  //   if (eaBleAncsSw.s_missedcall.e_action == CommonAction.no_action) {
  //     tempAncsSw.s_missedcall_action = 0;
  //   } else if (eaBleAncsSw.s_missedcall.e_action ==
  //       CommonAction.one_long_vibration) {
  //     tempAncsSw.s_missedcall_action = 1;
  //   } else if (eaBleAncsSw.s_missedcall.e_action ==
  //       CommonAction.one_short_vibration) {
  //     tempAncsSw.s_missedcall_action = 2;
  //   } else if (eaBleAncsSw.s_missedcall.e_action ==
  //       CommonAction.two_long_vibration) {
  //     tempAncsSw.s_missedcall_action = 3;
  //   } else if (eaBleAncsSw.s_missedcall.e_action ==
  //       CommonAction.two_short_vibration) {
  //     tempAncsSw.s_missedcall_action = 4;
  //   } else if (eaBleAncsSw.s_missedcall.e_action ==
  //       CommonAction.long_vibration) {
  //     tempAncsSw.s_missedcall_action = 5;
  //   } else if (eaBleAncsSw.s_missedcall.e_action ==
  //       CommonAction.long_short_vibration) {
  //     tempAncsSw.s_missedcall_action = 6;
  //   } else if (eaBleAncsSw.s_missedcall.e_action == CommonAction.one_ring) {
  //     tempAncsSw.s_missedcall_action = 7;
  //   } else if (eaBleAncsSw.s_missedcall.e_action == CommonAction.two_ring) {
  //     tempAncsSw.s_missedcall_action = 8;
  //   } else if (eaBleAncsSw.s_missedcall.e_action == CommonAction.ring) {
  //     tempAncsSw.s_missedcall_action = 9;
  //   } else if (eaBleAncsSw.s_missedcall.e_action ==
  //       CommonAction.one_vibration_ring) {
  //     tempAncsSw.s_missedcall_action = 10;
  //   } else if (eaBleAncsSw.s_missedcall.e_action ==
  //       CommonAction.vibration_ring) {
  //     tempAncsSw.s_missedcall_action = 11;
  //   }
  //   if (eaBleAncsSw.s_sms.e_action == CommonAction.no_action) {
  //     tempAncsSw.s_sms_action = 0;
  //   } else if (eaBleAncsSw.s_sms.e_action == CommonAction.one_long_vibration) {
  //     tempAncsSw.s_sms_action = 1;
  //   } else if (eaBleAncsSw.s_sms.e_action == CommonAction.one_short_vibration) {
  //     tempAncsSw.s_sms_action = 2;
  //   } else if (eaBleAncsSw.s_sms.e_action == CommonAction.two_long_vibration) {
  //     tempAncsSw.s_sms_action = 3;
  //   } else if (eaBleAncsSw.s_sms.e_action == CommonAction.two_short_vibration) {
  //     tempAncsSw.s_sms_action = 4;
  //   } else if (eaBleAncsSw.s_sms.e_action == CommonAction.long_vibration) {
  //     tempAncsSw.s_sms_action = 5;
  //   } else if (eaBleAncsSw.s_sms.e_action ==
  //       CommonAction.long_short_vibration) {
  //     tempAncsSw.s_sms_action = 6;
  //   } else if (eaBleAncsSw.s_sms.e_action == CommonAction.one_ring) {
  //     tempAncsSw.s_sms_action = 7;
  //   } else if (eaBleAncsSw.s_sms.e_action == CommonAction.two_ring) {
  //     tempAncsSw.s_sms_action = 8;
  //   } else if (eaBleAncsSw.s_sms.e_action == CommonAction.ring) {
  //     tempAncsSw.s_sms_action = 9;
  //   } else if (eaBleAncsSw.s_sms.e_action == CommonAction.one_vibration_ring) {
  //     tempAncsSw.s_sms_action = 10;
  //   } else if (eaBleAncsSw.s_sms.e_action == CommonAction.vibration_ring) {
  //     tempAncsSw.s_sms_action = 11;
  //   }
  //   if (eaBleAncsSw.s_social.e_action == CommonAction.no_action) {
  //     tempAncsSw.s_social_action = 0;
  //   } else if (eaBleAncsSw.s_social.e_action ==
  //       CommonAction.one_long_vibration) {
  //     tempAncsSw.s_social_action = 1;
  //   } else if (eaBleAncsSw.s_social.e_action ==
  //       CommonAction.one_short_vibration) {
  //     tempAncsSw.s_social_action = 2;
  //   } else if (eaBleAncsSw.s_social.e_action ==
  //       CommonAction.two_long_vibration) {
  //     tempAncsSw.s_social_action = 3;
  //   } else if (eaBleAncsSw.s_social.e_action ==
  //       CommonAction.two_short_vibration) {
  //     tempAncsSw.s_social_action = 4;
  //   } else if (eaBleAncsSw.s_social.e_action == CommonAction.long_vibration) {
  //     tempAncsSw.s_social_action = 5;
  //   } else if (eaBleAncsSw.s_social.e_action ==
  //       CommonAction.long_short_vibration) {
  //     tempAncsSw.s_social_action = 6;
  //   } else if (eaBleAncsSw.s_social.e_action == CommonAction.one_ring) {
  //     tempAncsSw.s_social_action = 7;
  //   } else if (eaBleAncsSw.s_social.e_action == CommonAction.two_ring) {
  //     tempAncsSw.s_social_action = 8;
  //   } else if (eaBleAncsSw.s_social.e_action == CommonAction.ring) {
  //     tempAncsSw.s_social_action = 9;
  //   } else if (eaBleAncsSw.s_social.e_action ==
  //       CommonAction.one_vibration_ring) {
  //     tempAncsSw.s_social_action = 10;
  //   } else if (eaBleAncsSw.s_social.e_action == CommonAction.vibration_ring) {
  //     tempAncsSw.s_social_action = 11;
  //   }
  //   if (eaBleAncsSw.s_email.e_action == CommonAction.no_action) {
  //     tempAncsSw.s_email_action = 0;
  //   } else if (eaBleAncsSw.s_email.e_action ==
  //       CommonAction.one_long_vibration) {
  //     tempAncsSw.s_email_action = 1;
  //   } else if (eaBleAncsSw.s_email.e_action ==
  //       CommonAction.one_short_vibration) {
  //     tempAncsSw.s_email_action = 2;
  //   } else if (eaBleAncsSw.s_email.e_action ==
  //       CommonAction.two_long_vibration) {
  //     tempAncsSw.s_email_action = 3;
  //   } else if (eaBleAncsSw.s_email.e_action ==
  //       CommonAction.two_short_vibration) {
  //     tempAncsSw.s_email_action = 4;
  //   } else if (eaBleAncsSw.s_email.e_action == CommonAction.long_vibration) {
  //     tempAncsSw.s_email_action = 5;
  //   } else if (eaBleAncsSw.s_email.e_action ==
  //       CommonAction.long_short_vibration) {
  //     tempAncsSw.s_email_action = 6;
  //   } else if (eaBleAncsSw.s_email.e_action == CommonAction.one_ring) {
  //     tempAncsSw.s_email_action = 7;
  //   } else if (eaBleAncsSw.s_email.e_action == CommonAction.two_ring) {
  //     tempAncsSw.s_email_action = 8;
  //   } else if (eaBleAncsSw.s_email.e_action == CommonAction.ring) {
  //     tempAncsSw.s_email_action = 9;
  //   } else if (eaBleAncsSw.s_email.e_action ==
  //       CommonAction.one_vibration_ring) {
  //     tempAncsSw.s_email_action = 10;
  //   } else if (eaBleAncsSw.s_email.e_action == CommonAction.vibration_ring) {
  //     tempAncsSw.s_email_action = 11;
  //   }
  //   if (eaBleAncsSw.s_schedule.e_action == CommonAction.no_action) {
  //     tempAncsSw.s_schedule_action = 0;
  //   } else if (eaBleAncsSw.s_schedule.e_action ==
  //       CommonAction.one_long_vibration) {
  //     tempAncsSw.s_schedule_action = 1;
  //   } else if (eaBleAncsSw.s_schedule.e_action ==
  //       CommonAction.one_short_vibration) {
  //     tempAncsSw.s_schedule_action = 2;
  //   } else if (eaBleAncsSw.s_schedule.e_action ==
  //       CommonAction.two_long_vibration) {
  //     tempAncsSw.s_schedule_action = 3;
  //   } else if (eaBleAncsSw.s_schedule.e_action ==
  //       CommonAction.two_short_vibration) {
  //     tempAncsSw.s_schedule_action = 4;
  //   } else if (eaBleAncsSw.s_schedule.e_action == CommonAction.long_vibration) {
  //     tempAncsSw.s_schedule_action = 5;
  //   } else if (eaBleAncsSw.s_schedule.e_action ==
  //       CommonAction.long_short_vibration) {
  //     tempAncsSw.s_schedule_action = 6;
  //   } else if (eaBleAncsSw.s_schedule.e_action == CommonAction.one_ring) {
  //     tempAncsSw.s_schedule_action = 7;
  //   } else if (eaBleAncsSw.s_schedule.e_action == CommonAction.two_ring) {
  //     tempAncsSw.s_schedule_action = 8;
  //   } else if (eaBleAncsSw.s_schedule.e_action == CommonAction.ring) {
  //     tempAncsSw.s_schedule_action = 9;
  //   } else if (eaBleAncsSw.s_schedule.e_action ==
  //       CommonAction.one_vibration_ring) {
  //     tempAncsSw.s_schedule_action = 10;
  //   } else if (eaBleAncsSw.s_schedule.e_action == CommonAction.vibration_ring) {
  //     tempAncsSw.s_schedule_action = 11;
  //   }
  //   final String txt = await _channel.invokeMethod(
  //       "setAncsSwitch", convert.jsonEncode(tempAncsSw));
  //   Map<String, dynamic> info = convert.jsonDecode(txt);
  //   GeneralData generalData = GeneralData.fromJson(info);
  //   if (generalData.dataType == 0x0F) {
  //     generalCallback.mutualFail(generalData.errorCode);
  //   } else {
  //     generalCallback.result(generalData.result == 0 ? true : false);
  //   }
  // }

  // Future<void> setReminderOrder(EABleReminder eaBleReminder,
  //     EditAttentionCallback editAttentionCallback) async {
  //   Reminder reminder = Reminder();
  //   reminder.id = eaBleReminder.id;
  //   if (eaBleReminder.e_ops == ReminderOps.add) {
  //     reminder.e_ops = 0;
  //   } else if (eaBleReminder.e_ops == ReminderOps.edit) {
  //     reminder.e_ops = 1;
  //   }
  //   if (eaBleReminder.e_ops == ReminderOps.del) {
  //     reminder.e_ops = 2;
  //   }
  //   if (eaBleReminder.e_ops == ReminderOps.del_remind) {
  //     reminder.e_ops = 3;
  //   }
  //   if (eaBleReminder.e_ops == ReminderOps.del_alarm) {
  //     reminder.e_ops = 4;
  //   }
  //   if (eaBleReminder.e_ops == ReminderOps.del_remind_alarm) {
  //     reminder.e_ops = 5;
  //   } else {
  //     reminder.e_ops = 6;
  //   }
  //   if (eaBleReminder.s_index.isNotEmpty) {
  //     List<ReminderItem> itemList = <ReminderItem>[];
  //     reminder.tempList = itemList;
  //     for (int i = 0; i < eaBleReminder.s_index.length; i++) {
  //       ReminderItem reminderItem = ReminderItem();
  //       reminderItem.id = eaBleReminder.s_index.elementAt(i).id;
  //       reminderItem.hour = eaBleReminder.s_index.elementAt(i).hour;
  //       reminderItem.minute = eaBleReminder.s_index.elementAt(i).minute;
  //       reminderItem.year = eaBleReminder.s_index.elementAt(i).year;
  //       reminderItem.month = eaBleReminder.s_index.elementAt(i).month;
  //       reminderItem.day = eaBleReminder.s_index.elementAt(i).day;
  //       reminderItem.week_cycle_bit =
  //           eaBleReminder.s_index.elementAt(i).week_cycle_bit;
  //       reminderItem.sw = eaBleReminder.s_index.elementAt(i).sw;
  //       reminderItem.sec_sw = eaBleReminder.s_index.elementAt(i).sec_sw;
  //       reminderItem.sleep_duration =
  //           eaBleReminder.s_index.elementAt(i).sleep_duration;
  //       reminderItem.content = eaBleReminder.s_index.elementAt(i).content;
  //       if (eaBleReminder.s_index.elementAt(i).e_type == ReminderType.alarm) {
  //         reminderItem.e_type = 0;
  //       } else if (eaBleReminder.s_index.elementAt(i).e_type ==
  //           ReminderType.sleep) {
  //         reminderItem.e_type = 1;
  //       } else if (eaBleReminder.s_index.elementAt(i).e_type ==
  //           ReminderType.sport) {
  //         reminderItem.e_type = 2;
  //       } else if (eaBleReminder.s_index.elementAt(i).e_type ==
  //           ReminderType.drink) {
  //         reminderItem.e_type = 3;
  //       } else if (eaBleReminder.s_index.elementAt(i).e_type ==
  //           ReminderType.medicine) {
  //         reminderItem.e_type = 4;
  //       } else if (eaBleReminder.s_index.elementAt(i).e_type ==
  //           ReminderType.meeting) {
  //         reminderItem.e_type = 5;
  //       } else if (eaBleReminder.s_index.elementAt(i).e_type ==
  //           ReminderType.user) {
  //         reminderItem.e_type = 6;
  //       }
  //       if (eaBleReminder.s_index.elementAt(i).e_action ==
  //           CommonAction.no_action) {
  //         reminderItem.e_action = 0;
  //       } else if (eaBleReminder.s_index.elementAt(i).e_action ==
  //           CommonAction.one_long_vibration) {
  //         reminderItem.e_action = 1;
  //       } else if (eaBleReminder.s_index.elementAt(i).e_action ==
  //           CommonAction.one_short_vibration) {
  //         reminderItem.e_action = 2;
  //       } else if (eaBleReminder.s_index.elementAt(i).e_action ==
  //           CommonAction.two_long_vibration) {
  //         reminderItem.e_action = 3;
  //       } else if (eaBleReminder.s_index.elementAt(i).e_action ==
  //           CommonAction.two_short_vibration) {
  //         reminderItem.e_action = 4;
  //       } else if (eaBleReminder.s_index.elementAt(i).e_action ==
  //           CommonAction.long_vibration) {
  //         reminderItem.e_action = 5;
  //       } else if (eaBleReminder.s_index.elementAt(i).e_action ==
  //           CommonAction.long_short_vibration) {
  //         reminderItem.e_action = 6;
  //       } else if (eaBleReminder.s_index.elementAt(i).e_action ==
  //           CommonAction.one_ring) {
  //         reminderItem.e_action = 7;
  //       } else if (eaBleReminder.s_index.elementAt(i).e_action ==
  //           CommonAction.two_ring) {
  //         reminderItem.e_action = 8;
  //       } else if (eaBleReminder.s_index.elementAt(i).e_action ==
  //           CommonAction.ring) {
  //         reminderItem.e_action = 9;
  //       } else if (eaBleReminder.s_index.elementAt(i).e_action ==
  //           CommonAction.one_vibration_ring) {
  //         reminderItem.e_action = 10;
  //       } else if (eaBleReminder.s_index.elementAt(i).e_action ==
  //           CommonAction.vibration_ring) {
  //         reminderItem.e_action = 11;
  //       }
  //       itemList.add(reminderItem);
  //     }
  //   }
  //   final String txt = await _channel.invokeMethod(
  //       "setReminderOrder", convert.jsonEncode(reminder));
  //   Map<String, dynamic> info = convert.jsonDecode(txt);
  //   EditAttentionData editAttentionData = EditAttentionData.fromJson(info);
  //   if (editAttentionData.dataType == 0x0F) {
  //     editAttentionCallback.mutualFail(editAttentionData.errorCode);
  //   } else {
  //     EABleRemindRespond eaBleRemindRespond = EABleRemindRespond();
  //     if (editAttentionData.result == 0) {
  //       eaBleRemindRespond.remindRespondResult = RemindRespondResult.success;
  //     } else if (editAttentionData.result == 1) {
  //       eaBleRemindRespond.remindRespondResult = RemindRespondResult.fail;
  //     } else if (editAttentionData.result == 2) {
  //       eaBleRemindRespond.remindRespondResult = RemindRespondResult.mem_full;
  //     } else if (editAttentionData.result == 3) {
  //       eaBleRemindRespond.remindRespondResult =
  //           RemindRespondResult.time_conflict;
  //     }
  //     eaBleRemindRespond.id = editAttentionData.id;
  //     editAttentionCallback.editResult(eaBleRemindRespond);
  //   }
  // }

  // Future<void> setDistanceUnit(EABleDistanceFormat eaBleDistanceFormat,
  //     GeneralCallback generalCallback) async {
  //   int unit = 0;
  //   if (eaBleDistanceFormat.e_format == DistanceUnit.kilometre) {
  //     unit = 0;
  //   } else {
  //     unit = 1;
  //   }
  //   final String txt = await _channel.invokeMethod("setDistanceUnit", unit);
  //   Map<String, dynamic> info = convert.jsonDecode(txt);
  //   GeneralData generalData = GeneralData.fromJson(info);
  //   if (generalData.dataType == 0x0F) {
  //     generalCallback.mutualFail(generalData.errorCode);
  //   } else {
  //     generalCallback.result(generalData.result == 0 ? true : false);
  //   }
  // }

  // Future<void> setWeightUnit(EABleWeightFormat eaBleWeightFormat,
  //     GeneralCallback generalCallback) async {
  //   int unit = 0;
  //   if (eaBleWeightFormat.e_format == WeightUnit.kilogram) {
  //     unit = 0;
  //   } else {
  //     unit = 1;
  //   }
  //   final String txt = await _channel.invokeMethod("setWeightUnit", unit);
  //   Map<String, dynamic> info = convert.jsonDecode(txt);
  //   GeneralData generalData = GeneralData.fromJson(info);
  //   if (generalData.dataType == 0x0F) {
  //     generalCallback.mutualFail(generalData.errorCode);
  //   } else {
  //     generalCallback.result(generalData.result == 0 ? true : false);
  //   }
  // }

  // Future<void> setHeartRateLimit(
  //     EABleHr eaBleHr, GeneralCallback generalCallback) async {
  //   Hr hr = Hr();
  //   hr.sw = eaBleHr.sw;
  //   hr.min_hr = eaBleHr.min_hr;
  //   hr.max_hr = eaBleHr.max_hr;
  //   final String txt = await _channel.invokeMethod(
  //       "setHeartRateLimit", convert.jsonEncode(hr));
  //   Map<String, dynamic> info = convert.jsonDecode(txt);
  //   GeneralData generalData = GeneralData.fromJson(info);
  //   if (generalData.dataType == 0x0F) {
  //     generalCallback.mutualFail(generalData.errorCode);
  //   } else {
  //     generalCallback.result(generalData.result == 0 ? true : false);
  //   }
  // }

  // Future<void> setCaloriesSwitch(
  //     int on, GeneralCallback generalCallback) async {
  //   int sw = 0;
  //   if (on <= 0) {
  //     sw = 0;
  //   } else {
  //     sw = 1;
  //   }
  //   final String txt = await _channel.invokeMethod("setCaloriesSwitch", sw);
  //   Map<String, dynamic> info = convert.jsonDecode(txt);
  //   GeneralData generalData = GeneralData.fromJson(info);
  //   if (generalData.dataType == 0x0F) {
  //     generalCallback.mutualFail(generalData.errorCode);
  //   } else {
  //     generalCallback.result(generalData.result == 0 ? true : false);
  //   }
  // }

  // Future<void> setGesturesSwitch(
  //     int on, GeneralCallback generalCallback) async {
  //   int sw = 0;
  //   if (on <= 0) {
  //     sw = 0;
  //   } else {
  //     sw = 1;
  //   }
  //   final String txt = await _channel.invokeMethod("setGesturesSwitch", sw);
  //   Map<String, dynamic> info = convert.jsonDecode(txt);
  //   GeneralData generalData = GeneralData.fromJson(info);
  //   if (generalData.dataType == 0x0F) {
  //     generalCallback.mutualFail(generalData.errorCode);
  //   } else {
  //     generalCallback.result(generalData.result == 0 ? true : false);
  //   }
  // }

  // Future<void> requestSyncMotionData(MotionReportType motionReportType,
  //     GeneralCallback generalCallback) async {
  //   int report = 0;
  //   if (motionReportType == MotionReportType.sport_data_req) {
  //     report = 0;
  //   } else if (motionReportType == MotionReportType.sleep_data_req) {
  //     report = 1;
  //   } else if (motionReportType == MotionReportType.hr_data_req) {
  //     report = 2;
  //   } else if (motionReportType == MotionReportType.gps_data_req) {
  //     report = 3;
  //   } else if (motionReportType == MotionReportType.multi_sports_data_req) {
  //     report = 4;
  //   } else if (motionReportType == MotionReportType.blood_oxygen_data_req) {
  //     report = 5;
  //   } else if (motionReportType == MotionReportType.pressure_data_req) {
  //     report = 6;
  //   } else if (motionReportType == MotionReportType.step_freq_data_req) {
  //     report = 7;
  //   } else if (motionReportType == MotionReportType.pace_data_req) {
  //     report = 8;
  //   } else if (motionReportType == MotionReportType.resting_hr_data_req) {
  //     report = 9;
  //   }
  //   final String txt =
  //       await _channel.invokeMethod("requestSyncMotionData", report);
  //   Map<String, dynamic> info = convert.jsonDecode(txt);
  //   GeneralData generalData = GeneralData.fromJson(info);
  //   if (generalData.dataType == 0x0F) {
  //     generalCallback.mutualFail(generalData.errorCode);
  //   } else {
  //     generalCallback.result(generalData.result == 0 ? true : false);
  //   }
  // }

  // Future<void> setCombination(EABleCombination eaBleCombination,
  //     GeneralCallback generalCallback) async {
  //   Combination combination = Combination();
  //   combination.bat_level = eaBleCombination.bat_level;
  //   combination.auto_check_hr_sw = eaBleCombination.auto_check_hr_sw;
  //   combination.auto_pressure_sw = eaBleCombination.auto_pressure_sw;
  //   combination.auto_sedentariness_sw = eaBleCombination.auto_sedentariness_sw;
  //   combination.gestures_sw = eaBleCombination.gestures_sw;
  //   combination.not_disturb_sw = eaBleCombination.not_disturb_sw;
  //   combination.set_vibrate_intensity = eaBleCombination.set_vibrate_intensity;
  //   print(combination.set_vibrate_intensity.toString());
  //   combination.wf_id = eaBleCombination.wf_id;
  //   combination.user_wf_id = eaBleCombination.user_wf_id;
  //   if (eaBleCombination.e_vibrate_intensity == VibrationIntensity.light) {
  //     combination.e_vibrate_intensity = 0;
  //   } else if (eaBleCombination.e_vibrate_intensity ==
  //       VibrationIntensity.medium) {
  //     combination.e_vibrate_intensity = 1;
  //   } else if (eaBleCombination.e_vibrate_intensity ==
  //       VibrationIntensity.strong) {
  //     combination.e_vibrate_intensity = 2;
  //   }
  //   if (eaBleCombination.e_hand_info == EAPersonHand.left) {
  //     combination.e_hand_info = 0;
  //   } else {
  //     combination.e_hand_info = 1;
  //   }
  //   if (eaBleCombination.e_unit_format == UnitFormat.metric) {
  //     combination.e_unit_format = 0;
  //   } else {
  //     combination.e_unit_format = 1;
  //   }
  //   if (eaBleCombination.e_status == EABatInfoStatus.normal) {
  //     combination.e_status = 0;
  //   } else {
  //     combination.e_status = 1;
  //   }
  //   final String txt = await _channel.invokeMethod(
  //       "setCombination", convert.jsonEncode(combination));
  //   Map<String, dynamic> info = convert.jsonDecode(txt);
  //   GeneralData generalData = GeneralData.fromJson(info);
  //   if (generalData.dataType == 0x0F) {
  //     generalCallback.mutualFail(generalData.errorCode);
  //   } else {
  //     generalCallback.result(generalData.result == 0 ? true : false);
  //   }
  // }

  // Future<void> setMenuPage(
  //     EABleMenuPage eaBleMenuPage, GeneralCallback generalCallback) async {
  //   MenuPage menuPage = MenuPage();
  //   if (eaBleMenuPage.typeList.isNotEmpty) {
  //     List<int> itemList = <int>[];
  //     menuPage.pageList = itemList;
  //     for (int i = 0; i < eaBleMenuPage.typeList.length; i++) {
  //       MenuType menuType = eaBleMenuPage.typeList.elementAt(i);
  //       if (menuType == MenuType.page_null) {
  //         itemList.add(0);
  //       } else if (menuType == MenuType.page_heart_rate) {
  //         itemList.add(1);
  //       } else if (menuType == MenuType.page_pressure) {
  //         itemList.add(2);
  //       } else if (menuType == MenuType.page_weather) {
  //         itemList.add(3);
  //       } else if (menuType == MenuType.page_music) {
  //         itemList.add(4);
  //       } else if (menuType == MenuType.page_breath) {
  //         itemList.add(5);
  //       } else if (menuType == MenuType.page_sleep) {
  //         itemList.add(6);
  //       } else if (menuType == MenuType.page_menstrual_cycle) {
  //         itemList.add(7);
  //       }
  //     }
  //   }
  //   final String txt = await _channel.invokeMethod(
  //       "setMenuPage", convert.jsonEncode(menuPage));
  //   Map<String, dynamic> info = convert.jsonDecode(txt);
  //   GeneralData generalData = GeneralData.fromJson(info);
  //   if (generalData.dataType == 0x0F) {
  //     generalCallback.mutualFail(generalData.errorCode);
  //   } else {
  //     generalCallback.result(generalData.result == 0 ? true : false);
  //   }
  // }

  // Future<void> setMenstrualCycle(
  //     EABlePeriod eaBlePeriod, GeneralCallback generalCallback) async {
  //   Period period = Period();
  //   if (eaBlePeriod.dataList.isNotEmpty) {
  //     List<PeriodItem> itemList = <PeriodItem>[];
  //     period.periodItem = itemList;
  //     for (int i = 0; i < eaBlePeriod.dataList.length; i++) {
  //       PeriodItem periodItem = PeriodItem();
  //       periodItem.days = eaBlePeriod.dataList.elementAt(i).days;
  //       periodItem.time_stamp = eaBlePeriod.dataList.elementAt(i).time_stamp;
  //       if (eaBlePeriod.dataList.elementAt(i).periodType ==
  //           PeriodType.menstrual) {
  //         periodItem.periodType = 2;
  //       } else if (eaBlePeriod.dataList.elementAt(i).periodType ==
  //           PeriodType.safety_period_1) {
  //         periodItem.periodType = 0;
  //       } else if (eaBlePeriod.dataList.elementAt(i).periodType ==
  //           PeriodType.safety_period_2) {
  //         periodItem.periodType = 1;
  //       } else if (eaBlePeriod.dataList.elementAt(i).periodType ==
  //           PeriodType.ovulation) {
  //         periodItem.periodType = 3;
  //       }
  //       itemList.add(periodItem);
  //     }
  //   }
  //   final String txt = await _channel.invokeMethod(
  //       "setMenstrualCycle", convert.jsonEncode(period));
  //   Map<String, dynamic> info = convert.jsonDecode(txt);
  //   GeneralData generalData = GeneralData.fromJson(info);
  //   if (generalData.dataType == 0x0F) {
  //     generalCallback.mutualFail(generalData.errorCode);
  //   } else {
  //     generalCallback.result(generalData.result == 0 ? true : false);
  //   }
  // }

  // Future<void> setWatchFace(
  //     EABleWatchFace eaBleWatchFace, GeneralCallback generalCallback) async {
  //   WatchFace watchFace = WatchFace();
  //   watchFace.user_wf_id = eaBleWatchFace.user_wf_id;
  //   watchFace.id = eaBleWatchFace.id;
  //   watchFace.user_wf_id_0 = eaBleWatchFace.user_wf_id_0;
  //   watchFace.user_wf_id_1 = eaBleWatchFace.user_wf_id_1;
  //   watchFace.user_wf_id_2 = eaBleWatchFace.user_wf_id_2;
  //   watchFace.user_wf_id_3 = eaBleWatchFace.user_wf_id_3;
  //   final String txt = await _channel.invokeMethod(
  //       "setWatchFace", convert.jsonEncode(watchFace));
  //   Map<String, dynamic> info = convert.jsonDecode(txt);
  //   GeneralData generalData = GeneralData.fromJson(info);
  //   if (generalData.dataType == 0x0F) {
  //     generalCallback.mutualFail(generalData.errorCode);
  //   } else {
  //     generalCallback.result(generalData.result == 0 ? true : false);
  //   }
  // }

  // Future<void> setAppPushSwitch(
  //     EABleInfoPush eaBleInfoPush, GeneralCallback generalCallback) async {
  //   InfoPush infoPush = InfoPush();
  //   if (eaBleInfoPush.s_app_sw.isNotEmpty) {
  //     List<int> itemList = <int>[];
  //     infoPush.list = itemList;
  //     for (int i = 0; i < eaBleInfoPush.s_app_sw.length; i++) {
  //       itemList.add(eaBleInfoPush.s_app_sw.elementAt(i).sw);
  //     }
  //   }
  //   final String txt = await _channel.invokeMethod(
  //       "setAppPushSwitch", convert.jsonEncode(infoPush));
  //   Map<String, dynamic> info = convert.jsonDecode(txt);
  //   GeneralData generalData = GeneralData.fromJson(info);
  //   if (generalData.dataType == 0x0F) {
  //     generalCallback.mutualFail(generalData.errorCode);
  //   } else {
  //     generalCallback.result(generalData.result == 0 ? true : false);
  //   }
  // }

  // Future<void> mobileOperationResponse(EABlePhoneResponse eaBlePhoneResponse,
  //     DataResponseCallback dataResponseCallback) async {
  //   ResponseWatch responseWatch = ResponseWatch();
  //   responseWatch.id = eaBlePhoneResponse.id;
  //   if (eaBlePhoneResponse.eaBleExecutiveResponse ==
  //       EABleExecutiveResponse.execute) {
  //     responseWatch.result = 0;
  //   } else if (eaBlePhoneResponse.eaBleExecutiveResponse ==
  //       EABleExecutiveResponse.success) {
  //     responseWatch.result = 1;
  //   } else if (eaBlePhoneResponse.eaBleExecutiveResponse ==
  //       EABleExecutiveResponse.fail) {
  //     responseWatch.result = 2;
  //   }

  //   final String txt = await _channel.invokeMethod(
  //       "mobileOperationResponse", convert.jsonEncode(responseWatch));
  //   Map<String, dynamic> info = convert.jsonDecode(txt);
  //   OperationResponseData operationResponseData =
  //       OperationResponseData.fromJson(info);
  //   if (operationResponseData.dataType == 0x0F) {
  //     dataResponseCallback.mutualFail(operationResponseData.errorCode);
  //   } else {
  //     dataResponseCallback.mutualSuccess();
  //   }
  // }

  // Future<void> musicQueryResponse(EABleMusicRespond eaBleMusicRespond,
  //     DataResponseCallback dataResponseCallback) async {
  //   MusicRespond musicRespond = MusicRespond();
  //   musicRespond.content = eaBleMusicRespond.content;
  //   musicRespond.duration = eaBleMusicRespond.duration;
  //   musicRespond.volume = eaBleMusicRespond.volume;
  //   musicRespond.elapsedtime = eaBleMusicRespond.elapsedtime;
  //   musicRespond.artist = eaBleMusicRespond.artist;
  //   if (eaBleMusicRespond.e_status == MusicStatus.not_play) {
  //     musicRespond.e_status = 0;
  //   } else if (eaBleMusicRespond.e_status == MusicStatus.playing) {
  //     musicRespond.e_status = 1;
  //   } else if (eaBleMusicRespond.e_status == MusicStatus.stop_play) {
  //     musicRespond.e_status = 2;
  //   }
  //   final String txt = await _channel.invokeMethod(
  //       "musicQueryResponse", convert.jsonEncode(musicRespond));
  //   Map<String, dynamic> info = convert.jsonDecode(txt);
  //   OperationResponseData operationResponseData =
  //       OperationResponseData.fromJson(info);
  //   if (operationResponseData.dataType == 0x0F) {
  //     dataResponseCallback.mutualFail(operationResponseData.errorCode);
  //   } else {
  //     dataResponseCallback.mutualSuccess();
  //   }
  // }

  // Future<void> motionDataResponse(
  //     EABleGeneralSportRespond eaBleGeneralSportRespond,
  //     MotionDataResponseCallback motionDataResponseCallback) async {
  //   GeneralSportRespond generalSportRespond = GeneralSportRespond();
  //   generalSportRespond.request_id = eaBleGeneralSportRespond.request_id;
  //   if (eaBleGeneralSportRespond.e_common_flag == CommonFlag.begin) {
  //     generalSportRespond.e_common_flag = 0;
  //   } else if (eaBleGeneralSportRespond.e_common_flag == CommonFlag.proceed) {
  //     generalSportRespond.e_common_flag = 1;
  //   }
  //   if (eaBleGeneralSportRespond.e_common_flag == CommonFlag.end) {
  //     generalSportRespond.e_common_flag = 2;
  //   }
  //   if (eaBleGeneralSportRespond.e_common_flag == CommonFlag.begin_end) {
  //     generalSportRespond.e_common_flag = 3;
  //   }
  //   final String txt = await _channel.invokeMethod(
  //       "motionDataResponse", convert.jsonEncode(generalSportRespond));
  //   Map<String, dynamic> info = convert.jsonDecode(txt);
  //   OperationResponseData operationResponseData =
  //       OperationResponseData.fromJson(info);
  //   if (operationResponseData.dataType == 0x0F) {
  //     motionDataResponseCallback.mutualFail(operationResponseData.errorCode);
  //   } else {
  //     motionDataResponseCallback.mutualSuccess();
  //   }
  // }

  // Future<EABleConnectState> getDeviceConnectState() async {
  //   int state = await _channel.invokeMethod('getDeviceConnectState');
  //   if (state == 0) {
  //     return EABleConnectState.STATE_CONNECTED;
  //   } else if (state == 1) {
  //     return EABleConnectState.STATE_CONNECTING;
  //   } else if (state == 2) {
  //     return EABleConnectState.STATE_DISCONNECT;
  //   } else {
  //     return EABleConnectState.STATE_IDLE;
  //   }
  // }

  // Future<void> disconnectPeripheral() async {
  //   await _channel.invokeMethod("disconnectPeripheral");
  // }

  // Future<void> otaUpdate(
  //     List<EABleOta> otaList, OtaCallback otaCallback) async {
  //   OtaData otaData = OtaData();
  //   if (otaList.isNotEmpty) {
  //     for (int i = 0; i < otaList.length; i++) {
  //       Ota ota = Ota();
  //       ota.fileByte = otaList.elementAt(i).fileByte;
  //       ota.version = otaList.elementAt(i).version;
  //       if (otaList.elementAt(i).otaType == OtaType.apollo) {
  //         ota.otaType = 0;
  //       } else if (otaList.elementAt(i).otaType == OtaType.res) {
  //         ota.otaType = 1;
  //       } else if (otaList.elementAt(i).otaType == OtaType.hr) {
  //         ota.otaType = 2;
  //       } else if (otaList.elementAt(i).otaType == OtaType.tp) {
  //         ota.otaType = 3;
  //       } else if (otaList.elementAt(i).otaType == OtaType.agps) {
  //         ota.otaType = 4;
  //       } else if (otaList.elementAt(i).otaType == OtaType.gps) {
  //         ota.otaType = 5;
  //       } else if (otaList.elementAt(i).otaType == OtaType.stm32) {
  //         ota.otaType = 6;
  //       } else if (otaList.elementAt(i).otaType == OtaType.user_wf) {
  //         ota.otaType = 7;
  //       }
  //       otaData.otaList.add(ota);
  //     }
  //   }
  //   String txt =
  //       await _channel.invokeMethod("otaUpdate", convert.jsonEncode(otaData));
  //   Map<String, dynamic> info = convert.jsonDecode(txt);
  //   OtaResult otaResult = OtaResult.fromJson(info);
  //   if (otaResult.dataType == 0) {
  //     otaCallback.success();
  //   } else if (otaResult.dataType == 0x0F) {
  //     otaCallback.mutualFail(otaResult.errorCode);
  //   } else if (otaResult.dataType == 1) {
  //     otaCallback.mutualFail(otaResult.progress);
  //   }
  // }

  // Future<void> agpsUpdate(EABleAgps eaBleAgps, OtaCallback otaCallback) async {
  //   AgpsData agpsData = AgpsData();
  //   agpsData.longitude = eaBleAgps.longitude;
  //   agpsData.latitude = eaBleAgps.latitude;
  //   agpsData.altitude = eaBleAgps.altitude;
  //   if (eaBleAgps.list.isNotEmpty) {
  //     for (int i = 0; i < eaBleAgps.list.length; i++) {
  //       Agps ota = Agps();
  //       ota.fileByte = eaBleAgps.list.elementAt(i).fileByte;
  //       if (eaBleAgps.list.elementAt(i).agpsType == AgpsType.gps) {
  //         ota.otaType = 0;
  //       } else if (eaBleAgps.list.elementAt(i).agpsType == AgpsType.beidou) {
  //         ota.otaType = 1;
  //       } else if (eaBleAgps.list.elementAt(i).agpsType == AgpsType.galileo) {
  //         ota.otaType = 2;
  //       } else if (eaBleAgps.list.elementAt(i).agpsType == AgpsType.glonass) {
  //         ota.otaType = 3;
  //       } else if (eaBleAgps.list.elementAt(i).agpsType == AgpsType.qzss) {
  //         ota.otaType = 4;
  //       }
  //       agpsData.list.add(ota);
  //     }
  //   }
  //   final String txt =
  //       await _channel.invokeMethod("agpsUpdate", convert.jsonEncode(agpsData));
  //   Map<String, dynamic> info = convert.jsonDecode(txt);
  //   OtaResult otaResult = OtaResult.fromJson(info);
  //   if (otaResult.dataType == 0) {
  //     otaCallback.success();
  //   } else if (otaResult.dataType == 0x0F) {
  //     otaCallback.mutualFail(otaResult.errorCode);
  //   } else if (otaResult.dataType == 1) {
  //     otaCallback.mutualFail(otaResult.progress);
  //   }
  // }
}
