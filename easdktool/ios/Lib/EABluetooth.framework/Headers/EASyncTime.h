//
//  EASyncTime.h
//  EABluetooth
//
//  Created by Aye on 2021/3/19.
//

#import <EABluetooth/EABaseModel.h>
NS_ASSUME_NONNULL_BEGIN


/// 同步时间
@interface EASyncTime : EABaseModel

/** 年 */
@property(nonatomic, assign) NSInteger year;

/** 月 */
@property(nonatomic, assign) NSInteger month;

/** 日 */
@property(nonatomic, assign) NSInteger day;

/** 时 */
@property(nonatomic, assign) NSInteger hour;

/** 分 */
@property(nonatomic, assign) NSInteger minute;

/** 秒 */
@property(nonatomic, assign) NSInteger second;

/** 小时制 */
@property(nonatomic, assign) EATimeHourType timeHourType;

/** 当前时区：0时区、东时区、西时区 */
@property(nonatomic, assign) EATimeZone timeZone;

/** 当前时区:时 */
@property(nonatomic, assign) NSInteger timeZoneHour;

/** 当前时区:分 */
@property(nonatomic, assign) NSInteger timeZoneMinute;

/** 同步模式：正常、同步至机芯 */
@property(nonatomic, assign) EASyncType syncType;



/// 获取同步时间相关信息
/// @param data 数据流
+ (EASyncTime *)getModelByData:(NSData *)data;



/// 获取当前时间
+ (EASyncTime *)getCurrentTime;




@end

NS_ASSUME_NONNULL_END
