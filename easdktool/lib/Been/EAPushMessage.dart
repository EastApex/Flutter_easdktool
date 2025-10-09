part of easdktool.been;

class EAPushMessage {
  int messageId = 0;
  EAPushMessageActionType messageActionType = EAPushMessageActionType.add;
  EAPushMessageType messageType = EAPushMessageType.unknow;
  String title = "";
  String content = "";
  String date = "";

  EAPushMessage();

  Map toMap() {
    return {
      "messageId": messageId,
      "messageActionType": messageActionType.index,
      "messageType": messageType.index,
      "title": title,
      "content": content,
      "date": date,
    };
  }
}
