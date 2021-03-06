package com.burhanloey.waktusolat.components;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.burhanloey.waktusolat.R;
import com.burhanloey.waktusolat.modules.esolat.ESolat;
import com.burhanloey.waktusolat.modules.esolat.ESolatManager;
import com.burhanloey.waktusolat.modules.esolat.model.PrayerTime;
import com.burhanloey.waktusolat.modules.esolat.tasks.LoadCallback;
import com.burhanloey.waktusolat.modules.timeformat.TimeFormatter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindViews;
import butterknife.ButterKnife;
import dagger.android.support.DaggerFragment;

public class PrayerTimesFragment extends DaggerFragment {
    @Inject
    ESolatManager eSolatManager;

    @Inject
    TimeFormatter timeFormatter;

    @BindViews({
            R.id.subuhtime_textview,
            R.id.zuhurtime_textview,
            R.id.asartime_textview,
            R.id.maghribtime_textview,
            R.id.ishatime_textview
    })
    List<TextView> timeTextViews;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_prayertimes, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    private void update(PrayerTime prayerTime) {
        List<String> time = timeFormatter.formatPrayerTime(prayerTime);

        for (int i = 0; i < time.size(); i++) {
            timeTextViews.get(i).setText(time.get(i));
        }
    }

    private void clear() {
        for (TextView textView : timeTextViews) {
            textView.setText(R.string.not_available);
        }
    }

    /**
     * Load todays' prayer time.
     *
     * @param districtCode District code
     */
    public void loadPrayerTime(final String districtCode) {
        eSolatManager.load(districtCode, new LoadCallback() {
            @Override
            public void onResponse(List<PrayerTime> prayerTimes) {
                update(prayerTimes.get(0));  // Today's prayer times at index 0
            }

            @Override
            public void onMissingData() {
                clear();
            }
        });
    }

    /**
     * Same with loadPrayerTime(String) but takes spinners position.
     *
     * @param statePosition State spinner position
     * @param districtPosition District spinner position
     */
    public void loadPrayerTime(int statePosition, int districtPosition) {
        String districtCode = ESolat.getDistrictCode(statePosition, districtPosition);
        loadPrayerTime(districtCode);
    }
}
