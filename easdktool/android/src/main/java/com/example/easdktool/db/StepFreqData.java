package com.example.easdktool.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class StepFreqData {
    @Id
    private long time_stamp;
    @Property
    private int step_freq_value;
    @Generated(hash = 526434766)
    public StepFreqData(long time_stamp, int step_freq_value) {
        this.time_stamp = time_stamp;
        this.step_freq_value = step_freq_value;
    }
    @Generated(hash = 814939287)
    public StepFreqData() {
    }
    public long getTime_stamp() {
        return this.time_stamp;
    }
    public void setTime_stamp(long time_stamp) {
        this.time_stamp = time_stamp;
    }
    public int getStep_freq_value() {
        return this.step_freq_value;
    }
    public void setStep_freq_value(int step_freq_value) {
        this.step_freq_value = step_freq_value;
    }
}
