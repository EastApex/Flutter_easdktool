// ignore_for_file: file_names, constant_identifier_names, non_constant_identifier_names

part of easdktool.been;

class EAMenstrualReminder {
  /// Menstrual start reminder switch, off by default
  bool menstrualBeginSw = false;

  /// Menstrual end reminder switch, off by default
  bool menstrualEndSw = false;

  /// Easy pregnancy start to remind switch, default off
  bool easyPregnancyBeginSw = false;

  /// Easy pregnancy end to remind switch, default off
  bool easyPregnancyEndSw = false;

  /// Ovulation day reminder switch, default off
  bool ovulationDaySw = false;

  /// How many days in advance to remind for menstrualBeginSw (optional 1-5 days)
  int menstrualReminderDaysBefore = 0;

  /// remind for menstrualBeginSw hour
  int menstrualReminderHours = 0;

  /// remind for menstrualBeginSw minute
  int menstrualReminderMinutes = 0;

  EAMenstrualReminder();
  EAMenstrualReminder.fromMap(Map<String, dynamic> map) {
    if (map["menstrualBeginSw"] != null) {
      menstrualBeginSw = map["menstrualBeginSw"];
    }
    if (map["menstrualEndSw"] != null) {
      menstrualEndSw = map["menstrualEndSw"];
    }
    if (map["easyPregnancyBeginSw"] != null) {
      easyPregnancyBeginSw = map["easyPregnancyBeginSw"];
    }
    if (map["easyPregnancyEndSw"] != null) {
      easyPregnancyEndSw = map["easyPregnancyEndSw"];
    }
    if (map["ovulationDaySw"] != null) {
      ovulationDaySw = map["ovulationDaySw"];
    }
    if (map["menstrualReminderDaysBefore"] != null) {
      menstrualReminderDaysBefore = map["menstrualReminderDaysBefore"];
    }
    if (map["menstrualReminderHours"] != null) {
      menstrualReminderHours = map["menstrualReminderHours"];
    }
    if (map["menstrualReminderMinutes"] != null) {
      menstrualReminderMinutes = map["menstrualReminderMinutes"];
    }
  }
  Map<String, dynamic> toMap() {
    return {
      "menstrualBeginSw": menstrualBeginSw,
      "menstrualEndSw": menstrualEndSw,
      "easyPregnancyBeginSw": easyPregnancyBeginSw,
      "easyPregnancyEndSw": easyPregnancyEndSw,
      "ovulationDaySw": ovulationDaySw,
      "menstrualReminderDaysBefore": menstrualReminderDaysBefore,
      "menstrualReminderHours": menstrualReminderHours,
      "menstrualReminderMinutes": menstrualReminderMinutes,
    };
  }
}
