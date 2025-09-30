package com.example.easdktool;

import android.os.Handler;
import android.os.Looper;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.apex.ax_bluetooth.utils.LogUtils;

import java.util.Map;

import io.flutter.plugin.common.MethodChannel;

public class Return2Flutter {
    MethodChannel channel;
    final String kBingdingWatchResponse = "BingdingWatchResponse";
    final String kSetWatchResponse = "SetWatchResponse";
    final String kGetWatchResponse = "GetWatchResponse";
    final String TAG = this.getClass().getSimpleName();
    Handler mHandler;

    public Return2Flutter(MethodChannel channel) {
        this.channel = channel;
    }

    public void setWatchDataResponse(int respondCodeType, int type) {
        if (mHandler != null) {
            mHandler = null;
        }
        mHandler = new Handler(Looper.getMainLooper());
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("dataType", type);
                jsonObject.put("respondCodeType", respondCodeType);

                if (channel != null) {
                    LogUtils.i(TAG, "Pass the results to flutter");
                    if (type == 6) {
                        channel.invokeMethod(kBingdingWatchResponse, jsonObject.toJSONString());
                    } else {
                        channel.invokeMethod(kSetWatchResponse, jsonObject.toJSONString());
                    }
                }
                mHandler = null;
            }
        });
    }

    public void sendWatchDataWithMap(Map mapValue, int type) {
        if (mHandler != null) {
            mHandler = null;
        }
        mHandler = new Handler(Looper.getMainLooper());
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("dataType", type);
                jsonObject.put("value", mapValue);
                String jsonString = jsonObject.toJSONString();

                if (channel != null) {
                    LogUtils.i(TAG, "Pass the results to flutter");
                    channel.invokeMethod(kGetWatchResponse, jsonString);
                }
                mHandler = null;
            }
        });
    }

    public void sendWatchDataWithOtherKeyValue(String key, int value, int type) {
        if (mHandler != null) {
            mHandler = null;
        }
        mHandler = new Handler(Looper.getMainLooper());
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                JSONObject jsonObject1 = new JSONObject();
                jsonObject1.put(key, value);
                String jsonString = jsonObject1.toJSONString();
                Map map = JSON.parseObject(jsonString);
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("dataType", type);
                jsonObject.put("value", map);
                if (channel != null) {
                    LogUtils.i(TAG, "Pass the results to flutter");
                    channel.invokeMethod(kGetWatchResponse, jsonObject.toJSONString());
                }
                mHandler = null;
            }
        });
    }
}
