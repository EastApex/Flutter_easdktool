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
    EAConnectStateTypeUnunited = 0,
    
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
    
    /** 寻找手表 */
    EADeviceOpsTypeStartSearchWatch = 10,

    /** 停止寻找手表 */
    EADeviceOpsTypeStopSearchWatch = 11,
    
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

#endif /* EAEnumh */
