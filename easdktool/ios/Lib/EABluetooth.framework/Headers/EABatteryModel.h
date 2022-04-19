//
//  EABatteryModel.h
//  EABluetooth
//
//  Created by Aye on 2021/3/19.
//

#import <EABluetooth/EABaseModel.h>
NS_ASSUME_NONNULL_BEGIN


/// 设备电量信息
@interface EABatteryModel : EABaseModel

/** 电池状态 : 正常、充电中*/
@property(nonatomic, assign) EABatteryStatus batteryStatus;

/** 电量值  0 ~ 100*/
@property(nonatomic, assign) NSInteger level;


/// MARK: - 获取设备电量相关信息
/// @param data 数据流
+ (EABatteryModel *)getModelByData:(NSData *)data;


@end

NS_ASSUME_NONNULL_END
