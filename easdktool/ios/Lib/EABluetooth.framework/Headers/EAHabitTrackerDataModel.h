//
//  EAHabitTrackerDataModel.h
//  EABluetooth
//
//  Created by Aye on 2022/5/13.
//

#import <EABluetooth/EABluetooth.h>
NS_ASSUME_NONNULL_BEGIN



@interface EAHistoryHabitTrackerModel : EABaseModel

/** 时间戳 （决定是哪一天的） */
@property(nonatomic, assign) NSInteger timeStamp;

@property(nonatomic, readwrite) EAHabitTrackerIconType eIconId;

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

/** 标志 */
@property(nonatomic, assign) EAHabitTrackerFlag eFlag;

/** 自定义内容：最多支持64字节的utf8，字符串（大小详见对应OPTIONS文件） */
@property(nonatomic, strong) NSString *content;

@end


/// 习惯大数据
@interface EAHabitTrackerDataModel : EABaseBigDataModel

@property(nonatomic, strong) NSMutableArray<EAHistoryHabitTrackerModel*> *sIndexArray;

+ (EAHabitTrackerDataModel *)getHabitTrackerData:(NSData *)data;

@end

NS_ASSUME_NONNULL_END
