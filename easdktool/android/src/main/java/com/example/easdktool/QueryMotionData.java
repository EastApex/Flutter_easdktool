package com.example.easdktool;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.alibaba.fastjson.JSONObject;
import com.apex.ax_bluetooth.utils.LogUtils;
import com.example.easdktool.db.BloodData;
import com.example.easdktool.db.DailyData;
import com.example.easdktool.db.DataManager;
import com.example.easdktool.db.GpsData;
import com.example.easdktool.db.HabitData;
import com.example.easdktool.db.HeartData;
import com.example.easdktool.db.MotionHeart;
import com.example.easdktool.db.MultiData;
import com.example.easdktool.db.RestingHeartData;
import com.example.easdktool.db.SleepData;
import com.example.easdktool.db.SleepScore;
import com.example.easdktool.db.StepFreqData;
import com.example.easdktool.db.StepPaceData;
import com.example.easdktool.db.StressData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.flutter.plugin.common.MethodChannel;

public class QueryMotionData {
    private MethodChannel channel;
    final String TAG = this.getClass().getSimpleName();
    /* 大数据步数 */
    final int kEADataInfoTypeStepData = 3001;
    /* 大数据睡眠 */
    final int kEADataInfoTypeSleepData = 3002;
    /* 大数据心率  */
    final int kEADataInfoTypeHeartRateData = 3003;
    /* 大数据GPS */
    final int kEADataInfoTypeGPSData = 3004;
    /* 大数据多运动 */
    final int kEADataInfoTypeSportsData = 3005;
    /* 大数据血氧 */
    final int kEADataInfoTypeBloodOxygenData = 3006;
    /* 大数据压力 */
    final int kEADataInfoTypeStressData = 3007;
    /* 大数据步频 */
    final int kEADataInfoTypeStepFreqData = 3008;
    /* 大数据配速 */
    final int kEADataInfoTypeStepPaceData = 3009;
    /* 大数据静息心率 */
    final int kEADataInfoTypeRestingHeartRateData = 3010;
    /* 习惯数据 */
    final int EADataInfoTypeHabitTrackerData = 3011;
    final int kEADataInfoTypeSleepScoreData = 3012;
    final int kEADataInfoTypeMotionHeartData = 3013;
    final String kQueryBigWatchData = "QueryBigWatchData";
    int type;
    Handler mHandler;


    public QueryMotionData(MethodChannel channel, int type) {
        this.channel = channel;
        this.type = type;
        mHandler = new Handler(Looper.getMainLooper());
    }

