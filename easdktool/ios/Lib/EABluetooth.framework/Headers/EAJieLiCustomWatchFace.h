//
//  EAJieLiCustomWatchFace.h
//  EABluetooth
//
//  Created by Aye on 2025/11/3.
//

#import <EABluetooth/EABluetooth.h>

NS_ASSUME_NONNULL_BEGIN
/**
 * id = 82
 * JL707平台自定义表盘信息
 * Customize the dial information for the JL707 platform
*/
@interface EAJieLiCustomWatchFace : EABaseModel

/// Configuration: Time style【配置:时间样式】
@property(nonatomic, assign) EAJieLiCusWatchFaceSetTimeStyle eCusTimeStyle;

/// Configuration: Pointer style【配置:指针样式】
@property(nonatomic, readwrite) EAJieLiCusWatchFaceSetPointerStyle eCusPointStyle;

/// Style Select【样式选择】
@property(nonatomic, readwrite) EAJieLiCusWatchFaceSelectStyle eCusStyleSelect;

/// Operation【操作】
@property(nonatomic, assign) EAJieLiCusWatchFaceOps eCusops;

///
@property(nonatomic, assign) NSInteger timeStyleA;

///
@property(nonatomic, assign) NSInteger timeStyleR;

///
@property(nonatomic, assign) NSInteger timeStyleG;

///
@property(nonatomic, assign) NSInteger timeStyleB;

///
@property(nonatomic,readonly) UIColor *timeStyleColor;



#pragma mark  init number style
+ (instancetype)eaInitCusTimeStyle:(EAJieLiCusWatchFaceSetTimeStyle)eCusTimeStyle color:(UIColor *)color;

#pragma mark  init pointer style
+ (instancetype)eaInitCusPointerStyle:(EAJieLiCusWatchFaceSetPointerStyle)eCusPointerStyle;





#pragma mark
+ (instancetype)eaInitOtaCusTimeStyle:(EAJieLiCusWatchFaceSetTimeStyle)eCusTimeStyle DEPRECATED_MSG_ATTRIBUTE("");
+ (instancetype)eaInitSetCusTimeStyle:(EAJieLiCusWatchFaceSetTimeStyle)eCusTimeStyle DEPRECATED_MSG_ATTRIBUTE("");


@end
NS_ASSUME_NONNULL_END
