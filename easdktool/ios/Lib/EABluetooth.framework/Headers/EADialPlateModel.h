//
//  EADialPlateModel.h
//  EABluetooth
//
//  Created by Aye on 2021/6/21.
//  File Name:33:Watch Face【表盘信息】


#import <EABluetooth/EABluetooth.h>

NS_ASSUME_NONNULL_BEGIN



/**
 * id = 33
 * 表盘信息
 * Dial information（Watch Face information）
 */
@interface EADialPlateModel : EABaseModel

/// Dial id, (0 represents online custom dial, 1 to n, built-in dial number)【表盘id, (0代表在线自定义表盘，1~n，内置表盘编号)】
@property(nonatomic, assign) NSInteger id_p;

/// Other types of dial ids. If set, it has a value【当前设置的自定义表盘id,如果设置了就有值】
@property(nonatomic, copy) NSString *userWfId;

@property(nonatomic, copy) NSString * userWfId0;

@property(nonatomic, copy) NSString * userWfId1;

@property(nonatomic, copy) NSString * userWfId2;

@property(nonatomic, copy) NSString * userWfId3;

@property(nonatomic, copy) NSString *userWfId4;

@property(nonatomic, copy) NSString *userWfId5;

@property(nonatomic, copy) NSString *userWfId6;

@property(nonatomic, copy) NSString *userWfId7;

@property(nonatomic, copy) NSString *userWfId8;

@property(nonatomic, copy) NSString *userWfId9;



/// show the built-in watch face
/// - Parameter id_p: Dial id
+ (instancetype)eaInitBuiltInWatchFaceWithID:(NSInteger )id_p;


/// show the other watch face
/// - Parameter userWfId: Dial id
+ (instancetype)eaInitCustomWatchFaceWithID:(NSString *)userWfId;


@end

NS_ASSUME_NONNULL_END
