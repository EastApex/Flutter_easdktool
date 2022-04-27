import 'dart:async';
import 'package:flutter/services.dart';
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
const String kEAScanWacth = "EAScanWacth"; // 搜索手表
const String kEAStopScanWacth = "EAStopScanWacth"; //停止搜索手表

/// MARK: - invoke method Name
const String kConnectState = "ConnectState";
const String kArgumentsError = "ArgumentsError";
const String kBluetoothState = "BluetoothState";
const String kSetWatchResponse = "SetWatchResponse";
const String kGetWatchResponse = "GetWatchResponse";
const String kGetBigWatchData = "GetBigWatchData";
const String kOperationPhone = "OperationPhone";
const String kProgress = "Progress";
const String kScanWacthResponse = "ScanWacthResponse";

class EASDKTool {
  static const MethodChannel _channel = MethodChannel(kEAsdktool);

  EASDKTool._privateConstructor();

  static final EASDKTool _instance = EASDKTool._privateConstructor();

  factory EASDKTool() {
    return _instance;
  }

  static EAGetDataCallback? mGetDataCallback;
  static EASetDataCallback? mSetDataCallback;
  static EAGetBitDataCallback? mGetBitDataCallback;
  static OperationPhoneCallback? mOperationPhoneCallback;
  static EABleConnectListener? mEaBleConnectListener;
  static OperationWatchCallback? mOperationCallback;
  static EAOTAProgressCallback? mOTAProgressCallback;
  static EAScanWatchCallback? mScanWatchCallback;

  static void addOperationPhoneCallback(
      OperationPhoneCallback operationPhoneCallback) {
    mOperationPhoneCallback = operationPhoneCallback;
  }

  static void addBleConnectListener(EABleConnectListener eaBleConnectListener) {
    mEaBleConnectListener = eaBleConnectListener;
    _channel.setMethodCallHandler(platformCallHandler);
  }

  /// 开始搜索手表
  /// scan wacth,
  void scanWatch(bool scanAll, EAScanWatchCallback scanWatchCallback) {
    mScanWatchCallback = scanWatchCallback;
    _channel.invokeMethod(
        kEAScanWacth, convert.jsonEncode({"scanAll": scanAll}));
  }

  /// 停止搜索
  /// stop watch
  void stopWatch() {
    _channel.invokeMethod(kEAStopScanWacth);
  }

  /// 打开SDK Log
  /// Open SDK Log
  void showLog(bool isShow) {
    _channel.invokeMethod(kEALog, convert.jsonEncode({"showLog": isShow}));
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
    _channel.invokeMethod(kEAbindingWatch, convert.jsonEncode(map));
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
     *  8.Progress          => The progress situation【进度情况】
     *  9.ScanWacthResponse => 【返回手表】
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
      case kScanWacthResponse:
        Map<String, dynamic> info = convert.jsonDecode(methodCall.arguments);
        EAConnectParam connectParam = EAConnectParam.fromMap(info);
        if (mScanWatchCallback != null) {
          mScanWatchCallback!.scanRespond(connectParam);
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
}
