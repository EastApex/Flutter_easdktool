//
//  EAPhoneOpsModel.h
//  EABluetooth
//
//  Created by Aye on 2021/3/22.
//  File Name:2001:Operate Phone【操作手机】


#import <EABluetooth/EABaseModel.h>

NS_ASSUME_NONNULL_BEGIN

/**
 * Operate the mobile phone data model
 * 操作手机数据模型
*/
@interface EAPhoneOpsModel : EABaseModel

/// Operation type【操作类型】
@property(nonatomic, assign) EAPhoneOps eOps;

/// Operation status【操作状态】
@property(nonatomic, assign) EAPhoneOpsStatus eOpsStatus;


@end

NS_ASSUME_NONNULL_END
