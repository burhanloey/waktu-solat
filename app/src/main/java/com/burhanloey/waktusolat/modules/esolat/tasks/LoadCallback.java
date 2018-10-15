package com.burhanloey.waktusolat.modules.esolat.tasks;

import com.burhanloey.waktusolat.modules.esolat.model.PrayerTime;

import java.util.List;

/**
 * Callbacks during prayer time data loading from database. The callbacks will run on UI thread.
 */
public interface LoadCallback {
    void onResponse(List<PrayerTime> prayerTimes);
    void onMissingData();
}
