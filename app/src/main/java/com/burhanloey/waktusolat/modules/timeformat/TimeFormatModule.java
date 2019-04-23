package com.burhanloey.waktusolat.modules.timeformat;

import android.annotation.SuppressLint;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class TimeFormatModule {
    @Provides
    @Named("ms-my")
    Locale provideLocale() {
        return new Locale("ms", "my");
    }

    @Provides
    @Named("year")
    DateFormat provideYearFormat(@Named("ms-my") Locale locale) {
        return new SimpleDateFormat("yyyy", locale);
    }

    @Provides
    @Named("date-key")
    DateFormat provideDateFormatforDateKey(@Named("ms-my") Locale locale) {
        return new SimpleDateFormat("dd-MMM-yyyy", locale);
    }

    @Provides
    @Named("date-display")
    DateFormat provideDateFormatforDayDisplay(@Named("ms-my") Locale locale) {
        return new SimpleDateFormat("EEEE, dd MMM yyyy", locale);
    }

    @SuppressLint("SimpleDateFormat")
    @Provides
    @Named("time-from")
    DateFormat provideDateFormatforFromTime() {
        return new SimpleDateFormat("HH:mm:ss");
    }

    @SuppressLint("SimpleDateFormat")
    @Provides
    @Named("time-to")
    DateFormat provideDateFormatforToTime() {
        return new SimpleDateFormat("hh:mm aaa");
    }

    @Provides
    TimeFormatter provideTimeFormatter(@Named("year") DateFormat yearFormat,
                                       @Named("date-key") DateFormat dateKeyFormat,
                                       @Named("date-display") DateFormat dateDisplayFormat,
                                       @Named("time-from") DateFormat timeFromFormat,
                                       @Named("time-to") DateFormat timeToFormat) {
        return new TimeFormatter(
                yearFormat, dateKeyFormat, dateDisplayFormat, timeFromFormat, timeToFormat);
    }
}
