package com.burhanloey.waktusolat;

import com.burhanloey.waktusolat.services.esolat.ESolatModule;

import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Component(modules = {
        AndroidSupportInjectionModule.class,
        BuildersModule.class,
        AppModule.class,
        ESolatModule.class
})
public interface AppComponent extends AndroidInjector<App> {
    @Component.Builder
    abstract class Builder extends AndroidInjector.Builder<App> {}
}
