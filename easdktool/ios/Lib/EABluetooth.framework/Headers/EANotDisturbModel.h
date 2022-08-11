//
//  EANotDisturbModel.h
//  EABluetooth
//
//  Created by Aye on 2021/3/19.
//

#import <EABluetooth/EABaseModel.h>
NS_ASSUME_NONNULL_BEGIN


/// Do not disturb Settings
/// 免打扰设置
@interface EANotDisturbModel : EABaseModel

/// EADeviceOpsType
/// 开关： 0关闭 1打开
@property(nonatomic, assign) NSInteger sw;

/// begin hour
///  开始时间 ：小时
@property(nonatomic, assign) NSInteger beginHour;

/// begin minute
///  开始时间 ：分钟
@property(nonatomic, assign) NSInteger beginMinute;

/// end hour
///  结束时间 ：小时
@property(nonatomic, assign) NSInteger endHour;

/// end minute
///  结束时间 ：分钟
@property(nonatomic, assign) NSInteger endMinute;


+ (EANotDisturbModel *)getModelByData:(NSData *)data;


@end

NS_ASSUME_NONNULL_END
