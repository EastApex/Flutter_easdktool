//
//  EAAutoCheckHeartRateModel.h
//  EABluetooth
//
//  Created by Aye on 2021/3/19.
//  File Name:17:HR Monitoring【心率监测】


#import <EABluetooth/EABaseModel.h>

NS_ASSUME_NONNULL_BEGIN



/**
 * id = 17
 * Automatic heart rate monitoring
 * 自动心率监测
 */
@interface EAAutoCheckHeartRateModel : EABaseModel

/// Interval: Unit: minute. 0 indicates that the monitoring function is disabled【间隔时长：单位分钟，0为关闭监测功能】
@property(nonatomic, assign) NSInteger interval;


/// Initialize【初始化】
/// - Parameter interval: Interval duration: in minutes, 0 to turn off the monitoring function【间隔时长：单位分钟，0为关闭监测功能】
+ (instancetype )eaInitAutoCheckHeartRateModelWithInterval:(NSInteger )interval;



@end

NS_ASSUME_NONNULL_END
