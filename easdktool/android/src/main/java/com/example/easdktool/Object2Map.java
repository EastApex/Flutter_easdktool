package com.example.easdktool;

import com.alibaba.fastjson.JSONObject;
import com.apex.bluetooth.model.EABleAncsSw;
import com.apex.bluetooth.model.EABleAutoCheckSleep;
import com.apex.bluetooth.model.EABleBatInfo;
import com.apex.bluetooth.model.EABleCombination;
import com.apex.bluetooth.model.EABleDailyGoal;
import com.apex.bluetooth.model.EABleGesturesBrightScreen;
import com.apex.bluetooth.model.EABleHabit;
import com.apex.bluetooth.model.EABleHr;
import com.apex.bluetooth.model.EABleInfoPush;
import com.apex.bluetooth.model.EABleMenuPage;
import com.apex.bluetooth.model.EABleMonitorReminder;
import com.apex.bluetooth.model.EABleNotDisturb;
import com.apex.bluetooth.model.EABlePersonInfo;
import com.apex.bluetooth.model.EABleReminder;
import com.apex.bluetooth.model.EABleSedentariness;
import com.apex.bluetooth.model.EABleSyncTime;
import com.apex.bluetooth.model.EABleWatchFace;
import com.apex.bluetooth.model.EABleWatchInfo;
import com.apex.bluetooth.model.TodayTotalData;
import com.example.easdktool.been.ReminderItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Object2Map {
    public Map watchInfo2Map(EABleWatchInfo eaBleWatchInfo) {
        Map map = new HashMap();
        map.put("eBindingInfo", eaBleWatchInfo.bindingInfo.getValue());
        map.put("agpsUpdateTimestamp", eaBleWatchInfo.getAgps_update_timestamp());
        map.put("firmwareVersion", eaBleWatchInfo.getFirmwareVersion());
        map.put("userId", eaBleWatchInfo.getUserId());
        map.put("id_p", eaBleWatchInfo.getWatchId());
        map.put("bleMacAddr", eaBleWatchInfo.getBle_mac_addr());
        map.put("isWaitForBinding", eaBleWatchInfo.getIs_wait_for_binding());
        String watchType = eaBleWatchInfo.getWatchType();
        if (watchType.equals("G01")) {
            map.put("type", "iTouch Flex");
        } else {
            map.put("type", eaBleWatchInfo.getWatchType());
        }
        return map;
    }

    public Map userInfo2Map(EABlePersonInfo eaBlePersonInfo) {
        Map<String, Integer> map = new HashMap();
        map.put("eSexInfo", eaBlePersonInfo.e_sex_info.getValue());
        map.put("eHandInfo", eaBlePersonInfo.e_hand_info.getValue());
        map.put("eSkinColor", eaBlePersonInfo.e_skin_color.getValue());
        map.put("age", eaBlePersonInfo.getAge());
        map.put("height", eaBlePersonInfo.getHeight());
        map.put("weight", eaBlePersonInfo.getWeight());
        return map;
    }

    public Map time2Map(EABleSyncTime eaBleSyncTime) {
        Map<String, Integer> map = new HashMap();
        map.put("timeHourType", eaBleSyncTime.e_hour_system.getValue());
        map.put("timeZone", eaBleSyncTime.e_time_zone.getValue());
        map.put("timeZoneHour", eaBleSyncTime.getTime_zone_hour());
        map.put("timeZoneMinute", eaBleSyncTime.getTime_zone_minute());
        map.put("year", eaBleSyncTime.getYear());
        map.put("month", eaBleSyncTime.getMonth());
        map.put("day", eaBleSyncTime.getDay());
        map.put("hour", eaBleSyncTime.getHour());
        map.put("minute", eaBleSyncTime.getMinute());
        map.put("second", eaBleSyncTime.getSecond());
        map.put("e_sync_mode", eaBleSyncTime.getE_sync_mode().getValue());
        return map;
    }

    public Map battery2Map(EABleBatInfo eaBleBatInfo) {
        Map<String, Integer> map = new HashMap();
        map.put("eStatus", eaBleBatInfo.e_status.getValue());
        map.put("level", eaBleBatInfo.getLevel());
        return map;
    }

    public Map disturb2Map(EABleNotDisturb eaBleNotDisturb) {
        Map<String, Integer> map = new HashMap();
        map.put("beginHour", eaBleNotDisturb.getBegin_hour());
        map.put("beginMinute", eaBleNotDisturb.getBegin_minute());
        map.put("endHour", eaBleNotDisturb.getEnd_hour());
        map.put("endMinute", eaBleNotDisturb.getBegin_minute());
        map.put("sw", eaBleNotDisturb.getSw());
        return map;
    }

    public Map goal2Map(EABleDailyGoal eaBleDailyGoal) {
        String sw = "sw";
        String goal = "goal";
        JSONObject jsonObject = new JSONObject();
        if (eaBleDailyGoal.getS_step() != null) {
            addKeyValues(sw, eaBleDailyGoal.getS_step().getSw(), goal, eaBleDailyGoal.getS_step().getGoal(), jsonObject, "sStep");
        }
        if (eaBleDailyGoal.getS_calorie() != null) {
            addKeyValues(sw, eaBleDailyGoal.getS_calorie().getSw(), goal, eaBleDailyGoal.getS_calorie().getGoal(), jsonObject, "sCalorie");
        }
        if (eaBleDailyGoal.getS_distance() != null) {
            addKeyValues(sw, eaBleDailyGoal.getS_distance().getSw(), goal, eaBleDailyGoal.getS_distance().getGoal(), jsonObject, "sDistance");
        }
        if (eaBleDailyGoal.getS_duration() != null) {
            addKeyValues(sw, eaBleDailyGoal.getS_duration().getSw(), goal, eaBleDailyGoal.getS_duration().getGoal(), jsonObject, "sDuration");
        }
        if (eaBleDailyGoal.getS_sleep() != null) {
            addKeyValues(sw, eaBleDailyGoal.getS_sleep().getSw(), goal, eaBleDailyGoal.getS_sleep().getGoal(), jsonObject, "sSleep");
        }
        Map map = jsonObject.getInnerMap();
        return map;
    }

    public Map sleep2Map(EABleAutoCheckSleep eaBleAutoCheckSleep) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("weekCycleBit", eaBleAutoCheckSleep.getWeek_cycle_bit());
        jsonObject.put("beginHour", eaBleAutoCheckSleep.getBegin_hour());
        jsonObject.put("beginMinute", eaBleAutoCheckSleep.getBegin_minute());
        jsonObject.put("endHour", eaBleAutoCheckSleep.getEnd_hour());
        jsonObject.put("endMinute", eaBleAutoCheckSleep.getEnd_minute());
        Map map = jsonObject.getInnerMap();
        return map;
    }

    public Map sedentariness2Map(EABleSedentariness eaBleSedentariness) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("interval", eaBleSedentariness.getInterval());
        jsonObject.put("weekCycleBit", eaBleSedentariness.getWeek_cycle_bit());
        jsonObject.put("beginHour", eaBleSedentariness.getBegin_hour());
        jsonObject.put("beginMinute", eaBleSedentariness.getBegin_minute());
        jsonObject.put("endHour", eaBleSedentariness.getEnd_hour());
        jsonObject.put("endMinute", eaBleSedentariness.getEnd_minute());
        jsonObject.put("stepThreshold", eaBleSedentariness.getStep_threshold());
        Map map = jsonObject.getInnerMap();
        return map;
    }

    public Map reminder2Map(EABleReminder eaBleReminder) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", eaBleReminder.id);
        jsonObject.put("e_ops", eaBleReminder.getE_ops().getValue());
        List<ReminderItem> items = new ArrayList<>();
        if (eaBleReminder.getS_index() != null && !eaBleReminder.getS_index().isEmpty()) {
            for (int i = 0; i < eaBleReminder.getS_index().size(); i++) {
                ReminderItem reminderItem = new ReminderItem();
                reminderItem.reminderEventType = eaBleReminder.getS_index().get(i).getE_type().getValue();
                reminderItem.id_p = eaBleReminder.getS_index().get(i).getId();
                reminderItem.hour = eaBleReminder.getS_index().get(i).getHour();
                reminderItem.minute = eaBleReminder.getS_index().get(i).getMinute();
                reminderItem.year = eaBleReminder.getS_index().get(i).getYear();
                reminderItem.month = eaBleReminder.getS_index().get(i).getMonth();
                reminderItem.day = eaBleReminder.getS_index().get(i).getDay();
                reminderItem.weekCycleBit = eaBleReminder.getS_index().get(i).getWeek_cycle_bit();
                reminderItem.sw = eaBleReminder.getS_index().get(i).getSw();
                reminderItem.secSw = eaBleReminder.getS_index().get(i).getSec_sw();
                reminderItem.sleepDuration = eaBleReminder.getS_index().get(i).getSleep_duration();
                reminderItem.remindActionType = eaBleReminder.getS_index().get(i).getE_action().getValue();
                reminderItem.content = eaBleReminder.getS_index().get(i).getContent();
                items.add(reminderItem);
            }
        }
        jsonObject.put("sIndexArray", items);
        Map map = jsonObject.getInnerMap();
        return map;
    }

    public Map ancs2Map(EABleAncsSw eaBleAncsSw) {
        JSONObject jsonObject = new JSONObject();
        if (eaBleAncsSw.getS_incomingcall() != null) {
            JSONObject jsonObject1 = new JSONObject();
            jsonObject1.put("sw", eaBleAncsSw.getS_incomingcall().getSw());
            jsonObject1.put("remindActionType", eaBleAncsSw.getS_incomingcall().getE_action().getValue());
            jsonObject.put("sIncomingcall", jsonObject1.getInnerMap());

        }
        if (eaBleAncsSw.getS_missedcall() != null) {
            JSONObject jsonObject2 = new JSONObject();
            jsonObject2.put("sw", eaBleAncsSw.getS_missedcall().getSw());
            jsonObject2.put("remindActionType", eaBleAncsSw.getS_missedcall().getE_action().getValue());
            jsonObject.put("sMissedcall", jsonObject2.getInnerMap());

        }
        if (eaBleAncsSw.getS_email() != null) {
            JSONObject jsonObject3 = new JSONObject();
            jsonObject3.put("sw", eaBleAncsSw.getS_email().getSw());
            jsonObject3.put("remindActionType", eaBleAncsSw.getS_email().getE_action().getValue());
            jsonObject.put("sEmail", jsonObject3.getInnerMap());
        }
        if (eaBleAncsSw.getS_sms() != null) {
            JSONObject jsonObject4 = new JSONObject();
            jsonObject4.put("sw", eaBleAncsSw.getS_sms().getSw());
            jsonObject4.put("remindActionType", eaBleAncsSw.getS_sms().getE_action().getValue());
            jsonObject.put("sSms", jsonObject4.getInnerMap());
        }
        if (eaBleAncsSw.getS_social() != null) {
            JSONObject jsonObject5 = new JSONObject();
            jsonObject5.put("sw", eaBleAncsSw.getS_social().getSw());
            jsonObject5.put("remindActionType", eaBleAncsSw.getS_social().getE_action().getValue());
            jsonObject.put("sSocial", jsonObject5.getInnerMap());
        }
        if (eaBleAncsSw.getS_schedule() != null) {
            JSONObject jsonObject6 = new JSONObject();
            jsonObject6.put("sw", eaBleAncsSw.getS_schedule().getSw());
            jsonObject6.put("remindActionType", eaBleAncsSw.getS_schedule().getE_action().getValue());
            jsonObject.put("sSchedule", jsonObject6.getInnerMap());
        }
        Map map = jsonObject.getInnerMap();
        return map;
    }

    public Map alertHeart2Map(EABleHr eaBleHr) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("sw", eaBleHr.getSw());
        jsonObject.put("maxHr", eaBleHr.getMax_hr());
        jsonObject.put("minHr", eaBleHr.getMin_hr());
        return jsonObject.getInnerMap();
    }

    public Map gestures2Map(EABleGesturesBrightScreen eaBleGesturesBrightScreen) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("eBrightSrc", eaBleGesturesBrightScreen.getBrightScreenSwitch().getValue());
        jsonObject.put("beginHour", eaBleGesturesBrightScreen.getBegin_hour());
        jsonObject.put("beginMinute", eaBleGesturesBrightScreen.getBegin_minute());
        jsonObject.put("endHour", eaBleGesturesBrightScreen.getEnd_hour());
        jsonObject.put("endMinute", eaBleGesturesBrightScreen.getEnd_minute());
        return jsonObject.getInnerMap();
    }

    public Map page2Map(EABleMenuPage eaBleMenuPage) {
        List<EABleMenuPage.MenuType> types = eaBleMenuPage.getAllSupportList();
        if (types == null || types.isEmpty()) {
            types = new ArrayList<>();
            types.add(EABleMenuPage.MenuType.page_pressure);
            types.add(EABleMenuPage.MenuType.page_heart_rate);
            types.add(EABleMenuPage.MenuType.page_menstrual_cycle);
            types.add(EABleMenuPage.MenuType.page_breath);
            types.add(EABleMenuPage.MenuType.page_music);
            types.add(EABleMenuPage.MenuType.page_sleep);
            types.add(EABleMenuPage.MenuType.page_weather);
            eaBleMenuPage.setAllSupportList(types);
        }
        List<Map> aList = new ArrayList<>();
        List<Map> sList = new ArrayList<>();

        for (int i = 0; i < eaBleMenuPage.getTypeList().size(); i++) {

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("eType", eaBleMenuPage.getTypeList().get(i).getValue());
            aList.add(jsonObject);
        }
        for (int i = 0; i < eaBleMenuPage.getAllSupportList().size(); i++) {

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("eType", eaBleMenuPage.getAllSupportList().get(i).getValue());
            sList.add(jsonObject);
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("aList", aList);
        jsonObject.put("sList", sList);
        return jsonObject.getInnerMap();
    }

    public Map combination2Map(EABleCombination eaBleCombination) {

        Map<String, Object> map = new HashMap();
        map.put("e_status", eaBleCombination.getE_status().getValue());
        map.put("e_vibrate_intensity", eaBleCombination.getE_vibrate_intensity().getValue());
        map.put("e_hand_info", eaBleCombination.getE_hand_info().getValue());
        map.put("e_unit_format", eaBleCombination.getE_unit_format().getValue());
        map.put("bat_level", eaBleCombination.getBat_level());
        map.put("auto_pressure_sw", eaBleCombination.getAuto_pressure_sw());
        map.put("auto_sedentariness_sw", eaBleCombination.getAuto_sedentariness_sw());
        map.put("gestures_sw", eaBleCombination.getGestures_sw());
        map.put("auto_check_hr_sw", eaBleCombination.getAuto_check_hr_sw());
        map.put("not_disturb_sw", eaBleCombination.getNot_disturb_sw());
        map.put("set_vibrate_intensity", eaBleCombination.getSet_vibrate_intensity());
        map.put("wf_id", eaBleCombination.getWf_id());
        map.put("user_wf_id", eaBleCombination.getUser_wf_id());
        return map;
    }

    public Map face2Map(EABleWatchFace eaBleWatchFace) {
        Map<String, Object> map = new HashMap();
        map.put("id", eaBleWatchFace.getId());
        map.put("user_wf_id", eaBleWatchFace.getUser_wf_id());
        map.put("user_wf_id_0", eaBleWatchFace.getUser_wf_id_0());
        map.put("user_wf_id_1", eaBleWatchFace.getUser_wf_id_1());
        map.put("user_wf_id_2", eaBleWatchFace.getUser_wf_id_2());
        map.put("user_wf_id_3", eaBleWatchFace.getUser_wf_id_3());
        return map;
    }

    public Map appPushSwitch2Map(EABleInfoPush eaBleInfoPush) {
        Map<String, Boolean> map = new HashMap<>();
        if (eaBleInfoPush.getS_app_sw() != null && !eaBleInfoPush.getS_app_sw().isEmpty()) {
            map.put("unknow", eaBleInfoPush.getS_app_sw().get(0).getSw() == 1 ? true : false);
            map.put("wechat", eaBleInfoPush.getS_app_sw().get(1).getSw() == 1 ? true : false);
            map.put("qq", eaBleInfoPush.getS_app_sw().get(2).getSw() == 1 ? true : false);
            map.put("facebook", eaBleInfoPush.getS_app_sw().get(3).getSw() == 1 ? true : false);
            map.put("twitter", eaBleInfoPush.getS_app_sw().get(4).getSw() == 1 ? true : false);
            map.put("messenger", eaBleInfoPush.getS_app_sw().get(5).getSw() == 1 ? true : false);
            map.put("hangouts", eaBleInfoPush.getS_app_sw().get(6).getSw() == 1 ? true : false);
            map.put("gmail", eaBleInfoPush.getS_app_sw().get(7).getSw() == 1 ? true : false);
            map.put("viber", eaBleInfoPush.getS_app_sw().get(8).getSw() == 1 ? true : false);
            map.put("snapchat", eaBleInfoPush.getS_app_sw().get(9).getSw() == 1 ? true : false);
            map.put("whatsApp", eaBleInfoPush.getS_app_sw().get(10).getSw() == 1 ? true : false);
            map.put("instagram", eaBleInfoPush.getS_app_sw().get(11).getSw() == 1 ? true : false);
            map.put("linkedin", eaBleInfoPush.getS_app_sw().get(12).getSw() == 1 ? true : false);
            map.put("line", eaBleInfoPush.getS_app_sw().get(13).getSw() == 1 ? true : false);
            map.put("skype", eaBleInfoPush.getS_app_sw().get(14).getSw() == 1 ? true : false);
            map.put("booking", eaBleInfoPush.getS_app_sw().get(15).getSw() == 1 ? true : false);
            map.put("airbnb", eaBleInfoPush.getS_app_sw().get(16).getSw() == 1 ? true : false);
            map.put("flipboard", eaBleInfoPush.getS_app_sw().get(17).getSw() == 1 ? true : false);
            map.put("spotify", eaBleInfoPush.getS_app_sw().get(18).getSw() == 1 ? true : false);
            map.put("pandora", eaBleInfoPush.getS_app_sw().get(19).getSw() == 1 ? true : false);
            map.put("telegram", eaBleInfoPush.getS_app_sw().get(20).getSw() == 1 ? true : false);
            map.put("dropbox", eaBleInfoPush.getS_app_sw().get(21).getSw() == 1 ? true : false);
            map.put("waze", eaBleInfoPush.getS_app_sw().get(22).getSw() == 1 ? true : false);
            map.put("lift", eaBleInfoPush.getS_app_sw().get(23).getSw() == 1 ? true : false);
            map.put("slack", eaBleInfoPush.getS_app_sw().get(24).getSw() == 1 ? true : false);
            map.put("shazam", eaBleInfoPush.getS_app_sw().get(25).getSw() == 1 ? true : false);
            map.put("deliveroo", eaBleInfoPush.getS_app_sw().get(26).getSw() == 1 ? true : false);
            map.put("kakaotalk", eaBleInfoPush.getS_app_sw().get(27).getSw() == 1 ? true : false);
            map.put("pinterest", eaBleInfoPush.getS_app_sw().get(28).getSw() == 1 ? true : false);
            map.put("tumblr", eaBleInfoPush.getS_app_sw().get(29).getSw() == 1 ? true : false);
            map.put("vk", eaBleInfoPush.getS_app_sw().get(30).getSw() == 1 ? true : false);
            map.put("youtube", eaBleInfoPush.getS_app_sw().get(31).getSw() == 1 ? true : false);
            if (eaBleInfoPush.getS_app_sw().size() > 32) {
                map.put("amazon", eaBleInfoPush.getS_app_sw().get(32).getSw() == 1 ? true : false);
                map.put("discord", eaBleInfoPush.getS_app_sw().get(33).getSw() == 1 ? true : false);
                map.put("github", eaBleInfoPush.getS_app_sw().get(34).getSw() == 1 ? true : false);
                map.put("googleMaps", eaBleInfoPush.getS_app_sw().get(35).getSw() == 1 ? true : false);
                map.put("newsBreak", eaBleInfoPush.getS_app_sw().get(36).getSw() == 1 ? true : false);
                map.put("rReddit", eaBleInfoPush.getS_app_sw().get(37).getSw() == 1 ? true : false);
                map.put("teams", eaBleInfoPush.getS_app_sw().get(38).getSw() == 1 ? true : false);
                map.put("tiktok", eaBleInfoPush.getS_app_sw().get(39).getSw() == 1 ? true : false);
                map.put("twitch", eaBleInfoPush.getS_app_sw().get(40).getSw() == 1 ? true : false);
                map.put("uberEats", eaBleInfoPush.getS_app_sw().get(41).getSw() == 1 ? true : false);
            }


        }
        return map;
    }

    public Map habit2Map(EABleHabit eaBleHabit) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", eaBleHabit.id);
        jsonObject.put("eOps", eaBleHabit.getE_ops().getValue());
        List<JSONObject> items = new ArrayList<>();
        if (eaBleHabit.getItemList() != null && !eaBleHabit.getItemList().isEmpty()) {
            for (int i = 0; i < eaBleHabit.getItemList().size(); i++) {

                EABleHabit.HabitItem habitItem = eaBleHabit.getItemList().get(i);
                JSONObject item = new JSONObject();
                item.put("eIconId", habitItem.getE_icon_id().getValue());
                item.put("id_p", habitItem.getId());
                item.put("beginHour", habitItem.getBegin_hour());
                item.put("beginMinute", habitItem.getBegin_minute());
                item.put("endHour", habitItem.getEnd_hour());
                item.put("endMinute", habitItem.getEnd_minute());
                item.put("r", habitItem.getRedColor());
                item.put("g", habitItem.getGreenColor());
                item.put("b", habitItem.getBlueColor());
                item.put("duration", habitItem.getDuration());
                item.put("eAction", habitItem.getE_action().getValue());
                item.put("content", habitItem.getContent());
                item.put("eFlag", habitItem.getHabitState().getValue());
                items.add(item);
            }
        }
        jsonObject.put("sIndexArray", items);
        Map map = jsonObject.getInnerMap();
        return map;
    }

    public Map today2Map(TodayTotalData todayTotalData) {
        Map<String, Integer> map = new HashMap();
        map.put("steps", todayTotalData.getSteps());
        map.put("calorie", todayTotalData.getCalorie());
        map.put("distance", todayTotalData.getDistance());
        map.put("duration", todayTotalData.getDuration());
        return map;
    }

    public Map monitorReminder2Map(EABleMonitorReminder eaBleMonitorReminder) {
        Map<String, Integer> map = new HashMap();
        map.put("eReminderType", eaBleMonitorReminder.getEaBleMonitorType().getValue());
        map.put("sw", eaBleMonitorReminder.getReminderSwitch());
        map.put("interval", eaBleMonitorReminder.getInterval());
        map.put("weekCycleBit", eaBleMonitorReminder.getWeek_cycle_bit());
        map.put("beginHour", eaBleMonitorReminder.getBegin_hour());
        map.put("beginMinute", eaBleMonitorReminder.getBegin_minute());
        map.put("endHour", eaBleMonitorReminder.getEnd_hour());
        map.put("endMinute", eaBleMonitorReminder.getEnd_minute());
        map.put("stepThreshold", eaBleMonitorReminder.getStep_threshold());
        map.put("cup", eaBleMonitorReminder.getCup());
        return map;
    }

    private void addKeyValues(String key1, Object value1, String key2, Object value2, JSONObject jsonObject, String jsonObjectKey) {
        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put(key1, value1);
        jsonObject1.put(key2, value2);
        jsonObject.put(jsonObjectKey, jsonObject1.getInnerMap());
    }
}
