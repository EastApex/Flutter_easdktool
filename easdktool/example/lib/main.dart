// ignore_for_file: avoid_print

// import 'dart:typed_data';

import 'dart:async';
import 'dart:ffi';
import 'dart:io';
import 'dart:isolate';
import 'dart:math';
import 'dart:ui';

import 'package:device_info_plus/device_info_plus.dart';
import 'package:easdktool/easdktool.dart';
import 'package:flutter/material.dart';
import 'package:easdktool/EACallback.dart';
import 'package:easdktool/Been/EABeen.dart';
import 'package:flutter/services.dart';
import 'package:flutter_foreground_task/flutter_foreground_task.dart';
import 'package:path_provider/path_provider.dart';
import 'package:permission_handler/permission_handler.dart';
import 'package:platform/platform.dart';
import 'FirstMethodPackageData.dart';
import 'ForegroundTaskHandler.dart';

//import 'package:flutter_blue_plus/flutter_blue_plus.dart';
// import 'package:dio/dio.dart';
// import 'dart:io';

// import 'package:flutter_ble_lib/flutter_ble_lib.dart';

void main() {
  runApp(const MyApp());
}

String connectState = "Unknown";

class MyApp extends StatefulWidget {
  const MyApp({Key? key}) : super(key: key);

  @override
  State<MyApp> createState() => _MyAppState();
}

class ConnectListener implements EABleConnectListener {
  EASDKTool easdkTool;

  ConnectListener(this.easdkTool);

  @override
  void connectError() {
    print('XWatch Package: connection Listener - connect Error');
  }

  @override
  void connectTimeOut() {
    print('XWatch Package: connection Listener - connect Timeout');
  }

  @override
  void deviceConnected() {
    /// 绑定手表
    print('XWatch Package: connection Listener - connected');
    easdkTool.getWatchData(
        kEADataInfoTypeWatch,
        EAGetDataCallback(onSuccess: ((info) async {
          Map<String, dynamic> value = info["value"];
          EABleWatchInfo eaBleWatchInfo = EABleWatchInfo.fromMap(value);
          print('info $value');

          /** 2nd.
           * 1.if isWaitForBinding = 0，bindInfo.bindingCommandType need equal 1
           * 2.if isWaitForBinding = 1，bindInfo.bindingCommandType need equal 0 ,
              The watch displays a waiting for confirmation binding screen,
              Wait to click OK or cancel
           */
          // if (eaBleWatchInfo.userId.isEmpty) {
          if (eaBleWatchInfo.bindingType == EABindingType.unbound) {
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
            bindInfo.bindingCommandType = 1;
            print("bindingcommandtype ${bindInfo.bindingCommandType}");

            easdkTool.bindingWatch(bindInfo,
                EABindingWatchCallback(onRespond: ((respond) {
              print('binding response  ${respond.respondCodeType}');
            })));
          } else {
            // XWatch.xWatchConnectionListener?.deviceConnected();
            // easdkTool.disConnectWatch();
          }
        }), onFail: ((info) {
          // XWatch.xWatchConnectionListener?.deviceDisconnected();
        })));
  }

  @override
  void deviceDisconnect() {
    print('XWatch Package: connection Listener - device disconnect');
    // XWatch.xWatchConnectionListener?.deviceDisconnected();
  }

  @override
  void deviceNotFind() {
    print('XWatch Package: connection Listener - device not find');
    // XWatch.xWatchConnectionListener?.deviceNotFound();
  }

  @override
  void notOpenLocation() {
    print('XWatch Package: connection Listener - not open location');
  }

  @override
  void paramError() {
    print('XWatch Package: connection Listener - Param error');
    // XWatch.xWatchConnectionListener?.deviceConnectionError();
  }

  @override
  void unopenedBluetooth() {
    print('XWatch Package: connection Listener - unopened Bluetooth');
  }

  @override
  void unsupportedBLE() {
    print('XWatch Package: connection Listener - unsupported BLE');
  }

  @override
  void iOSRelievePair() {
    print('XWatch Package: connection Listener - iOS Relieve Pair');
  }

  @override
  void iOSUnAuthorized() {
    print('XWatch Package: connection Listener - iOS Unauthorized');
  }
}

class _MyAppState extends State<MyApp> {
  T? _ambiguate<T>(T? value) => value;

  // The second method is initialization
  EASDKTool secondEasdkTool = EASDKTool();
  ReceivePort getReceivePort = ReceivePort();
  ReceivePort setReceivePort = ReceivePort();
  EAGetDataCallback? eaGetDataCallback;
  EASetDataCallback? eaSetDataCallback;

  @override
  void initState() {
    super.initState();

    if (LocalPlatform().isAndroid) {
      checkLocationPermission();

      //The second method is to initialize the channel

    } else {
      secondEasdkTool.showLog(1);
      secondEasdkTool.initChannel();

      /// 【添加监听】
      EASDKTool.addBleConnectListener(ConnectListener(secondEasdkTool));
      EASDKTool.addOperationPhoneCallback(OperationPhoneCallback((info) {
        operationPhoneListener(info);
      }));

      EAConnectParam connectParam = EAConnectParam.testInit();
      EASDKTool().connectToPeripheral(connectParam);
    }

    /// 打开 SDKLog
    EASDKTool().showLog(1);

    EASDKTool().showTest(1);

    ///搜索手表
    EASDKTool().scanWatch(EAScanWatchCallback((connectParam) {
      // print(connectParam.name + "🍀🍀🍀" + connectParam.snNumber);
      // print("");
      // print(connectParam.uuid);
      // print("");
    }));
  }

  void operationPhoneListener(Map info) {
    ///  Check whether info["opePhoneType"] belongs to EAOpePhoneType and perform the corresponding operation
    /// 【判断 info["opePhoneType"] 是属于EAOpePhoneType的哪一个，做对应的操作】
    print("🍀🍀🍀");
    print(info);
  }

  void initGetIsolate() {
    IsolateNameServer.removePortNameMapping("_ui_get_isolate");
    bool success = IsolateNameServer.registerPortWithName(
        getReceivePort.sendPort, "_ui_get_isolate");
    getReceivePort.listen((message) {
      eaGetDataCallback?.onSuccess(message);
    });
  }

  void initSetIsolate() {
    IsolateNameServer.removePortNameMapping("_ui_set_isolate");
    bool success = IsolateNameServer.registerPortWithName(
        setReceivePort.sendPort, "_ui_set_isolate");
    setReceivePort.listen((message) {
      eaSetDataCallback?.onRespond(message);
    });
  }

  foregroundTask() {
    _ambiguate(WidgetsBinding.instance)?.addPostFrameCallback((_) async {
      // You can get the previous ReceivePort without restarting the service.
      if (await FlutterForegroundTask.isRunningService) {
        final newReceivePort = await FlutterForegroundTask.receivePort;
        registerReceivePort(newReceivePort);
      } else {
        initForegroundTask();
        startForegroundTask();
      }
    });
  }

  bool registerReceivePort(ReceivePort? receivePort) {
    closeReceivePort(receivePort);

    if (receivePort != null) {
      receivePort = receivePort;
      receivePort.listen((message) {
        print(message);
      });

      return true;
    }
    return false;
  }

  void closeReceivePort(ReceivePort? receivePort) {
    receivePort?.close();
    receivePort = null;
  }

  Future<void> initForegroundTask() async {
    FlutterForegroundTask.init(
      androidNotificationOptions: AndroidNotificationOptions(
        channelId: 'notification_channel_id',
        channelName: 'Foreground Notification',
        channelDescription:
            'This notification appears when the foreground service is running.',
        channelImportance: NotificationChannelImportance.LOW,
        priority: NotificationPriority.LOW,
        iconData: const NotificationIconData(
          resType: ResourceType.mipmap,
          resPrefix: ResourcePrefix.ic,
          name: 'launcher',
          // backgroundColor: Colors.orange,
        ),
      ),
      iosNotificationOptions: const IOSNotificationOptions(
        showNotification: true,
        playSound: false,
      ),
      foregroundTaskOptions: const ForegroundTaskOptions(
        interval: 5000,
        autoRunOnBoot: true,
        allowWifiLock: true,
      ),
      //printDevLog: true,
    );
  }

