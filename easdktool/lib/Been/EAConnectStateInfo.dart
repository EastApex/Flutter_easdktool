// ignore_for_file: file_names, constant_identifier_names, non_constant_identifier_names

part of easdktool.been;

/// 日常目标值明细
class EAConnectStateInfo {
  EAConnectStateType connectState =
      EAConnectStateType.EAConnectStateTypeUnconnected;

  EAConnectStateInfo.fromMap(Map<String, dynamic> map) {
    if (map["connectState"] != null) {
      connectState = EAConnectStateType.values[map["connectState"]];
    }
  }
}
