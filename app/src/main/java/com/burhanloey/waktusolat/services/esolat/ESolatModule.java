package com.burhanloey.waktusolat.services.esolat;

import com.burhanloey.waktusolat.services.storage.AppDatabase;
import com.burhanloey.waktusolat.services.timeformat.TimeFormatter;

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
    ESolatDownloader provideESolatDownloader(ESolatApi eSolatApi,
                                             PrayerTimeDao prayerTimeDao,
                                             TimeFormatter timeFormatter) {
        return new ESolatDownloader(eSolatApi, prayerTimeDao, timeFormatter);
    }
}
