package com.burhanloey.waktusolat.services;

import java.util.List;

import lombok.Data;

@Data
public class YearlyPrayerTimes {
    private final List<PrayerTime> prayerTime;
}
