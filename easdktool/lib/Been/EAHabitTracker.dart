// ignore_for_file: file_names, constant_identifier_names, non_constant_identifier_names

part of easdktool.been;

/// 习惯
class EAHabitTracker {
  /// 习惯icon
  EAHabitTrackerIconType eIconId = EAHabitTrackerIconType.Study01;

  /// read respond中各提醒的id，其他情况为0
  int id_p = 0;

  /// 起始时间(时)
  int beginHour = 0;

  /// 起始时间(分)
  int beginMinute = 0;

  /// 结束时间(时)
  int endHour = 0;

  /// 结束时间(分)
  int endMinute = 0;

  /// 调色版RGB565 (R)
  int r = 0;

  /// 调色版RGB565 (G)
  int g = 0;

  /// 调色版RGB565 (B)
  int b = 0;

  /// 贪睡时间（单位：分钟）
  int duration = 0;

  /// 提醒方式
  EARemindActionType eAction = EARemindActionType.NoAction;

  /// 自定义内容：最多支持32字节的utf8，字符串（大小详见对应OPTIONS文件）
  String content = "";

  EAHabitTracker();
  EAHabitTracker.fromMap(Map<String, dynamic> map) {
    if (map["r"] != null) {
      r = map["r"];
    }
    if (map["g"] != null) {
      g = map["g"];
    }
    if (map["b"] != null) {
      b = map["b"];
    }
    if (map["content"] != null) {
      content = map["content"];
    }
    if (map["id_p"] != null) {
      id_p = map["id_p"];
    }
    if (map["duration"] != null) {
      duration = map["duration"];
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
    if (map["eIconId"] != null) {
      eIconId = EAHabitTrackerIconType.values[map["eIconId"]];
    }
    if (map["eAction"] != null) {
      eAction = EARemindActionType.values[map["eAction"]];
    }
  }
}

/// 习惯列表
class EAHabitTrackers {
  EAHabitTrackerOps eOps = EAHabitTrackerOps.Add;

  /// id: 在write request的ops为编辑 删除此条操作中赋值，其他情况为0
  int id_p = 0;

  /// 最多20个
  List<EAHabitTracker> list = [];

  EAHabitTrackers.formMap(Map<String, dynamic> map) {
    if (map["sIndexArray"] != null) {
      List sPageArray = map["sIndexArray"];
      for (Map<String, dynamic> item in sPageArray) {
        EAHabitTracker habitTracker = EAHabitTracker.fromMap(item);
        list.add(habitTracker);
      }
    }
    if (map["eOps"] != null) {
      eOps = EAHabitTrackerOps.values[map["eOps"]];
    }
    if (map["id_p"] != null) {
      id_p = map["id_p"];
    }
  }
}
