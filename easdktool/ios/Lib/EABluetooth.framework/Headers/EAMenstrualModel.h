//
//  EAMenstrualModel.h
//  EABluetooth
//
//  Created by Aye on 2021/6/21.
//

#import <EABluetooth/EABluetooth.h>

NS_ASSUME_NONNULL_BEGIN

@interface EAMenstrualModel : EABaseModel

/** 经期类型描述 */
@property(nonatomic, assign) EAMenstruationType eType;

/** 时间戳 */
@property(nonatomic, assign) NSInteger timeStamp;

/** 提示的天数：（如果e_type是安全期，days>0，则表示下一个经期的天数。如果是经期，days>0，则代表经期第几天) */
@property(nonatomic, assign) NSInteger days;

@end


@interface EAMenstruals : EABaseModel

/** 最多支持45个 */
@property(nonatomic, strong) NSMutableArray<EAMenstrualModel*> *sDateArray;


+ (EAMenstruals *)allocInitWithStartDate:(NSString *)start keepDay:(NSInteger)keepDay cycleDay:(NSInteger)cycleDay;


@end



NS_ASSUME_NONNULL_END
