package com.example.easdktool.jieli.ota;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;


import com.apex.ax_bluetooth.callback.BleConnectStatusListener;
import com.apex.ax_bluetooth.callback.OtaCallback;
import com.apex.ax_bluetooth.core.EABleManager;
import com.example.easdktool.jieli.watchface.JieliWatchFaceManager;
import com.jieli.jl_bt_ota.constant.StateCode;
import com.jieli.jl_bt_ota.interfaces.IBluetoothCallback;
import com.jieli.jl_bt_ota.interfaces.IUpgradeCallback;
import com.jieli.jl_bt_ota.model.BleScanMessage;
import com.jieli.jl_bt_ota.model.base.BaseError;
import com.jieli.jl_bt_ota.model.base.CommandBase;
import com.jieli.jl_bt_ota.util.BluetoothUtil;
import com.jieli.jl_bt_ota.util.CHexConver;

public class JieliOtaInstance {
    final String TAG = this.getClass().getSimpleName();
    private static JieliOtaInstance jieliOtaInstance;
    private JieliOtaManager jieliOtaManager;
    private OtaCallback otaCallback;
    private BleConnectStatusListener bleConnectStatusListener;


    public static JieliOtaInstance getInstance() {
        if (jieliOtaInstance == null) {
            synchronized (JieliOtaInstance.class) {
                if (jieliOtaInstance == null) {
                    jieliOtaInstance = new JieliOtaInstance();
                }
            }
        }
        return jieliOtaInstance;
    }

    private JieliOtaInstance() {
    }

    public void setReconnectApi(BleConnectStatusListener bleConnectStatusListener) {
        this.bleConnectStatusListener = bleConnectStatusListener;
        if (jieliOtaManager != null) {
            jieliOtaManager.setBleConnectStatusListener(bleConnectStatusListener);
        }

    }

    public void init(Context mContext, String filePath) {
        jieliOtaManager = new JieliOtaManager(mContext.getApplicationContext());
        jieliOtaManager.registerBluetoothCallback(new JieliBluetoothCallback(filePath));
        jieliOtaManager.setBleConnectStatusListener(bleConnectStatusListener);
    }


    public void startOta(String filePath, OtaCallback otaCallback, Context mContext) {
        this.otaCallback = otaCallback;
        JieliWatchFaceManager.getInstance().deleteConnectListener();
        JieliWatchFaceManager.getInstance().deleteDeviceReceiveData();
        init(mContext, filePath);
        jieliOtaManager.setOtaCallback(otaCallback);
    }


    public void release() {
        if (jieliOtaManager != null) {
            jieliOtaManager.release();
            jieliOtaManager = null;
        }
    }


    private class JieliBluetoothCallback implements IBluetoothCallback {
        String filePath;

        public JieliBluetoothCallback(String filePath) {
            this.filePath = filePath;
        }

        @Override
        public void onAdapterStatus(boolean b, boolean b1) {
            Log.i(TAG, "onAdapterStatus");
        }

        @Override
        public void onDiscoveryStatus(boolean b, boolean b1) {
            Log.i(TAG, "onDiscoveryStatus");
        }

        @Override
        public void onDiscovery(BluetoothDevice bluetoothDevice, BleScanMessage bleScanMessage) {
            Log.i(TAG, "onDiscovery");
        }

        @Override
        public void onBleDataBlockChanged(BluetoothDevice bluetoothDevice, int i, int i1) {
            Log.i(TAG, "onBleDataBlockChanged");
        }

        @Override
        public void onBtDeviceConnection(BluetoothDevice bluetoothDevice, int i) {
            Log.i("onBtDeviceConnection", "onBtDeviceConnection:" + i);
        }


        @Override
        public void onConnection(BluetoothDevice bluetoothDevice, int i) {
            Log.i("jieliLog", "设备连接状态：" + i + ",设备信息;" + (bluetoothDevice != null ? bluetoothDevice.toString() : null));

            if (i == StateCode.CONNECTION_OK && jieliOtaManager != null) {
                if (jieliOtaManager.isOTA()) {
                    Log.i(TAG, "正在OTA");
                    if (!TextUtils.isEmpty(jieliOtaManager.getReconnectAddress())) {
                        return;
                    } else {
                        if (otaCallback != null) {
                            otaCallback.mutualFail(0x11);
                        }

                        release();
                    }


                } else {
                    if (jieliOtaManager != null) {
                        jieliOtaManager.getBluetoothOption().setFirmwareFilePath(filePath);
                        jieliOtaManager.startOTA(new JieliUpdateCallback());
                    }
                }


            } else if (i == StateCode.CONNECTION_DISCONNECT || i == StateCode.CONNECTION_FAILED) {
                Log.i("jieliLog", "断连");
                EABleManager.getInstance().disconnectPeripheral();
            }


        }

