package com.burhanloey.waktusolat;

import com.burhanloey.waktusolat.activities.AlarmReceiver;
import com.burhanloey.waktusolat.activities.BootReceiver;
import com.burhanloey.waktusolat.activities.MainActivity;
import com.burhanloey.waktusolat.activities.NextAlarmService;
import com.burhanloey.waktusolat.activities.PrayerTimesFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class BuildersModule {
    @ContributesAndroidInjector
    abstract MainActivity bindMainActivity();

    @ContributesAndroidInjector
    abstract PrayerTimesFragment bindPrayerTimesFragment();

    @ContributesAndroidInjector
    abstract AlarmReceiver bindAlarmReceiver();

    @ContributesAndroidInjector
    abstract BootReceiver bindBootReceiver();

    @ContributesAndroidInjector
    abstract NextAlarmService bindNextAlarmService();
}
