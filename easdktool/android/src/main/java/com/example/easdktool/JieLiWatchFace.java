package com.example.easdktool;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import com.alibaba.fastjson.JSONObject;
import com.apex.ax_bluetooth.callback.GeneralCallback;
import com.apex.ax_bluetooth.callback.OtaCallback;
import com.apex.ax_bluetooth.callback.WatchInfoCallback;
import com.apex.ax_bluetooth.core.EABleManager;
import com.apex.ax_bluetooth.enumeration.QueryWatchInfoType;
import com.apex.ax_bluetooth.model.EABleWatchInfo;
import com.apex.ax_bluetooth.utils.LogUtils;
import com.example.easdktool.been.OtaProgress;
import com.example.easdktool.db.DailyData;
import com.example.easdktool.db.DataManager;
import com.example.easdktool.jieli.watchface.JieliDialCallback;
import com.example.easdktool.jieli.watchface.JieliWatchFaceManager;
import com.example.easdktool.jieli.watchface.JieliWatchInfo;
import com.jieli.jl_rcsp.model.base.BaseError;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.flutter.plugin.common.MethodChannel;

public class JieLiWatchFace {
    final String TAG = this.getClass().getSimpleName();
    Handler mHandler;
    private MethodChannel channel;
    final String kAddJieLiWatchFace = "AddJieLiWatchFace";
    final String kDeleteJieLiWatchFace = "DeleteJieLiWatchFace";
    final String kGetJieLiWatchFace = "GetJieLiWatchFace";

    public JieLiWatchFace(MethodChannel channel) {
        this.channel = channel;
    }

