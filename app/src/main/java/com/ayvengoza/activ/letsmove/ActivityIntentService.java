package com.ayvengoza.activ.letsmove;

import android.app.IntentService;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.android.gms.location.ActivityRecognitionResult;
import com.google.android.gms.location.DetectedActivity;

import java.util.ArrayList;

/**
 * Created by ang on 21.11.17.
 */

public class ActivityIntentService extends IntentService {
    private static final String TAG = "ActivityIntentService";

    public static final String STRING_ACTION = "com.ayvengoza.activ.letsmove.STRING_ACTION";
    public static final String STRING_EXTRA = "com.ayvengoza.activ.letsmove.STRING_EXTRA";

    private AppDataBase mDataBase;

    public static ArrayList<DetectedActivity> extractDetectedActivity(Intent i){
        ArrayList<DetectedActivity> detectedActivities =
                i.getParcelableArrayListExtra(STRING_EXTRA);
        return detectedActivities;
    }

    private static Active getActiveFromResult(ActivityRecognitionResult result){
        ArrayList<DetectedActivity> detectedActivities = (ArrayList) result.getProbableActivities();
        Active active = new Active();
        active.setTime(result.getTime());
        for(DetectedActivity detected : detectedActivities){
            switch (detected.getType()){
                case DetectedActivity.IN_VEHICLE:
                    active.setInVenicle(detected.getConfidence());
                    break;
                case DetectedActivity.ON_BICYCLE:
                    active.setOnBicycle(detected.getConfidence());
                    break;
                case  DetectedActivity.ON_FOOT:
                    active.setOnFoot(detected.getConfidence());
                    break;
                case DetectedActivity.STILL:
                    active.setOnFoot(detected.getConfidence());
                    break;
                case DetectedActivity.UNKNOWN:
                    active.setUnknown(detected.getConfidence());
                    break;
                case DetectedActivity.TILTING:
                    active.setTitling(detected.getConfidence());
                    break;
                case DetectedActivity.WALKING:
                    active.setWalking(detected.getConfidence());
                    break;
                case DetectedActivity.RUNNING:
                    active.setRunning(detected.getConfidence());
            }
        }
        return active;
    }

    public ActivityIntentService(){
        super(TAG);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if(ActivityRecognitionResult.hasResult(intent)){
            ActivityRecognitionResult result = ActivityRecognitionResult.extractResult(intent);
            Active active = getActiveFromResult(result);
            mDataBase = Room.databaseBuilder(getApplicationContext(),
                    AppDataBase.class, AppDataBase.DB_NAME).build();
            mDataBase.getActiveDao().insert(active);
//            Intent i = new Intent(STRING_ACTION);
//            i.putExtra(STRING_EXTRA, detectedActivities);
//            LocalBroadcastManager.getInstance(this).sendBroadcast(i);
            Log.i(TAG, "Result: " + result.toString());
        }
    }
}
