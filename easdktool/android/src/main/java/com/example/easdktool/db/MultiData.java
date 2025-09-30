package com.example.easdktool.db;

import com.apex.ax_bluetooth.model.EABleMultiData;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class MultiData {
    @Property
    private int motionType;
    @Id
    private long begin_time_stamp;
    @Property
    private long end_time_stamp;
    @Property
    private int steps;
    @Property
    private int calorie;
    @Property
    private int distance;
    @Property
    private int duration;
    @Property
    private int training_effect_normal;
    @Property
    private int training_effect_warmUp;
    @Property
    private int training_effect_fatconsumption;
    @Property
    private int training_effect_aerobic;
    @Property
    private int training_effect_anaerobic;
    @Property
    private int training_effect_limit;
    @Property
    private int average_heart_rate;
    @Property
    private float average_temperature;
    @Property
    private int average_speed;
    @Property
    private int average_pace;
    @Property
    private int average_step_freq;
    @Property
    private int average_stride;
    @Property
    private int average_altitude;
    @Property
    private int average_heart_rate_max;
    @Property
    private int average_heart_rate_min;
    @Generated(hash = 1773724687)
    public MultiData(int motionType, long begin_time_stamp, long end_time_stamp,
            int steps, int calorie, int distance, int duration,
            int training_effect_normal, int training_effect_warmUp,
            int training_effect_fatconsumption, int training_effect_aerobic,
            int training_effect_anaerobic, int training_effect_limit,
            int average_heart_rate, float average_temperature, int average_speed,
            int average_pace, int average_step_freq, int average_stride,
            int average_altitude, int average_heart_rate_max,
            int average_heart_rate_min) {
        this.motionType = motionType;
        this.begin_time_stamp = begin_time_stamp;
        this.end_time_stamp = end_time_stamp;
        this.steps = steps;
        this.calorie = calorie;
        this.distance = distance;
        this.duration = duration;
        this.training_effect_normal = training_effect_normal;
        this.training_effect_warmUp = training_effect_warmUp;
        this.training_effect_fatconsumption = training_effect_fatconsumption;
        this.training_effect_aerobic = training_effect_aerobic;
        this.training_effect_anaerobic = training_effect_anaerobic;
        this.training_effect_limit = training_effect_limit;
        this.average_heart_rate = average_heart_rate;
        this.average_temperature = average_temperature;
        this.average_speed = average_speed;
        this.average_pace = average_pace;
        this.average_step_freq = average_step_freq;
        this.average_stride = average_stride;
        this.average_altitude = average_altitude;
        this.average_heart_rate_max = average_heart_rate_max;
        this.average_heart_rate_min = average_heart_rate_min;
    }
    @Generated(hash = 305623652)
    public MultiData() {
    }
    public int getMotionType() {
        return this.motionType;
    }
    public void setMotionType(int motionType) {
        this.motionType = motionType;
    }
    public long getBegin_time_stamp() {
        return this.begin_time_stamp;
    }
    public void setBegin_time_stamp(long begin_time_stamp) {
        this.begin_time_stamp = begin_time_stamp;
    }
    public long getEnd_time_stamp() {
        return this.end_time_stamp;
    }
    public void setEnd_time_stamp(long end_time_stamp) {
        this.end_time_stamp = end_time_stamp;
    }
    public int getSteps() {
        return this.steps;
    }
    public void setSteps(int steps) {
        this.steps = steps;
    }
    public int getCalorie() {
        return this.calorie;
    }
    public void setCalorie(int calorie) {
        this.calorie = calorie;
    }
    public int getDistance() {
        return this.distance;
    }
    public void setDistance(int distance) {
        this.distance = distance;
    }
    public int getDuration() {
        return this.duration;
    }
    public void setDuration(int duration) {
        this.duration = duration;
    }
    public int getTraining_effect_normal() {
        return this.training_effect_normal;
    }
    public void setTraining_effect_normal(int training_effect_normal) {
        this.training_effect_normal = training_effect_normal;
    }
    public int getTraining_effect_warmUp() {
        return this.training_effect_warmUp;
    }
    public void setTraining_effect_warmUp(int training_effect_warmUp) {
        this.training_effect_warmUp = training_effect_warmUp;
    }
    public int getTraining_effect_fatconsumption() {
        return this.training_effect_fatconsumption;
    }
    public void setTraining_effect_fatconsumption(
            int training_effect_fatconsumption) {
        this.training_effect_fatconsumption = training_effect_fatconsumption;
    }
    public int getTraining_effect_aerobic() {
        return this.training_effect_aerobic;
    }
    public void setTraining_effect_aerobic(int training_effect_aerobic) {
        this.training_effect_aerobic = training_effect_aerobic;
    }
    public int getTraining_effect_anaerobic() {
        return this.training_effect_anaerobic;
    }
    public void setTraining_effect_anaerobic(int training_effect_anaerobic) {
        this.training_effect_anaerobic = training_effect_anaerobic;
    }
    public int getTraining_effect_limit() {
        return this.training_effect_limit;
    }
    public void setTraining_effect_limit(int training_effect_limit) {
        this.training_effect_limit = training_effect_limit;
    }
    public int getAverage_heart_rate() {
        return this.average_heart_rate;
    }
    public void setAverage_heart_rate(int average_heart_rate) {
        this.average_heart_rate = average_heart_rate;
    }
    public float getAverage_temperature() {
        return this.average_temperature;
    }
    public void setAverage_temperature(float average_temperature) {
        this.average_temperature = average_temperature;
    }
    public int getAverage_speed() {
        return this.average_speed;
    }
    public void setAverage_speed(int average_speed) {
        this.average_speed = average_speed;
    }
    public int getAverage_pace() {
        return this.average_pace;
    }
    public void setAverage_pace(int average_pace) {
        this.average_pace = average_pace;
    }
    public int getAverage_step_freq() {
        return this.average_step_freq;
    }
    public void setAverage_step_freq(int average_step_freq) {
        this.average_step_freq = average_step_freq;
    }
    public int getAverage_stride() {
        return this.average_stride;
    }
    public void setAverage_stride(int average_stride) {
        this.average_stride = average_stride;
    }
    public int getAverage_altitude() {
        return this.average_altitude;
    }
    public void setAverage_altitude(int average_altitude) {
        this.average_altitude = average_altitude;
    }
    public int getAverage_heart_rate_max() {
        return this.average_heart_rate_max;
    }
    public void setAverage_heart_rate_max(int average_heart_rate_max) {
        this.average_heart_rate_max = average_heart_rate_max;
    }
    public int getAverage_heart_rate_min() {
        return this.average_heart_rate_min;
    }
    public void setAverage_heart_rate_min(int average_heart_rate_min) {
        this.average_heart_rate_min = average_heart_rate_min;
    }

}
