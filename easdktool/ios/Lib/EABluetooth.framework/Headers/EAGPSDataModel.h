//
//  EAGPSDataModel.h
//  EABluetooth
//
//  Created by Aye on 2021/3/23.
//

#import <EABluetooth/EABaseBigDataModel.h>

NS_ASSUME_NONNULL_BEGIN


/// GPS 数据
@interface EAGPSDataModel : EABaseModel

/** 时间戳 */
@property(nonatomic, assign) NSInteger timeStamp;

/** 纬度 */
@property(nonatomic, assign) float latitude;

/** 经度 */
@property(nonatomic, assign) float longitude;

///** 海拔，单位:米 */
//@property(nonatomic, assign) float altitude;
//
///** 速度，单位: KM/H */
//@property(nonatomic, assign) float speed;
//
///** 距离，单位: 米 */
//@property(nonatomic, assign) float distance;


@end


@interface EAUploadGPSData : EABaseBigDataModel

/** 多运动包（大小详见对应OPTIONS文件） */
@property(nonatomic, strong) NSMutableArray<EAGPSDataModel*> *sIndexArray;

+ (EAUploadGPSData *)getGPSData:(NSData *)data;

@end


NS_ASSUME_NONNULL_END
