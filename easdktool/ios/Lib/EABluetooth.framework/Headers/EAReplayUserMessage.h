//
//  EAReplayUserMessage.h
//  EABluetooth
//
//  File Name:59:User-defined Reply Messages【自定义回复消息】


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
