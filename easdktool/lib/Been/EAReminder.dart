// ignore_for_file: file_names, constant_identifier_names, non_constant_identifier_names

part of easdktool.been;

/// 提醒事件
class EAReminder {
  /// 提醒事件类型
  EAReminderEventType reminderEventType = EAReminderEventType.Alarm;

  int id_p = 0;

  /// 时
  int hour = 0;

  /// 分
  int minute = 0;

  /// 年
  int year = 0;

  /// 月
  int month = 0;

  /// 日
  int day = 0;

  /// 周期：位对应从bit0~bit6对应周日~周六
  int weekCycleBit = 0;

  /// 开关
  int sw = 0;

  /// 二次提醒开关
  int secSw = 0;

  /// 贪睡时间（单位：秒）
  int sleepDuration = 0;

  /// 提醒方式
  EARemindActionType remindActionType = EARemindActionType.NoAction;

  /// 自定义内容：最多支持96字节的utf8，字符串（大小详见对应OPTIONS文件）
  String content = "";

  EAReminder();
  EAReminder.fromMap(Map<String, dynamic> map) {
    if (map["sw"] != null) {
      sw = map["sw"];
    }
    if (map["secSw"] != null) {
      secSw = map["secSw"];
    }
    if (map["sleepDuration"] != null) {
      sleepDuration = map["sleepDuration"];
    }
    if (map["content"] != null) {
      content = map["content"];
    }
    if (map["id_p"] != null) {
      id_p = map["id_p"];
    }
    if (map["hour"] != null) {
      hour = map["hour"];
    }
    if (map["minute"] != null) {
      minute = map["minute"];
    }
    if (map["year"] != null) {
      year = map["year"];
    }
    if (map["month"] != null) {
      month = map["month"];
    }
    if (map["day"] != null) {
      day = map["day"];
    }
    if (map["weekCycleBit"] != null) {
      weekCycleBit = map["weekCycleBit"];
    }
    if (map["eAction"] != null) {
      remindActionType = EARemindActionType.values[map["eAction"]];
    }
    if (map["eType"] != null) {
      reminderEventType = EAReminderEventType.values[map["eType"]];
    }
  }

  Map<String, dynamic> toMap() {
    return {
      "reminderEventType": reminderEventType.index,
      "eType": reminderEventType.index,
      "id_p": id_p,
      "hour": hour,
      "minute": minute,
      "year": year,
      "month": month,
      "day": day,
      "weekCycleBit": weekCycleBit,
      "sw": sw,
      "secSw": secSw,
      "sleepDuration": sleepDuration,
      "content": content,
      "remindActionType": remindActionType.index,
      "eAction": remindActionType.index,
    };
  }
}

/// 提醒事件与操作
class EAReminderOps {
  /// What are you do？【提醒事件操作】
  EAReminderEventOps eOps = EAReminderEventOps.Add;

  /// 删除、编辑时需要有对应的值,此时eOps需要赋值
  int id_p = 0;

  /// 最多16个（大小详见对应OPTIONS文件）
  List<EAReminder> list = <EAReminder>[];

  EAReminderOps();
  EAReminderOps.fromMap(Map<String, dynamic> map) {
    if (map["sIndexArray"] != null) {
      List sIndexArray = map["sIndexArray"];

      for (Map<String, dynamic> item in sIndexArray) {
        EAReminder eaReminder = EAReminder.fromMap(item);
        list.add(eaReminder);
      }
    }
    if (map["eOps"] != null) {
      eOps = EAReminderEventOps.values[map["eOps"]];
    }
    if (map["id_p"] != null) {
      id_p = map["id_p"];
    }
  }

  Map<String, dynamic> toMap() {
    List array = [];
    for (EAReminder reminder in list) {
      Map map = reminder.toMap();
      array.add(map);
    }
    return {"id_p": id_p, "eOps": eOps.index, "sIndexArray": array};
  }
}

class EAReminderRespond {
  EARespondCodeType eOpsStatus = EARespondCodeType.Success;

  /// 提醒id: 在write request的ops为新增 编辑回应中赋值，其他情况为0
  int id_p = 0;
}
