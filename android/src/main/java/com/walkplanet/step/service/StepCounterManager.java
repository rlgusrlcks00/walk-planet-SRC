package com.walkplanet.step.service;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;

public class StepCounterManager implements SensorEventListener {
    private SensorManager sensorManager;
    private Sensor stepDetectorSensor;
    private int stepCount = 0;
    private Context context;
    private StepCounterListener listener;

    private static final String PREFS_NAME = "StepCounterPrefs";
    private static final String STEPS_KEY = "steps";

    public interface StepCounterListener {
        void onStepCountUpdated(int stepCount);
    }

    public StepCounterManager(Context context, StepCounterListener listener) {
        this.context = context;
        this.listener = listener;
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        stepDetectorSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
        Log.d("StepCounterManager", "StepDetectorSensor : " + stepDetectorSensor);
        // Load the saved step count from SharedPreferences
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        stepCount = prefs.getInt(STEPS_KEY, 0);
    }

    public void startStepDetector() {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACTIVITY_RECOGNITION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((AppCompatActivity) context, new String[]{Manifest.permission.ACTIVITY_RECOGNITION}, 1);
        } else {
            if (stepDetectorSensor != null) {
                sensorManager.registerListener(this, stepDetectorSensor, SensorManager.SENSOR_DELAY_FASTEST);
            }
        }
    }

    public void stopStepDetector() {
        sensorManager.unregisterListener(this);
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        stepCount = prefs.getInt(STEPS_KEY, 0);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(STEPS_KEY, stepCount);
        editor.apply();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_STEP_DETECTOR) {
            Log.d("StepCounterManager", "Step detected : " + stepCount);
            SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
            stepCount = prefs.getInt(STEPS_KEY, 0);
            stepCount++;
            if (listener != null) {
                listener.onStepCountUpdated(stepCount);
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}
