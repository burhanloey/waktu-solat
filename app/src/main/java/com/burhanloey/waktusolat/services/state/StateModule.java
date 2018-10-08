package com.burhanloey.waktusolat.services.state;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

@Module
public class StateModule {
    @Provides
    LocalStorage provideLocalStorage(Context context) {
        return new LocalStorage(context);
    }

    @Provides
    StateService provideStateService(LocalStorage localStorage) {
        return new StateService(localStorage);
    }
}
