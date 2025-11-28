//
//  EAStressMonitor.h
//  EABluetooth
//
//  Created by Aye on 2023/2/3.
//  File Name:51:Stress Monitoring【压力监测】


#import <EABluetooth/EABluetooth.h>

NS_ASSUME_NONNULL_BEGIN

/**
 * id = 51
 * Automatic Stress monitoring
 * 自动压力监测
 */
@interface EAStressMonitor : EABaseModel

/// 0 off, 1 on 【0关，1开】
@property(nonatomic, assign) BOOL sw;

/// Interval time, unit: minutes (minimum 60 minutes)【间隔时间，单位：分钟(最低不少于60分钟)】
@property(nonatomic, assign) NSInteger interval;

/// Initialization【初始化】
/// - Parameters:
///   - sw: on-off Zero off, one on[0关，1开]
+ (instancetype )eaInitWithOnOff:(BOOL)sw;


/// Initialization
/// - Parameters:
///   - sw: 0 off, 1 on 【0关，1开】
///   - interval: Interval[间隔时间]
+ (instancetype )eaInitWithOnOff:(BOOL)sw interval:(NSInteger)interval;

@end

NS_ASSUME_NONNULL_END
