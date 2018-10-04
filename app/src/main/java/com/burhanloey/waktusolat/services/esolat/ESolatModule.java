package com.burhanloey.waktusolat.services.esolat;

import com.burhanloey.waktusolat.AppDatabase;

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
    ESolatService provideESolatService(Retrofit retrofit) {
        return retrofit.create(ESolatService.class);
    }

    @Provides
    PrayerTimeDao providePrayerTimeDao(AppDatabase appDatabase) {
        return appDatabase.prayerTimeDao();
    }
}
