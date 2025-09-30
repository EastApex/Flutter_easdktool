// ignore_for_file: file_names, constant_identifier_names, non_constant_identifier_names

part of easdktool.been;

/// 基础卡路里开关
class EACaloriesSetting {
  /// 开关： 0关闭 1打开
  int sw = 1;

  EACaloriesSetting.fromMap(Map<String, dynamic> map) {
    if (map["sw"] != null) {
      sw = map["sw"];
    }
  }
}
