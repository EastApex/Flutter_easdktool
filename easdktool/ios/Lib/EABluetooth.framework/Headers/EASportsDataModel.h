//
//  EASportsDataModel.h
//  EABluetooth
//
//  Created by Aye on 2021/3/23.
//

#import <EABluetooth/EABaseBigDataModel.h>

NS_ASSUME_NONNULL_BEGIN


/// 多运动数据
@interface EASportsDataModel : EABaseModel

/** 运动类型 */
@property(nonatomic, assign) EASportType eType;

/** 起始时间戳 */
@property(nonatomic, assign) NSInteger beginTimeStamp;

/** 停止时间戳 */
@property(nonatomic, assign) NSInteger endTimeStamp;

/** 步数 */
@property(nonatomic, assign) NSInteger steps;

/** 卡路里（单位:小卡) */
@property(nonatomic, assign) NSInteger calorie;

/** 距离 （单位:厘米） */
@property(nonatomic, assign) NSInteger distance;

/** 运动时长(单位:秒) */
@property(nonatomic, assign) NSInteger duration;


/** 训练效果 正常心率 时长(单位:秒) */
@property(nonatomic, assign) NSInteger trainingEffectNormal;

/** 训练效果 热身心率 时长(单位:秒) */
@property(nonatomic, assign) NSInteger trainingEffectWarmUp;

/** 训练效果 消耗脂肪 时长(单位:秒) */
@property(nonatomic, assign) NSInteger trainingEffectFatconsumption;

/** 训练效果 有氧心率 时长(单位:秒) */
@property(nonatomic, assign) NSInteger trainingEffectAerobic;

/** 训练效果 无氧心率 时长(单位:秒) */
@property(nonatomic, assign) NSInteger trainingEffectAnaerobic;

/** 训练效果 极限心率 时长(单位:秒) */
@property(nonatomic, assign) NSInteger trainingEffectLimit;

/** 平均心率 */
@property(nonatomic, assign) NSInteger averageHeartRate;

/** 平均体温（单位：摄氏度） */
@property(nonatomic, assign) float averageTemperature;

/** 平均速度（单位: KM/H *100倍） */
@property(nonatomic, assign) float averageSpeed;

/** 平均配速（单位: S/KM） */
@property(nonatomic, assign) float averagePace;

/** 平均步频（单位: SPM 步每分钟） */
@property(nonatomic, assign) float averageStepFreq;

/** 平均步距（单位:厘米） */
@property(nonatomic, assign) float averageStride;

/** 平均海拔（单位:厘米） */
@property(nonatomic, assign) float averageAltitude;

/** 最大心率 */
@property(nonatomic, assign) NSInteger averageHeartRateMax;

/** 最小心率 */
@property(nonatomic, assign) NSInteger averageHeartRateMin;

@end

@interface EAUploadSportsData : EABaseBigDataModel


/** 多运动数据包（大小详见对应OPTIONS文件） */
@property(nonatomic,strong) NSMutableArray<EASportsDataModel*> *sIndexArray;

+ (EAUploadSportsData *)getSportsData:(NSData *)data;


@end

NS_ASSUME_NONNULL_END
