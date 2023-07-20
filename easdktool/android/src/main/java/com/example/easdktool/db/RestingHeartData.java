package com.example.easdktool.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class RestingHeartData {
    @Id
    private long time_stamp;
    @Property
    private int hr_value;
    @Generated(hash = 1042586508)
    public RestingHeartData(long time_stamp, int hr_value) {
        this.time_stamp = time_stamp;
        this.hr_value = hr_value;
    }
    @Generated(hash = 238028280)
    public RestingHeartData() {
    }
    public long getTime_stamp() {
        return this.time_stamp;
    }
    public void setTime_stamp(long time_stamp) {
        this.time_stamp = time_stamp;
    }
    public int getHr_value() {
        return this.hr_value;
    }
    public void setHr_value(int hr_value) {
        this.hr_value = hr_value;
    }
}
