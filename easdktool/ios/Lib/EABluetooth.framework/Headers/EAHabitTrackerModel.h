//
//  EAHabitTrackerModel.h
//  EABluetooth
//
//  Created by Aye on 2022/3/9.
//  File Name:38:Habit Tracker【习惯】


#import <EABluetooth/EABluetooth.h>

NS_ASSUME_NONNULL_BEGIN


@class EAHabitTrackerModel;

/**
 * id = 38
 * 习惯追踪
 * Habit Tracker
 */
@interface EAHabitTrackers : EABaseModel


@property(nonatomic, assign) EAHabitTrackerOps eOps;

@property(nonatomic, assign) NSInteger id_p;

///  Up to 12 【最多12个】
@property(nonatomic, strong) NSMutableArray<EAHabitTrackerModel*> *sIndexArray;

/// will delete id_ps【要删除的id_p】
@property(nonatomic,strong)NSMutableArray<NSNumber*> *sDelIndexArray;


@end

/**
 * 习惯追踪
 * Habit Tracker Model
 */
@interface EAHabitTrackerModel : EABaseModel

/// icon ID
@property(nonatomic, assign) EAHabitTrackerIconType eIconId;

/// id
@property(nonatomic, assign) NSInteger id_p;

/// Start time: hour【开始时间:时】
@property(nonatomic, assign) NSInteger beginHour;

/// Start time: minute【开始时间:分】
@property(nonatomic, assign) NSInteger beginMinute;

/// End time: hour【结束时间:时】
@property(nonatomic, assign) NSInteger endHour;

/// End time: minute【结束时间:分】
@property(nonatomic, assign) NSInteger endMinute;

/// Red value (0 - 255)【红色数值（0~255）】
@property(nonatomic, assign) NSInteger r;

/// Green value (0 - 255)【绿色数值（0~255）】
@property(nonatomic, assign) NSInteger g;

/// Blue value (0 - 255)【蓝色数值（0~255）】
@property(nonatomic, assign) NSInteger b;

/// Snooze time (minute)【贪睡时间（单位：分钟）写死30分钟 】
@property(nonatomic, assign) NSInteger duration;


@property(nonatomic, assign) EARemindActionType eAction;

@property(nonatomic, copy,) NSString *content;

/// Cycle【周期】
@property(nonatomic, assign) NSInteger weekCycleBit;

/// Status
@property(nonatomic, assign) EAHabitTrackerFlag eFlag;


@end


/**
 * id = 39 ：习惯追踪回应
 *     write request：   (上位机--->固件 id：38)        respond：（固件--->上位机 id：39）
 *     read  request:     不支持            respond：不支持
 **/
NS_CLASS_DEPRECATED_IOS(9_0, 9_0, "No longer supported; please adopt EARespondModel") __TVOS_PROHIBITED
//  NOT_ANALYSIS_CLASS
@interface EAHabitTrackerRespondModel : EABaseModel

/** 操作状态 */
@property(nonatomic, assign) EAHabitTrackerRespondType eOpsStatus;

/** 提醒id: 在write request的ops为新增 编辑回应中赋值，其他情况为0 */
@property(nonatomic, assign) NSInteger id_p;

+ (EAHabitTrackerRespondModel *)getModelByData:(NSData *)data;


@end





NS_ASSUME_NONNULL_END
