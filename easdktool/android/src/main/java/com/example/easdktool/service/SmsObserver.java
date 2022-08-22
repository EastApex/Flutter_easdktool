package com.example.easdktool.service;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.apex.bluetooth.callback.GeneralCallback;
import com.apex.bluetooth.core.EABleManager;
import com.apex.bluetooth.enumeration.EABleConnectState;
import com.apex.bluetooth.model.EABleSocialContact;


import java.util.Calendar;

public class SmsObserver extends ContentObserver {
    public static Context mContext;
    public static final Uri MMSSMS_ALL_MESSAGE_URI = Uri.parse("content://sms/inbox");

    public static final String SORT_FIELD_STRING = "_id desc";  // 排序

    public static final String DB_FIELD_ID = "_id";

    public static final String DB_FIELD_ADDRESS = "address";

    public static final String DB_FIELD_PERSON = "person";

    public static final String DB_FIELD_BODY = "body";

    public static final String DB_FIELD_DATE = "date";

    public static final String DB_FIELD_TYPE = "type";

    public static final String DB_FIELD_THREAD_ID = "thread_id";

    public static final String[] ALL_DB_FIELD_NAME = {

            DB_FIELD_ID, DB_FIELD_ADDRESS, DB_FIELD_PERSON, DB_FIELD_BODY,

            DB_FIELD_DATE, DB_FIELD_TYPE, DB_FIELD_THREAD_ID};
    private ContentResolver mResolver;

    /**
     * Creates a content observer.
     *
     * @param handler The handler to run {@link #onChange} on, or null if none.
     */
    public SmsObserver(Handler handler, @NonNull ContentResolver contentResolver, @NonNull Context context) {
        super(handler);
        mResolver = contentResolver;
        mContext = context;
    }

    @Override
    public void onChange(boolean selfChange, Uri uri) {
        super.onChange(selfChange);
        if (EABleManager.getInstance().getDeviceConnectState() != EABleConnectState.STATE_CONNECTED) {
            return;
        }
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.READ_SMS) == PackageManager.PERMISSION_GRANTED) {
            getSmsContent();
        }

    }

    @SuppressLint("Range")
    private void getSmsContent() {
        Cursor cursor = null;
        try {
            cursor = mResolver.query(MMSSMS_ALL_MESSAGE_URI, ALL_DB_FIELD_NAME, null, null, SORT_FIELD_STRING);
            if (cursor != null) {
                cursor.moveToFirst();
                String strAddress = cursor.getString(cursor.getColumnIndex(DB_FIELD_ADDRESS));    // 短信号码
                final String strbody = cursor.getString(cursor.getColumnIndex(DB_FIELD_BODY));
                String dateTime = cursor.getString(cursor.getColumnIndex(DB_FIELD_DATE));
                Calendar calendar = Calendar.getInstance();
                long currentTime = calendar.getTimeInMillis();
                if (!TextUtils.isEmpty(dateTime)) {
                    calendar.setTimeInMillis(Long.parseLong(dateTime));
                    if (Math.abs(currentTime - calendar.getTimeInMillis()) >= 30 * 1000) {
                        return;
                    }
                }
                int month = calendar.get(Calendar.MONTH) + 1;
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);
                int second = calendar.get(Calendar.SECOND);
                String hintTime = calendar.get(Calendar.YEAR) + (month < 10 ? "0" + month : month + "") + (day < 10 ? "0" + day : day + "") + "T" + (hour < 10 ? "0" + hour : hour + "") + (minute < 10 ? "0" + minute : minute + "") + (second < 10 ? "0" + second : second + "");
                if (TextUtils.isEmpty(strAddress) && TextUtils.isEmpty(strbody)) {
                    return;
                }
                EABleSocialContact eaBleSocialContact = new EABleSocialContact();
                eaBleSocialContact.seteType(EABleSocialContact.SocialContactType.sms);
                eaBleSocialContact.setE_ops(EABleSocialContact.SocialContactOps.add);
                eaBleSocialContact.setContent(strbody);
                eaBleSocialContact.setTitle(strAddress);

                eaBleSocialContact.setDate(hintTime);
                if (EABleManager.getInstance().getDeviceConnectState() == EABleConnectState.STATE_CONNECTED) {
                    // Log.e(TAG, "推送信息:" + packageName);
                    if (EABleManager.getInstance().getCurrentOtaState()) {
                        return;
                    }
                    EABleManager.getInstance().pushInfo2Watch(eaBleSocialContact, new GeneralCallback() {

                        @Override
                        public void mutualFail(int i) {

                        }

                        @Override
                        public void result(boolean b) {

                        }
                    });
                }


            }
        } catch (Exception e) {
        } finally {
            if (cursor != null) {
                try {
                    cursor.close();
                } catch (Exception e) {
                    e.printStackTrace();

                }

            }
        }
    }
}
