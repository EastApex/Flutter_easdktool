package com.example.easdktool.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class DailyData {
    @Id
    private long time_stamp;
    @Property
    private int steps;
    @Property
    private int calorie;
    @Property
    private int distance;
    @Property
    private int duration;
    @Property
    private int average_heart_rate;
    @Generated(hash = 1426104562)
    public DailyData(long time_stamp, int steps, int calorie, int distance,
            int duration, int average_heart_rate) {
        this.time_stamp = time_stamp;
        this.steps = steps;
        this.calorie = calorie;
        this.distance = distance;
        this.duration = duration;
        this.average_heart_rate = average_heart_rate;
    }
    @Generated(hash = 556979270)
    public DailyData() {
    }
    public long getTime_stamp() {
        return this.time_stamp;
    }
    public void setTime_stamp(long time_stamp) {
        this.time_stamp = time_stamp;
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
    public int getAverage_heart_rate() {
        return this.average_heart_rate;
    }
    public void setAverage_heart_rate(int average_heart_rate) {
        this.average_heart_rate = average_heart_rate;
    }

}
