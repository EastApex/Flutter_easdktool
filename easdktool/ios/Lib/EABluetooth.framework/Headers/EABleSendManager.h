//  
//  EABleSendManager.h
//  EABluetooth
//
//  Created by Aye on 2021/3/26.
//  File Name:Cmd Send【命令发送】

#import <Foundation/Foundation.h>
#import <EABluetooth/EAModelHeader.h>
#import <EABluetooth/EABleManager.h>
#import <EABluetooth/EAEnum.h>
#import <EABluetooth/EABleConfig.h>
#import <EABluetooth/EAWatchSupportModel.h>
#import <UIKit/UIKit.h>

NS_ASSUME_NONNULL_BEGIN

/**
 * EABleSendManager is a Bluetooth data class: responsible for operations such as communication and data interaction with the watch.
 * EABleSendManager是蓝牙数据类：负责与手表的通讯、交互数据等操作。
 */
@interface EABleSendManager : NSObject


/// The singleton 【单例】
+ (instancetype)defaultManager;



///  Get data 【获取数据】
/// - Parameters:
///   - dataInfoType: data type 【数据类型】
///   - result: Response 【响应】
- (void)operationGetInfoWithType:(EADataInfoType)dataInfoType result:(ResultGetInfoBlock )result;

/// Get data from class ’EARequestModel‘ 【获取数据】
/// - Parameters:
///   - requestModel: data model【数据类型】
///   - result: Response 【响应】
- (void)operationGetInfoWithRequestModel:(EARequestModel *)requestModel result:(ResultGetInfoBlock )result;

/// Set data 【修改数据】
/// - Parameters:
///   - changeModel: data model【数据类型】
///   - respond: Response 【响应】
- (void)operationChangeModel:(EABaseModel *)changeModel respond:(RespondBlock )respond;

/// App controlled watch App操控手表
/// - Parameters:
///   - deviceOpsType: ops type 【操作类型】
///   - respond: Response 【响应】
- (void)operationControlledWatchWithType:(EADeviceOpsType)deviceOpsType result:(RespondBlock )respond;



/// upgrade [OTA]
- (BOOL)upgradeFiles:(NSArray<EAFileModel *> *)list DEPRECATED_MSG_ATTRIBUTE("Please use \"Class EAOTAManager func\"");

/// Watch face [OTA]
- (BOOL)upgradeWatchFaceFile:(EAFileModel *)watchFaceFile DEPRECATED_MSG_ATTRIBUTE("Please use \"Class EAOTAManager func\"");

/// OTA
- (BOOL)upgrade:(EAOTA *)ota DEPRECATED_MSG_ATTRIBUTE("Please use \"upgradeFiles:\"");

///备注：2个获取大数据方法不能同时使用。需要等待手表发送完成大数据消息才会有数据【监听通知 kNTF_EAGetDeviceOpsPhoneMessage】,然后调用 getBigDataWithBigDataType: 获取相关大数据详细内容
///Note: The two methods of obtaining big data cannot be used at the same time.Data will not be available until the watch sends the big data message[Listening notification kNTF_EAGetDeviceOpsPhoneMessage],Then call getBigDataWithBigDataType: access to relevant data details

/// Get big data 获取所有大数据
- (void)operationgGetBigDataRespond:(RespondBlock )respond DEPRECATED_MSG_ATTRIBUTE("Please use \"Class EABleBigDataManager func\"");
/// Get big data 获取所有大数据
- (void)operationgGetBigData:(EAGetBigDataRequestModel *)model respond:(RespondBlock )respond DEPRECATED_MSG_ATTRIBUTE("Please use \"Class EABleBigDataManager func\"");
/// only get one big data 单独获取大数据
- (void)operationgOnlyGetBigData:(EADataInfoType )bigDataType respond:(RespondBlock )respond DEPRECATED_MSG_ATTRIBUTE("Please use \"Class EABleBigDataManager func\"");

/// Get and delete big data by bigDataType 【Data will not be available until the watch sends the big data message：EAPhoneOpsBig8803DataUpdateFinish】
/// 获取大数据（并删除数据）【需要等待手表发送完成大数据消息才会有数据：EAPhoneOpsBig8803DataUpdateFinish】
- (NSArray *)getBigDataWithBigDataType:(EADataInfoType)bigDataType DEPRECATED_MSG_ATTRIBUTE("Please use \"Class EABleBigDataManager func\"");


/// Customize the background watch face【自定义背景图片的表盘】
- (NSInteger )customWatchFaceBackgroundImage:(UIImage *)backGroundImage colorType:(EACWFTimerColorType )colorType styleType:(EACWFStyleType)styleType DEPRECATED_MSG_ATTRIBUTE("Please use \"Class EAMakeWatchFaceManager func\"");

/// Customize the background watch face 【自定义背景图片的表盘】
- (NSInteger )customNumberWatchFaceBackgroundImage:(UIImage *)backGroundImage list:(NSArray <EACustomNumberWatchFaceModel *>*)numberList DEPRECATED_MSG_ATTRIBUTE("Please use \"Class EAMakeWatchFaceManager func\"");


/// Get ppg Data 获取ppg数据【单线程操作】
- (void)getPpgDataResult:(ResultGetPpgBlock )result DEPRECATED_MSG_ATTRIBUTE();


/** * You are not advised to use */
/** Need to operate in a single thread: make sure that the call does not respond until the last operation is complete. Try to use the queue operation method above */
/** 以下不建议使用 */
/** 需要在单线程操作:确保上次操作完成后，调用才会响应，尽量使用上面队列操作方法 */

/// Get data 获取数据【单线程操作】
- (void)getInfoByInfoType:(EADataInfoType)dataInfoType result:(ResultGetInfoBlock )result DEPRECATED_MSG_ATTRIBUTE();
/// Get data 获取数据【单线程操作】
- (void)getInfoByRequestModel:(EARequestModel *)requestModel result:(ResultGetInfoBlock )result DEPRECATED_MSG_ATTRIBUTE();
/// Set data 修改数据【单线程操作】
- (void)changeInfo:(EABaseModel *)baseModel respond:(RespondBlock )respond DEPRECATED_MSG_ATTRIBUTE();
/// Get big data 获取大数据【单线程操作】
- (void)getBigDataRequestModel:(EAGetBigDataRequestModel *)model respond:(RespondBlock )respond DEPRECATED_MSG_ATTRIBUTE();


/// ignore：
- (void)setNilBlock;
- (void)setchannelDataNil;
- (void)setBleQueueNil;
- (void)setIsConnected:(BOOL)isConnected;
- (EAWatchModel *)getConnectWatchModel;
- (EAWatchSupportModel *)getConnectWatchSupportModel;
- (void)openCommunicationTimer;
- (void)closeCommunicationTimer;
- (BOOL)getOTAState;
#pragma mark - Test 分析蓝牙数据
- (void)test_analyzeDataString:(NSString *)dataString inCharacteristicUUID:(NSString *)characteristicUUIDString;
@end

NS_ASSUME_NONNULL_END
