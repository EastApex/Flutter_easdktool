#import "EasdktoolPlugin.h"
#import "BluetoothFunc.h"




#define kTimerSec 10
#define PlugInLog(format,...)  (self.config.debug ?  NSLog(@"[🍀🍀]:- " format, ##__VA_ARGS__) : @"")


@interface EasdktoolPlugin()<EABleManagerDelegate>
@property(nonatomic,assign) NSInteger timerSec;// 定时器时间

/* 创建一个内置定时器（系统）*/
@property(nonatomic,strong) dispatch_source_t timer;

/** <#name#> */
@property(nonatomic,strong) FlutterMethodChannel* channel ;

@property(nonatomic,strong) EABleConfig *config;


@end

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
#define kEALog                      @"EAShowLog"            // log
#define kEASyncPhoneInfoToWacth     @"EASyncPhoneInfoToWacth"// 设置手表信息
#define kEAScanWacth                @"EAScanWacth"          // 搜索手表
#define kEAStopScanWacth            @"EAStopScanWacth"          //停止搜索手表
#define kEAGetWacthStateInfo        @"EAGetWacthStateInfo"  //获取手表连接状态信息



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
    
    [_channel invokeMethod:kOperationPhone arguments:@(phoneOpsModel.eOps)];
    
    if(phoneOpsModel.eOps == EAPhoneOpsBig8803DataUpdateFinish) {
        
        for (int i = 1; i < 12; i ++) {
            
            NSInteger dataType = 3000 + i;
            NSArray *list = [[EABleSendManager defaultManager] getBigDataWithBigDataType:(dataType)];
            if (list.count > 0) {
                
                NSDictionary *info = @{
                    @"dataType":@(dataType),
                    @"flag":@(2),
                    @"value":[list yy_modelToJSONObject],
                };
                [_channel invokeMethod:kGetBigWatchData arguments:[info yy_modelToJSONString]];
            }
        }
    }
}



- (void)showProgress:(NSNotification *)no {
    
    PlugInLog(@"~~~~~~ showProgress:%@",no.object);
    if ([no.object floatValue] < 0) {
        
        [_channel invokeMethod:kProgress arguments:@(-1)];
        return;
    }
    
    NSInteger progress = [[NSString stringWithFormat:@"%0.2f",[no.object floatValue] * 100] integerValue];
    [_channel invokeMethod:kProgress arguments:@(progress)];
}




