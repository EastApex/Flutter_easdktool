part of easdktool.been;

class JieliWatchFace {
  String? name;

  int status = 0;
  String? bitmapUri;
  String? uuid;
  String? version;
  int size = 0;
  String? fileUrl;
  String? updateUUID;
  String? updateVersion;
  String? updateUrl;

  String? customBgFatPath;

  String? path;


  JieliWatchFace.fromMap(Map<String, dynamic> map) {
    if (map["name"] != null) {
      name = map["name"];
    }
    if (map["status"] != null) {
      status = map["status"];
    }
    if (map["bitmapUri"] != null) {
      bitmapUri = map["bitmapUri"];
    }
    if (map["uuid"] != null) {
      uuid = map["uuid"];
    }
    if (map["version"] != null) {
      version = map["version"];
    }
    if (map["size"] != null) {
      size = map["size"];
    }
    if (map["fileUrl"] != null) {
      fileUrl = map["fileUrl"];
    }
    if (map["updateUUID"] != null) {
      updateUUID = map["updateUUID"];
    }
    if (map["updateVersion"] != null) {
      updateVersion = map["updateVersion"];
    }
    if (map["updateUrl"] != null) {
      updateUrl = map["updateUrl"];
    }
    if (map["customBgFatPath"] != null) {
      customBgFatPath = map["customBgFatPath"];
    }
    if (map["path"] != null) {
      path = map["path"];
    }
  }
}