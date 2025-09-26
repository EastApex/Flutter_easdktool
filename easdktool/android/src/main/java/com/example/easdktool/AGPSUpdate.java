package com.example.easdktool;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import com.alibaba.fastjson.JSONObject;
import com.apex.ax_bluetooth.callback.FeaturesCallback;
import com.apex.ax_bluetooth.core.EABleManager;
import com.apex.ax_bluetooth.enumeration.EABleConnectState;
import com.apex.ax_bluetooth.enumeration.QueryWatchInfoType;
import com.apex.ax_bluetooth.model.EABleFeatures;
import com.apex.ax_bluetooth.model.EABleOta;
import com.apex.ax_bluetooth.utils.LogUtils;
import com.example.easdktool.been.OtaProgress;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Spliterators;

import io.flutter.plugin.common.MethodChannel;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class AGPSUpdate {
    final String TAG = this.getClass().getSimpleName();
    private String[] urlList;
    private Map<String, String> cacheList;
    private Handler mHandler;
    private final String kProgress = "Progress";
    private MethodChannel channel;
    byte[] dataByte;

    public AGPSUpdate(MethodChannel channel) {
        this.channel = channel;
        /**
         if (BuildConfig.DEBUG) {
         urlList = new String[]{"http://apexwear-dev.oss-cn-shenzhen.aliyuncs.com/AGPS/f1e1G7.pgl",
         "http://apexwear-dev.oss-cn-shenzhen.aliyuncs.com/AGPS/f1e1C7.pgl",
         "http://apexwear-dev.oss-cn-shenzhen.aliyuncs.com/AGPS/f1e1J7.pgl"};

         } else {
         */
        urlList = new String[]{"https://apexwear-xinjiapo.oss-ap-southeast-1.aliyuncs.com/AGPS/f1e1G7.pgl",
                "https://apexwear-xinjiapo.oss-ap-southeast-1.aliyuncs.com/AGPS/f1e1C7.pgl",
                "https://apexwear-xinjiapo.oss-ap-southeast-1.aliyuncs.com/AGPS/f1e1J7.pgl"};
        // }
    }


    public void startUpdate(final Context mContext) {

        EABleManager.getInstance().queryWatchInfo(QueryWatchInfoType.features, new FeaturesCallback() {
            @Override
            public void featuresList(EABleFeatures eaBleFeatures) {
                if (eaBleFeatures != null) {
                    if (eaBleFeatures.getGps_setting() == 1) {
                        new Thread() {
                            @Override
                            public void run() {
                                super.run();
                                //  File file = getLocalCacheFile(mContext);
                                //  if (isTimeOut(file)) {
                                LogUtils.i(TAG, "重新下载");
                                //重新下载
                                startDownAgps(mContext);
                                packageAgpsFile(mContext);

                                //  }

                                if (mHandler == null) {
                                    mHandler = new Handler(Looper.getMainLooper());
                                }
                                mHandler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        final File nFile = getLocalCacheFile(mContext);
                                        if (nFile == null || !nFile.exists() || !nFile.isFile()) {
                                            LogUtils.i(TAG, "本地缓存的文件不存在");
                                            if (channel != null) {
                                                OtaProgress otaProgress = new OtaProgress();
                                                otaProgress.isSuccess = false;
                                                otaProgress.progress = -1;
                                                otaProgress.errorType = 0x10;
                                                channel.invokeMethod(kProgress, JSONObject.toJSONString(otaProgress));
                                            }
                                        } else {
                                            LogUtils.i(TAG, "本地缓存的文件存在");
                                            List<EABleOta> otaList = new ArrayList<>();
                                            EABleOta eaBleOta = new EABleOta();
                                            eaBleOta.setOtaType(EABleOta.OtaType.agps);
                                            eaBleOta.setPop(false);
                                            eaBleOta.setFilePath(nFile.getAbsolutePath());
                                            otaList.add(eaBleOta);
                                            new OTAFunction(channel).startAgps2Watch(otaList);


                                        }
                                        mHandler = null;
                                    }
                                });

                            }
                        }.start();


                    } else {
                        LogUtils.i(TAG, "不支持GPS功能");

                    }
                }

            }

            @Override
            public void mutualFail(int i) {
                LogUtils.i(TAG, "查询功能信息失败:" + i);
            }
        });


    }

    /**
     * 缓存数据是否已超时
     *
     * @return
     */
    private boolean isTimeOut(File f) {

        long cacheTime = 0;
        try {
            if (f != null && f.exists()) {

                //获取缓存时间
                String cTime = f.getName();
                if (!TextUtils.isEmpty(cTime)) {
                    cacheTime = Long.parseLong(cTime);

                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (Math.abs(cacheTime - System.currentTimeMillis()) >= 24 * 60 * 60 * 1000) {//间隔一天
            LogUtils.i(TAG, "超时");
            return true;
        }
        return false;
    }

    private File getLocalCacheFile(Context mContext) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("AX", Context.MODE_PRIVATE);
        String cacheAgpsFilePath = sharedPreferences.getString("agps", null);
        if (TextUtils.isEmpty(cacheAgpsFilePath)) {
            return null;
        }
        return new File(cacheAgpsFilePath);
    }

    private void saveLocalCacheFile(Context mContext, String value) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("AX", Context.MODE_PRIVATE);
        sharedPreferences.edit().putString("agps", value).commit();

    }

    private void startDownAgps(Context mContext) {
        try {
            if (urlList == null) {
                return;
            }
            OkHttpClient client = new OkHttpClient();
            cacheList = new HashMap<>();
            for (int i = 0; i < urlList.length; i++) {
                if (!getNetStatus(mContext)) {
                    return;
                }
                Request request = new Request.Builder().url(urlList[i]).build();
                Call call = client.newCall(request);
                Response response = call.execute();
                if (response != null && response.isSuccessful()) {
                    ResponseBody responseBody = response.body();
                    if (responseBody != null) {
                        String fName = "gps";
                        if (urlList[i].contains("f1e1G7")) {
                            fName = "gps";
                        } else if (urlList[i].contains("f1e1C7")) {
                            fName = "bd";
                        } else {
                            fName = "qzss";
                        }
                        String filePath = getCacheFilePath(mContext, fName);
                        InputStream inputStream = responseBody.byteStream();

                        if (inputStream != null) {
                            FileOutputStream savePath = new FileOutputStream(filePath);
                            int len;
                            byte[] readByte = new byte[1024];
                            while ((len = inputStream.read(readByte)) != -1) {
                                savePath.write(readByte, 0, len);
                            }
                            inputStream.close();
                            savePath.flush();
                            savePath.close();
                            cacheList.put(fName, filePath);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private boolean getNetStatus(Context mContext) {
        ConnectivityManager connectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            //如果仅仅是用来判断网络连接
            //则可以使用 cm.getActiveNetworkInfo().isAvailable();
            NetworkInfo info = connectivityManager.getActiveNetworkInfo();
            if (info != null) {
                return info.isConnected();
            }
        }
        return false;
    }

    private String getCacheFilePath(Context mContext, String fName) {
        try {
            String dirPath;
            if (mContext.getExternalCacheDir() != null) {
                dirPath = mContext.getExternalFilesDir("agps").getPath();
            } else {
                dirPath = mContext.getFilesDir().getPath() + "/agps";
            }
            if (!TextUtils.isEmpty(dirPath)) {
                File file = new File(dirPath);
                if (file == null || !file.exists()) {
                    file.mkdirs();
                }
                String filePath = dirPath + "/" + fName;
                File pFile = new File(filePath);
                if (pFile != null && pFile.exists() && pFile.isFile()) {
                    pFile.delete();
                }
                pFile.createNewFile();
                return filePath;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    private void packageAgpsFile(Context mContext) {
        try {
            if (cacheList == null || cacheList.isEmpty() || cacheList.size() < 3) {
                LogUtils.i(TAG, "下载的文件有问题");
                return;
            }
            byte[] headByte = new byte[36];
            int currentTime = (int) (System.currentTimeMillis() / 1000);
            headByte[0] = (byte) currentTime;
            headByte[1] = (byte) (currentTime >> 8);
            headByte[2] = (byte) (currentTime >> 16);
            headByte[3] = (byte) (currentTime >> 24);

            int flag = 1;
            packageFile(flag, cacheList.get("gps"), headByte);
            flag += 1;
            packageFile(flag, cacheList.get("qzss"), headByte);
            flag += 1;
            packageFile(flag, cacheList.get("bd"), headByte);
            if (dataByte != null) {
                byte[] tempByte = new byte[dataByte.length + headByte.length];
                System.arraycopy(headByte, 0, tempByte, 0, headByte.length);
                System.arraycopy(dataByte, 0, tempByte, headByte.length, dataByte.length);
                headByte = null;
                dataByte = null;
                File oldCache = getLocalCacheFile(mContext);
                if (oldCache != null && oldCache.exists() && oldCache.isFile()) {
                    oldCache.delete();
                }

                //将文件保存
                String fPath = getCacheFilePath(mContext, System.currentTimeMillis() + "");
                File sFile = new File(fPath);
                if (sFile.exists()) {
                    sFile.delete();
                }
                sFile.createNewFile();
                FileOutputStream fileOutputStream = new FileOutputStream(fPath);
                fileOutputStream.write(tempByte, 0, tempByte.length);
                fileOutputStream.flush();
                fileOutputStream.close();
                saveLocalCacheFile(mContext, fPath);
                LogUtils.i(TAG, "保存最终文件到本地成功");
            } else {
                LogUtils.i(TAG, "封装的文件不存在");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void packageFile(int flag, String filePath, byte[] headByte) {
        if (!TextUtils.isEmpty(filePath)) {
            File f = new File(filePath);
            if (f.exists() && f.length() > 0) {
                try {
                    LogUtils.e(TAG, "当前处理文件:" + filePath);
                    //读取数据
                    FileInputStream fileInputStream = new FileInputStream(f);
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    int len;
                    byte[] readByte = new byte[1024];
                    while ((len = fileInputStream.read(readByte)) != -1) {
                        byteArrayOutputStream.write(readByte, 0, len);
                    }
                    byte[] fileByte = byteArrayOutputStream.toByteArray();
                    if (fileByte != null) {
                        byte[] fByte = new byte[fileByte.length + 1];
                        if (f.getName().contains("gps")) {
                            fByte[0] = 0x00;
                        } else if (f.getName().contains("bd")) {
                            fByte[0] = 0x03;
                        } else if (f.getName().contains("qzss")) {
                            fByte[0] = 0x05;
                        }
                        System.arraycopy(fileByte, 0, fByte, 1, fileByte.length);
                        fileByte = null;
                        int startIndex, startAddress;
                        if (dataByte == null) {
                            LogUtils.i(TAG, "创建数组");
                            dataByte = new byte[fByte.length];
                            startIndex = 0;
                            startAddress = headByte.length;
                        } else {
                            LogUtils.i(TAG, "改变数组");
                            byte[] tempByte = new byte[dataByte.length];
                            System.arraycopy(dataByte, 0, tempByte, 0, dataByte.length);
                            dataByte = new byte[tempByte.length + fByte.length];
                            System.arraycopy(tempByte, 0, dataByte, 0, tempByte.length);
                            startIndex = tempByte.length;
                            startAddress = headByte.length + tempByte.length;
                            tempByte = null;

                        }
                        System.arraycopy(fByte, 0, dataByte, startIndex, fByte.length);
                        fByte = null;
                        headByte[flag * 4] = (byte) startAddress;
                        headByte[flag * 4 + 1] = (byte) (startAddress >> 8);
                        headByte[flag * 4 + 2] = (byte) (startAddress >> 16);
                        headByte[flag * 4 + 3] = (byte) (startAddress >> 24);

                    } else {
                        LogUtils.i(TAG, "单个的文件转字节不存在");
                    }
                    byteArrayOutputStream.close();
                    fileInputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
    }

}
