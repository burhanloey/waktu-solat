package com.burhanloey.waktusolat.modules.esolat;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.burhanloey.waktusolat.modules.esolat.model.PrayerTime;

import java.util.ArrayList;
import java.util.List;

@Dao
public abstract class PrayerTimeDao {
    /**
     * Find prayer time given the date and district code.
     *
     * @param districtCode District code. Refer ESolat for reference.
     * @param dates A list of Date. Refer TimeFormatModule for reference on the format.
     * @return A list of Prayer time
     */
    @Query("SELECT * FROM prayertime WHERE districtCode = :districtCode AND date IN (:dates)")
    public abstract List<PrayerTime> find(String districtCode, List<String> dates);

    /**
     * Insert a list of prayer times into the database. If the data already exists, they will be
     * ignored.
     *
     * @param prayerTimes Array of prayer times.
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    protected abstract long[] insertAll(PrayerTime... prayerTimes);

    /**
     * Update a list of prayer times.
     *
     * @param prayerTimes List of prayer imtes.
     */
    @Update(onConflict = OnConflictStrategy.IGNORE)
    protected abstract void updateAll(List<PrayerTime> prayerTimes);

    /**
     * Update or insert prayer times in the database.
     *
     * @param prayerTimes Array of prayer times
     */
    @Transaction
    public void upsertAll(PrayerTime... prayerTimes) {
        long[] insertResult = insertAll(prayerTimes);
        List<PrayerTime> updateList = new ArrayList<>();

        for (int i = 0; i < insertResult.length; i++) {
            if (insertResult[i] == -1) {
                updateList.add(prayerTimes[i]);
            }
        }

        if (!updateList.isEmpty()) {
            updateAll(updateList);
        }
    }
}
