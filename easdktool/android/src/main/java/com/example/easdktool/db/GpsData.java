package com.example.easdktool.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class GpsData {
    @Id
    private long time_stamp;
    @Property
    private double latitude;
    @Property
    private double longitude;
    @Generated(hash = 2023600021)
    public GpsData(long time_stamp, double latitude, double longitude) {
        this.time_stamp = time_stamp;
        this.latitude = latitude;
        this.longitude = longitude;
    }
    @Generated(hash = 1232323744)
    public GpsData() {
    }
    public long getTime_stamp() {
        return this.time_stamp;
    }
    public void setTime_stamp(long time_stamp) {
        this.time_stamp = time_stamp;
    }
    public double getLatitude() {
        return this.latitude;
    }
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
    public double getLongitude() {
        return this.longitude;
    }
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }


}
