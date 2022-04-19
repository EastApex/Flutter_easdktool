//
//  EABleManager.h
//  Template
//
//  Created by Aye on 2021/3/15.
//

#import <Foundation/Foundation.h>
#import <CoreBluetooth/CoreBluetooth.h>
#import "EAPeripheralModel.h"
#import "EABleConfig.h"
#import <EABluetooth/EAEnum.h>
NS_ASSUME_NONNULL_BEGIN


/// MARK: -  绑定类型
typedef NS_ENUM(NSUInteger, EAConnectStatus) {
    
    /** 链接失败 */
    EAConnectStatusFailed = 0,
    
    /** 链接成功 */
    EAConnectStatusSucceed = 1,
    
    /** 断开链接 */
    EAConnectStatusDisconnect = 2,
    
    /** 蓝牙开启 */
    EABlePoweredOn = 3,
    
    /** 蓝牙关闭 */
    EABlePoweredOff = 4,
    
    /** 链接失败,需要忽略设备 */
    EAConnectStatusFailedWithRemovedPairing = 5,
    
    /** 链接失败,超时 */
    EAConnectStatusFailedWithTimeOut = 6,
    
};

/** 连接失败 */
#define kNTF_EAConnectStatusFailed          @"EAConnectStatusFailed"
/** 连接成功 */
#define kNTF_EAConnectStatusSucceed         @"EAConnectStatusSucceed"
/** 断开链接 */
#define kNTF_EAConnectStatusDisconnect      @"EAConnectStatusDisconnect"
/** 蓝牙开启 */
#define kNTF_EABlePoweredOn                 @"EABlePoweredOn"
/** 蓝牙关闭 */
#define kNTF_EABlePoweredOff                @"EABlePoweredOff"
/** 收到手表发送的操作手机消息 */
#define kNTF_EAGetDeviceOpsPhoneMessage     @"EAGetDeviceOpsPhoneMessage"

/** OTA数据传输完成 */
#define kNTF_EAOTAAGPSDataFinish            @"EAOTAAGPSDataFinish"
/** OTA进度 */
#define kNTF_EAOTAAGPSDataing               @"EAOTAAGPSDataing"

/**  */
#define kNTF_EABackgroundTask               @"EABackgroundTask"

/**
    必须实现的 通知
    

 */



@protocol EABleManagerDelegate <NSObject>

/// 扫描发现的设备 （实时）
/// @param peripheralModel 设备
- (void)didDiscoverPeripheral:(EAPeripheralModel *)peripheralModel;

@end

/// 蓝牙数据代理
@protocol EABleManagerDataDelegate <NSObject>

/// 蓝牙数据
/// @param characteristic 通道
/// @param error 错误信息
- (void)updateValueForCharacteristic:(CBCharacteristic *)characteristic error:(NSError *)error;

@end


/// 蓝牙数据
typedef void(^UpdateValueBlock)(CBCharacteristic *characteristic,NSError *error);

@interface EABleManager : NSObject

/// 蓝牙设置
@property (nonatomic,strong) EABleConfig *bleConfig;

/// 搜索设备代理
@property(nonatomic,weak) id<EABleManagerDelegate> delegate;

/// 蓝牙数据代理
@property(nonatomic,weak) id<EABleManagerDataDelegate> dataDelegate;

///// 链接设备状态
//@property(nonatomic,assign) BOOL isConnected;

/// 连接设备状态
@property(nonatomic,assign) EAConnectStateType connectState;


/// 单例
+ (instancetype)defaultManager;


#pragma mark - 设备相关方法

/// 扫描
- (void)scanPeripherals;
/// 停止扫描设备
- (void)stopScanPeripherals;

/// 连接设备
/// @param peripheralModel 设备
- (void)connectToPeripheral:(EAPeripheralModel *)peripheralModel;

/// 重连设备
- (void)reConnectToPeripheral ;
/// 重连设备
- (void)reConnectToPeripheral:(NSString *)sn;

- (void)cancelConnectingPeripheral;

/// 解绑设备（移除关联）
- (void)unbindPeripheral;


/// 断链设备(不移除关联)
- (void)disconnectPeripheral;


/// 设备数据交互
/// @param Data 数据流
/// @param characteristicType 通道类型
- (void)writeValue:(NSData *)Data forCharacteristic:(EACharacteristicType )characteristicType;


/// 是否开启蓝牙
- (BOOL)isBleOn;

/// 是否已连接设备
- (BOOL)isConnected;

/// 获取链接的设备信息
- (EAPeripheralModel *)getPeripheralModel;



@end

NS_ASSUME_NONNULL_END
