package com.example.easdktool.callback;

import com.apex.bluetooth.callback.DataResponseCallback;
import com.example.easdktool.Return2Flutter;

import io.flutter.plugin.common.MethodChannel;

public class DataResponseCall implements DataResponseCallback {
    MethodChannel channel;

    public DataResponseCall(MethodChannel channel) {
        this.channel = channel;
    }

    @Override
    public void mutualSuccess() {
        new Return2Flutter(channel).setWatchDataResponse(0, 2004);
    }

    @Override
    public void mutualFail(int i) {
        new Return2Flutter(channel).setWatchDataResponse(1, 2004);
    }
}
