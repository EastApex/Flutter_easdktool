//
//  EAVoiceData.h
//  EABluetooth
//
//  Created by Aye on 2025/4/11.
//

#import <EABluetooth/EABluetooth.h>

NS_ASSUME_NONNULL_BEGIN

@interface EAVoiceData : EABaseBigDataModel

@property(nonatomic, assign) NSInteger voicePackageLen;
@property(nonatomic, strong) NSData *voicePackage;

+ (EAVoiceData *)getBigData:(NSData *)data;
@end

NS_ASSUME_NONNULL_END
