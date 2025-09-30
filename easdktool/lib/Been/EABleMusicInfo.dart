part of easdktool.been;

class EABleMusicInfo {
  int volume = 0;
  String? content;
  int duration = 0;
  int elapsedtime = 0;
  String? artist;
  int playState = 0;

  Map<String, dynamic> toMap() {
    return {
      "volume": volume,
      "content": content,
      "duration": duration,
      "elapsedtime": elapsedtime,
      "artist": artist,
      "playState": playState
    };
  }
}
