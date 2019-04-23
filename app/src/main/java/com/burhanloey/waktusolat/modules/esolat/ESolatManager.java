package com.burhanloey.waktusolat.modules.esolat;

import android.support.annotation.NonNull;

import com.burhanloey.waktusolat.modules.esolat.model.PrayerTime;
import com.burhanloey.waktusolat.modules.esolat.model.YearlyPrayerTimes;
import com.burhanloey.waktusolat.modules.esolat.tasks.FetchCallback;
import com.burhanloey.waktusolat.modules.esolat.tasks.LoadCallback;
import com.burhanloey.waktusolat.modules.esolat.tasks.TaskManager;
import com.burhanloey.waktusolat.modules.timeformat.TimeFormatter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ESolatManager {
    private final ESolatApi eSolatApi;
    private final TaskManager taskManager;
    private final TimeFormatter timeFormatter;

    public ESolatManager(ESolatApi eSolatApi, TaskManager taskManager, TimeFormatter timeFormatter) {
        this.eSolatApi = eSolatApi;
        this.taskManager = taskManager;
        this.timeFormatter = timeFormatter;
    }

    /**
     * Fetch data from API, and then save to database. Supply FetchCallback for things to do during
     * API failure, and after success. The callbacks will run on UI thread.
     *
     * @param districtCode District code for which data to download for
     * @param callback FetchCallback for API failure and on success
     */
    public void download(final String districtCode, final FetchCallback callback) {
        eSolatApi.yearlyPrayerTimes(timeFormatter.currentYear(), districtCode)
                .enqueue(new Callback<YearlyPrayerTimes>() {
                    @Override
                    public void onResponse(@NonNull Call<YearlyPrayerTimes> call,
                                           @NonNull Response<YearlyPrayerTimes> response) {
                        YearlyPrayerTimes yearlyPrayerTimes = response.body();

                        if (yearlyPrayerTimes == null ||
                                yearlyPrayerTimes.getPrayerTime() == null) {
                            return;
                        }

                        for (PrayerTime prayerTime : yearlyPrayerTimes.getPrayerTime()) {
                            prayerTime.setDistrictCode(districtCode);
                        }

                        callback.onSavingData();

                        taskManager.savePrayerTimes(yearlyPrayerTimes, callback);
                    }

                    @Override
                    public void onFailure(@NonNull Call<YearlyPrayerTimes> call,
                                          @NonNull Throwable t) {
                        callback.onFailure(t.getMessage());
                    }
                });
    }

    /**
     * Load today's prayer time from database. Supply LoadCallback for things to do on response and
     * on missing data. The callbacks will run on UI thread.
     *
     * @param districtCode District code for which data to load for
     * @param callback LoadCallback on response and on missing data
     */
    public void load(String districtCode, LoadCallback callback) {
        taskManager.loadPrayerTimes(districtCode, callback);
    }
}
