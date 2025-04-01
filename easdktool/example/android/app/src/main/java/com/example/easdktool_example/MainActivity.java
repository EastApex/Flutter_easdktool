package com.example.easdktool_example;

import android.Manifest;
import android.bluetooth.BluetoothA2dp;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothHeadset;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import com.apex.ax_bluetooth.utils.LogUtils;
import com.example.easdktool.ConnectAudioUtils;
import com.example.easdktool.EasdktoolPlugin;
import com.example.easdktool.broadcast.BtConnectBroadcast;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import io.flutter.Log;
import io.flutter.embedding.android.FlutterActivity;
import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugins.GeneratedPluginRegistrant;

public class MainActivity extends FlutterActivity {
    final String TAG = this.getClass().getSimpleName();
    BtConnectBroadcast btConnectBroadcast;
    MacBroadcast macBroadcast;
    private static final String BLUETOOTH_CHANNEL = "samples.flutter.io/bluetooth";

    @Override
    public void configureFlutterEngine(@NonNull FlutterEngine flutterEngine) {
        GeneratedPluginRegistrant.registerWith(flutterEngine);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        btConnectBroadcast = new BtConnectBroadcast();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
        intentFilter.addAction(BluetoothA2dp.ACTION_CONNECTION_STATE_CHANGED);
        intentFilter.addAction(BluetoothHeadset.ACTION_CONNECTION_STATE_CHANGED);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            registerReceiver(btConnectBroadcast, intentFilter, Context.RECEIVER_EXPORTED);
        } else {
            registerReceiver(btConnectBroadcast, intentFilter);
        }
        IntentFilter filter1 = new IntentFilter("com.apex.pairBluetooth");
        macBroadcast = new MacBroadcast();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            registerReceiver(macBroadcast, filter1, Context.RECEIVER_EXPORTED);
        } else {
            registerReceiver(macBroadcast, filter1);
        }

        LogUtils.i(TAG, "开始蓝牙广播监听");

    }

    class MacBroadcast extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (!TextUtils.isEmpty(action) && action.equalsIgnoreCase("com.apex.pairBluetooth")) {
                String mac = intent.getStringExtra("mac");
                if (TextUtils.isEmpty(mac)) {
                    return;
                }
                BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                BluetoothDevice bluetoothDevice = bluetoothAdapter.getRemoteDevice(mac.toUpperCase());
                if (bluetoothDevice != null) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    int states = bluetoothDevice.getBondState();
                    if (btConnectBroadcast != null) {
                        btConnectBroadcast.setCurrentBlueAddress(mac.toUpperCase());
                    }
                    LogUtils.i(TAG, "开始连接BT:" + states);
                    if (states == BluetoothDevice.BOND_NONE) {

                        //配对

                        if (!TextUtils.isEmpty(Build.PRODUCT) && (Build.PRODUCT.equalsIgnoreCase("sagit") || Build.PRODUCT.equalsIgnoreCase("starqltezc") || Build.PRODUCT.equalsIgnoreCase("MHA-AL00") || Build.PRODUCT.equalsIgnoreCase("cuscoi_g"))) {
                            LogUtils.e(TAG, "走直连配对");
                            bluetoothDevice.createBond();

                        } else {
                            try {
                                Log.e(TAG, "非直连配对");
                                Method bondMethod = BluetoothDevice.class.getDeclaredMethod("createBond", int.class);
                                bondMethod.setAccessible(true);
                                bondMethod.invoke(bluetoothDevice, -1);

                            } catch (NoSuchMethodException e) {
                                LogUtils.e(TAG, "找不到反射的方法");
                            } catch (InvocationTargetException e) {
                                LogUtils.e(TAG, "反射时的错误:" + e.getMessage());

                            } catch (IllegalAccessException e) {
                                LogUtils.e(TAG, "反射时非法错误:" + e.getMessage());
                            }

                        }
                    } else if (states == BluetoothDevice.BOND_BONDING) {
                        LogUtils.i(TAG, "正在配对");
                    } else if (states == BluetoothDevice.BOND_BONDED) {
                        LogUtils.i(TAG, "已经配对");
                        new ConnectAudioUtils().connectAudio(MainActivity.this, bluetoothDevice);
                    }
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (btConnectBroadcast != null) {
            unregisterReceiver(btConnectBroadcast);
            btConnectBroadcast = null;
        }
        if (macBroadcast != null) {
            unregisterReceiver(macBroadcast);
            macBroadcast = null;
        }

    }

}
