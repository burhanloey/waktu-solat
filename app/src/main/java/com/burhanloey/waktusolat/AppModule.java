package com.burhanloey.waktusolat;

import android.arch.persistence.room.Room;
import android.content.Context;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
    @Provides
    Context provideContext(App app) {
        return app.getApplicationContext();
    }

    @Provides
    AppDatabase provideAppDatabase(Context context) {
        return Room
                .databaseBuilder(context, AppDatabase.class, "waktusolat")
                .build();
    }

    @Provides
    ExecutorService provideExecutorService() {
        return Executors.newSingleThreadExecutor();
    }
}
