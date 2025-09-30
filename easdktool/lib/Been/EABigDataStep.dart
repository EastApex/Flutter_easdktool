// ignore_for_file: file_names, constant_identifier_names, non_constant_identifier_names

part of easdktool.been;

class EABigDataStep {
  int calorie = 354;
  int distance = 0;
  int duration = 0;
  int steps = 0;
  int timeStamp = 0;

  EABigDataStep.fromMap(Map<String, dynamic> map) {
    if (map["calorie"] != null) {
      calorie = map["calorie"];
    }
    if (map["distance"] != null) {
      distance = map["distance"];
    }
    if (map["duration"] != null) {
      duration = map["duration"];
    }
    if (map["steps"] != null) {
      steps = map["steps"];
    }
    if (map["timeStamp"] != null) {
      timeStamp = map["timeStamp"];
    }
    if (map["time_stamp"] != null) {
      timeStamp = map["time_stamp"];
    }
  }
}
