part of easdktool.been;

class EAOtaProgress {
  int progress = -1;
  bool isSuccess = false;

  EAOtaProgress();

  EAOtaProgress.fromMap(Map<String, dynamic> map) {
    if (map["progress"] != null) {
      progress = map["progress"];
    }
    if (map["isSuccess"] != null) {
      isSuccess = map["isSuccess"];
    }
  }
}
