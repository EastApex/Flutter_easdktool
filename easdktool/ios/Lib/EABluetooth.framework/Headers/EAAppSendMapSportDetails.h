//
//  EAAppSendSportDetails.h
//  EABluetooth
//
//  Created by Aye on 2022/12/14.
//  File Name:47:Send Sport Details【发送运动数据】


#import <EABluetooth/EABluetooth.h>

NS_ASSUME_NONNULL_BEGIN


/**
 * id = 47
 * send APP to record the motion information [after the motion is suspended, you need to stop sending this command, if you need to resume the motion, you can continue to send this name to the watch]
 * 发送APP记录运动信息[运动暂停后，需要停止发送此命令，如果需要恢复运动，继续发此命名给手表即可]
 *
 */
@interface EAAppSendMapSportDetails : EABaseModel

/// Current total exercise duration (unit: second)【当前总运动时长(单位:秒)】
@property(nonatomic, assign) NSInteger duration;

/// Current total distance (unit: cm)【当前总距离（单位:厘米）】
@property(nonatomic, assign) NSInteger distance;

/// Current pace (unit: S/KM)【当前配速（单位: S/KM）】
@property(nonatomic, assign) NSInteger pace;

/// Calories to be reported back after disconnection【断连后需要回传卡路里】
@property(nonatomic, assign) NSInteger calorie;

/// Steps to be reported back after disconnection【断连后需要回传步数】
@property(nonatomic, assign) NSInteger step;

/// Total number to be reported back after disconnection (number of times of rope - skipping currently)【断连后需要回传总次数（跳绳当前跳了多少下）】
@property(nonatomic, assign) NSInteger count;

/// Current speed (unit: M/H)【当前速度（单位: M/H） 】
@property(nonatomic, assign) NSInteger speed;


/// Initialize
/// - Parameters:
///   - duration: duration
///   - distance: distance
///   - pace: pace
+ (instancetype )eaInitWithDuration:(NSInteger)duration distance:(NSInteger)distance pace:(NSInteger)pace;


/// Initialize
/// - Parameters:
///   - duration: duration 
///   - distance: distance
///   - pace: pace
///   - calorie: calorie
///   - step: step
///   - count: count
+ (instancetype )eaInitWithDuration:(NSInteger)duration distance:(NSInteger)distance pace:(NSInteger)pace calorie:(NSInteger)calorie step:(NSInteger)step count:(NSInteger)count;

@end

NS_ASSUME_NONNULL_END