    public void queryData() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (type == 0) {//表示日常数据
                    List<DailyData> dailyDataList = DataManager.getInstance().queryDailyData();
                    List<Map<String, Object>> dataList = new ArrayList<>();
                    if (dailyDataList != null && !dailyDataList.isEmpty()) {
                        for (int i = 0; i < dailyDataList.size(); i++) {
                            Map<String, Object> map = new HashMap<>();
                            map.put("time_stamp", dailyDataList.get(i).getTime_stamp());
                            map.put("steps", dailyDataList.get(i).getSteps());
                            map.put("calorie", dailyDataList.get(i).getCalorie());
                            map.put("distance", dailyDataList.get(i).getDistance());
                            map.put("duration", dailyDataList.get(i).getDuration());
                            map.put("average_heart_rate", dailyDataList.get(i).getAverage_heart_rate());
                            dataList.add(map);
                        }
                    }
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("value", dataList);
                    jsonObject.put("dataType", kEADataInfoTypeStepData);
                    sendBigWatchData(jsonObject);
                } else if (type == 1) {
                    List<SleepData> sleepDataList = DataManager.getInstance().querySleepData();
                    List<Map<String, Object>> dataList = new ArrayList<>();
                    if (sleepDataList != null && !sleepDataList.isEmpty()) {
                        for (int i = 0; i < sleepDataList.size(); i++) {
                            Map<String, Object> map = new HashMap<>();
                            map.put("time_stamp", sleepDataList.get(i).getTime_stamp());
                            map.put("e_sleep_node", sleepDataList.get(i).getSleepMode());
                            dataList.add(map);
                        }
                    }
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("value", dataList);
                    jsonObject.put("dataType", kEADataInfoTypeSleepData);
                    sendBigWatchData(jsonObject);
                } else if (type == 2) {
                    List<HeartData> heartDataList = DataManager.getInstance().queryHeartData();
                    List<Map<String, Object>> dataList = new ArrayList<>();
                    if (heartDataList != null && !heartDataList.isEmpty()) {
                        for (int i = 0; i < heartDataList.size(); i++) {
                            Map<String, Object> map = new HashMap<>();
                            map.put("time_stamp", heartDataList.get(i).getTime_stamp());
                            map.put("hr_value", heartDataList.get(i).getHr_value());
                            dataList.add(map);
                        }
                    }
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("value", dataList);
                    jsonObject.put("dataType", kEADataInfoTypeHeartRateData);
                    sendBigWatchData(jsonObject);
                } else if (type == 3) {
                    List<GpsData> gpsDataList = DataManager.getInstance().queryGpsData();
                    List<Map<String, Object>> dataList = new ArrayList<>();
                    if (gpsDataList != null && !gpsDataList.isEmpty()) {
                        for (int i = 0; i < gpsDataList.size(); i++) {
                            Map<String, Object> map = new HashMap<>();
                            map.put("time_stamp", gpsDataList.get(i).getTime_stamp());
                            map.put("latitude", gpsDataList.get(i).getLatitude());
                            map.put("longitude", gpsDataList.get(i).getLongitude());
                            dataList.add(map);
                        }
                    }
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("value", dataList);
                    jsonObject.put("dataType", kEADataInfoTypeGPSData);
                    sendBigWatchData(jsonObject);
                } else if (type == 4) {
                    List<MultiData> multiDataList = DataManager.getInstance().queryMultiData();
                    List<Map<String, Object>> dataList = new ArrayList<>();
                    if (multiDataList != null && !multiDataList.isEmpty()) {
                        for (int i = 0; i < multiDataList.size(); i++) {
                            Map<String, Object> map = new HashMap<>();
                            map.put("e_type", multiDataList.get(i).getMotionType());
                            map.put("begin_time_stamp", multiDataList.get(i).getBegin_time_stamp());
                            map.put("end_time_stamp", multiDataList.get(i).getEnd_time_stamp());
                            map.put("steps", multiDataList.get(i).getSteps());
                            map.put("calorie", multiDataList.get(i).getCalorie());
                            map.put("distance", multiDataList.get(i).getDistance());
                            map.put("duration", multiDataList.get(i).getDuration());
                            map.put("training_effect_normal", multiDataList.get(i).getTraining_effect_normal());
                            map.put("training_effect_warmUp", multiDataList.get(i).getTraining_effect_warmUp());
                            map.put("training_effect_fatconsumption", multiDataList.get(i).getTraining_effect_fatconsumption());
                            map.put("training_effect_aerobic", multiDataList.get(i).getTraining_effect_aerobic());
                            map.put("training_effect_anaerobic", multiDataList.get(i).getTraining_effect_anaerobic());
                            map.put("training_effect_limit", multiDataList.get(i).getTraining_effect_limit());
                            map.put("average_heart_rate", multiDataList.get(i).getAverage_heart_rate());
                            map.put("average_temperature", multiDataList.get(i).getAverage_temperature());
                            map.put("average_speed", multiDataList.get(i).getAverage_speed());
                            map.put("average_pace", multiDataList.get(i).getAverage_pace());
                            map.put("average_step_freq", multiDataList.get(i).getAverage_step_freq());
                            map.put("average_stride", multiDataList.get(i).getAverage_stride());
                            map.put("average_altitude", multiDataList.get(i).getAverage_altitude());
                            map.put("average_heart_rate_max", multiDataList.get(i).getAverage_heart_rate_max());
                            map.put("average_heart_rate_min", multiDataList.get(i).getAverage_heart_rate_min());
                            dataList.add(map);
                        }
                    }
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("value", dataList);
                    jsonObject.put("dataType", kEADataInfoTypeSportsData);
                    sendBigWatchData(jsonObject);
                } else if (type == 5) {
                    List<BloodData> bloodDataList = DataManager.getInstance().queryBloodData();
                    List<Map<String, Object>> dataList = new ArrayList<>();
                    if (bloodDataList != null && !bloodDataList.isEmpty()) {
                        for (int i = 0; i < bloodDataList.size(); i++) {
                            Map<String, Object> map = new HashMap<>();
                            map.put("time_stamp", bloodDataList.get(i).getTime_stamp());
                            map.put("blood_oxygen_value", bloodDataList.get(i).getBlood_oxygen_value());
                            dataList.add(map);
                        }
                    }
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("value", dataList);
                    jsonObject.put("dataType", kEADataInfoTypeBloodOxygenData);
                    sendBigWatchData(jsonObject);
                } else if (type == 6) {
                    List<StressData> stressDataList = DataManager.getInstance().queryStressData();
                    List<Map<String, Object>> dataList = new ArrayList<>();
                    if (stressDataList != null && !stressDataList.isEmpty()) {
                        for (int i = 0; i < stressDataList.size(); i++) {
                            Map<String, Object> map = new HashMap<>();
                            map.put("time_stamp", stressDataList.get(i).getTime_stamp());
                            map.put("stess_value", stressDataList.get(i).getStess_value());
                            map.put("e_type", stressDataList.get(i).getPressureLevel());
                            dataList.add(map);
                        }
                    }
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("value", dataList);
                    jsonObject.put("dataType", kEADataInfoTypeStressData);
                    sendBigWatchData(jsonObject);
                } else if (type == 7) {
                    List<StepFreqData> freqDataList = DataManager.getInstance().queryStepFreqData();
                    List<Map<String, Object>> dataList = new ArrayList<>();
                    if (freqDataList != null && !freqDataList.isEmpty()) {
                        for (int i = 0; i < freqDataList.size(); i++) {
                            Map<String, Object> map = new HashMap<>();
                            map.put("time_stamp", freqDataList.get(i).getTime_stamp());
                            map.put("step_freq_value", freqDataList.get(i).getStep_freq_value());
                            dataList.add(map);
                        }
                    }
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("value", dataList);
                    jsonObject.put("dataType", kEADataInfoTypeStepFreqData);
                    sendBigWatchData(jsonObject);
                } else if (type == 8) {
                    List<StepPaceData> paceDataList = DataManager.getInstance().queryStepPaceData();
                    List<Map<String, Object>> dataList = new ArrayList<>();
                    if (paceDataList != null && !paceDataList.isEmpty()) {
                        for (int i = 0; i < paceDataList.size(); i++) {
                            Map<String, Object> map = new HashMap<>();
                            map.put("time_stamp", paceDataList.get(i).getTime_stamp());
                            map.put("step_pace_value", paceDataList.get(i).getStep_pace_value());
                            dataList.add(map);
                        }
                    }
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("value", dataList);
                    jsonObject.put("dataType", kEADataInfoTypeStepPaceData);
                    sendBigWatchData(jsonObject);
                } else if (type == 9) {
                    List<RestingHeartData> restingHeartDataList = DataManager.getInstance().queryRestingHeartData();
                    List<Map<String, Object>> dataList = new ArrayList<>();
                    if (restingHeartDataList != null && !restingHeartDataList.isEmpty()) {
                        for (int i = 0; i < restingHeartDataList.size(); i++) {
                            Map<String, Object> map = new HashMap<>();
                            map.put("time_stamp", restingHeartDataList.get(i).getTime_stamp());
                            map.put("hr_value", restingHeartDataList.get(i).getHr_value());
                            dataList.add(map);
                        }
                    }
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("value", dataList);
                    jsonObject.put("dataType", kEADataInfoTypeRestingHeartRateData);
                    sendBigWatchData(jsonObject);
                } else if (type == 10) {
                    List<HabitData> habitDataList = DataManager.getInstance().queryHabitData();
                    List<Map<String, Object>> dataList = new ArrayList<>();
                    if (habitDataList != null && !habitDataList.isEmpty()) {
                        for (int i = 0; i < habitDataList.size(); i++) {
                            Map<String, Object> map = new HashMap<>();
                            map.put("time_stamp", habitDataList.get(i).getTime_stamp());
                            map.put("begin_hour", habitDataList.get(i).getBegin_hour());
                            map.put("begin_minute", habitDataList.get(i).getBegin_minute());
                            map.put("end_hour", habitDataList.get(i).getEnd_hour());
                            map.put("end_minute", habitDataList.get(i).getEnd_minute());
                            map.put("redColor", habitDataList.get(i).getRedColor());
                            map.put("greenColor", habitDataList.get(i).getGreenColor());
                            map.put("blueColor", habitDataList.get(i).getBlueColor());
                            map.put("habitState", habitDataList.get(i).getHabitState());
                            map.put("habitIcon", habitDataList.get(i).getHabitIcon());
                            if (!TextUtils.isEmpty(habitDataList.get(i).getMContent())) {
                                map.put("content", habitDataList.get(i).getMContent());
                            }
                            dataList.add(map);
                        }
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("value", dataList);
                        jsonObject.put("dataType", EADataInfoTypeHabitTrackerData);
                        sendBigWatchData(jsonObject);
                    }
                }else if (type==11){
                    List<SleepScore> sleepScoreList = DataManager.getInstance().querySleepScoreData();
                    List<Map<String, Object>> dataList = new ArrayList<>();
                    if (sleepScoreList != null && !sleepScoreList.isEmpty()) {
                        for (int i = 0; i < sleepScoreList.size(); i++) {
                            Map<String, Object> map = new HashMap<>();
                            map.put("startTime", sleepScoreList.get(i).getStartTime());
                            map.put("endTime", sleepScoreList.get(i).getEndTime());
                            map.put("score", sleepScoreList.get(i).getSleep_score());
                            dataList.add(map);
                        }
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("value", dataList);
                        jsonObject.put("dataType", kEADataInfoTypeSleepScoreData);
                        sendBigWatchData(jsonObject);
                    }
                }else if (type==12){
                    List<MotionHeart> motionHeartList = DataManager.getInstance().queryMotionHeartData();
                    List<Map<String, Object>> dataList = new ArrayList<>();
                    if (motionHeartList != null && !motionHeartList.isEmpty()) {
                        for (int i = 0; i < motionHeartList.size(); i++) {
                            Map<String, Object> map = new HashMap<>();
                            map.put("stampTime", motionHeartList.get(i).getStampTime());
                            map.put("motionHr", motionHeartList.get(i).getMotionHr());
                            dataList.add(map);
                        }
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("value", dataList);
                        jsonObject.put("dataType", kEADataInfoTypeMotionHeartData);
                        sendBigWatchData(jsonObject);
                    }
                }

            }
        });
    }

    private void sendBigWatchData(@NonNull JSONObject jsonObject) {
        if (channel != null) {
            LogUtils.i(TAG, "Deliver query big data to flutter");
            channel.invokeMethod(kQueryBigWatchData, jsonObject.toJSONString());
        }
        mHandler = null;
    }
}
