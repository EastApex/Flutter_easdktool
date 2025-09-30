import 'dart:ffi';

import 'package:easdktool/Been/EABeen.dart';
import 'package:flutter/material.dart';

typedef OnSuccess = void Function(Map<String, dynamic> info);
typedef OnFail = void Function(Map<String, dynamic> info);
typedef OnRespond = void Function(EARespond respond);
typedef Progress = void Function(int progress, int isSuccess,int errorType);
typedef ScanRespond = void Function(EAConnectParam connectParam);
typedef PreviewImage = void Function(Image previewImage);
typedef NeedOTA = void Function(int needOTAinfo);

/// Get the watch data callback【获取手表数据回调】
class EAGetDataCallback {
  OnSuccess onSuccess;
  OnFail onFail;

  EAGetDataCallback({required this.onSuccess, required this.onFail});
}

/// Example Set the watch data callback【设置手表数据回调】
class EASetDataCallback {
  OnRespond onRespond;

  EASetDataCallback({required this.onRespond});
}

/// Example Set the watch data callback【设置手表数据回调】
class EABindingWatchCallback {
  OnRespond onRespond;

  EABindingWatchCallback({required this.onRespond});
}

class JieLiWatchFaceCallback {
  OnSuccess callback;

  JieLiWatchFaceCallback(this.callback);
}

/// Get watch big data callback【获取手表大数据回调】
class EAGetBitDataCallback {
  OnSuccess callback;

  EAGetBitDataCallback(this.callback);
}

/// Operating the callback【操作回调】
class OperationPhoneCallback {
  OnSuccess callback;

  OperationPhoneCallback(this.callback);
}

class QueryMotionDataCallback {
  OnSuccess callback;

  QueryMotionDataCallback(this.callback);
}

/// Operating the callback【操作回调】
class OperationWatchCallback {
  OnSuccess callback;

  OperationWatchCallback(this.callback);
}

/// OTA progress callback【OTA 进度回调】
class EAOTAProgressCallback {
  Progress callback;

  EAOTAProgressCallback(this.callback);
}

/// Custom Watch Face Preview Image callback【预览图回调】
class EACustomWatchfacePreviewImageCallback {
  PreviewImage previewImage;

  EACustomWatchfacePreviewImageCallback(this.previewImage);
}

class EAScanWatchCallback {
  ScanRespond scanRespond;

  EAScanWatchCallback(this.scanRespond);
}

/// JieLi Need Forced OTA  the callback【操作回调】
class JieLiNeedForcedOtaCallback {
  NeedOTA needOTA;

  JieLiNeedForcedOtaCallback(this.needOTA);
}

// Watch connection status and Bluetooth monitoring【手表连接状态和蓝牙监听】
abstract class EABleConnectListener {
  void deviceConnected();

  void deviceNotFind();

  void connectError();

  void connectTimeOut();

  void deviceDisconnect();

  void iOSRelievePair();

  void paramError();

  void unopenedBluetooth();

  void unsupportedBLE();

  void notOpenLocation();

  void iOSUnAuthorized();
}
