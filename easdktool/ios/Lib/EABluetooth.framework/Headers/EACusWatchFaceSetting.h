//
//  EACusWatchFaceSetting.h
//  EABluetooth
//
//  Created by Aye on 2025/4/8.
//  File Name:75:His Watch Face Information【表盘信息】


#import <EABluetooth/EABluetooth.h>

NS_ASSUME_NONNULL_BEGIN



/**
 * id = 75
 * Customize the dial information
 * 自定义表盘信息
 */

@interface EACusWatchFaceSetting : EABaseModel

/// 操作:读取/设置/删除
@property(nonatomic,assign) EACusWatchFaceSetType eWfops;

/// 自定义表盘的ID,不需要设置，SDK自动生成
@property(nonatomic,strong) NSString *cusId;
/// 修改时需要填入就的表盘Id
@property(nonatomic,strong) NSString *cusId_old;


@property(nonatomic,strong) UIImage *bgImage;
@property(nonatomic,strong) UIImage *styleImage;

@property(nonatomic,strong) UIColor *photoColor;
@property(nonatomic,strong) UIColor *timeColor;

@property(nonatomic,assign) EACusWatchFaceSetTimeStyle eTimeStyle;
@property(nonatomic,assign) EACusWatchFaceSetTimePosition eTimePosition;


/// Circular top, square left top data【圆形top 、方形left top 数据】
@property(nonatomic,assign) EACusWatchFaceFunctionType top_Or_leftTop;
/// Circular right, square right top data【圆形right 、方形right top 数据】
@property(nonatomic,assign) EACusWatchFaceFunctionType right_Or_rightTop;
/// Circular left, square left bottom data【圆形left 、方形left bottom 数据】
@property(nonatomic,assign) EACusWatchFaceFunctionType left_Or_leftBottom;
/// Circular bottom, square right bottom data【圆形bottom 、方形right bottom 数据】
@property(nonatomic,assign) EACusWatchFaceFunctionType bottom_Or_rightBottom;

@property(nonatomic,strong) UIImage *image_top_Or_leftTop;
@property(nonatomic,strong) UIImage *image_right_Or_rightTop;
@property(nonatomic,strong) UIImage *image_left_Or_leftBottom;
@property(nonatomic,strong) UIImage *image_bottom_Or_rightBottom;

@property(nonatomic,assign) NSInteger padding_top_Or_leftTop;
@property(nonatomic,assign) NSInteger padding_right_Or_rightTop;
@property(nonatomic,assign) NSInteger padding_left_Or_leftBottom;
@property(nonatomic,assign) NSInteger padding_bottom_Or_rightBottom;


// 以下不需要设置
@property(nonatomic, assign) NSInteger photoColorR;
@property(nonatomic, assign) NSInteger photoColorG;
@property(nonatomic, assign) NSInteger photoColorB;
@property(nonatomic, assign) NSInteger photoColorA;

@property(nonatomic, assign) NSInteger timeColorR;
@property(nonatomic, assign) NSInteger timeColorG;
@property(nonatomic, assign) NSInteger timeColorB;
@property(nonatomic, assign) NSInteger timeColorA;

@property(nonatomic, strong) NSString *timeColorHexString;
@property(nonatomic, strong) NSString *photoColorHexString;



/// Get
+ (instancetype)eaGetCusWatchFaceSettingWithCusId:(NSString *)cusId;

/// Set
+ (instancetype)eaSetCusWatchFaceSettingWithCusId:(NSString *)cusId;

/// Delete
+ (instancetype)eaDelCusWatchFaceSettingWithCusId:(NSString *)cusId;




- (NSData *)getRequestModelData;
@end

NS_ASSUME_NONNULL_END
