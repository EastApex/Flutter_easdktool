//
//  EABlacklightTimeoutModel.h
//  EABluetooth
//
//  Created by Aye on 2021/3/19.
//  File Name:8:Screen Dies Time【屏幕熄灭时间】


#import <EABluetooth/EABaseModel.h>
NS_ASSUME_NONNULL_BEGIN



/**
 * id = 8
 * Time when the screen automatically dies
 * 屏幕背景光熄灭时间
 */

@interface EABlacklightTimeoutModel : EABaseModel

/// Automatic screen off time (unit: second)【自动灭屏时间,单位（秒） 0xFFFFFFFF 无限亮屏 ffffffff(十六进制) = 4294967295(十进制)】
@property(nonatomic, assign) NSInteger timeOut;



/// Initialize【初始化】
///- Parameter timeout: Sleep time【休眠时间】
+ (instancetype)eaInitWithBlacklightTimeout:(NSInteger )timeout;


@end

NS_ASSUME_NONNULL_END
