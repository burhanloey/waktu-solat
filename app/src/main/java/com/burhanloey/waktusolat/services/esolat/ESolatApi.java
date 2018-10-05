package com.burhanloey.waktusolat.services.esolat;

import com.burhanloey.waktusolat.services.esolat.model.YearlyPrayerTimes;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ESolatApi {
    @GET("index.php?r=esolatApi/takwimsolat&period=year")
    Call<YearlyPrayerTimes> yearlyPrayerTimes(@Query("zone") String zone);
}
