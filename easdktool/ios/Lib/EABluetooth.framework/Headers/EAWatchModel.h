//
//  EAWatchModel.h
//  EABluetooth
//
//  Created by Aye on 2021/3/18.
//

#import <EABluetooth/EABaseModel.h>

NS_ASSUME_NONNULL_BEGIN



/// Watch the information
/// 手表信息
@interface EAWatchModel : EABaseModel

/// Watch SN no.
/// 手表SN号：
@property(nonatomic, copy) NSString *id_p;

/// Watch models
/// 手表型号
@property(nonatomic, copy) NSString *type;

/// firmware version
/// 固件版本：
@property(nonatomic, copy) NSString *firmwareVersion;

/// Whether the binding
/// 是否绑定
@property(nonatomic, assign) EABindingType bindingType;

/// userId
/// 用户id
@property(nonatomic, strong) NSString *userId;


/// The last update timestamp of the watch AGPS. 0 indicates that it has not been updated
/// 手表agps的最后更新时间戳，0为没更新过
@property(nonatomic, assign) NSInteger agpsUpdateTimestamp;

/// watch MAC Address
/// 手表MAC地址
@property(nonatomic, strong) NSString *bleMacAddr;

/// Whether to wait for the device to confirm the binding
/// 是否需要等待设备确认绑定
@property(nonatomic,assign) NSInteger isWaitForBinding;

/// 获取手表相关信息
/// @param data 数据流
+ (EAWatchModel *)getModelByData:(NSData *)data;




@end

NS_ASSUME_NONNULL_END
