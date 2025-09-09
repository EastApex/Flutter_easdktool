package com.example.easdktool.jieli.watchface;

import android.bluetooth.BluetoothDevice;
import android.text.TextUtils;
import android.util.Log;

import com.apex.ax_bluetooth.callback.BleConnectStatusListener;
import com.apex.ax_bluetooth.callback.GeneralCallback;
import com.apex.ax_bluetooth.callback.JieliConnectListener;
import com.apex.ax_bluetooth.callback.JieliDataCallback;
import com.apex.ax_bluetooth.callback.OtaCallback;
import com.apex.ax_bluetooth.core.EABleManager;
import com.apex.ax_bluetooth.enumeration.EABleConnectState;
import com.google.gson.Gson;
import com.jieli.jl_bt_ota.constant.StateCode;
import com.jieli.jl_fatfs.interfaces.OnFatFileProgressListener;
import com.jieli.jl_fatfs.model.FatFile;
import com.jieli.jl_fatfs.utils.FatUtil;
import com.jieli.jl_rcsp.impl.RcspAuth;
import com.jieli.jl_rcsp.impl.WatchOpImpl;
import com.jieli.jl_rcsp.interfaces.listener.ThreadStateListener;
import com.jieli.jl_rcsp.interfaces.watch.OnWatchCallback;
import com.jieli.jl_rcsp.interfaces.watch.OnWatchOpCallback;
import com.jieli.jl_rcsp.model.WatchConfigure;
import com.jieli.jl_rcsp.model.base.BaseError;
import com.jieli.jl_rcsp.model.device.DeviceConfiguration;
import com.jieli.jl_rcsp.model.device.settings.v0.NetworkInfo;

import java.util.ArrayList;
import java.util.List;

public class JieliWatchFaceManager extends WatchOpImpl {
    final String TAG = this.getClass().getSimpleName();
    private int currentStates;
    private static JieliWatchFaceManager jieliWatchFaceManager;
    private OnWatchCallback onWatchCallback;
    BleConnectStatusListener bleConnectStatusListener;
    private RcspAuth mRcspAuth;
    private boolean startAuth;
    public int initSuccess = 0;
    private JieliWatchInitCallback jieliWatchInitCallback;
    private GetWatchMsgTask getWatchMsgTask;

    public void setBleConnectStatusListener(BleConnectStatusListener bleConnectStatusListener) {
        this.bleConnectStatusListener = bleConnectStatusListener;
    }

    public void setJieliWatchInitCallback(JieliWatchInitCallback jieliWatchInitCallback) {
        this.jieliWatchInitCallback = jieliWatchInitCallback;
    }

    public static JieliWatchFaceManager getInstance() {
        if (jieliWatchFaceManager == null) {
            synchronized (JieliWatchFaceManager.class) {
                if (jieliWatchFaceManager == null) {
                    jieliWatchFaceManager = new JieliWatchFaceManager(FUNC_WATCH);
                }
            }
        }
        return jieliWatchFaceManager;
    }

    private JieliWatchFaceManager(int i) {
        super(i);


    }

    private void initSystem() {
        if (onWatchCallback == null) {
            onWatchCallback = new OnWatchCallback() {
                @Override
                public void onWatchSystemInit(int i) {
                    Log.i(TAG, "手表系统初始化结果：" + i);
                    if (i == 0) {
                        //初始化成功；
                        initSuccess = 2;//初始化成功
                        if (jieliWatchInitCallback != null) {
                            jieliWatchInitCallback.initResult(2);
                        }
                    } else {
                        initSuccess = 3;//初始化失败
                        if (jieliWatchInitCallback != null) {
                            jieliWatchInitCallback.initResult(3);
                        }
                        EABleManager.getInstance().disconnectPeripheral();
                        if (bleConnectStatusListener != null) {
                            bleConnectStatusListener.deviceDisconnect();
                        }
                        release();

                    }
                    super.onWatchSystemInit(i);
                }

                @Override
                public void onWatchSystemException(BluetoothDevice bluetoothDevice, int i) {
                    Log.i(TAG, "手表系统异常：" + i);
                    EABleManager.getInstance().disconnectPeripheral();
                    if (bleConnectStatusListener != null) {
                        bleConnectStatusListener.deviceDisconnect();
                    }
                    release();
                    super.onWatchSystemException(bluetoothDevice, i);
                }


                @Override
                public void onResourceUpdateUnfinished(BluetoothDevice bluetoothDevice) {
                    Log.i(TAG, "资源更新未完成");
                    super.onResourceUpdateUnfinished(bluetoothDevice);
                }

                @Override
                public void onNetworkModuleException(BluetoothDevice device, NetworkInfo info) {
                    Log.i(TAG, "网络错误");
                    super.onNetworkModuleException(device, info);
                }

                @Override
                public void onRcspInit(BluetoothDevice bluetoothDevice, boolean b) {
                    if (b) {
                        Log.i(TAG, "Rcsp 初始化成功");
                    } else {
                        Log.i(TAG, "Rcsp 初始化失败，断开蓝牙连接");
                        EABleManager.getInstance().disconnectPeripheral();
                        if (bleConnectStatusListener != null) {
                            bleConnectStatusListener.deviceDisconnect();
                        }
                        release();
                    }
                    super.onRcspInit(bluetoothDevice, b);
                }


            };
            /**
             * 注册手表表盘相关回调
             */
            registerOnWatchCallback(onWatchCallback);
            initSuccess = 1;//正在初始化
        }

        addConnectListener();

    }

