package com.burhanloey.waktusolat;

import com.burhanloey.waktusolat.modules.alarm.PrayerAlarmModule;
import com.burhanloey.waktusolat.modules.esolat.ESolatModule;
import com.burhanloey.waktusolat.modules.state.StateModule;
import com.burhanloey.waktusolat.modules.storage.StorageModule;
import com.burhanloey.waktusolat.modules.timeformat.TimeFormatModule;

import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Component(modules = {
        AndroidSupportInjectionModule.class,
        BuildersModule.class,
        AppModule.class,
        StorageModule.class,
        ESolatModule.class,
        TimeFormatModule.class,
        StateModule.class,
        PrayerAlarmModule.class
})
public interface AppComponent extends AndroidInjector<App> {
    @Component.Builder
    abstract class Builder extends AndroidInjector.Builder<App> {}
}
