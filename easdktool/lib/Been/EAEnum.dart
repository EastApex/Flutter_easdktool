// ignore_for_file: file_names, constant_identifier_names, non_constant_identifier_names

part of easdktool.been;

/// MARK: -  回应
enum EARespondCodeType {
  /// 【成功】
  Success,

  /// 【失败】
  Fail,

  /// 【超过支持的最大数量】
  MemFull,

  /// 【时间重复】
  TimeConflict,
}

///【绑定状态】
enum EABindingType {
  bound, // 【已绑定】
  unbound // 【未绑定】
}

///Sex【性别】
enum EAPersonSex {
  male, //male 【男性】
  female //female【女性】
}

/// 肤色
enum EASkinColor {
  skinWhite, // 【白皮肤】
  skinWhiteYellow, // 【白偏黄】
  skinYellow, // 【黄皮肤】
  skinYellowBlack, // 【黄偏黑】
  skinBalck //【黑皮肤】
}

///Wearing a way【佩戴方式】
enum EAPersonHand {
  left, //【左手】
  right //【右手】
}

/// Charging status【充电状态】
enum EABatInfoStatus {
  normal, // normal 【正常状态】
  charging // Charging【充电中】
}

/// Language type【语言类型】
enum EALanguageType {
  default0, //default english
  english, //english
  chineseSimplifid, //Chinese Simplified
  chineseTraditional, //chinese traditional
  korean, //Korean
  thai, //Thai
  japanese, //Japanese
  spanish, //Spanish
  francais, //French
  deutsch, //German
  italiano, //Italian
  polski, //Polski
  portuguese, //Portuguese
  russian, //Russian
  dutch, //Nederlands
  arabic, //arabic
  greek, //Greek
  hebrew, //Hebrew
  swedish, //Swedish
  unknown
}

///System of units【单位制度】
enum EAUnifiedUnitType {
  metric, //Metric(m,km)
  british //Imperial(feet,miles)
}

/// Distance unit type 【距离单位类型】
enum EADistanceUnitType {
  Kilometre, // 【公里】

  Mile, // 【英里】
}

/// Unit type of weight 【重量单位类型】
enum EAWeightUnitType {
  Kilogram, // 千克
  Pound, // 英镑
}

/// Time formats 【时间制式】
enum EATimeHourType {
  /// 12小时制
  hour12,

  /// 24小时制
  hour24,
}

/// TimeZone 【时区】
enum EATimeZone {
  /// 0时区
  zero,

  /// 东时区
  east,

  /// 西时区
  west,
}

/// MARK: - 天气类型
enum EAWeatherType {
  /// 晴
  clearDay,

  /// 多云
  cloudy,

  /// 阴
  gloomy,

  /// 阵雨
  drizzle,

  /// 强阵雨
  moderateRain,

  /// 雷阵雨
  thunderstorm,

  /// 大雨
  typeHeavyRain,

  /// 冻雨
  sleet,

  /// 小雪
  lightSnow,

  /// 中雪
  moderateSnow,

  /// 大雪
  heavySnow,
}

///Air quality type 【空气质量类型】
enum EAWeatherAirType {
  /// 优
  excellent,

  /// 良
  good,

  /// 差
  bad,
}

/// Uv intensity type【紫外线强度类型】
enum EAWeatherRaysType {
  /// 弱
  Weak,

  /// 中等
  Medium,

  /// 强
  Strong,

  /// 很强
  VeryStrong,

  /// 超级强
  SuperStrong,
}

/// The moon type 【月相类型】
enum EAWeatherMoonType {
  /// 新月
  NewMoon,

  /// 峨眉月
  WaxingCrescentMoon,

  /// 上弦月
  QuarterMoon,

  /// 盈半月
  HalfMoon1,

  /// 盈凸月
  WaxingGibbousMoon,

  /// 满月
  FullMoon,

  /// 亏凸月
  WaningGibbousMoon,

  /// 亏半月
  HalfMoon2,

  /// 下弦月
  LastQuarterMoon,

  /// 残月
  WaningCrescentMoon,
}

/// Temperature of the unit【温度单位】
enum EAWeatherUnit {
  /// 摄氏度
  Centigrade,

  /// 华氏度
  Fahrenheit,
}

/// Type of reminder mode【提醒方式】
enum EARemindActionType {
  /// 不动作
  NoAction,

  /// Single long shock【单次长震】
  OneLongVibration,

  /// A single short【单次短震】
  OneShortVibration,

  /// Two long shock【两次长震】
  TwoLongVibration,

  /// Two short short【两次短震】
  TwoShortVibration,

  /// Has been long shock【一直长震】
  LongVibration,

  /// Long and short earthquake【一直长短震】
  LongShortVibration,

  /// A single ring【单次响铃】
  OneRing,

  /// Ring the bell twice【两次响铃】
  TwoRing,

  /// Ring the bell【一直响铃】
  Ring,

  /// One vibration + ring【一次震动+响铃】
  OneVibrationRing,

  /// Keep vibrating + ring the bell【一直震动+响铃】
  VibrationRing,
}

/// Alert event type【提醒事件类型】
/// Alarm is Alarm
/// Sleep、Sport、Drink、Medicine、Meeting、User is Reminder
enum EAReminderEventType {
  /// 闹钟
  Alarm,

