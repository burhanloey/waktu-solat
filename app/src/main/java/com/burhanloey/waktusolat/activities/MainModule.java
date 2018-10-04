package com.burhanloey.waktusolat.activities;

import com.burhanloey.waktusolat.services.esolat.ESolatService;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class MainModule {
    @Provides
    Retrofit provideRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(ESolatService.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Provides
    ESolatService provideESolatService(Retrofit retrofit) {
        return retrofit.create(ESolatService.class);
    }
}
