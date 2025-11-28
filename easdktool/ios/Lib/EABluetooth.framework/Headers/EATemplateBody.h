//
//  EATemplateBody.h
//  EABluetooth
//
//  Created by Aye on 2024/6/13.
//  File Name:66:Template body【体温】

#import <EABluetooth/EABluetooth.h>

NS_ASSUME_NONNULL_BEGIN
/**
 *  id = 66
 *  template body
 *  体温
 */
@interface EATemplateBody : EABaseModel

/// 0 off, 1 on
@property(nonatomic, assign) BOOL sw;

/// Interval time, unit: minutes (It is recommended to be no less than 60 minutes)
@property(nonatomic, assign) NSInteger interval;

@end

NS_ASSUME_NONNULL_END
