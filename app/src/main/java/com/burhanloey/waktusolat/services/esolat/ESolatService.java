package com.burhanloey.waktusolat.services.esolat;

import android.support.annotation.NonNull;

import com.burhanloey.waktusolat.services.esolat.model.PrayerTime;
import com.burhanloey.waktusolat.services.esolat.model.YearlyPrayerTimes;
import com.burhanloey.waktusolat.services.esolat.tasks.FetchCallback;
import com.burhanloey.waktusolat.services.esolat.tasks.LoadCallback;
import com.burhanloey.waktusolat.services.timeformat.TimeFormatService;

import java.util.List;
import java.util.concurrent.ExecutorService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ESolatService {
    private final ESolatApi eSolatApi;
    private final PrayerTimeDao prayerTimeDao;
    private final TimeFormatService timeFormatService;
    private final ExecutorService executorService;

    public ESolatService(ESolatApi eSolatApi,
                         PrayerTimeDao prayerTimeDao,
                         TimeFormatService timeFormatService,
                         ExecutorService executorService) {
        this.eSolatApi = eSolatApi;
        this.prayerTimeDao = prayerTimeDao;
        this.timeFormatService = timeFormatService;
        this.executorService = executorService;
    }

    /**
     * Save downloaded prayer times into database. Don't forget to associate district code for each
     * of the data.
     */
    private void savePrayerTimes(final List<PrayerTime> prayerTimes,
                                 final String districtCode,
                                 final FetchCallback callback) {
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                for (PrayerTime prayerTime : prayerTimes) {
                    prayerTime.setDistrictCode(districtCode);
                }

                prayerTimeDao.insertAll(prayerTimes);

                callback.onSuccess();
            }
        });
    }

    /**
     * Fetch data from API, and then save to database. Supply FetchCallback for things to do during
     * API failure, and after success.
     *
     * @param districtCode District code for which data to fetch for
     * @param callback FetchCallback for API failure and on success
     */
    public void fetch(final String districtCode, final FetchCallback callback) {
        eSolatApi.yearlyPrayerTimes(districtCode)
                .enqueue(new Callback<YearlyPrayerTimes>() {
                    @Override
                    public void onResponse(@NonNull Call<YearlyPrayerTimes> call,
                                           @NonNull Response<YearlyPrayerTimes> response) {
                        YearlyPrayerTimes yearlyPrayerTimes = response.body();

                        if (yearlyPrayerTimes == null ||
                                yearlyPrayerTimes.getPrayerTime() == null ||
                                yearlyPrayerTimes.getPrayerTime().isEmpty()) {
                            return;
                        }

                        savePrayerTimes(yearlyPrayerTimes.getPrayerTime(), districtCode, callback);
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
     * on missing data.
     *
     * @param districtCode District code for which data to load for
     * @param callback LoadCallback on response and on missing data
     */
    public void load(final String districtCode, final LoadCallback callback) {
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                String today = timeFormatService.today();
                PrayerTime prayerTime = prayerTimeDao.find(today, districtCode);

                if (prayerTime == null) {
                    callback.onMissingData();
                } else {
                    callback.onResponse(prayerTime);
                }
            }
        });
    }
}
