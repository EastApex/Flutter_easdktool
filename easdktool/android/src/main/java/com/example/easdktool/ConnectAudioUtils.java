package com.example.easdktool;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothA2dp;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothHeadset;
import android.bluetooth.BluetoothProfile;
import android.content.Context;

import com.apex.bluetooth.utils.LogUtils;

import java.lang.reflect.Method;

public class ConnectAudioUtils {
    final String TAG = this.getClass().getSimpleName();

    @SuppressLint("MissingPermission")
    public void connectAudio(Context context, final BluetoothDevice bluetoothDevice) {
        int states = BluetoothAdapter.getDefaultAdapter().getProfileConnectionState(BluetoothProfile.HEADSET);
        if (states == BluetoothAdapter.STATE_DISCONNECTED) {
            BluetoothAdapter.getDefaultAdapter().getProfileProxy(context, new BluetoothProfile.ServiceListener() {
                @Override
                public void onServiceConnected(int profile, BluetoothProfile proxy) {
                    if (BluetoothProfile.HEADSET == profile) {
                        BluetoothHeadset bluetoothHeadset = (BluetoothHeadset) proxy;
                        try {
                            LogUtils.e(TAG, "connect headset");
                            Method method = BluetoothHeadset.class.getMethod("connect", new Class[]{BluetoothDevice.class});
                            method.setAccessible(true);
                            boolean result = (boolean) method.invoke(bluetoothHeadset, bluetoothDevice);
                            LogUtils.i(TAG, "bluetoothHeadset连接结果:" + result);
                        } catch (Exception e) {
                            LogUtils.e(TAG, "headset:" + e.getMessage());
                        }
                    }


                }

                @Override
                public void onServiceDisconnected(int profile) {

                }
            }, BluetoothProfile.HEADSET);
        }
        int a2dpStates = BluetoothAdapter.getDefaultAdapter().getProfileConnectionState(BluetoothProfile.A2DP);
        if (a2dpStates == BluetoothAdapter.STATE_DISCONNECTED) {
            BluetoothAdapter.getDefaultAdapter().getProfileProxy(context, new BluetoothProfile.ServiceListener() {
                @Override
                public void onServiceConnected(int profile, BluetoothProfile proxy) {
                    if (BluetoothProfile.A2DP == profile) {
                        BluetoothA2dp bluetoothA2dp = (BluetoothA2dp) proxy;
                        try {
                            LogUtils.e(TAG, "connect A2DP");
                            Method method = BluetoothA2dp.class.getMethod("connect", new Class[]{BluetoothDevice.class});
                            method.setAccessible(true);
                            method.invoke(bluetoothA2dp, bluetoothDevice);
                        } catch (Exception e) {
                            LogUtils.e(TAG, "A2DP:" + e.getMessage());
                        }
                    }


                }

                @Override
                public void onServiceDisconnected(int profile) {

                }
            }, BluetoothProfile.A2DP);
        }
    }
}
