//
//  EADeviceOps.h
//  EABluetooth
//
//  Created by Aye on 2021/3/19.
//  File Name:12:Operating The Watch【操作设备】


#import <EABluetooth/EABaseModel.h>
NS_ASSUME_NONNULL_BEGIN



/**
 * id = 12
 * Operating the watch
 * 操作设备
 */
@interface EADeviceOps : EABaseModel


/// Operating Device Type【操作类型】
@property(nonatomic, assign) EADeviceOpsType deviceOpsType;

/// need set EADeviceOpsStatusExecute to Operating the watch
@property(nonatomic, assign) EADeviceOpsStatus deviceOpsStatus __attribute__((deprecated));


/// Initialize【初始化】
///- Parameter deviceOpsType: Operation type 【操作类型】
+ (instancetype)eaInitDeviceOpsType:(EADeviceOpsType )deviceOpsType;



@end

NS_ASSUME_NONNULL_END
