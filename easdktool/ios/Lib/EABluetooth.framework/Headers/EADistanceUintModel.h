//
//  EADistanceUintModel.h
//  EABluetooth
//
//  Created by Aye on 2021/3/22.
//

#import <EABluetooth/EABaseModel.h>

NS_ASSUME_NONNULL_BEGIN


/// 距离单位
@interface EADistanceUintModel : EABaseModel

@property(nonatomic, assign) EADistanceUnit unit;

/// MARK: - 获取距离单位相关信息
/// @param data 数据流
+ (EADistanceUintModel *)getModelByData:(NSData *)data;






@end

NS_ASSUME_NONNULL_END
