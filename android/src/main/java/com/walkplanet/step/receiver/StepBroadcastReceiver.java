package com.walkplanet.step.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

public class StepBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG = "StepBroadcastReceiver";
    private static final String ACTION_RESET_STEP = "com.walkplanet.ACTION_RESET_STEP";
    private static final String TODAY_STEPS = "com.walkplanet.ACTION_TODAY_STEPS";
    private static final String TODAY_STEPS_VALUE = "todaySteps";
    private static final String PREFS_NAME = "StepCounterPrefs";
    private static final String STEPS_KEY = "steps";
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "Broadcast received");
        if (intent != null) {
            Log.d(TAG, "Intent action: " + intent.getAction());
            if (ACTION_RESET_STEP.equals(intent.getAction())) {
                Log.d(TAG, "Received broadcast: Action=" + ACTION_RESET_STEP);
                SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putInt(STEPS_KEY, 0);
                editor.apply();
                Log.d(TAG, "RESET STEP : " + prefs.getInt(STEPS_KEY, 0));
            } else if (TODAY_STEPS.equals(intent.getAction())) {
                Log.d(TAG, "Received broadcast: Action=" + TODAY_STEPS);
                SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putInt(STEPS_KEY, intent.getIntExtra(TODAY_STEPS_VALUE, 0));
                editor.apply();
                Log.d(TAG, "TODAY STEPS : " + prefs.getInt(STEPS_KEY, 0));
            } else {
                Log.d(TAG, "Unknown action");
            }
        } else {
            Log.d(TAG, "Intent is null");
        }
    }
}
