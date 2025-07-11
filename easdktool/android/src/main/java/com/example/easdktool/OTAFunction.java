package com.example.easdktool;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.apex.ax_bluetooth.callback.OtaCallback;
import com.apex.ax_bluetooth.core.EABleManager;
import com.apex.ax_bluetooth.model.EABleOta;
import com.apex.ax_bluetooth.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.flutter.plugin.common.MethodChannel;

public class OTAFunction {
    private MethodChannel channel;
    final String kProgress = "Progress";
    private final String TAG = this.getClass().getSimpleName();
    Handler mHandler;

    public OTAFunction(MethodChannel channel) {
        this.channel = channel;
    }

    private List<EABleOta> getOtaInfo(Map<String, Object> map) {
        List<JSONObject> wArray = (List<JSONObject>) map.get("otas");
        if (wArray != null && !wArray.isEmpty()) {
            List<EABleOta> otaDataList = new ArrayList<>();
            for (int i = 0; i < wArray.size(); i++) {
                JSONObject wMap = wArray.get(i);
                EABleOta tempOtaData = new EABleOta();
                tempOtaData.setVersion(wMap.getString("version"));
                tempOtaData.setPop(true);
                int type = wMap.getInteger("firmwareType");
                if (type == 0) {
                    tempOtaData.setOtaType(EABleOta.OtaType.apollo);
                } else if (type == 1) {
                    tempOtaData.setOtaType(EABleOta.OtaType.res);
                } else if (type == 2) {
                    tempOtaData.setOtaType(EABleOta.OtaType.hr);
                } else if (type == 3) {
                    tempOtaData.setOtaType(EABleOta.OtaType.tp);
                } else if (type == 4) {
                    tempOtaData.setOtaType(EABleOta.OtaType.user_wf);

                }
                tempOtaData.setFilePath(wMap.getString("binPath"));
                otaDataList.add(tempOtaData);

            }
            return otaDataList;
        }
        return null;
    }

    public void startOta(Map<String, Object> map) {
        List<EABleOta> otaList = getOtaInfo(map);
        if (otaList != null && !otaList.isEmpty()) {
            EABleManager.getInstance().otaUpdate(otaList, new OtaCallback() {
                @Override
                public void success() {
                    if (mHandler==null){
                        mHandler=new Handler(Looper.getMainLooper());
                    }
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (channel != null) {
                                channel.invokeMethod(kProgress, 100);
                            }
                            mHandler=null;
                        }
                    });

                }

                @Override
                public void progress(int i) {
                    if (mHandler==null){
                        mHandler=new Handler(Looper.getMainLooper());
                    }
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            LogUtils.i(TAG, "当前进度:" + i);
                            if (channel != null) {
                                channel.invokeMethod(kProgress, i);
                            }
                        }
                    });

                }

                @Override
                public void hisUpdateId(int[] ints) {

                }

                @Override
                public void mutualFail(int i) {
                    if (mHandler==null){
                        mHandler=new Handler(Looper.getMainLooper());
                    }
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (channel != null) {
                                channel.invokeMethod(kProgress, -1);
                            }
                            mHandler=null;
                        }
                    });

                }
            });
        } else {
            if (channel != null) {
                channel.invokeMethod(kProgress, -1);
            }
        }
    }
    public void startCustomWatchFace(List<EABleOta> otaList){
        EABleManager.getInstance().otaUpdate(otaList, new OtaCallback() {
            @Override
            public void success() {
                if (mHandler==null){
                    mHandler=new Handler(Looper.getMainLooper());
                }
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (channel != null) {
                            channel.invokeMethod(kProgress, 100);
                        }
                        mHandler=null;
                    }
                });
            }

            @Override
            public void progress(int i) {
                if (mHandler==null){
                    mHandler=new Handler(Looper.getMainLooper());
                }
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        LogUtils.i(TAG, "当前进度:" + i);
                        if (channel != null) {
                            channel.invokeMethod(kProgress, i);
                        }
                    }
                });
            }

            @Override
            public void hisUpdateId(int[] ints) {

            }

            @Override
            public void mutualFail(int i) {
                if (mHandler==null){
                    mHandler=new Handler(Looper.getMainLooper());
                }
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (channel != null) {
                            channel.invokeMethod(kProgress, -1);
                        }
                        mHandler=null;
                    }
                });
            }
        });
    }
    public void startAgps2Watch(List<EABleOta> otaList){
        EABleManager.getInstance().otaUpdate(otaList, new OtaCallback() {
            @Override
            public void success() {
                if (mHandler==null){
                    mHandler=new Handler(Looper.getMainLooper());
                }
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (channel != null) {
                            channel.invokeMethod(kProgress, 100);
                        }
                        mHandler=null;
                    }
                });
            }

            @Override
            public void progress(int i) {
                if (mHandler==null){
                    mHandler=new Handler(Looper.getMainLooper());
                }
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        LogUtils.i(TAG, "当前进度:" + i);
                        if (channel != null) {
                            channel.invokeMethod(kProgress, i);
                        }
                    }
                });
            }

            @Override
            public void hisUpdateId(int[] ints) {

            }

            @Override
            public void mutualFail(int i) {
                if (mHandler==null){
                    mHandler=new Handler(Looper.getMainLooper());
                }
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (channel != null) {
                            channel.invokeMethod(kProgress, -1);
                        }
                        mHandler=null;
                    }
                });
            }
        });
    }
}
