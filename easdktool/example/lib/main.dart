// ignore_for_file: avoid_print

// import 'dart:typed_data';

import 'dart:io';

import 'package:flutter/material.dart';
import 'package:easdktool/easdktool.dart';
import 'package:easdktool/EACallback.dart';
import 'package:easdktool/Been/EABeen.dart';
import 'package:flutter/services.dart';
import 'package:path_provider/path_provider.dart';

// import 'package:dio/dio.dart';
// import 'dart:io';

// import 'package:flutter_ble_lib/flutter_ble_lib.dart';

void main() {
  runApp(const MyApp());
}

String connectState = "Unknown";

class ConnectListener implements EABleConnectListener {
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

    EASDKTool().getWatchData(
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
                bindInfo.bindMod = 1;
                if (eaBleWatchInfo.isWaitForBinding == 0) {
                  //Bind command type: End【绑定命令类型：结束】
                  bindInfo.bindingCommandType = 1;
                } else {
                  //Bind command type: Begin【绑定命令类型：开始】
                  bindInfo.bindingCommandType = 0;
                }
                EASDKTool().bindingWatch(bindInfo);
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

class MyApp extends StatefulWidget {
  const MyApp({Key? key}) : super(key: key);

  @override
  State<MyApp> createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  @override
  void initState() {
    super.initState();

    /// 打开 SDKLog
    EASDKTool().showLog(1);

    connectBluetooth(); // test

    /// 【添加监听】
    EASDKTool.addBleConnectListener(ConnectListener());
    EASDKTool.addOperationPhoneCallback(OperationPhoneCallback((info) {
      operationPhoneListener(info);
    }));

    /// search watch
    // EASDKTool().scanWatch(EAScanWatchCallback((connectParam) {
    //   print(connectParam.name);
    // }));

    // EASDKTool().stopWatch();
  }

  /// 【绑定手表】
  void connectBluetooth() {
    EAConnectParam connectParam = EAConnectParam();
    connectParam.connectAddress =
        "45:41:CD:11:11:02"; //"45:41:46:03:F2:A7"; // "45:41:70:97:FC:84"; // andriond need
    connectParam.snNumber = "0020060000099990101";
    //"001007220516000001","002006000009999009","001007220719000021","001007220516000001"; //"001001211112000028"; // iOS need
    EASDKTool().connectToPeripheral(connectParam);
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

  void getWacthStateInfo() async {
    EAConnectStateInfo connectStateInfo = await EASDKTool().getWacthStateInfo();
    print(connectStateInfo.connectState.index);
  }

  void setWatchData(int dataType, Map map) {
    EASDKTool().setWatchData(dataType, map,
        EASetDataCallback(onRespond: ((respond) {
      print(respond.respondCodeType);
    })));
  }

  void getBigWatchData() {
    EASDKTool().getBigWatchData(EAGetBitDataCallback(((info) {
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
            print(model.steps);
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
            print(model.sportType);
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

  void operationPhoneListener(Map info) {
    ///  Check whether info["opePhoneType"] belongs to EAOpePhoneType and perform the corresponding operation
    /// 【判断 info["opePhoneType"] 是属于EAOpePhoneType的哪一个，做对应的操作】
  }

  void returnClassValue(int dataType, Map<String, dynamic> value) {
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
      case EADataInfoTypeBlePairState:
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
      default:
    }
  }

  // ignore: non_constant_identifier_names
  Widget TextView(String text) {
    return Container(
      child: Text(text,
          style: TextStyle(fontSize: 15, fontWeight: FontWeight.w500)),
      height: 60,
    );
  }

// ignore: non_constant_identifier_names
  Widget TitleView(String title) {
    return Container(
      child: Text(title,
          style: TextStyle(fontSize: 20, fontWeight: FontWeight.bold)),
      height: 30,
      color: Colors.grey.shade300,
    );
  }

  @override
  Widget build(BuildContext context) {
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
                  getWatchData(kEADataInfoTypeWatch);
                },
              ),
              GestureDetector(
                child: TextView('2.Obtaining User information【获取用户信息】'),
                onTap: () {
                  getWatchData(kEADataInfoTypeUser);
                },
              ),
              GestureDetector(
                child: TextView('3.Get watch screen brightness【获取手表屏幕亮度】'),
                onTap: () {
                  getWatchData(kEADataInfoTypeBlacklight);
                },
              ),
              GestureDetector(
                child: TextView('4.Obtain the battery【获取手表电量信息】'),
                onTap: () {
                  getWatchData(kEADataInfoTypeBattery);
                },
              ),
              GestureDetector(
                child: TextView('5.Obtain the device language【设备语言信息】'),
                onTap: () {
                  getWatchData(kEADataInfoTypeLanguage);
                },
              ),
              GestureDetector(
                child: TextView('6.Obtain the device unit system【设备单位制度】'),
                onTap: () {
                  getWatchData(kEADataInfoTypeUnifiedUnit);
                },
              ),
              GestureDetector(
                child: TextView('7.Obtain the DND period【获取手表免打扰时间段】'),
                onTap: () {
                  getWatchData(kEADataInfoTypeNotDisturb);
                },
              ),
              GestureDetector(
                child: TextView('8.Obtain the daily target value【获取手表日常目标值】'),
                onTap: () {
                  getWatchData(kEADataInfoTypeDailyGoal);
                },
              ),
              GestureDetector(
                child: TextView(
                    '9.Obtain the automatic sleep monitoring【获取手表自动睡眠监测】'),
                onTap: () {
                  getWatchData(kEADataInfoTypeAutoCheckSleep);
                },
              ),
              GestureDetector(
                child: TextView(
                    '10.Get watch automatic heart rate monitoring【获取手表自动心率监测】'),
                onTap: () {
                  getWatchData(kEADataInfoTypeAutoCheckHeartRate);
                },
              ),
              GestureDetector(
                child: TextView('11.Get watch sedentary monitoring【获取手表久坐监测】'),
                onTap: () {
                  getWatchData(kEADataInfoTypeAutoCheckSedentariness);
                },
              ),
              GestureDetector(
                child: TextView(
                    '12.Get watch Social alert switch(SMS、PhoneCall、Email)【社交提醒开关】'),
                onTap: () {
                  getWatchData(kEADataInfoTypeSocialSwitch);
                },
              ),
              GestureDetector(
                child: TextView('13.Get watch alerts【获取手表提醒】'),
                onTap: () {
                  getWatchData(kEADataInfoTypeReminder);
                },
              ),
              GestureDetector(
                child:
                    TextView('14.Get heart rate alarm threshold【获取手表心率报警门限】'),
                onTap: () {
                  getWatchData(kEADataInfoTypeHeartRateWaringSetting);
                },
              ),
              GestureDetector(
                child: TextView('15.Get distance unit【获取距离单位】'),
                onTap: () {
                  getWatchData(kEADataInfoTypeDistanceUnit);
                },
              ),
              GestureDetector(
                child: TextView('16.Get weight Unit【获取重量单位】'),
                onTap: () {
                  getWatchData(kEADataInfoTypeWeightUnit);
                },
              ),
              GestureDetector(
                child: TextView('17.Get heart rate waring【心率报警门限】'),
                onTap: () {
                  getWatchData(kEADataInfoTypeHeartRateWaringSetting);
                },
              ),
              GestureDetector(
                child: TextView('18.Get calories open state【卡路里开关】'),
                onTap: () {
                  getWatchData(kEADataInfoTypeCaloriesSetting);
                },
              ),
              GestureDetector(
                child: TextView('19.Get gestures open state【抬手亮屏开关】'),
                onTap: () {
                  getWatchData(kEADataInfoTypeGesturesSetting);
                },
              ),
              GestureDetector(
                child: TextView('20.Get general information【获取手表通用信息】'),
                onTap: () {
                  getWatchData(kEADataInfoTypeWatchSettingInfo);
                },
              ),
              GestureDetector(
                child: TextView('21.Get the first-level menu【获取手表一级菜单】'),
                onTap: () {
                  getWatchData(kEADataInfoTypeHomePage);
                },
              ),
              GestureDetector(
                child: TextView('22.Interest rates screen time【息屏时间】'),
                onTap: () {
                  getWatchData(kEADataInfoTypeBlacklightTimeout);
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
                  getWatchData(kEADataInfoTypeHabitTracker);
                },
              ),
              GestureDetector(
                child: TextView('25.Obtain sport show data【获取运动显示值】'),
                onTap: () {
                  getWatchData(kEADataInfoTypeSportShowData);
                },
              ),
              GestureDetector(
                child: TextView('26.get paired watche state【获取手表配对状态】'),
                onTap: () {
                  // getWatchData(EADataInfoTypeBlePairState);

                  EASDKTool().getWatchData(
                      EADataInfoTypeBlePairState,
                      EAGetDataCallback(
                          onSuccess: ((info) {
                            Map<String, dynamic> value = info["value"];
                            EAWatchPairStateModel sportShowData =
                                EAWatchPairStateModel.fromMap(value);
                          }),
                          onFail: ((info) {})));
                },
              ),
              GestureDetector(
                child: TextView('27.Obtain watch time【获取手表时间】'),
                onTap: () {
                  getWatchData(kEADataInfoTypeSyncTime);
                },
              ),
              GestureDetector(
                child: TextView('28.Obtain App notifications 【获取App消息推送】'),
                onTap: () {
                  getWatchData(kEADataInfoTypeAppMessage);
                },
              ),
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

                  setWatchData(kEADataInfoTypeUser, personInfo.toMap());
                },
              ),
              GestureDetector(
                child: TextView('2.Set the watch time【设置手表时间】'), // 同步手机时间到手表
                onTap: () {
                  EASyncTime syncTime = EASyncTime();
                  syncTime.day = 20;
                  syncTime.month = 7;
                  syncTime.year = 2022;
                  syncTime.hour = 10;
                  syncTime.minute = 6;
                  syncTime.second = 0;
                  syncTime.timeHourType = EATimeHourType.hour24;
                  syncTime.timeZone = EATimeZone.east;
                  syncTime.timeZoneHour = 8;
                  syncTime.timeZoneMinute = 0;
                  setWatchData(kEADataInfoTypeSyncTime, syncTime.toMap());
                },
              ),
              GestureDetector(
                child: TextView('3.Setting the Watch Language【设置手表语言】'),
                onTap: () {
                  EALanguage language = EALanguage();
                  language.type = EALanguageType.english;
                  setWatchData(kEADataInfoTypeLanguage, language.toMap());
                },
              ),
              GestureDetector(
                child: TextView('4.Set the watch unit-metric【设置手表单位-KM】'),
                onTap: () {
                  EAUnifiedUnit unifiedUnit = EAUnifiedUnit();
                  unifiedUnit.unit = EAUnifiedUnitType.metric;
                  setWatchData(kEADataInfoTypeUnifiedUnit, unifiedUnit.toMap());
                },
              ),
              GestureDetector(
                child: TextView('4.Set the watch unit-british【设置手表单位-MI】'),
                onTap: () {
                  EAUnifiedUnit unifiedUnit = EAUnifiedUnit();
                  unifiedUnit.unit = EAUnifiedUnitType.british;
                  setWatchData(kEADataInfoTypeUnifiedUnit, unifiedUnit.toMap());
                },
              ),
              GestureDetector(
                child: TextView('5.Set the DND period【设置免打扰时间段】'),
                onTap: () {
                  EANotDisturb notDisturb = EANotDisturb();
                  notDisturb.sw = 1;
                  notDisturb.beginHour = 1;
                  notDisturb.beginMinute = 0;
                  notDisturb.endHour = 12;
                  notDisturb.endMinute = 0;
                  setWatchData(kEADataInfoTypeNotDisturb, notDisturb.toMap());
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
                  setWatchData(kEADataInfoTypeDailyGoal, dailyGoals.toMap());
                },
              ),
              GestureDetector(
                child: TextView(
                    '7.Set up automatic heart rate monitoring【设置自动心率监测】'),
                onTap: () {
                  EAAutoCheckHeartRate autoCheckHeartRate =
                      EAAutoCheckHeartRate(60);
                  setWatchData(kEADataInfoTypeAutoCheckHeartRate,
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
                  autoCheckSedentariness.interval = 45;
                  setWatchData(kEADataInfoTypeAutoCheckSedentariness,
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
                  setWatchData(kEADataInfoTypeWeather, weathers.toMap());
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
                  setWatchData(kEADataInfoTypeReminder, reminderOps.toMap());
                },
              ),
              GestureDetector(
                child:
                    TextView('11.Set the heart rate alarm threshold【设置心率报警门限】'),
                onTap: () {
                  EAHeartRateWaringSetting heartRateWaringSetting =
                      EAHeartRateWaringSetting(1, 160, 40);
                  setWatchData(kEADataInfoTypeHeartRateWaringSetting,
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
                  setWatchData(kEADataInfoTypeGesturesSetting,
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
                  setWatchData(kEADataInfoTypeHomePage, homePages.toMap());
                },
              ),
              GestureDetector(
                child: TextView('14.Set the period【设置经期】'),
                onTap: () {
                  EAMenstrual menstrual = EAMenstrual("2022-04-15", 5, 28);
                  setWatchData(kEADataInfoTypeMenstrual, menstrual.toMap());
                },
              ),
              GestureDetector(
                child: TextView('15.Set the built-in dial【设置内置表盘】'),
                onTap: () {
                  EAWatchFacelInfo watchFacelInfo = EAWatchFacelInfo.buildIn(3);
                  setWatchData(
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

                  EASDKTool().getWatchData(
                      kEADataInfoTypeAppMessage,
                      EAGetDataCallback(
                          onSuccess: ((info) {
                            Map<String, dynamic> value = info["value"];
                            EAShowAppMessage showAppMessage =
                                EAShowAppMessage.fromMap(value);
                            showAppMessage.wechat = false;
                            setWatchData(kEADataInfoTypeAppMessage,
                                showAppMessage.toMap());
                          }),
                          onFail: ((info) {})));
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
                  setWatchData(
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
                  setWatchData(kEADataInfoTypePushInfo, eapushMessage.toMap());
                },
              ),
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

                  EASDKTool().operationWatch(
                      EAOperationWatchType.StopSearchPhone,
                      OperationWatchCallback((info) {}));
                },
              ),
              GestureDetector(
                child: TextView('2. Looking for your watch【寻找手表】'),
                onTap: () {
                  EASDKTool().operationWatch(
                      EAOperationWatchType.StartSearchWatch,
                      OperationWatchCallback((info) {}));
                },
              ),
              GestureDetector(
                child: TextView('3.Stop looking for your watch【停止寻找手表】'),
                onTap: () {
                  EASDKTool().operationWatch(
                      EAOperationWatchType.StopSearchWatch,
                      OperationWatchCallback((info) {}));
                },
              ),
              GestureDetector(
                child: TextView('4.Show iPhone pairing alert【iOS手机弹出配对提醒】'),
                onTap: () {
                  EASDKTool().operationWatch(
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
                 * * * 3.【firmwareType】：固件类型，0Apollo 2Res 3Tp 4Hr
                 * 
                 *  [type == 1], firmware upgrade precautions
                 * * * 1. The upgrade must be larger than the current version to succeed.
                 * * * 2. The version must be in a certain format (xx represents a number below).
                      Apollo Apollo: APxxBxx
                      Word stock Res: Rxx
                      Tp: screen Txx
                      Heart rate Hr: Hxx
                 * * * firmwareType: firmwareType, 0Apollo 2Res 3Tp 4Hr           
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
                      await rootBundle.load("assets/bin/002006_AP0.1B2.1.bin");
                  String path = (await getApplicationSupportDirectory()).path;
                  final buffer = bytes.buffer;
                  await File('$path/appolo.bin').writeAsBytes(buffer
                      .asUint8List(bytes.offsetInBytes, bytes.lengthInBytes));

                  var bytes2 =
                      await rootBundle.load("assets/bin/002006_R0.7.bin");
                  String path2 = (await getApplicationSupportDirectory()).path;
                  final buffer2 = bytes.buffer;
                  await File('$path2/Res.bin').writeAsBytes(buffer2.asUint8List(
                      bytes2.offsetInBytes, bytes2.lengthInBytes));

                  EAOTA appoloOTA = EAOTA(
                      '$path/appolo.bin', EAFirmwareType.Apollo, "AP0.1B2.1");
                  EAOTA resOTA =
                      EAOTA('$path2/Res.bin', EAFirmwareType.Res, "R0.7");

                  EAOTAList eaList = EAOTAList(0, [appoloOTA, resOTA]);
                  EASDKTool().otaUpgrade(eaList,
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
                  EASDKTool().unbindWatch();
                },
              ),
              GestureDetector(
                child: TextView('2.disconnect equipment【断开设备】'),
                onTap: () {
                  EASDKTool().disConnectWatch();
                },
              ),
            ],
          ),
        ),
      ),
    );
  }
}

class EADevice {
  String name = "";
  String snNumber = "";
  String macAddress = "";
  int rssi = 0;

  EADevice.n();
  EADevice(this.name, this.snNumber, this.macAddress, this.rssi);
}
