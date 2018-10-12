package com.burhanloey.waktusolat.services.esolat;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.burhanloey.waktusolat.services.esolat.model.PrayerTime;
import com.burhanloey.waktusolat.services.esolat.model.YearlyPrayerTimes;
import com.burhanloey.waktusolat.services.esolat.tasks.FetchCallback;
import com.burhanloey.waktusolat.services.esolat.tasks.LoadCallback;
import com.burhanloey.waktusolat.services.timeformat.TimeFormatter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ESolatService {
    private final ESolatApi eSolatApi;
    private final PrayerTimeDao prayerTimeDao;
    private final TimeFormatter timeFormatter;

    public ESolatService(ESolatApi eSolatApi,
                         PrayerTimeDao prayerTimeDao,
                         TimeFormatter timeFormatter) {
        this.eSolatApi = eSolatApi;
        this.prayerTimeDao = prayerTimeDao;
        this.timeFormatter = timeFormatter;
    }

    /**
     * Save downloaded prayer times into database. Don't forget to associate district code for each
     * of the data. Also, the callback should be run on UI thread.
     */
    private void savePrayerTimes(final Activity activity,
                                 final List<PrayerTime> prayerTimes,
                                 final String districtCode,
                                 final FetchCallback callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (PrayerTime prayerTime : prayerTimes) {
                    prayerTime.setDistrictCode(districtCode);
                }

                prayerTimeDao.insertAll(prayerTimes);

                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        callback.onSuccess();
                    }
                });
            }
        }).start();
    }

    /**
     * Fetch data from API, and then save to database. Supply FetchCallback for things to do during
     * API failure, and after success. The callbacks will run on UI thread.
     *
     * @param activity Activity where this method is called
     * @param districtCode District code for which data to fetch for
     * @param callback FetchCallback for API failure and on success
     */
    public void fetch(final Activity activity,
                      final String districtCode,
                      final FetchCallback callback) {
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

                        savePrayerTimes(
                                activity,
                                yearlyPrayerTimes.getPrayerTime(),
                                districtCode,
                                callback
                        );
                    }

                    @Override
                    public void onFailure(@NonNull Call<YearlyPrayerTimes> call,
                                          @NonNull final Throwable t) {
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                callback.onFailure(t.getMessage());
                            }
                        });
                    }
                });
    }

    /**
     * Load today's prayer time from database. Supply LoadCallback for things to do on response and
     * on missing data. The callbacks will run on UI thread.
     *
     * @param activity Activity where this method is called
     * @param districtCode District code for which data to load for
     * @param callback LoadCallback on response and on missing data
     */
    public void load(final Activity activity,
                     final String districtCode,
                     final LoadCallback callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String today = timeFormatter.today();
                final PrayerTime prayerTime = prayerTimeDao.find(today, districtCode);

                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (prayerTime == null) {
                            callback.onMissingData();
                        } else {
                            callback.onResponse(prayerTime);
                        }
                    }
                });
            }
        }).start();
    }
}
