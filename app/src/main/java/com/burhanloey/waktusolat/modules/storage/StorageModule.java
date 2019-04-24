package com.burhanloey.waktusolat.modules.storage;

import android.content.Context;

import androidx.room.Room;

import dagger.Module;
import dagger.Provides;

@Module
public class StorageModule {
    private static AppDatabase appDatabase;

    @Provides
    AppDatabase provideAppDatabase(Context context) {
        if (appDatabase == null) {
            appDatabase =  Room
                    .databaseBuilder(context, AppDatabase.class, "waktusolat")
                    .build();
        }
        return appDatabase;
    }

    @Provides
    LocalStorage provideLocalStorage(Context context) {
        return new LocalStorage(context);
    }
}
