//  NOT_ANALYSIS_CLASS
//  EALogData.h
//  EABluetooth
//
//  Created by Aye on 2023/11/9.
//

#import <EABluetooth/EABluetooth.h>

NS_ASSUME_NONNULL_BEGIN

@interface EADebugLogData : EABaseBigDataModel

/** 透传的log */
@property(nonatomic, strong) NSString *log;

/// NOT_ANALYSIS_FUNCTION
+ (EADebugLogData *)getBigData:(NSData *)data;
@end

NS_ASSUME_NONNULL_END
