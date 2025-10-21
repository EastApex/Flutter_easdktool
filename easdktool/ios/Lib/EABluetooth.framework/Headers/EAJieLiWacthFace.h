//
//  EAJieLiWacthFace.h
//  EABluetooth
//
//  Created by Aye on 2025/9/5.
//

#import <EABluetooth/EABluetooth.h>

NS_ASSUME_NONNULL_BEGIN


@interface EAJieLiWacthFace : EABaseModel

/// 获取杰里表盘名称列表
+ (BOOL)eaGetWatchFaceList:(void (^)(NSArray *wfNames,NSError *error))complete;

/// 删除杰里表盘
/// - Parameters:
///   - wfName: 表盘名称
+ (BOOL)eaDelWatchFace:(NSString *)wfName complete:(void (^)(BOOL succ,NSError *error))complete;

/// 设置杰里当前表盘
/// - Parameters:
///   - wfName: 表盘名称
+ (BOOL)eaSetCurrentWatchFace:(NSString *)wfName complete:(void (^)(BOOL succ,NSError *error))complete;

@end

@interface EAJieLiOldWacthFace : EABaseModel

/// 最早更新的外置表盘ID ,用于删除表盘
@property(nonatomic, strong) NSString *oldestId;
@end

@interface EAJieLiCustomWacthFace : EABaseModel

/** 配置:时间样式 */
@property(nonatomic, assign) EAJieLiCusWatchFaceSetTimeStyle eCusTimeStyle;

/** 操作 */
@property(nonatomic, assign) EAJieLiCusWatchFaceOps eCusops;

+ (instancetype)eaInitOtaCusTimeStyle:(EAJieLiCusWatchFaceSetTimeStyle)eCusTimeStyle;
+ (instancetype)eaInitSetCusTimeStyle:(EAJieLiCusWatchFaceSetTimeStyle)eCusTimeStyle;

@end



NS_ASSUME_NONNULL_END
