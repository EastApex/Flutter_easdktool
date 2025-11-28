//
//  EAAutoCheckSleepModel.h
//  EABluetooth
//
//  Created by Aye on 2021/3/19.
//  File Name:16:Sleep Monitoring【睡眠监测】


#import <EABluetooth/EABaseModel.h>
NS_ASSUME_NONNULL_BEGIN



/**
 * id = 16
 * Automatic sleep monitoring
 * 自动睡眠监测设置
 *
 * Cycle: == "Sunday ~ Saturday,
 * 0: off 1: on
 *
 * Eg1:
 * Sunday Monday Tuesday Wednesday Thursday Friday Saturday
 * 0, 1, 1, 0, 0, 1
 * We get 0110001, and we get 1000110 by little endian
 * Convert 1000110 binary to base 10, that is, weekCycleBit is 70 to enable the Monday, Tuesday, and Saturday detection
 *
 * Eg2:
 * Sunday Monday Tuesday Wednesday Thursday Friday Saturday
 * 0, 0, 0, 0, 1, 1
 * You get 0000011, and you get 1100000 by the little end
 * Convert 1100000 binary to base 10. At this time, weekCycleBit is 96
 * If weekCycleBit is 0, the monitoring function is disabled
 * If weekCycleBit is set to 127, the daily monitoring function is enabled
 *
 * 周期：==》周日~周六，
 * 0：关闭 1：开启
 * eg1：
 *
 * 周日 周一 周二 周三 周四 周五 周六
 * 0 1 1 0 0 0 1
 * 得到 0110001，按照小端排序得到 1000110
 * 将 1000110 二进制转为 10进制 即此时 weekCycleBit 为 70 开启 周一二六 检测
 *
 * eg2：
 * 周日 周一 周二 周三 周四 周五 周六
 * 0 0 0 0 0 1 1
 * 得到 0000011，按照小端排序得到 1100000
 * 将 1100000二进制转为 10进制 即此时 weekCycleBit 为 96 开启 周五六 检测
 *
 * weekCycleBit 为0 即 关闭监测功能
 * weekCycleBit 为127 即 开启每天监测功能
 *
 */
@interface EAAutoCheckSleepModel : EABaseModel

/// Cycle【周期】
@property(nonatomic, assign) NSInteger weekCycleBit;

/// Start hour【开始时】
@property(nonatomic, assign) NSInteger beginHour;
/// Start minute【开始分】
@property(nonatomic, assign) NSInteger beginMinute;
/// End hour【结束时】
@property(nonatomic, assign) NSInteger endHour;
/// End minute【结束分】
@property(nonatomic, assign) NSInteger endMinute;


/// Initialize【初始化】
/// - Parameters:
///   - weekCycleBit: Cycle【周期】
///   - beginHour: Start hour【开始时】
///   - beginMinute: Start minute【开始分】
///   - endHour: End hour【结束时】
///   - endMinute: End minute【结束分】
+ (instancetype)eaInitWithWeekCycleBit:(NSInteger )weekCycleBit
                             beginHour:(NSInteger )beginHour
                           beginMinute:(NSInteger )beginMinute
                               endHour:(NSInteger )endHour
                             endMinute:(NSInteger )endMinute;



@end

NS_ASSUME_NONNULL_END
