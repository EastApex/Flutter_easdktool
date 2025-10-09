// ignore_for_file: file_names, constant_identifier_names, non_constant_identifier_names

part of easdktool.been;

class EARespond {
  EARespondCodeType respondCodeType = EARespondCodeType.Success;
  int dataType = 0;

  EARespond();
  EARespond.fromMap(Map<String, dynamic> map) {
    if (map["dataType"] != null) {
      dataType = map["dataType"];
    }
    if (map["respondCodeType"] != null) {
      respondCodeType = EARespondCodeType.values[map["respondCodeType"]];
    }
  }
}
