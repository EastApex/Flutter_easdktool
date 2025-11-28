//
//  EASportSupport.h
//  EABluetooth
//
//  Created by Aye on 2024/8/12.
//  File Name:68:Support Sports【支持运动】


#import <EABluetooth/EABluetooth.h>

NS_ASSUME_NONNULL_BEGIN
/**
 * id = 68
 * when support sport for App map sport (id = 46) & APP starts watch motion (screen motion)(id = 54)
 * 当App支持运动时，地图运动（id = 46） & App开始手表运动（屏幕运动）（id = 54）
 */
@interface EASportSupport : EABaseModel

@property(nonatomic, copy) NSData *mapSport;
@property(nonatomic, copy) NSData *launchScreenSport;

@property(nonatomic,copy) NSArray *mapSports;
@property(nonatomic,copy) NSArray *screenSports;

@end

NS_ASSUME_NONNULL_END
