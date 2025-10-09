// ignore_for_file: file_names, constant_identifier_names, non_constant_identifier_names

part of easdktool.been;

class EASportHrWarning {
  /// Switch: 0 Off 1 on
  /// 开关： 0关闭 1打开
  int sw = 0;

  /// Heart rate upper limit
  int maxHr = 180;

  /// Lower heart rate
  int minHr = 50;

  EASportHrWarning(this.sw, this.maxHr, this.minHr);

  Map<String, dynamic> toMap() {
    return {"sw": sw, "maxHr": maxHr, "minHr": minHr};
  }

  EASportHrWarning.fromMap(Map<String, dynamic> map) {
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
}
