package com.burhanloey.waktusolat.services;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ESolatService {
    String URL = "https://www.e-solat.gov.my/";

    @GET("index.php?r=esolatApi/takwimsolat&period=year")
    Call<YearlyPrayerTimes> yearlyPrayerTimes(@Query("zone") String zone);
}
