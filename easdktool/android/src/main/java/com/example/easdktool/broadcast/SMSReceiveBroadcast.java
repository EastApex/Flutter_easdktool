package com.example.easdktool.broadcast;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.telephony.SmsMessage;
import android.text.TextUtils;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import com.apex.bluetooth.callback.GeneralCallback;
import com.apex.bluetooth.core.EABleManager;
import com.apex.bluetooth.enumeration.EABleConnectState;
import com.apex.bluetooth.model.EABleSocialContact;
import com.apex.bluetooth.utils.LogUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SMSReceiveBroadcast extends BroadcastReceiver {
    private final String TAG = this.getClass().getSimpleName();
    public final String SMS_RECEIVED_ACTION = "android.provider.Telephony.SMS_RECEIVED";

    @Override
    public void onReceive(Context context, Intent intent) {
        LogUtils.e(TAG, "Receive a text message-----");
        if (intent.getAction().equals(SMS_RECEIVED_ACTION)) {
            Object[] pdus = (Object[]) intent.getExtras().get("pdus");
            String currentName = "";
            String content = "";
            long date = Calendar.getInstance().getTimeInMillis();
            for (Object pdu : pdus) {
                SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) pdu);
                String sender = smsMessage.getDisplayOriginatingAddress();
                if (TextUtils.isEmpty(currentName)) {
                    currentName = sender;
                    date = smsMessage.getTimestampMillis();
                }
                if (sender.equalsIgnoreCase(currentName)) {
                    content += smsMessage.getDisplayMessageBody();
                } else {
                    sendSms(context,date, sender, content);
                    currentName = sender;
                    content = smsMessage.getDisplayMessageBody();
                    date = smsMessage.getTimestampMillis();
                }
            }
            sendSms(context,date, currentName, content);
        }
    }

    private void sendSms(Context mContent,long dateTime, String sender, String smsContent) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(dateTime);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
        String hintTime = calendar.get(Calendar.YEAR) + (month < 10 ? "0" + month : month + "") + (day < 10 ? "0" + day : day + "") + "T" + (hour < 10 ? "0" + hour : hour + "") + (minute < 10 ? "0" + minute : minute + "") + (second < 10 ? "0" + second : second + "");
        EABleSocialContact eaBleSocialContact = new EABleSocialContact();
        eaBleSocialContact.seteType(EABleSocialContact.SocialContactType.sms);
        eaBleSocialContact.setE_ops(EABleSocialContact.SocialContactOps.add);
        eaBleSocialContact.setContent(smsContent);
        eaBleSocialContact.setTitle(getSenderName(mContent,sender));
        eaBleSocialContact.setDate(hintTime);
        LogUtils.e(TAG, "SMS sender:" + sender);
        if (EABleManager.getInstance().getDeviceConnectState() == EABleConnectState.STATE_CONNECTED) {
            // Log.e(TAG, "推送信息:" + packageName);
            if (EABleManager.getInstance().getCurrentOtaState()) {
                return;
            }
            EABleManager.getInstance().pushInfo2Watch(eaBleSocialContact, new GeneralCallback() {

                @Override
                public void mutualFail(int i) {
                    LogUtils.e(TAG, "Sms push failed");
                }

                @Override
                public void result(boolean b) {
                    LogUtils.e(TAG, "Sms push successful");
                }
            });
        }
    }

    private String getSenderName(Context mContent, String phoneNum) {
        String contactName = phoneNum;
        if (ActivityCompat.checkSelfPermission(mContent, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            Uri personUri = Uri.withAppendedPath(
                    ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(phoneNum));
            Cursor cur = mContent.getContentResolver().query(personUri,
                    new String[]{ContactsContract.PhoneLookup.DISPLAY_NAME},
                    null, null, null);

            if (cur != null) {
                if (cur.moveToFirst()) {
                    int nameIdx = cur.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME);
                    contactName = cur.getString(nameIdx);
                }
                cur.close();
            }
        }
        return contactName;

    }
}
