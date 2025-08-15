//
//  EARequestModel.h
//  EABluetooth
//
//  Created by Aye on 2021/3/23.
//

#import <EABluetooth/EABaseModel.h>

NS_ASSUME_NONNULL_BEGIN


/// 请求数据
@interface EARequestModel : EABaseModel

/** 协议请求的id号 */
@property(nonatomic, assign) NSInteger requestId;

/** 附加类型
 目前 读取 requestId = 45 使用
 */
@property(nonatomic, assign) NSInteger type;


/// 目前在 requestId = 75 使用
@property(nonatomic, strong) NSString *string;



- (instancetype)initWithRequestId:(NSInteger )requestId type:(NSInteger )type;
+ (instancetype)eaInitWithRequestId:(NSInteger )requestId type:(NSInteger )type;


+ (instancetype)eaInitWithRequestId:(NSInteger )requestId string:(NSString *)string;


//+ (NSData *)getModelDataByRequestId:(NSInteger )requestId;

- (NSData *)getRequestModelData;


@end







NS_ASSUME_NONNULL_END
