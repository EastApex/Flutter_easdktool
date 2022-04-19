//
//  EAReminderModel.h
//  EABluetooth
//
//  Created by Aye on 2021/3/22.
//

#import <EABluetooth/EABaseModel.h>

NS_ASSUME_NONNULL_BEGIN


/// 提醒事件
@interface EAReminderModel : EABaseModel


/** 提醒事件类型 */
@property(nonatomic, assign) EAReminderEventType reminderEventType;


@property(nonatomic, assign) NSInteger id_p;

/** 时 */
@property(nonatomic, assign) NSInteger hour;

/** 分 */
@property(nonatomic, assign) NSInteger minute;

/** 年 */
@property(nonatomic, assign) NSInteger year;

/** 月 */
@property(nonatomic, assign) NSInteger month;

/** 日 */
@property(nonatomic, assign) NSInteger day;

/** 周期：位对应从bit0~bit6对应周日~周六 */
@property(nonatomic, assign) NSInteger weekCycleBit;

/** 开关 */
@property(nonatomic, assign) NSInteger sw;

/** 二次提醒开关 */
@property(nonatomic, assign) NSInteger secSw;

/** 贪睡时间（单位：秒） */
@property(nonatomic, assign) NSInteger sleepDuration;

/** 提醒方式 */
@property(nonatomic, assign) EARemindActionType remindActionType;

/** 自定义内容：最多支持96字节的utf8，字符串（大小详见对应OPTIONS文件） */
@property(nonatomic, strong) NSString *content;

@end


/// 提醒事件操作
@interface EAReminderOps : EABaseModel

@property(nonatomic, assign) EAReminderEventOps eOps;

@property(nonatomic, assign) NSInteger id_p;

/** 最多16个（大小详见对应OPTIONS文件） */
@property(nonatomic,strong) NSMutableArray<EAReminderModel*> *sIndexArray;




/// MARK: - 获取提醒事件操作设置相关信息
/// @param data 数据流
+ (EAReminderOps *)getModelByData:(NSData *)data ;


/// MARK: - 获取提醒事件操作设置数据流
- (NSData *)getModelData ;




@end



NS_ASSUME_NONNULL_END
