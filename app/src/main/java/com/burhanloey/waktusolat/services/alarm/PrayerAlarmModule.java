package com.burhanloey.waktusolat.services.alarm;

import android.content.Context;

import com.burhanloey.waktusolat.services.esolat.ESolatManager;
import com.burhanloey.waktusolat.services.state.StateManager;
import com.burhanloey.waktusolat.services.timeformat.TimeFormatter;

import dagger.Module;
import dagger.Provides;

@Module
public class PrayerAlarmModule {
    @Provides
    PrayerAlarmManager providePrayerAlarmManager(ESolatManager eSolatManager,
                                                 StateManager stateManager,
                                                 TimeFormatter timeFormatter,
                                                 Context context) {
        return new PrayerAlarmManager(eSolatManager, stateManager, timeFormatter, context);
    }
}
