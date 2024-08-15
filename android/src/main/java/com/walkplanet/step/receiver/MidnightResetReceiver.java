package com.walkplanet.step.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import com.walkplanet.step.service.StepCounterService;

public class MidnightResetReceiver extends BroadcastReceiver {
    private static final String TAG = "MidnightResetReceiver";
    private static final String PREFS_NAME = "StepCounterPrefs";
    private static final String STEPS_KEY = "steps";
    private static final String LAST_RESET_KEY = "last_reset_time";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "MidnightResetReceiver onReceive");
        if ("com.walkplanet.ACTION_MIDNIGHT_RESET".equals(intent.getAction())) {
            SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt(STEPS_KEY, 0);
            editor.putLong(LAST_RESET_KEY, System.currentTimeMillis());
            editor.apply();
            Log.d(TAG, "Steps reset to 0 by MidnightResetReceiver");

            Intent serviceIntent = new Intent(context, StepCounterService.class);
            serviceIntent.setAction("com.walkplanet.ACTION_RESET_STEPS");
            context.startService(serviceIntent);
            Log.d(TAG, "======================== Count Service reset to 0 by MidnightResetReceiver");
        }
    }
}
