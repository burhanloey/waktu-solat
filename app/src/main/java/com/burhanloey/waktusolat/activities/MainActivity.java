package com.burhanloey.waktusolat.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Spinner;
import android.widget.Toast;

import com.burhanloey.waktusolat.R;
import com.burhanloey.waktusolat.services.esolat.ESolat;
import com.burhanloey.waktusolat.services.esolat.ESolatService;
import com.burhanloey.waktusolat.services.esolat.YearlyPrayerTimes;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends DaggerAppCompatActivity {
    @Inject
    ESolatService eSolatService;

    @Inject
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void showMessage(View view) {
        Spinner spinner = findViewById(R.id.spinner);
        int position = spinner.getSelectedItemPosition();
        String districtCode = ESolat.getDistrictCode(position);

        Call<YearlyPrayerTimes> prayTimes = eSolatService.yearlyPrayerTimes(districtCode);

        prayTimes.enqueue(new Callback<YearlyPrayerTimes>() {
            @Override
            public void onResponse(@NonNull Call<YearlyPrayerTimes> call,
                                   @NonNull Response<YearlyPrayerTimes> response) {
                YearlyPrayerTimes yearlyPrayerTimes = response.body();

                if (yearlyPrayerTimes == null ||
                        yearlyPrayerTimes.getPrayerTime() == null ||
                        yearlyPrayerTimes.getPrayerTime().isEmpty()) {
                    return;
                }

                String firstPrayerTime = yearlyPrayerTimes.getPrayerTime().get(0).toString();
                Toast.makeText(context, firstPrayerTime, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(@NonNull Call<YearlyPrayerTimes> call, @NonNull Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
