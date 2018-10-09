package com.burhanloey.waktusolat.services.esolat.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import lombok.Data;

/**
 * A model that is used during both ESolat API and database operations for prayer time.
 */
@Entity(tableName = "prayertime",
        indices = {@Index(value = {"districtCode", "date"}, unique = true)})
@Data
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

    @Ignore
    private String hijri;

    @Ignore
    private String day;

    @Ignore
    private String imsak;

    @Ignore
    private String syuruk;
}
