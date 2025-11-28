//
//  EAMenstrualReminder.h
//  EABluetooth
//
//  Created by Aye on 2023/2/20.
//  File Name:55:Menstrual Reminder【经期提醒】


#import <EABluetooth/EABluetooth.h>

NS_ASSUME_NONNULL_BEGIN

/**
 *  id = 55
 *  Menstrual Reminder
 *  经期提醒
 */
@interface EAMenstrualReminder : EABaseModel

/// Menstrual start reminder switch, off by default【经期开始提醒开关，默认关闭】
@property(nonatomic, assign) BOOL menstrualBeginSw;

/// Menstrual end reminder switch, off by default【经期结束提醒开关，默认关闭】
@property(nonatomic, assign) BOOL menstrualEndSw;

/// Easy pregnancy start to remind switch, default off【易孕期开始提醒开关，默认关闭】
@property(nonatomic, assign) BOOL easyPregnancyBeginSw;

/// Easy pregnancy end to remind switch, default off【易孕期易孕期提醒开关，默认关闭】
@property(nonatomic, assign) BOOL easyPregnancyEndSw;

///
/// Ovulation day reminder switch, default off【排卵日提醒开关，默认关闭】
@property(nonatomic, assign) BOOL ovulationDaySw;


/// Reminder time for the start of menstruation (corresponding to menstrual_begin_sw): How many days in advance to remind (optional 1 - 5 days)【经期开始(对应menstrual_begin_sw)提醒时间：提前多少天提醒（可选1~5天）】
@property(nonatomic, assign) NSInteger menstrualReminderDaysBefore;

/// Reminder time for the start of menstruation (corresponding to menstrual_begin_sw): hour【经期开始(对应menstrual_begin_sw)提醒时间：小时】
@property(nonatomic, assign) NSInteger menstrualReminderHours;

/// Reminder time for the start of menstruation (corresponding to menstrual_begin_sw): minute【经期开始(对应menstrual_begin_sw)提醒时间：分钟 】
@property(nonatomic, assign) NSInteger menstrualReminderMinutes;


/// Open or close with one key【一键开启或者关闭】
/// - Parameter onOff: on or Off
+ (instancetype )eaInitWithOnOff:(BOOL)onOff;


/// Open or close with one key【一键开启或者关闭】
/// - Parameter onOff: on or Off
+ (instancetype )eaInitWithOnOff:(BOOL)onOff beforeReminderDay:(NSInteger)beforeReminderDay hour:(NSInteger)hour minute:(NSInteger)minute;

@end

NS_ASSUME_NONNULL_END
