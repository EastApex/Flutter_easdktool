//
//  EACaloriesSettingModel.h
//  EABluetooth
//
//  Created by Aye on 2021/3/22.
//

#import <EABluetooth/EABaseModel.h>

NS_ASSUME_NONNULL_BEGIN


/// 基础卡路里开关
@interface EACaloriesSettingModel : EABaseModel

/** 开关： 0关闭 1打开 */
@property(nonatomic, assign) NSInteger sw;

/// MARK: - 获取卡路里开关设置相关信息
/// @param data 数据流
+ (EACaloriesSettingModel *)getModelByData:(NSData *)data;




@end

NS_ASSUME_NONNULL_END