- (void)handleMethodCall:(FlutterMethodCall*)call result:(FlutterResult)result {
    PlugInLog(@"call method:%@ , arguments = %@",call.method,call.arguments);
    if ([call.method isEqualToString:kEALog]) { // FIXME: - log
        
        NSDictionary *arguments = [self dictionaryWithJsonString:call.arguments] ;
        if ([self checkArgumentName:@"showLog" inArguments:arguments]) {
            
            _config.debug = [arguments[@"showLog"] boolValue];
            [[EABleManager defaultManager] setBleConfig:_config];
        }
    }
    
    else if ([call.method isEqualToString:kEAGetWacthStateInfo]) { // FIXME: - 获取手表连接状态信息
        
        EAConnectStateType connectStateType = [EABleManager defaultManager].connectState;
        NSDictionary *info = @{
            @"connectState":@(connectStateType),
        };
        
        result([info yy_modelToJSONString]);
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
                
                [[EABleManager defaultManager] unbindPeripheral];
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
            PlugInLog(@"开始绑定");
            if (ops == EABindingOpsTypeNormalBegin) {
                
                PlugInLog(@"绑定中：等待用户点击");
            }
            WeakSelf;
            [[EABleSendManager defaultManager] operationChangeModel:bingingOps respond:^(EARespondModel * _Nonnull respondModel) {
                
                
                if (ops == EABindingOpsTypeNormalBegin) {
                    
                    if (respondModel.eErrorCode == EARespondCodeTypeSuccess) {
                        
                        PlugInLog(@"绑定中：用户点击 ✅");
                        bingingOps.ops = EABindingOpsTypeEnd;
                        [[EABleSendManager defaultManager] operationChangeModel:bingingOps respond:^(EARespondModel * _Nonnull respondModel) {
                            
                            PlugInLog(@"绑定结束");
                            [selfWeak setWatchRespondWithDataType:EADataInfoTypeBinding respondCodeType:(respondModel.eErrorCode == EARespondCodeTypeSuccess)?0:1];
                            
                            
                            EADeviceOps *ops = [[EADeviceOps alloc] init];
                            ops.deviceOpsType = EADeviceOpsTypeShowiPhonePairingAlert;
                            ops.deviceOpsStatus = EADeviceOpsStatusExecute;
                            [[EABleSendManager defaultManager] operationChangeModel:ops respond:^(EARespondModel * _Nonnull respondModel) {
                                
                                PlugInLog(@"弹窗 iOS配对");
                                [selfWeak setWatchRespondWithDataType:EADataInfoTypeDeviceOps respondCodeType:(respondModel.eErrorCode == EARespondCodeTypeSuccess)?0:1];
                            }];
                        }];
                    }else {
                        
                        PlugInLog(@"绑定中：用户点击 ❎");
                        [[EABleManager defaultManager] unbindPeripheral];
                        PlugInLog(@"绑定结束");
                    }
                }else {
                    
                    PlugInLog(@"绑定结束");
                    [selfWeak setWatchRespondWithDataType:EADataInfoTypeBinding respondCodeType:(respondModel.eErrorCode == EARespondCodeTypeSuccess)?0:1];
                    if (respondModel.eErrorCode == EARespondCodeTypeFail) {
                        
                        [[EABleManager defaultManager] unbindPeripheral];
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
//            if (dataInfoType == EADataInfoTypeMonitorReminder) {
                
                NSInteger type = [arguments[@"type"] integerValue];
                [self getWatchInfo:dataInfoType type:type];
//            }else {
//
//                [self getWatchInfo:dataInfoType];
//            }
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
                    
                    EAUserModel *userModel = [EAUserModel yy_modelWithJSON:value];
                    [[EABleSendManager defaultManager] operationChangeModel:userModel respond:^(EARespondModel * _Nonnull respondModel) {
                        
                        [selfWeak setWatchRespondWithDataType:dataInfoType respondCodeType:respondModel.eErrorCode];
                    }];
                }break;
                case EADataInfoTypeSyncTime: {
                    
                    // EASyncTime *syncTime = [EASyncTime getCurrentTime];
                    EASyncTime *syncTime = [EASyncTime yy_modelWithJSON:value];
                    syncTime.timeHourType = [value[@"timeHourType"] intValue];
                    syncTime.timeZone = [value[@"timeZone"] intValue];
                    [[EABleSendManager defaultManager] operationChangeModel:syncTime respond:^(EARespondModel * _Nonnull respondModel) {
                        
                        [selfWeak setWatchRespondWithDataType:dataInfoType respondCodeType:respondModel.eErrorCode];
                        
                    }];
                }break;
                case EADataInfoTypeLanguage: {
                    
                    EALanguageModel *language = [EALanguageModel yy_modelWithJSON:value];
                    [[EABleSendManager defaultManager] operationChangeModel:language respond:^(EARespondModel * _Nonnull respondModel) {
                        
                        [selfWeak setWatchRespondWithDataType:dataInfoType respondCodeType:respondModel.eErrorCode];
                    }];
                }break;
                case EADataInfoTypeUnifiedUnit: {
                    
                    EAUnifiedUnitModel *unit = [EAUnifiedUnitModel yy_modelWithJSON:value];
                    unit.unit = [[value objectForKey:@"unit"] integerValue];
                    [[EABleSendManager defaultManager] operationChangeModel:unit respond:^(EARespondModel * _Nonnull respondModel) {
                        
                        [selfWeak setWatchRespondWithDataType:dataInfoType respondCodeType:respondModel.eErrorCode];
                    }];
                }break;
                case EADataInfoTypeNotDisturb: {
                    
                    EANotDisturbModel *notDisturbModel = [EANotDisturbModel yy_modelWithJSON:value];
                    [[EABleSendManager defaultManager] operationChangeModel:notDisturbModel respond:^(EARespondModel * _Nonnull respondModel) {
                        
                        [selfWeak setWatchRespondWithDataType:dataInfoType respondCodeType:respondModel.eErrorCode];
                    }];
                }break;
                case EADataInfoTypeDailyGoal: {
                    
                    EADailyGoalModel *dailyGoal = [EADailyGoalModel yy_modelWithJSON:value];
                    [[EABleSendManager defaultManager] operationChangeModel:dailyGoal respond:^(EARespondModel * _Nonnull respondModel) {
                        
                        [selfWeak setWatchRespondWithDataType:dataInfoType respondCodeType:respondModel.eErrorCode];
                    }];
                }break;
                case EADataInfoTypeAutoCheckHeartRate: {
                    
                    EAAutoCheckHeartRateModel *dailyGoal = [EAAutoCheckHeartRateModel yy_modelWithJSON:value];
                    [[EABleSendManager defaultManager] operationChangeModel:dailyGoal respond:^(EARespondModel * _Nonnull respondModel) {
                        
                        [selfWeak setWatchRespondWithDataType:dataInfoType respondCodeType:respondModel.eErrorCode];
                    }];
                }break;
                case EADataInfoTypeAutoCheckSedentariness: {
                    
                    NSInteger weekCycleBit = [value[@"weekCycleBit"] integerValue];
                    EAAutoCheckSedentarinessModel *sedentariness = [EAAutoCheckSedentarinessModel yy_modelWithJSON:value];
                    sedentariness.weekCycleBit = weekCycleBit;
                    [[EABleSendManager defaultManager] operationChangeModel:sedentariness respond:^(EARespondModel * _Nonnull respondModel) {
                        
                        [selfWeak setWatchRespondWithDataType:dataInfoType respondCodeType:respondModel.eErrorCode];
                    }];
                }break;
                case EADataInfoTypeWeather: {
                    
                    EAWeatherModel *model = [EAWeatherModel yy_modelWithJSON:value];
                    [[EABleSendManager defaultManager] operationChangeModel:model respond:^(EARespondModel * _Nonnull respondModel) {
                        
                        [selfWeak setWatchRespondWithDataType:dataInfoType respondCodeType:respondModel.eErrorCode];
                    }];
                }break;
                case EADataInfoTypeReminder: {
                    
                    EAReminderOps *model = [EAReminderOps yy_modelWithJSON:value];
                    [[EABleSendManager defaultManager] operationChangeModel:model respond:^(EARespondModel * _Nonnull respondModel) {
                        
                        if ([respondModel isKindOfClass:[EAReminderRespondModel class]]) {
                            
                            EAReminderRespondModel *model = (EAReminderRespondModel*)respondModel;
                            [selfWeak setWatchRespondWithDataType:dataInfoType respondCodeType:model.eOpsStatus];
                            
                        }
                    }];
                }break;
                case EADataInfoTypeHeartRateWaringSetting: {
                    
                    EAHeartRateWaringSettingModel *model = [EAHeartRateWaringSettingModel yy_modelWithJSON:value];
                    [[EABleSendManager defaultManager] operationChangeModel:model respond:^(EARespondModel * _Nonnull respondModel) {
                        
                        [selfWeak setWatchRespondWithDataType:dataInfoType respondCodeType:respondModel.eErrorCode];
                    }];
                    
                }break;
                case EADataInfoTypeGesturesSetting: {
                    
                    EAGesturesSettingModel *model = [EAGesturesSettingModel yy_modelWithJSON:value];
                    [[EABleSendManager defaultManager] operationChangeModel:model respond:^(EARespondModel * _Nonnull respondModel) {
                        
                        [selfWeak setWatchRespondWithDataType:dataInfoType respondCodeType:respondModel.eErrorCode];
                    }];
                    
                }break;
                case EADataInfoTypeHomePage: {
                    
                    EAHomePageModel *model = [EAHomePageModel yy_modelWithJSON:value];
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
                        
                        EAMenstruals *model = [EAMenstruals allocInitWithStartDate:startDate keepDay:keepDay cycleDay:cycleDay];
                        [[EABleSendManager defaultManager] operationChangeModel:model respond:^(EARespondModel * _Nonnull respondModel) {
                            
                            [selfWeak setWatchRespondWithDataType:dataInfoType respondCodeType:respondModel.eErrorCode];
                        }];
                    }else {
                        
                        [_channel invokeMethod:kArgumentsError arguments:@"arguments error"];
                    }
                    
                }break;
                case EADataInfoTypeDialPlate: {
                    
                    EADialPlateModel *model = [EADialPlateModel yy_modelWithJSON:value];
                    [[EABleSendManager defaultManager] operationChangeModel:model respond:^(EARespondModel * _Nonnull respondModel) {
                        
                        [selfWeak setWatchRespondWithDataType:dataInfoType respondCodeType:respondModel.eErrorCode];
                    }];
                    
                }break;
                case EADataInfoTypeAppMessage: {
                    
                    EAShowAppMessageModel *showAppMessageModel = [EAShowAppMessageModel yy_modelWithJSON:value];
                    EAAppMessageSwitchData *model = [showAppMessageModel getEAAppMessageSwitchData];
                    [[EABleSendManager defaultManager] operationChangeModel:model respond:^(EARespondModel * _Nonnull respondModel) {
                        
                        [selfWeak setWatchRespondWithDataType:dataInfoType respondCodeType:respondModel.eErrorCode];
                    }];
                    
                }break;
                case EADataInfoTypeHabitTracker: {
                    
                    EAHabitTrackers *model = [EAHabitTrackers yy_modelWithJSON:value];
                    [[EABleSendManager defaultManager] operationChangeModel:model respond:^(EARespondModel * _Nonnull respondModel) {
                        
                        if ([respondModel isKindOfClass:[EAHabitTrackerRespondModel class]]) {
                            
                            EAHabitTrackerRespondModel *model = (EAHabitTrackerRespondModel*)respondModel;
                            [selfWeak setWatchRespondWithDataType:dataInfoType respondCodeType:model.eOpsStatus];
                            
                        }
                    }];
                }break;
                    
                case  EADataInfoTypeSocialSwitch:{
                    
                    EASocialSwitchModel *model = [EASocialSwitchModel yy_modelWithJSON:value];
                    [[EABleSendManager defaultManager] operationChangeModel:model respond:^(EARespondModel * _Nonnull respondModel) {
                        
                       [selfWeak setWatchRespondWithDataType:dataInfoType respondCodeType:respondModel.eErrorCode];
                    }];
                }break;
                case  EADataInfoTypeMonitorReminder:{
                    
                    EAMonitorReminder *model = [EAMonitorReminder yy_modelWithJSON:value];
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
        
        EAGetBigDataRequestModel *model = [[EAGetBigDataRequestModel alloc] init];
        model.sportDataReq = 1;
        [[EABleSendManager defaultManager] operationgGetBigData:model respond:^(EARespondModel * _Nonnull respondModel) {
            
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
                [self.channel invokeMethod:kOperationWacthResponse arguments:[info yy_modelToJSONString]];
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
            
            return;
        }
        
        NSArray *otas = arguments[@"otas"];
        if ([otas isKindOfClass:[NSArray class]] && otas.count > 0) {
            
            [self otaAction:otas];
            
        }else {
            
            [_channel invokeMethod:kArgumentsError arguments:@"otas not null"];
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

- (void)getWatchInfo:(EADataInfoType )dataInfoType {
    
    WeakSelf
    [[EABleSendManager defaultManager] operationGetInfoWithType:(dataInfoType) result:^(EABaseModel * _Nonnull baseModel) {
        
        NSDictionary *value = [baseModel yy_modelToJSONObject];
        
        if (dataInfoType == EADataInfoTypeAppMessage) {
            
            EAShowAppMessageModel *showAppMessageModel = [EAShowAppMessageModel allocInitWithAppMessageSwitchData:(EAAppMessageSwitchData *)baseModel];
            value = [showAppMessageModel yy_modelToJSONObject];
        }
        if ([[value objectForKey:@"type"] isEqualToString:@"G01"]) {
            
            NSMutableDictionary *newValue = [NSMutableDictionary dictionaryWithDictionary:value];
            newValue[@"type"] = @"iTouch Flex";
            
            value = [newValue copy];
        }
        
        NSDictionary *info = @{
            @"dataType":@(dataInfoType),
            @"value":value,
        };
        [selfWeak.channel invokeMethod:kGetWatchResponse arguments:[info yy_modelToJSONString]];
    }];
}

- (void)getWatchInfo:(EADataInfoType )dataInfoType type:(NSInteger )type {
    
    EARequestModel *requestModel = [[EARequestModel alloc] initWithRequestId:dataInfoType type:type];
    
    WeakSelf
    [[EABleSendManager defaultManager] operationGetInfoWithRequestModel:requestModel result:^(EABaseModel * _Nonnull baseModel) {
        
        NSDictionary *value = [baseModel yy_modelToJSONObject];
        
        if (dataInfoType == EADataInfoTypeAppMessage) {
            
            EAShowAppMessageModel *showAppMessageModel = [EAShowAppMessageModel allocInitWithAppMessageSwitchData:(EAAppMessageSwitchData *)baseModel];
            value = [showAppMessageModel yy_modelToJSONObject];
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
        [selfWeak.channel invokeMethod:kGetWatchResponse arguments:[info yy_modelToJSONString]];
    }];
}


- (void)setWatchRespondWithDataType:(NSInteger)dataType respondCodeType:(NSInteger )respondCodeType {
    
    NSDictionary *info = @{
        @"respondCodeType":@(respondCodeType),
        @"dataType":@(dataType),
    };
    if (dataType == EADataInfoTypeBinding) {
        [self.channel invokeMethod:kBingdingWatchResponse arguments:[info yy_modelToJSONString]];
        
    }else {
        [self.channel invokeMethod:kSetWatchResponse arguments:[info yy_modelToJSONString]];
        
    }
}


- (void)otaAction:(NSArray *)otas {
    
    // OTA
    NSMutableArray *models = [NSMutableArray new];
    
    BOOL isWatchFace = NO;
    
    for (NSDictionary *item in otas) {
        
        NSString *binPath = item[@"binPath"];
        NSInteger otaType = [item[@"firmwareType"] integerValue];  // Apollo, Res, Tp, Hr
        NSString *version = item[@"version"];
        
        NSFileManager *manager = [NSFileManager defaultManager];
        NSData *data = [manager contentsAtPath:binPath];
        
        if (data.length == 0) {
            
            [_channel invokeMethod:kProgress arguments:@(-1)];
            PlugInLog(@"The data is null in binPath!");
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
            
        }else if (otaType == 4) {
            
            otaType = EAOtaRequestTypeUserWf;
            isWatchFace = YES;
        }
        
        EAFileModel *fileModel = [EAFileModel allocInitWithPath:binPath otaType:otaType version:version];
        [models addObject:fileModel];
    }
    
    
    if (models.count > 0) {
        
        if(!isWatchFace) {
            
            [[EABleSendManager defaultManager] upgradeFiles:models];
        }
        else if(isWatchFace && models.count == 1)
        {
            [[EABleSendManager defaultManager] upgradeWatchFaceFile:[models firstObject]];
        }
        else {
            
            [_channel invokeMethod:kArgumentsError arguments:@"otas type error"];
        }
        _timerSec = kTimerSec;
        [self startTimer];
    }
    
}

- (void)watchfaceAction:(EAFileModel *)fileModel {
    
    [[EABleSendManager defaultManager] upgradeWatchFaceFile:fileModel];
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
    PlugInLog(@"~~~~~~ method:showFail");
    WeakSelf
    dispatch_async(dispatch_get_main_queue(), ^{
        
        [selfWeak.channel invokeMethod:kProgress arguments:@(-1)];
    });
}

#pragma mark - EABleManagerDelegate

/// 扫描发现的设备 （实时）
/// @param peripheralModel 设备
- (void)didDiscoverPeripheral:(EAPeripheralModel *)peripheralModel {
    
    NSMutableDictionary *item = [[NSMutableDictionary alloc] init];
    [item setValue:peripheralModel.SN forKey:@"snNumber"];
    [item setValue:peripheralModel.peripheral.name forKey:@"name"];
    [item setValue:@(peripheralModel.RSSI.integerValue) forKey:@"rssi"];
    [item setValue:peripheralModel.advertisementData forKey:@"advertisementData"];
    [item setValue:peripheralModel.peripheral.identifier.UUIDString forKey:@"uuid"];
    
    [self.channel invokeMethod:kScanWacthResponse arguments:[item yy_modelToJSONString]];
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
        PlugInLog(@"json解析失败：%@",err);
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
