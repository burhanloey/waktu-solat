package com.burhanloey.waktusolat.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.burhanloey.waktusolat.R;
import com.burhanloey.waktusolat.services.esolat.ESolat;
import com.burhanloey.waktusolat.services.esolat.ESolatService;
import com.burhanloey.waktusolat.services.esolat.model.PrayerTime;
import com.burhanloey.waktusolat.services.esolat.tasks.LoadCallback;
import com.burhanloey.waktusolat.services.timeformat.TimeFormatService;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindViews;
import butterknife.ButterKnife;
import dagger.android.support.DaggerFragment;

public class PrayerTimesFragment extends DaggerFragment {
    @Inject
    ESolatService eSolatService;

    @Inject
    TimeFormatService timeFormatService;

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
        List<String> time = timeFormatService.formatPrayerTime(prayerTime);

        for (int i = 0; i < time.size(); i++) {
            timeTextViews.get(i).setText(time.get(i));
        }
    }

    private void clear() {
        for (TextView textView : timeTextViews) {
            textView.setText(R.string.not_available);
        }
    }

    public void loadPrayerTime(final String districtCode) {
        eSolatService.load(districtCode, new LoadCallback() {
            @Override
            public void onResponse(PrayerTime prayerTime) {
                update(prayerTime);
            }

            @Override
            public void onMissingData() {
                clear();
            }
        });
    }

    public void loadPrayerTime(int position) {
        String districtCode = ESolat.getDistrictCode(position);
        loadPrayerTime(districtCode);
    }
}
