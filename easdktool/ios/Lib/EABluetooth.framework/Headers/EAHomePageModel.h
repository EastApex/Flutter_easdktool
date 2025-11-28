//
//  EAHomePageModel.h
//  EABluetooth
//
//  Created by Aye on 2021/5/8.
//  File Name:31:Level 1 Menu Settings【手表一级菜单】


#import <EABluetooth/EABaseModel.h>

NS_ASSUME_NONNULL_BEGIN

@class EAPageModel;


/**
 * id = 31
 * 手表一级菜单设置
 * Level 1 menu Settings
 */
@interface EAHomePageModel : EABaseModel

/// Set a new menu type【设置新的菜单类型】
@property(nonatomic, strong) NSMutableArray<EAPageModel*> *sPageArray;

/// Menu types supported for setting【支持设置的菜单类型】
@property(nonatomic, strong) NSMutableArray<EAPageModel*> *supportPageArray;

@end

/**
 * Page
 * 页面类型
 */
@interface EAPageModel : EABaseModel

@property(nonatomic, assign) EAFirstLeverType eType;

@end

NS_ASSUME_NONNULL_END
