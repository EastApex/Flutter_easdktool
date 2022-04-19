// ignore_for_file: file_names, constant_identifier_names, non_constant_identifier_names

part of easdktool.been;

class EASocialOps {
  // 开关： 0关闭 1打开
  int sw = 0;

  /// 提醒方式
  EARemindActionType remindActionType = EARemindActionType.NoAction;

  EASocialOps();
  EASocialOps.fromMap(Map<String, dynamic> map) {
    if (map["sw"] != null) {
      sw = map["sw"];
    }
    if (map["remindActionType"] != null) {
      remindActionType = EARemindActionType.values[map["remindActionType"]];
    }
  }
}

class EASocialSwitch {
  /// 来电
  EASocialOps sIncomingcall = EASocialOps();

  /// 未知来电
  EASocialOps sMissedcall = EASocialOps();

  /// 短信
  EASocialOps sSms = EASocialOps();

  /// 社交
  EASocialOps sSocial = EASocialOps();

  /// 邮件
  EASocialOps sEmail = EASocialOps();

  /// 日程
  EASocialOps sSchedule = EASocialOps();

  EASocialSwitch.fromMap(Map<String, dynamic> map) {
    if (map["sIncomingcall"] != null) {
      sIncomingcall = EASocialOps.fromMap(map["sIncomingcall"]);
    }
    if (map["sMissedcall"] != null) {
      sMissedcall = EASocialOps.fromMap(map["sMissedcall"]);
    }
    if (map["sSms"] != null) {
      sSms = EASocialOps.fromMap(map["sSms"]);
    }
    if (map["sSocial"] != null) {
      sSocial = EASocialOps.fromMap(map["sSocial"]);
    }
    if (map["sEmail"] != null) {
      sEmail = EASocialOps.fromMap(map["sEmail"]);
    }
    if (map["sSchedule"] != null) {
      sEmail = EASocialOps.fromMap(map["sSchedule"]);
    }
  }
}
