// ignore_for_file: file_names, constant_identifier_names, non_constant_identifier_names

part of easdktool.been;

class EABigDataHabitTracker {
  //时间戳
  int timeStamp = 0;

  EAHabitTrackerIconType eIconId = EAHabitTrackerIconType.Study01;

// 起始时间(时)
  int beginHour = 0;

// 起始时间(分)
  int beginMinute = 0;

// 结束时间(时)
  int endHour = 0;

// 结束时间(分)
  int endMinute = 0;

// 调色版RGB565 (R)
  int r = 0;

// 调色版RGB565 (G)
  int g = 0;

// 调色版RGB565 (B)
  int b = 0;

// 标志 */
  EAHabitTrackerFlag eFlag = EAHabitTrackerFlag.EAHabitTrackerFlagInitial;

// 自定义内容：最多支持64字节的utf8，字符串（
  String content = '';

  EABigDataHabitTracker.fromMap(Map<String, dynamic> map) {
    if (map["timeStamp"] != null) {
      timeStamp = map["timeStamp"];
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

    if (map["eFlag"] != null) {
      eFlag = EAHabitTrackerFlag.values[map["eFlag"]];
    }

    if (map["eIconId"] != null) {
      eIconId = EAHabitTrackerIconType.values[map["eIconId"]];
    }

    if (map["e_flag"] != null) {
      eFlag = EAHabitTrackerFlag.values[map["e_flag"]];
    }

    if (map["e_icon_id"] != null) {
      eIconId = EAHabitTrackerIconType.values[map["e_icon_id"]];
    }

    if (map["time_stamp"] != null) {
      timeStamp = map["time_stamp"];
    }
    if (map["begin_hour"] != null) {
      beginHour = map["begin_hour"];
    }

    if (map["begin_minute"] != null) {
      beginMinute = map["begin_minute"];
    }

    if (map["end_minute"] != null) {
      endMinute = map["end_minute"];
    }

    if (map["end_hour"] != null) {
      endHour = map["end_hour"];
    }
  }
}
