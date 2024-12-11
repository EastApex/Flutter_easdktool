package com.example.easdktool.service;

import static androidx.core.app.NotificationCompat.PRIORITY_MAX;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;

import com.example.easdktool.R;


public class Channel {
    // private NotificationChannel channel;
    // private Notification notification;
    // private NotificationManager notificationManager;
    private static Channel mChannel;

    public static Channel getInstance() {
        if (mChannel == null) {
            synchronized (Channel.class) {
                if (mChannel == null) {
                    mChannel = new Channel();
                }
            }
        }
        return mChannel;
    }

    public Notification createNotification(@NonNull Context mContext) {
        try {
            int icon = R.mipmap.ic_launcher;
            String channelId = mContext.getString(R.string.apex_watch);
            String channelName = mContext.getString(R.string.apex_watch);
            if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.QUERY_ALL_PACKAGES) == PackageManager.PERMISSION_GRANTED) {
                PackageInfo packageInfo = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0);
                String appName = packageInfo.applicationInfo.name;
                icon = packageInfo.applicationInfo.icon;
                channelId = appName;
                channelName = appName;
            }

            NotificationChannel notificationChannel;
            //Intent mIntent = new Intent(mContext, MainActivity.class);
            //  PendingIntent mPendingIntent = PendingIntent.getActivity(mContext, 0, mIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                notificationChannel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_NONE);
                notificationChannel.setShowBadge(false);
                notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);

                notificationChannel.enableLights(false);
                notificationChannel.enableVibration(false);
                notificationChannel.setVibrationPattern(new long[]{0});
                notificationChannel.setSound(null, null);

                NotificationManager manager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
                manager.createNotificationChannel(notificationChannel);
                Notification notification = new Notification.Builder(mContext, channelId)
                        //  .setContentTitle(mContext.getString(com.common.lib.R.string.app_running))
                        .setSmallIcon(icon)
                        .setBadgeIconType(Notification.BADGE_ICON_NONE)


                        //  .setContentIntent(mPendingIntent)
                        .build();
                manager.notify(100, notification);
                return notification;
            } else {
                NotificationManager manager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
                NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(mContext, channelId);
                Notification notification = notificationBuilder.setSmallIcon(icon).setPriority(PRIORITY_MAX).
                        setShowWhen(false)
                        //  .setContentText(mContext.getString(com.common.lib.R.string.app_running))
                        .setAutoCancel(false).build();
                // notificationBuilder.setContentIntent(mPendingIntent);
                // manager.notify(AppCommon.SERVICE_ID, notification);
                return notification;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } catch (Error e) {
            e.printStackTrace();
        }
/**
 if (notificationManager == null) {
 notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
 }
 if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
 if (channel == null) {
 channel = new NotificationChannel(AppCommon.SERVICE_ID + "", mContext.getString(R.string.app_name), NotificationManager.IMPORTANCE_NONE);
 channel.setShowBadge(true);//显示logo
 // channel.setDescription("bottombar notification");//设置描述
 channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
 if (notificationManager != null) {
 notificationManager.createNotificationChannel(channel);
 }
 }
 }
 if (notification == null) {
 notification = new NotificationCompat.Builder(mContext)
 .setChannelId(AppCommon.SERVICE_ID + "")
 //.setAutoCancel(false)
 //.setWhen(System.currentTimeMillis())
 .setSmallIcon(R.mipmap.ic_launcher)
 .setContentText(mContext.getString(R.string.app_running))
 // .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
 .build();
 notificationManager.notify(AppCommon.SERVICE_ID, notification);
 }

 return notification;
 */
        return null;
    }
/**
 public void destroy() {
 if (notificationManager != null) {
 notificationManager.cancel(AppCommon.SERVICE_ID);
 notificationManager = null;
 }
 notification = null;
 channel = null;
 mChannel = null;
 }
 */
}
