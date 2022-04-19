//
//  EABloodOxygenDataModel.h
//  EABluetooth
//
//  Created by Aye on 2021/3/23.
//

#import <EABluetooth/EABaseBigDataModel.h>

NS_ASSUME_NONNULL_BEGIN


/// 血氧数据
@interface EABloodOxygenDataModel : EABaseModel

/** 时间戳 */
@property(nonatomic, assign) NSInteger timeStamp;

/** 血氧值 */
@property(nonatomic, assign) NSInteger bloodOxygenValue;

@end

@interface EABloodOxygenData : EABaseBigDataModel


/** 血氧包（大小详见对应OPTIONS文件） */
@property(nonatomic, strong) NSMutableArray<EABloodOxygenDataModel*> *sIndexArray;

+ (EABloodOxygenData *)getBloodOxygenData:(NSData *)data;


@end

NS_ASSUME_NONNULL_END
