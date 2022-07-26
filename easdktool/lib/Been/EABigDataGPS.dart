// ignore_for_file: file_names, constant_identifier_names, non_constant_identifier_names

part of easdktool.been;

class EABigDataGPS {
  //时间戳
  int timeStamp = 0;

  // 纬度
  double latitude = 0;

  // 经度
  double longitude = 0;
  EABigDataGPS.fromMap(Map<String, dynamic> map) {
    if (map["timeStamp"] != null) {
      timeStamp = map["timeStamp"];
    }
    if (map["time_stamp"] != null) {
      timeStamp = map["time_stamp"];
    }
    if (map["latitude"] != null) {
      latitude = map["latitude"];
    }
    if (map["longitude"] != null) {
      longitude = map["longitude"];
    }
  }
}
