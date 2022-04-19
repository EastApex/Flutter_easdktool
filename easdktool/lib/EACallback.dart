import 'package:easdktool/Been/EABeen.dart';

typedef OnSuccess = void Function(Map<String, dynamic> info);
typedef OnFail = void Function(Map<String, dynamic> info);
typedef OnRespond = void Function(EARespond respond);
typedef Progress = void Function(int progress);

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
