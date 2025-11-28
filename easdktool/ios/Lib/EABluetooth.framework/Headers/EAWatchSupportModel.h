//
//  EAWatchSupportModel.h
//  EABluetooth
//
//  Created by Aye on 2022/9/1.
//  File Name:44:Watch Supports【手表支持设置的功能】


#import <EABluetooth/EABaseModel.h>
#import <EABluetooth/EARespondModel.h>
#import <EABluetooth/EARequestModel.h>
NS_ASSUME_NONNULL_BEGIN



///
 #pragma  【id的值 == EADataInfoType的值】
 #pragma  【Value of id == Value of EADataInfoType】
 

/**
 * id = 44:
 * 手表支持设置的功能
 * The watch supports Settings
 */
@interface EAWatchSupportModel : EABaseModel

/// id = 26:Heart rate Warning Settings【心率警告设置】
@property(nonatomic, assign) BOOL hrWarning;

/// id = 17：Heart rate monitor【心率监测】
@property(nonatomic, assign) BOOL hrMonitoring;

/// Resting heart rate monitoring【静息心率监测】
@property(nonatomic, assign) BOOL rhrMonitoring;

/// Blood pressure measurement【血压测量】
@property(nonatomic, assign) BOOL bloodPressureMonitoring;

/// id = 51：Pressure monitoring【压力监测】
@property(nonatomic, assign) BOOL pressureMonitoring;

/// Sleep monitoring【睡眠监测】
@property(nonatomic, assign) BOOL sleepMonitoring;

///id = 20：weather set【天气设置】
@property(nonatomic, assign) BOOL weatherSettings;

/// id = 28：Raise your hand to display the screen【抬手亮屏设置】
@property(nonatomic, assign) BOOL gesturesWakeUpSettings;

@property(nonatomic, assign) BOOL vibrationModeSettings __attribute__((deprecated));

/// id = 4：Setting of Wearing Mode【佩戴方式设置】
@property(nonatomic, assign) BOOL wearingModeSettings;

/// id = 21：
@property(nonatomic, assign) BOOL incomingCallReminder;

/// id = 21：
@property(nonatomic, assign) BOOL missedCallReminder;

/// id = 21:
@property(nonatomic, assign) BOOL smsReminder;

/// id = 21:
@property(nonatomic, assign) BOOL mailReminder;

/// id = 15:Goal achievement reminder Settings【目标达成提醒设置】
@property(nonatomic, assign) BOOL goalAchievementReminder;

/// id = 22：Schedule (eating, drinking, etc.)【日程设置(吃饭喝水等)】
@property(nonatomic, assign) BOOL scheduleSetting;

/// id = 22：The alarm clock Settings【闹钟设置】
@property(nonatomic, assign) BOOL alarmSettings;

/// id = 34 ：App push (switch)【App推送(开关)】
@property(nonatomic, assign) BOOL appPushSettings;

/// id = 18：Sedentary monitoring【久坐监测】
@property(nonatomic, assign) BOOL sedentaryMonitoring;

/// id = 13： Disturb setting【勿扰设置】
@property(nonatomic, assign) BOOL disturbSetting;

/// id = 31：Primary Menu Setting【一级菜单设置】
@property(nonatomic, assign) BOOL menuSettings;

/// id = 38：Habit tracker settings【习惯设置】
@property(nonatomic, assign) BOOL habitTrackerSettings;

/// id = 38：Habit tracker color settings【习惯颜色设置】
@property(nonatomic, assign) BOOL habitTrackerColorSettings;

/// id = 11：Unit setting (Metric system, British system)【单位设置（公制、英制】
@property(nonatomic, assign) BOOL unitSettings;

/// id = 27：Basic calorie switch setting【基础卡路里开关设置】
@property(nonatomic, assign) BOOL baseCaloriesSettings;

/// id = 32：Menstrual period set【经期设置】
@property(nonatomic, assign) BOOL menstrualSetting;

