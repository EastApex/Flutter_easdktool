//
//  EASleepDataModel.h
//  EABluetooth
//
//  Created by Aye on 2021/3/22.
//  File Name:3002:Sleep【睡眠】

#import <EABluetooth/EABaseBigDataModel.h>

NS_ASSUME_NONNULL_BEGIN

/**
 * A sleep state data model
 * 睡眠状态数据模式
 */
@interface EASleepDataModel : EABigDataModel

/// sleep status【睡眠node】
@property(nonatomic, assign) EASleepNode eSleepNode;

@end

//  NOT_ANALYSIS_CLASS
@interface EAUpLoadSleepData : EABaseBigDataModel

@property(nonatomic, strong) NSMutableArray<EASleepDataModel*> *sIndexArray;

/// NOT_ANALYSIS_FUNCTION
+ (EAUpLoadSleepData *)getSleepData:(NSData *)data;

@end


@interface EAItemSleepModel : NSObject

/// sleep status
@property(nonatomic, assign) EASleepNode eSleepNode;

/// Enter sleep timestamp
@property(nonatomic, assign) NSInteger beginTimestamp;

/// End sleep timestamp
@property(nonatomic, assign) NSInteger endTimestamp;

/// Sleep duration
@property(nonatomic, assign) NSInteger duration;

+ (instancetype)eaInitWithSleepNode:(EASleepNode)eSleepNode beginTimestamp:(NSInteger)beginTimestamp endTimestamp:(NSInteger)endTimestamp duration:(NSInteger)duration;
@end


@interface EAOneSleepModel : NSObject

/// Timestamp of 00:00:00 on a certain day【某日00:00:00时间戳】
@property(nonatomic, assign) NSInteger dayTimestamp;

/// Enter sleep timestamp
@property(nonatomic, assign) NSInteger beginTimestamp;

/// End sleep timestamp
@property(nonatomic, assign) NSInteger endTimestamp;

/// Light sleep time (minutes)【浅睡时长（单位：分钟）】
@property(nonatomic, assign) NSInteger lightSleepMinutes;

/// Deep sleep time (minutes)【深睡时长（单位：分钟）】
@property(nonatomic, assign) NSInteger deepSleepMinutes;

/// Awake time (minutes)【清醒时长（单位：分钟）】
@property(nonatomic, assign) NSInteger awakeSleepMinutes;

/// REM sleep time (minutes)【眼动时长（单位：分钟）】
@property(nonatomic, assign) NSInteger remSleepMinutes;

/// Number of awakenings【清醒次数】
@property(nonatomic, assign) NSInteger awakeCount;

/// Total sleep time (minutes)【总睡眠时长（单位：分钟）】
@property(nonatomic, assign) NSInteger totalMinutes;

/// Timestamps of all sleep states, separated by ,【所有睡眠状态开始时间戳，以,隔开】
@property(nonatomic, assign) NSString *timestamps;

/// All sleep states, separated by ,【所有睡眠状态，以,隔开】
@property(nonatomic, assign) NSString *sleepNodes;

/// All sleep - state information【所有睡眠状态信息】
@property(nonatomic,strong) NSArray<EAItemSleepModel *> *itemSleepArray;
@end


@interface EADaySleepModel : NSObject

/// [All one - time sleep information for the day]【当日所有一次睡眠信息】
@property(nonatomic,strong) NSArray<EAOneSleepModel *> *oneSleepArray;

/// Timestamp of 00:00:00 on a certain day【某日00:00:00时间戳】
@property(nonatomic, assign) NSInteger dayTimestamp;

@end




NS_ASSUME_NONNULL_END
