//
//  EAOtaRespond.h
//  EABluetooth
//
//  Created by Aye on 2021/3/23.
//

#import <EABluetooth/EABaseModel.h>

NS_ASSUME_NONNULL_BEGIN

//  NOT_ANALYSIS_CLASS
@interface EAOtaRespond : EABaseModel

/// data type
@property(nonatomic, assign) NSInteger requestId;

/// Device ota status response【设备ota状态回应】
@property(nonatomic, assign) EAOtaRespondStatus eStatus;

/// How many bytes of data have the current type of device received【当前类型设备已经收到多少字节的数据】
@property(nonatomic, assign) NSInteger receiveBytes;

@property(nonatomic, strong) NSData *hisJsonReqId;

/// 海思字库图库 需要升级的字库json的ID
@property(nonatomic, copy) NSArray *hisJsonReqIdArray;

/// 获取回应数据
/// @param data 数据流
+ (EAOtaRespond *)getModelByData:(NSData *)data;

@end

NS_ASSUME_NONNULL_END
