// ignore_for_file: avoid_print

// import 'dart:typed_data';

import 'package:flutter/material.dart';
import 'package:easdktool/easdktool.dart';
import 'package:easdktool/EACallback.dart';
import 'package:easdktool/Been/EABeen.dart';

// import 'package:path_provider/path_provider.dart';
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
    EABindInfo bindInfo = EABindInfo();
    bindInfo.user_id = "1008690";
    bindInfo.bindMod = 1; // Turn on the daily step interval for 30 minutes
    EASDKTool().bindingWatch(bindInfo);
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
        "45:41:46:03:F2:A2"; //"45:41:70:97:FC:84"; // andriond need
    connectParam.snNumber =
        "001012220623000021"; //"001001211112000028"; // iOS need
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
      print(info);
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

                /// 获取ios已配对的手表
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
                  syncTime.day = 18;
                  syncTime.month = 5;
                  syncTime.year = 2022;
                  syncTime.hour = 12;
                  syncTime.minute = 0;
                  syncTime.second = 0;
                  syncTime.timeHourType = EATimeHourType.hour12;
                  syncTime.timeZone = EATimeZone.zero;
                  syncTime.timeZoneHour = 0;
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
                child: TextView('4.Set the watch unit【设置手表单位】'),
                onTap: () {
                  EAUnifiedUnit unifiedUnit = EAUnifiedUnit();
                  unifiedUnit.unit = EAUnifiedUnitType.metric;
                  setWatchData(kEADataInfoTypeLanguage, unifiedUnit.toMap());
                },
              ),
              GestureDetector(
                child: TextView('5.Set the DND period【设置免打扰时间段】'),
                onTap: () {
                  EANotDisturb notDisturb = EANotDisturb();
                  notDisturb.sw = 1;
                  notDisturb.beginHour = 22;
                  notDisturb.beginMinute = 0;
                  notDisturb.endHour = 8;
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
                  weathers.currentTemperature = 20;
                  weathers.place = "Guanzhou";
                  weathers.days = [dayWeather, dayWeather2];
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

                  EAReminder reminder = EAReminder();
                  reminder.reminderEventType = EAReminderEventType.Alarm;
                  reminder.hour = 19;
                  reminder.minute = 32;
                  reminder.sw = 1;
                  reminder.secSw = 0;
                  reminder.remindActionType = EARemindActionType.NoAction;
                  reminder.sleepDuration = 5 * 60;

                  EAReminder reminder2 = EAReminder();
                  reminder2.reminderEventType = EAReminderEventType.Sleep;
                  reminder2.hour = 22;
                  reminder2.minute = 30;
                  reminder2.sw = 1;
                  reminder2.secSw = 0;
                  reminder2.remindActionType = EARemindActionType.NoAction;
                  reminder2.sleepDuration = 5 * 60;

                  EAReminderOps reminderOps = EAReminderOps();
                  reminderOps.list = [reminder, reminder2];
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
                  /**
                * 【sAppSwArray】Is an array sorted by the following types【为按以下类型排序的的数组】
                * unknow=0; //
                * wechat =1;
                * qq=2;
                * facebook=3;
                * twitter = 4;
                * messenger =5;
                * hangouts =6;
                * gmail = 7;
                * viber=8;
                * snapchat=9;
                * whatsApp=10;
                * instagram =11;
                * linkedin =12;
                * line =13;
                * skype =14;
                * booking =15;
                * airbnb =16;
                * flipboard =17;
                *spotify =18;
                * pandora =19;
                * telegram =20;
                * dropbox =21;
                * waze =22;
                * lift =23;
                * slack =24;
                * shazam =25;
                * deliveroo =26;
                * kakaotalk =27;
                * pinterest =28;
                * tumblr =29;
                * vk =30;
                * youtube=31;
                */
                  EAApp _00unknow = EAApp(false); //类型：其他社交类型
                  EAApp _01wechat = EAApp(false);
                  EAApp _02qq = EAApp(false);
                  EAApp _03facebook = EAApp(false);
                  EAApp _04twitter = EAApp(false);
                  EAApp _05messenger = EAApp(false);
                  EAApp _06hangouts = EAApp(false);
                  EAApp _07gmail = EAApp(false);
                  EAApp _08viber = EAApp(false);
                  EAApp _09snapchat = EAApp(false);
                  EAApp _10whatsApp = EAApp(false);
                  EAApp _11instagram = EAApp(false);
                  EAApp _12linkedin = EAApp(false);
                  EAApp _13line = EAApp(false);
                  EAApp _14skype = EAApp(false);
                  EAApp _15booking = EAApp(false);
                  EAApp _16airbnb = EAApp(false);
                  EAApp _17flipboard = EAApp(false);
                  EAApp _18spotify = EAApp(false);
                  EAApp _19pandora = EAApp(false);
                  EAApp _20telegram = EAApp(false);
                  EAApp _21dropbox = EAApp(false);
                  EAApp _22waze = EAApp(false);
                  EAApp _23lift = EAApp(false);
                  EAApp _24slack = EAApp(false);
                  EAApp _25shazam = EAApp(false);
                  EAApp _26deliveroo = EAApp(false);
                  EAApp _27kakaotalk = EAApp(false);
                  EAApp _28pinterest = EAApp(false);
                  EAApp _29tumblr = EAApp(false);
                  EAApp _30vk = EAApp(false);
                  EAApp _31youtube = EAApp(false);

                  EAAppPushSwitch appPushSwitch = EAAppPushSwitch();
                  appPushSwitch.list.addAll([
                    _00unknow,
                    _01wechat,
                    _02qq,
                    _03facebook,
                    _04twitter,
                    _05messenger,
                    _06hangouts,
                    _07gmail,
                    _08viber,
                    _09snapchat,
                    _10whatsApp,
                    _11instagram,
                    _12linkedin,
                    _13line,
                    _14skype,
                    _15booking,
                    _16airbnb,
                    _17flipboard,
                    _18spotify,
                    _19pandora,
                    _20telegram,
                    _21dropbox,
                    _22waze,
                    _23lift,
                    _24slack,
                    _25shazam,
                    _26deliveroo,
                    _27kakaotalk,
                    _28pinterest,
                    _29tumblr,
                    _30vk,
                    _31youtube
                  ]);
                  setWatchData(
                      kEADataInfoTypeAppMessage, appPushSwitch.toMap());
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

                  EAOTA ota1 = EAOTA("", EAFirmwareType.Apollo, "AP0.1B4.6");

                  EAOTAList otaList = EAOTAList(0, [ota1]);
                  //(info) {})
                  EASDKTool().otaUpgrade(otaList,
                      EAOTAProgressCallback((progress) {
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
