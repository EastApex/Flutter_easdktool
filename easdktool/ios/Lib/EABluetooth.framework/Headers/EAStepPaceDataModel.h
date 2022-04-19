//
//  EAStepPaceDataModel.h
//  EABluetooth
//
//  Created by Aye on 2021/3/31.
//

#import <EABluetooth/EABaseBigDataModel.h>

NS_ASSUME_NONNULL_BEGIN

///配速数据
@interface EAStepPaceDataModel : EABaseModel

/** 时间戳 */
@property(nonatomic, assign) NSInteger timeStamp;

/** 配速值 */
@property(nonatomic, assign) NSInteger stepPaceValue;

@end

@interface EAStepPaceData : EABaseBigDataModel

/** 配速包（大小详见对应OPTIONS文件） */
@property(nonatomic, strong) NSMutableArray<EAStepPaceDataModel*> *sIndexArray;

+ (EAStepPaceData *)getStepPaceData:(NSData *)data;


@end
NS_ASSUME_NONNULL_END
