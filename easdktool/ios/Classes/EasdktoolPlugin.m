#import "EasdktoolPlugin.h"
#import "BluetoothFunc.h"




#define kTimerSec 10
@interface EasdktoolPlugin()
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
#define kEALog                      @"EALog"                // log
#define kEASyncPhoneInfoToWacth     @"EASyncPhoneInfoToWacth"// 设置手表信息

/// MARK: - invoke method Name
#define kArgumentsError             @"ArgumentsError"
#define kConnectState               @"ConnectState"
#define kBluetoothState             @"BluetoothState"
#define kSetWatchResponse           @"SetWatchResponse"
#define kGetWatchResponse           @"GetWatchResponse"
#define kGetWatchResponse           @"GetWatchResponse"
#define kGetBigWatchData            @"GetBigWatchData"
#define kOperationPhone             @"OperationPhone"
#define kProgress                   @"Progress"

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
    _config.deviceHeadNames = @[@"APEX A02",@"APEX M02",@"APEX M02L",@"APEX M51",@"iTouch Flex"]; // 需要支持的蓝牙设备名称
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
    
    [_channel invokeMethod:kConnectState arguments:@(ConnectState_succ)];
    
}

- (void)connectFailed {
    
    [_channel invokeMethod:kConnectState arguments:@(ConnectState_fail)];
}

