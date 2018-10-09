package com.burhanloey.waktusolat.services.esolat.model;

import java.util.List;

import lombok.Data;

/**
 * A container class to wrap a list of PrayerTime due to how ESolat API data is structured.
 */
@Data
public class YearlyPrayerTimes {
    private final List<PrayerTime> prayerTime;
}
