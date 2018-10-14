package com.burhanloey.waktusolat.services.timeformat;

import com.burhanloey.waktusolat.services.esolat.model.PrayerTime;

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
    private final DateFormat dateFormat;
    private final DateFormat fromDateFormat;
    private final DateFormat toDateFormat;

    public TimeFormatter(DateFormat dateFormat,
                         DateFormat fromDateFormat,
                         DateFormat toDateFormat) {
        this.dateFormat = dateFormat;
        this.fromDateFormat = fromDateFormat;
        this.toDateFormat = toDateFormat;
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

    /**
     * Get tomorrow's date formatted to match the date from ESolat API.
     *
     * @return Tomorrow's date
     */
    public String tomorrow() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, 1);
        Date tomorrow = calendar.getTime();
        return dateFormat.format(tomorrow);
    }

    /**
     * Calculate alarm info from a list of prayer times (means can include two days of prayer
     * times, today and tomorrow).
     *
     * @param prayerTimes A list of prayer times
     * @return A list of AlarmInfo
     */
    public List<AlarmInfo> calculateAlarmInfos(PrayerTime... prayerTimes) {
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
            Date date = fromDateFormat.parse(from);
            return toDateFormat.format(date);
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
            Date dateInfo = dateFormat.parse(date);
            Date timeInfo = fromDateFormat.parse(time);

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