    public void addWatchFace(final String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            if (mHandler == null) {
                mHandler = new Handler(Looper.getMainLooper());
            }
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    if (channel != null) {
                        OtaProgress otaProgress = new OtaProgress();
                        otaProgress.isSuccess = -1;
                        otaProgress.progress = -1;
                        otaProgress.errorType = 0x10;
                        channel.invokeMethod(kAddJieLiWatchFace, JSONObject.toJSONString(otaProgress));
                    }
                    mHandler = null;
                }
            });
            return;
        }
        File file = new File(filePath);
        if (file == null || !file.exists() || !file.isFile()) {
            if (mHandler == null) {
                mHandler = new Handler(Looper.getMainLooper());
            }
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    if (channel != null) {
                        OtaProgress otaProgress = new OtaProgress();
                        otaProgress.isSuccess = -1;
                        otaProgress.progress = -1;
                        otaProgress.errorType = 0x10;
                        channel.invokeMethod(kAddJieLiWatchFace, JSONObject.toJSONString(otaProgress));
                    }
                    mHandler = null;
                }
            });
            return;
        }
        new Thread(){
            @Override
            public void run() {
                super.run();
                File nFile = new File(filePath);
                String name = getInternalName(nFile);
                if (TextUtils.isEmpty(name)) {
                    returnFailData(0x10);
                    return;
                }
                String orName = nFile.getName();
                String endPath = filePath;
                if (!name.equals(orName)) {//需要给文件重命名
                    endPath = renameFile(file, name);
                }
                if (TextUtils.isEmpty(endPath)) {
                    returnFailData(0x10);
                    return;
                }
                final File tFile = new File(endPath);
                if (tFile == null || !tFile.exists() || !tFile.isFile()) {
                    returnFailData(0x10);
                    return;
                }
                final String lastPath = endPath;
                add707WatchFace(lastPath);
            }
        }.start();
        /**
        EABleManager.getInstance().queryWatchInfo(QueryWatchInfoType.watch_info, new WatchInfoCallback() {
            @Override
            public void watchInfo(final EABleWatchInfo eaBleWatchInfo) {
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        //修改判断文件名
                        File nFile = new File(filePath);
                        String name = getInternalName(nFile);
                        if (TextUtils.isEmpty(name)) {
                            returnFailData(0x10);
                            return;
                        }
                        String orName = nFile.getName();
                        String endPath = filePath;
                        if (!name.equals(orName)) {//需要给文件重命名
                            endPath = renameFile(file, name);
                        }
                        if (TextUtils.isEmpty(endPath)) {
                            returnFailData(0x10);
                            return;
                        }
                        final File tFile = new File(endPath);
                        if (tFile == null || !tFile.exists() || !tFile.isFile()) {
                            returnFailData(0x10);
                            return;
                        }
                        final String lastPath = endPath;
                        if (eaBleWatchInfo != null) {
                            final int dialNum = eaBleWatchInfo.getEx_dial_num();

                            JieliWatchFaceManager.getInstance().getDialList(new JieliDialCallback() {
                                @Override
                                public void jieliDial(List<JieliWatchInfo> watchInfos) {
                                    if (watchInfos != null) {
                                        if (dialNum <= watchInfos.size()) {
                                            if (mHandler == null) {
                                                mHandler = new Handler(Looper.getMainLooper());
                                            }
                                            mHandler.post(new Runnable() {
                                                @Override
                                                public void run() {
                                                    if (channel != null) {
                                                        OtaProgress otaProgress = new OtaProgress();
                                                        otaProgress.isSuccess = -1;
                                                        otaProgress.progress = -1;
                                                        otaProgress.errorType = 0x1C;
                                                        channel.invokeMethod(kAddJieLiWatchFace, JSONObject.toJSONString(otaProgress));
                                                    }
                                                    mHandler = null;
                                                }
                                            });
                                            return;
                                        }
                                    }
                                    add707WatchFace(lastPath);

                                }

                                @Override
                                public void error(BaseError error) {

                                }
                            });
                            return;
                        }
                        add707WatchFace(lastPath);
                    }
                }.start();

            }

            @Override
            public void mutualFail(int i) {
                if (mHandler == null) {
                    mHandler = new Handler(Looper.getMainLooper());
                }
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (channel != null) {
                            OtaProgress otaProgress = new OtaProgress();
                            otaProgress.isSuccess = -1;
                            otaProgress.progress = -1;
                            otaProgress.errorType = 0x10;
                            channel.invokeMethod(kAddJieLiWatchFace, JSONObject.toJSONString(otaProgress));
                        }
                        mHandler = null;
                    }
                });
            }
        });
         */

    }

    private String renameFile(File orFile, String name) {
        try {
            String orDir = orFile.getParent();
            File file = new File(orDir, name);
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
            FileInputStream fileInputStream = new FileInputStream(orFile);
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            byte[] readByte = new byte[1024];
            int readSize;
            while ((readSize = fileInputStream.read(readByte)) != -1) {
                fileOutputStream.write(readByte, 0, readSize);
            }
            fileOutputStream.close();
            fileInputStream.close();
            return file.getAbsolutePath();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    private void returnFailData(final int error) {
        if (mHandler == null) {
            mHandler = new Handler(Looper.getMainLooper());
        }
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (channel != null) {
                    OtaProgress otaProgress = new OtaProgress();
                    otaProgress.isSuccess = -1;
                    otaProgress.progress = -1;
                    otaProgress.errorType = error;
                    channel.invokeMethod(kAddJieLiWatchFace, JSONObject.toJSONString(otaProgress));
                }
                mHandler = null;
            }
        });
    }

    private String getInternalName(final File f) {
        String name = null;
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(f, "r");
            byte[] bytes = new byte[400];
            randomAccessFile.read(bytes);
            String content = new String(bytes, Charset.forName("UTF-8"));
            if (!TextUtils.isEmpty(content)) {
                String splitContent = null;
                if (content.contains("sty")) {
                    splitContent = content.substring(0, content.indexOf("sty"));
                } else if (content.contains("res")) {
                    splitContent = content.substring(0, content.indexOf("res"));
                } else if (content.contains("str")) {
                    splitContent = content.substring(0, content.indexOf("str"));
                } else if (content.contains("json")) {
                    splitContent = content.substring(0, content.indexOf("json"));
                } else if (content.contains("view")) {
                    splitContent = content.substring(0, content.indexOf("view"));
                }
                if (!TextUtils.isEmpty(splitContent)) {
                    if (splitContent.contains("watch")) {
                        splitContent = splitContent.substring(splitContent.indexOf("watch"), splitContent.length());

                    } else if (splitContent.contains("WATCH")) {
                        splitContent = splitContent.substring(splitContent.indexOf("WATCH"), splitContent.length());
                    }

                }
                if (!TextUtils.isEmpty(splitContent)) {
                    if (splitContent.contains(".")) {
                        name = splitContent.split("\\.")[0];
                    }
                }
            }
            randomAccessFile.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return name;
    }

    private void add707WatchFace(final String filePath) {
        JieliWatchFaceManager.getInstance().addWatchFace(filePath, new OtaCallback() {
            @Override
            public void success() {
                if (mHandler == null) {
                    mHandler = new Handler(Looper.getMainLooper());
                }
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (channel != null) {
                            OtaProgress otaProgress = new OtaProgress();
                            otaProgress.isSuccess = 1;
                            otaProgress.progress = 100;
                            channel.invokeMethod(kAddJieLiWatchFace, JSONObject.toJSONString(otaProgress));
                        }
                        mHandler = null;
                    }
                });

            }

            @Override
            public void progress(final int progress) {
                if (mHandler == null) {
                    mHandler = new Handler(Looper.getMainLooper());
                }
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        LogUtils.i(TAG, "当前进度:" + progress);
                        if (channel != null) {
                            OtaProgress otaProgress = new OtaProgress();
                            otaProgress.isSuccess = 0;
                            otaProgress.progress = progress;
                            channel.invokeMethod(kAddJieLiWatchFace, JSONObject.toJSONString(otaProgress));
                        }
                        mHandler = null;
                    }
                });
            }

            @Override
            public void hisUpdateId(int[] ints) {

            }

            @Override
            public void mutualFail(final int i) {
                if (mHandler == null) {
                    mHandler = new Handler(Looper.getMainLooper());
                }
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (channel != null) {
                            OtaProgress otaProgress = new OtaProgress();
                            otaProgress.isSuccess = -1;
                            otaProgress.progress = -1;
                            otaProgress.errorType = i;
                            channel.invokeMethod(kAddJieLiWatchFace, JSONObject.toJSONString(otaProgress));
                        }
                        mHandler = null;
                    }
                });
            }
        });
    }

    public void deleteWatchFace(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            if (mHandler == null) {
                mHandler = new Handler(Looper.getMainLooper());
            }
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("respondCodeType", 1);
                    if (channel != null) {
                        channel.invokeMethod(kDeleteJieLiWatchFace, jsonObject.toJSONString());
                    }
                    mHandler = null;
                }
            });
            return;
        }
        new Thread() {
            @Override
            public void run() {
                super.run();
                JieliWatchFaceManager.getInstance().deleteWatchFace(filePath, new GeneralCallback() {
                    @Override
                    public void result(final boolean b, int i) {
                        if (mHandler == null) {
                            mHandler = new Handler(Looper.getMainLooper());
                        }
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                JSONObject jsonObject = new JSONObject();
                                jsonObject.put("respondCodeType", b ? 0 : 1);
                                if (channel != null) {
                                    channel.invokeMethod(kDeleteJieLiWatchFace, jsonObject.toJSONString());
                                }
                                mHandler = null;
                            }
                        });

                    }

                    @Override
                    public void mutualFail(int i) {
                        if (mHandler == null) {
                            mHandler = new Handler(Looper.getMainLooper());
                        }
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                JSONObject jsonObject = new JSONObject();
                                jsonObject.put("respondCodeType", 1);
                                if (channel != null) {
                                    channel.invokeMethod(kDeleteJieLiWatchFace, jsonObject.toJSONString());
                                }
                                mHandler = null;
                            }
                        });
                    }
                });
            }
        }.start();

    }

    public void getWatchFace() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                JieliWatchFaceManager.getInstance().getDialList(new JieliDialCallback() {
                    @Override
                    public void jieliDial(List<JieliWatchInfo> watchInfos) {
                        final List<Map<String, Object>> dataList = new ArrayList<>();
                        if (watchInfos != null && !watchInfos.isEmpty()) {
                            for (int i = 0; i < watchInfos.size(); i++) {
                                Map<String, Object> map = new HashMap<>();
                                map.put("name", watchInfos.get(i).name);
                                map.put("status", watchInfos.get(i).status);
                                map.put("bitmapUri", watchInfos.get(i).bitmapUri);
                                map.put("uuid", watchInfos.get(i).uuid);
                                map.put("version", watchInfos.get(i).version);
                                map.put("size", watchInfos.get(i).size);
                                map.put("fileUrl", watchInfos.get(i).fileUrl);
                                map.put("updateUUID", watchInfos.get(i).updateUUID);
                                map.put("updateVersion", watchInfos.get(i).updateVersion);
                                map.put("updateUrl", watchInfos.get(i).updateUrl);
                                map.put("customBgFatPath", watchInfos.get(i).customBgFatPath);
                                map.put("path", watchInfos.get(i).path);
                                dataList.add(map);
                            }
                        }
                        if (mHandler == null) {
                            mHandler = new Handler(Looper.getMainLooper());
                        }
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                JSONObject jsonObject = new JSONObject();
                                jsonObject.put("value", dataList);
                                if (channel != null) {
                                    LogUtils.i(TAG, "Deliver query big data to flutter:" + jsonObject.toJSONString());
                                    channel.invokeMethod(kGetJieLiWatchFace, jsonObject.toJSONString());
                                }
                                mHandler = null;
                            }
                        });

                    }

                    @Override
                    public void error(BaseError error) {
                        LogUtils.i(TAG, "获取707表盘时发生错误：" + error.toString());
                        if (mHandler == null) {
                            mHandler = new Handler(Looper.getMainLooper());
                        }
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                if (channel != null) {
                                    final List<Map<String, Object>> dataList = new ArrayList<>();
                                    JSONObject jsonObject = new JSONObject();
                                    jsonObject.put("value", dataList);
                                    channel.invokeMethod(kGetJieLiWatchFace, jsonObject.toJSONString());
                                }
                                mHandler = null;
                            }
                        });
                    }
                });
            }
        }.start();

    }

}
