package com.walkplanet.notification.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import com.walkplanet.MainActivity;
import com.walkplanet.R;

public class NotificationService extends Service {
    private static final String TAG = "NotificationService";
    private static final String CHANNEL_ID = "step_counter_service";
    private static final int NOTIFICATION_ID = 1;
    public static final String ACTION_START_FOREGROUND = "com.walkplanet.notification.START_FOREGROUND";
    public static final String ACTION_UPDATE_STEP_COUNT = "com.walkplanet.notification.UPDATE_STEP_COUNT";
    public static final String EXTRA_STEP_COUNT = "extra_step_count";

    private static final String GROUP_KEY_STEP_COUNTER = "com.walkplanet.STEP_COUNTER_GROUP";


    private NotificationManager notificationManager;
    private static final String PREFS_NAME = "StepCounterPrefs";
    private static final String STEPS_KEY = "steps";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "NotificationService onCreate");
        startForegroundService();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "NotificationService onStartCommand");

        if (intent != null && ACTION_UPDATE_STEP_COUNT.equals(intent.getAction())) {
            int stepCount = intent.getIntExtra(EXTRA_STEP_COUNT, 0);
            updateNotification(stepCount);
        } else if (intent != null && ACTION_START_FOREGROUND.equals(intent.getAction())) {
            // SharedPreferences에서 걸음 수를 불러와서 초기 알림을 설정
            SharedPreferences prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
            int stepCount = prefs.getInt(STEPS_KEY, 0);
            updateNotification(stepCount);
        } else if (intent != null && "com.walkplanet.ACTION_RESET_STEPS".equals(intent.getAction())) {
            SharedPreferences prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt(STEPS_KEY, 0);
            editor.apply();
            updateNotification(0);
        }

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "NotificationService onDestroy");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void startForegroundService() {
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        createNotificationChannel();

        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        int stepCount = prefs.getInt(STEPS_KEY, 0);

        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                this, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("오늘 걸음 수")
                .setContentText(stepCount + " 걸음")
                .setSmallIcon(R.mipmap.ic_walk)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_walk_mate))
                .setContentIntent(pendingIntent)
                .setOngoing(true)
                .setGroup(GROUP_KEY_STEP_COUNTER)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("오늘의 걸음 수는 " + stepCount + " 걸음입니다. 계속 걸어서 더 많은 걸음을 기록해보세요!"))
                .build();

        startForeground(NOTIFICATION_ID, notification);
        Log.d(TAG, "Foreground service started with steps: " + stepCount);

        // Add a summary notification
        Notification summaryNotification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Step Counter Summary")
                .setContentText("You have new step count updates.")
                .setSmallIcon(R.mipmap.ic_walk)
                .setStyle(new NotificationCompat.InboxStyle()
                        .addLine("Step count: " + stepCount))
                .setGroup(GROUP_KEY_STEP_COUNTER)
                .setContentIntent(pendingIntent)
                .setOngoing(true)
                .setGroupSummary(true)
                .build();

        notificationManager.notify(0, summaryNotification);
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "Step Counter Service",
                    NotificationManager.IMPORTANCE_LOW
            );
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void updateNotification(int stepCount) {
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                this, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("오늘 걸음 수 : ")
                .setContentText(stepCount + " 걸음")
                .setSmallIcon(R.mipmap.ic_walk)
                .setContentIntent(pendingIntent)
                .setOngoing(true)
                .setGroup(GROUP_KEY_STEP_COUNTER)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("오늘의 걸음 수는 " + stepCount + " 걸음입니다. 계속 걸어서 더 많은 걸음을 기록해보세요!"))
                .build();

        notificationManager.notify(NOTIFICATION_ID, notification);
        Log.d(TAG, "Notification updated: " + stepCount);
    }

    private PendingIntent getPendingIntentForReset() {
        Intent resetIntent = new Intent(this, MainActivity.class);
        resetIntent.setAction("com.walkplanet.ACTION_RESET_STEPS");
        return PendingIntent.getActivity(
                this, 0, resetIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );
    }
}
