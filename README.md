# Flutter_easdktool
EASDKTOOL


## Normal scanning can be started only when Bluetooth is enabled and the location permission is allowed. The scanning is time-consuming. The developer can set the scanning time through the timer. The recommended scanning duration is 8-10 seconds. Do not scan frequently. You can scan only five times within 30 seconds. Otherwise, the android system will prompt you that the device cannot be scanned "is scanning too frequently"


### 1. Add this SDK dependency in the project's pubspec.yaml.
```
  easdktool:
    git:
      url: https://github.com/EastApex/Flutter_easdktool.git
      path: easdktool
```
### 2. Use
```
import 'package:easdktool/easdktool.dart';
```
### 3. Main method
        
    Singleton:
        EASDKTool()
    
    SDK log shows:
        EASDKTool().showLog(bool isShow)
            => isShow:true show sdklog, false not show
    
    Connect the watch:
        EASDKTool().connectToPeripheral(EAConnectParam connectParam);
            => connectParam
                    => connectAddress : Android needs a mac address to connect the watch
                    => snNumber : iOS needs the SN number of the watch to connect to the watch
            For details, please see class EAConnectParam;
            
    Bind the watch: (note: after the connection is successful, you need to bind the watch, the watch can enter the watch main interface)
        EASDKTool().bindingWatch(EABindInfo bindInfo)
            => bindInfo
                    => user_id: the owner of the watch
             
    Unbinding the watch: (note: after unbinding, the data of the watch will be cleared, and the watch will display the QR code interface)
        EASDKTool().unbindWatch()
        
    Actively disconnect the watch: (note: disconnected, the watch is disconnected from the phone, and the data is not synchronized)
        EASDKTool().disConnectWatch()
        
    Get watch information
        EASDKTool().getWatchData(int dataType, EAGetDataCallback getDataCallback)
            => dataType: The type of watch data obtained: For details, please check the [EADataInfoType.dart] file
            => getDataCallback: callback for watch data.
            
    Set watch information
        EASDKTool().setWatchData(int dataType, Map value, EASetDataCallback setDataCallback)
            => dataType: Set the type of watch data (same as the type to get watch data)
            => setDataCallback: Set the callback of watch information (success or failure or other reasons).
            
    Get all watch big data (daily steps, heart rate, exercise records, etc.)
        EASDKTool().getBigWatchData(EAGetBitDataCallback getBitDataCallback)
            => getBitDataCallback: callback for getting big data. Distinguish different types of data according to dataType
        note:
            There are some fields that iOS and Android return differently, so be careful
        
    Operate the watch
        EASDKTool().operationWatch(EAOperationWatchType operationType,OperationWatchCallback operationCallback)
            => operationType: The type of watch operation, please see EAOperationWatchType in the [EAEnum.dart] file for details;
            => operationCallback: callback for operating the watch (successful failure or other reasons)
    
    Firmware upgrade
        EASDKTool().otaUpgrade(EAOTAList otaList, EAOTAProgressCallback otaProgressCallback)
            => otaList:
                => type: The default is 0
                => otas: array List<EAOTA>
                    => EAOTA
                        => binPath: local path to firmware
                        => firmwareType: firmware type (Apollo, Res, Tp, Hr) For details, please check the EAFirmwareType of the [EAEnum.dart] file;
                        => version: the version number of the firmware
            => otaProgressCallback: callback of progress status (-1: data transfer failed, 100: data transfer completed, 0~99: data transfer completion )
        note:
            1. Must be newer than the current version to upgrade successfully,
            2.version must be in a certain format (the following xx represents a value)
                Apollo Apollo: APxxBxx
                Font Res: Rxx
                Screen Tp: Txx
                Heart rate Hr: Hxx
            3.【firmwareType】: firmware type, 0Apollo 2Res 3Tp 4Hr
            4. The font library Res is an iterative upgrade: if the current watch font version is R0.1, and there are new font versions R0.2 and R0.3, then both R0.2 and R0.3 need to be passed to the SDK.
            5. Other firmware types are update and upgrade: if the current Apollo version of the watch is AP0.1B0.2, and there are new Apollo versions AP0.1B0.2 and AP0.1B0.3, then only the highest one needs to be uploaded. The firmware and version number corresponding to the version number can be given to the SDK.

## Big data response parameter parsing

