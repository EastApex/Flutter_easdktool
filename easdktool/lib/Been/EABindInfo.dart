// ignore_for_file: file_names, constant_identifier_names, non_constant_identifier_names

part of easdktool.been;

class EABindInfo {
  String? user_id;

  EABindInfo();

  Map<String, dynamic> toMap() {
    return {"user_id": user_id};
  }
}
