//
//  EAEnum.h
//  EAProductDemo
//
//  Created by Aye on 2021/3/18.
//

#ifndef EAEnumh
#define EAEnumh


/// 数据类型
typedef NS_ENUM(NSUInteger, EADataInfoType) {
    
    EADataInfoTypeRespond = 1,
    EADataInfoTypeRequest = 2,
    /** 手表 */
    EADataInfoTypeWatch = 3,
    
    /** 用户 */
    EADataInfoTypeUser = 4,
    
    /** 同步时间 */
    EADataInfoTypeSyncTime = 5,
    
    /** 绑定 */
    EADataInfoTypeBinding = 6,
    
    /** 屏幕亮度 */
    EADataInfoTypeBlacklight = 7,
    
    /** 屏幕自动灭屏时间 */
    EADataInfoTypeBlacklightTimeout = 8,
    
    /** 设备电量信息 */
    EADataInfoTypeBattery = 9,
    
    /** 设备语言信息 */
    EADataInfoTypeLanguage = 10,
    
    /** 统一设备单位 */
    EADataInfoTypeUnifiedUnit = 11,
    
    /** 设备操作 */
    EADataInfoTypeDeviceOps = 12,
    
    /** 免打扰时间段 */
    EADataInfoTypeNotDisturb = 13,
    
    /** 家乡时区设置 */
    EADataInfoTypeHomeTimeZone = 14,
    
    /** 日常目标值设置 */
    EADataInfoTypeDailyGoal = 15,
    
    /** 自动睡眠监测 */
    EADataInfoTypeAutoCheckSleep = 16,
    
    /** 自动心率监测 */
    EADataInfoTypeAutoCheckHeartRate = 17,
    
    /** 久坐监测 */
    EADataInfoTypeAutoCheckSedentariness = 18,
    
    /** 通用天气 */
    EADataInfoTypeWeather = 20,
    
    /** 社交提醒开关 */
    EADataInfoTypeSocialSwitch = 21,
    
    /** 提醒 */
    EADataInfoTypeReminder = 22,
    
    /** 提醒回应 */
    EADataInfoTypeReminderRespond = 23,
    
    /** 距离单位 */
    EADataInfoTypeDistanceUnit = 24,
    
    /** 重量单位 */
    EADataInfoTypeWeightUnit = 25,
    
    /** 心率报警门限 */
    EADataInfoTypeHeartRateWaringSetting = 26,
    
    /** 基础卡路里开关 */
    EADataInfoTypeCaloriesSetting = 27,
    
    /** 抬手亮屏开关 */
    EADataInfoTypeGesturesSetting = 28,
    
    /** 大数据获取命令 */
    EADataInfoTypeGetBigData = 29,
    
    /** 设置组合命令 */
    EADataInfoTypeCombination = 30,
    
    /** 一级菜单设置命令 */
    EADataInfoTypeHomePage = 31,
    
    /** 经期命令 */
    EADataInfoTypeMenstrual = 32,
    
    /** 表盘命令 */
    EADataInfoTypeDialPlate = 33,
    
    /** 消息推送开关 */
    EADataInfoTypeAppMessage = 34,
    
    /** 血压校准值 （老人表）*/
    EADataInfoTypeBloodPressure = 36,
    
    /** 自动监测 心率 血氧 血压 （老人表） */
    EADataInfoTypeAutoMonitor = 37,
    
    /** 习惯追踪 */
    EADataInfoTypeHabitTracker = 38,
    
    /**习惯追踪回应 */
    EADataInfoTypeHabitTrackerRespond = 39,
    
    /**当前运动显示值 */
    EADataInfoTypeSportShowData = 40,
    
    /**获取蓝牙配对状态 */
    EADataInfoTypeBlePairState = 41,
    
    /**通讯录 */
    EADataInfoTypeTelephoneBook = 42,
    
    /** 操作手机命令 */
    EADataInfoTypePhoneOps = 2001,
    
    /** MTU */
    EADataInfoTypeMTU = 2006,
    
    /** 大数据步数 */
    EADataInfoTypeStepData = 3001,
    
    /** 大数据睡眠 */
    EADataInfoTypeSleepData = 3002,
    
    /** 大数据心率  */
    EADataInfoTypeHeartRateData = 3003,
    
    /** 大数据GPS */
    EADataInfoTypeGPSData = 3004,
    
    /** 大数据多运动 */
    EADataInfoTypeSportsData = 3005,
    
    /** 大数据血氧 */
    EADataInfoTypeBloodOxygenData = 3006,
    
    /** 大数据压力 */
    EADataInfoTypeStressData = 3007,
    
    /** 大数据步频 */
    EADataInfoTypeStepFreqData = 3008,
    
    /** 大数据配速 */
    EADataInfoTypeStepPaceData = 3009,
    
    /** 大数据静息心率 */
    EADataInfoTypeRestingHeartRateData = 3010,
    
    /** 习惯数据 */
    EADataInfoTypeHabitTrackerData = 3011,
    

    
    /** OTA命令 */
    EADataInfoTypeOTARequest = 9001,
    
    /** OTA命令回应 */
    EADataInfoTypeOTARespond = 9000,
};

