//
//  EAUserModel.h
//  EABluetooth
//
//  Created by Aye on 2021/3/18.
//

#import <EABluetooth/EABaseModel.h>
NS_ASSUME_NONNULL_BEGIN


/// User information
/// 用户信息
@interface EAUserModel : EABaseModel

/// sex
/// 性别
@property(nonatomic, assign) EASexType sexType;

/// age
/// 年龄
@property(nonatomic, assign) NSInteger age;

/// height:unit cm
/// 身高，单位：厘米
@property(nonatomic, assign) NSInteger height;

/// weight:unit g
/// 体重，单位：克
@property(nonatomic, assign) NSInteger weight;

/// Wear way
/// 穿戴方式
@property(nonatomic, assign) EAWearWayType wearWayType;

/// 肤色 
@property(nonatomic, assign) EASkinColorType eSkinColor;

/// 获取用户相关信息
/// @param data 数据流
+ (EAUserModel *)getModelByData:(NSData *)data;







@end

NS_ASSUME_NONNULL_END
