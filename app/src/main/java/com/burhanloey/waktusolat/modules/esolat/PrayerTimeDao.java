package com.burhanloey.waktusolat.modules.esolat;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.burhanloey.waktusolat.modules.esolat.model.PrayerTime;

import java.util.List;

@Dao
public interface PrayerTimeDao {
    /**
     * Find prayer time given the date and district code.
     *
     * @param districtCode District code. Refer ESolat for reference.
     * @param dates A list of Date. Refer TimeFormatModule for reference on the format.
     * @return A list of Prayer time
     */
    @Query("SELECT * FROM prayertime WHERE districtCode = :districtCode AND date IN (:dates)")
    List<PrayerTime> find(String districtCode, List<String> dates);

    /**
     * Insert a list of prayer times into the database. If the data already exists, they will be
     * replaced.
     *
     * @param prayerTimes List of prayer times.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<PrayerTime> prayerTimes);
}
