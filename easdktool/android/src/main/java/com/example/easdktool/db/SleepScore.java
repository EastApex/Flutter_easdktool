package com.example.easdktool.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class SleepScore {
    @Property
    private int sleep_score;
    @Id
    private long startTime;
    @Property
    private long endTime;
    @Generated(hash = 1855616421)
    public SleepScore(int sleep_score, long startTime, long endTime) {
        this.sleep_score = sleep_score;
        this.startTime = startTime;
        this.endTime = endTime;
    }
    @Generated(hash = 1315634270)
    public SleepScore() {
    }
    public int getSleep_score() {
        return this.sleep_score;
    }
    public void setSleep_score(int sleep_score) {
        this.sleep_score = sleep_score;
    }
    public long getStartTime() {
        return this.startTime;
    }
    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }
    public long getEndTime() {
        return this.endTime;
    }
    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }


}
