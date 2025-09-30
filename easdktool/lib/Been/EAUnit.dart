// ignore_for_file: file_names, constant_identifier_names, non_constant_identifier_names

part of easdktool.been;

/*
 * 统一单位
 */
class EAUnifiedUnit {
  EAUnifiedUnitType unit = EAUnifiedUnitType.metric;

  EAUnifiedUnit();
  EAUnifiedUnit.fromMap(Map<String, dynamic> map) {
    if (map["eFormat"] != null) {
      unit = EAUnifiedUnitType.values[map["eFormat"]];
    }
  }

  Map<String, dynamic> toMap() {
    return {
      "unit": unit.index,
    };
  }
}

/// 距离单位
class EADistanceUint {
  EADistanceUnitType unit = EADistanceUnitType.Kilometre;

  EADistanceUint.fromMap(Map<String, dynamic> map) {
    if (map["eFormat"] != null) {
      unit = EADistanceUnitType.values[map["eFormat"]];
    }
  }

  Map<String, dynamic> toMap() {
    return {
      "unit": unit.index,
    };
  }
}

/// 重量单位
class EAWeightUnit {
  EAWeightUnitType unit = EAWeightUnitType.Kilogram;

  EAWeightUnit.fromMap(Map<String, dynamic> map) {
    if (map["eFormat"] != null) {
      unit = EAWeightUnitType.values[map["eFormat"]];
    }
  }

  Map<String, dynamic> toMap() {
    return {
      "unit": unit.index,
    };
  }
}
