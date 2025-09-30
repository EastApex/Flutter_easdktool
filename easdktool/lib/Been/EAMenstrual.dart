// ignore_for_file: file_names, constant_identifier_names, non_constant_identifier_names

part of easdktool.been;

/// 生理周期信息
class EAMenstrual {
  /// 持续天数,范围 5~10
  int keepDay = 5;

  /// 周期,范围 20~45
  int cycleDay = 28;

  /// 月经开始日期(必须为 yyyy-MM-dd)
  String startDate = "2022-01-01";

  EAMenstrual(this.startDate, this.keepDay, this.cycleDay);

  Map toMap() {
    return {
      "keepDay": keepDay,
      "cycleDay": cycleDay,
      "startDate": startDate,
    };
  }
}
