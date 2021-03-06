package com.burhanloey.waktusolat.modules.esolat.tasks;

import com.burhanloey.waktusolat.modules.esolat.PrayerTimeDao;
import com.burhanloey.waktusolat.modules.esolat.model.PrayerTime;
import com.burhanloey.waktusolat.modules.timeformat.TimeFormatter;

public class TaskManager {
    private final PrayerTimeDao prayerTimeDao;
    private final TimeFormatter timeFormatter;

    public TaskManager(PrayerTimeDao prayerTimeDao, TimeFormatter timeFormatter) {
        this.prayerTimeDao = prayerTimeDao;
        this.timeFormatter = timeFormatter;
    }

    public void savePrayerTimes(PrayerTime[] prayerTimes, FetchCallback callback) {
        callback.onSavingData();

        new SavePrayerTimesTask(prayerTimeDao, callback)
                .execute(prayerTimes);
    }

    public void loadPrayerTimes(String districtCode, LoadCallback callback) {
        new LoadPrayerTimesTask(prayerTimeDao, timeFormatter, callback)
                .execute(districtCode);
    }
}
