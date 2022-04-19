//
//  EADeviceOps.h
//  EABluetooth
//
//  Created by Aye on 2021/3/19.
//

#import <EABluetooth/EABaseModel.h>
NS_ASSUME_NONNULL_BEGIN


/// 操作设备
@interface EADeviceOps : EABaseModel

/** 操作类型 */
@property(nonatomic, assign) EADeviceOpsType deviceOpsType;

/** 操作状态 */
@property(nonatomic, assign) EADeviceOpsStatus deviceOpsStatus;



/// @param data 数据流
+ (EADeviceOps *)getModelByData:(NSData *)data;


@end

NS_ASSUME_NONNULL_END
