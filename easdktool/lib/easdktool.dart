import 'dart:async';
import 'dart:developer';
import 'dart:ffi';
import 'dart:isolate';
import 'dart:typed_data';
import 'dart:ui';
import 'package:flutter/cupertino.dart';
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
const String kEACustomWatchface = "EACustomWatchface"; // ota
const String kEALog = "EAShowLog"; // log
const String saveData = "saveData";
const String kEAScanWacth = "EAScanWacth"; // 搜索手表
const String kEAStopScanWacth = "EAStopScanWacth"; //停止搜索手表
const String kEAGetWacthStateInfo = "EAGetWacthStateInfo"; //获取手表连接状态信息
const String kEATest = "EATest"; // Test mode
const String kEAAGPS = "EAAGPS"; // AGPS
const String kAddJieLiWatchFace = "AddJieLiWatchFace";
const String kDeleteJieLiWatchFace = "DeleteJieLiWatchFace";
const String kGetJieLiWatchFace = "GetJieLiWatchFace";

/// MARK: - invoke method Name
const String kConnectState = "ConnectState";
const String kArgumentsError = "ArgumentsError";
const String kBluetoothState = "BluetoothState";
const String kBingdingWatchResponse = "BingdingWatchResponse";
const String kSetWatchResponse = "SetWatchResponse";
const String kGetWatchResponse = "GetWatchResponse";
const String kGetBigWatchData = "GetBigWatchData";
const String kOperationPhone = "OperationPhone";
const String kProgress = "Progress";
const String kScanWacthResponse = "ScanWacthResponse";
const String kOperationWacthResponse = "OperationWacthResponse";
const String kCustomWatchFaceResponse = "CustomWatchFaceResponse";
const String queryData = "queryData";
final String deleteData = "deleteData";
const String kQueryBigWatchData = "QueryBigWatchData";
final String ConnectBT = "connectClassicBluetooth";
const String kJieLiNeedForcedOTA = "JieLiNeedForcedOTA";

class EASDKTool {
  static const MethodChannel _channel = MethodChannel(kEAsdktool);

  EASDKTool._privateConstructor();

  static final EASDKTool _instance = EASDKTool._privateConstructor();

  factory EASDKTool() {
    return _instance;
  }

  EAGetDataCallback? mGetDataCallback;
  EASetDataCallback? mSetDataCallback;
  EABindingWatchCallback? mBindingWatchCallback;
  static EAGetBitDataCallback? mGetBitDataCallback;
  static OperationPhoneCallback? mOperationPhoneCallback;
  static EABleConnectListener? mEaBleConnectListener;
  OperationWatchCallback? mOperationCallback;
  EAOTAProgressCallback? mOTAProgressCallback;
  EAScanWatchCallback? mScanWatchCallback;
  QueryMotionDataCallback? mQueryMotionDataCallback;
  EACustomWatchfacePreviewImageCallback? mCustomWatchfacePreviewImageCallback;
  JieLiNeedForcedOtaCallback? mJieLiNeedForcedOtaCallback;
  JieLiWatchFaceCallback? mJieliWatchFaceCallback;

  static void addOperationPhoneCallback(
      OperationPhoneCallback operationPhoneCallback) {
    mOperationPhoneCallback = operationPhoneCallback;
  }

  static void addMotionDataCallback(EAGetBitDataCallback eaGetBitDataCallback) {
    mGetBitDataCallback = eaGetBitDataCallback;
  }

  static void addBleConnectListener(EABleConnectListener eaBleConnectListener) {
    mEaBleConnectListener = eaBleConnectListener;
  }

  static void addJieLiNeedForcedOtaCallback(
      JieLiNeedForcedOtaCallback mJieLiNeedForcedOtaCallback) {
    mJieLiNeedForcedOtaCallback = mJieLiNeedForcedOtaCallback;
  }

  void initChannel() {
    _channel.setMethodCallHandler(platformCallHandler);
  }

  /// 开始搜索手表
  /// scan wacth,
  void scanWatch(EAScanWatchCallback scanWatchCallback) {
    mScanWatchCallback = scanWatchCallback;
    _channel.invokeMethod(kEAScanWacth);
  }

  /// 停止搜索
  /// stop watch
  void stopWatch() {
    _channel.invokeMethod(kEAStopScanWacth);
  }

  /// 打开SDK Log
  /// Open SDK Log
  void showLog(int isShow) {
    _channel.invokeMethod(kEALog, convert.jsonEncode({"showLog": isShow}));
  }

  void saveData2DB(int isSave) {
    _channel.invokeMethod(saveData, convert.jsonEncode({"saveData": isSave}));
  }

  void queryMotionData(
      QueryType queryType, QueryMotionDataCallback queryCallback) {
    mQueryMotionDataCallback = queryCallback;
    EAQueryMotionData eaQueryMotionData = new EAQueryMotionData();
    eaQueryMotionData.dataType = queryType.index;
    String queryString = convert.jsonEncode(eaQueryMotionData);
    _channel.invokeMethod(queryData, queryString);
  }

