package com.example.easdktool.service;

import android.Manifest;
import android.app.Notification;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.alibaba.fastjson.JSONObject;
import com.apex.bluetooth.callback.GeneralCallback;
import com.apex.bluetooth.core.EABleManager;
import com.apex.bluetooth.enumeration.EABleConnectState;
import com.apex.bluetooth.model.EABleSocialContact;
import com.apex.bluetooth.utils.LogUtils;
import com.example.easdktool.R;

import java.util.Calendar;


public class PushService extends NotificationListenerService {
    public static PushService mPushService;
    private final String TAG = this.getClass().getSimpleName();
    private Handler mHandler = new Handler();
    private SmsObserver smsObserver;
    public final static Uri SMS_DB = Uri.parse("content://sms/");

    @Override
    public void onCreate() {
        super.onCreate();
        PackageManager pm = getPackageManager();
        ComponentName tComponentName = new ComponentName(this, PushService.class);
        pm.setComponentEnabledSetting(tComponentName, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
        pm.setComponentEnabledSetting(tComponentName, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
        mPushService = this;
        initSmsListener();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (!SystemRunningUtils.isEnable(this)) {
            SystemRunningUtils.startNotificationPermission(this);
        }

        return Service.START_STICKY;
    }

    private void initSmsListener() {
        smsObserver = new SmsObserver(mHandler, getContentResolver(), this);
        getContentResolver().registerContentObserver(SMS_DB, true, smsObserver);


    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        super.onNotificationPosted(sbn);
        if (sbn == null) {
            return;
        }
        String packageName = sbn.getPackageName();
        if (TextUtils.isEmpty(packageName)) {
            return;
        }
        if (packageName.equalsIgnoreCase(getPackageName()) || packageName.equalsIgnoreCase("com.google.android.gm")) {
            return;
        }
        if (packageName.equalsIgnoreCase("com.google.android.apps.maps")) {
            return;
        }
        Notification notification = sbn.getNotification();
        if (notification != null && notification.actions != null && notification.actions.length < 2) {
            if (packageName.equals(AppList.WHATSAPP)) {
                //whatsApp自己音视频,忽略;
                return;
            }
        }
        //判断是否为音乐
        if (AppMusicControl.isNoSupportRemoteController(packageName)) {
            return;
        }
        if (AppMusicControl.isExistMusicApp(packageName)) {
            LogUtils.d(TAG, "onClientMetadataUpdate=" + notification.tickerText);
            return;// 为true表示是音乐播放器
        }
        String tTickerText = "";
        String tEXTRA_TITLE = "";
        String tEXTRA_TEXT = "";
        if (notification.tickerText != null) {
            tTickerText = notification.tickerText.toString();
        }
        Object obj_EXTRA_TITLE = notification.extras.get(Notification.EXTRA_TITLE);
        if (obj_EXTRA_TITLE != null) {
            tEXTRA_TITLE = obj_EXTRA_TITLE.toString();
        }
        Object obj_EXTRA_TEXT = notification.extras.get(Notification.EXTRA_TEXT);
        if (obj_EXTRA_TEXT != null) {
            tEXTRA_TEXT = obj_EXTRA_TEXT.toString();
        }
        String pushTitle = "";

        if (!TextUtils.isEmpty(tEXTRA_TITLE)) {
            pushTitle = tEXTRA_TITLE;
        } else {
            if (!TextUtils.isEmpty(tTickerText)) {
                pushTitle = tTickerText;
            }
        }
        if (TextUtils.isEmpty(pushTitle) && TextUtils.isEmpty(tEXTRA_TEXT)) {
            return;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(sbn.getNotification().when);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
        String hintTime = calendar.get(Calendar.YEAR) + (month < 10 ? "0" + month : month + "") + (day < 10 ? "0" + day : day + "")
                + "T" + (hour < 10 ? "0" + hour : hour + "") + (minute < 10 ? "0" + minute : minute + "") + (second < 10 ? "0" + second : second + "");
        EABleSocialContact eaBleSocialContact = new MessageUtils().getSocialMessage(packageName, pushTitle, tEXTRA_TEXT, PushService.this, hintTime);
        if (eaBleSocialContact != null) {
            if (EABleManager.getInstance().getDeviceConnectState() == EABleConnectState.STATE_CONNECTED) {
                EABleManager.getInstance().pushInfo2Watch(eaBleSocialContact, new GeneralCallback() {
                    @Override
                    public void result(boolean b) {

                        LogUtils.i(TAG, getString(R.string.information_success));
                    }

                    @Override
                    public void mutualFail(int i) {
                        Log.i(TAG, getString(R.string.information_push_fail));
                    }
                });
            } else {
                LogUtils.i(TAG, getString(R.string.information_fail));
            }
        }
    }

    @Override
    public void onDestroy() {
        if (smsObserver != null) {
            try {
                getContentResolver().unregisterContentObserver(smsObserver);
            } catch (Exception e) {

            }
        }
        smsObserver = null;
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
            mHandler = null;
        }
        // stopForeground(true);

        super.onDestroy();
    }

}
