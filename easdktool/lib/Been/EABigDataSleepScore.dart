// ignore_for_file: file_names, constant_identifier_names, non_constant_identifier_names

part of easdktool.been;

class EABigDataSleepScore {
  int sleepScore = 0;
  int beginTimeStamp = 0;
  int endTimeStamp = 0;

  EABigDataSleepScore.fromMap(Map<String, dynamic> map) {
    if (map["sleepScore"] != null) {
      sleepScore = map["sleepScore"];
    }
    if (map["beginTimeStamp"] != null) {
      beginTimeStamp = map["beginTimeStamp"];
    }
    if (map["endTimeStamp"] != null) {
      beginTimeStamp = map["beginTimeStamp"];
    }
  }
}
