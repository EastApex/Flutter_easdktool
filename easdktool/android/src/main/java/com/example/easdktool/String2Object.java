package com.example.easdktool;

import android.text.TextUtils;

import com.alibaba.fastjson.JSONObject;
import com.apex.ax_bluetooth.enumeration.CommonAction;
import com.apex.ax_bluetooth.enumeration.HabitIcon;
import com.apex.ax_bluetooth.enumeration.HabitState;
import com.apex.ax_bluetooth.enumeration.PersonHand;
import com.apex.ax_bluetooth.enumeration.TimeZone;
import com.apex.ax_bluetooth.enumeration.UnitFormat;
import com.apex.ax_bluetooth.enumeration.VibrationIntensity;
import com.apex.ax_bluetooth.model.EABleAncsSw;
import com.apex.ax_bluetooth.model.EABleAutoStressMonitor;
import com.apex.ax_bluetooth.model.EABleBindInfo;
import com.apex.ax_bluetooth.model.EABleContact;
import com.apex.ax_bluetooth.model.EABleDailyGoal;
import com.apex.ax_bluetooth.model.EABleDevUnit;
import com.apex.ax_bluetooth.model.EABleDeviceLanguage;
import com.apex.ax_bluetooth.model.EABleGesturesBrightScreen;
import com.apex.ax_bluetooth.model.EABleHabit;
import com.apex.ax_bluetooth.model.EABleHr;
import com.apex.ax_bluetooth.model.EABleInfoPush;
import com.apex.ax_bluetooth.model.EABleMenuPage;
import com.apex.ax_bluetooth.model.EABleMonitorReminder;
import com.apex.ax_bluetooth.model.EABleMusicRespond;
import com.apex.ax_bluetooth.model.EABleNotDisturb;
import com.apex.ax_bluetooth.model.EABlePeriodReminder;
import com.apex.ax_bluetooth.model.EABlePersonInfo;
import com.apex.ax_bluetooth.model.EABlePhysiologyData;
import com.apex.ax_bluetooth.model.EABleReminder;
import com.apex.ax_bluetooth.model.EABleSedentariness;
import com.apex.ax_bluetooth.model.EABleSleepBloodSwitch;
import com.apex.ax_bluetooth.model.EABleSocialContact;
import com.apex.ax_bluetooth.model.EABleSyncTime;
import com.apex.ax_bluetooth.model.EABleWatchFace;
import com.apex.ax_bluetooth.model.EABleWeather;
import com.apex.ax_bluetooth.utils.LogUtils;
import com.example.easdktool.been.ShowAppMessage;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class String2Object {
    final String TAG = this.getClass().getSimpleName();

    public EABlePersonInfo string2User(String jsonString) {
        Map<String, Object> personInfo = JSONObject.parseObject(jsonString, Map.class);
        EABlePersonInfo eaBlePersonInfo = new EABlePersonInfo();
        eaBlePersonInfo.setAge((Integer) personInfo.get("age"));
        eaBlePersonInfo.setHeight((Integer) personInfo.get("height"));
        eaBlePersonInfo.setWeight((Integer) personInfo.get("weight"));
        int pHand = (int) personInfo.get("eHandInfo");
        if (pHand == 0) {
            eaBlePersonInfo.setE_hand_info(PersonHand.left);
        } else {
            eaBlePersonInfo.setE_hand_info(PersonHand.right);
        }
        int eSex = (int) personInfo.get("eSexInfo");
        if (eSex == 0) {
            eaBlePersonInfo.setE_sex_info(EABlePersonInfo.PersonSex.male);
        } else {
            eaBlePersonInfo.setE_sex_info(EABlePersonInfo.PersonSex.female);
        }
        int eSkin = (int) personInfo.get("eSkinColor");
        if (eSkin == 0) {
            eaBlePersonInfo.setE_skin_color(EABlePersonInfo.SkinColor.skin_white);
        } else if (eSkin == 1) {
            eaBlePersonInfo.setE_skin_color(EABlePersonInfo.SkinColor.skin_white_yellow);
        } else if (eSkin == 2) {
            eaBlePersonInfo.setE_skin_color(EABlePersonInfo.SkinColor.skin_yellow);
        } else if (eSkin == 3) {
            eaBlePersonInfo.setE_skin_color(EABlePersonInfo.SkinColor.skin_yellow_black);
        } else if (eSkin == 4) {
            eaBlePersonInfo.setE_skin_color(EABlePersonInfo.SkinColor.skin_balck);
        }
        return eaBlePersonInfo;
    }

    public EABleSyncTime string2Time(String jsonString) {
        Map<String, Integer> syncTime = JSONObject.parseObject(jsonString, Map.class);
        EABleSyncTime eaBleSyncTime = new EABleSyncTime();
        eaBleSyncTime.setYear(syncTime.get("year"));
        eaBleSyncTime.setMonth(syncTime.get("month"));
        eaBleSyncTime.setDay(syncTime.get("day"));
        eaBleSyncTime.setHour(syncTime.get("hour"));
        eaBleSyncTime.setMinute(syncTime.get("minute"));
        eaBleSyncTime.setSecond(syncTime.get("second"));
        eaBleSyncTime.setTime_zone_hour(syncTime.get("timeZoneHour"));
        eaBleSyncTime.setTime_zone_minute(syncTime.get("timeZoneMinute"));
        int tType = syncTime.get("timeHourType");
        if (tType == 0) {
            eaBleSyncTime.setE_hour_system(EABleSyncTime.HourSystem.hour_12);
        } else {
            eaBleSyncTime.setE_hour_system(EABleSyncTime.HourSystem.hour_24);
        }
        int tZone = syncTime.get("timeZone");
        if (tZone == 0) {
            eaBleSyncTime.setE_time_zone(TimeZone.zero);
        } else if (tZone == 1) {
            eaBleSyncTime.setE_time_zone(TimeZone.east);
        } else if (tZone == 2) {
            eaBleSyncTime.setE_time_zone(TimeZone.west);
        } else {
            eaBleSyncTime.setE_time_zone(TimeZone.unknown);
        }
        return eaBleSyncTime;
    }

    public EABleDeviceLanguage string2Language(String jsonString) {
        Map<String, Integer> map = JSONObject.parseObject(jsonString, Map.class);
        int language = (int) map.get("language");
        EABleDeviceLanguage eaBleDeviceLanguage = new EABleDeviceLanguage();
        if (language == 0) {
            eaBleDeviceLanguage.setE_type(EABleDeviceLanguage.LanguageType.default_type);
        } else if (language == 1) {
            eaBleDeviceLanguage.setE_type(EABleDeviceLanguage.LanguageType.english);
        } else if (language == 2) {
            eaBleDeviceLanguage.setE_type(EABleDeviceLanguage.LanguageType.chinese_simplifid);
        } else if (language == 3) {
            eaBleDeviceLanguage.setE_type(EABleDeviceLanguage.LanguageType.chinese_traditional);
        } else if (language == 4) {
            eaBleDeviceLanguage.setE_type(EABleDeviceLanguage.LanguageType.korean);
        } else if (language == 5) {
            eaBleDeviceLanguage.setE_type(EABleDeviceLanguage.LanguageType.thai);
        } else if (language == 6) {
            eaBleDeviceLanguage.setE_type(EABleDeviceLanguage.LanguageType.japanese);
        } else if (language == 7) {
            eaBleDeviceLanguage.setE_type(EABleDeviceLanguage.LanguageType.spanish);
        } else if (language == 8) {
            eaBleDeviceLanguage.setE_type(EABleDeviceLanguage.LanguageType.francais);
        } else if (language == 9) {
            eaBleDeviceLanguage.setE_type(EABleDeviceLanguage.LanguageType.deutsch);
        } else if (language == 10) {
            eaBleDeviceLanguage.setE_type(EABleDeviceLanguage.LanguageType.italiano);
        } else if (language == 11) {
            eaBleDeviceLanguage.setE_type(EABleDeviceLanguage.LanguageType.polski);
        } else if (language == 12) {
            eaBleDeviceLanguage.setE_type(EABleDeviceLanguage.LanguageType.portuguese);
        } else if (language == 13) {
            eaBleDeviceLanguage.setE_type(EABleDeviceLanguage.LanguageType.russian);
        } else if (language == 14) {
            eaBleDeviceLanguage.setE_type(EABleDeviceLanguage.LanguageType.dutch);
        } else if (language == 15) {
            eaBleDeviceLanguage.setE_type(EABleDeviceLanguage.LanguageType.arabic);
        } else if (language == 16) {
            eaBleDeviceLanguage.setE_type(EABleDeviceLanguage.LanguageType.greek);
        } else if (language == 17) {
            eaBleDeviceLanguage.setE_type(EABleDeviceLanguage.LanguageType.hebrew);
        } else if (language == 18) {
            eaBleDeviceLanguage.setE_type(EABleDeviceLanguage.LanguageType.swedish);
        } else if (language == 19) {
            eaBleDeviceLanguage.setE_type(EABleDeviceLanguage.LanguageType.osmanli);
        } else if (language == 20) {
            eaBleDeviceLanguage.setE_type(EABleDeviceLanguage.LanguageType.czech);
        } else if (language == 21) {
            eaBleDeviceLanguage.setE_type(EABleDeviceLanguage.LanguageType.indonesia);
        } else if (language == 22) {
            eaBleDeviceLanguage.setE_type(EABleDeviceLanguage.LanguageType.melayu);
        } else if (language == 23) {
            eaBleDeviceLanguage.setE_type(EABleDeviceLanguage.LanguageType.farsi);
        } else if (language == 24) {
            eaBleDeviceLanguage.setE_type(EABleDeviceLanguage.LanguageType.vietnamese);
        } else if (language == 25) {
            eaBleDeviceLanguage.setE_type(EABleDeviceLanguage.LanguageType.belarusian);
        } else if (language == 26) {
            eaBleDeviceLanguage.setE_type(EABleDeviceLanguage.LanguageType.hungarian);
        } else if (language == 27) {
            eaBleDeviceLanguage.setE_type(EABleDeviceLanguage.LanguageType.hindi);
        } else if (language == 28) {
            eaBleDeviceLanguage.setE_type(EABleDeviceLanguage.LanguageType.ukrainian);
        } else if (language == 29) {
            eaBleDeviceLanguage.setE_type(EABleDeviceLanguage.LanguageType.romanian);
        } else if (language == 30) {
            eaBleDeviceLanguage.setE_type(EABleDeviceLanguage.LanguageType.moldovan);
        } else if (language == 31) {
            eaBleDeviceLanguage.setE_type(EABleDeviceLanguage.LanguageType.bengali);
        } else if (language == 32) {
            eaBleDeviceLanguage.setE_type(EABleDeviceLanguage.LanguageType.slovak);
        } else if (language == 33) {
            eaBleDeviceLanguage.setE_type(EABleDeviceLanguage.LanguageType.danish);
        } else if (language == 34) {
            eaBleDeviceLanguage.setE_type(EABleDeviceLanguage.LanguageType.norwegian);
        } else if (language == 35) {
            eaBleDeviceLanguage.setE_type(EABleDeviceLanguage.LanguageType.finnish);
        } else if (language == 36) {
            eaBleDeviceLanguage.setE_type(EABleDeviceLanguage.LanguageType.unknown);
        }
        return eaBleDeviceLanguage;
    }

    public EABleDevUnit string2Unit(String jsonString) {
        Map<String, Integer> map = JSONObject.parseObject(jsonString, Map.class);
        int unit = (int) map.get("unit");
        EABleDevUnit eaBleDevUnit = new EABleDevUnit();
        if (unit == 0) {
            eaBleDevUnit.setE_format(UnitFormat.metric);
        } else {
            eaBleDevUnit.setE_format(UnitFormat.british);
        }
        return eaBleDevUnit;
    }

    public EABleNotDisturb string2Disturb(String jsonString) {
        Map<String, Integer> map = JSONObject.parseObject(jsonString, Map.class);
        EABleNotDisturb eaBleNotDisturb = new EABleNotDisturb();
        eaBleNotDisturb.setSw(map.get("sw"));
        eaBleNotDisturb.setBegin_hour(map.get("beginHour"));
        eaBleNotDisturb.setBegin_minute(map.get("beginMinute"));
        eaBleNotDisturb.setEnd_hour(map.get("endHour"));
        eaBleNotDisturb.setEnd_minute(map.get("endMinute"));
        eaBleNotDisturb.setWatch_sw(map.get("watchNotDisturbSw"));
        return eaBleNotDisturb;
    }

    public EABleDailyGoal string2Goal(String jsonString) {
        Map<String, JSONObject> dailyGoal = JSONObject.parseObject(jsonString, Map.class);
        JSONObject calorie = dailyGoal.get("sCalorie");
        JSONObject step = dailyGoal.get("sStep");
        JSONObject distance = dailyGoal.get("sDistance");
        JSONObject duration = dailyGoal.get("sDuration");
        JSONObject sleep = dailyGoal.get("sSleep");
        EABleDailyGoal eaBleDailyData = new EABleDailyGoal();
        if (sleep != null) {
            EABleDailyGoal.EABleDaily eaBleDaily = new EABleDailyGoal.EABleDaily();
            eaBleDaily.setGoal(sleep.getInteger("goal"));
            eaBleDaily.setSw(sleep.getInteger("sw"));
            eaBleDailyData.setS_sleep(eaBleDaily);
        }
        if (duration != null) {
            EABleDailyGoal.EABleDaily eaBleDaily = new EABleDailyGoal.EABleDaily();
            eaBleDaily.setGoal(duration.getInteger("goal"));
            eaBleDaily.setSw(duration.getInteger("sw"));
            eaBleDailyData.setS_duration(eaBleDaily);
        }
        if (distance != null) {
            EABleDailyGoal.EABleDaily eaBleDaily = new EABleDailyGoal.EABleDaily();
            eaBleDaily.setGoal(distance.getInteger("goal"));
            eaBleDaily.setSw(distance.getInteger("sw"));
            eaBleDailyData.setS_distance(eaBleDaily);
        }
        if (step != null) {
            EABleDailyGoal.EABleDaily eaBleDaily = new EABleDailyGoal.EABleDaily();
            eaBleDaily.setGoal(step.getInteger("goal"));
            eaBleDaily.setSw(step.getInteger("sw"));
            eaBleDailyData.setS_step(eaBleDaily);
        }
        if (calorie != null) {
            EABleDailyGoal.EABleDaily eaBleDaily = new EABleDailyGoal.EABleDaily();
            eaBleDaily.setGoal(calorie.getInteger("goal"));
            eaBleDaily.setSw(calorie.getInteger("sw"));
            eaBleDailyData.setS_calorie(eaBleDaily);
        }
        return eaBleDailyData;
    }

    public int string2HeartSwitch(String jsonString) {
        Map<String, Integer> map = JSONObject.parseObject(jsonString, Map.class);
        int intervalTime = (int) map.get("interval");
        return intervalTime;
    }

    public EABleSedentariness string2Sedentariness(String jsonString) {
        Map<String, Integer> map = JSONObject.parseObject(jsonString, Map.class);
        EABleSedentariness eaBleSedentariness = new EABleSedentariness();
        eaBleSedentariness.setBegin_hour(map.get("beginHour"));
        eaBleSedentariness.setBegin_minute(map.get("beginMinute"));
        eaBleSedentariness.setEnd_hour(map.get("endHour"));
        eaBleSedentariness.setEnd_minute(map.get("endMinute"));
        eaBleSedentariness.setInterval(map.get("interval"));
        eaBleSedentariness.setStep_threshold(map.get("stepThreshold"));
        eaBleSedentariness.setWeek_cycle_bit(map.get("weekCycleBit"));
        eaBleSedentariness.setNoon_begin_hour(map.get("noonBeginHour"));
        eaBleSedentariness.setNoon_begin_minute(map.get("noonBeginMinute"));
        eaBleSedentariness.setNoon_end_hour(map.get("noonEndHour"));
        eaBleSedentariness.setNoon_begin_minute(map.get("noonEndMinute"));
        eaBleSedentariness.setNoon_sw(map.get("noonSw"));
        eaBleSedentariness.setSw(map.get("sw"));
        return eaBleSedentariness;
    }

    public EABleWeather string2Weather(String jsonString) {
        Map<String, Object> map = JSONObject.parseObject(jsonString, Map.class);
        EABleWeather eaBleWeather = new EABleWeather();
        int cTemperature = (int) map.get("currentTemperature");
        eaBleWeather.setCurrent_temperature(cTemperature);
        eaBleWeather.setPlace((String) map.get("place"));
        int temperatureUnit = (int) map.get("eFormat");
        eaBleWeather.setTemperatureUnit(temperatureUnit == 0 ? EABleWeather.TemperatureUnit.centigrade : EABleWeather.TemperatureUnit.fahrenheit);
        List<JSONObject> wArray = (List<JSONObject>) map.get("sDayArray");
        if (wArray != null && !wArray.isEmpty()) {
            List<EABleWeather.EABleWeatherItem> itemList = new ArrayList<>();
            eaBleWeather.setS_day(itemList);
            for (int i = 0; i < wArray.size(); i++) {
                JSONObject wMap = wArray.get(i);
                EABleWeather.EABleWeatherItem eaBleWeatherItem = new EABleWeather.EABleWeatherItem();
                eaBleWeatherItem.setAir_humidity((Integer) wMap.getInteger("airGrade"));
                eaBleWeatherItem.setCloudiness((Integer) wMap.getInteger("cloudiness"));
                eaBleWeatherItem.setMax_temperature((Integer) wMap.getInteger("maxTemperature"));
                eaBleWeatherItem.setMax_wind_power((Integer) wMap.getInteger("maxWindPower"));
                eaBleWeatherItem.setMin_temperature((Integer) wMap.getInteger("minTemperature"));
                eaBleWeatherItem.setMin_wind_power((Integer) wMap.getInteger("minWindPower"));
                int riseTime = wMap.getInteger("sunriseTimestamp");
                int setTime = wMap.getInteger("sunsetTimestamp");
                eaBleWeatherItem.setSunrise_timestamp(riseTime);
                eaBleWeatherItem.setSunset_timestamp(setTime);
                int dayType = (int) wMap.get("eDayType");
                if (dayType == 0) {
                    eaBleWeatherItem.setE_day_type(EABleWeather.WeatherType.clear);
                } else if (dayType == 1) {
                    eaBleWeatherItem.setE_day_type(EABleWeather.WeatherType.cloudy);
                } else if (dayType == 2) {
                    eaBleWeatherItem.setE_day_type(EABleWeather.WeatherType.gloomy);
                } else if (dayType == 3) {
                    eaBleWeatherItem.setE_day_type(EABleWeather.WeatherType.drizzle);
                } else if (dayType == 4) {
                    eaBleWeatherItem.setE_day_type(EABleWeather.WeatherType.moderate_rain);
                } else if (dayType == 5) {
                    eaBleWeatherItem.setE_day_type(EABleWeather.WeatherType.thunderstorm);
                } else if (dayType == 6) {
                    eaBleWeatherItem.setE_day_type(EABleWeather.WeatherType.heavy_rain);
                } else if (dayType == 7) {
                    eaBleWeatherItem.setE_day_type(EABleWeather.WeatherType.sleet);
                } else if (dayType == 8) {
                    eaBleWeatherItem.setE_day_type(EABleWeather.WeatherType.light_snow);
                } else if (dayType == 9) {
                    eaBleWeatherItem.setE_day_type(EABleWeather.WeatherType.moderate_snow);
                } else if (dayType == 10) {
                    eaBleWeatherItem.setE_day_type(EABleWeather.WeatherType.heavy_snow);
                } else if (dayType == 11) {
                    eaBleWeatherItem.setE_day_type(EABleWeather.WeatherType.typhoon);
                } else if (dayType == 12) {
                    eaBleWeatherItem.setE_day_type(EABleWeather.WeatherType.dust);
                } else if (dayType == 13) {
                    eaBleWeatherItem.setE_day_type(EABleWeather.WeatherType.sandstorm);
                } else if (dayType == 14) {
                    eaBleWeatherItem.setE_day_type(EABleWeather.WeatherType.fog);
                } else if (dayType == 15) {
                    eaBleWeatherItem.setE_day_type(EABleWeather.WeatherType.haze);
                }
                int nightType = (int) wMap.get("eNightType");
                if (nightType == 0) {
                    eaBleWeatherItem.setE_night_type(EABleWeather.WeatherType.clear);
                } else if (nightType == 1) {
                    eaBleWeatherItem.setE_night_type(EABleWeather.WeatherType.cloudy);
                } else if (nightType == 2) {
                    eaBleWeatherItem.setE_night_type(EABleWeather.WeatherType.gloomy);
                } else if (nightType == 3) {
                    eaBleWeatherItem.setE_night_type(EABleWeather.WeatherType.drizzle);
                } else if (nightType == 4) {
                    eaBleWeatherItem.setE_night_type(EABleWeather.WeatherType.moderate_rain);
                } else if (nightType == 5) {
                    eaBleWeatherItem.setE_night_type(EABleWeather.WeatherType.thunderstorm);
                } else if (nightType == 6) {
                    eaBleWeatherItem.setE_night_type(EABleWeather.WeatherType.heavy_rain);
                } else if (nightType == 7) {
                    eaBleWeatherItem.setE_night_type(EABleWeather.WeatherType.sleet);
                } else if (nightType == 8) {
                    eaBleWeatherItem.setE_night_type(EABleWeather.WeatherType.light_snow);
                } else if (nightType == 9) {
                    eaBleWeatherItem.setE_night_type(EABleWeather.WeatherType.moderate_snow);
                } else if (nightType == 10) {
                    eaBleWeatherItem.setE_night_type(EABleWeather.WeatherType.heavy_snow);
                } else if (nightType == 11) {
                    eaBleWeatherItem.setE_night_type(EABleWeather.WeatherType.typhoon);
                } else if (nightType == 12) {
                    eaBleWeatherItem.setE_night_type(EABleWeather.WeatherType.dust);
                } else if (nightType == 13) {
                    eaBleWeatherItem.setE_night_type(EABleWeather.WeatherType.sandstorm);
                } else if (nightType == 14) {
                    eaBleWeatherItem.setE_night_type(EABleWeather.WeatherType.fog);
                } else if (nightType == 15) {
                    eaBleWeatherItem.setE_night_type(EABleWeather.WeatherType.haze);
                }
                int moon = (int) wMap.get("eMoon");
                if (moon == 0) {
                    eaBleWeatherItem.setE_moon(EABleWeather.Moon.new_moon);
                } else if (moon == 1) {
                    eaBleWeatherItem.setE_moon(EABleWeather.Moon.waxing_crescent_moon);
                } else if (moon == 2) {
                    eaBleWeatherItem.setE_moon(EABleWeather.Moon.quarter_moon);
                } else if (moon == 3) {
                    eaBleWeatherItem.setE_moon(EABleWeather.Moon.half_moon_1);
                } else if (moon == 4) {
                    eaBleWeatherItem.setE_moon(EABleWeather.Moon.waxing_gibbous_moon);
                } else if (moon == 5) {
                    eaBleWeatherItem.setE_moon(EABleWeather.Moon.full_moon);
                } else if (moon == 6) {
                    eaBleWeatherItem.setE_moon(EABleWeather.Moon.waning_gibbous_moon);
                } else if (moon == 7) {
                    eaBleWeatherItem.setE_moon(EABleWeather.Moon.half_moon_2);
                } else if (moon == 8) {
                    eaBleWeatherItem.setE_moon(EABleWeather.Moon.last_quarter_moon);
                } else if (moon == 9) {
                    eaBleWeatherItem.setE_moon(EABleWeather.Moon.waning_crescent_moon);
                }
                int ray = (int) wMap.get("eRays");
                if (ray == 0) {
                    eaBleWeatherItem.setE_rays(EABleWeather.RaysLevel.weak);
                } else if (ray == 1) {
                    eaBleWeatherItem.setE_rays(EABleWeather.RaysLevel.medium);
                } else if (ray == 2) {
                    eaBleWeatherItem.setE_rays(EABleWeather.RaysLevel.strong);
                } else if (ray == 3) {
                    eaBleWeatherItem.setE_rays(EABleWeather.RaysLevel.very_strong);
                } else if (ray == 4) {
                    eaBleWeatherItem.setE_rays(EABleWeather.RaysLevel.super_strong);
                }
                int air = (int) wMap.get("eAir");
                if (air == 0) {
                    eaBleWeatherItem.setE_air(EABleWeather.AirQuality.excellent);
                } else if (air == 1) {
                    eaBleWeatherItem.setE_air(EABleWeather.AirQuality.good);
                } else {
                    eaBleWeatherItem.setE_air(EABleWeather.AirQuality.bad);
                }
                itemList.add(eaBleWeatherItem);
            }
        }
        return eaBleWeather;
    }

    public EABleAncsSw string2Ancs(String jsonString) {
        Map<String, Object> map = JSONObject.parseObject(jsonString, Map.class);
        EABleAncsSw eaBleAncsSw = new EABleAncsSw();

        Map<String, Object> sIncomingcall = (Map<String, Object>) map.get("sIncomingcall");
        Map<String, Object> sMissedcall = (Map<String, Object>) map.get("sMissedcall");
        Map<String, Object> sSms = (Map<String, Object>) map.get("sSms");
        Map<String, Object> sSocial = (Map<String, Object>) map.get("sSocial");
        Map<String, Object> sEmail = (Map<String, Object>) map.get("sEmail");
        Map<String, Object> sSchedule = (Map<String, Object>) map.get("sSchedule");

        eaBleAncsSw.setS_incomingcall(getAncsSwItem(sIncomingcall));
        eaBleAncsSw.setS_missedcall(getAncsSwItem(sMissedcall));
        eaBleAncsSw.setS_sms(getAncsSwItem(sSms));
        eaBleAncsSw.setS_social(getAncsSwItem(sSocial));
        eaBleAncsSw.setS_email(getAncsSwItem(sEmail));
        eaBleAncsSw.setS_schedule(getAncsSwItem(sSchedule));
        return eaBleAncsSw;
    }

    public EABleReminder string2Reminder(String jsonString) {
        Map<String, Object> map = JSONObject.parseObject(jsonString, Map.class);
        EABleReminder eaBleReminder = new EABleReminder();
        eaBleReminder.setId((Integer) map.get("id_p"));
        int eOps = (int) map.get("eOps");
        if (eOps == 0) {
            eaBleReminder.setE_ops(EABleReminder.ReminderOps.add);
        } else if (eOps == 1) {
            eaBleReminder.setE_ops(EABleReminder.ReminderOps.edit);
        } else if (eOps == 2) {
            eaBleReminder.setE_ops(EABleReminder.ReminderOps.del);
        } else if (eOps == 3) {
            eaBleReminder.setE_ops(EABleReminder.ReminderOps.del_remind);
        } else if (eOps == 4) {
            eaBleReminder.setE_ops(EABleReminder.ReminderOps.del_alarm);
        } else if (eOps == 5) {
            eaBleReminder.setE_ops(EABleReminder.ReminderOps.del_remind_alarm);
        } else {
            eaBleReminder.setE_ops(EABleReminder.ReminderOps.unknown);
        }
        List<JSONObject> wArray = (List<JSONObject>) map.get("sIndexArray");
        if (wArray != null && !wArray.isEmpty()) {
            List<EABleReminder.EABleReminderItem> itemList = new ArrayList<>();
            eaBleReminder.setS_index(itemList);
            for (int i = 0; i < wArray.size(); i++) {
                JSONObject wMap = wArray.get(i);
                EABleReminder.EABleReminderItem reminderItem = new EABleReminder.EABleReminderItem();
                reminderItem.setContent((String) wMap.get("content"));
                reminderItem.setDay((Integer) wMap.get("day"));
                reminderItem.setHour((Integer) wMap.get("hour"));
                reminderItem.setId((Integer) wMap.get("id_p"));
                reminderItem.setMinute((Integer) wMap.get("minute"));
                reminderItem.setMonth((Integer) wMap.get("month"));
                reminderItem.setSec_sw((Integer) wMap.get("secSw"));
                reminderItem.setSleep_duration((Integer) wMap.get("sleepDuration"));
                reminderItem.setSw((Integer) wMap.get("sw"));
                reminderItem.setWeek_cycle_bit((Integer) wMap.get("weekCycleBit"));
                reminderItem.setYear((Integer) wMap.get("year"));
                int remindActionType = (int) wMap.get("remindActionType");
                if (remindActionType == 0) {
                    reminderItem.setE_action(CommonAction.no_action);
                } else if (remindActionType == 1) {
                    reminderItem.setE_action(CommonAction.one_long_vibration);
                } else if (remindActionType == 2) {
                    reminderItem.setE_action(CommonAction.one_short_vibration);
                } else if (remindActionType == 3) {
                    reminderItem.setE_action(CommonAction.two_long_vibration);
                } else if (remindActionType == 4) {
                    reminderItem.setE_action(CommonAction.two_short_vibration);
                } else if (remindActionType == 5) {
                    reminderItem.setE_action(CommonAction.long_vibration);
                } else if (remindActionType == 6) {
                    reminderItem.setE_action(CommonAction.long_short_vibration);
                } else if (remindActionType == 7) {
                    reminderItem.setE_action(CommonAction.one_ring);
                } else if (remindActionType == 8) {
                    reminderItem.setE_action(CommonAction.two_ring);
                } else if (remindActionType == 9) {
                    reminderItem.setE_action(CommonAction.ring);
                } else if (remindActionType == 10) {
                    reminderItem.setE_action(CommonAction.one_vibration_ring);
                } else if (remindActionType == 11) {
                    reminderItem.setE_action(CommonAction.vibration_ring);
                }
                int reminderEventType = (int) wMap.get("reminderEventType");
                if (reminderEventType == 0) {
                    reminderItem.setE_type(EABleReminder.ReminderType.alarm);
                } else if (reminderEventType == 1) {
                    reminderItem.setE_type(EABleReminder.ReminderType.sleep);
                } else if (reminderEventType == 2) {
                    reminderItem.setE_type(EABleReminder.ReminderType.sport);
                } else if (reminderEventType == 3) {
                    reminderItem.setE_type(EABleReminder.ReminderType.drink);
                } else if (reminderEventType == 4) {
                    reminderItem.setE_type(EABleReminder.ReminderType.medicine);
                } else if (reminderEventType == 5) {
                    reminderItem.setE_type(EABleReminder.ReminderType.meeting);
                } else if (reminderEventType == 6) {
                    reminderItem.setE_type(EABleReminder.ReminderType.user);
                }
                itemList.add(reminderItem);
            }
        }
        return eaBleReminder;
    }

    public EABleHr string2HeartAlert(String jsonString) {
        Map<String, Integer> map = JSONObject.parseObject(jsonString, Map.class);
        EABleHr eaBleHr = new EABleHr();
        eaBleHr.setSw(map.get("sw"));
        eaBleHr.setMax_hr(map.get("maxHr"));
        eaBleHr.setMin_hr(map.get("minHr"));
        return eaBleHr;
    }

    public EABleHabit string2Habit(String jsonString) {
        Map<String, Object> map = JSONObject.parseObject(jsonString, Map.class);
        EABleHabit eaBleHabit = new EABleHabit();
        eaBleHabit.setId((Integer) map.get("id_p"));

        int eOps = (int) map.get("eOps");
        if (eOps == 0) {
            eaBleHabit.setE_ops(EABleHabit.HabitualOperation.add);
        } else if (eOps == 1) {
            eaBleHabit.setE_ops(EABleHabit.HabitualOperation.edit);
        } else if (eOps == 2) {
            eaBleHabit.setE_ops(EABleHabit.HabitualOperation.del);
        } else {
            eaBleHabit.setE_ops(EABleHabit.HabitualOperation.del_all);
        }
        List<JSONObject> wArray = (List<JSONObject>) map.get("sIndexArray");
        if (wArray != null && !wArray.isEmpty()) {
            List<EABleHabit.HabitItem> itemList = new ArrayList<>();
            eaBleHabit.setItemList(itemList);
            for (int i = 0; i < wArray.size(); i++) {
                JSONObject wMap = wArray.get(i);
                EABleHabit.HabitItem habitItem = new EABleHabit.HabitItem();
                habitItem.setContent((String) wMap.get("content"));
                habitItem.setBegin_hour((Integer) wMap.get("beginHour"));
                habitItem.setBegin_minute((Integer) wMap.get("beginMinute"));
                habitItem.setEnd_hour((Integer) wMap.get("endHour"));
                habitItem.setEnd_minute((Integer) wMap.get("endMinute"));
                habitItem.setBlueColor((Integer) wMap.get("b"));
                habitItem.setGreenColor((Integer) wMap.get("g"));
                habitItem.setRedColor((Integer) wMap.get("r"));
                habitItem.setDuration((Integer) wMap.get("duration"));
                habitItem.setId((Integer) wMap.get("id_p"));
                int remindActionType = (int) wMap.get("eAction");
                if (remindActionType == 0) {
                    habitItem.setE_action(CommonAction.no_action);
                } else if (remindActionType == 1) {
                    habitItem.setE_action(CommonAction.one_long_vibration);
                } else if (remindActionType == 2) {
                    habitItem.setE_action(CommonAction.one_short_vibration);
                } else if (remindActionType == 3) {
                    habitItem.setE_action(CommonAction.two_long_vibration);
                } else if (remindActionType == 4) {
                    habitItem.setE_action(CommonAction.two_short_vibration);
                } else if (remindActionType == 5) {
                    habitItem.setE_action(CommonAction.long_vibration);
                } else if (remindActionType == 6) {
                    habitItem.setE_action(CommonAction.long_short_vibration);
                } else if (remindActionType == 7) {
                    habitItem.setE_action(CommonAction.one_ring);
                } else if (remindActionType == 8) {
                    habitItem.setE_action(CommonAction.two_ring);
                } else if (remindActionType == 9) {
                    habitItem.setE_action(CommonAction.ring);
                } else if (remindActionType == 10) {
                    habitItem.setE_action(CommonAction.one_vibration_ring);
                } else if (remindActionType == 11) {
                    habitItem.setE_action(CommonAction.vibration_ring);
                }
                int reminderEventType = (int) wMap.get("eIconId");
                if (reminderEventType == 0) {
                    habitItem.setE_icon_id(HabitIcon.study_01);
                } else if (reminderEventType == 1) {
                    habitItem.setE_icon_id(HabitIcon.sleep_02);
                } else if (reminderEventType == 2) {
                    habitItem.setE_icon_id(HabitIcon.study_03);
                } else if (reminderEventType == 3) {
                    habitItem.setE_icon_id(HabitIcon.chores_04);
                } else if (reminderEventType == 4) {
                    habitItem.setE_icon_id(HabitIcon.havefun_05);
                } else if (reminderEventType == 5) {
                    habitItem.setE_icon_id(HabitIcon.drink_06);
                } else if (reminderEventType == 6) {
                    habitItem.setE_icon_id(HabitIcon.sun_07);
                } else if (reminderEventType == 7) {
                    habitItem.setE_icon_id(HabitIcon.teeth_08);
                } else if (reminderEventType == 8) {
                    habitItem.setE_icon_id(HabitIcon.calendar_09);
                } else if (reminderEventType == 9) {
                    habitItem.setE_icon_id(HabitIcon.piano_10);
                } else if (reminderEventType == 10) {
                    habitItem.setE_icon_id(HabitIcon.fruit_11);
                } else if (reminderEventType == 11) {
                    habitItem.setE_icon_id(HabitIcon.medicine_12);
                } else if (reminderEventType == 12) {
                    habitItem.setE_icon_id(HabitIcon.draw_13);
                } else if (reminderEventType == 13) {
                    habitItem.setE_icon_id(HabitIcon.target_14);
                } else if (reminderEventType == 14) {
                    habitItem.setE_icon_id(HabitIcon.dog_15);
                } else if (reminderEventType == 15) {
                    habitItem.setE_icon_id(HabitIcon.exercise_16);
                } else if (reminderEventType == 16) {
                    habitItem.setE_icon_id(HabitIcon.bed_17);
                } else {
                    habitItem.setE_icon_id(HabitIcon.tidyup_18);
                }
                int flag = (int) wMap.get("eFlag");
                if (flag == 0) {
                    habitItem.setHabitState(HabitState.initial);
                } else if (flag == 1) {
                    habitItem.setHabitState(HabitState.in_progress);
                } else if (flag == 2) {
                    habitItem.setHabitState(HabitState.completed);
                } else {
                    habitItem.setHabitState(HabitState.cancel);
                }
                itemList.add(habitItem);
            }
        }
        return eaBleHabit;
    }

    public EABleGesturesBrightScreen string2Gesture(String jsonString) {
        Map<String, Integer> map = JSONObject.parseObject(jsonString, Map.class);
        EABleGesturesBrightScreen eaBleGesturesBrightScreen = new EABleGesturesBrightScreen();
        eaBleGesturesBrightScreen.setBegin_hour(map.get("beginHour"));
        eaBleGesturesBrightScreen.setBegin_minute(map.get("beginMinute"));
        eaBleGesturesBrightScreen.setEnd_hour(map.get("endHour"));
        eaBleGesturesBrightScreen.setEnd_minute(map.get("endMinute"));
        int bType = map.get("eBrightSrc");
        if (bType == 0) {
            eaBleGesturesBrightScreen.setBrightScreenSwitch(EABleGesturesBrightScreen.BrightScreenSwitch.close);
        } else if (bType == 1) {
            eaBleGesturesBrightScreen.setBrightScreenSwitch(EABleGesturesBrightScreen.BrightScreenSwitch.all_day);
        } else {
            eaBleGesturesBrightScreen.setBrightScreenSwitch(EABleGesturesBrightScreen.BrightScreenSwitch.select_time);
        }
        return eaBleGesturesBrightScreen;
    }

    public EABleMenuPage string2Page(String jsonString) {
        Map<String, Object> map = JSONObject.parseObject(jsonString, Map.class);
        List<JSONObject> wArray = (List<JSONObject>) map.get("sPageArray");
        EABleMenuPage homePage = new EABleMenuPage();
        List<EABleMenuPage.MenuType> typeList = new ArrayList<>();
        if (wArray != null && !wArray.isEmpty()) {
            for (int i = 0; i < wArray.size(); i++) {
                JSONObject wMap = wArray.get(i);
                int menu = wMap.getInteger("eType");
                if (menu == 0) {
                    typeList.add(EABleMenuPage.MenuType.page_null);
                } else if (menu == 1) {
                    typeList.add(EABleMenuPage.MenuType.page_heart_rate);
                } else if (menu == 2) {
                    typeList.add(EABleMenuPage.MenuType.page_pressure);
                } else if (menu == 3) {
                    typeList.add(EABleMenuPage.MenuType.page_weather);
                } else if (menu == 4) {
                    typeList.add(EABleMenuPage.MenuType.page_music);
                } else if (menu == 5) {
                    typeList.add(EABleMenuPage.MenuType.page_breath);
                } else if (menu == 6) {
                    typeList.add(EABleMenuPage.MenuType.page_sleep);
                } else if (menu == 7) {
                    typeList.add(EABleMenuPage.MenuType.page_menstrual_cycle);
                } else if (menu == 8) {
                    typeList.add(EABleMenuPage.MenuType.page_camera);
                } else if (menu == 9) {
                    typeList.add(EABleMenuPage.MenuType.page_workout);
                }
            }
        }


        if (typeList.isEmpty()) {
            typeList.add(EABleMenuPage.MenuType.page_null);
        }
        homePage.setTypeList(typeList);
        return homePage;
    }

    public EABlePhysiologyData string2Physiology(String jsonString) {
        Map<String, Object> map = JSONObject.parseObject(jsonString, Map.class);
//                Period period = new Period();
//                period.startDate = (String) map.get("startDate");
//                period.cycleDay = (int) map.get("cycleDay");
//                period.keepDay = (int) map.get("keepDay");
//                EABlePeriod eaBlePeriod = period.getPeriodDate();

        long startTime = 0;
        String startDate = (String) map.get("startDate");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            startTime = simpleDateFormat.parse(startDate).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }


        EABlePhysiologyData eaBlePhysiologyData = new EABlePhysiologyData();
        eaBlePhysiologyData.setStartTime(startTime);
        eaBlePhysiologyData.setCycleTime((int) map.get("cycleDay"));
        eaBlePhysiologyData.setKeepTime((int) map.get("keepDay"));
        return eaBlePhysiologyData;
    }

    public EABleMusicRespond string2Music(String jsonString) {
        Map<String, Object> map = JSONObject.parseObject(jsonString, Map.class);
        EABleMusicRespond eaBleMusicRespond = new EABleMusicRespond();
        eaBleMusicRespond.setArtist((String) map.get("artist"));
        eaBleMusicRespond.setContent((String) map.get("content"));
        eaBleMusicRespond.setDuration((Integer) map.get("duration"));
        eaBleMusicRespond.setElapsedtime((Integer) map.get("elapsedtime"));
        eaBleMusicRespond.setVolume((Integer) map.get("volume"));
        int state = (int) map.get("playState");
        if (state == 0) {
            eaBleMusicRespond.setE_status(EABleMusicRespond.MusicStatus.not_play);
        } else if (state == 1) {
            eaBleMusicRespond.setE_status(EABleMusicRespond.MusicStatus.playing);
        } else {
            eaBleMusicRespond.setE_status(EABleMusicRespond.MusicStatus.stop_play);
        }
        return eaBleMusicRespond;
    }

    public EABleWatchFace string2Face(String jsonString) {
        Map<String, Integer> map = JSONObject.parseObject(jsonString, Map.class);
        int builtInId = (int) map.get("id_p");
        EABleWatchFace eaBleWatchFace = new EABleWatchFace();
        eaBleWatchFace.setId(builtInId);
        return eaBleWatchFace;
    }

    public EABleInfoPush string2PushSwitch(String jsonString) {
        final ShowAppMessage showAppMessage = JSONObject.parseObject(jsonString, ShowAppMessage.class);
        EABleInfoPush eaBleInfoPush = new EABleInfoPush();
        eaBleInfoPush.setS_app_sw(showAppMessage.getSwitchList());
        return eaBleInfoPush;
    }

    public EABleSocialContact string2Message(String jsonString) {
        Map<String, Object> map = JSONObject.parseObject(jsonString, Map.class);
        EABleSocialContact eaBleSocialContact = new EABleSocialContact();
        eaBleSocialContact.setTitle((String) map.get("title"));
        eaBleSocialContact.setContent((String) map.get("content"));
        eaBleSocialContact.setDate((String) map.get("date"));
        eaBleSocialContact.setId((Integer) map.get("messageId"));
        int actionType = (int) map.get("messageActionType");
        int messageType = (int) map.get("messageType");
        eaBleSocialContact.setE_ops(getPushInfoAction(actionType));
        eaBleSocialContact.seteType(getPushInfoType(messageType));
        return eaBleSocialContact;
    }

    public EABleMonitorReminder string2MonitorReminder(String jsonString) {
        Map<String, Integer> map = JSONObject.parseObject(jsonString, Map.class);

        EABleMonitorReminder monitorReminder = new EABleMonitorReminder();
        int eReminderType = (int) map.get("eReminderType");
        ;
        switch (eReminderType) {
            case 0:
                monitorReminder.setEaBleMonitorType(EABleMonitorReminder.EABleMonitorType.drink);
                break;
            case 1:
                monitorReminder.setEaBleMonitorType(EABleMonitorReminder.EABleMonitorType.washHands);
                break;
            case 2:
                monitorReminder.setEaBleMonitorType(EABleMonitorReminder.EABleMonitorType.takeMedicine);
                break;
            default:
                break;
        }

        monitorReminder.setReminderSwitch((int) map.get("sw"));
        monitorReminder.setBegin_hour((int) map.get("beginHour"));
        monitorReminder.setBegin_minute((int) map.get("beginMinute"));
        monitorReminder.setEnd_hour((int) map.get("endHour"));
        monitorReminder.setEnd_minute((int) map.get("endMinute"));
        monitorReminder.setWeek_cycle_bit((int) map.get("weekCycleBit"));
        monitorReminder.setInterval((int) map.get("interval"));
        monitorReminder.setCup((int) map.get("cup"));
        monitorReminder.setStep_threshold((int) map.get("stepThreshold"));
        return monitorReminder;
    }

    public List<EABleContact> string2Contacts(String jsonString) {
        List<EABleContact> contacts = new ArrayList<>();
        if (!TextUtils.isEmpty(jsonString)) {
            Map<String, Object> map = JSONObject.parseObject(jsonString, Map.class);
            if (map != null) {
                List<JSONObject> wArray = (List<JSONObject>) map.get("sIndexArray");
                if (wArray != null && !wArray.isEmpty()) {
                    for (int i = 0; i < wArray.size(); i++) {
                        JSONObject jsonObject = wArray.get(i);
                        if (jsonObject != null) {
                            EABleContact eaBleContact = new EABleContact();
                            eaBleContact.setContactName(jsonObject.getString("name"));
                            eaBleContact.setContactNum(jsonObject.getString("num"));
                            contacts.add(eaBleContact);
                        }
                    }
                }

            }
        }
        return contacts;

    }

    public EABleSleepBloodSwitch string2SleepSpo2(String jsonString) {
        Map<String, Integer> map = JSONObject.parseObject(jsonString, Map.class);
        EABleSleepBloodSwitch eaBleSleepBloodSwitch = new EABleSleepBloodSwitch();
        eaBleSleepBloodSwitch.setInterval(map.get("interval"));
        eaBleSleepBloodSwitch.setSw(map.get("sw"));
        return eaBleSleepBloodSwitch;
    }

    public EABleAutoStressMonitor string2Stress(String jsonString) {
        Map<String, Integer> map = JSONObject.parseObject(jsonString, Map.class);
        EABleAutoStressMonitor autoStressMonitor = new EABleAutoStressMonitor();
        autoStressMonitor.setIntervalTime(map.get("interval"));
        autoStressMonitor.setSw(map.get("sw"));
        return autoStressMonitor;
    }

    public VibrationIntensity string2Vibrate(String jsonString) {
        Map<String, Integer> map = JSONObject.parseObject(jsonString, Map.class);
        int type = map.get("eVibrateIntensity");
        if (type == 0) {
            return VibrationIntensity.light;
        } else if (type == 1) {
            return VibrationIntensity.medium;
        } else if (type == 2) {
            return VibrationIntensity.strong;
        } else {
            return VibrationIntensity.not_vibrate;
        }
    }

    public EABlePeriodReminder string2PeriodReminder(String jsonString) {
        Map<String, Object> map = JSONObject.parseObject(jsonString, Map.class);
        boolean pStart = (boolean) map.get("menstrualBeginSw");
        boolean pEnd = (boolean) map.get("menstrualEndSw");
        boolean eStart = (boolean) map.get("easyPregnancyBeginSw");
        boolean eEnd = (boolean) map.get("easyPregnancyEndSw");
        boolean oStart = (boolean) map.get("ovulationDaySw");
        EABlePeriodReminder eaBlePeriodReminder = new EABlePeriodReminder();
        eaBlePeriodReminder.setPeriodStart(pStart ? 1 : 0);
        eaBlePeriodReminder.setPeriodEnd(pEnd ? 1 : 0);
        eaBlePeriodReminder.setPregnancyStart(eStart ? 1 : 0);
        eaBlePeriodReminder.setPregnancyEnd(eEnd ? 1 : 0);
        eaBlePeriodReminder.setOvulation_day_sw(oStart ? 1 : 0);
        eaBlePeriodReminder.setReminderDay((Integer) map.get("menstrualReminderDaysBefore"));
        eaBlePeriodReminder.setReminderHour((Integer) map.get("menstrualReminderHours"));
        eaBlePeriodReminder.setReminderMinute((Integer) map.get("menstrualReminderMinutes"));
        return eaBlePeriodReminder;
    }


    public EABleBindInfo string2BindInfo(String jsonString) {
        Map<String, Object> param = JSONObject.parseObject(jsonString, Map.class);
        int ops = (int) param.get("ops");
        String user_id = (String) param.get("user_id");
        int mod = (int) param.get("bindMod");
        EABleBindInfo eaBleBindInfo = new EABleBindInfo();
        eaBleBindInfo.setUser_id(user_id);
        LogUtils.i(TAG, "daily data sync time:" + mod);
        eaBleBindInfo.setE_ops(ops == 1 ? EABleBindInfo.BindingOps.end : EABleBindInfo.BindingOps.normal_begin);
        eaBleBindInfo.setBind_mod(mod);
        return eaBleBindInfo;
    }

    private EABleSocialContact.SocialContactOps getPushInfoAction(int action) {
        if (action == 0) {
            return EABleSocialContact.SocialContactOps.add;
        } else if (action == 1) {
            return EABleSocialContact.SocialContactOps.edit;
        } else if (action == 2) {
            return EABleSocialContact.SocialContactOps.del;
        } else if (action == 3) {
            return EABleSocialContact.SocialContactOps.del_type;
        } else {
            return EABleSocialContact.SocialContactOps.del_all;
        }
    }

    private EABleSocialContact.SocialContactType getPushInfoType(int type) {
        if (type == 0) {
            return EABleSocialContact.SocialContactType.incomingcall;
        } else if (type == 1) {
            return EABleSocialContact.SocialContactType.missedcall;
        } else if (type == 2) {
            return EABleSocialContact.SocialContactType.social;
        } else if (type == 3) {
            return EABleSocialContact.SocialContactType.schedule;
        } else if (type == 4) {
            return EABleSocialContact.SocialContactType.email;
        } else if (type == 5) {
            return EABleSocialContact.SocialContactType.sms;
        } else if (type == 6) {
            return EABleSocialContact.SocialContactType.unknow;
        } else if (type == 7) {
            return EABleSocialContact.SocialContactType.wechat;
        } else if (type == 8) {
            return EABleSocialContact.SocialContactType.qq;
        } else if (type == 9) {
            return EABleSocialContact.SocialContactType.facebook;
        } else if (type == 10) {
            return EABleSocialContact.SocialContactType.twitter;
        } else if (type == 11) {
            return EABleSocialContact.SocialContactType.messenger;
        } else if (type == 12) {
            return EABleSocialContact.SocialContactType.hangouts;
        } else if (type == 13) {
            return EABleSocialContact.SocialContactType.gmail;
        } else if (type == 14) {
            return EABleSocialContact.SocialContactType.viber;
        } else if (type == 15) {
            return EABleSocialContact.SocialContactType.snapchat;
        } else if (type == 16) {
            return EABleSocialContact.SocialContactType.whatsApp;
        } else if (type == 17) {
            return EABleSocialContact.SocialContactType.instagram;
        } else if (type == 18) {
            return EABleSocialContact.SocialContactType.linkedin;
        } else if (type == 19) {
            return EABleSocialContact.SocialContactType.line;
        } else if (type == 20) {
            return EABleSocialContact.SocialContactType.skype;
        } else if (type == 21) {
            return EABleSocialContact.SocialContactType.booking;
        } else if (type == 22) {
            return EABleSocialContact.SocialContactType.airbnb;
        } else if (type == 23) {
            return EABleSocialContact.SocialContactType.flipboard;
        } else if (type == 24) {
            return EABleSocialContact.SocialContactType.spotify;
        } else if (type == 25) {
            return EABleSocialContact.SocialContactType.pandora;
        } else if (type == 26) {
            return EABleSocialContact.SocialContactType.telegram;
        } else if (type == 27) {
            return EABleSocialContact.SocialContactType.dropbox;
        } else if (type == 28) {
            return EABleSocialContact.SocialContactType.waze;
        } else if (type == 29) {
            return EABleSocialContact.SocialContactType.lift;
        } else if (type == 30) {
            return EABleSocialContact.SocialContactType.slack;
        } else if (type == 31) {
            return EABleSocialContact.SocialContactType.shazam;
        } else if (type == 32) {
            return EABleSocialContact.SocialContactType.deliveroo;
        } else if (type == 33) {
            return EABleSocialContact.SocialContactType.kakaotalk;
        } else if (type == 34) {
            return EABleSocialContact.SocialContactType.pinterest;
        } else if (type == 35) {
            return EABleSocialContact.SocialContactType.tumblr;
        } else if (type == 36) {
            return EABleSocialContact.SocialContactType.vk;
        } else if (type == 37) {
            return EABleSocialContact.SocialContactType.youtube;
        } else if (type == 38) {
            return EABleSocialContact.SocialContactType.outlook;
        } else if (type == 39) {
            return EABleSocialContact.SocialContactType.amazon;
        } else if (type == 40) {
            return EABleSocialContact.SocialContactType.discord;
        } else if (type == 41) {
            return EABleSocialContact.SocialContactType.github;
        } else if (type == 42) {
            return EABleSocialContact.SocialContactType.google_maps;
        } else if (type == 43) {
            return EABleSocialContact.SocialContactType.news_break;
        } else if (type == 44) {
            return EABleSocialContact.SocialContactType.reddit;
        } else if (type == 45) {
            return EABleSocialContact.SocialContactType.teams;
        } else if (type == 46) {
            return EABleSocialContact.SocialContactType.tiktok;
        } else if (type == 47) {
            return EABleSocialContact.SocialContactType.twitch;
        } else if (type == 48) {
            return EABleSocialContact.SocialContactType.uber_eats;
        }/**
         else if (type == 49) {
         return EABleSocialContact.SocialContactType.doordash;
         } else if (type == 50) {
         return EABleSocialContact.SocialContactType.grubhub;
         } else if (type == 51) {
         return EABleSocialContact.SocialContactType.instacart;
         } else if (type == 52) {
         return EABleSocialContact.SocialContactType.postmates;
         } else if (type == 53) {
         return EABleSocialContact.SocialContactType.zoom;
         } else if (type == 54) {
         return EABleSocialContact.SocialContactType.uber;
         } else if (type == 55) {
         return EABleSocialContact.SocialContactType.apple_email;
         } else if (type == 56) {
         return EABleSocialContact.SocialContactType.ding_talk;
         } else if (type == 57) {
         return EABleSocialContact.SocialContactType.alipay;
         } else if (type == 58) {
         return EABleSocialContact.SocialContactType.true_caller;
         } else if (type == 59) {
         return EABleSocialContact.SocialContactType.hotstar;
         } else if (type == 60) {
         return EABleSocialContact.SocialContactType.phone_pe;
         } else if (type == 61) {
         return EABleSocialContact.SocialContactType.zomato;
         } else if (type == 62) {
         return EABleSocialContact.SocialContactType.dailyhunt;
         } else if (type == 63) {
         return EABleSocialContact.SocialContactType.inshorts;
         } else if (type == 64) {
         return EABleSocialContact.SocialContactType.jio_tv;
         } else if (type == 65) {
         return EABleSocialContact.SocialContactType.yahoo;
         } else if (type == 66) {
         return EABleSocialContact.SocialContactType.paytm;
         } else if (type == 67) {
         return EABleSocialContact.SocialContactType.swiggy;
         } else if (type == 68) {
         return EABleSocialContact.SocialContactType.calendar;
         } else if (type == 69) {
         return EABleSocialContact.SocialContactType.wynk_music;
         } else if (type == 70) {
         return EABleSocialContact.SocialContactType.gaana;
         } else if (type == 71) {
         return EABleSocialContact.SocialContactType.flipkart;
         } else if (type == 72) {
         return EABleSocialContact.SocialContactType.netflix;
         } else if (type == 73) {
         return EABleSocialContact.SocialContactType.amazon_prime;
         } else if (type == 74) {
         return EABleSocialContact.SocialContactType.google_pay;
         } else if (type == 75) {
         return EABleSocialContact.SocialContactType.ola;
         } else if (type == 76) {
         return EABleSocialContact.SocialContactType.zalo;
         } else if (type == 77) {
         return EABleSocialContact.SocialContactType.book_my_show;
         } else if (type == 78) {
         return EABleSocialContact.SocialContactType.make_my_trip;
         } else if (type == 79) {
         return EABleSocialContact.SocialContactType.fastrack_reflex_word;
         } else if (type == 80) {
         return EABleSocialContact.SocialContactType.yt_music;
         } else if (type == 81) {
         return EABleSocialContact.SocialContactType.dunzo;
         } else if (type == 82) {
         return EABleSocialContact.SocialContactType.google_drive;
         } else if (type == 83) {
         return EABleSocialContact.SocialContactType.titan_smart_word;
         }*/
        else {
            return EABleSocialContact.SocialContactType.unknow;
        }
    }


    private EABleAncsSw.EABleAncsSwItem getAncsSwItem(Map<String, Object> map) {
        EABleAncsSw.EABleAncsSwItem ancsSwItem = new EABleAncsSw.EABleAncsSwItem();
        ancsSwItem.setSw((int) map.get("sw"));
        ancsSwItem.setE_action(getCommonAction((int) map.get("remindActionType")));
        return ancsSwItem;
    }

    private CommonAction getCommonAction(int remindActionType) {

        if (remindActionType == 0) {
            return CommonAction.no_action;
        } else if (remindActionType == 1) {
            return CommonAction.one_long_vibration;
        } else if (remindActionType == 2) {
            return CommonAction.one_short_vibration;
        } else if (remindActionType == 3) {
            return CommonAction.two_long_vibration;
        } else if (remindActionType == 4) {
            return CommonAction.two_short_vibration;
        } else if (remindActionType == 5) {
            return CommonAction.long_vibration;
        } else if (remindActionType == 6) {
            return CommonAction.long_short_vibration;
        } else if (remindActionType == 7) {
            return CommonAction.one_ring;
        } else if (remindActionType == 8) {
            return CommonAction.two_ring;
        } else if (remindActionType == 9) {
            return CommonAction.ring;
        } else if (remindActionType == 10) {
            return CommonAction.one_vibration_ring;
        } else if (remindActionType == 11) {
            return CommonAction.vibration_ring;
        }
        return CommonAction.no_action;
    }
}
