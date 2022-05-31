//
//  EASportShowDataModel.h
//  EABluetooth
//
//  Created by Aye on 2022/5/30.
//

#import <EABluetooth/EABluetooth.h>

NS_ASSUME_NONNULL_BEGIN

@interface EASportShowDataModel : EABaseModel

/** 运动显示：步数 */
@property(nonatomic, assign) NSInteger steps;

/** 运动显示：卡路里（单位:小卡) */
@property(nonatomic, assign) NSInteger calorie;

/** 运动显示：距离 （单位:厘米） */
@property(nonatomic, assign) NSInteger distance;

/** 运动显示：运动时长(单位:秒) */
@property(nonatomic, assign) NSInteger duration;

+ (EASportShowDataModel *)getModelByData:(NSData *)data;

@end

NS_ASSUME_NONNULL_END
