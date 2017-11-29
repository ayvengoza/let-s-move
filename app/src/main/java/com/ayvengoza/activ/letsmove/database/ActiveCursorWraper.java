package com.ayvengoza.activ.letsmove.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.ayvengoza.activ.letsmove.Active;
import com.ayvengoza.activ.letsmove.database.ActiveDbSchema.ActiveTable;

/**
 * Created by ang on 29.11.17.
 */

public class ActiveCursorWraper extends CursorWrapper {
    /**
     * Creates a cursor wrapper.
     *
     * @param cursor The underlying cursor to wrap.
     */
    public ActiveCursorWraper(Cursor cursor) {
        super(cursor);
    }

    public Active getActive(){
        long time = getLong(getColumnIndex(ActiveTable.Cols.TIME));
        long elapsate = getLong(getColumnIndex(ActiveTable.Cols.ELAPSED));
        float inViehicle = getFloat(getColumnIndex(ActiveTable.Cols.IN_VEHICLE));
        float onBicycle = getFloat(getColumnIndex(ActiveTable.Cols.ON_BICYCLE));
        float onFoot = getFloat(getColumnIndex(ActiveTable.Cols.ON_FOOT));
        float running = getFloat(getColumnIndex(ActiveTable.Cols.RUNNING));
        float walking = getFloat(getColumnIndex(ActiveTable.Cols.WALKING));
        float tilting = getFloat(getColumnIndex(ActiveTable.Cols.TILTING));
        float still = getFloat(getColumnIndex(ActiveTable.Cols.STILL));
        float unknown = getFloat(getColumnIndex(ActiveTable.Cols.UNKNOWN));

        Active active = new Active();
        active.setTime(time);
        active.setElapsed(elapsate);
        active.setInVehicle(inViehicle);
        active.setOnBicycle(onBicycle);
        active.setOnFoot(onFoot);
        active.setRunning(running);
        active.setWallking(walking);
        active.setTilting(tilting);
        active.setStill(still);
        active.setUnknown(unknown);

        return active;
    }
}
