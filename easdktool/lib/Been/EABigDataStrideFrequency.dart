// ignore_for_file: file_names, constant_identifier_names, non_constant_identifier_names

part of easdktool.been;

class EABigDataStrideFrequency {
// 时间戳
  int timeStamp = 0;

// 步频值
  int stepFreqValue = 0;

  EABigDataStrideFrequency.fromMap(Map<String, dynamic> map) {
    if (map["timeStamp"] != null) {
      timeStamp = map["timeStamp"];
    }
    if (map["time_stamp"] != null) {
      timeStamp = map["time_stamp"];
    }
    if (map["stepFreqValue"] != null) {
      stepFreqValue = map["stepFreqValue"];
    }
    if (map["step_freq_value"] != null) {
      stepFreqValue = map["step_freq_value"];
    }
  }
}