/// MARK: -  通道类型类型
typedef NS_ENUM(NSUInteger, EACharacteristicType) {
    
    /** 小数据通道，上位机*/
    EACharacteristicTypeSmall = 0, // 8801
    
    /** 小数据通道，固件*/
    EACharacteristicTypeSmall2,  // 8802
    
    /** 大数据通道 */
    EACharacteristicTypeBig,  // 8803
    
    /** OTA通道 */
    EACharacteristicTypeOTAMessage, // 9901
    
    /** OTA通道 */
    EACharacteristicTypeOTAData, // 9902
    
};


/// MARK: - 手表连接状态
typedef NS_ENUM(NSUInteger,EAConnectStateType) {
    
    /** 断开连接 */
    EAConnectStateTypeUnconnected = 0,
    
    /** 已连接 */
    EAConnectStateTypeConnected = 1,
    
    /** 连接中 */
    EAConnectStateTypeConnecting = 2,
};


/// MARK: -  绑定类型
typedef NS_ENUM(NSUInteger, EARespondCodeType) {
    
    /** 成功 */
    EARespondCodeTypeSuccess = 0,
    
    /** 失败 */
    EARespondCodeTypeFail,
    
    /** 超过支持的最大数量 */
    EARespondCodeTypeMemFull,
    
    /** 时间重复 */
    EARespondCodeTypeTimeConflict,
};

/// MARK: -  绑定类型
typedef NS_ENUM(NSUInteger, EABindingType) {
    
    /** 已绑定 */
    EABindingTypeBound = 0,
    /** 未绑定 */
    EABindingTypeUnBound,
};


/// MARK: -  性别类型
typedef NS_ENUM(NSUInteger, EASexType) {
    
    /** 男士 */
    EASexTypeMale = 0,
    /** 女士 */
    EASexTypeFemale,
};

/// MARK: -  穿戴方式
typedef NS_ENUM(NSUInteger, EAWearWayType) {
    
    /** 左手戴 */
    EAWearWayTypeLeftHand = 0,
    /** 右手戴 */
    EAWearWayTypeRightHand,
};

/// MARK: -  肤色
typedef NS_ENUM(NSUInteger, EASkinColorType) {
    
    /** 白 */
    EASkinColorTypeWhite = 0,
    
    /** 白偏黄 */
    EASkinColorTypeWhiteYellow = 1,
    
    /** 黄 */
    EASkinColorTypeYellow = 2,
    
    /** 黄偏黑 */
    EASkinColorTypeYellowBlack = 3,
    
    /** 黑 */
    EASkinColorTypeBalck = 4,
};

/// MARK: -  时间制式
typedef NS_ENUM(NSUInteger, EATimeHourType) {
    
    /** 12小时制 */
    EATimeHourTypeHour12 = 0,
    /** 24小时制 */
    EATimeHourTypeHour24,
};

/// MARK: -  时区
typedef NS_ENUM(NSUInteger, EATimeZone) {
    
    /** 0时区 */
    EATimeZoneZero = 0,
    /** 东时区 */
    EATimeZoneEast,
    /** 西时区 */
    EATimeZoneWest,
};

/// MARK: - 同步模式：正常、同步至机芯
typedef NS_ENUM(NSUInteger, EASyncType) {
    
    /** 正常 */
    EASyncTypeNormal = 0,
    /** 同步至机芯 */
    EASyncTypeWatch,
};

/// MARK: - 绑定操作
typedef NS_ENUM(NSUInteger,EABindingOpsType) {
    
    /** 正常绑定开始 */
    EABindingOpsTypeNormalBegin = 0,
    
    /** 二维码绑定开始 */
    EABindingOpsTypeQrCodeBegin = 1,
    
    /** 绑定结束 */
    EABindingOpsTypeEnd = 2,
};


/// MARK: - 电池状态
typedef NS_ENUM(NSUInteger,EABatteryStatus) {
    
    /** 正常 */
    EABatteryStatusNormal = 0,
    
    /** 充电中 */
    EABatteryStatusInCharging = 1,
};


/// MARK: - 语言类型
typedef NS_ENUM(NSUInteger,EALanguageType) {
    
    /** 默认 */
    EALanguageTypeDefault = 0,
    
    /** 英文 */
    EALanguageTypeEnglish = 1,
    
    /** 中文简体 */
    EALanguageTypeChineseSimplifid = 2,
    
    /** 中文繁体 */
    EALanguageTypeChineseTraditional = 3,
    
    /** 韩文 */
    EALanguageTypeKorean = 4,
    
    /** 泰文 */
    EALanguageTypeThai = 5,
    
    /** 日文 */
    EALanguageTypeJapanese = 6,
    
    /** 西班牙文 */
    EALanguageTypeSpanish = 7,
    
    /** 法文 */
    EALanguageTypeFrancais = 8,
    
    /** 德文 */
    EALanguageTypeDeutsch = 9,
    
    /** 意大利文 */
    EALanguageTypeItaliano = 10,
    
    /** 波兰文 */
    EALanguageTypePolski = 11,
    
    /** 葡萄牙文 */
    EALanguageTypePortuguese = 12,
    
    /** 俄文 */
    EALanguageTypeRussian = 13,
    
    /** 荷兰文 */
    EALanguageTypeDutch = 14,
    
    /** 阿拉伯文 */
    EALanguageTypeArabic = 15,
    
    /** 希腊文 */
    EALanguageTypeGreek = 16,
    
    /** 希伯来文 */
    EALanguageTypeHebrew = 17,
    
    /** 瑞典文 */
    EALanguageTypeSwedish = 18,
    
};


