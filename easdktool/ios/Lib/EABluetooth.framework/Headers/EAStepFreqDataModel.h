//
//  EAStepFreqDataModel.h
//  EABluetooth
//
//  Created by Aye on 2021/3/31.
//  File Name:3008:Step Frequency【步频】

#import <EABluetooth/EABaseBigDataModel.h>

NS_ASSUME_NONNULL_BEGIN

/**
 * Step frequency data model
 * 步频数据模型
*/
@interface EAStepFreqDataModel : EABigDataModel


/// stride frequency【步频值】
@property(nonatomic, assign) NSInteger stepFreqValue;

@end

//  NOT_ANALYSIS_CLASS
@interface EAStepFreqData : EABaseBigDataModel

@property(nonatomic, strong) NSMutableArray<EAStepFreqDataModel*> *sIndexArray;

/// NOT_ANALYSIS_FUNCTION
+ (EAStepFreqData *)getStepFreqData:(NSData *)data;


@end
NS_ASSUME_NONNULL_END
