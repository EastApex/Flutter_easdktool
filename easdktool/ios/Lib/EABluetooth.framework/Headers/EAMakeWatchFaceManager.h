//  NEED_CLASS_ANALYSIS
//  EAMakeWatchFaceManager.h
//  EABluetooth
//
//  Created by Aye on 2023/2/25.
//

#import <Foundation/Foundation.h>
#import <UIKit/UIKit.h>
#import <EABluetooth/EAEnum.h>
#import "EACustomNumberWatchFaceModel.h"

#import <EABluetooth/EAOTAManager.h>

NS_ASSUME_NONNULL_BEGIN

@class EACusWatchFaceSetting;


/**
 * EAMakeWatchFaceManager is a custom watch face operation class, responsible for functions such as obtaining custom watch face preview images, generating custom watch faces and synchronizing them to the watch.
 *
 * Currently, custom watch faces have their corresponding methods according to different chips.
 * 1.General chip type
 *      Currently, several custom watch face methods are defined.
 *  1.1 Digital watch face: Just pass in the watch - sized dial image and color to generate the watch face defined by the SDK.
 *  1.2 Pointer watch face: Just pass in the watch - sized dial image and pointer style to generate the watch face defined by the SDK.
 *  1.3 Pointer - scale watch face: Just pass in the watch - sized dial image, pointer style and scale style to generate the watch face defined by the SDK.
 *  1.4 Advanced custom digital watch face: Completely customize the digital style, which may take you a lot of time to debug. You need to pass in the dial image and an array of [EACustomNumberWatchFaceModel] to generate the watch face. It is best to process it according to your custom watch face UI parameters.
 *  1.5 Advanced custom pointer watch face: Completely customize the pointer style, which may take you a lot of time to debug. You need to pass in the dial image and an array of [EACustomPointerWatchFaceModel] to generate the watch face. It is best to process it according to your custom watch face UI parameters.
 *
 * 2.His chip type
 *  2.1 Set the watch face according to the properties of the EACusWatchFaceSetting object. Please refer to Class EACusWatchFaceSetting for details.
 *
 * 3.JieLi 707 chip type
 *  3.1 Set the watch face according to the properties of the EAJieLiCusWatchFaceSetTimeStyle object. Please refer to Class EAJieLiCusWatchFaceSetTimeStyle for details.
 *
 * EAMakeWatchFaceManager 是自定义表盘操作类，如获取自定义表盘预览图、生成自定义表盘并同步到手表等功能。
 *
 * 目前自定义表盘按照芯片的不同，都有其适应的自定义表盘方法。
 * 1.通用芯片类型
 *  目前定义了种自定义表盘方法
 *  1.1 数字表盘：只需要传入手表尺寸的表盘图片和颜色即可生成SDK定义好的表盘。
 *  1.2 指针表盘：只需要传入手表尺寸的表盘图片和指针样式即可生成SDK定义好的表盘。
 *  1.3 指针刻度表盘：只需要传入手表尺寸的表盘图片、指针样式、刻度样式即可生成SDK定义好的表盘。
 *  1.4 高级自定义数字表盘：完全自定义数字样式，这可能需要您花费大量时间来调试。需要传入表盘图片、[EACustomNumberWatchFaceModel]数组来生成表盘，最好是根据你们的自定义表盘UI参数来处理。
 *  1.5 高级自定义指针表盘：完全自定义指针样式，这可能需要您花费大量时间来调试。需要传入表盘图片、[EACustomPointerWatchFaceModel]数组来生成表盘，最好是根据你们的自定义表盘UI参数来处理。
 *
 * 2.His芯片类型
 *  2.1 根据EACusWatchFaceSetting对象的属性来设置表盘，具体请查看Class EACusWatchFaceSetting。
 *
 * 3.杰里707芯片类型
 *  3.1 根据EAJieLiCusWatchFaceSetTimeStyle对象的属性来设置表盘，具体请查看Class EAJieLiCusWatchFaceSetTimeStyle。
 *
 *
 */
@interface EAMakeWatchFaceManager : NSObject


#pragma mark - thumbnails【预览图】

/// Type 1.1： Create thumbnail of default style digital watch face【数字表盘预览图】
/// - Parameters:
///   - image: image【图片】
///   - color: color【颜色】
+ (UIImage *)eaGetDefaultNumberThumbnailWithImage:(UIImage *)image color:(UIColor *)color;

