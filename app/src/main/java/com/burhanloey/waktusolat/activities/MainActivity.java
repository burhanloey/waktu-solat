package com.burhanloey.waktusolat.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Spinner;
import android.widget.Toast;

import com.burhanloey.waktusolat.R;
import com.burhanloey.waktusolat.services.esolat.ESolat;
import com.burhanloey.waktusolat.services.esolat.ESolatApi;
import com.burhanloey.waktusolat.services.esolat.PrayerTimeDao;
import com.burhanloey.waktusolat.services.esolat.model.PrayerTime;
import com.burhanloey.waktusolat.services.esolat.model.YearlyPrayerTimes;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends DaggerAppCompatActivity {
    @Inject
    ESolatApi eSolatApi;

    @Inject
    PrayerTimeDao prayerTimeDao;

    @Inject
    Context context;

    private Spinner districtCodeSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (districtCodeSpinner == null) { districtCodeSpinner = findViewById(R.id.spinner); }
    }

    public void showMessage(View view) {
        int position = districtCodeSpinner.getSelectedItemPosition();
        final String districtCode = ESolat.getDistrictCode(position);

        Call<YearlyPrayerTimes> yearlyPrayerTimes = eSolatApi.yearlyPrayerTimes(districtCode);

        yearlyPrayerTimes.enqueue(new Callback<YearlyPrayerTimes>() {
            @Override
            public void onResponse(@NonNull Call<YearlyPrayerTimes> call,
                                   @NonNull Response<YearlyPrayerTimes> response) {
                YearlyPrayerTimes yearlyPrayerTimes = response.body();

                if (yearlyPrayerTimes == null ||
                        yearlyPrayerTimes.getPrayerTime() == null ||
                        yearlyPrayerTimes.getPrayerTime().isEmpty()) {
                    return;
                }

                for (PrayerTime prayerTime : yearlyPrayerTimes.getPrayerTime()) {
                    prayerTime.setDistrictCode(districtCode);
                }

                prayerTimeDao.insertAll(yearlyPrayerTimes.getPrayerTime());

                PrayerTime firstPrayerTime = prayerTimeDao.findOne("05-Feb-2018", districtCode);

                Toast.makeText(context, firstPrayerTime.toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(@NonNull Call<YearlyPrayerTimes> call, @NonNull Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
