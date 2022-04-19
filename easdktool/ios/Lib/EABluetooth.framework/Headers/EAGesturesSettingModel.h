//
//  EAGesturesSettingModel.h
//  EABluetooth
//
//  Created by Aye on 2021/3/22.
//

#import <EABluetooth/EABaseModel.h>

NS_ASSUME_NONNULL_BEGIN


/// 抬手亮屏开关
@interface EAGesturesSettingModel : EABaseModel

@property(nonatomic, assign) EAGesturesBrightType eBrightSrc;

/** 开始时间 ：小时 */
@property(nonatomic, assign) NSInteger beginHour;

/** 开始时间 ：分钟 */
@property(nonatomic, assign) NSInteger beginMinute;

/** 结束时间 ：小时 */
@property(nonatomic,  assign) NSInteger endHour;

/** 结束时间 ：分钟 */
@property(nonatomic,  assign) NSInteger endMinute;


/// MARK: - 获取抬手亮屏开关设置相关信息
/// @param data 数据流
+ (EAGesturesSettingModel *)getModelByData:(NSData *)data;




@end

NS_ASSUME_NONNULL_END
