// ignore_for_file: file_names, constant_identifier_names, non_constant_identifier_names
part of easdktool.been;

class EAMonitorReminder {
  /// 提醒事件类型
  EAMonitorReminderType eReminderType = EAMonitorReminderType.drink;

  /// 开关
  int sw = 0;

  /// 间隔时长：单位分钟
  int interval = 60;

  /// 周期：位对应从bit0~bit6对应周日~周六,0仅一次提醒
  int weekCycleBit = 0;

  /// 开始时间 ：小时
  int beginHour = 8;

  /// 开始时间 ：分钟
  int beginMinute = 0;

  /// 结束时间 ：小时 */
  int endHour = 20;

  /// 结束时间 ：分钟 */
  int endMinute = 0;

  /// 步数阈值 ：低于此步数则提醒久坐（reminder_type = Sedentary） */
  int stepThreshold = 10;

  /// 杯：喝多少杯水，一杯水 = 200ml（reminder_type = Drink） */
  int cup = 1;

  EAMonitorReminder();
  EAMonitorReminder.fromMap(Map<String, dynamic> map) {
    if (map["eReminderType"] != null) {
      eReminderType = EAMonitorReminderType.values[map["eReminderType"]];
    }
    if (map["sw"] != null) {
      sw = map["sw"];
    }
    if (map["interval"] != null) {
      interval = map["interval"];
    }
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
    if (map["stepThreshold"] != null) {
      stepThreshold = map["stepThreshold"];
    }
    if (map["cup"] != null) {
      cup = map["cup"];
    }
  }
  Map<String, dynamic> toMap() {
    return {
      "eReminderType": eReminderType.index,
      "sw": sw,
      "interval": interval,
      "weekCycleBit": weekCycleBit,
      "beginHour": beginHour,
      "beginMinute": beginMinute,
      "endHour": endHour,
      "endMinute": endMinute,
      "stepThreshold": stepThreshold,
      "cup": cup
    };
  }
}

class EAMonitorReminderRead {
  List<EAMonitorReminder> list = <EAMonitorReminder>[];

  EAMonitorReminderRead();
  EAMonitorReminderRead.fromMap(Map<String, dynamic> map) {
    if (map["sIndexArray"] != null) {
      List sIndexArray = map["sIndexArray"];

      for (Map<String, dynamic> item in sIndexArray) {
        EAMonitorReminder eaMonitorReminder = EAMonitorReminder.fromMap(item);
        list.add(eaMonitorReminder);
      }
    }
  }
}