/// MARK: - 统一单位
typedef NS_ENUM(NSUInteger,EAUnifiedUnit) {
    
    /** 公制 */
    EALengthUnitMetric = 0,
    
    /** 英制 */
    EALengthUnitBritish = 1,
};

/// MARK: - 操作设备类型
typedef NS_ENUM(NSUInteger,EADeviceOpsType) {
    
    /** 恢复出厂设置 */
    EADeviceOpsTypeRestoreFactory = 0,
    
    /** 重启设备 */
    EADeviceOpsTypeReset = 1,
    
    /** 设备关机 */
    EADeviceOpsTypePowerOff = 2,
    
    /** 断开蓝牙 */
    EADeviceOpsTypeDisconnectBle = 3,
    
    /** 进入飞行模式 */
    EADeviceOpsTypeEnteringFlightMode = 4,
    
    /** 点亮屏幕 */
    EADeviceOpsTypeLightUpTheScreen = 5,
    
    /** 熄灭屏幕 */
    EADeviceOpsTypeTurnOffTheScreen = 6,
    
    /** 停止寻找手机(上位机需求) */
    EADeviceOpsTypeStopSearchPhone = 7,
    
    /** 工厂模式专用：进入工厂测试模式 */
    EADeviceOpsTypeEnterFactoryTestMode = 8,

    /** 工厂模式专用：退出工厂测试模式 */
    EADeviceOpsTypeExitFactoryTestMode = 9,
    
    /** 寻找手表 */
    EADeviceOpsTypeStartSearchWatch = 10,
    
    /** 停止寻找手表 */
    EADeviceOpsTypeStopSearchWatch = 11,
    
    /** 停止相机 */
    EADeviceOpsTypeStopCamera = 12,
    
    /** 使能设备操作IOS手机弹出配对框 */
    EADeviceOpsTypeShowiPhonePairingAlert = 13,
};


/// MARK: - 设备操作状态
typedef NS_ENUM(NSUInteger,EADeviceOpsStatus) {
    
    /** 执行 */
    EADeviceOpsStatusExecute = 0,
    
    /** 执行成功 */
    EADeviceOpsStatusSuccess = 1,
    
    /** 执行失败 */
    EADeviceOpsStatusFail = 2,
};


/// MARK: - 温度单位
typedef NS_ENUM(NSUInteger,EAWeatherUnit) {
    
    /** 摄氏度 */
    EAWeatherUnitCentigrade = 0,
    
    /** 华氏度 */
    EAWeatherUnitFahrenheit = 1,
};

/// MARK: - 天气类型
typedef NS_ENUM(NSUInteger,EAWeatherType) {
    
    /** 晴 （100 102 150） */
    EAWeatherTypeClear = 0,
    
    /** 多云（101 103 153 502~515） */
    EAWeatherTypeCloudy = 1,
    
    /** 阴（104 154 500 501 999） */
    EAWeatherTypeGloomy = 2,
    
    /** 阵雨（300 305 309 350） */
    EAWeatherTypeDrizzle = 3,
    
    /** 强阵雨（301 306 314 399 351） */
    EAWeatherTypeModerateRain = 4,
    
    /** 雷阵雨（302 303 304） */
    EAWeatherTypeThunderstorm = 5,
    
    /** 大雨（307 308 310 311 312 315 316 317 318） */
    EAWeatherTypeHeavyRain = 6,
    
    /** 冻雨（313 404 405 406 456） */
    EAWeatherTypeSleet = 7,
    
    /** 小雪（400 407 457） */
    EAWeatherTypeLightSnow = 8,
    
    /** 中雪（401 408 499） */
    EAWeatherTypeModerateSnow = 9,
    
    /** 大雪（402 403 409 410） */
    EAWeatherTypeHeavySnow = 10,
};

/// MARK: - 空气质量类型
typedef NS_ENUM(NSUInteger,EAWeatherAirType) {
    
    /** 优 */
    EAWeatherAirTypeExcellent = 0,
    
    /** 良 */
    EAWeatherAirTypeGood = 1,
    
    /** 差 */
    EAWeatherAirTypeBad = 2,
};

/// MARK: - 紫外线强度
typedef NS_ENUM(NSUInteger,EAWeatherRaysType) {
    
    /** 弱 */
    EAWeatherRaysTypeWeak = 0,
    
    /** 中等 */
    EAWeatherRaysTypeMedium = 1,
    
    /** 强 */
    EAWeatherRaysTypeStrong = 2,
    
    /** 很强 */
    EAWeatherRaysTypeVeryStrong = 3,
    
    /** 超级强 */
    EAWeatherRaysTypeSuperStrong = 4,
};

/// MARK: - 月相类型
typedef NS_ENUM(NSUInteger,EAWeatherMoonType) {
    
    /** 新月 */
    EAWeatherMoonTypeNewMoon = 0,
    
    /** 峨眉月 */
    EAWeatherMoonTypeWaxingCrescentMoon = 1,
    
    /** 上弦月 */
    EAWeatherMoonTypeQuarterMoon = 2,
    
    /** 盈半月 */
    EAWeatherMoonTypeHalfMoon1 = 3,
    
    /** 盈凸月 */
    EAWeatherMoonTypeWaxingGibbousMoon = 4,
    
    /** 满月 */
    EAWeatherMoonTypeFullMoon = 5,
    
    /** 亏凸月 */
    EAWeatherMoonTypeWaningGibbousMoon = 6,
    
    /** 亏半月 */
    EAWeatherMoonTypeHalfMoon2 = 7,
    
    /** 下弦月 */
    EAWeatherMoonTypeLastQuarterMoon = 8,
    
    /** 残月 */
    EAWeatherMoonTypeWaningCrescentMoon = 9,
};

