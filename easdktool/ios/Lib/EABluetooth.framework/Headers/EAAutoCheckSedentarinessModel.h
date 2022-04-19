//
//  EAAutoCheckSedentarinessModel.h
//  EABluetooth
//
//  Created by Aye on 2021/3/19.
//

#import <EABluetooth/EABaseModel.h>
NS_ASSUME_NONNULL_BEGIN

///久坐监测
@interface EAAutoCheckSedentarinessModel : EABaseModel

/** 间隔时长：单位分钟，0为关闭监测功能 */
@property(nonatomic, assign) NSInteger  interval;

/** 周期：位对应从bit0~bit6对应周日~周六
    同 EAAutoCheckSleepModel.weekCycleBit
 */
@property(nonatomic, assign) NSInteger  weekCycleBit;

/** 开始时间 ：小时 */
@property(nonatomic, assign) NSInteger  beginHour;

/** 开始时间 ：分钟 */
@property(nonatomic, assign) NSInteger  beginMinute;

/** 结束时间 ：小时 */
@property(nonatomic, assign) NSInteger  endHour;

/** 结束时间 ：分钟 */
@property(nonatomic, assign) NSInteger  endMinute;

/** 步数阈值 ：低于此步数则提醒久坐 */
@property(nonatomic, assign) NSInteger  stepThreshold;


/// MARK: - 获取久坐监测设置相关信息
/// @param data 数据流
+ (EAAutoCheckSedentarinessModel *)getModelByData:(NSData *)data;




@end

NS_ASSUME_NONNULL_END
