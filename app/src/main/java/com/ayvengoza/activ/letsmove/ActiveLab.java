package com.ayvengoza.activ.letsmove;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.ayvengoza.activ.letsmove.database.ActiveCursorWraper;
import com.ayvengoza.activ.letsmove.database.ActiveDbHelper;
import com.ayvengoza.activ.letsmove.database.ActiveDbSchema;
import com.ayvengoza.activ.letsmove.database.ActiveDbSchema.ActiveTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ang on 29.11.17.
 */

public class ActiveLab {
    private static final String TAG = "ActiveLab";
    private static ActiveLab sActiveLab;

    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static ActiveLab get(Context context){
        if(sActiveLab == null){
            return new ActiveLab(context);
        }
        return sActiveLab;
    }

    private ActiveLab(Context context){
        mContext = context;
        try{
            mDatabase = new ActiveDbHelper(mContext).getWritableDatabase();
            Log.i(TAG, "Get writable database " + ActiveDbHelper.DB_NAME);
        } catch (SQLiteException e){
            mDatabase = new ActiveDbHelper(mContext).getReadableDatabase();
            Log.i(TAG, "Get readable database " + ActiveDbHelper.DB_NAME);
        }

    }

    public void addActive(Active active){
        ContentValues values = getContentValues(active);
        mDatabase.insert(ActiveTable.NAME, null, values);
    }

    public List<Active> getActives(){
        List<Active> actives = new ArrayList<>();
        ActiveCursorWraper cursor = queryActives(null, null);

        try{
            cursor.moveToFirst();
            while(!cursor.isAfterLast()){
                actives.add(cursor.getActive());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }

        return actives;
    }

    public List<Active> getActivesFromTime(long startTime){
        List<Active> actives = new ArrayList<>();
        ActiveCursorWraper cursor = queryActives(
                ActiveTable.Cols.TIME + " > ?", new String[]{String.valueOf(startTime)});
        try{
            cursor.moveToFirst();
            while(!cursor.isAfterLast()){
                actives.add(cursor.getActive());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }

        return actives;
    }

    private ActiveCursorWraper queryActives(String whereClause, String[] whereArgs){
        Cursor cursor = mDatabase.query(
                ActiveTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null);
        return new ActiveCursorWraper(cursor);
    }

    private static ContentValues getContentValues(Active active) {
        ContentValues values = new ContentValues();
        values.put(ActiveTable.Cols.TIME, active.getTime());
        values.put(ActiveTable.Cols.ELAPSED, active.getElapsed());
        values.put(ActiveTable.Cols.IN_VEHICLE, active.getInVehicle());
        values.put(ActiveTable.Cols.ON_BICYCLE, active.getOnBicycle());
        values.put(ActiveTable.Cols.ON_FOOT, active.getOnFoot());
        values.put(ActiveTable.Cols.RUNNING, active.getRunning());
        values.put(ActiveTable.Cols.WALKING, active.getWallking());
        values.put(ActiveTable.Cols.TILTING, active.getTilting());
        values.put(ActiveTable.Cols.STILL, active.getStill());
        values.put(ActiveTable.Cols.UNKNOWN, active.getUnknown());
        return values;
    }


}
