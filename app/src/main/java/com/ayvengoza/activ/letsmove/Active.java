package com.ayvengoza.activ.letsmove;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by ang on 27.11.17.
 */

@Entity
public class Active {
    @PrimaryKey
    private long Time;
    private int Still;
    private int InVenicle;
    private int OnBicycle;
    private int OnFoot;
    private int Running;
    private int Walking;
    private int Titling;
    private int Unknown;

    public long getTime() {
        return Time;
    }

    public void setTime(long time) {
        Time = time;
    }

    public int getStill() {
        return Still;
    }

    public void setStill(int still) {
        Still = still;
    }

    public int getInVenicle() {
        return InVenicle;
    }

    public void setInVenicle(int inVenicle) {
        InVenicle = inVenicle;
    }

    public int getOnBicycle() {
        return OnBicycle;
    }

    public void setOnBicycle(int onBicycle) {
        OnBicycle = onBicycle;
    }

    public int getOnFoot() {
        return OnFoot;
    }

    public void setOnFoot(int onFoot) {
        OnFoot = onFoot;
    }

    public int getRunning() {
        return Running;
    }

    public void setRunning(int running) {
        Running = running;
    }

    public int getWalking() {
        return Walking;
    }

    public void setWalking(int walking) {
        Walking = walking;
    }

    public int getTitling() {
        return Titling;
    }

    public void setTitling(int titling) {
        Titling = titling;
    }

    public int getUnknown() {
        return Unknown;
    }

    public void setUnknown(int unknown) {
        Unknown = unknown;
    }
}