- (void)loseConnect {
    
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
    
    [_channel invokeMethod:kBluetoothState arguments:@(phoneOpsModel.eOps)];
    
    if(phoneOpsModel.eOps == EAPhoneOpsBig8803DataUpdateFinish) {
        
        for (int i = 1; i < 11; i ++) {
            
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
    
    if ([no.object floatValue] < 0) {
        
        [_channel invokeMethod:kProgress arguments:@(-1)];
        return;
    }
    
    NSInteger progress = [[NSString stringWithFormat:@"%0.2f",[no.object floatValue] * 100] integerValue];
    [_channel invokeMethod:kProgress arguments:@(progress)];
}




- (void)handleMethodCall:(FlutterMethodCall*)call result:(FlutterResult)result {
    
    if ([call.method isEqualToString:kEALog]) { // FIXME: - log
        
        NSDictionary *arguments = [self dictionaryWithJsonString:call.arguments] ;
        if ([self checkArgumentName:@"showLog" inArguments:arguments]) {
            
            _config.debug = [arguments[@"showLog"] boolValue];
            [[EABleManager defaultManager] setBleConfig:_config];
        }
        
    }else if ([call.method isEqualToString:kEAConnectWatch]) {  // FIXME: - 连接
        
        NSDictionary *arguments = [self dictionaryWithJsonString:call.arguments] ;
        if ([self checkArgumentName:@"snNumber" inArguments:arguments]) {
            
            NSString *snNumber = arguments[@"snNumber"];
            if (snNumber.length == 0 || [snNumber isEqualToString:@"null"]) {
                
                [_channel invokeMethod:kArgumentsError arguments:@"snNumber error"];
                return;
            }
            [[EABleManager defaultManager] reConnectToPeripheral:snNumber];
        }
        
    }else if ([call.method isEqualToString:kEADisConnectWatch]) { // FIXME: - 断开
        
        [[EABleManager defaultManager] disconnectPeripheral];
        
    }else if ([call.method isEqualToString:kEAUnbindWatch]) { // FIXME: - 解绑
        
        [[EABleManager defaultManager] unbindPeripheral];
        
    }
    else if ([call.method isEqualToString:kEAbindingWatch]) { // FIXME: - 绑定手表
        
        NSDictionary *arguments = [self dictionaryWithJsonString:call.arguments] ;
        if ([self checkArgumentName:@"user_id" inArguments:arguments]) {
            
            // 判断断连
            if (![EABleManager defaultManager].isConnected) {
                
                [self loseConnect];
                return;
            }
            
            EABingingOps *bingingOps = [[EABingingOps alloc] init];
            bingingOps.ops = EABindingOpsTypeEnd;
            NSString *userId = [NSString stringWithFormat:@"%@",arguments[@"user_id"]];
            if (userId.length > 0) {
                
                bingingOps.userId = userId;
            }
            [self bingdingWatch:bingingOps];
        }
        
    }
    else if ([call.method isEqualToString:kEASyncPhoneInfoToWacth]) { // FIXME: - 同步手机信息
        
        
        
    }
    else if ([call.method isEqualToString:kEAGetWatchInfo]) { // FIXME: - 获取手表信息
        
        NSDictionary *arguments = [self dictionaryWithJsonString:call.arguments] ;
        if ([self checkArgumentName:@"type" inArguments:arguments]) {
            
            // 判断断连
            if (![EABleManager defaultManager].isConnected) {
                
                [self loseConnect];
                return;
            }
            EADataInfoType dataInfoType = [arguments[@"type"] integerValue];
            [self getWatchInfo:dataInfoType];
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
        if (![self checkArgumentName:@"type" inArguments:arguments]) {
            
            return;
        }
        if (![self checkArgumentName:@"jsonString" inArguments:arguments]) {
            
            return;
        }
    
        NSString *valueJsonString = arguments[@"jsonString"];
        NSDictionary *value = [self dictionaryWithJsonString:valueJsonString];
        
        if ([value isKindOfClass:[NSDictionary class]]) {
            
            EADataInfoType dataInfoType = [[arguments objectForKey:@"type"] integerValue];
            WeakSelf;
            switch (dataInfoType) {
                case EADataInfoTypeUser:{
                    
                    EAUserModel *userModel = [EAUserModel yy_modelWithJSON:value];
                    [[EABleSendManager defaultManager] changeInfo:userModel respond:^(EARespondModel * _Nonnull respondModel) {
                        
                        [selfWeak setWatchRespondWithDataType:dataInfoType respondCodeType:respondModel.eErrorCode];
                    }];
                }break;
                case EADataInfoTypeSyncTime: {
                    
                    EASyncTime *syncTime = [EASyncTime getCurrentTime];
                    [[EABleSendManager defaultManager] changeInfo:syncTime respond:^(EARespondModel * _Nonnull respondModel) {
                        
                        [selfWeak setWatchRespondWithDataType:dataInfoType respondCodeType:respondModel.eErrorCode];
                        
                    }];
                }break;
                case EADataInfoTypeLanguage: {
                    
                    EALanguageModel *language = [EALanguageModel yy_modelWithJSON:value];
                    [[EABleSendManager defaultManager] changeInfo:language respond:^(EARespondModel * _Nonnull respondModel) {
                        
                        [selfWeak setWatchRespondWithDataType:dataInfoType respondCodeType:respondModel.eErrorCode];
                    }];
                }break;
                case EADataInfoTypeUnifiedUnit: {
                    
                    EAUnifiedUnitModel *unit = [EAUnifiedUnitModel yy_modelWithJSON:value];
                    [[EABleSendManager defaultManager] changeInfo:unit respond:^(EARespondModel * _Nonnull respondModel) {
                        
                        [selfWeak setWatchRespondWithDataType:dataInfoType respondCodeType:respondModel.eErrorCode];
                    }];
                }break;
                case EADataInfoTypeNotDisturb: {
                    
                    EANotDisturbModel *notDisturbModel = [EANotDisturbModel yy_modelWithJSON:value];
                    [[EABleSendManager defaultManager] changeInfo:notDisturbModel respond:^(EARespondModel * _Nonnull respondModel) {
                        
                        [selfWeak setWatchRespondWithDataType:dataInfoType respondCodeType:respondModel.eErrorCode];
                    }];
                }break;
                case EADataInfoTypeDailyGoal: {
                    
                    EADailyGoalModel *dailyGoal = [EADailyGoalModel yy_modelWithJSON:value];
                    [[EABleSendManager defaultManager] changeInfo:dailyGoal respond:^(EARespondModel * _Nonnull respondModel) {
                        
                        [selfWeak setWatchRespondWithDataType:dataInfoType respondCodeType:respondModel.eErrorCode];
                    }];
                }break;
                case EADataInfoTypeAutoCheckHeartRate: {
                    
                    EAAutoCheckHeartRateModel *dailyGoal = [EAAutoCheckHeartRateModel yy_modelWithJSON:value];
                    [[EABleSendManager defaultManager] changeInfo:dailyGoal respond:^(EARespondModel * _Nonnull respondModel) {
                        
                        [selfWeak setWatchRespondWithDataType:dataInfoType respondCodeType:respondModel.eErrorCode];
                    }];
                }break;
                case EADataInfoTypeAutoCheckSedentariness: {
                    
                    NSInteger weekCycleBit = [EADataValue getWeekCycleByWeekCycleBitString:value[@"cycles"]];
                    EAAutoCheckSedentarinessModel *sedentariness = [EAAutoCheckSedentarinessModel yy_modelWithJSON:value];
                    sedentariness.weekCycleBit = weekCycleBit;
                    [[EABleSendManager defaultManager] changeInfo:sedentariness respond:^(EARespondModel * _Nonnull respondModel) {
                        
                        [selfWeak setWatchRespondWithDataType:dataInfoType respondCodeType:respondModel.eErrorCode];
                    }];
                }break;
                case EADataInfoTypeWeather: {
                    
                    EAWeatherModel *model = [EAWeatherModel yy_modelWithJSON:value];
                    [[EABleSendManager defaultManager] changeInfo:model respond:^(EARespondModel * _Nonnull respondModel) {
                        
                        [selfWeak setWatchRespondWithDataType:dataInfoType respondCodeType:respondModel.eErrorCode];
                    }];
                }break;
                case EADataInfoTypeReminder: {
                    
                    EAReminderOps *model = [EAReminderOps yy_modelWithJSON:value];
                    [[EABleSendManager defaultManager] changeInfo:model respond:^(EARespondModel * _Nonnull respondModel) {
                        
                        [selfWeak setWatchRespondWithDataType:dataInfoType respondCodeType:respondModel.eErrorCode];
                    }];
                }break;
                case EADataInfoTypeHeartRateWaringSetting: {
                    
                    EAHeartRateWaringSettingModel *model = [EAHeartRateWaringSettingModel yy_modelWithJSON:value];
                    [[EABleSendManager defaultManager] changeInfo:model respond:^(EARespondModel * _Nonnull respondModel) {
                        
                        [selfWeak setWatchRespondWithDataType:dataInfoType respondCodeType:respondModel.eErrorCode];
                    }];
                    
                }break;
                case EADataInfoTypeGesturesSetting: {
                    
                    EAGesturesSettingModel *model = [EAGesturesSettingModel yy_modelWithJSON:value];
                    [[EABleSendManager defaultManager] changeInfo:model respond:^(EARespondModel * _Nonnull respondModel) {
                        
                        [selfWeak setWatchRespondWithDataType:dataInfoType respondCodeType:respondModel.eErrorCode];
                    }];
                    
                }break;
                case EADataInfoTypeHomePage: {
                    
                    EAHomePageModel *model = [EAHomePageModel yy_modelWithJSON:value];
                    [[EABleSendManager defaultManager] changeInfo:model respond:^(EARespondModel * _Nonnull respondModel) {
                        
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
                        [[EABleSendManager defaultManager] changeInfo:model respond:^(EARespondModel * _Nonnull respondModel) {
                            
                            [selfWeak setWatchRespondWithDataType:dataInfoType respondCodeType:respondModel.eErrorCode];
                        }];
                    }else {
                        
                        [_channel invokeMethod:kArgumentsError arguments:@"arguments error"];
                    }
                    
                }break;
                case EADataInfoTypeDialPlate: {
                    
                    EADialPlateModel *model = [EADialPlateModel yy_modelWithJSON:value];
                    [[EABleSendManager defaultManager] changeInfo:model respond:^(EARespondModel * _Nonnull respondModel) {
                        
                        [selfWeak setWatchRespondWithDataType:dataInfoType respondCodeType:respondModel.eErrorCode];
                    }];
                    
                }break;
                case EADataInfoTypeAppMessage: {
                    
                    EAAppMessageSwitchData *model = [EAAppMessageSwitchData yy_modelWithJSON:value];
                    [[EABleSendManager defaultManager] changeInfo:model respond:^(EARespondModel * _Nonnull respondModel) {
                        
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
        [[EABleSendManager defaultManager] getBigDataRequestModel:model];
        
    }
    else if ([call.method isEqualToString:kEAOperationWatch]) { // FIXME: - 操作手表
        
        // 判断断连
        if (![EABleManager defaultManager].isConnected) {
            
            [self loseConnect];
        }else {
            
            
            NSDictionary *arguments = [self dictionaryWithJsonString:call.arguments] ;
            // 判断设置类型
            if (![self checkArgumentName:@"type" inArguments:arguments]) {
                
                return;
            }
            EADeviceOps *model = [[EADeviceOps alloc] init];
            model.deviceOpsType = [[arguments valueForKey:@"type"] integerValue];
            model.deviceOpsStatus = EADeviceOpsStatusExecute;
            [[EABleSendManager defaultManager] changeInfo:model respond:^(EARespondModel * _Nonnull respondModel) {
                
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
        if (![self checkArgumentName:@"type" inArguments:arguments]) {
            
            return;
        }
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
        
        [selfWeak setWatchRespondWithDataType:EADataInfoTypeBinding respondCodeType:succ?0:1];
    }];
}


- (void)getWatchInfo:(EADataInfoType )dataInfoType {
    
    WeakSelf
    [[EABleSendManager defaultManager] getInfoByInfoType:(dataInfoType) result:^(EABaseModel * _Nonnull baseModel) {
        
        NSDictionary *value = [baseModel yy_modelToJSONObject];
        NSDictionary *info = @{
            @"dataType":@(dataInfoType),
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
    [self.channel invokeMethod:kSetWatchResponse arguments:[info yy_modelToJSONString]];
}


- (void)otaAction:(NSArray *)otas {
    
    // OTA
    NSMutableArray *models = [NSMutableArray new];
    NSMutableData *otaDatas = [[NSMutableData alloc]init];
    NSInteger i = 1;
    NSMutableDictionary *resDic = [NSMutableDictionary new];
    
    // 查找apollo 放置在最后
    EAOTAModel *apolloModel;
    for (NSDictionary *item in otas) {
        
        NSString *binPath = item[@"binPath"];
        NSInteger otaType = [item[@"firmwareType"] integerValue];
        NSString *version = item[@"version"];

        NSFileManager *manager = [NSFileManager defaultManager];
        NSData *data = [manager contentsAtPath:binPath];
        
        if (data.length == 0) {
            
            continue;
        }
        if (otaType == EAOtaRequestTypeRes||otaType == EAOtaRequestTypeUserWf) {
            
            data = [data subdataWithRange:NSMakeRange(4, data.length - 4 )];
            
        }
        [otaDatas appendData:data];
        
        EAOTAModel *otaModel = [EAOTAModel new];
        otaModel.otaType = otaType;
        otaModel.binPath = binPath;
        otaModel.currentSize = [data length];
        otaModel.crc = [otaModel getCrcValue:(otaType == EAOtaRequestTypeRes||otaType == EAOtaRequestTypeUserWf) andBinPath:binPath];
        otaModel.version = version;
        i ++ ;
        
        if (otaType == EAOtaRequestTypeRes ) {
            
            [resDic setValue:otaModel forKey:[otaModel.version substringFromIndex:1]];
        }else {
            
            if (otaModel.otaType == EAOtaRequestTypeApollo) {
                
                apolloModel = otaModel;
            }
            [models addObject:otaModel];
        }
    }
    
    // 字库排序，小到大
    if (resDic.allKeys.count > 1 ) {
                
        // 排序 时间戳 从小到大
        NSMutableArray *sortKey = [NSMutableArray arrayWithArray:resDic.allKeys];
        [sortKey sortUsingComparator:^NSComparisonResult(id _Nonnull obj1, id _Nonnull obj2) {
            
            //从小到大排列
            if ([obj1 floatValue] > [obj2 floatValue]) {
                
                return NSOrderedDescending;
            } else {
                
                return NSOrderedAscending;
            }
        }];
        
        for (NSString *key in sortKey) {
            
            [models addObject:resDic[key]];
        }
        
    }else {
        
        [models addObjectsFromArray:resDic.allValues];
    }
    
    // apollo 放置在最后
    if (apolloModel) {
        
        [models removeObject:apolloModel];
        [models addObject:apolloModel];
    }
    
    // 配置序号
    i = 1;
    for (EAOTAModel *otaModel in models) {
        
        otaModel.number = i;
        i ++;
    }
    
    if (models.count > 0) {
        
        EAOTA *ota = [EAOTA new];
        ota.otaModels = [NSMutableArray arrayWithArray:models];
        ota.totalSize = [otaDatas length];
        ota.popUpInterface = YES;
        ota.isTestMode = 0;
        [[EABleSendManager defaultManager] upgrade:ota];
        
        _timerSec = kTimerSec;
        [self startTimer];
    }

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
    
    WeakSelf
    dispatch_async(dispatch_get_main_queue(), ^{
        
        [selfWeak.channel invokeMethod:kProgress arguments:@(-1)];
    });
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
        NSLog(@"json解析失败：%@",err);
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
