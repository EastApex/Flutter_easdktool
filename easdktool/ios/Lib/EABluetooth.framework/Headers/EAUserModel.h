//
//  EAUserModel.h
//  EABluetooth
//
//  Created by Aye on 2021/3/18.
//  File Name:4:User Information【用户信息】


#import <EABluetooth/EABaseModel.h>
NS_ASSUME_NONNULL_BEGIN



/**
 * id = 4
 * User information
 * 用户信息
*/
@interface EAUserModel : EABaseModel

/// sex【性别】
@property(nonatomic, assign) EASexType sexType;

/// age【年龄】
@property(nonatomic, assign) NSInteger age;

/// height:cm【身高，单位：厘米、
@property(nonatomic, assign) NSInteger height;

/// weight:g【体重，单位：克】
@property(nonatomic, assign) NSInteger weight;

/// Wear way【穿戴方式】
@property(nonatomic, assign) EAWearWayType wearWayType __attribute__((deprecated));

/// Skin Color【肤色】
@property(nonatomic, assign) EASkinColorType eSkinColor __attribute__((deprecated));









@end

NS_ASSUME_NONNULL_END
