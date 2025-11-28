//
//  EATemplateEnvironment.h
//  EABluetooth
//
//  Created by Aye on 2024/6/13.
//  File Name:67:Environment Template【环境温度】


#import <EABluetooth/EABluetooth.h>

NS_ASSUME_NONNULL_BEGIN
/**
 * id = 67
 * template environment
 * 环境温度
 */
@interface EATemplateEnvironment : EABaseModel

/// 0 off, 1 on
@property(nonatomic, assign) BOOL sw;

/// Interval time, unit: minutes (It is recommended to be no less than 60 minutes)
@property(nonatomic, assign) NSInteger interval;

@end

NS_ASSUME_NONNULL_END
