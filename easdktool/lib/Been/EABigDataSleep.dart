// ignore_for_file: file_names, constant_identifier_names, non_constant_identifier_names

part of easdktool.been;

enum EASleepNode {
  // 活动状态
  Activity,

  // 进入睡眠 (!!!)
  Enter,

  // 睡眠中途醒来
  Wake,

  // 快速眼动
  Rem,

  // 浅睡
  Light,

  // 深睡
  Deep,

  // 退出睡眠(!!!)
  Quit,

  // 未知
  Unknown,

  // 睡眠摘要
  Summary,
}

class EABigDataSleep {
  // 时间戳
  int timeStamp = 0;

// 睡眠node
  EASleepNode eSleepNode = EASleepNode.Unknown;

  EABigDataSleep.fromMap(Map<String, dynamic> map) {
    if (map["timeStamp"] != null) {
      timeStamp = map["timeStamp"];
    }
    if (map["time_stamp"] != null) {
      timeStamp = map["time_stamp"];
    }
    if (map["eSleepNode"] != null) {
      eSleepNode = EASleepNode.values[map["eSleepNode"]];
    }
    if (map["e_sleep_node"] != null) {
      eSleepNode = EASleepNode.values[map["e_sleep_node"]];
    }
  }
}
