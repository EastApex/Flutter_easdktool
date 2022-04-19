// ignore_for_file: file_names, constant_identifier_names, non_constant_identifier_names

part of easdktool.been;

class EAApp {
  bool sw = true;

  EAApp(this.sw);
  Map toMap() {
    return {"sw": sw};
  }
}

class EAAppPushSwitch {
  /*
 unknow=0;        //类型：其他社交类型
 wechat =1;
 qq=2;
 facebook=3;
 twitter = 4;
 messenger =5;
 hangouts =6;
 gmail = 7;
 viber=8;
 snapchat=9;
 whatsApp=10;
 instagram =11;
 linkedin =12;
 line =13;
 skype =14;
 booking =15;
 airbnb =16;
 flipboard =17;
 spotify =18;
 pandora =19;
 telegram =20;
 dropbox =21;
 waze =22;
 lift =23;
 slack =24;
 shazam =25;
 deliveroo =26;
 kakaotalk =27;
 pinterest =28;
 tumblr =29;
 vk =30;
 youtube=31;
 */
  /// list的元素全部为以上32种，
  List<EAApp> list = [];

  Map toMap() {
    List array = [];
    for (EAApp app in list) {
      Map map = app.toMap();
      array.add(map);
    }
    return {"sIndexArray": array};
  }
}
