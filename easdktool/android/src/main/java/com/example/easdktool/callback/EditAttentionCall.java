package com.example.easdktool.callback;

import com.apex.ax_bluetooth.callback.EditAttentionCallback;
import com.apex.ax_bluetooth.model.EABleRemindRespond;
import com.example.easdktool.Return2Flutter;

import io.flutter.plugin.common.MethodChannel;

public class EditAttentionCall implements EditAttentionCallback {
    MethodChannel channel;

    public EditAttentionCall(MethodChannel channel) {
        this.channel = channel;
    }

    @Override
    public void editResult(EABleRemindRespond eaBleRemindRespond) {
        new Return2Flutter(channel).setWatchDataResponse(eaBleRemindRespond.getRemindRespondOps().getValue(), 22);
    }

    @Override
    public void mutualFail(int i) {
        new Return2Flutter(channel).setWatchDataResponse(1, 22);
    }
}
