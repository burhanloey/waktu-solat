package com.burhanloey.waktusolat.services.storage;

import android.arch.persistence.room.Room;
import android.content.Context;

import dagger.Module;
import dagger.Provides;

@Module
public class StorageModule {
    @Provides
    AppDatabase provideAppDatabase(Context context) {
        return Room
                .databaseBuilder(context, AppDatabase.class, "waktusolat")
                .build();
    }

    @Provides
    LocalStorage provideLocalStorage(Context context) {
        return new LocalStorage(context);
    }
}
