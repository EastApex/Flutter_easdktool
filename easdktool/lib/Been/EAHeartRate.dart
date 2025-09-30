// ignore_for_file: file_names, constant_identifier_names, non_constant_identifier_names

part of easdktool.been;

/// 心率报警门限设置
class EAHeartRateWaringSetting {
  /// 开关： 0关闭 1打开
  int sw = 0;

  /// Heart rate upper limit【心率上限值】
  int maxHr = 180;

  /// Heart rate lower limit【心率下限值】
  int minHr = 40;

  EAHeartRateWaringSetting(this.sw, this.maxHr, this.minHr);
  EAHeartRateWaringSetting.fromMap(Map<String, dynamic> map) {
    if (map["sw"] != null) {
      sw = map["sw"];
    }
    if (map["maxHr"] != null) {
      maxHr = map["maxHr"];
    }
    if (map["minHr"] != null) {
      minHr = map["minHr"];
    }
  }

  Map toMap() {
    return {
      "sw": sw,
      "maxHr": maxHr,
      "minHr": minHr,
    };
  }
}
