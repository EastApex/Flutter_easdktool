//
//  EANotDisturbModel.h
//  EABluetooth
//
//  Created by Aye on 2021/3/19.
//

#import <EABluetooth/EABaseModel.h>
NS_ASSUME_NONNULL_BEGIN


/// 免打扰设置
@interface EANotDisturbModel : EABaseModel

/** 开关： 0关闭 1打开 */
@property(nonatomic, assign) NSInteger sw;

/** 开始时间 ：小时 */
@property(nonatomic, assign) NSInteger beginHour;

/** 开始时间 ：分钟 */
@property(nonatomic, assign) NSInteger beginMinute;

/** 结束时间 ：小时 */
@property(nonatomic, assign) NSInteger endHour;

/** 结束时间 ：分钟 */
@property(nonatomic, assign) NSInteger endMinute;


/// MARK: - 获取免打扰设置相关信息
/// @param data 数据流
+ (EANotDisturbModel *)getModelByData:(NSData *)data;





@end

NS_ASSUME_NONNULL_END
