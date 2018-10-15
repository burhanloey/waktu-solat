package com.burhanloey.waktusolat.activities;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.burhanloey.waktusolat.R;

import dagger.android.DaggerBroadcastReceiver;

public class AlarmReceiver extends DaggerBroadcastReceiver {
    private static final String CHANNEL_ID = "CALL_FOR_PRAYER";

    /**
     * Notification channel is required for Android Oreo and above.
     */
    private void createNotificationChannel(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelName = "Waktu Solat";
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, channelName,
                    NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("Alarm untuk waktu solat.");

            NotificationManager manager = context.getSystemService(NotificationManager.class);
            if (manager != null) {
                manager.createNotificationChannel(channel);
            }
        }
    }

    /**
     * Creating notification for prayer.
     */
    private void callForPrayer(Context context, String title, String text) {
        Notification notification = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(text)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .build();

        createNotificationChannel(context);

        NotificationManagerCompat manager = NotificationManagerCompat.from(context);
        manager.notify(0, notification);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        String title = intent.getStringExtra("title");
        String text = intent.getStringExtra("text");
        callForPrayer(context, title, text);

        Intent nextAlarmIntent = new Intent(context, NextAlarmService.class);
        NextAlarmService.enqueueWork(context, nextAlarmIntent);
    }
}
