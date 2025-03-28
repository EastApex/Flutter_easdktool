package com.example.easdktool_example;

import android.bluetooth.BluetoothA2dp;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothHeadset;
import android.content.Context;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.apex.ax_bluetooth.utils.LogUtils;
import com.example.easdktool.EasdktoolPlugin;
import com.example.easdktool.broadcast.BtConnectBroadcast;

import io.flutter.embedding.android.FlutterActivity;
import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.plugins.GeneratedPluginRegistrant;

public class MainActivity extends FlutterActivity {
    final String TAG = this.getClass().getSimpleName();
  //  BtConnectBroadcast btConnectBroadcast;

    @Override
    public void configureFlutterEngine(@NonNull FlutterEngine flutterEngine) {
        GeneratedPluginRegistrant.registerWith(flutterEngine);
    }
/**
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
        LogUtils.i(TAG, "开始蓝牙广播监听");
    }

    @Override
    protected void onDestroy() {
        if (btConnectBroadcast != null ) {
            unregisterReceiver(btConnectBroadcast);
            btConnectBroadcast = null;
        }
        super.onDestroy();
    }
    */
}
