#import "EasdktoolPlugin.h"
#import "BluetoothFunc.h"




#define kTimerSec 10
#define kPlugInLog(format,...)  (self.config.debug ?  NSLog(@"SDK[😁]:- " format, ##__VA_ARGS__) : @"")


@interface EasdktoolPlugin()<EABleManagerDelegate>
@property(nonatomic,assign) NSInteger timerSec;// 定时器时间

/* 创建一个内置定时器（系统）*/
@property(nonatomic,strong) dispatch_source_t timer;

/** <#name#> */
@property(nonatomic,strong) FlutterMethodChannel* channel ;

@property(nonatomic,strong) EABleConfig *config;

@property(nonatomic,strong) EAFileModel *jlFirmwareFileModel;

@end

#define kDocumentsPath      [NSHomeDirectory() stringByAppendingPathComponent:@"Documents"]
#define kBinFilePath        [kDocumentsPath stringByAppendingPathComponent:@"/agps.bin"]


/// MARK: - call method Name
#define kEAConnectWatch             @"EAConnectWatch"       // 连接
#define kEADisConnectWatch          @"EADisConnectWatch"    // 断开
#define kEAUnbindWatch              @"EAUnbindWatch"        // 解绑
#define kEAbindingWatch             @"EAbindingWatch"       // 绑定
#define kEAGetWatchInfo             @"EAGetWatchInfo"       // 获取手表数据
#define kEASetWatchInfo             @"EASetWatchInfo"       // 设置手表信息
#define kEAGetBigWatchData          @"EAGetBigWatchData"    // 获取手表大数据
#define kEAOperationWatch           @"EAOperationWatch"     // 操作手表
#define kEAOTA                      @"EAOTA"                // ota
#define kEACustomWatchface          @"EACustomWatchface"    // 自定义表盘
#define kEAOTACustomWatchface       @"EAOTACustomWatchface" // 自定义表盘
#define kEALog                      @"EAShowLog"            // log
#define kEASyncPhoneInfoToWacth     @"EASyncPhoneInfoToWacth"// 设置手表信息
#define kEAScanWacth                @"EAScanWacth"          // 搜索手表
#define kEAStopScanWacth            @"EAStopScanWacth"          //停止搜索手表
#define kEAGetWacthStateInfo        @"EAGetWacthStateInfo"  //获取手表连接状态信息
#define kEATest                     @"EATest"               // 测试状态
#define kEAAGPS                     @"EAAGPS"               // AGPS

// 杰里表盘相关
#define kAddJieLiWatchFace          @"AddJieLiWatchFace"
#define kDeleteJieLiWatchFace       @"DeleteJieLiWatchFace"
#define kGetJieLiWatchFace          @"GetJieLiWatchFace"
#define kJieLiCusWatchFace          @"JieLiCusWatchFace"



/// MARK: - invoke method Name
#define kArgumentsError             @"ArgumentsError"
#define kConnectState               @"ConnectState"
#define kBluetoothState             @"BluetoothState"
#define kBingdingWatchResponse      @"BingdingWatchResponse"
#define kSetWatchResponse           @"SetWatchResponse"
#define kGetWatchResponse           @"GetWatchResponse"
#define kGetBigWatchData            @"GetBigWatchData"
#define kOperationPhone             @"OperationPhone"
#define kProgress                   @"Progress"
#define kScanWacthResponse          @"ScanWacthResponse"
#define kOperationWacthResponse     @"OperationWacthResponse"
#define kCustomWatchFaceResponse    @"CustomWatchFaceResponse"
#define kJieLiNeedForcedOTA         @"JieLiNeedForcedOTA"


/// MARK: - 枚举
//绑定状态 0:连接失败 1:连接成功 2:断开连接 3:连接超时 4:无此设备 5:iOS需要移除配对
typedef NS_ENUM(NSUInteger, ConnectState) {
    
    ConnectState_fail,
    ConnectState_succ,
    ConnectState_disConnect,
    ConnectState_timeout,
    ConnectState_notFind,
    ConnectState_iOSRelievePair,
};

//蓝牙状态 0:未开启蓝牙 1:蓝牙开启 2:蓝牙未授权 3:定位未开启 4:不支持BLE
typedef NS_ENUM(NSUInteger, BluetoothState) {
    
    BluetoothState_unOpen,
    BluetoothState_open,
    BluetoothState_unAuthorized,
    BluetoothState_unOpenLocation,
    BluetoothState_unSupportBle,
};

//蓝牙消息回应 0:失败 1:成功 2:未知
typedef NS_ENUM(NSUInteger, BluetoothResponse) {
    
    BluetoothResponse_fail,
    BluetoothResponse_succ,
    BluetoothResponse_unknown,
};

// FIXME: -
@implementation EasdktoolPlugin

+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
    EasdktoolPlugin* instance = [[EasdktoolPlugin alloc] init];
    instance.channel = [FlutterMethodChannel
                        methodChannelWithName:@"easdktool"
                        binaryMessenger:[registrar messenger]];
    
    [instance setBleConfig];
    [registrar addMethodCallDelegate:instance channel:instance.channel];
}

- (void)setBleConfig {
    
    _config = [EABleConfig getDefaultConfig];
    _config.debug = YES;
    _config.canScanAllDevices = YES;
    _config.onlyShowEaWatch = NO;
    [[EABleManager defaultManager] setBleConfig:_config];
    
    [self addNotification];
}

#pragma mark - Notification
- (void)addNotification {
    
    [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(connectSucc) name:kNTF_EAConnectStatusSucceed object:nil];
    [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(connectFailed) name:kNTF_EAConnectStatusFailed object:nil];
    [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(loseConnect) name:kNTF_EAConnectStatusDisconnect object:nil];
    
    [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(blePoweredOn) name:kNTF_EABlePoweredOn object:nil];
    [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(blePoweredOff) name:kNTF_EABlePoweredOff object:nil];
    
    [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(getDeviceOpsPhoneMessage:) name:kNTF_EAGetDeviceOpsPhoneMessage object:nil];
    
    [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(showProgress:) name:kNTF_EAOTAAGPSDataing object:nil];
    
    [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(jlNeedForcedOTA) name:kNTF_EAJLNeedForcedOTA object:nil];

    
}

- (void)jlNeedForcedOTA  {
    
    [NSObject cancelPreviousPerformRequestsWithTarget:self selector:@selector(connectTimeOut) object:nil];

    if (_jlFirmwareFileModel) {
        
        [[EAOTAManager defaultManager] eaUpgradeJLFile:_jlFirmwareFileModel progress:^(CGFloat progress) {
            
            [[NSNotificationCenter defaultCenter] postNotificationName:kNTF_EAOTAAGPSDataing object:[NSNumber numberWithFloat:progress] userInfo:nil];
        } complete:^(BOOL succ, NSError * _Nullable error) {
            
            if (succ) {
                
                [[NSNotificationCenter defaultCenter] postNotificationName:kNTF_EAOTAAGPSDataing object:[NSNumber numberWithFloat:1] userInfo:nil];
            }
            else
            {
                [[NSNotificationCenter defaultCenter] postNotificationName:kNTF_EAOTAAGPSDataing object:[NSNumber numberWithFloat:error.code] userInfo:nil];
            }
        }];
    }
    else
    {
        [_channel invokeMethod:kJieLiNeedForcedOTA arguments:@""];
    }
    
}


- (void)connectSucc {
    
    [NSObject cancelPreviousPerformRequestsWithTarget:self selector:@selector(connectTimeOut) object:nil];
    [_channel invokeMethod:kConnectState arguments:@(ConnectState_succ)];
    
}

- (void)connectFailed {
    
    [NSObject cancelPreviousPerformRequestsWithTarget:self selector:@selector(connectTimeOut) object:nil];
    [_channel invokeMethod:kConnectState arguments:@(ConnectState_fail)];
}

