package com.example.easdktool.broadcast;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothA2dp;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothHeadset;
import android.bluetooth.BluetoothProfile;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import com.apex.ax_bluetooth.utils.LogUtils;
import com.example.easdktool.ConnectAudioUtils;

import java.lang.reflect.Method;

public class BtConnectBroadcast extends BroadcastReceiver {
    final String TAG = getClass().getSimpleName();
    String currentBlueAddress;

    public void setCurrentBlueAddress(String currentBlueAddress) {
        this.currentBlueAddress = currentBlueAddress;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (!TextUtils.isEmpty(action)) {
            if (action.equalsIgnoreCase(BluetoothDevice.ACTION_BOND_STATE_CHANGED)) {
                int state = intent.getIntExtra(BluetoothDevice.EXTRA_BOND_STATE, -1);
                final BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                if (device != null && device.getAddress().equalsIgnoreCase(currentBlueAddress)) {
                    LogUtils.e(TAG, "蓝牙地址:" + device.getAddress());
                }

                switch (state) {
                    case BluetoothDevice.BOND_NONE:
                        if (device != null && device.getAddress().equalsIgnoreCase(currentBlueAddress)) {
                            LogUtils.e(TAG, "BOND_NONE 删除配对");

                        }

                        break;
                    case BluetoothDevice.BOND_BONDING:
                        if (device != null && device.getAddress().equalsIgnoreCase(currentBlueAddress)) {
                            LogUtils.e(TAG, "BOND_BONDING 正在配对");

                        }

                        break;
                    case BluetoothDevice.BOND_BONDED:
                        //判断是否为魅族pro6
                        if (device != null && device.getAddress().equalsIgnoreCase(currentBlueAddress)) {
                            /**
                             if (!TextUtils.isEmpty(Build.PRODUCT) && (Build.PRODUCT.equalsIgnoreCase("sagit") || Build.PRODUCT.equalsIgnoreCase("walleye") || Build.PRODUCT.equalsIgnoreCase("starqltezc"))) {
                             return;
                             }
                             */
                            new ConnectAudioUtils().connectAudio(context, device);

                        }
                        break;
                }
            } else if (action.equalsIgnoreCase(BluetoothA2dp.ACTION_CONNECTION_STATE_CHANGED)) {
                int connectState = intent.getIntExtra(BluetoothA2dp.EXTRA_STATE, BluetoothA2dp.STATE_DISCONNECTED);
                Log.e(TAG, "接收到A2DP连接状态改变广播:" + connectState);
                @SuppressLint("MissingPermission") int states = BluetoothAdapter.getDefaultAdapter().getProfileConnectionState(BluetoothProfile.A2DP);
                if (states == BluetoothAdapter.STATE_CONNECTED) {
                    LogUtils.i(TAG, "A2DP音频已连接");
                } else if (states == BluetoothAdapter.STATE_CONNECTING) {
                    LogUtils.i(TAG, "A2DP音频正在连接");
                } else {
                    LogUtils.i(TAG, "A2DP音频未连接");
                }
            } else if (action.equalsIgnoreCase(BluetoothHeadset.ACTION_CONNECTION_STATE_CHANGED)) {
                int connectState = intent.getIntExtra(BluetoothHeadset.EXTRA_STATE, BluetoothHeadset.STATE_DISCONNECTED);
                Log.e(TAG, "接收到Headset连接状态改变广播:" + connectState);
                @SuppressLint("MissingPermission") int states = BluetoothAdapter.getDefaultAdapter().getProfileConnectionState(BluetoothProfile.HEADSET);
                if (states == BluetoothAdapter.STATE_CONNECTED) {
                    LogUtils.i(TAG, "HEADSET音频已连接");
                } else if (states == BluetoothAdapter.STATE_CONNECTING) {
                    LogUtils.i(TAG, "HEADSET音频正在连接");
                } else {
                    LogUtils.i(TAG, "HEADSET音频未连接");
                }
            }
        }
    }


}
