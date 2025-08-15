//
//  EACusWatchFaceConfig.h
//  EABluetooth
//
//  Created by Aye on 2025/4/8.
//

#import <EABluetooth/EABluetooth.h>

NS_ASSUME_NONNULL_BEGIN







/// 海思表盘自定义配置
@interface EACusWatchFaceBaseConfig : EABaseModel

/** 配置1:照片颜色使能 */
@property(nonatomic, assign) BOOL photoColorEn;

/** 配置2:时间样式使能 */
@property(nonatomic, assign) BOOL timeStyleEn;

/** 配置3:时间位置使能 */
@property(nonatomic, assign) BOOL timePositionEn;

/** 配置4:时间颜色使能 */
@property(nonatomic, assign) BOOL timeColorEn;

/** 配置5:方:表盘左上方数据使能 /圆:表盘上方数据使能 */
@property(nonatomic, assign) BOOL topDataEn;

/** 配置6:方:表盘右上方数据使能 /圆:表盘右方数据使能 */
@property(nonatomic, assign) BOOL rightDataEn;

/** 配置7:方:表盘左下方数据使能 /圆:表盘左方数据使能 */
@property(nonatomic, assign) BOOL leftDataEn;

/** 配置8:方:表盘右下方数据使能 /圆:表盘下方数据使能 */
@property(nonatomic, assign) BOOL bottomDataEn;

@end


@interface EACusWatchFaceConfig : EABaseModel

/** 固件端支持表盘的最大个数 */
@property(nonatomic, assign) NSInteger wfMaxCount;
@property(nonatomic, strong) EACusWatchFaceBaseConfig *sBaseConfig;

/**Support Func => See ENUM EACusWatchFaceFunctionType */
@property(nonatomic, strong) NSMutableArray *sFuncSelectArray;


@end

NS_ASSUME_NONNULL_END
