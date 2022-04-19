//
//  EARestingHeartRateDataModel.h
//  EABluetooth
//
//  Created by Aye on 2021/7/21.
//

#import <EABluetooth/EABaseBigDataModel.h>

NS_ASSUME_NONNULL_BEGIN


@interface EARestingHeartRateModel : EABaseModel

/** 时间戳 */
@property(nonatomic, assign) NSInteger  timeStamp;

/** 心率值 */
@property(nonatomic, assign) NSInteger  hrValue;

@end


@interface EARestingHeartRateDataModel : EABaseBigDataModel

/** 心率包 */
@property(nonatomic, strong) NSMutableArray<EARestingHeartRateModel*> *sIndexArray;

+ (EARestingHeartRateDataModel *)getRestingHeartRateData:(NSData *)data;

@end

NS_ASSUME_NONNULL_END