- (void)loseConnect {
    
    [NSObject cancelPreviousPerformRequestsWithTarget:self selector:@selector(connectTimeOut) object:nil];
    [_channel invokeMethod:kConnectState arguments:@(ConnectState_disConnect)];
}

- (void)blePoweredOn {
    
    if ([[EABleManager defaultManager] getPeripheralModel]) {
        
        [[EABleManager defaultManager] reConnectToPeripheral];
    }
}

- (void)blePoweredOff {
    
    [_channel invokeMethod:kBluetoothState arguments:@(BluetoothState_unOpen)];
    
}

- (void)getDeviceOpsPhoneMessage:(NSNotification *)noti  {
    
    EAPhoneOpsModel *phoneOpsModel = (EAPhoneOpsModel *)noti.object;
    NSDictionary *info = @{
        @"opePhoneType":@(phoneOpsModel.eOps)
    };
    [_channel invokeMethod:kOperationPhone arguments:[info modelToJSONString]];
    
    if(phoneOpsModel.eOps == EAPhoneOpsBig8803DataUpdateFinish) {
        
        for (int i = 1; i < 14; i ++) {
            
            NSInteger dataType = 3000 + i;
            //            NSArray *list = [[EABleSendManager defaultManager] getBigDataWithBigDataType:(dataType)];
            NSArray *list = [[EABleBigDataManager defaultManager] eaGetBigDataWithBigDataType:dataType];
            if (list.count > 0) {
                
                NSDictionary *info = @{
                    @"dataType":@(dataType),
                    @"flag":@(2),
                    @"value":[list modelToJSONObject],
                };
                [_channel invokeMethod:kGetBigWatchData arguments:[info modelToJSONString]];
            }
        }
    }
}



- (void)showProgress:(NSNotification *)no {
    
    kPlugInLog(@"~~~~~~ showProgress:%@",no.object);
    if ([no.object floatValue] < 0) {
        
        NSDictionary *info = @{
            @"progress":@(-1)
        };
        [_channel invokeMethod:kProgress arguments:[info modelToJSONString]];
        return;
    }
    
   NSInteger progress = [[NSString stringWithFormat:@"%0.2f",[no.object floatValue] * 100] integerValue];
    if (progress >= 100) {
        
        NSDictionary *info = @{
            @"progress":@(progress),
            @"isSuccess":@(1),
        };
        [_channel invokeMethod:kProgress arguments:[info modelToJSONString]];
    }
    else
    {
        NSDictionary *info = @{
            @"progress":@(progress),
            @"isSuccess":@(0),
        };
        [_channel invokeMethod:kProgress arguments:[info modelToJSONString]];
    }
}




