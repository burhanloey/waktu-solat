package com.burhanloey.waktusolat.services.esolat;

import android.annotation.SuppressLint;

import com.burhanloey.waktusolat.AppDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class ESolatModule {
    @Provides
    Retrofit provideRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(ESolat.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Provides
    ESolatApi provideESolatApi(Retrofit retrofit) {
        return retrofit.create(ESolatApi.class);
    }

    @Provides
    PrayerTimeDao providePrayerTimeDao(AppDatabase appDatabase) {
        return appDatabase.prayerTimeDao();
    }

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
}
