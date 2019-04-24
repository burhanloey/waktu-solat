package com.burhanloey.waktusolat.modules.esolat;

import com.burhanloey.waktusolat.modules.esolat.tasks.TaskManager;
import com.burhanloey.waktusolat.modules.storage.AppDatabase;
import com.burhanloey.waktusolat.modules.timeformat.TimeFormatter;

import dagger.Module;
import dagger.Provides;

@Module
public class ESolatModule {
    @Provides
    PrayerTimeDao providePrayerTimeDao(AppDatabase appDatabase) {
        return appDatabase.prayerTimeDao();
    }

    @Provides
    TaskManager provideTaskManager(PrayerTimeDao prayerTimeDao, TimeFormatter timeFormatter) {
        return new TaskManager(prayerTimeDao, timeFormatter);
    }

    @Provides
    ESolatManager provideESolatManager(TaskManager taskManager, TimeFormatter timeFormatter) {
        return new ESolatManager(taskManager, timeFormatter);
    }
}
