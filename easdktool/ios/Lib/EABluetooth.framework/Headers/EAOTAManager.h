//
//  EAOTAManager.h
//  EABluetooth
//
//  Created by Aye on 2024/8/2.
//  File Name:OTA

#import <Foundation/Foundation.h>
#import "EABleConfig.h"
#import "EAOTAModel.h"
NS_ASSUME_NONNULL_BEGIN




/**
 * EAOTAManager is a watch upgrade management class, including OTA operation types such as firmware, resources, watch faces, APGS, etc.
 *
 * OTA watch firmware and resource rules. Just understand. You can directly send the file to the SDK and it will be processed automatically.
 * The display form of the watch firmware version: AP0.1B1.2R0.1T0.1H0.1
 * OTA file name rule:

 * Watch firmware name: 001001_AP0.1B1.2.bin
 * "AP" represents the major version, and "B" follows the minor version
 * The major version refers to the test confirmation version. If a certain version is confirmed, then add 1 to this version
 * B is a minor version under this version, used to fix various bugs and add requirements
 *
 * Font library name: 001001_R0.1.bin
 * Screen name: 001001_T0.1.bin
 * Heart rate name: 001001_H0.1.bin
 *
 * 001001 is a specific identifier for watches
 *
 * Suppose the current watch firmwareVersion => AP0.1B0.9R0.3T0.1G0.1
 * AP0.1B0.9 => The firmware version number is 0.1build 0.9
 * R0.3=> The font library version number is 0.3
 * T0.1=> Screen version number 0.1
 * H0.1=> Heart rate version number 0.1
 *
 * The new firmware version numbers are AP0.1B1.0 and AP0.1B1.1
 * The new font library version numbers are R0.4 and R0.5
 *
 * For firmware version upgrades, it is sufficient to upgrade to the maximum version number, that is, to upgrade to AP0.1B1.1. There is no need to upgrade to AP0.1B1.0
 * The font library version upgrade requires upgrading all versions that are larger than the current font library version, that is, both R0.4 and R0.5 need to be upgraded
 * The screen and heart rate follow the same logic as the firmware. Just upgrade to the largest version
 *
 *
 * EAOTAManager是手表升级管理类，含固件、资源、表盘、APGS等OTA操作类型
 *
 * OTA手表固件、资源规则。只需要了解即可，您直接传文件给SDK会自动处理好。
 * 手表固件版本显示形式：AP0.1B1.2R0.1T0.1H0.1
 * OTA文件名称规则：

 * 手表固件名称：001001_AP0.1B1.2.bin
 * AP是大版本，B后面是小版本
 * 大版本指的是测试确认版本，假如某个版本确认了，那就这个版本+1
 * B是在这个版本下的小版本，用于修改各种bug，增加需求
 *
 * 字库名称：001001_R0.1.bin
 * 屏幕名称：001001_T0.1.bin
 * 心率名称：001001_H0.1.bin
 *
 * 001001 是手表的特定标识
 *
 * 假设 当前手表 firmwareVersion => AP0.1B0.9R0.3T0.1G0.1
 * AP0.1B0.9 => 固件版本号是0.1 build 0.9
 * R0.3=>字库版本号是0.3
 * T0.1=>屏幕版本号0.1
 * H0.1=>心率版本号0.1
 *
 * 有新的 固件版本号 是 AP0.1B1.0 和 AP0.1B1.1
 * 有新的 字库版本号是 R0.4 和 R0.5
 *
 * 固件版本升级 升级 最大版本号就可以了，即升级 AP0.1B1.1，不需要升级 AP0.1B1.0
 * 字库版本升级 需要升级所有比当前字库版本大的版本，即 R0.4 和 R0.5都要升级
 * 屏幕和心率同固件一样逻辑，升级最大版本的就行
 *
 *
 */
@interface EAOTAManager : NSObject

/// 单例
+ (instancetype)defaultManager;

/// 获取OTA状态，返回YES说明手表处于OTA状态
- (BOOL)getOTAState;


