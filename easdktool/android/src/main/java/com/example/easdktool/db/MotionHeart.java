package com.example.easdktool.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class MotionHeart {
    @Id
    private long stampTime;
    @Property
    private int motionHr;
    @Generated(hash = 887474438)
    public MotionHeart(long stampTime, int motionHr) {
        this.stampTime = stampTime;
        this.motionHr = motionHr;
    }
    @Generated(hash = 2073664306)
    public MotionHeart() {
    }
    public long getStampTime() {
        return this.stampTime;
    }
    public void setStampTime(long stampTime) {
        this.stampTime = stampTime;
    }
    public int getMotionHr() {
        return this.motionHr;
    }
    public void setMotionHr(int motionHr) {
        this.motionHr = motionHr;
    }

}
