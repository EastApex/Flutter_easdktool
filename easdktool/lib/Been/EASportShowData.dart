// ignore_for_file: file_names, constant_identifier_names, non_constant_identifier_names

part of easdktool.been;

class EASportShowData {
  /// 运动显示：步数
  /// steps
  int steps = 0;

  /// 运动显示：卡路里（单位:小卡)
  /// calorie (unit: cal)    note:1000 cal = 1kcal
  int calorie = 0;

  /// 运动显示：距离 （单位:厘米）
  /// distance (unit:cm)
  int distance = 0;

  /// 运动显示：运动时长(单位:秒)
  /// duration (unit:second)
  int duration = 0;

  EASportShowData();
  EASportShowData.fromMap(Map<String, dynamic> map) {
    if (map["steps"] != null) {
      steps = map["steps"];
    }
    if (map["calorie"] != null) {
      calorie = map["calorie"];
    }
    if (map["distance"] != null) {
      distance = map["distance"];
    }
    if (map["duration"] != null) {
      duration = map["duration"];
    }
  }
}
