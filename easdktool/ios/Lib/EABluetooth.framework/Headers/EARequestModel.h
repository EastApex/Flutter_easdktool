//
//  EARequestModel.h
//  EABluetooth
//
//  Created by Aye on 2021/3/23.
//  File Name:1:Query Command Model【查询命令】

#import <EABluetooth/EABaseModel.h>

NS_ASSUME_NONNULL_BEGIN

/**
 * id = 1
 * Command to query watch information, all obtained by converting to the data stream of Class EARequestModel.
 * 查询手表信息的命令，都是转化为Class EARequestModel的数据流来获取。
 */
@interface EARequestModel : EABaseModel

/// ID number of the protocol request【协议请求的id号】
@property(nonatomic, assign) NSInteger requestId;

/// Additional type, currently used when reading requestId = 45【附加类型,目前 读取 requestId = 45 使用】
@property(nonatomic, assign) NSInteger type;


/// Additional type, currently used in requestId = 75【附加类型,目前在 requestId = 75 使用】
@property(nonatomic, strong) NSString *string;



/// Initialize the request command【初始化】
/// - Parameters:
///   - requestId: Data type ID【数据类型id】
///   - type: Additional type【附加类型】
- (instancetype)initWithRequestId:(NSInteger )requestId type:(NSInteger )type;

/// Initialize the request command【初始化】
/// - Parameters:
///   - requestId: Data type ID【数据类型id】
///   - type: Additional type【附加类型】
+ (instancetype)eaInitWithRequestId:(NSInteger )requestId type:(NSInteger )type;


/// Initialize the request command【初始化】
/// - Parameters:
///   - requestId: Data type ID【数据类型id】
///   - string: Additional type【附加类型】
+ (instancetype)eaInitWithRequestId:(NSInteger )requestId string:(NSString *)string;


//  NOT_ANALYSIS_CLASS
- (NSData *)getRequestModelData;


@end






NS_ASSUME_NONNULL_END
