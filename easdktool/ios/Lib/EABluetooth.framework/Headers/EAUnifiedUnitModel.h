//
//  EALengthUnitModel.h
//  EABluetooth
//
//  Created by Aye on 2021/3/19.
//

#import <EABluetooth/EABaseModel.h>
NS_ASSUME_NONNULL_BEGIN


/// 统一单位
@interface EAUnifiedUnitModel : EABaseModel


@property(nonatomic, assign) EAUnifiedUnit unit;

/// MARK: - 获取长度单位相关信息
/// @param data 数据流
+ (EAUnifiedUnitModel *)getModelByData:(NSData *)data;



@end

NS_ASSUME_NONNULL_END
