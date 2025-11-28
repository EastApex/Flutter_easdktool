//
//  EAOTAModel.h
//  EABluetooth
//
//  Created by Aye on 2021/6/30.
//  File Name:OTA File【OTA文件】


#import <EABluetooth/EABaseModel.h>

NS_ASSUME_NONNULL_BEGIN




/**
 * EAFileModel is an OTA file model, which defines information such as OTA file type, file path or file data, and version number.
 * EAFileModel是OTA文件模型，定义了OTA文件类型、文件路径或者文件数据、版本号等信息。
 */
@interface EAFileModel : EABaseModel

/// Ota type【ota类型】
@property(nonatomic,assign) EAOtaRequestType otaType;

/// Bin Indicates the path where the package is stored(binPath or binData just pass one of these values.)【bin包所在路径（binPath 或者 binData 2传一即可）】
@property(nonatomic,strong) NSString *binPath;

/// The version number 【版本号】
@property(nonatomic,copy) NSString *version;

/// Data flow of the bin file(binPath or binData just pass one of these values.)【bin文件的数据流（binPath 或者 binData 2传一即可）】
@property(nonatomic,strong) NSData *binData;


/// set watch face id,max 32【设置表盘id，最长32】
@property(nonatomic,strong) NSString *watchFaceId;

/// His resource id【海思ResId】
@property(nonatomic,assign,readonly) NSInteger hisResId;


/// Music Name
@property(nonatomic,strong) NSString *musicName;


/// jieli watch face name
@property(nonatomic,readonly) NSString *jlWatchFaceName;



/// init with his resource id
/// - Parameter hisResId: his resource id
- (instancetype )initWithHisResId:(NSInteger)hisResId ;



/// init with path
/// - Parameters:
///   - binPath: bin path
///   - otaType: ota type
///   - version: version
+ (EAFileModel *)eaInitWithPath:(NSString *)binPath otaType:(EAOtaRequestType )otaType version:(NSString *)version;

/// init with data
/// - Parameters:
///   - binData: bin data
///   - otaType: ota type
///   - version: version
+ (EAFileModel *)eaInitWithData:(NSData *)binData otaType:(EAOtaRequestType )otaType version:(NSString *)version;

/// init watch face file
/// - Parameters:
///   - binPath: bin path
///   - version: version
///   - watchFaceId: watch face id,can nil
+ (EAFileModel *)eaInitWatchFaceFileWithPath:(NSString *)binPath version:(NSString *)version watchFaceId:(NSString *)watchFaceId;

/// init watch face data
/// - Parameters:
///   - binData: data
///   - version: version
///   - watchFaceId: watch face id,can nil
+ (EAFileModel *)eaInitWatchFaceFileWithData:(NSData *)binData version:(NSString *)version watchFaceId:(NSString *)watchFaceId;


/// init His watch face file
/// - Parameter binPath: path
+ (EAFileModel *)eaInitHisWatchFaceFileWithPath:(NSString *)binPath;

/// init His watch face file
/// - Parameter binData: data
+ (EAFileModel *)eaInitHisWatchFaceFileWithData:(NSData *)binData;


/// init His Cus watch face file
/// - Parameter binData: data
+ (EAFileModel *)eaInitHisCusWatchFaceFileWithData:(NSData *)binData;

/// init His Cus watch face file
/// - Parameter binPath: path
+ (EAFileModel *)eaInitHisCusWatchFaceFileWithPath:(NSString *)binPath;



/// init His Res
/// - Parameter zipPath: zip path
+ (EAFileModel *)eaInitHisResWithPath:(NSString *)zipPath;


/// init music
/// - Parameters:
///   - musicPath: music path
///   - musicName: music name
+ (EAFileModel *)eaInitMusicWithPath:(NSString *)musicPath musicName:(NSString *)musicName;


/// init jieli firmware file
/// - Parameter binPath: path
+ (EAFileModel *)eaInitJlFirmwareFileWithPath:(NSString *)binPath;

/// init jieli watch face file
/// - Parameter binPath: path
+ (EAFileModel *)eaInitJlWatchFaceFileWithPath:(NSString *)binPath;



/// Initialize the file infos【初始化文件类型】
+ (EAFileModel *)allocInitWithPath:(NSString *)binPath otaType:(EAOtaRequestType )otaType version:(NSString *)version DEPRECATED_MSG_ATTRIBUTE("Please use \"eaInitWithPath: otaType: version:\"");

/// NOT_ANALYSIS_FUNCTION
/// 检查是否可以OTA表盘
- (BOOL)checkCanOTAWatchFace;

@end



//  NOT_ANALYSIS_CLASS
@interface EAOTAModel : EABaseModel


/// OTA type
@property(nonatomic,assign) EAOtaRequestType otaType;

/// file path
@property(nonatomic,strong) NSString *binPath;

/// version
@property(nonatomic,copy) NSString *version;

/// file data
@property(nonatomic,strong) NSData *binData;

/// watch face id
@property(nonatomic,strong) NSString *watchFaceId;

 /// data size
@property(nonatomic,assign) NSInteger currentSize;

 /// crc
@property(nonatomic,assign) uint16_t crc;

 /// AGPS Data
@property(nonatomic,assign) NSData *agpsData;

 /// number
@property(nonatomic,assign) NSInteger number;

/// His Res Id
@property(nonatomic,assign) NSInteger hisResId;


- (NSData *)getOtaData;

- (uint16_t)getCrcValue:(BOOL)isRes;

- (uint16_t)getCrcValue:(BOOL)isRes andBinPath:(NSString *)binPath;

- (uint16_t)crc16WithJL707Data:(NSData *)data;


- (uint16_t )getHisWfCrcValueWithBinPath:(NSString *)binPath;

/// 初始化OTA更新
/// @param binPath binPath
/// @param otaType EAOtaRequestType
- (EAOTAModel *)initWithPath:(NSString *)binPath otaType:(EAOtaRequestType )otaType version:(NSString *)version;




@end

//  NOT_ANALYSIS_CLASS
@interface EAOTA : EABaseModel

 ///ota的所有数据包
@property(nonatomic,strong) NSMutableArray <EAOTAModel *> *otaModels;

 ///当前ota Bin 包大小 （单位 bytes）
@property(nonatomic,assign) float totalSize;

 ///是否弹出升级界面 1打开升级页面，0不打开
@property(nonatomic, assign) NSInteger popUpInterface;

 ///测试专用：强制升级
@property(nonatomic, assign) NSInteger isTestMode;

@end

NS_ASSUME_NONNULL_END
