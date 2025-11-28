//
//  EAHabitTrackerDataModel.h
//  EABluetooth
//
//  Created by Aye on 2022/5/13.
//  File Name:3011:Habit Tracker【习惯】

#import <EABluetooth/EABluetooth.h>
NS_ASSUME_NONNULL_BEGIN


/**
 * Habit tracker data model
 * 习惯数据模型
*/
@interface EAHistoryHabitTrackerModel : EABigDataModel


/// Habit picture resource ID【习惯图片资源id】
@property(nonatomic, assign) EAHabitTrackerIconType eIconId;

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

/// Habit status【习惯状态】
@property(nonatomic, assign) EAHabitTrackerFlag eFlag;

/// Custom content: supports up to 64 bytes of utf8, string【自定义内容：最多支持64字节的utf8，字符串】
@property(nonatomic, strong) NSString *content;

@end


//  NOT_ANALYSIS_CLASS
@interface EAHabitTrackerDataModel : EABaseBigDataModel

@property(nonatomic, strong) NSMutableArray<EAHistoryHabitTrackerModel*> *sIndexArray;

/// NOT_ANALYSIS_FUNCTION
+ (EAHabitTrackerDataModel *)getHabitTrackerData:(NSData *)data;

@end

NS_ASSUME_NONNULL_END
