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
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.ActivityRecognition;
import com.google.android.gms.location.ActivityRecognitionClient;
import com.google.android.gms.location.DetectedActivity;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by ang on 21.11.17.
 */

public class StartMoveFragment extends Fragment implements ResultCallback{
    private static final String TAG = "StartMoveFragment";

    private GoogleApiClient mGoogleApiClient;
    private ActivityDetectionBroadcastReceiver mBroadcastReceiver;
    private Button mRequestUpdatesBtn;
    private Button mRemoveUpdatesBtn;
    private TextView mTextView;

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
        mTextView = (TextView) view.findViewById(R.id.text_view);
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
        updateUI();
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

    public void requestActivityUpdates() {
        if (mGoogleApiClient.isConnected()) {
            ActivityRecognitionClient activityRecognitionClient =
                    ActivityRecognition.getClient(getActivity());
            Task task = activityRecognitionClient.requestActivityUpdates(10_000L, getActivityDetectionPendingIntent());
            Date date = new Date();
            AppPreferences.setStartTime(getActivity(), date.getTime());
        }
    }

    public void removeActivityUpdates() {
        ActivityRecognitionClient activityRecognitionClient =
                ActivityRecognition.getClient(getActivity());
        Task task = activityRecognitionClient.removeActivityUpdates(getActivityDetectionPendingIntent());
        AppPreferences.setStartTime(getActivity(), 0);
    }

    private void updateUI(){
        long time = AppPreferences.getStartTime(getActivity());
        if(time != 0) {
            ActiveLab activeLab = ActiveLab.get(getActivity());
            List<Active> actives = activeLab.getActivesFromTime(time);

            StringBuilder stringBuilder = new StringBuilder();
            for (Active active : actives) {
                stringBuilder.append(active + "\n");
            }

            mTextView.setText(stringBuilder.toString());
        }
    }

    private PendingIntent getActivityDetectionPendingIntent() {
        Intent intent = new Intent(getActivity(), ActivityIntentService.class);

        return PendingIntent.getService(getActivity(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    @Override
    public void onResult(@NonNull Result result) {
    }

    public class ActivityDetectionBroadcastReceiver extends BroadcastReceiver{

        public String getDetectedActivity(Context context, int detectedActivityType){
            Resources resources = context.getResources();
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

        @Override
        public void onReceive(Context context, Intent intent) {
            ArrayList<DetectedActivity> detectedActivities =
                    ActivityIntentService.extractDetectedActivity(intent);
            updateUI();
            Log.d(TAG, "Receive intent in ActivityDetectionBroadcastReceiver");
        }
    }
}
