//
//  EABlePairStateModel.h
//  EABluetooth
//
//  Created by Aye on 2022/6/24.
//  File Name:41:Watch Pair State【手表配对情况】


#import <EABluetooth/EABluetooth.h>

NS_ASSUME_NONNULL_BEGIN


/**
 * id = 41:
 * 手表配对情况
 * Watch Pair State
 */
@interface EABlePairStateModel : EABaseModel
/// 0: Not paired. 1: Already paired【0: 没有配对 1: 已经配对】
@property(nonatomic, assign) BOOL secState;


@end

NS_ASSUME_NONNULL_END
