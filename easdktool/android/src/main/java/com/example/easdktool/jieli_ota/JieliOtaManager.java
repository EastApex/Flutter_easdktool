package com.example.easdktool.jieli_ota;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.apex.ax_bluetooth.callback.BleConnectStatusListener;
import com.apex.ax_bluetooth.callback.JieliConnectListener;
import com.apex.ax_bluetooth.callback.JieliDataCallback;
import com.apex.ax_bluetooth.callback.OtaCallback;
import com.apex.ax_bluetooth.core.EABleBluetoothOption;
import com.apex.ax_bluetooth.core.EABleManager;
import com.apex.ax_bluetooth.enumeration.EABleConnectState;
import com.jieli.jl_bt_ota.constant.StateCode;
import com.jieli.jl_bt_ota.impl.BluetoothOTAManager;
import com.jieli.jl_bt_ota.impl.RcspAuth;
import com.jieli.jl_bt_ota.interfaces.IBluetoothCallback;
import com.jieli.jl_bt_ota.model.BluetoothOTAConfigure;

import java.util.IllegalFormatCodePointException;
import java.util.Timer;
import java.util.TimerTask;


public class JieliOtaManager extends BluetoothOTAManager {
    private int currentStates;
    IBluetoothCallback iBluetoothCallback;
    Context mContext;
    OtaCallback otaCallback;
    BleConnectStatusListener bleConnectStatusListener;


    public JieliOtaManager(Context context) {
        super(context);
        mContext = context;
        createOTAConfigure();
        addReceiveDeviceData();
        EABleManager.getInstance().setJieliConnectListener(new JieliConnectListener() {
            @Override
            public void connectStateUpdate(int i) {
                if (i == 1) {
                    addReceiveDeviceData();
                }
                changeDeviceConnectState(i);
            }
        });
        int setState;
        if (EABleManager.getInstance().getDeviceConnectState() == EABleConnectState.STATE_CONNECTED) {
            setState = StateCode.CONNECTION_OK;

        } else if (EABleManager.getInstance().getDeviceConnectState() == EABleConnectState.STATE_CONNECTING) {
            setState = StateCode.CONNECTION_CONNECTING;
        } else {
            setState = StateCode.CONNECTION_DISCONNECT;
        }
        changeDeviceConnectState(setState);


    }

    public void changeDeviceConnectState(int newStatus) {
        Log.i("jieliLog", "需要改变的改变连接状态：" + newStatus + ",改变之前的状态：" + currentStates);
        if (currentStates != newStatus) {
            currentStates = newStatus;
            onBtDeviceConnection(getConnectedDevice(), currentStates);
            if (newStatus == 0) {
                setConnectedBtDevice(null);
            }
        }
    }

    public void setOtaCallback(OtaCallback otaCallback) {
        this.otaCallback = otaCallback;
    }

    public void setBleConnectStatusListener(BleConnectStatusListener bleConnectStatusListener) {
        this.bleConnectStatusListener = bleConnectStatusListener;
    }

    @Override
    public BluetoothDevice getConnectedDevice() {
        Log.i(TAG, "获取连接设备");
        BluetoothDevice bluetoothDevice = EABleManager.getInstance().getConnectDevice();
        Log.i(TAG, "getConnectedDevice:" + (bluetoothDevice != null ? bluetoothDevice.toString() : null));
        return bluetoothDevice;
    }

    @Override
    public BluetoothGatt getConnectedBluetoothGatt() {
        Log.i(TAG, "获取连接设备蓝牙gatt");
        BluetoothGatt bluetoothGatt = EABleManager.getInstance().getConnectedBluetoothGatt();
        Log.i(TAG, "getConnectedBluetoothGatt:" + (bluetoothGatt != null ? bluetoothGatt.toString() : null));
        return bluetoothGatt;
    }


