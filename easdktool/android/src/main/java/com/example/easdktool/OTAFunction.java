package com.example.easdktool;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.apex.ax_bluetooth.callback.GeneralCallback;
import com.apex.ax_bluetooth.callback.OtaCallback;
import com.apex.ax_bluetooth.core.EABleManager;
import com.apex.ax_bluetooth.model.EABleDev;
import com.apex.ax_bluetooth.model.EABleOta;
import com.apex.ax_bluetooth.utils.LogUtils;
import com.example.easdktool.been.OtaProgress;
import com.example.easdktool.jieli.ota.JieliOtaInstance;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

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
                } else if (type == 5) {
                    tempOtaData.setOtaType(null);
                }
                tempOtaData.setFilePath(wMap.getString("binPath"));
                otaDataList.add(tempOtaData);

            }
            return otaDataList;
        }
        return null;
    }

    public void startOta(Map<String, Object> map, Context mContext) {
        List<EABleOta> otaList = getOtaInfo(map);
        if (otaList != null && !otaList.isEmpty()) {
            EABleOta jieliOta = null;
            for (EABleOta ota : otaList) {
                if (ota.getOtaType() == null) {
                    jieliOta = ota;

                }
                break;
            }
            if (jieliOta != null) {
                EABleDev eaBleDev = new EABleDev();
                eaBleDev.setE_ops(EABleDev.DevOps.jl707_ota_start_request);
                final String otaPath = jieliOta.getFilePath();
                EABleManager.getInstance().setDeviceOps(eaBleDev, new GeneralCallback() {
                    @Override
                    public void result(boolean b, final int i) {
                        if (b) {
                            new Timer().schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    JieliOtaInstance.getInstance().startOta(otaPath, new OtaCallback() {
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
                                                        otaProgress.isSuccess = true;
                                                        otaProgress.progress = 100;
                                                        channel.invokeMethod(kProgress, JSONObject.toJSONString(otaProgress));
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
                                                        otaProgress.isSuccess = false;
                                                        otaProgress.progress = progress;
                                                        channel.invokeMethod(kProgress, JSONObject.toJSONString(otaProgress));
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
                                                        otaProgress.isSuccess = false;
                                                        otaProgress.progress = -1;
                                                        otaProgress.errorType = i;
                                                        channel.invokeMethod(kProgress, JSONObject.toJSONString(otaProgress));
                                                    }
                                                    mHandler = null;
                                                }
                                            });

                                        }
                                    }, mContext);
                                }
                            }, 2000);

                        } else {
                            if (mHandler == null) {
                                mHandler = new Handler(Looper.getMainLooper());
                            }
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    if (channel != null) {
                                        OtaProgress otaProgress = new OtaProgress();
                                        otaProgress.isSuccess = false;
                                        otaProgress.progress = -1;
                                        otaProgress.errorType = i;
                                        channel.invokeMethod(kProgress, JSONObject.toJSONString(otaProgress));
                                    }
                                    mHandler = null;
                                }
                            });
                        }

                    }

                    @Override
                    public void mutualFail(final int i) {
                        Log.e(TAG, "请求707OTA开始错误:" + i);
                        if (mHandler == null) {
                            mHandler = new Handler(Looper.getMainLooper());
                        }
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                if (channel != null) {
                                    OtaProgress otaProgress = new OtaProgress();
                                    otaProgress.isSuccess = false;
                                    otaProgress.progress = -1;
                                    otaProgress.errorType = i;
                                    channel.invokeMethod(kProgress, JSONObject.toJSONString(otaProgress));
                                }
                                mHandler = null;
                            }
                        });
                    }
                });

                return;
            }
            EABleManager.getInstance().otaUpdate(otaList, new OtaCallback() {
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
                                otaProgress.isSuccess = true;
                                otaProgress.progress = 100;
                                channel.invokeMethod(kProgress, JSONObject.toJSONString(otaProgress));
                            }
                            mHandler = null;
                        }
                    });

                }

                @Override
                public void progress(int i) {
                    if (mHandler == null) {
                        mHandler = new Handler(Looper.getMainLooper());
                    }
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            LogUtils.i(TAG, "当前进度:" + i);
                            if (channel != null) {
                                OtaProgress otaProgress = new OtaProgress();
                                otaProgress.isSuccess = false;
                                otaProgress.progress = i;
                                channel.invokeMethod(kProgress, JSONObject.toJSONString(otaProgress));
                            }
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
                                otaProgress.isSuccess = false;
                                otaProgress.progress = -1;
                                otaProgress.errorType = i;
                                channel.invokeMethod(kProgress, JSONObject.toJSONString(otaProgress));
                            }
                            mHandler = null;
                        }
                    });

                }
            });
        } else {
            if (channel != null) {
                OtaProgress otaProgress = new OtaProgress();
                otaProgress.isSuccess = false;
                otaProgress.progress = -1;
                otaProgress.errorType = 0x10;
                channel.invokeMethod(kProgress, JSONObject.toJSONString(otaProgress));
            }
        }
    }

    public void startCustomWatchFace(List<EABleOta> otaList) {
        EABleManager.getInstance().otaUpdate(otaList, new OtaCallback() {
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
                            otaProgress.isSuccess = true;
                            otaProgress.progress = 100;
                            channel.invokeMethod(kProgress, JSONObject.toJSONString(otaProgress));
                        }
                        mHandler = null;
                    }
                });
            }

            @Override
            public void progress(final int i) {
                if (mHandler == null) {
                    mHandler = new Handler(Looper.getMainLooper());
                }
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        LogUtils.i(TAG, "当前进度:" + i);
                        if (channel != null) {
                            OtaProgress otaProgress = new OtaProgress();
                            otaProgress.isSuccess = false;
                            otaProgress.progress = i;
                            channel.invokeMethod(kProgress, JSONObject.toJSONString(otaProgress));
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
                            otaProgress.isSuccess = false;
                            otaProgress.progress = -1;
                            otaProgress.errorType=i;
                            channel.invokeMethod(kProgress, JSONObject.toJSONString(otaProgress));
                        }
                        mHandler = null;
                    }
                });
            }
        });
    }

    public void startAgps2Watch(List<EABleOta> otaList) {
        EABleManager.getInstance().otaUpdate(otaList, new OtaCallback() {
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
                            otaProgress.isSuccess = true;
                            otaProgress.progress = 100;
                            channel.invokeMethod(kProgress, JSONObject.toJSONString(otaProgress));
                        }
                        mHandler = null;
                    }
                });
            }

            @Override
            public void progress(final int i) {
                if (mHandler == null) {
                    mHandler = new Handler(Looper.getMainLooper());
                }
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        LogUtils.i(TAG, "当前进度:" + i);
                        if (channel != null) {
                            OtaProgress otaProgress = new OtaProgress();
                            otaProgress.isSuccess = false;
                            otaProgress.progress = i;
                            channel.invokeMethod(kProgress, JSONObject.toJSONString(otaProgress));
                        }
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
                            otaProgress.isSuccess = false;
                            otaProgress.progress = -1;
                            otaProgress.errorType=i;
                            channel.invokeMethod(kProgress, JSONObject.toJSONString(otaProgress));
                        }
                        mHandler = null;
                    }
                });
            }
        });
    }
}
