//
//  EABleSendManager.h
//  EABluetooth
//
//  Created by Aye on 2021/3/26.
//

#import <Foundation/Foundation.h>
#import <EABluetooth/EAModelHeader.h>
#import <EABluetooth/EABleManager.h>
#import <EABluetooth/EAEnum.h>
#import <EABluetooth/EABleConfig.h>
NS_ASSUME_NONNULL_BEGIN


typedef void(^ResultGetInfoBlock)(EABaseModel *baseModel);
typedef void(^RespondBlock)(EARespondModel *respondModel);




@interface EABleSendManager : NSObject



/// 单例
+ (instancetype)defaultManager;


- (void)setNilBlock;
- (void)setchannelDataNil;

/// 设置设备连接状态
/// @param isConnected YES:已连接
- (void)setIsConnected:(BOOL)isConnected;

- (void)getInfoOperationByInfoType:(EADataInfoType)dataInfoType result:(ResultGetInfoBlock )result;


/// 通用获取相关信息方法
/// @param dataInfoType 数据类型
/// @param result 返回的结果
- (void)getInfoByInfoType:(EADataInfoType)dataInfoType result:(ResultGetInfoBlock )result;


///  通用修改相关信息方法
/// @param baseModel 将要修改的数据
/// @param respond 是否成功
- (void)changeInfo:(EABaseModel *)baseModel respond:(RespondBlock )respond;


/// MARK: - 获取大数据请求
/// 获取大数据请求
- (void)getBigDataRequestModel:(EAGetBigDataRequestModel *)model;


/// 手表OTA
/// @param ota EAOTA
- (void)upgrade:(EAOTA *)ota;


/// 获取大数据（只支持大数据类型）
- (NSArray *)getBigDataWithBigDataType:(EADataInfoType)bigDataType;



/// 测试用的，分析大数据
/// @param dataString 完整的大数据数据流
- (void)analyzeBigDataString:(NSString *)dataString;

/// 测试用的，分析大数据
/// @param pbDataString 完整pbDataString
- (NSArray *)analyzeBigDataString:(NSString *)pbDataString andIdNmuber:(NSInteger )idNumber;


- (NSData *)getLastRecordData;

///// 删除大数据
//- (void)deleteBigDataWithBigDataType:(EADataInfoType)bigDataType;

///// 删除大数据
//- (void)deleteAllBigData;

@end

NS_ASSUME_NONNULL_END
