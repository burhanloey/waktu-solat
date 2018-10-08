package com.burhanloey.waktusolat.services.timeformat;

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
    @Named("date")
    DateFormat provideDateFormatforDate(@Named("ms-my") Locale locale) {
        return new SimpleDateFormat("dd-MMM-yyyy", locale);
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
    TimeFormatService provideTimeFormatService(@Named("date") DateFormat dateFormat,
                                               @Named("time-from") DateFormat fromDateFormat,
                                               @Named("time-to") DateFormat toDateFormat) {
        return new TimeFormatService(dateFormat, fromDateFormat, toDateFormat);
    }
}
