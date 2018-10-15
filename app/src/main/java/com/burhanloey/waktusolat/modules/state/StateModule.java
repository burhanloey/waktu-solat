package com.burhanloey.waktusolat.modules.state;

import com.burhanloey.waktusolat.modules.storage.LocalStorage;

import dagger.Module;
import dagger.Provides;

@Module
public class StateModule {
    @Provides
    StateManager provideStateManager(LocalStorage localStorage) {
        return new StateManager(localStorage);
    }
}
