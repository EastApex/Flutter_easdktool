//
//  EAAppLaunchScreenSport.h
//  EABluetooth
//
//  Created by Aye on 2023/2/3.
//  File Name:54:Launch Screen Sport【投屏运动】

#import <EABluetooth/EABluetooth.h>

NS_ASSUME_NONNULL_BEGIN


/// MARK: -  通道类型类型
typedef NS_ENUM(NSUInteger, EAAppLaunchScreenSportStatus) {
    
    EAAppLaunchScreenSportStatusEnd = 0,
    EAAppLaunchScreenSportStatusStart = 1,
    EAAppLaunchScreenSportStatusPause = 2,
    EAAppLaunchScreenSportStatusContinue = 3
};

/// id = 54 ：
/// id = 54:
///



/**
 * id = 54
 * APP启动手表运动（投屏运动）
 * APP starts watch motion (screen motion)
 *
 * 监听通知【kNTF_EAAppSportRealTime】并实现通知方法获取EAAppSportRealTimeModel对象
 * Listen to the notification [kNTF_EAAppSportRealTime] and implement the notification method to obtain EAAppSportRealTimeModel object
 */
@interface EAAppLaunchScreenSport : EABaseModel

/// Type of motion【运动类型】
@property(nonatomic, assign) EAWatchSportType eSportType;

/// Motion status: 0 End 1 Start 2 Pause 3 Continue【运动状态：0结束 1开启 2暂停 3继续】
@property(nonatomic, assign) EAAppLaunchScreenSportStatus status;

/// Interval for reporting data (Default: 5 seconds) : (unit: second)【手表上报数据间隔时长(默认5秒)：（单位：秒）】
@property(nonatomic, assign) NSInteger interval;

/// Initialization【初始化】
/// - Parameters:
///   - sportType: Type of motion【运动类型】
///   - status: Motion status【运动状态】
///   - interval:Interval for reporting data【手表上报数据间隔时长】
+ (instancetype )eaInitWithSportType:(EAWatchSportType )sportType status:(EAAppLaunchScreenSportStatus)status interval:(NSInteger)interval;
@end

NS_ASSUME_NONNULL_END
