//
//  EABingingOps.h
//  EABluetooth
//
//  Created by Aye on 2021/3/19.
//

#import <EABluetooth/EABaseModel.h>
NS_ASSUME_NONNULL_BEGIN


/// 绑定操作 
@interface EABingingOps : EABaseModel

/** 绑定操作进度 */
@property(nonatomic, assign) EABindingOpsType ops;

/** 用户id */
@property(nonatomic, strong) NSString *userId;

/** 绑定模式 0: 正常模式  1: 计步数据固定间隔存储 */
@property(nonatomic, assign) NSInteger bindMod;

+ (EABingingOps *)getModelByData:(NSData *)data ;

@end

NS_ASSUME_NONNULL_END
