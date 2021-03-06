package com.burhanloey.waktusolat.modules.alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.burhanloey.waktusolat.components.AlarmReceiver;
import com.burhanloey.waktusolat.modules.esolat.ESolat;
import com.burhanloey.waktusolat.modules.esolat.ESolatManager;
import com.burhanloey.waktusolat.modules.esolat.model.PrayerTime;
import com.burhanloey.waktusolat.modules.esolat.tasks.LoadCallback;
import com.burhanloey.waktusolat.modules.state.StateManager;
import com.burhanloey.waktusolat.modules.timeformat.TimeFormatter;

import java.util.List;

public class PrayerAlarmManager {
    private final ESolatManager eSolatManager;
    private final StateManager stateManager;
    private final TimeFormatter timeFormatter;
    private final Context context;

    public PrayerAlarmManager(ESolatManager eSolatManager,
                              StateManager stateManager,
                              TimeFormatter timeFormatter,
                              Context context) {
        this.eSolatManager = eSolatManager;
        this.stateManager = stateManager;
        this.timeFormatter = timeFormatter;
        this.context = context;
    }

    /**
     * Find next alarm from the list.
     */
    private AlarmInfo nextAlarm(List<PrayerTime> prayerTimes) {
        List<AlarmInfo> alarmInfos = timeFormatter.calculateAlarmInfos(prayerTimes);
        long now = System.currentTimeMillis();

        for (AlarmInfo alarmInfo : alarmInfos) {
            if (alarmInfo.getTime() > now) {
                return alarmInfo;
            }
        }

        return null;
    }

    /**
     * Set up alarm in AlarmManager.
     */
    private void setupAlarm(List<PrayerTime> prayerTimes) {
        AlarmInfo alarmInfo = nextAlarm(prayerTimes);
        if (alarmInfo == null) {
            Toast.makeText(context, "Waktu Solat: Alarm not set because data not available. " +
                    "Wait until tomorrow to download next year data.", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra("title", alarmInfo.getTitle());
        intent.putExtra("text", alarmInfo.getText());

        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (alarmManager != null) {
            alarmManager.set(AlarmManager.RTC_WAKEUP, alarmInfo.getTime(), pendingIntent);
        }
    }


    /**
     * Set next alarm according to current selection of district.
     */
    public void setNextAlarm() {
        int statePosition = stateManager.getStatePosition();
        int districtPosition = stateManager.getDistrictPosition();
        String districtCode = ESolat.getDistrictCode(statePosition, districtPosition);

        eSolatManager.load(districtCode, new LoadCallback() {
            @Override
            public void onResponse(List<PrayerTime> prayerTimes) {
                setupAlarm(prayerTimes);
            }

            @Override
            public void onMissingData() {
                Toast.makeText(context, "Alarm not set because data not available.",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Cancel any prayer alarm registered to phone by this app.
     */
    public void cancelAlarm() {
        Intent intent = new Intent(context, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent);
        }
    }
}
