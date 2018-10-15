package com.burhanloey.waktusolat.components;

import android.content.Context;
import android.content.Intent;

import dagger.android.DaggerBroadcastReceiver;

public class BootReceiver extends DaggerBroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        if ("android.intent.action.BOOT_COMPLETED".equals(intent.getAction())) {
            Intent nextAlarmIntent = new Intent(context, NextAlarmService.class);
            NextAlarmService.enqueueWork(context, nextAlarmIntent);
        }
    }
}