- (void)handleMethodCall:(FlutterMethodCall*)call result:(FlutterResult)result {
    kPlugInLog(@"call method:%@ , arguments = %@",call.method,call.arguments);
    if ([call.method isEqualToString:kEALog]) { // FIXME: - log
        
        NSDictionary *arguments = [self dictionaryWithJsonString:call.arguments] ;
        if ([self checkArgumentName:@"showLog" inArguments:arguments]) {
            
            _config.debug = [arguments[@"showLog"] boolValue];
            [[EABleManager defaultManager] setBleConfig:_config];
        }
    }
    if ([call.method isEqualToString:kEATest]) { // FIXME: - 测试
        
        NSDictionary *arguments = [self dictionaryWithJsonString:call.arguments] ;
        if ([self checkArgumentName:@"test" inArguments:arguments]) {
            
            _config.isTest = [arguments[@"test"] integerValue];
            [[EABleManager defaultManager] setBleConfig:_config];
        }
    }
    else if ([call.method isEqualToString:kEAGetWacthStateInfo]) { // FIXME: - 获取手表连接状态信息
        
        EAConnectStateType connectStateType = [EABleManager defaultManager].connectState;
        NSDictionary *info = @{
            @"connectState":@(connectStateType),
        };
        
        result([info modelToJSONString]);
    }
    else if ([call.method isEqualToString:kEAScanWacth]) { // FIXME: - 搜索
        
        //        NSDictionary *arguments = [self dictionaryWithJsonString:call.arguments] ;
        //        if ([self checkArgumentName:@"scanAll" inArguments:arguments]) {
        //
        //            _config.canScanAllDevices = [arguments[@"scanAll"] boolValue];
        //            [[EABleManager defaultManager] setBleConfig:_config];
        //        }
        
        [EABleManager defaultManager].delegate = self;
        [[EABleManager defaultManager] scanPeripherals];
        
    }
    else if ([call.method isEqualToString:kEAStopScanWacth]) { // FIXME: - 停止
        
        [EABleManager defaultManager].delegate = nil;
        [[EABleManager defaultManager] stopScanPeripherals];
        
    }
    else if ([call.method isEqualToString:kEAConnectWatch]) {  // FIXME: - 连接
        
        NSDictionary *arguments = [self dictionaryWithJsonString:call.arguments] ;
        if ([self checkArgumentName:@"snNumber" inArguments:arguments]) {
            
            NSString *snNumber = arguments[@"snNumber"];
            if (snNumber.length == 0 || [snNumber isEqualToString:@"null"]) {
                
                [_channel invokeMethod:kArgumentsError arguments:@"snNumber error"];
                return;
            }
            [[EABleManager defaultManager] reConnectToPeripheral:snNumber];
            
            [self performSelector:@selector(connectTimeOut) withObject:nil afterDelay:20];
        }
        
        
        
    }
    else if ([call.method isEqualToString:kEADisConnectWatch]) { // FIXME: - 断开
        
        [[EABleManager defaultManager] disconnectPeripheral];
        
    }
    else if ([call.method isEqualToString:kEAUnbindWatch]) { // FIXME: - 解绑
        
        EADeviceOps *deviceOps = [[EADeviceOps alloc] init];
        deviceOps.deviceOpsType = EADeviceOpsTypeRestoreFactory;
        deviceOps.deviceOpsStatus = EADeviceOpsStatusExecute;
        [[EABleSendManager defaultManager] operationChangeModel:deviceOps respond:^(EARespondModel * _Nonnull respondModel) {
            
            if (respondModel.eErrorCode == EARespondCodeTypeSuccess) {
                
                [[EABleManager defaultManager] disconnectAndNotReConnectPeripheral];
            }
        }];
    }
    else if ([call.method isEqualToString:kEAbindingWatch]) { // FIXME: - 绑定手表
        
        NSDictionary *arguments = [self dictionaryWithJsonString:call.arguments] ;
        if ([self checkArgumentName:@"user_id" inArguments:arguments]) {
            
            // 判断断连
            if (![EABleManager defaultManager].isConnected) {
                
                [self loseConnect];
                return;
            }
            
            EABindingOpsType ops = [arguments[@"ops"] integerValue] == 1 ? EABindingOpsTypeEnd:EABindingOpsTypeNormalBegin;
            __block EABingingOps *bingingOps = [[EABingingOps alloc] init];
            bingingOps.ops = ops;
            bingingOps.bindMod = [arguments[@"bindMod"] integerValue];
            NSString *userId = [NSString stringWithFormat:@"%@",arguments[@"user_id"]];
            if (userId.length > 0) {
                
                bingingOps.userId = userId;
            }
            kPlugInLog(@"开始绑定");
            if (ops == EABindingOpsTypeNormalBegin) {
                
                kPlugInLog(@"绑定中：等待用户点击");
            }
            WeakSelf;
            [[EABleSendManager defaultManager] operationChangeModel:bingingOps respond:^(EARespondModel * _Nonnull respondModel) {
                
                
                if (ops == EABindingOpsTypeNormalBegin) {
                    
                    if (respondModel.eErrorCode == EARespondCodeTypeSuccess) {
                        
                        kPlugInLog(@"绑定中：用户点击 ✅");
                        bingingOps.ops = EABindingOpsTypeEnd;
                        [[EABleSendManager defaultManager] operationChangeModel:bingingOps respond:^(EARespondModel * _Nonnull respondModel) {
                            
                            kPlugInLog(@"绑定结束");
                            [selfWeak setWatchRespondWithDataType:EADataInfoTypeBinding respondCodeType:(respondModel.eErrorCode == EARespondCodeTypeSuccess)?0:1];
                            
                            
                            EADeviceOps *ops = [[EADeviceOps alloc] init];
                            ops.deviceOpsType = EADeviceOpsTypeShowiPhonePairingAlert;
                            ops.deviceOpsStatus = EADeviceOpsStatusExecute;
                            [[EABleSendManager defaultManager] operationChangeModel:ops respond:^(EARespondModel * _Nonnull respondModel) {
                                
                                kPlugInLog(@"弹窗 iOS配对");
                                [selfWeak setWatchRespondWithDataType:EADataInfoTypeDeviceOps respondCodeType:(respondModel.eErrorCode == EARespondCodeTypeSuccess)?0:1];
                            }];
                        }];
                    }else {
                        
                        kPlugInLog(@"绑定中：用户点击 ❎");
                        [[EABleManager defaultManager] disconnectAndNotReConnectPeripheral];
                        kPlugInLog(@"绑定结束");
                    }
                }else {
                    
                    kPlugInLog(@"绑定结束");
                    [selfWeak setWatchRespondWithDataType:EADataInfoTypeBinding respondCodeType:(respondModel.eErrorCode == EARespondCodeTypeSuccess)?0:1];
                    if (respondModel.eErrorCode == EARespondCodeTypeFail) {
                        
                        [[EABleManager defaultManager] disconnectAndNotReConnectPeripheral];
                    }
                }
            }];
        }
    }
    else if ([call.method isEqualToString:kEAGetWatchInfo]) { // FIXME: - 获取手表信息
        
        NSDictionary *arguments = [self dictionaryWithJsonString:call.arguments] ;
        if ([self checkArgumentName:@"dataType" inArguments:arguments]) {
            
            // 判断断连
            if (![EABleManager defaultManager].isConnected) {
                
                [self loseConnect];
                return;
            }
            EADataInfoType dataInfoType = [arguments[@"dataType"] integerValue];
            NSInteger type = [arguments[@"type"] integerValue];
            [self getWatchInfo:dataInfoType type:type];
            
        }
    }
    else if ([call.method isEqualToString:kEASetWatchInfo]) { // FIXME: - 设置手表信息
        
        // 判断断连
        if (![EABleManager defaultManager].isConnected) {
            
            [self loseConnect];
            return;
        }
        NSDictionary *arguments = [self dictionaryWithJsonString:call.arguments] ;
        // 判断设置类型
        if (![self checkArgumentName:@"dataType" inArguments:arguments]) {
            
            return;
        }
        if (![self checkArgumentName:@"jsonString" inArguments:arguments]) {
            
            return;
        }
        
        NSString *valueJsonString = arguments[@"jsonString"];
        NSDictionary *value = [self dictionaryWithJsonString:valueJsonString];
        
        if ([value isKindOfClass:[NSDictionary class]]) {
            
            EADataInfoType dataInfoType = [[arguments objectForKey:@"dataType"] integerValue];
            WeakSelf;
            switch (dataInfoType) {
                case EADataInfoTypeUser:{
                    
                    EAUserModel *userModel = [EAUserModel modelWithJSON:value];
                    [[EABleSendManager defaultManager] operationChangeModel:userModel respond:^(EARespondModel * _Nonnull respondModel) {
                        
                        [selfWeak setWatchRespondWithDataType:dataInfoType respondCodeType:respondModel.eErrorCode];
                    }];
                }break;
                case EADataInfoTypeSyncTime: {
                    
                    // EASyncTime *syncTime = [EASyncTime getCurrentTime];
                    EASyncTime *syncTime = [EASyncTime modelWithJSON:value];
                    syncTime.timeHourType = [value[@"timeHourType"] intValue];
                    syncTime.timeZone = [value[@"timeZone"] intValue];
                    [[EABleSendManager defaultManager] operationChangeModel:syncTime respond:^(EARespondModel * _Nonnull respondModel) {
                        
                        [selfWeak setWatchRespondWithDataType:dataInfoType respondCodeType:respondModel.eErrorCode];
                        
                    }];
                }break;
                case EADataInfoTypeLanguage: {
                    
                    EALanguageModel *language = [EALanguageModel modelWithJSON:value];
                    EALanguageType languageType = [[value objectForKey:@"language"] integerValue];
                    language.language = languageType;
                    [[EABleSendManager defaultManager] operationChangeModel:language respond:^(EARespondModel * _Nonnull respondModel) {
                        
                        [selfWeak setWatchRespondWithDataType:dataInfoType respondCodeType:respondModel.eErrorCode];
                    }];
                }break;
                case EADataInfoTypeUnifiedUnit: {
                    
                    EAUnifiedUnitModel *unit = [EAUnifiedUnitModel modelWithJSON:value];
                    unit.unit = [[value objectForKey:@"unit"] integerValue];
                    [[EABleSendManager defaultManager] operationChangeModel:unit respond:^(EARespondModel * _Nonnull respondModel) {
                        
                        [selfWeak setWatchRespondWithDataType:dataInfoType respondCodeType:respondModel.eErrorCode];
                    }];
                }break;
                case EADataInfoTypeNotDisturb: {
                    
                    EANotDisturbModel *notDisturbModel = [EANotDisturbModel modelWithJSON:value];
                    [[EABleSendManager defaultManager] operationChangeModel:notDisturbModel respond:^(EARespondModel * _Nonnull respondModel) {
                        
                        [selfWeak setWatchRespondWithDataType:dataInfoType respondCodeType:respondModel.eErrorCode];
                    }];
                }break;
                case EADataInfoTypeDailyGoal: {
                    
                    EADailyGoalModel *dailyGoal = [EADailyGoalModel modelWithJSON:value];
                    [[EABleSendManager defaultManager] operationChangeModel:dailyGoal respond:^(EARespondModel * _Nonnull respondModel) {
                        
                        [selfWeak setWatchRespondWithDataType:dataInfoType respondCodeType:respondModel.eErrorCode];
                    }];
                }break;
                case EADataInfoTypeAutoCheckHeartRate: {
                    
                    EAAutoCheckHeartRateModel *dailyGoal = [EAAutoCheckHeartRateModel modelWithJSON:value];
                    [[EABleSendManager defaultManager] operationChangeModel:dailyGoal respond:^(EARespondModel * _Nonnull respondModel) {
                        
                        [selfWeak setWatchRespondWithDataType:dataInfoType respondCodeType:respondModel.eErrorCode];
                    }];
                }break;
                case EADataInfoTypeAutoCheckSedentariness: {
                    
                    NSInteger weekCycleBit = [value[@"weekCycleBit"] integerValue];
                    EAAutoCheckSedentarinessModel *sedentariness = [EAAutoCheckSedentarinessModel modelWithJSON:value];
                    sedentariness.weekCycleBit = weekCycleBit;
                    [[EABleSendManager defaultManager] operationChangeModel:sedentariness respond:^(EARespondModel * _Nonnull respondModel) {
                        
                        [selfWeak setWatchRespondWithDataType:dataInfoType respondCodeType:respondModel.eErrorCode];
                    }];
                }break;
                case EADataInfoTypeWeather: {
                    
                    EAWeatherModel *model = [EAWeatherModel modelWithJSON:value];
                    [[EABleSendManager defaultManager] operationChangeModel:model respond:^(EARespondModel * _Nonnull respondModel) {
                        
                        [selfWeak setWatchRespondWithDataType:dataInfoType respondCodeType:respondModel.eErrorCode];
                    }];
                }break;
                case EADataInfoTypeReminder: {
                    
                    EAReminderOps *model = [EAReminderOps modelWithJSON:value];
                    [[EABleSendManager defaultManager] operationChangeModel:model respond:^(EARespondModel * _Nonnull respondModel) {
                        
                        if ([respondModel isKindOfClass:[EAReminderRespondModel class]]) {
                            
                            EAReminderRespondModel *model = (EAReminderRespondModel*)respondModel;
                            [selfWeak setWatchRespondWithDataType:dataInfoType respondCodeType:model.eOpsStatus];
                            
                        }
                    }];
                }break;
                case EADataInfoTypeHeartRateWaringSetting: {
                    
                    EAHeartRateWaringSettingModel *model = [EAHeartRateWaringSettingModel modelWithJSON:value];
                    [[EABleSendManager defaultManager] operationChangeModel:model respond:^(EARespondModel * _Nonnull respondModel) {
                        
                        [selfWeak setWatchRespondWithDataType:dataInfoType respondCodeType:respondModel.eErrorCode];
                    }];
                    
                }break;
                case EADataInfoTypeGesturesSetting: {
                    
                    EAGesturesSettingModel *model = [EAGesturesSettingModel modelWithJSON:value];
                    [[EABleSendManager defaultManager] operationChangeModel:model respond:^(EARespondModel * _Nonnull respondModel) {
                        
                        [selfWeak setWatchRespondWithDataType:dataInfoType respondCodeType:respondModel.eErrorCode];
                    }];
                    
                }break;
                case EADataInfoTypeHomePage: {
                    
                    EAHomePageModel *model = [EAHomePageModel modelWithJSON:value];
                    [[EABleSendManager defaultManager] operationChangeModel:model respond:^(EARespondModel * _Nonnull respondModel) {
                        
                        [selfWeak setWatchRespondWithDataType:dataInfoType respondCodeType:respondModel.eErrorCode];
                    }];
                    
                }break;
                case EADataInfoTypeMenstrual: {
                    
                    // 判断参数
                    NSString *startDate = value[@"startDate"];
                    NSInteger keepDay = [value[@"keepDay"] integerValue];
                    NSInteger cycleDay = [value[@"cycleDay"] integerValue];
                    
                    if ([startDate containsString:@"-"] && startDate.length == 10 && (keepDay >=4 || keepDay<=10) && (cycleDay >=20 || cycleDay <=45)) {
                        
                        //                        EAMenstruals *model = [EAMenstruals allocInitWithStartDate:startDate keepDay:keepDay cycleDay:cycleDay];
                        EAMenstruals *model = [EAMenstruals eaAllocInitWithStartDate:startDate keepDay:keepDay cycleDay:cycleDay judgeCurrentTime:NO];
                        [[EABleSendManager defaultManager] operationChangeModel:model respond:^(EARespondModel * _Nonnull respondModel) {
                            
                            [selfWeak setWatchRespondWithDataType:dataInfoType respondCodeType:respondModel.eErrorCode];
                        }];
                    }else {
                        
                        [_channel invokeMethod:kArgumentsError arguments:@"arguments error"];
                    }
                    
                }break;
                case EADataInfoTypeDialPlate: {
                    
                    EADialPlateModel *model = [EADialPlateModel modelWithJSON:value];
                    [[EABleSendManager defaultManager] operationChangeModel:model respond:^(EARespondModel * _Nonnull respondModel) {
                        
                        [selfWeak setWatchRespondWithDataType:dataInfoType respondCodeType:respondModel.eErrorCode];
                    }];
                    
                }break;
                case EADataInfoTypeAppMessage: {
                    
                    EAShowAppMessageModel *showAppMessageModel = [EAShowAppMessageModel modelWithJSON:value];
                    EAAppMessageSwitchData *model = [showAppMessageModel getEAAppMessageSwitchData];
                    [[EABleSendManager defaultManager] operationChangeModel:model respond:^(EARespondModel * _Nonnull respondModel) {
                        
                        [selfWeak setWatchRespondWithDataType:dataInfoType respondCodeType:respondModel.eErrorCode];
                    }];
                    
                }break;
                case EADataInfoTypeHabitTracker: {
                    
                    EAHabitTrackers *model = [EAHabitTrackers modelWithJSON:value];
                    [[EABleSendManager defaultManager] operationChangeModel:model respond:^(EARespondModel * _Nonnull respondModel) {
                        
                        if ([respondModel isKindOfClass:[EAHabitTrackerRespondModel class]]) {
                            
                            EAHabitTrackerRespondModel *model = (EAHabitTrackerRespondModel*)respondModel;
                            [selfWeak setWatchRespondWithDataType:dataInfoType respondCodeType:model.eOpsStatus];
                            
                        }
                    }];
                }break;
                    
                case  EADataInfoTypeSocialSwitch:{
                    
                    EASocialSwitchModel *model = [EASocialSwitchModel modelWithJSON:value];
                    [[EABleSendManager defaultManager] operationChangeModel:model respond:^(EARespondModel * _Nonnull respondModel) {
                        
                        [selfWeak setWatchRespondWithDataType:dataInfoType respondCodeType:respondModel.eErrorCode];
                    }];
                }break;
                case  EADataInfoTypeMonitorReminder:{
                    
                    EAMonitorReminder *model = [EAMonitorReminder modelWithJSON:value];
                    [[EABleSendManager defaultManager] operationChangeModel:model respond:^(EARespondModel * _Nonnull respondModel) {
                        
                        [selfWeak setWatchRespondWithDataType:dataInfoType respondCodeType:respondModel.eErrorCode];
                    }];
                }break;
                case  EADataInfoTypeTelephoneBook:{
                    
                    EATelephoneBookModel *model = [EATelephoneBookModel modelWithJSON:value];
                    [[EABleSendManager defaultManager] operationChangeModel:model respond:^(EARespondModel * _Nonnull respondModel) {
                        
                        [selfWeak setWatchRespondWithDataType:dataInfoType respondCodeType:respondModel.eErrorCode];
                    }];
                }break;
                case  EADataInfoTypeSleepBloodOxygenMonitor:{
                    
                    EASleepBloodOxygenMonitor *model = [EASleepBloodOxygenMonitor modelWithJSON:value];
                    [[EABleSendManager defaultManager] operationChangeModel:model respond:^(EARespondModel * _Nonnull respondModel) {
                        
                        [selfWeak setWatchRespondWithDataType:dataInfoType respondCodeType:respondModel.eErrorCode];
                    }];
                }break;
                case  EADataInfoTypeStressMonitor:{
                    
                    EAStressMonitor *model = [EAStressMonitor modelWithJSON:value];
                    [[EABleSendManager defaultManager] operationChangeModel:model respond:^(EARespondModel * _Nonnull respondModel) {
                        
                        [selfWeak setWatchRespondWithDataType:dataInfoType respondCodeType:respondModel.eErrorCode];
                    }];
                }break;
                case  EADataInfoTypeVibrateIntensity:{
                    
                    EAVibrateIntensity *model = [EAVibrateIntensity modelWithJSON:value];
                    [[EABleSendManager defaultManager] operationChangeModel:model respond:^(EARespondModel * _Nonnull respondModel) {
                        
                        [selfWeak setWatchRespondWithDataType:dataInfoType respondCodeType:respondModel.eErrorCode];
                    }];
                }break;
                case  EADataInfoTypeMenstrualReminder:{
                    
                    EAMenstrualReminder *model = [EAMenstrualReminder modelWithJSON:value];
                    [[EABleSendManager defaultManager] operationChangeModel:model respond:^(EARespondModel * _Nonnull respondModel) {
                        
                        [selfWeak setWatchRespondWithDataType:dataInfoType respondCodeType:respondModel.eErrorCode];
                    }];
                }break;
                case  EADataInfoTypeUploadGPSLocation:{
                    
                    EAGPSLocation *model = [EAGPSLocation modelWithJSON:value];
                    [[EABleSendManager defaultManager] operationChangeModel:model respond:^(EARespondModel * _Nonnull respondModel) {
                        
                        [selfWeak setWatchRespondWithDataType:dataInfoType respondCodeType:respondModel.eErrorCode];
                    }];
                }break;
                default:
                    [_channel invokeMethod:kArgumentsError arguments:@"arguments error"];
                    break;
            }
            
        }else {
            
            [_channel invokeMethod:kArgumentsError arguments:@"arguments error"];
        }
    }
    else if ([call.method isEqualToString:kEAGetBigWatchData]) { // FIXME: - 获取大数据
        
        // 判断断连
        if (![EABleManager defaultManager].isConnected) {
            
            [self loseConnect];
            return;
        }
        
        //        EAGetBigDataRequestModel *model = [[EAGetBigDataRequestModel alloc] init];
        //        model.sportDataReq = 1;
        //        [[EABleSendManager defaultManager] operationgGetBigData:model respond:^(EARespondModel * _Nonnull respondModel) {
        //
        //        }];
        
        [[EABleBigDataManager defaultManager] eaSendSyncBigData:^(EARespondCodeType eaRespondCodeType) {
            
        }];
    }
    else if ([call.method isEqualToString:kEAOperationWatch]) { // FIXME: - 操作手表
        
        // 判断断连
        if (![EABleManager defaultManager].isConnected) {
            
            [self loseConnect];
        }else {
            
            
            NSDictionary *arguments = [self dictionaryWithJsonString:call.arguments] ;
            // 判断设置类型
            if (![self checkArgumentName:@"dataType" inArguments:arguments]) {
                
                return;
            }
            NSInteger index = [[arguments valueForKey:@"dataType"] integerValue];
            if (index >= 8) {
                
                index += 2;
            }
            EADeviceOps *model = [[EADeviceOps alloc] init];
            model.deviceOpsType = index;
            model.deviceOpsStatus = EADeviceOpsStatusExecute;
            [[EABleSendManager defaultManager] operationChangeModel:model respond:^(EARespondModel * _Nonnull respondModel) {
                
                NSDictionary *info = @{
                    @"respondCodeType":@(respondModel.eErrorCode),
                    @"operationType":@([[arguments valueForKey:@"dataType"] integerValue]),
                };
                [self.channel invokeMethod:kOperationWacthResponse arguments:[info modelToJSONString]];
            }];
            
        }
    }
    else if ([call.method isEqualToString:kEAOTA]) { // FIXME: - OTA
        
        // 判断断连
        if (![EABleManager defaultManager].isConnected) {
            
            [self loseConnect];
            return;
        }

        NSDictionary *arguments = [self dictionaryWithJsonString:call.arguments] ;
        // 判断设置类型
        if (![self checkArgumentName:@"otas" inArguments:arguments]) {
            
            [_channel invokeMethod:kArgumentsError arguments:@"otas not null"];
            return;
        }
        
        NSArray *otas = arguments[@"otas"];
        if ([otas isKindOfClass:[NSArray class]] && otas.count > 0) {
            
            [self otaAction:otas];
            
        }else {
            
            [_channel invokeMethod:kArgumentsError arguments:@"otas not null"];
        }
    }
    else if ([call.method isEqualToString:kEAAGPS]) { // FIXME: - AGPS
        
        // 判断断连
        if (![EABleManager defaultManager].isConnected) {
            
            [self loseConnect];
            return;
        }
 
        
        [[EAOTAManager defaultManager] eaUpgradeAGPSProgress:^(CGFloat progress) {
            
            [[NSNotificationCenter defaultCenter] postNotificationName:kNTF_EAOTAAGPSDataing object:[NSNumber numberWithFloat:progress] userInfo:nil];
            
        } complete:^(BOOL succ, NSError * _Nullable error) {
            
            if (succ) {
                
                [[NSNotificationCenter defaultCenter] postNotificationName:kNTF_EAOTAAGPSDataing object:[NSNumber numberWithFloat:1] userInfo:nil];
            }
            else
            {
                [[NSNotificationCenter defaultCenter] postNotificationName:kNTF_EAOTAAGPSDataing object:[NSNumber numberWithFloat:error.code] userInfo:nil];
            }
        }];
        //        }
    }
    
    else if ([call.method isEqualToString:kEACustomWatchface]) { // FIXME: - 自定义表盘
        
        // 判断断连
        if (![EABleManager defaultManager].isConnected) {
            
            [self loseConnect];
            return;
        }
        
        if ([EABleManager defaultManager].eaPeripheralModel.typeOfOTA == 1) {
            
            [_channel invokeMethod:kArgumentsError arguments:@"not support this function"];
            return;
        }
        
        NSDictionary *arguments = [self dictionaryWithJsonString:call.arguments] ;
        
        NSString *bgImagePath = arguments[@"bgImagePath"];
        UIImage *bgImage = [UIImage imageWithContentsOfFile:bgImagePath];
        if (!bgImage) {
            [_channel invokeMethod:kArgumentsError arguments:@"bgImagePath not null"];
            return;
        }
        
        BOOL isNumberWf = [arguments[@"isNumberWf"] boolValue];
        BOOL getPreviewImage = [arguments[@"getPreviewImage"] boolValue];
        NSInteger pointerColorType = [arguments[@"pointerColorType"] integerValue];
        
        if (isNumberWf) {
            
            NSString *colorHex = arguments[@"numbeColorHex"];
            UIColor *color = [UIColor colorWithHexString:colorHex];
            if (!color) {
                [_channel invokeMethod:kArgumentsError arguments:@"numbeColorHex is error"];
                return;
            }
            
            if (getPreviewImage) {
                
                UIImage *thumbnail = [EAMakeWatchFaceManager eaGetDefaultNumberThumbnailWithImage:bgImage color:color];
                [_channel invokeMethod:kCustomWatchFaceResponse arguments:UIImageJPEGRepresentation(thumbnail, 1)];
            }
            else
            {
                [EAMakeWatchFaceManager eaOtaDefaultNumberWatchFaceWithImage:bgImage color:color watchFaceId:@"" progress:^(CGFloat progress) {
                    
                    
                    [[NSNotificationCenter defaultCenter] postNotificationName:kNTF_EAOTAAGPSDataing object:[NSNumber numberWithFloat:progress] userInfo:nil];
                    
                } complete:^(BOOL succ, NSError * _Nullable error) {
                    
                    if (succ) {
                        
                        [[NSNotificationCenter defaultCenter] postNotificationName:kNTF_EAOTAAGPSDataing object:[NSNumber numberWithFloat:1] userInfo:nil];
                    }
                    else
                    {
                        [[NSNotificationCenter defaultCenter] postNotificationName:kNTF_EAOTAAGPSDataing object:[NSNumber numberWithFloat:error.code] userInfo:nil];
                    }
                }];
            }
        }
        else
        {
            if (getPreviewImage) {
                UIImage *thumbnail = [EAMakeWatchFaceManager eaGetDefaultPointerThumbnailWithImage:bgImage colorType:(pointerColorType == 0 ? EACWFTimerColorTypeBlack : EACWFTimerColorTypeWhite)];
                [_channel invokeMethod:kCustomWatchFaceResponse arguments:UIImageJPEGRepresentation(thumbnail, 1)];
            }
            else
            {
                [EAMakeWatchFaceManager eaOtaDefaultPointerWatchFaceWithImage:bgImage colorType:(pointerColorType == 0 ? EACWFTimerColorTypeBlack : EACWFTimerColorTypeWhite) watchFaceId:@"" progress:^(CGFloat progress) {
                    
                    
                    [[NSNotificationCenter defaultCenter] postNotificationName:kNTF_EAOTAAGPSDataing object:[NSNumber numberWithFloat:progress] userInfo:nil];
                    
                } complete:^(BOOL succ, NSError * _Nullable error) {
                    
                    if (succ) {
                        
                        [[NSNotificationCenter defaultCenter] postNotificationName:kNTF_EAOTAAGPSDataing object:[NSNumber numberWithFloat:1] userInfo:nil];
                    }
                    else
                    {
                        [[NSNotificationCenter defaultCenter] postNotificationName:kNTF_EAOTAAGPSDataing object:[NSNumber numberWithFloat:error.code] userInfo:nil];
                    }
                }];
            }
        }
  
    }
    else if ([call.method isEqualToString:kAddJieLiWatchFace]) { // FIXME: - 杰里707 添加表盘
           
        WeakSelf
        // 判断断连
        if (![EABleManager defaultManager].isConnected) {
            
            [self loseConnect];
            return;
        }
        NSString *filePath = call.arguments;
        EAFileModel *fileModel = [EAFileModel eaInitJlWatchFaceFileWithPath:filePath];
        
        [EAJieLiWacthFace eaGetWatchFaceList:^(NSArray * _Nonnull wfNames, NSError * _Nonnull error) {
               
            if (error) {
                
                NSDictionary *info = @{
                    @"isSuccess":@(NO),
                    @"errorType":@(error.code),
                    @"error":[error.domain isNotBlank]?error.domain:@""
                };
                [selfWeak.channel invokeMethod:kAddJieLiWatchFace arguments:[info modelToJSONString]];
            }
            else
            {
            
                if (wfNames.count >= 2) {
                    
                    NSDictionary *info = @{
                        @"isSuccess":@(NO),
                        @"errorType":@(0),
                        @"error":@"Insufficient space,Please delete and then synchronize [空间不足,请删除后再同步]"
                    };
                    [selfWeak.channel invokeMethod:kAddJieLiWatchFace arguments:[info modelToJSONString]];
                }
                else
                {
                    
                    [[EAOTAManager defaultManager] eaUpgradeWatchFaceFile:fileModel progress:^(CGFloat progress) {
                        
                        NSDictionary *info = @{
                            @"progress":@(progress * 100),
                            @"isSuccess":@(0)
                        };
                        [selfWeak.channel invokeMethod:kAddJieLiWatchFace arguments:[info modelToJSONString]];
                     
                        
                    } complete:^(BOOL succ, NSError * _Nullable error) {
                        
                        if (succ) {
                            
                            NSDictionary *info = @{
                                @"isSuccess":@(YES)
                            };
                            [selfWeak.channel invokeMethod:kAddJieLiWatchFace arguments:[info modelToJSONString]];
                        
                        }
                        else
                        {
                            
                            NSDictionary *info = @{
                                @"isSuccess":@(NO),
                                @"errorType":@(error.code),
                                @"error":[error.domain isNotBlank]?error.domain:@""
                            };
                            [selfWeak.channel invokeMethod:kAddJieLiWatchFace arguments:[info modelToJSONString]];
                            
                        }
                    }];
                }
            }
            
        }];
        
        
        
    }
    else if ([call.method isEqualToString:kDeleteJieLiWatchFace]) { // FIXME: - 杰里707 删除表盘
        
        // 判断断连
        if (![EABleManager defaultManager].isConnected) {
            
            [self loseConnect];
            return;
        }
        NSString *wfName = call.arguments;
        if (![wfName isNotBlank]) {
            
            [_channel invokeMethod:kArgumentsError arguments:@"Pls write dail name"];
            return;
        }
        
        WeakSelf
        [EAJieLiWacthFace eaDelWatchFace:wfName complete:^(BOOL succ, NSError * _Nonnull error) {
           
            if (error) {
                
                kPlugInLog(@"%@",error.domain);
            }
            int respondCodeType = (succ)?0:1;
            NSDictionary *info = @{
                @"respondCodeType":@(respondCodeType),
            };
            [selfWeak.channel invokeMethod:kDeleteJieLiWatchFace arguments:[info modelToJSONString]];
        }];
        
        
    }
    else if ([call.method isEqualToString:kGetJieLiWatchFace]) {// FIXME: - 杰里707 获取表盘
        
        // 判断断连
        if (![EABleManager defaultManager].isConnected) {
            
            [self loseConnect];
            return;
        }
      
        WeakSelf
        [EAJieLiWacthFace eaGetWatchFaceList:^(NSArray * _Nonnull wfNames, NSError * _Nonnull error) {
           
            if (error) {
                
                NSDictionary *info = @{
                    @"isSuccess":@(NO),
                    @"errorType":@(error.code),
                    @"error":[error.domain isNotBlank]?error.domain:@""
                };
                [selfWeak.channel invokeMethod:kGetJieLiWatchFace arguments:[info modelToJSONString]];
            }
            else
            {
                
                NSDictionary *info = @{
                    @"value":[wfNames modelToJSONString],
                };
                [selfWeak.channel invokeMethod:kGetJieLiWatchFace arguments:[info modelToJSONString]];
            }
        }];
    }
    else if ([call.method isEqualToString:kJieLiCusWatchFace]) {// FIXME: - 杰里707 自定义表盘
        
        // 判断断连
        if (![EABleManager defaultManager].isConnected) {
            
            [self loseConnect];
            return;
        }
        
        if ([EABleManager defaultManager].eaPeripheralModel.typeOfOTA == 0) {
            
            [_channel invokeMethod:kArgumentsError arguments:@"not support this function"];
            return;
        }
        
        NSDictionary *arguments = [self dictionaryWithJsonString:call.arguments] ;
        
        NSString *bgImagePath = arguments[@"bgImagePath"];
        UIImage *bgImage = [UIImage imageWithContentsOfFile:bgImagePath];
        if (!bgImage) {
            [_channel invokeMethod:kArgumentsError arguments:@"bgImagePath not null"];
            return;
        }
        
        EAJieLiCusWatchFaceSetTimeStyle style = [arguments[@"style"] integerValue];
        BOOL getPreviewImage = [arguments[@"getPreviewImage"] boolValue];
        
        if (getPreviewImage) {
            
//           UIImage *thumbnail = [EAMakeWatchFaceManager eaGetJieLiThumbnailWithImage:bgImage timeStyle:(style)];
            
            EAJieLiCustomWatchFace *jlCwf = [EAJieLiCustomWatchFace eaInitSetCusTimeStyle:(style)];
            UIImage *thumbnail = [EAMakeWatchFaceManager eaGetJieLiThumbnailWithImage:bgImage customWatchFace:jlCwf];
            [_channel invokeMethod:kCustomWatchFaceResponse arguments:UIImageJPEGRepresentation(thumbnail, 1)];
        }
        else
        {
            EAJieLiCustomWatchFace *jlCwf = [EAJieLiCustomWatchFace eaInitSetCusTimeStyle:(style)];
            [EAMakeWatchFaceManager eaOTAJieLiWatchFaceWithImage:bgImage customWacthFace:jlCwf cusInfos:^(NSString * _Nonnull cusId, UIImage * _Nonnull thumbnail, UIImage * _Nonnull bgImage) {
                
            } progress:^(CGFloat p) {
                [[NSNotificationCenter defaultCenter] postNotificationName:kNTF_EAOTAAGPSDataing object:[NSNumber numberWithFloat:p] userInfo:nil];
            } complete:^(BOOL succ, NSError * _Nullable error) {
                
                if (succ) {
                    
                    [[NSNotificationCenter defaultCenter] postNotificationName:kNTF_EAOTAAGPSDataing object:[NSNumber numberWithFloat:1] userInfo:nil];
                }
                else
                {
                    [[NSNotificationCenter defaultCenter] postNotificationName:kNTF_EAOTAAGPSDataing object:[NSNumber numberWithFloat:error.code] userInfo:nil];
                }
            }];
            
//            [EAMakeWatchFaceManager eaOTAJieLiWatchFaceWithImage:bgImage timeStyle:style cusInfos:^(NSString * _Nonnull cusId, UIImage * _Nonnull thumbnail, UIImage * _Nonnull bgImage) {
//                
//            } progress:^(CGFloat p) {
//                
//                [[NSNotificationCenter defaultCenter] postNotificationName:kNTF_EAOTAAGPSDataing object:[NSNumber numberWithFloat:p] userInfo:nil];
//                
//            } complete:^(BOOL succ, NSError * _Nullable error) {
//                
//                if (succ) {
//                    
//                    [[NSNotificationCenter defaultCenter] postNotificationName:kNTF_EAOTAAGPSDataing object:[NSNumber numberWithFloat:1] userInfo:nil];
//                }
//                else
//                {
//                    [[NSNotificationCenter defaultCenter] postNotificationName:kNTF_EAOTAAGPSDataing object:[NSNumber numberWithFloat:error.code] userInfo:nil];
//                }
//            }];
        }
        
    }
    else{
        
        result(FlutterMethodNotImplemented);
    }
}


