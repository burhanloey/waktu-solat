package com.burhanloey.waktusolat.modules.esolat;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.burhanloey.waktusolat.modules.esolat.model.PrayerTime;
import com.burhanloey.waktusolat.modules.esolat.tasks.FetchCallback;
import com.burhanloey.waktusolat.modules.esolat.tasks.LoadCallback;
import com.burhanloey.waktusolat.modules.esolat.tasks.TaskManager;
import com.burhanloey.waktusolat.modules.timeformat.TimeFormatter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ESolatManager {
    private final TaskManager taskManager;
    private final TimeFormatter timeFormatter;

    public ESolatManager(TaskManager taskManager, TimeFormatter timeFormatter) {
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
    public void download(Context context, final String districtCode, final FetchCallback callback) {
        String url = "https://www.burhanloey.com/waktu-solat/calendar/" +
                timeFormatter.currentYear() + "/" +
                districtCode + ".json";

        RequestQueue requestQueue = Volley.newRequestQueue(context);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        PrayerTime[] prayerTimes = mapToPojo(response, districtCode);
                        taskManager.savePrayerTimes(prayerTimes, callback);
                    } catch (JSONException e) {
                        callback.onFailure(e.getMessage());
                    }
                },
                error -> callback.onFailure(error.getMessage()));

        requestQueue.add(request);
    }

    /**
     * Map response body to an array of PrayerTime.
     *
     * @param response Response JSON
     * @param districtCode District code
     * @return An array of PrayerTime
     * @throws JSONException For malformed JSON string
     */
    private PrayerTime[] mapToPojo(JSONObject response, String districtCode) throws JSONException {
        JSONArray prayerTimesJson = response.getJSONArray("prayerTime");
        PrayerTime[] prayerTimes = new PrayerTime[prayerTimesJson.length()];

        for (int i = 0; i < prayerTimesJson.length(); i++) {
            JSONObject prayerTimeJson = prayerTimesJson.getJSONObject(i);

            prayerTimes[i] = PrayerTime.builder()
                    .districtCode(districtCode)
                    .date(prayerTimeJson.getString("date"))
                    .fajr(prayerTimeJson.getString("fajr"))
                    .dhuhr(prayerTimeJson.getString("dhuhr"))
                    .asr(prayerTimeJson.getString("asr"))
                    .maghrib(prayerTimeJson.getString("maghrib"))
                    .isha(prayerTimeJson.getString("isha"))
                    .build();
        }

        return prayerTimes;
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
