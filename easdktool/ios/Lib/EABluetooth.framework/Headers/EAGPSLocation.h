//
//  EAGPSLocation.h
//  EABluetooth
//
//  Created by Aye on 2025/4/8.
//

#import <EABluetooth/EABluetooth.h>

NS_ASSUME_NONNULL_BEGIN

@interface EAGPSLocation : EABaseModel

@property(nonatomic, assign) CGFloat latitude;
@property(nonatomic, assign) CGFloat longitude;

- (instancetype)eaInitUploadGPSLocationWithLatitude:(CGFloat)latitude longitude:(CGFloat)longitude;

@end

NS_ASSUME_NONNULL_END
