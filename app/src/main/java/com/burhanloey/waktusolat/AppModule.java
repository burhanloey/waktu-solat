package com.burhanloey.waktusolat;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
    @Provides
    Context provideContext(App app) {
        return app.getApplicationContext();
    }
}
