package com.burhanloey.waktusolat.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import com.burhanloey.waktusolat.R;
import com.burhanloey.waktusolat.services.esolat.ESolat;
import com.burhanloey.waktusolat.services.esolat.ESolatApi;
import com.burhanloey.waktusolat.services.esolat.PrayerTimeDao;
import com.burhanloey.waktusolat.services.esolat.model.PrayerTime;
import com.burhanloey.waktusolat.services.esolat.model.YearlyPrayerTimes;

import java.util.List;
import java.util.concurrent.ExecutorService;

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
    ExecutorService executorService;

    @Inject
    Context context;

    private Spinner districtCodeSpinner;
    private PrayerTimesFragment fragment;

    private void bindView() {
        if (districtCodeSpinner == null) {
            districtCodeSpinner = findViewById(R.id.spinner);
        }
        if (fragment == null) {
            fragment = (PrayerTimesFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.prayertimes_fragment);
        }
    }
    private void loadPrayerTime(int position) {
        String districtCode = ESolat.getDistrictCode(position);
        fragment.loadPrayerTime(districtCode);
    }

    private void bindBehavior() {
        districtCodeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SharedPreferences.Editor editor = getPreferences(Context.MODE_PRIVATE).edit();
                editor.putInt("position", position);
                editor.apply();

                loadPrayerTime(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindView();
        bindBehavior();

        SharedPreferences preferences = getPreferences(Context.MODE_PRIVATE);
        int position = preferences.getInt("position", 0);

        districtCodeSpinner.setSelection(position);
        loadPrayerTime(position);
    }

    private void toast(final String words) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context, words, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void savePrayerTimes(final List<PrayerTime> prayerTimes, final String districtCode) {
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                for (PrayerTime prayerTime : prayerTimes) {
                    prayerTime.setDistrictCode(districtCode);
                }

                prayerTimeDao.insertAll(prayerTimes);

                fragment.loadPrayerTime(districtCode);
            }
        });
    }

    public void fetch(View view) {
        int position = districtCodeSpinner.getSelectedItemPosition();
        final String districtCode = ESolat.getDistrictCode(position);

        eSolatApi.yearlyPrayerTimes(districtCode)
                .enqueue(new Callback<YearlyPrayerTimes>() {
                    @Override
                    public void onResponse(@NonNull Call<YearlyPrayerTimes> call,
                                           @NonNull Response<YearlyPrayerTimes> response) {
                        YearlyPrayerTimes yearlyPrayerTimes = response.body();

                        if (yearlyPrayerTimes == null ||
                                yearlyPrayerTimes.getPrayerTime() == null ||
                                yearlyPrayerTimes.getPrayerTime().isEmpty()) {
                            return;
                        }

                        savePrayerTimes(yearlyPrayerTimes.getPrayerTime(), districtCode);
                    }

                    @Override
                    public void onFailure(@NonNull Call<YearlyPrayerTimes> call,
                                          @NonNull Throwable t) {
                        toast(t.getMessage());
                    }
                });
    }
}
