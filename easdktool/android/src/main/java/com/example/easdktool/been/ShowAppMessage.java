package com.example.easdktool.been;

import com.alibaba.fastjson.JSONObject;
import com.apex.ax_bluetooth.model.EABleInfoPush;

import java.util.ArrayList;
import java.util.List;

public class ShowAppMessage {

    public boolean unknow;
    public boolean wechat;
    public boolean qq;
    public boolean facebook;
    public boolean twitter;
    public boolean messenger;
    public boolean hangouts;
    public boolean gmail;
    public boolean viber;
    public boolean snapchat;
    public boolean whatsApp;
    public boolean instagram;
    public boolean linkedin;
    public boolean line;
    public boolean skype;
    public boolean booking;
    public boolean airbnb;
    public boolean flipboard;
    public boolean spotify;
    public boolean pandora;
    public boolean telegram;
    public boolean dropbox;
    public boolean waze;
    public boolean lift;
    public boolean slack;
    public boolean shazam;
    public boolean deliveroo;
    public boolean kakaotalk;
    public boolean pinterest;
    public boolean tumblr;
    public boolean vk;
    public boolean youtube;
    public boolean amazon;
    public boolean discord;
    public boolean github;
    public boolean googleMaps;
    public boolean newsBreak;
    public boolean rReddit;
    public boolean teams;
    public boolean tiktok;
    public boolean twitch;
    public boolean uberEats;

    public List<EABleInfoPush.EABlePushSwitch> getSwitchList(){

        List<EABleInfoPush.EABlePushSwitch> switchList = new ArrayList<>();
        switchList.add(getPushSwitch(unknow));
        switchList.add(getPushSwitch(wechat));
        switchList.add(getPushSwitch(qq));
        switchList.add(getPushSwitch(facebook));
        switchList.add(getPushSwitch(twitter));
        switchList.add(getPushSwitch(messenger));
        switchList.add(getPushSwitch(hangouts));
        switchList.add(getPushSwitch(gmail));
        switchList.add(getPushSwitch(viber));
        switchList.add(getPushSwitch(snapchat));
        switchList.add(getPushSwitch(whatsApp));
        switchList.add(getPushSwitch(instagram));
        switchList.add(getPushSwitch(linkedin));
        switchList.add(getPushSwitch(line));
        switchList.add(getPushSwitch(skype));
        switchList.add(getPushSwitch(booking));
        switchList.add(getPushSwitch(airbnb));
        switchList.add(getPushSwitch(flipboard));
        switchList.add(getPushSwitch(spotify));
        switchList.add(getPushSwitch(pandora));
        switchList.add(getPushSwitch(telegram));
        switchList.add(getPushSwitch(dropbox));
        switchList.add(getPushSwitch(waze));
        switchList.add(getPushSwitch(lift));
        switchList.add(getPushSwitch(slack));
        switchList.add(getPushSwitch(shazam));
        switchList.add(getPushSwitch(deliveroo));
        switchList.add(getPushSwitch(kakaotalk));
        switchList.add(getPushSwitch(pinterest));
        switchList.add(getPushSwitch(tumblr));
        switchList.add(getPushSwitch(vk));
        switchList.add(getPushSwitch(youtube));
        switchList.add(getPushSwitch(amazon));
        switchList.add(getPushSwitch(discord));
        switchList.add(getPushSwitch(github));
        switchList.add(getPushSwitch(googleMaps));
        switchList.add(getPushSwitch(newsBreak));
        switchList.add(getPushSwitch(rReddit));
        switchList.add(getPushSwitch(teams));
        switchList.add(getPushSwitch(tiktok));
        switchList.add(getPushSwitch(twitch));
        switchList.add(getPushSwitch(uberEats));
        return switchList;
    }

    EABleInfoPush.EABlePushSwitch getPushSwitch(Boolean key){

        EABleInfoPush.EABlePushSwitch pushSwitch = new EABleInfoPush.EABlePushSwitch();
        pushSwitch.setSw(key ? 1 : 0);
        return pushSwitch;
    }


}
