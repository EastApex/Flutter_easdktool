// ignore_for_file: file_names, constant_identifier_names, non_constant_identifier_names

part of easdktool.been;

class EABindInfo {
  String? user_id;
  // 绑定模式 0: Normal mode 【正常模式】  1:Stepper data is stored at fixed intervals 【计步数据固定间隔存储】
  int bindMod = 0;

  EABindInfo();

  Map<String, dynamic> toMap() {
    return {"user_id": user_id, "bindMod": bindMod};
  }
}
