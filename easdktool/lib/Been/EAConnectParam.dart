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

  EAConnectParam.testInit() {
    connectAddress = "45:41:6C:30:92:4F";
    // connectAddress = "DC:71:DD:A0:39:D1";
    // "45:41:15:82:52:84"; //"45:41:46:03:F2:A7"; // "45:41:70:97:FC:84"; // andriond need
    snNumber = "006056240322000005";
    //"001007220516000001","002006000009999010","001007220719000021","001007220516000001"; //"001001211112000028"; // iOS need
  }

  EAConnectParam.init(String address, String sn) {
    connectAddress = address;
    snNumber = sn;
  }

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
