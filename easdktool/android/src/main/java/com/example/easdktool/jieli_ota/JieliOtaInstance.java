package com.example.easdktool.jieli_ota;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.util.Log;

import com.apex.ax_bluetooth.callback.BleConnectStatusListener;
import com.apex.ax_bluetooth.callback.DataReportCallback;
import com.apex.ax_bluetooth.callback.MotionDataReportCallback;
import com.apex.ax_bluetooth.callback.OtaCallback;
import com.apex.ax_bluetooth.core.EABleManager;
import com.apex.ax_bluetooth.enumeration.EABleConnectState;
import com.jieli.jl_bt_ota.constant.ErrorCode;
import com.jieli.jl_bt_ota.constant.StateCode;
import com.jieli.jl_bt_ota.interfaces.IActionCallback;
import com.jieli.jl_bt_ota.interfaces.IBluetoothCallback;
import com.jieli.jl_bt_ota.interfaces.IUpgradeCallback;
import com.jieli.jl_bt_ota.model.BleScanMessage;
import com.jieli.jl_bt_ota.model.base.BaseError;
import com.jieli.jl_bt_ota.model.base.CommandBase;
import com.jieli.jl_bt_ota.model.response.TargetInfoResponse;

import java.sql.Time;
import java.util.Timer;
import java.util.TimerTask;

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


    public void setReconnectApi(BleConnectStatusListener bleConnectStatusListener) {
        this.bleConnectStatusListener = bleConnectStatusListener;
    }


    private void createOta(Context mContext, String filePath) {
        jieliOtaManager = new JieliOtaManager(mContext);
        jieliOtaManager.registerBluetoothCallback(new JieliBluetoothCallback());
        jieliOtaManager.getBluetoothOption().setFirmwareFilePath(filePath);
    }


    public void startOta(String filePath, Context mContext, OtaCallback otaCallback) {
        this.otaCallback = otaCallback;
        createOta(mContext, filePath);
    }


    public void release() {
        if (jieliOtaManager != null) {
            jieliOtaManager.release();
            jieliOtaManager = null;
        }
    }


    private class JieliBluetoothCallback implements IBluetoothCallback {

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
            Log.i(TAG, "onBtDeviceConnection");
        }


        @Override
        public void onConnection(BluetoothDevice bluetoothDevice, int i) {
            if (i == StateCode.CONNECTION_OK && jieliOtaManager != null) {
                if (jieliOtaManager.isOTA()) {
                    Log.i(TAG, "正在OTA");
                    if (otaCallback != null) {
                        otaCallback.mutualFail(0x11);
                    }
                    return;
                }

                jieliOtaManager.queryMandatoryUpdate(new IActionCallback<TargetInfoResponse>() {
                    @Override
                    public void onSuccess(TargetInfoResponse targetInfoResponse) {
                        Log.i(TAG, "查询强制升级信息");
                        if (targetInfoResponse != null) {
                            Log.i(TAG, "当前模式：" + targetInfoResponse.getCurFunction());
                            Log.i(TAG, "强制升级：" + targetInfoResponse.getMandatoryUpgradeFlag());
                            Log.i(TAG, "请求升级标志：" + targetInfoResponse.getRequestOtaFlag());
                            Log.i(TAG, "是否支持双备份：" + targetInfoResponse.isSupportDoubleBackup());
                            Log.i(TAG, "单备份连接方式：" + targetInfoResponse.getSingleBackupOtaWay());
                            Log.i(TAG, "扩展模式：" + targetInfoResponse.getExpandMode());
                            Log.i(TAG, "是否允许连接：" + targetInfoResponse.getAllowConnectFlag());
                        }
                        if (jieliOtaManager != null) {
                            jieliOtaManager.startOTA(new JieliUpdateCallback());
                        }


                    }

                    @Override
                    public void onError(BaseError baseError) {
                        Log.i("HomeActivity", "查询强制升级信息错误");
                        if (baseError.getCode() == ErrorCode.ERR_NONE && baseError.getSubCode() == ErrorCode.ERR_NONE) {
                            TargetInfoResponse targetInfoResponse = jieliOtaManager.getDeviceInfo();
                            if (targetInfoResponse != null) {
                                Log.i(TAG, "当前模式：" + targetInfoResponse.getCurFunction());
                                Log.i(TAG, "强制升级：" + targetInfoResponse.getMandatoryUpgradeFlag());
                                Log.i(TAG, "请求升级标志：" + targetInfoResponse.getRequestOtaFlag());
                                Log.i(TAG, "是否支持双备份：" + targetInfoResponse.isSupportDoubleBackup());
                                Log.i(TAG, "单备份连接方式：" + targetInfoResponse.getSingleBackupOtaWay());
                                Log.i(TAG, "扩展模式：" + targetInfoResponse.getExpandMode());
                                Log.i(TAG, "是否允许连接：" + targetInfoResponse.getAllowConnectFlag());
                            }
                            //开始OTA升级
                            if (jieliOtaManager != null) {
                                jieliOtaManager.startOTA(new JieliUpdateCallback());

                            }
                            return;
                        }
                        Log.i("HomeActivity", baseError.toString());
                    }
                });
            } else {
                release();
                if (otaCallback != null) {
                    otaCallback.mutualFail(i);
                }
            }

        }

        @Override
        public void onReceiveCommand(BluetoothDevice bluetoothDevice, CommandBase commandBase) {
            Log.i(TAG, "onReceiveCommand:" + commandBase.toString());
        }

        @Override
        public void onA2dpStatus(BluetoothDevice bluetoothDevice, int i) {
            Log.i(TAG, "onA2dpStatus:" + i);
        }

        @Override
        public void onHfpStatus(BluetoothDevice bluetoothDevice, int i) {
            Log.i(TAG, "onHfpStatus:" + i);
        }

        @Override
        public void onMandatoryUpgrade(BluetoothDevice bluetoothDevice) {
            Log.i(TAG, "onMandatoryUpgrade");

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
                if (bleConnectStatusListener != null) {
                    bleConnectStatusListener.deviceDisconnect();
                }
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
        public void onNeedReconnect(String s, boolean b) {
            Log.i(TAG, "需要重连设备");
            //自定义开始重连设备
            /**
             if (jieliOtaManager.getBluetoothOption().isUseReconnect()) {
             new Timer().schedule(new TimerTask() {
            @Override public void run() {
            try {
            if (EABleManager.getInstance().getDeviceConnectState() == EABleConnectState.STATE_IDLE || EABleManager.getInstance().getDeviceConnectState() == EABleConnectState.STATE_DISCONNECT) {
            EABleManager.getInstance().connectToPeripheral(s, jieliOtaManager.mContext, bleConnectStatusListener, 400, dataReportCallback, motionDataReportCallback, autoConnectBt);
            }
            } catch (Exception e) {
            e.printStackTrace();
            }
            }
            }, 4000);

             }
             */
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
            release();

        }

        @Override
        public void onCancelOTA() {
            Log.i(TAG, "ota被取消");
            if (otaCallback != null) {
                otaCallback.mutualFail(0x18);
            }
            release();
        }

        @Override
        public void onError(BaseError baseError) {
            Log.i(TAG, "ota错误：" + baseError.toString());
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
                if (bleConnectStatusListener != null) {
                    bleConnectStatusListener.deviceDisconnect();
                }
            }

        }
    }


    public void setDeviceConnection(int stateCode) {
        if (jieliOtaManager != null) {
            jieliOtaManager.onBtDeviceConnection(jieliOtaManager.getConnectedDevice(), stateCode);
        }
    }

}
