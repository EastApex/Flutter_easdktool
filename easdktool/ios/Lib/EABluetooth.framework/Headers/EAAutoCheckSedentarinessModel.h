//
//  EAAutoCheckSedentarinessModel.h
//  EABluetooth
//
//  Created by Aye on 2021/3/19.
//  File Name:18:Sedentary Monitoring【久坐监测】


#import <EABluetooth/EABaseModel.h>
NS_ASSUME_NONNULL_BEGIN


/// MARK: - Sedentary switch status【久坐开关状态】
typedef NS_ENUM(NSUInteger,EASedentarinessStatusSwitch) {
    
    EASedentarinessStatusSwitchOff = 10,
    EASedentarinessStatusSwitchOn = 11,
};


/**
 * id = 18
 * Sedentary monitoring
 * 久坐监测
 */
@interface EAAutoCheckSedentarinessModel : EABaseModel

/// Interval duration: minute【间隔时长：分钟】
@property(nonatomic, assign) NSInteger  interval;

/// Cycle【周期】
@property(nonatomic, assign) NSInteger  weekCycleBit;

@property(nonatomic, assign) NSInteger  beginHour;

@property(nonatomic, assign) NSInteger  beginMinute;

@property(nonatomic, assign) NSInteger  endHour;

@property(nonatomic, assign) NSInteger  endMinute;

/// Step threshold: Below this threshold indicates sedentary behavior【步数阈值 ：低于此步数则提醒久坐】
@property(nonatomic, assign) NSInteger  stepThreshold;

/// Sedentary switch (10: off, 11: on)【久坐开关 (10:关闭，11:打开)】
@property(nonatomic, assign) EASedentarinessStatusSwitch sw;

/// Lunch break switch【午休开关】
@property(nonatomic, assign) NSInteger noonSw;

/// Lunch break start time: hour【午休开始时间 ：小时】
@property(nonatomic, assign) NSInteger noonBeginHour;

/// Lunch break start time: minute【午休开始时间 ：分钟】
@property(nonatomic, assign) NSInteger noonBeginMinute;

/// Lunch break end time: hour【午休结束时间 ：小时】
@property(nonatomic, assign) NSInteger noonEndHour;

/// Lunch break end time: minute【午休结束时间 ：分钟】
@property(nonatomic, assign) NSInteger noonEndMinute;


@end

NS_ASSUME_NONNULL_END
