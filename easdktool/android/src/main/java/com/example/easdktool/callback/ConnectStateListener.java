package com.example.easdktool.callback;

import android.os.Handler;
import android.os.Looper;

import com.apex.ax_bluetooth.callback.BleConnectStatusListener;
import com.example.easdktool.enumerate.BluetoothState;
import com.example.easdktool.enumerate.ConnectState;

import io.flutter.plugin.common.MethodChannel;

public class ConnectStateListener implements BleConnectStatusListener {
    private MethodChannel channel;
    final String kConnectState = "ConnectState";
    final String kBluetoothState = "BluetoothState";

    public ConnectStateListener(MethodChannel channel) {
        this.channel = channel;
    }

    @Override
    public void deviceConnected() {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                if (channel != null) {
                    channel.invokeMethod(kConnectState, ConnectState.succ.getValue());
                }
            }
        });
    }


    @Override
    public void deviceDisconnect() {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                if (channel != null) {
                    channel.invokeMethod(kConnectState, ConnectState.disConnect.getValue());
                }
            }
        });
    }

    @Override
    public void deviceNotFind() {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                if (channel != null) {
                    channel.invokeMethod(kConnectState, ConnectState.notFind.getValue());
                }
            }
        });
    }

    @Override
    public void connectError(int i) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                if (channel != null) {
                    channel.invokeMethod(kConnectState, ConnectState.fail.getValue());
                }
            }
        });
    }

    @Override
    public void connectTimeOut() {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                if (channel != null) {
                    channel.invokeMethod(kConnectState, ConnectState.timeout.getValue());
                }
            }
        });
    }

    @Override
    public void unsupportedBLE() {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                if (channel != null) {
                    channel.invokeMethod(kBluetoothState, BluetoothState.unSupportBle.getValue());
                }
            }
        });
    }

    @Override
    public void unopenedBluetooth() {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                if (channel != null) {
                    channel.invokeMethod(kBluetoothState, BluetoothState.unOpen.getValue());
                }
            }
        });
    }

    @Override
    public void notOpenLocation() {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                if (channel != null) {
                    channel.invokeMethod(kBluetoothState, BluetoothState.unOpenLocation.getValue());
                }
            }
        });
    }
}
