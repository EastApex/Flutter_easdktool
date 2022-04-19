//
//  EALanguageModel.h
//  EABluetooth
//
//  Created by Aye on 2021/3/19.
//

#import <EABluetooth/EABaseModel.h>
NS_ASSUME_NONNULL_BEGIN


/// 设备语言
@interface EALanguageModel : EABaseModel

/** 设备语言类型 --》具体见枚举*/
@property(nonatomic, assign) EALanguageType language;


/// MARK: - 获取设备语言相关信息
/// @param data 数据流
+ (EALanguageModel *)getModelByData:(NSData *)data;





@end

NS_ASSUME_NONNULL_END
