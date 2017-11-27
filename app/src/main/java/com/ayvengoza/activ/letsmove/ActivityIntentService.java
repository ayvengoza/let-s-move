package com.ayvengoza.activ.letsmove;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.TextureView;

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

    public ActivityIntentService(){
        super(TAG);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        ActivityRecognitionResult result = ActivityRecognitionResult.extractResult(intent);
        Intent i = new Intent(STRING_ACTION);
        ArrayList<DetectedActivity> detectedActivities = (ArrayList) result.getProbableActivities();
        i.putExtra(STRING_EXTRA, detectedActivities);
        LocalBroadcastManager.getInstance(this).sendBroadcast(i);
        Log.i(TAG, "Result: " + result.toString());
    }
}
