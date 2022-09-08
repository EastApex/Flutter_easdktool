#import <Flutter/Flutter.h>

/* FIXME: - 强弱引用 */
#define WeakObj(obj) __weak typeof(obj) obj##Weak = obj
#define StrongObj(obj) __strong typeof(obj) obj ## Strong = obj##Weak
#define WeakSelf WeakObj(self);




/// WatchMassageType
typedef NS_ENUM(NSUInteger, WatchMassageType) {
    
    
    WatchMassageTypeSearchPhone = 0, //寻找手机
    WatchMassageTypeStopSearchPhone = 1, //停止寻找手机(固件需求)
    WatchMassageTypeConnectTheCamera = 2,//连接相机
    WatchMassageTypeStartTakingPictures = 3,//开启拍照
    WatchMassageTypeStopTakingPictures = 4,//停止拍照
    WatchMassageTypeRequestTheLatestWeather = 5,//请求最新天气信息
    WatchMassageTypeRequestTheAgps = 6,//请求最新agps星历数据
    WatchMassageTypeRequestTheMenstrualCycle = 7,//请求最新经期数据
    WatchMassageTypeBig8803DataUpdateFinish = 8,//大数据传输完成
    
    
    /// Connect status
    WatchMassageTypeConnectFailed = 100,  // 连接手表失败
    WatchMassageTypeConnectSucc = 101,    // 连接手表成功
    
    ///
    WatchMassageTypeLoseConnect = 200,    // 手表断连
    
    /// Binding status
    WatchMassageTypeBindingFailed = 300,  // 手表绑定失败
    WatchMassageTypeBindingSucc = 301,    // 手表绑定成功
    
    
    
};

@interface EasdktoolPlugin : NSObject<FlutterPlugin>
+ (void)registerWithRegistrar:(nonnull NSObject<FlutterPluginRegistrar> *)registrar;

@end
