package com.burhanloey.waktusolat.activities;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.burhanloey.waktusolat.R;
import com.burhanloey.waktusolat.services.alarm.PrayerAlarmManager;
import com.burhanloey.waktusolat.services.esolat.ESolat;
import com.burhanloey.waktusolat.services.esolat.ESolatManager;
import com.burhanloey.waktusolat.services.esolat.tasks.FetchCallback;
import com.burhanloey.waktusolat.services.state.StateManager;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.OnItemSelected;
import dagger.android.support.DaggerAppCompatActivity;

public class MainActivity extends DaggerAppCompatActivity {
    @Inject
    ESolatManager eSolatManager;

    @Inject
    PrayerAlarmManager prayerAlarmManager;

    @Inject
    StateManager stateManager;

    @Inject
    Context context;

    @BindView(R.id.spinner)
    Spinner districtCodeSpinner;

    @BindView(R.id.notifications_switch)
    Switch notificationsSwitch;

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

        int position = stateManager.getPosition();
        districtCodeSpinner.setSelection(position);
        fragment.loadPrayerTime(position);

        boolean isChecked = stateManager.getNotificationsEnabled();
        notificationsSwitch.setChecked(isChecked);
    }

    @OnClick(R.id.fetch_button)
    public void fetch(View view) {
        int position = districtCodeSpinner.getSelectedItemPosition();
        final String districtCode = ESolat.getDistrictCode(position);

        eSolatManager.fetch(districtCode, new FetchCallback() {
            @Override
            public void onCompleted() {
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
        stateManager.setPosition(position);
        fragment.loadPrayerTime(position);
    }

    @OnCheckedChanged(R.id.notifications_switch)
    public void notify(CompoundButton button, boolean isChecked) {
        stateManager.setNotificationsEnabled(isChecked);

        if (isChecked) {
            prayerAlarmManager.setNextAlarm();
        } else {
            prayerAlarmManager.cancelAlarm();
        }
    }
}
