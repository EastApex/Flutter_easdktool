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
- (void)setBleQueueNil;


/// 设置设备连接状态
/// @param isConnected YES:已连接
- (void)setIsConnected:(BOOL)isConnected;

/// 获取数据【队列操作】
- (void)operationGetInfoWithType:(EADataInfoType)dataInfoType result:(ResultGetInfoBlock )result;
/// 修改数据【队列操作】
- (void)operationChangeModel:(EABaseModel *)changeModel respond:(RespondBlock )respond;
/// 获取大数据【队列操作】
- (void)operationgGetBigData:(EAGetBigDataRequestModel *)model respond:(RespondBlock )respond;
/// 手表OTA【队列操作】
- (void)upgrade:(EAOTA *)ota;
/// 获取大数据（bigDataType 只支持大数据类型 3000~3999）
- (NSArray *)getBigDataWithBigDataType:(EADataInfoType)bigDataType;
/// 获取音频数据【通知收到 ‘录音完成’ 才能调用此方法获取录音数据】
- (NSData *)getAudioDataData;


/** 以下不建议使用 */
/** 需要在单线程操作:确保上次操作完成后，调用才会响应，尽量使用上面队列操作方法 */

/// 获取数据【单线程操作】
- (void)getInfoByInfoType:(EADataInfoType)dataInfoType result:(ResultGetInfoBlock )result;
/// 修改数据【单线程操作】
- (void)changeInfo:(EABaseModel *)baseModel respond:(RespondBlock )respond;
/// 获取大数据【单线程操作】
- (void)getBigDataRequestModel:(EAGetBigDataRequestModel *)model respond:(RespondBlock )respond;;


/** 以下 为测试使用 */

/// 分析大数据
- (void)analyzeBigDataString:(NSString *)dataString;
/// 分析大数据
- (NSArray *)analyzeBigDataString:(NSString *)pbDataString andIdNmuber:(NSInteger )idNumber;




@end

NS_ASSUME_NONNULL_END
