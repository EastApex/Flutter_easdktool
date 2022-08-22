package com.example.easdktool.service;

import android.app.ActivityManager;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;

import com.apex.bluetooth.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SystemRunningUtils {
    private static final String TAG = SystemRunningUtils.class.getSimpleName();

    public static boolean isRunning(String packageName, Context context) {
        try {
            ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningAppProcessInfo> processInfoList = am.getRunningAppProcesses();
            if (processInfoList != null && !processInfoList.isEmpty()) {
                for (int i = 0; i < processInfoList.size(); i++) {
                    ActivityManager.RunningAppProcessInfo processInfo = processInfoList.get(i);
                    if (processInfo.processName.contains(packageName)) {
                        return true;
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 判断服务是否在运行
     *
     * @param cName
     * @param context
     * @return
     */
    public static boolean isServiceRunning(String cName, Context context) {
        try {
            ActivityManager myAM = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            ArrayList<ActivityManager.RunningServiceInfo> runningService = (ArrayList<ActivityManager.RunningServiceInfo>) myAM.getRunningServices(1000);
            if (runningService == null || runningService.isEmpty()) {
                return false;
            }
            for (int i = 0; i < runningService.size(); i++) {
                if (runningService.get(i).service.getClassName().toString().equals(cName)) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 通知权限是否开启
     *
     * @param context
     * @return
     */
    public static boolean isEnable(Context context) {
        String string = Settings.Secure.getString(context.getContentResolver(),
                "enabled_notification_listeners");
        if (!TextUtils.isEmpty(string)) {

            final String[] names = string.split(":");
            for (int i = 0; i < names.length; i++) {
                final ComponentName cn = ComponentName.unflattenFromString(names[i]);
                if (cn != null) {
                    if (TextUtils.equals(context.getPackageName(), cn.getPackageName())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 开启服务
     *
     * @param serviceClass
     * @param context
     */
    public static void startService(Class serviceClass, Context context) {
        try {
            Intent intent = new Intent(context, serviceClass);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context.startForegroundService(intent);
            } else {
                context.startService(intent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 判断当前系统语言是否为中文
     *
     * @return
     */
    public static boolean isZh_rCN() {
        String defaultLanguage = Locale.getDefault().getLanguage();
        if (!TextUtils.isEmpty(defaultLanguage)) {
            if (defaultLanguage.equalsIgnoreCase("zh")) {
                return true;
            } else {
                return false;
            }

        } else {
            return false;
        }
    }

    public static void startNotificationPermission(Context mContext) {
        try {
            Intent intent = new Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(intent);
        } catch (
                ActivityNotFoundException e) {
            try {
                Intent intent = new Intent();
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                ComponentName cn = new ComponentName("com.android.settings", "com.android.settings.Settings$NotificationAccessSettingsActivity");
                intent.setComponent(cn);
                intent.putExtra(":settings:show_fragment", "NotificationAccessSettings");
                mContext.startActivity(intent);

            } catch (Exception ex) {
                ex.printStackTrace();
                LogUtils.e(TAG,ex.getMessage());

            }
        }
    }

}