- (void)bingdingWatch:(EABingingOps *)bingingOps {
    
    WeakSelf
    [BluetoothFunc bingdingWatch:bingingOps completion:^(BOOL succ) {
        
        if (succ) {
            
            EADeviceOps *ops = [[EADeviceOps alloc] init];
            ops.deviceOpsType = EADeviceOpsTypeShowiPhonePairingAlert;
            ops.deviceOpsStatus = EADeviceOpsStatusExecute;
            [[EABleSendManager defaultManager] operationChangeModel:ops respond:^(EARespondModel * _Nonnull respondModel) {
                
            }];
        }
        [selfWeak setWatchRespondWithDataType:EADataInfoTypeBinding respondCodeType:succ?0:1];
    }];
}


- (void)getWatchInfo:(EADataInfoType )dataInfoType type:(NSInteger )type {
    
    EARequestModel *requestModel = [[EARequestModel alloc] initWithRequestId:dataInfoType type:type];
    
    WeakSelf
    [[EABleSendManager defaultManager] operationGetInfoWithRequestModel:requestModel result:^(EABaseModel * _Nonnull baseModel) {
        
        
        EADataInfoModel *model = [baseModel getDataInfoModel];
        if (model.requestId == dataInfoType) {
            
            NSDictionary *value = [baseModel modelToJSONObject];
            
            if (dataInfoType == EADataInfoTypeAppMessage) {
                
                EAShowAppMessageModel *showAppMessageModel = [EAShowAppMessageModel eaAllocInitWithAppMessageSwitchData:(EAAppMessageSwitchData *)baseModel];
                value = [showAppMessageModel modelToJSONObject];
            }
            
            if ([[value objectForKey:@"type"] isEqualToString:@"G01"]) {
                
                NSMutableDictionary *newValue = [NSMutableDictionary dictionaryWithDictionary:value];
                newValue[@"type"] = @"iTouch Flex";
                
                value = [newValue copy];
            }
            
            NSDictionary *info = @{
                @"dataType":@(dataInfoType),
                @"type":@(type),
                @"value":value,
            };
            [selfWeak.channel invokeMethod:kGetWatchResponse arguments:[info modelToJSONString]];
        }
        else
        {
            EARespondModel *eaRespondModel = [EARespondModel eaInitErrorWithRequestId:dataInfoType];
            NSDictionary *value = [eaRespondModel modelToJSONObject];
            NSDictionary *info = @{
                @"dataType":@(dataInfoType),
                @"type":@(type),
                @"value":value,
            };
            [selfWeak.channel invokeMethod:kGetWatchResponse arguments:[info modelToJSONString]];
            
        }
    }];
    
    if (dataInfoType == EADataInfoTypeWatch) {
        
        [[EABleSendManager defaultManager] operationGetInfoWithType:(EADataInfoTypeWatchSupport) result:^(EABaseModel * _Nonnull baseModel) {
            
        }];
    }
}


