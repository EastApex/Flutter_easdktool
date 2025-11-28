//
//  EALengthUnitModel.h
//  EABluetooth
//
//  Created by Aye on 2021/3/19.
//  File Name:11:Watch Unit【单位】

#import <EABluetooth/EABaseModel.h>
NS_ASSUME_NONNULL_BEGIN


/**
 * id = 11
 * Watch unit(Metric , imperial）
 * 手表单位（公制、英制）
 */
@interface EAUnifiedUnitModel : EABaseModel


/// unit type 【单位】
@property(nonatomic, assign) EAUnifiedUnit unit;



/// Initialize【初始化】
///- Parameter languageType: unit type 【单位】
+ (instancetype)eaInitWithUnit:(EAUnifiedUnit )unit;
@end

NS_ASSUME_NONNULL_END