/// OTA
/// - Parameter
///   - list: Files that need to be upgraded (multiple)【需要升级的文件(可多个)】
///   - progress: progress
///   - complete: complete
- (BOOL)eaUpgradeFiles:(NSArray<EAFileModel *> *)list progress:(void (^)(CGFloat progress))progress complete:(OTACompleteBlock)complete;


/// OTA Watch Face【OTA表盘】
/// - Parameter
///   - watchFaceFile: Watch Face File【表盘文件】
///   - progress: progress
///   - complete: complete
- (BOOL)eaUpgradeWatchFaceFile:(EAFileModel *)watchFaceFile progress:(void (^)(CGFloat progress))progress complete:(OTACompleteBlock)complete;



/// Check the HisRes.zip file【解压并获取需要OTA的文件】
/// - Parameters:
///   - hisResZipFile: HisRes.zip
///   - progress: progress【解压进度】
///   - complete: HisRes.bin file that needs to be upgraded【需要升级的HisRes.bin文件】
///   - uncompressProgressBlock: uncompress Progress
///   - checkJsonProgressBlock: checkJson Progress
///   - fail: fail
- (void)eaCheckHisRes:(EAFileModel *)hisResZipFile uncompressProgress:(UncompressProgressBlock)uncompressProgressBlock checkProgress:(CheckJsonProgressBlock)checkJsonProgressBlock complete:(CheckHisResCompleteBlock)complete fail:(CheckHisResErrorBlock)fail;



/// Decompress the Hisilicon Res file【解压海思Res文件】
/// - Parameters:
///   - hisResFilePath: Path of the hisilicon Res file【海思Res文件路径】
///   - progress: progress【进度】
///   - complete: complete
///   - uncompressProgress: uncompress progress
///   - uncompressRrror: uncompress error
- (void)eaUncompressHisResFilePath:(NSString *)hisResFilePath uncompressProgress:(void (^)(CGFloat progress))uncompressProgress complete:(void (^)(NSString *jsonPath,NSString *uncompressPath))complete uncompressRrror:(CheckHisResErrorBlock)uncompressRrror;


/// Analyze the json file to get the Hisilicon file that needs OTA【分析json文件获取需要OTA的海思文件】
/// - Parameters:
///   - jsonPath: jsonPath
///   - checkProgress: check progress
///   - complete: complete
///   - fail: fail
- (void)eaCheckHisJsonPath:(NSString *)jsonPath checkProgress:(void (^)(CGFloat progress))checkProgress complete:(CheckHisResCompleteBlock)complete fail:(CheckHisResErrorBlock)fail;


/// OTA AGPS,sdk will down apgs file
/// - Parameters:
///   - checkAgpsUpdateTime: check Agps Update Time
///   - progress: progress
///   - complete: complete
- (BOOL)eaUpgradeAGPSCheckTime:(BOOL)checkAgpsUpdateTime progress :(OTAProgressBlock)progress complete:(OTACompleteBlock)complete;


/// OTA Music File
/// - Parameters:
///   - musicFile: music file
///   - progress: progress
///   - complete: complete
- (BOOL)eaOTAMusicFile:(EAFileModel *)musicFile progress:(void (^)(CGFloat progress))progress complete:(OTACompleteBlock)complete;


/// 杰里 OTA
/// - Parameters:
///   - jlFile: jl File
///   - otaProgress:  progress
///   - otaCompleteBlock: complete
- (BOOL)eaUpgradeJLFile:(EAFileModel *)jlFile progress:(OTAProgressBlock)otaProgress complete:(OTACompleteBlock)otaCompleteBlock;



/// OTA AGPS,need apgs file
/// - Parameters:
///   - agpsFile: agps File
///   - progress: progress
///   - complete: complete
- (BOOL)eaUpgradeAGPSFile:(EAFileModel *)agpsFile progress:(void (^)(CGFloat progress))progress complete:(OTACompleteBlock)complete DEPRECATED_MSG_ATTRIBUTE();


/// OTA AGPS,sdk will down apgs file
/// - Parameters:
///   - progress: progress
///   - complete: complete
- (BOOL)eaUpgradeAGPSProgress:(void (^)(CGFloat progress))progress complete:(OTACompleteBlock)complete DEPRECATED_MSG_ATTRIBUTE();
@end

NS_ASSUME_NONNULL_END