- (void)setWatchRespondWithDataType:(NSInteger)dataType respondCodeType:(NSInteger )respondCodeType {
    
    NSDictionary *info = @{
        @"respondCodeType":@(respondCodeType),
        @"dataType":@(dataType),
    };
    if (dataType == EADataInfoTypeBinding) {
        [self.channel invokeMethod:kBingdingWatchResponse arguments:[info modelToJSONString]];
        
    }else {
        [self.channel invokeMethod:kSetWatchResponse arguments:[info modelToJSONString]];
        
    }
}


- (void)otaAction:(NSArray *)otas {
    
    // OTA
    NSMutableArray *models = [NSMutableArray new];
    
    
    _jlFirmwareFileModel = nil;
    
    BOOL isWatchFace = NO;
    
    for (NSDictionary *item in otas) {
        
        NSString *binPath = item[@"binPath"];
        NSInteger otaType = [item[@"firmwareType"] integerValue];  // Apollo, Res, Tp, Hr
        NSString *version = item[@"version"];
        
        NSFileManager *manager = [NSFileManager defaultManager];
        NSData *data = [manager contentsAtPath:binPath];
        
        if (data.length == 0) {
            
            NSDictionary *info = @{
                @"progress":@(-1)
            };
            [_channel invokeMethod:kProgress arguments:[info modelToJSONString]];
            kPlugInLog(@"The data is null in binPath!");
            return;
        }
        if (otaType == 1) {
            
            otaType = EAOtaRequestTypeRes;
        }
        else if (otaType == 2) {
            
            otaType = EAOtaRequestTypeTp;
        }
        else if (otaType == 3) {
            
            otaType = EAOtaRequestTypeHr;
            
        }
        else if (otaType == 4) {
            
            otaType = EAOtaRequestTypeUserWf;
            isWatchFace = YES;
        }
        else if (otaType == 5) {
            
            otaType = EAOtaRequestTypeJLFirmware;
            
            _jlFirmwareFileModel = [EAFileModel eaInitJlFirmwareFileWithPath:binPath];
        }
        //        EAFileModel *fileModel = [EAFileModel allocInitWithPath:binPath otaType:otaType version:version];
        EAFileModel *fileModel = [EAFileModel eaInitWithPath:binPath otaType:otaType version:version];
        [models addObject:fileModel];
    }
    
    
    if (_jlFirmwareFileModel) {
        
        [[EAOTAManager defaultManager] eaUpgradeJLFile:_jlFirmwareFileModel progress:^(CGFloat progress) {
            
            [[NSNotificationCenter defaultCenter] postNotificationName:kNTF_EAOTAAGPSDataing object:[NSNumber numberWithFloat:progress] userInfo:nil];
        } complete:^(BOOL succ, NSError * _Nullable error) {
            
            if (succ) {
                
                [[NSNotificationCenter defaultCenter] postNotificationName:kNTF_EAOTAAGPSDataing object:[NSNumber numberWithFloat:1] userInfo:nil];
            }
            else
            {
                [[NSNotificationCenter defaultCenter] postNotificationName:kNTF_EAOTAAGPSDataing object:[NSNumber numberWithFloat:error.code] userInfo:nil];
            }
        }];
        
        return;
    }
    
    
    
    if (models.count > 0) {
        
        if(!isWatchFace) {
            
            //            [[EABleSendManager defaultManager] upgradeFiles:models];
            [[EAOTAManager defaultManager] eaUpgradeFiles:models progress:^(CGFloat progress) {
                
                [[NSNotificationCenter defaultCenter] postNotificationName:kNTF_EAOTAAGPSDataing object:[NSNumber numberWithFloat:progress] userInfo:nil];
                
            } complete:^(BOOL succ, NSError * _Nullable error) {
                
                if (succ) {
                    
                    [[NSNotificationCenter defaultCenter] postNotificationName:kNTF_EAOTAAGPSDataing object:[NSNumber numberWithFloat:1] userInfo:nil];
                }
                else
                {
                    [[NSNotificationCenter defaultCenter] postNotificationName:kNTF_EAOTAAGPSDataing object:[NSNumber numberWithFloat:error.code] userInfo:nil];
                }
            }];
        }
        else if(isWatchFace && models.count == 1)
        {
            //            [[EABleSendManager defaultManager] upgradeWatchFaceFile:[models firstObject]];
            [[EAOTAManager defaultManager] eaUpgradeWatchFaceFile:[models firstObject] progress:^(CGFloat progress) {
                
                [[NSNotificationCenter defaultCenter] postNotificationName:kNTF_EAOTAAGPSDataing object:[NSNumber numberWithFloat:progress] userInfo:nil];
            } complete:^(BOOL succ, NSError * _Nullable error) {
                
                if (succ) {
                    
                    [[NSNotificationCenter defaultCenter] postNotificationName:kNTF_EAOTAAGPSDataing object:[NSNumber numberWithFloat:1] userInfo:nil];
                }
                else
                {
                    [[NSNotificationCenter defaultCenter] postNotificationName:kNTF_EAOTAAGPSDataing object:[NSNumber numberWithFloat:error.code] userInfo:nil];
                }
            }];
        }
        else {
            
            [_channel invokeMethod:kArgumentsError arguments:@"otas type error"];
        }
        _timerSec = kTimerSec;
        [self startTimer];
    }
    
}


