package com.burhanloey.waktusolat.services.esolat;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.burhanloey.waktusolat.services.esolat.model.PrayerTime;

import java.util.List;

@Dao
public interface PrayerTimeDao {
    @Query("SELECT * FROM prayertime WHERE date = :date AND districtCode = :districtCode")
    PrayerTime find(String date, String districtCode);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAll(List<PrayerTime> prayerTimes);
}
