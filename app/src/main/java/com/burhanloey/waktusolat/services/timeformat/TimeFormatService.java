package com.burhanloey.waktusolat.services.timeformat;

import com.burhanloey.waktusolat.services.esolat.model.PrayerTime;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    private String formatTime(String from) {
        try {
            Date date = fromDateFormat.parse(from);
            return toDateFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return from;
    }

    public List<String> formatPrayerTime(PrayerTime prayerTime) {
        List<String> formatted = new ArrayList<>(5);
        formatted.add(formatTime(prayerTime.getFajr()));
        formatted.add(formatTime(prayerTime.getDhuhr()));
        formatted.add(formatTime(prayerTime.getAsr()));
        formatted.add(formatTime(prayerTime.getMaghrib()));
        formatted.add(formatTime(prayerTime.getIsha()));

        return formatted;
    }

    public String today() {
        return dateFormat.format(new Date());
    }
}
