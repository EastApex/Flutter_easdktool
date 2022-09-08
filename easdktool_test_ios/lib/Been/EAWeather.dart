// ignore_for_file: file_names, constant_identifier_names, non_constant_identifier_names

part of easdktool.been;

/// 天气信息
class EADayWeather {
  /// 天气状况 （日间）
  EAWeatherType eDayType = EAWeatherType.clearDay;

  /// 天气状况 （夜间）
  EAWeatherType eNightType = EAWeatherType.clearDay;

  /// 最低温度
  int minTemperature = 0;

  /// 最高温度
  int maxTemperature = 0;

  /// 日出时间 (时间戳)
  int sunriseTimestamp = 0;

  /// 日落时间 (时间戳)
  int sunsetTimestamp = 0;

  /// 空气质量
  EAWeatherAirType eAir = EAWeatherAirType.excellent;

  /// 最低风力等级
  int minWindPower = 0;

  /// 最高风力等级
  int maxWindPower = 0;

  /// 紫外线强度
  EAWeatherRaysType eRays = EAWeatherRaysType.Weak;

  /// 空气湿度(1~100)%
  int airHumidity = 0;

  /// 月相
  EAWeatherMoonType eMoon = EAWeatherMoonType.FullMoon;

  /// 云量(1~100)%
  int cloudiness = 0;

  /// 空气质量分数 AQI
  int airGrade = 0;

  EADayWeather();

  Map<String, dynamic> toMap() {
    return {
      "eDayType": eDayType.index,
      "eNightType": eNightType.index,
      "eAir": eAir.index,
      "eRays": eRays.index,
      "eMoon": eMoon.index,
      "minTemperature": minTemperature,
      "maxTemperature": maxTemperature,
      "sunriseTimestamp": sunriseTimestamp,
      "sunsetTimestamp": sunsetTimestamp,
      "minWindPower": minWindPower,
      "maxWindPower": maxWindPower,
      "cloudiness": cloudiness,
      "airGrade": airGrade,
    };
  }
}

/// 天气数组
class EAWeathers {
  /// 温度单位
  EAWeatherUnit weatherUnit = EAWeatherUnit.Centigrade;

  /// 当前温度
  int currentTemperature = 0;

  /// 最多支持8天
  List<EADayWeather> days = <EADayWeather>[];

  /// 位置
  String place = "";

  EAWeathers();

  Map<String, dynamic> toMap() {
    List list = [];
    for (EADayWeather day in days) {
      Map map = day.toMap();
      list.add(map);
    }

    return {
      "eFormat": weatherUnit.index,
      "currentTemperature": currentTemperature,
      "place": place,
      "sDayArray": list
    };
  }
}
