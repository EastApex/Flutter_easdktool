package com.example.easdktool.service;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.apex.ax_bluetooth.model.EABleSocialContact;
import com.apex.ax_bluetooth.utils.LogUtils;
import com.example.easdktool.R;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class MessageUtils {
    private final String TAG = this.getClass().getSimpleName();

    public EABleSocialContact getSocialMessage(@NonNull String pkg, String messageTitle, String messageContent, @NonNull Context mContext, String messageTime) {
        EABleSocialContact eaBleSocialContact = new EABleSocialContact();
        if (pkg.equalsIgnoreCase(AppList.WECHAT)) {
            eaBleSocialContact.seteType(EABleSocialContact.SocialContactType.wechat);
            eaBleSocialContact.setE_ops(EABleSocialContact.SocialContactOps.add);
        } else if (pkg.equalsIgnoreCase(AppList.AIRBNB)) {
            eaBleSocialContact.seteType(EABleSocialContact.SocialContactType.airbnb);
            eaBleSocialContact.setE_ops(EABleSocialContact.SocialContactOps.add);
        } else if (pkg.equalsIgnoreCase(AppList.DELIVEROO)) {
            eaBleSocialContact.seteType(EABleSocialContact.SocialContactType.deliveroo);
            eaBleSocialContact.setE_ops(EABleSocialContact.SocialContactOps.add);
        } else if (pkg.equalsIgnoreCase(AppList.HANGOUTS)) {
            eaBleSocialContact.seteType(EABleSocialContact.SocialContactType.hangouts);
            eaBleSocialContact.setE_ops(EABleSocialContact.SocialContactOps.add);
        } else if (pkg.equalsIgnoreCase(AppList.GMAIL)) {
            eaBleSocialContact.seteType(EABleSocialContact.SocialContactType.gmail);
            eaBleSocialContact.setE_ops(EABleSocialContact.SocialContactOps.add);
        } else if (pkg.equalsIgnoreCase(AppList.KAKAOTALK)) {
            eaBleSocialContact.seteType(EABleSocialContact.SocialContactType.kakaotalk);
            eaBleSocialContact.setE_ops(EABleSocialContact.SocialContactOps.add);
        } else if (pkg.equalsIgnoreCase(AppList.INSTAGRAM)) {
            eaBleSocialContact.seteType(EABleSocialContact.SocialContactType.instagram);
            eaBleSocialContact.setE_ops(EABleSocialContact.SocialContactOps.add);
        } else if (pkg.equalsIgnoreCase(AppList.BOOKING)) {
            eaBleSocialContact.seteType(EABleSocialContact.SocialContactType.booking);
            eaBleSocialContact.setE_ops(EABleSocialContact.SocialContactOps.add);
        } else if (pkg.equalsIgnoreCase(AppList.FACEBOOK)) {
            eaBleSocialContact.seteType(EABleSocialContact.SocialContactType.facebook);
            eaBleSocialContact.setE_ops(EABleSocialContact.SocialContactOps.add);
        } else if (pkg.equalsIgnoreCase(AppList.LINKEDIN)) {
            eaBleSocialContact.seteType(EABleSocialContact.SocialContactType.linkedin);
            eaBleSocialContact.setE_ops(EABleSocialContact.SocialContactOps.add);
        } else if (pkg.equalsIgnoreCase(AppList.DROPBOX)) {
            eaBleSocialContact.seteType(EABleSocialContact.SocialContactType.dropbox);
            eaBleSocialContact.setE_ops(EABleSocialContact.SocialContactOps.add);
        } else if (pkg.equalsIgnoreCase(AppList.LYFT)) {
            eaBleSocialContact.seteType(EABleSocialContact.SocialContactType.lift);
            eaBleSocialContact.setE_ops(EABleSocialContact.SocialContactOps.add);
        } else if (pkg.equalsIgnoreCase(AppList.SKYPE)) {
            eaBleSocialContact.seteType(EABleSocialContact.SocialContactType.skype);
            eaBleSocialContact.setE_ops(EABleSocialContact.SocialContactOps.add);
        } else if (pkg.equalsIgnoreCase(AppList.QQ)) {
            eaBleSocialContact.seteType(EABleSocialContact.SocialContactType.qq);
            eaBleSocialContact.setE_ops(EABleSocialContact.SocialContactOps.add);
        } else if (pkg.equalsIgnoreCase(AppList.SHAZAM)) {
            eaBleSocialContact.seteType(EABleSocialContact.SocialContactType.shazam);
            eaBleSocialContact.setE_ops(EABleSocialContact.SocialContactOps.add);
        } else if (pkg.equalsIgnoreCase(AppList.SNAPCHAT)) {
            eaBleSocialContact.seteType(EABleSocialContact.SocialContactType.snapchat);
            eaBleSocialContact.setE_ops(EABleSocialContact.SocialContactOps.add);
        } else if (pkg.equalsIgnoreCase(AppList.TELEGRAM)) {
            eaBleSocialContact.seteType(EABleSocialContact.SocialContactType.telegram);
            eaBleSocialContact.setE_ops(EABleSocialContact.SocialContactOps.add);
        } else if (pkg.equalsIgnoreCase(AppList.LINE)) {
            eaBleSocialContact.seteType(EABleSocialContact.SocialContactType.line);
            eaBleSocialContact.setE_ops(EABleSocialContact.SocialContactOps.add);
        } else if (pkg.equalsIgnoreCase(AppList.TUMBLR)) {
            eaBleSocialContact.seteType(EABleSocialContact.SocialContactType.tumblr);
            eaBleSocialContact.setE_ops(EABleSocialContact.SocialContactOps.add);
        } else if (pkg.equalsIgnoreCase(AppList.TWITTER)) {
            eaBleSocialContact.seteType(EABleSocialContact.SocialContactType.twitter);
            eaBleSocialContact.setE_ops(EABleSocialContact.SocialContactOps.add);
        } else if (pkg.equalsIgnoreCase(AppList.VIBER)) {
            eaBleSocialContact.seteType(EABleSocialContact.SocialContactType.viber);
            eaBleSocialContact.setE_ops(EABleSocialContact.SocialContactOps.add);
        } else if (pkg.equalsIgnoreCase(AppList.WAZE)) {
            eaBleSocialContact.seteType(EABleSocialContact.SocialContactType.waze);
            eaBleSocialContact.setE_ops(EABleSocialContact.SocialContactOps.add);
        } else if (pkg.equalsIgnoreCase(AppList.WHATSAPP)) {
            eaBleSocialContact.seteType(EABleSocialContact.SocialContactType.whatsApp);
            eaBleSocialContact.setE_ops(EABleSocialContact.SocialContactOps.add);
            if (!TextUtils.isEmpty(messageTitle) && messageTitle.equalsIgnoreCase("WhatsApp")) {
                if (!TextUtils.isEmpty(messageContent)) {
                    messageContent = messageContent.substring(0, messageContent.length() - 3);
                }
            }
        } else if (pkg.equalsIgnoreCase(AppList.YOUTUBE)) {
            eaBleSocialContact.seteType(EABleSocialContact.SocialContactType.youtube);
            eaBleSocialContact.setE_ops(EABleSocialContact.SocialContactOps.add);
        } else if (pkg.equalsIgnoreCase(AppList.MESSENGER)) {
            eaBleSocialContact.seteType(EABleSocialContact.SocialContactType.messenger);
            eaBleSocialContact.setE_ops(EABleSocialContact.SocialContactOps.add);
        } else if (pkg.equalsIgnoreCase(AppList.PINTEREST)) {
            eaBleSocialContact.seteType(EABleSocialContact.SocialContactType.pinterest);
            eaBleSocialContact.setE_ops(EABleSocialContact.SocialContactOps.add);
        } else if (pkg.equalsIgnoreCase(AppList.SLACK)) {
            eaBleSocialContact.seteType(EABleSocialContact.SocialContactType.slack);
            eaBleSocialContact.setE_ops(EABleSocialContact.SocialContactOps.add);
        } else if (pkg.equalsIgnoreCase(AppList.SPOTIFY)) {
            eaBleSocialContact.seteType(EABleSocialContact.SocialContactType.spotify);
            eaBleSocialContact.setE_ops(EABleSocialContact.SocialContactOps.add);
        } else if (pkg.equalsIgnoreCase(AppList.VKONTAKTE)) {
            eaBleSocialContact.seteType(EABleSocialContact.SocialContactType.vk);
            eaBleSocialContact.setE_ops(EABleSocialContact.SocialContactOps.add);
        } else if (pkg.equalsIgnoreCase(AppList.FLIPBOARD)) {
            eaBleSocialContact.seteType(EABleSocialContact.SocialContactType.flipboard);
            eaBleSocialContact.setE_ops(EABleSocialContact.SocialContactOps.add);
        } else if (pkg.equalsIgnoreCase(AppList.PANDORA)) {
            eaBleSocialContact.seteType(EABleSocialContact.SocialContactType.pandora);
            eaBleSocialContact.setE_ops(EABleSocialContact.SocialContactOps.add);
        } else if (pkg.equalsIgnoreCase(AppList.OUTLOOK)) {
            eaBleSocialContact.seteType(EABleSocialContact.SocialContactType.outlook);
            eaBleSocialContact.setE_ops(EABleSocialContact.SocialContactOps.add);
        } else if (pkg.equalsIgnoreCase(AppList.AMAZON)) {
            eaBleSocialContact.seteType(EABleSocialContact.SocialContactType.amazon);
            eaBleSocialContact.setE_ops(EABleSocialContact.SocialContactOps.add);
        } else if (pkg.equalsIgnoreCase(AppList.DISCORD)) {
            eaBleSocialContact.seteType(EABleSocialContact.SocialContactType.discord);
            eaBleSocialContact.setE_ops(EABleSocialContact.SocialContactOps.add);
        } else if (pkg.equalsIgnoreCase(AppList.GITHUB)) {
            eaBleSocialContact.seteType(EABleSocialContact.SocialContactType.github);
            eaBleSocialContact.setE_ops(EABleSocialContact.SocialContactOps.add);
        } else if (pkg.equalsIgnoreCase(AppList.GOOGLE_MAP)) {
            eaBleSocialContact.seteType(EABleSocialContact.SocialContactType.google_maps);
            eaBleSocialContact.setE_ops(EABleSocialContact.SocialContactOps.add);
        } else if (pkg.equalsIgnoreCase(AppList.NEWS_BREAK)) {
            eaBleSocialContact.seteType(EABleSocialContact.SocialContactType.news_break);
            eaBleSocialContact.setE_ops(EABleSocialContact.SocialContactOps.add);
        } else if (pkg.equalsIgnoreCase(AppList.REDDIT)) {
            eaBleSocialContact.seteType(EABleSocialContact.SocialContactType.reddit);
            eaBleSocialContact.setE_ops(EABleSocialContact.SocialContactOps.add);
        } else if (pkg.equalsIgnoreCase(AppList.TEAMS)) {
            eaBleSocialContact.seteType(EABleSocialContact.SocialContactType.teams);
            eaBleSocialContact.setE_ops(EABleSocialContact.SocialContactOps.add);
        } else if (pkg.equalsIgnoreCase(AppList.TIKTOK)) {
            eaBleSocialContact.seteType(EABleSocialContact.SocialContactType.tiktok);
            eaBleSocialContact.setE_ops(EABleSocialContact.SocialContactOps.add);
        } else if (pkg.equalsIgnoreCase(AppList.TWITCH)) {
            eaBleSocialContact.seteType(EABleSocialContact.SocialContactType.twitch);
            eaBleSocialContact.setE_ops(EABleSocialContact.SocialContactOps.add);
        } else if (pkg.equalsIgnoreCase(AppList.UBER_EATS)) {
            eaBleSocialContact.seteType(EABleSocialContact.SocialContactType.uber_eats);
            eaBleSocialContact.setE_ops(EABleSocialContact.SocialContactOps.add);
        } else if (pkg.equalsIgnoreCase(AppList.SONY_MAIL)) {
            eaBleSocialContact.seteType(EABleSocialContact.SocialContactType.email);
            eaBleSocialContact.setE_ops(EABleSocialContact.SocialContactOps.add);
        } else if (pkg.equalsIgnoreCase(AppList.NET_MAIL_126)) {
            eaBleSocialContact.seteType(EABleSocialContact.SocialContactType.email);
            eaBleSocialContact.setE_ops(EABleSocialContact.SocialContactOps.add);
        } else if (pkg.equalsIgnoreCase(AppList.NET_MAIL)) {
            eaBleSocialContact.seteType(EABleSocialContact.SocialContactType.email);
            eaBleSocialContact.setE_ops(EABleSocialContact.SocialContactOps.add);
        } else if (pkg.equalsIgnoreCase(AppList.NET_MAIL_163)) {
            eaBleSocialContact.seteType(EABleSocialContact.SocialContactType.email);
            eaBleSocialContact.setE_ops(EABleSocialContact.SocialContactOps.add);
        } else if (pkg.equalsIgnoreCase(AppList.SINA_MAIL)) {
            eaBleSocialContact.seteType(EABleSocialContact.SocialContactType.email);
            eaBleSocialContact.setE_ops(EABleSocialContact.SocialContactOps.add);
        } else if (pkg.equalsIgnoreCase(AppList.CORE_MAIL)) {
            eaBleSocialContact.seteType(EABleSocialContact.SocialContactType.email);
            eaBleSocialContact.setE_ops(EABleSocialContact.SocialContactOps.add);
        } else if (pkg.equalsIgnoreCase(AppList.QQ_MAIL)) {
            eaBleSocialContact.seteType(EABleSocialContact.SocialContactType.email);
            eaBleSocialContact.setE_ops(EABleSocialContact.SocialContactOps.add);
        } else if (pkg.equalsIgnoreCase(AppList.MAIL_189)) {
            eaBleSocialContact.seteType(EABleSocialContact.SocialContactType.email);
            eaBleSocialContact.setE_ops(EABleSocialContact.SocialContactOps.add);
        } else if (pkg.equalsIgnoreCase(AppList.ALI_MAIL)) {
            eaBleSocialContact.seteType(EABleSocialContact.SocialContactType.email);
            eaBleSocialContact.setE_ops(EABleSocialContact.SocialContactOps.add);
        } else if (pkg.equalsIgnoreCase(AppList.E_MAIL)) {
            eaBleSocialContact.seteType(EABleSocialContact.SocialContactType.email);
            eaBleSocialContact.setE_ops(EABleSocialContact.SocialContactOps.add);
        } else if (pkg.equalsIgnoreCase(AppList.WAN_MEI_MAIL)) {
            eaBleSocialContact.seteType(EABleSocialContact.SocialContactType.email);
            eaBleSocialContact.setE_ops(EABleSocialContact.SocialContactOps.add);
        } else if (pkg.equalsIgnoreCase(AppList.ZOHO_MAIL)) {
            eaBleSocialContact.seteType(EABleSocialContact.SocialContactType.email);
            eaBleSocialContact.setE_ops(EABleSocialContact.SocialContactOps.add);
        } else if (pkg.equalsIgnoreCase(AppList.LENOVO_MAIL)) {
            eaBleSocialContact.seteType(EABleSocialContact.SocialContactType.email);
            eaBleSocialContact.setE_ops(EABleSocialContact.SocialContactOps.add);
        } else if (pkg.equalsIgnoreCase(AppList.KINGSOFT_MAIL)) {
            eaBleSocialContact.seteType(EABleSocialContact.SocialContactType.email);
            eaBleSocialContact.setE_ops(EABleSocialContact.SocialContactOps.add);
        } else if (pkg.equalsIgnoreCase(AppList.MAIL_139)) {
            eaBleSocialContact.seteType(EABleSocialContact.SocialContactType.email);
            eaBleSocialContact.setE_ops(EABleSocialContact.SocialContactOps.add);
        } else if (pkg.toLowerCase().contains("mail") || pkg.toLowerCase().contains("email")) {
            eaBleSocialContact.seteType(EABleSocialContact.SocialContactType.email);
            eaBleSocialContact.setE_ops(EABleSocialContact.SocialContactOps.add);
        } else {
            PackageManager packageManager = mContext.getPackageManager();
            if (packageManager != null) {
                try {
                    PackageInfo packageInfo = packageManager.getPackageInfo(pkg, PackageManager.GET_CONFIGURATIONS);
                    if (packageInfo != null) {
                        if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0) {
                            return null;
                        }
                    }
                    if (pkg.equalsIgnoreCase("com.ting.mp3.android")) {//过滤掉千千静听
                        return null;
                    }
                    Intent audioIntent = new Intent(Intent.ACTION_VIEW);
                    audioIntent.addCategory(Intent.CATEGORY_DEFAULT);
                    audioIntent.setDataAndType(Uri.fromFile(new File("")), "audio/*");
                    List<ResolveInfo> infoList = packageManager.queryIntentActivities(audioIntent, PackageManager.MATCH_ALL);
                    if (infoList != null && !infoList.isEmpty()) {
                        for (ResolveInfo resolveInfo : infoList) {
                            if (resolveInfo.activityInfo.packageName.equals(pkg)) {
                                return null;
                            }
                        }
                    }
                    Intent videoIntent = new Intent(Intent.ACTION_VIEW);
                    videoIntent.addCategory(Intent.CATEGORY_DEFAULT);
                    videoIntent.setDataAndType(Uri.fromFile(new File("")), "video/*");
                    List<ResolveInfo> videoList = packageManager.queryIntentActivities(videoIntent, PackageManager.MATCH_ALL);
                    if (videoList != null && !videoList.isEmpty()) {
                        for (ResolveInfo resolveInfo : videoList) {
                            if (resolveInfo.activityInfo.packageName.equals(pkg)) {
                                return null;
                            }
                        }
                    }
                    Intent browIntent = new Intent(Intent.ACTION_VIEW);
                    browIntent.addCategory(Intent.CATEGORY_BROWSABLE);
                    browIntent.setData(Uri.parse("http://www.baidu.com/"));
                    List<ResolveInfo> activities = packageManager.queryIntentActivities(browIntent, PackageManager.MATCH_DEFAULT_ONLY);
                    if (activities != null && !activities.isEmpty()) {
                        ResolveInfo r0 = activities.get(0);
                        List<ResolveInfo> bActivities = new ArrayList<>();
                        for (ResolveInfo info : activities) {
                            if (r0.priority != info.priority || r0.isDefault != info.isDefault) {
                                continue;
                            }
                            bActivities.add(info);
                        }
                        if (bActivities != null && !bActivities.isEmpty()) {
                            for (ResolveInfo resolveInfo : bActivities) {
                                if (resolveInfo.activityInfo.packageName.equals(pkg)) {
                                    return null;
                                }
                            }
                        }
                    }
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }

            }

            eaBleSocialContact.seteType(EABleSocialContact.SocialContactType.unknow);
            eaBleSocialContact.setE_ops(EABleSocialContact.SocialContactOps.add);

        }
        eaBleSocialContact.setContent(messageContent);
        eaBleSocialContact.setDate(messageTime);
        eaBleSocialContact.setTitle(messageTitle);
        return eaBleSocialContact;

    }


    public EABleSocialContact getCurrentMissIncomingCall(@NonNull String phoneNum, long hangTime, @NonNull Context mContext) {
        String cName = getIncomingName(phoneNum, mContext);
        String noticeTime = getNoticeTime(hangTime);
        EABleSocialContact eaBleSocialContact = new EABleSocialContact();
        eaBleSocialContact.seteType(EABleSocialContact.SocialContactType.missedcall);
        eaBleSocialContact.setE_ops(EABleSocialContact.SocialContactOps.add);
        eaBleSocialContact.setTitle(mContext.getString(R.string.miss_call));
        eaBleSocialContact.setContent(cName);
        eaBleSocialContact.setDate(noticeTime);
        return eaBleSocialContact;
    }

    private String getIncomingName(String phoneNum, Context mContext) {
        String name = null;
        if (!TextUtils.isEmpty(phoneNum)) {
            String pNumber = phoneNum.replace(" ", "");
            name = pNumber;
            if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
                Uri uri = Uri.parse("content://com.android.contacts/data/phones/filter/" + phoneNum);
                ContentResolver resolver = mContext.getContentResolver();
                Cursor cursor = resolver.query(uri, new String[]{ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER}, null, null, null);
                if (cursor != null) {
                    boolean isLoop = true;
                    while (cursor.moveToNext() && isLoop) {
                        String displayName = cursor.getString(0);
                        String phoneNumber = cursor.getString(1);
                        //  Log.e(TAG, "姓名:" + displayName + ",电话:" + phoneNumber);
                        if (!TextUtils.isEmpty(phoneNumber)) {
                            phoneNumber = phoneNumber.replace(" ", "");
                            if (pNumber.equalsIgnoreCase(phoneNumber)) {
                                name = displayName;
                                isLoop = false;
                            }
                        }
                    }
                    cursor.close();
                }
            }
        }
        return name;
    }

    private String getNoticeTime(long noticeTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(noticeTime);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
        return calendar.get(Calendar.YEAR) + (month < 10 ? "0" + month : month + "") + (day < 10 ? "0" + day : day + "") + "T" + (hour < 10 ? "0" + hour : hour + "") + (minute < 10 ? "0" + minute : minute + "") + (second < 10 ? "0" + second : second + "");
    }

    public EABleSocialContact getIncomingCallNotice(@NonNull String phoneNum, long hangTime, @NonNull Context mContext) {
        String cName = getIncomingName(phoneNum, mContext);
        String noticeTime = getNoticeTime(hangTime);
        EABleSocialContact eaBleSocialContact = new EABleSocialContact();
        eaBleSocialContact.seteType(EABleSocialContact.SocialContactType.incomingcall);
        eaBleSocialContact.setE_ops(EABleSocialContact.SocialContactOps.add);
        eaBleSocialContact.setTitle(cName);
        eaBleSocialContact.setContent(cName);
        eaBleSocialContact.setDate(noticeTime);
        return eaBleSocialContact;
    }
}
