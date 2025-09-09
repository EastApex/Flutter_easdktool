package com.example.easdktool.jieli.ota;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import com.apex.ax_bluetooth.callback.BleConnectStatusListener;
import com.apex.ax_bluetooth.callback.OtaCallback;
import com.apex.ax_bluetooth.core.EABleManager;

public class Reconnect {
    private final String TAG = this.getClass().getSimpleName();
    private OtaCallback otaCallback;
    private BleConnectStatusListener bleConnectStatusListener;
    private JieliOtaManager jieliOtaManager;


    public Reconnect(OtaCallback otaCallback, BleConnectStatusListener bleConnectStatusListener, JieliOtaManager jieliOtaManager) {
        this.otaCallback = otaCallback;
        this.bleConnectStatusListener = bleConnectStatusListener;
        this.jieliOtaManager = jieliOtaManager;
    }

    public void reconnectDevice(final String deviceAddress, Context mContext) {
        if (TextUtils.isEmpty(deviceAddress)) {
            if (otaCallback != null) {
                otaCallback.mutualFail(0x01);
            }
            if (bleConnectStatusListener != null) {
                bleConnectStatusListener.connectError(0x01);
            }
            return;
        }
        boolean connectPermission = true;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                connectPermission = false;
            }
        }
        if (!connectPermission) {
            if (otaCallback != null) {
                otaCallback.mutualFail(0x17);
            }
            if (bleConnectStatusListener != null) {
                bleConnectStatusListener.connectError(0x17);
            }
            return;
        }
        //重连
        /**
         byte[] addressBytes = BluetoothUtil.addressCovertToByteArray(deviceAddress);
         int value = CHexConver.byteToInt(addressBytes[addressBytes.length - 1]) + 1;
         addressBytes[addressBytes.length - 1] = CHexConver.intToByte(value);
         String newAddress = BluetoothUtil.hexDataCovetToAddress(addressBytes);
         Log.i("jieliLog", "新的蓝牙地址：" + newAddress+",旧的蓝牙地址："+deviceAddress);
         */
        Log.i("jieliLog", "新的蓝牙地址：" + null + ",旧的蓝牙地址：" + deviceAddress);
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (!bluetoothAdapter.isEnabled()) {
            if (bleConnectStatusListener != null) {
                bleConnectStatusListener.unopenedBluetooth();
            }
            return;
        }
        //开始重连
        if (bleConnectStatusListener == null) {
            bleConnectStatusListener = new BleConnectStatusListener() {
                @Override
                public void deviceConnected() {

                }

                @Override
                public void deviceDisconnect() {

                }

                @Override
                public void connectTimeOut() {

                }

                @Override
                public void deviceNotFind() {

                }

                @Override
                public void unopenedBluetooth() {

                }

                @Override
                public void connectError(int i) {

                }

                @Override
                public void unsupportedBLE() {

                }

                @Override
                public void notOpenLocation() {

                }
            };
        }
        try {
            //   jieliOtaManager.setReconnectAddress(newAddress);
            EABleManager.getInstance().connectToPeripheral(deviceAddress, mContext, new BleConnectStatusListener() {
                @Override
                public void deviceConnected() {
                    if (jieliOtaManager != null) {
                        jieliOtaManager.getBluetoothOption().setUseAuthDevice(true);
                        jieliOtaManager.addConnectListener();
                    }

                }

                @Override
                public void deviceDisconnect() {
                    if (bleConnectStatusListener != null) {
                        bleConnectStatusListener.deviceDisconnect();
                    }

                }

                @Override
                public void connectTimeOut() {
                    if (bleConnectStatusListener != null) {
                        bleConnectStatusListener.connectTimeOut();
                    }
                }

                @Override
                public void deviceNotFind() {
                    if (bleConnectStatusListener != null) {
                        bleConnectStatusListener.deviceNotFind();
                    }
                }

                @Override
                public void unopenedBluetooth() {
                    if (bleConnectStatusListener != null) {
                        bleConnectStatusListener.unopenedBluetooth();
                    }
                }

                @Override
                public void connectError(int i) {
                    if (bleConnectStatusListener != null) {
                        bleConnectStatusListener.connectError(i);
                    }
                }

                @Override
                public void unsupportedBLE() {
                    if (bleConnectStatusListener != null) {
                        bleConnectStatusListener.unsupportedBLE();
                    }
                }

                @Override
                public void notOpenLocation() {
                    if (bleConnectStatusListener != null) {
                        bleConnectStatusListener.notOpenLocation();
                    }
                }
            }, 400, null, null, false);
        } catch (Exception e) {
            e.printStackTrace();
            if (bleConnectStatusListener != null) {
                bleConnectStatusListener.connectError(0x02);
            }
        }

    }
}
