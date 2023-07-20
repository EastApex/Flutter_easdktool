package com.example.easdktool.db;

import com.apex.bluetooth.enumeration.HabitIcon;
import com.apex.bluetooth.enumeration.HabitState;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class HabitData {
    @Property
    private int habitIcon;
    @Property
    private int habitState;
    @Id
    private long time_stamp;
    @Property
    private int begin_hour;
    @Property
    private int begin_minute;
    @Property
    private int end_hour;
    @Property
    private int end_minute;
    @Property
    private int redColor;
    @Property
    private int greenColor;
    @Property
    private int blueColor;
    @Property
    private String mContent;
    @Generated(hash = 1832334050)
    public HabitData(int habitIcon, int habitState, long time_stamp, int begin_hour,
            int begin_minute, int end_hour, int end_minute, int redColor,
            int greenColor, int blueColor, String mContent) {
        this.habitIcon = habitIcon;
        this.habitState = habitState;
        this.time_stamp = time_stamp;
        this.begin_hour = begin_hour;
        this.begin_minute = begin_minute;
        this.end_hour = end_hour;
        this.end_minute = end_minute;
        this.redColor = redColor;
        this.greenColor = greenColor;
        this.blueColor = blueColor;
        this.mContent = mContent;
    }
    @Generated(hash = 1644739583)
    public HabitData() {
    }
    public int getHabitIcon() {
        return this.habitIcon;
    }
    public void setHabitIcon(int habitIcon) {
        this.habitIcon = habitIcon;
    }
    public int getHabitState() {
        return this.habitState;
    }
    public void setHabitState(int habitState) {
        this.habitState = habitState;
    }
    public long getTime_stamp() {
        return this.time_stamp;
    }
    public void setTime_stamp(long time_stamp) {
        this.time_stamp = time_stamp;
    }
    public int getBegin_hour() {
        return this.begin_hour;
    }
    public void setBegin_hour(int begin_hour) {
        this.begin_hour = begin_hour;
    }
    public int getBegin_minute() {
        return this.begin_minute;
    }
    public void setBegin_minute(int begin_minute) {
        this.begin_minute = begin_minute;
    }
    public int getEnd_hour() {
        return this.end_hour;
    }
    public void setEnd_hour(int end_hour) {
        this.end_hour = end_hour;
    }
    public int getEnd_minute() {
        return this.end_minute;
    }
    public void setEnd_minute(int end_minute) {
        this.end_minute = end_minute;
    }
    public int getRedColor() {
        return this.redColor;
    }
    public void setRedColor(int redColor) {
        this.redColor = redColor;
    }
    public int getGreenColor() {
        return this.greenColor;
    }
    public void setGreenColor(int greenColor) {
        this.greenColor = greenColor;
    }
    public int getBlueColor() {
        return this.blueColor;
    }
    public void setBlueColor(int blueColor) {
        this.blueColor = blueColor;
    }
    public String getMContent() {
        return this.mContent;
    }
    public void setMContent(String mContent) {
        this.mContent = mContent;
    }
}
