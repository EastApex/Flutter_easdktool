//
//  EAWatchModel.h
//  EABluetooth
//
//  Created by Aye on 2021/3/18.
//  File Name:3:Watch Information【手表信息】

#import <EABluetooth/EABaseModel.h>
#import <EABluetooth/EARespondModel.h>
#import <EABluetooth/EARequestModel.h>
NS_ASSUME_NONNULL_BEGIN


// EA040002000803EF


/**
 * id = 3
 * Watch the information
 * 手表信息
*/
@interface EAWatchModel : EABaseModel

/// Watch SN no.【手表SN号】
@property(nonatomic, copy) NSString *id_p;

/// Watch models【手表型号】
@property(nonatomic, copy) NSString *type;

/// firmware version【固件版本】
@property(nonatomic, copy) NSString *firmwareVersion;

/// firmware version【固件版本】
@property(nonatomic,  copy) NSString *customFirmwareVersion;

/// Whether the binding【是否绑定】
@property(nonatomic, assign) EABindingType bindingType;

/// userId【 用户id】
@property(nonatomic, strong) NSString *userId;

/// The last update timestamp of the watch AGPS. 0 indicates that it has not been updated【手表agps的最后更新时间戳，0为没更新过】
@property(nonatomic, assign) NSInteger agpsUpdateTimestamp;

/// watch MAC Address【手表MAC地址】
@property(nonatomic, strong) NSString *bleMacAddr;

/// Whether to wait for the device to confirm the binding【是否需要等待设备确认绑定】
@property(nonatomic,assign) NSInteger isWaitForBinding;

/// Watch function list: 0 Not supported 1: supported【手表功能列表: 0 不支持 1:支持】
/// ** see class EAWatchSupportModel
@property(nonatomic, assign) BOOL projSettings;

/// Watch size - width【手表尺寸-宽度】
@property(nonatomic, assign) NSInteger width;

/// Watch size - height【手表尺寸-高度】
@property(nonatomic, assign) NSInteger height;

/// Watch thumbnail size - width【手表缩略图尺寸-宽度】
@property(nonatomic, assign) NSInteger thumbnailWidth;

/// Watch thumbnail size - height【手表缩略图尺寸-高度】
@property(nonatomic, assign) NSInteger thumbnailHeight;

/// Watch thumbnail - Rounded corners【手表缩略图-圆角】
@property(nonatomic, assign) NSInteger thumbnailRadius;

/// Screen type: 0: square screen 1: round screen【手表屏幕类型: 0:方屏 1:圆屏】
@property(nonatomic, assign) EAScreenType screenType;

/// Binding a watch sn is not supported: 0: The watch SN is supported. 1: The watch SN is not supported【不支持sn号绑定手表: 0:支持 1:不支持】
@property(nonatomic, assign) NSInteger notSupportSn;

/// Maximum supported dial memory size (storage space, unit KB)【最大支持表盘内存大小（存储空间,单位KB）】
@property(nonatomic, assign) NSInteger maxWatchSize;

/// LCD pixel types: 0: RGB565 1:GXGPU_RGB888 2:ARGB8565(Jali) 3: Hisilicon Platform 4:JL707 Single Backup 5:JL707 Dual Backup (CRC16-CCITT)【LCD像素类型: 0: RGB565 1:GXGPU_RGB888 2:ARGB8565(杰理)  3:海思平台 4:JL707单备份 5:JL707双备份（CRC16-CCITT）】
@property(nonatomic, assign) NSInteger lcdPixelType;


/// Number of alarms supported: 0: 10 alarms are supported by default. n: n alarms are supported【支持闹钟数量：0:默认支持10个  n:支持n个】
@property(nonatomic, assign) NSInteger numOfAlarmSupported;

/// Number of supported schedules: 0: 10 by default n: n supported【支持日程数量：0:默认支持10个  n:支持n个】
@property(nonatomic, assign) NSInteger numOfScheduleSupported;

/// Number of supported built-in dials【内置表盘支持个数】
@property(nonatomic, assign) NSInteger inDialNum;

/// Number of supported external dials【外置表盘支持个数】
@property(nonatomic, assign) NSInteger exDialNum;



/// fw verion
@property(nonatomic,readonly) NSString *apbVerion;

/// res verion
@property(nonatomic,readonly) NSString *resVerion;

/// number version  -> x.x.x
@property(nonatomic,readonly) NSString *numberVersion;

@end

NS_ASSUME_NONNULL_END