- (void)watchfaceAction:(EAFileModel *)fileModel {
    
    //    [[EABleSendManager defaultManager] upgradeWatchFaceFile:fileModel];
    [[EAOTAManager defaultManager] eaUpgradeWatchFaceFile:fileModel progress:^(CGFloat progress) {
        
        [[NSNotificationCenter defaultCenter] postNotificationName:kNTF_EAOTAAGPSDataing object:[NSNumber numberWithFloat:progress] userInfo:nil];
    } complete:^(BOOL succ, NSError * _Nullable error) {
        if (succ) {
            
            [[NSNotificationCenter defaultCenter] postNotificationName:kNTF_EAOTAAGPSDataing object:[NSNumber numberWithFloat:1] userInfo:nil];
        }
        else
        {
            [[NSNotificationCenter defaultCenter] postNotificationName:kNTF_EAOTAAGPSDataing object:[NSNumber numberWithFloat:error.code] userInfo:nil];
        }
    }];
}


- (void)connectTimeOut {
    
    [[EABleManager defaultManager] stopScanPeripherals];
    [[EABleManager defaultManager] cancelConnectingPeripheral];
    [_channel invokeMethod:kConnectState arguments:@(ConnectState_notFind)];
}



#pragma mark - Timer func
- (void)startTimer{
    
    if (_timer == nil) {
        
        WeakSelf
        dispatch_queue_t queue = dispatch_get_global_queue(0, 0);
        _timer = dispatch_source_create(DISPATCH_SOURCE_TYPE_TIMER, 0, 0,queue);
        dispatch_source_set_timer(_timer,dispatch_walltime(NULL, 0),1 * NSEC_PER_SEC, 0);
        dispatch_source_set_event_handler(_timer, ^{
            
            selfWeak.timerSec --;
            
            if (selfWeak.timerSec == 0) {
                
                [selfWeak cancelTimer];
                [selfWeak showFail];
            }
        });
        dispatch_resume(_timer);
    }
}
- (void)cancelTimer{
    
    if (_timer) {
        
        dispatch_source_cancel(_timer);
        _timer = nil;
    }
}

