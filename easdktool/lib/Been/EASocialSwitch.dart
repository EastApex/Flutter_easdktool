// ignore_for_file: file_names, constant_identifier_names, non_constant_identifier_names

part of easdktool.been;

class EASocialOps {
  // 开关： 0关闭 1打开
  int sw = 0;

  /// 提醒方式
  EARemindActionType remindActionType = EARemindActionType.NoAction;

  EASocialOps();
  EASocialOps.init(this.sw, this.remindActionType);

  EASocialOps.fromMap(Map<String, dynamic> map) {
    if (map["sw"] != null) {
      sw = map["sw"];
    }
    if (map["remindActionType"] != null) {
      remindActionType = EARemindActionType.values[map["remindActionType"]];
    }
  }
  Map<String, dynamic> toMap() {
    return {
      "sw": sw,
      "remindActionType": remindActionType.index,
      "eAction": remindActionType.index,
    };
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

  EASocialSwitch();
  EASocialSwitch.init(int sIncomingcall, int sMissedcall, int sSms, int sSocial,
      int sEmail, int sSchedule, EARemindActionType remindActionType) {
    this.sIncomingcall = EASocialOps.init(sIncomingcall, remindActionType);
    this.sMissedcall = EASocialOps.init(sMissedcall, remindActionType);
    this.sSms = EASocialOps.init(sSms, remindActionType);
    this.sSocial = EASocialOps.init(sSocial, remindActionType);
    this.sSchedule = EASocialOps.init(sSchedule, remindActionType);
    this.sEmail = EASocialOps.init(sEmail, remindActionType);
  }

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

  Map<String, dynamic> toMap() {
    return {
      "sIncomingcall": sIncomingcall.toMap(),
      "sMissedcall": sMissedcall.toMap(),
      "sSms": sSms.toMap(),
      "sSocial": sSocial.toMap(),
      "sEmail": sEmail.toMap(),
      "sSchedule": sSchedule.toMap(),
    };
  }
}