  Future<bool> startForegroundTask() async {
    ReceivePort? receivePort;
    bool reqResult;
    if (await FlutterForegroundTask.isRunningService) {
      reqResult = await FlutterForegroundTask.restartService();
    } else {
      reqResult = await FlutterForegroundTask.startService(
        notificationTitle: '',
        notificationText: '',
        callback: ForegroundTaskCallback,
      );
    }

    if (reqResult) {
      receivePort = await FlutterForegroundTask.receivePort;
    }

    return registerReceivePort(receivePort);
  }

  void secondMethodSetWatchData(int dataType, Map map) {
    secondEasdkTool.setWatchData(dataType, map,
        EASetDataCallback(onRespond: ((respond) {
      print(respond.respondCodeType.toString() +
          '设置的数据类型:' +
          respond.dataType.toString());
    })));
  }

  void getBigWatchData() {
    if (LocalPlatform().isAndroid) {
      firstMethodSetWatchData(29, Map(),
          EASetDataCallback(onRespond: (onRespond) {
        print("get Big data,The first method is to get the callback" +
            onRespond.respondCodeType.toString());
      }), 4);
    } else {
      secondEasdkTool.getBigWatchData(EAGetBitDataCallback(((info) {
        /// Determine what kind of big data "dataType" is
        ///【判断dataType是属于那种大数据】

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

          default:
            break;
        }
      })));
    }
  }

  /// Timestamp to date 【时间戳转日期】
  /// [timestamp] 时间戳
  /// [onlyNeedDate]Whether to display only the date but not the time【是否只显示日期 舍去时间】
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

  void returnClassValue(int dataType, Map<String, dynamic> value) {
    print(dataType);
    print(value);
    switch (dataType) {
      case kEADataInfoTypeWatch:
        {
          EABleWatchInfo eaBleWatchInfo = EABleWatchInfo.fromMap(value);
          print(eaBleWatchInfo.watchType);
        }
        break;
      case kEADataInfoTypeUser:
        {
          EAPersonInfo eaPersonInfo = EAPersonInfo.fromMap(value);
          print(eaPersonInfo.age);
        }
        break;
      case kEADataInfoTypeBlacklight:
        {
          EAScreenBrightness eaScreenBrightness =
              EAScreenBrightness.fromMap(value);
          print(eaScreenBrightness.level);
        }
        break;
      case kEADataInfoTypeBlacklightTimeout:
        {
          EAScreenTimeout eaScreenBrightness = EAScreenTimeout.fromMap(value);
          print(eaScreenBrightness.timeout);
        }
        break;
      case kEADataInfoTypeGesturesSetting:
        {
          EAScreenGesturesSetting eaScreenBrightness =
              EAScreenGesturesSetting.fromMap(value);
          print(eaScreenBrightness.beginHour);
        }
        break;

      case kEADataInfoTypeBattery:
        {
          EABatInfo eaBatInfo = EABatInfo.fromMap(value);
          print(eaBatInfo.level);
        }
        break;
      case kEADataInfoTypeLanguage:
        {
          EALanguage eaLanguage = EALanguage.fromMap(value);
          print(eaLanguage.type);
        }
        break;
      case kEADataInfoTypeUnifiedUnit:
        {
          EAUnifiedUnit eaLanguage = EAUnifiedUnit.fromMap(value);
          print(eaLanguage.unit);
        }
        break;
      case kEADataInfoTypeDistanceUnit:
        {
          EADistanceUint eaDistanceUint = EADistanceUint.fromMap(value);
          print(eaDistanceUint.unit);
        }
        break;
      case kEADataInfoTypeWeightUnit:
        {
          EAWeightUnit eaWeightUnit = EAWeightUnit.fromMap(value);
          print(eaWeightUnit.unit);
        }
        break;
      case kEADataInfoTypeNotDisturb:
        {
          EANotDisturb eaNotDisturbModel = EANotDisturb.fromMap(value);
          print(eaNotDisturbModel.sw);
        }
        break;
      case kEADataInfoTypeDailyGoal:
        {
          EADailyGoals eaDailyGoalModel = EADailyGoals.fromMap(value);
          print(eaDailyGoalModel.sStep.goal);
        }
        break;
      case kEADataInfoTypeAutoCheckSleep:
        {
          EAAutoCheckSleep eaAutoCheckSleep = EAAutoCheckSleep.fromMap(value);
          print(eaAutoCheckSleep.weekCycleBit);
        }
        break;
      case kEADataInfoTypeAutoCheckHeartRate:
        {
          EAAutoCheckHeartRate eaAutoCheckHeartRate =
              EAAutoCheckHeartRate.fromMap(value);
          print(eaAutoCheckHeartRate.interval);
        }
        break;
      case kEADataInfoTypeAutoCheckSedentariness:
        {
          EAAutoCheckSedentariness eaAutoCheckSedentariness =
              EAAutoCheckSedentariness.fromMap(value);
          print(eaAutoCheckSedentariness.stepThreshold);
        }
        break;
      case kEADataInfoTypeSocialSwitch:
        {
          EASocialSwitch eaSocialSwitch = EASocialSwitch.fromMap(value);
          print(eaSocialSwitch.sIncomingcall.remindActionType);
        }
        break;
      case kEADataInfoTypeReminder:
        {
          EAReminderOps eaReminder = EAReminderOps.fromMap(value);
          print(eaReminder.id_p);
        }
        break;
      case kEADataInfoTypeHeartRateWaringSetting:
        {
          EAHeartRateWaringSetting eaHeartRateWaringSetting =
              EAHeartRateWaringSetting.fromMap(value);
          print(eaHeartRateWaringSetting.maxHr);
        }
        break;
      case kEADataInfoTypeCaloriesSetting:
        {
          EACaloriesSetting eaCaloriesSetting =
              EACaloriesSetting.fromMap(value);
          print(eaCaloriesSetting.sw);
        }
        break;
      case kEADataInfoTypeWatchSettingInfo:
        {
          EAWatchSettingInfo eaWatchSettingInfo =
              EAWatchSettingInfo.fromMap(value);
          print(eaWatchSettingInfo.wfId);
        }
        break;
      case kEADataInfoTypeHomePage:
        {
          EAHomePages homePages = EAHomePages.fromMap(value);
          print(homePages.list);
        }
        break;
      case kEADataInfoTypeHabitTracker:
        {
          EAHabitTrackers habitTrackers = EAHabitTrackers.formMap(value);
          print(habitTrackers.list);
        }
        break;
      case kEADataInfoTypeSportShowData:
        {
          EASportShowData sportShowData = EASportShowData.fromMap(value);
          print(sportShowData.calorie);
        }
        break;
      case kEADataInfoTypeBlePairState:
        {
          EAWatchPairStateModel sportShowData =
              EAWatchPairStateModel.fromMap(value);
          print(sportShowData.secState);
        }
        break;
      case kEADataInfoTypeSyncTime:
        {
          EASyncTime syncTime = EASyncTime.fromMap(value);
          print(syncTime.day);
        }
        break;
      case kEADataInfoTypeAppMessage:
        {
          print(value);

          EAShowAppMessage showAppMessage = EAShowAppMessage.fromMap(value);
          print(showAppMessage);
        }
        break;
      case kEADataInfoTypeMonitorReminder:
        {
          print(value);

          EAMonitorReminder monitorReminder = EAMonitorReminder.fromMap(value);
          print(monitorReminder);
        }
        break;
      case kEADataInfoTypeReadTelephoneBook:
        {
          EAReadTelephoneBook readTelephoneBook =
              EAReadTelephoneBook.formMap(value);
          print(readTelephoneBook);
        }
        break;
      case kEADataInfoTypeBloodOxygenMonitor:
        {
          EABloodOxygenMonitor bloodOxygenMonitor =
              EABloodOxygenMonitor.fromMap(value);
          print(bloodOxygenMonitor.interval);
        }
        break;
      case kEADataInfoTypeStressMonitor:
        {
          EAStressMonitor stressMonitor = EAStressMonitor.fromMap(value);
          print(stressMonitor);
        }
        break;
      case kEADataInfoTypeVibrateIntensity:
        {
          EAVibrateIntensity vibrateIntensity =
              EAVibrateIntensity.fromMap(value);
          print(vibrateIntensity);
        }
        break;
      case kEADataInfoTypeMenstrualReminder:
        {
          EAMenstrualReminder menstrualReminder =
              EAMenstrualReminder.fromMap(value);
          print(menstrualReminder);
        }
        break;
      // case kEADataInfoTypeSportHrWarning:
      //   {
      //     EASportHrWarning sportHrWarning = EASportHrWarning.fromMap(value);
      //     print(sportHrWarning);
      //   }
      //   break;
      default:
        break;
    }
  }

  // ignore: non_constant_identifier_names
  Widget TextView(String text) {
    return Container(
      child: Text(text,
          style: const TextStyle(fontSize: 15, fontWeight: FontWeight.w500)),
      height: 60,
      color: Colors.white,
    );
  }

  // ignore: non_constant_identifier_names
  Widget NewTextView(String text) {
    return Container(
      child: Text(text,
          style: const TextStyle(
              fontSize: 15,
              fontWeight: FontWeight.w500,
              color: Color.fromARGB(255, 248, 113, 113))),
      height: 60,
      color: Colors.white,
    );
  }

