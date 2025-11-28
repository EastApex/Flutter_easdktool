//
//  EASportDataModel.h
//  EABluetooth
//
//  Created by Aye on 2021/3/22.
//  File Name:3001:Step【步数】

#import <EABluetooth/EABaseBigDataModel.h>

NS_ASSUME_NONNULL_BEGIN



/**
 * EAStepModel is a model for daily step, calorie, and distance recording.
 * EAStepModel是日常记录步数、卡路里、距离模型
 */
@interface EAStepModel : EABigDataModel

/// steps【运动数据：步数】
@property(nonatomic, assign) NSInteger steps;

/// calorie (cal)【运动数据：卡路里（单位:小卡)】
@property(nonatomic, assign) NSInteger calorie;

/// distance (cm) 【运动数据：距离 （单位:厘米）】
@property(nonatomic, assign) NSInteger distance;

/// duration (s)【运动数据：运动时长(单位:秒)】
@property(nonatomic, assign) NSInteger duration;

/// average heart rate【运动数据：平均心率】
@property(nonatomic, assign) NSInteger averageHeartRate;


@end

//  NOT_ANALYSIS_CLASS
@interface EAStepData : EABaseBigDataModel

@property(nonatomic, strong) NSMutableArray<EAStepModel*> *sIndexArray;

/// NOT_ANALYSIS_FUNCTION
+ (EAStepData *)getSportData:(NSData *)data;

@end



NS_ASSUME_NONNULL_END
