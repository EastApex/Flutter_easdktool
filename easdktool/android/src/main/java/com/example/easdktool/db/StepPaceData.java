package com.example.easdktool.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class StepPaceData {
    @Id
    private long time_stamp;
    @Property
    private int step_pace_value;
    @Generated(hash = 102750147)
    public StepPaceData(long time_stamp, int step_pace_value) {
        this.time_stamp = time_stamp;
        this.step_pace_value = step_pace_value;
    }
    @Generated(hash = 35352576)
    public StepPaceData() {
    }
    public long getTime_stamp() {
        return this.time_stamp;
    }
    public void setTime_stamp(long time_stamp) {
        this.time_stamp = time_stamp;
    }
    public int getStep_pace_value() {
        return this.step_pace_value;
    }
    public void setStep_pace_value(int step_pace_value) {
        this.step_pace_value = step_pace_value;
    }

}
