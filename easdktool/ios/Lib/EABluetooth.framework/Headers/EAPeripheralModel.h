//
//  EAPeripheralModel.h
//  Template
//
//  Created by Aye on 2021/3/15.
//  File Name:Ble Device【Ble设备】

#import <Foundation/Foundation.h>

@class CBPeripheral;
NS_ASSUME_NONNULL_BEGIN
/**
 * EAPeripheralModel is the data model for SDK to search Bluetooth devices, containing information related to EA watches and Bluetooth device objects.
 * EAPeripheralModel是SDK搜索蓝牙设备数据模型，含有EA手表相关信息和蓝牙设备对象
 */
@interface EAPeripheralModel : NSObject<NSCopying>

/// CBPeripheral【蓝牙设备】
@property(nonatomic,strong) CBPeripheral *peripheral;

/// signal value【信号量】
@property(nonatomic,strong) NSNumber *RSSI;

/// Broadcast packet【广播数据包】
@property(nonatomic,strong) NSDictionary *advertisementData;

/// Serial number or Mac Address【sn或者Mac地址】
@property(nonatomic,strong) NSString *SN;

///client code 【客户端代码】
@property(nonatomic,strong) NSString *clientCode;

/// Bluetooth watch name 【蓝牙名】
@property(nonatomic,strong) NSString *localName;

/// Firmware version number【固件版本号】
@property(nonatomic,assign) NSInteger ap;

/// Firmware version Build number【固件版本Build号】
@property(nonatomic,assign) NSInteger b;

/// Resource version number【资源版本号】
@property(nonatomic,assign) NSInteger r;

/// Which Build is the current version【当前版本第几次Build】
@property(nonatomic,assign) NSInteger builtInVersion;

/// quantity of electricity 【电量】
@property(nonatomic,assign) NSInteger charge;

/// Factory inspection【工厂校验】
@property(nonatomic,assign) NSInteger fStatus;


/// type of OTA ->1: jl_707【OTA类型】
@property(nonatomic,assign) NSInteger typeOfOTA;

/// status of OTA ->1: in uboot【OTA状态】
@property(nonatomic,assign) NSInteger statusOfOTA;


@property(nonatomic,strong) NSString *kehuhao __attribute__((deprecated));
@property(nonatomic,strong) NSString *banben __attribute__((deprecated));
@property(nonatomic,assign) NSInteger number __attribute__((deprecated));

@end

NS_ASSUME_NONNULL_END