  void deleteSaveData(QueryType queryType) {
    EADeleteMotion eaDeleteMotion = new EADeleteMotion();
    eaDeleteMotion.dataType = queryType.index;
    String deleteString = convert.jsonEncode(eaDeleteMotion);
    _channel.invokeMethod(deleteData, deleteString);
  }

  /// 打开SDK 测试模式
  /// Open SDK Test Mode
  /// 1.Big data is not deleted after this function is enabled.
  /// 2.For debugging of big data.
  /// 3.The package item must be set to 0.
  void showTest(int test) {
    _channel.invokeMethod(kEATest, convert.jsonEncode({"test": test}));
  }

  /// 连接手表
  /// Connect the watch
  void connectToPeripheral(EAConnectParam connectParam) {
    String param = convert.jsonEncode(connectParam);
    _channel.invokeMethod(kEAConnectWatch, param);
  }

  void pairBt(String btAddress) {
    _channel.invokeMethod(ConnectBT, btAddress);
  }

  /// 获取手表连接状态信息
  /// get the watch connect state info.
  Future<EAConnectStateInfo> getWacthStateInfo() async {
    String json = await _channel.invokeMethod(kEAGetWacthStateInfo);
    Map<String, dynamic> info = convert.jsonDecode(json);
    return EAConnectStateInfo.fromMap(info);
  }

  /// 绑定手表
  /// Binding a watch
  void bindingWatch(
      EABindInfo bindInfo, EABindingWatchCallback bindingWatchCallback) {
    mBindingWatchCallback = bindingWatchCallback;
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
    dataInfoType.dataType = dataType;
    String param = convert.jsonEncode(dataInfoType);
    _channel.invokeMethod(kEAGetWatchInfo, param);
  }

  /// 获取手表信息
  /// Obtaining watch Information
  void getWatchData2(
      int dataType, int type, EAGetDataCallback getDataCallback) {
    mGetDataCallback = getDataCallback;
    EAGetData dataInfoType = EAGetData();
    dataInfoType.type = type;
    dataInfoType.dataType = dataType;
    String param = convert.jsonEncode(dataInfoType);
    _channel.invokeMethod(kEAGetWatchInfo, param);
  }

