//
//  EASleepScoreData.h
//  EABluetooth
//
//  Created by Aye on 2023/6/29.
//  File Name:3012:Sleep Score【睡眠得分】

#import <EABluetooth/EABluetooth.h>

NS_ASSUME_NONNULL_BEGIN

/**
 * Sleep score data model
 * 睡眠得分数据模型
*/
@interface EASleepScoreModel : EABigDataModel

/// sleep score【睡眠得分】
@property(nonatomic, assign) NSInteger sleepScore;

/// begin timeStamp【睡眠起始时间戳】
@property(nonatomic, assign) NSInteger beginTimeStamp;

/// end timeStamp【睡眠停止时间戳】
@property(nonatomic, assign) NSInteger endTimeStamp;

@end

//  NOT_ANALYSIS_CLASS
@interface EASleepScoreData : EABaseBigDataModel

@property(nonatomic, strong) NSMutableArray<EASleepScoreModel*> *sIndexArray;

/// NOT_ANALYSIS_FUNCTION
+ (EASleepScoreData *)getBigData:(NSData *)data;


@end

NS_ASSUME_NONNULL_END
