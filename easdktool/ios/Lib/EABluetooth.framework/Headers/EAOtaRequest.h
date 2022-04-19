//
//  EAOtaRequest.h
//  EABluetooth
//
//  Created by Aye on 2021/3/23.
//

#import <EABluetooth/EABaseModel.h>

NS_ASSUME_NONNULL_BEGIN

@interface EAOtaRequest : EABaseModel

/** ota类型 */
@property(nonatomic, assign) EAOtaRequestType eType;

/** 该类型版本号（大小详见对应OPTIONS文件） */
@property(nonatomic, copy) NSString *version;

/** 全部类型ota数据的总字节大小 */
@property(nonatomic, assign) NSInteger totalSize;

/** 当前类型ota数据的字节大小 */
@property(nonatomic, assign) NSInteger currentSize;

/** 如果当前类型是res，这个res_addr是从res的bin中截取前4个字节 */
@property(nonatomic, assign) NSInteger resAddr;

/** 当前类型ota数据的16bit的crc码 */
@property(nonatomic, assign) uint16_t crc;

/** 多少字节数据后等待设备数据包回应（影响ota速度: 根据手机性能调整，最高2048字节） */
@property(nonatomic, assign) NSInteger waitBytes;

/** 是否弹出升级界面 1打开升级页面，0不打开*/
@property(nonatomic, assign) NSInteger popUpInterface;


@property(nonatomic, assign) NSInteger isTestMode;

/// MARK: - 
+ (EAOtaRequest *)getModelByData:(NSData *)data;

@end

NS_ASSUME_NONNULL_END
