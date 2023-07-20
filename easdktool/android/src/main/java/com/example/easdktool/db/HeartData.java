package com.example.easdktool.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class HeartData {
    @Id
    private long time_stamp;
    @Property
    private int hr_value;
    @Generated(hash = 379891218)
    public HeartData(long time_stamp, int hr_value) {
        this.time_stamp = time_stamp;
        this.hr_value = hr_value;
    }
    @Generated(hash = 241019369)
    public HeartData() {
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
