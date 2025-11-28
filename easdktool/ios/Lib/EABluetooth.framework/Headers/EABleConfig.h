//
//  EABleConfig.h
//  Template
//
//  Created by Aye on 2021/3/16.
//  File Name:SDK Config【SDK配置】

#import <Foundation/Foundation.h>

NS_ASSUME_NONNULL_BEGIN


#define kEADocumentsPath            [NSHomeDirectory() stringByAppendingPathComponent:@"Documents"]
#define kEAWatchFacePath            [kEADocumentsPath stringByAppendingPathComponent:@"EAWatchFace"]
#define kEATempPath                 NSTemporaryDirectory()

#define EALog(format, ...) {\
if ([EABleConfig logEnable]) {\
NSLog(@"[EALog]%s-%d " format, __func__,__LINE__, ##__VA_ARGS__);\
NSString *logFirstString = [NSString stringWithFormat:@"%s:%d ||** ",__func__,__LINE__];\
NSString *logString = [NSString stringWithFormat:@""format,##__VA_ARGS__];\
[EABleConfig writeLog:[logFirstString stringByAppendingString:logString]];\
}\
}\

/**
 * EABleConfig is an SDK configuration class with functions such as debug mode and log saving.
 * EABleConfig是SDK配置类，有debug模式、保存log等功能
 */
@interface EABleConfig : NSObject

/// Whether to print logs. This function is disabled by default【是否打印日志，默认关闭】
@property (nonatomic, assign) BOOL debug;

/// Search for the watch's Bluetooth name【搜索手表的蓝牙名称】
@property (nonatomic, copy) NSArray *deviceHeadNames;

/// Test special (please set use in the debugging stage => 0: delete big data, 1: do not delete big data)【测试专用（请在调试阶段设置使用=》0:删除大数据，1:不删除大数据）】
@property (nonatomic, assign) NSInteger isTest;

/// Whether to save logs. This parameter is disabled by default.This function takes effect only when debug is set to YES【是否保存日志，默认关闭,需要 debug 为YES才能起作用】
@property (nonatomic, assign) BOOL saveLog;

/// SDK不开启重连手表，默认关闭
@property (nonatomic, assign) BOOL sdkNotOpenReconnectWatch;

/// Enable command timeout response.  Defalut NO,
@property (nonatomic, assign) BOOL openCmdTimeOut;


/// Only search for and display EA watches【仅搜索和显示EA手表】
@property (nonatomic, assign) BOOL onlyShowEaWatch;


/// Scan all devices and turn them off by default【扫描所有设备 ，默认关闭】
 @property (nonatomic, assign) BOOL canScanAllDevices ;


/// ignore：默认 ”8800“
@property (nonatomic, copy) NSArray *uuids __attribute__((deprecated));

/// 检索通道设备：默认8800
@property (nonatomic, assign) NSInteger scanchannel __attribute__((deprecated));

/// ignore：工具 ，默认关闭
@property (nonatomic, assign) BOOL tool __attribute__((deprecated));

/// ignore：工厂
@property (nonatomic, assign) BOOL factory __attribute__((deprecated));

/// ignore：厂测操作
@property (nonatomic, assign) NSInteger factory_ops __attribute__((deprecated));

/// ignore：
@property (nonatomic, assign) BOOL canSaveWatchFaceFile __attribute__((deprecated)) ;

/// ignore：
@property(nonatomic,assign) NSInteger popUpInterface __attribute__((deprecated));

/// ignore：
@property(nonatomic,assign) NSInteger extraSportHrs __attribute__((deprecated));



/// Initialize the default configuration【初始化默认配置】
+ (EABleConfig *)getDefaultConfig;




/// NOT_ANALYSIS_FUNCTION
+ (BOOL)logEnable ;
/// NOT_ANALYSIS_FUNCTION
+ (BOOL)toolEnable;
/// NOT_ANALYSIS_FUNCTION
+ (BOOL)saveLogEnable;
/// NOT_ANALYSIS_FUNCTION
+ (void)writeLog:(NSString *)logLastString;
/// NOT_ANALYSIS_FUNCTION
+ (BOOL)checkJLBil;
/// NOT_ANALYSIS_FUNCTION
+ (BOOL)checkEAHisCompress;
@end




typedef void(^CheckHisResCompleteBlock)(NSArray * _Nullable hisresFiles);
typedef void(^CheckHisResErrorBlock)(NSError * _Nullable error);
typedef void(^OTACompleteBlock)(BOOL succ, NSError * _Nullable error);
typedef void(^UncompressProgressBlock)(CGFloat progress);
typedef void(^CheckJsonProgressBlock)(CGFloat progress);
typedef void(^OTAProgressBlock)(CGFloat progress);

typedef void(^EAJieLiOTAResultBlock)  (NSInteger result);


NS_ASSUME_NONNULL_END
