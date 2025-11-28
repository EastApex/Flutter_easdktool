//
//  EASportHrData.h
//  EABluetooth
//
//  Created by Aye on 2023/6/29.
//  File Name:3013:Sport Heart Rate【运动心率】

#import <EABluetooth/EABluetooth.h>

NS_ASSUME_NONNULL_BEGIN

/**
 * Sport heart rate data model
 * 运动心率数据模型
*/
@interface EASportHrModel : EABigDataModel

/// heart rate【心率值】
@property(nonatomic, assign) NSInteger hrValue;

@end

//  NOT_ANALYSIS_CLASS
@interface EASportHrData : EABaseBigDataModel

@property(nonatomic, strong) NSMutableArray<EASportHrModel*> *sIndexArray;

/// NOT_ANALYSIS_FUNCTION
+ (EASportHrData *)getBigData:(NSData *)data;
@end

NS_ASSUME_NONNULL_END
