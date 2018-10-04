package com.burhanloey.waktusolat.services.esolat;

import lombok.Data;

@Data
public class PrayerTime {
    private final String hijri;
    private final String date;
    private final String day;
    private final String imsak;
    private final String fajr;
    private final String syuruk;
    private final String dhuhr;
    private final String asr;
    private final String maghrib;
    private final String isha;
}
