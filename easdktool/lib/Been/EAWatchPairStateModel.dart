// ignore_for_file: file_names, constant_identifier_names, non_constant_identifier_names

part of easdktool.been;

class EAWatchPairStateModel {
  bool secState = false;

  EAWatchPairStateModel.fromMap(Map<String, dynamic> map) {
    if (map["secState"] != null) {
      secState = map["secState"];
    }
  }
}
