//
//  EADialPlateModel.h
//  EABluetooth
//
//  Created by Aye on 2021/6/21.
//

#import <EABluetooth/EABluetooth.h>

NS_ASSUME_NONNULL_BEGIN

@interface EADialPlateModel : EABaseModel

/** 表盘id, (0代表在线自定义表盘，1~n，内置表盘编号) */
@property(nonatomic, assign) NSInteger id_p;

/** 在线自定义表盘id */
@property(nonatomic, copy) NSString *userWfId;

/** 已存在线自定义表盘id0 */
@property(nonatomic, copy) NSString *userWfId0;

/** 已存在线自定义表盘id1 */
@property(nonatomic, copy) NSString *userWfId1;

/** 已存在线自定义表盘id2 */
@property(nonatomic, copy) NSString *userWfId2;

/** 已存在线自定义表盘id3 */
@property(nonatomic, copy) NSString *userWfId3;

/// MARK: - 获取设备表盘信息
+ (EADialPlateModel *)getModelByData:(NSData *)data;

@end

NS_ASSUME_NONNULL_END
