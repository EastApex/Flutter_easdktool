//
//  EAStressDataModel.h
//  EABluetooth
//
//  Created by Aye on 2021/3/31.
//

#import <EABluetooth/EABaseBigDataModel.h>

NS_ASSUME_NONNULL_BEGIN


/// 压力数据
@interface EAStressDataModel : EABaseModel

/** 时间戳 */
@property(nonatomic, assign) NSInteger timeStamp;

/** 压力 */
@property(nonatomic, assign) NSInteger stessValue;

/** 压力类型 */
@property(nonatomic, assign) EAStressDataType eType;

@end

@interface EAStressData : EABaseBigDataModel


/** 压力包（大小详见对应OPTIONS文件） */
@property(nonatomic, strong) NSMutableArray<EAStressDataModel*> *sIndexArray;

+ (EAStressData *)getStressData:(NSData *)data;


@end

NS_ASSUME_NONNULL_END
