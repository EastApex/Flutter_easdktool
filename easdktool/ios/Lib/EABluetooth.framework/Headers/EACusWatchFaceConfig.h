//
//  EACusWatchFaceConfig.h
//  EABluetooth
//
//  Created by Aye on 2025/4/8.
//  File Name:73:His Watch Face Config【海思表盘配置】


#import <EABluetooth/EABluetooth.h>

NS_ASSUME_NONNULL_BEGIN






@class EACusWatchFaceBaseConfig;


/**
 * id = 73
 * His watch face customization configuration
 * 海思表盘自定义配置
 */
@interface EACusWatchFaceConfig : EABaseModel

/// max account【固件端支持表盘的最大个数】
@property(nonatomic, assign) NSInteger wfMaxCount;
/// configuration
@property(nonatomic, strong) EACusWatchFaceBaseConfig *sBaseConfig;

///Support Func => See ENUM EACusWatchFaceFunctionType
@property(nonatomic, strong) NSMutableArray *sFuncSelectArray;


@end


/**
 * Configuration
 */
@interface EACusWatchFaceBaseConfig : EABaseModel

/// Configuration 1: Photo color enable【配置1:照片颜色使能】
@property(nonatomic, assign) BOOL photoColorEn;

/// Configuration 2: Time style enable【配置2:时间样式使能】
@property(nonatomic, assign) BOOL timeStyleEn;

/// Configuration 3: Time position enable【配置3:时间位置使能】
@property(nonatomic, assign) BOOL timePositionEn;

/// Configuration 4: Time color enable【配置4:时间颜色使能】
@property(nonatomic, assign) BOOL timeColorEn;

/// Configuration 5: Square: Data enable at the upper - left of the dial / Round: Data enable at the top of the dial【配置5:方:表盘左上方数据使能 /圆:表盘上方数据使能】
@property(nonatomic, assign) BOOL topDataEn;

/// Configuration 6: Square: Data enable at the upper - right of the dial / Round: Data enable at the right of the dial【配置6:方:表盘右上方数据使能 /圆:表盘右方数据使能】
@property(nonatomic, assign) BOOL rightDataEn;

/// Configuration 7: Square: Data enable at the lower - left of the dial / Round: Data enable at the left of the dial【配置7:方:表盘左下方数据使能 /圆:表盘左方数据使能】
@property(nonatomic, assign) BOOL leftDataEn;

/// Configuration 8: Square: Data enable at the lower - right of the dial / Round: Data enable at the bottom of the dial【配置8:方:表盘右下方数据使能 /圆:表盘下方数据使能】
@property(nonatomic, assign) BOOL bottomDataEn;

@end


NS_ASSUME_NONNULL_END
