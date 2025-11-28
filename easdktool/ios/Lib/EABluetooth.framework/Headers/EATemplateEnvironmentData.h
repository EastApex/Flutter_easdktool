//
//  EATemplateEnvironmentData.h
//  EABluetooth
//
//  Created by Aye on 2024/6/19.
//  File Name:3015:Environmen Template【环境温度】

#import <EABluetooth/EABluetooth.h>

NS_ASSUME_NONNULL_BEGIN


/**
 * Environmen template data model
 * 环境温度数据模型
*/
@interface EATemplateEnvironmentDataItem : EABaseBigDataModel

/// Ambient temperature (in degrees Celsius)【环境温度（单位摄氏度）】
@property(nonatomic, assign) NSInteger environment;

@end


//  NOT_ANALYSIS_CLASS
@interface EATemplateEnvironmentData : EABaseBigDataModel

@property(nonatomic, strong) NSMutableArray<EATemplateEnvironmentDataItem *> *sIndexArray;

/// NOT_ANALYSIS_FUNCTION
+ (EATemplateEnvironmentData *)getBigData:(NSData *)data;
@end

NS_ASSUME_NONNULL_END
