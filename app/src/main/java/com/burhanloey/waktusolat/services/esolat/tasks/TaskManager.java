package com.burhanloey.waktusolat.services.esolat.tasks;

import com.burhanloey.waktusolat.services.esolat.PrayerTimeDao;
import com.burhanloey.waktusolat.services.esolat.model.YearlyPrayerTimes;
import com.burhanloey.waktusolat.services.timeformat.TimeFormatter;

public class TaskManager {
    private final PrayerTimeDao prayerTimeDao;
    private final TimeFormatter timeFormatter;

    public TaskManager(PrayerTimeDao prayerTimeDao, TimeFormatter timeFormatter) {
        this.prayerTimeDao = prayerTimeDao;
        this.timeFormatter = timeFormatter;
    }

    public void savePrayerTimes(YearlyPrayerTimes yearlyPrayerTimes, FetchCallback callback) {
        new SavePrayerTimesTask(prayerTimeDao, callback)
                .execute(yearlyPrayerTimes);
    }

    public void loadPrayerTimes(String districtCode, LoadCallback callback) {
        new LoadPrayerTimesTask(prayerTimeDao, timeFormatter, callback)
                .execute(districtCode);
    }
}
