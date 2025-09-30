// ignore_for_file: file_names, constant_identifier_names, non_constant_identifier_names
part of easdktool.been;

class EAOTA {
  String binPath = ""; // Local bin path【本地bin路径】
  EAFirmwareType firmwareType =
      EAFirmwareType.Apollo; // The firmware type【固件类型】
  String version = ""; // 【版本号】

  EAOTA(this.binPath, this.firmwareType, this.version);

  EAOTA.watchface(String filePath) {
    binPath = filePath;
    firmwareType = EAFirmwareType.wf;
  }
  Map toJson() {
    return {
      "binPath": binPath,
      "firmwareType": firmwareType.index,
      "version": version
    };
  }
}

class EAOTAList {
  int type = 0; // 0:ota 1:watch face
  List<EAOTA> otas = [];

  EAOTAList(this.type, this.otas);

  Map toJson() {
    return {"type": type, "otas": otas};
  }
}

class EACustomWatchFace {
  bool isNumberWf = true;
  String bgImagePath = "";
  String numbeColorHex = "";
  int pointerColorType = 0; // 0 black  1 wehite
  bool getPreviewImage =
      true; // true:get Preview Image  false: Ota Custom Watch Face

  EACustomWatchFace();
  Map toJson() {
    return {
      "isNumberWf": isNumberWf,
      "bgImagePath": bgImagePath,
      "numbeColorHex": numbeColorHex,
      "pointerColorType": pointerColorType,
      "getPreviewImage": getPreviewImage,
    };
  }
}