  /// 睡觉
  Sleep,

  /// 运动
  Sport,

  /// Drink water  喝水
  Drink,

  /// 吃药
  Medicine,

  /// 会议
  Meeting,

  /// User defined【用户自定义】
  User,

  /// 洗手
  WashHand,
}

///Alert event action type【提醒事件操作】
enum EAReminderEventOps {
  /// add this【新增】
  Add,

  /// Edit this【编辑】
  Edit,

  /// Delete this【删除此条】
  Del,

  /// Delete all reminders【删除全部提醒】
  DelRemind,

  /// Delete all alarm【删除全部闹钟】
  DelAlarm,

  /// Delete all reminders & alarm【删除全部闹钟及提醒】
  DelRemindAlarm,
}

/// Raise the screen type【抬手亮屏】
enum EAGesturesBrightType {
  /// 【关闭】
  Close,

  /// 【全天开启】
  AllDay,

  /// Custom time【选择时间段开启】
  SelectTime,
}

/// Vibration mode【振动模式】
enum EAVibrateIntensityType {
  /// 弱
  Light,

  /// 正常
  Medium,

  /// 强
  Strong,
}

/// Primary menu type【一级菜单类型】
enum EAFirstLeverType {
  /// 无
  PageNull,

  /// 心率
  PageHeartRate,

  /// 压力
  PagePressure,

  /// 天气
  PageWeather,

  /// 音乐
  PageMusic,

  /// 呼吸
  PageBreath,

  /// 睡眠
  PageSleep,

  /// 生理周期
  PageMenstrualCycle,
}

///Custom icon types【习惯图标类型】
enum EAHabitTrackerIconType {
  Study01,
  Sleep02,
  Study03,
  Chores04,
  Havefun05,
  Drink06,
  Sun07,
  Teeth08,
  Calendar09,
  Piano10,
  Fruit11,
  Medicine12,
  Draw13,
  Target14,
  Dog15,
  Exercise16,
  Bed17,
  Tidyup18,
}

/// MARK: - 习惯事件操作
enum EAHabitTrackerOps {
  /// 新增
  Add,

  /// 编辑
  Edit,

  /// 删除此条
  Del,

  /// 删除全部
  DelAll,
}

/// MARK: - 习惯追踪flag
enum EAHabitTrackerFlag {
  /// 初始态
  EAHabitTrackerFlagInitial,

  /// 进行中
  EAHabitTrackerFlagInProgress,

  /// 已完成
  EAHabitTrackerFlagCompleted,

  /// 已取消
  EAHabitTrackerFlagCancel,
}

/// Operating Phone Type 【Operating Phone Type】
enum EAOpePhoneType {
  /// 寻找手机
  SearchPhone,

  /// 停止寻找手机(固件需求)
  StopSearchPhone,

  /// 连接相机
  ConnectTheCamera,

  /// 开启拍照
  StartTakingPictures,

  /// 停止拍照
  StopTakingPictures,

  /// 请求最新天气信息
  RequestTheLatestWeather,

  /// 请求最新agps星历数据
  RequestTheAgps,

  /// 请求最新经期数据
  RequestTheMenstrualCycle,

  /// 8803大数据传输完成
  Big8803DataUpdateFinish,

  /// mic录音准备
  MicRecordReady,

  /// mic录音结束
  MicRecordStop,

  /// 停止寻找手表(固件需求)
  StopSearchWatch,
}

/// MARK: - 操作设备类型
enum EAOperationWatchType {
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

  /// 停止相机
  StopCamera,

  /// 使能设备操作IOS手机弹出配对框
  ShowiPhonePairingAlert,
}

///【OTA 固件类型】
/// Firmware type
enum EAFirmwareType { Apollo, Res, Tp, Hr }

/// 手表连接状态
/// Watch Connect State Type
enum EAConnectStateType {
  /// 断开连接
  EAConnectStateTypeUnconnected,

  /// 已连接
  EAConnectStateTypeConnected,

  /// 连接中
  EAConnectStateTypeConnecting,
}

enum EAPushMessageActionType {
  ///新增(来电属于新增)
  add,

  ///编辑此条
  edit,

  ///删除此条(来电挂断属于删除此条)
  del,

  ///删除此类型全部
  del_type,

  ///删除全部社交
  del_all,
}

enum EAPushMessageType {
  incomingcall,
  missedcall,
  social,
  schedule,
  email,
  sms,
  unknow,
  wechat,
  qq,
  facebook,
  twitter,
  messenger,
  hangouts,
  gmail,
  viber,
  snapchat,
  whatsApp,
  instagram,
  linkedin,
  line,
  skype,
  booking,
  airbnb,
  flipboard,
  spotify,
  pandora,
  telegram,
  dropbox,
  waze,
  lift,
  slack,
  shazam,
  deliveroo,
  kakaotalk,
  pinterest,
  tumblr,
  vk,
  youtube,
  outlook,
  amazon,
  discord,
  github,
  google_maps,
  news_break,
  reddit,
  teams,
  tiktok,
  twitch,
  uber_eats,
}

enum EAMonitorReminderType {
  drink,
  washHands,
  // sedentary,
}
