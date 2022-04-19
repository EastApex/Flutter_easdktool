//
//  EABleConfig.h
//  Template
//
//  Created by Aye on 2021/3/16.
//

#import <Foundation/Foundation.h>

NS_ASSUME_NONNULL_BEGIN

//自定义打印
#define EALog(format, ...) {\
if ([EABleConfig logEnable]) {\
NSLog(@"[EALog]:%s:%d" format, __func__, __LINE__, ##__VA_ARGS__);\
}\
}\


@interface EABleConfig : NSObject

/// 是否打印日志，默认关闭
@property (nonatomic, assign) BOOL debug;

/// 工具 ，默认关闭
@property (nonatomic, assign) BOOL tool;

/// 显示信号量 ，默认关闭
@property (nonatomic, assign) BOOL showRSSI;

/// 搜索设备的 HeadName
@property (nonatomic, copy) NSArray *deviceHeadNames;



+ (EABleConfig *)getDefaultConfig;


+ (BOOL)logEnable;

+ (BOOL)toolEnable;
@end

NS_ASSUME_NONNULL_END
