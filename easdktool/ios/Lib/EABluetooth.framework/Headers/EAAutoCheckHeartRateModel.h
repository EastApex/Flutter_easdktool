//
//  EAAutoCheckHeartRateModel.h
//  EABluetooth
//
//  Created by Aye on 2021/3/19.
//

#import <EABluetooth/EABaseModel.h>

NS_ASSUME_NONNULL_BEGIN


/// 自动心率监测
@interface EAAutoCheckHeartRateModel : EABaseModel

/** 间隔时长：单位分钟，0为关闭监测功能 */
@property(nonatomic, assign) NSInteger interval;


/// MARK: - 获取自动心率监测设置相关信息
/// @param data 数据流
+ (EAAutoCheckHeartRateModel *)getModelByData:(NSData *)data;






@end

NS_ASSUME_NONNULL_END
