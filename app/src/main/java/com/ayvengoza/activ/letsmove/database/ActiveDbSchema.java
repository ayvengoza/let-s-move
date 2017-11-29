package com.ayvengoza.activ.letsmove.database;

/**
 * Created by ang on 29.11.17.
 */

public class ActiveDbSchema {
    public static final class ActiveTable {
        public static final String NAME = "ACTIVITIES";
        public static final class Cols {
            public static final String ID = "_id";
            public static final String TIME = "TIME";
            public static final String ELAPSED = "ELAPSED";
            public static final String IN_VEHICLE = "IN_VEHICLE";
            public static final String ON_BICYCLE = "ON_BICYCLE";
            public static final String ON_FOOT = "ON_FOOT";
            public static final String RUNNING = "RUNNING";
            public static final String WALKING = "WALKING";
            public static final String STILL = "STILL";
            public static final String TILTING = "TILTING";
            public static final String UNKNOWN = "UNKNOWN";
        }
    }
}