/// MARK: - 提醒方式
typedef NS_ENUM(NSUInteger,EARemindActionType) {
    
    /** 不动作 */
    EARemindActionTypeNoAction = 0,
    
    /** 单次长震 */
    EARemindActionTypeOneLongVibration = 1,
    
    /** 单次短震 */
    EARemindActionTypeOneShortVibration = 2,
    
    /** 两次长震 */
    EARemindActionTypeTwoLongVibration = 3,
    
    /** 两次短震 */
    EARemindActionTypeTwoShortVibration = 4,
    
    /** 一直长震 */
    EARemindActionTypeLongVibration = 5,
    
    /** 一直长短震 */
    EARemindActionTypeLongShortVibration = 6,
    
    /** 单次响铃 */
    EARemindActionTypeOneRing = 7,
    
    /** 两次响铃 */
    EARemindActionTypeTwoRing = 8,
    
    /** 一直响铃 */
    EARemindActionTypeRing = 9,
    
    /** 一次震动+响铃 */
    EARemindActionTypeOneVibrationRing = 10,
    
    /** 一直震动+响铃 */
    EARemindActionTypeVibrationRing = 11,
};


/// MARK: - 提醒事件类型
typedef NS_ENUM(NSUInteger,EAReminderEventType) {
    
    /** 闹钟 */
    EAReminderEventTypeAlarm = 0,
    
    /** 睡觉 */
    EAReminderEventTypeSleep = 1,
    
    /** 运动 */
    EAReminderEventTypeSport = 2,
    
    /** 喝水 */
    EAReminderEventTypeDrink = 3,
    
    /** 吃药 */
    EAReminderEventTypeMedicine = 4,
    
    /** 会议 */
    EAReminderEventTypeMeeting = 5,
    
    /** 用户自定义 */
    EAReminderEventTypeUser = 6,
};


/// MARK: - 提醒事件操作
typedef NS_ENUM(NSUInteger,EAReminderEventOps) {
    
    /** 操作：新增 */
    EAReminderEventOpsAdd = 0,
    
    /** 操作：编辑 */
    EAReminderEventOpsEdit = 1,
    
    /** 操作：删除此条 */
    EAReminderEventOpsDel = 2,
    
    /** 操作：删除全部提醒 */
    EAReminderEventOpsDelRemind = 3,
    
    /** 操作：删除全部闹钟 */
    EAReminderEventOpsDelAlarm = 4,
    
    /** 操作：删除全部闹钟及提醒 */
    EAReminderEventOpsDelRemindAlarm = 5,
};


/// MARK: - 距离单位类型
typedef NS_ENUM(NSUInteger,EADistanceUnit) {
    
    /** 公里 */
    EADistanceUnitKilometre = 0,
    
    /** 英里 */
    EADistanceUnitMile = 1,
};

/// MARK: - 体重单位类型
typedef NS_ENUM(NSUInteger,EAWeightUnit) {
    
    /** 千克 */
    EAWeightUnitKilogram = 0,
    
    /** 英镑 */
    EAWeightUnitPound = 1,
};

/// MARK: - 操作手机类型
typedef NS_ENUM(NSUInteger,EAPhoneOps) {
    
    /** 寻找手机 */
    EAPhoneOpsSearchPhone = 0,
    
    /** 停止寻找手机(固件需求) */
    EAPhoneOpsStopSearchPhone = 1,
    
    /** 连接相机 */
    EAPhoneOpsConnectTheCamera = 2,
    
    /** 开启拍照 */
    EAPhoneOpsStartTakingPictures = 3,
    
    /** 停止拍照 */
    EAPhoneOpsStopTakingPictures = 4,
    
    /** 请求最新天气信息 */
    EAPhoneOpsRequestTheLatestWeather = 5,
    
    /** 请求最新agps星历数据 */
    EAPhoneOpsRequestTheAgps = 6,
    
    /** 请求最新经期数据 */
    EAPhoneOpsRequestTheMenstrualCycle = 7,
    
    /** 8803大数据传输完成 */
    EAPhoneOpsBig8803DataUpdateFinish = 8,
    
    /** mic录音准备 lin */
    EAPhoneOpsMicRecordReady = 9,
    
    /** mic录音结束 lin */
    EAPhoneOpsMicRecordStop = 10,
    
    /** 停止寻找手表(固件需求) */
    EAPhoneOpsStopSearchWatch = 11,
};

/// MARK: - 操作手机状态
typedef NS_ENUM(NSUInteger,EAPhoneOpsStatus) {
    
    /** 执行 */
    EAPhoneOpsStatusExecute = 0,
    
    /** 执行成功 */
    EAPhoneOpsStatusSuccess = 1,
    
    /** 执行失败 */
    EAPhoneOpsStatusFail = 2,
};


/// MARK: - 上传状态
typedef NS_ENUM(NSUInteger,EAUploadRespondStatus) {
    
    /** 起始包 */
    EAUploadRespondStatusBegin = 0,
    
    /** 中间包 */
    EAUploadRespondStatusContinue = 1,
    
    /** 结束包 */
    EAUploadRespondStatusEnd = 2,
    
    /** 开始即结束包 */
    EAUploadRespondStatusBeginEnd = 3,
};


