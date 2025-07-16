package com.example.easdktool.jieli_ota;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.content.Context;
import android.util.Log;

import com.apex.ax_bluetooth.callback.BleConnectStatusListener;
import com.apex.ax_bluetooth.callback.DataReportCallback;
import com.apex.ax_bluetooth.callback.JieliConnectListener;
import com.apex.ax_bluetooth.callback.JieliDataCallback;
import com.apex.ax_bluetooth.callback.MotionDataReportCallback;
import com.apex.ax_bluetooth.callback.OtaCallback;
import com.apex.ax_bluetooth.core.EABleBluetoothOption;
import com.apex.ax_bluetooth.core.EABleManager;
import com.apex.ax_bluetooth.enumeration.EABleConnectState;
import com.jieli.jl_bt_ota.constant.ErrorCode;
import com.jieli.jl_bt_ota.constant.StateCode;
import com.jieli.jl_bt_ota.impl.BluetoothOTAManager;
import com.jieli.jl_bt_ota.impl.RcspAuth;
import com.jieli.jl_bt_ota.interfaces.IActionCallback;
import com.jieli.jl_bt_ota.interfaces.IBluetoothCallback;
import com.jieli.jl_bt_ota.interfaces.IUpgradeCallback;
import com.jieli.jl_bt_ota.model.BleScanMessage;
import com.jieli.jl_bt_ota.model.BluetoothOTAConfigure;
import com.jieli.jl_bt_ota.model.base.BaseError;
import com.jieli.jl_bt_ota.model.base.CommandBase;
import com.jieli.jl_bt_ota.model.response.TargetInfoResponse;
import com.jieli.jl_bt_ota.util.CHexConver;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;

public class JieliOtaManager extends BluetoothOTAManager {
    private int currentStates;
    IBluetoothCallback iBluetoothCallback;
    ConcurrentLinkedQueue<ReceiveData> receiveDataCache = new ConcurrentLinkedQueue<>();
    ScheduledExecutorService sendExecutorService = Executors.newScheduledThreadPool(1);
    Context mContext;


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
                Log.i(TAG, "开始改变连接设备状态");
                onBtDeviceConnection(getConnectedDevice(), i);
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
        onBtDeviceConnection(getConnectedDevice(), setState);


    }

    @Override
    public void onBtDeviceConnection(BluetoothDevice bluetoothDevice, int i) {
        if (currentStates != i) {
            currentStates = i;
            Log.i(TAG, "改变连接设备状态");
            super.onBtDeviceConnection(bluetoothDevice, currentStates);
        } else {
            Log.i(TAG, "设备状态相同，不用改变");
        }

    }

    @Override
    public BluetoothDevice getConnectedDevice() {
        return EABleManager.getInstance().getConnectDevice();
    }

    @Override
    public BluetoothGatt getConnectedBluetoothGatt() {
        return EABleManager.getInstance().getConnectedBluetoothGatt();
    }

    @Override
    public void connectBluetoothDevice(BluetoothDevice bluetoothDevice) {

    }

    @Override
    public void disconnectBluetoothDevice(BluetoothDevice bluetoothDevice) {

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
        BluetoothOTAConfigure bluetoothOption = new BluetoothOTAConfigure();
        bluetoothOption.setPriority(BluetoothOTAConfigure.PREFER_BLE);
        bluetoothOption.setUseReconnect(false);
        bluetoothOption.setUseAuthDevice(true);
        bluetoothOption.setMtu(EABleBluetoothOption.maxMtu > 20 ? EABleBluetoothOption.maxMtu - 3 : 20);//现在默认等于20
        //  bluetoothOption.setMtu(EABleBluetoothOption.otaMtu > 20 ? EABleBluetoothOption.otaMtu - 3 : 20);//现在默认等于20
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
        super.release();
    }


}
