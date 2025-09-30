// ignore_for_file: file_names, constant_identifier_names, non_constant_identifier_names

part of easdktool.been;

class EAContactModel {
  String name = "";
  String num = "";

  EAContactModel(this.name, this.num);

  Map<String, dynamic> toMap() {
    return {"name": name, "num": num};
  }
}

class EATelephoneBook {
  List<EAContactModel> contacts = <EAContactModel>[];

  EATelephoneBook();
  Map<String, dynamic> toMap() {
    List list = [];
    for (EAContactModel day in contacts) {
      Map map = day.toMap();
      list.add(map);
    }
    return {"sIndexArray": list};
  }
}

class EAPhoneModel {
  String num = "";

  EAPhoneModel(this.num);

  Map<String, dynamic> toMap() {
    return {"num": num};
  }

  EAPhoneModel.fromMap(Map<String, dynamic> map) {
    if (map["num"] != null) {
      num = map["num"];
    }
  }
}

class EAReadTelephoneBook {
  List<EAPhoneModel> phones = <EAPhoneModel>[];
  EAReadTelephoneBook();
  EAReadTelephoneBook.formMap(Map<String, dynamic> map) {
    if (map["sIndexArray"] != null) {
      List sPageArray = map["sIndexArray"];
      for (Map<String, dynamic> item in sPageArray) {
        EAPhoneModel phoneModel = EAPhoneModel.fromMap(item);
        phones.add(phoneModel);
      }
    }
  }
}
