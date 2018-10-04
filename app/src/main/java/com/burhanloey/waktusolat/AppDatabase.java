package com.burhanloey.waktusolat;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.burhanloey.waktusolat.services.esolat.PrayerTime;
import com.burhanloey.waktusolat.services.esolat.PrayerTimeDao;

@Database(entities = {PrayerTime.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract PrayerTimeDao prayerTimeDao();
}
