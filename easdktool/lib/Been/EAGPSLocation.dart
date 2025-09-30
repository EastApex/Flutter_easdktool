// ignore_for_file: file_names, constant_identifier_names, non_constant_identifier_names

part of easdktool.been;

class EAGPSLocation {
  
  double latitude = 0.0;
  double longitude = 0.0;

  EAGPSLocation();
  EAGPSLocation.fromMap(Map<String, dynamic> map) {
    if (map["latitude"] != null) {
      latitude = map["latitude"];
    }
    if (map["longitude"] != null) {
      latitude = map["longitude"];
    }
  }
  Map<String, dynamic> toMap() {
    return {
      "longitude": longitude,
      "latitude": latitude,
    };
  }
}

