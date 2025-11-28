//
//  EASendRealTimeDataOnOff.h
//  EABluetooth
//
//  Created by Aye on 2023/2/3.
//  File Name:53:Real-time Data Switch【实时数据开关】


#import <EABluetooth/EABluetooth.h>

NS_ASSUME_NONNULL_BEGIN

/**
 * id = 53
 * 实时数据开关
 * real-time data switch
 */
@interface EASendRealTimeDataOnOff : EABaseModel

/// 0 off, 1 on 【0关，1开】
@property(nonatomic, assign) BOOL sw;

/// Initialization【初始化】
/// - Parameters:
///   - sw: on-off;0 off, 1 on 【0关，1开】
+ (instancetype )eaInitWithOnOff:(BOOL)sw;


@end

NS_ASSUME_NONNULL_END
