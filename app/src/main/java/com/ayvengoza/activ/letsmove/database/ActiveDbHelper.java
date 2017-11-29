package com.ayvengoza.activ.letsmove.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ayvengoza.activ.letsmove.database.ActiveDbSchema.ActiveTable;

/**
 * Created by ang on 28.11.17.
 */

public class ActiveDbHelper extends SQLiteOpenHelper {
    public static final int VERSION = 1;
    public static final String DB_NAME = "active.db";


    public ActiveDbHelper(Context context){
        super(context, DB_NAME, null, VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE " + ActiveTable.NAME +
                        " (" +
                        ActiveTable.Cols.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        ActiveTable.Cols.TIME + " INTEGER, " +
                        ActiveTable.Cols.ELAPSED + " INTEGER, " +
                        ActiveTable.Cols.IN_VEHICLE + " REAL, " +
                        ActiveTable.Cols.ON_BICYCLE + " REAL, " +
                        ActiveTable.Cols.ON_FOOT + " REAL, " +
                        ActiveTable.Cols.RUNNING + " REAL, " +
                        ActiveTable.Cols.WALKING + " REAL, " +
                        ActiveTable.Cols.STILL + " REAL, " +
                        ActiveTable.Cols.TILTING + " REAL, " +
                        ActiveTable.Cols.UNKNOWN + " REAL" +
                ");"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


}
