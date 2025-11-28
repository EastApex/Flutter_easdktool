//  
//  EABleBigDataManager.h
//  EABluetooth
//
//  Created by Aye on 2023/7/17.
//  File Name:Big Data【大数据】

#import <Foundation/Foundation.h>
#import <EABluetooth/EAEnum.h>

NS_ASSUME_NONNULL_BEGIN


typedef void(^ResultBlock)(EARespondCodeType eaRespondCodeType);

/**
 * EABleBigDataManager is a big data reading class responsible for obtaining watch big data such as daily steps, heart rate, and exercise records.
 * EABleBigDataManager是大数据读取类，负责获取手表大数据，如日常步数、心率、运动记录等
 */
@interface EABleBigDataManager : NSObject

/// The singleton 【单例】
+ (instancetype)defaultManager;

/// Initiate synchronous big data
/// Data will not be available until the watch sends the big data message[Listening notification kNTF_EAGetDeviceOpsPhoneMessage],Then call eaGetBigDataButNotDeleteWithBigDataType: access to relevant data details
/// 发起同步大数据
/// 需要等待手表发送完成大数据消息才会有数据【监听通知 kNTF_EAGetDeviceOpsPhoneMessage】,然后调用 eaGetBigDataButNotDeleteWithBigDataType: 获取相关大数据详细内容
/// - Parameter respond: Response 【响应】
- (void)eaSendSyncBigData:(ResultBlock )respond;


/// Get and delete big data by bigDataType 【Data will not be available until the watch sends the big data message：EAPhoneOpsBig8803DataUpdateFinish】
/// 获取大数据（并删除数据）【需要等待手表发送完成大数据消息才会有数据：EAPhoneOpsBig8803DataUpdateFinish】
/// - Parameter bigDataType: big data type【大数据类型】
- (NSArray *)eaGetBigDataWithBigDataType:(EADataInfoType)bigDataType;


/// Get big data without deleting it by bigDataType 【Data will not be available until the watch sends the big data message：EAPhoneOpsBig8803DataUpdateFinish】
/// 获取大数据但不删除数据【需要等待手表发送完成大数据消息才会有数据：EAPhoneOpsBig8803DataUpdateFinish】
/// - Parameter bigDataType: big data type【大数据类型】
- (NSArray *)eaGetBigDataButNotDeleteWithBigDataType:(EADataInfoType)bigDataType;

/// Delete big data by bigDataType
/// 删除大数据
/// - Parameter bigDataType: big data type【大数据类型】
- (void)eaDeleteBigDataWithBigDataType:(EADataInfoType)bigDataType;

/// Delete all big data by bigDataType
/// 删除所有大数据
- (void)eaDeleteAllBigData;



/// Obtaining and processing sleep data(Sleep combined interval: 60 minutes)
/// 获取并处理睡眠数据(睡眠合并间隔：60分钟)
/// - Parameter mergeTime: Sleep combined interval【睡眠合并间隔】
- (NSArray *)eaGetProcessingSleepData:(NSInteger)mergeTime;



/// AolonFit专用
/// - Parameters:
///   - benginTimestamp: begin day timestamp 【开始时间】
///   - endTimestamp: end day timestamp 【结束时间】
- (NSArray *)aolonFitHrsWithBenginTimestamp:(NSInteger )benginTimestamp endTimestamp:(NSInteger )endTimestamp;


///// Retrieve audio data [Call this method to retrieve audio data only when notification 'recording completed' is received]
///// 获取音频数据【通知收到 ‘录音完成’ 才能调用此方法获取录音数据】
//- (NSData *)getAudioDataData;































/// ignore：
/// test
- (NSArray *)analyzeBigDataString:(NSString *)pbDataString andIdNmuber:(NSInteger )idNumber;

@end

NS_ASSUME_NONNULL_END