/// MARK: - 睡眠状态
typedef NS_ENUM(NSUInteger,EASleepNode) {
    
    /** 活动状态 */
    EASleepNodeActivity = 0,
    
    /** 进入睡眠 (!!!) */
    EASleepNodeEnter = 1,
    
    /** 睡眠中途醒来 */
    EASleepNodeWake = 2,
    
    /** 快速眼动 */
    EASleepNodeRem = 3,
    
    /** 浅睡 */
    EASleepNodeLight = 4,
    
    /** 深睡 */
    EASleepNodeDeep = 5,
    
    /** 退出睡眠(!!!) */
    EASleepNodeQuit = 6,
    
    /** 未知 */
    EASleepNodeUnknown = 7,
    
    /** 睡眠摘要 */
    EASleepNodeSummary = 8,
};

/// MARK: - 运动类型
typedef NS_ENUM(NSUInteger,EASportType) {
    
    /** 日常运动 */
    EASportTypeDaily = 0,

    /** 户外步行 */
    EASportTypeOurdoorWalking = 1,

    /** 户外跑步 */
    EASportTypeOurdoorRunning = 2,

    /** 户外徒步 */
    EASportTypeOurdoorOnFoot = 3,

    /** 户外登山 */
    EASportTypeOurdoorOnMountaineering = 4,

    /** 户外越野跑 */
    EASportTypeOurdoorTrailRunning = 5,

    /** 户外单车 */
    EASportTypeOurdoorCycling = 6,

    /** 户外游泳 */
    EASportTypeOutdoorSwimming = 7,

    /** 室内步行 */
    EASportTypeIndoorWalking = 8,

    /** 室内跑步 */
    EASportTypeIndoorRunning = 9,

    /** 室内锻炼 */
    EASportTypeIndoorExercise = 10,

    /** 室内单车 */
    EASportTypeIndoorCycling = 11,

    /** 椭圆机 */
    EASportTypeElliptical = 12,

    /** 瑜伽 */
    EASportTypeYoga = 13,

    /** 划船机 */
    EASportTypeRowing = 14,

    /** 室内游泳 */
    EASportTypeIndoorSwimming = 15,

    /** 攀岩 */
    EASportTypeOdRock = 16,

    /** 滑板 */
    EASportTypeOdSkate = 17,

    /** 轮滑 */
    EASportTypeOdRoller = 18,

    /** 跑酷 */
    EASportTypeOdParkour = 19,

    /** 跳伞 */
    EASportTypeOdParachute = 20,

    /** HIIT */
    EASportTypeTrainHit = 21,

    /** 举重 */
    EASportTypeTrainWeight = 22,

    /** 平板支撑 */
    EASportTypeTrainPlank = 23,

    /** 开合跳 */
    EASportTypeTrainJumping = 24,

    /** 爬楼机 */
    EASportTypeTrainStair = 25,

    /** 核心训练 */
    EASportTypeTrainCore = 26,

    /** 柔韧训练 */
    EASportTypeTrainFlex = 27,

    /** 普拉提 */
    EASportTypeTrainPilates = 28,

    /** 拉伸 */
    EASportTypeTrainStretch = 29,

    /** 力量训练 */
    EASportTypeTrainStrength = 30,

    /** 交叉训练 */
    EASportTypeTrainCross = 31,

    /** 哑铃训练 */
    EASportTypeTrainDumbbell = 32,

    /** 硬拉 */
    EASportTypeTrainDeadlift = 33,

    /** 仰卧起坐 */
    EASportTypeTrainSit = 34,

    /** 功能性训练 */
    EASportTypeTrainFuncition = 35,

    /** 上肢训练 */
    EASportTypeTrainUpper = 36,

    /** 下肢训练 */
    EASportTypeTrainLower = 37,

    /** 腹肌训练 */
    EASportTypeTrainAbs = 38,

    /** 背部训练 */
    EASportTypeTrainBack = 39,

    /** 帆船 */
    EASportTypeWaterSailboat = 40,

    /** 浆板 */
    EASportTypeWaterSup = 41,

    /** 水球 */
    EASportTypeWaterPolo = 42,

    /** 划水 */
    EASportTypeWaterThrash = 43,

    /** 皮划艇 */
    EASportTypeWaterKayak = 44,

    /** 漂流 */
    EASportTypeWaterDrifting = 45,

    /** 划船 */
    EASportTypeWaterBoating = 46,

    /** 蹼泳 */
    EASportTypeWaterFin = 47,

    /** 跳水 */
    EASportTypeWaterDiving = 48,

    /** 花样游泳 */
    EASportTypeWaterArtistic = 49,

    /** 潜水 */
    EASportTypeWaterSnorkel = 50,

    /** 风筝冲浪 */
    EASportTypeWaterKitesurfing = 51,

    /** 沙滩车 */
    EASportTypeWaterAtv = 52,

    /** 沙滩足球 */
    EASportTypeWaterBeach = 53,

    /** 舞蹈 */
    EASportTypeDanceDance = 54,

    /** 肚皮舞 */
    EASportTypeDanceDelly = 55,

    /** 体操 */
    EASportTypeDanceGymnastics = 56,

    /** 健身操 */
    EASportTypeDanceAerobics = 57,

    /** 街舞 */
    EASportTypeDanceHipHop = 58,

    /** 拳击 */
    EASportTypeFightBoxing = 59,

    /** 武术 */
    EASportTypeFightWushu = 60,

    /** 摔跤 */
    EASportTypeFightWrestling = 61,

    /** 太极 */
    EASportTypeFightTaichi = 62,

    /** 泰拳 */
    EASportTypeFightMuayThai = 63,

    /** 柔道 */
    EASportTypeFightJudo = 64,

    /** 跆拳道 */
    EASportTypeFightTaekwondo = 65,

    /** 空手道 */
    EASportTypeFightKarate = 66,

    /** 自由搏击 */
    EASportTypeFightFreeSparring = 67,

    /** 足球 */
    EASportTypeBallSoccer = 68,

    /** 篮球 */
    EASportTypeBallBasketball = 69,

    /** 排球 */
    EASportTypeBallVolleyball = 70,

    /** 羽毛球 */
    EASportTypeBallBadminton = 71,

    /** 乒乓球 */
    EASportTypeBallPingpong = 72,

    /** 板球 */
    EASportTypeBallCricket = 73,

    /** 橄榄球 */
    EASportTypeBallFootball = 74,

    /** 墙球 */
    EASportTypeBallRacqurball = 75,

    /** 手球 */
    EASportTypeBallHandball = 76,

    /** 壁球 */
    EASportTypeBallSquash = 77,

    /** 毽球 */
    EASportTypeBallShuttlecock = 78,

    /** 藤球 */
    EASportTypeBallRaga = 79,

    /** 雪车 */
    EASportTypeSnowBoard = 80,

    /** 双板滑雪 */
    EASportTypeSnowSkis = 81,

    /** 冰球 */
    EASportTypeSnowPuck = 82,

    /** 滑冰 */
    EASportTypeSnowSkate = 83,

    /** 冰壶 */
    EASportTypeSnowCurling = 84,

    /** 单板滑雪 */
    EASportTypeSnowMobile = 85,

    /** 雪橇 */
    EASportTypeSnowSled = 86,

    /** 冥想 */
    EASportTypeLeisureMeditation = 87,

    /** 剑道 */
    EASportTypeLeisureKendo = 88,

    /** 击剑 */
    EASportTypeLeisureFence = 89,

    /** 保龄球 */
    EASportTypeLeisureBowling = 90,

    /** 台球 */
    EASportTypeLeisureBilliards = 91,

    /** 射箭 */
    EASportTypeLeisureArchery = 92,

    /** 飞镖 */
    EASportTypeLeisureDarts = 93,

    /** 骑马 */
    EASportTypeLeisureHorse = 94,

    /** 呼啦圈 */
    EASportTypeLeisureHula = 95,

    /** 放风筝 */
    EASportTypeLeisureKite = 96,

    /** 钓鱼 */
    EASportTypeLeisureFishing = 97,

    /** 飞盘 */
    EASportTypeLeisureFribee = 98,

    /** 马术 */
    EASportTypeLeisureEquestrian = 99,

    /** 赛车 */
    EASportTypeLeisureRacing = 100,

    /** 自由锻炼 */
    EASportTypeOtherFree = 101,

    /** 跳绳 */
    EASportTypeOtherRope = 102,

    /** 上楼梯 */
    EASportTypeOtherClimb = 103,

    /** 拔河 */
    EASportTypeOtherPush = 104,

    /** 单杠 */
    EASportTypeOtherHorizontal = 105,

    /** 双杠 */
    EASportTypeOtherParallel = 106,

    /** 网球 */
    EASportTypeTennis = 107,

    /** 棒球 */
    EASportTypeBaseball = 108,

    /** 曲棍球 */
    EASportTypeHockey = 109,

    /** 自定义运动 */
    EASportTypeCustomSport = 110,

    /** 踏步 */
    EASportTypeMarkTime = 111,

    /** 漫步机 */
    EASportTypeWalkingMachine = 112,

    /** 田径 */
    EASportTypeAthletics = 113,

    /** 腰腹训练 */
    EASportTypeLumbarAbdomenTraining = 114,

    /** 拉丁舞 */
    EASportTypeLatinDance = 115,

    /** 芭蕾 */
    EASportTypeBallet = 116,

    /** 高尔夫 */
    EASportTypeGolf = 117,

    /** 民族舞 */
    EASportTypeFolkDance = 118,

    /** 长曲棍球 */
    EASportTypeLacrosse = 119,

    /** 垒球 */
    EASportTypeSoftball = 120,

    /** 匹克球 */
    EASportTypePeakBall = 121,

    /** 蹦床 */
    EASportTypeTrampoline = 122,

    /** 酷跑 */
    EASportTypeParkour = 123,

    /** 俯卧撑 */
    EASportTypePushUp = 124,

    /** 跳高 */
    EASportTypeHighJump = 125,

    /** 跳远 */
    EASportTypeLongJump = 126,

    
    
    /** 智慧运动: 日常运动 */
    EASportTypeDailyEx = 32768,

    /** 智慧运动: 户外步行 */
    EASportTypeOurdoorWalkingEx = 32769,

    /** 智慧运动: 户外跑步 */
    EASportTypeOurdoorRunningEx = 32770,

    /** 智慧运动: 户外徒步 */
    EASportTypeOurdoorOnFootEx = 32771,

    /** 智慧运动: 户外登山 */
    EASportTypeOurdoorOnMountaineeringEx = 32772,

    /** 智慧运动: 户外越野跑 */
    EASportTypeOurdoorTrailRunningEx = 32773,

    /** 智慧运动: 户外单车 */
    EASportTypeOurdoorCyclingEx = 32774,

    /** 智慧运动: 户外游泳 */
    EASportTypeOutdoorSwimmingEx = 32775,

    /** 智慧运动: 室内步行 */
    EASportTypeIndoorWalkingEx = 32776,

    /** 智慧运动: 室内跑步 */
    EASportTypeIndoorRunningEx = 32777,

    /** 智慧运动: 室内锻炼 */
    EASportTypeIndoorExerciseEx = 32778,

    /** 智慧运动: 室内单车 */
    EASportTypeIndoorCyclingEx = 32779,

    /** 智慧运动: 椭圆机 */
    EASportTypeEllipticalEx = 32780,

    /** 智慧运动: 瑜伽 */
    EASportTypeYogaEx = 32781,

    /** 智慧运动: 划船机 */
    EASportTypeRowingEx = 32782,

    /** 智慧运动: 室内游泳 */
    EASportTypeIndoorSwimmingEx = 32783,
    
};

