// ignore_for_file: file_names, constant_identifier_names, non_constant_identifier_names

part of easdktool.been;

/// 监测睡眠
class EAAutoCheckSleep {
  ///周期：==》周日~周六
  ///0：关闭
  ///1：开启
  ///eg1：周日 ~ 周一 ~ 周二 ~ 周三 ~ 周四 ~ 周五 ~ 周六
  ///      0     1      1     0     0     0      1
  ///将 0110001 二进制转为 10进制 即此时 weekCycleBit 为 49 开启 周一二六 监测
  ///
  ///eg2：周日 ~ 周一 ~ 周二 ~ 周三 ~ 周四 ~ 周五 ~ 周六
  ///      0     0      0     0     0      1     1
  ///将 0000010 二进制转为 10进制 即此时 weekCycleBit 为 3 开启 周五六 监测
  ///
  ///weekCycleBit 为0 即 关闭监测功能

  int weekCycleBit = 0;

  /// 开始时间 ：小时
  int beginHour = 0;

  /// 开始时间 ：分钟
  int beginMinute = 0;

  /// 结束时间 ：小时
  int endHour = 0;

  /// 结束时间 ：分钟
  int endMinute = 0;

  EAAutoCheckSleep();
  EAAutoCheckSleep.fromMap(Map<String, dynamic> map) {
    if (map["weekCycleBit"] != null) {
      weekCycleBit = map["weekCycleBit"];
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
      "weekCycleBit": weekCycleBit,
      "beginHour": beginHour,
      "beginMinute": beginMinute,
      "endHour": endHour,
      "endMinute": endMinute
    };
  }
}

/// 监测心率
class EAAutoCheckHeartRate {
  /// The interval time:minutes ，（0 indicates that the monitoring function is disabled）
  ///【间隔时长:分钟（0为关闭监测功能）】
  int interval = 30;

  EAAutoCheckHeartRate(this.interval);
  EAAutoCheckHeartRate.fromMap(Map<String, dynamic> map) {
    if (map["interval"] != null) {
      interval = map["interval"];
    }
  }

  Map<String, dynamic> toMap() {
    return {
      "interval": interval,
    };
  }
}

class EAAutoCheckSedentariness extends EAAutoCheckSleep {
  ///步数阈值 ：低于此步数则提醒久坐
  int stepThreshold = 10;

  /// 间隔时长：单位分钟，0为关闭监测功能
  int interval = 60;

  EAAutoCheckSedentariness();
  EAAutoCheckSedentariness.fromMap(Map<String, dynamic> map)
      : super.fromMap(map) {
    if (map["stepThreshold"] != null) {
      stepThreshold = map["stepThreshold"];
    }
    if (map["interval"] != null) {
      interval = map["interval"];
    }
  }

  @override
  Map<String, dynamic> toMap() {
    Map<String, dynamic> map = super.toMap();
    map["interval"] = interval;
    map["stepThreshold"] = stepThreshold;
    return map;
  }
}
