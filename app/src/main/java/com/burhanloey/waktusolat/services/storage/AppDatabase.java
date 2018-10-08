package com.burhanloey.waktusolat.services.storage;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.burhanloey.waktusolat.services.esolat.model.PrayerTime;
import com.burhanloey.waktusolat.services.esolat.PrayerTimeDao;

@Database(entities = {PrayerTime.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract PrayerTimeDao prayerTimeDao();
}
