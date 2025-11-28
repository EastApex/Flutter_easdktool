//
//  EASleepBloodOxygenMonitor.h
//  EABluetooth
//
//  Created by Aye on 2023/2/2.
//  File Name:50:Oxygen Monitoring【血氧监测】

#import <EABluetooth/EABluetooth.h>

NS_ASSUME_NONNULL_BEGIN

/**
 * id = 50 ：
 * Automatic sleep oxygen monitoring (night oxygen monitoring)
 * 自动睡眠血氧监测（夜间血氧监测）
 */
@interface EASleepBloodOxygenMonitor : EABaseModel

/// 0 off, 1 on 【0关，1开】
@property(nonatomic, assign) BOOL sw;

/// Interval: 30 (unit: minute), range: 30, 45, 60, 90, and 120【间隔时间，默认半小时：30（单位：分钟），范围（30、45、60、90、120）】
@property(nonatomic, assign) NSInteger interval;




/// Initialization【初始化】
/// - Parameters:
///   - sw: on-off，0 off, 1 on 【0关，1开】
///   - interval: Interval[间隔时间]
+ (instancetype )eaInitWithOnOff:(BOOL)sw interval:(NSInteger)interval;




@end

NS_ASSUME_NONNULL_END
