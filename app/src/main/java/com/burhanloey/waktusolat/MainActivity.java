package com.burhanloey.waktusolat;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Spinner;
import android.widget.Toast;

import com.burhanloey.waktusolat.services.ESolatService;
import com.burhanloey.waktusolat.services.YearlyPrayerTimes;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private final static String[] districtCodes = { "JHR01", "JHR02", "JHR03", "JHR04", "KDH01",
            "KDH02", "KDH03", "KDH04", "KDH05", "KDH06", "KDH07", "KTN01", "KTN03", "MLK01",
            "NGS01", "NGS02", "PHG01", "PHG02", "PHG03", "PHG04", "PHG05", "PHG06", "PLS01",
            "PNG01", "PRK01", "PRK02", "PRK03", "PRK04", "PRK05", "PRK06", "PRK07", "SBH01",
            "SBH02", "SBH03", "SBH04", "SBH05", "SBH06", "SBH07", "SBH08", "SBH09", "SGR01",
            "SGR02", "SGR03", "SWK01", "SWK02", "SWK03", "SWK04", "SWK05", "SWK06", "SWK07",
            "SWK08", "SWK09", "TRG01", "TRG02", "TRG03", "TRG04", "WLY01", "WLY02" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void showMessage(View view) {
        Spinner spinner = findViewById(R.id.spinner);
        int position = spinner.getSelectedItemPosition();
        String districtCode = districtCodes[position];

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ESolatService.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ESolatService service = retrofit.create(ESolatService.class);

        Call<YearlyPrayerTimes> prayTimes = service.yearlyPrayerTimes(districtCode);

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
                Toast.makeText(getApplicationContext(), firstPrayerTime, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(@NonNull Call<YearlyPrayerTimes> call, @NonNull Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
