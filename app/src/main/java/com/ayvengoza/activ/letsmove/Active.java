package com.ayvengoza.activ.letsmove;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by ang on 29.11.17.
 */

public class Active {
    private long mTime;
    private long mElapsed;
    private float mInVehicle;
    private float mOnBicycle;
    private float mOnFoot;
    private float mRunning;
    private float mWallking;
    private float mStill;
    private float mTilting;
    private float mUnknown;

    public long getTime() {
        return mTime;
    }

    public void setTime(long time) {
        mTime = time;
    }

    public long getElapsed() {
        return mElapsed;
    }

    public void setElapsed(long elapsed) {
        mElapsed = elapsed;
    }

    public float getInVehicle() {
        return mInVehicle;
    }

    public void setInVehicle(float inVehicle) {
        mInVehicle = inVehicle;
    }

    public float getOnBicycle() {
        return mOnBicycle;
    }

    public void setOnBicycle(float onBicycle) {
        mOnBicycle = onBicycle;
    }

    public float getOnFoot() {
        return mOnFoot;
    }

    public void setOnFoot(float onFoot) {
        mOnFoot = onFoot;
    }

    public float getRunning() {
        return mRunning;
    }

    public void setRunning(float running) {
        mRunning = running;
    }

    public float getWallking() {
        return mWallking;
    }

    public void setWallking(float wallking) {
        mWallking = wallking;
    }

    public float getStill() {
        return mStill;
    }

    public void setStill(float still) {
        mStill = still;
    }

    public float getTilting() {
        return mTilting;
    }

    public void setTilting(float tilting) {
        mTilting = tilting;
    }

    public float getUnknown() {
        return mUnknown;
    }

    public void setUnknown(float unknown) {
        mUnknown = unknown;
    }

    @Override
    public String toString() {
        Date date = new Date(mTime);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        return "T:" + sdf.format(date) +
                " ST:" + (int)mStill +
                " T:" + (int)mTilting +
                " F:" + (int)mOnFoot +
                " W:" + (int)mWallking +
                " R:" + (int)mRunning +
                " B:" + (int)mOnBicycle +
                " V:" + (int)mInVehicle +
                " U:" + (int)mUnknown;
    }

}