// ignore: non_constant_identifier_names
  Widget TitleView(String title) {
    return Container(
      child: Text(title,
          style: const TextStyle(fontSize: 20, fontWeight: FontWeight.bold)),
      height: 30,
      color: Colors.grey.shade300,
    );
  }

  void getWatchData(int dataType) {
    EASDKTool().getWatchData(
        dataType,
        EAGetDataCallback(
            onSuccess: ((info) {
              int dataType = info["dataType"];
              Map<String, dynamic> value = info["value"];
              returnClassValue(dataType, value);
            }),
            onFail: ((info) {})));
  }

  void setWatchData(int dataType, Map map) {
    EASDKTool().setWatchData(dataType, map,
        EASetDataCallback(onRespond: ((respond) {
      print(respond.respondCodeType.toString() +
          "设置的数据类型:" +
          respond.dataType.toString());
    })));
  }

  void firstMethodGetWatchData(
      int dataType, EAGetDataCallback getetDataCallback) {
    if (LocalPlatform().isAndroid) {
      eaGetDataCallback = getetDataCallback;
      PackageData packageData = PackageData();
      packageData.action = 1;
      packageData.param = dataType;
      final SendPort? send =
          IsolateNameServer.lookupPortByName("_notifications_");
      send?.send(packageData);
    } else {
      getWatchData(dataType);
    }
  }

  void firstMethodSetWatchData(
      int dataType, Map map, EASetDataCallback setDataCallback, int action) {
    if (LocalPlatform().isAndroid) {
      eaSetDataCallback = setDataCallback;
      PackageData packageData = PackageData();
      packageData.action = action;
      packageData.dataType = dataType;
      packageData.param = map;
      final SendPort? send =
          IsolateNameServer.lookupPortByName("_notifications_");
      send?.send(packageData);
    } else {
      setWatchData(dataType, map);
    }
  }

  void secondMethodGetWatchData(int dataType) {
    secondEasdkTool.getWatchData(
        dataType,
        EAGetDataCallback(
            onSuccess: ((info) {
              int dataType = info["dataType"];
              Map<String, dynamic> value = info["value"];
              returnClassValue(dataType, value);
            }),
            onFail: ((info) {})));
  }

  void secondMethodGetWatchData2(int dataType, int type) {
    secondEasdkTool.getWatchData2(
        dataType,
        type,
        EAGetDataCallback(
            onSuccess: ((info) {
              int dataType = info["dataType"];
              Map<String, dynamic> value = info["value"];
              returnClassValue(dataType, value);
            }),
            onFail: ((info) {})));
  }

  void getWacthStateInfo() async {
    EAConnectStateInfo connectStateInfo = await EASDKTool().getWacthStateInfo();
    print(connectStateInfo.connectState.index);
  }

  @override
  Widget build(BuildContext context) {
    //初始化通知
    return MaterialApp(
      debugShowCheckedModeBanner: false, // 加入这行代码,即可关闭'DEBUG'字样
      home: Scaffold(
        appBar: AppBar(
          title: Text("Flutter Demo For EASDK"),
        ),
        body: Center(
          child: ListView(
            children: [
              TitleView('  Access to information【获取信息】'),
              GestureDetector(
                child: TextView('1.Obtaining watch Information【获取手表信息】'),
                onTap: () {
                  firstMethodGetWatchData(
                      kEADataInfoTypeWatch,
                      EAGetDataCallback(
                          onSuccess: (onSuccess) {
                            int dataType = onSuccess["dataType"];
                            Map<String, dynamic> value = onSuccess["value"];
                            print("The first method is to get the callback");
                            returnClassValue(dataType, value);
                          },
                          onFail: (onFail) {}));
                },
              ),
              GestureDetector(
                child: TextView('2.Obtaining User information【获取用户信息】'),
                onTap: () {
                  secondMethodGetWatchData(kEADataInfoTypeUser);
                },
              ),
              GestureDetector(
                child: TextView('3.Get watch screen brightness【获取手表屏幕亮度】'),
                onTap: () {
                  secondMethodGetWatchData(kEADataInfoTypeBlacklight);
                },
              ),
              GestureDetector(
                child: TextView('4.Obtain the battery【获取手表电量信息】'),
                onTap: () {
                  secondMethodGetWatchData(kEADataInfoTypeBattery);
                },
              ),
              GestureDetector(
                child: TextView('5.Obtain the device language【设备语言信息】'),
                onTap: () {
                  secondMethodGetWatchData(kEADataInfoTypeLanguage);
                },
              ),
              GestureDetector(
                child: TextView('6.Obtain the device unit system【设备单位制度】'),
                onTap: () {
                  secondMethodGetWatchData(kEADataInfoTypeUnifiedUnit);
                },
              ),
              GestureDetector(
                child: TextView('7.Obtain the DND period【获取手表免打扰时间段】'),
                onTap: () {
                  secondMethodGetWatchData(kEADataInfoTypeNotDisturb);
                },
              ),
              GestureDetector(
                child: TextView('8.Obtain the daily target value【获取手表日常目标值】'),
                onTap: () {
                  secondMethodGetWatchData(kEADataInfoTypeDailyGoal);
                },
              ),
              GestureDetector(
                child: TextView(
                    '9.Obtain the automatic sleep monitoring【获取手表自动睡眠监测】'),
                onTap: () {
                  secondMethodGetWatchData(kEADataInfoTypeAutoCheckSleep);
                },
              ),
              GestureDetector(
                child: TextView(
                    '10.Get watch automatic heart rate monitoring【获取手表自动心率监测】'),
                onTap: () {
                  secondMethodGetWatchData(kEADataInfoTypeAutoCheckHeartRate);
                },
              ),
              GestureDetector(
                child: TextView('11.Get watch sedentary monitoring【获取手表久坐监测】'),
                onTap: () {
                  secondMethodGetWatchData(
                      kEADataInfoTypeAutoCheckSedentariness);
                },
              ),
              GestureDetector(
                child: TextView(
                    '12.Get watch Social alert switch(SMS、PhoneCall、Email)【社交提醒开关】'),
                onTap: () {
                  secondMethodGetWatchData(kEADataInfoTypeSocialSwitch);
                },
              ),
              GestureDetector(
                child: TextView('13.Get watch alerts【获取手表提醒】'),
                onTap: () {
                  secondMethodGetWatchData(kEADataInfoTypeReminder);
                },
              ),
              GestureDetector(
                child:
                    TextView('14.Get heart rate alarm threshold【获取手表心率报警门限】'),
                onTap: () {
                  secondMethodGetWatchData(
                      kEADataInfoTypeHeartRateWaringSetting);
                },
              ),
              GestureDetector(
                child: TextView('15.Get distance unit【获取距离单位】'),
                onTap: () {
                  secondMethodGetWatchData(kEADataInfoTypeDistanceUnit);
                },
              ),
              GestureDetector(
                child: TextView('16.Get weight Unit【获取重量单位】'),
                onTap: () {
                  secondMethodGetWatchData(kEADataInfoTypeWeightUnit);
                },
              ),
              GestureDetector(
                child: TextView('17.Get heart rate waring【心率报警门限】'),
                onTap: () {
                  secondMethodGetWatchData(
                      kEADataInfoTypeHeartRateWaringSetting);
                },
              ),
              GestureDetector(
                child: TextView('18.Get calories open state【卡路里开关】'),
                onTap: () {
                  secondMethodGetWatchData(kEADataInfoTypeCaloriesSetting);
                },
              ),
              GestureDetector(
                child: TextView('19.Get gestures open state【抬手亮屏开关】'),
                onTap: () {
                  secondMethodGetWatchData(kEADataInfoTypeGesturesSetting);
                },
              ),
              GestureDetector(
                child: TextView('20.Get general information【获取手表通用信息】'),
                onTap: () {
                  secondMethodGetWatchData(kEADataInfoTypeWatchSettingInfo);
                },
              ),
              GestureDetector(
                child: TextView('21.Get the first-level menu【获取手表一级菜单】'),
                onTap: () {
                  secondMethodGetWatchData(kEADataInfoTypeHomePage);
                },
              ),
              GestureDetector(
                child: TextView('22.Interest rates screen time【息屏时间】'),
                onTap: () {
                  secondMethodGetWatchData(kEADataInfoTypeBlacklightTimeout);
                },
              ),
              GestureDetector(
                child: TextView(
                    '23.Obtain the connection status of the watch【获取手表连接状态】'),
                onTap: () {
                  getWacthStateInfo();
                },
              ),
              GestureDetector(
                child:
                    TextView('24.Obtain the Habit Tracker of the watch【获取习惯】'),
                onTap: () {
                  secondMethodGetWatchData(kEADataInfoTypeHabitTracker);
                },
              ),
              GestureDetector(
                child: TextView('25.Obtain sport show data【获取运动显示值】'),
                onTap: () {
                  secondMethodGetWatchData(kEADataInfoTypeSportShowData);
                },
              ),
              GestureDetector(
                child: TextView('26.get paired watche state【获取手表配对状态】'),
                onTap: () {
                  secondMethodGetWatchData(kEADataInfoTypeBlePairState);
                },
              ),
              GestureDetector(
                child: TextView('27.Obtain watch time【获取手表时间】'),
                onTap: () {
                  secondMethodGetWatchData(kEADataInfoTypeSyncTime);
                },
              ),
              GestureDetector(
                child: TextView('28.Obtain App notifications 【获取App消息推送】'),
                onTap: () {
                  secondMethodGetWatchData(kEADataInfoTypeAppMessage);
                },
              ),
              GestureDetector(
                child: TextView('29.read monitor reminder event 【提醒事件监测（读取）】'),
                onTap: () {
                  secondMethodGetWatchData2(kEADataInfoTypeMonitorReminder,
                      EAMonitorReminderType.drink.index);
                },
              ),
              GestureDetector(
                child: TextView('30.Save motion data to database【保存运动数据到数据库】'),
                onTap: () {
                  //1 为保存,0为步保存
                  secondEasdkTool.saveData2DB(1);
                },
              ),
              GestureDetector(
                child: TextView('31.Query saved data【查询保存的运动数据】'),
                onTap: () {
                  //1 为保存,0为步保存
                  secondEasdkTool.queryMotionData(QueryType.heart_data,
                      QueryMotionDataCallback(((info) {
                    showMotionData(info);
                  })));
                },
              ),
              GestureDetector(
                child: TextView('32.Delete saved data【删除保存的数据】'),
                onTap: () {
                  secondEasdkTool.deleteSaveData(QueryType.multi_data);
                },
              ),
              GestureDetector(
                child: NewTextView('33.Get Watch Address Book 【获取手表通讯录】'),
                onTap: () {
                  secondMethodGetWatchData(kEADataInfoTypeReadTelephoneBook);
                },
              ),
              GestureDetector(
                child: NewTextView('34.Blood oxygen monitoring data【血氧监测数据】'),
                onTap: () {
                  secondMethodGetWatchData(kEADataInfoTypeBloodOxygenMonitor);
                },
              ),
              GestureDetector(
                child: NewTextView('35.Stress monitoring data【压力监测数据】'),
                onTap: () {
                  secondMethodGetWatchData(kEADataInfoTypeStressMonitor);
                },
              ),
              GestureDetector(
                child: NewTextView('36.VibrateIntensity【震动】'),
                onTap: () {
                  secondMethodGetWatchData(kEADataInfoTypeVibrateIntensity);
                },
              ),
              GestureDetector(
                child: NewTextView('37.Menstrual Reminder【经期提醒】'),
                onTap: () {
                  secondMethodGetWatchData(kEADataInfoTypeMenstrualReminder);
                },
              ),
              // GestureDetector(
              //   child: NewTextView('38.Sport Hr Warning【运动心率提醒】'),
              //   onTap: () {
              //     secondMethodGetWatchData(kEADataInfoTypeSportHrWarning);
              //   },
              // ),
              TitleView('  Setting【设置信息】'),
              GestureDetector(
                child: TextView('1.Set up information【设置用户信息】'),
                onTap: () {
                  /// 初始化对象，并赋值
                  EAPersonInfo personInfo = EAPersonInfo();
                  personInfo.age = 27;
                  personInfo.weight = 75 * 1000;
                  personInfo.height = 172;
                  personInfo.skinColor = EASkinColor.skinYellow;
                  personInfo.sex = EAPersonSex.female;
                  firstMethodSetWatchData(
                      kEADataInfoTypeUser, personInfo.toMap(),
                      EASetDataCallback(onRespond: (onRespond) {
                    print("set data,The first method is to get the callback" +
                        onRespond.respondCodeType.toString());
                  }), 2);
                },
              ),
              GestureDetector(
                child: TextView('2.Set the watch time【设置手表时间】'),
                // 同步手机时间到手表
                onTap: () {
                  EASyncTime syncTime = EASyncTime();
                  syncTime.day = 2;
                  syncTime.month = 12;
                  syncTime.year = 2022;
                  syncTime.hour = 10;
                  syncTime.minute = 45;
                  syncTime.second = 0;
                  syncTime.timeHourType = EATimeHourType.hour24;
                  syncTime.timeZone = EATimeZone.east;
                  syncTime.timeZoneHour = 8;
                  syncTime.timeZoneMinute = 0;
                  secondMethodSetWatchData(
                      kEADataInfoTypeSyncTime, syncTime.toMap());
                },
              ),
              GestureDetector(
                child: TextView('3.Setting the Watch Language【设置手表语言】'),
                onTap: () {
                  EALanguage language = EALanguage();
                  language.type = EALanguageType.francais;
                  secondMethodSetWatchData(
                      kEADataInfoTypeLanguage, language.toMap());
                },
              ),
              GestureDetector(
                child: TextView('4.Set the watch unit-metric【设置手表单位-KM】'),
                onTap: () {
                  EAUnifiedUnit unifiedUnit = EAUnifiedUnit();
                  unifiedUnit.unit = EAUnifiedUnitType.metric;
                  secondMethodSetWatchData(
                      kEADataInfoTypeUnifiedUnit, unifiedUnit.toMap());
                },
              ),
              GestureDetector(
                child: TextView('4.Set the watch unit-british【设置手表单位-MI】'),
                onTap: () {
                  EAUnifiedUnit unifiedUnit = EAUnifiedUnit();
                  unifiedUnit.unit = EAUnifiedUnitType.british;
                  secondMethodSetWatchData(
                      kEADataInfoTypeUnifiedUnit, unifiedUnit.toMap());
                },
              ),
              GestureDetector(
                child: TextView('5.Set the DND period【设置免打扰时间段】'),
                onTap: () {
                  EANotDisturb notDisturb = EANotDisturb();
                  notDisturb.sw = 1;
                  notDisturb.beginHour = 15;
                  notDisturb.beginMinute = 0;
                  notDisturb.endHour = 16;
                  notDisturb.endMinute = 0;
                  secondMethodSetWatchData(
                      kEADataInfoTypeNotDisturb, notDisturb.toMap());
                },
              ),
              GestureDetector(
                child: TextView('6.Set daily target values【设置日常目标值】'),
                onTap: () {
                  EADailyGoals dailyGoals = EADailyGoals();
                  dailyGoals.sCalorie = EADailyGoalItem.wihtValue(1, 5000);
                  dailyGoals.sStep = EADailyGoalItem.wihtValue(1, 8000);
                  dailyGoals.sDistance = EADailyGoalItem.wihtValue(1, 1000);
                  dailyGoals.sDuration =
                      EADailyGoalItem.wihtValue(1, 2 * 60 * 60);
                  dailyGoals.sSleep = EADailyGoalItem.wihtValue(1, 8 * 60 * 60);
                  secondMethodSetWatchData(
                      kEADataInfoTypeDailyGoal, dailyGoals.toMap());
                },
              ),
              GestureDetector(
                child: TextView(
                    '7.Set up automatic heart rate monitoring【设置自动心率监测】'),
                onTap: () {
                  EAAutoCheckHeartRate autoCheckHeartRate =
                      EAAutoCheckHeartRate(15);
                  secondMethodSetWatchData(kEADataInfoTypeAutoCheckHeartRate,
                      autoCheckHeartRate.toMap());
                },
              ),
              GestureDetector(
                child: TextView('8.Set sedentary monitoring【设置久坐监测】'),
                onTap: () {
                  EAAutoCheckSedentariness autoCheckSedentariness =
                      EAAutoCheckSedentariness();
                  autoCheckSedentariness.beginHour = 8;
                  autoCheckSedentariness.endHour = 22;
                  autoCheckSedentariness.stepThreshold = 100;
                  autoCheckSedentariness.interval = 1;
                  autoCheckSedentariness.weekCycleBit = 127;
                  autoCheckSedentariness.sw = 11;
                  secondMethodSetWatchData(
                      kEADataInfoTypeAutoCheckSedentariness,
                      autoCheckSedentariness.toMap());
                },
              ),
              GestureDetector(
                child: TextView('9.Set the weather【设置天气】'),
                onTap: () {
                  EADayWeather dayWeather = EADayWeather();
                  dayWeather.eDayType = EAWeatherType.clearDay;
                  dayWeather.eNightType = EAWeatherType.moderateRain;
                  dayWeather.minTemperature = 10;
                  dayWeather.maxTemperature = 26;
                  dayWeather.maxWindPower = 5;
                  dayWeather.minWindPower = 2;
                  dayWeather.eAir = EAWeatherAirType.excellent;

                  EADayWeather dayWeather2 = EADayWeather();
                  dayWeather2.eDayType = EAWeatherType.lightSnow;
                  dayWeather2.eNightType = EAWeatherType.moderateRain;
                  dayWeather2.minTemperature = -15;
                  dayWeather2.maxTemperature = -10;
                  dayWeather2.maxWindPower = 1;
                  dayWeather2.minWindPower = 3;
                  dayWeather2.eAir = EAWeatherAirType.bad;

                  EAWeathers weathers = EAWeathers();
                  weathers.currentTemperature = -12;
                  weathers.place = "hxhhiox";
                  weathers.days = [dayWeather, dayWeather2];
                  weathers.weatherUnit = EAWeatherUnit.Fahrenheit;
                  secondMethodSetWatchData(
                      kEADataInfoTypeWeather, weathers.toMap());
                },
              ),
              GestureDetector(
                child: TextView('10.Set the alarm and remind time【设置闹钟、提醒时间】'),
                onTap: () {
                  /** Note:【注意事项：】
                   * 【id_p】：edit, delete only need to assign, edit, delete the corresponding reminder.【编辑、删除才需要赋值，编辑、删除对应的提醒。】
                   * The assignment to content will only be displayed when reminderEventType = 6【当 reminderEventType = 6时，给 content 赋值才会显示】
                   * SIndexArray does not need to pass values when eOps = is removed.【当 eOps = 删除时，sIndexArray 不需要传值】
                   */

                  // EAReminder reminder = EAReminder();
                  // reminder.reminderEventType = EAReminderEventType.Alarm;
                  // reminder.hour = 11;
                  // reminder.minute = 26;
                  // reminder.sw = 1;
                  // reminder.secSw = 0;
                  // reminder.remindActionType = EARemindActionType.LongVibration;
                  // reminder.sleepDuration = 5 * 60;

                  EAReminder reminder2 = EAReminder();
                  reminder2.reminderEventType = EAReminderEventType.Drink;
                  reminder2.hour = 11;
                  reminder2.minute = 28;
                  reminder2.sw = 1;
                  reminder2.secSw = 0;
                  reminder2.remindActionType = EARemindActionType.LongVibration;
                  reminder2.sleepDuration = 5 * 60;

                  // EAReminder reminder3 = EAReminder();
                  // reminder3.reminderEventType = EAReminderEventType.Drink;
                  // reminder3.hour = 23;
                  // reminder3.minute = 19;
                  // reminder3.sw = 1;
                  // reminder3.secSw = 0;
                  // reminder3.remindActionType = EARemindActionType.LongVibration;
                  // reminder3.sleepDuration = 5 * 60;

                  EAReminderOps reminderOps = EAReminderOps();
                  reminderOps.list = [reminder2];
                  secondMethodSetWatchData(
                      kEADataInfoTypeReminder, reminderOps.toMap());
                },
              ),
              GestureDetector(
                child:
                    TextView('11.Set the heart rate alarm threshold【设置心率报警门限】'),
                onTap: () {
                  EAHeartRateWaringSetting heartRateWaringSetting =
                      EAHeartRateWaringSetting(1, 160, 40);
                  secondMethodSetWatchData(
                      kEADataInfoTypeHeartRateWaringSetting,
                      heartRateWaringSetting.toMap());
                },
              ),
              GestureDetector(
                child: TextView(
                    '12.Raise your hand to light the screen switch【抬手亮屏开关】'),
                onTap: () {
                  // 全天开启
                  EAScreenGesturesSetting screenGesturesSetting =
                      EAScreenGesturesSetting.allDay();
                  secondMethodSetWatchData(kEADataInfoTypeGesturesSetting,
                      screenGesturesSetting.toMap());
                },
              ),
              GestureDetector(
                child: TextView('13.Setting the Level 1 Menu【设置一级菜单】'),
                onTap: () {
                  ///【显示的页面】
                  EAPage hrPage = EAPage.hr();
                  EAPage musicPage = EAPage.music();
                  EAPage menstrualCyclePage = EAPage.menstrualCycle();
                  EAHomePages homePages = EAHomePages();
                  homePages.list = [hrPage, musicPage, menstrualCyclePage];
                  secondMethodSetWatchData(
                      kEADataInfoTypeHomePage, homePages.toMap());
                },
              ),
              GestureDetector(
                child: TextView('14.Set the period【设置经期】'),
                onTap: () {
                  EAMenstrual menstrual = EAMenstrual("2022-04-15", 5, 28);
                  secondMethodSetWatchData(
                      kEADataInfoTypeMenstrual, menstrual.toMap());
                },
              ),
              GestureDetector(
                child: TextView('15.Set the built-in dial【设置内置表盘】'),
                onTap: () {
                  EAWatchFacelInfo watchFacelInfo = EAWatchFacelInfo.buildIn(3);
                  secondMethodSetWatchData(
                      kEADataInfoTypeWatchFace, watchFacelInfo.toMap());
                },
              ),
              GestureDetector(
                child: TextView('16.Message push switch【消息推送开关】'),
                onTap: () {
                  /*
                  1. Use kEADataInfoTypeAppMessage to obtain EAShowAppMessage first
                  2. Set EAShowAppMessage

                  1.先使用 kEADataInfoTypeAppMessage 获取 EAShowAppMessage
                  2.再设置 EAShowAppMessage
                  */

                  secondMethodGetWatchData(kEADataInfoTypeAppMessage);
                },
              ),
              GestureDetector(
                child: TextView('17.Set the Habit Tracker【设置习惯】'),
                onTap: () {
                  EAHabitTracker habitTracker = EAHabitTracker();
                  habitTracker.r = 0;
                  habitTracker.g = 255;
                  habitTracker.b = 255;
                  habitTracker.content = "dog";
                  habitTracker.duration = 30;
                  habitTracker.beginHour = 22;
                  habitTracker.beginMinute = 0;
                  habitTracker.endHour = 7;
                  habitTracker.endMinute = 00;
                  habitTracker.eAction = EARemindActionType.LongShortVibration;
                  habitTracker.eIconId = EAHabitTrackerIconType.Dog15;

                  EAHabitTrackers habitTrackers = EAHabitTrackers();
                  habitTrackers.list = [habitTracker];
                  habitTrackers.eOps = EAHabitTrackerOps.Add;
                  secondMethodSetWatchData(
                      kEADataInfoTypeHabitTracker, habitTrackers.toMap());
                },
              ),
              GestureDetector(
                child: TextView('18.Push message【推送信息到手表】'),
                onTap: () {
                  EAPushMessage eapushMessage = EAPushMessage();
                  eapushMessage.messageType = EAPushMessageType.facebook;
                  eapushMessage.messageActionType = EAPushMessageActionType.add;
                  eapushMessage.title = "test";
                  DateTime dateTime = DateTime.now();
                  eapushMessage.date = "2022" +
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
                  eapushMessage.content =
                      "Test push information" + dateTime.second.toString();
                  secondMethodSetWatchData(
                      kEADataInfoTypePushInfo, eapushMessage.toMap());
                },
              ),
              GestureDetector(
                child: TextView('19.Set social switch【社交提醒开关】'),
                onTap: () {
                  EASocialSwitch eaSocialSwitch = EASocialSwitch.init(
                      1, 1, 1, 1, 1, 1, EARemindActionType.LongShortVibration);
                  secondMethodSetWatchData(
                      kEADataInfoTypeSocialSwitch, eaSocialSwitch.toMap());
                },
              ),
              GestureDetector(
                child: TextView('20.Monitor reminder event【提醒事件监测】'),
                onTap: () {
                  EAMonitorReminder monitorReminder = EAMonitorReminder();
                  monitorReminder.eReminderType = EAMonitorReminderType.drink;
                  monitorReminder.sw = 1;
                  monitorReminder.weekCycleBit = 127; // 127:all day
                  monitorReminder.interval = 1;
                  monitorReminder.beginHour = 8;
                  monitorReminder.beginMinute = 0;
                  monitorReminder.endHour = 22;
                  monitorReminder.endMinute = 0;
                  monitorReminder.cup = 2;
                  secondMethodSetWatchData(
                      kEADataInfoTypeMonitorReminder, monitorReminder.toMap());
                },
              ),
              GestureDetector(
                child: TextView('21.Set music info【同步当前音乐信息】'),
                onTap: () {
                  EABleMusicInfo eableMusicInfo = EABleMusicInfo();
                  eableMusicInfo.content = "Baby One More Time";
                  eableMusicInfo.artist = "Backstreet Boys";
                  eableMusicInfo.duration = 60 * 4;
                  eableMusicInfo.elapsedtime = 60;
                  eableMusicInfo.playState = 1;
                  eableMusicInfo.volume = 30;
                  secondMethodSetWatchData(
                      kEADataInfoTypeMusic, eableMusicInfo.toMap());
                },
              ),
              GestureDetector(
                child: NewTextView('22.Set telephone book【同步通讯录】'),
                onTap: () {
                  EAContactModel eaContactModel =
                      EAContactModel("Tony", "+011125128");
                  EAContactModel eaContactModel2 =
                      EAContactModel("Lily", "+018461382");
                  EATelephoneBook eaTelephoneBook = EATelephoneBook();
                  eaTelephoneBook.contacts = [eaContactModel, eaContactModel2];
                  secondMethodSetWatchData(
                      kEADataInfoTypeTelephoneBook, eaTelephoneBook.toMap());
                },
              ),
              GestureDetector(
                child: NewTextView('23.Set Blood Oxygen Monitor【设置血氧监测】'),
                onTap: () {
                  EABloodOxygenMonitor eaBloodOxygenMonitor =
                      EABloodOxygenMonitor(0, 60);

                  secondMethodSetWatchData(kEADataInfoTypeBloodOxygenMonitor,
                      eaBloodOxygenMonitor.toMap());
                },
              ),
              GestureDetector(
                child: NewTextView('24.Set Stress Monitor【设置压力监测】'),
                onTap: () {
                  EAStressMonitor eaStressMonitor = EAStressMonitor(0, 60);

                  secondMethodSetWatchData(
                      kEADataInfoTypeStressMonitor, eaStressMonitor.toMap());
                },
              ),
              GestureDetector(
                child: NewTextView('25.Set VibrateIntensity【设置震动】'),
                onTap: () {
                  EAVibrateIntensity eaVibrateIntensity =
                      EAVibrateIntensity(EAVibrateIntensityType.Medium);

                  secondMethodSetWatchData(kEADataInfoTypeVibrateIntensity,
                      eaVibrateIntensity.toMap());
                },
              ),
              GestureDetector(
                child: NewTextView('25.Set Menstrual Reminder【经期提醒】'),
                onTap: () {
                  EAMenstrualReminder eaMenstrualReminder =
                      EAMenstrualReminder();
                  eaMenstrualReminder.menstrualBeginSw = true;
                  eaMenstrualReminder.menstrualReminderDaysBefore = 1;
                  eaMenstrualReminder.menstrualReminderHours = 9;
                  eaMenstrualReminder.menstrualReminderMinutes = 0;
                  secondMethodSetWatchData(kEADataInfoTypeMenstrualReminder,
                      eaMenstrualReminder.toMap());
                },
              ),
              // GestureDetector(
              //   child: NewTextView('25.Set Sport Hr Warning【运动心率告警】'),
              //   onTap: () {
              //     EASportHrWarning eaSportHrWarning =
              //         EASportHrWarning(0, 180, 60);

              //     secondMethodSetWatchData(
              //         kEADataInfoTypeSportHrWarning, eaSportHrWarning.toMap());
              //   },
              // ),
              TitleView(' Getting big data【获取大数据】'),
              GestureDetector(
                child:
                    TextView('Send a request to obtain big data 【发送获取大数据请求】'),
                onTap: () {
                  /**
                   * 返回所有的大数据，手表会自动清除已返回的大数据
                   * 注意监听 _dataChannel 返回 8后,即可获取各类型的大数据
                   * Return all big data, the watch will automatically clear the returned big data
                   * Notice after listener _dataChannel returns 8, you can obtain all types of big data
                   */
                  print("获取大数据");
                  getBigWatchData();
                },
              ),
              TitleView('  Operating watch commands【操作手表命令】'),
              GestureDetector(
                child: TextView('1.Stop looking for your phone【停止寻找手机】'),
                onTap: () {
                  /**
                   * 【EAOperationWatchType】
                      /// 恢复出厂设置
                      RestoreFactory,
                      /// 重启设备
                      Reset,
                      /// 设备关机
                      PowerOff,
                      /// 断开蓝牙
                      DisconnectBle,
                      /// 进入飞行模式
                      EnteringFlightMode,
                      /// 点亮屏幕
                      LightUpTheScreen,
                      /// 熄灭屏幕
                      TurnOffTheScreen,
                      /// 停止寻找手机
                      StopSearchPhone,
                      /// 寻找手表
                      StartSearchWatch,
                      /// 停止寻找手表
                      StopSearchWatch,
                   */

                  secondEasdkTool.operationWatch(
                      EAOperationWatchType.StopSearchPhone,
                      OperationWatchCallback((info) {}));
                },
              ),
              GestureDetector(
                child: TextView('2. Looking for your watch【寻找手表】'),
                onTap: () {
                  secondEasdkTool.operationWatch(
                      EAOperationWatchType.StartSearchWatch,
                      OperationWatchCallback((info) {}));
                },
              ),
              GestureDetector(
                child: TextView('3.Stop looking for your watch【停止寻找手表】'),
                onTap: () {
                  secondEasdkTool.operationWatch(
                      EAOperationWatchType.StopSearchWatch,
                      OperationWatchCallback((info) {}));
                },
              ),
              GestureDetector(
                child: TextView('4.Show iPhone pairing alert【iOS手机弹出配对提醒】'),
                onTap: () {
                  secondEasdkTool.operationWatch(
                      EAOperationWatchType.ShowiPhonePairingAlert,
                      OperationWatchCallback((info) {}));
                },
              ),
              TitleView('  OTA【升级】'),
              GestureDetector(
                child: TextView('1.To upgrade the firmware【升级固件】'),
                onTap: () async {
                  /**
                   *
                   * 【type == 1】，升级固件注意事项
                   * * * 1.必须要比当前版本大才能升级成功，
                   * * * 2.version 必须按照 一定的格式来 (以下 xx 代表为数值)
                      阿波罗  Apollo: APxxBxx
                      字库    Res: Rxx
                      屏幕    Tp: Txx
                      心率    Hr: Hxx
                   * * * 3.【firmwareType】：
                   *
                   *  [type == 1], firmware upgrade precautions
                   * * * 1. The upgrade must be larger than the current version to succeed.
                   * * * 2. The version must be in a certain format (xx represents a number below).
                      Apollo Apollo: APxxBxx
                      Word stock Res: Rxx
                      Tp: screen Txx
                      Heart rate Hr: Hxx
                   * * * firmwareType:
                   */

                  /**
                   * 假设 当前手表 firmwareVersion => AP0.1B0.9R0.3T0.1G0.1
                   * AP0.1B0.9 => 固件版本号是0.1 build 0.9
                   * R0.3=>字库版本号是0.3
                   * T0.1=>屏幕版本号0.1
                   * H0.1=>心率版本号0.1
                   *
                   * 有新的 固件版本号 是 AP0.1B1.0 和 AP0.1B1.1
                   * 有新的 字库版本号是 R0.4 和 R0.5
                   *
                   * 固件版本升级 升级 最大版本号就可以了，即升级 AP0.1B1.1，不需要升级 AP0.1B1.0
                   * 字库版本升级 需要升级所有比当前字库版本大的版本，即 R0.4 和 R0.5都要升级
                   * 屏幕和心率同固件一样逻辑，升级最大版本的就行
                   */

                  /**
                   * Assume that the firmwareVersion of the current watch is => AP0.1b0.9R0.3T0.1G0.1
                   * ap0.1b0.9 => The firmware version number is 0.1 build 0.9
                   * R0.3=> The font version is 0.3
                   * T0.1=> Screen version 0.1
                   * H0.1=> heart rate version 0.1
                   *
                   * There are new firmware version numbers AP0.1b1.0 and AP0.1b1.1
                   * There are new font version numbers R0.4 and R0.5
                   *
                   * Firmware version upgrade You only need to upgrade the maximum version, that is, AP0.1b1.1. You do not need to upgrade AP0.1b1.0
                   * All versions larger than the current font version need to be upgraded, that is, R0.4 and R0.5 need to be upgraded
                   * The screen and heart rate are just as logical as firmware. Upgrade to the largest version
                   */

                  // EAOTA ota1 = EAOTA(
                  //     "固件AP0.1B1.1本地文件路径", EAFirmwareType.Apollo, "AP0.1B1.1");
                  // EAOTA ota2 = EAOTA("字库0.4本地文件路径", EAFirmwareType.Res, "R0.4");
                  // EAOTA ota3 = EAOTA("字库0.5本地文件路径", EAFirmwareType.Res, "R0.5");

                  // EAOTAList otaList = EAOTAList(0, [ota1, ota2, ota3]);

                  // EASDKTool().otaUpgrade(otaList,
                  //     EAOTAProgressCallback((progress) {
                  //   if (progress == -1) {
                  //     // transmit data fail;

                  //   } else if (progress == 100) {
                  //     // transmit data succ;
                  //   } else {
                  //     // transmit data progress
                  //   }
                  // }));

                  var bytes =
                      await rootBundle.load("assets/bin/002019_AP0.1B0.4.bin");
                  String path = (await getApplicationSupportDirectory()).path;
                  String filePath =
                      '$path/' + DateTime.now().toString() + '.bin';
                  final buffer = bytes.buffer;
                  await File(filePath).writeAsBytes(buffer.asUint8List(
                      bytes.offsetInBytes, bytes.lengthInBytes));

                  var bytes1 =
                      await rootBundle.load("assets/bin/002019_AP0.1B0.5.bin");
                  String path1 = (await getApplicationSupportDirectory()).path;
                  String filePath1 =
                      '$path1/' + DateTime.now().toString() + '.bin';
                  final buffer1 = bytes1.buffer;
                  await File(filePath1).writeAsBytes(buffer1.asUint8List(
                      bytes1.offsetInBytes, bytes1.lengthInBytes));
                  var bytes4 =
                      await rootBundle.load("assets/bin/002019_AP0.1B0.6.bin");
                  String path4 = (await getApplicationSupportDirectory()).path;
                  String filePath4 =
                      '$path4/' + DateTime.now().toString() + '.bin';
                  final buffer4 = bytes4.buffer;
                  await File(filePath4).writeAsBytes(buffer4.asUint8List(
                      bytes4.offsetInBytes, bytes4.lengthInBytes));

                  var bytes5 =
                      await rootBundle.load("assets/bin/002019_AP0.1B0.7.bin");
                  String path5 = (await getApplicationSupportDirectory()).path;
                  String filePath5 =
                      '$path5/' + DateTime.now().toString() + '.bin';
                  final buffer5 = bytes5.buffer;
                  await File(filePath5).writeAsBytes(buffer5.asUint8List(
                      bytes5.offsetInBytes, bytes5.lengthInBytes));

                  var bytes2 =
                      await rootBundle.load("assets/bin/002019_R0.2.bin");
                  String path2 = (await getApplicationSupportDirectory()).path;
                  String filePath2 =
                      '$path2/' + DateTime.now().toString() + '.bin';
                  final buffer2 = bytes2.buffer;
                  await File(filePath2).writeAsBytes(buffer2.asUint8List(
                      bytes2.offsetInBytes, bytes2.lengthInBytes));

                  var bytes3 =
                      await rootBundle.load("assets/bin/002019_R0.3.bin");
                  String path3 = (await getApplicationSupportDirectory()).path;
                  String filePath3 =
                      '$path3/' + DateTime.now().toString() + '.bin';
                  final buffer3 = bytes3.buffer;
                  await File(filePath3).writeAsBytes(buffer3.asUint8List(
                      bytes3.offsetInBytes, bytes3.lengthInBytes));
                  var bytes9 =
                      await rootBundle.load("assets/bin/watchface_U38.bin");
                  String path9 = (await getApplicationSupportDirectory()).path;
                  String filePath9 =
                      '$path9/' + DateTime.now().toString() + '.bin';
                  final buffer9 = bytes9.buffer;
                  await File(filePath9).writeAsBytes(buffer9.asUint8List(
                      bytes9.offsetInBytes, bytes9.lengthInBytes));
                  EAOTA dialOTA = EAOTA(filePath9, EAFirmwareType.wf, "");
                  EAOTA appoloOTA =
                      EAOTA(filePath, EAFirmwareType.Apollo, "AP0.1B0.4");
                  EAOTA appoloOTA1 =
                      EAOTA(filePath1, EAFirmwareType.Apollo, "AP0.1B0.5");
                  EAOTA resOTA = EAOTA(filePath2, EAFirmwareType.Res, "R0.2");
                  EAOTA resOTA1 = EAOTA(filePath3, EAFirmwareType.Res, "R0.3");
                  EAOTA appoloOTA2 =
                      EAOTA(filePath4, EAFirmwareType.Apollo, "AP0.1B0.6");
                  EAOTA appoloOTA3 =
                      EAOTA(filePath5, EAFirmwareType.Apollo, "AP0.1B0.7");

                  EAOTAList eaList = EAOTAList(0, [dialOTA]);
                  secondEasdkTool.otaUpgrade(eaList,
                      EAOTAProgressCallback((progress) {
                    print("OTA进度:" + progress.toString());
                    if (progress == -1) {
                      // transmit data fail;

                    } else if (progress == 100) {
                      // transmit data succ;
                    } else {
                      // transmit data progress
                    }
                  }));
                },
              ),
              GestureDetector(
                child: TextView('2.watch face【表盘】'),
                onTap: () async {
                  var bytes =
                      await rootBundle.load("assets/bin/watchface_U38.bin");
                  String path = (await getApplicationSupportDirectory()).path;
                  String filePath =
                      '$path/' + DateTime.now().toString() + '.bin';
                  final buffer = bytes.buffer;
                  await File(filePath).writeAsBytes(buffer.asUint8List(
                      bytes.offsetInBytes, bytes.lengthInBytes));

                  EAOTA watchfaceOTA = EAOTA.watchface(filePath);

                  EAOTAList eaList = EAOTAList(1, [watchfaceOTA]);
                  secondEasdkTool.otaUpgrade(eaList,
                      EAOTAProgressCallback((progress) {
                    print("表盘进度:" + progress.toString());
                    if (progress == -1) {
                      // transmit data fail;

                    } else if (progress == 100) {
                      // transmit data succ;
                    } else {
                      // transmit data progress
                    }
                  }));
                },
              ),
              TitleView('  Custom Watch Face【自定义表盘】'),
              GestureDetector(
                child: NewTextView('1.Nmuber Preview Image【数字表盘预览图】'),
                onTap: () async {
                  String filePath = await getImageAndPassToPlugin(
                      "assets/images/360*360.png");

                  EACustomWatchFace customWatchFace = EACustomWatchFace();
                  customWatchFace.isNumberWf = true;
                  customWatchFace.bgImagePath = filePath;
                  customWatchFace.numbeColorHex = "#0000FF";
                  customWatchFace.getPreviewImage = true;
                  secondEasdkTool
                      .getCustomWatchfacePreviewImage(customWatchFace,
                          EACustomWatchfacePreviewImageCallback((previewImage) {
                    print(previewImage);
                  }));
                },
              ),
              GestureDetector(
                child: NewTextView('2.OTA Nmuber Watch Face【OTA数字表盘】'),
                onTap: () async {
                  String filePath = await getImageAndPassToPlugin(
                      "assets/images/360*360.png");

                  EACustomWatchFace customWatchFace = EACustomWatchFace();
                  customWatchFace.isNumberWf = true;
                  customWatchFace.bgImagePath = filePath;
                  customWatchFace.numbeColorHex = "#0000FF";
                  customWatchFace.getPreviewImage = false;
                  secondEasdkTool.otaCustomWatchface(customWatchFace,
                      EAOTAProgressCallback((progress) {
                    print("OTA进度:" + progress.toString());
                    if (progress == -1) {
                      // transmit data fail;

                    } else if (progress == 100) {
                      // transmit data succ;
                    } else {
                      // transmit data progress
                    }
                  }));
                },
              ),
              GestureDetector(
                child: NewTextView('3.Pointer Preview Image【指针表盘预览图】'),
                onTap: () async {
                  String filePath = await getImageAndPassToPlugin(
                      "assets/images/360*360.png");

                  EACustomWatchFace customWatchFace = EACustomWatchFace();
                  customWatchFace.isNumberWf = false;
                  customWatchFace.bgImagePath = filePath;
                  customWatchFace.pointerColorType = 1;
                  customWatchFace.getPreviewImage = true;
                  secondEasdkTool
                      .getCustomWatchfacePreviewImage(customWatchFace,
                          EACustomWatchfacePreviewImageCallback((previewImage) {
                    print(previewImage);
                  }));
                },
              ),
              GestureDetector(
                child: NewTextView('4.OTA Pointer Watch Face【OTA指针表盘】'),
                onTap: () async {
                  String filePath = await getImageAndPassToPlugin(
                      "assets/images/360*360.png");
                  EACustomWatchFace customWatchFace = EACustomWatchFace();
                  customWatchFace.isNumberWf = false;
                  customWatchFace.bgImagePath = filePath;
                  customWatchFace.pointerColorType = 1;
                  customWatchFace.getPreviewImage = false;
                  secondEasdkTool.otaCustomWatchface(customWatchFace,
                      EAOTAProgressCallback((progress) {
                    print("OTA进度:" + progress.toString());
                    if (progress == -1) {
                      // transmit data fail;

                    } else if (progress == 100) {
                      // transmit data succ;
                    } else {
                      // transmit data progress
                    }
                  }));
                },
              ),
              TitleView('  unbindWatch【解绑】'),
              GestureDetector(
                child: TextView('1.Unbundling equipment【解绑设备】'),
                onTap: () {
                  secondEasdkTool.unbindWatch();
                },
              ),
              GestureDetector(
                child: TextView('2.disconnect equipment【断开设备】'),
                onTap: () {
                  secondEasdkTool.disConnectWatch();
                },
              ),
            ],
          ),
        ),
      ),
    );
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

      default:
        break;
    }
  }

  Future<bool> getLocationPermission() async {
    PermissionStatus myPermission = await Permission.location.request();
    if (myPermission == PermissionStatus.granted) {
      return true;
    }
    return false;
  }

  Future<bool> getBluethScanPermission() async {
    PermissionStatus myPermission = await Permission.bluetoothScan.request();
    if (myPermission == PermissionStatus.granted) {
      return true;
    }
    return false;
  }

  Future<bool> getBluethConnectPermission() async {
    PermissionStatus myPermission = await Permission.bluetoothConnect.request();
    if (myPermission == PermissionStatus.granted) {
      return true;
    }
    return false;
  }

  void checkLocationPermission() async {
    bool locationPermission = await getLocationPermission();
    if (locationPermission) {
      DeviceInfoPlugin deviceInfoPlugin = DeviceInfoPlugin();
      AndroidDeviceInfo androidDeviceInfo = await deviceInfoPlugin.androidInfo;
      if (androidDeviceInfo != null) {
        if (androidDeviceInfo.version != null) {
          var sdkInt = androidDeviceInfo.version.sdkInt;
          if (sdkInt! > 11) {
            bool scanPermission = await getBluethScanPermission();
            if (scanPermission) {
              bool connectPermission = await getBluethConnectPermission();
              if (connectPermission) {
                initGetIsolate();
                initSetIsolate();
                foregroundTask();
                secondEasdkTool.initChannel();
              }
            }
          }
        }
      }
    }
  }

  Future<String> getImageAndPassToPlugin(String imageName) async {
    // 获取图片目录
    // final directory = await getApplicationDocumentsDirectory();
    // final imagePath = '${directory.path}/$imageName';
    // return imagePath;

    var bytes = await rootBundle.load(imageName);
    String path = (await getApplicationSupportDirectory()).path;
    String filePath = '$path/' + "bgImage" + '.png';
    final buffer = bytes.buffer;
    await File(filePath).writeAsBytes(
        buffer.asUint8List(bytes.offsetInBytes, bytes.lengthInBytes));
    return filePath;
  }
}
