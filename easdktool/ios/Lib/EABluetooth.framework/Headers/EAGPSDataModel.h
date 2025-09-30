//
//  EAGPSDataModel.h
//  EABluetooth
//
//  Created by Aye on 2021/3/23.
//

#import <EABluetooth/EABaseBigDataModel.h>

NS_ASSUME_NONNULL_BEGIN


/// GPS 数据
@interface EAGPSDataModel : EABigDataModel


/// latitude
///  纬度
@property(nonatomic, assign) float latitude;

/// longitude
/// 经度
@property(nonatomic, assign) float longitude;


/** 卫星信号强度 */
@property(nonatomic, assign) NSInteger satelliteSig;

@end


@interface EAUploadGPSData : EABaseBigDataModel

@property(nonatomic, strong) NSMutableArray<EAGPSDataModel*> *sIndexArray;

+ (EAUploadGPSData *)getGPSData:(NSData *)data;

@end



/// ignore:GPS测试数据
@interface EATestGPSDataModel : EABigDataModel


/// latitude
///  纬度
@property(nonatomic, assign) float latitude;

/// longitude
/// 经度
@property(nonatomic, assign) float longitude;


/** 卫星信号强度 */
@property(nonatomic, assign) NSInteger satelliteSig;

@end

/// ignore:GPS测试数据
@interface EATestUploadGPSData : EABaseBigDataModel

@property(nonatomic, strong) NSMutableArray<EATestGPSDataModel*> *sIndexArray;

+ (EATestUploadGPSData *)getGPSData:(NSData *)data;

@end







NS_ASSUME_NONNULL_END
