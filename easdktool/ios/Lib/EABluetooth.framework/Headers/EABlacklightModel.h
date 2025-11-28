//
//  EABrightnessModel.h
//  EABluetooth
//
//  Created by Aye on 2021/3/19.
//  File Name:7:Screen Brightness【屏幕亮度】

#import <EABluetooth/EABaseModel.h>

NS_ASSUME_NONNULL_BEGIN


/**
 * id = 7
 * The screen brightness of watch
 * 屏幕亮度
 */
@interface EABlacklightModel : EABaseModel

/// brightness  0~100 【亮度 0~100】
@property(nonatomic, assign) NSInteger level;



/// Initialize the brightness model【初始化亮度模型】
///- Parameter level: Brightness value【初始化亮度模型】
+ (instancetype)eaInitWithBlacklightLevel:(NSInteger )level;





@end

NS_ASSUME_NONNULL_END
