package com.burhanloey.waktusolat.services.esolat.tasks;

import android.os.AsyncTask;

import com.burhanloey.waktusolat.services.esolat.PrayerTimeDao;
import com.burhanloey.waktusolat.services.esolat.model.PrayerTime;
import com.burhanloey.waktusolat.services.timeformat.TimeFormatter;

/**
 * A task to load today's prayer time from database. The callbacks will run on UI thread.
 */
public class LoadPrayerTimesTask extends AsyncTask<String, Void, PrayerTime> {
    private final PrayerTimeDao prayerTimeDao;
    private final TimeFormatter timeFormatter;
    private final LoadCallback callback;

    public LoadPrayerTimesTask(PrayerTimeDao prayerTimeDao,
                               TimeFormatter timeFormatter,
                               LoadCallback callback) {
        this.prayerTimeDao = prayerTimeDao;
        this.timeFormatter = timeFormatter;
        this.callback = callback;
    }

    @Override
    protected PrayerTime doInBackground(String... strings) {
        String districtCode = strings[0];
        String today = timeFormatter.today();
        return prayerTimeDao.find(today, districtCode);
    }

    @Override
    protected void onPostExecute(PrayerTime prayerTime) {
        if (prayerTime == null) {
            callback.onMissingData();
        } else {
            callback.onResponse(prayerTime);
        }
    }
}
