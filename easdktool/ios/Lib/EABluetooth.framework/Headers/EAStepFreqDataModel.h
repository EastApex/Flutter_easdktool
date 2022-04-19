//
//  EAStepFreqDataModel.h
//  EABluetooth
//
//  Created by Aye on 2021/3/31.
//

#import <EABluetooth/EABaseBigDataModel.h>

NS_ASSUME_NONNULL_BEGIN

/// 步频数据
@interface EAStepFreqDataModel : EABaseModel

/** 时间戳 */
@property(nonatomic, assign) NSInteger timeStamp;

/** 步频值 */
@property(nonatomic, assign) NSInteger stepFreqValue;

@end

@interface EAStepFreqData : EABaseBigDataModel

/** 步频包（大小详见对应OPTIONS文件） */
@property(nonatomic, strong) NSMutableArray<EAStepFreqDataModel*> *sIndexArray;

+ (EAStepFreqData *)getStepFreqData:(NSData *)data;


@end
NS_ASSUME_NONNULL_END