        @Override
        public void onReceiveCommand(BluetoothDevice bluetoothDevice, CommandBase commandBase) {
            Log.i("jieliLog", "onReceiveCommand:" + commandBase.toString());
        }

        @Override
        public void onA2dpStatus(BluetoothDevice bluetoothDevice, int i) {
            //   Log.i(TAG, "onA2dpStatus:" + i);
        }

        @Override
        public void onHfpStatus(BluetoothDevice bluetoothDevice, int i) {
            //  Log.i(TAG, "onHfpStatus:" + i);
        }

        @Override
        public void onMandatoryUpgrade(BluetoothDevice bluetoothDevice) {
            //  Log.i(TAG, "onMandatoryUpgrade");

        }

        @Override
        public void onError(BaseError baseError) {
            Log.i(TAG, "BaseError:" + baseError.toString());
            int code = baseError.getCode();
            if (code == 0x1001 || code == 0x4003 || code == 0x5004 || code == 0x5005) {
                if (otaCallback != null) {
                    otaCallback.mutualFail(0x10);
                }

                release();
            } else if (code == 0x1012) {
                if (otaCallback != null) {
                    otaCallback.mutualFail(0x06);
                }
                release();
            } else if (code == 0x3004 || code == 0x3005) {
                if (otaCallback != null) {
                    otaCallback.mutualFail(0x04);
                }
                release();
            } else if (code == 0x4001 || code == 0x4006 || code == 0x4007 || code == 0x4009 || code == 0x400A || code == 0x400D || code == 0x400E || code == 0x4010 || code == 0x4014 || code == 0x5001 || code == 0x5006) {
                if (otaCallback != null) {
                    otaCallback.mutualFail(code);
                }
                release();
            } else if (code == 0x4002) {
                if (otaCallback != null) {
                    otaCallback.mutualFail(0x0D);
                }
                release();
            } else if (code == 0x4004) {
                if (otaCallback != null) {
                    otaCallback.mutualFail(0x05);
                }
                release();
            } else if (code == 0x4005) {
                if (otaCallback != null) {
                    otaCallback.mutualFail(0x13);
                }
                release();
            } else if (code == 0x4008 || code == 0x4013) {
                if (otaCallback != null) {
                    otaCallback.mutualFail(0x11);
                }
                release();

            } else if (code == 0x400C || code == 0x400F) {
                if (otaCallback != null) {
                    otaCallback.mutualFail(0x12);
                }
                release();
            } else if (code == 0x3002 || code == 0x3007 || code == 0x3008 || code == 0x300A || code == 0x300B || code == 0x400B || code == 0x4011 || code == 0x4012) {
                setDeviceConnection(StateCode.CONNECTION_DISCONNECT);
                EABleManager.getInstance().disconnectPeripheral();
                if (otaCallback != null) {
                    otaCallback.mutualFail(code);
                }
                if (bleConnectStatusListener != null) {
                    bleConnectStatusListener.deviceDisconnect();
                }
                release();
            }

        }

    }


    private class JieliUpdateCallback implements IUpgradeCallback {
        @Override
        public void onStartOTA() {
            Log.i(TAG, "开始OTA");
            if (otaCallback != null) {
                otaCallback.progress(0);
            }
        }

        @Override
        public void onNeedReconnect(final String s, boolean b) {
            Log.i("jieliLog", "onNeedReconnect，地址：" + s + ",是否重连：" + b);

            if (b) {
                if (jieliOtaManager != null) {
                    if (jieliOtaManager.getBluetoothOption().isUseReconnect()) {
                        if (!TextUtils.isEmpty(s)) {
                            EABleManager.getInstance().disconnectPeripheral();
                            byte[] addressByte = BluetoothUtil.addressCovertToByteArray(s);
                            int value = CHexConver.byteToInt(addressByte[addressByte.length - 1]) + 1;
                            addressByte[addressByte.length - 1] = CHexConver.intToByte(value);
                            String newAddr = BluetoothUtil.hexDataCovetToAddress(addressByte);
                            Log.i("jieliLog", "新的蓝牙地址：" + newAddr + ",旧的蓝牙地址：" + s);
                            if (jieliOtaManager != null) {
                                jieliOtaManager.autoReconnect(newAddr);
                            }
                        }
                    }
                }
            }

        }

        @Override
        public void onProgress(int i, float v) {
            Log.i(TAG, "进度：" + v + ",升级类型：" + i);
            if (otaCallback != null) {
                otaCallback.progress((int) v);
            }
        }


        @Override
        public void onStopOTA() {
            Log.i(TAG, "ota完成");
            if (otaCallback != null) {
                otaCallback.progress(100);
                otaCallback.success();
            }
            if (jieliOtaManager != null) {
                jieliOtaManager.setReconnectAddress(null);
            }
            release();
            EABleManager.getInstance().disconnectPeripheral();
            if (bleConnectStatusListener != null) {
                bleConnectStatusListener.deviceDisconnect();
            }

        }

