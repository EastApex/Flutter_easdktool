//
//  EAPpgAuto.h
//  EABluetooth
//
//  Created by Aye on 2025/4/24.
//

#import <EABluetooth/EABluetooth.h>

NS_ASSUME_NONNULL_BEGIN

@interface EAPpgAuto : EABaseModel

/** 自动开关: 0关闭 1打开 */
@property(nonatomic, assign) BOOL sw;

/** 间隔时长：单位(分钟) */
@property(nonatomic, assign) NSInteger interval;

/** 功能打开后超时关闭时间: 单位(秒) 如不设超时关闭则写0(听从设备超时时间) */
@property(nonatomic, assign) NSInteger timeoutSec;

///** 开始时间: 小时 */
//@property(nonatomic, assign) NSInteger beginHour;
//
///** 开始时间: 分钟 */
//@property(nonatomic, assign) NSInteger beginMinute;
//
///** 结束时间: 小时 */
//@property(nonatomic, assign) NSInteger endHour;
//
///** 结束时间: 分钟 */
//@property(nonatomic, assign) NSInteger endMinute;

+ (instancetype )eaInitWithOpen:(BOOL)open timeoutSec:(NSInteger)timeoutSec interval:(NSInteger)interval ;

@end

NS_ASSUME_NONNULL_END