### Big data types
```
    3001:step
    3002:sleep
    3003:head rate
    3004:GPS
    3005:multi-motion data
    3006:blood oxygen
    3007:stress
    3008:step frequency
    3009:step pace
    3010:resting heart rate
```

### Daily steps
```
/** timestamp */
int timeStamp;

/** Movement data: number of steps */
int steps;

/** Exercise data: Calories (in calories) */
int calorie;

/** Movement data: distance (unit: cm) */
int distance;

/** Exercise data: exercise duration (unit: second) */
int duration;

/** Exercise data: average heart rate */
int averageHeartRate;
```
### Sleep
```
/** timestamp */
int timeStamp;

/** Sleep type 0: active state 1: entering sleep 2: waking up in the middle of sleep 3: RAPID eye movement 4: light sleep 5: deep sleep 6: exiting sleep 7: Unknown 8: Sleep summary */
int eSleepNode;
```
### Heart rate || Resting heart rate
```
/** timestamp */
int timeStamp;

/** Heart rate */
int hrValue;

```
### GPS data
```
/** timestamp */
int timeStamp;

/** Latitude */
float latitude;

/** longitude */ 
float longitude;
```
### Multi-motion data
```
/** Sport type 1: outdoor walking 2: outdoor running 3: outdoor hiking 4: outdoor mountaineering 5: outdoor trail running 6: outdoor cycling 7: outdoor swimming 8: indoor walking 9: indoor running 10: indoor exercise 11: indoor cycling 12: elliptical machine 13: Yoga 14: rowing machine 15: indoor swimming */
int eType;

/** Start timestamp */
int beginTimeStamp;

/** Stop timestamp */
int endTimeStamp;

Steps / * * * /
int steps;

/** Calories (in calories) */
int calorie;

/** Distance (unit: cm) */
int distance;

/** Exercise duration (unit: second) */
int duration;

/** Training effect Normal heart rate duration (unit: second) */
int trainingEffectNormal;

/** Training effect Warm-up heart rate duration (unit: second) */
int trainingEffectWarmUp;

/** Training effect Fat consumption duration (unit: second) */
int trainingEffectFatconsumption;

/** Training effect Aerobic heart rate duration (unit: second) */
int trainingEffectAerobic;

/** Training effect Anaerobic heart rate duration (unit: second) */
int trainingEffectAnaerobic;

/** Duration of training effect limit heart rate (unit: second) */
int trainingEffectLimit;

/** Average heart rate */
int averageHeartRate;

/** Average body temperature (unit: Celsius) */
float averageTemperature;

/** Average speed (unit: KM/H *100 times) */
float averageSpeed;

/** Average pace (unit: S/KM) */
float averagePace;

/** Average step frequency (SPM steps per minute) */
float averageStepFreq;

/** Average step size (unit: cm) */
float averageStride;

/** Average altitude (in cm) */
float averageAltitude;

/** Maximum heart rate */
int averageHeartRateMax;

/** Minimum heart rate */
int averageHeartRateMin;
```
### blood oxygen data
```
/** timestamp */
int timeStamp;

/** Blood oxygen */
int bloodOxygenValue;
```
### Stree data
```
/** timestamp */
int timeStamp;

/ * * * / pressure
int stessValue;

/** Pressure type 0: unknown 1: relaxed 2: normal 3: medium 4: high */
int eType;

```
### Step frequency
```
/** timestamp */
int timeStamp;

/** step frequency */
int stepFreqValue;
```
### Step pace 
```
/** timestamp */
int timeStamp;

/** speed value */
int stepPaceValue;
```


## Note：在蓝牙开启且允许定位权限下，才能开始正常扫描。扫描是耗时的，开发者可以 通过定时器设定扫描时间，建议扫描时长 8-10 秒。不要频繁扫描，30S 内最多只 能扫描 5 次，否则扫描不到设备，安卓系统会提示 is scanning too frequently。

