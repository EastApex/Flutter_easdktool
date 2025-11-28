//
//  EARestingHeartRateDataModel.h
//  EABluetooth
//
//  Created by Aye on 2021/7/21.
//  File Name:3010:Resting Heart Rate【静息心率】

#import <EABluetooth/EABaseBigDataModel.h>

NS_ASSUME_NONNULL_BEGIN

/**
 * Resting heart rate data model
 * 静息心率数据模型
*/
@interface EARestingHeartRateModel : EABigDataModel

/// heart rate【心率值】
@property(nonatomic, assign) NSInteger  hrValue;

@end

//  NOT_ANALYSIS_CLASS
@interface EARestingHeartRateDataModel : EABaseBigDataModel

@property(nonatomic, strong) NSMutableArray<EARestingHeartRateModel*> *sIndexArray;

/// NOT_ANALYSIS_FUNCTION
+ (EARestingHeartRateDataModel *)getRestingHeartRateData:(NSData *)data;

@end

NS_ASSUME_NONNULL_END
