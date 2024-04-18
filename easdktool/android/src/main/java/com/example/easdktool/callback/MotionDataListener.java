package com.example.easdktool.callback;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;

import com.alibaba.fastjson.JSONObject;
import com.apex.bluetooth.callback.MotionDataReportCallback;
import com.apex.bluetooth.enumeration.CommonFlag;
import com.apex.bluetooth.model.EABleBloodOxygen;
import com.apex.bluetooth.model.EABleDailyData;
import com.apex.bluetooth.model.EABleGpsData;
import com.apex.bluetooth.model.EABleHabitRecord;
import com.apex.bluetooth.model.EABleHeartData;
import com.apex.bluetooth.model.EABleMultiData;
import com.apex.bluetooth.model.EABlePaceData;
import com.apex.bluetooth.model.EABlePressureData;
import com.apex.bluetooth.model.EABleRestingRateData;
import com.apex.bluetooth.model.EABleSleepData;
import com.apex.bluetooth.model.EABleStepFrequencyData;
import com.apex.bluetooth.utils.LogUtils;
import com.example.easdktool.db.BloodData;
import com.example.easdktool.db.DailyData;
import com.example.easdktool.db.DataManager;
import com.example.easdktool.db.GpsData;
import com.example.easdktool.db.HabitData;
import com.example.easdktool.db.HeartData;
import com.example.easdktool.db.MultiData;
import com.example.easdktool.db.RestingHeartData;
import com.example.easdktool.db.SleepData;
import com.example.easdktool.db.StepFreqData;
import com.example.easdktool.db.StepPaceData;
import com.example.easdktool.db.StressData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.flutter.plugin.common.MethodChannel;

public class MotionDataListener implements MotionDataReportCallback {
    private MethodChannel channel;
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
    final String kGetBigWatchData = "GetBigWatchData";
    final String TAG = this.getClass().getSimpleName();

    public MotionDataListener(MethodChannel channel) {
        this.channel = channel;
    }

    @Override
    public void dailyExerciseData(List<EABleDailyData> list, CommonFlag commonFlag) {
        LogUtils.i(TAG, "Prepare to pass daily data to Flutter");
        List<Map<String, Object>> dataList = new ArrayList<>();
        List<DailyData> dailyDataList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            DailyData dailyData = new DailyData();
            dailyData.setCalorie(list.get(i).getCalorie());
            dailyData.setDistance(list.get(i).getDistance());
            dailyData.setDuration(list.get(i).getDuration());
            dailyData.setSteps(list.get(i).getSteps());
            dailyData.setTime_stamp(list.get(i).getTime_stamp());
            dailyData.setAverage_heart_rate(list.get(i).getAverage_heart_rate());
            dailyDataList.add(dailyData);
            Map<String, Object> map = new HashMap<>();
            map.put("time_stamp", list.get(i).getTime_stamp());
            map.put("steps", list.get(i).getSteps());
            map.put("calorie", list.get(i).getCalorie());
            map.put("distance", list.get(i).getDistance());
            map.put("duration", list.get(i).getDuration());
            map.put("average_heart_rate", list.get(i).getAverage_heart_rate());
            dataList.add(map);
        }
        if (dailyDataList != null && !dailyDataList.isEmpty()) {
            DataManager.getInstance().insertBatchDailyData(dailyDataList);
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("value", dataList);
        jsonObject.put("dataType", kEADataInfoTypeStepData);
        sendBigWatchData(jsonObject);
    }

    @Override
    public void sleepData(List<EABleSleepData> list, CommonFlag commonFlag) {
        LogUtils.i(TAG, "Prepare to pass sleep data to flutter");
        List<Map<String, Object>> dataList = new ArrayList<>();
        List<SleepData> sleepDataList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            SleepData sleepData = new SleepData();
            sleepData.setSleepMode(list.get(i).getE_sleep_node().getValue());
            sleepData.setTime_stamp(list.get(i).getTime_stamp());
            sleepDataList.add(sleepData);
            Map<String, Object> map = new HashMap<>();
            map.put("time_stamp", list.get(i).getTime_stamp());
            map.put("e_sleep_node", list.get(i).getE_sleep_node().getValue());
            dataList.add(map);
        }
        if (sleepDataList != null && !sleepDataList.isEmpty()) {
            DataManager.getInstance().insertBatchSleepData(sleepDataList);
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("value", dataList);
        jsonObject.put("dataType", kEADataInfoTypeSleepData);
        sendBigWatchData(jsonObject);
    }

