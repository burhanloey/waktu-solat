package com.burhanloey.waktusolat.services.esolat.tasks;

import com.burhanloey.waktusolat.services.esolat.model.PrayerTime;

import java.util.List;

/**
 * Callbacks during prayer time data loading from database. The callbacks will run on UI thread.
 */
public interface LoadCallback {
    void onResponse(List<PrayerTime> prayerTime);
    void onMissingData();
}
