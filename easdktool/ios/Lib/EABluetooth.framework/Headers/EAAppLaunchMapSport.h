//
//  EAAppLaunchSport.h
//  EABluetooth
//
//  Created by Aye on 2022/12/14.
//  File Name:46: App launches Sport【App发起运动】


#import <EABluetooth/EABluetooth.h>

NS_ASSUME_NONNULL_BEGIN


/**
 * id = 46
 * App发起运动(App地图地图需要参与)
 * App launches campaign (App map requires participation)
 *
 *
 * 监听通知【kNTF_EAAppSportRealTime】并实现通知方法获取EAAppSportRealTimeModel对象
 * Listen to the notification [kNTF_EAAppSportRealTime] and implement the notification method to obtain EAAppSportRealTimeModel object
 */
@interface EAAppLaunchMapSport : EABaseModel

/// Motion status: 0 Off 1 On 2 Pause(See class EAAppSendMapSportDetails if you resume sports after pausing.)[运动状态：0关闭 1开启 2暂停（若暂停后恢复运动，请查看 class EAAppSendMapSportDetails ）]
@property(nonatomic, assign) EAAppLaunchSportStatus status;

/// Motion type (Only motion type numbered from 1 to 6)[运动类型(仅支持运动类型序号为 1~6)]
@property(nonatomic, assign) EAWatchSportType eSportType;

/// Interval for reporting data (Default: 5 seconds)【手表上报数据间隔时长(默认5秒)】
@property(nonatomic, assign) NSInteger interval;

/// Detection status (do not enable motion, only detect whether motion can be enabled)【检测状态（不开启运动，只检测运动是否可以开启）】
@property(nonatomic, assign) NSInteger checkSport;


/// init
/// - Parameters:
///   - status: Motion Status
///   - eSportType: Motion Type
///   - interval: Interval for reporting data
+ (instancetype )eaInitWithStatus:(EAAppLaunchSportStatus)status sportType:(EAWatchSportType)eSportType interval:(NSInteger)interval;


/// Detects whether motion can be turned on
+ (void)eaInitCheckSport:(void (^)(EARespondCodeType eaRespondCodeType))result;

@end

NS_ASSUME_NONNULL_END
