package com.burhanloey.waktusolat.modules.esolat;

import com.burhanloey.waktusolat.modules.esolat.tasks.TaskManager;
import com.burhanloey.waktusolat.modules.storage.AppDatabase;
import com.burhanloey.waktusolat.modules.timeformat.TimeFormatter;

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
    TaskManager provideTaskManager(PrayerTimeDao prayerTimeDao, TimeFormatter timeFormatter) {
        return new TaskManager(prayerTimeDao, timeFormatter);
    }

    @Provides
    ESolatManager provideESolatManager(ESolatApi eSolatApi, TaskManager taskManager, TimeFormatter timeFormatter) {
        return new ESolatManager(eSolatApi, taskManager, timeFormatter);
    }
}
