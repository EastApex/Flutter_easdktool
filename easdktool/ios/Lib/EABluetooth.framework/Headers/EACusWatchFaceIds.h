//
//  EACusWatchFaceIds.h
//  EABluetooth
//
//  Created by Aye on 2025/4/8.
//  File Name:74:His Watch Face ID【自定义表盘的ID】

#import <EABluetooth/EABluetooth.h>

NS_ASSUME_NONNULL_BEGIN
@class EACusWatchFaceId;

/**
 * id = 74
 * Customize the ID of the watch face
 * 自定义表盘的ID
 */
@interface EACusWatchFaceIds : EABaseModel

@property(nonatomic, strong) NSMutableArray<EACusWatchFaceId*> *sStrCusIdArray;

@property(nonatomic,strong) NSMutableArray *ids;

@end

/**
 * Customize the ID of the watch face
 * 自定义表盘的ID
 */
@interface EACusWatchFaceId : EABaseModel

@property(nonatomic, strong) NSString *cusId;

@end



NS_ASSUME_NONNULL_END