/// Type 1.2：Create thumbnail of pointer watch face【创建指针表盘预览图】
/// - Parameters:
///   - image: image 【图片】
///   - colorType: color type 【颜色类型】
+ (UIImage *)eaGetDefaultPointerThumbnailWithImage:(UIImage *)image colorType:(EACWFTimerColorType )colorType;

/// Type 1.3：Create thumbnail of pointer watch face【创建指针表盘预览图】
/// - Parameters:
///   - image: image 【图片】
///   - colorType: color type 【颜色类型】
///   - scaleStyle: scale style 【刻度】
+ (UIImage *)eaGetDefaultPointerThumbnailWithImage:(UIImage *)image colorType:(EACWFTimerColorType )colorType scaleStyle:(EACWFPointerScaleStyle)scaleStyle;


/// Type 1.4：Create a fully custom digital watch face thumbnail 【创建完全自定义数字表盘预览图】
/// - Parameters:
///   - image: image【图片】
///   - numberList: number style List【数字样式】
+ (UIImage *)eaGetNumberThumbnailWithImage:(UIImage *)image list:(NSArray <EACustomNumberWatchFaceModel *>*)numberList;

/// Type 1.5：Create a fully custom pointer watch face thumbnail 【创建完全自定义指针表盘预览图】
/// - Parameters:
///   - image: image【图片】
///   - pointerList: pointer style List【指针样式】
+ (UIImage *)eaGetPointerThumbnailWithImage:(UIImage *)image list:(NSArray <EACustomPointerWatchFaceModel *>*)pointerList ;



/// Type 2.1: Get the thumbnail of the custom watch face on the His platform【海思自定义表盘预览图】
/// - Parameter cusWatchFaceSetting: Custom Watch Face Setting 【自定义表盘样式】
+ (UIImage *)eaGetHisCusThumbnailWithCusWatchFaceSetting:(EACusWatchFaceSetting *)cusWatchFaceSetting;



/// Type 3.1: Get the thumbnail of the Jerry watch face【杰里自定义表盘预览图】
/// - Parameters:
///   - image: image 【图片】
///   - jlTimeStyle: jieli time style 【杰里时间样式】
+ (UIImage *)eaGetJieLiThumbnailWithImage:(UIImage *)image timeStyle:(EAJieLiCusWatchFaceSetTimeStyle )jlTimeStyle;





#pragma mark - watch face ota 【表盘OTA】

/// Type 1.1：Default style digital watch face【默认数字表盘】
/// - Parameters:
///   - image: image 【图片】
///   - color: color 【颜色】
///   - watchFaceId: watch face Id
///   - progress: progress
///   - complete: complete
+ (NSInteger )eaOtaDefaultNumberWatchFaceWithImage:(UIImage *)image color:(UIColor *)color watchFaceId:(NSString *)watchFaceId progress:(void (^)(CGFloat progress))progress complete:(OTACompleteBlock)complete;


/// Type 1.2：Default style digital watch face【默认指针表盘】
/// - Parameters:
///   - image: image 【图片】
///   - colorType: color type 【颜色类型】
///   - watchFaceId: watch face Id
///   - progress: progress
///   - complete: complete
+ (NSInteger )eaOtaDefaultPointerWatchFaceWithImage:(UIImage *)image colorType:(EACWFTimerColorType )colorType watchFaceId:(NSString *)watchFaceId progress:(void (^)(CGFloat progress))progress complete:(OTACompleteBlock)complete;

/// Type 1.3：Default style digital watch face【默认指针表盘】
/// - Parameters:
///   - image: image 【图片】
///   - colorType: color type 【颜色类型】
///   - scaleStyle: scale style 【刻度】
///   - watchFaceId: watch face Id
///   - progress: progress
///   - complete: complete
+ (NSInteger )eaOtaDefaultPointerWatchFaceWithImage:(UIImage *)image colorType:(EACWFTimerColorType )colorType scaleStyle:(EACWFPointerScaleStyle)scaleStyle watchFaceId:(NSString *)watchFaceId progress:(void (^)(CGFloat progress))progress complete:(OTACompleteBlock)complete;


