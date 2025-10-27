//  NOT_ANALYSIS_CLASS
//  EATestHrModel.h
//  EABluetooth
//
//  Created by Aye on 2021/8/21.
//

#import <EABluetooth/EABaseBigDataModel.h>

NS_ASSUME_NONNULL_BEGIN

@interface EATestHrModel : EABigDataModel

@property(nonatomic, assign) NSInteger hrValue;

/// NOT_ANALYSIS_FUNCTION
+ (EATestHrModel *)getTestHeartRateData:(NSData *)data;

@end

NS_ASSUME_NONNULL_END