/// id = 42、id = 43 ：Phone contact【通讯录】
@property(nonatomic, assign) BOOL phoneContact;

/// Support the bell【支持铃声（有喇叭）】
@property(nonatomic, assign) BOOL ringtoneSupport;

/// Is there a GPS? 【有没GPS】
@property(nonatomic, assign) BOOL gpsSetting;

/// Body temperature measurement【体温测量】
@property(nonatomic, assign) BOOL temperatureMonitoring;

/// Smart Movement【智慧运动】
@property(nonatomic, assign) BOOL smartSportsSetting;

/// id = 45： Alert event monitoring【提醒事件监测】
@property(nonatomic, assign) BOOL monitorReminder;

/// Classic Bluetooth one-button connection 【经典蓝牙一键连接功能】
@property(nonatomic, assign) BOOL btOneKeyConnect;

/// id = 20：Weather supports air quality【天气支持空气质量】
@property(nonatomic, assign) BOOL weatherAir;

/// id = 12：findWatch【查找手表】
@property(nonatomic, assign) BOOL findWatch;

/// id = 46：Support App Map Sport
@property(nonatomic, assign) BOOL supportAppMapSport;

/// id = 48:Support app startup detection (heart rate, blood oxygen, pressure and other functions)【支持app启动检测（心率、血氧、压力等功能）】
@property(nonatomic, assign) BOOL supportAppDetection;

/// id = 49：Big data can be obtained separately by type【支持大数据单独按类型获取】
@property(nonatomic, assign) BOOL supportOnlyGetBigData;

/// id = 50 Support blood oxygen monitoring switch protocol【支持血氧监测开关协议】
@property(nonatomic, assign) BOOL sleepBloodOxygenMonitorSetting;

/// id = 51：Support for pressure monitoring switch protocol【 支持压力监测开关协议】
@property(nonatomic, assign) BOOL stressMonitorSetting;

/// id = 52：Supports real-time data switching protocol【支持实时数据开关协议】
@property(nonatomic, assign) BOOL sendRealTimeDataSetting;

/// id = 22：Supports all write operations of reminding protocols and all replace operations of reminding protocols based on types【支持提醒协议全部写入操作、支持提醒协议根据类型全部替换操作】
@property(nonatomic, assign) BOOL reminderAllInAndReplaceTypeSetting;

/// id = 53：Support for vibration mode setting protocol【支持震动模式设置协议】
@property(nonatomic, assign) BOOL vibrateIntensitySetting;

/// id = 54：Support App startup motion protocol (screen motion)【支持App启动运动协议（投屏运动】
@property(nonatomic, assign) BOOL appLaunchScreenSport;


/// id = 55：Menstrual reminder【经期提醒】
@property(nonatomic, assign) BOOL menstrualReminder;

/// id = 48：Support app startup detection (heart rate【支持app启动检测（心率）】
@property(nonatomic, assign) BOOL supportAppDetectionHr;

/// id = 48：Support app startup detection (blood oxygen)【支持app启动检测（血氧）】
@property(nonatomic, assign) BOOL supportAppDetectionSqO2;

/// id = 48：Support app startup detection (Stress【支持app启动检测（压力）】
@property(nonatomic, assign) BOOL supportAppDetectionStress;

/// id=56：Remove custom (online) watch faces【删除自定义（在线）表盘协议】
@property(nonatomic, assign) BOOL wfDelete;

/// id=57：Exercise heart rate alarm【运动心率报警】
@property(nonatomic, assign) BOOL hrSportWarning;

/// id=58：Emergency contact(Sos)【紧急联系人】
@property(nonatomic, assign) BOOL contactSos;

/// Support heart rate data separation (min: normal heart rate \\ exercise heart rate)【支持心率数据分离(分:普通心率\\运动心率)】
@property(nonatomic, assign) BOOL hrDataSeparation;

