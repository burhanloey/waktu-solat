package com.burhanloey.waktusolat.modules.esolat;

import com.burhanloey.waktusolat.modules.esolat.model.YearlyPrayerTimes;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ESolatApi {
    /**
     * API endpoint for yearly prayer times from ESolat website.
     *
     * @param zone Zone/district code
     * @return Yearly prayer times
     */
    @GET("waktu-solat/calendar/{year}/{zone}.json")
    Call<YearlyPrayerTimes> yearlyPrayerTimes(@Path("year") String year, @Path("zone") String zone);
}
