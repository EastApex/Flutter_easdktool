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

+ (EABingingOps *)getModelByData:(NSData *)data ;

@end

NS_ASSUME_NONNULL_END
