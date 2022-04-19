// ignore_for_file: file_names, constant_identifier_names, non_constant_identifier_names

part of easdktool.been;

class EASyncTime {
  // 年
  int year = 2022;
// 月
  int month = 1;
// 日
  int day = 1;
// 时
  int hour = 0;
// 分
  int minute = 0;
// 秒
  int second = 0;
// 小时制
  EATimeHourType timeHourType = EATimeHourType.hour12;
// 当前时区：0时区、东时区、西时区
  EATimeZone timeZone = EATimeZone.zero;
// 当前时区:时
  int timeZoneHour = 0;
// 当前时区:分
  int timeZoneMinute = 0;

  Map<String, dynamic> toMap() {
    return {
      "year": year,
      "month": month,
      "day": day,
      "hour": hour,
      "minute": minute,
      "second": second,
      "timeZoneHour": timeZoneHour,
      "timeZoneMinute": timeZoneMinute,
      "timeHourType": timeHourType.index,
      "timeZone": timeZone.index,
    };
  }
}