/// Type 1.4：Advanced custom digital watch face【高级自定义数字表盘】
/// - Parameters:
///   - image: image 【图片】
///   - numberList:  number style List【数字样式】
///   - watchFaceId: watch face Id
///   - progress: progress
///   - complete: complete
+ (NSInteger )eaOtaNumberWatchFaceWithImage:(UIImage *)image list:(NSArray <EACustomNumberWatchFaceModel *>*)numberList watchFaceId:(NSString *)watchFaceId progress:(void (^)(CGFloat progress))progress complete:(OTACompleteBlock)complete;



/// Type 1.5：Advanced custom pointer watch face【高级自定义指针表盘】
/// - Parameters:
///   - image: image 【图片】
///   - pointerList: pointer style List【指针样式】
///   - colorType: color type 【颜色类型】
///   - watchFaceId: watch face Id
///   - progress: progress
///   - complete: complete
+ (NSInteger )eaOtaPointerWatchFaceWithImage:(UIImage *)image list:(NSArray <EACustomPointerWatchFaceModel *>*)pointerList colorType:(EACWFTimerColorType )colorType watchFaceId:(NSString *)watchFaceId progress:(void (^)(CGFloat progress))progress complete:(OTACompleteBlock)complete;


/// Type 1.5：Advanced custom pointer watch face【高级自定义指针表盘】
/// - Parameters:
///   - image: image 【图片】
///   - pointerList: pointer style List【指针样式】
///   - colorType: color type 【颜色类型】
///   - scaleStyle: scale style 【刻度】
///   - watchFaceId: watch face Id
///   - progress: progress
///   - complete: complete
+ (NSInteger )eaOtaPointerWatchFaceWithImage:(UIImage *)image list:(NSArray <EACustomPointerWatchFaceModel *>*)pointerList colorType:(EACWFTimerColorType )colorType scaleStyle:(EACWFPointerScaleStyle)scaleStyle watchFaceId:(NSString *)watchFaceId progress:(void (^)(CGFloat progress))progress complete:(OTACompleteBlock)complete;




/// Type 2.1:海思自定义表盘
/// - Parameters:
///   - cusWatchFaceSetting: Custom Watch Face Setting 【自定义表盘样式】
///   - cusIdBlock: Custom Infomation
///   - progress: progress
///   - complete: complete
+ (NSInteger )eaOtaHisCusWatchFaceWithCusWatchFaceSetting:(EACusWatchFaceSetting *)cusWatchFaceSetting cusInfos:(void (^)(NSString * cusId,UIImage *thumbnail,UIImage *bgImage))cusIdBlock progress:(void (^)(CGFloat progress))progress complete:(OTACompleteBlock)complete;



/// Type 3.1: OTA the JieLi watch face 【杰里海思自定义表盘】
/// - Parameters:
///   - image: image 【图片】
///   - jlTimeStyle: jieli time style 【杰里时间样式】
///   - cusIdBlock: Custom Infomation
///   - progress: progress
///   - complete: complete
+ (NSInteger )eaOTAJieLiWatchFaceWithImage:(UIImage *)image timeStyle:(EAJieLiCusWatchFaceSetTimeStyle )jlTimeStyle cusInfos:(void (^)(NSString * cusId,UIImage *thumbnail,UIImage *bgImage))cusIdBlock progress:(void (^)(CGFloat p))progress complete:(OTACompleteBlock)complete;































/// Type2：Default style digital watch face【默认指针表盘】
+ (NSInteger )eaOtaDefaultPointerWatchFaceWithImage:(UIImage *)image colorType:(EACWFTimerColorType )colorType DEPRECATED_MSG_ATTRIBUTE("Please use \"aOtaDefaultPointerWatchFaceWithImage: colorType: watchFaceId: progress: complete:\"");

/// Type3：Default style digital watch face【默认数字表盘】
+ (NSInteger )eaOtaDefaultNumberWatchFaceWithImage:(UIImage *)image color:(UIColor *)color DEPRECATED_MSG_ATTRIBUTE("Please use \"eaOtaDefaultNumberWatchFaceWithImage: color: watchFaceId: progress: complete:\"") ;

/// Type4：Advanced custom digital watch face【高级自定义数字表盘】
+ (NSInteger )eaOtaNumberWatchFaceWithImage:(UIImage *)image list:(NSArray <EACustomNumberWatchFaceModel *>*)numberList DEPRECATED_MSG_ATTRIBUTE("Please use \"eaOtaNumberWatchFaceWithImage: list: watchFaceId: progress: complete:\"");

