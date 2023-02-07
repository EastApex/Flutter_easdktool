package com.example.easdktool.callback;

import android.util.Log;

import com.apex.bluetooth.callback.HabitResultCallback;
import com.apex.bluetooth.model.EABleHabitRespond;
import com.example.easdktool.Return2Flutter;

import io.flutter.plugin.common.MethodChannel;

public class HabitResultCall implements HabitResultCallback {
    MethodChannel channel;

    public HabitResultCall(MethodChannel channel) {
        this.channel = channel;
    }

    @Override
    public void editResult(EABleHabitRespond eaBleHabitRespond) {
        Log.e("HabitResultCall", "获取习惯回调的结果:" + eaBleHabitRespond.getResult().getValue());
        new Return2Flutter(channel).setWatchDataResponse(eaBleHabitRespond.result.getValue(), 38);

    }

    @Override
    public void mutualFail(int i) {
        Log.e("HabitResultCall", "设置习惯时发生错误:" + i);
        new Return2Flutter(channel).setWatchDataResponse(1, 38);
    }
}
