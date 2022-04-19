# Flutter_easdktool
EASDKTOOL

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
            => otaProgressCallback: callback of progress status (-1: data transfer failed, 100: data transfer completed, 0~99: data transfer completion)
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
    
    
