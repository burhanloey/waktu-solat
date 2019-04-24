package com.burhanloey.waktusolat.modules.esolat.model;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A model that is used during both ESolat API and database operations for prayer time.
 */
@Entity(tableName = "prayertime",
        indices = {@Index(value = {"districtCode", "date"}, unique = true)})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PrayerTime {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String districtCode;
    private String date;
    private String fajr;
    private String dhuhr;
    private String asr;
    private String maghrib;
    private String isha;
}
