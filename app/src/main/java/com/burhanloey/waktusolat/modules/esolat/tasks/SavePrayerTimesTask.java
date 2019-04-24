package com.burhanloey.waktusolat.modules.esolat.tasks;

import android.os.AsyncTask;

import com.burhanloey.waktusolat.modules.esolat.PrayerTimeDao;
import com.burhanloey.waktusolat.modules.esolat.model.PrayerTime;

/**
 * A task to save downloaded prayer times into database. Don't forget to associate district code
 * for each of the data. The callback will run on UI thread.
 */
public class SavePrayerTimesTask extends AsyncTask<PrayerTime, Void, Void> {
    private final PrayerTimeDao prayerTimeDao;
    private final FetchCallback callback;

    public SavePrayerTimesTask(PrayerTimeDao prayerTimeDao, FetchCallback callback) {
        this.prayerTimeDao = prayerTimeDao;
        this.callback = callback;
    }

    @Override
    protected Void doInBackground(PrayerTime... prayerTimes) {
        prayerTimeDao.upsertAll(prayerTimes);

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        callback.onCompleted();
    }
}