### 1.在项目的 pubspec.yaml 添加本SDK依赖。
```
  easdktool:
    git:
      url: https://github.com/EastApex/Flutter_easdktool.git
      path: easdktool
```
### 2.使用
```
import 'package:easdktool/easdktool.dart';
```
### 3.主要方法
        
    单例：
    
        EASDKTool()
    
    SDK log显示：
        EASDKTool().showLog(bool isShow)
            => isShow:true 显示sdklog，false 不显示
    
    连接手表：
        EASDKTool().connectToPeripheral(EAConnectParam connectParam);
            => connectParam
                 =>  connectAddress ：Android 需要 mac地址才能连接手表
                 =>  snNumber ：iOS 需要 手表的SN号 来连接手表
            详细请查看 class EAConnectParam；
            
    绑定手表：（note:连接成功后需要绑定手表，手表才能进入手表主界面)
        EASDKTool().bindingWatch(EABindInfo bindInfo)
            => bindInfo
                 => user_id:手表的主人
             
    解绑手表：（note:解绑后，手表的数据将会被清空，手表会显示二维码界面）
        EASDKTool().unbindWatch()
        
    主动断连手表：（note:断连，手表和手机断开连接，不同步数据）
        EASDKTool().disConnectWatch() 
        
    获取手表信息
        EASDKTool().getWatchData(int dataType, EAGetDataCallback getDataCallback) 
            => dataType:获取手表数据的类型：详细请查看 【EADataInfoType.dart】 文件
            => getDataCallback:手表数据的回调。
            
    设置手表信息
        EASDKTool().setWatchData(int dataType, Map value, EASetDataCallback setDataCallback)
            => dataType:设置手表数据的类型（同获取手表数据的类型）
            => setDataCallback:设置手表信息的回调（成功失败或其他原因）。
            
    获取所有手表大数据（日常步数、心率、运动记录等）
        EASDKTool().getBigWatchData(EAGetBitDataCallback getBitDataCallback)
            => getBitDataCallback:获取大数据的回调。根据 dataType 来区分 不同类型的数据 
        note：有一些类的字段 iOS和安卓返回的不一样，需要注意下
        
    操作手表
        EASDKTool().operationWatch(EAOperationWatchType operationType,OperationWatchCallback operationCallback)
            => operationType:操作手表的类型，详细请查看 【EAEnum.dart】文件的 EAOperationWatchType；
            => operationCallback:操作手表的回调（成功失败或其他原因）
    
    固件升级
        EASDKTool().otaUpgrade(EAOTAList otaList, EAOTAProgressCallback otaProgressCallback)
            => otaList:
                => type: 默认为0即可
                => otas: 数组 List<EAOTA>
                    => EAOTA
                        => binPath:固件的本地路径
                        => firmwareType:固件类型 （Apollo, Res, Tp, Hr） 详细请查看 【EAEnum.dart】文件的 EAFirmwareType；
                        => version: 固件的版本号
            => otaProgressCallback: 进度情况的回调（-1：数据传输失败，100：数据传输完成，0~99：数据传输完成度）
        note:
            1.必须要比当前版本大才能升级成功，
            2.version 必须按照 一定的格式来 (以下 xx 代表为数值)
                阿波罗  Apollo: APxxBxx
                字库    Res: Rxx
                屏幕    Tp: Txx
                心率    Hr: Hxx
            3.【firmwareType】：固件类型，0Apollo 2Res 3Tp 4Hr
            4. 字库 Res 为迭代升级：如当前手表的字库version为R0.1时，此时有新的字库版本R0.2、R0.3，那么都需要传 R0.2、R0.3 给SDK。
            5. 其他固件类型为更新升级：如当前手表的阿波罗version为AP0.1B0.2时，此时有新的阿波罗版本AP0.1B0.2、AP0.1B0.3，那么只需要传最高的版本号对应的固件和版本号 给SDK即可。
    
    

OTA Eg:
```
                  String uslString = "http://47.119.196.148/admin/1648451351478001001_AP0.1B4.6.bin";

                  final Directory appDirectory = await getTemporaryDirectory();
                  String savePath = appDirectory.path + '/file.bin';

                  Dio dio = Dio();
                  dio.options.connectTimeout = 10000; //设置连接超时时间
                  dio.options.receiveTimeout = 10000; //设置数据接收超时时间
                  Response response;

                  try {
                    response = await dio.download(uslString, savePath);
                    if (response.statusCode == 200) {
                      //下载文件成功
                      final file = File(savePath);
                      Uint8List content = await file.readAsBytes();
                      print('length ${content.length}');

                      EAOTA ota1 =
                          EAOTA(savePath, EAFirmwareType.Apollo, "AP0.1B4.6");

                      EAOTAList otaList = EAOTAList(0, [ota1]);
                      //(info) {})
                      EASDKTool().otaUpgrade(otaList,
                          EAOTAProgressCallback((progress) {
                        if (progress == -1) {
                          // transmit data fail;

                        } else if (progress == 100) {
                          // transmit data succ;
                        } else {
                          // transmit data progress

                        }
                      }));
                    } else {
                      throw Exception('接口出错');
                    }
                  } catch (e) {
                    throw Exception('下载文件失败');
                  }
```

