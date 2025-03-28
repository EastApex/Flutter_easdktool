package com.example.easdktool.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class SleepData {
    @Id
    private long time_stamp;
    @Property
    private int SleepMode;
    @Generated(hash = 814192771)
    public SleepData(long time_stamp, int SleepMode) {
        this.time_stamp = time_stamp;
        this.SleepMode = SleepMode;
    }
    @Generated(hash = 1639116881)
    public SleepData() {
    }
    public long getTime_stamp() {
        return this.time_stamp;
    }
    public void setTime_stamp(long time_stamp) {
        this.time_stamp = time_stamp;
    }
    public int getSleepMode() {
        return this.SleepMode;
    }
    public void setSleepMode(int SleepMode) {
        this.SleepMode = SleepMode;
    }



}
