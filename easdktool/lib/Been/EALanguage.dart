// ignore_for_file: file_names, constant_identifier_names, non_constant_identifier_names
part of easdktool.been;

/// 手表语言
class EALanguage {
  EALanguageType type = EALanguageType.default0;
  EALanguage();
  EALanguage.fromMap(Map<String, dynamic> map) {
    if (map["eType"] != null) {
      type = EALanguageType.values[map["eType"]];
    }
  }
  Map<String, dynamic> toMap() {
    return {
      "language": type.index,
    };
  }
}
