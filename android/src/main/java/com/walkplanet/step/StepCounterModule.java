package com.walkplanet.step;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import androidx.annotation.NonNull;
import com.facebook.react.bridge.*;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.walkplanet.step.service.StepCounterManager;
import com.walkplanet.step.service.StepCounterService;

public class StepCounterModule extends ReactContextBaseJavaModule implements StepCounterManager.StepCounterListener {
    private static ReactApplicationContext reactContext;
    private StepCounterManager stepCounterManager;

    StepCounterModule(ReactApplicationContext context) {
        super(context);
        reactContext = context;
        stepCounterManager = new StepCounterManager(context, this);
        StepCounterService.setReactApplicationContext(reactContext); // Add this line
    }

    @NonNull
    @Override
    public String getName() {
        return "StepCounter";
    }

    @ReactMethod
    public void startStepCounter(Promise promise) {
        if (reactContext.checkSelfPermission(android.Manifest.permission.ACTIVITY_RECOGNITION) != PackageManager.PERMISSION_GRANTED) {
            promise.reject("PERMISSION_DENIED", "Activity Recognition permission denied");
            return;
        }
        Intent serviceIntent = new Intent(reactContext, StepCounterService.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            reactContext.startForegroundService(serviceIntent);
        } else {
            reactContext.startService(serviceIntent);
        }
        promise.resolve(null);
    }

    @ReactMethod
    public void stopStepCounter(Promise promise) {
        Intent serviceIntent = new Intent(reactContext, StepCounterService.class);
        reactContext.stopService(serviceIntent);
        promise.resolve(null);
    }

    @Override
    public void onStepCountUpdated(int stepCount) {
        WritableMap params = Arguments.createMap();
        params.putInt("stepCount", stepCount);
        reactContext
                .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                .emit("StepCountUpdated", params);
    }

    @ReactMethod
    public void addListener(String eventName) {
        // Keep: Required for RN built-in Event Emitter Calls.
    }

    @ReactMethod
    public void removeListeners(Integer count) {
        // Keep: Required for RN built-in Event Emitter Calls.
    }
}
