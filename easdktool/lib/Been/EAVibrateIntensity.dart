// ignore_for_file: file_names, constant_identifier_names, non_constant_identifier_names

part of easdktool.been;

class EAVibrateIntensity {
  EAVibrateIntensityType eVibrateIntensity = EAVibrateIntensityType.Medium;

  EAVibrateIntensity(this.eVibrateIntensity);

  Map<String, dynamic> toMap() {
    return {"eVibrateIntensity": eVibrateIntensity.index};
  }

  EAVibrateIntensity.fromMap(Map<String, dynamic> map) {
    if (map["eVibrateIntensity"] != null) {
      eVibrateIntensity =
          EAVibrateIntensityType.values[map["eVibrateIntensity"]];
    }
  }
}