- (void)showFail {
    kPlugInLog(@"~~~~~~ method:showFail");
    WeakSelf
    dispatch_async(dispatch_get_main_queue(), ^{
        
        NSDictionary *info = @{
            @"progress":@(-1)
        };
        [selfWeak.channel invokeMethod:kProgress arguments:[info modelToJSONString]];
    });
}

#pragma mark - EABleManagerDelegate
#define kData @"kCBAdvDataManufacturerData"
/// 扫描发现的设备 （实时）
/// @param peripheralModel 设备
- (void)didDiscoverPeripheral:(EAPeripheralModel *)peripheralModel {
    
    NSMutableDictionary *advertisementData = [NSMutableDictionary dictionaryWithDictionary:peripheralModel.advertisementData];
    advertisementData[kData] = [EADataValue eaConvertToHexStringByData:peripheralModel.advertisementData[kData]];
    
    NSMutableDictionary *item = [[NSMutableDictionary alloc] init];
    [item setValue:peripheralModel.SN forKey:@"snNumber"];
    [item setValue:peripheralModel.peripheral.name forKey:@"name"];
    [item setValue:@(peripheralModel.RSSI.integerValue) forKey:@"rssi"];
    [item setValue:advertisementData forKey:@"advertisementData"];
    [item setValue:peripheralModel.peripheral.identifier.UUIDString forKey:@"uuid"];
    [item setValue:@(peripheralModel.typeOfOTA) forKey:@"isJL707"];
    [item setValue:@(peripheralModel.statusOfOTA) forKey:@"needOta"];

    
    [self.channel invokeMethod:kScanWacthResponse arguments:[item modelToJSONString]];
}


