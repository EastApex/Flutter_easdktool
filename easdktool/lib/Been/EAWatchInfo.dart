// ignore_for_file: file_names, constant_identifier_names, non_constant_identifier_names

part of easdktool.been;

/// 手表信息
class EABleWatchInfo {
/*
 * firmwareVersion => AP0.1B0.9R0.3T0.1G0.1
 * AP0.1B0.9 => 固件版本号是0.1 build 0.9
 * R0.3=>字库版本号是0.3
 * T0.1=>屏幕版本号0.1
 * H0.1=>心率版本号0.1
 */

  String snNumber = ""; // sn号
  String watchType = ""; // 手表型号
  String firmwareVersion = ""; //  版本号
  EABindingType bindingType = EABindingType.unbound; // 绑定状态
  String userId = ""; // 已绑定的用户id
  int agpsUpdateTimestamp = 0; // agps Update Timestamp
  String bleMacAddr = ""; //
  int isWaitForBinding =
      0; // Whether to wait for the device to confirm binding【是否需要等待设备确认绑定】
  int lcdPixelType = 0; // if 4 is jl 707 watch
  String custom_firmware_version = "";

  // int proj_settings = 0;				//项目功能列表: 0 不支持 1:支持 (固件端实现 id=44协议后才能写1)
  // int lcd_full_w =0;				//LCD表盘宽度
  // int lcd_full_h =01;				//LCD表盘高度
  // int lcd_full_type =0;				//LCD表盘类型: 0:方屏 1:圆屏
  // int lcd_preview_w =0;				//LCD表盘缩略图宽度
  // int lcd_preview_h =0;				//LCD表盘缩略图高度
  // int lcd_preview_radius =0;				//LCD表盘缩略图圆角半径
  // int not_support_sn =0;				//不支持sn号绑定手表: 0:支持 1:不支持
  // int max_watch_size = 0;				//最大支持表盘内存大小（存储空间,单位KB）
  // int lcd_pixel_type = 0;				//LCD像素类型: 0: RGB565 1: GXGPU_RGB888 2: ARGB8565(杰理)  3: 海思平台
  // int num_of_alarm_supported = 0;			//支持闹钟数量：0:默认支持10个  n:支持n个
  // int num_of_schedule_supported = 20;			//支持日程数量：0:默认支持10个  n:支持n个
  // int custom_firmware_version =0;			//客户版本号(如果有值，则用于显示给客户)：最多支持32字节的utf8，字符串（大小详见对应OPTIONS文件）

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
    if (map["lcdPixelType"] != null) {
      isWaitForBinding = map["lcdPixelType"];
    }
    if (map["lcd_pixel_type"] != null) {
      isWaitForBinding = map["lcd_pixel_type"];
    }
    if (map["custom_firmware_version"] != null) {
      custom_firmware_version = map["custom_firmware_version"];
    }
  }
}
