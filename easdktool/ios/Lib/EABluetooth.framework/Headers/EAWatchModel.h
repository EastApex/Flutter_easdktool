//
//  EAWatchModel.h
//  EABluetooth
//
//  Created by Aye on 2021/3/18.
//

#import <EABluetooth/EABaseModel.h>

NS_ASSUME_NONNULL_BEGIN



/// 手表信息
@interface EAWatchModel : EABaseModel

/** 手表id：最多支持32字节的utf8，字符串 */
@property(nonatomic, copy) NSString *id_p;

/** 设备类型：最多支持32字节的utf8，字符串 */
@property(nonatomic, copy) NSString *type;

/** 固件版本：最多支持64字节的utf8，字符串 */
@property(nonatomic, copy) NSString *firmwareVersion;

/** 是否绑定：EABindingType */
@property(nonatomic, assign) EABindingType bindingType;

/** 用户id */
@property(nonatomic, strong) NSString *userId;


/** 手表agps的最后更新时间戳，0为没更新过 */
@property(nonatomic, assign) NSInteger agpsUpdateTimestamp;


/// 获取手表相关信息
/// @param data 数据流
+ (EAWatchModel *)getModelByData:(NSData *)data;




@end

NS_ASSUME_NONNULL_END