#pragma mark - Private
- (NSDictionary *)dictionaryWithJsonString:(NSString *)jsonString {
    if (jsonString == nil) {
        
        return nil;
    }
    
    NSData *jsonData = [jsonString dataUsingEncoding:NSUTF8StringEncoding];
    NSError *err;
    NSDictionary *dic = [NSJSONSerialization JSONObjectWithData:jsonData options:NSJSONReadingAllowFragments error:&err];
    if(err){
        kPlugInLog(@"json解析失败：%@",err);
        return nil;
    }
    return dic;
}

- (BOOL)checkArgumentName:(NSString *)argumentName inArguments:(NSDictionary *)arguments {
    
    if (!arguments) {
        
        [_channel invokeMethod:kArgumentsError arguments:@"arguments error"];
        return NO;
    }
    
    if (![arguments.allKeys containsObject:argumentName]) {
        
        [_channel invokeMethod:kArgumentsError arguments:[argumentName stringByAppendingString:@" error"]];
        return NO;
    }
    
    NSString *argumentNameValue = [NSString stringWithFormat:@"%@",arguments[argumentName]];
    if (argumentNameValue.length == 0 || [argumentNameValue isEqualToString:@"null"]) {
        
        [_channel invokeMethod:kArgumentsError arguments:[argumentName stringByAppendingString:@" value error"]];
        return NO;
    }
    return YES;
}


@end

/**
 
 import 'package:flutter/services.dart';
 import 'package:path_provider/path_provider.dart';
 
 // 假设这是你的iOS插件通道
 const platform = MethodChannel('plugins.flutter.io/my_plugin');
 
 Future<void> getImageAndPassToPlugin(String imageName) async {
 // 获取图片目录
 final directory = await getApplicationDocumentsDirectory();
 final imagePath = '${directory.path}/$imageName';
 
 // 传递图片路径给iOS插件
 try {
 final Map<String, dynamic> params = <String, dynamic>{
 'imagePath': imagePath,
 };
 await platform.invokeMethod('passImagePath', params);
 } on PlatformException catch (e) {
 print("Failed to pass image path: '${e.message}'.");
 }
 }
 
 
 */
