//
//  EATemplateEnvironmentData.h
//  EABluetooth
//
//  Created by Aye on 2024/6/19.
//

#import <EABluetooth/EABluetooth.h>

NS_ASSUME_NONNULL_BEGIN



@interface EATemplateEnvironmentDataItem : EABaseBigDataModel

/** 环境温度（单位摄氏度） */
@property(nonatomic, assign) NSInteger environment;

@end

@interface EATemplateEnvironmentData : EABaseBigDataModel

@property(nonatomic, strong) NSMutableArray<EATemplateEnvironmentDataItem *> *sIndexArray;

+ (EATemplateEnvironmentData *)getBigData:(NSData *)data;
@end

NS_ASSUME_NONNULL_END
