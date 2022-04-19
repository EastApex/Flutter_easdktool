# Flutter_easdktool
EASDKTOOL

1.在项目的 pubspec.yaml 添加本SDK依赖。

  easdktool:
    git:
      url: https://github.com/EastApex/Flutter_easdktool.git
      path: easdktool

2.使用
import 'package:easdktool/easdktool.dart';

3.主要方法
        
    单例：
        Easdktool()
    
    SDK log显示：
        Easdktool().showLog(bool isShow)
            => isShow:true 显示sdklog，false 不显示
    
    连接手表：
        Easdktool().connectToPeripheral(EAConnectParam connectParam);
            => connectParam
                    =>  connectAddress ：Android 需要 mac地址才能连接手表
                    =>  snNumber ：iOS 需要 手表的SN号 来连接手表
            详细请查看 class EAConnectParam；
            
    绑定手表：（note:连接成功后需要绑定手表，手表才能进入手表主界面)
        Easdktool().bindingWatch(EABindInfo bindInfo)
            => bindInfo
                    => user_id:手表的主人
             
    解绑手表：（note:解绑后，手表的数据将会被清空，手表会显示二维码界面）
        Easdktool().unbindWatch()
        
    主动断连手表：（note:断连，手表和手机断开连接，不同步数据）
        Easdktool().disConnectWatch() 
        
    获取手表信息
        Easdktool().getWatchData(int dataType, EAGetDataCallback getDataCallback) 
            => dataType:获取手表数据的类型：详细请查看 【EADataInfoType.dart】 文件
            => getDataCallback:手表数据的回调。
            
    设置手表信息
        Easdktool().setWatchData(int dataType, Map value, EASetDataCallback setDataCallback)
            => dataType:设置手表数据的类型（同获取手表数据的类型）
            => setDataCallback:设置手表信息的回调（成功失败或其他原因）。
            
    获取所有手表大数据（日常步数、心率、运动记录等）
        Easdktool().getBigWatchData(EAGetBitDataCallback getBitDataCallback)
            => getBitDataCallback:获取大数据的回调。根据 dataType 来区分 不同类型的数据 
            
    操作手表
        Easdktool().operationWatch(EAOperationWatchType operationType,OperationWatchCallback operationCallback)
            => operationType:操作手表的类型，详细请查看 【EAEnum.dart】文件的 EAOperationWatchType；
            => operationCallback:操作手表的回调（成功失败或其他原因）
    
    固件升级
        Easdktool().otaUpgrade(EAOTAList otaList, EAOTAProgressCallback otaProgressCallback)
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
    
    