    @Override
    public void heartData(List<EABleHeartData> list, CommonFlag commonFlag) {
        LogUtils.i(TAG, "Prepare to pass heart rate data to flutter");
        List<Map<String, Object>> dataList = new ArrayList<>();
        List<HeartData> heartDataList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            HeartData heartData = new HeartData();
            heartData.setTime_stamp(list.get(i).getTime_stamp());
            heartData.setHr_value(list.get(i).getHr_value());
            heartDataList.add(heartData);
            Map<String, Object> map = new HashMap<>();
            map.put("time_stamp", list.get(i).getTime_stamp());
            map.put("hr_value", list.get(i).getHr_value());
            dataList.add(map);
        }
        if (heartDataList != null && !heartDataList.isEmpty()) {
            DataManager.getInstance().insertBatchHeartData(heartDataList);
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("value", dataList);
        jsonObject.put("dataType", kEADataInfoTypeHeartRateData);
        sendBigWatchData(jsonObject);
    }

    @Override
    public void gpsData(List<EABleGpsData> list, CommonFlag commonFlag) {
        LogUtils.i(TAG, "Prepare to pass GPS data to Flutter");
        List<Map<String, Object>> dataList = new ArrayList<>();
        List<GpsData> gpsDataList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            GpsData gpsData = new GpsData();
            gpsData.setTime_stamp(list.get(i).getTime_stamp());
            gpsData.setLatitude(list.get(i).getLatitude());
            gpsData.setLongitude(list.get(i).getLongitude());
            gpsDataList.add(gpsData);
            Map<String, Object> map = new HashMap<>();
            map.put("time_stamp", list.get(i).getTime_stamp());
            map.put("latitude", list.get(i).getLatitude());
            map.put("longitude", list.get(i).getLongitude());
            dataList.add(map);
        }
        if (gpsDataList != null && !gpsDataList.isEmpty()) {
            DataManager.getInstance().insertBatchGpsData(gpsDataList);
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("value", dataList);
        jsonObject.put("dataType", kEADataInfoTypeGPSData);
        sendBigWatchData(jsonObject);
    }

    @Override
    public void multiMotionData(List<EABleMultiData> list, CommonFlag commonFlag) {
        LogUtils.i(TAG, "Prepare to pass multi-motion data to flutter");
        List<Map<String, Object>> dataList = new ArrayList<>();
        List<MultiData> multiDataList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            MultiData multiData = new MultiData();
            Map<String, Object> map = new HashMap<>();
            map.put("e_type", list.get(i).getE_type().getValue());
            multiData.setMotionType(list.get(i).getE_type().getValue());
            map.put("begin_time_stamp", list.get(i).getBegin_time_stamp());
            multiData.setBegin_time_stamp(list.get(i).getBegin_time_stamp());
            map.put("end_time_stamp", list.get(i).getEnd_time_stamp());
            multiData.setEnd_time_stamp(list.get(i).getEnd_time_stamp());
            map.put("steps", list.get(i).getSteps());
            multiData.setSteps(list.get(i).getSteps());
            map.put("calorie", list.get(i).getCalorie());
            multiData.setCalorie(list.get(i).getCalorie());
            map.put("distance", list.get(i).getDistance());
            multiData.setDistance(list.get(i).getDistance());
            map.put("duration", list.get(i).getDuration());
            multiData.setDuration(list.get(i).getDuration());
            map.put("training_effect_normal", list.get(i).getTraining_effect_normal());
            multiData.setTraining_effect_normal(list.get(i).getTraining_effect_normal());
            map.put("training_effect_warmUp", list.get(i).getTraining_effect_warmUp());
            multiData.setTraining_effect_warmUp(list.get(i).getTraining_effect_warmUp());
            map.put("training_effect_fatconsumption", list.get(i).getTraining_effect_fatconsumption());
            multiData.setTraining_effect_fatconsumption(list.get(i).getTraining_effect_fatconsumption());
            map.put("training_effect_aerobic", list.get(i).getTraining_effect_aerobic());
            multiData.setTraining_effect_aerobic(list.get(i).getTraining_effect_aerobic());
            map.put("training_effect_anaerobic", list.get(i).getTraining_effect_anaerobic());
            multiData.setTraining_effect_anaerobic(list.get(i).getTraining_effect_anaerobic());
            map.put("training_effect_limit", list.get(i).getTraining_effect_limit());
            multiData.setTraining_effect_limit(list.get(i).getTraining_effect_limit());
            map.put("average_heart_rate", list.get(i).getAverage_heart_rate());
            multiData.setAverage_heart_rate(list.get(i).getAverage_heart_rate());
            map.put("average_temperature", list.get(i).getAverage_temperature());
            multiData.setAverage_temperature(list.get(i).getAverage_temperature());
            map.put("average_speed", list.get(i).getAverage_speed());
            multiData.setAverage_speed(list.get(i).getAverage_speed());
            map.put("average_pace", list.get(i).getAverage_pace());
            multiData.setAverage_pace(list.get(i).getAverage_pace());
            map.put("average_step_freq", list.get(i).getAverage_step_freq());
            multiData.setAverage_step_freq(list.get(i).getAverage_step_freq());
            map.put("average_stride", list.get(i).getAverage_stride());
            multiData.setAverage_stride(list.get(i).getAverage_stride());
            map.put("average_altitude", list.get(i).getAverage_altitude());
            multiData.setAverage_altitude(list.get(i).getAverage_altitude());
            map.put("average_heart_rate_max", list.get(i).getAverage_heart_rate_max());
            multiData.setAverage_heart_rate_max(list.get(i).getAverage_heart_rate_max());
            map.put("average_heart_rate_min", list.get(i).getAverage_heart_rate_min());
            multiData.setAverage_heart_rate_min(list.get(i).getAverage_heart_rate_min());
            multiDataList.add(multiData);
            dataList.add(map);
        }
        if (multiDataList != null && !multiDataList.isEmpty()) {
            DataManager.getInstance().insertBatchMultiData(multiDataList);
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("value", dataList);
        jsonObject.put("dataType", kEADataInfoTypeSportsData);
        sendBigWatchData(jsonObject);

    }