/// MARK: - OTA 响应类型
typedef NS_ENUM(NSUInteger,EAOtaRespondStatus) {
    
    /** 接受ota请求 */
    EAOtaRespondStatusAccept = 0,
    
    /** 拒绝ota请求 : 其他原因 */
    EAOtaRespondStatusReject = 1,
    
    /** 拒绝ota请求 : 设备已经更新此版本 */
    EAOtaRespondStatusRejectVersionError = 2,
    
    /** 请继续发送数据包 */
    EAOtaRespondStatusProceed = 3,
    
    /** 传输完成，crc校验错误 */
    EAOtaRespondStatusCrcError = 4,
    
    /** 传输完成 */
    EAOtaRespondStatusComplete = 5,
};

/// MARK: - OTA 请求类型
typedef NS_ENUM(NSUInteger,EAOtaRequestType) {
    
    /** 阿波罗 */
    EAOtaRequestTypeApollo = 0,
    
    /** stm */
    EAOtaRequestTypeStm32 = 1,
    
    /** res */
    EAOtaRequestTypeRes = 2,
    
    /** tp */
    EAOtaRequestTypeTp = 3,
    
    /** 心率 */
    EAOtaRequestTypeHr = 4,
    
    /** gps */
    EAOtaRequestTypeGps = 5,
    
    /** agps */
    EAOtaRequestTypeAgps = 6,
    
    /** 自定义表盘 */
    EAOtaRequestTypeUserWf = 7,
};


