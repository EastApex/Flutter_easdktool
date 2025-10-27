//
//  EAJieLiWacthFace.h
//  EABluetooth
//
//  Created by Aye on 2025/9/5.
//

#import <EABluetooth/EABluetooth.h>

NS_ASSUME_NONNULL_BEGIN

/**
 * id = 81
 * JL707平台需要删除的表盘信息
 * The dial information that needs to be deleted on the JL707 platform
*/
@interface EAJieLiOldWacthFace : EABaseModel

/// The earliest updated external watch face ID, used to delete the watch face【最早更新的外置表盘ID ,用于删除表盘】
@property(nonatomic, strong) NSString *oldestId;
@end


/**
 * id = 82
 * JL707平台自定义表盘信息
 * Customize the dial information for the JL707 platform
*/
@interface EAJieLiCustomWacthFace : EABaseModel

/// Configuration: Time style【配置:时间样式】
@property(nonatomic, assign) EAJieLiCusWatchFaceSetTimeStyle eCusTimeStyle;

/// Operation【操作 】
@property(nonatomic, assign) EAJieLiCusWatchFaceOps eCusops;


/// Initialization OTA
/// - Parameter eCusTimeStyle: eCusTimeStyle
+ (instancetype)eaInitOtaCusTimeStyle:(EAJieLiCusWatchFaceSetTimeStyle)eCusTimeStyle;


/// Initialization Set
/// - Parameter eCusTimeStyle: eCusTimeStyle
+ (instancetype)eaInitSetCusTimeStyle:(EAJieLiCusWatchFaceSetTimeStyle)eCusTimeStyle;

@end

/**
 * JL707表盘操作
 * JL707 dial operation
*/
@interface EAJieLiWacthFace : EABaseModel

/// Get the list of JieLi watch face names【获取杰里表盘名称列表】
+ (BOOL)eaGetWatchFaceList:(void (^)(NSArray *wfNames,NSError *error))complete;

/// Delete a JieLi watch face【删除杰里表盘】
/// - Parameters:
///   - wfName: Watch face name【表盘名称】
+ (BOOL)eaDelWatchFace:(NSString *)wfName complete:(void (^)(BOOL succ,NSError *error))complete;

/// Set the current JieLi watch face【设置杰里当前表盘】
/// - Parameters:
///   - wfName: Watch face name【表盘名称】
+ (BOOL)eaSetCurrentWatchFace:(NSString *)wfName complete:(void (^)(BOOL succ,NSError *error))complete;

@end






NS_ASSUME_NONNULL_END
