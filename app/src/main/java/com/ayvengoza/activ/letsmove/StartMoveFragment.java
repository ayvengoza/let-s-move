package com.ayvengoza.activ.letsmove;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.ActivityRecognition;
import com.google.android.gms.location.DetectedActivity;

import java.util.ArrayList;

/**
 * Created by ang on 21.11.17.
 */

public class StartMoveFragment extends Fragment implements ResultCallback{
    private static final String TAG = "StartMoveFragment";

    private GoogleApiClient mGoogleApiClient;
    private ActivityDetectionBroadcastReceiver mBroadcastReceiver;
    private Button mRequestUpdatesBtn;
    private Button mRemoveUpdatesBtn;

    public static StartMoveFragment newInstance(){
        Bundle args = new Bundle();
        StartMoveFragment fragment = new StartMoveFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBroadcastReceiver = new ActivityDetectionBroadcastReceiver();
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addApi(ActivityRecognition.API)
                .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                    @Override
                    public void onConnected(@Nullable Bundle bundle) {
                        Log.i(TAG, "Connected google api");
                    }

                    @Override
                    public void onConnectionSuspended(int i) {
                        Log.i(TAG, "Connection suspended google api");
                        mGoogleApiClient.connect();
                    }
                })
                .addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        Log.i(TAG, "Connection failed. Error: " + connectionResult.getErrorCode());
                    }
                })
                .build();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_start_move, container, false);
        mRequestUpdatesBtn = (Button) view.findViewById(R.id.request_updates_button);
        mRequestUpdatesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestActivityUpdates();
            }
        });
        mRemoveUpdatesBtn = (Button) view.findViewById(R.id.remove_updates_button);
        mRemoveUpdatesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeActivityUpdates();
            }
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    public void onResume() {
        super.onResume();
        LocalBroadcastManager lbm = LocalBroadcastManager.getInstance(getActivity());
        lbm.registerReceiver(mBroadcastReceiver, new IntentFilter(ActivityIntentService.STRING_ACTION));
    }

    @Override
    public void onStop() {
        super.onStop();
        if(mGoogleApiClient.isConnected())
            mGoogleApiClient.disconnect();
    }

    public String getDetectedActivity(int detectedActivityType){
        Resources resources = getActivity().getResources();
        switch(detectedActivityType){
            case DetectedActivity.IN_VEHICLE:
                return resources.getString(R.string.in_vehicle);
            case DetectedActivity.ON_BICYCLE:
                return resources.getString(R.string.on_bicycle);
            case DetectedActivity.ON_FOOT:
                return resources.getString(R.string.on_foot);
            case DetectedActivity.RUNNING:
                return resources.getString(R.string.running);
            case DetectedActivity.WALKING:
                return resources.getString(R.string.walking);
            case DetectedActivity.STILL:
                return resources.getString(R.string.still);
            case DetectedActivity.TILTING:
                return resources.getString(R.string.tilting);
            case DetectedActivity.UNKNOWN:
                return resources.getString(R.string.unknown);
            default:
                return resources.getString(R.string.unidentifiable_activity, detectedActivityType);
        }
    }

    public void requestActivityUpdates() {
        if (mGoogleApiClient.isConnected()) {
            ActivityRecognition.ActivityRecognitionApi
                    .requestActivityUpdates(mGoogleApiClient, 0, getActivityDetectionPendingIntent());
        }
    }

    public void removeActivityUpdates() {
        ActivityRecognition.ActivityRecognitionApi
                .removeActivityUpdates(mGoogleApiClient,getActivityDetectionPendingIntent());
    }

    private PendingIntent getActivityDetectionPendingIntent() {
        Intent intent = new Intent(getActivity(), ActivityIntentService.class);

        return PendingIntent.getService(getActivity(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    @Override
    public void onResult(@NonNull Result result) {
    }

    public class ActivityDetectionBroadcastReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            ArrayList<DetectedActivity> detectedActivities =
                    ActivityIntentService.extractDetectedActivity(intent);
            String activityString = "";
            for(DetectedActivity activity : detectedActivities){
                activityString += " Activity: " +
                        getDetectedActivity(activity.getType()) + ", Confidence: "+
                        activity.getConfidence() + "%\n";
            }
            Log.i(TAG, activityString);
        }
    }
}
