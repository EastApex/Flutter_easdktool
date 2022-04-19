//
//  EAGetBigDataRequestModel.h
//  EABluetooth
//
//  Created by Aye on 2021/3/31.
//

#import <EABluetooth/EABaseModel.h>

NS_ASSUME_NONNULL_BEGIN


/// 获取大数据请求
@interface EAGetBigDataRequestModel : EABaseModel

///
/// 以下属性为 优先 返回
///
/** 运动数据 请求 */
@property(nonatomic, assign) NSInteger sportDataReq;

/** 睡眠数据 请求 */
@property(nonatomic, assign) NSInteger sleepDataReq;

/** 心率数据 请求 */
@property(nonatomic, assign) NSInteger hrDataReq;

/** GPS数据 请求 */
@property(nonatomic, assign) NSInteger gpsDataReq;

/** 多运动数据 请求 */
@property(nonatomic, assign) NSInteger multiSportsDataReq;

/** 血氧数据 请求 */
@property(nonatomic, assign) NSInteger bloodOxygenDataDataReq;

/** 压力数据 请求 */
@property(nonatomic, assign) NSInteger stressDataReq;

/** 步频数据 请求 */
@property(nonatomic, assign) NSInteger stepFreqDataReq;

/** 配速数据 请求 */
@property(nonatomic, assign) NSInteger stepPaceDataReq;

/** 静态心率数据 请求 */
@property(nonatomic, assign) NSInteger restingHrDataReq;

@end

NS_ASSUME_NONNULL_END
