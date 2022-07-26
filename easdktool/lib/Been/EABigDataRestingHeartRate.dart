// ignore_for_file: file_names, constant_identifier_names, non_constant_identifier_names

part of easdktool.been;

class EABigDataRestingHeartRate {
// 时间戳
  int timeStamp = 0;

// 心率值
  int hrValue = 0;

  EABigDataRestingHeartRate.fromMap(Map<String, dynamic> map) {
    if (map["timeStamp"] != null) {
      timeStamp = map["timeStamp"];
    }
    if (map["time_stamp"] != null) {
      timeStamp = map["time_stamp"];
    }
    if (map["hrValue"] != null) {
      hrValue = map["hrValue"];
    }
    if (map["hr_value"] != null) {
      hrValue = map["hr_value"];
    }
  }
}
