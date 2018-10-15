package com.burhanloey.waktusolat.services.alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.burhanloey.waktusolat.activities.AlarmReceiver;
import com.burhanloey.waktusolat.services.esolat.ESolatManager;
import com.burhanloey.waktusolat.services.esolat.model.PrayerTime;
import com.burhanloey.waktusolat.services.esolat.tasks.LoadCallback;
import com.burhanloey.waktusolat.services.timeformat.TimeFormatter;

import java.util.List;

public class PrayerAlarmManager {
    private final ESolatManager eSolatManager;
    private final TimeFormatter timeFormatter;
    private final Context context;

    public PrayerAlarmManager(ESolatManager eSolatManager,
                              TimeFormatter timeFormatter,
                              Context context) {
        this.eSolatManager = eSolatManager;
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

        return alarmInfos.get(0);
    }

    /**
     * Set up alarm in AlarmManager.
     */
    private void set(List<PrayerTime> prayerTimes) {
        String districtCode = prayerTimes.get(0).getDistrictCode();
        AlarmInfo alarmInfo = nextAlarm(prayerTimes);

        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra("districtCode", districtCode);
        intent.putExtra("title", alarmInfo.getTitle());
        intent.putExtra("text", alarmInfo.getText());

        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (alarmManager != null) {
            alarmManager.set(AlarmManager.RTC_WAKEUP, alarmInfo.getTime(), pendingIntent);
        }
    }



    public void setAlarm(String districtCode) {
        eSolatManager.load(districtCode, new LoadCallback() {
            @Override
            public void onResponse(List<PrayerTime> prayerTimes) {
                set(prayerTimes);
            }

            @Override
            public void onMissingData() {
                Toast.makeText(context, "Alarm not set because data not available.",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}
