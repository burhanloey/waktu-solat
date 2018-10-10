package com.burhanloey.waktusolat.activities;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;
import android.widget.Toast;

import com.burhanloey.waktusolat.R;
import com.burhanloey.waktusolat.services.esolat.ESolat;
import com.burhanloey.waktusolat.services.esolat.ESolatService;
import com.burhanloey.waktusolat.services.esolat.tasks.FetchCallback;
import com.burhanloey.waktusolat.services.state.StateService;

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
    StateService stateService;

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

        int position = stateService.getPosition();
        districtCodeSpinner.setSelection(position);
        fragment.loadPrayerTime(position);
    }

    @OnClick(R.id.fetch_button)
    public void fetch(View view) {
        int position = districtCodeSpinner.getSelectedItemPosition();
        final String districtCode = ESolat.getDistrictCode(position);

        eSolatService.fetch(this, districtCode, new FetchCallback() {
            @Override
            public void onSuccess() {
                fragment.loadPrayerTime(districtCode);
            }

            @Override
            public void onFailure(String message) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @OnItemSelected(R.id.spinner)
    public void load(int position) {
        stateService.setPosition(position);
        fragment.loadPrayerTime(position);
    }
}
