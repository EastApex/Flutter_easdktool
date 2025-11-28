//
//  EAReplayMessage.h
//  EABluetooth
//
//  Created by Aye on 2023/7/24.
//  File Name:2005:Quick Response【快速回复】


#import <EABluetooth/EABluetooth.h>

NS_ASSUME_NONNULL_BEGIN


/**
 * Quick response model: The function of replying to text messages when a user's watch rejects a call
 * 快速回复模型:用户手表拒接电话回复短信的功能
*/
@interface EAReplayMessage : EABaseModel

/// Message ID【消息id】
@property(nonatomic, assign) NSInteger id_p;

/// Content【内容】
@property(nonatomic, strong) NSString *content;

/// Nickname【昵称】
@property(nonatomic, strong) NSString *name;

/// Phone number【电话号码】
@property(nonatomic, strong) NSString *num;
@end

NS_ASSUME_NONNULL_END
