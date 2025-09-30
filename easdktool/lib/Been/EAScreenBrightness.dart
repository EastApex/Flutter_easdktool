part of easdktool.been;

///  屏幕亮度
class EAScreenBrightness {
  int level = 0; // 1~100 亮度

  EAScreenBrightness.fromMap(Map<String, dynamic> map) {
    if (map["level"] != null) {
      level = map["level"];
    }
  }
}

/// 息屏时间
class EAScreenTimeout {
  /// 自动灭屏时间,单位（秒) = 4294967295(无限亮屏)
  int timeout = 5;

  EAScreenTimeout.fromMap(Map<String, dynamic> map) {
    if (map["timeout"] != null) {
      timeout = map["timeout"];
    }
  }
}

/// 抬手亮屏开关
class EAScreenGesturesSetting {
  EAGesturesBrightType eBrightSrc = EAGesturesBrightType.AllDay;

  /// 开始时间 ：小时
  int beginHour = 0;

  /// 开始时间 ：分钟
  int beginMinute = 0;

  /// 结束时间 ：小时
  int endHour = 0;

  /// 结束时间 ：分钟
  int endMinute = 0;

  EAScreenGesturesSetting();
  EAScreenGesturesSetting.allDay() {
    eBrightSrc = EAGesturesBrightType.AllDay;
  }
  EAScreenGesturesSetting.close() {
    eBrightSrc = EAGesturesBrightType.Close;
  }
  EAScreenGesturesSetting.slectTime(
      this.beginHour, this.beginMinute, this.endHour, this.endMinute) {
    eBrightSrc = EAGesturesBrightType.SelectTime;
  }

  EAScreenGesturesSetting.fromMap(Map<String, dynamic> map) {
    if (map["eBrightSrc"] != null) {
      eBrightSrc = EAGesturesBrightType.values[map["eBrightSrc"]];
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

  Map toMap() {
    return {
      "eBrightSrc": eBrightSrc.index,
      "beginHour": beginHour,
      "beginMinute": beginMinute,
      "endHour": endHour,
      "endMinute": endMinute,
    };
  }
}
