//
//  EAHomePageModel.h
//  EABluetooth
//
//  Created by Aye on 2021/5/8.
//

#import <EABluetooth/EABaseModel.h>

NS_ASSUME_NONNULL_BEGIN


@interface EAPageModel : EABaseModel

/** 一级菜单类型描述 */
@property(nonatomic, assign) EAFirstLeverType eType;

@end


/// 一级菜单
@interface EAHomePageModel : EABaseModel


@property(nonatomic, strong) NSMutableArray<EAPageModel*> *sPageArray;


/// MARK: - 获取一级菜单
/// @param data 数据流
+ (EAHomePageModel *)getModelByData:(NSData *)data;
@end

NS_ASSUME_NONNULL_END
