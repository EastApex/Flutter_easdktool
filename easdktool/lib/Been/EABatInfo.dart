// ignore_for_file: file_names, constant_identifier_names, non_constant_identifier_names

part of easdktool.been;

/*
 * 电量信息
 */
class EABatInfo {
  EABatInfoStatus batteryStatus = EABatInfoStatus.normal; // 充电状态
  int level = 0; // 电量

  EABatInfo.fromMap(Map<String, dynamic> map) {
    if (map["level"] != null) {
      level = map["level"];
    }

    if (map["eStatus"] != null) {
      batteryStatus = EABatInfoStatus.values[map["eStatus"]];
    }
  }
}
