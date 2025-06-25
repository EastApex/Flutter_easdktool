//
//  EAAiText.h
//  EABluetooth
//
//  Created by Aye on 2025/4/8.
//

#import <EABluetooth/EABluetooth.h>

NS_ASSUME_NONNULL_BEGIN

@interface EAAiText : EABaseModel

/** 类型 */
@property(nonatomic, assign) EAAiTextType eType;

@property(nonatomic, assign) NSInteger aiTxtLen;

/** AI文字信息 (一次不能超过2000字)*/
@property(nonatomic, strong) NSString *aiTxt; 

+ (instancetype)eaInitAiTextWithType:(EAAiTextType)type text:(NSString * )text;


@end

NS_ASSUME_NONNULL_END
