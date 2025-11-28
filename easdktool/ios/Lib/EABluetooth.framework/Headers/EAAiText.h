//
//  EAAiText.h
//  EABluetooth
//
//  Created by Aye on 2025/4/8.
//  File Name:76:AI Text【AI文本】


#import <EABluetooth/EABluetooth.h>

NS_ASSUME_NONNULL_BEGIN

/**
 * id = 76
 * AI Text
 * AI 文本
*/
@interface EAAiText : EABaseModel

/// Type[类型]
@property(nonatomic, assign) EAAiTextType eType;

/// AI text information (no more than 2,000 words at a time)【AI文字信息 (一次不能超过2000字)】
@property(nonatomic, strong) NSString *aiTxt;

@property(nonatomic, assign) NSInteger aiTxtLen __attribute__((deprecated));


/// Initialization
/// - Parameters:
///   - type: type
///   - text: text
+ (instancetype)eaInitAiTextWithType:(EAAiTextType)type text:(NSString * )text;


@end

NS_ASSUME_NONNULL_END
