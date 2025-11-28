//
//  EARespondModel.h
//  EABluetooth
//
//  Created by Aye on 2021/3/31.
//  File Name:2:Response Model【命令响应】


#import <EABluetooth/EABaseModel.h>

NS_ASSUME_NONNULL_BEGIN

/**
 * id = 2
 * Watch response model: Respond to set watch information status
 * 手表响应模型：响应设置手表信息状态
 */
@interface EARespondModel : EABaseModel

/// Protocol request ID number【协议请求的id号】
@property(nonatomic, assign) EADataInfoType requestId;

/// Response status【响应状态】
@property(nonatomic, assign) EARespondCodeType eErrorCode;

/// Data type ID【数据类型id】
@property(nonatomic, assign) NSInteger id_p;







/// NOT_ANALYSIS_FUNCTION
+ (instancetype)eaInitSuccWithRequestId:(EADataInfoType)requestId;
/// NOT_ANALYSIS_FUNCTION
+ (instancetype)eaInitErrorWithRequestId:(EADataInfoType)requestId;
/// NOT_ANALYSIS_FUNCTION
+ (instancetype)eaInitMemFullWithRequestId:(EADataInfoType)requestId;
/// NOT_ANALYSIS_FUNCTION
+ (instancetype)eaInitTimeConflictWithRequestId:(EADataInfoType)requestId;
/// NOT_ANALYSIS_FUNCTION
+ (instancetype)eaInitNotReadWithRequestId:(EADataInfoType)requestId;
/// NOT_ANALYSIS_FUNCTION
+ (instancetype)eaInitNotWriteWithRequestId:(EADataInfoType)requestId;
/// NOT_ANALYSIS_FUNCTION
+ (instancetype)eaInitSuccWithRequestId:(EADataInfoType)requestId id_p:(NSInteger)id_p;
/// NOT_ANALYSIS_FUNCTION
+ (instancetype)eaInitErrorWithRequestId:(EADataInfoType)requestId id_p:(NSInteger)id_p;
/// NOT_ANALYSIS_FUNCTION
+ (instancetype)eaInitTimeOutWithRequestId:(EADataInfoType)requestId;

@end

NS_ASSUME_NONNULL_END
