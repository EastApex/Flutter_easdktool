//
//  EACusWatchFaceGlobalSetting.h
//  EABluetooth
//
//  Created by Aye on 2025/8/14.
//

#import <EABluetooth/EABluetooth.h>

NS_ASSUME_NONNULL_BEGIN

@interface EACusWatchFaceGlobalSetting : EABaseModel

/// 是否允许点击切换表盘
/// Is it allowed to click to switch the watch face
@property(nonatomic, assign) BOOL clickSwitchSw;

+ (instancetype )eaInitWithSwitch:(BOOL)open;
@end

NS_ASSUME_NONNULL_END
