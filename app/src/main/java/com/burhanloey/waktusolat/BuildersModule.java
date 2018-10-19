package com.burhanloey.waktusolat;

import com.burhanloey.waktusolat.components.AlarmReceiver;
import com.burhanloey.waktusolat.components.LocationActivity;
import com.burhanloey.waktusolat.components.MainActivity;
import com.burhanloey.waktusolat.components.NextAlarmService;
import com.burhanloey.waktusolat.components.PrayerTimesFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class BuildersModule {
    @ContributesAndroidInjector
    abstract MainActivity bindMainActivity();

    @ContributesAndroidInjector
    abstract LocationActivity bindLocationActivity();

    @ContributesAndroidInjector
    abstract PrayerTimesFragment bindPrayerTimesFragment();

    @ContributesAndroidInjector
    abstract AlarmReceiver bindAlarmReceiver();

    @ContributesAndroidInjector
    abstract NextAlarmService bindNextAlarmService();
}
