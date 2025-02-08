package com.example.easdktool_example;

import android.annotation.SuppressLint;
import android.app.Service;
import android.bluetooth.BluetoothA2dp;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothHeadset;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.Nullable;

import com.apex.ax_bluetooth.utils.LogUtils;
import com.example.easdktool.ConnectAudioUtils;
import com.example.easdktool.broadcast.BtConnectBroadcast;
import com.example.easdktool.service.Channel;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.Provider;

import io.flutter.plugin.common.EventChannel;

public class PairService extends Service {
    final String TAG = this.getClass().getSimpleName();
    BtConnectBroadcast btConnectBroadcast;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (btConnectBroadcast == null) {
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


        }


    }

    @SuppressLint("MissingPermission")
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LogUtils.e("startForegroundService", "开启前台服务");
            startForeground(100, Channel.getInstance().createNotification(this));
        }
        String btAddress = intent.getStringExtra("btAddress");
        if (!TextUtils.isEmpty(btAddress)) {
            BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            BluetoothDevice bluetoothDevice = bluetoothAdapter.getRemoteDevice(btAddress);
            if (bluetoothDevice != null) {
                int states = bluetoothDevice.getBondState();
                if (btConnectBroadcast != null) {
                    btConnectBroadcast.setCurrentBlueAddress(btAddress);
                }
                LogUtils.i(TAG, "开始连接BT");
                if (states == BluetoothDevice.BOND_NONE) {

                    //配对
                    Log.i(TAG, "品牌:" + Build.PRODUCT);

                    if (!TextUtils.isEmpty(Build.PRODUCT) && (Build.PRODUCT.equalsIgnoreCase("sagit") || Build.PRODUCT.equalsIgnoreCase("starqltezc") || Build.PRODUCT.equalsIgnoreCase("MHA-AL00")||Build.PRODUCT.equalsIgnoreCase("cuscoi_g"))) {
                        LogUtils.e(TAG, "走直连配对");
                        bluetoothDevice.createBond();

                    } else {
                        LogUtils.e(TAG, "走反射配对");
                        try {
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
                    new ConnectAudioUtils().connectAudio(this, bluetoothDevice);
                }
            }
        }

        return Service.START_STICKY;
    }

    @Override
    public void onDestroy() {
        if (btConnectBroadcast != null) {
            unregisterReceiver(btConnectBroadcast);
            btConnectBroadcast = null;
        }
        super.onDestroy();
    }
}
