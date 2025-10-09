// ignore_for_file: file_names, constant_identifier_names, non_constant_identifier_names

part of easdktool.been;

/*
 * 连接手表
 * Android need connectAddress
 * iOS need snNumber
 */
class EAConnectParam {
  String connectAddress = "";
  String snNumber = "";
  String name = "";
  Map advertisementData = {};
  int rssi = 0;
  String uuid = "";
  int isJL707 = 0; //  1  is jl707 watch
  int needOta =
      0; //  1: It indicates that the jl707 watch requires mandatory OTA

  EAConnectParam();

  EAConnectParam.testInit() {
    connectAddress = "45:42:B9:0B:7D:F6";
   //   connectAddress = "45:41:80:B9:1C:EB";
    // "45:41:15:82:52:84"; //"45:41:46:03:F2:A7"; // "45:41:70:97:FC:84"; // andriond need
    snNumber = "45:42:B9:03:21:91";
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
    if (map["isJL707"] != null) {
      isJL707 = map["isJL707"];
    }

    if (map["needOta"] != null) {
      isJL707 = map["needOta"];
    }
  }
}
