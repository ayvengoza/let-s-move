package com.ayvengoza.activ.letsmove;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

/**
 * Created by ang on 27.11.17.
 */
@Database(entities = {Active.class}, version = 1)
public abstract class AppDataBase extends RoomDatabase {
    public static final String DB_NAME = "active-database";
    public abstract ActiveDao getActiveDao();
}
