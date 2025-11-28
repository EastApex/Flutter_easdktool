//
//  EADailyGoalModel.h
//  EABluetooth
//
//  Created by Aye on 2021/3/19.
//  File Name:15:Daily Target Setting【日常目标值设置】


#import <EABluetooth/EABaseModel.h>
NS_ASSUME_NONNULL_BEGIN

@class EADailyGoalItem;

/**
 * id = 15
 * Daily target value setting
 * 日常目标值设置
 */
@interface EADailyGoalModel : EABaseModel


/// Step (steps)【步数，目标值单位: 步】
@property(nonatomic, strong) EADailyGoalItem *sStep;


/// Calorie (cal)【卡路里，目标值单位: 卡】
@property(nonatomic, strong) EADailyGoalItem *sCalorie;

/// Distance (meter)【 距离，目标值单位: 米】
@property(nonatomic, strong) EADailyGoalItem *sDistance;

/// Duration (second)【运动时长，目标值单位: 秒】
@property(nonatomic, strong) EADailyGoalItem *sDuration;

/// Sleep time (second)【睡眠时长，目标值单位: 秒】
@property(nonatomic, strong) EADailyGoalItem *sSleep;




/// Initialize【初始化】
/// - Parameters:
///   - onOff: switch: 0 off 1 On
///   - stepGoal: Step (steps)【步数，目标值单位: 步】
///   - calorieGoal: Calorie (cal)【卡路里，目标值单位: 卡】
///   - distanceGoal: Distance (meter)【 距离，目标值单位: 米】
///   - durationGoal: Duration (second)【运动时长，目标值单位: 秒】
///   - sleepGoal: Sleep time (second)【睡眠时长，目标值单位: 秒】
+ (instancetype)eaInitWithOnOff:(NSInteger)onOff
                       stepGoal:(NSInteger)stepGoal
                    calorieGoal:(NSInteger)calorieGoal
                   distanceGoal:(NSInteger)distanceGoal
                   durationGoal:(NSInteger)durationGoal
                      sleepGoal:(NSInteger)sleepGoal;



@end


/**
 * Details of daily target values
 * 日常目标值明细
 */
@interface EADailyGoalItem : EABaseModel

/// Switch: 0 off, 1 on【开关： 0关闭 1打开】
@property(nonatomic, assign) NSInteger sw;


/// Target value【目标值】
@property(nonatomic, assign) NSInteger goal;


/// Initialize【初始化】
/// - Parameters:
///   - onOff: Switch【开关】
///   - goal: Target value【目标值】
+ (instancetype)eaInitWithOnOff:(NSInteger)onOff goal:(NSInteger)goal;

@end







NS_ASSUME_NONNULL_END
