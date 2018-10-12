package com.burhanloey.waktusolat.services.state;

import com.burhanloey.waktusolat.services.storage.LocalStorage;

import dagger.Module;
import dagger.Provides;

@Module
public class StateModule {
    @Provides
    StateManager provideStateManager(LocalStorage localStorage) {
        return new StateManager(localStorage);
    }
}
