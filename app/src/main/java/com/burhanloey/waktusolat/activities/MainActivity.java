package com.burhanloey.waktusolat.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;
import android.widget.Toast;

import com.burhanloey.waktusolat.R;
import com.burhanloey.waktusolat.services.esolat.ESolat;
import com.burhanloey.waktusolat.services.esolat.ESolatService;
import com.burhanloey.waktusolat.services.esolat.tasks.FetchCallback;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemSelected;
import dagger.android.support.DaggerAppCompatActivity;

public class MainActivity extends DaggerAppCompatActivity {
    @Inject
    ESolatService eSolatService;

    @Inject
    Context context;

    @BindView(R.id.spinner)
    Spinner districtCodeSpinner;

    PrayerTimesFragment fragment;

    private void bindFragment() {
        if (fragment == null) {
            fragment = (PrayerTimesFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.prayertimes_fragment);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        bindFragment();

        SharedPreferences preferences = getPreferences(Context.MODE_PRIVATE);
        int position = preferences.getInt("position", 0);

        districtCodeSpinner.setSelection(position);
        fragment.loadPrayerTime(position);
    }

    private void toast(final String words) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context, words, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @OnClick(R.id.fetch_button)
    public void fetch(View view) {
        int position = districtCodeSpinner.getSelectedItemPosition();
        final String districtCode = ESolat.getDistrictCode(position);

        eSolatService.fetch(districtCode, new FetchCallback() {
            @Override
            public void onSuccess() {
                fragment.loadPrayerTime(districtCode);
            }

            @Override
            public void onFailure(String message) {
                toast(message);
            }
        });
    }

    @OnItemSelected(R.id.spinner)
    public void savePosition(int position) {
        SharedPreferences.Editor editor = getPreferences(Context.MODE_PRIVATE).edit();
        editor.putInt("position", position);
        editor.apply();

        fragment.loadPrayerTime(position);
    }
}
