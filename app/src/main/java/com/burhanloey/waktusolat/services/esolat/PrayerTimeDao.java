package com.burhanloey.waktusolat.services.esolat;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.burhanloey.waktusolat.services.esolat.model.PrayerTime;

import java.util.List;

@Dao
public interface PrayerTimeDao {
    /**
     * Find prayer time given the date and district code.
     *
     * @param date Date. Refer TimeFormatModule for reference on the format.
     * @param districtCode District code. Refer ESolat for reference.
     * @return Prayer time
     */
    @Query("SELECT * FROM prayertime WHERE date = :date AND districtCode = :districtCode")
    PrayerTime find(String date, String districtCode);

    /**
     * Insert a list of prayer times into the database. If the data already exists, they will be
     * ignored.
     *
     * @param prayerTimes List of prayer times.
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAll(List<PrayerTime> prayerTimes);
}
