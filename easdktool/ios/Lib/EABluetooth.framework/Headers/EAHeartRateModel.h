//
//  EAHeartRateModel.h
//  EABluetooth
//
//  Created by Aye on 2021/3/22.
//  File Name:3003:Heart Rate【心率】

#import <EABluetooth/EABaseBigDataModel.h>

NS_ASSUME_NONNULL_BEGIN


/**
 * Heart rate data model
 * 心率数据模型
 */
@interface EAHeartRateModel : EABigDataModel

/// heart rate【心率值】
@property(nonatomic, assign) NSInteger hrValue;

@end

//  NOT_ANALYSIS_CLASS
@interface EAUploadHeartRateData : EABaseBigDataModel

@property(nonatomic, strong) NSMutableArray<EAHeartRateModel*> *sIndexArray;

/// NOT_ANALYSIS_FUNCTION
+ (EAUploadHeartRateData *)getHeartRatetData:(NSData *)data;

@end



NS_ASSUME_NONNULL_END
