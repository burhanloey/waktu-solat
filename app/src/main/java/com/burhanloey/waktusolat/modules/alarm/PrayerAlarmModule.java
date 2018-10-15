package com.burhanloey.waktusolat.modules.alarm;

import android.content.Context;

import com.burhanloey.waktusolat.modules.esolat.ESolatManager;
import com.burhanloey.waktusolat.modules.state.StateManager;
import com.burhanloey.waktusolat.modules.timeformat.TimeFormatter;

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
