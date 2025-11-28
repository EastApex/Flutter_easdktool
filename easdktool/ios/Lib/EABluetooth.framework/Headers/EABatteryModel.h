//
//  EABatteryModel.h
//  EABluetooth
//
//  Created by Aye on 2021/3/19.
//  File Name:9:Power Information【电量信息】

#import <EABluetooth/EABaseModel.h>
NS_ASSUME_NONNULL_BEGIN


/**
 * id = 9
 * Watch power information
 * 设备电量信息
 */
@interface EABatteryModel : EABaseModel

/// battery status【电池状态 : 正常、充电中】
@property(nonatomic, assign) EABatteryStatus batteryStatus;

/// Battery value【电量值  0 ~ 100】
@property(nonatomic, assign) NSInteger level;


@end

NS_ASSUME_NONNULL_END
