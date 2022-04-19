//
//  EABlacklightTimeoutModel.h
//  EABluetooth
//
//  Created by Aye on 2021/3/19.
//

#import <EABluetooth/EABaseModel.h>
NS_ASSUME_NONNULL_BEGIN


/// 屏幕背景光熄灭时间
@interface EABlacklightTimeoutModel : EABaseModel

/** 自动灭屏时间,单位（秒） 0xFFFFFFFF 无限亮屏*/ // ffffffff(十六进制) = 4294967295(十进制)
@property(nonatomic, assign) NSInteger timeOut;


/// MARK: - 获取屏幕背景光熄灭时间相关信息
/// @param data 数据流
+ (EABlacklightTimeoutModel *)getModelByData:(NSData *)data;



@end

NS_ASSUME_NONNULL_END
