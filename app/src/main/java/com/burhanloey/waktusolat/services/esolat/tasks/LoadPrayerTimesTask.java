package com.burhanloey.waktusolat.services.esolat.tasks;

import android.os.AsyncTask;

import com.burhanloey.waktusolat.services.esolat.PrayerTimeDao;
import com.burhanloey.waktusolat.services.esolat.model.PrayerTime;
import com.burhanloey.waktusolat.services.timeformat.TimeFormatter;

import java.util.ArrayList;
import java.util.List;

/**
 * A task to load today's prayer time from database. The callbacks will run on UI thread.
 */
public class LoadPrayerTimesTask extends AsyncTask<String, Void, List<PrayerTime>> {
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
    protected List<PrayerTime> doInBackground(String... strings) {
        String districtCode = strings[0];

        List<String> dates = new ArrayList<>(2);
        dates.add(timeFormatter.today());
        dates.add(timeFormatter.tomorrow());

        return prayerTimeDao.find(districtCode, dates);
    }

    @Override
    protected void onPostExecute(List<PrayerTime> prayerTimes) {
        if (prayerTimes.isEmpty()) {
            callback.onMissingData();
        } else {
            callback.onResponse(prayerTimes);
        }
    }
}
