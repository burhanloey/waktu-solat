package com.burhanloey.waktusolat.modules.esolat.tasks;

import android.os.AsyncTask;

import com.burhanloey.waktusolat.modules.esolat.PrayerTimeDao;
import com.burhanloey.waktusolat.modules.esolat.model.PrayerTime;
import com.burhanloey.waktusolat.modules.esolat.model.YearlyPrayerTimes;

import java.util.List;

/**
 * A task to save downloaded prayer times into database. Don't forget to associate district code
 * for each of the data. The callback will run on UI thread.
 */
public class SavePrayerTimesTask extends AsyncTask<YearlyPrayerTimes, Void, Void> {
    private final PrayerTimeDao prayerTimeDao;
    private final FetchCallback callback;

    public SavePrayerTimesTask(PrayerTimeDao prayerTimeDao, FetchCallback callback) {
        this.prayerTimeDao = prayerTimeDao;
        this.callback = callback;
    }

    @Override
    protected Void doInBackground(YearlyPrayerTimes... yearlyPrayerTimes) {
        List<PrayerTime> prayerTimes = yearlyPrayerTimes[0].getPrayerTime();
        prayerTimeDao.insertAll(prayerTimes);

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        callback.onCompleted();
    }
}
