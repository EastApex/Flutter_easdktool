//
//  EASocialSwitchModel.h
//  EABluetooth
//
//  Created by Aye on 2021/3/22.
//

#import <EABluetooth/EABaseModel.h>

NS_ASSUME_NONNULL_BEGIN



/// 社交提醒设置
@interface EASocialOps : EABaseModel

/** 开关 */
@property(nonatomic, assign) NSInteger sw;

/** 提醒方式 */
@property(nonatomic, assign) EARemindActionType remindActionType;

@end


/// 社交提醒开关
@interface EASocialSwitchModel : EABaseModel

/** 来电 */
@property(nonatomic, strong) EASocialOps *sIncomingcall;


/** 未知来电 */
@property(nonatomic, strong) EASocialOps *sMissedcall;


/** 短信 */
@property(nonatomic, strong) EASocialOps *sSms;


/** 社交 */
@property(nonatomic, strong) EASocialOps *sSocial;


/** 邮件 */
@property(nonatomic, strong) EASocialOps *sEmail;


/** 日程 */
@property(nonatomic, strong) EASocialOps *sSchedule;


/// MARK: - 获取社交提醒开关设置相关信息
/// @param data 数据流
+ (EASocialSwitchModel *)getModelByData:(NSData *)data ;


/// MARK: - 获取社交提醒开关设置数据流
- (NSData *)getModelData ;


@end



NS_ASSUME_NONNULL_END
