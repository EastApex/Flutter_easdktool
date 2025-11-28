//
//  EAWatchFaceDelete.h
//  EABluetooth
//
//  Created by Aye on 2023/6/29.
//  File Name:56:Remove Watch Faces【删除表盘】


#import <EABluetooth/EABluetooth.h>

NS_ASSUME_NONNULL_BEGIN

/**
 *  id = 56
 *  删除自定义（在线）表盘
 *  Remove custom (online) watch faces
 */
@interface EAWatchFaceDelete : EABaseModel

/// Custom (online) watch face id【自定义（在线）表盘id】
@property(nonatomic, strong) NSString *userWfId;


/// Initialization
/// - Parameter userWfId: userWfId
+ (instancetype )eaInitWithWfId:(NSString *)userWfId;

@end

NS_ASSUME_NONNULL_END
