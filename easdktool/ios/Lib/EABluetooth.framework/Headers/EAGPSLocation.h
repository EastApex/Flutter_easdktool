//
//  EAGPSLocation.h
//  EABluetooth
//
//  Created by Aye on 2025/4/8.
//  File Name:72:Current Location【当前位置】


#import <EABluetooth/EABluetooth.h>

NS_ASSUME_NONNULL_BEGIN



/**
 * id = 72
 * Current location (longitude and latitude)
 * 当前位置（经纬度）
 */
@interface EAGPSLocation : EABaseModel

@property(nonatomic, assign) CGFloat latitude;
@property(nonatomic, assign) CGFloat longitude;


/// Initialization
/// - Parameters:
///   - latitude: latitude
///   - longitude: longitude 
+ (instancetype)eaInitUploadGPSLocationWithLatitude:(CGFloat)latitude longitude:(CGFloat)longitude;

@end

NS_ASSUME_NONNULL_END
