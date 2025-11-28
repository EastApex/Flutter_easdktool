//
//  EABloodOxygenDataModel.h
//  EABluetooth
//
//  Created by Aye on 2021/3/23.
//  File Name:3006:Blood Oxygen【血氧】

#import <EABluetooth/EABaseBigDataModel.h>

NS_ASSUME_NONNULL_BEGIN

/**
 * Blood oxygen data model
 * 血氧数据模型
 */
@interface EABloodOxygenDataModel : EABigDataModel

/// blood Oxygen【血氧】
@property(nonatomic, assign) NSInteger bloodOxygenValue;

@end

//  NOT_ANALYSIS_CLASS
@interface EABloodOxygenData : EABaseBigDataModel

@property(nonatomic, strong) NSMutableArray<EABloodOxygenDataModel*> *sIndexArray;

/// NOT_ANALYSIS_FUNCTION
+ (EABloodOxygenData *)getBloodOxygenData:(NSData *)data;


@end

NS_ASSUME_NONNULL_END
