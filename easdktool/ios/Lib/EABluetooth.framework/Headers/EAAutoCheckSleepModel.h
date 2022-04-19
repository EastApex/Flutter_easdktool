//
//  EAAutoCheckSleepModel.h
//  EABluetooth
//
//  Created by Aye on 2021/3/19.
//

#import <EABluetooth/EABaseModel.h>
NS_ASSUME_NONNULL_BEGIN


/// 自动睡眠监测设置
@interface EAAutoCheckSleepModel : EABaseModel

/** 周期：==》周日~周六，
 0：关闭
 1：开启
 eg1：周日 ~ 周一 ~ 周二 ~ 周三 ~ 周四 ~ 周五 ~ 周六
      0     1      1     0     0     0      1
 将 0110001 二进制转为 10进制 即此时 weekCycleBit 为 49 开启 周一二六 检测
 
 eg2：周日 ~ 周一 ~ 周二 ~ 周三 ~ 周四 ~ 周五 ~ 周六
      0     0      0     0     0      1     1
 将 0000010 二进制转为 10进制 即此时 weekCycleBit 为 3 开启 周五六 检测
 
 weekCycleBit 为0 即 关闭监测功能
 */
@property(nonatomic, assign) NSInteger weekCycleBit;

/** 开始时间 ：小时 */
@property(nonatomic, assign) NSInteger beginHour;

/** 开始时间 ：分钟 */
@property(nonatomic, assign) NSInteger beginMinute;

/** 结束时间 ：小时 */
@property(nonatomic, assign) NSInteger endHour;

/** 结束时间 ：分钟 */
@property(nonatomic, assign) NSInteger endMinute;



/// MARK: - 获取自动睡眠监测设置相关信息
/// @param data 数据流
+ (EAAutoCheckSleepModel *)getModelByData:(NSData *)data;




@end

NS_ASSUME_NONNULL_END
