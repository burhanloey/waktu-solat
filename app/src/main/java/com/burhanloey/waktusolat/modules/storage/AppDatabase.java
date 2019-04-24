package com.burhanloey.waktusolat.modules.storage;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.burhanloey.waktusolat.modules.esolat.PrayerTimeDao;
import com.burhanloey.waktusolat.modules.esolat.model.PrayerTime;

@Database(entities = {PrayerTime.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract PrayerTimeDao prayerTimeDao();
}
