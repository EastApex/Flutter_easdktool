//
//  EABingingOps.h
//  EABluetooth
//
//  Created by Aye on 2021/3/19.
//  File Name:6:Binding Watch【绑定手表】


#import <EABluetooth/EABaseModel.h>
NS_ASSUME_NONNULL_BEGIN


/**
 * id = 6
 * Binding watch
 * 绑定手表
 */
@interface EABingingOps : EABaseModel

/// Binding watch operation type: defalut set EABindingOpsTypeEnd【绑定状态】
@property(nonatomic, assign) EABindingOpsType ops;

/// userId:you need set the userId to complete the binding watch【用户id:必须要设置 用户id才能完成绑定手表】
@property(nonatomic, strong) NSString *userId;

/// Daily step recording mode: 0: normal mode 1: Step data stored at fixed intervals (30 minutes)【日常步数记录方式：0: 正常模式  1: 计步数据固定间隔存储（30分钟）】
@property(nonatomic, assign) NSInteger bindMod;


/// 开始绑定
/// - Parameter uid: 用户ID,手表可能用于校验用户
+ (instancetype)eaInitBeginWithUid:(NSString *)uid;

/// 结束绑定
/// - Parameter uid: 用户ID，手表设定为最终用户
+ (instancetype)eaInitEndWithUid:(NSString *)uid;


@end

NS_ASSUME_NONNULL_END
