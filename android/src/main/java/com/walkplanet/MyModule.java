package com.walkplanet;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.PowerManager;
import android.provider.Settings;
import android.util.Log;
import androidx.annotation.NonNull;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.walkplanet.notification.receiver.NotificationReceiver;
import com.walkplanet.step.receiver.RestartServiceReceiver;
import com.walkplanet.step.receiver.StepBroadcastReceiver;

public class MyModule extends ReactContextBaseJavaModule {
    private static ReactApplicationContext reactContext;
    private static final String ACTION_RESET_STEP = "com.walkplanet.ACTION_RESET_STEP";

    private static final String TODAY_STEPS = "com.walkplanet.ACTION_TODAY_STEPS";
    private static final String TODAY_STEPS_VALUE = "todaySteps";

    MyModule(ReactApplicationContext context) {
        super(context);
        reactContext = context;
    }

    @NonNull
    @Override
    public String getName() {
        return "MyModule";
    }

    @ReactMethod
    public void openBatteryOptimizationSettings(Promise promise) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                reactContext.startActivity(intent);
                promise.resolve(null);
            } else {
                promise.reject("Battery optimization settings not available for this Android version.");
            }
        } catch (Exception e) {
            promise.reject("Error opening battery optimization settings: " + e.getMessage());
        }
    }

    @ReactMethod
    public void isIgnoringBatteryOptimizations(Promise promise) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                PowerManager pm = (PowerManager) reactContext.getSystemService(Context.POWER_SERVICE);
                if (pm.isIgnoringBatteryOptimizations(reactContext.getPackageName())) {
                    promise.resolve(true);
                } else {
                    promise.resolve(false);
                }
            } else {
                promise.resolve(false);
            }
        } catch (Exception e) {
            promise.reject("Error checking battery optimization status: " + e.getMessage());
        }
    }

    @ReactMethod
    public void openAppBatterySettings(Promise promise) {
        try {
            Intent intent = new Intent();
            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts("package", reactContext.getPackageName(), null);
            intent.setData(uri);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            reactContext.startActivity(intent);
            promise.resolve(null);
        } catch (Exception e) {
            promise.reject("Error opening app battery settings: " + e.getMessage());
        }
    }

    @ReactMethod
    public void sendResetStepBroadcast() {
        try {
            Intent resetStepsIntent = new Intent(reactContext, StepBroadcastReceiver.class);
            resetStepsIntent.setAction(ACTION_RESET_STEP);
            reactContext.sendBroadcast(resetStepsIntent);

            Intent restartIntnet = new Intent(reactContext, RestartServiceReceiver.class);
            restartIntnet.setAction("RestartService");
            reactContext.sendBroadcast(restartIntnet);

            Intent restartNotificationIntnet = new Intent(reactContext, NotificationReceiver.class);
            restartIntnet.setAction("RestartNotification");
            reactContext.sendBroadcast(restartNotificationIntnet);

            Log.d("MyModule", "Reset step broadcast sent");
        } catch (Exception e) {
            Log.e("MyModule", "Error sending reset step broadcast: " + e.getMessage());
        }
    }

    @ReactMethod
    public void sendTodayStepsBroadcast(int todaySteps) {
        try {
            Intent todayStepsIntent = new Intent(reactContext, StepBroadcastReceiver.class);
            todayStepsIntent.setAction(TODAY_STEPS);
            todayStepsIntent.putExtra(TODAY_STEPS_VALUE, todaySteps);
            reactContext.sendBroadcast(todayStepsIntent);

            Intent restartIntnet = new Intent(reactContext, RestartServiceReceiver.class);
            restartIntnet.setAction("RestartService");
            reactContext.sendBroadcast(restartIntnet);

            Intent restartNotificationIntnet = new Intent(reactContext, NotificationReceiver.class);
            restartIntnet.setAction("RestartNotification");
            reactContext.sendBroadcast(restartNotificationIntnet);

            Log.d("MyModule", "Today steps broadcast sent");
        } catch (Exception e) {
            Log.e("MyModule", "Error sending today steps broadcast: " + e.getMessage());
        }
    }
}
