package com.burhanloey.waktusolat.activities;

import android.content.Intent;

import com.burhanloey.waktusolat.services.alarm.PrayerAlarmManager;
import com.burhanloey.waktusolat.services.state.StateManager;

import javax.inject.Inject;

import dagger.android.DaggerIntentService;

public class NextAlarmService extends DaggerIntentService {
    @Inject
    StateManager stateManager;

    @Inject
    PrayerAlarmManager prayerAlarmManager;

    public NextAlarmService() {
        super("com.burhanloey.waktusolat.activities.NextAlarmService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        boolean isNotificationsEnabled = stateManager.getNotificationsEnabled();

        if (isNotificationsEnabled) {
            prayerAlarmManager.setNextAlarm();
        }
    }
}