    @Override
    public void bloodOxygenData(List<EABleBloodOxygen> list, CommonFlag commonFlag) {
        LogUtils.i(TAG, "Prepare to pass blood oxygen data to flutter");
        List<Map<String, Object>> dataList = new ArrayList<>();
        List<BloodData> bloodDataList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            BloodData bloodData = new BloodData();
            bloodData.setBlood_oxygen_value(list.get(i).getBlood_oxygen_value());
            bloodData.setTime_stamp(list.get(i).getTime_stamp());
            bloodDataList.add(bloodData);
            Map<String, Object> map = new HashMap<>();
            map.put("time_stamp", list.get(i).getTime_stamp());
            map.put("blood_oxygen_value", list.get(i).getBlood_oxygen_value());
            dataList.add(map);
        }
        if (bloodDataList != null && !bloodDataList.isEmpty()) {
            DataManager.getInstance().insertBatchBloodData(bloodDataList);
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("value", dataList);
        jsonObject.put("dataType", kEADataInfoTypeBloodOxygenData);
        sendBigWatchData(jsonObject);
    }

    @Override
    public void pressureData(List<EABlePressureData> list, CommonFlag commonFlag) {
        LogUtils.i(TAG, "Prepare to pass the pressure data to the flutter");
        List<Map<String, Object>> dataList = new ArrayList<>();
        List<StressData> stressDataList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            StressData stressData = new StressData();
            stressData.setPressureLevel(list.get(i).getE_type().getValue());
            stressData.setTime_stamp(list.get(i).getTime_stamp());
            stressData.setStess_value(list.get(i).getStess_value());
            stressDataList.add(stressData);
            Map<String, Object> map = new HashMap<>();
            map.put("time_stamp", list.get(i).getTime_stamp());
            map.put("stess_value", list.get(i).getStess_value());
            map.put("e_type", list.get(i).getE_type().getValue());
            dataList.add(map);
        }
        if (stressDataList != null && !stressDataList.isEmpty()) {
            DataManager.getInstance().insertBatchStressData(stressDataList);
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("value", dataList);
        jsonObject.put("dataType", kEADataInfoTypeStressData);
        sendBigWatchData(jsonObject);
    }

    @Override
    public void stepFrequencyData(List<EABleStepFrequencyData> list, CommonFlag commonFlag) {
        LogUtils.i(TAG, "Prepare to pass cadence data to flutter");
        List<Map<String, Object>> dataList = new ArrayList<>();
        List<StepFreqData> stepFreqDataList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            StepFreqData stepFreqData = new StepFreqData();
            stepFreqData.setStep_freq_value(list.get(i).getStep_freq_value());
            stepFreqData.setTime_stamp(list.get(i).getTime_stamp());
            stepFreqDataList.add(stepFreqData);
            Map<String, Object> map = new HashMap<>();
            map.put("time_stamp", list.get(i).getTime_stamp());
            map.put("step_freq_value", list.get(i).getStep_freq_value());
            dataList.add(map);
        }
        if (stepFreqDataList != null && !stepFreqDataList.isEmpty()) {
            DataManager.getInstance().insertBatchFreqData(stepFreqDataList);
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("value", dataList);
        jsonObject.put("dataType", kEADataInfoTypeStepFreqData);
        sendBigWatchData(jsonObject);
    }

