//
//  EATemplateBobyData.h
//  EABluetooth
//
//  Created by Aye on 2024/6/13.
//  File Name:3014:Template【温度】


#import <EABluetooth/EABluetooth.h>

NS_ASSUME_NONNULL_BEGIN

/**
 * Template data model
 * 温度数据模型
*/
@interface EATemplateBobyDataItem : EABigDataModel

/// Body temperature (in degrees Celsius)【体温 （单位摄氏度）】
@property(nonatomic, assign) NSInteger body;

/// Ambient temperature (in degrees Celsius)【环境温度（单位摄氏度）】
@property(nonatomic, assign) NSInteger environment;

@end


//  NOT_ANALYSIS_CLASS
@interface EATemplateBodyData : EABaseBigDataModel

@property(nonatomic, strong) NSMutableArray<EATemplateBobyDataItem*> *sIndexArray;

/// NOT_ANALYSIS_FUNCTION
+ (EATemplateBodyData *)getBigData:(NSData *)data;

@end

NS_ASSUME_NONNULL_END
