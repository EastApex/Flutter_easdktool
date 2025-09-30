// ignore_for_file: file_names, constant_identifier_names, non_constant_identifier_names

part of easdktool.been;

/// 表盘信息
class EAWatchFacelInfo {
  //// 表盘id, (0代表在线自定义表盘，1~n，内置表盘编号)
  int builtInId = 1;

  //// 在线自定义表盘id
  String userWfId = '';

  //// 已存在线自定义表盘id0
  String userWfId0 = '';

  //// 已存在线自定义表盘id1
  String userWfId1 = '';

  //// 已存在线自定义表盘id2
  String userWfId2 = '';

  //// 已存在线自定义表盘id3
  String userWfId3 = '';

  EAWatchFacelInfo();
  EAWatchFacelInfo.buildIn(this.builtInId);

  EAWatchFacelInfo.fromMap(Map<String, dynamic> map) {
    if (map["id_p"] != null) {
      builtInId = map["id_p"];
    }
    if (map["userWfId"] != null) {
      userWfId = map["userWfId"];
    }
    if (map["userWfId0"] != null) {
      userWfId0 = map["userWfId0"];
    }
    if (map["userWfId1"] != null) {
      userWfId1 = map["userWfId1"];
    }
    if (map["userWfId2"] != null) {
      userWfId2 = map["userWfId2"];
    }
    if (map["userWfId3"] != null) {
      userWfId3 = map["userWfId3"];
    }
  }
  Map toMap() {
    return {
      "id_p": builtInId,
    };
  }
}
