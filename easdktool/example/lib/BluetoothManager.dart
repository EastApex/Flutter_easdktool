import 'package:easdktool/easdktool.dart';

class BluetoothManager {
  BluetoothManager._privateConstructor();

  EASDKTool secondEasdkTool = EASDKTool();

  static final BluetoothManager _instance =
      BluetoothManager._privateConstructor();

  factory BluetoothManager() {
    return _instance;
  }
}
