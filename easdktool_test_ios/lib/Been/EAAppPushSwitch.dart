// ignore_for_file: file_names, constant_identifier_names, non_constant_identifier_names

part of easdktool.been;

class EAShowAppMessage {
  bool unknow = true;
  bool wechat = true;
  bool qq = true;
  bool facebook = true;
  bool twitter = true;
  bool messenger = true;
  bool hangouts = true;
  bool gmail = true;
  bool viber = true;
  bool snapchat = true;
  bool whatsApp = true;
  bool instagram = true;
  bool linkedin = true;
  bool line = true;
  bool skype = true;
  bool booking = true;
  bool airbnb = true;
  bool flipboard = true;
  bool spotify = true;
  bool pandora = true;
  bool telegram = true;
  bool dropbox = true;
  bool waze = true;
  bool lift = true;
  bool slack = true;
  bool shazam = true;
  bool deliveroo = true;
  bool kakaotalk = true;
  bool pinterest = true;
  bool tumblr = true;
  bool vk = true;
  bool youtube = true;
  bool amazon = true;
  bool discord = true;
  bool github = true;
  bool googleMaps = true;
  bool newsBreak = true;
  bool rReddit = true;
  bool teams = true;
  bool tiktok = true;
  bool twitch = true;
  bool uberEats = true;

  Map toMap() {
    return {
      "unknow": unknow,
      "wechat": wechat,
      "qq": qq,
      "facebook": facebook,
      "twitter": twitter,
      "messenger": messenger,
      "hangouts": hangouts,
      "gmail": gmail,
      "viber": viber,
      "snapchat": snapchat,
      "whatsApp": whatsApp,
      "instagram": instagram,
      "linkedin": linkedin,
      "line": line,
      "skype": skype,
      "booking": booking,
      "airbnb": airbnb,
      "flipboard": flipboard,
      "spotify": spotify,
      "pandora": pandora,
      "telegram": telegram,
      "dropbox": dropbox,
      "waze": waze,
      "lift": lift,
      "slack": slack,
      "shazam": shazam,
      "deliveroo": deliveroo,
      "kakaotalk": kakaotalk,
      "pinterest": pinterest,
      "tumblr": tumblr,
      "vk": vk,
      "youtube": youtube,
      "amazon": amazon,
      "discord": discord,
      "github": github,
      "googleMaps": googleMaps,
      "newsBreak": newsBreak,
      "rReddit": rReddit,
      "teams": teams,
      "tiktok": tiktok,
      "twitch": twitch,
      "uberEats": uberEats,
    };
  }

  EAShowAppMessage();
  EAShowAppMessage.fromMap(Map<String, dynamic> map) {
    if (map["unknow"] != null) {
      unknow = map["unknow"];
    }
    if (map["wechat"] != null) {
      wechat = map["wechat"];
    }

    if (map["qq"] != null) {
      qq = map["qq"];
    }

    if (map["facebook"] != null) {
      facebook = map["facebook"];
    }

    if (map["twitter"] != null) {
      twitter = map["twitter"];
    }

    if (map["messenger"] != null) {
      messenger = map["messenger"];
    }

    if (map["hangouts"] != null) {
      hangouts = map["hangouts"];
    }

    if (map["gmail"] != null) {
      gmail = map["gmail"];
    }

    if (map["viber"] != null) {
      viber = map["viber"];
    }

    if (map["snapchat"] != null) {
      snapchat = map["snapchat"];
    }

    if (map["whatsApp"] != null) {
      whatsApp = map["whatsApp"];
    }

    if (map["instagram"] != null) {
      instagram = map["instagram"];
    }

    if (map["linkedin"] != null) {
      linkedin = map["linkedin"];
    }

    if (map["line"] != null) {
      line = map["line"];
    }

    if (map["skype"] != null) {
      skype = map["skype"];
    }

    if (map["booking"] != null) {
      booking = map["booking"];
    }

    if (map["airbnb"] != null) {
      airbnb = map["airbnb"];
    }

    if (map["flipboard"] != null) {
      flipboard = map["flipboard"];
    }

    if (map["spotify"] != null) {
      spotify = map["spotify"];
    }

    if (map["pandora"] != null) {
      pandora = map["pandora"];
    }

    if (map["telegram"] != null) {
      telegram = map["telegram"];
    }

    if (map["dropbox"] != null) {
      dropbox = map["dropbox"];
    }

    if (map["waze"] != null) {
      waze = map["waze"];
    }

    if (map["lift"] != null) {
      lift = map["lift"];
    }

    if (map["slack"] != null) {
      slack = map["slack"];
    }

    if (map["shazam"] != null) {
      shazam = map["shazam"];
    }

    if (map["deliveroo"] != null) {
      deliveroo = map["deliveroo"];
    }

    if (map["kakaotalk"] != null) {
      kakaotalk = map["kakaotalk"];
    }

    if (map["pinterest"] != null) {
      pinterest = map["pinterest"];
    }

    if (map["tumblr"] != null) {
      tumblr = map["tumblr"];
    }

    if (map["vk"] != null) {
      vk = map["vk"];
    }

    if (map["youtube"] != null) {
      youtube = map["youtube"];
    }

    if (map["amazon"] != null) {
      amazon = map["amazon"];
    }

    if (map["discord"] != null) {
      discord = map["discord"];
    }

    if (map["github"] != null) {
      github = map["github"];
    }

    if (map["googleMaps"] != null) {
      googleMaps = map["googleMaps"];
    }

    if (map["newsBreak"] != null) {
      newsBreak = map["newsBreak"];
    }

    if (map["rReddit"] != null) {
      rReddit = map["rReddit"];
    }

    if (map["teams"] != null) {
      teams = map["teams"];
    }

    if (map["tiktok"] != null) {
      tiktok = map["tiktok"];
    }

    if (map["twitch"] != null) {
      twitch = map["twitch"];
    }

    if (map["uberEats"] != null) {
      uberEats = map["uberEats"];
    }
  }
}

class EAApp {
  bool sw = true;

  EAApp(this.sw);
  EAApp.fromMap(Map<String, dynamic> map) {
    if (map["sw"] != null) {
      sw = map["sw"];
    }
  }

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

  EAAppPushSwitch();
  EAAppPushSwitch.fromMap(Map<String, dynamic> map) {
    List array = map["sAppSwArray"];
    for (Map<String, dynamic> map in array) {
      EAApp app = EAApp.fromMap(map);
      list.add(app);
    }
  }
  Map toMap() {
    List array = [];
    for (EAApp app in list) {
      Map map = app.toMap();
      array.add(map);
    }
    return {"sIndexArray": array};
  }
}
