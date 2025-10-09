import 'dart:async';

//import 'dart:html';
import 'dart:io';
import 'dart:isolate';
import 'dart:ui';

import 'package:easdktool/Been/EABeen.dart';
import 'package:easdktool/EACallback.dart';
import 'package:easdktool/easdktool.dart';
import 'package:flutter/material.dart';
import 'package:flutter_foreground_task/flutter_foreground_task.dart';
import 'package:fluttertoast/fluttertoast.dart';
//import 'package:flutter_notification_listener/flutter_notification_listener.dart';

import 'FirstMethodPackageData.dart';

@pragma('vm:entry-point')
void ForegroundTaskCallback() {
  FlutterForegroundTask.setTaskHandler(MyTaskHandler());
}

class MyTaskHandler extends TaskHandler {
  EASDKTool easdkTool = new EASDKTool();

  @override
  Future<void> onDestroy(DateTime timestamp,bool isTimeout) {
    // TODO: implement onDestroy
    throw UnimplementedError();
  }

  @override
  void onRepeatEvent(DateTime timestamp) {
    // TODO: implement onRepeatEvent
  }

  @override
  Future<void> onStart(DateTime timestamp, TaskStarter starter) async {
    // TODO: implement onStart
    print("Foreground Service Started");
    initEASDK();
  }

  void initEASDK() async {
    easdkTool.showLog(1);
    EASDKTool.addBleConnectListener(ConnectListener(easdkTool));
    EASDKTool.addOperationPhoneCallback(OperationPhoneCallback((info) {
      operationPhoneListener(info);
    }));
    EASDKTool.addMotionDataCallback(EAGetBitDataCallback(((info) {
      showMotionData(info);
    })));
    easdkTool.initChannel();
    connectBluetooth();
  }

  void connectBluetooth() {
    EAConnectParam connectParam = EAConnectParam.testInit();
    easdkTool.connectToPeripheral(connectParam);
  }

  void getWatchData(int dataType) {
    easdkTool.getWatchData(
        dataType,
        EAGetDataCallback(
            onSuccess: ((info) {
              final SendPort? sendPort =
                  IsolateNameServer.lookupPortByName("_ui_get_isolate");
              print("将获取到的数据传到UI线程");
              sendPort?.send(info);
              //将数据回传到UI界面
              //int dataType = info["dataType"];
              // Map<String, dynamic> value = info["value"];
              // returnClassValue(dataType, value);
            }),
            onFail: ((info) {})));
  }

  void setWatchData(int dataType, Map map) {
    easdkTool.setWatchData(dataType, map,
        EASetDataCallback(onRespond: ((respond) {
      final SendPort? sendPort =
          IsolateNameServer.lookupPortByName("_ui_set_isolate");
      print("将设置的结果传到UI线程");
      sendPort?.send(respond);
    })));
  }

  static DateTime timestampToDate(int timestamp) {
    DateTime dateTime = DateTime.now();

    ///如果是十三位时间戳返回这个
    if (timestamp.toString().length == 13) {
      dateTime = DateTime.fromMillisecondsSinceEpoch(timestamp);
    } else if (timestamp.toString().length == 16) {
      ///如果是十六位时间戳
      dateTime = DateTime.fromMicrosecondsSinceEpoch(timestamp);
    } else if (timestamp.toString().length == 10) {
      ///如果是十位时间戳
      dateTime = DateTime.fromMillisecondsSinceEpoch(timestamp * 1000);
    }
    return dateTime;
  }

  static String timestampToDateStr(int timestamp, {onlyNeedDate = false}) {
    DateTime dataTime = timestampToDate(timestamp);
    String dateTime = dataTime.toString();

    dateTime = dateTime.substring(0, dateTime.length - 4);
    if (onlyNeedDate) {
      List<String> dataList = dateTime.split(" ");
      dateTime = dataList[0];
    }
    return dateTime;
  }

