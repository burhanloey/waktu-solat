package com.burhanloey.waktusolat.services.esolat.tasks;

import com.burhanloey.waktusolat.services.esolat.model.PrayerTime;

/**
 * Callbacks during prayer time data loading from database.
 */
public interface LoadCallback {
    void onResponse(PrayerTime prayerTime);
    void onMissingData();
}
