//
//  EAOpsBt.h
//  EABluetooth
//
//  Created by Aye on 2023/7/21.
//  File Name:59:BT Control【BT控制】

#import <EABluetooth/EABluetooth.h>

NS_ASSUME_NONNULL_BEGIN


/**
 * id = 59
 * Classic Bluetooth BT Control
 * 经典蓝牙BT控制
 */
@interface EAOpsBt : EABaseModel

/// BT Type
@property(nonatomic, assign) EABtType eBtStatusType;


/// Initialization
/// - Parameter eaBtType: BT Type
+ (instancetype )eaInitOnOff:(EABtType)eaBtType;

@end

NS_ASSUME_NONNULL_END
