import 'package:flutter/services.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:easdktool/easdktool.dart';

void main() {
  const MethodChannel channel = MethodChannel('easdktool');

  TestWidgetsFlutterBinding.ensureInitialized();

  setUp(() {
    channel.setMockMethodCallHandler((MethodCall methodCall) async {
      return '42';
    });
  });

  tearDown(() {
    channel.setMockMethodCallHandler(null);
  });

  test('getPlatformVersion', () async {});
}
