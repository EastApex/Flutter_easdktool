// ignore_for_file: file_names, constant_identifier_names, non_constant_identifier_names

part of easdktool.been;

/// 日常目标值明细
class EADailyGoalItem extends Object {
  // 开关： 0关闭 1打开
  int sw = 0;
  // 目标值
  int goal = 0;

  // 默认的构造方法，无参的构造方法可以省略
  EADailyGoalItem();

  EADailyGoalItem.wihtValue(this.sw, this.goal);

  EADailyGoalItem.fromMap(Map<String, dynamic> map) {
    if (map["goal"] != null) {
      goal = map["goal"];
    }
    if (map["sw"] != null) {
      sw = map["sw"];
    }
  }
}

/// 日常目标值设置
class EADailyGoals {
  // 步数，目标值单位: 步
  EADailyGoalItem sStep = EADailyGoalItem();

  // 卡路里，目标值单位: 卡
  EADailyGoalItem sCalorie = EADailyGoalItem();

  // 距离，目标值单位: 米
  EADailyGoalItem sDistance = EADailyGoalItem();

  // 运动时长，目标值单位: 秒
  EADailyGoalItem sDuration = EADailyGoalItem();

  // 睡眠时长，目标值单位: 秒
  EADailyGoalItem sSleep = EADailyGoalItem();
  EADailyGoals();
  EADailyGoals.fromMap(Map<String, dynamic> map) {
    if (map["sCalorie"] != null) {
      sCalorie = EADailyGoalItem.fromMap(map["sCalorie"]);
    }
    if (map["sStep"] != null) {
      sStep = EADailyGoalItem.fromMap(map["sStep"]);
    }
    if (map["sDistance"] != null) {
      sDistance = EADailyGoalItem.fromMap(map["sDistance"]);
    }
    if (map["sDuration"] != null) {
      sDuration = EADailyGoalItem.fromMap(map["sDuration"]);
    }
    if (map["sSleep"] != null) {
      sSleep = EADailyGoalItem.fromMap(map["sSleep"]);
    }
  }

  Map<String, dynamic> toMap() {
    return {
      "sCalorie": {
        "sw": sCalorie.sw,
        "goal": sCalorie.goal,
      },
      "sStep": {
        "sw": sStep.sw,
        "goal": sStep.goal,
      },
      "sDistance": {
        "sw": sDistance.sw,
        "goal": sDistance.goal,
      },
      "sDuration": {
        "sw": sDuration.sw,
        "goal": sDuration.goal,
      },
      "sSleep": {
        "sw": sSleep.sw,
        "goal": sSleep.goal,
      },
    };
  }
}
