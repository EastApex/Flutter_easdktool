package com.example.easdktool.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class BloodData {
    @Id
    private long time_stamp;
    @Property
    private int blood_oxygen_value;
    @Generated(hash = 309086944)
    public BloodData(long time_stamp, int blood_oxygen_value) {
        this.time_stamp = time_stamp;
        this.blood_oxygen_value = blood_oxygen_value;
    }
    @Generated(hash = 1705986732)
    public BloodData() {
    }
    public long getTime_stamp() {
        return this.time_stamp;
    }
    public void setTime_stamp(long time_stamp) {
        this.time_stamp = time_stamp;
    }
    public int getBlood_oxygen_value() {
        return this.blood_oxygen_value;
    }
    public void setBlood_oxygen_value(int blood_oxygen_value) {
        this.blood_oxygen_value = blood_oxygen_value;
    }
}
