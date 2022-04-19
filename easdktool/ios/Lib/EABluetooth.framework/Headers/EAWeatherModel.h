//
//  EAWeatherModel.h
//  EABluetooth
//
//  Created by Aye on 2021/3/19.
//

#import <EABluetooth/EABaseModel.h>

NS_ASSUME_NONNULL_BEGIN


/// 天气信息
@interface EADayWeatherModel : EABaseModel

/** 天气状况 （日间）*/
@property(nonatomic, assign) EAWeatherType eDayType;

/** 天气状况 （夜间） */
@property(nonatomic, assign) EAWeatherType eNightType;

/** 最低温度 */
@property(nonatomic, assign) NSInteger minTemperature;

/** 最高温度 */
@property(nonatomic, assign) NSInteger maxTemperature;

/** 日出时间 (时间戳) */
@property(nonatomic, assign) NSInteger sunriseTimestamp;

/** 日落时间 (时间戳) */
@property(nonatomic, assign) NSInteger sunsetTimestamp;

/** 空气质量 */
@property(nonatomic, readwrite) EAWeatherAirType eAir;

/** 最低风力等级 */
@property(nonatomic, assign) NSInteger minWindPower;

/** 最高风力等级 */
@property(nonatomic, assign) NSInteger maxWindPower;

/** 紫外线强度 */
@property(nonatomic, assign) EAWeatherRaysType eRays;

/** 空气湿度(1~100)% */
@property(nonatomic, assign) NSInteger airHumidity;

/** 月相 */
@property(nonatomic, assign) EAWeatherMoonType eMoon;

/** 云量(1~100)% */
@property(nonatomic, assign) NSInteger cloudiness;

/** 空气质量分数 AQI */
@property(nonatomic, assign) NSInteger airGrade;
@end


/// 天气数组
@interface EAWeatherModel : EABaseModel


/** 温度单位 */
@property(nonatomic, assign) EAWeatherUnit eFormat;

/** 当前温度 */
@property(nonatomic, assign) NSInteger currentTemperature;

/** 最多支持8天 */
@property(nonatomic, strong) NSMutableArray<EADayWeatherModel*> *sDayArray;

/** 位置 */
@property(nonatomic, strong) NSString *place;

/// MARK: - 获取天气数组相关信息
/// @param data 数据流
+ (EAWeatherModel *)getModelByData:(NSData *)data;





@end


NS_ASSUME_NONNULL_END
