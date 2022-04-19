//
//  EAHomeTimeZoneModel.h
//  EABluetooth
//
//  Created by Aye on 2021/3/19.
//

#import <EABluetooth/EABaseModel.h>
NS_ASSUME_NONNULL_BEGIN




/// 家乡时区信息
@interface EAHomeTimeZoneItem : EABaseModel

/** 家乡时区: 0时区、东时区、西时区 */
@property(nonatomic, assign) EATimeZone timeZone;

/** 家乡时区: 时 */
@property(nonatomic, assign) NSInteger timeZoneHour;

/** 家乡时区: 分 */
@property(nonatomic, assign) NSInteger timeZoneMinute;

/** 家乡名称: 最多支持32字节的utf8，字符串（大小详见对应OPTIONS文件） */
@property(nonatomic, copy) NSString *place;




@end





/// 家乡时区
@interface EAHomeTimeZoneModel : EABaseModel

/** 最多支持8个（大小详见对应OPTIONS文件） */
@property(nonatomic, strong) NSMutableArray<EAHomeTimeZoneItem*> *sHomeArray;



/// MARK: - 获取家乡时区信息相关信息
/// @param data 数据流
+ (EAHomeTimeZoneModel *)getModelByData:(NSData *)data;





@end

NS_ASSUME_NONNULL_END
