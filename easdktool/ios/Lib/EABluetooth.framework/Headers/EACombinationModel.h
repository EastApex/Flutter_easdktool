//
//  EACombination.h
//  EABluetooth
//
//  Created by Aye on 2021/5/8.
//  File Name:30:Basic Device Information【设备基本信息】


#import <EABluetooth/EABaseModel.h>

NS_ASSUME_NONNULL_BEGIN



/**
 * id = 30
 * Basic Device Information[cannot be modified]
 * 设备基本信息【不支持修改】
 */
@interface EACombinationModel : EABaseModel

/// charging state 【充电状态】
@property(nonatomic, assign) EABatteryStatus eBatStatus;

/// electric quantity value 【电量值 】
@property(nonatomic, assign) NSInteger batLevel;

/// Automatic pressure monitoring switch【自动压力监测开关】
@property(nonatomic, assign) NSInteger autoPressureSw;

/// Automatic sedentary monitoring switch 【自动久坐监测开关】
@property(nonatomic, assign) NSInteger autoSedentarinessSw;

/// Raise the screen setting switch【抬手亮屏设置开关】
@property(nonatomic, assign) NSInteger gesturesSw;

/// vibrational mode【振动模式】
@property(nonatomic, assign) EAVibrateIntensityType eVibrateIntensity;

/// way of wearing【佩戴方式】
@property(nonatomic, assign) EAWearWayType eHandInfo;

/// Units【单位】
@property(nonatomic, assign) EAUnifiedUnit eUnitFormat;

/// Heart rate monitoring switch【心率监测开关】
@property(nonatomic, assign) NSInteger autoCheckHrSw;

/// Do not disturb the mode switch【勿扰模式开关】
@property(nonatomic, assign) NSInteger notDisturbSw;

@property(nonatomic, assign) NSInteger setVibrateIntensity;

/// Dial ID, (0 indicates online custom dial, 1~n, internal dial number)  【表盘id, (0代表在线自定义表盘，1~n，内置表盘编号)】
@property(nonatomic, assign) NSInteger wfId;

///  Online custom dial ID【在线自定义表盘id 】
//@property(nonatomic, copy) NSString *userWfId;

/// Blood oxygen monitoring switch【血氧监测开关 】
@property(nonatomic, assign) NSInteger sleepBloodOxygenSw;

/// stress monitoring switch[【压力监测开关 】
//@property(nonatomic, assign) NSInteger stressSw;





@end

NS_ASSUME_NONNULL_END