    @Override
    public void speedData(List<EABlePaceData> list, CommonFlag commonFlag) {
        LogUtils.i(TAG, "Prepare to pass pace data to Flutter");
        List<Map<String, Object>> dataList = new ArrayList<>();
        List<StepPaceData> stepPaceDataList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            StepPaceData stepPaceData = new StepPaceData();
            stepPaceData.setStep_pace_value(list.get(i).getStep_pace_value());
            stepPaceData.setTime_stamp(list.get(i).getTime_stamp());
            stepPaceDataList.add(stepPaceData);
            Map<String, Object> map = new HashMap<>();
            map.put("time_stamp", list.get(i).getTime_stamp());
            map.put("step_pace_value", list.get(i).getStep_pace_value());
            dataList.add(map);
        }
        if (stepPaceDataList != null && !stepPaceDataList.isEmpty()) {
            DataManager.getInstance().insertBatchPaceData(stepPaceDataList);
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("value", dataList);
        jsonObject.put("dataType", kEADataInfoTypeStepPaceData);
        sendBigWatchData(jsonObject);
    }

    @Override
    public void restingHeartRateData(List<EABleRestingRateData> list, CommonFlag commonFlag) {
        LogUtils.i(TAG, "Prepare to pass resting heart rate data to flutter");
        List<Map<String, Object>> dataList = new ArrayList<>();
        List<RestingHeartData> restingHeartDataList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            RestingHeartData restingHeartData = new RestingHeartData();
            restingHeartData.setTime_stamp(list.get(i).getTime_stamp());
            restingHeartData.setHr_value(list.get(i).getHr_value());
            restingHeartDataList.add(restingHeartData);
            Map<String, Object> map = new HashMap<>();
            map.put("time_stamp", list.get(i).getTime_stamp());
            map.put("hr_value", list.get(i).getHr_value());
            dataList.add(map);
        }
        if (restingHeartDataList != null && !restingHeartDataList.isEmpty()) {
            DataManager.getInstance().insertBatchRestingHeartData(restingHeartDataList);
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("value", dataList);
        jsonObject.put("dataType", kEADataInfoTypeRestingHeartRateData);
        sendBigWatchData(jsonObject);
    }

    @Override
    public void getHabitData(List<EABleHabitRecord> list, CommonFlag commonFlag) {
        LogUtils.i(TAG, "Prepare to pass habit data to flutter");
        List<Map<String, Object>> dataList = new ArrayList<>();
        List<HabitData> habitDataList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            HabitData habitData = new HabitData();
            Map<String, Object> map = new HashMap<>();
            map.put("time_stamp", list.get(i).getTime_stamp());
            habitData.setTime_stamp(list.get(i).getTime_stamp());
            map.put("begin_hour", list.get(i).getBegin_hour());
            habitData.setBegin_hour(list.get(i).getBegin_hour());
            map.put("begin_minute", list.get(i).getBegin_minute());
            habitData.setBegin_minute(list.get(i).getBegin_minute());
            map.put("end_hour", list.get(i).getEnd_hour());
            habitData.setEnd_hour(list.get(i).getEnd_hour());
            map.put("end_minute", list.get(i).getEnd_minute());
            habitData.setEnd_minute(list.get(i).getEnd_minute());
            map.put("redColor", list.get(i).getRedColor());
            habitData.setRedColor(list.get(i).getRedColor());
            map.put("greenColor", list.get(i).getGreenColor());
            habitData.setGreenColor(list.get(i).getGreenColor());
            map.put("blueColor", list.get(i).getBlueColor());
            habitData.setBlueColor(list.get(i).getBlueColor());
            map.put("habitState", list.get(i).getHabitState().getValue());
            habitData.setHabitState(list.get(i).getHabitState().getValue());
            map.put("habitIcon", list.get(i).getHabitIcon().getValue());
            habitData.setHabitIcon(list.get(i).getHabitIcon().getValue());
            if (TextUtils.isEmpty(list.get(i).getmContent())) {
                map.put("content", list.get(i).getmContent());
                habitData.setMContent(list.get(i).getmContent());

            }
            habitDataList.add(habitData);
            dataList.add(map);
        }
        if (habitDataList != null && !habitDataList.isEmpty()) {
            DataManager.getInstance().insertBatchHabitData(habitDataList);
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("value", dataList);
        jsonObject.put("dataType", EADataInfoTypeHabitTrackerData);
        sendBigWatchData(jsonObject);

    }

    @Override
    public void mutualFail(int i) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("value", null);
        sendBigWatchData(jsonObject);
    }

    private void sendBigWatchData(@NonNull JSONObject jsonObject) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                if (channel != null) {
                    LogUtils.i(TAG, "Deliver big data to flutter");
                    channel.invokeMethod(kGetBigWatchData, jsonObject.toJSONString());
                }
            }
        });

    }
}
