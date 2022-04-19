//
//  EAWeightUnitModel.h
//  EABluetooth
//
//  Created by Aye on 2021/3/22.
//

#import <EABluetooth/EABaseModel.h>

NS_ASSUME_NONNULL_BEGIN

@interface EAWeightUnitModel : EABaseModel

@property(nonatomic, assign) EAWeightUnit unit;

/// MARK: - 获取重量单位相关信息
/// @param data 数据流
+ (EAWeightUnitModel *)getModelByData:(NSData *)data;



@end

NS_ASSUME_NONNULL_END
