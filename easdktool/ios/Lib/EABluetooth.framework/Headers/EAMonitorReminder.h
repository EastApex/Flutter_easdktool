//
//  EAMonitorReminder.h
//  EABluetooth
//
//  Created by Aye on 2022/9/27.
//

#import <EABluetooth/EABluetooth.h>

/*
Cycle: == "Sunday ~ Saturday,
0: off 1: on
Eg1:

Sunday Monday Tuesday Wednesday Thursday Friday Saturday
0, 1, 1, 0, 0, 1
We get 0110001, and we get 1000110 by little endian
Convert 1000110 binary to base 10, that is, weekCycleBit is 70 to enable the Monday, Tuesday, and Saturday detection

Eg2:
Sunday Monday Tuesday Wednesday Thursday Friday Saturday
0, 0, 0, 0, 1, 1
You get 0000011, and you get 1100000 by the little end
Convert 1100000 binary to base 10. At this time, weekCycleBit is 96

If weekCycleBit is 0, the monitoring function is disabled
If weekCycleBit is set to 127, the daily monitoring function is enabled
*/
 
/*
周期：==》周日~周六，
0：关闭 1：开启
eg1：

周日 周一 周二 周三 周四 周五 周六
0 1 1 0 0 0 1
得到 0110001，按照小端排序得到 1000110
将 1000110 二进制转为 10进制 即此时 weekCycleBit 为 70 开启 周一二六 检测

eg2：
周日 周一 周二 周三 周四 周五 周六
0 0 0 0 0 1 1
得到 0000011，按照小端排序得到 1100000
将 1100000二进制转为 10进制 即此时 weekCycleBit 为 96 开启 周五六 检测

weekCycleBit 为0 即 关闭监测功能
weekCycleBit 为127 即 开启每天监测功能
*/


NS_ASSUME_NONNULL_BEGIN

/// 提醒事件监测
/// Monitor reminder event
@interface EAMonitorReminder : EABaseModel






/** 提醒事件类型 */
@property(nonatomic, assign) EAMonitorReminderType eReminderType;

/** 开关 */
@property(nonatomic, assign) NSInteger sw;

/** 间隔时长：单位分钟 */
@property(nonatomic, assign) NSInteger interval;

/** 周期：位对应从bit0~bit6对应周日~周六,0仅一次提醒 */
@property(nonatomic, assign) NSInteger weekCycleBit;

/** 开始时间 ：小时 */
@property(nonatomic, assign) NSInteger beginHour;

/** 开始时间 ：分钟 */
@property(nonatomic, assign) NSInteger beginMinute;

/** 结束时间 ：小时 */
@property(nonatomic, assign) NSInteger endHour;

/** 结束时间 ：分钟 */
@property(nonatomic, assign) NSInteger endMinute;

/** 步数阈值 ：低于此步数则提醒久坐（reminder_type = Sedentary） */
@property(nonatomic, assign) NSInteger stepThreshold;

/** 杯：喝多少杯水，一杯水 = 200ml（reminder_type = Drink） */
@property(nonatomic, assign) NSInteger cup;

+ (EAMonitorReminder *)initDrinkMonitorWithOnOff:(BOOL)sw
                                        interval:(NSInteger)interval
                                    weekCycleBit:(NSInteger)weekCycleBit
                                       beginHour:(NSInteger)beginHour
                                     beginMinute:(NSInteger)beginMinute
                                         endHour:(NSInteger)endHour
                                       endMinute:(NSInteger)endMinute
                                             cup:(NSInteger)cup;


+ (EAMonitorReminder *)initWashHandsMonitorWithOnOff:(BOOL)sw
                                            interval:(NSInteger)interval
                                        weekCycleBit:(NSInteger)weekCycleBit
                                           beginHour:(NSInteger)beginHour
                                         beginMinute:(NSInteger)beginMinute
                                             endHour:(NSInteger)endHour
                                           endMinute:(NSInteger)endMinute;

//+ (EAMonitorReminder *)initSedentaryMonitorWithOnOff:(BOOL)sw
//                                            interval:(NSInteger)interval
//                                        weekCycleBit:(NSInteger)weekCycleBit
//                                           beginHour:(NSInteger)beginHour
//                                         beginMinute:(NSInteger)beginMinute
//                                             endHour:(NSInteger)endHour
//                                           endMinute:(NSInteger)endMinute
//                                       stepThreshold:(NSInteger)stepThreshold;


+ (EAMonitorReminder *)getModelByData:(NSData *)data;
@end



NS_ASSUME_NONNULL_END