  void operationPhoneListener(Map info) {
    ///  Check whether info["opePhoneType"] belongs to EAOpePhoneType and perform the corresponding operation
    /// 【判断 info["opePhoneType"] 是属于EAOpePhoneType的哪一个，做对应的操作】
    /**
     * type   0  Find your phone
     *        1  End Phone Search
     *        2  Ready to take a picture
     *        3  photograph
     *        4  End taking pictures
     *        5  Weather      Can synchronize weather to watch
     *        6  AGPS file update      AGPS of watches can be updated through OTA, only for watches that support GPS
     *        7  female physiological cycle       The physiological cycle data of women can be synchronized to the watch
     *        8  Motion data synchronization completed
     *        9  Stop looking for a watch
     *        11 playing music information        Music information can be synchronized to the watch
     *        12 Music operation      Pause playback, increase and decrease the volume of the previous song and the next song.
     *                                After the app completes the corresponding action,it needs to synchronize the music information
     *                                to the watch to keep consistent
     *
     *
     *        0x23  hang up
     *        0x24  answer the phone
     */
    if (info.isNotEmpty) {
      int type = info["opePhoneType"];
      if (type == 0) {
      } else if (type == 1) {
      } else if (type == 2) {
      } else if (type == 3) {
      } else if (type == 4) {
      } else if (type == 5) {
      } else if (type == 6) {
      } else if (type == 7) {
      } else if (type == 8) {
      } else if (type == 9) {
      } else if (type == 11) {
      } else if (type == 12) {
        int action = info["action"];
        /**
         * action   0    Start playing
         *          1    stop playing
         *          2    Previous song
         *          3    next song
         *          4    Volume up
         *          5    Volume decrease
         */
        print(action.toString());
        String mess = "stop playing";
        if (action == 0) {
          mess = "start playing";
        } else if (action == 1) {
          mess = "stop playing";
        } else if (action == 2) {
          mess = "previous song";
        } else if (action == 3) {
          mess = "next song";
        } else if (action == 4) {
          mess = "volume up";
        } else if (action == 5) {
          mess = "Volume decrease";
        }
        Fluttertoast.showToast(
            msg: mess,
            toastLength: Toast.LENGTH_SHORT,
            gravity: ToastGravity.CENTER,
            backgroundColor: Colors.grey[800],
            textColor: Colors.cyan,
            fontSize: 16.0);
      } else if (type == 0x18) {
        if (Platform.isAndroid) {
          print("开始连接BT");
          easdkTool.pairBt("45:41:5B:16:58:4A");
        }
      }
    }
  }

  void showMotionData(Map info) {
    int dataType = info['dataType'];
    List<dynamic> list = info['value'];
    print(dataType);

    if (list.isEmpty) {
      return;
    }
    switch (dataType) {
      case kEADataInfoTypeStepData: //Daily steps【日常步数】

        for (Map<String, dynamic> item in list) {
          EABigDataStep model = EABigDataStep.fromMap(item);
          print(model.timeStamp);
          print('Daily steps date: ' + timestampToDateStr(model.timeStamp));
        }
        break;
      case kEADataInfoTypeSleepData: // sleep
        for (Map<String, dynamic> item in list) {
          EABigDataSleep model = EABigDataSleep.fromMap(item);
          print(model.timeStamp);
        }
        break;
      case kEADataInfoTypeHeartRateData: // heart rate
        for (Map<String, dynamic> item in list) {
          EABigDataHeartRate model = EABigDataHeartRate.fromMap(item);
          print(model.timeStamp);
          print('heart rate date: ' + timestampToDateStr(model.timeStamp));
        }

        break;
      case kEADataInfoTypeGPSData: // gps
        for (Map<String, dynamic> item in list) {
          EABigDataGPS model = EABigDataGPS.fromMap(item);
          print(model.timeStamp);
        }
        break;
      case kEADataInfoTypeSportsData: // sports
        for (Map<String, dynamic> item in list) {
          EABigDataSport model = EABigDataSport.fromMap(item);
          print(model.beginTimeStamp);
          print('beginDate: ' + timestampToDateStr(model.beginTimeStamp));
        }
        break;
      case kEADataInfoTypeBloodOxygenData: // Blood oxygen
        for (Map<String, dynamic> item in list) {
          EABigDataBloodOxygen model = EABigDataBloodOxygen.fromMap(item);
          print(model.timeStamp);
        }
        break;
      case kEADataInfoTypeStressData: // Stress
        for (Map<String, dynamic> item in list) {
          EABigDataStress model = EABigDataStress.fromMap(item);
          print(model.timeStamp);
        }
        break;
      case kEADataInfoTypeStepFreqData: // stride frequency
        for (Map<String, dynamic> item in list) {
          EABigDataStrideFrequency model =
              EABigDataStrideFrequency.fromMap(item);
          print(model.timeStamp);
        }
        break;
      case kEADataInfoTypeStepPaceData: // stride Pace
        for (Map<String, dynamic> item in list) {
          EABigDataStridePace model = EABigDataStridePace.fromMap(item);
          print(model.timeStamp);
        }
        break;
      case kEADataInfoTypeRestingHeartRateData: //resting heart rate
        for (Map<String, dynamic> item in list) {
          EABigDataRestingHeartRate model =
              EABigDataRestingHeartRate.fromMap(item);
          print(model.timeStamp);
        }
        break;
      case EADataInfoTypeHabitTrackerData: // habit tracker
        for (Map<String, dynamic> item in list) {
          EABigDataHabitTracker model = EABigDataHabitTracker.fromMap(item);
          print(model.timeStamp);
        }
        break;
      case EADataInfoTypeSleepScoreData:
        for (Map<String, dynamic> item in list) {
          EABigDataSleepScore model = EABigDataSleepScore.fromMap(item);
          print(model.beginTimeStamp);
        }
        break;
      case EADataInfoTypeSportHrData:
        for (Map<String, dynamic> item in list) {
          EABigDataSportHeartRate model = EABigDataSportHeartRate.fromMap(item);
          print(model.timeStamp);
        }
        break;

      default:
        break;
    }
  }
/**
    pushNotification(NotificationEvent event) {
    //This method handles pushing the notification to watch.
    EAPushMessage eapushMessage = EAPushMessage();
    eapushMessage.messageType = EAPushMessageType.whatsApp;
    eapushMessage.messageActionType = EAPushMessageActionType.add;
    eapushMessage.title = event.title.toString();

    DateTime dateTime = DateTime.now();
    eapushMessage.date = dateTime.year.toString() +
    (dateTime.month < 10
    ? "0" + dateTime.month.toString()
    : dateTime.month.toString()) +
    (dateTime.day < 10
    ? "0" + dateTime.day.toString()
    : dateTime.day.toString()) +
    "T" +
    (dateTime.hour < 10
    ? "0" + dateTime.hour.toString()
    : dateTime.hour.toString()) +
    (dateTime.minute < 10
    ? "0" + dateTime.minute.toString()
    : dateTime.minute.toString()) +
    (dateTime.second < 10
    ? "0" + dateTime.second.toString()
    : dateTime.second.toString());
    eapushMessage.content = event.message.toString();
    easdkTool.setWatchData(kEADataInfoTypePushInfo, eapushMessage.toMap(),
    EASetDataCallback(onRespond: ((respond) {
    print('---> ${eapushMessage.messageType}');
    print(respond.respondCodeType);
    })));
    }
 */
}

