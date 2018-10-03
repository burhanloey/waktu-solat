package com.burhanloey.waktusolat;

import android.content.Context;

import com.burhanloey.waktusolat.services.ESolatService;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class AppModule {
    @Provides
    Context provideContext(App app) {
        return app.getApplicationContext();
    }

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
