// ignore_for_file: file_names, constant_identifier_names, non_constant_identifier_names

part of easdktool.been;

/// 手表信息
class EABleWatchInfo {
  String snNumber = ""; // sn号
  String watchType = ""; // 手表型号
  String firmwareVersion = ""; //  版本号
  EABindingType bindingType = EABindingType.unbound; // 绑定状态
  String userId = ""; // 已绑定的用户id
  int agpsUpdateTimestamp = 0; // agps更新时间
  String bleMacAddr = ""; //
  int isWaitForBinding =
      0; // Whether to wait for the device to confirm binding【是否需要等待设备确认绑定】

  EABleWatchInfo.fromMap(Map<String, dynamic> map) {
    if (map["agpsUpdateTimestamp"] != null) {
      agpsUpdateTimestamp = map["agpsUpdateTimestamp"];
    }

    if (map["eBindingInfo"] != null) {
      int eBindingInfo = map["eBindingInfo"];
      bindingType = EABindingType.values[eBindingInfo];
    }

    if (map["firmwareVersion"] != null) {
      firmwareVersion = map["firmwareVersion"];
    }

    if (map["userId"] != null) {
      userId = map["userId"];
    }

    if (map["id_p"] != null) {
      snNumber = map["id_p"];
    }

    if (map["type"] != null) {
      watchType = map["type"];
    }
    if (map["bleMacAddr"] != null) {
      bleMacAddr = map["bleMacAddr"];
    }
    if (map["isWaitForBinding"] != null) {
      isWaitForBinding = map["isWaitForBinding"];
    }
  }
}
