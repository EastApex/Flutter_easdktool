import 'package:easdktool/Been/EABeen.dart';
import 'package:easdktool/EACallback.dart';
import 'package:flutter/material.dart';
import 'package:easdktool/easdktool.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Devices',
      theme: ThemeData(
        primarySwatch: Colors.blue,
        visualDensity: VisualDensity.adaptivePlatformDensity,
      ),
      home: const DeviceListScreen(),
    );
  }
}

class DeviceListScreen extends StatefulWidget {
  const DeviceListScreen({super.key});

  @override
  _DeviceListScreenState createState() => _DeviceListScreenState();
}

class _DeviceListScreenState extends State<DeviceListScreen> {
  // 存储字符串的列表
  final List<EAConnectParam> _deviceList = [];
  EASDKTool easdkTool = EASDKTool();

  @override
  void initState() {
    super.initState();

    easdkTool.initChannel();

    /// 【添加监听】
    EASDKTool.addBleConnectListener(ConnectListener(easdkTool));

    // /// 打开 SDKLog
    easdkTool.showLog(1);

    ///搜索手表
    easdkTool.scanWatch(
      EAScanWatchCallback((connectParam) {
        print("【Demo】:Name:${connectParam.name} Sign:${connectParam.snNumber}");

        // 检查设备是否已在列表中
        bool isAlreadyAdded = _deviceList.any(
          (d) => d.snNumber == connectParam.snNumber,
        );
        if (!isAlreadyAdded) {
          setState(() {
            _deviceList.insert(0, connectParam);
          });
        }

        if (_deviceList.length > 30) {
          easdkTool.stopWatch();
        }
      }),
    );
  }

  // 列表项点击事件处理
  void _onItemTapped(EAConnectParam connectParam) {
    showDialog(
      context: context,
      builder: (context) => AlertDialog(
        title: const Text('Connect Device'),
        content: Text(
          "Name:${connectParam.name} Sign:${connectParam.snNumber}",
        ),
        actions: [
          TextButton(
            onPressed: () => Navigator.pop(context),
            child: const Text('Cancel'),
          ),
          // 保存按钮
          TextButton(
            onPressed: () {
              _connectDevice(connectParam);
              Navigator.pop(context);
            },
            child: const Text('Connect'),
          ),
        ],
      ),
    );
  }

  void _connectDevice(EAConnectParam connectParam) {
    easdkTool.connectToPeripheral(connectParam);
  }

  @override
  void dispose() {
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text('Devices')),
      body: ListView.builder(
        padding: const EdgeInsets.all(16.0),
        // 列表项数量
        itemCount: _deviceList.length,
        // 构建每个列表项
        itemBuilder: (context, index) {
          final connectParam = _deviceList[index];
          return Card(
            elevation: 2,
            margin: const EdgeInsets.symmetric(vertical: 8.0),
            child: ListTile(
              title: Text(
                "Name:${connectParam.name} Sign:${connectParam.snNumber}",
              ),
              leading: const Icon(Icons.access_time, color: Colors.blue),
              trailing: const Icon(Icons.arrow_forward_ios, size: 16),
              // 添加点击事件
              onTap: () => _onItemTapped(connectParam),
            ),
          );
        },
      ),
    );
  }
}

class ConnectListener implements EABleConnectListener {
  EASDKTool easdkTool;

  ConnectListener(this.easdkTool);

  @override
  void connectError() {
    print('【Demo】: connection Listener - connect Error');
  }

  @override
  void connectTimeOut() {
    print('【Demo】: connection Listener - connect Timeout');
  }

  @override
  void deviceConnected() {
    /// 绑定手表
    print('【Demo】: connection Listener - connected');
    easdkTool.getWatchData(
      kEADataInfoTypeWatch,
      EAGetDataCallback(
        onSuccess: ((info) async {
          Map<String, dynamic> value = info["value"];
          EABleWatchInfo eaBleWatchInfo = EABleWatchInfo.fromMap(value);
          print('【Demo】 $value');

          if (eaBleWatchInfo.bindingType == EABindingType.unbound) {
            EABindInfo bindInfo = EABindInfo();
            bindInfo.bindMod = 0;
            bindInfo.bindingCommandType = 1;
            easdkTool.bindingWatch(
              bindInfo,
              EABindingWatchCallback(
                onRespond: ((respond) {
                  print('【Demo】binding response  ${respond.respondCodeType}');
                }),
              ),
            );
          } else {}
        }),
        onFail: ((info) {}),
      ),
    );
  }

  @override
  void deviceDisconnect() {
    print('【Demo】: connection Listener - device disconnect');
    // XWatch.xWatchConnectionListener?.deviceDisconnected();
  }

  @override
  void deviceNotFind() {
    print('XWatch Package: connection Listener - device not find');
    // XWatch.xWatchConnectionListener?.deviceNotFound();
  }

  @override
  void notOpenLocation() {
    print('XWatch Package: connection Listener - not open location');
  }

  @override
  void paramError() {
    print('XWatch Package: connection Listener - Param error');
    // XWatch.xWatchConnectionListener?.deviceConnectionError();
  }

  @override
  void unopenedBluetooth() {
    print('XWatch Package: connection Listener - unopened Bluetooth');
  }

  @override
  void unsupportedBLE() {
    print('XWatch Package: connection Listener - unsupported BLE');
  }

  @override
  void iOSRelievePair() {
    print('XWatch Package: connection Listener - iOS Relieve Pair');
  }

  @override
  void iOSUnAuthorized() {
    print('XWatch Package: connection Listener - iOS Unauthorized');
  }
}
