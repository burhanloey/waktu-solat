package com.burhanloey.waktusolat.services.esolat.tasks;

import com.burhanloey.waktusolat.services.esolat.model.PrayerTime;

/**
 * Callbacks during prayer time data loading from database. The callbacks will run on UI thread.
 */
public interface LoadCallback {
    void onResponse(PrayerTime prayerTime);
    void onMissingData();
}