/// MARK: - OTA 图片类型
typedef NS_ENUM(NSUInteger,EAUIType) {
    
    /** 未知的归类 */
    EAUITypeUnknow = 0,
    
    /** 背景 */
    EAUITypeBackground = 1,
    
    /** 数字字体，打包0~9,固定宽高，在bin中按0~9排序 */
    EAUITypeNumberFont = 2,
    
    /** 年 */
    EAUITypeYear = 3,
    
    /** 月 */
    EAUITypeMonth = 4,
    
    /** 日 */
    EAUITypeDay = 5,
    
    /** 时 */
    EAUITypeHour = 6,
    
    /** 分 */
    EAUITypeMinute = 7,
    
    /** 秒 */
    EAUITypeSecond = 8,
    
    /** 时针 */
    EAUITypeHourHand = 9,
    
    /** 分针 */
    EAUITypeMinuteHand = 10,
    
    /** 秒针 */
    EAUITypeSecondHand = 11,
    
    /** 家乡时区时针 */
    EAUITypeHomeHourHand = 12,
    
    /** 家乡时区分针 */
    EAUITypeHomeMinuteHand = 13,
    
    /** 家乡时区秒针 */
    EAUITypeHomeSecondHand = 14,
    
    /** 步数图标 */
    EAUITypeSteps = 15,
    
    /** 步数字体 */
    EAUITypeStepsNumberFont = 16,
    
    /** 卡路里 */
    EAUITypeCalorie = 17,
    
    /** 卡路里字体 */
    EAUITypeCalorieNumberFont = 18,
    
    /** 距离 */
    EAUITypeDistance = 19,
    
    /** 距离 字体 */
    EAUITypeDistanceNumberFont = 20,
    
    /** 运动时长 */
    EAUITypeDuration = 21,
    
    /** 运动时长字体 */
    EAUITypeDurationNumberFont = 22,
    
    /** 心率 */
    EAUITypeHeartRate = 23,
    
    /** 心率字体 */
    EAUITypeHeartRateNumberFont = 24,
    
    /** 天气 */
    EAUITypeWeather = 25,
    
    /** 电池 */
    EAUITypeBattery = 26,
};

/// MARK: - 压力数据类型
typedef NS_ENUM(NSUInteger,EAStressDataType) {
    
    /** * */
    EAStressDataTypeStressUnkown = 0,
    
    /** 放松 */
    EAStressDataTypeStressRelax = 1,
    
    /** 正常 */
    EAStressDataTypeStressNormal = 2,
    
    /** 中等 */
    EAStressDataTypeStressMiddle = 3,
    
    /** 高 */
    EAStressDataTypeStressHigh = 4,
};

