
//  EAHomeTimeZoneModel.h
//  EABluetooth
//
//  Created by Aye on 2021/3/19.
//  File Name:14:World Time【世界时间】

#import <EABluetooth/EABaseModel.h>
NS_ASSUME_NONNULL_BEGIN





/**
 * id:14
 * World Time
 * 世界时间
 */
@interface EAHomeTimeZoneItem : EABaseModel

/// Time zone: 0 time zone, east time zone, west time zone【时区: 0时区、东时区、西时区】
@property(nonatomic, assign) EATimeZone timeZone;

/// Time zone: hour【时区: 时】
@property(nonatomic, assign) NSInteger timeZoneHour;

/// Time zone: minute【时区: 分】
@property(nonatomic, assign) NSInteger timeZoneMinute;

/// Home name: Supports up to 32 bytes of utf8【家名称: 最多支持32字节的utf8，】
@property(nonatomic, copy) NSString *place;




@end





/// 家乡时区
@interface EAHomeTimeZoneModel : EABaseModel

@property(nonatomic, strong) NSMutableArray<EAHomeTimeZoneItem*> *sHomeArray;








@end

NS_ASSUME_NONNULL_END
