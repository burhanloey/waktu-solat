package com.burhanloey.waktusolat.components;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.burhanloey.waktusolat.R;
import com.burhanloey.waktusolat.modules.alarm.PrayerAlarmManager;
import com.burhanloey.waktusolat.modules.esolat.ESolat;
import com.burhanloey.waktusolat.modules.esolat.ESolatManager;
import com.burhanloey.waktusolat.modules.esolat.tasks.FetchCallback;
import com.burhanloey.waktusolat.modules.state.StateManager;

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

    @BindView(R.id.state_spinner)
    Spinner stateSpinner;

    @BindView(R.id.district_spinner)
    Spinner districtSpinner;

    @BindView(R.id.fetch_button)
    Button fetchButton;

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    @BindView(R.id.notifications_switch)
    Switch notificationsSwitch;

    PrayerTimesFragment fragment;

    private void bindFragment() {
        if (fragment == null) {
            fragment = (PrayerTimesFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.prayertimes_fragment);
        }
    }

    private void loadDistricts(int statePosition) {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                ESolat.getDistrictArray(statePosition), android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        districtSpinner.setAdapter(adapter);
    }

    private void loadPreviousState() {
        int statePosition = stateManager.getStatePosition();
        stateSpinner.setSelection(statePosition);

        loadDistricts(statePosition);

        int districtPosition = stateManager.getDistrictPosition();
        districtSpinner.setSelection(districtPosition);

        fragment.loadPrayerTime(statePosition, districtPosition);

        boolean isChecked = stateManager.getNotificationsEnabled();
        notificationsSwitch.setChecked(isChecked);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        bindFragment();
        loadPreviousState();
    }

    /**
     * Show visual cues that the app is loading.
     */
    private void showLoading() {
        fetchButton.setText(R.string.fetching);
        fetchButton.setEnabled(false);
        progressBar.setVisibility(View.VISIBLE);
    }

    /**
     * Hide loading visual cues.
     */
    private void hideLoading() {
        fetchButton.setText(R.string.fetch);
        fetchButton.setEnabled(true);
        progressBar.setVisibility(View.GONE);
    }

    @OnClick(R.id.fetch_button)
    public void fetch(View view) {
        int statePosition = stateSpinner.getSelectedItemPosition();
        int districtPosition = districtSpinner.getSelectedItemPosition();
        final String districtCode = ESolat.getDistrictCode(statePosition, districtPosition);

        showLoading();

        eSolatManager.fetch(districtCode, new FetchCallback() {
            @Override
            public void onCompleted() {
                hideLoading();

                fragment.loadPrayerTime(districtCode);
            }

            @Override
            public void onFailure(String message) {
                hideLoading();

                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @OnItemSelected(R.id.state_spinner)
    public void selectState(int statePosition) {
        loadDistricts(statePosition);

        stateManager.saveStatePosition(statePosition);

        int districtPosition = districtSpinner.getSelectedItemPosition();
        fragment.loadPrayerTime(statePosition, districtPosition);
    }

    @OnItemSelected(R.id.district_spinner)
    public void selectDistrict(int districtPosition) {
        stateManager.saveDistrictPosition(districtPosition);

        int statePosition = stateSpinner.getSelectedItemPosition();
        fragment.loadPrayerTime(statePosition, districtPosition);
    }

    @OnCheckedChanged(R.id.notifications_switch)
    public void notify(CompoundButton button, boolean isChecked) {
        stateManager.saveNotificationsEnabled(isChecked);

        if (isChecked) {
            prayerAlarmManager.setNextAlarm();
        } else {
            prayerAlarmManager.cancelAlarm();
        }
    }
}
