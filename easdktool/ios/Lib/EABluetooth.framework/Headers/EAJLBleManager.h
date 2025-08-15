//
//  EAJLBleManager.h
//  EABluetooth
//
//  Created by Aye on 2025/6/27.
//

#import <Foundation/Foundation.h>
#import <CoreBluetooth/CoreBluetooth.h>
#import <UIKit/UIKit.h>
#import <EABluetooth/EABleConfig.h>
#import <EABluetooth/EAPeripheralModel.h>













NS_ASSUME_NONNULL_BEGIN

#define SENDBYSINGLE  0 //1：通过信号检测发送 0：通过直接塞数据发送

#define eakFLT_BLE_FOUND         @"FLT_BLE_FOUND"            //发现设备
#define eakFLT_BLE_PAIRED        @"FLT_BLE_PAIRED"          //BLE已配对
#define eakFLT_BLE_CONNECTED     @"FLT_BLE_CONNECTED"        //BLE已连接
#define eakFLT_BLE_DISCONNECTED  @"FLT_BLE_DISCONNECTED"     //BLE断开连接

#define eakFLT_BLE_SERVICE      @"AE00" //服务号
#define eakFLT_BLE_RCSP_W       @"AE01" //命令“写”通道
#define eakFLT_BLE_RCSP_R       @"AE02" //命令“读”通道

#define eaJL_OTA_Progress       @"JL_OTA_Progress" //Progress
#define eaJL_OTA_Complete       @"JL_OTA_Complete" //Complete
#define eaJL_OTA_Fail           @"JL_OTA_Fail" //Fail






@interface HandleBroadcastPtl : NSObject
@property(nonatomic,strong,readonly)NSHashTable         *delegates;
@property(nonatomic,strong)NSLock                       *delegateLock;

-(void)addDelegate:(id)delegate;
-(void)removeDelegate:(id)delegate;
-(void)removeAll;
@end



@interface JLBleEntity : NSObject                                //蓝牙设备模型

@property (strong, nonatomic) NSNumber *mRSSI;
@property (strong, nonatomic) CBPeripheral *mPeripheral;
@property (strong, nonatomic) NSString *mName;
@property (strong, nonatomic) NSString *bleMacAddress;
@property (strong, nonatomic) NSString *edrMacAddress;
@property (assign, nonatomic) uint8_t mType;
@property (assign, nonatomic) uint16_t pid;
@property (assign, nonatomic) uint16_t uid;

@end


@protocol JLBleManagerOtaDelegate <NSObject>

@required
/**
 *  ota升级过程状态回调
 */
- (void)otaProgressWithOtaResult:(UInt8)result withProgress:(float)progress;

@end



@interface EAJLBleManager : HandleBroadcastPtl


@property (assign, nonatomic) CBManagerState mBleManagerState;
@property (strong, nonatomic) CBPeripheral *__nullable mBlePeripheral;

/// 连接蓝牙是否需要认证配对
@property (assign, nonatomic) BOOL isPaired;

/// 配对秘钥（默认为空）
@property (assign, nonatomic) NSData *pairKey;

/// 连接设备的MTU（单次最大发送数据）
@property (assign, nonatomic) NSInteger bleMtu;

/// 上一次连接的蓝牙UUID
@property (strong, nonatomic) NSString *lastUUID;

/// 上一次连接的蓝牙地址
@property (strong, nonatomic) NSString *__nullable lastBleMacAddress;



@property (assign, nonatomic) BOOL isConnected;

@property (strong, nonatomic) JLBleEntity *__nullable currentEntity;

/**
 * 单例
 */
+ (instancetype)sharedInstance;

/**
 开始搜索
 */
-(void)startScanBLE;

/**
 停止搜索
 */
-(void)stopScanBLE;

/**
 连接设备
 @param peripheral 蓝牙设备类
 */
-(void)connectBLE:(CBPeripheral*)peripheral;

/**
 断开连接
 */
-(void)disconnectBLE;

/**
 使用UUID，重连设备。
*/
-(void)connectPeripheralWithUUID:(NSString *)uuid;


- (void)connectedPeripheralWithUUID:(NSString*)uuid;

- (void)connetInUboot:(EAPeripheralModel *)peripheralModel;


/// HID设备重连
/// - Parameter uuid: 设备 identifyString
-(void)findHid:(NSString *)uuid;

#pragma mark - 杰理蓝牙库OTA流程相关业务

typedef void(^GET_DEVICE_CALLBACK)(BOOL needForcedUpgrade);

typedef void(^CANCEL_CALLBACK)(uint8_t status);

/**
 *  获取已连接的蓝牙设备信息
 */
- (void)getDeviceInfo:(GET_DEVICE_CALLBACK _Nonnull)callback;

/**
 *  OTA升级
 */
- (void)otaFuncWithFilePath:(NSString *)otaFilePath ;

- (void)otaFuncCancel:(CANCEL_CALLBACK _Nonnull)result;


- (void)setSelectedOtaFilePath:(NSString *)selectedOtaFilePath;
@end


NS_ASSUME_NONNULL_END