class ConnectListener implements EABleConnectListener {
  EASDKTool? _easdkTool;

  ConnectListener(this._easdkTool);

  @override
  void connectError() {
    print("connectError");
  }

  @override
  void connectTimeOut() {
    print("connectTimeOut");
  }

  @override
  void deviceConnected() {
    print('Device connected');
    _easdkTool?.getWatchData(
        kEADataInfoTypeWatch,
        EAGetDataCallback(
            onSuccess: ((info) async {
              Map<String, dynamic> value = info["value"];
              EABleWatchInfo eaBleWatchInfo = EABleWatchInfo.fromMap(value);

              if (eaBleWatchInfo.userId.isEmpty) {
                /**
                    1st.
                 * get watch infomation,to determine 'isWaitForBinding' the value 【连接成功后，获取手表信息，判断'isWaitForBinding'的值】
                    2nd.
                 * 1.if isWaitForBinding = 0，bindInfo.bindingCommandType need equal 1
                 * 2.if isWaitForBinding = 1，bindInfo.bindingCommandType need equal 0 ,
                    The watch displays a waiting for confirmation binding screen,
                    Wait to click OK or cancel
                 */

                EABindInfo bindInfo = EABindInfo();
                bindInfo.user_id = "1008690";
// Turn on the daily step interval for 30 minutes
                bindInfo.bindMod = 0;
                if (eaBleWatchInfo.isWaitForBinding == 0) {
//Bind command type: End【绑定命令类型：结束】
                  bindInfo.bindingCommandType = 1;
                } else {
//Bind command type: Begin【绑定命令类型：开始】
                  bindInfo.bindingCommandType = 0;
                }

                _easdkTool?.bindingWatch(bindInfo,
                    EABindingWatchCallback(onRespond: ((respond) {
                  print(respond.respondCodeType);
                })));
              }
            }),
            onFail: ((info) {})));
  }

  @override
  void deviceDisconnect() {
    print("deviceDisconnect");
  }

  @override
  void deviceNotFind() {
    print("deviceNotFind");
  }

  @override
  void notOpenLocation() {
    print("notOpenLocation");
  }

  @override
  void paramError() {
    print("paramError");
  }

  @override
  void unopenedBluetooth() {
    print("unopenedBluetooth");
  }

  @override
  void unsupportedBLE() {
    print("unsupportedBLE");
  }

  @override
  void iOSRelievePair() {
    print("iOSRelievePair");
  }

  @override
  void iOSUnAuthorized() {
    print("iOSUnAuthorized");
  }
}
