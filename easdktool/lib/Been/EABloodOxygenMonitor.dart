// ignore_for_file: file_names, constant_identifier_names, non_constant_identifier_names

part of easdktool.been;

class EABloodOxygenMonitor {
  int sw = 0; // 1 on, 0 off
  int interval = 60; // minute

  EABloodOxygenMonitor(this.sw, this.interval);

  Map<String, dynamic> toMap() {
    return {"sw": sw, "interval": interval};
  }

  EABloodOxygenMonitor.fromMap(Map<String, dynamic> map) {
    if (map["sw"] != null) {
      sw = map["sw"];
    }
    if (map["interval"] != null) {
      interval = map["interval"];
    }
  }
}
