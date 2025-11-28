//
//  EAStressDataModel.h
//  EABluetooth
//
//  Created by Aye on 2021/3/31.
//  File Name:3007:Stress【压力】

#import <EABluetooth/EABaseBigDataModel.h>

NS_ASSUME_NONNULL_BEGIN


/**
 * Stress data model
 * 压力数据模型
 */
@interface EAStressDataModel : EABigDataModel

/// stess【压力】
@property(nonatomic, assign) NSInteger stessValue;

/// Stress data type【压力类型】
@property(nonatomic, assign) EAStressDataType eType;

@end

//  NOT_ANALYSIS_CLASS
@interface EAStressData : EABaseBigDataModel

@property(nonatomic, strong) NSMutableArray<EAStressDataModel*> *sIndexArray;

/// NOT_ANALYSIS_FUNCTION
+ (EAStressData *)getStressData:(NSData *)data;


@end

NS_ASSUME_NONNULL_END
