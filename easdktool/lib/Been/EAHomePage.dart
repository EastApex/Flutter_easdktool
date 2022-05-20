// ignore_for_file: file_names, constant_identifier_names, non_constant_identifier_names

part of easdktool.been;

/// 页面类型
class EAPage {
  EAFirstLeverType type = EAFirstLeverType.PageNull;

  EAPage();
  EAPage.hr() {
    type = EAFirstLeverType.PageHeartRate;
  }
  EAPage.pressure() {
    type = EAFirstLeverType.PagePressure;
  }
  EAPage.weather() {
    type = EAFirstLeverType.PageWeather;
  }
  EAPage.music() {
    type = EAFirstLeverType.PageMusic;
  }
  EAPage.breath() {
    type = EAFirstLeverType.PageBreath;
  }
  EAPage.sleep() {
    type = EAFirstLeverType.PageSleep;
  }
  EAPage.menstrualCycle() {
    type = EAFirstLeverType.PageMenstrualCycle;
  }

  EAPage.fromMap(Map<String, dynamic> map) {
    if (map["eType"] != null) {
      type = EAFirstLeverType.values[map["eType"]];
    }
  }

  Map toMap() {
    return {
      "eType": type.index,
    };
  }
}

/// 一级菜单
class EAHomePages {
  List<EAPage> list = <EAPage>[];
  List<EAPage> supportPageArray = <EAPage>[];

  EAHomePages();
  EAHomePages.fromMap(Map<String, dynamic> map) {
    if (map["sPageArray"] != null) {
      List sPageArray = map["sPageArray"];
      for (Map<String, dynamic> item in sPageArray) {
        EAPage page = EAPage.fromMap(item);
        list.add(page);
      }
    }
    if (map["supportPageArray"] != null) {
      List supportPageArray = map["supportPageArray"];
      for (Map<String, dynamic> item in supportPageArray) {
        EAPage page = EAPage.fromMap(item);
        supportPageArray.add(page);
      }
    }
  }

  Map toMap() {
    List pages = [];
    for (EAPage page in list) {
      pages.add(page.toMap());
    }
    return {"sPageArray": pages, "supportPageArray": supportPageArray};
  }
}
