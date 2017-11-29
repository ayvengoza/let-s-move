package com.ayvengoza.activ.letsmove;

import android.app.IntentService;
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

    public static ArrayList<DetectedActivity> extractDetectedActivity(Intent i){
        ArrayList<DetectedActivity> detectedActivities =
                i.getParcelableArrayListExtra(STRING_EXTRA);
        return detectedActivities;
    }

    public static Active getActive(ActivityRecognitionResult result){
        Active active = new Active();
        active.setTime(result.getTime());
        active.setElapsed(result.getElapsedRealtimeMillis());

        ArrayList<DetectedActivity> detectedActivities = (ArrayList) result.getProbableActivities();
        for(DetectedActivity act : detectedActivities){
            switch (act.getType()){
                case DetectedActivity.IN_VEHICLE:
                    active.setInVehicle(act.getConfidence());
                    break;
                case DetectedActivity.ON_BICYCLE:
                    active.setOnBicycle(act.getConfidence());
                    break;
                case DetectedActivity.ON_FOOT:
                    active.setOnFoot(act.getConfidence());
                    break;
                case DetectedActivity.STILL:
                    active.setStill(act.getConfidence());
                    break;
                case DetectedActivity.UNKNOWN:
                    active.setUnknown(act.getConfidence());
                    break;
                case DetectedActivity.TILTING:
                    active.setTilting(act.getConfidence());
                    break;
                case DetectedActivity.WALKING:
                    active.setWallking(act.getConfidence());
                    break;
                case DetectedActivity.RUNNING:
                    active.setWallking(act.getConfidence());
                    break;
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
            ArrayList<DetectedActivity> detectedActivities = (ArrayList) result.getProbableActivities();

            ActiveLab activeLab = ActiveLab.get(this);
            Active active = getActive(result);
            activeLab.addActive(active);

            Intent i = new Intent(STRING_ACTION);
            i.putExtra(STRING_EXTRA, detectedActivities);
            LocalBroadcastManager.getInstance(this).sendBroadcast(i);
            Log.i(TAG, "Result: " + result.toString());
        }
    }
}
