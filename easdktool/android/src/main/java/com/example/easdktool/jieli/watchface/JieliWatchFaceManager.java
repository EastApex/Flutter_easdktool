package com.example.easdktool.jieli.watchface;

import android.bluetooth.BluetoothDevice;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.text.TextUtils;
import android.util.Log;

import com.apex.ax_bluetooth.callback.BleConnectStatusListener;
import com.apex.ax_bluetooth.callback.GeneralCallback;
import com.apex.ax_bluetooth.callback.JieliConnectListener;
import com.apex.ax_bluetooth.callback.JieliDataCallback;
import com.apex.ax_bluetooth.callback.OtaCallback;
import com.apex.ax_bluetooth.core.EABleManager;
import com.apex.ax_bluetooth.enumeration.EABleConnectState;
import com.apex.ax_bluetooth.model.EABleCustom707Dial;
import com.apex.ax_bluetooth.model.EABleWatchInfo;
import com.apex.ax_bluetooth.utils.LogUtils;
import com.google.gson.Gson;
import com.jieli.bmp_convert.BmpConvert;
import com.jieli.bmp_convert.OnConvertListener;
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

import java.io.File;
import java.io.FileOutputStream;
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
        addConnectListener();
        if (onWatchCallback == null) {
            onWatchCallback = new OnWatchCallback() {
                @Override
                public void onWatchSystemInit(int i) {
                    super.onWatchSystemInit(i);
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
                        /**
                         EABleManager.getInstance().disconnectPeripheral();
                         if (bleConnectStatusListener != null) {
                         bleConnectStatusListener.deviceDisconnect();
                         }
                         release();
                         */

                    }

                }

                @Override
                public void onWatchSystemException(BluetoothDevice bluetoothDevice, int i) {
                    super.onWatchSystemException(bluetoothDevice, i);
                    Log.i(TAG, "手表系统异常：" + i);
                    restoreWatchSystem(new OnFatFileProgressListener() {
                        @Override
                        public void onStart(String s) {

                        }

                        @Override
                        public void onProgress(float v) {

                        }

                        @Override
                        public void onStop(int i) {

                        }
                    });
                    initSuccess = 3;//初始化失败
                    if (jieliWatchInitCallback != null) {
                        jieliWatchInitCallback.initResult(3);
                    }
                    /**
                     EABleManager.getInstance().disconnectPeripheral();
                     if (bleConnectStatusListener != null) {
                     bleConnectStatusListener.deviceDisconnect();
                     }
                     release();
                     */

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
                    super.onRcspInit(bluetoothDevice, b);
                    if (b) {
                        Log.i(TAG, "Rcsp 初始化成功");
                    } else {
                        Log.i(TAG, "Rcsp 初始化失败，断开蓝牙连接");
                        initSuccess = 3;//初始化失败
                        if (jieliWatchInitCallback != null) {
                            jieliWatchInitCallback.initResult(3);
                        }
                        /**
                         EABleManager.getInstance().disconnectPeripheral();
                         if (bleConnectStatusListener != null) {
                         bleConnectStatusListener.deviceDisconnect();
                         }
                         release();
                         */
                    }

                }


            };
            /**
             * 注册手表表盘相关回调
             */
            registerOnWatchCallback(onWatchCallback);
            initSuccess = 1;//正在初始化
        }


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
                Log.i(TAG, "设备认证失败");
                initSuccess = 3;//初始化失败
                if (jieliWatchInitCallback != null) {
                    jieliWatchInitCallback.initResult(3);
                }
                //设备认证失败
                /**
                 EABleManager.getInstance().disconnectPeripheral();
                 if (bleConnectStatusListener != null) {
                 bleConnectStatusListener.deviceDisconnect();
                 }
                 release();
                 */

            }
        });
        mRcspAuth.stopAuth(getConnectedDevice(), false); //清除旧的设备认证
        //  RcspAuth.setAuthTimeout(10000);
        startAuth = true;
        boolean ret = mRcspAuth.startAuth(getConnectedDevice()); //开始设备认证, 结果是操作结果
    }

    //查询手表表盘
    public void getDialList(JieliDialCallback jieliDialCallback) {
        if (initSuccess != 2) {
            Log.i(TAG, "手表未初始化完成");
            if (initSuccess == 0) {
                if (jieliDialCallback != null) {
                    jieliDialCallback.error(new BaseError(0x19));
                }
                return;
            }
            if (initSuccess == 1) {
                if (jieliDialCallback != null) {
                    jieliDialCallback.error(new BaseError(0x1A));
                }
                return;
            }
            if (initSuccess == 3) {
                if (jieliDialCallback != null) {
                    jieliDialCallback.error(new BaseError(0x1B));
                }
                return;
            }

            return;
        }
        Log.i(TAG, "开始获取所有的表盘");
        listWatchList(new OnWatchOpCallback<ArrayList<FatFile>>() {
            @Override
            public void onSuccess(ArrayList<FatFile> fatFiles) {
                Log.i(TAG, "获取所有的表盘成功");
                List<FatFile> fatFileList = getWatchDialList(fatFiles);
                if (fatFileList != null && !fatFileList.isEmpty()) {
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

    private void getCustomDialModelInfo(JieliDialCallback jieliDialCallback) {
        if (initSuccess != 2) {
            if (initSuccess == 0) {
                if (jieliDialCallback != null) {
                    jieliDialCallback.error(new BaseError(0x19));
                }
                return;
            }
            if (initSuccess == 1) {
                if (jieliDialCallback != null) {
                    jieliDialCallback.error(new BaseError(0x1A));
                }
                return;
            }
            if (initSuccess == 3) {
                if (jieliDialCallback != null) {
                    jieliDialCallback.error(new BaseError(0x1B));
                }
                return;
            }

            return;
        }
        Log.i(TAG, "开始获取所有的表盘");
        listWatchList(new OnWatchOpCallback<ArrayList<FatFile>>() {
            @Override
            public void onSuccess(ArrayList<FatFile> fatFiles) {
                Log.i(TAG, "获取所有的表盘成功");
                List<FatFile> fatFileList = getCustomMode(fatFiles);
                if (fatFileList != null && !fatFileList.isEmpty()) {
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
                    Log.i(TAG, "没有自定义表盘");
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

    public void customDialBack(final String backFile, final OtaCallback otaCallback, final EABleWatchInfo eaBleWatchInfo, final int selectStyle) {
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
        boolean isValid = isValidImage(backFile);
        if (!isValid || eaBleWatchInfo == null || eaBleWatchInfo.getLcd_full_w() <= 0 || eaBleWatchInfo.getLcd_full_h() <= 0) {
            if (otaCallback != null) {
                otaCallback.mutualFail(0x10);
            }
            return;
        }
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    //将文件转换成合适大小
                    String transPath = imageTransformation(backFile, eaBleWatchInfo.getLcd_full_w(), eaBleWatchInfo.getLcd_full_h(), eaBleWatchInfo.getLcd_preview_radius(), eaBleWatchInfo.getLcd_full_type());
                    if (TextUtils.isEmpty(transPath)) {
                        if (otaCallback != null) {
                            otaCallback.mutualFail(0x10);
                        }
                        return;
                    }
                    final File tFile = new File(transPath);
                    if (tFile == null || !tFile.exists() || !tFile.isFile()) {
                        if (otaCallback != null) {
                            otaCallback.mutualFail(0x10);
                        }
                        return;
                    }
                    File outFile = new File(tFile.getParent(), "bgp_w000");
                    if (outFile.exists()) {
                        outFile.delete();
                    }
                    outFile.createNewFile();
                    final BmpConvert bmpConvert = new BmpConvert();
                    bmpConvert.bitmapConvert(BmpConvert.TYPE_707N_RGB, transPath, outFile.getAbsolutePath(), new OnConvertListener() {
                        @Override
                        public void onStart(String s) {

                        }

                        @Override
                        public void onStop(boolean b, String s) {
                            tFile.delete();
                            bmpConvert.release();
                            if (b) {
                                LogUtils.i(TAG, "开始获取模板表盘，BmpConvert:" + s);
                                startInstallCustomDial(otaCallback, selectStyle, s);
                            } else {
                                if (otaCallback != null) {
                                    otaCallback.mutualFail(0x10);
                                }
                                return;
                            }
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private boolean isValidImage(final String backFile) {
        if (TextUtils.isEmpty(backFile)) {
            return false;
        }
        File bFile = new File(backFile);
        if (!bFile.exists()) {
            return false;
        }
        if (!bFile.isFile()) {
            return false;
        }
        String[] fName = bFile.getName().split("\\.");
        if (fName.length < 2) {
            return false;
        }
        if (fName[1].equalsIgnoreCase("jpg") || fName[1].equalsIgnoreCase("jpeg") || fName[1].equalsIgnoreCase("png")) {
            return true;
        }
        return false;

    }

    /**
     * 将原始图片转换成屏幕大小，并保存，保存文件固定位bgp_w000;
     *
     * @param filePath
     * @param screenWidth
     * @param screenHigh
     * @param corner
     * @param screenType
     * @return
     */
    private String imageTransformation(String filePath, int screenWidth, int screenHigh, int corner, int screenType) {
        try {
            if (screenWidth <= 0 || screenHigh <= 0) {
                return null;
            }
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inScaled = false;
            Bitmap bitmap = BitmapFactory.decodeFile(filePath, options);
            if (bitmap == null) {
                return null;
            }
            //将图片转换成屏幕大小
            int bitmapWidth = bitmap.getWidth();
            int bitmapHigh = bitmap.getHeight();
            if (bitmapWidth < screenWidth || bitmapHigh < screenHigh) {
                float scaleX = screenWidth * 1.0f / bitmapWidth;
                float scaleY = screenHigh * 1.0f / bitmapHigh;
                float maxScale = Math.max(scaleX, scaleY);
                Matrix matrix = new Matrix();
                matrix.setScale(maxScale, maxScale);
                bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, false);
                LogUtils.e(TAG, "缩放之后图片的大小,宽:" + bitmap.getWidth() + ",高:" + bitmap.getHeight());
            }
            //图片按屏幕大小进行截取
            Bitmap outBitmap = Bitmap.createBitmap(screenWidth, screenHigh, Bitmap.Config.ARGB_8888);
            bitmapWidth = bitmap.getWidth();
            bitmapHigh = bitmap.getHeight();
            //将图片转换成屏幕形状
            Canvas canvas = new Canvas(outBitmap);
            Paint paint = new Paint();
            paint.setAntiAlias(true);
            if (screenType == 1) {//表示圆形
                int raduis = Math.min(screenHigh, screenWidth) / 2;
                canvas.drawCircle(screenWidth / 2, screenHigh / 2, raduis, paint);
                paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            } else {
                RectF rectF = new RectF(0, 0, screenWidth, screenHigh);
                canvas.drawRoundRect(rectF, corner, corner, paint);
                paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            }
            canvas.drawBitmap(bitmap, 0, 0, paint);
            //将图片进行保存
            File file = new File(filePath);
            String fileType = file.getName().split("\\.")[1];
            String dir = file.getParent();
            File outFile = new File(dir, "bgp" + "." + fileType);
            if (outFile.exists() && outFile.isFile()) {
                outFile.delete();
            }
            outFile.createNewFile();
            FileOutputStream stream = new FileOutputStream(outFile);
            if (fileType.equalsIgnoreCase("png")) {
                outBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            } else {
                outBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            }
            stream.flush();
            stream.close();
            bitmap.recycle();
            outBitmap.recycle();
            outBitmap = null;
            bitmap = null;
            return outFile.getAbsolutePath();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    private void startInstallCustomDial(final OtaCallback otaCallback, final int style, final String dialPath) {
        modifyStyle(style, otaCallback, null, dialPath, null);
        /**
         getCustomDialModelInfo(new JieliDialCallback() {
        @Override public void jieliDial(List<JieliWatchInfo> watchInfos) {
        if (watchInfos == null || watchInfos.isEmpty()) {
        File cFile = new File(dialPath);
        if (cFile.exists() && cFile.isFile()) {
        cFile.delete();
        }
        if (otaCallback != null) {
        otaCallback.mutualFail(0x16);
        }
        return;
        }
        final JieliWatchInfo jieliWatchInfo = watchInfos.get(0);
        modifyStyle(style, otaCallback, jieliWatchInfo.customBgFatPath, dialPath, jieliWatchInfo.path);

        }

        @Override public void error(BaseError error) {
        File cFile = new File(dialPath);
        if (cFile.exists() && cFile.isFile()) {
        cFile.delete();
        }
        if (otaCallback != null) {
        otaCallback.mutualFail(error.getCode());
        }

        }
        });
         */

    }

    /**
     * @param style       要设置的风格号
     * @param otaCallback
     * @param customPath  模板表盘原始的背景
     * @param filePath    下发的背景路径
     * @param orPath      模板表盘的原始路径
     */
    private void modifyStyle(final int style, final OtaCallback otaCallback, final String customPath, final String filePath, final String orPath) {
        EABleCustom707Dial eaBleCustom707Dial = new EABleCustom707Dial();
        eaBleCustom707Dial.setOtaStyle(0);
        eaBleCustom707Dial.setDialStyle(style);
        EABleManager.getInstance().setCustom707DialInfo(eaBleCustom707Dial, new GeneralCallback() {
            @Override
            public void result(boolean success, int error) {
                Log.i(TAG, "设置风格成功：" + success);
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        changeDial(orPath, otaCallback, filePath, customPath);
                    }
                }.start();


            }

            @Override
            public void mutualFail(int errorCode) {
                File cFile = new File(filePath);
                if (cFile.exists() && cFile.isFile()) {
                    cFile.delete();
                }
                if (otaCallback != null) {
                    otaCallback.mutualFail(errorCode);
                }
            }
        });
    }

    /**
     * @param otaCallback
     * @param filePath    下发的背景路径
     * @param orPath      模板表盘路径
     */
    private void deleteDialBack(final OtaCallback otaCallback, final String filePath, final String orPath) {
        enableCustomWatchBg(null, new OnWatchOpCallback<FatFile>() {
            @Override
            public void onSuccess(FatFile fatFile) {
                addDialBack(filePath, otaCallback, orPath);
            }

            @Override
            public void onFailed(BaseError baseError) {
                File cFile = new File(filePath);
                if (cFile.exists() && cFile.isFile()) {
                    cFile.delete();
                }
                if (otaCallback != null) {
                    otaCallback.mutualFail(baseError.getCode());
                }
            }
        });
    }

    /**
     * 下发自定义表盘
     *
     * @param dialPath    传送到手表的背景路径
     * @param otaCallback
     * @param orDialPath  模板表盘所在的路径
     */
    private void addDialBack(final String dialPath, final OtaCallback otaCallback, final String orDialPath) {
        createWatchFile(dialPath, true, new OnFatFileProgressListener() {
            @Override
            public void onStart(String s) {
                if (otaCallback != null) {
                    otaCallback.progress(0);
                }

            }

            @Override
            public void onProgress(float v) {
                if (otaCallback != null) {
                    otaCallback.progress((int) v);
                }
            }

            @Override
            public void onStop(int i) {


                if (i == 0) {
                    /**
                     if (otaCallback != null) {
                     otaCallback.success();
                     }
                     */
                    associationPointDialBack(dialPath, otaCallback);
                } else {
                    if (otaCallback != null) {
                        otaCallback.mutualFail(i);
                    }
                    File cFile = new File(dialPath);
                    if (cFile.exists() && cFile.isFile()) {
                        cFile.delete();
                    }
                }

            }
        });
    }

    /**
     * @param orPath      模板表盘路径
     * @param otaCallback
     * @param path        下发背景路径
     */
    private void changeDial(final String orPath, final OtaCallback otaCallback, final String path, final String cusImage) {
        /**
         replaceWatchFile(path, new OnFatFileProgressListener() {
        @Override public void onStart(String s) {
        if (otaCallback != null) {
        otaCallback.progress(0);
        }
        }

        @Override public void onProgress(float v) {
        if (otaCallback != null) {
        otaCallback.progress((int) v);
        }
        }

        @Override public void onStop(int i) {
        if (i == 0) {

        if (otaCallback != null) {
        otaCallback.success();
        }
        File cFile = new File(path);
        if (cFile.exists() && cFile.isFile()) {
        cFile.delete();
        }

        } else {
        if (otaCallback != null) {
        otaCallback.mutualFail(i);
        }
        File cFile = new File(path);
        if (cFile.exists() && cFile.isFile()) {
        cFile.delete();
        }
        }
        }
        });
         */

        setCurrentWatchInfo(orPath, new OnWatchOpCallback<FatFile>() {
            @Override
            public void onSuccess(FatFile fatFile) {
                if (TextUtils.isEmpty(cusImage)) {
                    addDialBack(path, otaCallback, orPath);
                } else {
                    deleteDialBack(otaCallback, path, orPath);
                }


            }

            @Override
            public void onFailed(BaseError baseError) {
                File cFile = new File(path);
                if (cFile.exists() && cFile.isFile()) {
                    cFile.delete();
                }
                if (otaCallback != null) {
                    otaCallback.mutualFail(baseError.getCode());
                }

            }
        });
    }

    /**
     * 关联到当前表盘
     *
     * @param dialPath 背景路径
     */
    private void associationPointDialBack(final String dialPath, final OtaCallback otaCallback) {//关联到表盘
        enableCustomWatchBg(FatUtil.getFatFilePath(dialPath), new OnWatchOpCallback<FatFile>() {
            @Override
            public void onSuccess(FatFile fatFile) {
                Log.i(TAG, "关联成功自定义背景成功");
                if (otaCallback != null) {
                    otaCallback.success();
                }
                File cFile = new File(dialPath);
                if (cFile.exists() && cFile.isFile()) {
                    cFile.delete();
                }
            }

            @Override
            public void onFailed(BaseError baseError) {
                Log.i(TAG, "关联自定义背景失败：" + baseError.getMessage());
                if (otaCallback != null) {
                    otaCallback.mutualFail(baseError.getCode());
                }
                File cFile = new File(dialPath);
                if (cFile.exists() && cFile.isFile()) {
                    cFile.delete();
                }
            }
        });
    }

    /**
     * 不改背景，直接修改自定风格
     *
     * @param styleType
     * @param generalCallback
     */
    public void changeCustomDialStyle(final int styleType, final GeneralCallback generalCallback) {
        getCustomDialModelInfo(new JieliDialCallback() {
            @Override
            public void jieliDial(List<JieliWatchInfo> watchInfos) {
                if (watchInfos != null && !watchInfos.isEmpty()) {
                    final JieliWatchInfo jieliWatchInfo = watchInfos.get(0);
                    EABleCustom707Dial eaBleCustom707Dial = new EABleCustom707Dial();
                    eaBleCustom707Dial.setDialStyle(styleType);
                    eaBleCustom707Dial.setOtaStyle(1);
                    EABleManager.getInstance().setCustom707DialInfo(eaBleCustom707Dial, new GeneralCallback() {
                        @Override
                        public void result(boolean success, int error) {
                            if (success) {
                                setCurrentWatchInfo(jieliWatchInfo.path, new OnWatchOpCallback<FatFile>() {
                                    @Override
                                    public void onSuccess(FatFile fatFile) {
                                        if (generalCallback != null) {
                                            generalCallback.result(true, 0);
                                        }
                                    }

                                    @Override
                                    public void onFailed(BaseError baseError) {
                                        if (generalCallback != null) {
                                            generalCallback.mutualFail(baseError.getCode());
                                        }
                                    }
                                });
                            } else {
                                if (generalCallback != null) {
                                    generalCallback.result(false, error);
                                }
                            }
                        }

                        @Override
                        public void mutualFail(int errorCode) {
                            if (generalCallback != null) {
                                generalCallback.mutualFail(errorCode);
                            }

                        }
                    });

                } else {
                    if (generalCallback != null) {
                        generalCallback.mutualFail(0x16);
                    }
                }

            }

            @Override
            public void error(BaseError error) {
                if (generalCallback != null) {
                    generalCallback.mutualFail(error.getCode());
                }
            }
        });
    }

    private List<FatFile> getCustomMode(List<FatFile> fatFiles) {
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
            if ((fName.startsWith("WATCH") || fName.startsWith("watch")) && fName.equalsIgnoreCase("watch9")) {
                result.add(fatFile);
            }
        }
        return result;
    }

}