    public void addConnectListener() {
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

    public void deleteConnectListener() {
        EABleManager.getInstance().setJieliConnectListener(null);
    }

    public void deleteDeviceReceiveData() {
        EABleManager.getInstance().addJieliDataCallback(null);
    }

    public void init() {
        addReceiveDeviceData();
        deviceAuth();

    }

    private void requestDialConfiguration(BluetoothDevice device) {//获取手表的所有信息
        DeviceConfiguration deviceConfiguration = mStatusManager.getDeviceConfigure(device);
        if (null == deviceConfiguration) {
            requestDeviceConfigure(new OnWatchOpCallback<WatchConfigure>() {
                @Override
                public void onSuccess(WatchConfigure result) {
                    //获取的表盘属性
                    Log.i(TAG, "WatchConfigure:" + (result == null ? null : result.toString()));

                }

                @Override
                public void onFailed(BaseError error) {
                    Log.i(TAG, "requestDeviceConfigure失败");
                }
            });
        } else {
            Log.i(TAG, "存在DeviceConfiguration");
        }
    }

    public void changeDeviceConnectState(int newStatus) {
        Log.i("jieliLog", "需要改变的改变连接状态：" + newStatus + ",改变之前的状态：" + currentStates);
        if (currentStates != newStatus) {
            currentStates = newStatus;
            notifyBtDeviceConnection(getConnectedDevice(), currentStates);
        }
    }

    @Override
    public BluetoothDevice getConnectedDevice() {
        return EABleManager.getInstance().getConnectDevice();
    }

    @Override
    public boolean sendDataToDevice(BluetoothDevice bluetoothDevice, byte[] bytes) {
        Log.i(TAG, "发送数据大小：" + bytes.length);
        EABleManager.getInstance().sendJieliDialData(bytes);
        return true;
    }

    /**
     * 获取到的杰理数据，交给杰理处理
     */
    public void addReceiveDeviceData() {
        EABleManager.getInstance().addJieliDataCallback(new JieliDataCallback() {
            @Override
            public void jieliData(BluetoothDevice device, byte[] data) {
                if (startAuth) {
                    mRcspAuth.handleAuthData(device, data);
                    return;
                }
                Log.i("sendDataToDevice", "收到来自device的数据,数据长度：" + data.length);
                notifyReceiveDeviceData(device, data);
            }
        });
    }


    @Override
    public void release() {
        if (onWatchCallback != null) {
            unregisterOnWatchCallback(onWatchCallback);
            onWatchCallback = null;
        }
        startAuth = false;
        if (mRcspAuth != null) {
            mRcspAuth.stopAuth(getConnectedDevice(), false);
            mRcspAuth.destroy();
            mRcspAuth = null;
        }
        deleteConnectListener();
        deleteDeviceReceiveData();
        initSuccess = 0;
        jieliWatchFaceManager = null;
        if (null != getWatchMsgTask) {
            getWatchMsgTask.interrupt();
            getWatchMsgTask = null;
        }
        super.release();
    }

    private void deviceAuth() {
        mRcspAuth = new RcspAuth(new RcspAuth.IRcspAuthOp() {
            @Override
            public boolean sendAuthDataToDevice(BluetoothDevice device, byte[] data) {
                return sendDataToDevice(device, data);
            }
        }, new RcspAuth.OnRcspAuthListener() {
            @Override
            public void onInitResult(boolean b) {
                //初始化结果回调
            }

            @Override
            public void onAuthSuccess(BluetoothDevice bluetoothDevice) {
                //设备认证成功
                startAuth = false;
                initSystem();
            }

            @Override
            public void onAuthFailed(BluetoothDevice bluetoothDevice, int i, String s) {
                //设备认证失败
                EABleManager.getInstance().disconnectPeripheral();
                if (bleConnectStatusListener != null) {
                    bleConnectStatusListener.deviceDisconnect();
                }
                release();

            }
        });
        mRcspAuth.stopAuth(getConnectedDevice(), false); //清除旧的设备认证
        startAuth = true;
        boolean ret = mRcspAuth.startAuth(getConnectedDevice()); //开始设备认证, 结果是操作结果
    }

    //查询手表表盘
    public void getDialList(JieliDialCallback jieliDialCallback) {
        if (initSuccess != 2) {
            Log.i(TAG, "手表未初始化完成");
            return;
        }
        Log.i(TAG, "开始获取所有的表盘");
        listWatchList(new OnWatchOpCallback<ArrayList<FatFile>>() {
            @Override
            public void onSuccess(ArrayList<FatFile> fatFiles) {
                Log.i(TAG, "获取所有的表盘成功");
                List<FatFile> fatFileList = getWatchDialList(fatFiles);
                if (fatFileList != null&&!fatFileList.isEmpty()) {
                    if (getWatchMsgTask == null) {
                        getWatchMsgTask = new GetWatchMsgTask(jieliWatchFaceManager, fatFileList, jieliDialCallback, new ThreadStateListener() {
                            @Override
                            public void onStart(long l) {

                            }

                            @Override
                            public void onFinish(long l) {
                                if (getWatchMsgTask != null && getWatchMsgTask.getId() == l) {
                                    getWatchMsgTask = null;
                                }
                            }
                        });
                        getWatchMsgTask.start();
                    }
                } else {
                    if (jieliDialCallback != null) {
                        jieliDialCallback.jieliDial(null);
                    }
                }

            }

            @Override
            public void onFailed(BaseError baseError) {
                Log.i(TAG, "获取所有的表盘失败");
                if (jieliDialCallback != null) {
                    jieliDialCallback.error(baseError);
                }

            }
        });
    }

    private List<FatFile> getWatchDialList(List<FatFile> fatFiles) {
        if (fatFiles == null || fatFiles.isEmpty()) {
            return null;
        }
        Log.i(TAG, new Gson().toJson(fatFiles));
        ArrayList<FatFile> result = new ArrayList<>();
        for (FatFile fatFile : fatFiles) {
            String fName = fatFile.getName();
            if (TextUtils.isEmpty(fName)) {
                continue;
            }
            if ((fName.startsWith("WATCH") || fName.startsWith("watch")) && fName.length() >= 7) {
                result.add(fatFile);
            }
        }
        return result;
    }

    public void deleteWatchFace(String faceWatchPath, GeneralCallback generalCallback) {
        if (initSuccess != 2) {
            Log.i(TAG, "手表未初始化完成");
            if (initSuccess == 0) {
                if (generalCallback != null) {
                    generalCallback.mutualFail(0x19);
                }
                return;
            }
            if (initSuccess == 1) {
                if (generalCallback != null) {
                    generalCallback.mutualFail(0x1A);
                }
                return;
            }
            if (initSuccess == 3) {
                if (generalCallback != null) {
                    generalCallback.mutualFail(0x1B);
                }
                return;
            }
            return;
        }
        if (TextUtils.isEmpty(faceWatchPath)) {
            if (generalCallback != null) {
                generalCallback.mutualFail(0x10);
            }
            return;
        }
        deleteWatchFile(faceWatchPath, new OnFatFileProgressListener() {
            @Override
            public void onStart(String s) {

            }

            @Override
            public void onProgress(float v) {

            }

            @Override
            public void onStop(int i) {
                if (generalCallback != null) {
                    generalCallback.result(i == 0 ? true : false, i);
                }
            }
        });
    }

    public void addWatchFace(final String faceWatchPath, OtaCallback otaCallback) {
        if (initSuccess != 2) {
            Log.i(TAG, "手表未初始化完成");
            if (initSuccess == 0) {
                if (otaCallback != null) {
                    otaCallback.mutualFail(0x19);
                }
                return;
            }
            if (initSuccess == 1) {
                if (otaCallback != null) {
                    otaCallback.mutualFail(0x1A);
                }
                return;
            }
            if (initSuccess == 3) {
                if (otaCallback != null) {
                    otaCallback.mutualFail(0x1B);
                }
                return;
            }
            return;
        }
        if (TextUtils.isEmpty(faceWatchPath)) {
            if (otaCallback != null) {
                otaCallback.mutualFail(0x10);
            }
            return;
        }
        createWatchFile(faceWatchPath, false, new OnFatFileProgressListener() {
            @Override
            public void onStart(String s) {
                Log.i(TAG, "表盘开始");
                if (otaCallback != null) {
                    otaCallback.progress(0);
                }

            }

            @Override
            public void onProgress(float v) {
                if (otaCallback != null) {
                    otaCallback.progress((int) v);
                }
                Log.i(TAG, "表盘进度：" + v);
            }

            @Override
            public void onStop(int i) {
                Log.i(TAG, "表盘结束");

                if (i == 0) {
                    if (otaCallback != null) {
                        otaCallback.success();
                    }
                    setCurrentWatchInfo(FatUtil.getFatFilePath(faceWatchPath), new OnWatchOpCallback<FatFile>() {
                        @Override
                        public void onSuccess(FatFile fatFile) {
                            Log.i(TAG, "设置当前表盘成功");
                        }

                        @Override
                        public void onFailed(BaseError baseError) {
                            Log.i(TAG, "设置当前表盘失败");
                        }
                    });
                } else {
                    Log.i(TAG, "OTA表盘错误码：" + i);
                    if (otaCallback != null) {
                        otaCallback.mutualFail(i);
                    }
                }
            }
        });
    }

}
