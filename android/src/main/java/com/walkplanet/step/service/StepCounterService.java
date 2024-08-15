package com.walkplanet.step.service;

import android.app.*;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.walkplanet.MainActivity;
import com.walkplanet.step.receiver.MidnightResetReceiver;
import com.walkplanet.R;
import com.walkplanet.notification.service.NotificationService;
import com.walkplanet.step.receiver.RestartServiceReceiver;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class StepCounterService extends Service implements StepCounterManager.StepCounterListener {
    private static final String TAG = "StepCounterService";
    private static final String PREFS_NAME = "StepCounterPrefs";
    private static final String STEPS_KEY = "steps";
    private static final String LAST_RESET_KEY = "last_reset_time";
    private static final int NOTIFICATION_ID = 2;
    private static final String CHANNEL_ID = "step_counter_service";

    private static final String GROUP_KEY_STEP_COUNTER = "com.walkplanet.STEP_COUNTER_GROUP";

    private StepCounterManager stepCounterManager;
//    private Handler handler;
//    private Runnable runnable;
    private static ReactApplicationContext reactContext;

    public static void setReactApplicationContext(ReactApplicationContext context) {
        reactContext = context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "Service onCreate");
        stepCounterManager = new StepCounterManager(this, this);

        setMidnightAlarm();
        // 포그라운드 서비스 시작
        startForegroundService();
        startNotificationService();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "Service onStartCommand");

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACTIVITY_RECOGNITION) == PackageManager.PERMISSION_GRANTED) {
            stepCounterManager.startStepDetector();
        } else {
            Log.e(TAG, "Permission for ACTIVITY_RECOGNITION not granted");
            stopSelf();
        }

        // SharedPreferences에서 걸음 수를 불러와서 서비스가 재시작될 때 유지되도록 함
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        int initialStepCount = prefs.getInt(STEPS_KEY, 0);
        Log.d(TAG, "Initial step count ==onStartCommand==: " + initialStepCount);
        updateNotification(initialStepCount);
        startForegroundService();

        if(intent != null && intent.getAction() != null && intent.getAction().equals("com.walkplanet.ACTION_RESET_STEPS")) {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt(STEPS_KEY, 0);
            editor.apply();
            updateNotification(0);
            Log.d(TAG, "======================== Step count reset to 0 by intent");
        }
        setMidnightAlarm();

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "Service onDestroy");
        stepCounterManager.stopStepDetector();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            stopForeground(Service.STOP_FOREGROUND_DETACH);
        } else {
            stopForeground(false);
        }

        Intent broadcastIntent = new Intent(this, RestartServiceReceiver.class);
        broadcastIntent.setAction("RestartService");
        sendBroadcast(broadcastIntent);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void startForegroundService() {
        try {
            createNotificationChannel();

            Intent notificationIntent = new Intent(this, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(
                    this, 0, notificationIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
            );

            Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setContentTitle("WALK MATE")
                    .setContentText("걸음 수를 측정 중이에요!")
                    .setSmallIcon(R.mipmap.ic_walk)
                    .setContentIntent(pendingIntent)
                    .setOngoing(true)
                    .setGroup(GROUP_KEY_STEP_COUNTER)
                    .build();

            startForeground(NOTIFICATION_ID, notification);
            Log.d(TAG, "Foreground service started");

        } catch (Exception e) {
            Log.e(TAG, "Exception in startForegroundService", e);
        }
    }

    private void startNotificationService() {
        try {
            Intent notificationIntent = new Intent(this, NotificationService.class);
            notificationIntent.setAction(NotificationService.ACTION_START_FOREGROUND);
            ContextCompat.startForegroundService(this, notificationIntent);
        } catch (Exception e) {
            Log.e(TAG, "Exception in startNotificationService", e);
        }
    }

    private void updateNotification(int stepCount) {
        // NotificationService에 알림 업데이트 인텐트
        Intent notificationIntent = new Intent(this, NotificationService.class);
        notificationIntent.setAction(NotificationService.ACTION_UPDATE_STEP_COUNT);
        notificationIntent.putExtra(NotificationService.EXTRA_STEP_COUNT, stepCount);
        startService(notificationIntent);

        // React Native 이벤트 발생
        sendStepCountUpdateToReactNative(stepCount);
    }

    private void sendStepCountUpdateToReactNative(int stepCount) {
        if (reactContext != null && reactContext.hasActiveCatalystInstance()) {
            WritableMap params = Arguments.createMap();
            params.putInt("stepCount", stepCount);
            reactContext
                    .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                    .emit("StepCountUpdated", params);
            Log.d(TAG, "React Native event sent: StepCountUpdated, Steps=" + stepCount);
        } else {
            Log.e(TAG, "ReactApplicationContext is null, cannot send event");
        }
    }

    private void setMidnightAlarm() {
        Intent intent = new Intent(this, MidnightResetReceiver.class);
        intent.setAction("com.walkplanet.ACTION_MIDNIGHT_RESET");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Calendar midnight = Calendar.getInstance();
        midnight.set(Calendar.HOUR_OF_DAY, 0);
        midnight.set(Calendar.MINUTE, 0);
        midnight.set(Calendar.SECOND, 0);
        midnight.set(Calendar.MILLISECOND, 0);
        Log.d(TAG, "AlarmManager is start: " + midnight.getTime());

        if (Calendar.getInstance().after(midnight)) {
            midnight.add(Calendar.DAY_OF_YEAR, 1);
            Log.d(TAG, "Midnight is already passed, setting alarm for next day");
        }

        if (alarmManager != null) {
            long alarmTime = midnight.getTimeInMillis();
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, alarmTime, pendingIntent);
            Log.d(TAG, "Midnight alarm set for step reset");
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.getDefault());
            String formattedDate = dateFormat.format(new Date(alarmTime));
            Log.d(TAG, "Midnight alarm set for: " + formattedDate);
        }
        Log.d(TAG, "AlarmManager initialized: " + (alarmManager != null));
        Log.d(TAG, "Current time: " + Calendar.getInstance().getTime());
    }

    @Override
    public void onStepCountUpdated(int stepCount) {
        Log.d(TAG, "Step count updated: " + stepCount);
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(STEPS_KEY, stepCount);
        editor.apply();
        updateNotification(stepCount);
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "Step Counter Service",
                    NotificationManager.IMPORTANCE_LOW
            );
            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            manager.createNotificationChannel(channel);
        }
    }
}
