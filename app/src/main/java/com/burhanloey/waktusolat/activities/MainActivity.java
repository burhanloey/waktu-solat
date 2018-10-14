package com.burhanloey.waktusolat.activities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;
import android.widget.Toast;

import com.burhanloey.waktusolat.R;
import com.burhanloey.waktusolat.services.esolat.ESolat;
import com.burhanloey.waktusolat.services.esolat.ESolatManager;
import com.burhanloey.waktusolat.services.esolat.tasks.FetchCallback;
import com.burhanloey.waktusolat.services.state.StateManager;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemSelected;
import dagger.android.support.DaggerAppCompatActivity;

public class MainActivity extends DaggerAppCompatActivity {
    @Inject
    ESolatManager eSolatManager;

    @Inject
    StateManager stateManager;

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

    private void testAlarm() {
        Intent intent = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        long currentTimeMillis = System.currentTimeMillis();
        long triggerAtMillis = currentTimeMillis + 5000;

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (alarmManager != null) {
            alarmManager.set(AlarmManager.RTC_WAKEUP, triggerAtMillis, pendingIntent);
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

        testAlarm();
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
}
