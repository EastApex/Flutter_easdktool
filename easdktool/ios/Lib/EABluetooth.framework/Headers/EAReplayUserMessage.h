//
//  EAReplayUserMessage.h
//  EABluetooth
//
//  Created by Aye on 2023/7/24.
//

#import <EABluetooth/EABluetooth.h>

NS_ASSUME_NONNULL_BEGIN

@class EAReplayUserMessage;


/**
 * id = 60
 * User-defined reply messages
 * 自定义回复消息
 */
@interface EAReplayUserMessageData : EABaseModel


@property(nonatomic, strong) NSMutableArray<EAReplayUserMessage *> *sIndexArray;


@end


@interface EAReplayUserMessage : EABaseModel

@property(nonatomic, strong) NSString *msg;

@end

NS_ASSUME_NONNULL_END
