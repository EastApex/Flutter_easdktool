//
//  EACusWatchFaceIds.h
//  EABluetooth
//
//  Created by Aye on 2025/4/8.
//

#import <EABluetooth/EABluetooth.h>

NS_ASSUME_NONNULL_BEGIN


@interface EACusWatchFaceId : EABaseModel

/** 自定义表盘的ID（大小详见对应OPTIONS文件） */
@property(nonatomic, strong) NSString *cusId;

@end

@interface EACusWatchFaceIds : EABaseModel

@property(nonatomic, strong) NSMutableArray<EACusWatchFaceId*> *sStrCusIdArray;

@property(nonatomic,strong) NSMutableArray *ids;

@end

NS_ASSUME_NONNULL_END
