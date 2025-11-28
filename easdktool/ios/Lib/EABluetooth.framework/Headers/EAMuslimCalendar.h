//
//  EAMuslimCalendar.h
//  EABluetooth
//
//  Created by Aye on 2024/11/13.
//  File Name:71:Muslim Calendar【穆斯林回历】

#import <EABluetooth/EABluetooth.h>

NS_ASSUME_NONNULL_BEGIN


/**
 * id = 71
 * Muslim Calendar
 * 穆斯林回历
 */
@interface EAMuslimCalendar : EABaseModel

/// Year【当前回历年份】
@property(nonatomic,  assign) NSInteger mYear;

/// Month【当前回历月份】
@property(nonatomic,  assign) NSInteger mMonth;

/// Day【当前回历日期】
@property(nonatomic,  assign) NSInteger mDay;


/// Initialization
/// - Parameters:
///   - year: year 
///   - month: month
///   - day: day
+ (instancetype)eaInitMuslimCalendarWithYear:(NSInteger)year month:(NSInteger)month day:(NSInteger)day;

@end

NS_ASSUME_NONNULL_END
