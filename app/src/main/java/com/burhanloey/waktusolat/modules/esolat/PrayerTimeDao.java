package com.burhanloey.waktusolat.modules.esolat;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

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
     * ignored.
     *
     * @param prayerTimes List of prayer times.
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAll(List<PrayerTime> prayerTimes);
}
