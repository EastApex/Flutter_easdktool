package com.example.easdktool.service;

import static android.content.Context.AUDIO_SERVICE;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.media.AudioManager;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;

import androidx.annotation.Nullable;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class AppMusicControl {
    /**
     * 通过在切歌时延时发送一个空的音乐信息给设备, 当接收到音乐改变广播时, 取消发送,
     * 实现"判断App是否能获取到MusicPlayer的音乐信息",
     * 但由于播放/暂停时MusicPlayer并不会发送广播, so, 效果不好~~
     */
    private static final boolean FLAG_SEND_EMPTY_MUSIC_INFO = false;
    /**
     * 是否下发音乐状态的flag
     */
    private static final boolean FLAG_SEND_MUSIC_STATE = true;
    private static List<String> sPreferentialMusicPlayerAppList;
    private static Set<String> sNoMusicPlayerClassSet;
    private static Set<String> sNoMusicPlayerAppSet;
    private static ResolveInfo sPrevMusicPlayer;
    public static String MusicName = "";
    public static String artistName = "";
    public static int playState;
    public static long durationTime;
    public static long playProgress;





    public static Boolean isNoSupportRemoteController(String packageName) {
        getPreferentialMusicPlayerAppList();
        if (sPreferentialMusicPlayerAppList.contains(packageName)) {
            if ("com.netease.cloudmusic".equals(packageName)) {
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    public static Boolean isExistMusicApp(String packageName) {
        getPreferentialMusicPlayerAppList();

        return sPreferentialMusicPlayerAppList.contains(packageName);
    }

    public static List<String> getPreferentialMusicPlayerAppList() {
        if (sPreferentialMusicPlayerAppList == null) {
            sPreferentialMusicPlayerAppList = new ArrayList<>();
            /*系统音乐播放器*/
            sPreferentialMusicPlayerAppList.add("com.google.android.music");
            sPreferentialMusicPlayerAppList.add("com.sonyericsson.music");
            sPreferentialMusicPlayerAppList.add("com.miui.player");
            sPreferentialMusicPlayerAppList.add("com.htc.music");
            sPreferentialMusicPlayerAppList.add("com.sec.android.app.music");
            sPreferentialMusicPlayerAppList.add("com.huawei.hwvplayer");
            sPreferentialMusicPlayerAppList.add("com.coolpad.music");
            sPreferentialMusicPlayerAppList.add("com.android.bbkmusic");
            sPreferentialMusicPlayerAppList.add("com.samsung.android.app.music.chn");


            /*第三方音乐播放器*/
            sPreferentialMusicPlayerAppList.add("com.netease.cloudmusic");
            sPreferentialMusicPlayerAppList.add("com.tencent.qqmusic");
            sPreferentialMusicPlayerAppList.add("com.kugou.android");
            sPreferentialMusicPlayerAppList.add("cn.kuwo.player");
            sPreferentialMusicPlayerAppList.add("com.spotify.music");
            sPreferentialMusicPlayerAppList.add("cmccwm.mobilemusic");

        }
        return sPreferentialMusicPlayerAppList;
    }



    private static boolean equals(ResolveInfo a, ResolveInfo b) {
        if (a == b) return true;
        if (a != null && b != null && a.activityInfo.packageName.equals(b.activityInfo.packageName)) {
            return true;
        }
        return false;
    }



    public static List<ResolveInfo> getMediaButtonReceiver(PackageManager mPm) {
        List<ResolveInfo> list = mPm.queryBroadcastReceivers(new Intent(Intent.ACTION_MEDIA_BUTTON), 0);
        Map<Integer, ResolveInfo> tempMap = new TreeMap<>();

        Iterator<ResolveInfo> iterator = list.iterator();
        while (iterator.hasNext()) {
            ResolveInfo info = iterator.next();
            String packageName = info.activityInfo.packageName;
            if (getNoMusicPlayerAppSet().contains(packageName)) {// 移除非音乐播放器的应用
                iterator.remove();
            } else if (getNoMusicPlayerClassSet().contains(info.activityInfo.name)) {// 移除特殊的非音乐播放器的Class
                iterator.remove();
            } else {
                int index = getPreferentialMusicPlayerAppList().indexOf(packageName);
                if (index != -1) {
                    tempMap.put(index, info);// 把优先的音乐播放器放在临时的map中
                    iterator.remove();
                }
            }
        }
        int offset = 0;
        for (Map.Entry<Integer, ResolveInfo> entry : tempMap.entrySet()) {
            list.add(offset++, entry.getValue());// 在list的开头, 添加优先的音乐播放器
        }
        return list;
    }

    public static Set<String> getNoMusicPlayerAppSet() {
        if (sNoMusicPlayerAppSet == null) {
            sNoMusicPlayerAppSet = new HashSet<>();
            sNoMusicPlayerAppSet.add("com.google.android.apps.magazines");
            sNoMusicPlayerAppSet.add("com.netflix.mediaclient");
            sNoMusicPlayerAppSet.add("com.amazon.kindle");
            sNoMusicPlayerAppSet.add("net.flixster.android");
            sNoMusicPlayerAppSet.add("air.com.vudu.air.DownloaderTablet");
            sNoMusicPlayerAppSet.add("com.espn.score_center");
            sNoMusicPlayerAppSet.add("org.coursera.android");
            sNoMusicPlayerAppSet.add("com.sec.android.app.mv.player");
        }
        return sNoMusicPlayerAppSet;
    }

    private static Set<String> getNoMusicPlayerClassSet() {
        if (sNoMusicPlayerClassSet == null) {
            sNoMusicPlayerClassSet = new HashSet<>();
            sNoMusicPlayerClassSet.add("com.sec.android.app.music.common.player.soundplayer.SoundPlayer$MediaButtonReceiver");// 三星S6, 自带播放器, 包含一个没用的Receiver
        }
        return sNoMusicPlayerClassSet;
    }


}
