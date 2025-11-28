//  
//  EAVoiceData.h
//  EABluetooth
//
//  Created by Aye on 2025/4/11.
//  File Name:3016:Voice Data【语音】

#import <EABluetooth/EABluetooth.h>

NS_ASSUME_NONNULL_BEGIN

@interface EAVoiceData : EABaseBigDataModel

@property(nonatomic, assign) NSInteger voicePackageLen;
@property(nonatomic, strong) NSData *voicePackage;

/// NOT_ANALYSIS_FUNCTION
+ (EAVoiceData *)getBigData:(NSData *)data;
@end

NS_ASSUME_NONNULL_END
