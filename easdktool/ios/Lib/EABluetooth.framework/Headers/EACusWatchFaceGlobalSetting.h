//
//  EACusWatchFaceGlobalSetting.h
//  EABluetooth
//
//  Created by Aye on 2025/8/14.
//  File Name:80:Watch Face Global【自定义表盘全局设置】


#import <EABluetooth/EABluetooth.h>

NS_ASSUME_NONNULL_BEGIN


/**
 * id = 80
 * Customize the global Settings of the watch face
 * 自定义表盘全局设置
*/
@interface EACusWatchFaceGlobalSetting : EABaseModel

/// Is it allowed to click to switch the watch face【是否允许点击切换表盘】
@property(nonatomic, assign) BOOL clickSwitchSw;


/// Initialization
/// - Parameter open: open
+ (instancetype )eaInitWithSwitch:(BOOL)open;
@end

NS_ASSUME_NONNULL_END
