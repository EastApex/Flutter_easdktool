// ignore_for_file: file_names, constant_identifier_names, non_constant_identifier_names

part of easdktool.been;

/*
 * 个人信息
 */
class EAPersonInfo {
  EAPersonSex sex = EAPersonSex.female; //性别
  int age = 0; //年龄
  int height = 0; //身高 单位cm
  int weight = 0; //体重 单位g
  EAPersonHand personHand = EAPersonHand.left; //穿戴方式
  EASkinColor skinColor = EASkinColor.skinYellow; //肤色

  EAPersonInfo();
  EAPersonInfo.fromMap(Map<String, dynamic> map) {
    if (map["eSexInfo"] != null) {
      sex = EAPersonSex.values[map["eSexInfo"]];
    }

    if (map["age"] != null) {
      age = map["age"];
    }

    if (map["height"] != null) {
      height = map["height"];
    }

    if (map["weight"] != null) {
      weight = map["weight"];
    }

    if (map["eHandInfo"] != null) {
      personHand = EAPersonHand.values[map["eHandInfo"]];
    }

    if (map["eSkinColor"] != null) {
      skinColor = EASkinColor.values[map["eSkinColor"]];
    }
  }

  Map<String, dynamic> toMap() {
    return {
      "eSexInfo": sex.index,
      "age": age,
      "height": height,
      "weight": weight,
      "eHandInfo": personHand.index,
      "eSkinColor": skinColor.index
    };
  }
}
