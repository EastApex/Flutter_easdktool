//
//  EAAiConfig.h
//  EABluetooth
//
//  Created by Aye on 2025/4/14.
//  File Name:77:AI Configuration【AI配置】

#import <EABluetooth/EABluetooth.h>

NS_ASSUME_NONNULL_BEGIN


/**
 * id = 77
 * AI配置
 * AI configuration
*/
@interface EAAiConfig : EABaseModel

/// Deep thinking【深度思考】
@property(nonatomic, assign) NSInteger deepThinkingEn;

/// Online search【联网搜索】
@property(nonatomic, assign) NSInteger onlineSearchEn;



@end

NS_ASSUME_NONNULL_END
