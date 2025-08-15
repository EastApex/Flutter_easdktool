//
//  EAPeripheralModel.h
//  Template
//
//  Created by Aye on 2021/3/15.
//

#import <Foundation/Foundation.h>

@class CBPeripheral;
NS_ASSUME_NONNULL_BEGIN

@interface EAPeripheralModel : NSObject<NSCopying>

/// CBPeripheral
@property(nonatomic,strong) CBPeripheral *peripheral;
@property(nonatomic,strong) NSNumber *RSSI;
/// Broadcast packet
@property(nonatomic,strong) NSDictionary *advertisementData;
/// sn号 或者 mac地址
@property(nonatomic,strong) NSString *SN;
@property(nonatomic,strong) NSString *clientCode;

/// Bluetooth watch name
@property(nonatomic,strong) NSString *localName;

/// ignore
@property(nonatomic,strong) NSString *kehuhao;
@property(nonatomic,strong) NSString *banben;
@property(nonatomic,assign) NSInteger number;

@property(nonatomic,assign) NSInteger ap;
@property(nonatomic,assign) NSInteger b;
@property(nonatomic,assign) NSInteger r;
@property(nonatomic,assign) NSInteger b1;

/// quantity of electricity
@property(nonatomic,assign) NSInteger charge;

@property(nonatomic,assign) NSInteger fStatus;


/// 1: jl_707
@property(nonatomic,assign) NSInteger typeOfOTA;
/// 1: in uboot
@property(nonatomic,assign) NSInteger statusOfOTA;

@end

NS_ASSUME_NONNULL_END
