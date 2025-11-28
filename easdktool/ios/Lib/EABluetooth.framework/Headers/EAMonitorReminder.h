//
//  EAMonitorReminder.h
//  EABluetooth
//
//  Created by Aye on 2022/9/27.
//  File Name:45:Monitor Reminder Event【提醒事件监测】


#import <EABluetooth/EABluetooth.h>




NS_ASSUME_NONNULL_BEGIN


/**
 * id = 45
 * 提醒事件监测
 * Monitor reminder event
 */
@interface EAMonitorReminder : EABaseModel


/// Reminder event type【提醒事件类型 】
@property(nonatomic, assign) EAMonitorReminderType eReminderType;

/// Switch【开关 】
@property(nonatomic, assign) NSInteger sw;

/// Interval duration: in minutes【间隔时长：单位分钟 】
@property(nonatomic, assign) NSInteger interval;

/// Cycle【周期】
@property(nonatomic, assign) NSInteger weekCycleBit;

/// Start time: hour【开始时间 ：小时 】
@property(nonatomic, assign) NSInteger beginHour;

/// Start time: minute【开始时间 ：分钟 】
@property(nonatomic, assign) NSInteger beginMinute;

/// End time: hour【结束时间 ：小时 】
@property(nonatomic, assign) NSInteger endHour;

/// End time: minute【结束时间 ：分钟 】
@property(nonatomic, assign) NSInteger endMinute;

/// Step threshold: Remind of sedentary if steps are below this value (reminder_type = Sedentary)【步数阈值 ：低于此步数则提醒久坐（reminder_type = Sedentary） 】
@property(nonatomic, assign) NSInteger stepThreshold;

/// Cups: How many cups of water to drink, one cup = 200ml (reminder_type = Drink)【杯：喝多少杯水，一杯水 = 200ml（reminder_type = Drink） 】
@property(nonatomic, assign) NSInteger cup;


/// Initialize water drinking reminder【初始化喝水提醒】
/// - Parameters:
///   - sw: Switch【开关】
///   - interval: Interval duration (minutes)【间隔时长（分）】
///   - weekCycleBit: Cycle【周期】
///   - beginHour: Start hour【开始时】
///   - beginMinute: Start minute【开始分】
///   - endHour: End hour【结束时】
///   - endMinute: End minute【结束分】
///   - cup: Number of cups of water【多少杯水】
+ (EAMonitorReminder *)eaInitDrinkMonitorWithOnOff:(BOOL)sw
                                        interval:(NSInteger)interval
                                    weekCycleBit:(NSInteger)weekCycleBit
                                       beginHour:(NSInteger)beginHour
                                     beginMinute:(NSInteger)beginMinute
                                         endHour:(NSInteger)endHour
                                       endMinute:(NSInteger)endMinute
                                             cup:(NSInteger)cup;


/// Initialize hand - washing reminder【初始化洗手提醒】
/// - Parameters:
///   - sw: Switch【开关】
///   - interval: Interval duration (minutes)【间隔时长（分）】
///   - weekCycleBit: Cycle【周期】
///   - beginHour: Start hour【开始时】
///   - beginMinute: Start minute【开始分】
///   - endHour: End hour【结束时】
///   - endMinute: End minute【结束分】
+ (EAMonitorReminder *)eaInitWashHandsMonitorWithOnOff:(BOOL)sw
                                            interval:(NSInteger)interval
                                        weekCycleBit:(NSInteger)weekCycleBit
                                           beginHour:(NSInteger)beginHour
                                         beginMinute:(NSInteger)beginMinute
                                             endHour:(NSInteger)endHour
                                           endMinute:(NSInteger)endMinute;


/// Initialize Take Medicine 【初始化吃药】
/// - Parameters:
///   - sw: Switch【开关】
///   - interval: Interval duration (minutes)【间隔时长（分）】
///   - weekCycleBit: Cycle【周期】
///   - beginHour: Start hour【开始时】
///   - beginMinute: Start minute【开始分】
///   - endHour: End hour【结束时】
///   - endMinute: End minute【结束分】
+ (EAMonitorReminder *)initTakeMedicineMonitorWithOnOff:(BOOL)sw
                                            interval:(NSInteger)interval
                                        weekCycleBit:(NSInteger)weekCycleBit
                                           beginHour:(NSInteger)beginHour
                                         beginMinute:(NSInteger)beginMinute
                                             endHour:(NSInteger)endHour
                                           endMinute:(NSInteger)endMinute;



/// Get water - drinking reminder【获取喝水提醒】
+ (void)eaGetDrinkReminder:(ResultGetInfoBlock )result;
/// Get hand - washing reminder【获取洗手提醒】
+ (void)eaGetWashHandsReminder:(ResultGetInfoBlock )result;
/// Get medicine - taking reminder【获取吃药提醒】
+ (void)eaGetTakeMedicineReminder:(ResultGetInfoBlock )result;

@end



NS_ASSUME_NONNULL_END
