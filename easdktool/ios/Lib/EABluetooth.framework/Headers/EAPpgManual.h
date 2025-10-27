//  NOT_ANALYSIS_CLASS
//  EAPpgManual.h
//  EABluetooth
//
//  Created by Aye on 2025/4/24.
//

#import <EABluetooth/EABluetooth.h>

NS_ASSUME_NONNULL_BEGIN

@interface EAPpgManual : EABaseModel

/** 手动开关: 0关闭 1打开 */
@property(nonatomic, assign) BOOL sw;

/** 功能打开后超时关闭时间: 单位(秒) 如不设超时关闭则写0(听从设备超时时间) */
@property(nonatomic, assign) NSInteger timeoutSec;


// long time out
+ (instancetype )eaInitWithOpen:(BOOL)open;

+ (instancetype )eaInitWithOpen:(BOOL)open timeoutSec:(NSInteger)timeoutSec;

@end

NS_ASSUME_NONNULL_END
