package com.example.easdktool.callback;

import android.os.Handler;
import android.os.Looper;

import com.alibaba.fastjson.JSONObject;
import com.apex.bluetooth.callback.DataReportCallback;
import com.apex.bluetooth.callback.DataResponseCallback;
import com.apex.bluetooth.core.EABleManager;
import com.apex.bluetooth.model.EABleExecutiveResponse;
import com.apex.bluetooth.model.EABleMtu;
import com.apex.bluetooth.model.EABleMusicControl;
import com.apex.bluetooth.model.EABlePhoneResponse;
import com.apex.bluetooth.model.EABleQueryMusic;
import com.apex.bluetooth.model.EABleSocialResponse;

import io.flutter.plugin.common.MethodChannel;

public class DeviceOperationListener implements DataReportCallback {
    private MethodChannel channel;
    final String kOperationPhone = "OperationPhone"; //操作手机

    public DeviceOperationListener(MethodChannel channel) {
        this.channel = channel;
    }

    @Override
    public void searchPhone() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("opePhoneType", 0);
        sendOpePhone(jsonObject);

    }

    @Override
    public void stopSearchPhone() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("opePhoneType", 1);
        sendOpePhone(jsonObject);
    }

    @Override
    public void connectCamera() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("opePhoneType", 2);
        sendOpePhone(jsonObject);
        responseSeekPhone(true, 2);
    }

    @Override
    public void takePhoto() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("opePhoneType", 3);
        sendOpePhone(jsonObject);
        responseSeekPhone(true, 3);
    }

    @Override
    public void endTakePhoto() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("opePhoneType", 4);
        sendOpePhone(jsonObject);
        responseSeekPhone(true, 4);
    }

    @Override
    public void updateWeather() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("opePhoneType", 5);
        sendOpePhone(jsonObject);
        responseSeekPhone(true, 5);
    }

    @Override
    public void circadian() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("opePhoneType", 7);
        sendOpePhone(jsonObject);
        responseSeekPhone(true, 7);
    }

    @Override
    public void updateAgps() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("opePhoneType", 6);
        sendOpePhone(jsonObject);
        responseSeekPhone(true, 6);
    }

    @Override
    public void transmissionComplete() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("opePhoneType", 8);
        sendOpePhone(jsonObject);
    }

    @Override
    public void stopSearchWatch() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("opePhoneType", 9);
        sendOpePhone(jsonObject);
    }

    @Override
    public void queryMusic(final EABleQueryMusic eaBleQueryMusic) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("opePhoneType", 0x0B);
        sendOpePhone(jsonObject);

    }


    @Override
    public void musicControl(final EABleMusicControl eaBleMusicControl) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("opePhoneType", 0x0C);
        jsonObject.put("volume", eaBleMusicControl.getVolume());
        jsonObject.put("elapsedtime", eaBleMusicControl.getElapsedtime());
        if (eaBleMusicControl.getE_ops() == EABleMusicControl.MusicControl.play_start) {
            jsonObject.put("action", 0);
        }
        if (eaBleMusicControl.getE_ops() == EABleMusicControl.MusicControl.play_stop) {
            jsonObject.put("action", 1);
        }
        if (eaBleMusicControl.getE_ops()== EABleMusicControl.MusicControl.previous_song) {
            jsonObject.put("action", 2);
        }
        if (eaBleMusicControl.getE_ops() == EABleMusicControl.MusicControl.next_song) {
            jsonObject.put("action", 3);
        }
        if (eaBleMusicControl.getE_ops() == EABleMusicControl.MusicControl.volume_up) {
            jsonObject.put("action", 4);
        }
        if (eaBleMusicControl.getE_ops() == EABleMusicControl.MusicControl.volume_reduction) {
            jsonObject.put("action", 5);
        }
        sendOpePhone(jsonObject);


    }


    @Override
    public void socialResponse(final EABleSocialResponse eaBleSocialResponse) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("opePhoneType", 0x0D);
        jsonObject.put("socialId", eaBleSocialResponse.getId());
        jsonObject.put("content", eaBleSocialResponse.getContent());
        sendOpePhone(jsonObject);

    }

    @Override
    public void mtu(final EABleMtu eaBleMtu) {

    }

    @Override
    public void answerIncoming() {

    }

    @Override
    public void hangUpIncoming() {

    }

    @Override
    public void mutualFail(final int i) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("opePhoneType", 0x0F);
        jsonObject.put("errorCode", i);
        sendOpePhone(jsonObject);

    }

    private void sendOpePhone(JSONObject jsonObject) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                if (channel != null) {
                    channel.invokeMethod(kOperationPhone, jsonObject.toJSONString());
                }
            }
        });
    }

    private void responseSeekPhone(boolean success, int operationId) {
        EABlePhoneResponse eaBlePhoneSeek = new EABlePhoneResponse();
        if (!success) {
            eaBlePhoneSeek.setEaBleExecutiveResponse(EABleExecutiveResponse.fail);
        } else {
            eaBlePhoneSeek.setEaBleExecutiveResponse(EABleExecutiveResponse.success);
        }
        eaBlePhoneSeek.setId(operationId);
        EABleManager.getInstance().mobileOperationResponse(eaBlePhoneSeek, new DataResponseCallback() {
            @Override
            public void mutualSuccess() {

            }

            @Override
            public void mutualFail(int i) {

            }
        });

    }
}
