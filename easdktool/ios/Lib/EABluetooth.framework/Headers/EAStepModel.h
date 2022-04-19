//
//  EASportDataModel.h
//  EABluetooth
//
//  Created by Aye on 2021/3/22.
//

#import <EABluetooth/EABaseBigDataModel.h>

NS_ASSUME_NONNULL_BEGIN



/// 日常步数
@interface EAStepModel : EABaseModel

/** 时间戳 */
@property(nonatomic, assign) NSInteger timeStamp;

/** 运动数据：步数 */
@property(nonatomic, assign) NSInteger steps;

/** 运动数据：卡路里（单位:小卡) */
@property(nonatomic, assign) NSInteger calorie;

/** 运动数据：距离 （单位:厘米） */
@property(nonatomic, assign) NSInteger distance;

/** 运动数据：运动时长(单位:秒) */
@property(nonatomic, assign) NSInteger duration;

/** 运动数据：平均心率 */
@property(nonatomic, assign) NSInteger averageHeartRate;


@end


/// 手机端获取设备上传运动数据
@interface EAStepData : EABaseBigDataModel

/** 运动包（大小详见对应OPTIONS文件） */
@property(nonatomic, strong) NSMutableArray<EAStepModel*> *sIndexArray;

+ (EAStepData *)getSportData:(NSData *)data;

@end



NS_ASSUME_NONNULL_END