/// Type5：Advanced custom pointer watch face【高级自定义指针表盘】
+ (NSInteger )eaOtaPointerWatchFaceWithImage:(UIImage *)image list:(NSArray <EACustomPointerWatchFaceModel *>*)pointerList colorType:(EACWFTimerColorType )colorType DEPRECATED_MSG_ATTRIBUTE("Please use \"eaOtaPointerWatchFaceWithImage: list: colorType: watchFaceId: progress: complete:\"");

 
+ (NSInteger )eaOtaDefaultPointerWatchFaceWithImage:(UIImage *)image colorType:(EACWFTimerColorType )colorType watchFaceId:(NSString *)watchFaceId DEPRECATED_MSG_ATTRIBUTE("Please use \"aOtaDefaultPointerWatchFaceWithImage: colorType: watchFaceId: progress: complete:\"");

+ (NSInteger )eaOtaDefaultNumberWatchFaceWithImage:(UIImage *)image color:(UIColor *)color watchFaceId:(NSString *)watchFaceId DEPRECATED_MSG_ATTRIBUTE("Please use \"eaOtaDefaultNumberWatchFaceWithImage: color: watchFaceId: progress: complete:\"");

+ (NSInteger )eaOtaNumberWatchFaceWithImage:(UIImage *)image list:(NSArray <EACustomNumberWatchFaceModel *>*)numberList watchFaceId:(NSString *)watchFaceId DEPRECATED_MSG_ATTRIBUTE("Please use \"eaOtaNumberWatchFaceWithImage: list: watchFaceId: progress: complete:\"");

+ (NSInteger )eaOtaPointerWatchFaceWithImage:(UIImage *)image list:(NSArray <EACustomPointerWatchFaceModel *>*)pointerList colorType:(EACWFTimerColorType )colorType watchFaceId:(NSString *)watchFaceId DEPRECATED_MSG_ATTRIBUTE("Please use \"eaOtaPointerWatchFaceWithImage: list: colorType: watchFaceId: progress: complete:\"");


+ (UIImage *)eaGetPointerThumbnailWithImage:(UIImage *)image colorType:(EACWFTimerColorType )colorType scaleStyle :(EACWFPointerScaleStyle)scaleStyle DEPRECATED_MSG_ATTRIBUTE("Please use \"eaGetDefaultPointerThumbnailWithImage: colorType:\"");

/// Type1：Create thumbnails of digital watch face【创建数字表盘预览图】
/// @return Thumbnail
+ (UIImage *)eaGetNumberThumbnailWithImage:(UIImage *)image colorType:(EACWFTimerColorType )colorType DEPRECATED_MSG_ATTRIBUTE("Please use \"eaGetDefaultNumberThumbnailWithImage: color:\"");

/// Type1：Digital watch face with custom picture【自定义图片的数字表盘】
+ (NSInteger )eaOtaNumberWatchFaceWithImage:(UIImage *)image colorType:(EACWFTimerColorType )colorType DEPRECATED_MSG_ATTRIBUTE("Please use \"eaOtaDefaultNumberWatchFaceWithImage: color:\"");

/// Type2：Pointer watch face with custom  picture【自定义图片的指针表盘】
+ (NSInteger )eaOtaPointerWatchFaceWithImage:(UIImage *)image colorType:(EACWFTimerColorType )colorType scaleStyle :(EACWFPointerScaleStyle)scaleStyle DEPRECATED_MSG_ATTRIBUTE("Please use \"eaOtaDefaultPointerWatchFaceWithImage: colorType:\"");

/// Type2：Pointer watch face with custom  picture【自定义图片的指针表盘】
+ (NSInteger )eaOtaPointerWatchFaceWithImage:(UIImage *)image colorType:(EACWFTimerColorType )colorType scaleStyle :(EACWFPointerScaleStyle)scaleStyle watchFaceId:(NSString *)watchFaceId DEPRECATED_MSG_ATTRIBUTE("Please use \"eaOtaDefaultPointerWatchFaceWithImage: colorType: watchFaceId:\"");


+ (UIImage *)testLoadSvg DEPRECATED_MSG_ATTRIBUTE();


@end

NS_ASSUME_NONNULL_END
