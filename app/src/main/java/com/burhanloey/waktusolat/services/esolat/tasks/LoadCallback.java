package com.burhanloey.waktusolat.services.esolat.tasks;

import com.burhanloey.waktusolat.services.esolat.model.PrayerTime;

public interface LoadCallback {
    void onResponse(PrayerTime prayerTime);
    void onMissingData();
}
