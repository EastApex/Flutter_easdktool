// ignore_for_file: file_names, constant_identifier_names, non_constant_identifier_names

part of easdktool.been;

/*
 * 连接手表
 * Android 需要传 connectAddress
 * iOS 需要传 snNumber
 */
class EAConnectParam {
  String connectAddress = "";
  String snNumber = "";
  String name = "";
  Map advertisementData = {};
  int rssi = 0;
  String uuid = "";
  EAConnectParam();
  Map<String, dynamic> toJson() =>
      {'connectAddress': connectAddress, 'snNumber': snNumber};

  EAConnectParam.fromMap(Map<String, dynamic> map) {
    if (map["connectAddress"] != null) {
      connectAddress = map["connectAddress"];
    }
    if (map["snNumber"] != null) {
      snNumber = map["snNumber"];
    }
    if (map["name"] != null) {
      name = map["name"];
    }
    if (map["rssi"] != null) {
      rssi = map["rssi"];
    }
    if (map["advertisementData"] != null) {
      advertisementData = map["advertisementData"];
    }
    if (map["uuid"] != null) {
      uuid = map["uuid"];
    }
  }
}
