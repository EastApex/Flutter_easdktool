//
//  EAHeartRateWaringSettingModel.h
//  EABluetooth
//
//  Created by Aye on 2021/3/22.
//

#import <EABluetooth/EABaseModel.h>

NS_ASSUME_NONNULL_BEGIN


/// 心率报警门限设置
@interface EAHeartRateWaringSettingModel : EABaseModel

/** 开关： 0关闭 1打开 */
@property(nonatomic, assign) NSInteger sw;

/** 心率上限值 */
@property(nonatomic, assign) NSInteger maxHr;

/** 心率下限值 */
@property(nonatomic, assign) NSInteger minHr;


/// MARK: - 获取心率报警门限设置相关信息
/// @param data 数据流
+ (EAHeartRateWaringSettingModel *)getModelByData:(NSData *)data;





@end

NS_ASSUME_NONNULL_END
