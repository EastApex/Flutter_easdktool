//
//  EASportShowDataModel.h
//  EABluetooth
//
//  Created by Aye on 2022/5/30.
//  File Name:40:Watch Daily Data【日常界面数据】


#import <EABluetooth/EABluetooth.h>

NS_ASSUME_NONNULL_BEGIN


/**
 * id = 40:
 * 手表日常界面数据
 * daily interface data of the watch
 */
@interface EASportShowDataModel : EABaseModel

/// Unit: steps【单位：步】
@property(nonatomic, assign) NSInteger steps;
/// Unit: calories【单位：小卡】
@property(nonatomic, assign) NSInteger calorie;
/// Unit: centimeters【单位：厘米】
@property(nonatomic, assign) NSInteger distance;
/// Unit: seconds【单位:秒】
@property(nonatomic, assign) NSInteger duration;



@end

NS_ASSUME_NONNULL_END
