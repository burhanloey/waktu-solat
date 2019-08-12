package com.burhanloey.waktusolat.modules.timeformat;

import com.burhanloey.waktusolat.modules.alarm.AlarmInfo;
import com.burhanloey.waktusolat.modules.esolat.model.PrayerTime;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * A service to format time and date.
 */
public class TimeFormatter {
    private final DateFormat yearFormat;
    private final DateFormat dateKeyFormat;
    private final DateFormat augustDateKeyFormat;
    private final DateFormat dateDisplayFormat;
    private final DateFormat timeFromFormat;
    private final DateFormat timeToFormat;

    public TimeFormatter(DateFormat yearFormat,
                         DateFormat dateKeyFormat,
                         DateFormat augustDateKeyFormat,
                         DateFormat dateDisplayFormat,
                         DateFormat timeFromFormat,
                         DateFormat timeToFormat) {
        this.yearFormat = yearFormat;
        this.dateKeyFormat = dateKeyFormat;
        this.augustDateKeyFormat = augustDateKeyFormat;
        this.dateDisplayFormat = dateDisplayFormat;
        this.timeFromFormat = timeFromFormat;
        this.timeToFormat = timeToFormat;
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
     * Get current year.
     *
     * @return Current year
     */
    public String currentYear() {
        return yearFormat.format(new Date());
    }

    /**
     * Get today's day and date for display.
     *
     * @return Day and date for display
     */
    public String todayDisplay() {
        return dateDisplayFormat.format(new Date());
    }

    /**
     * Get today's date formatted to match the date from ESolat API.
     *
     * @return Today's date
     */
    public String today() {
        Calendar calendar = Calendar.getInstance();

        // The data from E-solat portal use different format for August dates which is 'Ogos'
        // instead of the more consistent 'Ogo'.
        if (calendar.get(Calendar.MONTH) == Calendar.AUGUST) {
            return augustDateKeyFormat.format(calendar.getTime());
        } else {
            return dateKeyFormat.format(calendar.getTime());
        }
    }

    /**
     * Get tomorrow's date formatted to match the date from ESolat API.
     *
     * @return Tomorrow's date
     */
    public String tomorrow() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, 1);

        // The data from E-solat portal use different format for August dates which is 'Ogos'
        // instead of the more consistent 'Ogo'.
        if (calendar.get(Calendar.MONTH) == Calendar.AUGUST) {
            return augustDateKeyFormat.format(calendar.getTime());
        } else {
            return dateKeyFormat.format(calendar.getTime());
        }
    }

    /**
     * Calculate alarm info from a list of prayer times (means can include two days of prayer
     * times, today and tomorrow).
     *
     * @param prayerTimes A list of prayer times
     * @return A list of AlarmInfo
     */
    public List<AlarmInfo> calculateAlarmInfos(List<PrayerTime> prayerTimes) {
        List<AlarmInfo> alarmInfos = new ArrayList<>(10);

        for (PrayerTime prayerTime : prayerTimes) {
            String date = prayerTime.getDate();

            alarmInfos.add(toAlarmInfo("Subuh", date, prayerTime.getFajr()));
            alarmInfos.add(toAlarmInfo("Zuhur", date, prayerTime.getDhuhr()));
            alarmInfos.add(toAlarmInfo("Asar", date, prayerTime.getAsr()));
            alarmInfos.add(toAlarmInfo("Maghrib", date, prayerTime.getMaghrib()));
            alarmInfos.add(toAlarmInfo("Isha'", date, prayerTime.getIsha()));
        }

        return alarmInfos;
    }



    private AlarmInfo toAlarmInfo(String title, String date, String time) {
        return new AlarmInfo(title, formatTime(time), toMillis(date, time));
    }

    /**
     * Format time from military to AM/PM.
     */
    private String formatTime(String from) {
        try {
            Date date = timeFromFormat.parse(from);
            return timeToFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return from;
    }

    /**
     * Holy shit! This is horrible. No wonder people said to just use JODA-Time library.
     */
    private long toMillis(String date, String time) {
        try {
            Date dateInfo = dateKeyFormat.parse(date);
            Date timeInfo = timeFromFormat.parse(time);

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(dateInfo);

            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

            calendar.setTime(timeInfo);

            int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);

            return new GregorianCalendar(year, month, dayOfMonth, hourOfDay, minute)
                    .getTime()  // to Date
                    .getTime(); // to long
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