  /// 【设置手表信息（修改的类型，数据，回调）】
  /// Set watch information (modified type, data, callback)
  void setWatchData(
      int dataType, Map value, EASetDataCallback setDataCallback) {
    mSetDataCallback = setDataCallback;
    EASetData dataInfoType = EASetData();
    dataInfoType.dataType = dataType;
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

  void getNewBigWatchData(EASetDataCallback eaSetDataCallback) {
    mSetDataCallback = eaSetDataCallback;
    if (_channel != null) {
      _channel.invokeMethod(kEAGetBigWatchData);
    } else {
      debugPrint("_channel不存在");
    }
  }

  /// 【操作手表】
  ///  operation watch  operationType see EAEnum.dart => EAOperationWatchType
  void operationWatch(EAOperationWatchType operationType,
      OperationWatchCallback operationCallback) {
    mOperationCallback = operationCallback;
    EASetData dataInfoType = EASetData();
    dataInfoType.dataType = operationType.index;
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

  void addJieLiWatchFace(
      String filePath, EAOTAProgressCallback otaProgressCallback) {
    mOTAProgressCallback = otaProgressCallback;
    _channel.invokeListMethod(kAddJieLiWatchFace, filePath);
  }

  void deleteJieLiWatchFace(String filePath, EASetDataCallback dataCallback) {
    mSetDataCallback = dataCallback;
    _channel.invokeListMethod(kDeleteJieLiWatchFace, filePath);
  }

  void getJieLiWatchFace(JieLiWatchFaceCallback jieLiWatchFaceCallback) {
    mJieliWatchFaceCallback = jieLiWatchFaceCallback;
    _channel.invokeListMethod(kGetJieLiWatchFace);
  }

  void watchface(EAOTAList otaList, EAOTAProgressCallback otaProgressCallback) {
    mOTAProgressCallback = otaProgressCallback;
    String param = convert.jsonEncode(otaList);
    _channel.invokeMethod(kEAOTA, param);
  }

  void syncAGPS(EAOTAProgressCallback otaProgressCallback) {
    mOTAProgressCallback = otaProgressCallback;
    _channel.invokeMethod(kEAAGPS, null);
  }

  void getCustomWatchfacePreviewImage(
      EACustomWatchFace customWatchFace,
      EACustomWatchfacePreviewImageCallback
          customWatchfacePreviewImageCallback) {
    mCustomWatchfacePreviewImageCallback = customWatchfacePreviewImageCallback;
    String param = convert.jsonEncode(customWatchFace);
    _channel.invokeMethod(kEACustomWatchface, param);
  }

  void otaCustomWatchface(EACustomWatchFace customWatchFace,
      EAOTAProgressCallback otaProgressCallback) {
    mOTAProgressCallback = otaProgressCallback;
    String param = convert.jsonEncode(customWatchFace);
    _channel.invokeMethod(kEACustomWatchface, param);
  }

  //
  Future<dynamic> platformCallHandler(MethodCall methodCall) async {
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
      case kJieLiNeedForcedOTA:
        print("JieLiNeedForcedOTA");
        if (mJieLiNeedForcedOtaCallback != null) {
          mJieLiNeedForcedOtaCallback!.needOTA(true);
        }
        //

        break;
      case kAddJieLiWatchFace:
        Map<String, dynamic> info = convert.jsonDecode(methodCall.arguments);
        EAOtaProgress eaOtaProgress = EAOtaProgress.fromMap(info);
        if (mOTAProgressCallback != null) {
          mOTAProgressCallback!
              .callback(eaOtaProgress!.progress, eaOtaProgress!.isSuccess);
        }
        break;
      case kDeleteJieLiWatchFace:
        Map<String, dynamic> info = convert.jsonDecode(methodCall.arguments);
        EARespond respond = EARespond.fromMap(info);
        if (mSetDataCallback != null) {
          mSetDataCallback!.onRespond(respond);
        }
        break;
      case kGetJieLiWatchFace:
        debugPrint("707 dial has been pulled back to Flutter");
        Map<String, dynamic> info = convert.jsonDecode(methodCall.arguments);
        if (mJieliWatchFaceCallback != null) {
          mJieliWatchFaceCallback!.callback(info);
        } else {
          debugPrint("Flutter doesn't set up 707 dial callbacks");
        }
        break;

      case kArgumentsError:
        String error = methodCall.arguments;
        print("error");
        break;
      case kConnectState:
        int state = methodCall.arguments;
        deviceConnectState(state);
        break;
      case kBluetoothState:
        int state = methodCall.arguments;
        bluetoothState(state);
        break;
      case kBingdingWatchResponse:
        Map<String, dynamic> info = convert.jsonDecode(methodCall.arguments);
        print("^^^^^^^^ info = " + methodCall.arguments);
        EARespond respond = EARespond.fromMap(info);
        print("绑定的回调");
        if (mBindingWatchCallback != null) {
          mBindingWatchCallback!.onRespond(respond);
          print("^^^^^^^^ respond");
        }
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
        debugPrint("Big data has been pulled back to Flutter");
        Map<String, dynamic> info = convert.jsonDecode(methodCall.arguments);
        if (mGetBitDataCallback != null) {
          mGetBitDataCallback!.callback(info);
        } else {
          debugPrint("Flutter doesn't set up big data callbacks");
        }
        break;
      case kQueryBigWatchData:
        debugPrint("query big data has been pulled back to Flutter");
        Map<String, dynamic> info = convert.jsonDecode(methodCall.arguments);
        if (mQueryMotionDataCallback != null) {
          mQueryMotionDataCallback!.callback(info);
        } else {
          debugPrint("Flutter doesn't set up big data callbacks");
        }
        break;
      case kOperationPhone:
        Map<String, dynamic> info = convert.jsonDecode(methodCall.arguments);
        if (mOperationPhoneCallback != null) {
          mOperationPhoneCallback!.callback(info);
        }
        break;
      case kProgress:
        Map<String, dynamic> info = convert.jsonDecode(methodCall.arguments);
        EAOtaProgress eaOtaProgress = EAOtaProgress.fromMap(info);
        if (mOTAProgressCallback != null) {
          mOTAProgressCallback!
              .callback(eaOtaProgress!.progress, eaOtaProgress!.isSuccess);
        }
        break;
      case kScanWacthResponse:
        Map<String, dynamic> info = convert.jsonDecode(methodCall.arguments);
        EAConnectParam connectParam = EAConnectParam.fromMap(info);
        if (mScanWatchCallback != null) {
          mScanWatchCallback!.scanRespond(connectParam);
        }
        break;
      case kOperationWacthResponse:
        Map<String, dynamic> info = convert.jsonDecode(methodCall.arguments);
        if (mOperationCallback != null) {
          mOperationCallback!.callback(info);
        }
        break;
      case queryData:
        debugPrint("query Big data has been pulled back to Flutter");
        Map<String, dynamic> info = convert.jsonDecode(methodCall.arguments);
        if (mQueryMotionDataCallback != null) {
          mQueryMotionDataCallback!.callback(info);
        } else {
          debugPrint("Flutter doesn't set up big data callbacks");
        }
        break;
      case kCustomWatchFaceResponse:
        final Uint8List imageData = methodCall.arguments as Uint8List;
        // 将图片数据转换为Image对象
        final Image previewImage = Image.memory(imageData);
        if (mCustomWatchfacePreviewImageCallback != null) {
          mCustomWatchfacePreviewImageCallback!.previewImage(previewImage);
        } else {
          debugPrint("Flutter doesn't set up big data callbacks");
        }
        break;
    }
  }

  // Device Connection status【设备连接状态】
  Future<void> deviceConnectState(int code) async {
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
