package com.burhanloey.waktusolat.components;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.burhanloey.waktusolat.R;
import com.burhanloey.waktusolat.modules.esolat.ESolat;
import com.burhanloey.waktusolat.modules.esolat.ESolatManager;
import com.burhanloey.waktusolat.modules.esolat.tasks.FetchCallback;
import com.burhanloey.waktusolat.modules.state.StateManager;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemSelected;
import butterknife.OnTouch;
import dagger.android.support.DaggerAppCompatActivity;

public class LocationActivity extends DaggerAppCompatActivity {
    @Inject
    StateManager stateManager;

    @Inject
    ESolatManager eSolatManager;

    @Inject
    Context context;

    @BindView(R.id.state_spinner)
    Spinner stateSpinner;

    @BindView(R.id.district_spinner)
    Spinner districtSpinner;

    @BindView(R.id.download_button)
    Button downloadButton;

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    private boolean isInteracting = false;

    private void loadStates() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.state, R.layout.spinner_item);
        adapter.setDropDownViewResource(R.layout.spinner_item);
        stateSpinner.setAdapter(adapter);
    }

    private void loadDistricts(int statePosition) {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                ESolat.getDistrictArray(statePosition), R.layout.spinner_item);
        adapter.setDropDownViewResource(R.layout.spinner_item);
        districtSpinner.setAdapter(adapter);
    }

    private void init() {
        loadStates();

        int statePosition = stateManager.getStatePosition();
        stateSpinner.setSelection(statePosition);

        loadDistricts(statePosition);

        int districtPosition = stateManager.getDistrictPosition();
        districtSpinner.setSelection(districtPosition);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        ButterKnife.bind(this);
        init();
    }

    @OnItemSelected(R.id.state_spinner)
    public void selectState(int statePosition) {
        if (isInteracting) {
            loadDistricts(statePosition);

            stateManager.saveStatePosition(statePosition);
        }
    }

    @OnTouch(R.id.state_spinner)
    public boolean interact() {
        isInteracting = true;
        return false;  // to not consume event (like not calling preventDefault() in JavaScript)
    }

    @OnItemSelected(R.id.district_spinner)
    public void selectDistrict(int districtPosition) {
        stateManager.saveDistrictPosition(districtPosition);
    }

    /**
     * Show visual cues that the app is loading.
     */
    private void showLoading() {
        downloadButton.setText(R.string.downloading);
        downloadButton.setEnabled(false);
        progressBar.setVisibility(View.VISIBLE);
    }

    /**
     * Hide loading visual cues.
     */
    private void hideLoading() {
        downloadButton.setText(R.string.download);
        downloadButton.setEnabled(true);
        progressBar.setVisibility(View.GONE);
    }

    @OnClick(R.id.download_button)
    public void download() {
        int statePosition = stateSpinner.getSelectedItemPosition();
        int districtPosition = districtSpinner.getSelectedItemPosition();
        final String districtCode = ESolat.getDistrictCode(statePosition, districtPosition);

        showLoading();

        eSolatManager.download(this, districtCode, new FetchCallback() {
            @Override
            public void onCompleted() {
                hideLoading();
            }

            @Override
            public void onSavingData() {
                downloadButton.setText(R.string.saving_data);
            }

            @Override
            public void onFailure(String message) {
                hideLoading();

                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @OnClick(R.id.back_button)
    public void back() {
        finish();
    }
}
