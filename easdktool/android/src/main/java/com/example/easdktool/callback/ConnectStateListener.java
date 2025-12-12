package com.example.easdktool.callback;

import android.os.Build;
import android.os.Handler;
import android.os.Looper;

import com.apex.ax_bluetooth.callback.BleConnectStatusListener;
import com.apex.ax_bluetooth.callback.WatchInfoCallback;
import com.apex.ax_bluetooth.core.EABleManager;
import com.apex.ax_bluetooth.enumeration.QueryWatchInfoType;
import com.apex.ax_bluetooth.model.EABleWatchInfo;
import com.example.easdktool.BuildConfig;
import com.example.easdktool.enumerate.BluetoothState;
import com.example.easdktool.enumerate.ConnectState;
import com.example.easdktool.jieli.watchface.JieliWatchFaceManager;

import io.flutter.plugin.common.MethodChannel;

public class ConnectStateListener implements BleConnectStatusListener {
    private MethodChannel channel;
    final String kConnectState = "ConnectState";
    final String kBluetoothState = "BluetoothState";
    ConnectStateListener connectStateListener;


    public ConnectStateListener(MethodChannel channel) {
        connectStateListener = this;
        this.channel = channel;
    }

    @Override
    public void deviceConnected() {
        EABleManager.getInstance().queryWatchInfo(QueryWatchInfoType.watch_info, new WatchInfoCallback() {
            @Override
            public void watchInfo(EABleWatchInfo eaBleWatchInfo) {
                if (eaBleWatchInfo != null) {
                    if (eaBleWatchInfo.getLcd_pixel_type() == 4 || eaBleWatchInfo.getLcd_pixel_type() == 5) {
                        JieliWatchFaceManager.getInstance().release();
                        JieliWatchFaceManager.getInstance().setBleConnectStatusListener(connectStateListener);
                        // JieliWatchFaceManager.getInstance().addConnectListener();
                        // JieliWatchFaceManager.getInstance().addReceiveDeviceData();
                        JieliWatchFaceManager.getInstance().init();
                    }
                }
            }

            @Override
            public void mutualFail(int i) {

            }
        });
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
