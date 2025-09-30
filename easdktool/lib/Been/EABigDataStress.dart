// ignore_for_file: file_names, constant_identifier_names, non_constant_identifier_names

part of easdktool.been;

/// MARK: - 压力数据类型
enum EAStressDataType {
  Unkown,
  // 放松
  StressRelax,
  // 正常
  StressNormal,
  // 中等
  StressMiddle,
  // 高
  StressHigh,
}

class EABigDataStress {
// 时间戳
  int timeStamp = 0;

// 压力
  int stessValue = 0;

// 压力类型
  EAStressDataType eType = EAStressDataType.Unkown;

  EABigDataStress.fromMap(Map<String, dynamic> map) {
    if (map["timeStamp"] != null) {
      timeStamp = map["timeStamp"];
    }

    if (map["stessValue"] != null) {
      stessValue = map["stessValue"];
    }
    if (map["eType"] != null) {
      eType = EAStressDataType.values[map["eType"]];
    }

    if (map["time_stamp"] != null) {
      timeStamp = map["time_stamp"];
    }
    if (map["stess_value"] != null) {
      stessValue = map["stess_value"];
    }
    if (map["e_type"] != null) {
      eType = EAStressDataType.values[map["e_type"]];
    }
  }
}
