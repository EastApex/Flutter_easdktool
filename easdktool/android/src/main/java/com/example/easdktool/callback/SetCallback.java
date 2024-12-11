package com.example.easdktool.callback;

import android.os.Handler;
import android.os.Looper;

import com.alibaba.fastjson.JSONObject;
import com.apex.bluetooth.callback.GeneralCallback;
import com.apex.bluetooth.utils.LogUtils;
import com.example.easdktool.Return2Flutter;

import io.flutter.plugin.common.MethodChannel;

/**
 * 通用回调
 */
public class SetCallback implements GeneralCallback {
    int commandType;
    MethodChannel channel;


    public SetCallback(int commandType, MethodChannel channel) {
        this.commandType = commandType;
        this.channel = channel;
    }


    @Override
    public void mutualFail(int i) {
        LogUtils.i("SetCallback", "指令操作出现错误:" + i);
        new Return2Flutter(channel).setWatchDataResponse(1, commandType);

    }


    @Override
    public void result(boolean b, int i) {
        LogUtils.i("SetCallback", "指令操作结果:" + (b ? "成功" : "失败"));
        new Return2Flutter(channel).setWatchDataResponse(0, commandType);
    }
}
