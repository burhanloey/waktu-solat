package com.burhanloey.waktusolat;

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
    ExecutorService provideExecutorService() {
        return Executors.newSingleThreadExecutor();
    }
}
