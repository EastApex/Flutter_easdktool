//
//  EAAppSportRealTimeModel.h
//  EABluetooth
//
//  Created by Aye on 2022/12/14.
//  File Name:2005:Real-time Sport Data【运动实时数据】

#import <EABluetooth/EABluetooth.h>

NS_ASSUME_NONNULL_BEGIN

/**
 * Real-time data model of motion state
 * 运动状态实时数据模型
*/
@interface EAAppSportRealTimeModel : EABaseModel

/// The total number of steps currently in motion【当前运动的总步数】
@property(nonatomic, assign) NSInteger steps;

/// Total calories from current exercise (in calories)【当前运动的总卡路里（单位:小卡)】
@property(nonatomic, assign) NSInteger calorie;

///  Heart rate of current exercise【当前运动的心率】
@property(nonatomic, assign) NSInteger hr;

/// Current total distance (cm)【当前总距离（单位:厘米）】
@property(nonatomic, assign) NSInteger distance;

/// Pace (second /KM)【配速（单位:秒/KM）】
@property(nonatomic, assign) NSInteger pace;

/// Stride frequency (steps per minute)【步频（单位:步/分钟）】
@property(nonatomic, assign) NSInteger strideFrequency;

/// Current total number (how many jumps the rope is currently taking)【当前总次数（跳绳当前跳了多少下）】
@property(nonatomic, assign) NSInteger count;

///  Current altitude (cm)【当前海拔高度（单位:厘米）】
@property(nonatomic, assign) NSInteger altitude;

/// Time stamp 【时间戳】
@property(nonatomic, assign) NSInteger timestamp;

/// speed(M/H) [当前速度（单位: M/H）]
@property(nonatomic, assign) NSInteger speed;


@end

NS_ASSUME_NONNULL_END
