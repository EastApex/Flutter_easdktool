package com.example.easdktool.db;

import com.apex.ax_bluetooth.model.EABlePressureData;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class StressData {
    @Id
    private long time_stamp;
    @Property
    private int stess_value;
    @Property
    private int pressureLevel;
    @Generated(hash = 340325548)
    public StressData(long time_stamp, int stess_value, int pressureLevel) {
        this.time_stamp = time_stamp;
        this.stess_value = stess_value;
        this.pressureLevel = pressureLevel;
    }
    @Generated(hash = 353323082)
    public StressData() {
    }
    public long getTime_stamp() {
        return this.time_stamp;
    }
    public void setTime_stamp(long time_stamp) {
        this.time_stamp = time_stamp;
    }
    public int getStess_value() {
        return this.stess_value;
    }
    public void setStess_value(int stess_value) {
        this.stess_value = stess_value;
    }
    public int getPressureLevel() {
        return this.pressureLevel;
    }
    public void setPressureLevel(int pressureLevel) {
        this.pressureLevel = pressureLevel;
    }


}
