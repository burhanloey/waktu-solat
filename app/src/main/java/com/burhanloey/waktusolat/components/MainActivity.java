package com.burhanloey.waktusolat.components;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Switch;
import android.widget.TextView;

import com.burhanloey.waktusolat.R;
import com.burhanloey.waktusolat.modules.alarm.PrayerAlarmManager;
import com.burhanloey.waktusolat.modules.state.StateManager;
import com.burhanloey.waktusolat.modules.timeformat.TimeFormatter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import dagger.android.support.DaggerAppCompatActivity;

public class MainActivity extends DaggerAppCompatActivity {
    @Inject
    PrayerAlarmManager prayerAlarmManager;

    @Inject
    StateManager stateManager;

    @Inject
    TimeFormatter timeFormatter;

    @BindView(R.id.daydate_textview)
    TextView dayDateTextView;

    @BindView(R.id.notifications_switch)
    Switch notificationsSwitch;

    PrayerTimesFragment fragment;

    private void bindFragment() {
        if (fragment == null) {
            fragment = (PrayerTimesFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.prayertimes_fragment);
        }
    }

    private void loadFragment() {
        int statePosition = stateManager.getStatePosition();
        int districtPosition = stateManager.getDistrictPosition();
        fragment.loadPrayerTime(statePosition, districtPosition);
    }

    private void init() {
        dayDateTextView.setText(timeFormatter.todayDisplay());

        loadFragment();

        boolean isChecked = stateManager.getNotificationsEnabled();
        notificationsSwitch.setChecked(isChecked);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        bindFragment();
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadFragment();
    }

    @OnCheckedChanged(R.id.notifications_switch)
    public void notify(boolean isChecked) {
        stateManager.saveNotificationsEnabled(isChecked);

        if (isChecked) {
            prayerAlarmManager.setNextAlarm();
        } else {
            prayerAlarmManager.cancelAlarm();
        }
    }

    @OnClick(R.id.location_button)
    public void chooseLocation() {
        Intent intent = new Intent(this, LocationActivity.class);
        startActivity(intent);
    }
}
