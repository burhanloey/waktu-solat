package com.burhanloey.waktusolat.services.esolat;

import com.burhanloey.waktusolat.services.storage.AppDatabase;
import com.burhanloey.waktusolat.services.timeformat.TimeFormatService;

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
    ESolatService provideESolatService(ESolatApi eSolatApi,
                                       PrayerTimeDao prayerTimeDao,
                                       TimeFormatService timeFormatService) {
        return new ESolatService(eSolatApi, prayerTimeDao, timeFormatService);
    }
}
