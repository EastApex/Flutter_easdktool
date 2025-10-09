// ignore_for_file: file_names, constant_identifier_names, non_constant_identifier_names

part of easdktool.been;

class EABigDataBloodOxygen {
  //时间戳
  int timeStamp = 0;

  // 血氧值
  int bloodOxygenValue = 0;
  EABigDataBloodOxygen.fromMap(Map<String, dynamic> map) {
    if (map["timeStamp"] != null) {
      timeStamp = map["timeStamp"];
    }
    if (map["time_stamp"] != null) {
      timeStamp = map["time_stamp"];
    }
    if (map["bloodOxygenValue"] != null) {
      bloodOxygenValue = map["bloodOxygenValue"];
    }
    if (map["blood_oxygen_value"] != null) {
      bloodOxygenValue = map["blood_oxygen_value"];
    }
  }
}
