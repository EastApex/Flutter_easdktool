//
//  EALanguageModel.h
//  EABluetooth
//
//  Created by Aye on 2021/3/19.
//

#import <EABluetooth/EABaseModel.h>
NS_ASSUME_NONNULL_BEGIN


/**
 * Language
 * 语种
 */
@interface EALanguageItemModel : EABaseModel

/// Language Type【语言类型】
@property(nonatomic, assign) EALanguageType languageName;

@end


/**
 * id = 10
 * Watch language information
 * 设备语言
 */
@interface EALanguageModel : EABaseModel

/// Language【语种】
@property(nonatomic, assign) EALanguageType language;

/// Support languages 【支持的语言】
@property(nonatomic, copy) NSArray<EALanguageItemModel*> *sLanguageArray;


/// Initialize【初始化】
///- Parameter languageType: Language type【语言类型】
+ (instancetype)eaInitWithLanguageType:(EALanguageType )languageType;
@end

NS_ASSUME_NONNULL_END