## 大数据回应参数解析

### 大数据类型
```
    3001:大数据步数
    3002:大数据睡眠
    3003:大数据心率
    3004:大数据GPS
    3005:大数据多运动
    3006:大数据血氧
    3007:大数据压力
    3008:大数据步频
    3009:大数据配速
    3010:大数据静息心率
```

### 日常步数
```
/** 时间戳 */
int timeStamp;

/** 运动数据：步数 */
int steps;

/** 运动数据：卡路里（单位:小卡) */
int calorie;

/** 运动数据：距离 （单位:厘米） */
int distance;

/** 运动数据：运动时长(单位:秒) */
int duration;

/** 运动数据：平均心率 */
int averageHeartRate;
```
### 睡眠
```
/** 时间戳 */
int timeStamp;

/** 睡眠类型  0:活动状态 1:进入睡眠 2:睡眠中途醒来 3:快速眼动 4:浅睡 5:深睡 6:退出睡眠 7:未知 8:睡眠摘要*/
 int eSleepNode;
```
### 心率 || 静息心率
```
/** 时间戳 */
int timeStamp;

/** 心率值 */
int hrValue;

```
### GPS数据
```
/** 时间戳 */
int timeStamp;

/** 纬度 */
 float latitude;

/** 经度 */
 float longitude;
```
### 多运动数据
```
/** 运动类型 1:户外步行 2:户外跑步 3:户外徒步 4:户外登山 5:户外越野跑 6:户外单车 7:户外游泳 8:室内步行 9:室内跑步 10:室内锻炼 11:室内单车 12:椭圆机 13:瑜伽 14:划船机 15:室内游泳*/
int eType;

/** 起始时间戳 */
int beginTimeStamp;

/** 停止时间戳 */
int endTimeStamp;

/** 步数 */
int steps;

/** 卡路里（单位:小卡) */
int calorie;

/** 距离 （单位:厘米） */
int distance;

/** 运动时长(单位:秒) */
int duration;

/** 训练效果 正常心率 时长(单位:秒) */
int trainingEffectNormal;

/** 训练效果 热身心率 时长(单位:秒) */
int trainingEffectWarmUp;

/** 训练效果 消耗脂肪 时长(单位:秒) */
int trainingEffectFatconsumption;

/** 训练效果 有氧心率 时长(单位:秒) */
int trainingEffectAerobic;

/** 训练效果 无氧心率 时长(单位:秒) */
int trainingEffectAnaerobic;

/** 训练效果 极限心率 时长(单位:秒) */
int trainingEffectLimit;

/** 平均心率 */
int averageHeartRate;

/** 平均体温（单位：摄氏度） */
 float averageTemperature;

/** 平均速度（单位: KM/H *100倍） */
 float averageSpeed;

/** 平均配速（单位: S/KM） */
 float averagePace;

/** 平均步频（单位: SPM 步每分钟） */
 float averageStepFreq;

/** 平均步距（单位:厘米） */
 float averageStride;

/** 平均海拔（单位:厘米） */
 float averageAltitude;

/** 最大心率 */
int averageHeartRateMax;

/** 最小心率 */
int averageHeartRateMin;
```
### 血氧数据
```
/** 时间戳 */
int timeStamp;

/** 血氧值 */
int bloodOxygenValue;
```
### 压力数据
```
/** 时间戳 */
int timeStamp;

/** 压力 */
int stessValue;

/** 压力类型 0:未知 1:放松 2:正常 3:中等 4:高*/
int eType;

```
### 步频数据
```
/** 时间戳 */
int timeStamp;

/** 步频值 */
int stepFreqValue;
```
### 配速数据
```
/** 时间戳 */
int timeStamp;

/** 配速值 */
int stepPaceValue;
```

