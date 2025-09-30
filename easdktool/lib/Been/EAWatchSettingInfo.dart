// ignore_for_file: file_names, constant_identifier_names, non_constant_identifier_names

part of easdktool.been;

/// 获取手表各个设置状态
class EAWatchSettingInfo {
  ///【充电状态：不可设置】
  EABatInfoStatus eBatStatus = EABatInfoStatus.normal;

  ///【电量值：不可设置】
  int batLevel = 0;

  ///【自动压力监测开关：可设置】
  int autoPressureSw = 0;

  ///【自动久坐监测开关：不可设置】
  int autoSedentarinessSw = 0;

  ///【抬手亮屏设置开关：不可设置】
  int gesturesSw = 0;

  ///【振动模式：可设置】
  EAVibrateIntensityType eVibrateIntensity = EAVibrateIntensityType.Medium;

  ///【佩戴方式：可设置】
  EAPersonHand eHandInfo = EAPersonHand.left;

  ///【单位设置：可设置】
  EAUnifiedUnitType eUnitFormat = EAUnifiedUnitType.metric;

  ///【心率监测开关：可设置】
  int autoCheckHrSw = 0;

  /// App not disturb time period switch 【App勿扰模式开关：不可设置】
  /// Use class EANotDisturb to set the Do Not Disturb period【使用 class EANotDisturb 设置免打扰时间段】
  int notDisturbSw = 0;

  ///【是否单独设定振动模式：可设置 （ 0:不单独设置 1:单独设置）】
  int setVibrateIntensity = 1;

  ///【表盘id：不可设置 (0代表在线自定义表盘，1~n，内置表盘编号)】
  int wfId = 1;

  ///【在线自定义表盘id：不可设置】
  String userWfId = "";

  EAWatchSettingInfo.fromMap(Map<String, dynamic> map) {
    if (map["eBatStatus"] != null) {
      eBatStatus = EABatInfoStatus.values[map["eBatStatus"]];
    }
    if (map["batLevel"] != null) {
      batLevel = map["batLevel"];
    }
    if (map["autoPressureSw"] != null) {
      autoPressureSw = map["autoPressureSw"];
    }
    if (map["autoSedentarinessSw"] != null) {
      autoSedentarinessSw = map["autoSedentarinessSw"];
    }
    if (map["gesturesSw"] != null) {
      gesturesSw = map["gesturesSw"];
    }
    if (map["eVibrateIntensity"] != null) {
      eVibrateIntensity =
          EAVibrateIntensityType.values[map["eVibrateIntensity"]];
    }
    if (map["eHandInfo"] != null) {
      eHandInfo = EAPersonHand.values[map["eHandInfo"]];
    }
    if (map["eUnitFormat"] != null) {
      eUnitFormat = EAUnifiedUnitType.values[map["eUnitFormat"]];
    }
    if (map["autoCheckHrSw"] != null) {
      autoCheckHrSw = map["autoCheckHrSw"];
    }
    if (map["notDisturbSw"] != null) {
      notDisturbSw = map["notDisturbSw"];
    }
    if (map["setVibrateIntensity"] != null) {
      setVibrateIntensity = map["setVibrateIntensity"];
    }
    if (map["wfId"] != null) {
      wfId = map["wfId"];
    }
    if (map["userWfId"] != null) {
      userWfId = map["userWfId"];
    }
  }
}
