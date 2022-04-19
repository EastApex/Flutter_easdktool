// ignore_for_file: file_names, constant_identifier_names, non_constant_identifier_names
part of easdktool.been;

/// 免打扰设置
class EANotDisturb {
// 开关： 0关闭 1打开
  int sw = 0;

// 开始时间 ：小时(0~23)
  int beginHour = 0;

// 开始时间 ：分钟(0~59)
  int beginMinute = 0;

// 结束时间 ：小时(0~23)
  int endHour = 0;

// 结束时间 ：分钟(0~59)
  int endMinute = 0;

  EANotDisturb();
  EANotDisturb.fromMap(Map<String, dynamic> map) {
    if (map["sw"] != null) {
      sw = map["sw"];
    }
    if (map["beginHour"] != null) {
      beginHour = map["beginHour"];
    }
    if (map["beginMinute"] != null) {
      beginMinute = map["beginMinute"];
    }
    if (map["endHour"] != null) {
      endHour = map["endHour"];
    }
    if (map["endMinute"] != null) {
      endMinute = map["endMinute"];
    }
  }

  Map<String, dynamic> toMap() {
    return {
      "sw": sw,
      "beginHour": beginHour,
      "beginMinute": beginMinute,
      "endHour": endHour,
      "endMinute": endMinute,
    };
  }
}
