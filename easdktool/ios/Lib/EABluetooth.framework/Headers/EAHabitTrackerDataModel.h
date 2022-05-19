//
//  EAHabitTrackerDataModel.h
//  EABluetooth
//
//  Created by Aye on 2022/5/13.
//

#import <EABluetooth/EABluetooth.h>
@class EAHabitTrackerModel;
NS_ASSUME_NONNULL_BEGIN

/// 习惯大数据
@interface EAHabitTrackerDataModel : EABaseBigDataModel

@property(nonatomic, strong) NSMutableArray<EAHabitTrackerModel*> *sIndexArray;

+ (EAHabitTrackerDataModel *)getHabitTrackerData:(NSData *)data;

@end

NS_ASSUME_NONNULL_END