/// id=3009：The pace data supports whether the mile pace option is available【配速数据支持是否为英里配速选项】
@property(nonatomic, assign) BOOL supportMilePaceData;

/// id=22：Schedule reminders: Whether user-defined reminders are supported【日程提醒: 是否支持 用户自定义提醒】
@property(nonatomic, assign) BOOL scheduleSettingUser;

/// id=59：Classic Bluetooth BT Control；【功能与协议: 是否支持 经典蓝牙BT控制 】
@property(nonatomic, assign) BOOL supportBtOps;

/// id=60：User-defined reply messages【 是否支持 用户自定义回复消息】
@property(nonatomic, assign) BOOL supportUserMsg;

/// id=32：support OvulationDay【是否支持  经期排卵日】
@property(nonatomic, assign) BOOL supportOvulationDay;

/// id = 51：Does it support pressure monitoring time intervals【是否支持 压力监测 时间间隔】
@property(nonatomic, assign) BOOL stressMonitorInterval;

/// id = 34：The message push switch supports up to 128 bytes (otherwise 64 bytes).【消息推送开关支持最多128个字节（否则64个）】
@property(nonatomic, assign) BOOL appSwNum128Bytes;

/// id = 61：Does not support the bin of firmware log【否支持 固件log的bin】
@property(nonatomic, assign) BOOL supportWatchLog;

/// id = 14：World Time【世界时间】
@property(nonatomic, assign) BOOL supportHomeZone;

/// id = 64：Stocks【股票】
@property(nonatomic, assign) BOOL supportStocks;

/// id = 65:Lock the screen away from your wrist【离腕锁屏】
@property(nonatomic, assign) BOOL supportPwd;

/// id = 66:Automatic body surface temperature monitoring【自动体表温度监测】
@property(nonatomic, assign) BOOL supportTemplateBody;

///id = 67:Automatic environmental temperature monitoring【自动环境温度监测】
@property(nonatomic, assign) BOOL supportTemplateEnvironment;


/// id = 68:The type of watch that supports activities initiated by the APP【手表支持APP发起运动的类型 】
@property(nonatomic, assign) BOOL supportAppSportSupport;

/// Does the watch support OTA music files【手表是否支持OTA音乐文件】
@property(nonatomic, assign) BOOL musicOtaSupport;

/// id = 69: The maximum and minimum heart rate values of the watch【手表心率最高值及最小值】
@property(nonatomic, assign) BOOL hrMaxMinShowSupport;

/// id = 70: muslim pilgrimage time【muslim朝拜时间】
@property(nonatomic, assign) BOOL muslimDirTimeSupport;

/// id = 71: muslim Calendar【muslim日历】
@property(nonatomic, assign) BOOL muslimTimestampSupport;


///id = 73、74、75: Support for custom dial protocols【自定义表盘协议支持】
@property(nonatomic, assign) BOOL cusWatchfaceSupport;

/// id = 76、77:AI text distribution protocol support【AI文字下发协议支持】
@property(nonatomic, assign) BOOL aiTxtSupport;

/// id = 78, 79: Heart rate PPG data switch support【心率PPG数据开关支持】
@property(nonatomic, assign) BOOL hrPpgSwSupport;


/// id = 80: Global configuration of custom dial【自定义表盘全局配置】
@property(nonatomic, assign) BOOL watchfaceGlobalSettingSupport;


/// id = 72: Support for longitude and latitude information of mobile phones【手机的经纬度信息支持】
@property(nonatomic, assign) BOOL appGpsLocationSupport;


/// id = 81: External dial information of JL707 platform【JL707平台外置表盘信息】
@property(nonatomic, assign) BOOL jl707ExDailInfoSupport;

/// id = 82:JL707 platform Custom dial information【JL707平台自定义表盘信息】
@property(nonatomic, assign) BOOL jl707CusDailInfoSupport;


@end

NS_ASSUME_NONNULL_END
