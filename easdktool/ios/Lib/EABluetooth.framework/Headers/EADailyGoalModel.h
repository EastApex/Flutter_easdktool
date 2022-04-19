//
//  EADailyGoalModel.h
//  EABluetooth
//
//  Created by Aye on 2021/3/19.
//

#import <EABluetooth/EABaseModel.h>
NS_ASSUME_NONNULL_BEGIN

/// 日常目标值明细
@interface EADailyGoalItem : EABaseModel

/** 开关： 0关闭 1打开 */
@property(nonatomic, assign) NSInteger sw;

/** 目标值 */
@property(nonatomic, assign) NSInteger goal;

@end



/// 日常目标值设置
@interface EADailyGoalModel : EABaseModel


/** 步数，目标值单位: 步 */
@property(nonatomic, strong) EADailyGoalItem *sStep;


/** 卡路里，目标值单位: 卡 */
@property(nonatomic, strong) EADailyGoalItem *sCalorie;

/** 距离，目标值单位: 米 */
@property(nonatomic, strong) EADailyGoalItem *sDistance;


/** 运动时长，目标值单位: 秒 */
@property(nonatomic, strong) EADailyGoalItem *sDuration;


/** 睡眠时长，目标值单位: 秒 */
@property(nonatomic, strong) EADailyGoalItem *sSleep;



/// MARK: - 获取日常目标值设置相关信息
/// @param data 数据流
+ (EADailyGoalModel *)getModelByData:(NSData *)data;





@end

NS_ASSUME_NONNULL_END
