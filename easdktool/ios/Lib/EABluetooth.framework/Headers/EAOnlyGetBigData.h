//  NOT_ANALYSIS_CLASS
//  EAOnlyGetBigData.h
//  EABluetooth
//
//  Created by Aye on 2022/12/14.
//

#import <EABluetooth/EABluetooth.h>

NS_ASSUME_NONNULL_BEGIN

/// id = 49 ：
/// id = 49:  

/**
 * id = 49
 * 单独获取大数据命令 （发送完成发送命令 8802发送id = 2001：8803大数据传输完成）
 * Obtain big data commands separately (send complete Send command 8802 Send id = 2001:8803 Big data transmission complete)
 */
@interface EAOnlyGetBigData : EABaseModel

/// Big data type【大数据类型】
@property(nonatomic, assign) EADataInfoType eBigDataType;


/// Initialization
/// - Parameter bigDataType: bigDataType
+ (instancetype )eaInitWithBigDataType:(EADataInfoType)bigDataType;

@end

NS_ASSUME_NONNULL_END
