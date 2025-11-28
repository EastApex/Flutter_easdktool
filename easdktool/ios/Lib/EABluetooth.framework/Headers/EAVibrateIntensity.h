//
//  EAVibrateIntensity.h
//  EABluetooth
//
//  Created by Aye on 2023/2/3.
//  File Name:53:Vibrate Mode【震动模式】


#import <EABluetooth/EABluetooth.h>

NS_ASSUME_NONNULL_BEGIN

/**
 * id = 54
 * 震动模式
 * vibrate mode
 */
@interface EAVibrateIntensity : EABaseModel

/// Vibration type【震动类型】
@property(nonatomic, assign) EAVibrateIntensityType eVibrateIntensity;

/// Initialization【初始化】
/// - Parameters:
///   - eaVibrateIntensityType: Vibration type【震动类型】
+ (instancetype )eaInitWithVibrateIntensityType:(EAVibrateIntensityType )eaVibrateIntensityType;



@end

NS_ASSUME_NONNULL_END