/// MARK: - 振动模式
typedef NS_ENUM(NSUInteger,EAVibrateIntensityType) {
    
    /** 弱 */
    EAVibrateIntensityTypeLight = 0,
    
    /** 正常 */
    EAVibrateIntensityTypeMedium = 1,
    
    /** 强 */
    EAVibrateIntensityTypeStrong = 2,
    
};

/// MARK: - 一级菜单样式
typedef NS_ENUM(NSUInteger,EAFirstLeverType) {
    
    /** 无 */
    EAFirstLeverTypePageNull = 0,
    
    /** 心率 */
    EAFirstLeverTypePageHeartRate = 1,
    
    /** 压力 */
    EAFirstLeverTypePagePressure = 2,
    
    /** 天气 */
    EAFirstLeverTypePageWeather = 3,
    
    /** 音乐 */
    EAFirstLeverTypePageMusic = 4,
    
    /** 呼吸 */
    EAFirstLeverTypePageBreath = 5,
    
    /** 睡眠 */
    EAFirstLeverTypePageSleep = 6,
    
    /** 生理周期 */
    EAFirstLeverTypePageMenstrualCycle = 7,
    
};

/// MARK: - 经期类型
typedef NS_ENUM(NSUInteger, EAMenstruationType) {
    
    /** 第一次安全期 */
    EAMenstruationTypeFirstSafePeriod        = 0,
    
    /** 第2次安全期 */
    EAMenstruationTypeSecondSafePeriod,
    
    /** 经期 */
    EAMenstruationTypePeriod,
    
    /** 易孕期 */
    EAMenstruationTypeEasyPregnancy,
    
    /** 排卵日 */
    EAMenstruationTypeOvulationDay,
    
    /** 未设置时间 */
    EAMenstruationTypeUnSetting,
    
};

/// MARK: - 抬手亮屏
typedef NS_ENUM(NSUInteger, EAGesturesBrightType) {
    
    /** 关闭 */
    EAGesturesBrightTypeClose = 0,
    
    /** 全天开启 */
    EAGesturesBrightTypeAllDay = 1,
    
    /** 选择时间段开启 */
    EAGesturesBrightTypeSelectTime = 2,
};


/// MARK: - 习惯事件操作
typedef NS_ENUM(NSUInteger,EAHabitTrackerOps) {
    
    /** 操作：新增 */
    EAHabitTrackerOpsAdd = 0,
    
    /** 操作：编辑 */
    EAHabitTrackerOpsEdit = 1,
    
    /** 操作：删除此条 */
    EAHabitTrackerOpsDel = 2,
    
    /** 操作：删除全部 */
    EAHabitTrackerOpsDelAll = 3,
};

/// MARK: - 习惯图标类型
typedef NS_ENUM(NSUInteger,EAHabitTrackerIconType) {
    
    EAHabitTrackerIconTypeStudy01 = 0,
    EAHabitTrackerIconTypeSleep02 = 1,
    EAHabitTrackerIconTypeStudy03 = 2,
    EAHabitTrackerIconTypeChores04 = 3,
    EAHabitTrackerIconTypeHavefun05 = 4,
    EAHabitTrackerIconTypeDrink06 = 5,
    EAHabitTrackerIconTypeSun07 = 6,
    EAHabitTrackerIconTypeTeeth08 = 7,
    EAHabitTrackerIconTypeCalendar09 = 8,
    EAHabitTrackerIconTypePiano10 = 9,
    EAHabitTrackerIconTypeFruit11 = 10,
    EAHabitTrackerIconTypeMedicine12 = 11,
    EAHabitTrackerIconTypeDraw13 = 12,
    EAHabitTrackerIconTypeTarget14 = 13,
    EAHabitTrackerIconTypeDog15 = 14,
    EAHabitTrackerIconTypeExercise16 = 15,
    EAHabitTrackerIconTypeBed17 = 16,
    EAHabitTrackerIconTypeTidyup18 = 17,
    EAHabitTrackerIconTypeEatFood = 18,
    EAHabitTrackerIconTypePackYourBag = 19,
};



/// MARK: - 习惯追踪回应状态
typedef NS_ENUM(NSUInteger,EAHabitTrackerRespondType) {
    
    /** 执行成功 */
    EAHabitTrackerRespondTypeSuccess = 0,
    
    /** 执行失败 */
    EAHabitTrackerRespondTypeFail = 1,
    
    /** 超过支持的最大数量 */
    EAHabitTrackerRespondTypeMemFull = 2,
    
    /** 时间重复 */
    EAHabitTrackerRespondTypeTimeConflict = 3,
};


#pragma mark - 习惯追踪flag
typedef NS_ENUM(NSUInteger,EAHabitTrackerFlag) {
    
    /** 初始态 */
    EAHabitTrackerFlagInitial = 0,
    
    /** 进行中 */
    EAHabitTrackerFlagInProgress = 1,
    
    /** 已完成 */
    EAHabitTrackerFlagCompleted = 2,
    
    /** 已取消 */
    EAHabitTrackerFlagCancel = 3,
};


#pragma mark - 通讯录flag
typedef NS_ENUM(NSUInteger,EAPhoneContactFlag) {
    
    /** 标志: 起始包 */
    EAPhoneContactFlagBegin = 0,

    /** 标志: 非起始包 */
    EAPhoneContactFlagProceed = 1,
};

#endif /* EAEnumh */