    @Override
    public void connectBluetoothDevice(BluetoothDevice bluetoothDevice) {
        Log.i("jieliLog", "连接蓝牙");
        if (isBleScanning()) {
            stopBLEScan();
        }
        if (bluetoothDevice == null) {
            Log.i("jieliLog", "连接的蓝牙信息不存在");
            return;
        }
        if (!getBluetoothOption().isUseReconnect()) {
            autoReconnect(bluetoothDevice.getAddress());
        }

    }

    @Override
    public void disconnectBluetoothDevice(BluetoothDevice bluetoothDevice) {
        Log.i(TAG, "断开蓝牙，释放资源");
        EABleManager.getInstance().disconnectPeripheral();
    }

    @Override
    public boolean sendDataToDevice(BluetoothDevice bluetoothDevice, byte[] bytes) {
        Log.i("sendDataToDevice", "发送数据到device,数据长度：" + bytes.length);
        EABleManager.getInstance().sendJieliData(bytes);
        return true;
    }

    @Override
    public boolean registerBluetoothCallback(IBluetoothCallback iBluetoothCallback) {
        this.iBluetoothCallback = iBluetoothCallback;
        return super.registerBluetoothCallback(iBluetoothCallback);
    }


    /**
     * OTA开始之前进行设置
     */
    private void createOTAConfigure() {
        EABleManager.getInstance().setJieliOtaStatus(true);
        BluetoothOTAConfigure bluetoothOption = new BluetoothOTAConfigure();
        bluetoothOption.setPriority(BluetoothOTAConfigure.PREFER_BLE);
        bluetoothOption.setUseReconnect(false);
        bluetoothOption.setUseAuthDevice(true);
      //  bluetoothOption.setMtu(EABleBluetoothOption.maxMtu > 20 ? EABleBluetoothOption.maxMtu - 3 : 20);
      //  if (BuildConfig.DEBUG) {
            bluetoothOption.setMtu(EABleBluetoothOption.otaMtu > 20 ? EABleBluetoothOption.otaMtu - 3 : 20);//现在默认等于20
      //  }
        bluetoothOption.setTimeoutMs(30000);
        bluetoothOption.setNeedChangeMtu(false);
        bluetoothOption.setUseJLServer(false);
        configure(bluetoothOption);
        RcspAuth.setAuthTimeout(10000);
    }

    /**
     * 获取到的杰理数据，交给杰理处理
     */
    private void addReceiveDeviceData() {
        EABleManager.getInstance().addJieliDataCallback(new JieliDataCallback() {
            @Override
            public void jieliData(BluetoothDevice device, byte[] data) {
                /**
                 try {
                 if (receiveDataCache != null) {
                 receiveDataCache.add(new ReceiveData(device, data));
                 }
                 } catch (Exception e) {
                 e.printStackTrace();
                 }
                 */
                Log.i("sendDataToDevice", "收到来自device的数据,数据长度：" + data.length);
                onReceiveDeviceData(device, data);
            }
        });
    }


    class ReceiveData {
        BluetoothDevice device;
        byte[] data;

        public ReceiveData(BluetoothDevice device, byte[] data) {
            this.device = device;
            this.data = data;
        }
    }

    @Override
    public void release() {
        if (iBluetoothCallback != null) {
            unregisterBluetoothCallback(iBluetoothCallback);
        }
        mContext = null;
        EABleManager.getInstance().setJieliConnectListener(null);
        EABleManager.getInstance().addJieliDataCallback(null);
        EABleManager.getInstance().setJieliOtaStatus(false);
        super.release();
    }

    public void autoReconnect(final String macAddress) {
        if (!TextUtils.isEmpty(macAddress)) {
            //开始搜寻蓝牙
            Log.i("jieliLog", "开始重连:" + macAddress);
            EABleManager.getInstance().disconnectPeripheral();
            new Reconnect(otaCallback, bleConnectStatusListener).reconnectDevice(macAddress, mContext);
        }
    }

}
