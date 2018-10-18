package com.burhanloey.waktusolat.components;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.JobIntentService;

import com.burhanloey.waktusolat.modules.alarm.PrayerAlarmManager;
import com.burhanloey.waktusolat.modules.state.StateManager;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

/**
 * Using JobIntentService instead of DaggerIntentService because from Android Oreo and above, you
 * need to use JobScheduler to run background Service. JobIntentService will use either Service or
 * JobScheduler on the right version.
 */
public class NextAlarmService extends JobIntentService {
    private static final int JOB_ID = 0;

    @Inject
    StateManager stateManager;

    @Inject
    PrayerAlarmManager prayerAlarmManager;

    /**
     * Enqueue this Service/JobSchedule.
     *
     * @param context Application context
     */
    public static void enqueueWork(Context context) {
        Intent intent = new Intent(context, NextAlarmService.class);
        enqueueWork(context, NextAlarmService.class, JOB_ID, intent);
    }

    @Override
    public void onCreate() {
        AndroidInjection.inject(this);
        super.onCreate();
    }

    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        boolean isNotificationsEnabled = stateManager.getNotificationsEnabled();

        if (isNotificationsEnabled) {
            prayerAlarmManager.setNextAlarm();
        }
    }
}
