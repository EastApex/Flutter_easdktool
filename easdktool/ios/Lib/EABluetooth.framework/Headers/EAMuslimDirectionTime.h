//
//  EAMuslimDirectionTime.h
//  EABluetooth
//
//  Created by Aye on 2024/11/13.
//  File Name:70:Muslim Direction Time【穆斯林时间】


#import <EABluetooth/EABluetooth.h>

NS_ASSUME_NONNULL_BEGIN
@class EAMuslimTimeItem;



/**
 * id = 70
 * Muslim Direction Time
 * 穆斯林时间
 */
@interface EAMuslimDirectionTime : EABaseModel

@property(nonatomic, strong) NSMutableArray<EAMuslimTimeItem*> *daydirArray;

@property(nonatomic, assign) NSInteger kneelerDir;

@end




@interface EAMuslimTimeItem : EABaseModel

@property(nonatomic, assign) NSInteger fajrTimestamp;

@property(nonatomic, assign) NSInteger sunRiseTimestamp;

@property(nonatomic, assign) NSInteger dhuhrTimestamp;

@property(nonatomic, assign) NSInteger asrTimestamp;

@property(nonatomic, assign) NSInteger maghribTimestamp;

@property(nonatomic, assign) NSInteger ishaTimestamp;


@end





NS_ASSUME_NONNULL_END
