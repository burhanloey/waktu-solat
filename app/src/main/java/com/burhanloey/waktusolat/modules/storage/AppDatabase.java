package com.burhanloey.waktusolat.modules.storage;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.burhanloey.waktusolat.modules.esolat.model.PrayerTime;
import com.burhanloey.waktusolat.modules.esolat.PrayerTimeDao;

@Database(entities = {PrayerTime.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract PrayerTimeDao prayerTimeDao();
}
