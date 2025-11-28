//
//  EARealTimeModel.h
//  EABluetooth
//
//  Created by Aye on 2022/12/14.
//  File Name:2005:Real-time Data【实时数据】


#import <EABluetooth/EABluetooth.h>

NS_ASSUME_NONNULL_BEGIN

/**
 * Real-time data model: Returns when the watch generates new values
 * 实时数据模型:当手表有产出新的值时就返回
*/
@interface EARealTimeModel : EABaseModel

/// Current total steps【当前总步数】
@property(nonatomic, assign) NSInteger steps;

/// Current total calories (unit: calorie)【当前总卡路里（单位:小卡)】
@property(nonatomic, assign) NSInteger calorie;

/// Current total distance (unit: centimeter)【当前总距离（单位:厘米）】
@property(nonatomic, assign) NSInteger distance;

/// Current total exercise duration (unit: second)【当前总运动时长(单位:秒)】
@property(nonatomic, assign) NSInteger duration;

/// Current heart rate【当前心率】
@property(nonatomic, assign) NSInteger hr;

/// Current pressure【当前压力】
@property(nonatomic, assign) NSInteger pressure;

/// Current blood oxygen【当前血氧】
@property(nonatomic, assign) NSInteger bloodOxygen;

/// Current battery level【当前电量】
@property(nonatomic, assign) NSInteger batLev;


@end

NS_ASSUME_NONNULL_END
