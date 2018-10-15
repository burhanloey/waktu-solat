package com.burhanloey.waktusolat.activities;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.JobIntentService;

import com.burhanloey.waktusolat.services.alarm.PrayerAlarmManager;
import com.burhanloey.waktusolat.services.state.StateManager;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

public class NextAlarmService extends JobIntentService {
    private static final int JOB_ID = 0;

    @Inject
    StateManager stateManager;

    @Inject
    PrayerAlarmManager prayerAlarmManager;

    public static void enqueueWork(Context context, Intent intent) {
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
