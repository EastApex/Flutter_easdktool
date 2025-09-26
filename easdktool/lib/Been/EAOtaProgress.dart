part of easdktool.been;

class EAOtaProgress {
  int progress = -1;
  int isSuccess = 0;
  int errorType = -1;
  String error = "";

  EAOtaProgress();

  EAOtaProgress.fromMap(Map<String, dynamic> map) {
    if (map["progress"] != null) {
      progress = map["progress"];
    }
    if (map["isSuccess"] != null) {
      isSuccess = map["isSuccess"];
    }
    if (map["errorType"] != null) {
      errorType = map["errorType"];
    }
    if (map["error"] != null) {
      error = map["error"];
    }
  }
}
