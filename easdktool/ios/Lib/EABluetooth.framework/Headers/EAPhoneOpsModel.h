//
//  EAPhoneOpsModel.h
//  EABluetooth
//
//  Created by Aye on 2021/3/22.
//

#import <EABluetooth/EABaseModel.h>

NS_ASSUME_NONNULL_BEGIN


/// 操作手机
@interface EAPhoneOpsModel : EABaseModel

/** 操作 */
@property(nonatomic, readwrite) EAPhoneOps eOps;

/** 操作状态 */
@property(nonatomic, readwrite) EAPhoneOpsStatus eOpsStatus;


/// 获取操作手机相关信息
/// @param data 数据流
+ (EAPhoneOpsModel *)getModelByData:(NSData *)data;






@end

NS_ASSUME_NONNULL_END
