//
//  EAHabitTrackerModel.h
//  EABluetooth
//
//  Created by Aye on 2022/3/9.
//

#import <EABluetooth/EABluetooth.h>

NS_ASSUME_NONNULL_BEGIN
/**
 目前仅支持 G01系列
 
 */



@interface EAHabitTrackerModel : EABaseModel

@property(nonatomic, assign) EAHabitTrackerIconType eIconId;

/** read respond中各提醒的id，其他情况为0 */
@property(nonatomic, assign) NSInteger id_p;

/** 起始时间(时) */
@property(nonatomic, assign) NSInteger beginHour;

/** 起始时间(分) */
@property(nonatomic, assign) NSInteger beginMinute;

/** 结束时间(时) */
@property(nonatomic, assign) NSInteger endHour;

/** 结束时间(分) */
@property(nonatomic, assign) NSInteger endMinute;

/** 调色版RGB565 (R) */
@property(nonatomic, assign) NSInteger r;

/** 调色版RGB565 (G) */
@property(nonatomic, assign) NSInteger g;

/** 调色版RGB565 (B) */
@property(nonatomic, assign) NSInteger b;

/** 贪睡时间（单位：分钟） */
@property(nonatomic, assign) NSInteger duration;

/** 提醒方式 */
@property(nonatomic, assign) EARemindActionType eAction;

/** 自定义内容：最多支持32字节的utf8，字符串（大小详见对应OPTIONS文件） */
@property(nonatomic, copy,) NSString *content;

@end

/**
 * id = 38 : 习惯追踪
 * write request：   (上位机--->固件 id：38)       respond：（固件--->上位机 id：39）
 * read  request:    (上位机--->固件 id：2)        respond：（固件--->上位机 id：38）
 **/
@interface EAHabitTrackers : EABaseModel

@property(nonatomic, assign) EAHabitTrackerOps eOps;

/** id: 在write request的ops为编辑 删除此条操作中赋值，其他情况为0 */
@property(nonatomic, assign) NSInteger id_p;

/** 最多20个 */
@property(nonatomic, strong) NSMutableArray<EAHabitTrackerModel*> *sIndexArray;

+ (EAHabitTrackers *)getModelByData:(NSData *)data;

@end

/**
 * id = 39 ：习惯追踪回应
 *     write request：   (上位机--->固件 id：38)        respond：（固件--->上位机 id：39）
 *     read  request:     不支持            respond：不支持
 **/
@interface EAHabitTrackerRespondModel : EABaseModel

/** 操作状态 */
@property(nonatomic, assign) EAHabitTrackerRespondType eOpsStatus;

/** 提醒id: 在write request的ops为新增 编辑回应中赋值，其他情况为0 */
@property(nonatomic, assign) NSInteger id_p;

+ (EAHabitTrackerRespondModel *)getModelByData:(NSData *)data;
@end





NS_ASSUME_NONNULL_END