        @Override
        public void onCancelOTA() {
            Log.i(TAG, "ota被取消");
            if (otaCallback != null) {
                otaCallback.mutualFail(0x18);
            }
            if (jieliOtaManager != null) {
                jieliOtaManager.setReconnectAddress(null);
            }
            release();
        }
        //断连
        // 0x3002 发送数据失败 应该通知断连
        // 0x3007 发送数据超时，应该通知断连
        // 0x3008 回复状态失败，应该通知断连
        // 0x300A 设备回复错误结果，应该通知断连
        // 0x300B 等待命令超时，应该通知断连
        // 0x400B 命令超时，应该通知断连
        // 0x4011 回连设备超时，应该通知断连
        // 0x4012 回连设备失败,应该通知断连

        //OTA正在执行
        // 0x4008 升级程序正在进行，回调OTA正在执行
        // 0x4013 设备正在升级中，回调OTA正在执行

        //拒绝OTA
        // 0x400C 升级文件的固件版本一致，回调拒绝OTA
        // 0x400F 相同文件,回调拒绝OTA

        //退出OTA
        // 0x1001 参数错误，应该退出OTA，
        // 0x1012 远端设备未连接，也应该退出OTA；
        // 0x3004 数据格式异常，应该退出OTA
        // 0x3005 解包异常，应该退出OTA
        // 0x4001 OTA升级失败，应该退出OTA
        // 0x4002 设备低电压，应该退出OTA
        // 0x4003 升级文件错误，应该退出OTA
        // 0x4004 读取偏移量失败，应该退出OTA
        // 0x4005 数据校验失败，应该退出OTA
        // 0x4006 加密key不匹配，应该退出OTA
        // 0x4007 升级类型出错,应该退出OTA
        // 0x4009 升级过程中出现长度错误,应该退出OTA
        // 0x400A flash读写错误，应该退出OTA
        // 0x400D TWS未连接，退出OTA
        // 0x400E 耳机未在充电仓
        // 0x4010 未知升级错误，退出OTA
        // 0x4014 设备处于设备双连模式，退出OTA
        // 0x5001 认证设备失败，退出OTA
        // 0x5004 未找到升级文件，退出OTA
        // 0x5005 未找到升级数据，退出OTA
        // 0x5006 IO异常，退出OTA
        @Override
        public void onError(BaseError baseError) {
            Log.i(TAG, "ota错误：" + baseError.toString());
            jieliOtaManager.onBtDeviceConnection(jieliOtaManager.getConnectedDevice(), StateCode.CONNECTION_DISCONNECT);
            int code = baseError.getCode();
            if (code == 0x1001 || code == 0x4003 || code == 0x5004 || code == 0x5005) {
                if (otaCallback != null) {
                    otaCallback.mutualFail(0x10);
                }
                release();
            } else if (code == 0x1012) {
                if (otaCallback != null) {
                    otaCallback.mutualFail(0x06);
                }
                release();
            } else if (code == 0x3004 || code == 0x3005) {
                if (otaCallback != null) {
                    otaCallback.mutualFail(0x04);
                }
                release();
            } else if (code == 0x4001 || code == 0x4006 || code == 0x4007 || code == 0x4009 || code == 0x400A || code == 0x400D || code == 0x400E || code == 0x4010 || code == 0x4014 || code == 0x5001 || code == 0x5006) {
                if (otaCallback != null) {
                    otaCallback.mutualFail(code);
                }
                release();
            } else if (code == 0x4002) {
                if (otaCallback != null) {
                    otaCallback.mutualFail(0x0D);
                }
                release();
            } else if (code == 0x4004) {
                if (otaCallback != null) {
                    otaCallback.mutualFail(0x05);
                }
                release();
            } else if (code == 0x4005) {
                if (otaCallback != null) {
                    otaCallback.mutualFail(0x13);
                }
                release();
            } else if (code == 0x4008 || code == 0x4013) {
                if (otaCallback != null) {
                    otaCallback.mutualFail(0x11);
                }
                release();

            } else if (code == 0x400C || code == 0x400F) {
                if (otaCallback != null) {
                    otaCallback.mutualFail(0x12);
                }
                release();
            } else if (code == 0x3002 || code == 0x3007 || code == 0x3008 || code == 0x300A || code == 0x300B || code == 0x400B || code == 0x4011 || code == 0x4012) {
                setDeviceConnection(StateCode.CONNECTION_DISCONNECT);
                EABleManager.getInstance().disconnectPeripheral();
                if (otaCallback != null) {
                    otaCallback.mutualFail(code);
                }
                if (bleConnectStatusListener != null) {
                    bleConnectStatusListener.deviceDisconnect();
                }
                release();
            }

        }
    }

    public void setDeviceConnection(int stateCode) {
        if (jieliOtaManager != null) {
            jieliOtaManager.changeDeviceConnectState(stateCode);
        }
    }


}
