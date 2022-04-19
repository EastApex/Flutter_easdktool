//
//  EACombination.h
//  EABluetooth
//
//  Created by Aye on 2021/5/8.
//

#import <EABluetooth/EABaseModel.h>

NS_ASSUME_NONNULL_BEGIN


/// 设备基本信息
@interface EACombinationModel : EABaseModel

/** 充电状态 */
@property(nonatomic, assign) EABatteryStatus eBatStatus;

/** 电量值 */
@property(nonatomic, assign) NSInteger batLevel;

/** 自动压力监测开关 */
@property(nonatomic, assign) NSInteger autoPressureSw;

/** 自动久坐监测开关 */
@property(nonatomic, assign) NSInteger autoSedentarinessSw;

/** 抬手亮屏设置开关 */
@property(nonatomic, assign) NSInteger gesturesSw;

/** 振动模式 */
@property(nonatomic, assign) EAVibrateIntensityType eVibrateIntensity;

/** 佩戴方式 */
@property(nonatomic, assign) EAWearWayType eHandInfo;

/** 单位设置 */
@property(nonatomic, assign) EAUnifiedUnit eUnitFormat;

/** 心率监测开关 */
@property(nonatomic, assign) NSInteger autoCheckHrSw;

/** 勿扰模式开关 */
@property(nonatomic, assign) NSInteger notDisturbSw;

/** 是否单独设定振动模式 0:不单独设置 1:单独设置 */
@property(nonatomic, assign) BOOL setVibrateIntensity;

/** 表盘id, (0代表在线自定义表盘，1~n，内置表盘编号) */
@property(nonatomic, assign) NSInteger wfId;

/** 在线自定义表盘id */
@property(nonatomic, strong) NSString *userWfId;

/// MARK: - 获取设备基本信息
+ (EACombinationModel *)getModelByData:(NSData *)data;

@end

NS_ASSUME_NONNULL_END
