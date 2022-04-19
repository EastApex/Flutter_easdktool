//
//  EASleepDataModel.h
//  EABluetooth
//
//  Created by Aye on 2021/3/22.
//

#import <EABluetooth/EABaseBigDataModel.h>

NS_ASSUME_NONNULL_BEGIN

/// 睡眠数据
@interface EASleepDataModel : EABaseModel

/** 时间戳 */
@property(nonatomic, assign) NSInteger timeStamp;

/** 睡眠node */
@property(nonatomic, assign) EASleepNode eSleepNode;

@end


@interface EAUpLoadSleepData : EABaseBigDataModel


/** 睡眠包（大小详见对应OPTIONS文件） */
@property(nonatomic, strong) NSMutableArray<EASleepDataModel*> *sIndexArray;

+ (EAUpLoadSleepData *)getSleepData:(NSData *)data;

@end



NS_ASSUME_NONNULL_END
