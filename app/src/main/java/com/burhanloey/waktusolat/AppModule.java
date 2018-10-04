package com.burhanloey.waktusolat;

import android.arch.persistence.room.Room;
import android.content.Context;

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
                .allowMainThreadQueries()  // TODO: Do transaction in AsyncTask
                .build();
    }
}
