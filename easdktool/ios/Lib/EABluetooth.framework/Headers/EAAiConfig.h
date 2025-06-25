//
//  EAAiConfig.h
//  EABluetooth
//
//  Created by Aye on 2025/4/14.
//

#import <EABluetooth/EABluetooth.h>

NS_ASSUME_NONNULL_BEGIN

@interface EAAiConfig : EABaseModel
/** 深度思考使能  0:禁用 1:使能 */
@property(nonatomic, assign) NSInteger deepThinkingEn;

/** 联网搜索使能  0:禁用 1:使能 */
@property(nonatomic, assign) NSInteger onlineSearchEn;



@end

NS_ASSUME_NONNULL_END
