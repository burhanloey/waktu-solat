package com.burhanloey.waktusolat.services.timeformat;

import com.burhanloey.waktusolat.services.esolat.model.PrayerTime;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A service to format time and date.
 */
public class TimeFormatService {
    private final DateFormat dateFormat;
    private final DateFormat fromDateFormat;
    private final DateFormat toDateFormat;

    public TimeFormatService(DateFormat dateFormat,
                             DateFormat fromDateFormat,
                             DateFormat toDateFormat) {
        this.dateFormat = dateFormat;
        this.fromDateFormat = fromDateFormat;
        this.toDateFormat = toDateFormat;
    }

    /**
     * Format time from military to AM/PM.
     */
    private String formatTime(String from) {
        try {
            Date date = fromDateFormat.parse(from);
            return toDateFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return from;
    }

    /**
     * Format Subuh, Zuhur, Asar, Maghrib, and Isha' prayer times. This returns List to match the
     * Collection used to group the TextViews by ButterKnife.
     *
     * @param prayerTime Raw PrayerTime data in military
     * @return List of AM/PM prayer time strings
     */
    public List<String> formatPrayerTime(PrayerTime prayerTime) {
        List<String> formatted = new ArrayList<>(5);
        formatted.add(formatTime(prayerTime.getFajr()));
        formatted.add(formatTime(prayerTime.getDhuhr()));
        formatted.add(formatTime(prayerTime.getAsr()));
        formatted.add(formatTime(prayerTime.getMaghrib()));
        formatted.add(formatTime(prayerTime.getIsha()));

        return formatted;
    }

    /**
     * Get today's date formatted to match the date from ESolat API.
     *
     * @return Today's date
     */
    public String today() {
        return dateFormat.format(new Date());
    }
}
